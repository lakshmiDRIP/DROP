
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
 * <i>FixedPointFinderNewton</i> customizes the FixedPointFinder for Open (Newton's) fixed point finder
 * 	functionality.
 * <br><br>
 * FixedPointFinderNewton applies the following customization:
 * <br>
 * <ul>
 * 	<li>
 * 		Initializes the fixed point finder by computing a starting variate in the convergence zone
 * 	</li>
 * 	<li>
 * 		Iterating the next search variate using the Newton's method.
 * 	</li>
 * </ul>
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver">R<sup>1</sup> To R<sup>1</sup></a> Solver</li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixedPointFinderNewton extends org.drip.function.r1tor1solver.FixedPointFinder {
	private org.drip.function.r1tor1solver.ExecutionInitializer _ei = null;

	private double calcVariateOFSlope (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("FixedPointFinderNewton::calcVariateOFSlope => Invalid input!");

		org.drip.quant.calculus.Differential diff = _of.differential (dblVariate, 1);

		if (null == diff)
			throw new java.lang.Exception
				("FixedPointFinderNewton::calcVariateTargetSlope => Cannot evaluate Derivative for variate "
					+ dblVariate);

		return diff.calcSlope (false);
	}

	@Override protected boolean iterateVariate (
		final org.drip.function.r1tor1solver.IteratedVariate vi,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop)
	{
		if (null == vi || null == rfop) return false;

		double dblVariate = vi.getVariate();

		try {
			double dblVariateNext = dblVariate - calcVariateOFSlope (dblVariate) * vi.getOF();

			return vi.setVariate (dblVariateNext) && vi.setOF (_of.evaluate (dblVariateNext)) &&
				rfop.incrOFDerivCalcs() && rfop.incrOFCalcs();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override protected org.drip.function.r1tor1solver.ExecutionInitializationOutput initializeVariateZone (
		final org.drip.function.r1tor1solver.InitializationHeuristics ih)
	{
		return _ei.initializeBracket (ih, _dblOFGoal);
	}

	/**
	 * FixedPointFinderNewton constructor
	 * 
	 * @param dblOFGoal OF Goal
	 * @param of Objective Function
	 * @param bWhine TRUE - Balk on Encountering Exception
	 * 
	 * @throws java.lang.Exception Propogated from underneath
	 */

	public FixedPointFinderNewton (
		final double dblOFGoal,
		final org.drip.function.definition.R1ToR1 of,
		final boolean bWhine)
		throws java.lang.Exception
	{
		super (dblOFGoal, of, null, bWhine);

		_ei = new org.drip.function.r1tor1solver.ExecutionInitializer (_of, null, true);
	}
}
