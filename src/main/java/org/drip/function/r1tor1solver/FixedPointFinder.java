
package org.drip.function.r1tor1solver;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>FixedPointFinder</i> is the base abstract class that is implemented by customized invocations, e.g.,
 * Newton's method, or any of the bracketing methodologies.
 * <br><br>
 * FixedPointFinder invokes the core routine for determining the fixed point from the goal. The
 * 	ExecutionControl determines the execution termination. The initialization heuristics implements
 * 	targeted customization of the search.
 * <br><br>
 * FixedPointFinder main flow comprises of the following steps:
 * <br>
 * <ul>
 * 	<li>
 * 		Initialize the fixed point search zone by determining either a) the brackets, or b) the starting
 * 			variate.
 * 	</li>
 * 	<li>
 * 		Compute the absolute OF tolerance that establishes the attainment of the fixed point.
 * 	</li>
 * 	<li>
 * 		Launch the variate iterator that iterates the variate.
 * 	</li>
 * 	<li>
 * 		Iterate until the desired tolerance has been attained
 * 	</li>
 * 	<li>
 * 		Return the fixed point output.
 * 	</li>
 * </ul>
 * <br><br>
 * Fixed point finders that derive from this provide implementations for the following:
 * <br>
 * <ul>
 * 	<li>
 * 	- Variate initialization: They may choose either bracketing initializer, or the convergence initializer -
 * 		functionality is provided for both in this module.
 * 	</li>
 * 	<li>
 * 	- Variate Iteration: Variates are iterated using a) any of the standard primitive built-in variate
 * 		iterators (or custom ones), or b) a variate selector scheme for each iteration.
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Solvers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class FixedPointFinder {
	protected boolean _bWhine = false;
	protected double _dblOFGoal = java.lang.Double.NaN;
	protected org.drip.function.r1tor1solver.ExecutionControl _ec = null;
	protected org.drip.function.definition.R1ToR1 _of = null;

	protected FixedPointFinder (
		final double dblOFGoal,
		final org.drip.function.definition.R1ToR1 of,
		final org.drip.function.r1tor1solver.ExecutionControl ec,
		final boolean bWhine)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblOFGoal = dblOFGoal) || null == (_of = of))
			throw new java.lang.Exception ("FixedPointFinder constructor: Invalid inputs");

		_ec = new org.drip.function.r1tor1solver.ExecutionControl (of, null);

		_bWhine = bWhine;
	}

	protected abstract boolean iterateVariate (
		final org.drip.function.r1tor1solver.IteratedVariate vi,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop);

	protected abstract org.drip.function.r1tor1solver.ExecutionInitializationOutput initializeVariateZone (
		final org.drip.function.r1tor1solver.InitializationHeuristics ih);

	/**
	 * Invoke the solution 1D root finding sequence
	 * 
	 * @param ih Optional Initialization Heuristics
	 * 
	 * @return Root finder Solution Object for the variate
	 */

	public org.drip.function.r1tor1solver.FixedPointFinderOutput findRoot (
		final org.drip.function.r1tor1solver.InitializationHeuristics ih)
	{
		org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = null;

		org.drip.function.r1tor1solver.ExecutionInitializationOutput eiop = initializeVariateZone (ih);

		if (null == eiop || !eiop.isDone()) return null;

		try {
			rfop = new org.drip.function.r1tor1solver.FixedPointFinderOutput (eiop);

			if (!rfop.incrOFCalcs()) return rfop;

			double dblOF = _of.evaluate (eiop.getStartingVariate());

			double dblAbsoluteTolerance = _ec.calcAbsoluteOFTolerance (dblOF);

			double dblAbsoluteConvergence = _ec.calcAbsoluteVariateConvergence (eiop.getStartingVariate());

			org.drip.function.r1tor1solver.IteratedVariate iv = new
				org.drip.function.r1tor1solver.IteratedVariate (eiop, dblOF);

			int iNumIterationsPending = _ec.getNumIterations();

			while (!_ec.hasOFReachedGoal (dblAbsoluteTolerance, iv.getOF(), _dblOFGoal)) {
				double dblPrevVariate = iv.getVariate();

				if (!rfop.incrIterations() || 0 == --iNumIterationsPending || !iterateVariate (iv, rfop))
					return rfop;

				if (_ec.isVariateConvergenceCheckEnabled() && (java.lang.Math.abs (dblPrevVariate -
					iv.getVariate()) < dblAbsoluteConvergence))
					break;
			}

			rfop.setRoot (iv.getVariate());
		} catch (java.lang.Exception e) {
			if (_bWhine) e.printStackTrace();
		}

		return rfop;
	}

	/**
	 * Invoke the solution 1D root finding sequence
	 * 
	 * @return Root finder Solution Object for the variate
	 */

	public org.drip.function.r1tor1solver.FixedPointFinderOutput findRoot()
	{
		return findRoot (null);
	}
}
