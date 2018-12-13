
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

public abstract class FixedRdFinder {

	/**
	 * Flag Indicating whether the Verifier Increment Metrics are to be Traced
	 */

	public static boolean s_bVerifierIncrementBlog = false;

	private org.drip.function.rdtor1solver.ConvergenceControl _cc = null;
	private org.drip.function.definition.RdToR1 _rdToR1ObjectiveFunction = null;
	private org.drip.function.rdtor1descent.LineStepEvolutionControl _lsec = null;

	protected FixedRdFinder (
		final org.drip.function.definition.RdToR1 rdToR1ObjectiveFunction,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lsec,
		final org.drip.function.rdtor1solver.ConvergenceControl cc)
		throws java.lang.Exception
	{
		if (null == (_rdToR1ObjectiveFunction = rdToR1ObjectiveFunction) || null == (_cc = cc))
			throw new java.lang.Exception ("FixedRdFinder Constructor => Invalid Inputs");

		_lsec = lsec;
	}

	/**
	 * Retrieve the Objective Function
	 * 
	 * @return The Objective Function
	 */

	public org.drip.function.definition.RdToR1 objectiveFunction()
	{
		return _rdToR1ObjectiveFunction;
	}

	/**
	 * Retrieve the Line Step Evolution Control
	 * 
	 * @return The Line Step Evolution Control
	 */

	public org.drip.function.rdtor1descent.LineStepEvolutionControl lineStepEvolutionControl()
	{
		return _lsec;
	}

	/**
	 * Retrieve the Convergence Control Parameters
	 * 
	 * @return The Convergence Control Parameters
	 */

	public org.drip.function.rdtor1solver.ConvergenceControl control()
	{
		return _cc;
	}

	/**
	 * Solve for the Optimal Variate-Inequality Constraint Multiplier Tuple Using the Variate/Inequality
	 *  Constraint Tuple Convergence
	 *  
	 * @param vicmStarting The Starting Variate/Inequality Constraint Tuple Set
	 * 
	 * @return The Optimal Variate-Inequality Constraint Multiplier Tuple
	 */

