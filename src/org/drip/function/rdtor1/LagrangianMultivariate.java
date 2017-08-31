
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
 * LagrangianMultivariate implements an R^d To R^1 Multivariate Function along with the specified Set of
 * 	Equality Constraints.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LagrangianMultivariate extends org.drip.function.definition.RdToR1 {
	private org.drip.function.definition.RdToR1 _RdToR1Objective = null;
	private org.drip.function.definition.RdToR1[] _aRdToR1EqualityConstraint = null;

	/**
	 * LagrangianMultivariate Constructor
	 * 
	 * @param RdToR1Objective The Objective Function
	 * @param aRdToR1EqualityConstraint Array of Equality Constraint Functions
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LagrangianMultivariate (
		final org.drip.function.definition.RdToR1 RdToR1Objective,
		final org.drip.function.definition.RdToR1[] aRdToR1EqualityConstraint)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_RdToR1Objective = RdToR1Objective))
			throw new java.lang.Exception ("LagrangianMultivariate Constructor => Invalid Inputs");

		_aRdToR1EqualityConstraint = aRdToR1EqualityConstraint;
	}

	/**
	 * Retrieve the Objective R^d To R^1 Function Instance
	 * 
	 * @return The Objective R^d To R^1 Function Instance
	 */

	public org.drip.function.definition.RdToR1 objectiveFunction()
	{
		return _RdToR1Objective;
	}

	/**
	 * Retrieve the Array of the Constraint R^d To R^1 Function Instances
	 * 
	 * @return The Array of Constraint R^d To R^1 Function Instances
	 */

	public org.drip.function.definition.RdToR1[] constraintFunctions()
	{
		return _aRdToR1EqualityConstraint;
	}

	/**
	 * Retrieve the Objective Function Dimension
	 * 
	 * @return The Objective Function Dimension
	 */

	public int objectiveFunctionDimension()
	{
		return _RdToR1Objective.dimension();
	}

	/**
	 * Retrieve the Constraint Function Dimension
	 * 
	 * @return The Constraint Function Dimension
	 */

	public int constraintFunctionDimension()
	{
		return null == _aRdToR1EqualityConstraint ? 0 : _aRdToR1EqualityConstraint.length;
	}

	@Override public int dimension()
	{
		return objectiveFunctionDimension() + constraintFunctionDimension();
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		org.drip.function.rdtor1.ObjectiveConstraintVariateSet ocvs =
			org.drip.function.rdtor1.ObjectiveConstraintVariateSet.Partition (adblVariate,
				objectiveFunctionDimension());

		if (null == ocvs)
			throw new java.lang.Exception ("LagrangianMultivariate::evaluate => Invalid Inputs");

		double[] adblConstraintVariate = ocvs.constraintVariates();

		double[] adblObjectiveVariate = ocvs.objectiveVariates();

		int iNumConstraint = adblConstraintVariate.length;

		double dblValue = _RdToR1Objective.evaluate (adblObjectiveVariate);

		for (int i = 0; i < iNumConstraint; ++i)
			dblValue += adblConstraintVariate[i] * _aRdToR1EqualityConstraint[i].evaluate
				(adblObjectiveVariate);

		return dblValue;
	}

	@Override public double[] jacobian (
		final double[] adblVariate)
	{
		int iObjectiveDimension = objectiveFunctionDimension();

		int iConstraintDimension = constraintFunctionDimension();

		double[] adblObjectiveVariate = null;
		double[] adblConstraintVariate = null;
		double[][] aadblConstraintJacobian = null;
		double[] adblJacobian = new double[iObjectiveDimension + iConstraintDimension];

		if (0 == iConstraintDimension)
			adblObjectiveVariate = adblVariate;
		else {
			org.drip.function.rdtor1.ObjectiveConstraintVariateSet ocvs =
				org.drip.function.rdtor1.ObjectiveConstraintVariateSet.Partition (adblVariate,
					iObjectiveDimension);

			if (null == ocvs) return null;

			adblObjectiveVariate = ocvs.objectiveVariates();

			adblConstraintVariate = ocvs.constraintVariates();
		}

		double[] adblObjectiveJacobian = _RdToR1Objective.jacobian (adblObjectiveVariate);

		if (null == adblObjectiveJacobian) return null;

		if (0 != iConstraintDimension) aadblConstraintJacobian = new double[iConstraintDimension][];

		for (int i = 0; i < iConstraintDimension; ++i) {
			if (null == (aadblConstraintJacobian[i] = _aRdToR1EqualityConstraint[i].jacobian
				(adblObjectiveVariate)))
				return null;

			try {
				adblJacobian[iObjectiveDimension + i] = _aRdToR1EqualityConstraint[i].evaluate
					(adblObjectiveVariate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		for (int i = 0; i < iObjectiveDimension; ++i) {
			adblJacobian[i] = adblObjectiveJacobian[i];

			for (int j = 0; j < iConstraintDimension; ++j)
				adblJacobian[i] += adblConstraintVariate[j] * aadblConstraintJacobian[j][i];
		}

		return adblJacobian;
	}

	@Override public double[][] hessian (
		final double[] adblVariate)
	{
		int iObjectiveDimension = objectiveFunctionDimension();

		int iConstraintDimension = constraintFunctionDimension();

		double[] adblObjectiveVariate = null;
		double[] adblConstraintVariate = null;

		if (0 == iConstraintDimension)
			adblObjectiveVariate = adblVariate;
		else {
			org.drip.function.rdtor1.ObjectiveConstraintVariateSet ocvs =
				org.drip.function.rdtor1.ObjectiveConstraintVariateSet.Partition (adblVariate,
					iObjectiveDimension);

			if (null == ocvs) return null;

			adblObjectiveVariate = ocvs.objectiveVariates();

			adblConstraintVariate = ocvs.constraintVariates();
		}

		double[][] aadblObjectiveHessian = _RdToR1Objective.hessian (adblObjectiveVariate);

		double[][] aadblConstraintJacobian = null;
		double[][][] aaadblConstraintHessian = null;
		int iDimension = iObjectiveDimension + iConstraintDimension;
		double[][] aadblHessian = new double[iDimension][iDimension];

		if (0 != iConstraintDimension) {
			aadblConstraintJacobian = new double[iConstraintDimension][];
			aaadblConstraintHessian = new double[iConstraintDimension][][];
		}

		for (int i = 0; i < iConstraintDimension; ++i) {
			if (null == (aaadblConstraintHessian[i] = _aRdToR1EqualityConstraint[i].hessian
				(adblObjectiveVariate)))
				return null;
		}

		for (int i = 0; i < iObjectiveDimension; ++i) {
			for (int j = 0; j < iObjectiveDimension; ++j) {
				aadblHessian[i][j] = aadblObjectiveHessian[i][j];

				for (int k = 0; k < iConstraintDimension; ++k)
					aadblHessian[i][j] += adblConstraintVariate[k] * aaadblConstraintHessian[k][i][j];
			}
		}

		for (int i = 0; i < iConstraintDimension; ++i) {
			for (int j = 0; j < iConstraintDimension; ++j)
				aadblHessian[i + iObjectiveDimension][j + iObjectiveDimension] = 0.;

			if (null == (aadblConstraintJacobian[i] = _aRdToR1EqualityConstraint[i].jacobian
				(adblObjectiveVariate)))
				return null;
		}

		for (int i = 0; i < iConstraintDimension; ++i) {
			for (int j = 0; j < iObjectiveDimension; ++j) {
				aadblHessian[iObjectiveDimension + i][j] = aadblConstraintJacobian[i][j];
				aadblHessian[j][iObjectiveDimension + i] = aadblConstraintJacobian[i][j];
			}
		}

		return aadblHessian;
	}
}
