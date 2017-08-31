
package org.drip.function.r1tor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * FixedPointFinderBracketing customizes the FixedPointFinder for bracketing based fixed point finder
 * 	functionality.
 * 
 * FixedPointFinderBracketing applies the following customization:
 * 	- Initializes the fixed point finder by computing the starting brackets
 * 	- Iterating the next search variate using one of the specified variate iterator primitives.
 * 
 * By default, FixedPointFinderBracketing does not do compound iterations of the variate using any schemes -
 * 	that is done by classes that extend it.
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
