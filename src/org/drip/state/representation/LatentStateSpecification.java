
package org.drip.state.representation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * LatentStateSpecification holds the fields necessary to specify a complete Latent State. It includes the
 * 	Latent State Type, the Latent State Label, and the Latent State Quantification metric.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateSpecification {
	private java.lang.String _strLatentState = "";
	private org.drip.state.identifier.LatentStateLabel _label = null;
	private java.lang.String _strLatentStateQuantificationMetric = "";

	/**
	 * LatentStateSpecification constructor
	 * 
	 * @param strLatentState The Latent State
	 * @param strLatentStateQuantificationMetric The Latent State Quantification Metric
	 * @param label The Specific Latent State Label
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public LatentStateSpecification (
		final java.lang.String strLatentState,
		final java.lang.String strLatentStateQuantificationMetric,
		final org.drip.state.identifier.LatentStateLabel label)
		throws java.lang.Exception
	{
		if (null == (_strLatentState = strLatentState) || _strLatentState.isEmpty() || null ==
			(_strLatentStateQuantificationMetric = strLatentStateQuantificationMetric) ||
				_strLatentStateQuantificationMetric.isEmpty() || null == (_label = label))
			throw new java.lang.Exception ("LatentStateSpecification ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Latent State
	 * 
	 * @return The Latent State
	 */

	public java.lang.String latentState()
	{
		return _strLatentState;
	}

	/**
	 * Retrieve the Latent State Label
	 * 
	 * @return The Latent State Label
	 */

	public org.drip.state.identifier.LatentStateLabel label()
	{
		return _label;
	}

	/**
	 * Retrieve the Latent State Quantification Metric
	 * 
	 * @return The Latent State Quantification Metric
	 */

	public java.lang.String latentStateQuantificationMetric()
	{
		return _strLatentStateQuantificationMetric;
	}

	/**
	 * Does the Specified Latent State Specification Instance match the current one?
	 * 
	 * @param lssOther The "Other" Latent State Specification Instance
	 * 
	 * @return TRUE - Matches the Specified Latent State Specification Instance
	 */

	public boolean match (
		final LatentStateSpecification lssOther)
	{
		return null == lssOther ? false : _strLatentState.equalsIgnoreCase (lssOther.latentState()) &&
			_strLatentStateQuantificationMetric.equalsIgnoreCase (lssOther.latentStateQuantificationMetric())
				&& _label.match (lssOther.label());
	}

	/**
	 * Display the Latent State Details
	 * 
	 * @param strComment The Comment Prefix
	 */

	public void displayString (
		final java.lang.String strComment)
	{
		System.out.println ("\t[LatentStateSpecification]: " + _strLatentState + " | " +
			_strLatentStateQuantificationMetric + " | " + _label.fullyQualifiedName());
	}
}
