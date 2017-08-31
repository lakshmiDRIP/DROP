
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
 * JumpDiffusionVertex holds the Snapshot Values of the Realized R^1 Variable - its Value, whether it has
 *  terminated, and the Cumulative Hazard Integral - and Time.
 *
 * @author Lakshmi Krishnamurthy
 */

public class JumpDiffusionVertex {
	private boolean _bJumpOccurred = false;
	private double _dblTime = java.lang.Double.NaN;
	private double _dblValue = java.lang.Double.NaN;
	private double _dblCumulativeHazardIntegral = java.lang.Double.NaN;

	/**
	 * JumpDiffusionVertex Constructor
	 * 
	 * @param dblTime The Time Instant
	 * @param dblValue The Random Variable Value
	 * @param dblCumulativeHazardIntegral The Jump Occurrence Cumulative Hazard Integral
	 * @param bJumpOccurred TRUE - Jump Occurred
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionVertex (
		final double dblTime,
		final double dblValue,
		final double dblCumulativeHazardIntegral,
		final boolean bJumpOccurred)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblTime = dblTime) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblValue = dblValue) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblCumulativeHazardIntegral =
					dblCumulativeHazardIntegral))
			throw new java.lang.Exception ("JumpDiffusionVertex Constructor => Invalid Inputs");

		_bJumpOccurred = bJumpOccurred;
	}

	/**
	 * Retrieve the Evolution Time Instant
	 * 
	 * @return The Evolution Time Instant
	 */

	public double time()
	{
		return _dblTime;
	}

	/**
	 * Retrieve the Realized Random Value
	 * 
	 * @return The Realized Random Value
	 */

	public double value()
	{
		return _dblValue;
	}

	/**
	 * Retrieve the Jump Occurred Flag
	 * 
	 * @return TRUE - Jump Occurred
	 */

	public boolean jumpOccurred()
	{
		return _bJumpOccurred;
	}

	/**
	 * Retrieve the Jump Occurrence Cumulative Hazard Integral
	 * 
	 * @return The Jump Occurrence Cumulative Hazard Integral
	 */

	public final double cumulativeHazardIntegral()
	{
		return _dblCumulativeHazardIntegral;
	}
}
