
package org.drip.sample.fundinghistorical;

import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.feed.loader.*;
import org.drip.historical.state.FundingCurveMetrics;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.state.FundingCurveAPI;
import org.drip.service.template.LatentMarketStateBuilder;

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
 * <i>JPYShapePreserving1YForward</i> Generates the Historical JPY Shape Preserving Funding Curve Native 1Y
 * Compounded Forward Rate.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fundinghistorical/README.md">Multi-Mode Funding Curve Historical Forwards</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class JPYShapePreserving1YForward {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "JPY";
		String strClosesLocation = "C:\\DROP\\Daemons\\Transforms\\FundingStateMarks\\" + strCurrency + "ShapePreservingReconstitutor.csv";
		String[] astrForTenor = new String[] {
			"1Y"
		};
		String[] astrInTenor = new String[] {
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
		String[] astrFixFloatMaturityTenor = new String[] {
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

		double[] adblFixFloatQuote1Y = csvGrid.doubleArrayAtColumn (1);

		double[] adblFixFloatQuote2Y = csvGrid.doubleArrayAtColumn (2);

		double[] adblFixFloatQuote3Y = csvGrid.doubleArrayAtColumn (3);

		double[] adblFixFloatQuote4Y = csvGrid.doubleArrayAtColumn (4);

		double[] adblFixFloatQuote5Y = csvGrid.doubleArrayAtColumn (5);

		double[] adblFixFloatQuote6Y = csvGrid.doubleArrayAtColumn (6);

		double[] adblFixFloatQuote7Y = csvGrid.doubleArrayAtColumn (7);

		double[] adblFixFloatQuote8Y = csvGrid.doubleArrayAtColumn (8);

		double[] adblFixFloatQuote9Y = csvGrid.doubleArrayAtColumn (9);

		double[] adblFixFloatQuote10Y = csvGrid.doubleArrayAtColumn (10);

		double[] adblFixFloatQuote11Y = csvGrid.doubleArrayAtColumn (11);

		double[] adblFixFloatQuote12Y = csvGrid.doubleArrayAtColumn (12);

		double[] adblFixFloatQuote15Y = csvGrid.doubleArrayAtColumn (13);

		double[] adblFixFloatQuote20Y = csvGrid.doubleArrayAtColumn (14);

		double[] adblFixFloatQuote25Y = csvGrid.doubleArrayAtColumn (15);

		double[] adblFixFloatQuote30Y = csvGrid.doubleArrayAtColumn (16);

		double[] adblFixFloatQuote40Y = csvGrid.doubleArrayAtColumn (17);

		double[] adblFixFloatQuote50Y = csvGrid.doubleArrayAtColumn (18);

		int iNumClose = adtClose.length;
		JulianDate[] adtSpot = new JulianDate[iNumClose];
		double[][] aadblFixFloatQuote = new double[iNumClose][18];

		for (int i = 0; i < iNumClose; ++i) {
			adtSpot[i] = adtClose[i];
			aadblFixFloatQuote[i][0] = adblFixFloatQuote1Y[i];
			aadblFixFloatQuote[i][1] = adblFixFloatQuote2Y[i];
			aadblFixFloatQuote[i][2] = adblFixFloatQuote3Y[i];
			aadblFixFloatQuote[i][3] = adblFixFloatQuote4Y[i];
			aadblFixFloatQuote[i][4] = adblFixFloatQuote5Y[i];
			aadblFixFloatQuote[i][5] = adblFixFloatQuote6Y[i];
			aadblFixFloatQuote[i][6] = adblFixFloatQuote7Y[i];
			aadblFixFloatQuote[i][7] = adblFixFloatQuote8Y[i];
			aadblFixFloatQuote[i][8] = adblFixFloatQuote9Y[i];
			aadblFixFloatQuote[i][9] = adblFixFloatQuote10Y[i];
			aadblFixFloatQuote[i][10] = adblFixFloatQuote11Y[i];
			aadblFixFloatQuote[i][11] = adblFixFloatQuote12Y[i];
			aadblFixFloatQuote[i][12] = adblFixFloatQuote15Y[i];
			aadblFixFloatQuote[i][13] = adblFixFloatQuote20Y[i];
			aadblFixFloatQuote[i][14] = adblFixFloatQuote25Y[i];
			aadblFixFloatQuote[i][15] = adblFixFloatQuote30Y[i];
			aadblFixFloatQuote[i][16] = adblFixFloatQuote40Y[i];
			aadblFixFloatQuote[i][17] = adblFixFloatQuote50Y[i];
		}

		String strDump = "Date";

		for (String strInTenor : astrInTenor) {
			for (String strForTenor : astrForTenor)
				strDump += "," + strInTenor + strForTenor;
		}

		System.out.println (strDump);

		Map<JulianDate, FundingCurveMetrics> mapFCM = FundingCurveAPI.HorizonMetrics (
			adtSpot,
			astrFixFloatMaturityTenor,
			aadblFixFloatQuote,
			astrInTenor,
			astrForTenor,
			strCurrency,
			LatentMarketStateBuilder.SHAPE_PRESERVING
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
