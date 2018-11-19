
package org.drip.service.json;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>KeyHoleSkeleton</i> forwards the JSON Request to the Appropriate Processor and retrieves the Response
 * JSON.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/json">JSON</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class KeyHoleSkeleton {

	/**
	 * JSON Based in/out Generic Thunker
	 * 
	 * @param jsonInput JSON Request
	 * 
	 * @return JSON Response
	 */

	public static final org.drip.json.simple.JSONObject Thunker (
		final org.drip.json.simple.JSONObject jsonInput)
	{
    	if (null == jsonInput || !jsonInput.containsKey ("API") || !jsonInput.containsKey ("Parameters"))
			return null;

		java.lang.Object objAPIName = jsonInput.get ("API");

		if (!(objAPIName instanceof java.lang.String)) return null;

		java.lang.String strAPIName = (java.lang.String) objAPIName;

		org.drip.service.env.EnvManager.InitEnv ("");

		java.lang.Object objParameter = jsonInput.get ("Parameters");

		if (null == objParameter) return null;

		org.drip.json.simple.JSONObject jsonParameter = (org.drip.json.simple.JSONObject)
    		org.drip.json.simple.JSONValue.parse (objParameter.toString());

		if ("DATE::ISHOLIDAY".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.DateProcessor.IsHoliday (jsonParameter);

		if ("DATE::ADDDAYS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.DateProcessor.AddDays (jsonParameter);

		if ("DATE::ADJUSTBUSINESSDAYS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.DateProcessor.AdjustBusinessDays (jsonParameter);

		if ("FUNDINGSTATE".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.LatentStateProcessor.FundingCurveThunker (jsonParameter);

		if ("CREDITSTATE".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.LatentStateProcessor.CreditCurveThunker (jsonParameter);

		if ("DEPOSIT::SECULARMETRICS".equalsIgnoreCase (strAPIName)) return null;

		if ("DEPOSIT::CURVEMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.DepositProcessor.CurveMetrics (jsonParameter);

		if ("BOND::SECULARMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.BondProcessor.SecularMetrics (jsonParameter);

		if ("BOND::CURVEMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.BondProcessor.CurveMetrics (jsonParameter);

		if ("BOND::CASHFLOWS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.BondProcessor.CashFlows (jsonParameter);

		if ("FORWARDRATEFUTURES::SECULARMETRICS".equalsIgnoreCase (strAPIName)) return null;

		if ("FORWARDRATEFUTURES::CURVEMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.ForwardRateFuturesProcessor.CurveMetrics (jsonParameter);

		if ("FIXFLOAT::SECULARMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.FixFloatProcessor.CurveMetrics (jsonParameter);

		if ("CREDITDEFAULTSWAP::SECULARMETRICS".equalsIgnoreCase (strAPIName)) return null;

		if ("CREDITDEFAULTSWAP::CURVEMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.CreditDefaultSwapProcessor.CurveMetrics (jsonParameter);

		if ("FIXEDASSETBACKED::SECULARMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.FixedAssetBackedProcessor.SecularMetrics (jsonParameter);

		if ("PORTFOLIOALLOCATION::BUDGETCONSTRAINEDMEANVARIANCE".equalsIgnoreCase (strAPIName))
			return org.drip.json.assetallocation.PortfolioConstructionProcessor.BudgetConstrainedAllocator
				(jsonParameter);

		if ("PORTFOLIOALLOCATION::RETURNSCONSTRAINEDMEANVARIANCE".equalsIgnoreCase (strAPIName))
			return org.drip.json.assetallocation.PortfolioConstructionProcessor.ReturnsConstrainedAllocator
				(jsonParameter);

		if ("PREPAYASSETBACKED::SECULARMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.PrepayAssetBackedProcessor.SecularMetrics (jsonParameter);

		if ("TREASURYBOND::SECULARMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.service.json.TreasuryBondProcessor.SecularMetrics (jsonParameter);

		if ("BLACKLITTERMAN::BAYESIANMETRICS".equalsIgnoreCase (strAPIName))
			return org.drip.json.assetallocation.BlackLittermanProcessor.Estimate (jsonParameter);

		return null;
	}

	/**
	 * JSON String Based in/out Generic Thunker
	 * 
	 * @param strJSONRequest JSON String Request
	 * 
	 * @return JSON String Response
	 */

	public static final java.lang.String Thunker (
		final java.lang.String strJSONRequest)
	{
    	if (null == strJSONRequest || strJSONRequest.isEmpty()) return null;

		java.lang.Object objInput = org.drip.json.simple.JSONValue.parse (strJSONRequest);

		if (null == objInput || !(objInput instanceof org.drip.json.simple.JSONObject)) return null;

		org.drip.json.simple.JSONObject jsonResponse = Thunker ((org.drip.json.simple.JSONObject) objInput);

		return null == jsonResponse ? null : jsonResponse.toJSONString();
	}
}
