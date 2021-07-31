
package org.drip.sample.credithistorical;

import java.util.*;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.state.CreditCurveMetrics;
import org.drip.service.env.EnvManager;
import org.drip.service.state.CreditCurveAPI;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>CDXNAIGS245YMetrics</i> generates the Historical Credit Survival/Recovery Metrics for the Index
 * Contract CDX NA IG S24 5Y.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/credithistorical/README.md">CDX NA IG Historical Metrics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDXNAIGS245YMetrics {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iSeries = 24;
		String strClosesLocation = "C:\\DROP\\Daemons\\Transforms\\CreditCDXMarks\\CDXNAIGS" + iSeries + "5YReconstitutor.csv";
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
		String[] astrFundingFixingMaturityTenor = new String[] {
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
			strClosesLocation,
			true
		);

		JulianDate[] adtClose = csvGrid.dateArrayAtColumn (0);

		double[] adblFundingFixingQuote1Y = csvGrid.doubleArrayAtColumn (1);

		double[] adblFundingFixingQuote2Y = csvGrid.doubleArrayAtColumn (2);

		double[] adblFundingFixingQuote3Y = csvGrid.doubleArrayAtColumn (3);

		double[] adblFundingFixingQuote4Y = csvGrid.doubleArrayAtColumn (4);

		double[] adblFundingFixingQuote5Y = csvGrid.doubleArrayAtColumn (5);

		double[] adblFundingFixingQuote6Y = csvGrid.doubleArrayAtColumn (6);

		double[] adblFundingFixingQuote7Y = csvGrid.doubleArrayAtColumn (7);

		double[] adblFundingFixingQuote8Y = csvGrid.doubleArrayAtColumn (8);

		double[] adblFundingFixingQuote9Y = csvGrid.doubleArrayAtColumn (9);

		double[] adblFundingFixingQuote10Y = csvGrid.doubleArrayAtColumn (10);

		double[] adblFundingFixingQuote11Y = csvGrid.doubleArrayAtColumn (11);

		double[] adblFundingFixingQuote12Y = csvGrid.doubleArrayAtColumn (12);

		double[] adblFundingFixingQuote15Y = csvGrid.doubleArrayAtColumn (13);

		double[] adblFundingFixingQuote20Y = csvGrid.doubleArrayAtColumn (14);

		double[] adblFundingFixingQuote25Y = csvGrid.doubleArrayAtColumn (15);

		double[] adblFundingFixingQuote30Y = csvGrid.doubleArrayAtColumn (16);

		double[] adblFundingFixingQuote40Y = csvGrid.doubleArrayAtColumn (17);

		double[] adblFundingFixingQuote50Y = csvGrid.doubleArrayAtColumn (18);

		String[] astrFullCreditIndexName = csvGrid.stringArrayAtColumn (19);

		double[] adblCreditIndexQuotedSpread = csvGrid.doubleArrayAtColumn (20);

		int iNumClose = adtClose.length;
		JulianDate[] adtSpot = new JulianDate[iNumClose];
		double[][] aadblFundingFixingQuote = new double[iNumClose][18];

		for (int i = 0; i < iNumClose; ++i) {
			adtSpot[i] = adtClose[i];
			aadblFundingFixingQuote[i][0] = adblFundingFixingQuote1Y[i];
			aadblFundingFixingQuote[i][1] = adblFundingFixingQuote2Y[i];
			aadblFundingFixingQuote[i][2] = adblFundingFixingQuote3Y[i];
			aadblFundingFixingQuote[i][3] = adblFundingFixingQuote4Y[i];
			aadblFundingFixingQuote[i][4] = adblFundingFixingQuote5Y[i];
			aadblFundingFixingQuote[i][5] = adblFundingFixingQuote6Y[i];
			aadblFundingFixingQuote[i][6] = adblFundingFixingQuote7Y[i];
			aadblFundingFixingQuote[i][7] = adblFundingFixingQuote8Y[i];
			aadblFundingFixingQuote[i][8] = adblFundingFixingQuote9Y[i];
			aadblFundingFixingQuote[i][9] = adblFundingFixingQuote10Y[i];
			aadblFundingFixingQuote[i][10] = adblFundingFixingQuote11Y[i];
			aadblFundingFixingQuote[i][11] = adblFundingFixingQuote12Y[i];
			aadblFundingFixingQuote[i][12] = adblFundingFixingQuote15Y[i];
			aadblFundingFixingQuote[i][13] = adblFundingFixingQuote20Y[i];
			aadblFundingFixingQuote[i][14] = adblFundingFixingQuote25Y[i];
			aadblFundingFixingQuote[i][15] = adblFundingFixingQuote30Y[i];
			aadblFundingFixingQuote[i][16] = adblFundingFixingQuote40Y[i];
			aadblFundingFixingQuote[i][17] = adblFundingFixingQuote50Y[i];
			adblCreditIndexQuotedSpread[i] *= 10000.;
		}

		int i = 0;
		String strDump = "Date,QuotedSpread";

		for (String strForTenor : astrForTenor)
			strDump += ",SURVIVAL::" + strForTenor + ",RECOVERY::" + strForTenor;

		System.out.println (strDump);

		TreeMap<JulianDate, CreditCurveMetrics> mapCCM = CreditCurveAPI.HorizonMetrics (
			adtSpot,
			astrFundingFixingMaturityTenor,
			aadblFundingFixingQuote,
			astrFullCreditIndexName,
			adblCreditIndexQuotedSpread,
			astrForTenor
		);

		Set<JulianDate> setSpotDate = mapCCM.keySet();

		for (JulianDate dtSpot : setSpotDate) {
			strDump = dtSpot.toString() + "," + adblCreditIndexQuotedSpread[i++];

			CreditCurveMetrics ccmSpot = mapCCM.get (dtSpot);

			for (String strForTenor : astrForTenor) {
				JulianDate dtFor = dtSpot.addTenor (strForTenor);

				strDump += "," + ccmSpot.survivalProbability (dtFor) + "," + ccmSpot.recoveryRate (dtFor);
			}

			System.out.println (strDump);
		}

		EnvManager.TerminateEnv();
	}
}
