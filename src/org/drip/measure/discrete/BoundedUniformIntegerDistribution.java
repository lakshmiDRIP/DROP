
package org.drip.measure.discrete;

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
 * BoundedUniformIntegerDistribution implements the Univariate Bounded Uniform Integer Distribution, with the
 *  Integer being generated between a(n inclusive) lower and an upper Bound.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundedUniformIntegerDistribution extends org.drip.measure.continuous.R1 {
	private int _iStart = -1;
	private int _iFinish = -1;

	/**
	 * Construct a Univariate Bounded Uniform Integer Distribution
	 * 
	 * @param iStart The Starting Integer
	 * @param iFinish The Finishing Integer
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public BoundedUniformIntegerDistribution (
		final int iStart,
		final int iFinish)
		throws java.lang.Exception
	{
		if ((_iFinish = iFinish) <= (_iStart = iStart))
			throw new java.lang.Exception ("BoundedUniformIntegerDistribution constructor: Invalid inputs");
	}

	/**
	 * Retrieve the Start
	 * 
	 * @return The Start
	 */

	public int start()
	{
		return _iStart;
	}

	/**
	 * Retrieve the Finish
	 * 
	 * @return The Finish
	 */

	public int finish()
	{
		return _iFinish;
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception
				("BoundedUniformIntegerDistribution::cumulative => Invalid inputs");

		if (dblX <= _iStart) return 0.;

		if (dblX >= _iFinish) return 1.;

		return (dblX - _iStart) / (_iFinish - _iStart);
	}

	@Override public double incremental (
		final double dblXLeft,
		final double dblXRight)
		throws java.lang.Exception
	{
		return cumulative (dblXRight) - cumulative (dblXLeft);
	}

	@Override public double invCumulative (
		final double dblY)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY) || dblY < 0. || dblY > 1.)
			throw new java.lang.Exception
				("BoundedUniformIntegerDistribution::invCumulative => Invalid inputs");

	    return dblY * (_iFinish - _iStart) + _iStart;
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		throw new java.lang.Exception
			("BoundedUniformIntegerDistribution::density => Not available for discrete distributions");
	}

	@Override public double mean()
	{
	    return 0.5 * (_iFinish + _iStart);
	}

	@Override public double variance()
	{
	    return (_iFinish - _iStart) * (_iFinish - _iStart) / 12.;
	}

	@Override public org.drip.quant.common.Array2D histogram()
	{
		int iGridWidth = _iFinish - _iStart;
		double[] adblX = new double[iGridWidth];
		double[] adblY = new double[iGridWidth];

		for (int i = 0; i < iGridWidth; ++i) {
			adblY[i] = 1. / iGridWidth;
			adblX[i] = _iStart + (i + 1);
		}

		try {
			
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
