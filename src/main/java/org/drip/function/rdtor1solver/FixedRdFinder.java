
package org.drip.function.rdtor1solver;

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
 * <i>FixedRdFinder</i> exports the Methods needed for the locating a Fixed R<sup>d</sup> Point.
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

public abstract class FixedRdFinder
{

	/**
	 * Flag Indicating whether the Verifier Increment Metrics are to be Traced
	 */

	public static boolean s_verifierIncrementBlog = false;

	private org.drip.function.definition.RdToR1 _objectiveFunction = null;
	private org.drip.function.rdtor1solver.ConvergenceControl _convergenceControl = null;
	private org.drip.function.rdtor1descent.LineStepEvolutionControl _lineStepEvolutionControl = null;

	protected FixedRdFinder (
		final org.drip.function.definition.RdToR1 objectiveFunction,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lineStepEvolutionControl,
		final org.drip.function.rdtor1solver.ConvergenceControl convergenceControl)
		throws java.lang.Exception
	{
		if (null == (_objectiveFunction = objectiveFunction) ||
			null == (_convergenceControl = convergenceControl))
		{
			throw new java.lang.Exception ("FixedRdFinder Constructor => Invalid Inputs");
		}

		_lineStepEvolutionControl = lineStepEvolutionControl;
	}

	/**
	 * Retrieve the Objective Function
	 * 
	 * @return The Objective Function
	 */

	public org.drip.function.definition.RdToR1 objectiveFunction()
	{
		return _objectiveFunction;
	}

	/**
	 * Retrieve the Line Step Evolution Control
	 * 
	 * @return The Line Step Evolution Control
	 */

	public org.drip.function.rdtor1descent.LineStepEvolutionControl lineStepEvolutionControl()
	{
		return _lineStepEvolutionControl;
	}

	/**
	 * Retrieve the Convergence Control Parameters
	 * 
	 * @return The Convergence Control Parameters
	 */

	public org.drip.function.rdtor1solver.ConvergenceControl convergenceControl()
	{
		return _convergenceControl;
	}

	/**
	 * Solve for the Optimal Variate-Inequality Constraint Multiplier Tuple Using the Variate/Inequality
	 *  Constraint Tuple Convergence
	 *  
	 * @param startingVariateConstraint The Starting Variate/Inequality Constraint Tuple
	 * 
	 * @return The Optimal Variate-Inequality Constraint Multiplier Tuple
	 */

