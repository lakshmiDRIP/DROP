
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
 * UnitSequenceAgnosticMetrics contains the Sample Distribution Metrics and Agnostic Bounds related to the
 *  specified Bounded [0, 1] Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnitSequenceAgnosticMetrics extends org.drip.sequence.metrics.BoundedSequenceAgnosticMetrics
{
	private double _dblPopulationMean = java.lang.Double.NaN;

	/**
	 * UnitSequenceAgnosticMetrics Constructor
	 * 
	 * @param adblSequence The Random Sequence
	 * @param dblPopulationMean The Mean of the Underlying Distribution
	 * 
	 * @throws java.lang.Exception Thrown if UnitSequenceAgnosticMetrics cannot be constructed
	 */

	public UnitSequenceAgnosticMetrics (
		final double[] adblSequence,
		final double dblPopulationMean)
		throws java.lang.Exception
	{
		super (adblSequence, null, 1.);

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
	 * Compute the Chernoff Binomial Upper Bound
	 * 
	 * @param dblLevel The Level at which the Bound is sought
	 * 
	 * @return The Chernoff Binomial Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Chernoff Binomial Upper Bound cannot be computed
	 */

	public double chernoffBinomialUpperBound (
		final double dblLevel)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLevel) || 1. < dblLevel)
			throw new java.lang.Exception
				("UnitSequenceAgnosticMetrics::chernoffBinomialUpperBound => Invalid Inputs");

		int iNumEntry = sequence().length;

		double dblPopulationMean = org.drip.quant.common.NumberUtil.IsValid (_dblPopulationMean) ?
			_dblPopulationMean : empiricalExpectation();

		double dblBound = java.lang.Math.pow (dblPopulationMean / dblLevel, iNumEntry * dblLevel) *
			java.lang.Math.pow ((1. - dblPopulationMean) / (1. - dblLevel), iNumEntry * (1. - dblLevel));

		if (!org.drip.quant.common.NumberUtil.IsValid (dblBound)) return 0.;

		return dblBound > 1. ? 1. : dblBound;
	}

	/**
	 * Compute the Chernoff-Poisson Binomial Upper Bound
	 * 
	 * @param dblLevel The Level at which the Bound is sought
	 * 
	 * @return The Chernoff-Poisson Binomial Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Chernoff-Poisson Binomial Upper Bound cannot be computed
	 */

	public double chernoffPoissonUpperBound (
		final double dblLevel)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLevel) || 1. < dblLevel)
			throw new java.lang.Exception
				("UnitSequenceAgnosticMetrics::ChernoffBinomialUpperBound => Invalid Inputs");

		int iNumEntry = sequence().length;

		double dblPopulationMean = org.drip.quant.common.NumberUtil.IsValid (_dblPopulationMean) ?
			_dblPopulationMean : empiricalExpectation();

		double dblBound = java.lang.Math.pow (dblPopulationMean / dblLevel, iNumEntry * dblLevel) *
			java.lang.Math.exp (iNumEntry * (dblLevel - dblPopulationMean));

		if (!org.drip.quant.common.NumberUtil.IsValid (dblBound)) return 0.;

		return dblBound > 1. ? 1. : dblBound;
	}

	/**
	 * Compute the Karp/Hagerup/Rub Pivot Departure Bounds outlined below:
	 * 
	 * 	- Karp, R. M. (1988): Probabilistic Analysis of Algorithms, University of California, Berkeley.
	 * 	- Hagerup, T., and C. Rub (1990): A Guided Tour of Chernoff Bounds, Information Processing Letters,
	 * 		33:305-308.
	 * 
	 * @param dblLevel The Level at which the Bound is sought
	 * 
	 * @return The Karp/Hagerup/Rub Pivot Departure Bounds
	 */

	public org.drip.sequence.metrics.PivotedDepartureBounds karpHagerupRubBounds (
		final double dblLevel)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLevel) || 1. < dblLevel) return null;

		int iNumEntry = sequence().length;

		double dblPopulationMean = org.drip.quant.common.NumberUtil.IsValid (_dblPopulationMean) ?
			_dblPopulationMean : empiricalExpectation();

		double dblScaledLevel = dblLevel / dblPopulationMean;

		double dblLowerBound = java.lang.Math.exp (-0.5 * dblPopulationMean * iNumEntry * dblScaledLevel *
			dblScaledLevel);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblLowerBound)) dblLowerBound = 0.;

		double dblUpperBound = java.lang.Math.exp (-1. * dblPopulationMean * iNumEntry * (1. +
			dblScaledLevel) * java.lang.Math.log (1. + dblScaledLevel) - dblScaledLevel);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblUpperBound)) dblUpperBound = 0.;

		try {
			return new org.drip.sequence.metrics.PivotedDepartureBounds
				(org.drip.sequence.metrics.PivotedDepartureBounds.PIVOT_ANCHOR_TYPE_MEAN,
					java.lang.Double.NaN, dblLowerBound > 1. ? 1. : dblLowerBound, dblUpperBound > 1. ? 1. :
						dblUpperBound);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
