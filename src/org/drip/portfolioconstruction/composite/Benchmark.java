
package org.drip.portfolioconstruction.composite;

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
 * Benchmark holds the Details of a given Benchmark.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Benchmark extends org.drip.portfolioconstruction.unit.Block {
	private java.lang.String _strType = "";
	private java.lang.String _strCategory = "";
	private org.drip.portfolioconstruction.composite.Holdings _ah = null;

	/**
	 * Construct a Standard Benchmark Instance Without Cash
	 * 
	 * @param strName The Benchmark Name
	 * @param strType The Benchmark Type
	 * @param strDescription The Benchmark Description
	 * @param ah The Benchmark Holdings
	 * 
	 * @return The Standard Benchmark Instance Without Cash
	 */

	public static final Benchmark Standard (
		final java.lang.String strName,
		final java.lang.String strType,
		final java.lang.String strCategory,
		final org.drip.portfolioconstruction.composite.Holdings ah)
	{
		try {
			return new Benchmark (strName, strName, strName, strType, strCategory, ah);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Benchmark Constructor
	 * 
	 * @param strName The Benchmark Name
	 * @param strID The Benchmark ID
	 * @param strDescription The Benchmark Description
	 * @param strType The Benchmark Type
	 * @param strDescription The Benchmark Description
	 * @param ah The Benchmark Holdings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Benchmark (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final java.lang.String strType,
		final java.lang.String strCategory,
		final org.drip.portfolioconstruction.composite.Holdings ah)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		if (null == (_strType = strType) || _strType.isEmpty() || null == (_strCategory = strCategory) ||
			_strCategory.isEmpty() || null == (_ah = ah))
			throw new java.lang.Exception ("Benchmark Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Benchmark Type
	 * 
	 * @return The Benchmark Type
	 */

	public java.lang.String type()
	{
		return _strType;
	}

	/**
	 * Retrieve the Benchmark Category
	 * 
	 * @return The Benchmark Category
	 */

	public java.lang.String category()
	{
		return _strCategory;
	}

	/**
	 * Retrieve the Benchmark Holdings
	 * 
	 * @return The Benchmark Holdings
	 */

	public org.drip.portfolioconstruction.composite.Holdings holdings()
	{
		return _ah;
	}
}
