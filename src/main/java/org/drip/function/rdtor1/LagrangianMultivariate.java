
package org.drip.function.rdtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>LagrangianMultivariate</i> implements a R<sup>d</sup> To R<sup>1</sup> Multivariate Function along
 * with the specified Set of Equality Constraints.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1/README.md">Built-in R<sup>d</sup> To R<sup>1</sup> Functions</a></li>
 *  </ul>
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
