
package org.drip.service.env;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>EnvManager</i> sets the environment/connection parameters, and populates the market parameters for the
 * given EOD.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/README.md">Library Module Loader Environment Manager</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EnvManager {
	private static boolean s_bInvocationCapture = false;

	/**
	 * Initialize the logger, the database connections, the day count parameters, and day count objects.
	 * 
	 * @param strConfig String representing the full path of the configuration file
	 * @param bInvocationCapture TRUE - Run the Invocation Capture
	 * 
	 * @return SQL Statement representing the initialized object.
	 */

	public static final java.sql.Statement InitEnv (
		final java.lang.String strConfig,
		final boolean bInvocationCapture)
	{
		if (s_bInvocationCapture = bInvocationCapture)
		{
			if (!org.drip.service.env.InvocationManager.Init())
			{
				System.out.println ("EnvManager::InitEnv => Cannot Initialize Invocation Manager!");

				return null;
			}
		}

		if (!org.drip.analytics.support.Logger.Init (strConfig)) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Logger Manager!");

			return null;
		}

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

		if (!org.drip.simm.common.ISDASettingsContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize ISDA SIMM 2.0 Specifications!");

			return null;
		}

		if (!org.drip.capital.env.CapitalEstimationContextManager.Init())
		{
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize Capital Estimation Context Manager!");

			return null;
		}

		if (!org.drip.capital.env.SystemicScenarioDesignContextManager.Init())
		{
			System.out.println ("EnvManager::InitEnv => Cannot Initialize GSST Design Context Manager!");

			return null;
		}

		if (!org.drip.capital.env.SystemicScenarioDefinitionContextManager.Init())
		{
			System.out.println
				("EnvManager::InitEnv => Cannot Initialize GSST Definition Context Manager!");

			return null;
		}

		if (s_bInvocationCapture)
		{
			if (!org.drip.service.env.InvocationManager.Setup())
			{
				System.out.println ("EnvManager::InitEnv => Cannot Setup Invocation Manager!");

				return null;
			}
		}

		return org.drip.param.config.ConfigLoader.OracleInit (strConfig);
	}

	/**
	 * Initialize the Environment Setup
	 * 
	 * @param strConfig String representing the full path of the configuration file
	 * 
	 * @return SQL Statement representing the initialized object.
	 */

	public static final java.sql.Statement InitEnv (
		final java.lang.String strConfig)
	{
		return InitEnv (
			strConfig,
			true
		);
	}

	/**
	 * Terminate the Environment Frame Context
	 * 
	 * @return The Environment Frame Context is Terminated
	 */

	public static final boolean TerminateEnv()
	{
		return s_bInvocationCapture ? org.drip.service.env.InvocationManager.Terminate() : true;
	}
}
