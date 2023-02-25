
package org.drip.sequence.metrics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>BoundedSequenceAgnosticMetrics</i> contains the Sample Distribution Metrics and Agnostic Bounds related
 * to the specified Bounded Sequence.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/metrics">Metrics</a></li>
 *  </ul>
 * <br><br>
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
		final org.drip.measure.continuous.R1Univariate distPopulation,
		final double dblSupport)
		throws java.lang.Exception
	{
		super (adblSequence, distPopulation);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblSupport = dblSupport) || 0. >= _dblSupport)
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblLevel) || dblLevel <= 0) return null;

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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblLevel) || dblLevel <= 0.) return null;

		try {
			double dblPopulationVariance = populationVariance();

			double dblVariance = (org.drip.numerical.common.NumberUtil.IsValid (dblPopulationVariance) ?
				dblPopulationVariance : empiricalVariance());

			double dblBennettFactor = _dblSupport * dblLevel / dblVariance;

			dblBennettFactor = (1. + dblBennettFactor) * java.lang.Math.log (1. + dblBennettFactor) -
				dblBennettFactor;

			double dblBound = java.lang.Math.exp (-1. * sequence().length * dblVariance * dblBennettFactor /
				(_dblSupport * _dblSupport));

			dblBound = dblBound < 1. ? dblBound : 1.;

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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblLevel) || dblLevel <= 0.) return null;

		try {
			double dblPopulationVariance = populationVariance();

			double dblVariance = (org.drip.numerical.common.NumberUtil.IsValid (dblPopulationVariance) ?
				dblPopulationVariance : empiricalVariance());

			double dblBound = java.lang.Math.exp (-1. * sequence().length * dblLevel * dblLevel / (2. *
				dblVariance + (2. * _dblSupport * dblLevel / 3.)));

			dblBound = dblBound < 1. ? dblBound : 1.;

			return new org.drip.sequence.metrics.PivotedDepartureBounds
				(org.drip.sequence.metrics.PivotedDepartureBounds.PIVOT_ANCHOR_TYPE_MEAN,
					java.lang.Double.NaN, dblBound, dblBound);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
