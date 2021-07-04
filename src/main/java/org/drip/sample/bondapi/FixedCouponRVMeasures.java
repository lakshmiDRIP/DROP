
package org.drip.sample.bondapi;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.analytics.output.BondRVMeasures;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.product.FixedBondAPI;

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
 * <i>FixedCouponRVMeasures</i> demonstrates the Invocation and Examination of the Relative Value Metrics for
 * the Fixed Coupon Bond.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bondapi/README.md">Fixed Coupon KRD + RV Measures</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedCouponRVMeasures {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

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

		int[] aiGovvieCurveTreasuryEffective = new int[] {
			iSpotDate,
			iSpotDate,
			iSpotDate,
			iSpotDate,
			iSpotDate,
			iSpotDate,
			iSpotDate
		};

		int[] aiGovvieCurveTreasuryMaturity = new int[] {
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

		String[] astrCreditCurveCDSTenor = new String[] {
			"06M",
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] adblCreditCurveCDSCoupon = new double[] {
			 60.,	//  6M
			 68.,	//  1Y
			 88.,	//  2Y
			102.,	//  3Y
			121.,	//  4Y
			138.,	//  5Y
			168.,	//  7Y
			188.	// 10Y
		};

		String strIssuerName = "AEG";
		int iBondCouponFrequency = 2;
		String strBondCouponDayCount = "30/360";
		String strBondCouponCurrency = "USD";
		String strGovvieCode = "UST";

		JulianDate[] adtBondEffective = new JulianDate[] {
			DateUtil.CreateFromYMD (2007, 12, 20),
			DateUtil.CreateFromYMD (1996,  7, 25),
			DateUtil.CreateFromYMD (1996, 10, 29),
			DateUtil.CreateFromYMD (2014, 12,  9),
			DateUtil.CreateFromYMD (1997,  4, 29),
			DateUtil.CreateFromYMD (2014,  9, 25),
			DateUtil.CreateFromYMD (2008,  5, 22),
			DateUtil.CreateFromYMD (2011,  7, 21)
		};

		JulianDate[] adtBondMaturity = new JulianDate[] {
			DateUtil.CreateFromYMD (2018,  1, 15),
			DateUtil.CreateFromYMD (2025,  7, 15),
			DateUtil.CreateFromYMD (2026, 10, 15),
			DateUtil.CreateFromYMD (2026, 12, 15),
			DateUtil.CreateFromYMD (2027,  4, 29),
			DateUtil.CreateFromYMD (2027, 10,  1),
			DateUtil.CreateFromYMD (2038,  5, 15),
			DateUtil.CreateFromYMD (2041,  7, 15)
		};

		double[] adblBondCoupon = new double[] {
			0.06000,
			0.07750,
			0.07625,
			0.04125,
			0.08000,
			0.04250,
			0.06400,
			0.05600
		};

		double[] adblCleanPrice = new double[] {
			1.08529,
			1.27021,
			1.27274,
			1.01235,
			1.31537,
			1.02263,
			1.27570,
			1.17460
		};

		BondRVMeasures[] aBMRV = new BondRVMeasures[adblCleanPrice.length];

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                                      ||");

		System.out.println ("\t|  Issuer Bond Relative Value Metrics                                                                  ||");

		System.out.println ("\t|  ------ ---- -------- ----- -------                                                                  ||");

		System.out.println ("\t|                                                                                                      ||");

		System.out.println ("\t|  L -> R                                                                                              ||");

		System.out.println ("\t|          Bond Issue Name                                                                             ||");

		System.out.println ("\t|          Asset Swap Spread                                                                           ||");

		System.out.println ("\t|          Bond Basis                                                                                  ||");

		System.out.println ("\t|          Credit Basis                                                                                ||");

		System.out.println ("\t|          Discount Margin                                                                             ||");

		System.out.println ("\t|          G Spread                                                                                    ||");

		System.out.println ("\t|          I Spread                                                                                    ||");

		System.out.println ("\t|          Option Adjusted Spread                                                                      ||");

		System.out.println ("\t|          Par Equivalent CDS Spread (PECS)                                                            ||");

		System.out.println ("\t|          Treasury Spread                                                                             ||");

		System.out.println ("\t|          Yield Spread                                                                                ||");

		System.out.println ("\t|          Z Spread                                                                                    ||");

		System.out.println ("\t|                                                                                                      ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblCleanPrice.length; ++i) {
			aBMRV[i] = FixedBondAPI.RelativeValueMetrics (
				strIssuerName,
				adtBondEffective[i].julian(),
				adtBondMaturity[i].julian(),
				adblBondCoupon[i],
				iBondCouponFrequency,
				strBondCouponDayCount,
				strBondCouponCurrency,
				iSpotDate,
				astrFundingCurveDepositTenor,
				adblFundingCurveDepositQuote,
				strFundingCurveDepositMeasure,
				adblFundingCurveFuturesQuote,
				strFundingCurveFuturesMeasure,
				astrFundingCurveFixFloatTenor,
				adblFundingCurveFixFloatQuote,
				strFundingFixFloatMeasure,
				strGovvieCode,
				aiGovvieCurveTreasuryEffective,
				aiGovvieCurveTreasuryMaturity,
				adblGovvieCurveTreasuryCoupon,
				adblGovvieCurveTreasuryYield,
				strGovvieCurveTreasuryMeasure,
				strIssuerName,
				astrCreditCurveCDSTenor,
				adblCreditCurveCDSCoupon,
				adblCreditCurveCDSCoupon,
				"FairPremium",
				adblCleanPrice[i]
			);

			Map<String, Double> mapRV = aBMRV[i].toMap ("");

			System.out.println (
				"\t| " + strIssuerName + " " + FormatUtil.FormatDouble (adblBondCoupon[i], 1, 4, 100.) + " " + adtBondMaturity[i] + " " +
				" | " + FormatUtil.FormatDouble (mapRV.get ("AssetSwapSpread"), 3, 0, 10000.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("BondBasis"), 3, 0, 10000.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("CreditBasis"), 3, 0, 1.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("DiscountMargin"), 3, 0, 1.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("GSpread"), 3, 0, 10000.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("ISpread"), 3, 0, 10000.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("OAS"), 3, 0, 10000.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("PECS"), 3, 0, 1.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("TSYSpread"), 3, 0, 10000.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("YieldSpread"), 3, 0, 10000.) +
				" | " + FormatUtil.FormatDouble (mapRV.get ("ZSpread"), 3, 0, 10000.) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
