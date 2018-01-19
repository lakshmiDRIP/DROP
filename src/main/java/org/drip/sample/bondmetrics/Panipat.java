
package org.drip.sample.bondmetrics;

import org.drip.analytics.date.*;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.env.EnvManager;
import org.drip.service.scenario.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * Panipat generates the Full Suite of Replication Metrics for Bond Panipat.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Panipat {

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2018,
			DateUtil.JANUARY,
			5
		);

		String[] astrDepositTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0170393 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01810,	// 98.190
			0.01995,	// 98.005
			0.02115,	// 97.885
			0.02220,	// 97.780
			0.02285,    // 97.715
			0.02340     // 97.660
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

		double[] adblFixFloatQuote = new double[] {
			0.021489, //  2Y
			0.022452, //  3Y
			0.022964, //  4Y
			0.023324, //  5Y
			0.023628, //  6Y
			0.029315, //  7Y
			0.024195, //  8Y
			0.024448, //  9Y
			0.024701, // 10Y
			0.024946, // 11Y
			0.025155, // 12Y
			0.025600, // 15Y
			0.026034, // 20Y
			0.026150, // 25Y
			0.026107, // 30Y
			0.025936, // 40Y
			0.025636  // 50Y
		};

		String[] astrGovvieTenor = new String[] {
			"1M",
			"3M",
			"6M",
			"1Y",
			"2Y",
			"3Y",
			"5Y",
			"7Y",
			"10Y",
			"15Y",
			"20Y",
			"30Y"
		};

		double[] adblGovvieYield = new double[] {
			0.01270, //  1M
			0.01390, //  3M
			0.01580, //  6M
			0.01800, //  1Y
			0.01960, //  2Y
			0.02060, //  3Y
			0.02290, //  5Y
			0.02400, //  7Y
			0.02470, // 10Y
			0.02535, // 15Y
			0.02640, // 20Y
			0.02810  // 30Y
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
		int iCouponFreq = 4;
		String strName = "Panipat";
		double dblCleanPrice = 1.02059;
		double dblIssuePrice = 1.;
		String strCurrency = "USD";
		double dblSpreadBump = 20.;
		double dblIssueAmount = 2.60e7;
		double dblFloatSpread = 0.0350;
		String strTreasuryCode = "UST";
		double dblFullFirstCoupon = 0.0485917;
		String strCouponDayCount = "30/360";
		double dblSpreadDurationMultiplier = 5.;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2016,
			6,
			27
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2021,
			7,
			15
		);

		double dblResetRate = dblFullFirstCoupon - dblFloatSpread;
		String strRateIndex = strCurrency + "-" + (12 / iCouponFreq) + "M";

		BondComponent bond = BondBuilder.CreateSimpleFloater (
			strName,
			strCurrency,
			strRateIndex,
			strName,
			dblFloatSpread,
			iCouponFreq,
			strCouponDayCount,
			dtEffective,
			dtMaturity,
			null,
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
			dblResetRate,
			iSettleLag,
			bond
		);

		BondReplicationRun abrr = abr.generateRun();

		System.out.println (abrr.display());

		EnvManager.TerminateEnv();
	}
}
