
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
 * PoissonDistribution implements the Univariate Poisson Distribution using the specified Mean/Variance.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PoissonDistribution extends org.drip.measure.continuous.R1 {
	private double _dblLambda = java.lang.Double.NaN;
	private double _dblExponentialLambda = java.lang.Double.NaN;

	/**
	 * Construct a PoissonDistribution Instance
	 * 
	 * @param dblLambda Lambda
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public PoissonDistribution (
		final double dblLambda)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLambda = dblLambda) || 0. >= _dblLambda)
			throw new java.lang.Exception ("PoissonDistribution constructor: Invalid inputs");

		_dblExponentialLambda = java.lang.Math.exp (-1. * _dblLambda);
	}

	/**
	 * Retrieve Lambda
	 * 
	 * @return Lambda
	 */

	public double lambda()
	{
		return _dblLambda;
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("PoissonDistribution::cumulative => Invalid inputs");

		int iEnd = (int) dblX;
		double dblYLocal = 1.;
		double dblYCumulative = 0.;

		for (int i = 1; i < iEnd; ++i) {
			i = i + 1;
			dblYLocal *= _dblLambda / i;
			dblYCumulative += _dblExponentialLambda * dblYLocal;
		}

		return dblYCumulative;
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY))
			throw new java.lang.Exception ("PoissonDistribution::invCumulative => Invalid inputs");

		int i = 0;
		double dblYLocal = 1.;
		double dblYCumulative = 0.;

		while (dblYCumulative < dblY) {
			i = i + 1;
			dblYLocal *= _dblLambda / i;
			dblYCumulative += _dblExponentialLambda * dblYLocal;
		}

		return i - 1;
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		throw new java.lang.Exception
			("PoissonDistribution::density => Not available for discrete distributions");
	}

	@Override public double mean()
	{
	    return _dblLambda;
	}

	@Override public double variance()
	{
	    return _dblLambda;
	}

	@Override public org.drip.quant.common.Array2D histogram()
	{
		return null;
	}
}
