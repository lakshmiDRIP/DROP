
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
 * ComputeClient contains the Functionality behind the DRIP API Compute Service Client.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComputeClient {
	private int _iComputeServerPort = -1;
	private java.lang.String _strComputeServerHost = "";
	private java.net.Socket _socketComputeServer = null;

	/**
	 * Construct Standard LocalHost-based Instance of the ComputeClient
	 * 
	 * @return The Standard LocalHost-based Instance of the ComputeClient
	 */

	public static final ComputeClient Standard()
	{
		try {
			ComputeClient cc = new ComputeClient ("127.0.0.1",
				org.drip.service.engine.ComputeServer.DRIP_COMPUTE_ENGINE_PORT);

			return cc.initialize() ? cc : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ComputeClient Constructor
	 * 
	 * @param strComputeServerHost The Compute Server Host
	 * @param iComputeServerPort The Compute Server Port
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ComputeClient (
		final java.lang.String strComputeServerHost,
		final int iComputeServerPort)
		throws java.lang.Exception
	{
		if (null == (_strComputeServerHost = strComputeServerHost) || 0 >= (_iComputeServerPort =
			iComputeServerPort))
			throw new java.lang.Exception ("ComputeClient Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Compute Server Host
	 * 
	 * @return The Compute Server Host
	 */

	public java.lang.String computeServerHost()
	{
		return _strComputeServerHost;
	}

	/**
	 * Retrieve the Compute Server Port
	 * 
	 * @return The Compute Server Port
	 */

	public int computeServerPort()
	{
		return _iComputeServerPort;
	}

	/**
	 * Establish a Connection to the Compute Server Engine
	 * 
	 * @return TRUE - Connection to the Compute Server Engine Established
	 */

	public boolean initialize()
	{
		try {
	    	_socketComputeServer = new java.net.Socket (_strComputeServerHost, _iComputeServerPort);

	    	return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Invoke a Request on the Compute Server and Retrieve the Response
	 * 
	 * @param jsonRequest The Input JSON Request
	 * 
	 * @return The Processed JSON Response
	 */

	public org.drip.json.simple.JSONObject invoke (
		final org.drip.json.simple.JSONObject jsonRequest)
	{
		if (!org.drip.service.engine.RequestResponseDecorator.AffixRequestHeaders (jsonRequest)) return null;

		try {
	    	java.io.PrintWriter pw = new java.io.PrintWriter (_socketComputeServer.getOutputStream(), true);

	    	pw.write (jsonRequest.toJSONString() + "\n");

	    	pw.flush();

	    	java.lang.Object objResponse = org.drip.json.simple.JSONValue.parse (new java.io.BufferedReader
	    		(new java.io.InputStreamReader (_socketComputeServer.getInputStream())).readLine());

			return null == objResponse || !(objResponse instanceof org.drip.json.simple.JSONObject) ? null :
				(org.drip.json.simple.JSONObject) objResponse;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings ("unchecked") public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		org.drip.service.env.EnvManager.InitEnv ("");

		String[] astrAssetName = new String[] {
			"TOK",
			"EWJ",
			"HYG",
			"LQD",
			"EMD",
			"GSG",
			"BWX"
		};

		double[] adblAssetExpectedReturns = new double[] {
			0.008355,
			0.007207,
			0.006279,
			0.002466,
			0.004472,
			0.006821,
			0.001570
		};

		double[][] aadblAssetReturnsCovariance = new double[][] {
			{0.002733, 0.002083, 0.001593, 0.000488, 0.001172, 0.002312, 0.000710},
			{0.002083, 0.002768, 0.001302, 0.000457, 0.001105, 0.001647, 0.000563},
			{0.001593, 0.001302, 0.001463, 0.000639, 0.001050, 0.001110, 0.000519},
			{0.000488, 0.000457, 0.000639, 0.000608, 0.000663, 0.000042, 0.000370},
			{0.001172, 0.001105, 0.001050, 0.000663, 0.001389, 0.000825, 0.000661},
			{0.002312, 0.001647, 0.001110, 0.000042, 0.000825, 0.005211, 0.000749},
			{0.000710, 0.000563, 0.000519, 0.000370, 0.000661, 0.000749, 0.000703}
		};

		double[] adblAssetLowerBound = new double[] {
			0.05,
			0.05,
			0.05,
			0.10,
			0.05,
			0.05,
			0.03
		};

		double[] adblAssetUpperBound = new double[] {
			0.40,
			0.40,
			0.30,
			0.60,
			0.35,
			0.15,
			0.50
		};

		double[][] aadblBound = new double[adblAssetExpectedReturns.length][2];

		for (int i = 0; i < adblAssetExpectedReturns.length; ++i) {
			aadblBound[i][0] = adblAssetLowerBound[i];
			aadblBound[i][1] = adblAssetUpperBound[i];
		}

		org.drip.json.simple.JSONObject jsonParameters = new org.drip.json.simple.JSONObject();

		jsonParameters.put ("AssetSet", org.drip.json.parser.Converter.Array (astrAssetName));

		jsonParameters.put ("AssetExpectedReturns", org.drip.json.parser.Converter.Array
			(adblAssetExpectedReturns));

		jsonParameters.put ("AssetReturnsCovariance", org.drip.json.parser.Converter.Array
			(aadblAssetReturnsCovariance));

		for (int i = 0; i < adblAssetExpectedReturns.length; ++i) {
			jsonParameters.put (astrAssetName[i] + "::LowerBound", aadblBound[i][0]);

			jsonParameters.put (astrAssetName[i] + "::UpperBound", aadblBound[i][1]);
		}

		org.drip.json.simple.JSONObject jsonRequest = new org.drip.json.simple.JSONObject();

		jsonRequest.put ("API", "PORTFOLIOALLOCATION::BUDGETCONSTRAINEDMEANVARIANCE");

		jsonRequest.put ("Parameters", jsonParameters);

		System.out.println ("\n\t|---------------- JSON REQUEST -----------------|\n");

		System.out.println (jsonRequest.toJSONString());

		ComputeClient cc = Standard();

		org.drip.json.simple.JSONObject jsonResponse = cc.invoke (jsonRequest);

		System.out.println ("\n\t|---------------- JSON RESPONSE ----------------|\n");

		System.out.println (jsonResponse.toJSONString());
	}
}
