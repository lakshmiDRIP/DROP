
package org.drip.sample.overnighthistorical;

import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.state.FundingCurveMetrics;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.state.OvernightCurveAPI;
import org.drip.service.template.LatentMarketStateBuilder;

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
 * <i>SEKSmooth1MForward</i> Generates the Historical SEK Smoothened Overnight Curve Native 1M Compounded
 * 	Forward Rate.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/overnighthistorical/README.md">G7 Smooth OIS 1M Forward</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SEKSmooth1MForward {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "SEK";
		String strClosesLocation = "C:\\DROP\\Daemons\\Transforms\\OvernightOISMarks\\" + strCurrency + "OISSmoothReconstitutor.csv";
		String[] astrForTenor = new String[] {
			"1M"
		};
		String[] astrInTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"4M",
			"5M",
			"6M",
			"9M",
			"1Y",
			"18M",
			"2Y",
			"3Y",
			"4Y",
			"5Y"
		};
		String[] astrOISMaturityTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"4M",
			"5M",
			"6M",
			"9M",
			"1Y",
			"18M",
			"2Y",
			"3Y",
			"4Y",
			"5Y"
		};

		CSVGrid csvGrid = CSVParser.StringGrid (
			strClosesLocation,
			true
		);

		JulianDate[] adtClose = csvGrid.dateArrayAtColumn (0);

		double[] adblOISQuote1W = csvGrid.doubleArrayAtColumn (1);

		double[] adblOISQuote2W = csvGrid.doubleArrayAtColumn (2);

		double[] adblOISQuote3W = csvGrid.doubleArrayAtColumn (3);

		double[] adblOISQuote1M = csvGrid.doubleArrayAtColumn (4);

		double[] adblOISQuote2M = csvGrid.doubleArrayAtColumn (5);

		double[] adblOISQuote3M = csvGrid.doubleArrayAtColumn (6);

		double[] adblOISQuote4M = csvGrid.doubleArrayAtColumn (7);

		double[] adblOISQuote5M = csvGrid.doubleArrayAtColumn (8);

		double[] adblOISQuote6M = csvGrid.doubleArrayAtColumn (9);

		double[] adblOISQuote9M = csvGrid.doubleArrayAtColumn (10);

		double[] adblOISQuote1Y = csvGrid.doubleArrayAtColumn (11);

		double[] adblOISQuote18M = csvGrid.doubleArrayAtColumn (12);

		double[] adblOISQuote2Y = csvGrid.doubleArrayAtColumn (13);

		double[] adblOISQuote3Y = csvGrid.doubleArrayAtColumn (14);

		double[] adblOISQuote4Y = csvGrid.doubleArrayAtColumn (15);

		double[] adblOISQuote5Y = csvGrid.doubleArrayAtColumn (16);

		int iNumClose = adtClose.length;
		JulianDate[] adtSpot = new JulianDate[iNumClose];
		double[][] aadblOISQuote = new double[iNumClose][16];

		for (int i = 0; i < iNumClose; ++i) {
			adtSpot[i] = adtClose[i];
			aadblOISQuote[i][0] = adblOISQuote1W[i];
			aadblOISQuote[i][1] = adblOISQuote2W[i];
			aadblOISQuote[i][2] = adblOISQuote3W[i];
			aadblOISQuote[i][3] = adblOISQuote1M[i];
			aadblOISQuote[i][4] = adblOISQuote2M[i];
			aadblOISQuote[i][5] = adblOISQuote3M[i];
			aadblOISQuote[i][6] = adblOISQuote4M[i];
			aadblOISQuote[i][7] = adblOISQuote5M[i];
			aadblOISQuote[i][8] = adblOISQuote6M[i];
			aadblOISQuote[i][9] = adblOISQuote9M[i];
			aadblOISQuote[i][10] = adblOISQuote1Y[i];
			aadblOISQuote[i][11] = adblOISQuote18M[i];
			aadblOISQuote[i][12] = adblOISQuote2Y[i];
			aadblOISQuote[i][13] = adblOISQuote3Y[i];
			aadblOISQuote[i][14] = adblOISQuote4Y[i];
			aadblOISQuote[i][15] = adblOISQuote5Y[i];
		}

		String strDump = "Date";

		for (String strInTenor : astrInTenor) {
			for (String strForTenor : astrForTenor)
				strDump += "," + strInTenor + strForTenor;
		}

		System.out.println (strDump);

		Map<JulianDate, FundingCurveMetrics> mapFCM = OvernightCurveAPI.HorizonMetrics (
			adtSpot,
			astrOISMaturityTenor,
			aadblOISQuote,
			astrInTenor,
			astrForTenor,
			strCurrency,
			LatentMarketStateBuilder.SMOOTH
		);

		for (int i = 0; i < iNumClose; ++i) {
			FundingCurveMetrics fcm = mapFCM.get (adtSpot[i]);

			strDump = adtSpot[i].toString();

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
		}

		EnvManager.TerminateEnv();
	}
}
