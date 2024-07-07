
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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

public class InteriorFixedPointFinder
	extends org.drip.function.rdtor1solver.FixedRdFinder
{
	private double _barrierStrength = java.lang.Double.NaN;
	private org.drip.function.rdtor1.BoundMultivariate[] _boundMultivariateFunctionArray = null;
	private org.drip.function.definition.RdToR1[] _inequalityConstraintMultivariateFunctionArray = null;

	private org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier incremental (
		final org.drip.function.rdtor1solver.ObjectiveFunctionPointMetrics objectiveFunctionPointMetrics,
		final org.drip.function.rdtor1solver.ConstraintFunctionPointMetrics
			inequalityConstraintFunctionPointMetrics)
	{
		if (null == objectiveFunctionPointMetrics ||
			null == inequalityConstraintFunctionPointMetrics)
		{
			return null;
		}

		int objectiveFunctionDimension = objectiveFunctionPointMetrics.dimension();

		double[] objectiveFunctionJacobian = objectiveFunctionPointMetrics.jacobian();

		double[][] objectiveFunctionHessian = objectiveFunctionPointMetrics.hessian();

		int inequalityConstraintCount = inequalityConstraintFunctionPointMetrics.count();

		double[] variateIncrementArray = new double[objectiveFunctionDimension];
		double[] inequalityConstraintIncrementCount = new double[inequalityConstraintCount];
		int constrainedObjectiveFunctionDimension = objectiveFunctionDimension + inequalityConstraintCount;
		double[][] constrainedObjectiveFunctionJacobianArray =
			new double[constrainedObjectiveFunctionDimension][constrainedObjectiveFunctionDimension];
		double[] constrainedObjectiveFunctionRHSArray = new double[constrainedObjectiveFunctionDimension];

		if (0 == objectiveFunctionDimension ||
			objectiveFunctionDimension != inequalityConstraintFunctionPointMetrics.dimension())
		{
			return null;
		}

		double[] inequalityConstraintFunctionMultiplierArray =
			inequalityConstraintFunctionPointMetrics.constraintFunctionMultiplierArray();

		double[][] inequalityConstraintFunctionJacobianArray =
			inequalityConstraintFunctionPointMetrics.constraintFunctionJacobianArray();

		double[] inequalityConstraintFunctionValueArray =
			inequalityConstraintFunctionPointMetrics.constraintFunctionValueArray();

		for (int objectiveFunctionDimensionIndexI = 0;
			objectiveFunctionDimensionIndexI < objectiveFunctionDimension;
			++objectiveFunctionDimensionIndexI)
		{
			for (int objectiveFunctionDimensionIndexJ = 0;
				objectiveFunctionDimensionIndexJ < objectiveFunctionDimension;
				++objectiveFunctionDimensionIndexJ)
			{
				constrainedObjectiveFunctionJacobianArray[objectiveFunctionDimensionIndexI][objectiveFunctionDimensionIndexJ]
					= objectiveFunctionHessian[objectiveFunctionDimensionIndexI][objectiveFunctionDimensionIndexJ];
			}

			for (int inequalityConstraintIndex = 0;
				inequalityConstraintIndex < inequalityConstraintCount;
				++inequalityConstraintIndex)
			{
				constrainedObjectiveFunctionJacobianArray[objectiveFunctionDimensionIndexI][inequalityConstraintIndex + objectiveFunctionDimension] =
					-1. * inequalityConstraintFunctionJacobianArray[objectiveFunctionDimensionIndexI][inequalityConstraintIndex];
			}
		}

		for (int inequalityConstraintIndexI = 0;
			inequalityConstraintIndexI < inequalityConstraintCount;
			++inequalityConstraintIndexI)
		{
			for (int inequalityConstraintIndexJ = 0;
				inequalityConstraintIndexJ < inequalityConstraintCount;
				++inequalityConstraintIndexJ)
			{
				constrainedObjectiveFunctionJacobianArray[inequalityConstraintIndexI + objectiveFunctionDimension][inequalityConstraintIndexJ + objectiveFunctionDimension]
					= inequalityConstraintIndexI == inequalityConstraintIndexJ ? inequalityConstraintFunctionValueArray[inequalityConstraintIndexI] : 0.;
			}

			for (int objectiveFunctionIndex = 0;
				objectiveFunctionIndex < objectiveFunctionDimension;
				++objectiveFunctionIndex)
			{
				constrainedObjectiveFunctionJacobianArray[inequalityConstraintIndexI + objectiveFunctionDimension][objectiveFunctionIndex] =
					inequalityConstraintFunctionMultiplierArray[inequalityConstraintIndexI] *
					inequalityConstraintFunctionJacobianArray[objectiveFunctionIndex][inequalityConstraintIndexI];
			}
		}

		for (int constrainedObjectiveFunctionIndex = 0;
			constrainedObjectiveFunctionIndex < constrainedObjectiveFunctionDimension;
			++constrainedObjectiveFunctionIndex)
		{
			if (constrainedObjectiveFunctionIndex < objectiveFunctionDimension)
			{
				constrainedObjectiveFunctionRHSArray[constrainedObjectiveFunctionIndex] =
					-1. * objectiveFunctionJacobian[constrainedObjectiveFunctionIndex];

				for (int inequalityConstraintIndex = 0;
					inequalityConstraintIndex < inequalityConstraintCount;
					++inequalityConstraintIndex)
				{
					constrainedObjectiveFunctionRHSArray[constrainedObjectiveFunctionIndex] +=
						inequalityConstraintFunctionJacobianArray[constrainedObjectiveFunctionIndex][inequalityConstraintIndex]
						* inequalityConstraintFunctionMultiplierArray[inequalityConstraintIndex];
				}
			}
			else
			{
				int constraintIndex = constrainedObjectiveFunctionIndex - objectiveFunctionDimension;
				constrainedObjectiveFunctionRHSArray[constrainedObjectiveFunctionIndex] =
					_barrierStrength - inequalityConstraintFunctionValueArray[constraintIndex] *
					inequalityConstraintFunctionMultiplierArray[constraintIndex];
			}
		}

		org.drip.numerical.linearalgebra.LinearizationOutput linearizationOutput =
			org.drip.numerical.linearsolver.LinearSystem.SolveUsingMatrixInversion (
				constrainedObjectiveFunctionJacobianArray,
				constrainedObjectiveFunctionRHSArray
			);

		if (null == linearizationOutput)
		{
			return null;
		}

		double[] variateConstraintIncrementArray = linearizationOutput.getTransformedRHS();

		if (null == variateConstraintIncrementArray ||
			variateConstraintIncrementArray.length != constrainedObjectiveFunctionDimension)
		{
			return null;
		}

		for (int constrainedObjectiveFunctionIndex = 0;
			constrainedObjectiveFunctionIndex < constrainedObjectiveFunctionDimension;
			++constrainedObjectiveFunctionIndex)
		{
			if (constrainedObjectiveFunctionIndex < objectiveFunctionDimension)
			{
				variateIncrementArray[constrainedObjectiveFunctionIndex] =
					variateConstraintIncrementArray[constrainedObjectiveFunctionIndex];
			}
			else
			{
				inequalityConstraintIncrementCount[constrainedObjectiveFunctionIndex - objectiveFunctionDimension]
					= variateConstraintIncrementArray[constrainedObjectiveFunctionIndex];
			}
		}

		try
		{
			return new org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier (
				true,
				variateIncrementArray,
				inequalityConstraintIncrementCount
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * InteriorFixedPointFinder Constructor
	 * 
	 * @param rdToR1ObjectiveFunction The Objective Function
	 * @param inequalityConstraintMultivariateFunctionArray Array of Inequality Constraints
	 * @param lsec The Line Step Evolution Control
	 * @param cc Convergence Control Parameters
	 * @param barrierStrength Barrier Strength
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InteriorFixedPointFinder (
		final org.drip.function.definition.RdToR1 rdToR1ObjectiveFunction,
		final org.drip.function.definition.RdToR1[] inequalityConstraintMultivariateFunctionArray,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lsec,
		final org.drip.function.rdtor1solver.ConvergenceControl cc,
		final double barrierStrength)
		throws java.lang.Exception
	{
		super (
			rdToR1ObjectiveFunction,
			lsec,
			cc
		);

		if (null == (_inequalityConstraintMultivariateFunctionArray = inequalityConstraintMultivariateFunctionArray) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_barrierStrength = barrierStrength))
		{
			throw new java.lang.Exception ("InteriorFixedPointFinder Constructor => Invalid Inputs");
		}

		int inequalityConstraintCount = _inequalityConstraintMultivariateFunctionArray.length;
		_boundMultivariateFunctionArray = 0 == inequalityConstraintCount ? null : new
			org.drip.function.rdtor1.BoundMultivariate[inequalityConstraintCount];

		if (0 == inequalityConstraintCount)
		{
			throw new java.lang.Exception ("InteriorFixedPointFinder Constructor => Invalid Inputs");
		}

		for (int inequalityConstraintIndex = 0;
			inequalityConstraintIndex < inequalityConstraintCount;
			++inequalityConstraintIndex)
		{
			if (null == _inequalityConstraintMultivariateFunctionArray[inequalityConstraintIndex])
			{
				throw new java.lang.Exception ("InteriorFixedPointFinder Constructor => Invalid Inputs");
			}

			if (_inequalityConstraintMultivariateFunctionArray[inequalityConstraintIndex] instanceof
				org.drip.function.rdtor1.BoundMultivariate)
			{
				_boundMultivariateFunctionArray[inequalityConstraintIndex] =
					(org.drip.function.rdtor1.BoundMultivariate)
					_inequalityConstraintMultivariateFunctionArray[inequalityConstraintIndex];
			}
		}
	}

	/**
	 * Retrieve the Array of Inequality Constraint Function
	 * 
	 * @return The Array of Inequality Constraint Function
	 */

	public org.drip.function.definition.RdToR1[] inequalityConstraintMultivariateFunctionArray()
	{
		return _inequalityConstraintMultivariateFunctionArray;
	}

	/**
	 * Retrieve the Barrier Strength
	 * 
	 * @return The Barrier Strength
	 */

	public double barrierStrength()
	{
		return _barrierStrength;
	}

	@Override public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier increment (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier currentVariateConstraint)
	{
		if (null == currentVariateConstraint)
		{
			return null;
		}

		double[] variateArray = currentVariateConstraint.variateArray();

		int variateCount = variateArray.length;
		int constraintCount = _inequalityConstraintMultivariateFunctionArray.length;
		double[][] constraintJacobianArray = new double[variateCount][constraintCount];
		double[] constraintValueArray = new double[constraintCount];

		if (0 == constraintCount)
		{
			return null;
		}

		for (int constraintIndex = 0;
			constraintIndex < constraintCount;
			++constraintIndex)
		{
			try
			{
				constraintValueArray[constraintIndex] =
					_inequalityConstraintMultivariateFunctionArray[constraintIndex].evaluate (
						variateArray
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			double[] constraintJacobian =
				_inequalityConstraintMultivariateFunctionArray[constraintIndex].jacobian (
					variateArray
				);

			if (null == constraintJacobian)
			{
				return null;
			}

			for (int variateIndex = 0;
				variateIndex < variateCount;
				++variateIndex)
			{
				constraintJacobianArray[variateIndex][constraintIndex] = constraintJacobian[variateIndex];
			}
		}

		org.drip.function.definition.RdToR1 objectiveFunction = objectiveFunction();

		try
		{
			return incremental (
				new org.drip.function.rdtor1solver.ObjectiveFunctionPointMetrics (
					objectiveFunction.jacobian (
						variateArray
					),
					objectiveFunction.hessian (
						variateArray
					)
				),
				new org.drip.function.rdtor1solver.ConstraintFunctionPointMetrics (
					constraintValueArray,
					constraintJacobianArray,
					currentVariateConstraint.constraintMultiplierArray()
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier next (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier currentVariateConstraint,
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier
			incrementalVariateConstraint,
		final double incrementFraction)
	{
		return org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier.Add (
			currentVariateConstraint,
			incrementalVariateConstraint,
			incrementFraction,
			_boundMultivariateFunctionArray
		);
	}
}
