
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
 * <i>FixedPointFinderZheng</i> implements the fixed point locator using Zheng's improvement to Brent's
 * method.
 * <br><br>
 * FixedPointFinderZheng overrides the iterateCompoundVariate method to achieve the desired simplification in
 * 	the iterative variate selection.
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver">R<sup>1</sup> To R<sup>1</sup></a> Solver</li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedPointFinderZheng extends org.drip.function.r1tor1solver.FixedPointFinderBracketing {
	@Override protected double iterateCompoundVariate (
		final double dblCurrentVariate,
		final double dblContraVariate,
		final double dblCurrentOF,
		final double dblContraPointOF,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop)
		throws java.lang.Exception
	{
		double dblVariateMid = org.drip.function.r1tor1solver.VariateIteratorPrimitive.Bisection (dblCurrentVariate,
			dblContraVariate);

		if (!rfop.incrOFCalcs())
			throw new java.lang.Exception
				("FixedPointFinderZheng::iterateCompoundVariate => Cannot increment rfop!");

		double dblOF = _of.evaluate (dblVariateMid);

		double dblNextVariate = java.lang.Double.NaN;

		if (dblCurrentOF != dblOF && dblContraPointOF != dblOF)
			dblNextVariate = org.drip.function.r1tor1solver.VariateIteratorPrimitive.InverseQuadraticInterpolation
				(dblCurrentVariate, dblVariateMid, dblContraVariate, dblCurrentOF, dblOF, dblContraPointOF);
		else
			dblNextVariate = org.drip.function.r1tor1solver.VariateIteratorPrimitive.FalsePosition (dblCurrentVariate,
				dblContraVariate, dblCurrentOF, dblContraPointOF);

		return dblVariateMid < dblNextVariate ? dblVariateMid : dblNextVariate;
	}

	/**
	 * FixedPointFinderZheng constructor
	 * 
	 * @param dblOFGoal OF Goal
	 * @param of Objective Function
	 * @param bWhine TRUE - Balk on Encountering Exception
	 * 
	 * @throws java.lang.Exception Propogated from below
	 */

	public FixedPointFinderZheng (
		final double dblOFGoal,
		final org.drip.function.definition.R1ToR1 of,
		final boolean bWhine)
		throws java.lang.Exception
	{
		super (dblOFGoal, of, null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, bWhine);
	}
}
