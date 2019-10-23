
package org.drip.feed.transformer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>OvernightIndexMarksReconstitutor</i> transforms the Overnight Instrument Manifest Measures (e.g.,
 * Deposits and OIS) Feed Inputs into Formats appropriate for Overnight Curve Construction and Measure
 * Generation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Load, Transform, and compute Target Metrics across Feeds</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/README.md">Market Data Reconstitutive Feed Transformer</a></li>
 *  </ul>
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
					strDump += org.drip.numerical.common.FormatUtil.FormatDouble (ffc.measureValue (valParams,
						null, csqc, null, "SwapRate"), 1, 6, 1.) + ",";
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					continue;
				}
			}

			for (int i = 0; i < iNumOISQuote; ++i)
				strDump += org.drip.numerical.common.FormatUtil.FormatDouble (adblOISQuote[i], 1, 6, 1.) + ",";

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
