
package org.drip.feed.transformer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>FundingFixFloatMarksReconstitutor</i> transforms the Funding Instrument Manifest Measures (e.g.,
 * Forward Rate for Deposits, Forward Rate for Futures, and Swap Rates for Fix/Float Swap) Feed Inputs into
 * Formats appropriate for Funding Curve Construction and Measure Generation.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Feed</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/transformer/README.md">Transformer</a></li>
 *  </ul>
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
