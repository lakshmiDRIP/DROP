
package org.drip.sequence.metrics;

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
 * PoissonSequenceAgnosticMetrics contains the Sample Distribution Metrics and Agnostic Bounds related to the
 *  specified Poisson Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PoissonSequenceAgnosticMetrics extends org.drip.sequence.metrics.SingleSequenceAgnosticMetrics {
	private double _dblPopulationMean = java.lang.Double.NaN;

	/**
	 * PoissonSequenceAgnosticMetrics Constructor
	 * 
	 * @param adblSequence The Random Sequence
	 * @param dblPopulationMean The Mean of the Underlying Distribution
	 * 
	 * @throws java.lang.Exception Thrown if PoissonSequenceAgnosticMetrics cannot be constructed
	 */

	public PoissonSequenceAgnosticMetrics (
		final double[] adblSequence,
		final double dblPopulationMean)
		throws java.lang.Exception
	{
		super (adblSequence, null);

		_dblPopulationMean = dblPopulationMean;
	}

	/**
	 * Retrieve the Mean of the Underlying Distribution
	 * 
	 * @return The Mean of the Underlying Distribution
	 */

	public double populationMean()
	{
		return _dblPopulationMean;
	}

	/**
	 * Compute the Chernoff-Stirling Upper Bound
	 * 
	 * @param dblLevel The Level at which the Bound is sought
	 * 
	 * @return The Chernoff-Stirling Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Chernoff-Stirling Upper Bound cannot be computed
	 */

	public double chernoffStirlingUpperBound (
		final double dblLevel)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLevel))
			throw new java.lang.Exception
				("PoissonSequenceAgnosticMetrics::chernoffStirlingUpperBound => Invalid Inputs");

		int iNumEntry = sequence().length;

		double dblPopulationMean = org.drip.quant.common.NumberUtil.IsValid (_dblPopulationMean) ?
			_dblPopulationMean : empiricalExpectation();

		double dblBound = (java.lang.Math.pow (dblPopulationMean / dblLevel, iNumEntry * dblLevel) *
			java.lang.Math.exp (iNumEntry * (dblLevel - dblPopulationMean) - (1. / (12. * iNumEntry *
				dblLevel + 1.)))) / java.lang.Math.sqrt (2. * java.lang.Math.PI * iNumEntry * dblLevel);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblBound)) return 0.;

		return dblBound > 1. ? 1. : dblBound;
	}
}
