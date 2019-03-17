
package org.drip.sample.fundingfeed;

import java.io.BufferedWriter;
import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.state.FundingCurveMetrics;
import org.drip.numerical.common.FormatUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.rates.*;
import org.drip.service.env.EnvManager;
import org.drip.service.state.FundingCurveAPI;
import org.drip.service.template.*;
import org.drip.state.discount.MergedDiscountForwardCurve;

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
 * <i>UnifiedShapePreserving1YStart</i> demonstrates the unified re-constitution and Metrics Generation.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fundingfeed/README.md">Funding Marks Feed ETL</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class UnifiedShapePreserving1YStart {

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "USD";
		String strFundingMarksLocation = "C:\\DROP\\Daemons\\Feeds\\FundingMarks\\" + strCurrency + "Formatted.csv";
		String strFundingMetricsLocation = "C:\\DROP\\Daemons\\Metrics\\FundingCurve\\Unified\\" + strCurrency + ".csv";

		String[] astrPreFixFloatTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"4Y",
			"5Y",
			"6Y",
			"7Y",
			"8Y",
			"9Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"40Y",
			"50Y"
		};
		String[] astrInTenor = new String[] {
			"1Y"
		};
		String[] astrForTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"4Y",
			"5Y",
			"6Y",
			"7Y",
			"8Y",
			"9Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
		};
		String[] astrPostFixFloatMaturityTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"4Y",
			"5Y",
			"6Y",
			"7Y",
			"8Y",
			"9Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"40Y",
			"50Y"
		};

		CSVGrid csvGrid = CSVParser.StringGrid (
			strFundingMarksLocation,
			false
		);

		Map<JulianDate, InstrumentSetTenorQuote> mapISTQ = csvGrid.groupedOrderedDouble (0.01);

		int iNumClose = mapISTQ.size();

		int iCloseDate = 0;
		String strDump = "Date";
		JulianDate[] adtClose = new JulianDate[iNumClose];
		double[][] aadblFixFloatQuote = new double[iNumClose][18];

		for (String strInTenor : astrInTenor) {
			for (String strForTenor : astrForTenor)
				strDump += "," + strInTenor + strForTenor;
		}

		System.out.println (strDump);

		BufferedWriter bwMetrics = new BufferedWriter (new java.io.FileWriter (strFundingMetricsLocation));

		bwMetrics.write (strDump + "\n");

		for (Map.Entry<JulianDate, InstrumentSetTenorQuote> meISTQ : mapISTQ.entrySet()) {
			if (null == meISTQ) continue;

			JulianDate dtSpot = meISTQ.getKey();

			InstrumentSetTenorQuote istq = meISTQ.getValue();

			if (null == dtSpot || null == istq) continue;

			double[] adblDepositQuote = istq.instrumentQuote ("DEPOSIT");

			String[] astrDepositMaturityTenor = istq.instrumentTenor ("DEPOSIT");

			double[] adblFixFloatQuote = istq.instrumentQuote ("FIXFLOAT");

			String[] astrFixFloatMaturityTenor = istq.instrumentTenor ("FIXFLOAT");

			int iNumDepositQuote = null == adblDepositQuote ? 0 : adblDepositQuote.length;
			int iNumFixFloatQuote = null == adblFixFloatQuote ? 0 : adblFixFloatQuote.length;
			int iNumDepositTenor = null == astrDepositMaturityTenor ? 0 : astrDepositMaturityTenor.length;
			int iNumFixFloatTenor = null == astrFixFloatMaturityTenor ? 0 : astrFixFloatMaturityTenor.length;

			if (0 == iNumFixFloatQuote || iNumDepositQuote != iNumDepositTenor || iNumFixFloatQuote !=
				iNumFixFloatTenor)
				continue;

			MergedDiscountForwardCurve dcFunding = LatentMarketStateBuilder.FundingCurve (
				dtSpot,
				strCurrency,
				astrDepositMaturityTenor,
				adblDepositQuote,
				"ForwardRate",
				null,
				"ForwardRate",
				astrFixFloatMaturityTenor,
				adblFixFloatQuote,
				"SwapRate",
				LatentMarketStateBuilder.SHAPE_PRESERVING
			);

			CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

			if (!csqc.setFundingState (dcFunding)) continue;

			ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

			FixFloatComponent[] aFFC = OTCInstrumentBuilder.FixFloatStandard (
				dtSpot,
				strCurrency,
				"ALL",
				astrPreFixFloatTenor,
				"MAIN",
				0.
			);

			for (int j = 0; j < aFFC.length; ++j)
				aadblFixFloatQuote[iCloseDate][j] = aFFC[j].measureValue (
					valParams,
					null,
					csqc,
					null,
					"SwapRate"
				);

			adtClose[iCloseDate] = dtSpot;

			if (++iCloseDate >= iNumClose) break;
		}

		Map<JulianDate, FundingCurveMetrics> mapFCM = FundingCurveAPI.HorizonMetrics (
			adtClose,
			astrPostFixFloatMaturityTenor,
			aadblFixFloatQuote,
			astrInTenor,
			astrForTenor,
			strCurrency,
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);

		for (int i = 0; i < iNumClose; ++i) {
			FundingCurveMetrics fcm = mapFCM.get (adtClose[i]);

			strDump = adtClose[i].toString();

			for (String strInTenor : astrInTenor) {
				for (String strForTenor : astrForTenor)
					strDump += "," + FormatUtil.FormatDouble (
						fcm.nativeForwardRate (
							strInTenor,
							strForTenor
						), 1, 5, 100.
					);
			}

			System.out.println (strDump);

			bwMetrics.write (strDump + "\n");
		}

		bwMetrics.close();

		EnvManager.TerminateEnv();
	}
}
