
package org.drip.sample.service;

import org.drip.analytics.date.*;
import org.drip.json.parser.Converter;
import org.drip.json.simple.JSONObject;
import org.drip.service.env.EnvManager;
import org.drip.service.json.KeyHoleSkeleton;

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
 * BondClientCurve demonstrates the Invocation and Examination of the JSON-based Bond Valuation Service for
 * 	generating the Curve Metrics.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondClientCurve {

	@SuppressWarnings ("unchecked") public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.MAY,
			9
		);

		String strCurrency = "USD";

		String[] astrDepositMaturityTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0103456 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01070,
			0.01235,
			0.01360
		};

		String[] astrFixFloatMaturityTenor = new String[] {
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

		double[] adblFixFloatQuote = new double[] {
			0.012484, //  1Y
			0.014987, //  2Y
			0.017036, //  3Y
			0.018624, //  4Y
			0.019868, //  5Y
			0.020921, //  6Y
			0.021788, //  7Y
			0.022530, //  8Y
			0.023145, //  9Y
			0.023685, // 10Y
			0.024153, // 11Y
			0.024562, // 12Y
			0.025389, // 15Y
			0.026118, // 20Y
			0.026368, // 25Y
			0.026432, // 30Y
			0.026339, // 40Y
			0.026122  // 50Y
		};

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2012,
			DateUtil.APRIL,
			12
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2027,
			DateUtil.APRIL,
			12
		);

		String strTreasuryCode = "UST";

		String[] astrTreasuryTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"5Y",
			"7Y",
			"10Y",
			"20Y",
			"30Y"
		};

		double[] adblTreasuryYield = new double[] {
			0.0083,	//  1Y
			0.0122, //  2Y
			0.0149, //  3Y
			0.0193, //  5Y
			0.0227, //  7Y
			0.0248, // 10Y
			0.0280, // 20Y
			0.0308  // 30Y
		};

		JSONObject jsonParameters = new JSONObject();

		jsonParameters.put ("SpotDate", dtSpot.toString());

		jsonParameters.put ("Currency", strCurrency);

		jsonParameters.put ("DepositTenor", Converter.Array (astrDepositMaturityTenor));

		jsonParameters.put ("DepositQuote", Converter.Array (adblDepositQuote));

		jsonParameters.put ("FuturesQuote", Converter.Array (adblFuturesQuote));

		jsonParameters.put ("FixFloatTenor", Converter.Array (astrFixFloatMaturityTenor));

		jsonParameters.put ("FixFloatQuote", Converter.Array (adblFixFloatQuote));

		jsonParameters.put ("TreasuryCode", strTreasuryCode);

		jsonParameters.put ("TreasuryTenor", Converter.Array (astrTreasuryTenor));

		jsonParameters.put ("TreasuryYield", Converter.Array (adblTreasuryYield));

		jsonParameters.put ("BondName", "  PDVSA  5.3750 12-APR-2027 ");

		jsonParameters.put ("BondCoupon", 0.053750);

		jsonParameters.put ("BondFrequency", 2);

		jsonParameters.put ("BondDayCount", "30/360");

		jsonParameters.put ("BondEffectiveDate", dtEffective.toString());

		jsonParameters.put ("BondMaturityDate", dtMaturity.toString());

		jsonParameters.put ("BondCleanPrice", 0.38239);

		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put ("API", "BOND::CURVEMETRICS");

		jsonRequest.put ("Parameters", jsonParameters);

		System.out.println ("\n\t|---------------- JSON REQUEST -----------------|\n");

		System.out.println (jsonRequest.toJSONString());

		System.out.println ("\n\t|---------------- JSON RESPONSE ----------------|\n");

		System.out.println (KeyHoleSkeleton.Thunker (jsonRequest.toJSONString()));
	}
}
