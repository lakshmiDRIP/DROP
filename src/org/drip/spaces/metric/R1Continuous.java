
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
 * R1Continuous implements the Normed, Bounded/Unbounded Continuous l^p R^1 Spaces.
 * 
 * The Reference we've used is:
 * 
 * 	- Carl, B., and I. Stephani (1990): Entropy, Compactness, and Approximation of Operators, Cambridge
 * 		University Press, Cambridge UK.
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Continuous extends org.drip.spaces.tensor.R1ContinuousVector implements
	org.drip.spaces.metric.R1Normed {
	private int _iPNorm = -1;
	private org.drip.measure.continuous.R1 _distR1 = null;

	/**
	 * Construct the Standard l^p R^1 Continuous Space Instance
	 * 
	 * @param dblLeftEdge The Left Edge
	 * @param dblRightEdge The Right Edge
	 * @param distR1 The R^1 Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return The Standard l^p R^1 Continuous Space Instance
	 */

	public static final R1Continuous Standard (
		final double dblLeftEdge,
		final double dblRightEdge,
		final org.drip.measure.continuous.R1 distR1,
		final int iPNorm)
	{
		try {
			return new R1Continuous (dblLeftEdge, dblRightEdge, distR1, iPNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Supremum (i.e., l^Infinity) R^1 Continuous Space Instance
	 * 
	 * @param dblLeftEdge The Left Edge
	 * @param dblRightEdge The Right Edge
	 * @param distR1 The R^1 Borel Sigma Measure
	 * 
	 * @return The Supremum (i.e., l^Infinity) R^1 Continuous Space Instance
	 */

	public static final R1Continuous Supremum (
		final double dblLeftEdge,
		final double dblRightEdge,
		final org.drip.measure.continuous.R1 distR1)
	{
		try {
			return new R1Continuous (dblLeftEdge, dblRightEdge, distR1, java.lang.Integer.MAX_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1Continuous Space Constructor
	 * 
	 * @param dblLeftEdge The Left Edge
	 * @param dblRightEdge The Right Edge
	 * @param distR1 The R^1 Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1Continuous (
		final double dblLeftEdge,
		final double dblRightEdge,
		final org.drip.measure.continuous.R1 distR1,
		final int iPNorm)
		throws java.lang.Exception
	{
		super (dblLeftEdge, dblRightEdge);

		if (0 > (_iPNorm = iPNorm))
			throw new java.lang.Exception ("R1Continuous Constructor: Invalid p-norm");

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
			throw new java.lang.Exception ("R1Continuous::sampleMetricNorm => Invalid Inputs");

		return java.lang.Math.abs (dblX);
	}

	@Override public double populationMode()
		throws java.lang.Exception
	{
		if (null == _distR1)
			throw new java.lang.Exception ("R1Continuous::populationMode => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcR1ToR1 = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return _distR1.density (dblX);
			}
		};

		org.drip.function.definition.VariateOutputPair vopMode = funcR1ToR1.maxima (leftEdge(), rightEdge());

		if (null == vopMode)
			throw new java.lang.Exception ("R1Continuous::populationMode => Cannot compute VOP Mode");

		return vopMode.variates()[0];
	}

	@Override public double populationMetricNorm()
		throws java.lang.Exception
	{
		if (null == _distR1)
			throw new java.lang.Exception ("R1Continuous::populationMetricNorm => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcR1ToR1 = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return sampleMetricNorm (dblX) * _distR1.density (dblX);
			}
		};

		return funcR1ToR1.integrate (leftEdge(), rightEdge());
	}

	@Override public double borelMeasureSpaceExpectation (
		final org.drip.function.definition.R1ToR1 funcR1ToR1)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || null == _distR1)
			throw new java.lang.Exception ("R1Continuous::borelMeasureSpaceExpectation => Invalid Inputs");

		org.drip.function.definition.R1ToR1 funcDensityR1ToR1 = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return funcR1ToR1.evaluate (dblX) * _distR1.density (dblX);
			}
		};

		return funcDensityR1ToR1.integrate (leftEdge(), rightEdge());
	}
}
