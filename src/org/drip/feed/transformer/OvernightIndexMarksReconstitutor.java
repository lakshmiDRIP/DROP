
package org.drip.feed.transformer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * OvernightIndexMarksReconstitutor transforms the Overnight Instrument Manifest Measures (e.g., Deposits and
 *  OIS) Feed Inputs into Formats appropriate for Overnight Curve Construction and Measure Generation.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OvernightIndexMarksReconstitutor {

	/**
	 * The Standard Overnight Input Calibration Manifest Measure Scaler
	 */

	public static final double s_dblScaler = 0.01;

	/**
	 * The Standard Overnight Swap Maturity Tenors
	 */

	public static final java.lang.String[] s_astrMaturityTenor = new java.lang.String[] {"1W", "2W", "3W",
		"1M", "2M", "3M", "4M", "5M", "6M", "9M", "1Y", "18M", "2Y", "3Y", "4Y", "5Y"};

	/**
	 * Dump the Regularized Marks of the ISTQ Map
	 * 
	 * @param strCurrency Currency
	 * @param mapISTQ The ISTQ Map
	 * @param iLatentStateType SHAPE PRESERVING/SMOOTH
	 * 
	 * @return TRUE - The Regularized ISTQ Map Successfully Dumped
	 */

	public static final boolean RegularizeMarks (
		final java.lang.String strCurrency,
		final java.util.Map<org.drip.analytics.date.JulianDate, org.drip.feed.loader.InstrumentSetTenorQuote>
			mapISTQ,
		final int iLatentStateType)
	{
		if (null == mapISTQ || 0 == mapISTQ.size()) return false;

		java.lang.String strHeader = "Date,";

		for (java.lang.String strMaturityTenor : s_astrMaturityTenor)
			strHeader += "OISPROC:" + strMaturityTenor + ",";

		for (java.lang.String strMaturityTenor : s_astrMaturityTenor)
			strHeader += "<<OISRAW:" + strMaturityTenor + ">>,";

		System.out.println (strHeader);

		for (java.util.Map.Entry<org.drip.analytics.date.JulianDate,
			org.drip.feed.loader.InstrumentSetTenorQuote> meISTQ : mapISTQ.entrySet()) {
			if (null == meISTQ) continue;

			org.drip.analytics.date.JulianDate dtSpot = meISTQ.getKey();

			org.drip.feed.loader.InstrumentSetTenorQuote istq = meISTQ.getValue();

			if (null == dtSpot || null == istq) continue;

			java.lang.String strDump = dtSpot.toString() + ",";

			double[] adblOISQuote = istq.instrumentQuote ("OIS");

			java.lang.String[] astrOISMaturityTenor = istq.instrumentTenor ("OIS");

			int iNumRegularizedTenor = s_astrMaturityTenor.length;
			double[] adblRegularizedOISQuote = new double[iNumRegularizedTenor];
			int iNumOISQuote = null == adblOISQuote ? 0 : adblOISQuote.length;
			int iNumOISTenor = null == astrOISMaturityTenor ? 0 : astrOISMaturityTenor.length;

			if (0 == iNumOISQuote || iNumOISQuote != iNumOISTenor) continue;

			org.drip.state.discount.MergedDiscountForwardCurve dcOvernight =
				org.drip.service.template.LatentMarketStateBuilder.OvernightCurve (dtSpot, strCurrency, null,
					null, null, null, null, null, null, null, null, null, astrOISMaturityTenor, adblOISQuote,
						"SwapRate", iLatentStateType);

			org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
				org.drip.param.market.CurveSurfaceQuoteContainer();

			if (!csqc.setFundingState (dcOvernight)) continue;

			org.drip.param.valuation.ValuationParams valParams =
				org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian());

			for (int i = 0; i < iNumRegularizedTenor; ++i)
				adblRegularizedOISQuote[i] = 0.;

			org.drip.product.rates.FixFloatComponent[] aOIS =
				org.drip.service.template.OTCInstrumentBuilder.OISFixFloat (dtSpot, strCurrency,
					s_astrMaturityTenor, adblRegularizedOISQuote, false);

			for (org.drip.product.rates.FixFloatComponent ffc : aOIS) {
				try {
					strDump += org.drip.quant.common.FormatUtil.FormatDouble (ffc.measureValue (valParams,
						null, csqc, null, "SwapRate"), 1, 6, 1.) + ",";
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					continue;
				}
			}

			for (int i = 0; i < iNumOISQuote; ++i)
				strDump += org.drip.quant.common.FormatUtil.FormatDouble (adblOISQuote[i], 1, 6, 1.) + ",";

			System.out.println (strDump);
		}

		return true;
	}

	/**
	 * Re-constitute the Horizon Quote Marks Using a Shape Preserving Re-constructor
	 * 
	 * @param strCurrency Currency
	 * @param strMarksLocation The Location of the CSV Marks File
	 * 
	 * @return The Transformed Horizon Quote Marks
	 */

	public static final boolean ShapePreservingRegularization (
		final java.lang.String strCurrency,
		final java.lang.String strMarksLocation)
	{
		org.drip.feed.loader.CSVGrid csvGrid = org.drip.feed.loader.CSVParser.StringGrid (strMarksLocation,
			false);

		return null == csvGrid ? false : RegularizeMarks (strCurrency, csvGrid.groupedOrderedDouble
			(s_dblScaler), org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);
	}

	/**
	 * Re-constitute the Horizon Quote Marks Using a Smooth Re-constructor
	 * 
	 * @param strCurrency Currency
	 * @param strMarksLocation The Location of the CSV Marks File
	 * 
	 * @return The Transformed Horizon Quote Marks
	 */

	public static final boolean SmoothRegularization (
		final java.lang.String strCurrency,
		final java.lang.String strMarksLocation)
	{
		org.drip.feed.loader.CSVGrid csvGrid = org.drip.feed.loader.CSVParser.StringGrid (strMarksLocation,
			false);

		return null == csvGrid ? false : RegularizeMarks (strCurrency, csvGrid.groupedOrderedDouble
			(s_dblScaler), org.drip.service.template.LatentMarketStateBuilder.SMOOTH);
	}
}
