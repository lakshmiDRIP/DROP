
package org.drip.portfolioconstruction.core;

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
 * FactorLoading contains the Factor Loadings and Specific Risks for a Specific Asset.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FactorLoading {
	private double _dblSpecificRisk = java.lang.Double.NaN;

	private java.util.Map<java.lang.String, java.lang.Double> _mapCoefficient = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * FactorLoading Constructor
	 * 
	 * @param dblSpecificRisk The Asset-specific Risk
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FactorLoading (
		final double dblSpecificRisk)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblSpecificRisk = dblSpecificRisk))
			throw new java.lang.Exception ("FactorLoading Constructor => Invalid Inputs");
	}

	/**
	 * Add a Factor Coefficient
	 * 
	 * @param strFactorID Factor ID
	 * @param dblCoefficient Factor Coefficient
	 * 
	 * @return TRUE => The Factor Coefficient successfully added
	 */

	public boolean addCoefficient (
		final java.lang.String strFactorID,
		final double dblCoefficient)
	{
		if (null == strFactorID || strFactorID.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
			(dblCoefficient))
			return false;

		_mapCoefficient.put (strFactorID, dblCoefficient);

		return true;
	}

	/**
	 * Retrieve the Asset's Specific Risk
	 * 
	 * @return The Asset's Specific Risk
	 */

	public double specificRisk()
	{
		return _dblSpecificRisk;
	}

	/**
	 * Retrieve the Factor Loading Coefficients
	 * 
	 * @return The Factor Loading Coefficients
	 */

	public java.util.Map<java.lang.String, java.lang.Double> coefficients()
	{
		return _mapCoefficient;
	}
}
