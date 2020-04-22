
package org.drip.sample.bondsink;

import org.drip.analytics.date.*;
import org.drip.numerical.common.Array2D;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.env.EnvManager;
import org.drip.service.scenario.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>Amaravati</i> generates the Full Suite of Replication Metrics for the Sinker Bond Amaravati.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bondsink/README.md">Sinkable Amortizing Capitalizing Bond Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Amaravati {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.MARCH,
			10
		);

		String[] astrDepositTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0130411 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01345,	// 98.655
			0.01470,	// 98.530
			0.01575,	// 98.425
			0.01660,	// 98.340
			0.01745,    // 98.255
			0.01845     // 98.155
		};

		String[] astrFixFloatTenor = new String[] {
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
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

		String[] astrGovvieTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"5Y",
			"7Y",
			"10Y",
			"20Y",
			"30Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.016410, //  2Y
			0.017863, //  3Y
			0.019030, //  4Y
			0.020035, //  5Y
			0.020902, //  6Y
			0.021660, //  7Y
			0.022307, //  8Y
			0.022879, //  9Y
			0.023363, // 10Y
			0.023820, // 11Y
			0.024172, // 12Y
			0.024934, // 15Y
			0.025581, // 20Y
			0.025906, // 25Y
			0.025973, // 30Y
			0.025838, // 40Y
			0.025560  // 50Y
		};

		double[] adblGovvieYield = new double[] {
			0.01219, //  1Y
			0.01391, //  2Y
			0.01590, //  3Y
			0.01937, //  5Y
			0.02200, //  7Y
			0.02378, // 10Y
			0.02677, // 20Y
			0.02927  // 30Y
		};

		String[] astrCreditTenor = new String[] {
			"06M",
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] adblCreditQuote = new double[] {
			 60.,	//  6M
			 68.,	//  1Y
			 88.,	//  2Y
			102.,	//  3Y
			121.,	//  4Y
			138.,	//  5Y
			168.,	//  7Y
			188.	// 10Y
		};

		double dblFX = 1.;
		int iSettleLag = 3;
		int iCouponFreq = 12;
		String strName = "Amaravati";
		double dblCleanPrice = 1.233069;
		double dblIssuePrice = 1.0;
		String strCurrency = "USD";
		double dblSpreadBump = 20.;
		double dblCouponRate = 0.07507; 
		double dblIssueAmount = 1.0e00;
		String strTreasuryCode = "UST";
		String strCouponDayCount = "30/360";
		double dblSpreadDurationMultiplier = 5.;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2009,
			DateUtil.DECEMBER,
			22
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2032,
			DateUtil.JANUARY,
			10
		);

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			strName,
			strCurrency,
			strName,
			dblCouponRate,
			iCouponFreq,
			strCouponDayCount,
			dtEffective,
			dtMaturity,
			Array2D.FromArray (
				new int[] {
					DateUtil.CreateFromYMD (2017,  4, 10).julian(),
					DateUtil.CreateFromYMD (2017,  5, 10).julian(),
					DateUtil.CreateFromYMD (2017,  6, 10).julian(),
					DateUtil.CreateFromYMD (2017,  7, 10).julian(),
					DateUtil.CreateFromYMD (2017,  8, 10).julian(),
					DateUtil.CreateFromYMD (2017,  9, 10).julian(),
					DateUtil.CreateFromYMD (2017, 10, 10).julian(),
					DateUtil.CreateFromYMD (2017, 11, 10).julian(),
					DateUtil.CreateFromYMD (2017, 12, 10).julian(),
					DateUtil.CreateFromYMD (2018,  1, 10).julian(),
					DateUtil.CreateFromYMD (2018,  2, 10).julian(),
					DateUtil.CreateFromYMD (2018,  3, 10).julian(),
					DateUtil.CreateFromYMD (2018,  4, 10).julian(),
					DateUtil.CreateFromYMD (2018,  5, 10).julian(),
					DateUtil.CreateFromYMD (2018,  6, 10).julian(),
					DateUtil.CreateFromYMD (2018,  7, 10).julian(),
					DateUtil.CreateFromYMD (2018,  8, 10).julian(),
					DateUtil.CreateFromYMD (2018,  9, 10).julian(),
					DateUtil.CreateFromYMD (2018, 10, 10).julian(),
					DateUtil.CreateFromYMD (2018, 11, 10).julian(),
					DateUtil.CreateFromYMD (2018, 12, 10).julian(),
					DateUtil.CreateFromYMD (2019,  1, 10).julian(),
					DateUtil.CreateFromYMD (2019,  2, 10).julian(),
					DateUtil.CreateFromYMD (2019,  3, 10).julian(),
					DateUtil.CreateFromYMD (2019,  4, 10).julian(),
					DateUtil.CreateFromYMD (2019,  5, 10).julian(),
					DateUtil.CreateFromYMD (2019,  6, 10).julian(),
					DateUtil.CreateFromYMD (2019,  7, 10).julian(),
					DateUtil.CreateFromYMD (2019,  8, 10).julian(),
					DateUtil.CreateFromYMD (2019,  9, 10).julian(),
					DateUtil.CreateFromYMD (2019, 10, 10).julian(),
					DateUtil.CreateFromYMD (2019, 11, 10).julian(),
					DateUtil.CreateFromYMD (2019, 12, 10).julian(),
					DateUtil.CreateFromYMD (2020,  1, 10).julian(),
					DateUtil.CreateFromYMD (2020,  2, 10).julian(),
					DateUtil.CreateFromYMD (2020,  3, 10).julian(),
					DateUtil.CreateFromYMD (2020,  4, 10).julian(),
					DateUtil.CreateFromYMD (2020,  5, 10).julian(),
					DateUtil.CreateFromYMD (2020,  6, 10).julian(),
					DateUtil.CreateFromYMD (2020,  7, 10).julian(),
					DateUtil.CreateFromYMD (2020,  8, 10).julian(),
					DateUtil.CreateFromYMD (2020,  9, 10).julian(),
					DateUtil.CreateFromYMD (2020, 10, 10).julian(),
					DateUtil.CreateFromYMD (2020, 11, 10).julian(),
					DateUtil.CreateFromYMD (2020, 12, 10).julian(),
					DateUtil.CreateFromYMD (2021,  1, 10).julian(),
					DateUtil.CreateFromYMD (2021,  2, 10).julian(),
					DateUtil.CreateFromYMD (2021,  3, 10).julian(),
					DateUtil.CreateFromYMD (2021,  4, 10).julian(),
					DateUtil.CreateFromYMD (2021,  5, 10).julian(),
					DateUtil.CreateFromYMD (2021,  6, 10).julian(),
					DateUtil.CreateFromYMD (2021,  7, 10).julian(),
					DateUtil.CreateFromYMD (2021,  8, 10).julian(),
					DateUtil.CreateFromYMD (2021,  9, 10).julian(),
					DateUtil.CreateFromYMD (2021, 10, 10).julian(),
					DateUtil.CreateFromYMD (2021, 11, 10).julian(),
					DateUtil.CreateFromYMD (2021, 12, 10).julian(),
					DateUtil.CreateFromYMD (2022,  1, 10).julian(),
					DateUtil.CreateFromYMD (2022,  2, 10).julian(),
					DateUtil.CreateFromYMD (2022,  3, 10).julian(),
					DateUtil.CreateFromYMD (2022,  4, 10).julian(),
					DateUtil.CreateFromYMD (2022,  5, 10).julian(),
					DateUtil.CreateFromYMD (2022,  6, 10).julian(),
					DateUtil.CreateFromYMD (2022,  7, 10).julian(),
					DateUtil.CreateFromYMD (2022,  8, 10).julian(),
					DateUtil.CreateFromYMD (2022,  9, 10).julian(),
					DateUtil.CreateFromYMD (2022, 10, 10).julian(),
					DateUtil.CreateFromYMD (2022, 11, 10).julian(),
					DateUtil.CreateFromYMD (2022, 12, 10).julian(),
					DateUtil.CreateFromYMD (2023,  1, 10).julian(),
					DateUtil.CreateFromYMD (2023,  2, 10).julian(),
					DateUtil.CreateFromYMD (2023,  3, 10).julian(),
					DateUtil.CreateFromYMD (2023,  4, 10).julian(),
					DateUtil.CreateFromYMD (2023,  5, 10).julian(),
					DateUtil.CreateFromYMD (2023,  6, 10).julian(),
					DateUtil.CreateFromYMD (2023,  7, 10).julian(),
					DateUtil.CreateFromYMD (2023,  8, 10).julian(),
					DateUtil.CreateFromYMD (2023,  9, 10).julian(),
					DateUtil.CreateFromYMD (2023, 10, 10).julian(),
					DateUtil.CreateFromYMD (2023, 11, 10).julian(),
					DateUtil.CreateFromYMD (2023, 12, 10).julian(),
					DateUtil.CreateFromYMD (2024,  1, 10).julian(),
					DateUtil.CreateFromYMD (2024,  2, 10).julian(),
					DateUtil.CreateFromYMD (2024,  3, 10).julian(),
					DateUtil.CreateFromYMD (2024,  4, 10).julian(),
					DateUtil.CreateFromYMD (2024,  5, 10).julian(),
					DateUtil.CreateFromYMD (2024,  6, 10).julian(),
					DateUtil.CreateFromYMD (2024,  7, 10).julian(),
					DateUtil.CreateFromYMD (2024,  8, 10).julian(),
					DateUtil.CreateFromYMD (2024,  9, 10).julian(),
					DateUtil.CreateFromYMD (2024, 10, 10).julian(),
					DateUtil.CreateFromYMD (2024, 11, 10).julian(),
					DateUtil.CreateFromYMD (2024, 12, 10).julian(),
					DateUtil.CreateFromYMD (2025,  1, 10).julian(),
					DateUtil.CreateFromYMD (2025,  2, 10).julian(),
					DateUtil.CreateFromYMD (2025,  3, 10).julian(),
					DateUtil.CreateFromYMD (2025,  4, 10).julian(),
					DateUtil.CreateFromYMD (2025,  5, 10).julian(),
					DateUtil.CreateFromYMD (2025,  6, 10).julian(),
					DateUtil.CreateFromYMD (2025,  7, 10).julian(),
					DateUtil.CreateFromYMD (2025,  8, 10).julian(),
					DateUtil.CreateFromYMD (2025,  9, 10).julian(),
					DateUtil.CreateFromYMD (2025, 10, 10).julian(),
					DateUtil.CreateFromYMD (2025, 11, 10).julian(),
					DateUtil.CreateFromYMD (2025, 12, 10).julian(),
					DateUtil.CreateFromYMD (2026,  1, 10).julian(),
					DateUtil.CreateFromYMD (2026,  2, 10).julian(),
					DateUtil.CreateFromYMD (2026,  3, 10).julian(),
					DateUtil.CreateFromYMD (2026,  4, 10).julian(),
					DateUtil.CreateFromYMD (2026,  5, 10).julian(),
					DateUtil.CreateFromYMD (2026,  6, 10).julian(),
					DateUtil.CreateFromYMD (2026,  7, 10).julian(),
					DateUtil.CreateFromYMD (2026,  8, 10).julian(),
					DateUtil.CreateFromYMD (2026,  9, 10).julian(),
					DateUtil.CreateFromYMD (2026, 10, 10).julian(),
					DateUtil.CreateFromYMD (2026, 11, 10).julian(),
					DateUtil.CreateFromYMD (2026, 12, 10).julian(),
					DateUtil.CreateFromYMD (2027,  1, 10).julian(),
					DateUtil.CreateFromYMD (2027,  2, 10).julian(),
					DateUtil.CreateFromYMD (2027,  3, 10).julian(),
					DateUtil.CreateFromYMD (2027,  4, 10).julian(),
					DateUtil.CreateFromYMD (2027,  5, 10).julian(),
					DateUtil.CreateFromYMD (2027,  6, 10).julian(),
					DateUtil.CreateFromYMD (2027,  7, 10).julian(),
					DateUtil.CreateFromYMD (2027,  8, 10).julian(),
					DateUtil.CreateFromYMD (2027,  9, 10).julian(),
					DateUtil.CreateFromYMD (2027, 10, 10).julian(),
					DateUtil.CreateFromYMD (2027, 11, 10).julian(),
					DateUtil.CreateFromYMD (2027, 12, 10).julian(),
					DateUtil.CreateFromYMD (2028,  1, 10).julian(),
					DateUtil.CreateFromYMD (2028,  2, 10).julian(),
					DateUtil.CreateFromYMD (2028,  3, 10).julian(),
					DateUtil.CreateFromYMD (2028,  4, 10).julian(),
					DateUtil.CreateFromYMD (2028,  5, 10).julian(),
					DateUtil.CreateFromYMD (2028,  6, 10).julian(),
					DateUtil.CreateFromYMD (2028,  7, 10).julian(),
					DateUtil.CreateFromYMD (2028,  8, 10).julian(),
					DateUtil.CreateFromYMD (2028,  9, 10).julian(),
					DateUtil.CreateFromYMD (2028, 10, 10).julian(),
					DateUtil.CreateFromYMD (2028, 11, 10).julian(),
					DateUtil.CreateFromYMD (2028, 12, 10).julian(),
					DateUtil.CreateFromYMD (2029,  1, 10).julian(),
					DateUtil.CreateFromYMD (2029,  2, 10).julian(),
					DateUtil.CreateFromYMD (2029,  3, 10).julian(),
					DateUtil.CreateFromYMD (2029,  4, 10).julian(),
					DateUtil.CreateFromYMD (2029,  5, 10).julian(),
					DateUtil.CreateFromYMD (2029,  6, 10).julian(),
					DateUtil.CreateFromYMD (2029,  7, 10).julian(),
					DateUtil.CreateFromYMD (2029,  8, 10).julian(),
					DateUtil.CreateFromYMD (2029,  9, 10).julian(),
					DateUtil.CreateFromYMD (2029, 10, 10).julian(),
					DateUtil.CreateFromYMD (2029, 11, 10).julian(),
					DateUtil.CreateFromYMD (2029, 12, 10).julian(),
					DateUtil.CreateFromYMD (2030,  1, 10).julian(),
					DateUtil.CreateFromYMD (2030,  2, 10).julian(),
					DateUtil.CreateFromYMD (2030,  3, 10).julian(),
					DateUtil.CreateFromYMD (2030,  4, 10).julian(),
					DateUtil.CreateFromYMD (2030,  5, 10).julian(),
					DateUtil.CreateFromYMD (2030,  6, 10).julian(),
					DateUtil.CreateFromYMD (2030,  7, 10).julian(),
					DateUtil.CreateFromYMD (2030,  8, 10).julian(),
					DateUtil.CreateFromYMD (2030,  9, 10).julian(),
					DateUtil.CreateFromYMD (2030, 10, 10).julian(),
					DateUtil.CreateFromYMD (2030, 11, 10).julian(),
					DateUtil.CreateFromYMD (2030, 12, 10).julian(),
					DateUtil.CreateFromYMD (2031,  1, 10).julian(),
					DateUtil.CreateFromYMD (2031,  2, 10).julian(),
					DateUtil.CreateFromYMD (2031,  3, 10).julian(),
					DateUtil.CreateFromYMD (2031,  4, 10).julian(),
					DateUtil.CreateFromYMD (2031,  5, 10).julian(),
					DateUtil.CreateFromYMD (2031,  6, 10).julian(),
					DateUtil.CreateFromYMD (2031,  7, 10).julian(),
					DateUtil.CreateFromYMD (2031,  8, 10).julian(),
					DateUtil.CreateFromYMD (2031,  9, 10).julian(),
					DateUtil.CreateFromYMD (2031, 10, 10).julian(),
					DateUtil.CreateFromYMD (2031, 11, 10).julian(),
					DateUtil.CreateFromYMD (2031, 12, 10).julian(),
				},
				new double[] {
					0.825788554,
					0.823162164,
					0.820519344,
					0.817859990,
					0.815184001,
					0.812491270,
					0.809781695,
					0.807055169,
					0.804311586,
					0.801550839,
					0.798772822,
					0.795977426,
					0.793164543,
					0.790334062,
					0.787485875,
					0.784619870,
					0.781735936,
					0.778833960,
					0.775913830,
					0.772975432,
					0.770018652,
					0.767043374,
					0.764049484,
					0.761036865,
					0.758005399,
					0.754954969,
					0.751885456,
					0.748796740,
					0.745688703,
					0.742561221,
					0.739414175,
					0.736247441,
					0.733060897,
					0.729854418,
					0.726627880,
					0.723381158,
					0.720114124,
					0.716826652,
					0.713518615,
					0.710189883,
					0.706840327,
					0.703469816,
					0.700078221,
					0.696665408,
					0.693231245,
					0.689775599,
					0.686298334,
					0.682799317,
					0.679278410,
					0.675735477,
					0.672170380,
					0.668582980,
					0.664973138,
					0.661340714,
					0.657685566,
					0.654007551,
					0.650306528,
					0.646582352,
					0.642834878,
					0.639063960,
					0.635269452,
					0.631451206,
					0.627609074,
					0.623742906,
					0.619852552,
					0.615937861,
					0.611998680,
					0.608034856,
					0.604046235,
					0.600032662,
					0.595993981,
					0.591930035,
					0.587840665,
					0.583725712,
					0.579585018,
					0.575418419,
					0.571225756,
					0.567006863,
					0.562761578,
					0.558489735,
					0.554191168,
					0.549865710,
					0.545513193,
					0.541133447,
					0.536726302,
					0.532291587,
					0.527829129,
					0.523338754,
					0.518820289,
					0.514273556,
					0.509698380,
					0.505094583,
					0.500461985,
					0.495800406,
					0.491109665,
					0.486389580,
					0.481639967,
					0.476860640,
					0.472051416,
					0.467212105,
					0.462342520,
					0.457442473,
					0.452511771,
					0.447550224,
					0.442557638,
					0.437533819,
					0.432478572,
					0.427391700,
					0.422273006,
					0.417122290,
					0.411939352,
					0.406723990,
					0.401476002,
					0.396195184,
					0.390881329,
					0.385534232,
					0.380153684,
					0.374739477,
					0.369291399,
					0.363809239,
					0.358292783,
					0.352741818,
					0.347156126,
					0.341535492,
					0.335879695,
					0.330188517,
					0.324461736,
					0.318699129,
					0.312900472,
					0.307065539,
					0.301194105,
					0.295285939,
					0.289340813,
					0.283358496,
					0.277338754,
					0.271281353,
					0.265186058,
					0.259052633,
					0.252880837,
					0.246670432,
					0.240421176,
					0.234132825,
					0.227805135,
					0.221437861,
					0.215030753,
					0.208583564,
					0.202096043,
					0.195567936,
					0.188998991,
					0.182388952,
					0.175737561,
					0.169044561,
					0.162309733,
					0.156072928,
					0.150025552,
					0.143940344,
					0.137817068,
					0.131655486,
					0.125455359,
					0.119216444,
					0.112938500,
					0.106621281,
					0.100264544,
					0.093868040,
					0.087431520,
					0.080954734,
					0.074437431,
					0.067879357,
					0.061280256,
					0.054639873,
					0.047957948,
					0.041234222,
					0.034468434,
					0.027660321,
					0.020809616,
					0.013916055,
					0.006979369,
				}
			),
			null
		);

		BondReplicator abr = BondReplicator.CorporateSenior (
			dblCleanPrice,
			dblIssuePrice,
			dblIssueAmount,
			dtSpot,
			astrDepositTenor,
			adblDepositQuote,
			adblFuturesQuote,
			astrFixFloatTenor,
			adblFixFloatQuote,
			dblSpreadBump,
			dblSpreadDurationMultiplier,
			strTreasuryCode,
			astrGovvieTenor,
			adblGovvieYield,
			astrCreditTenor,
			adblCreditQuote,
			dblFX,
			Double.NaN,
			iSettleLag,
			bond
		);

		BondReplicationRun abrr = abr.generateRun();

		System.out.println (abrr.display());

		EnvManager.TerminateEnv();
	}
}
