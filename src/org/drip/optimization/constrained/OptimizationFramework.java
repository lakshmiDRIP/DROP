
package org.drip.optimization.constrained;

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
 * OptimizationFramework holds the Non Linear Objective Function and the Collection of Equality and the
 *  Inequality Constraints that correspond to the Optimization Setup. The References are:
 * 
 * 	- Boyd, S., and L. van den Berghe (2009): Convex Optimization, Cambridge University Press, Cambridge UK.
 * 
 * 	- Eustaquio, R., E. Karas, and A. Ribeiro (2008): Constraint Qualification for Nonlinear Programming,
 * 		Technical Report, Federal University of Parana.
 * 
 * 	- Karush, A. (1939): Minima of Functions of Several Variables with Inequalities as Side Constraints,
 * 		M. Sc., University of Chicago, Chicago IL.
 * 
 * 	- Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming, Proceedings of the Second Berkeley
 * 		Symposium, University of California, Berkeley CA 481-492.
 * 
 * 	- Ruszczynski, A. (2006): Nonlinear Optimization, Princeton University Press, Princeton NJ.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OptimizationFramework {
	private org.drip.function.definition.RdToR1 _rdToR1Objective = null;
	private org.drip.function.definition.RdToR1[] _aRdToR1EqualityConstraint = null;
	private org.drip.function.definition.RdToR1[] _aRdToR1InequalityConstraint = null;

	/**
	 * OptimizationFramework Constructor
	 * 
	 * @param rdToR1Objective The R^d To R^1 Objective Function
	 * @param aRdToR1EqualityConstraint The Array of R^d To R^1 Equality Constraint Functions
	 * @param aRdToR1InequalityConstraint The Array of R^d To R^1 Inequality Constraint Functions
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public OptimizationFramework (
		final org.drip.function.definition.RdToR1 rdToR1Objective,
		final org.drip.function.definition.RdToR1[] aRdToR1EqualityConstraint,
		final org.drip.function.definition.RdToR1[] aRdToR1InequalityConstraint)
		throws java.lang.Exception
	{
		if (null == (_rdToR1Objective = rdToR1Objective))
			throw new java.lang.Exception ("OptimizationFramework Constructor => Invalid Inputs");

		int iObjectiveDimension = _rdToR1Objective.dimension();

		if (null != (_aRdToR1EqualityConstraint = aRdToR1EqualityConstraint)) {
			int iNumEqualityConstraint = _aRdToR1EqualityConstraint.length;

			for (int i = 0; i < iNumEqualityConstraint; ++i) {
				if (null == _aRdToR1EqualityConstraint[i] || _aRdToR1EqualityConstraint[i].dimension() !=
					iObjectiveDimension)
					throw new java.lang.Exception ("OptimizationFramework Constructor => Invalid Inputs");
			}
		}

		if (null != (_aRdToR1InequalityConstraint = aRdToR1InequalityConstraint)) {
			int iNumInequalityConstraint = _aRdToR1InequalityConstraint.length;

			for (int i = 0; i < iNumInequalityConstraint; ++i) {
				if (null == _aRdToR1InequalityConstraint[i] || _aRdToR1InequalityConstraint[i].dimension() !=
					iObjectiveDimension)
					throw new java.lang.Exception ("OptimizationFramework Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the R^d To R^1 Objective Function
	 * 
	 * @return The R^d To R^1 Objective Function
	 */

	public org.drip.function.definition.RdToR1 objectiveFunction()
	{
		return _rdToR1Objective;
	}

	/**
	 * Retrieve the Array of R^d To R^1 Equality Constraint Functions
	 * 
	 * @return The Array of R^d To R^1 Equality Constraint Functions
	 */

	public org.drip.function.definition.RdToR1[] equalityConstraint()
	{
		return _aRdToR1EqualityConstraint;
	}

	/**
	 * Retrieve the Array of R^d To R^1 Inequality Constraint Functions
	 * 
	 * @return The Array of R^d To R^1 Inequality Constraint Functions
	 */

	public org.drip.function.definition.RdToR1[] inequalityConstraint()
	{
		return _aRdToR1InequalityConstraint;
	}

	/**
	 * Retrieve the Number of Equality Constraints
	 * 
	 * @return The Number of Equality Constraints
	 */

	public int numEqualityConstraint()
	{
		return null == _aRdToR1EqualityConstraint ? 0 : _aRdToR1EqualityConstraint.length;
	}

	/**
	 * Retrieve the Number of Inequality Constraints
	 * 
	 * @return The Number of Inequality Constraints
	 */

	public int numInequalityConstraint()
	{
		return null == _aRdToR1InequalityConstraint ? 0 : _aRdToR1InequalityConstraint.length;
	}

	/**
	 * Indicate if the Optimizer Framework is Lagrangian
	 * 
	 * @return TRUE - The Optimizer Framework is Lagrangian
	 */

	public boolean isLagrangian()
	{
		return 0 == numInequalityConstraint() && 0 != numEqualityConstraint();
	}

	/**
	 * Indicate if the Optimizer Framework is Unconstrained
	 * 
	 * @return TRUE - The Optimizer Framework is Unconstrained
	 */

	public boolean isUnconstrained()
	{
		return 0 == numInequalityConstraint() && 0 == numEqualityConstraint();
	}

	/**
	 * Indicate if the specified Fritz John Multipliers are compatible with the Optimization Framework
	 * 
	 * @param fjm The specified FJM Multipliers
	 * 
	 * @return TRUE - The specified Fritz John Multipliers are compatible with the Optimization Framework
	 */

	public boolean isCompatible (
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm)
	{
		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		int iNumEqualityFJMMultiplier = null == fjm ? 0 : fjm.numEqualityCoefficients();

		if (iNumEqualityConstraint != iNumEqualityFJMMultiplier) return false;

		int iNumInequalityFJMMultiplier = null == fjm ? 0 : fjm.numInequalityCoefficients();

		return iNumInequalityConstraint == iNumInequalityFJMMultiplier;
	}

	/**
	 * Check the Candidate Point for Primal Feasibility
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - The Candidate Point has passed the Primal Feasibility Test
	 * 
	 * @throws java.lang.Exception Thrown if the Input in Invalid
	 */

	public boolean primalFeasibilityCheck (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (0. != _aRdToR1EqualityConstraint[i].evaluate (adblVariate)) return false;
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. < _aRdToR1InequalityConstraint[i].evaluate (adblVariate)) return false;
		}

		return true;
	}

	/**
	 * Check for Complementary Slackness across the Inequality Constraints
	 * 
	 * @param fjm The specified Fritz John Multipliers
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - The Complementary Slackness Test passed
	 * 
	 * @throws java.lang.Exception Thrown if the Input in Invalid
	 */

	public boolean complementarySlacknessCheck (
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (!isCompatible (fjm))
			throw new java.lang.Exception
				("OptimizationFramework::complementarySlacknessCheck => Invalid Inputs");

		int iNumInequalityConstraint = numInequalityConstraint();

		double[] adblInequalityConstraintCoefficient = null == fjm ? null :
			fjm.inequalityConstraintCoefficient();

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. != _aRdToR1InequalityConstraint[i].evaluate (adblVariate) *
				adblInequalityConstraintCoefficient[i])
				return false;
		}

		return true;
	}

	/**
	 * Check the Candidate Point for First Order Necessary Condition
	 * 
	 * @param fjm The specified Fritz John Multipliers
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - The Candidate Point satisfied the First Order Necessary Condition
	 * 
	 * @throws java.lang.Exception Thrown if the Input in Invalid
	 */

	public boolean isFONC (
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (!isCompatible (fjm))
			throw new java.lang.Exception ("OptimizationFramework::isFONC => Invalid Inputs");

		double[] adblFONCJacobian = _rdToR1Objective.jacobian (adblVariate);

		if (null == adblFONCJacobian)
			throw new java.lang.Exception ("OptimizationFramework::isFONC => Cannot calculate Jacobian");

		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		double[] adblEqualityConstraintCoefficient = null == fjm ? null :
			fjm.equalityConstraintCoefficient();

		double[] adblInequalityConstraintCoefficient = null == fjm ? null :
			fjm.inequalityConstraintCoefficient();

		int iDimension = _rdToR1Objective.dimension();

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			double[] adblJacobian = _aRdToR1EqualityConstraint[i].jacobian (adblVariate);

			if (null == adblJacobian)
				throw new java.lang.Exception ("OptimizationFramework::isFONC => Cannot calculate Jacobian");

			for (int j = 0; j < iDimension; ++j)
				adblFONCJacobian[j] = adblFONCJacobian[j] + adblEqualityConstraintCoefficient[j] *
					adblJacobian[j];
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			double[] adblJacobian = _aRdToR1InequalityConstraint[i].jacobian (adblVariate);

			if (null == adblJacobian)
				throw new java.lang.Exception ("OptimizationFramework::isFONC => Cannot calculate Jacobian");

			for (int j = 0; j < iDimension; ++j)
				adblFONCJacobian[j] = adblFONCJacobian[j] + adblInequalityConstraintCoefficient[j] *
					adblJacobian[j];
		}

		for (int j = 0; j < iDimension; ++j) {
			if (0. != adblFONCJacobian[j]) return false;
		}

		return true;
	}

	/**
	 * Check the Candidate Point for Second Order Sufficiency Condition
	 * 
	 * @param fjm The specified Fritz John Multipliers
	 * @param adblVariate The Candidate R^d Variate
	 * @param bCheckForMinima TRUE - Check whether the R^d Variate corresponds to the SOSC Minimum
	 * 
	 * @return TRUE - The Candidate Point satisfies the Second Order Sufficiency Condition
	 * 
	 * @throws java.lang.Exception Thrown if the Input in Invalid
	 */

	public boolean isSOSC (
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final double[] adblVariate,
		final boolean bCheckForMinima)
		throws java.lang.Exception
	{
		if (!isFONC (fjm, adblVariate)) return false;

		double[][] aadblSOSCHessian = _rdToR1Objective.hessian (adblVariate);

		if (null == aadblSOSCHessian)
			throw new java.lang.Exception ("OptimizationFramework::isSOSC => Cannot calculate Jacobian");

		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		double[] adblEqualityConstraintCoefficient = null == fjm ? null :
			fjm.equalityConstraintCoefficient();

		double[] adblInequalityConstraintCoefficient = null == fjm ? null :
			fjm.inequalityConstraintCoefficient();

		int iDimension = _rdToR1Objective.dimension();

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			double[][] aadblHessian = _aRdToR1EqualityConstraint[i].hessian (adblVariate);

			if (null == aadblHessian)
				throw new java.lang.Exception ("OptimizationFramework::isSOSC => Cannot calculate Jacobian");

			for (int j = 0; j < iDimension; ++j) {
				for (int k = 0; k < iDimension; ++k)
					aadblSOSCHessian[j][k] = aadblSOSCHessian[j][k] + adblEqualityConstraintCoefficient[j] *
						aadblHessian[j][k];
			}
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			double[][] aadblHessian = _aRdToR1InequalityConstraint[i].hessian (adblVariate);

			if (null == aadblHessian)
				throw new java.lang.Exception ("OptimizationFramework::isSOSC => Cannot calculate Jacobian");

			for (int j = 0; j < iDimension; ++j) {
				for (int k = 0; k < iDimension; ++k)
					aadblSOSCHessian[j][k] = aadblSOSCHessian[j][k] + adblInequalityConstraintCoefficient[j]
						* aadblHessian[j][k];
			}
		}

		double dblSOSC = org.drip.quant.linearalgebra.Matrix.DotProduct (adblVariate,
			org.drip.quant.linearalgebra.Matrix.Product (aadblSOSCHessian, adblVariate));

		return (bCheckForMinima && dblSOSC > 0.) || (!bCheckForMinima && dblSOSC < 0.);
	}

	/**
	 * Generate the Battery of Necessary and Sufficient Qualification Tests
	 * 
	 * @param fjm The specified Fritz John Multipliers
	 * @param adblVariate The Candidate R^d Variate
	 * @param bCheckForMinima TRUE - Check whether the R^d Variate corresponds to the SOSC Minimum
	 * 
	 * @return The Necessary and Sufficient Conditions Qualifier Instance
	 */

	public org.drip.optimization.constrained.NecessarySufficientConditions necessarySufficientQualifier (
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final double[] adblVariate,
		final boolean bCheckForMinima)
	{
		try {
			return org.drip.optimization.constrained.NecessarySufficientConditions.Standard (adblVariate, fjm,
				bCheckForMinima, primalFeasibilityCheck (adblVariate), fjm.dualFeasibilityCheck(),
					complementarySlacknessCheck (fjm, adblVariate), isFONC (fjm, adblVariate), isSOSC (fjm,
						adblVariate, bCheckForMinima));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Array of Active Constraints
	 * 
	 * @param adblVariate The R^d Variate
	 * 
	 * @return The Array of Active Constraints
	 */

	public org.drip.function.definition.RdToR1[] activeConstraints (
		final double[] adblVariate)
	{
		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		java.util.List<org.drip.function.definition.RdToR1> lsActiveConstraint = new
			java.util.ArrayList<org.drip.function.definition.RdToR1>();

		for (int i = 0; i < iNumEqualityConstraint; ++i)
			lsActiveConstraint.add (_aRdToR1EqualityConstraint[i]);

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			try {
				if (0. == _aRdToR1InequalityConstraint[i].evaluate (adblVariate))
					lsActiveConstraint.add (_aRdToR1InequalityConstraint[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int iNumActiveConstraint = lsActiveConstraint.size();

		org.drip.function.definition.RdToR1[] aRdToR1ActiveConstraint = new
			org.drip.function.definition.RdToR1[iNumActiveConstraint];

		for (int i = 0; i < iNumActiveConstraint; ++i)
			aRdToR1ActiveConstraint[i] = lsActiveConstraint.get (i);

		return aRdToR1ActiveConstraint;
	}

	/**
	 * Active Constraint Set Rank Computation
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return The Active Constraint Set Rank
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int activeConstraintRank (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		java.util.List<double[]> lsJacobian = new java.util.ArrayList<double[]>();

		double[] adblJacobian = _rdToR1Objective.jacobian (adblVariate);

		if (null == adblJacobian)
			throw new java.lang.Exception ("OptimizationFramework::activeConstraintRank => Cannot Compute");

		lsJacobian.add (adblJacobian);

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (null == (adblJacobian = _aRdToR1EqualityConstraint[i].jacobian (adblVariate)))
				throw new java.lang.Exception
					("OptimizationFramework::activeConstraintRank => Cannot Compute");

			lsJacobian.add (adblJacobian);
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. == _aRdToR1InequalityConstraint[i].evaluate (adblVariate)) {
				if (null == (adblJacobian = _aRdToR1InequalityConstraint[i].jacobian (adblVariate)))
					throw new java.lang.Exception
						("OptimizationFramework::activeConstraintRank => Cannot Compute");

				lsJacobian.add (adblJacobian);
			}
		}

		int iNumJacobian = lsJacobian.size();

		double[][] aadblJacobian = new double[iNumJacobian][];

		for (int i = 0; i < iNumJacobian; ++i)
			aadblJacobian[i] = lsJacobian.get (i);

		return org.drip.quant.linearalgebra.Matrix.Rank (aadblJacobian);
	}

	/**
	 * Compare the Active Constraint Set Rank at the specified against the specified Rank
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * @param iRank The specified Rank
	 * 
	 * @return TRUE - Active Constraint Set Rank matches the specified Rank
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean activeConstraintRankComparison (
		final double[] adblVariate,
		final int iRank)
		throws java.lang.Exception
	{
		return activeConstraintRank (adblVariate) == iRank;
	}

	/**
	 * Active Constraint Set Linear Dependence Check
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * @param bPositiveLinearDependenceCheck TRUE - Perform an Additional Positive Dependence Check
	 * 
	 * @return TRUE - Active Constraint Set Linear Dependence Check is satisfied
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean activeConstraintLinearDependence (
		final double[] adblVariate,
		final boolean bPositiveLinearDependenceCheck)
		throws java.lang.Exception
	{
		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		int iNumConstraint = iNumEqualityConstraint + iNumInequalityConstraint;
		double[][] aadblJacobian = new double[iNumConstraint][];

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (null == (aadblJacobian[i] = _aRdToR1EqualityConstraint[i].jacobian (adblVariate)))
				return false;
		}

		for (int i = iNumEqualityConstraint; i < iNumConstraint; ++i) {
			aadblJacobian[i] = null;
			org.drip.function.definition.RdToR1 rdToR1InequalityConstraint =
				_aRdToR1InequalityConstraint[i - iNumEqualityConstraint];

			if (0. == rdToR1InequalityConstraint.evaluate (adblVariate)) {
				if (null == (aadblJacobian[i] = rdToR1InequalityConstraint.jacobian (adblVariate)))
					return false;
			}
		}

		for (int i = 0; i < iNumConstraint; ++i) {
			if (null != aadblJacobian[i]) {
				for (int j = i + 1; j < iNumConstraint; ++j) {
					if (null != aadblJacobian[j] && 0. != org.drip.quant.linearalgebra.Matrix.DotProduct
						(aadblJacobian[i], aadblJacobian[j]))
						return false;
				}
			}
		}

		if (bPositiveLinearDependenceCheck) {
			for (int i = 0; i < iNumConstraint; ++i) {
				if (null != aadblJacobian[i] &&
					!org.drip.quant.linearalgebra.Matrix.PositiveLinearlyIndependent (aadblJacobian[i]))
					return false;
			}
		}

		return true;
	}

	/**
	 * Compute the Along/Away "Naturally" Incremented Variates
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return The Along/Away "Natural" Incremented Variates
	 */

	public double[][] alongAwayVariate (
		final double[] adblVariate)
	{
		double[] adblVariateIncrement = org.drip.quant.linearalgebra.Matrix.Product
			(org.drip.quant.linearalgebra.Matrix.InvertUsingGaussianElimination (_rdToR1Objective.hessian
				(adblVariate)), _rdToR1Objective.jacobian (adblVariate));

		if (null == adblVariateIncrement) return null;

		int iVariateDimension = adblVariate.length;
		double[] adblVariateAway = new double[iVariateDimension];
		double[] adblVariateAlong = new double[iVariateDimension];

		for (int i = 0; i < iVariateDimension; ++i) {
			adblVariateAway[i] = adblVariate[i] - adblVariateIncrement[i];
			adblVariateAlong[i] = adblVariate[i] + adblVariateIncrement[i];
		}

		return new double[][] {adblVariateAlong, adblVariateAway};
	}

	/**
	 * Check for Linearity Constraint Qualification
	 * 
	 * @return TRUE - Linearity Constraint Qualification is satisfied
	 */

	public boolean isLCQ()
	{
		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (!(_aRdToR1EqualityConstraint[i] instanceof org.drip.function.rdtor1.AffineMultivariate))
				return false;
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (!(_aRdToR1InequalityConstraint[i] instanceof org.drip.function.rdtor1.AffineMultivariate))
				return false;
		}

		return true;
	}

	/**
	 * Check for Linearity Independent Constraint Qualification
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - Linearity Independent Constraint Qualification is satisfied
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean isLICQ (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		return activeConstraintLinearDependence (adblVariate, false);
	}

	/**
	 * Check for Mangasarian Fromovitz Constraint Qualification
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - The Mangasarian Fromovitz Constraint Qualification is satisfied
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean isMFCQ (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		return activeConstraintLinearDependence (adblVariate, true);
	}

	/**
	 * Check for Constant Rank Constraint Qualification
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - The Constant Rank Constraint Qualification is satisfied
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean isCRCQ (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		int iRank = activeConstraintRank (adblVariate);

		double[][] aadblAlongAwayVariatePair = alongAwayVariate (adblVariate);

		if (null == aadblAlongAwayVariatePair)
			throw new java.lang.Exception ("OptimizationFramework::isCRCQ => Cannot generate along/away");

		return iRank == activeConstraintRank (aadblAlongAwayVariatePair[0]) && iRank == activeConstraintRank
			(aadblAlongAwayVariatePair[1]);
	}

	/**
	 * Check for Constant Positive Linear Dependence Constraint Qualification
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - The Constant Positive Linear Dependence Constraint Qualification is satisfied
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean isCPLDCQ (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (!isMFCQ (adblVariate)) return false;

		double[][] aadblAlongAwayVariatePair = alongAwayVariate (adblVariate);

		if (null == aadblAlongAwayVariatePair)
			throw new java.lang.Exception ("OptimizationFramework::isCPLDCQ => Cannot generate along/away");

		return isMFCQ (aadblAlongAwayVariatePair[0]) && isMFCQ (aadblAlongAwayVariatePair[1]);
	}

	/**
	 * Check for Quasi Normal Constraint Qualification
	 * 
	 * @param fjm The specified Fritz John Multipliers
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - The Quasi Normal Constraint Qualification is satisfied
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean isQNCQ (
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (!isCompatible (fjm))
			throw new java.lang.Exception ("OptimizationFramework::isQNCQ => Invalid Inputs");

		if (!isMFCQ (adblVariate)) return false;

		int iNumEqualityConstraint = numEqualityConstraint();

		double[] adblEqualityConstraintCoefficient = null == fjm ? null :
			fjm.equalityConstraintCoefficient();

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (0. != adblEqualityConstraintCoefficient[i] && 0. <= _aRdToR1EqualityConstraint[i].evaluate
				(adblVariate) * adblEqualityConstraintCoefficient[i])
				return false;
		}

		int iNumInequalityConstraint = numInequalityConstraint();

		double[] adblInequalityConstraintCoefficient = null == fjm ? null :
			fjm.inequalityConstraintCoefficient();

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. != adblInequalityConstraintCoefficient[i] && 0. <=
				_aRdToR1InequalityConstraint[i].evaluate (adblVariate) *
					adblInequalityConstraintCoefficient[i])
				return false;
		}

		return true;
	}

	/**
	 * Check for Slater Condition Constraint Qualification
	 * 
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return TRUE - The Slater Condition Constraint Qualification is satisfied
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean isSCCQ (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (!(_rdToR1Objective instanceof org.drip.function.rdtor1.ConvexMultivariate)) return false;

		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (0. != _aRdToR1EqualityConstraint[i].evaluate (adblVariate)) return false;
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. <= _aRdToR1InequalityConstraint[i].evaluate (adblVariate)) return false;
		}

		return true;
	}

	/**
	 * Generate the Battery of Regularity Constraint Qualification Tests
	 * 
	 * @param fjm The specified Fritz John Multipliers
	 * @param adblVariate The Candidate R^d Variate
	 * 
	 * @return The Regularity Constraint Qualifier Instance
	 */

	public org.drip.optimization.constrained.RegularityConditions regularityQualifier (
		final org.drip.optimization.constrained.FritzJohnMultipliers fjm,
		final double[] adblVariate)
	{
		try {
			return org.drip.optimization.constrained.RegularityConditions.Standard (adblVariate, fjm,
				isLCQ(), isLICQ (adblVariate), isMFCQ (adblVariate), isCRCQ (adblVariate), isCPLDCQ
					(adblVariate), isQNCQ (fjm, adblVariate), isSCCQ (adblVariate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
