
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * EnvManager sets the environment/connection parameters, and populates the market parameters for the given
 *  EOD.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EnvManager {

	/**
	 * Initialize the logger, the database connections, the day count parameters, and day count objects.
	 * 
	 * @param strConfig String representing the full path of the configuration file
	 * 
	 * @return SQL Statement representing the initialized object.
	 */

	public static final java.sql.Statement InitEnv (
		final java.lang.String strConfig)
	{
		org.drip.analytics.support.Logger.Init (strConfig);

		if (!org.drip.service.env.CacheManager.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Cache Manager!");

			return null;
		}

		if (!org.drip.analytics.daycount.Convention.Init (strConfig)) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Day Count Conventions!");

			return null;
		}

		if (!org.drip.service.env.StandardCDXManager.InitStandardCDXSeries()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize standard CDX Indexes!");

			return null;
		}

		if (!org.drip.market.definition.OvernightIndexContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Overnight Indexes!");

			return null;
		}

		if (!org.drip.market.definition.IBORIndexContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize IBOR Indexes!");

			return null;
		}

		if (!org.drip.market.exchange.ShortTermFuturesContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Short Term Futures!");

			return null;
		}

		if (!org.drip.market.exchange.FuturesOptionsContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Short Term Futures Options!");

			return null;
		}

		if (!org.drip.market.otc.IBORFixedFloatContainer.Init()) {
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize IBOR Fix-Float Convention Settings!");

			return null;
		}

		if (!org.drip.market.otc.IBORFloatFloatContainer.Init()) {
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize IBOR Float-Float Convention Settings!");

			return null;
		}

		if (!org.drip.market.otc.OvernightFixedFloatContainer.Init()) {
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize Overnight Fix-Float Convention Settings!");

			return null;
		}

		if (!org.drip.market.exchange.DeliverableSwapFuturesContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Deliverable Swap Futures Settings!");

			return null;
		}

		if (!org.drip.market.otc.CrossFloatConventionContainer.Init()) {
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize Cross-Currency Float-Float Convention Settings!");

			return null;
		}

		if (!org.drip.market.otc.SwapOptionSettlementContainer.Init()) {
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize the Swap Option Settlement Conventions!");

			return null;
		}

		if (!org.drip.market.otc.CreditIndexConventionContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize the Credit Index Conventions!");

			return null;
		}

		if (!org.drip.market.exchange.TreasuryFuturesConventionContainer.Init()) {
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize the Bond Futures Convention Conventions!");

			return null;
		}

		if (!org.drip.market.exchange.TreasuryFuturesOptionContainer.Init()) {
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize the Bond Futures Option Conventions!");

			return null;
		}

		if (!org.drip.market.definition.FXSettingContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize the FX Conventions!");

			return null;
		}

		if (!org.drip.market.issue.TreasurySettingContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize the Treasury Settings!");

			return null;
		}

		if (!org.drip.market.exchange.TreasuryFuturesContractContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize the Treasury Futures Contract!");

			return null;
		}

		return org.drip.param.config.ConfigLoader.OracleInit (strConfig);
	}
}
