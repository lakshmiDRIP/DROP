
package org.drip.sample.service;

import org.drip.analytics.date.*;
import org.drip.json.simple.JSONObject;
import org.drip.service.env.EnvManager;
import org.drip.service.json.KeyHoleSkeleton;

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
 * TreasuryBondClient demonstrates the Invocation and Examination of the JSON-based Treasury Bond Service
 *  Client.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryBondClient {

	@SuppressWarnings ("unchecked") public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblCoupon = 0.10;
		double dblNotional = 100000000.;
		double dblCleanPrice = 1.35213;
		String strTreasuryCode = "MBONO";

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2011,
			DateUtil.NOVEMBER,
			20
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2036,
			DateUtil.NOVEMBER,
			20
		);

		JulianDate dtSettle = DateUtil.CreateFromYMD (
			2016,
			DateUtil.MARCH,
			30
		);

		JSONObject jsonParameters = new JSONObject();

		jsonParameters.put ("TreasuryCode", strTreasuryCode);

		jsonParameters.put ("EffectiveDate", dtEffective.toString());

		jsonParameters.put ("MaturityDate", dtMaturity.toString());

		jsonParameters.put ("Coupon", dblCoupon);

		jsonParameters.put ("Notional", dblNotional);

		jsonParameters.put ("SettleDate", dtSettle.toString());

		jsonParameters.put ("CleanPrice", dblCleanPrice);

		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put ("API", "TREASURYBOND::SECULARMETRICS");

		jsonRequest.put ("Parameters", jsonParameters);

		System.out.println ("\n\t|---------------- JSON REQUEST -----------------|\n");

		System.out.println (jsonRequest.toJSONString());

		System.out.println ("\n\t|---------------- JSON RESPONSE ----------------|\n");

		System.out.println (KeyHoleSkeleton.Thunker (jsonRequest.toJSONString()));
	}
}
