
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
 * FixedPointFinderBrent customizes FixedPointFinderBracketing by applying the Brent's scheme of compound
 * 	variate selector.
 * 
 * Brent's scheme, as implemented here, is described in http://www.credit-trader.org. This implementation
 * 	retains absolute shifts that have happened to the variate for the past 2 iterations as the discriminant
 * 	that determines the next variate to be generated.
 * 
 * FixedPointFinderBrent uses the following parameters specified in VariateIterationSelectorParams:
 * 	- The Variate Primitive that is regarded as the "fast" method
 * 	- The Variate Primitive that is regarded as the "robust" method
 * 	- The relative variate shift that determines when the "robust" method is to be invoked over the "fast"
 * 	- The lower bound on the variate shift between iterations that serves as the fall-back to the "robust"
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedPointFinderBrent extends org.drip.function.r1tor1solver.FixedPointFinderBracketing {
	private double _dblVariateIterativeShift = java.lang.Double.NaN;
	private double _dblPreviousVariateIterativeShift = java.lang.Double.NaN;
	private org.drip.function.r1tor1solver.VariateIterationSelectorParams _visp = null;

	@Override protected double iterateCompoundVariate (
		final double dblCurrentVariate,
		final double dblContraVariate,
		final double dblCurrentOF,
		final double dblContraPointOF,
		final org.drip.function.r1tor1solver.FixedPointFinderOutput rfop)
		throws java.lang.Exception
	{
		double dblNextVariate = calcNextVariate (dblCurrentVariate, dblContraVariate, dblCurrentOF,
			dblContraPointOF, _visp.getFastVariateIteratorPrimitive(), rfop);

		double dblVariateEstimateShift = java.lang.Math.abs (dblNextVariate - dblCurrentVariate);

		if (org.drip.quant.common.NumberUtil.IsValid (_dblVariateIterativeShift) ||
			_visp.getRobustVariateIteratorPrimitive() == _iIteratorPrimitive) {
			if (dblVariateEstimateShift < _visp.getRelativeVariateShift() * _dblVariateIterativeShift &&
				_dblVariateIterativeShift > 0.5 * _visp.getVariateShiftLowerBound()) {
				_iIteratorPrimitive = _visp.getFastVariateIteratorPrimitive();

				_dblPreviousVariateIterativeShift = _dblVariateIterativeShift;
				_dblVariateIterativeShift = dblVariateEstimateShift;
				return dblNextVariate;
			}

			_iIteratorPrimitive = _visp.getRobustVariateIteratorPrimitive();

			_dblPreviousVariateIterativeShift = _dblVariateIterativeShift;
			_dblVariateIterativeShift = dblVariateEstimateShift;

			return calcNextVariate (dblCurrentVariate, dblContraVariate, dblCurrentOF, dblContraPointOF,
				_visp.getRobustVariateIteratorPrimitive(), rfop);
		}

		if (org.drip.quant.common.NumberUtil.IsValid (_dblPreviousVariateIterativeShift) &&
			(dblVariateEstimateShift < _visp.getRelativeVariateShift() * _dblPreviousVariateIterativeShift &&
				_dblPreviousVariateIterativeShift > 0.5 * _visp.getVariateShiftLowerBound())) {
			_iIteratorPrimitive = _visp.getFastVariateIteratorPrimitive();

			_dblPreviousVariateIterativeShift = _dblVariateIterativeShift;
			_dblVariateIterativeShift = dblVariateEstimateShift;
			return dblNextVariate;
		}

		_iIteratorPrimitive = _visp.getRobustVariateIteratorPrimitive();

		_dblPreviousVariateIterativeShift = _dblVariateIterativeShift;
		_dblVariateIterativeShift = dblVariateEstimateShift;

		return calcNextVariate (dblCurrentVariate, dblContraVariate, dblCurrentOF, dblContraPointOF,
			_visp.getRobustVariateIteratorPrimitive(), rfop);
	}

	/**
	 * FixedPointFinderBrent constructor
	 * 
	 * @param dblOFGoal OF Goal
	 * @param of Objective Function
	 * @param bWhine TRUE - Balk on Encountering Exception
	 * 
	 * @throws java.lang.Exception Propogated from below
	 */

	public FixedPointFinderBrent (
		final double dblOFGoal,
		final org.drip.function.definition.R1ToR1 of,
		final boolean bWhine)
		throws java.lang.Exception
	{
		super (dblOFGoal, of, null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.BISECTION, bWhine);

		_visp = new org.drip.function.r1tor1solver.VariateIterationSelectorParams();
	}
}
