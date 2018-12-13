
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>InteriorFixedPointFinder</i> generates the Iterators for solving R<sup>d</sup> To R<sup>1</sup>
 * Convex/Non-Convex Functions Under Inequality Constraints loaded using a Barrier Coefficient.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1solver/README.md">R<sup>d</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InteriorFixedPointFinder extends org.drip.function.rdtor1solver.FixedRdFinder {
	private double _dblBarrierStrength = java.lang.Double.NaN;
	private org.drip.function.rdtor1.BoundMultivariate[] _aBM = null;
	private org.drip.function.definition.RdToR1[] _aRdToR1InequalityConstraint = null;

	private org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier incremental (
		final org.drip.function.rdtor1solver.ObjectiveFunctionPointMetrics ofpm,
		final org.drip.function.rdtor1solver.ConstraintFunctionPointMetrics cfpmInequality)
	{
		if (null == ofpm || null == cfpmInequality) return null;

		int iDimension = ofpm.dimension();

		int iNumInequalityConstraint = cfpmInequality.count();

		double[] adblObjectiveFunctionJacobian = ofpm.jacobian();

		double[][] aadblObjectiveFunctionHessian = ofpm.hessian();

		double[] adblInequalityConstraintMultiplierIncrement = new double[iNumInequalityConstraint];
		double[] adblVariateIncrement = new double[iDimension];
		int iMSize = iDimension + iNumInequalityConstraint;
		double[][] aadblM = new double[iMSize][iMSize];
		double[] adblRHS = new double[iMSize];

		if (0 == iDimension || iDimension != cfpmInequality.dimension()) return null;

		double[] adblInequalityConstraintMultiplier = cfpmInequality.multiplier();

		double[][] aadblInequalityConstraintJacobian = cfpmInequality.jacobian();

		double[] adblInequalityConstraintValue = cfpmInequality.value();

		for (int i = 0; i < iDimension; ++i) {
			for (int j = 0; j < iDimension; ++j)
				aadblM[i][j] = aadblObjectiveFunctionHessian[i][j];

			for (int j = 0; j < iNumInequalityConstraint; ++j)
				aadblM[i][j + iDimension] = -1. * aadblInequalityConstraintJacobian[i][j];
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			for (int j = 0; j < iNumInequalityConstraint; ++j)
				aadblM[i + iDimension][j + iDimension] = i == j ? adblInequalityConstraintValue[i] : 0.;

			for (int j = 0; j < iDimension; ++j)
				aadblM[i + iDimension][j] = adblInequalityConstraintMultiplier[i] *
					aadblInequalityConstraintJacobian[j][i];
		}

		for (int i = 0; i < iMSize; ++i) {
			if (i < iDimension) {
				adblRHS[i] = -1. * adblObjectiveFunctionJacobian[i];

				for (int j = 0; j < iNumInequalityConstraint; ++j)
					adblRHS[i] += aadblInequalityConstraintJacobian[i][j] *
						adblInequalityConstraintMultiplier[j];
			} else {
				int iConstraintIndex = i - iDimension;
				adblRHS[i] = _dblBarrierStrength - adblInequalityConstraintValue[iConstraintIndex] *
					adblInequalityConstraintMultiplier[iConstraintIndex];
			}
		}

		org.drip.quant.linearalgebra.LinearizationOutput lo =
			org.drip.quant.linearalgebra.LinearSystemSolver.SolveUsingMatrixInversion (aadblM, adblRHS);

		if (null == lo) return null;

		double[] adblIncrement = lo.getTransformedRHS();

		if (null == adblIncrement || adblIncrement.length != iMSize) return null;

		for (int i = 0; i < iMSize; ++i) {
			if (i < iDimension)
				adblVariateIncrement[i] = adblIncrement[i];
			else
				adblInequalityConstraintMultiplierIncrement[i - iDimension] = adblIncrement[i];
		}

		try {
			return new org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier (true,
				adblVariateIncrement, adblInequalityConstraintMultiplierIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * InteriorFixedPointFinder Constructor
	 * 
	 * @param rdToR1ObjectiveFunction The Objective Function
	 * @param aRdToR1InequalityConstraint Array of Inequality Constraints
	 * @param lsec The Line Step Evolution Control
	 * @param cc Convergence Control Parameters
	 * @param dblBarrierStrength Barrier Strength
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InteriorFixedPointFinder (
		final org.drip.function.definition.RdToR1 rdToR1ObjectiveFunction,
		final org.drip.function.definition.RdToR1[] aRdToR1InequalityConstraint,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lsec,
		final org.drip.function.rdtor1solver.ConvergenceControl cc,
		final double dblBarrierStrength)
		throws java.lang.Exception
	{
		super (rdToR1ObjectiveFunction, lsec, cc);

		if (null == (_aRdToR1InequalityConstraint = aRdToR1InequalityConstraint) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBarrierStrength = dblBarrierStrength))
			throw new java.lang.Exception ("InteriorFixedPointFinder Constructor => Invalid Inputs");

		int iNumInequalityConstraint = _aRdToR1InequalityConstraint.length;
		_aBM = 0 == iNumInequalityConstraint ? null : new
			org.drip.function.rdtor1.BoundMultivariate[iNumInequalityConstraint];

		if (0 == iNumInequalityConstraint)
			throw new java.lang.Exception ("InteriorFixedPointFinder Constructor => Invalid Inputs");

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (null == _aRdToR1InequalityConstraint[i])
				throw new java.lang.Exception ("InteriorFixedPointFinder Constructor => Invalid Inputs");

			if (_aRdToR1InequalityConstraint[i] instanceof org.drip.function.rdtor1.BoundMultivariate)
				_aBM[i] = (org.drip.function.rdtor1.BoundMultivariate) _aRdToR1InequalityConstraint[i];
		}
	}

	/**
	 * Retrieve the Array of Inequality Constraints
	 * 
	 * @return The Array of Inequality Constraints
	 */

	public org.drip.function.definition.RdToR1[] inequalityConstraints()
	{
		return _aRdToR1InequalityConstraint;
	}

	/**
	 * Retrieve the Barrier Strength
	 * 
	 * @return The Barrier Strength
	 */

	public double barrierStrength()
	{
		return _dblBarrierStrength;
	}

	@Override public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier increment (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmCurrent)
	{
		if (null == vicmCurrent) return null;

		double[] adblVariate = vicmCurrent.variates();

		int iNumVariate = adblVariate.length;
		int iNumInequalityConstraint = _aRdToR1InequalityConstraint.length;
		double[] adblInequalityConstraintValue = new double[iNumInequalityConstraint];
		double[][] aadblInequalityConstraintJacobian = new double[iNumVariate][iNumInequalityConstraint];

		if (0 == iNumInequalityConstraint) return null;

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			try {
				adblInequalityConstraintValue[i] = _aRdToR1InequalityConstraint[i].evaluate (adblVariate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			double[] adblInequalityConstraintJacobian = _aRdToR1InequalityConstraint[i].jacobian
				(adblVariate);

			if (null == adblInequalityConstraintJacobian) return null;

			for (int j = 0; j < iNumVariate; ++j)
				aadblInequalityConstraintJacobian[j][i] = adblInequalityConstraintJacobian[j];
		}

		org.drip.function.definition.RdToR1 rdToR1ObjectiveFunction = objectiveFunction();

		try {
			return incremental (new org.drip.function.rdtor1solver.ObjectiveFunctionPointMetrics
				(rdToR1ObjectiveFunction.jacobian (adblVariate), rdToR1ObjectiveFunction.hessian
					(adblVariate)), new org.drip.function.rdtor1solver.ConstraintFunctionPointMetrics
						(adblInequalityConstraintValue, aadblInequalityConstraintJacobian,
							vicmCurrent.constraintMultipliers()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier next (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vcmtCurrent,
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vcmtIncrement,
		final double dblIncrementFraction)
	{
		return org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier.Add (vcmtCurrent,
			vcmtIncrement, dblIncrementFraction, _aBM);
	}
}
