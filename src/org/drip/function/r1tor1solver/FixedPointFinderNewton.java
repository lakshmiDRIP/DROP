
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
 * FixedPointFinderNewton customizes the FixedPointFinder for Open (Newton's) fixed point finder
 * 	functionality.
 * 
 * FixedPointFinderNewton applies the following customization:
 * 	- Initializes the fixed point finder by computing a starting variate in the convergence zone
 * 	- Iterating the next search variate using the Newton's method.
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
