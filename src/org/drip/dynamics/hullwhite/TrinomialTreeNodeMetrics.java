
package org.drip.dynamics.hullwhite;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * TrinomialTreeNodeMetrics records the Metrics associated with each Node in the Trinomial Tree Evolution of
 *  the Instantaneous Short Rate using the Hull-White Model.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TrinomialTreeNodeMetrics {
	private long _lTimeIndex = -1L;
	private long _lXStochasticIndex = 0L;
	private double _dblX = java.lang.Double.NaN;
	private double _dblAlpha = java.lang.Double.NaN;

	/**
	 * TrinomialTreeNodeMetrics Constructor
	 * 
	 * @param lTimeIndex The Tree Node's Time Index
	 * @param lXStochasticIndex The Tree Node's Stochastic Index
	 * @param dblX X
	 * @param dblAlpha Alpha
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TrinomialTreeNodeMetrics (
		final long lTimeIndex,
		final long lXStochasticIndex,
		final double dblX,
		final double dblAlpha)
		throws java.lang.Exception
	{
		if (0 > (_lTimeIndex = lTimeIndex) || !org.drip.quant.common.NumberUtil.IsValid (_dblX = dblX) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblAlpha = dblAlpha))
			throw new java.lang.Exception ("TrinomialTreeNodeMetrics ctr: Invalid Inputs");

		_lXStochasticIndex = lXStochasticIndex;
	}

	/**
	 * Retrieve the Node's X
	 * 
	 * @return The Node's X
	 */

	public double x()
	{
		return _dblX;
	}

	/**
	 * Retrieve the Node's Alpha
	 * 
	 * @return The Node's Alpha
	 */

	public double alpha()
	{
		return _dblAlpha;
	}

	/**
	 * Retrieve the Node's Short Rate
	 * 
	 * @return The Node's Short Rate
	 */

	public double shortRate()
	{
		return _dblX + _dblAlpha;
	}

	/**
	 * Retrieve the Tree Node's Time Index
	 * 
	 * @return The Time Index
	 */

	public long timeIndex()
	{
		return _lTimeIndex;
	}

	/**
	 * Retrieve the Tree Node's X Stochastic Index
	 * 
	 * @return The Tree Node's X Stochastic Index
	 */

	public long xStochasticIndex()
	{
		return _lXStochasticIndex;
	}
}
