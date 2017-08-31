
package org.drip.sample.service;

import org.drip.json.parser.Converter;
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
 * BudgetConstrainedAllocationClient demonstrates the Invocation and Examination of the JSON-based
 *  Budget Constrained Portfolio Allocation Service Client.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackLittermanBayesianClient {

	@SuppressWarnings ("unchecked") public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblTau = 1.0000;
		double dblDelta = 2.6;
		double dblRiskFreeRate = 0.00;

		String[] astrAssetName = new String[] {
			"ASSET 1",
			"ASSET 2",
			"ASSET 3",
			"ASSET 4",
			"ASSET 5",
			"ASSET 6"
		};

		double[] adblAssetEquilibriumWeight = new double[] {
			0.2535,
			0.1343,
			0.1265,
			0.1375,
			0.0733,
			0.2749
		};

		double[][] aadblAssetExcessReturnsCovariance = new double[][] {
			{0.00273, 0.00208, 0.00159, 0.00049, 0.00117, 0.00071},
			{0.00208, 0.00277, 0.00130, 0.00046, 0.00111, 0.00056},
			{0.00159, 0.00130, 0.00146, 0.00064, 0.00105, 0.00052},
			{0.00049, 0.00046, 0.00064, 0.00061, 0.00066, 0.00037},
			{0.00117, 0.00111, 0.00105, 0.00066, 0.00139, 0.00066},
			{0.00071, 0.00056, 0.00052, 0.00037, 0.00066, 0.00070}
		};

		double[][] aadblAssetSpaceViewProjection = new double[][] {
			{  0.00,  0.00, -1.00,  0.00,  1.00,  0.00},
			{  0.00,  1.00,  0.00,  0.00, -1.00,  0.00},
			{ -1.00,  1.00,  1.00,  0.00,  0.00, -1.00}
		};

		double[] adblProjectionExpectedExcessReturns = new double[] {
			0.0002,
			0.0003,
			0.0001
		};

		double[][] aadblProjectionExcessReturnsCovariance = new double[][] {
			{ 0.00075, -0.00053, -0.00033},
			{-0.00053,  0.00195,  0.00110},
			{-0.00033,  0.00110,  0.00217}
		};

		JSONObject jsonParameters = new JSONObject();

		jsonParameters.put (
			"AssetSet",
			Converter.Array (astrAssetName)
		);

		jsonParameters.put (
			"AssetSpaceViewProjection",
			Converter.Array (aadblAssetSpaceViewProjection)
		);

		jsonParameters.put (
			"AssetEquilibriumWeight",
			Converter.Array (adblAssetEquilibriumWeight)
		);

		jsonParameters.put (
			"AssetExcessReturnsCovariance",
			Converter.Array (aadblAssetExcessReturnsCovariance)
		);

		jsonParameters.put (
			"ProjectionExpectedExcessReturns",
			Converter.Array (adblProjectionExpectedExcessReturns)
		);

		jsonParameters.put (
			"ProjectionExcessReturnsCovariance",
			Converter.Array (aadblProjectionExcessReturnsCovariance)
		);

		jsonParameters.put (
			"RiskFreeRate",
			dblRiskFreeRate
		);

		jsonParameters.put (
			"Delta",
			dblDelta
		);

		jsonParameters.put (
			"Tau",
			dblTau
		);

		JSONObject jsonRequest = new JSONObject();

		jsonRequest.put (
			"API",
			"BLACKLITTERMAN::BAYESIANMETRICS"
		);

		jsonRequest.put ("Parameters", jsonParameters);

		System.out.println ("\n\t|---------------- JSON REQUEST -----------------|\n");

		System.out.println (jsonRequest.toJSONString());

		System.out.println ("\n\t|---------------- JSON RESPONSE ----------------|\n");

		System.out.println (KeyHoleSkeleton.Thunker (jsonRequest.toJSONString()));
	}
}
