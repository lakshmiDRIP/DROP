
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * BuildRecord records the Build Log - DRIP Version, Java Version, and Build Time Stamp.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BuildRecord
{
	private java.lang.String _strTimeStamp = "";
	private java.lang.String _strDRIPVersion = "";
	private java.lang.String _strJavaVersion = "";

	/**
	 * BuildRecord Constructor
	 * 
	 * @param strDRIPVersion The DRIP Build Version
	 * @param strJavaVersion The Java Build Version
	 * @param strTimeStamp The DRIP Build Time Stamp
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BuildRecord (
		final java.lang.String strDRIPVersion,
		final java.lang.String strJavaVersion,
		final java.lang.String strTimeStamp)
		throws java.lang.Exception
	{
		if (null == (_strDRIPVersion = strDRIPVersion) || _strDRIPVersion.isEmpty() ||
			null == (_strJavaVersion = strJavaVersion) || _strJavaVersion.isEmpty() ||
			null == (_strTimeStamp = strTimeStamp) || _strTimeStamp.isEmpty())
			throw new java.lang.Exception ("BuildRecord Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the DRIP Build Version
	 * 
	 * @return The DRIP Build Version
	 */

	public java.lang.String dripVersion()
	{
		return _strDRIPVersion;
	}

	/**
	 * Retrieve the Java Build Version
	 * 
	 * @return The Java Build Version
	 */

	public java.lang.String javaVersion()
	{
		return _strJavaVersion;
	}

	/**
	 * Retrieve the Build Time Stamp
	 * 
	 * @return The Build Time Stamp
	 */

	public java.lang.String timeStamp()
	{
		return _strTimeStamp;
	}
}
