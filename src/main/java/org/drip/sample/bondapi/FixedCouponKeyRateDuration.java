
package org.drip.sample.bondapi;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.product.FixedBondAPI;

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
 * <i>FixedCouponKeyRateDuration</i> demonstrates the Invocation and Examination of the Key Rate Duration
 * Computation for the Specified Treasury Futures.
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

public class FixedCouponKeyRateDuration {

	private static void ComputeKeyRateDuration (
		final java.lang.String strIssuerName,
		final JulianDate dtBondEffective,
		final JulianDate dtBondMaturity,
		final double dblBondCoupon,
		final int iBondCouponFrequency,
		final java.lang.String strBondCouponDayCount,
		final java.lang.String strBondCouponCurrency,
		final int iSpotDate,
		final java.lang.String strGovvieCode,
		final int[] aiGovvieCurveTreasuryEffectiveDate,
		final int[] aiGovvieCurveTreasuryMaturityDate,
		final double[] adblGovvieCurveTreasuryCoupon,
		final double[] adblGovvieCurveTreasuryYield,
		final java.lang.String strGovvieCurveTreasuryMeasure,
		final double dblBondMarketCleanPrice,
		final boolean bHeader,
		final boolean bTrailer)
		throws Exception
	{
		Map<String, Double> mapKeyRateDuration = FixedBondAPI.KeyRateDuration (
			strIssuerName,
			dtBondEffective.julian(),
			dtBondMaturity.julian(),
			dblBondCoupon,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			dblBondMarketCleanPrice
		);

		if (bHeader) {
			System.out.println ("\n\t|------------------------------------------------------------------------------------------------------------------------------------------------------||");

			System.out.print ("\t|          ISSUE         |");

			Set<String> setstrKey = mapKeyRateDuration.keySet();

			for (String strKey : setstrKey)
				System.out.print (" " + strKey + " |");

			System.out.println ("|");

			System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------||");
		}

		System.out.print ("\t|  " + strIssuerName + FormatUtil.FormatDouble (dblBondCoupon, 1, 4, 100.) + " " + dtBondMaturity + " |");

		for (Map.Entry<String, Double> me : mapKeyRateDuration.entrySet())
			System.out.print ("     " + FormatUtil.FormatDouble (-1. * me.getValue(), 1, 5, 1.) + "    |");

		System.out.println ("|");

		if (bTrailer)
			System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] args)
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

		String strIssuerName = "AEG";
		int iBondCouponFrequency = 2;
		String strBondCouponDayCount = "30/360";
		String strBondCouponCurrency = "USD";
		String strGovvieCode = "UST";

		ComputeKeyRateDuration (
			strIssuerName,
			DateUtil.CreateFromYMD (2007, 12, 20),
			DateUtil.CreateFromYMD (2018,  1, 15),
			0.06000,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			1.08529,
			true,
			false
		);

		ComputeKeyRateDuration (
			strIssuerName,
			DateUtil.CreateFromYMD (1996,  7, 25),
			DateUtil.CreateFromYMD (2025,  7, 15),
			0.07750,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			1.27021,
			false,
			false
		);

		ComputeKeyRateDuration (
			strIssuerName,
			DateUtil.CreateFromYMD (1996, 10, 29),
			DateUtil.CreateFromYMD (2026, 10, 15),
			0.07625,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			1.27274,
			false,
			false
		);

		ComputeKeyRateDuration (
			strIssuerName,
			DateUtil.CreateFromYMD (2014, 12,  9),
			DateUtil.CreateFromYMD (2026, 12, 15),
			0.04125,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			1.01235,
			false,
			false
		);

		ComputeKeyRateDuration (
			strIssuerName,
			DateUtil.CreateFromYMD (1997,  4, 29),
			DateUtil.CreateFromYMD (2027,  4, 29),
			0.08000,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			1.31527,
			false,
			false
		);

		ComputeKeyRateDuration (
			strIssuerName,
			DateUtil.CreateFromYMD (2014,  9, 25),
			DateUtil.CreateFromYMD (2027, 10,  1),
			0.04250,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			1.02263,
			false,
			false
		);

		ComputeKeyRateDuration (
			strIssuerName,
			DateUtil.CreateFromYMD (2008,  5, 22),
			DateUtil.CreateFromYMD (2038,  5, 15),
			0.06400,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			1.27570,
			false,
			false
		);

		ComputeKeyRateDuration (
			strIssuerName,
			DateUtil.CreateFromYMD (2011,  7, 21),
			DateUtil.CreateFromYMD (2041,  7, 15),
			0.05600,
			iBondCouponFrequency,
			strBondCouponDayCount,
			strBondCouponCurrency,
			iSpotDate,
			strGovvieCode,
			aiGovvieCurveTreasuryEffectiveDate,
			aiGovvieCurveTreasuryMaturityDate,
			adblGovvieCurveTreasuryCoupon,
			adblGovvieCurveTreasuryYield,
			strGovvieCurveTreasuryMeasure,
			1.17460,
			false,
			true
		);

		EnvManager.TerminateEnv();
	}
}
