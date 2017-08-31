
package org.drip.service.json;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * KeyHoleSkeleton forwards the JSON Request to the Appropriate Processor and retrieves the Response JSON.
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

		if ("FORWARDRATEFUTURES::SECULARMETRICS".equalsIgnoreCase (strAPIName))

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
