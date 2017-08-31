
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
 * ConvergenceControlParams holds the fields needed for the controlling the execution of Newton's method.
 * 
 * ConvergenceControlParams does that using the following parameters:
 * 	- The determinant limit below which the convergence zone is deemed to have been reached.
 * 	- Starting variate from where the convergence search is kicked off.
 * 	- The factor by which the variate expands across each iterative search.
 * 	- The number of search iterations.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConvergenceControlParams {
	/*
	 * Convergence Zone Locator Determination
	 */

	private int _iFixedPointConvergenceIterations = 0;
	private double _dblConvergenceZoneEdgeLimit = java.lang.Double.NaN;
	private double _dblConvergenceZoneVariateBegin = java.lang.Double.NaN;
	private double _dblConvergenceZoneVariateBumpFactor = java.lang.Double.NaN;

	/**
	 * Default Convergence Control Parameters constructor
	 */

	public ConvergenceControlParams()
	{
		/*
		 * Convergence Zone Locator Determination Initialization
		 */

		_iFixedPointConvergenceIterations = 100;
		_dblConvergenceZoneEdgeLimit = 0.01;
		_dblConvergenceZoneVariateBegin = 1.0e-30;
		_dblConvergenceZoneVariateBumpFactor = 3.;
	}

	/**
	 * ConvergenceControlParams constructor
	 * 
	 * @param iFixedPointConvergenceIterations Iterations to locate a variate inside the convergence zone
	 * @param dblConvergenceZoneVariateBegin Starting variate for convergence zone determination
	 * @param dblConvergenceZoneEdgeLimit Convergence zone edge limit
	 * @param dblConvergenceZoneVariateBumpFactor Convergence Zone Variate Bump Factor
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public ConvergenceControlParams (
		final int iFixedPointConvergenceIterations,
		final double dblConvergenceZoneVariateBegin,
		final double dblConvergenceZoneEdgeLimit,
		final double dblConvergenceZoneVariateBumpFactor)
		throws java.lang.Exception
	{
		if (0 >= (_iFixedPointConvergenceIterations = iFixedPointConvergenceIterations) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblConvergenceZoneVariateBegin =
				dblConvergenceZoneVariateBegin) || !org.drip.quant.common.NumberUtil.IsValid
					(_dblConvergenceZoneEdgeLimit = dblConvergenceZoneEdgeLimit) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblConvergenceZoneVariateBumpFactor =
							dblConvergenceZoneVariateBumpFactor))
			throw new java.lang.Exception ("ConvergenceControlParams constructor: Invalid inputs");
	}

	/**
	 * Return the number of fixed point convergence iterations
	 * 
	 * @return Number of fixed point convergence iterations
	 */

	public int getFixedPointConvergenceIterations()
	{
		return _iFixedPointConvergenceIterations;
	}

	/**
	 * Return the limit of the fixed point convergence zone edge
	 * 
	 * @return Limit of fixed point convergence zone edge
	 */

	public double getConvergenceZoneEdgeLimit()
	{
		return _dblConvergenceZoneEdgeLimit;
	}

	/**
	 * Return the start of the fixed point convergence variate
	 * 
	 * @return Start of the fixed point convergence variate
	 */

	public double getConvergenceZoneVariateBegin()
	{
		return _dblConvergenceZoneVariateBegin;
	}

	/**
	 * Return the bump factor for the fixed point convergence variate iteration
	 * 
	 * @return Bump factor for the fixed point convergence variate iteration
	 */

	public double getConvergenceZoneVariateBumpFactor()
	{
		return _dblConvergenceZoneVariateBumpFactor;
	}
}
