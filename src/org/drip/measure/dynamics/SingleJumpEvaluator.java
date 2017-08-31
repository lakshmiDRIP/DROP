
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
 * SingleJumpEvaluator implements the Single Point Jump Event Indication Evaluator that guides the One Factor
 *  Jump Random Process Variable Evolution.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SingleJumpEvaluator {
	private org.drip.measure.dynamics.LocalEvaluator _leDensity = null;
	private org.drip.measure.dynamics.LocalEvaluator _leMagnitude = null;

	/**
	 * SingleJumpEvaluator Constructor
	 * 
	 * @param leDensity The Jump Density Evaluator
	 * @param leMagnitude The Jump Magnitude Evaluator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SingleJumpEvaluator (
		final org.drip.measure.dynamics.LocalEvaluator leDensity,
		final org.drip.measure.dynamics.LocalEvaluator leMagnitude)
		throws java.lang.Exception
	{
		if (null == (_leDensity = leDensity) || null == (_leMagnitude = leMagnitude))
			throw new java.lang.Exception ("SingleJumpEvaluator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Jump Density Evaluator
	 * 
	 * @return The Jump Density Evaluator
	 */

	public org.drip.measure.dynamics.LocalEvaluator densityEvaluator()
	{
		return _leDensity;
	}

	/**
	 * Retrieve the Jump Magnitude Evaluator
	 * 
	 * @return The Jump Magnitude Evaluator
	 */

	public org.drip.measure.dynamics.LocalEvaluator magnitudeEvaluator()
	{
		return _leMagnitude;
	}
}
