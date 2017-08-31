
package org.drip.function.rdtor1solver;

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
 * ConstraintFunctionPointMetrics holds the R^d Point Base and Sensitivity Metrics of the Constraint
 *  Function.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstraintFunctionPointMetrics {
	private double[] _adblValue = null;
	private double[] _adblMultiplier = null;
	private double[][] _aadblJacobian = null;

	/**
	 * ConstraintFunctionPointMetrics Constructor
	 * 
	 * @param adblValue Constraint Value Array
	 * @param aadblJacobian Constraint Jacobian Matrix
	 * @param adblMultiplier Constraint Karush-Kahn-Tucker Multiplier Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConstraintFunctionPointMetrics (
		final double[] adblValue,
		final double[][] aadblJacobian,
		final double[] adblMultiplier)
		throws java.lang.Exception
	{
		if (null == (_adblValue = adblValue) || null == (_aadblJacobian = aadblJacobian) || null ==
			(_adblMultiplier = adblMultiplier))
			throw new java.lang.Exception ("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");

		int iDimension = _aadblJacobian.length;
		int iNumConstraint = _adblValue.length;

		if (0 == iNumConstraint || iNumConstraint != adblMultiplier.length || 0 == iDimension)
			throw new java.lang.Exception ("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");

		for (int i = 0; i < iNumConstraint; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (_adblValue[i]) ||
				!org.drip.quant.common.NumberUtil.IsValid (_adblMultiplier[i]))
				throw new java.lang.Exception ("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");
		}

		for (int i = 0; i < iDimension; ++i) {
			if (null == _aadblJacobian[i] || iNumConstraint != _aadblJacobian[i].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_aadblJacobian[i]))
				throw new java.lang.Exception
					("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Constraint Count
	 * 
	 * @return The Constraint Count
	 */

	public int count()
	{
		return _adblValue.length;
	}

	/**
	 * Retrieve the Constraint Dimension
	 * 
	 * @return The Constraint Dimension
	 */

	public int dimension()
	{
		return _aadblJacobian.length;
	}

	/**
	 * Retrieve the Constraint Value Array
	 * 
	 * @return The Constraint Value Array
	 */

	public double[] value()
	{
		return _adblValue;
	}

	/**
	 * Retrieve the Constraint KKR Multiplier Array
	 * 
	 * @return The Constraint KKR Multiplier Array
	 */

	public double[] multiplier()
	{
		return _adblMultiplier;
	}

	/**
	 * Retrieve the Constraint Jacobian Matrix
	 * 
	 * @return The Constraint Jacobian Matrix
	 */

	public double[][] jacobian()
	{
		return _aadblJacobian;
	}
}
