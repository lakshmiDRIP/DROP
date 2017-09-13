
package org.drip.portfolioconstruction.unit;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * Block forms the Base underneath all Portfolio Construction Objects.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Block {
	private java.lang.String _strID = "";
	private java.lang.String _strName = "";
	private java.util.Date _dateTimeStamp = null;
	private java.lang.String _strDescription = "";

	/**
	 * Construct a Standard Instance of a Block
	 * 
	 * @param strName The Block Name
	 * 
	 * @return The Standard Block Instance
	 */

	public static final Block Standard (
		final java.lang.String strName)
	{
		try {
			return new Block (strName, strName, strName);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Block Constructor
	 * 
	 * @param strName The Name
	 * @param strID The ID
	 * @param strDescription The Description
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Block (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_strID = strID) ||
			_strID.isEmpty() || null == (_strDescription = strDescription) || _strDescription.isEmpty())
			throw new java.lang.Exception ("Block Constructor => Invalid Inputs");

		_dateTimeStamp = new java.util.Date();
	}

	/**
	 * Retrieve the Name
	 * 
	 * @return The Name
	 */

	public java.lang.String name()
	{
		return _strName;
	}

	/**
	 * Retrieve the ID
	 * 
	 * @return The ID
	 */

	public java.lang.String id()
	{
		return _strID;
	}

	/**
	 * Retrieve the Description
	 * 
	 * @return The Description
	 */

	public java.lang.String description()
	{
		return _strDescription;
	}

	/**
	 * Retrieve the Creation Time Stamp
	 * 
	 * @return The Creation Time Stamp
	 */

	public java.util.Date timeStamp()
	{
		return _dateTimeStamp;
	}
}
