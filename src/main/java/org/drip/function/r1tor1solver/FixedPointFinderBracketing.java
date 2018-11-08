
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
 * <i>FixedPointFinderBracketing</i> customizes the FixedPointFinder for bracketing based fixed point finder
 * 	functionality.
 * <br><br>
 * FixedPointFinderBracketing applies the following customization:
 * <br>
 * <ul>
 * 	<li>
 * 		Initializes the fixed point finder by computing the starting brackets
 * 	</li>
 * 	<li>
 * 		Iterating the next search variate using one of the specified variate iterator primitives.
 * 	</li>
 * </ul>
 * <br><br>
 * By default, FixedPointFinderBracketing does not do compound iterations of the variate using any schemes -
 * 	that is done by classes that extend it.
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1solver">R<sup>1</sup> To R<sup>1</sup></a> Solver</li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedPointFinderBracketing extends org.drip.function.r1tor1solver.FixedPointFinder {
	protected int _iIteratorPrimitive = -1;
	protected org.drip.function.r1tor1solver.IteratedBracket _ib = null;

	private org.drip.function.r1tor1solver.ExecutionInitializer _ei = null;

	protected final double calcNextVariate (
		final double dblCurrentVariate,
		final double dblContraVariate,
		final double dblCurrentOF,
		final double dblContraPointOF,
		final int iIteratorPrimitive,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop)
		throws java.lang.Exception
	{
		if (org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION == iIteratorPrimitive)
			return org.drip.function.r1tor1solver.VariateIteratorPrimitive.Bisection (dblCurrentVariate,
				dblContraVariate);

		if (org.drip.function.r1tor1solver.VariateIteratorPrimitive.FALSE_POSITION == iIteratorPrimitive)
			return org.drip.function.r1tor1solver.VariateIteratorPrimitive.FalsePosition (dblCurrentVariate,
				dblContraVariate, dblCurrentOF, dblContraPointOF);

		double dblIntermediateVariate = org.drip.function.r1tor1solver.VariateIteratorPrimitive.Bisection
			(dblCurrentVariate, dblContraVariate);

		if (!rfop.incrOFCalcs())
			throw new java.lang.Exception
				("FixedPointFinderBracketing::calcNextVariate => Cannot increment rfop!");

		if (org.drip.function.r1tor1solver.VariateIteratorPrimitive.QUADRATIC_INTERPOLATION == iIteratorPrimitive)
			return org.drip.function.r1tor1solver.VariateIteratorPrimitive.QuadraticInterpolation (dblCurrentVariate,
				dblIntermediateVariate, dblContraVariate, dblCurrentOF, _of.evaluate
					(dblIntermediateVariate), dblContraPointOF);

		if (org.drip.function.r1tor1solver.VariateIteratorPrimitive.INVERSE_QUADRATIC_INTERPOLATION ==
			iIteratorPrimitive)
			return org.drip.function.r1tor1solver.VariateIteratorPrimitive.InverseQuadraticInterpolation
				(dblCurrentVariate, dblIntermediateVariate, dblContraVariate, dblCurrentOF, _of.evaluate
					(dblIntermediateVariate), dblContraPointOF);

		if (org.drip.function.r1tor1solver.VariateIteratorPrimitive.RIDDER == iIteratorPrimitive)
			return org.drip.function.r1tor1solver.VariateIteratorPrimitive.Ridder (dblCurrentVariate,
				dblIntermediateVariate, dblContraVariate, dblCurrentOF, _of.evaluate
					(dblIntermediateVariate), dblContraPointOF);

		throw new java.lang.Exception
			("FixedPointFinderBracketing.calcNextVariate => Unknown Iterator Primitive");
	}

	protected double iterateCompoundVariate (
		final double dblCurrentVariate,
		final double dblContraVariate,
		final double dblCurrentOF,
		final double dblContraPointOF,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop)
		throws java.lang.Exception
	{
		return calcNextVariate (dblCurrentVariate, dblContraVariate, dblCurrentOF, dblContraPointOF,
			_iIteratorPrimitive, rfop);
	}

	@Override protected boolean iterateVariate (
		final org.drip.function.r1tor1solver.IteratedVariate iv,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop)
	{
		if (null == iv || null == rfop) return false;

		double dblContraRoot = java.lang.Double.NaN;
		double dblContraRootOF = java.lang.Double.NaN;

		double dblOF = iv.getOF();

		double dblOFLeft = _ib.getOFLeft();

		double dblOFRight = _ib.getOFRight();

		double dblVariate = iv.getVariate();

		double dblVariateLeft = _ib.getVariateLeft();

		double dblVariateRight = _ib.getVariateRight();

		if (((dblOFLeft - _dblOFGoal) * (dblOF - _dblOFGoal)) > 0.) {
			if (!_ib.setOFLeft (dblOF) || !_ib.setVariateLeft (dblVariate)) return false;

			dblContraRootOF = dblOFRight;
			dblContraRoot = dblVariateRight;
		} else if (((dblOFRight - _dblOFGoal) * (dblOF - _dblOFGoal)) > 0.) {
			if (!_ib.setOFRight (dblOF) || !_ib.setVariateRight (dblVariate)) return false;

			dblContraRootOF = dblOFLeft;
			dblContraRoot = dblVariateLeft;
		}

		try {
			dblVariate = iterateCompoundVariate (dblVariate, dblContraRoot, dblOF, dblContraRootOF, rfop);

			return iv.setVariate (dblVariate) && iv.setOF (_of.evaluate (dblVariate)) && rfop.incrOFCalcs();
		} catch (java.lang.Exception e) {
			if (_bWhine) e.printStackTrace();
		}

		return false;
	}

	@Override protected org.drip.function.r1tor1solver.ExecutionInitializationOutput initializeVariateZone (
		final org.drip.function.r1tor1solver.InitializationHeuristics ih)
	{
		org.drip.function.r1tor1solver.BracketingOutput bop = null;

		if (null != ih && org.drip.function.r1tor1solver.InitializationHeuristics.SEARCH_HARD_BRACKETS ==
			ih.getDeterminant())
			bop = _ei.verifyHardSearchEdges (ih, _dblOFGoal);
		else
			bop = _ei.initializeBracket (ih, _dblOFGoal);

		if (null == bop || !bop.isDone()) return null;

		try {
			_ib = new org.drip.function.r1tor1solver.IteratedBracket (bop);

			return bop;
		} catch (java.lang.Exception e) {
			if (_bWhine) e.printStackTrace();
		}

		return null;
	}

	/**
	 * FixedPointFinderBracketing constructor
	 * 
	 * @param dblOFGoal OF Goal
	 * @param of Objective Function
	 * @param ec Execution Control
	 * @param iIteratorPrimitive Iterator Primitive
	 * @param bWhine TRUE - Balk on Encountering Exception
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public FixedPointFinderBracketing (
		final double dblOFGoal,
		final org.drip.function.definition.R1ToR1 of,
		final org.drip.function.r1tor1solver.ExecutionControl ec,
		final int iIteratorPrimitive,
		final boolean bWhine)
		throws java.lang.Exception
	{
		super (dblOFGoal, of, ec, bWhine);

		if (org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION != (_iIteratorPrimitive =
			iIteratorPrimitive) && org.drip.function.r1tor1solver.VariateIteratorPrimitive.FALSE_POSITION !=
				_iIteratorPrimitive &&
					org.drip.function.r1tor1solver.VariateIteratorPrimitive.QUADRATIC_INTERPOLATION !=
						_iIteratorPrimitive &&
							org.drip.function.r1tor1solver.VariateIteratorPrimitive.INVERSE_QUADRATIC_INTERPOLATION
								!= _iIteratorPrimitive &&
									org.drip.function.r1tor1solver.VariateIteratorPrimitive.RIDDER !=
										_iIteratorPrimitive)
			throw new java.lang.Exception ("FixedPointFinderBracketing constructor: Invalid inputs!");

		_ei = new org.drip.function.r1tor1solver.ExecutionInitializer (_of, null, true);
	}
}
