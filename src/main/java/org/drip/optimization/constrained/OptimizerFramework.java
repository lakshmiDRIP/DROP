
package org.drip.optimization.constrained;

import java.util.ArrayList;
import java.util.List;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1.AffineMultivariate;
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
	 * @param fritzJohnMultipliers The specified Fritz John Multipliers
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * @param checkForMinima TRUE - Check whether the R<sup>d</sup> Variate corresponds to the SOSC Minimum
	 * 
	 * @return The Necessary and Sufficient Conditions Qualifier Instance
	 */

	public NecessarySufficientConditions necessarySufficientQualifier (
		final FritzJohnMultipliers fritzJohnMultipliers,
		final double[] variateArray,
		final boolean checkForMinima)
	{
		try {
			return NecessarySufficientConditions.Standard (
				variateArray,
				fritzJohnMultipliers,
				checkForMinima,
				primalFeasibilityCheck (variateArray),
				fritzJohnMultipliers.dualFeasibilityCheck(),
				complementarySlacknessCheck (fritzJohnMultipliers, variateArray),
				isFONC (fritzJohnMultipliers, variateArray),
				isSOSC (fritzJohnMultipliers, variateArray, checkForMinima)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Array of Active Constraints
	 * 
	 * @param variateArray The R<sup>d</sup> Variate
	 * 
	 * @return The Array of Active Constraints
	 */

	public RdToR1[] activeConstraints (
		final double[] variateArray)
	{
		List<RdToR1> activeConstraintList = new ArrayList<RdToR1>();

		for (int equalityConstraintIndex = 0;
			equalityConstraintIndex < numEqualityConstraint();
			++equalityConstraintIndex)
		{
			activeConstraintList.add (_equalityConstraintArray[equalityConstraintIndex]);
		}

		for (int inequalityConstraintIndex = 0;
			inequalityConstraintIndex < numInequalityConstraint();
			++inequalityConstraintIndex)
		{
			try {
				if (0. == _inequalityConstraintArray[inequalityConstraintIndex].evaluate (variateArray)) {
					activeConstraintList.add (_inequalityConstraintArray[inequalityConstraintIndex]);
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		int activeConstraintCount = activeConstraintList.size();

		RdToR1[] activeConstraintRdToR1Array = new RdToR1[activeConstraintCount];

		for (int activeConstraintIndex = 0;
			activeConstraintIndex < activeConstraintCount;
			++activeConstraintIndex)
		{
			activeConstraintRdToR1Array[activeConstraintIndex] =
				activeConstraintList.get (activeConstraintIndex);
		}

		return activeConstraintRdToR1Array;
	}

	/**
	 * Active Constraint Set Rank Computation
	 * 
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * 
	 * @return The Active Constraint Set Rank
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public int activeConstraintRank (
		final double[] variateArray)
		throws Exception
	{
		List<double[]> jacobianList = new ArrayList<double[]>();

		double[] jacobian = _objectiveFunction.jacobian (variateArray);

		if (null == jacobian) {
			throw new Exception ("OptimizerFramework::activeConstraintRank => Cannot Compute");
		}

		jacobianList.add (jacobian);

		for (int equalityConstraintIndex = 0;
			equalityConstraintIndex < numEqualityConstraint();
			++equalityConstraintIndex)
		{
			if (null == (
				jacobian = _equalityConstraintArray[equalityConstraintIndex].jacobian (variateArray)
			))
			{
				throw new Exception ("OptimizerFramework::activeConstraintRank => Cannot Compute");
			}

			jacobianList.add (jacobian);
		}

		for (int inequalityConstraintIndex = 0;
			inequalityConstraintIndex < numInequalityConstraint();
			++inequalityConstraintIndex)
		{
			if (0. == _inequalityConstraintArray[inequalityConstraintIndex].evaluate (variateArray)) {
				if (null == (
					jacobian = _inequalityConstraintArray[inequalityConstraintIndex].jacobian (variateArray)
				))
				{
					throw new Exception ("OptimizerFramework::activeConstraintRank => Cannot Compute");
				}

				jacobianList.add (jacobian);
			}
		}

		int jacobianCount = jacobianList.size();

		double[][] jacobianArray = new double[jacobianCount][];

		for (int jacobianIndex = 0; jacobianIndex < jacobianCount; ++jacobianIndex) {
			jacobianArray[jacobianIndex] = jacobianList.get (jacobianIndex);
		}

		return R1MatrixUtil.Rank (jacobianArray);
	}

	/**
	 * Compare the Active Constraint Set Rank at the specified against the specified Rank
	 * 
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * @param rank The specified Rank
	 * 
	 * @return TRUE - Active Constraint Set Rank matches the specified Rank
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public boolean activeConstraintRankComparison (
		final double[] variateArray,
		final int rank)
		throws Exception
	{
		return rank == activeConstraintRank (variateArray);
	}

	/**
	 * Active Constraint Set Linear Dependence Check
	 * 
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * @param positiveLinearDependenceCheck TRUE - Perform an Additional Positive Dependence Check
	 * 
	 * @return TRUE - Active Constraint Set Linear Dependence Check is satisfied
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public boolean activeConstraintLinearDependence (
		final double[] variateArray,
		final boolean positiveLinearDependenceCheck)
		throws Exception
	{
		int equalityConstraintCount = numEqualityConstraint();

		int constraintCount = equalityConstraintCount + numInequalityConstraint();

		double[][] jacobianArray = new double[constraintCount][];

		for (int equalityConstraintIndex = 0;
			equalityConstraintIndex < equalityConstraintCount;
			++equalityConstraintIndex)
		{
			if (null == (
				jacobianArray[equalityConstraintIndex] =
					_equalityConstraintArray[equalityConstraintIndex].jacobian (variateArray)
			))
			{
				return false;
			}
		}

		for (int inequalityConstraintIndex = equalityConstraintCount;
			inequalityConstraintIndex < constraintCount;
			++inequalityConstraintIndex)
		{
			jacobianArray[inequalityConstraintIndex] = null;
			RdToR1 inequalityConstraint =
				_inequalityConstraintArray[inequalityConstraintIndex - equalityConstraintCount];

			if (0. == inequalityConstraint.evaluate (variateArray)) {
				if (null == (
					jacobianArray[inequalityConstraintIndex] = inequalityConstraint.jacobian (variateArray)
				))
				{
					return false;
				}
			}
		}

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			if (null != jacobianArray[constraintIndex]) {
				for (int constraintIndexJ = constraintIndex + 1;
					constraintIndexJ < constraintCount;
					++constraintIndexJ)
				{
					if (null != jacobianArray[constraintIndexJ] &&
						0. != R1MatrixUtil.DotProduct (
							jacobianArray[constraintIndex],
							jacobianArray[constraintIndexJ]
						)
					)
					{
						return false;
					}
				}
			}
		}

		if (positiveLinearDependenceCheck) {
			for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
				if (null != jacobianArray[constraintIndex] &&
					!R1MatrixUtil.PositiveLinearlyIndependent (jacobianArray[constraintIndex]))
				{
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Compute the Along/Away "Naturally" Incremented Variates
	 * 
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * 
	 * @return The Along/Away "Natural" Incremented Variates
	 */

	public double[][] alongAwayVariate (
		final double[] variateArray)
	{
		double[] variateIncrementArray = R1MatrixUtil.Product (
			R1MatrixUtil.InvertUsingGaussianElimination (_objectiveFunction.hessian (variateArray)),
			_objectiveFunction.jacobian (variateArray)
		);

		if (null == variateIncrementArray) {
			return null;
		}

		int variateDimension = variateArray.length;
		double[] awayVariateArray = new double[variateDimension];
		double[] alongVariateArray = new double[variateDimension];

		for (int variateIndex = 0; variateIndex < variateDimension; ++variateIndex) {
			awayVariateArray[variateIndex] =
				variateArray[variateIndex] - variateIncrementArray[variateIndex];
			alongVariateArray[variateIndex] =
				variateArray[variateIndex] + variateIncrementArray[variateIndex];
		}

		return new double[][] {alongVariateArray, awayVariateArray};
	}

	/**
	 * Check for Linearity Constraint Qualification
	 * 
	 * @return TRUE - Linearity Constraint Qualification is satisfied
	 */

	public boolean isLCQ()
	{
		for (int equalityConstraintIndex = 0;
			equalityConstraintIndex < numEqualityConstraint();
			++equalityConstraintIndex)
		{
			if (!(_equalityConstraintArray[equalityConstraintIndex] instanceof AffineMultivariate)) {
				return false;
			}
		}

		for (int inequalityConstraintIndex = 0;
			inequalityConstraintIndex < numInequalityConstraint();
			++inequalityConstraintIndex)
		{
			if (!(_inequalityConstraintArray[inequalityConstraintIndex] instanceof AffineMultivariate)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check for Linearity Independent Constraint Qualification
	 * 
	 * @param variateArray The Candidate R<sup>d</sup> Variate
	 * 
	 * @return TRUE - Linearity Independent Constraint Qualification is satisfied
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public boolean isLICQ (
		final double[] variateArray)
		throws Exception
	{
		return activeConstraintLinearDependence (variateArray, false);
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
