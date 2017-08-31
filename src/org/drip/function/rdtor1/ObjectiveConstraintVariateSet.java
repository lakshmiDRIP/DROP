
package org.drip.function.rdtor1;

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
 * ObjectiveConstraintVariateSet holds the R^d and R^1 Variates corresponding to the Objective Function and
 *  the Constraint Function respectively.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ObjectiveConstraintVariateSet {
	private double[] _adblObjectiveVariate = null;
	private double[] _adblConstraintVariate = null;

	/**
	 * Make a Unitary Variate Set
	 * 
	 * @param iNumVariate Number of Variates
	 * 
	 * @return Unitary Variate Set
	 */

	public static final double[] Unitary (
		final int iNumVariate)
	{
		if (0 >= iNumVariate) return null;

		double[] adblVariate = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblVariate[i] = 1.;

		return adblVariate;
	}

	/**
	 * Make a Variate Set with/without Constraint
	 * 
	 * @param iNumObjectiveFunctionVariate Number of the Objective Function Variates
	 * @param iNumConstraintFunctionVariate Number of the Constraint Function Variates
	 * 
	 * @return Variate Set with/without Constraint
	 */

	public static final double[] Uniform (
		final int iNumObjectiveFunctionVariate,
		final int iNumConstraintFunctionVariate)
	{
		if (0 >= iNumObjectiveFunctionVariate) return null;

		double[] adblVariate = new double[iNumObjectiveFunctionVariate + iNumConstraintFunctionVariate];

		for (int i = 0; i < iNumObjectiveFunctionVariate; ++i)
			adblVariate[i] = 1. / iNumObjectiveFunctionVariate;

		for (int i = 0; i < iNumConstraintFunctionVariate; ++i)
			adblVariate[i + iNumObjectiveFunctionVariate] = 0.;

		return adblVariate;
	}

	/**
	 * Make a Variate Set using a Pre-set Objective Variate Array with/without Constraint
	 * 
	 * @param adblObjectiveFunctionVariate Array of Pre-set Objective Variates
	 * @param iNumConstraintFunctionVariate Number of the Constraint Function Variates
	 * 
	 * @return Variate Set using a Pre-set Objective Variate Array with/without Constraint
	 */

	public static final double[] Preset (
		final double[] adblObjectiveFunctionVariate,
		final int iNumConstraintFunctionVariate)
	{
		if (null == adblObjectiveFunctionVariate) return null;

		int iNumObjectiveFunctionVariate = adblObjectiveFunctionVariate.length;

		if (0 >= iNumObjectiveFunctionVariate) return null;

		double[] adblVariate = new double[iNumObjectiveFunctionVariate + iNumConstraintFunctionVariate];

		for (int i = 0; i < iNumObjectiveFunctionVariate; ++i)
			adblVariate[i] = adblObjectiveFunctionVariate[i];

		for (int i = 0; i < iNumConstraintFunctionVariate; ++i)
			adblVariate[i + iNumObjectiveFunctionVariate] = 0.;

		return adblVariate;
	}

	/**
	 * Partition the Variate Array into the Objective Function Input Variates and the Constraint Variate
	 * 
	 * @param adblVariate The Input Variate Array
	 * @param iNumObjectiveFunctionVariate Number of the Objective Function Variates
	 * 
	 * @return The ObjectiveConstraintVariateSet Instance
	 */

	public static final ObjectiveConstraintVariateSet Partition (
		final double[] adblVariate,
		final int iNumObjectiveFunctionVariate)
	{
		if (null == adblVariate || 0 == iNumObjectiveFunctionVariate) return null;

		int iNumVariate = adblVariate.length;
		double[] adblObjectiveVariate = new double[iNumObjectiveFunctionVariate];
		double[] adblConstraintVariate = new double[iNumVariate - iNumObjectiveFunctionVariate];

		if (iNumObjectiveFunctionVariate >= iNumVariate) return null;

		for (int i = 0; i < iNumObjectiveFunctionVariate; ++i)
			adblObjectiveVariate[i] = adblVariate[i];

		for (int i = iNumObjectiveFunctionVariate; i < iNumVariate; ++i)
			adblConstraintVariate[i - iNumObjectiveFunctionVariate] = adblVariate[i];

		try {
			return new ObjectiveConstraintVariateSet (adblObjectiveVariate, adblConstraintVariate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ObjectiveConstraintVariate Constructor
	 * 
	 * @param adblObjectiveVariate Array of the Objective Function Variates
	 * @param adblConstraintVariate Array of the Constraint Function Variates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ObjectiveConstraintVariateSet (
		final double[] adblObjectiveVariate,
		final double[] adblConstraintVariate)
		throws java.lang.Exception
	{
		if (null == (_adblObjectiveVariate = adblObjectiveVariate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_adblObjectiveVariate) || null ==
				(_adblConstraintVariate = adblConstraintVariate) || !org.drip.quant.common.NumberUtil.IsValid
					(adblConstraintVariate))
			throw new java.lang.Exception ("ObjectiveConstraintVariateSet Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Array of the Objective Function Variates
	 * 
	 * @return The Array of the Objective Function Variates
	 */

	public double[] objectiveVariates()
	{
		return _adblObjectiveVariate;
	}

	/**
	 * Retrieve the Array of the Constraint Function Variates
	 * 
	 * @return The Array of the Constraint Function Variates
	 */

	public double[] constraintVariates()
	{
		return _adblConstraintVariate;
	}

	/**
	 * Unify the Objective Function and the Constraint Function Input Variate Set
	 * 
	 * @return The Unified Objective Function and the Constraint Function Input Variate Set
	 */

	public double[] unify()
	{
		int iNumObjectiveFunctionVariate = _adblObjectiveVariate.length;
		int iNumConstraintFunctionVariate = _adblConstraintVariate.length;
		int iNumVariate = iNumObjectiveFunctionVariate + iNumConstraintFunctionVariate;
		double[] adblVariate = new double[iNumVariate];

		for (int i = 0; i < iNumObjectiveFunctionVariate; ++i)
			adblVariate[i] = _adblObjectiveVariate[i];

		for (int i = 0; i < iNumConstraintFunctionVariate; ++i)
			adblVariate[iNumObjectiveFunctionVariate + i] = _adblConstraintVariate[i];

		return adblVariate;
	}
}
