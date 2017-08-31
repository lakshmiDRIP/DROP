
package org.drip.sequence.random;

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
 * BoxMullerGaussian implements the Univariate Gaussian Random Number Generator.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoxMullerGaussian extends org.drip.sequence.random.UnivariateSequenceGenerator {
	private double _dblMean = java.lang.Double.NaN;
	private double _dblSigma = java.lang.Double.NaN;
	private double _dblVariance = java.lang.Double.NaN;

	private java.util.Random _rng = new java.util.Random();

	/**
	 * BoxMullerGaussian Constructor
	 * 
	 * @param dblMean The Mean
	 * @param dblVariance The Variance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BoxMullerGaussian (
		final double dblMean,
		final double dblVariance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblMean = dblMean) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblVariance = dblVariance) || _dblVariance <= 0.)
			throw new java.lang.Exception ("BoxMullerGaussian ctr: Invalid Inputs");

		_dblSigma = java.lang.Math.sqrt (_dblVariance);
	}

	/**
	 * Retrieve the Mean of the Box-Muller Gaussian
	 * 
	 * @return Mean of the Box-Muller Gaussian
	 */

	public double mean()
	{
		return _dblMean;
	}

	/**
	 * Retrieve the Variance of the Box-Muller Gaussian
	 * 
	 * @return Variance of the Box-Muller Gaussian
	 */

	public double variance()
	{
		return _dblVariance;
	}

	@Override public double random()
	{
		return _dblMean + _dblSigma * java.lang.Math.sqrt (-2. * java.lang.Math.log (_rng.nextDouble())) *
			java.lang.Math.cos (2. * java.lang.Math.PI * _rng.nextDouble());
	}
}
