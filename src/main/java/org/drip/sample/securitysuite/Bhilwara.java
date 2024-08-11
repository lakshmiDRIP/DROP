
package org.drip.sample.securitysuite;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.scenario.*;

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
 * <i>Bhilwara</i> generates the Full Suite of Replication Metrics for Bond Bhilwara.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/securitysuite/README.md">Custom Security Relative Value Demonstration</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Bhilwara {

	/**
	 * Entry Point
	 * 
	 * @param astArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.OCTOBER,
			5
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
		String strName = "Bhilwara";
		double dblCleanPrice = 0.95;
		double dblIssuePrice = 1.;
		String strCurrency = "USD";
		double dblSpreadBump = 20.;
		double dblCouponRate = 0.03; 
		String strTreasuryCode = "UST";
		String strCouponDayCount = "30/360";
		double dblSpreadDurationMultiplier = 5.;

		org.drip.analytics.date.JulianDate[] adtPeriodEnd = new org.drip.analytics.date.JulianDate[] {
			DateUtil.CreateFromYMD (2017, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2017, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2017, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2017, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2018, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2018, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2019, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2019, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2020, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2020, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2021, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2021, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2022, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2022, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2023, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2023, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2024, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2024, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2025, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2025, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2026, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2026, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2027, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2027, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2028, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2028, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2029, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2029, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2030, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2030, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2031, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2031, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2032, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2032, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2033, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2033, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2034, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2034, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2035, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2035, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.JULY     , 25),
		};

		double[] adblPrincipalPayDown = new double[] {
			299135.42,
			293800.82,
			286422.31,
			284811.34,
			280035.33,
			270326.05,
			269712.70,
			26144.16,
			256686.70,
			251454.94,
			248096.89,
			241975.08,
			241697.45,
			236817.23,
			233143.59,
			231739.14,
			227993.33,
			225400.55,
			224352.13,
			220835.71,
			215670.45,
			218278.54,
			213286.08,
			210099.94,
			210256.27,
			207185.92,
			204347.86,
			203127.82,
			200332.53,
			200631.41,
			199262.80,
			194534.04,
			203827.56,
			192652.24,
			189464.50,
			201054.55,
			188939.02,
			214387.68,
			187778.97,
			179343.85,
			189669.13,
			176363.67,
			176262.86,
			168645.72,
			168773.49,
			166805.04,
			164318.82,
			163155.09,
			158829.45,
			158789.33,
			153827.32,
			152677.48,
			150337.52,
			147447.05,
			146919.10,
			142388.33,
			140765.07,
			136984.46,
			137165.33,
			133432.66,
			130939.82,
			131192.67,
			128281.73,
			125889.89,
			126159.02,
			122344.40,
			124132.89,
			122375.65,
			120127.34,
			117469.89,
			117736.91,
			115135.15,
			113499.44,
			112833.61,
			110775.61,
			111019.29,
			109454.50,
			107044.35,
			107304.17,
			104936.76,
			102638.27,
			103708.20,
			101424.44,
			99234.51,
			99074.90,
			97679.15,
			97152.74,
			95778.72,
			94487.89,
			94296.53,
			93807.05,
			91373.77,
			90147.87,
			89973.49,
			88748.25,
			87150.09,
			86341.31,
			84805.19,
			85035.19,
			83563.64,
			82420.58,
			81926.86,
			81809.16,
			79025.70,
			79569.20,
			78101.88,
			76748.40,
			76940.91,
			75569.06,
			75122.18,
			74700.40,
			73086.81,
			73516.53,
			71935.57,
			72108.42,
			70278.22,
			70124.85,
			68881.26,
			68243.67,
			67843.46,
			66097.31,
			66779.19,
			65100.05,
			64735.20,
			64617.74,
			63200.82,
			62602.68,
			62505.70,
			61656.14,
			60573.31,
			60449.54,
			59391.79,
			58820.86,
			58500.20,
			57261.46,
			57610.98,
			56846.90,
			55588.17,
			55932.23,
			54960.87,
			54458.37,
			53721.51,
			53414.67,
			52280.72,
			52361.31,
			51657.14,
			50765.54,
			50672.24,
			50201.50,
			49525.75,
			49624.48,
			48581.40,
			47743.86,
			48215.40,
			47388.72,
			45990.91,
			45912.95,
			45477.28,
			44876.02,
			44461.64,
			43872.03,
			43957.13,
			43706.39,
			42798.64,
			42231.38,
			42307.78,
			41759.21,
			41203.77,
			40805.59,
			40262.53,
			40141.05,
			39463.11,
			39090.44,
			38863.93,
			38639.77,
			37840.46,
			37912.67,
			37273.86,
			36920.28,
			36702.69,
			35953.59,
			36263.93,
			35397.65,
			35187.49,
			35106.59,
			34518.72,
			34556.59,
			33857.24,
			33655.11,
			32982.42,
			33257.16,
			32581.87,
			32155.38,
			32186.68,
			31649.70,
			31229.88,
			31254.65,
			30376.70,
			30617.78,
			30216.11,
			29821.74,
			29328.58,
			29350.86,
			28867.66,
			28591.17,
			28412.39,
			27852.78,
			27964.98,
			27604.96,
			27156.64,
			27264.30,
			26832.91,
			26576.29,
			26205.21,
			26048.68,
			31263.96,
			37878.87,
			33808.14,
			24634.37,
			24583.50,
			25933.13,
			37250.03,
			91357.96,
			704382.83,
			558714.10,
			319083.90,
			13025.09,
		};

		double[] adblCouponAmount = new double[] {
			32157.95,
			30759.71,
			33409.09,
			28990.84,
			29616.95,
			31196.73,
			26960.85,
			28530.68,
			28181.61,
			28764.64,
			27496.02,
			29875.28,
			25930.15,
			26493.25,
			27910.58,
			25840.64,
			25516.76,
			26036.67,
			23219.86,
			25382.73,
			26673.77,
			22341.89,
			23628.28,
			24877.50,
			23018.77,
			22716.61,
			23165.53,
			22861.07,
			23286.07,
			20823.41,
			20547.31,
			23069.15,
			20001.92,
			20404.04,
			21473.63,
			19200.37,
			20234.01,
			19959.45,
			19016.53,
			20630.34,
			17269.74,
			18850.73,
			16797.78,
			21229.35,
			18981.96,
			20019.66,
			19749.87,
			18856.70,
			20464.21,
			17132.07,
			19319.99,
			18469.61,
			17050.15,
			17988.65,
			16036.03,
			17524.11,
			16740.37,
			18178.63,
			15227.97,
			16646.25,
			16967.16,
			15184.30,
			16028.43,
			16339.73,
			14624.86,
			16436.19,
			13774.37,
			14090.69,
			14396.94,
			15168.67,
			13579.28,
			14338.90,
			14165.25,
			13543.32,
			14718.16,
			12778.13,
			13060.28,
			13763.79,
			11899.67,
			13018.49,
			13693.96,
			11482.11,
			12157.04,
			12815.40,
			11874.38,
			11735.80,
			11986.35,
			11846.13,
			12086.10,
			10825.54,
			10331.36,
			11305.83,
			11536.23,
			10334.25,
			10568.21,
			10795.32,
			10672.36,
			10891.00,
			9758.13,
			10312.75,
			10195.59,
			9755.32,
			9002.06,
			10489.50,
			9114.38,
			9322.49,
			9832.73,
			8811.07,
			9313.06,
			9208.44,
			8811.12,
			9583.01,
			8039.30,
			8800.85,
			7859.90,
			8881.73,
			7958.45,
			8412.11,
			8317.85,
			7959.20,
			8656.73,
			7263.06,
			8207.09,
			7862.23,
			7272.47,
			7686.33,
			7600.56,
			7030.72,
			7191.07,
			7584.10,
			6796.19,
			7182.86,
			7102.26,
			6795.90,
			7390.98,
			6422.40,
			6568.20,
			6926.45,
			5992.50,
			6347.08,
			6274.90,
			6410.16,
			6132.82,
			6668.94,
			5794.21,
			5925.24,
			6247.89,
			5790.73,
			5724.18,
			5846.85,
			5220.32,
			5712.30,
			6010.65,
			5041.42,
			5338.20,
			5627.69,
			5215.56,
			5155.26,
			5265.38,
			5204.48,
			5310.08,
			4756.57,
			4538.66,
			4965.64,
			5065.86,
			4537.32,
			4638.22,
			4736.15,
			4680.23,
			4774.04,
			4275.36,
			4515.74,
			4462.18,
			4266.90,
			4075.33,
			4442.79,
			3978.24,
			4201.15,
			4150.56,
			3968.18,
			4311.98,
			3614.58,
			4080.14,
			3904.60,
			3608.02,
			3809.20,
			3398.26,
			3715.46,
			3551.14,
			3857.64,
			3232.72,
			3533.95,
			3602.30,
			3223.76,
			3402.41,
			3467.83,
			3103.07,
			3485.93,
			2920.52,
			2985.95,
			3049.23,
			3210.74,
			2872.50,
			3030.77,
			2991.59,
			2857.54,
			3102.25,
			2690.86,
			2746.92,
			2891.37,
			2496.66,
			2639.19,
			2603.95,
			2654.68,
			2534.63,
			2750.46,
			2377.38,
			2409.61,
			2522.89,
			2332.86,
			2300.57,
			2342.06,
			2143.67,
			2167.53,
			1250.69,
			424.12,
			17.11,
		};

		double dblIssueAmount = R1MatrixUtil.Sum (adblPrincipalPayDown);

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2017,
			DateUtil.AUGUST,
			25
		);

		BondComponent bond = BondBuilder.CreateBondFromCF (
			strName,
			dtEffective,
			strCurrency,
			strName,
			strCouponDayCount,
			dblIssueAmount,
			dblCouponRate,
			iCouponFreq,
			adtPeriodEnd,
			adblCouponAmount,
			adblPrincipalPayDown,
			true
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

		double dblBalance = 1.;

		for (CompositePeriod p : bond.couponPeriods()) {
			int iEndDate = p.endDate();

			int iStartDate = p.startDate();

			double dblPrincipalPayDown = bond.notional (iStartDate) - bond.notional (iEndDate);

			double dblInterest = dblCouponRate * p.couponDCF() * bond.notional (iStartDate) * bond.couponFactor (iEndDate);

			dblBalance -= dblPrincipalPayDown;

			System.out.println (
				"\t" + new JulianDate (iEndDate) + " => " +
				FormatUtil.FormatDouble (dblPrincipalPayDown, 8, 2, dblIssueAmount) + " | " +
				FormatUtil.FormatDouble (dblInterest, 6, 2, dblIssueAmount) + " | " +
				FormatUtil.FormatDouble (dblPrincipalPayDown + dblInterest, 8, 2, dblIssueAmount) + " | " +
				FormatUtil.FormatDouble (dblBalance, 8, 2, dblIssueAmount) + " ||"
			);
		}

		EnvManager.TerminateEnv();
	}
}
