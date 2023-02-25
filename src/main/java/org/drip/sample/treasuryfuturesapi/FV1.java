
package org.drip.sample.treasuryfuturesapi;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.service.env.EnvManager;
import org.drip.service.product.TreasuryFuturesAPI;

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
 * <i>FV1</i> demonstrates the Invocation and Examination of the FV1 5Y UST Treasury Futures.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/treasuryfuturesapi/README.md">G20 Treasury Futures Valuation API</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FV1 {

	/**
	 * Entry Point
	 * 
	 * @param args Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int[] aiFuturesComponentTreasuryEffectiveDate = new int[] {
			DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY,  28).julian(), // 912828J5
			DateUtil.CreateFromYMD (2014, DateUtil.MARCH,     31).julian(), // 912828J8
			DateUtil.CreateFromYMD (2014, DateUtil.APRIL,     30).julian(), // 912828K5
			DateUtil.CreateFromYMD (2014, DateUtil.MAY,       31).julian(), // 912828XE
			DateUtil.CreateFromYMD (2014, DateUtil.JUNE,      30).julian(), // 912828XH
			DateUtil.CreateFromYMD (2014, DateUtil.JULY,      31).julian(), // 912828XM
			DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,    31).julian(), // 912828L3
			DateUtil.CreateFromYMD (2014, DateUtil.SEPTEMBER, 30).julian(), // 912828L6
			DateUtil.CreateFromYMD (2014, DateUtil.OCTOBER,   31).julian()  // 912828L9
		};

		int[] aiFuturesComponentTreasuryMaturityDate = new int[] {
			DateUtil.CreateFromYMD (2020, DateUtil.FEBRUARY,  28).julian(), // 912828J5
			DateUtil.CreateFromYMD (2020, DateUtil.MARCH,     31).julian(), // 912828J8
			DateUtil.CreateFromYMD (2020, DateUtil.APRIL,     30).julian(), // 912828K5
			DateUtil.CreateFromYMD (2020, DateUtil.MAY,       31).julian(), // 912828XE
			DateUtil.CreateFromYMD (2020, DateUtil.JUNE,      30).julian(), // 912828XH
			DateUtil.CreateFromYMD (2020, DateUtil.JULY,      31).julian(), // 912828XM
			DateUtil.CreateFromYMD (2020, DateUtil.AUGUST,    31).julian(), // 912828L3
			DateUtil.CreateFromYMD (2020, DateUtil.SEPTEMBER, 30).julian(), // 912828L6
			DateUtil.CreateFromYMD (2020, DateUtil.OCTOBER,   31).julian()  // 912828L9
		};

		double[] adblFuturesComponentTreasuryCoupon = new double[] {
			0.01375, // 912828J5
			0.01375, // 912828J8
			0.01375, // 912828K5
			0.01500, // 912828XE
			0.01625, // 912828XH
			0.01625, // 912828XM
			0.01375, // 912828L3
			0.01375, // 912828L6
			0.01375  // 912828L9
		};

		double[] adblFuturesComponentConversionFactor = new double[] {
			0.8317, // 912828J5
			0.8287, // 912828J8
			0.8258, // 912828K5
			0.8276, // 912828XE
			0.8297, // 912828XH
			0.8269, // 912828XM
			0.8141, // 912828L3
			0.8113, // 912828L6
			0.8084	// 912828L9
		};

		int iSpotDate = DateUtil.CreateFromYMD (
			2015,
			DateUtil.NOVEMBER,
			18
		).julian();

		String[] astrFundingCurveDepositTenor = new String[] {
			"2D",
			"1W",
			"1M",
			"2M",
			"3M"
		};

		double[] adblFundingCurveDepositQuote = new double[] {
			0.00195, // 2D
			0.00176, // 1W
			0.00301, // 1M
			0.00401, // 2M
			0.00492  // 3M
		};

		String strFundingCurveDepositMeasure = "ForwardRate";

		double[] adblFundingCurveFuturesQuote = new double[] {
			0.00609,
			0.00687
		};

		String strFundingCurveFuturesMeasure = "ForwardRate";

		String[] astrFundingCurveFixFloatTenor = new String[] {
			"01Y",
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

		double[] adblFundingCurveFixFloatQuote = new double[] {
			0.00762, //  1Y
			0.01055, //  2Y
			0.01300, //  3Y
			0.01495, //  4Y
			0.01651, //  5Y
			0.01787, //  6Y
			0.01904, //  7Y
			0.02005, //  8Y
			0.02090, //  9Y
			0.02166, // 10Y
			0.02231, // 11Y
			0.02289, // 12Y
			0.02414, // 15Y
			0.02570, // 20Y
			0.02594, // 25Y
			0.02627, // 30Y
			0.02648, // 40Y
			0.02632  // 50Y
		};

		String strFundingFixFloatMeasure = "SwapRate";

		int[] aiGovvieCurveTreasuryEffectiveDate = new int[] {
			iSpotDate,
			iSpotDate,
			iSpotDate,
			iSpotDate,
			iSpotDate,
			iSpotDate,
			iSpotDate
		};

		int[] aiGovvieCurveTreasuryMaturityDate = new int[] {
			new JulianDate (iSpotDate).addTenor ("1Y").julian(),
			new JulianDate (iSpotDate).addTenor ("2Y").julian(),
			new JulianDate (iSpotDate).addTenor ("3Y").julian(),
			new JulianDate (iSpotDate).addTenor ("5Y").julian(),
			new JulianDate (iSpotDate).addTenor ("7Y").julian(),
			new JulianDate (iSpotDate).addTenor ("10Y").julian(),
			new JulianDate (iSpotDate).addTenor ("30Y").julian()
		};

		double[] adblGovvieCurveTreasuryCoupon = new double[] {
			0.0100,
			0.0100,
			0.0125,
			0.0150,
			0.0200,
			0.0225,
			0.0300
		};

		double[] adblGovvieCurveTreasuryYield = new double[] {
			0.00692,
			0.00945,
			0.01257,
			0.01678,
			0.02025,
			0.02235,
			0.02972
		};

		String strGovvieCurveTreasuryMeasure = "Yield";

		double[] adblFuturesComponentTreasuryPrice = new double[] {
			 0.99909375, // 912828J5
			 0.99900000, // 912828J8
			 0.99890625, // 912828K5
			 0.99943750, // 912828XE
			 0.99984375, // 912828XH
			 0.99978125, // 912828XM
			 0.99862500, // 912828L3
			 0.99850000, // 912828L6
			 0.99853125  // 912828L9
		};

		Map<String, Double> mapTreasuryFutures = TreasuryFuturesAPI.ValuationMetrics (
			"FV1",
			aiFuturesComponentTreasuryEffectiveDate,
			aiFuturesComponentTreasuryMaturityDate,
			adblFuturesComponentTreasuryCoupon,
			adblFuturesComponentConversionFactor,
			iSpotDate,
			astrFundingCurveDepositTenor,
			adblFundingCurveDepositQuote,
			strFundingCurveDepositMeasure,
			adblFundingCurveFuturesQuote,
			strFundingCurveFuturesMeasure,
			astrFundingCurveFixFloatTenor,
			adblFundingCurveFixFloatQuote,
			strFundingFixFloatMeasure,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			adblFuturesComponentTreasuryPrice
		);

		for (Map.Entry<String, Double> me : mapTreasuryFutures.entrySet())
			System.out.println ("\t" + me.getKey() + " => " + me.getValue());

		EnvManager.TerminateEnv();
	}
}
