
package org.drip.service.json;

import org.drip.service.assetallocation.BlackLittermanProcessor;
import org.drip.service.assetallocation.PortfolioConstructionProcessor;
import org.drip.service.env.EnvManager;
import org.drip.service.representation.JSONObject;
import org.drip.service.representation.JSONValue;

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
 * <i>KeyHoleSkeleton</i> forwards the JSON Request to the Appropriate Processor and retrieves the Response
 * 	JSON. It provides the following Functions:
 * 
 * <ul>
 * 		<li>JSON Based in/out Generic Thunker</li>
 * 		<li>JSON String Based in/out Generic Thunker</li>
 * </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/json/README.md">JSON Based Valuation Request Service</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class KeyHoleSkeleton
{

	/**
	 * JSON Based in/out Generic Thunker
	 * 
	 * @param jsonInput JSON Request
	 * 
	 * @return JSON Response
	 */

	public static final JSONObject Thunker (
		final JSONObject jsonInput)
	{
    	if (null == jsonInput || !jsonInput.containsKey ("API") || !jsonInput.containsKey ("Parameters")) {
			return null;
    	}

		Object apiNameObject = jsonInput.get ("API");

		if (!(apiNameObject instanceof String)) {
			return null;
		}

		String apiName = (java.lang.String) apiNameObject;

		EnvManager.InitEnv ("");

		Object parameterObject = jsonInput.get ("Parameters");

		if (null == parameterObject) {
			return null;
		}

		JSONObject parameterJSON = (JSONObject) JSONValue.parse (parameterObject.toString());

		if ("DATE::ISHOLIDAY".equalsIgnoreCase (apiName)) {
			return DateProcessor.IsHoliday (parameterJSON);
		}

		if ("DATE::ADDDAYS".equalsIgnoreCase (apiName)) {
			return DateProcessor.AddDays (parameterJSON);
		}

		if ("DATE::ADJUSTBUSINESSDAYS".equalsIgnoreCase (apiName)) {
			return DateProcessor.AdjustBusinessDays (parameterJSON);
		}

		if ("FUNDINGSTATE".equalsIgnoreCase (apiName)) {
			return LatentStateProcessor.FundingCurveThunker (parameterJSON);
		}

		if ("CREDITSTATE".equalsIgnoreCase (apiName)) {
			return LatentStateProcessor.CreditCurveThunker (parameterJSON);
		}

		if ("DEPOSIT::SECULARMETRICS".equalsIgnoreCase (apiName)) {
			return null;
		}

		if ("DEPOSIT::CURVEMETRICS".equalsIgnoreCase (apiName)) {
			return DepositProcessor.CurveMetrics (parameterJSON);
		}

		if ("BOND::SECULARMETRICS".equalsIgnoreCase (apiName)) {
			return BondProcessor.SecularMetrics (parameterJSON);
		}

		if ("BOND::CURVEMETRICS".equalsIgnoreCase (apiName)) {
			return BondProcessor.CurveMetrics (parameterJSON);
		}

		if ("BOND::CASHFLOWS".equalsIgnoreCase (apiName)) {
			return BondProcessor.CashFlows (parameterJSON);
		}

		if ("FORWARDRATEFUTURES::SECULARMETRICS".equalsIgnoreCase (apiName)) {
			return null;
		}

		if ("FORWARDRATEFUTURES::CURVEMETRICS".equalsIgnoreCase (apiName)) {
			return ForwardRateFuturesProcessor.CurveMetrics (parameterJSON);
		}

		if ("FIXFLOAT::SECULARMETRICS".equalsIgnoreCase (apiName)) {
			return FixFloatProcessor.CurveMetrics (parameterJSON);
		}

		if ("CREDITDEFAULTSWAP::SECULARMETRICS".equalsIgnoreCase (apiName)) {
			return null;
		}

		if ("CREDITDEFAULTSWAP::CURVEMETRICS".equalsIgnoreCase (apiName)) {
			return CreditDefaultSwapProcessor.CurveMetrics (parameterJSON);
		}

		if ("FIXEDASSETBACKED::SECULARMETRICS".equalsIgnoreCase (apiName)) {
			return FixedAssetBackedProcessor.SecularMetrics (parameterJSON);
		}

		if ("PORTFOLIOALLOCATION::BUDGETCONSTRAINEDMEANVARIANCE".equalsIgnoreCase (apiName)) {
			return PortfolioConstructionProcessor.BudgetConstrainedAllocator (parameterJSON);
		}

		if ("PORTFOLIOALLOCATION::RETURNSCONSTRAINEDMEANVARIANCE".equalsIgnoreCase (apiName)) {
			return PortfolioConstructionProcessor.ReturnsConstrainedAllocator (parameterJSON);
		}

		if ("PREPAYASSETBACKED::SECULARMETRICS".equalsIgnoreCase (apiName)) {
			return PrepayAssetBackedProcessor.SecularMetrics (parameterJSON);
		}

		if ("TREASURYBOND::SECULARMETRICS".equalsIgnoreCase (apiName)) {
			return TreasuryBondProcessor.SecularMetrics (parameterJSON);
		}

		if ("BLACKLITTERMAN::BAYESIANMETRICS".equalsIgnoreCase (apiName)) {
			return BlackLittermanProcessor.Estimate (parameterJSON);
		}

		return null;
	}

	/**
	 * JSON String Based in/out Generic Thunker
	 * 
	 * @param requestJSON JSON String Request
	 * 
	 * @return JSON String Response
	 */

	public static final String Thunker (
		final String requestJSON)
	{
    	if (null == requestJSON || requestJSON.isEmpty()) {
    		return null;
    	}

		Object inputObject = JSONValue.parse (requestJSON);

		if (null == inputObject || !(inputObject instanceof JSONObject)) {
			return null;
		}

		JSONObject jsonResponse = Thunker ((JSONObject) inputObject);

		return null == jsonResponse ? null : jsonResponse.toJSONString();
	}
}
