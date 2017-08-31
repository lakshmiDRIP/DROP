
package org.drip.spaces.metric;

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
 * R1Combinatorial implements the Normed, Bounded/Unbounded Combinatorial l^p R^1 Spaces.
 * 
 * The Reference we've used is:
 * 
 * 	- Carl, B., and I. Stephani (1990): Entropy, Compactness, and Approximation of Operators, Cambridge
 * 		University Press, Cambridge UK.
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Combinatorial extends org.drip.spaces.tensor.R1CombinatorialVector implements
	org.drip.spaces.metric.R1Normed {
	private int _iPNorm = -1;
	private org.drip.measure.continuous.R1 _distR1 = null;

	/**
	 * Construct the Standard l^p R^1 Combinatorial Space Instance
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R^1 Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return The Standard l^p R^1 Combinatorial Space Instance
	 */

	public static final R1Combinatorial Standard (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.continuous.R1 distR1,
		final int iPNorm)
	{
		try {
			return new R1Combinatorial (lsElementSpace, distR1, iPNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Supremum (i.e., l^Infinity) R^1 Combinatorial Space Instance
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R^1 Borel Sigma Measure
	 * 
	 * @return The Supremum (i.e., l^Infinity) R^1 Combinatorial Space Instance
	 */

	public static final R1Combinatorial Supremum (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.continuous.R1 distR1)
	{
		try {
			return new R1Combinatorial (lsElementSpace, distR1, java.lang.Integer.MAX_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1Combinatorial Space Constructor
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R^1 Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1Combinatorial (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.continuous.R1 distR1,
		final int iPNorm)
		throws java.lang.Exception
	{
		super (lsElementSpace);

		if (0 > (_iPNorm = iPNorm))
			throw new java.lang.Exception ("R1Combinatorial Constructor: Invalid p-norm");

		_distR1 = distR1;
	}

	@Override public int pNorm()
	{
		return _iPNorm;
	}

	@Override public org.drip.measure.continuous.R1 borelSigmaMeasure()
	{
		return _distR1;
	}

	@Override public double sampleMetricNorm (
		final double dblX)
		throws java.lang.Exception
	{
		if (!validateInstance (dblX))
			throw new java.lang.Exception ("R1Combinatorial::sampleMetricNorm => Invalid Inputs");

		return java.lang.Math.abs (dblX);
	}

	@Override public double populationMode()
		throws java.lang.Exception
	{
		if (null == _distR1)
			throw new java.lang.Exception ("R1Combinatorial::populationMode => Invalid Inputs");

		double dblMode = java.lang.Double.NaN;
		double dblModeProbability = java.lang.Double.NaN;

		for (double dblElement : elementSpace()) {
			if (!org.drip.quant.common.NumberUtil.IsValid (dblMode))
				dblModeProbability = _distR1.density (dblMode = dblElement);
			else {
				double dblElementProbability = _distR1.density (dblElement);

				if (dblElementProbability > dblModeProbability) {
					dblMode = dblElement;
					dblModeProbability = dblElementProbability;
				}
			}
		}

		return dblMode;
	}

	@Override public double populationMetricNorm()
		throws java.lang.Exception
	{
		if (null == _distR1)
			throw new java.lang.Exception ("R1Combinatorial::populationMetricNorm => Invalid Inputs");

		double dblNorm = 0.;
		double dblNormalizer = 0.;

		for (double dblElement : elementSpace()) {
			double dblElementProbability = _distR1.density (dblElement);

			dblNormalizer += dblElementProbability;

			dblNorm += sampleMetricNorm (dblElement) * dblElementProbability;
		}

		return dblNorm / dblNormalizer;
	}

	@Override public double borelMeasureSpaceExpectation (
		final org.drip.function.definition.R1ToR1 funcR1ToR1)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || null == _distR1)
			throw new java.lang.Exception
				("R1Combinatorial::borelMeasureSpaceExpectation => Invalid Inputs");

		double dblNormalizer = 0.;
		double dblBorelMeasureSpaceExpectation = 0.;

		for (double dblElement : elementSpace()) {
			double dblElementProbability = _distR1.density (dblElement);

			dblNormalizer += dblElementProbability;

			dblBorelMeasureSpaceExpectation += funcR1ToR1.evaluate (dblElement) * dblElementProbability;
		}

		return dblBorelMeasureSpaceExpectation / dblNormalizer;
	}
}
