
package org.drip.measure.gaussian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * R1UnivariateNormal implements the Univariate R^1 Normal Distribution. It implements the Incremental, the
 *  Cumulative, and the Inverse Cumulative Distribution Densities.
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateNormal extends org.drip.measure.continuous.R1 {
	private double _dblMean = java.lang.Double.NaN;
	private double _dblSigma = java.lang.Double.NaN;

	/**
	 * Generate a N (0, 1) distribution
	 * 
	 * @return The N (0, 1) distribution
	 */

	public static final org.drip.measure.gaussian.R1UnivariateNormal Standard()
	{
		try {
			return new R1UnivariateNormal (0., 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a R1 Normal/Gaussian Distribution
	 * 
	 * @param dblMean Mean of the Distribution
	 * @param dblSigma Sigma of the Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public R1UnivariateNormal (
		final double dblMean,
		final double dblSigma)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblMean = dblMean) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblSigma = dblSigma) || 0. > _dblSigma)
			throw new java.lang.Exception ("R1UnivariateNormal Constructor: Invalid Inputs");
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1UnivariateNormal::cumulative => Invalid Inputs");

		if (0. == _dblSigma) return dblX >= _dblMean ? 1. : 0.;

		return org.drip.measure.gaussian.NormalQuadrature.CDF ((dblX - _dblMean) / _dblSigma);
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY) || 0. == _dblSigma)
			throw new java.lang.Exception ("R1UnivariateNormal::invCumulative => Cannot calculate");

	    return org.drip.measure.gaussian.NormalQuadrature.InverseCDF (dblY) * _dblSigma + _dblMean;
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1UnivariateNormal::density => Invalid Inputs");

		if (0. == _dblSigma) return dblX == _dblMean ? 1. : 0.;

		double dblMeanShift = (dblX - _dblMean) / _dblSigma;

		return java.lang.Math.exp (-0.5 * dblMeanShift * dblMeanShift);
	}

	@Override public double mean()
	{
	    return _dblMean;
	}

	@Override public double variance()
	{
	    return _dblSigma * _dblSigma;
	}

	@Override public org.drip.quant.common.Array2D histogram()
	{
		return null;
	}

	/**
	 * Compute the Error Function Around an Absolute Width around the Mean
	 * 
	 * @param dblX The Width
	 * 
	 * @return The Error Function Around an Absolute Width around the Mean
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double errorFunction (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1UnivariateNormal::errorFunction => Invalid Inputs");

		double dblWidth = java.lang.Math.abs (dblX);

		return cumulative (_dblMean + dblWidth) - cumulative (_dblMean - dblWidth);
	}

	/**
	 * Compute the Confidence given the Width around the Mean
	 * 
	 * @param dblWidth The Width
	 * 
	 * @return The Error Function Around an Absolute Width around the Mean
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double confidence (
		final double dblWidth)
		throws java.lang.Exception
	{
		return errorFunction (dblWidth);
	}

	/**
	 * Compute the Width around the Mean given the Confidence Level
	 * 
	 * @param dblConfidence The Confidence Level
	 * 
	 * @return The Width around the Mean given the Confidence Level
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double confidenceInterval (
		final double dblConfidence)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblConfidence) || 0. >= dblConfidence || 1. <=
			dblConfidence)
			throw new java.lang.Exception ("R1UnivariateNormal::confidenceInterval => Invalid Inputs");

		return invCumulative (0.5 * (1. + dblConfidence));
	}
}
