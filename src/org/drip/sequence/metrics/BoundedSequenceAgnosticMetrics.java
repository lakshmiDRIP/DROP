
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
 * BoundedSequenceAgnosticMetrics contains the Sample Distribution Metrics and Agnostic Bounds related to the
 *  specified Bounded Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundedSequenceAgnosticMetrics extends
	org.drip.sequence.metrics.SingleSequenceAgnosticMetrics {
	private double _dblSupport = java.lang.Double.NaN;

	/**
	 * BoundedSequenceAgnosticMetrics Constructor
	 * 
	 * @param adblSequence The Random Sequence
	 * @param distPopulation The Population Distribution
	 * @param dblSupport The Support of the Underlying Random Variable
	 * 
	 * @throws java.lang.Exception Thrown if BoundedSequenceAgnosticMetrics cannot be constructed
	 */

	public BoundedSequenceAgnosticMetrics (
		final double[] adblSequence,
		final org.drip.measure.continuous.R1 distPopulation,
		final double dblSupport)
		throws java.lang.Exception
	{
		super (adblSequence, distPopulation);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblSupport = dblSupport) || 0. >= _dblSupport)
			throw new java.lang.Exception ("BoundedSequenceAgnosticMetrics ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Random Sequence Support
	 * 
	 * @return The Support Underlying the Random Sequence
	 */

	public double support()
	{
		return _dblSupport;
	}

	/**
	 * Estimate Mean Departure Bounds of the Average using the Chernoff-Hoeffding Bound
	 * 
	 * @param dblLevel The Level at which the Departure is sought
	 * 
	 * @return The Mean Departure Bounds
	 */

	public org.drip.sequence.metrics.PivotedDepartureBounds chernoffHoeffdingAverageBounds (
		final double dblLevel)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLevel) || dblLevel <= 0) return null;

		double dblBound = java.lang.Math.exp (-2. * sequence().length * dblLevel * dblLevel / (_dblSupport *
			_dblSupport));

		dblBound = dblBound < 1. ? dblBound : 1.;

		try {
			return new org.drip.sequence.metrics.PivotedDepartureBounds
				(org.drip.sequence.metrics.PivotedDepartureBounds.PIVOT_ANCHOR_TYPE_MEAN,
					java.lang.Double.NaN, dblBound, dblBound);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Estimate Mean Departure Bounds of the Average using the Bennett Inequality Bounds
	 * 
	 * @param dblLevel The Level at which the Departure is sought
	 * 
	 * @return The Mean Departure Bounds
	 */

	public org.drip.sequence.metrics.PivotedDepartureBounds bennettAverageBounds (
		final double dblLevel)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLevel) || dblLevel <= 0.) return null;

		double dblPopulationVariance = populationVariance();

		double dblVariance = (org.drip.quant.common.NumberUtil.IsValid (dblPopulationVariance) ?
			dblPopulationVariance : empiricalVariance());

		double dblBennettFactor = _dblSupport * dblLevel / dblVariance;

		dblBennettFactor = (1. + dblBennettFactor) * java.lang.Math.log (1. + dblBennettFactor) -
			dblBennettFactor;

		double dblBound = java.lang.Math.exp (-1. * sequence().length * dblVariance * dblBennettFactor /
			(_dblSupport * _dblSupport));

		dblBound = dblBound < 1. ? dblBound : 1.;

		try {
			return new org.drip.sequence.metrics.PivotedDepartureBounds
				(org.drip.sequence.metrics.PivotedDepartureBounds.PIVOT_ANCHOR_TYPE_MEAN,
					java.lang.Double.NaN, dblBound, dblBound);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Estimate Mean Departure Bounds of the Average using the Bernstein Inequality Bounds
	 * 
	 * @param dblLevel The Level at which the Departure is sought
	 * 
	 * @return The Mean Departure Bounds
	 */

	public org.drip.sequence.metrics.PivotedDepartureBounds bernsteinAverageBounds (
		final double dblLevel)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLevel) || dblLevel <= 0.) return null;

		double dblPopulationVariance = populationVariance();

		double dblVariance = (org.drip.quant.common.NumberUtil.IsValid (dblPopulationVariance) ?
			dblPopulationVariance : empiricalVariance());

		double dblBound = java.lang.Math.exp (-1. * sequence().length * dblLevel * dblLevel / (2. *
			dblVariance + (2. * _dblSupport * dblLevel / 3.)));

		dblBound = dblBound < 1. ? dblBound : 1.;

		try {
			return new org.drip.sequence.metrics.PivotedDepartureBounds
				(org.drip.sequence.metrics.PivotedDepartureBounds.PIVOT_ANCHOR_TYPE_MEAN,
					java.lang.Double.NaN, dblBound, dblBound);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
