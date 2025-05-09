
package org.drip.service.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.drip.service.env.EnvManager;
import org.drip.service.jsonparser.Converter;
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
 * <i>ComputeClient</i> contains the Functionality behind the DROP API Compute Service Client. It provides
 * 	the following Functions:
 * 
 * <ul>
 * 		<li>Construct Standard LocalHost-based Instance of the <i>ComputeClient</i></li>
 * 		<li>Retrieve the Compute Server Host</li>
 * 		<li>Retrieve the Compute Server Port</li>
 * 		<li>Establish a Connection to the Compute Server Engine</li>
 * 		<li>Invoke a Request on the Compute Server and Retrieve the Response</li>
 * 		<li>Entry Point</li>
 * </ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/engine/README.md">Compute Engine Request-Response Thunker</a></td></tr>
 *  </table>
 * <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComputeClient
{
	private int _serverPort = -1;
	private String _serverHost = "";
	private Socket _socketServer = null;

	/**
	 * Construct Standard LocalHost-based Instance of the <i>ComputeClient</i>
	 * 
	 * @return The Standard LocalHost-based Instance of the <i>ComputeClient</i>
	 */

	public static final ComputeClient Standard()
	{
		try {
			ComputeClient computeClient = new ComputeClient (
				"127.0.0.1",
				ComputeServer.DRIP_COMPUTE_ENGINE_PORT
			);

			return computeClient.initialize() ? computeClient : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>ComputeClient</i> Constructor
	 * 
	 * @param serverHost The Compute Server Host
	 * @param serverPort The Compute Server Port
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ComputeClient (
		final String serverHost,
		final int serverPort)
		throws Exception
	{
		if (null == (_serverHost = serverHost) || 0 >= (_serverPort = serverPort)) {
			throw new Exception ("ComputeClient Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Compute Server Host
	 * 
	 * @return The Compute Server Host
	 */

	public String serverHost()
	{
		return _serverHost;
	}

	/**
	 * Retrieve the Compute Server Port
	 * 
	 * @return The Compute Server Port
	 */

	public int serverPort()
	{
		return _serverPort;
	}

	/**
	 * Establish a Connection to the Compute Server Engine
	 * 
	 * @return TRUE - Connection to the Compute Server Engine Established
	 */

	public boolean initialize()
	{
		try {
			_socketServer = new Socket (_serverHost, _serverPort);

	    	return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Invoke a Request on the Compute Server and Retrieve the Response
	 * 
	 * @param requestJSON The Input JSON Request
	 * 
	 * @return The Processed JSON Response
	 */

	public JSONObject invoke (
		final JSONObject requestJSON)
	{
		if (!RequestResponseDecorator.AffixRequestHeaders (requestJSON)) {
			return null;
		}

		try {
	    	PrintWriter printWriter = new PrintWriter (_socketServer.getOutputStream(), true);

	    	printWriter.write (requestJSON.toJSONString() + "\n");

	    	printWriter.flush();

	    	Object response = JSONValue.parse (
    			new BufferedReader (new InputStreamReader (_socketServer.getInputStream())).readLine()
			);

			return null == response || !(response instanceof JSONObject) ? null : (JSONObject) response;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Argument Array
	 * 
	 * @throws Exception Propagate Exception Encountered
	 */

	@SuppressWarnings ("unchecked") public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] assetNameArray = new String[] {"TOK", "EWJ", "HYG", "LQD", "EMD", "GSG", "BWX"};

		double[] expectedReturnsArray = new double[] {
			0.008355,
			0.007207,
			0.006279,
			0.002466,
			0.004472,
			0.006821,
			0.001570
		};

		double[][] returnsCovarianceMatrix = new double[][] {
			{0.002733, 0.002083, 0.001593, 0.000488, 0.001172, 0.002312, 0.000710},
			{0.002083, 0.002768, 0.001302, 0.000457, 0.001105, 0.001647, 0.000563},
			{0.001593, 0.001302, 0.001463, 0.000639, 0.001050, 0.001110, 0.000519},
			{0.000488, 0.000457, 0.000639, 0.000608, 0.000663, 0.000042, 0.000370},
			{0.001172, 0.001105, 0.001050, 0.000663, 0.001389, 0.000825, 0.000661},
			{0.002312, 0.001647, 0.001110, 0.000042, 0.000825, 0.005211, 0.000749},
			{0.000710, 0.000563, 0.000519, 0.000370, 0.000661, 0.000749, 0.000703}
		};

		double[] assetWeightLowerBoundArray = new double[] {0.05, 0.05, 0.05, 0.10, 0.05, 0.05, 0.03};

		double[] assetWeightUpperBoundArray = new double[] {0.40, 0.40, 0.30, 0.60, 0.35, 0.15, 0.50};

		double[][] assetWeightLowerUpperBoundConstraint = new double[expectedReturnsArray.length][2];

		for (int assetIndex = 0; assetIndex < expectedReturnsArray.length; ++assetIndex) {
			assetWeightLowerUpperBoundConstraint[assetIndex][0] = assetWeightLowerBoundArray[assetIndex];
			assetWeightLowerUpperBoundConstraint[assetIndex][1] = assetWeightUpperBoundArray[assetIndex];
		}

		JSONObject parametersJSON = new JSONObject();

		parametersJSON.put ("AssetSet", Converter.Array (assetNameArray));

		parametersJSON.put ("AssetExpectedReturns", Converter.Array (expectedReturnsArray));

		parametersJSON.put ("AssetReturnsCovariance", Converter.Array (returnsCovarianceMatrix));

		for (int assetIndex = 0; assetIndex < expectedReturnsArray.length; ++assetIndex) {
			parametersJSON.put (
				assetNameArray[assetIndex] + "::LowerBound",
				assetWeightLowerUpperBoundConstraint[assetIndex][0]
			);

			parametersJSON.put (
				assetNameArray[assetIndex] + "::UpperBound",
				assetWeightLowerUpperBoundConstraint[assetIndex][1]
			);
		}

		JSONObject requestJSON = new JSONObject();

		requestJSON.put ("API", "PORTFOLIOALLOCATION::BUDGETCONSTRAINEDMEANVARIANCE");

		requestJSON.put ("Parameters", parametersJSON);

		System.out.println ("\n\t|---------------- JSON REQUEST -----------------|\n");

		System.out.println (requestJSON.toJSONString());

		System.out.println ("\n\t|---------------- JSON RESPONSE ----------------|\n");

		System.out.println (Standard().invoke (requestJSON).toJSONString());
	}
}