	public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier convergeVariate (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmStarting)
	{
		if (null == vicmStarting) return null;

		org.drip.function.definition.RdToR1 rdToR1ObjectiveFunction = objectiveFunction();

		boolean bFixedPointFound = false;
		org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmCurrent = vicmStarting;
		org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmPrevious = vicmStarting;

		int iNumComparisonVariate = rdToR1ObjectiveFunction instanceof
			org.drip.function.rdtor1.LagrangianMultivariate ?
				((org.drip.function.rdtor1.LagrangianMultivariate)
					rdToR1ObjectiveFunction).objectiveFunctionDimension() :
						rdToR1ObjectiveFunction.dimension();

		org.drip.function.rdtor1solver.ConvergenceControl cc = control();

		double dblAbsoluteToleranceFallback = cc.absoluteTolerance();

		double dblRelativeTolerance = cc.relativeTolerance();

		while (!bFixedPointFound) {
			org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmIncrement = increment
				(vicmCurrent);

			if (null == vicmIncrement || null == (vicmCurrent = next (vicmPrevious, vicmIncrement,
				incrementFraction (vicmCurrent, vicmIncrement))))
				return null;

			try {
				bFixedPointFound =
					org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier.Compare
						(vicmCurrent, vicmPrevious, dblRelativeTolerance, dblAbsoluteToleranceFallback,
							iNumComparisonVariate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			vicmPrevious = vicmCurrent;
		}

		return vicmCurrent;
	}

	/**
	 * Solve for the Optimal Variate-Inequality Constraint Multiplier Tuple Using the Objective Function
	 *  Convergence
	 *  
	 * @param vicmStarting The Starting Variate/Inequality Constraint Tuple Set
	 * 
	 * @return The Optimal Variate-Inequality Constraint Multiplier Tuple
	 */

	public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier convergeObjectiveFunction (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmStarting)
	{
		if (null == vicmStarting) return null;

		boolean bFixedPointFound = false;
		double dblObjectiveFunctionPrevious = java.lang.Double.NaN;
		org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicm = vicmStarting;

		org.drip.function.definition.RdToR1 rdToR1ObjectiveFunction = objectiveFunction();

		try {
			dblObjectiveFunctionPrevious = rdToR1ObjectiveFunction.evaluate (vicm.variates());
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.function.rdtor1solver.ConvergenceControl cc = control();

		double dblIPBCAbsoluteTolerance = cc.absoluteTolerance();

		double dblOFAbsoluteTolerance = java.lang.Math.abs (dblObjectiveFunctionPrevious *
			cc.relativeTolerance());

		double dblAbsoluteTolerance = dblIPBCAbsoluteTolerance < dblOFAbsoluteTolerance ?
			dblIPBCAbsoluteTolerance : dblOFAbsoluteTolerance;

		while (!bFixedPointFound) {
			org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmIncrement = increment
				(vicm);

			if (null == vicmIncrement || null == (vicm = next (vicm, vicmIncrement, incrementFraction (vicm,
				vicmIncrement))))
				return null;

			try {
				double dblObjectiveFunction = rdToR1ObjectiveFunction.evaluate (vicm.variates());

				if (java.lang.Math.abs (dblObjectiveFunctionPrevious - dblObjectiveFunction) <
					dblAbsoluteTolerance)
					bFixedPointFound = true;

				dblObjectiveFunctionPrevious = dblObjectiveFunction;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return vicm;
	}

	/**
	 * Find the Optimal Variate-Inequality Constraint Multiplier Tuple using the Iteration Parameters
	 *  provided by the Convergence Control Instance
	 *  
	 * @param vicmStarting The Starting Variate-Inequality Constraint Multiplier Tuple
	 * 
	 * @return The Optimal Variate-Inequality Constraint Multiplier Tuple
	 */

	public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier find (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmStarting)
	{
		int iConvergenceType = control().convergenceType();

		if (org.drip.function.rdtor1solver.InteriorPointBarrierControl.OBJECTIVE_FUNCTION_SEQUENCE_CONVERGENCE
			== iConvergenceType)
			return convergeObjectiveFunction (vicmStarting);

		if (org.drip.function.rdtor1solver.InteriorPointBarrierControl.VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE
			== iConvergenceType)
			return convergeVariate (vicmStarting);

		return null;
	}

	/**
	 * Retrieve the Incremental Step Length Fraction
	 * 
	 * @param vicm The VICM Base Instance
	 * @param vicmFullIncrement The Full VICM Instance Increment
	 * 
	 * @return The VICM Incremental Step Length Fraction
	 */

	public double incrementFraction (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicm,
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicmFullIncrement)
	{
		if (null == _lsec || null == vicm || vicm.incremental() || null == vicmFullIncrement ||
			!vicmFullIncrement.incremental())
			return 1.;

		org.drip.function.rdtor1descent.LineEvolutionVerifier lev = _lsec.lineEvolutionVerifier();

		org.drip.function.definition.SizedVector sv = vicmFullIncrement.variateIncrementVector();

		org.drip.function.definition.UnitVector uvDirection = sv.direction();

		double dblReductionFactor = _lsec.reductionFactor();

		int iReductionStep = _lsec.reductionSteps();

		double[] adblVariate = vicm.variates();

		double dblStepLength = 1.;

		while (0 <= --iReductionStep) {
			org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics levm = lev.metrics (uvDirection,
				adblVariate, _rdToR1ObjectiveFunction, dblStepLength);

			if (null == levm) return 1.;

			if (s_bVerifierIncrementBlog) System.out.println (levm);

			if (levm.verify()) return dblStepLength;

			dblStepLength *= dblReductionFactor;
		}

		return 1.;
	}

	/**
	 * Produce the Incremental Variate-Constraint Multiplier
	 * 
	 * @param vcmtCurrent The Current Variate-Constraint Multiplier Tuple
	 * 
	 * @return The Incremental Variate-Constraint Multiplier
	 */

	abstract public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier increment (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vcmtCurrent);

	/**
	 * Iterate Over to the Next Variate-Constraint Multiplier Tuple
	 * 
	 * @param vcmtCurrent The Current Variate-Constraint Multiplier Tuple
	 * @param vcmtIncrement The Incremental Variate-Constraint Multiplier Tuple
	 * @param dblIncrementFraction The Incremental Fraction to be applied
	 * 
	 * @return The Next Variate-Constraint Multiplier Set
	 */

	abstract public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier next (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vcmtCurrent,
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vcmtIncrement,
		final double dblIncrementFraction);
}
