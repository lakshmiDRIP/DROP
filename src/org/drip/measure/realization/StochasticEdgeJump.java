
package org.drip.measure.realization;

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
 * StochasticEdgeJump holds the Edge of the Jump Stochastic Evaluator Outcome.
 *
 * @author Lakshmi Krishnamurthy
 */

public class StochasticEdgeJump {
	private boolean _bOccurred = false;
	private double _dblTarget = java.lang.Double.NaN;
	private double _dblHazardRate = java.lang.Double.NaN;
	private double _dblHazardIntegral = java.lang.Double.NaN;

	/**
	 * StochasticEdgeJump Constructor
	 * 
	 * @param bOccurred TRUE - The Jump Occurred in this Edge Period
	 * @param dblHazardRate The Hazard Rate
	 * @param dblHazardIntegral The Level Hazard Integral
	 * @param dblTarget The Jump Target
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StochasticEdgeJump (
		final boolean bOccurred,
		final double dblHazardRate,
		final double dblHazardIntegral,
		final double dblTarget)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblHazardRate = dblHazardRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblHazardIntegral = dblHazardIntegral) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblTarget = dblTarget))
			throw new java.lang.Exception ("StochasticEdgeJump Constructor => Invalid Inputs");

		_bOccurred = bOccurred;
	}

	/**
	 * Retrieve the "Jump Occurred in this Level Period" Flag
	 * 
	 * @return The "Jump Occurred in this Level Period" Flag
	 */

	public final boolean jumpOccurred()
	{
		return _bOccurred;
	}

	/**
	 * Retrieve the Jump Occurrence Probability Density
	 * 
	 * @return The Jump Occurrence Probability Density
	 */

	public final double hazardRate()
	{
		return _dblHazardRate;
	}

	/**
	 * Retrieve the Jump Occurrence Hazard Integral
	 * 
	 * @return The Jump Occurrence Hazard Integral
	 */

	public final double hazardIntegral()
	{
		return _dblHazardIntegral;
	}

	/**
	 * Retrieve the Jump Target Value
	 * 
	 * @return The Jump Target Value
	 */

	public final double target()
	{
		return _dblTarget;
	}
}
