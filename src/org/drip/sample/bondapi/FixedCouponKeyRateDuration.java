
package org.drip.sample.bondapi;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.product.FixedBondAPI;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * FixedCouponKeyRateDuration demonstrates the Invocation and Examination of the Key Rate Duration
 *  Computation for the Specified Treasury Futures.
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
		EnvManager.InitEnv ("");

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
	}
}
