
package org.drip.portfolioconstruction.optimizer;

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
 * ObjectiveTerm holds the Details of a given Objective Term.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ObjectiveTerm extends org.drip.portfolioconstruction.core.Block {
	private java.util.Map<java.lang.String, org.drip.portfolioconstruction.composite.BlockAttribute>
		_mapAttribute = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.composite.BlockAttribute>();

	private java.util.Map<java.lang.String, org.drip.portfolioconstruction.composite.BlockClassification>
		_mapClassification = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.composite.BlockClassification>();

	private org.drip.function.definition.RdToR1 _rdToR1 = null;

	/**
	 * ObjectiveTerm Constructor
	 * 
	 * @param strName The Objective Term Name
	 * @param strID The Objective Term ID
	 * @param strDescription The Objective Term Description
	 * @param rdToR1 The R^d To R^1 Objective Term Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ObjectiveTerm (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.function.definition.RdToR1 rdToR1)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		if (null == (_rdToR1 = rdToR1))
			throw new java.lang.Exception ("ObjectiveTerm Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Attributes relevant to the Objective Term
	 * 
	 * @return Map of Attributes relevant to the Objective Term
	 */

	public java.util.Map<java.lang.String, org.drip.portfolioconstruction.composite.BlockAttribute>
		attributes()
	{
		return _mapAttribute;
	}

	/**
	 * Retrieve the Classifications relevant to the Objective Term
	 * 
	 * @return Map of Classifications relevant to the Objective Term
	 */

	public java.util.Map<java.lang.String, org.drip.portfolioconstruction.composite.BlockClassification>
		classifications()
	{
		return _mapClassification;
	}

	/**
	 * The R^d To R^1 Objective Function
	 * 
	 * @return The R^d To R^1 Objective Function
	 */

	public org.drip.function.definition.RdToR1 rdtoR1()
	{
		return _rdToR1;
	}
}
