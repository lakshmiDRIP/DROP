
package org.drip.service.env;

import java.sql.Statement;

import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.Logger;
import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.env.SystemicScenarioDefinitionContextManager;
import org.drip.capital.env.SystemicScenarioDesignContextManager;
import org.drip.market.definition.FXSettingContainer;
import org.drip.market.definition.IBORIndexContainer;
import org.drip.market.definition.OvernightIndexContainer;
import org.drip.market.exchange.DeliverableSwapFuturesContainer;
import org.drip.market.exchange.FuturesOptionsContainer;
import org.drip.market.exchange.ShortTermFuturesContainer;
import org.drip.market.exchange.TreasuryFuturesContractContainer;
import org.drip.market.exchange.TreasuryFuturesConventionContainer;
import org.drip.market.exchange.TreasuryFuturesOptionContainer;
import org.drip.market.issue.TreasurySettingContainer;
import org.drip.market.otc.CreditIndexConventionContainer;
import org.drip.market.otc.CrossFloatConventionContainer;
import org.drip.market.otc.IBORFixedFloatContainer;
import org.drip.market.otc.IBORFloatFloatContainer;
import org.drip.market.otc.OvernightFixedFloatContainer;
import org.drip.market.otc.SwapOptionSettlementContainer;
import org.drip.param.config.ConfigLoader;
import org.drip.simm.common.ISDASettingsContainer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * 	given EOD. It provides the following Functions:
 * 
 * <ul>
 * 		<li>Initialize the logger, the database connections, the day count parameters, and day count objects</li>
 * 		<li>Initialize the Environment Setup</li>
 * 		<li>Terminate the Environment Frame Context</li>
 * </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/README.md">Library Module Loader Environment Manager</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EnvManager
{
	private static boolean _invocationCapture = false;

	/**
	 * Initialize the logger, the database connections, the day count parameters, and day count objects.
	 * 
	 * @param configPath String representing the full path of the configuration file
	 * @param invocationCapture TRUE - Run the Invocation Capture
	 * 
	 * @return SQL Statement representing the initialized object.
	 */

	public static final Statement InitEnv (
		final String configPath,
		final boolean invocationCapture)
	{
		if (_invocationCapture = invocationCapture) {
			if (!InvocationManager.Init()) {
				System.out.println ("EnvManager::InitEnv => Cannot Initialize Invocation Manager!");

				return null;
			}
		}

		if (!Logger.Init (configPath)) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Logger Manager!");

			return null;
		}

		if (!CacheManager.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Cache Manager!");

			return null;
		}

		if (!Convention.Init (configPath)) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Day Count Conventions!");

			return null;
		}

		if (!StandardCDXManager.InitializeSeries()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize standard CDX Indexes!");

			return null;
		}

		if (!OvernightIndexContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Overnight Indexes!");

			return null;
		}

		if (!IBORIndexContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize IBOR Indexes!");

			return null;
		}

		if (!ShortTermFuturesContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Short Term Futures!");

			return null;
		}

		if (!FuturesOptionsContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize Short Term Futures Options!");

			return null;
		}

		if (!IBORFixedFloatContainer.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize IBOR Fix-Float Convention Settings!"
			);

			return null;
		}

		if (!IBORFloatFloatContainer.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize IBOR Float-Float Convention Settings!"
			);

			return null;
		}

		if (!OvernightFixedFloatContainer.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize Overnight Fix-Float Convention Settings!"
			);

			return null;
		}

		if (!DeliverableSwapFuturesContainer.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize Deliverable Swap Futures Settings!"
			);

			return null;
		}

		if (!CrossFloatConventionContainer.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize Cross-Currency Float-Float Convention Settings!"
			);

			return null;
		}

		if (!SwapOptionSettlementContainer.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize the Swap Option Settlement Conventions!"
			);

			return null;
		}

		if (!CreditIndexConventionContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize the Credit Index Conventions!");

			return null;
		}

		if (!TreasuryFuturesConventionContainer.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize the Bond Futures Convention Conventions!"
			);

			return null;
		}

		if (!TreasuryFuturesOptionContainer.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize the Bond Futures Option Conventions!"
			);

			return null;
		}

		if (!FXSettingContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize the FX Conventions!");

			return null;
		}

		if (!TreasurySettingContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize the Treasury Settings!");

			return null;
		}

		if (!TreasuryFuturesContractContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize the Treasury Futures Contract!");

			return null;
		}

		if (!ISDASettingsContainer.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize ISDA SIMM 2.0 Specifications!");

			return null;
		}

		if (!CapitalEstimationContextManager.Init()) {
			System.out.println (
				"EnvManager::InitEnv => Cannot Initialize Capital Estimation Context Manager!"
			);

			return null;
		}

		if (!SystemicScenarioDesignContextManager.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize GSST Design Context Manager!");

			return null;
		}

		if (!SystemicScenarioDefinitionContextManager.Init()) {
			System.out.println ("EnvManager::InitEnv => Cannot Initialize GSST Definition Context Manager!");

			return null;
		}

		if (_invocationCapture) {
			if (!InvocationManager.Setup()) {
				System.out.println ("EnvManager::InitEnv => Cannot Setup Invocation Manager!");

				return null;
			}
		}

		return ConfigLoader.OracleInit (configPath);
	}

	/**
	 * Initialize the Environment Setup
	 * 
	 * @param configPath String representing the full path of the configuration file
	 * 
	 * @return SQL Statement representing the initialized object.
	 */

	public static final Statement InitEnv (
		final String configPath)
	{
		return InitEnv (configPath, true);
	}

	/**
	 * Terminate the Environment Frame Context
	 * 
	 * @return The Environment Frame Context is Terminated
	 */

	public static final boolean TerminateEnv()
	{
		return _invocationCapture ? InvocationManager.Terminate() : true;
	}
}
