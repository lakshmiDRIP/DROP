
package org.drip.service.engine;

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
 * RequestResponseDecorator contains the Functionality behind the DRIP API Compute Service Engine Request and
 *  Response Header Fields Affixing/Decoration.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RequestResponseDecorator {

	/**
	 * Affix the Headers on the JSON Request
	 * 
	 * @param jsonRequest The JSON Request
	 * 
	 * @return TRUE - The Headers successfully affixed
	 */

	@SuppressWarnings ("unchecked") public static final boolean AffixRequestHeaders (
		final org.drip.json.simple.JSONObject jsonRequest)
	{
		if (null == jsonRequest) return false;

		jsonRequest.put ("APITYPE", "REQUEST");

		jsonRequest.put ("REQUESTTIMESTAMP", new java.util.Date().toString());

		jsonRequest.put ("REQUESTID", org.drip.quant.common.StringUtil.GUID());

		return true;
	}

	/**
	 * Affix the Headers on the JSON Response
	 * 
	 * @param jsonResponse The JSON Response
	 * @param jsonRequest The JSON Request
	 * 
	 * @return TRUE - The Headers successfully affixed
	 */

	@SuppressWarnings ("unchecked") public static final boolean AffixResponseHeaders (
		final org.drip.json.simple.JSONObject jsonResponse,
		final org.drip.json.simple.JSONObject jsonRequest)
	{
		if (null == jsonResponse || null == jsonRequest) return false;

    	jsonResponse.put ("APITYPE", "RESPONSE");

    	jsonResponse.put ("REQUESTTIMESTAMP", org.drip.json.parser.Converter.StringEntry
    		(jsonRequest, "REQUESTTIMESTAMP"));

    	jsonResponse.put ("REQUESTID", org.drip.json.parser.Converter.StringEntry (jsonRequest,
    		"REQUESTID"));

    	jsonResponse.put ("RESPONSETIMESTAMP", new java.util.Date().toString());

    	jsonResponse.put ("RESPONSEID", org.drip.quant.common.StringUtil.GUID());

		return true;
	}
}
