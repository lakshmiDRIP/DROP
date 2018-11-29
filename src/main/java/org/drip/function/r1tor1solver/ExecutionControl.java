
package org.drip.function.r1tor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>ExecutionControl</i> implements the core fixed point search execution control and customization
 * functionality.
 * <br><br>
 * ExecutionControl is used for a) calculating the absolute tolerance, and b) determining whether the OF has
 * reached the goal.
 * <br><br>
 * ExecutionControl determines the execution termination using its ExecutionControlParams instance. 
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/r1tor1solver">R<sup>1</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExecutionControl {
	private org.drip.function.r1tor1solver.ExecutionControlParams _ecp = null;

	protected org.drip.function.definition.R1ToR1 _of = null;

	/**
	 * ExecutionControl constructor
	 * 
	 * @param of Objective Function
	 * @param ecp Execution Control Parameters
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public ExecutionControl (
		final org.drip.function.definition.R1ToR1 of,
		final org.drip.function.r1tor1solver.ExecutionControlParams ecp)
		throws java.lang.Exception
	{
		if (null == (_of = of))
			throw new java.lang.Exception ("ExecutionControl constructor: Invalid inputs");

		if (null == (_ecp = ecp)) _ecp = new org.drip.function.r1tor1solver.ExecutionControlParams();
	}

	/**
	 * Retrieve the Number of Iterations
	 * 
	 * @return Number of solver iterations
	 */

	public int getNumIterations()
	{
		return _ecp.getNumIterations();
	}

	/**
	 * Calculate the absolute OF tolerance using the initial OF value
	 * 
	 * @param dblOFInitial Initial OF Value
	 * 
	 * @return The absolute OF Tolerance
	 * 
	 * @throws java.lang.Exception Thrown if absolute tolerance cannot be calculated
	 */

	public double calcAbsoluteOFTolerance (
		final double dblOFInitial)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblOFInitial))
			throw new java.lang.Exception ("ExecutionControl::calcAbsoluteOFTolerance => Invalid inputs!");

		double dblAbsoluteTolerance = java.lang.Math.abs (dblOFInitial) * _ecp.getOFGoalToleranceFactor();

		if (!org.drip.quant.common.NumberUtil.IsValid (dblAbsoluteTolerance) || dblAbsoluteTolerance <
			_ecp.getAbsoluteOFToleranceFallback())
			dblAbsoluteTolerance = _ecp.getAbsoluteOFToleranceFallback();

		return dblAbsoluteTolerance;
	}

	/**
	 * Calculate the absolute variate convergence amount using the initial variate
	 * 
	 * @param dblInitialVariate Initial Variate
	 * 
	 * @return The Absolute Variate Convergence Amount
	 * 
	 * @throws java.lang.Exception Thrown if Absolute Variate Convergence Amount cannot be calculated
	 */

	public double calcAbsoluteVariateConvergence (
		final double dblInitialVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblInitialVariate))
			throw new java.lang.Exception
				("ExecutionControl::calcAbsoluteVariateConvergence => Invalid inputs!");

		double dblAbsoluteConvergence = java.lang.Math.abs (dblInitialVariate) *
			_ecp.getVariateConvergenceFactor();

		if (!org.drip.quant.common.NumberUtil.IsValid (dblAbsoluteConvergence) || dblAbsoluteConvergence <
			_ecp.getAbsoluteVariateConvergenceFallback())
			dblAbsoluteConvergence = _ecp.getAbsoluteVariateConvergenceFallback();

		return dblAbsoluteConvergence;
	}

	/**
	 * Check to see if the OF has reached the goal
	 * 
	 * @param dblAbsoluteTolerance Absolute Tolerance
	 * @param dblOF OF Value
	 * @param dblOFGoal OF Goal
	 * 
	 * @return TRUE - If the OF has reached the goal
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public boolean hasOFReachedGoal (
		final double dblAbsoluteTolerance,
		final double dblOF,
		final double dblOFGoal)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblAbsoluteTolerance) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblOF) || !org.drip.quant.common.NumberUtil.IsValid
				(dblOFGoal))
			throw new java.lang.Exception ("ExecutionControl::hasOFReachedGoal => Invalid inputs!");

		return dblAbsoluteTolerance > java.lang.Math.abs (dblOF - dblOFGoal);
	}

	/**
	 * Indicate if the variate convergence check has been turned on
	 * 
	 * @return TRUE - Variate convergence check has been turned on
	 */

	public boolean isVariateConvergenceCheckEnabled()
	{
		return _ecp.isVariateConvergenceCheckEnabled();
	}
}
