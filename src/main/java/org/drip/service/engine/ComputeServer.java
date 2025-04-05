
package org.drip.service.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.drip.service.env.EnvManager;
import org.drip.service.json.KeyHoleSkeleton;
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
 * <i>ComputeServer</i> contains the Functionality behind the DROP API Compute Service Engine. It provides
 * 	the following Functions:
 * 
 * <ul>
 * 		<li>The DRIP compute Service Engine Port</li>
 * 		<li>Create a Standard Instance of the <i>ComputeServer</i></li>
 * 		<li><i>ComputeServer</i> Constructor</li>
 * 		<li>Initialize the Compute Server Engine Listener Setup</li>
 * 		<li>Spin on the Listener Loop</li>
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

public class ComputeServer
{

	/**
	 * The DRIP compute Service Engine Port
	 */

	public static final int DRIP_COMPUTE_ENGINE_PORT = 9090;

	private int _listenerPort = -1;
	private ServerSocket _listenerServerSocket = null;

	/**
	 * Create a Standard Instance of the <i>ComputeServer</i>
	 * 
	 * @return The Standard <i>ComputeServer</i> Instance
	 */

	public static final ComputeServer Standard()
	{
		try {
			ComputeServer computeServer = new ComputeServer (DRIP_COMPUTE_ENGINE_PORT);

			return computeServer.initialize() ? computeServer : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>ComputeServer</i> Constructor
	 * 
	 * @param listenerPort The Listener Port
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ComputeServer (
		final int listenerPort)
		throws Exception
	{
		if (0 >= (_listenerPort = listenerPort)) {
			throw new Exception ("ComputeServer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Initialize the Compute Server Engine Listener Setup
	 * 
	 * @return TRUE - The Compute Server Engine Listener Setup
	 */

	public boolean initialize()
	{
		try {
			_listenerServerSocket = new ServerSocket (_listenerPort);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Spin on the Listener Loop
	 * 
	 * @return FALSE - Spinning Terminated
	 */

	public boolean spin()
	{
        while (true) {
        	try {
            	Socket listenerSocket = _listenerServerSocket.accept();

	        	if (null == listenerSocket) {
	        		return false;
	        	}

	        	String requestString = new BufferedReader (
        			new InputStreamReader (listenerSocket.getInputStream())
    			).readLine();

	        	System.out.println (requestString);

	        	Object requestObject = JSONValue.parse (requestString);

		    	if (null == requestObject || !(requestObject instanceof JSONObject)) {
		    		return false;
		    	}

		    	JSONObject requestJSON = (JSONObject) requestObject;

		    	Object responseObject = JSONValue.parse (KeyHoleSkeleton.Thunker (requestString));

		    	if (null == responseObject) {
		    		return false;
		    	}

		    	JSONObject responseJSON = (JSONObject) responseObject;

		    	if (!RequestResponseDecorator.AffixResponseHeaders (responseJSON, requestJSON)) {
		    		return false;
		    	}

	        	System.out.println ("\n\n" + responseJSON.toJSONString());

            	PrintWriter printWriter = new PrintWriter (listenerSocket.getOutputStream(), true);

            	printWriter.write (responseJSON.toJSONString() + "\n");

            	printWriter.flush();
        	} catch (Exception e) {
        		e.printStackTrace();

        		return false;
        	}
        }
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Argument Array
	 * 
	 * @throws Exception Propagate Exception Encountered
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		ComputeServer.Standard().spin();
	}
}
