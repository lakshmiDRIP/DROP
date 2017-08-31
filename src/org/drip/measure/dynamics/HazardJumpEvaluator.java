
package org.drip.measure.dynamics;


/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * HazardJumpEvaluator implements the Hazard Jump Process Point Event Indication Evaluator that guides the
 *  Single Factor Jump-Termination Random Process Variable Evolution.
 *
 * @author Lakshmi Krishnamurthy
 */

public class HazardJumpEvaluator extends org.drip.measure.dynamics.SingleJumpEvaluator {
	private double _dblMagnitude = java.lang.Double.NaN;
	private double _dblHazardRate = java.lang.Double.NaN;

	/**
	 * Generate a Standard Instance of HazardJumpEvaluator
	 * 
	 * @param dblHazardRate The Hazard Rate
	 * @param dblMagnitude The Magnitude
	 * 
	 * @return The Standard Instance of HazardJumpEvaluator
	 */

	public static final HazardJumpEvaluator Standard (
		final double dblHazardRate,
		final double dblMagnitude)
	{
		org.drip.measure.dynamics.LocalEvaluator leDensity = new org.drip.measure.dynamics.LocalEvaluator() {
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				return -1. * dblHazardRate * java.lang.Math.exp (-1. * dblHazardRate);
			}
		};

		org.drip.measure.dynamics.LocalEvaluator leMagnitude = new org.drip.measure.dynamics.LocalEvaluator()
		{
			@Override public double value (
				final org.drip.measure.realization.JumpDiffusionVertex jdv)
				throws java.lang.Exception
			{
				return dblMagnitude;
			}
		};

		try {
			return new HazardJumpEvaluator (dblHazardRate, dblMagnitude, leDensity, leMagnitude);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private HazardJumpEvaluator (
		final double dblHazardRate,
		final double dblMagnitude,
		final org.drip.measure.dynamics.LocalEvaluator leDensity,
		final org.drip.measure.dynamics.LocalEvaluator leMagnitude)
		throws java.lang.Exception
	{
		super (leDensity, leMagnitude);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblHazardRate = dblHazardRate) || 0. > _dblHazardRate
			|| !org.drip.quant.common.NumberUtil.IsValid (_dblMagnitude = dblMagnitude) || 0. >
				_dblMagnitude)
			throw new java.lang.Exception ("HazardJumpEvaluator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Hazard Rate
	 * 
	 * @return The Hazard Rate
	 */

	public double hazardRate()
	{
		return _dblHazardRate;
	}

	/**
	 * Retrieve the Magnitude
	 * 
	 * @return The Magnitude
	 */

	public double magnitude()
	{
		return _dblMagnitude;
	}
}
