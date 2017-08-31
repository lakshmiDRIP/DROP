
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
 * FundingFixFloatMarksReconstitutor transforms the Funding Instrument Manifest Measures (e.g., Forward Rate
 *  for Deposits, Forward Rate for Futures, and Swap Rates for Fix/Float Swap) Feed Inputs into Formats
 *  appropriate for Funding Curve Construction and Measure Generation.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingFixFloatMarksReconstitutor {

	/**
	 * The Standard Funding Input Calibration Manifest Measure Scaler
	 */

	public static final double s_dblScaler = 0.01;

	/**
	 * The Standard Deposit Maturity Tenors
	 */

	public static final java.lang.String[] s_astrDepositTenor = new java.lang.String[] {"1M", "2M", "3M",
		"4M", "5M", "6M"};

	/**
	 * The Standard Fix Float Maturity Tenors
	 */

	public static final java.lang.String[] s_astrFixFloatTenor = new java.lang.String[] {"1Y", "2Y", "3Y",
		"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"};

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

		for (java.lang.String strMaturityTenor : s_astrDepositTenor)
			strHeader += "DEPOSITPROC:" + strMaturityTenor + ",";

		for (java.lang.String strMaturityTenor : s_astrFixFloatTenor)
			strHeader += "FIXFLOATPROC:" + strMaturityTenor + ",";

		for (java.lang.String strMaturityTenor : s_astrDepositTenor)
			strHeader += "<<DEPOSITRAW:" + strMaturityTenor + ">>,";

		for (java.lang.String strMaturityTenor : s_astrFixFloatTenor)
			strHeader += "<<FIXFLOATRAW:" + strMaturityTenor + ">>,";

		System.out.println (strHeader);

		for (java.util.Map.Entry<org.drip.analytics.date.JulianDate,
			org.drip.feed.loader.InstrumentSetTenorQuote> meISTQ : mapISTQ.entrySet()) {
			if (null == meISTQ) continue;

			org.drip.analytics.date.JulianDate dtSpot = meISTQ.getKey();

			org.drip.feed.loader.InstrumentSetTenorQuote istq = meISTQ.getValue();

			if (null == dtSpot || null == istq) continue;

			java.lang.String strDump = dtSpot.toString() + ",";

			double[] adblDepositQuote = istq.instrumentQuote ("DEPOSIT");

			java.lang.String[] astrDepositMaturityTenor = istq.instrumentTenor ("DEPOSIT");

			double[] adblFixFloatQuote = istq.instrumentQuote ("FIXFLOAT");

			java.lang.String[] astrFixFloatMaturityTenor = istq.instrumentTenor ("FIXFLOAT");

			int iNumDepositQuote = null == adblDepositQuote ? 0 : adblDepositQuote.length;
			int iNumFixFloatQuote = null == adblFixFloatQuote ? 0 : adblFixFloatQuote.length;
			int iNumDepositTenor = null == astrDepositMaturityTenor ? 0 : astrDepositMaturityTenor.length;
			int iNumFixFloatTenor = null == astrFixFloatMaturityTenor ? 0 : astrFixFloatMaturityTenor.length;

			if (0 == iNumFixFloatQuote || iNumDepositQuote != iNumDepositTenor || iNumFixFloatQuote !=
				iNumFixFloatTenor)
				continue;

			org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
				org.drip.service.template.LatentMarketStateBuilder.FundingCurve (dtSpot, strCurrency,
					astrDepositMaturityTenor, adblDepositQuote, "ForwardRate", null, "ForwardRate",
						astrFixFloatMaturityTenor, adblFixFloatQuote, "SwapRate", iLatentStateType);

			org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
				org.drip.param.market.CurveSurfaceQuoteContainer();

			if (!csqc.setFundingState (dcFunding)) continue;

			org.drip.param.valuation.ValuationParams valParams =
				org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian());

			org.drip.product.rates.SingleStreamComponent[] aSSCDeposit =
				org.drip.service.template.OTCInstrumentBuilder.FundingDeposit (dtSpot, strCurrency,
					s_astrDepositTenor);

			for (org.drip.product.rates.SingleStreamComponent sscDeposit : aSSCDeposit) {
				try {
					strDump += org.drip.quant.common.FormatUtil.FormatDouble (sscDeposit.measureValue
						(valParams, null, csqc, null, "ForwardRate"), 1, 6, 1.) + ",";
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					continue;
				}
			}

			org.drip.product.rates.FixFloatComponent[] aFixFloat =
				org.drip.service.template.OTCInstrumentBuilder.FixFloatStandard (dtSpot, strCurrency, "ALL",
					s_astrFixFloatTenor, "MAIN", 0.);

			for (org.drip.product.rates.FixFloatComponent ffc : aFixFloat) {
				try {
					strDump += org.drip.quant.common.FormatUtil.FormatDouble (ffc.measureValue (valParams,
						null, csqc, null, "SwapRate"), 1, 6, 1.) + ",";
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					continue;
				}
			}

			for (int i = 0; i < iNumFixFloatQuote; ++i)
				strDump += org.drip.quant.common.FormatUtil.FormatDouble (adblFixFloatQuote[i], 1, 6, 1.) +
					",";

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