	public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier convergeVariate (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier startingVariateConstraint)
	{
		if (null == startingVariateConstraint)
		{
			return null;
		}

		org.drip.function.definition.RdToR1 objectiveFunction = objectiveFunction();

		boolean fixedPointFound = false;
		org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier currentVariateConstraint =
			startingVariateConstraint;
		org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier previousVariateConstraint =
			startingVariateConstraint;

		int comparisonVariateCount = objectiveFunction instanceof
			org.drip.function.rdtor1.LagrangianMultivariate ? (
				(org.drip.function.rdtor1.LagrangianMultivariate) objectiveFunction).objectiveFunctionDimension() :
				objectiveFunction.dimension();

		double absoluteToleranceFallback = _convergenceControl.absoluteTolerance();

		double relativeTolerance = _convergenceControl.relativeTolerance();

		while (!fixedPointFound)
		{
			org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier variateConstraint =
				increment (
					currentVariateConstraint
				);

			if (null == variateConstraint ||
				null == (
					currentVariateConstraint = next (
						previousVariateConstraint,
						variateConstraint,
						incrementFraction (
							currentVariateConstraint,
							variateConstraint
						)
					)
				)
			)
			{
				return null;
			}

			try
			{
				fixedPointFound =
					org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier.Compare (
						currentVariateConstraint,
						previousVariateConstraint,
						relativeTolerance,
						absoluteToleranceFallback,
						comparisonVariateCount
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			previousVariateConstraint = currentVariateConstraint;
		}

		return currentVariateConstraint;
	}

	/**
	 * Solve for the Optimal Variate-Inequality Constraint Multiplier Tuple Using the Objective Function
	 *  Convergence
	 *  
	 * @param startingVariateConstraint The Starting Variate/Inequality Constraint Tuple Set
	 * 
	 * @return The Optimal Variate-Inequality Constraint Multiplier Tuple
	 */

	public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier convergeObjectiveFunction (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier startingVariateConstraint)
	{
		if (null == startingVariateConstraint)
		{
			return null;
		}

		boolean fixedPointFound = false;
		double objectiveFunctionValuePrevious = java.lang.Double.NaN;
		org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier variateConstraint =
			startingVariateConstraint;

		try
		{
			objectiveFunctionValuePrevious = _objectiveFunction.evaluate (
				variateConstraint.variateArray()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		double convergenceControlAbsoluteTolerance = _convergenceControl.absoluteTolerance();

		double objectiveFunctionAbsoluteTolerance = java.lang.Math.abs (
			objectiveFunctionValuePrevious * _convergenceControl.relativeTolerance()
		);

		double dblAbsoluteTolerance = convergenceControlAbsoluteTolerance <
			objectiveFunctionAbsoluteTolerance ?
			convergenceControlAbsoluteTolerance : objectiveFunctionAbsoluteTolerance;

		while (!fixedPointFound)
		{
			org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier incrementalVariateConstraint
				= increment (
					variateConstraint
				);

			if (null == incrementalVariateConstraint ||
				null == (
					variateConstraint = next (
						variateConstraint,
						incrementalVariateConstraint,
						incrementFraction (
							variateConstraint,
							incrementalVariateConstraint
						)
					)
				)
			)
			{
				return null;
			}

			try
			{
				double objectiveFunctionValue = _objectiveFunction.evaluate (
					variateConstraint.variateArray()
				);

				if (java.lang.Math.abs (
						objectiveFunctionValuePrevious - objectiveFunctionValue
					) < dblAbsoluteTolerance
				)
				{
					fixedPointFound = true;
				}

				objectiveFunctionValuePrevious = objectiveFunctionValue;
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return variateConstraint;
	}

	/**
	 * Find the Optimal Variate-Inequality Constraint Multiplier Tuple using the Iteration Parameters
	 *  provided by the Convergence Control Instance
	 *  
	 * @param startingVariateConstraint The Starting Variate-Inequality Constraint Multiplier Tuple
	 * 
	 * @return The Optimal Variate-Inequality Constraint Multiplier Tuple
	 */

	public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier find (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier startingVariateConstraint)
	{
		int convergenceType = _convergenceControl.convergenceType();

		if (org.drip.function.rdtor1solver.InteriorPointBarrierControl.OBJECTIVE_FUNCTION_SEQUENCE_CONVERGENCE
			== convergenceType)
		{
			return convergeObjectiveFunction (startingVariateConstraint);
		}

		if (org.drip.function.rdtor1solver.InteriorPointBarrierControl.VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE
			== convergenceType)
		{
			return convergeVariate (startingVariateConstraint);
		}

		return null;
	}

	/**
	 * Retrieve the Incremental Step Length Fraction
	 * 
	 * @param variateConstraint The VariateInequalityConstraintMultiplier Base Instance
	 * @param variateConstraintIncrement The Full VariateInequalityConstraintMultiplier Instance Increment
	 * 
	 * @return The VariateInequalityConstraintMultiplier Incremental Step Length Fraction
	 */

	public double incrementFraction (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier variateConstraint,
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier
			variateConstraintIncrement)
	{
		if (null == _lineStepEvolutionControl ||
			null == variateConstraint || variateConstraint.incremental() ||
			null == variateConstraintIncrement || !variateConstraintIncrement.incremental())
		{
			return 1.;
		}

		org.drip.function.rdtor1descent.LineEvolutionVerifier lineEvolutionVerifier =
			_lineStepEvolutionControl.lineEvolutionVerifier();

		org.drip.function.definition.UnitVector variateIncrementDirectionVector =
			variateConstraintIncrement.variateIncrementVector().direction();

		int reductionStepCount = _lineStepEvolutionControl.reductionStepCount();

		double reductionFactor = _lineStepEvolutionControl.reductionFactor();

		double[] variateArray = variateConstraint.variateArray();

		double stepLength = 1.;

		while (0 <= --reductionStepCount)
		{
			org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics lineEvolutionVerifierMetrics =
				lineEvolutionVerifier.metrics (
					variateIncrementDirectionVector,
					variateArray,
					_objectiveFunction,
					stepLength
				);

			if (null == lineEvolutionVerifierMetrics)
			{
				return 1.;
			}

			if (s_verifierIncrementBlog)
			{
				System.out.println (lineEvolutionVerifierMetrics);
			}

			if (lineEvolutionVerifierMetrics.verify())
			{
				return stepLength;
			}

			stepLength *= reductionFactor;
		}

		return 1.;
	}

	/**
	 * Produce the Incremental Variate-Constraint Multiplier
	 * 
	 * @param currentVariateConstraint The Current Variate-Constraint Multiplier Tuple
	 * 
	 * @return The Incremental Variate-Constraint Multiplier
	 */

	abstract public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier increment (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier currentVariateConstraint);

	/**
	 * Iterate Over to the Next Variate-Constraint Multiplier Tuple
	 * 
	 * @param currentVariateConstraint The Current Variate-Constraint Multiplier Tuple
	 * @param incrementalVariateConstraint The Incremental Variate-Constraint Multiplier Tuple
	 * @param incrementFraction The Incremental Fraction to be applied
	 * 
	 * @return The Next Variate-Constraint Multiplier Set
	 */

	abstract public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier next (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier currentVariateConstraint,
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier incrementalVariateConstraint,
		final double incrementFraction);
}
