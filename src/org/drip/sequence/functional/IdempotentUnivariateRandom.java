
package org.drip.sequence.functional;

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
 * IdempotentUnivariateRandom contains the Implementation of the OffsetIdempotent Objective Function
 *  dependent on Univariate Random Variable.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IdempotentUnivariateRandom extends org.drip.function.r1tor1.OffsetIdempotent {
	private org.drip.measure.continuous.R1 _dist = null;

	/**
	 * IdempotentUnivariateRandom Constructor
	 * 
	 * @param dblOffset The Idempotent Offset
	 * @param dist The Underlying Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public IdempotentUnivariateRandom (
		final double dblOffset,
		final org.drip.measure.continuous.R1 dist)
		throws java.lang.Exception
	{
		super (dblOffset);

		_dist = dist;
	}

	/**
	 * Generate the Function Metrics for the specified Variate Sequence and its corresponding Weight
	 * 
	 * @param adblVariateSequence The specified Variate Sequence
	 * @param adblVariateWeight The specified Variate Weight
	 * 
	 * @return The Function Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics sequenceMetrics (
		final double[] adblVariateSequence,
		final double[] adblVariateWeight)
	{
		if (null == adblVariateSequence || null == adblVariateWeight) return null;

		int iNumVariate = adblVariateSequence.length;
		double[] adblFunctionSequence = new double[iNumVariate];

		if (0 == iNumVariate || iNumVariate != adblVariateWeight.length) return null;

		try {
			for (int i = 0; i < iNumVariate; ++i)
				adblFunctionSequence[i] = adblVariateWeight[i] * evaluate (adblVariateSequence[i]);

			return new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics (adblFunctionSequence, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Function Metrics for the specified Variate Sequence
	 * 
	 * @param adblVariateSequence The specified Variate Sequence
	 * 
	 * @return The Function Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics sequenceMetrics (
		final double[] adblVariateSequence)
	{
		if (null == adblVariateSequence) return null;

		int iNumVariate = adblVariateSequence.length;
		double[] adblVariateWeight = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblVariateWeight[i] = 1.;

		return sequenceMetrics (adblVariateSequence, adblVariateWeight);
	}

	/**
	 * Generate the Function Metrics using the Underlying Variate Distribution
	 * 
	 * @return The Function Sequence Metrics
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics sequenceMetrics()
	{
		if (null == _dist) return null;

		org.drip.quant.common.Array2D a2DHistogram = _dist.histogram();

		return null == a2DHistogram ? null : sequenceMetrics (a2DHistogram.x(), a2DHistogram.y());
	}

	/**
	 * Retrieve the Underlying Distribution
	 * 
	 * @return The Underlying Distribution
	 */

	public org.drip.measure.continuous.R1 underlyingDistribution()
	{
		return _dist;
	}
}
