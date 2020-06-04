
package org.drip.sample.service;

import org.drip.service.env.EnvManager;
import org.drip.service.json.KeyHoleSkeleton;
import org.drip.service.jsonparser.Converter;
import org.drip.service.representation.JSONObject;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>BudgetConstrainedAllocationClient</i> demonstrates the Invocation and Examination of the JSON-based
 *  Budget Constrained Portfolio Allocation Service Client.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/service/README.md">Curve Product Portfolio Valuation Services</a></li>
 *  </ul>
 * <br><br>
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

		EnvManager.TerminateEnv();
	}
}
