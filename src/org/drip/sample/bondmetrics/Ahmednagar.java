
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
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * Ahmednagar generates the Full Suite of Replication Metrics for Bond Ahmednagar.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Ahmednagar {

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
		int iCouponFreq = 2;
		String strName = "Ahmedabad";
		double dblCleanPrice = 1.;
		double dblIssuePrice = 1.;
		String strCurrency = "USD";
		double dblSpreadBump = 20.;
		double dblCouponRate = 0.0667; 
		double dblIssueAmount = 3.6e08;
		String strTreasuryCode = "UST";
		String strCouponDayCount = "30/360";
		double dblSpreadDurationMultiplier = 5.;

		org.drip.analytics.date.JulianDate[] adtPeriodEnd = new org.drip.analytics.date.JulianDate[] {
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
			DateUtil.CreateFromYMD (2036, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2036, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2036, DateUtil.DECEMBER , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.JANUARY  , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.FEBRUARY , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.MARCH    , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.APRIL    , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.MAY      , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.JUNE     , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.JULY     , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.AUGUST   , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.SEPTEMBER, 25),
			DateUtil.CreateFromYMD (2037, DateUtil.OCTOBER  , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.NOVEMBER , 25),
			DateUtil.CreateFromYMD (2037, DateUtil.DECEMBER , 25),
		};

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2010,
			3,
			18
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2020,
			4,
			7
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
			null,
			null
		);

		BondReplicator abr = BondReplicator.Standard (
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
	}
}
