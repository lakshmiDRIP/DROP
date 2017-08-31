
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
 * IntegerSequenceAgnosticMetrics contains the Sample Distribution Metrics and Agnostic Bounds related to the
 *  specified Integer Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IntegerSequenceAgnosticMetrics extends
	org.drip.sequence.metrics.SingleSequenceAgnosticMetrics {

	/**
	 * Build out the Sequence and their Metrics
	 * 
	 * @param adblSequence Array of Sequence Entries
	 * @param distPopulation The True Underlying Generator Distribution of the Population
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IntegerSequenceAgnosticMetrics (
		final double[] adblSequence,
		final org.drip.measure.continuous.R1 distPopulation)
		throws java.lang.Exception
	{
		super (adblSequence, distPopulation);

		if (!isPositive())
			throw new java.lang.Exception
				("IntegerSequenceAgnosticMetrics ctr => Series should be non-Negative!");
	}

	/**
	 * Retrieve the Upper Bound on Probability of X gt 0
	 * 
	 * @return The Upper Bound on Probability of X gt 0
	 */

	public double probGreaterThanZeroUpperBound()
	{
		double dblPopulationMean = populationMean();

		double dblProb = org.drip.quant.common.NumberUtil.IsValid (dblPopulationMean) ? dblPopulationMean :
			empiricalExpectation();

		return dblProb > 1. ? 1 : dblProb;
	}

	/**
	 * Retrieve the Upper Bound on Probability of X = 0
	 * 
	 * @return The Upper Bound on Probability of X = 0
	 */

	public double probEqualToZeroUpperBound()
	{
		double dblPopulationMean = populationMean();

		double dblMean = org.drip.quant.common.NumberUtil.IsValid (dblPopulationMean) ? dblPopulationMean :
			empiricalExpectation();

		double dblPopulationVariance = populationVariance();

		double dblVariance = org.drip.quant.common.NumberUtil.IsValid (dblPopulationVariance) ?
			dblPopulationVariance : empiricalVariance();

		return dblVariance / (dblMean * dblMean + dblVariance);
	}
}
