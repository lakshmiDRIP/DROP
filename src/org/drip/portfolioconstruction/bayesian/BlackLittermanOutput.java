
package org.drip.portfolioconstruction.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * BlackLittermanOutput holds the essential Outputs generated from either a Full or a Custom Confidence of
 * 	the Projection Black Litterman Bayesian Combination Run. The References are:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *  
 *  - Idzorek, T. (2005): A Step-by-Step Guide to the Black-Litterman Model: Incorporating User-Specified
 *  	Confidence Levels, Ibbotson Associates, Chicago
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackLittermanOutput {
	private double[] _adblAllocationAdjustmentTilt = null;
	private org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput _frooAdjusted = null;

	/**
	 * BlackLittermanOutput Constructor
	 * 
	 * @param frooAdjusted The Black Litterman Adjusted Forward Reverse Equilibrium Optimization Output
	 * @param adblAllocationAdjustmentTilt Array of the Allocation Adjustment Tilts
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BlackLittermanOutput (
		final org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput frooAdjusted,
		final double[] adblAllocationAdjustmentTilt)
		throws java.lang.Exception
	{
		if (null == (_frooAdjusted = frooAdjusted) || null == (_adblAllocationAdjustmentTilt =
			adblAllocationAdjustmentTilt) || 0 == _adblAllocationAdjustmentTilt.length)
			throw new java.lang.Exception ("BlackLittermanOutput Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Adjusted Forward Equilibrium Optimization Metrics
	 * 
	 * @return The Adjusted Forward Equilibrium Optimization Metrics
	 */

	public org.drip.portfolioconstruction.allocator.ForwardReverseOptimizationOutput adjustedMetrics()
	{
		return _frooAdjusted;
	}

	/**
	 * Retrieve the Array of the Black Litterman Allocation Adjustment Tilts
	 * 
	 * @return The Array of the Black Litterman Allocation Adjustment Tilts
	 */

	public double[] allocationAdjustmentTilt()
	{
		return _adblAllocationAdjustmentTilt;
	}
}
