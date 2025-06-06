
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
 * that is done by classes that extend it.
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
