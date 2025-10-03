
package org.drip.optimization.constrained;

import org.drip.function.definition.RdToR1;
import org.drip.numerical.linearalgebra.R1MatrixUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>OptimizerFramework</i> holds the Non Linear Objective Function and the Collection of Equality and the
 * 	Inequality Constraints that correspond to the Optimization Setup. It provides the following Functions:
 * 	<ul>
 * 		<li>Create a Standard Instance of <i>NecessarySufficientConditions</i></li>
 * 	</ul>
 * 
 * The References are:
 * 	<br>
 * 	<ul>
 * 		<li>
 * 			Boyd, S., and L. van den Berghe (2009): <i>Convex Optimization</i> <b>Cambridge University
 * 				Press</b> Cambridge UK
 * 		</li>
 * 		<li>
 * 			Eustaquio, R., E. Karas, and A. Ribeiro (2008): <i>Constraint Qualification for Nonlinear
 * 				Programming</i> <b>Federal University of Parana</b>
 * 		</li>
 * 		<li>
 * 			Karush, A. (1939): <i>Minima of Functions of Several Variables with Inequalities as Side
 * 			Constraints</i> <b>University of Chicago</b> Chicago IL
 * 		</li>
 * 		<li>
 * 			Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming <i>Proceedings of the Second Berkeley
 * 				Symposium</i> <b>University of California</b> Berkeley CA 481-492
 * 		</li>
 * 		<li>
 * 			Ruszczynski, A. (2006): <i>Nonlinear Optimization</i> <b>Princeton University Press</b> Princeton
 * 				NJ
 * 		</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/constrained/README.md">KKT Fritz-John Constrained Optimizer</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OptimizerFramework
{
	private RdToR1 _objectiveFunction = null;
	private RdToR1[] _equalityConstraintArray = null;
	private RdToR1[] _inequalityConstraintArray = null;

	/**
	 * <i>OptimizerFramework</i> Constructor
	 * 
	 * @param objectiveFunction R<sup>d</sup> To R<sup>1</sup> Objective Function
	 * @param equalityConstraintArray Array of R<sup>d</sup> To R<sup>1</sup> Equality Constraint Functions
	 * @param inequalityConstraintArray Array of R<sup>d</sup> To R<sup>1</sup> Inequality Constraint
	 * 	Functions
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public OptimizerFramework (
		final RdToR1 objectiveFunction,
		final RdToR1[] equalityConstraintArray,
		final RdToR1[] inequalityConstraintArray)
		throws Exception
	{
		if (null == (_objectiveFunction = objectiveFunction)) {
			throw new Exception ("OptimizerFramework Constructor => Invalid Inputs");
		}

		int objectiveFunctionDimension = _objectiveFunction.dimension();

		if (null != (_equalityConstraintArray = equalityConstraintArray)) {
			for (int equalityConstraintIndex = 0;
				equalityConstraintIndex < _equalityConstraintArray.length;
				++equalityConstraintIndex)
			{
				if (null == _equalityConstraintArray[equalityConstraintIndex] ||
					_equalityConstraintArray[equalityConstraintIndex].dimension() !=
						objectiveFunctionDimension)
				{
					throw new Exception ("OptimizerFramework Constructor => Invalid Inputs");
				}
			}
		}

		if (null != (_inequalityConstraintArray = inequalityConstraintArray)) {
			for (int inequalityConstraintIndex = 0;
				inequalityConstraintIndex < _inequalityConstraintArray.length;
				++inequalityConstraintIndex)
			{
				if (null == _inequalityConstraintArray[inequalityConstraintIndex] ||
					_inequalityConstraintArray[inequalityConstraintIndex].dimension() !=
						objectiveFunctionDimension)
				{
					throw new Exception ("OptimizerFramework Constructor => Invalid Inputs");
				}
			}
		}
	}

	/**
	 * Retrieve the R<sup>d</sup> To R<sup>1</sup> Objective Function
	 * 
	 * @return The R<sup>d</sup> To R<sup>1</sup> Objective Function
	 */

	public RdToR1 objectiveFunction()
	{
		return _objectiveFunction;
	}

	/**
	 * Retrieve the Array of R<sup>d</sup> To R<sup>1</sup> Equality Constraint Functions
	 * 
	 * @return The Array of R<sup>d</sup> To R<sup>1</sup> Equality Constraint Functions
	 */

	public RdToR1[] equalityConstraintArray()
	{
		return _equalityConstraintArray;
	}

	/**
	 * Retrieve the Array of R<sup>d</sup> To R<sup>1</sup> Inequality Constraint Functions
	 * 
	 * @return The Array of R<sup>d</sup> To R<sup>1</sup> Inequality Constraint Functions
	 */

	public RdToR1[] inequalityConstraintArray()
	{
		return _inequalityConstraintArray;
	}

	/**
	 * Retrieve the Number of Equality Constraints
	 * 
	 * @return The Number of Equality Constraints
	 */

	public int numEqualityConstraint()
	{
		return null == _equalityConstraintArray ? 0 : _equalityConstraintArray.length;
	}

	/**
	 * Retrieve the Number of Inequality Constraints
	 * 
	 * @return The Number of Inequality Constraints
	 */

	public int numInequalityConstraint()
	{
		return null == _inequalityConstraintArray ? 0 : _inequalityConstraintArray.length;
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
	 * @param fritzJohnMultipliers The specified FJM Multipliers
	 * 
	 * @return TRUE - The specified Fritz John Multipliers are compatible with the Optimization Framework
	 */

	public boolean isCompatible (
		final FritzJohnMultipliers fritzJohnMultipliers)
	{
		int equalityConstraintCount = numEqualityConstraint();

		int inequalityConstraintCount = numInequalityConstraint();

		int equalityFJMMultiplier = null == fritzJohnMultipliers ?
			0 : fritzJohnMultipliers.numEqualityCoefficients();

		if (equalityConstraintCount != equalityFJMMultiplier) {
			return false;
		}

		int inequalityFJMMultiplier = null == fritzJohnMultipliers ?
			0 : fritzJohnMultipliers.numInequalityCoefficients();

		return inequalityConstraintCount == inequalityFJMMultiplier;
	}

	/**
	 * Check the Candidate Point for Primal Feasibility
	 * 
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * 
	 * @return TRUE - The Candidate Point has passed the Primal Feasibility Test
	 * 
	 * @throws Exception Thrown if the Input in Invalid
	 */

	public boolean primalFeasibilityCheck (
		final double[] variateArray)
		throws Exception
	{
		for (int equalityConstraintIndex = 0;
			equalityConstraintIndex < numEqualityConstraint();
			++equalityConstraintIndex)
		{
			if (0. != _equalityConstraintArray[equalityConstraintIndex].evaluate (variateArray)) {
				return false;
			}
		}

		for (int inequalityConstraintIndex = 0;
			inequalityConstraintIndex < numInequalityConstraint();
			++inequalityConstraintIndex)
		{
			if (0. < _inequalityConstraintArray[inequalityConstraintIndex].evaluate (variateArray)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check for Complementary Slackness across the Inequality Constraints
	 * 
	 * @param fritzJohnMultipliers The specified Fritz John Multipliers
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * 
	 * @return TRUE - The Complementary Slackness Test passed
	 * 
	 * @throws Exception Thrown if the Input in Invalid
	 */

	public boolean complementarySlacknessCheck (
		final FritzJohnMultipliers fritzJohnMultipliers,
		final double[] variateArray)
		throws Exception
	{
		if (!isCompatible (fritzJohnMultipliers)) {
			throw new Exception ("OptimizerFramework::complementarySlacknessCheck => Invalid Inputs");
		}

		double[] inequalityConstraintCoefficientArray = null == fritzJohnMultipliers ? null :
			fritzJohnMultipliers.inequalityConstraintCoefficientArray();

		for (int inequalityConstraintIndex = 0;
			inequalityConstraintIndex < numInequalityConstraint();
			++inequalityConstraintIndex)
		{
			if (0. != _inequalityConstraintArray[inequalityConstraintIndex].evaluate (variateArray) *
				inequalityConstraintCoefficientArray[inequalityConstraintIndex])
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Check the Candidate Point for First Order Necessary Condition
	 * 
	 * @param fritzJohnMultipliers The specified Fritz John Multipliers
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * 
	 * @return TRUE - The Candidate Point satisfied the First Order Necessary Condition
	 * 
	 * @throws Exception Thrown if the Input in Invalid
	 */

	public boolean isFONC (
		final FritzJohnMultipliers fritzJohnMultipliers,
		final double[] variateArray)
		throws Exception
	{
		if (!isCompatible (fritzJohnMultipliers)) {
			throw new Exception ("OptimizerFramework::isFONC => Invalid Inputs");
		}

		double[] foncJacobian = _objectiveFunction.jacobian (variateArray);

		if (null == foncJacobian) {
			throw new Exception ("OptimizerFramework::isFONC => Cannot calculate Jacobian");
		}

		double[] equalityConstraintCoefficientArray = null == fritzJohnMultipliers ? null :
			fritzJohnMultipliers.equalityConstraintCoefficientArray();

		double[] inequalityConstraintCoefficientArray = null == fritzJohnMultipliers ? null :
			fritzJohnMultipliers.inequalityConstraintCoefficientArray();

		int dimension = _objectiveFunction.dimension();

		for (int equalityConstraintIndex = 0;
			equalityConstraintIndex < numEqualityConstraint();
			++equalityConstraintIndex)
		{
			double[] jacobian = _equalityConstraintArray[equalityConstraintIndex].jacobian (variateArray);

			if (null == jacobian) {
				throw new Exception ("OptimizerFramework::isFONC => Cannot calculate Jacobian");
			}

			for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
				foncJacobian[dimensionIndex] +=
					equalityConstraintCoefficientArray[dimensionIndex] * jacobian[dimensionIndex];
			}
		}

		for (int inequalityConstraintIndex = 0;
			inequalityConstraintIndex < numInequalityConstraint();
			++inequalityConstraintIndex)
		{
			double[] jacobian =
				_inequalityConstraintArray[inequalityConstraintIndex].jacobian (variateArray);

			if (null == jacobian) {
				throw new Exception ("OptimizerFramework::isFONC => Cannot calculate Jacobian");
			}

			for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
				foncJacobian[dimensionIndex] +=
					inequalityConstraintCoefficientArray[dimensionIndex] * jacobian[dimensionIndex];
			}
		}

		for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
			if (0. != foncJacobian[dimensionIndex]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check the Candidate Point for Second Order Sufficiency Condition
	 * 
	 * @param fritzJohnMultipliers The specified Fritz John Multipliers
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * @param checkForMinima TRUE - Check whether the R<sup>d</sup> Variate corresponds to the SOSC Minimum
	 * 
	 * @return TRUE - The Candidate Point satisfies the Second Order Sufficiency Condition
	 * 
	 * @throws Exception Thrown if the Input in Invalid
	 */

	public boolean isSOSC (
		final FritzJohnMultipliers fritzJohnMultipliers,
		final double[] variateArray,
		final boolean checkForMinima)
		throws Exception
	{
		if (!isFONC (fritzJohnMultipliers, variateArray)) {
			return false;
		}

		double[][] soscHessian = _objectiveFunction.hessian (variateArray);

		if (null == soscHessian) {
			throw new Exception ("OptimizerFramework::isSOSC => Cannot calculate Jacobian");
		}

		double[] equalityConstraintCoefficientArray = null == fritzJohnMultipliers ? null :
			fritzJohnMultipliers.equalityConstraintCoefficientArray();

		double[] inequalityConstraintCoefficientArray = null == fritzJohnMultipliers ? null :
			fritzJohnMultipliers.inequalityConstraintCoefficientArray();

		int dimension = _objectiveFunction.dimension();

		for (int equalityConstraintIndex = 0;
			equalityConstraintIndex < numEqualityConstraint();
			++equalityConstraintIndex)
		{
			double[][] hessian = _equalityConstraintArray[equalityConstraintIndex].hessian (variateArray);

			if (null == hessian) {
				throw new Exception ("OptimizerFramework::isSOSC => Cannot calculate Jacobian");
			}

			for (int dimensionIndexJ = 0; dimensionIndexJ < dimension; ++dimensionIndexJ) {
				for (int dimensionIndexK = 0; dimensionIndexK < dimension; ++dimensionIndexK) {
					soscHessian[dimensionIndexJ][dimensionIndexK] +=
						equalityConstraintCoefficientArray[dimensionIndexJ] *
						hessian[dimensionIndexJ][dimensionIndexK];
				}
			}
		}

		for (int inequalityConstraintIndex = 0;
			inequalityConstraintIndex < numInequalityConstraint();
			++inequalityConstraintIndex)
		{
			double[][] hessian =
				_inequalityConstraintArray[inequalityConstraintIndex].hessian (variateArray);

			if (null == hessian) {
				throw new Exception ("OptimizerFramework::isSOSC => Cannot calculate Jacobian");
			}

			for (int dimensionIndexJ = 0; dimensionIndexJ < dimension; ++dimensionIndexJ) {
				for (int dimensionIndexK = 0; dimensionIndexK < dimension; ++dimensionIndexK) {
					soscHessian[dimensionIndexJ][dimensionIndexK] +=
						inequalityConstraintCoefficientArray[dimensionIndexJ] *
						hessian[dimensionIndexJ][dimensionIndexK];
				}
			}
		}

		double sosc = R1MatrixUtil.DotProduct (
			variateArray,
			R1MatrixUtil.Product (soscHessian, variateArray)
		);

		return (checkForMinima && 0. < sosc) || (!checkForMinima && 0. > sosc);
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
			lsActiveConstraint.add (_equalityConstraintArray[i]);

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			try {
				if (0. == _inequalityConstraintArray[i].evaluate (adblVariate))
					lsActiveConstraint.add (_inequalityConstraintArray[i]);
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

		double[] adblJacobian = _objectiveFunction.jacobian (adblVariate);

		if (null == adblJacobian)
			throw new java.lang.Exception ("OptimizerFramework::activeConstraintRank => Cannot Compute");

		lsJacobian.add (adblJacobian);

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (null == (adblJacobian = _equalityConstraintArray[i].jacobian (adblVariate)))
				throw new java.lang.Exception
					("OptimizerFramework::activeConstraintRank => Cannot Compute");

			lsJacobian.add (adblJacobian);
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. == _inequalityConstraintArray[i].evaluate (adblVariate)) {
				if (null == (adblJacobian = _inequalityConstraintArray[i].jacobian (adblVariate)))
					throw new java.lang.Exception
						("OptimizerFramework::activeConstraintRank => Cannot Compute");

				lsJacobian.add (adblJacobian);
			}
		}

		int iNumJacobian = lsJacobian.size();

		double[][] aadblJacobian = new double[iNumJacobian][];

		for (int i = 0; i < iNumJacobian; ++i)
			aadblJacobian[i] = lsJacobian.get (i);

		return org.drip.numerical.linearalgebra.R1MatrixUtil.Rank (aadblJacobian);
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
			if (null == (aadblJacobian[i] = _equalityConstraintArray[i].jacobian (adblVariate)))
				return false;
		}

		for (int i = iNumEqualityConstraint; i < iNumConstraint; ++i) {
			aadblJacobian[i] = null;
			org.drip.function.definition.RdToR1 rdToR1InequalityConstraint =
				_inequalityConstraintArray[i - iNumEqualityConstraint];

			if (0. == rdToR1InequalityConstraint.evaluate (adblVariate)) {
				if (null == (aadblJacobian[i] = rdToR1InequalityConstraint.jacobian (adblVariate)))
					return false;
			}
		}

		for (int i = 0; i < iNumConstraint; ++i) {
			if (null != aadblJacobian[i]) {
				for (int j = i + 1; j < iNumConstraint; ++j) {
					if (null != aadblJacobian[j] && 0. != org.drip.numerical.linearalgebra.R1MatrixUtil.DotProduct
						(aadblJacobian[i], aadblJacobian[j]))
						return false;
				}
			}
		}

		if (bPositiveLinearDependenceCheck) {
			for (int i = 0; i < iNumConstraint; ++i) {
				if (null != aadblJacobian[i] &&
					!org.drip.numerical.linearalgebra.R1MatrixUtil.PositiveLinearlyIndependent (aadblJacobian[i]))
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
		double[] adblVariateIncrement = org.drip.numerical.linearalgebra.R1MatrixUtil.Product
			(org.drip.numerical.linearalgebra.R1MatrixUtil.InvertUsingGaussianElimination (_objectiveFunction.hessian
				(adblVariate)), _objectiveFunction.jacobian (adblVariate));

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
			if (!(_equalityConstraintArray[i] instanceof org.drip.function.rdtor1.AffineMultivariate))
				return false;
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (!(_inequalityConstraintArray[i] instanceof org.drip.function.rdtor1.AffineMultivariate))
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
			throw new java.lang.Exception ("OptimizerFramework::isCRCQ => Cannot generate along/away");

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
			throw new java.lang.Exception ("OptimizerFramework::isCPLDCQ => Cannot generate along/away");

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
			throw new java.lang.Exception ("OptimizerFramework::isQNCQ => Invalid Inputs");

		if (!isMFCQ (adblVariate)) return false;

		int iNumEqualityConstraint = numEqualityConstraint();

		double[] adblEqualityConstraintCoefficient = null == fjm ? null :
			fjm.equalityConstraintCoefficientArray();

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (0. != adblEqualityConstraintCoefficient[i] && 0. <= _equalityConstraintArray[i].evaluate
				(adblVariate) * adblEqualityConstraintCoefficient[i])
				return false;
		}

		int iNumInequalityConstraint = numInequalityConstraint();

		double[] adblInequalityConstraintCoefficient = null == fjm ? null :
			fjm.inequalityConstraintCoefficientArray();

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. != adblInequalityConstraintCoefficient[i] && 0. <=
				_inequalityConstraintArray[i].evaluate (adblVariate) *
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
		if (!(_objectiveFunction instanceof org.drip.function.rdtor1.ConvexMultivariate)) return false;

		int iNumEqualityConstraint = numEqualityConstraint();

		int iNumInequalityConstraint = numInequalityConstraint();

		for (int i = 0; i < iNumEqualityConstraint; ++i) {
			if (0. != _equalityConstraintArray[i].evaluate (adblVariate)) return false;
		}

		for (int i = 0; i < iNumInequalityConstraint; ++i) {
			if (0. <= _inequalityConstraintArray[i].evaluate (adblVariate)) return false;
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
