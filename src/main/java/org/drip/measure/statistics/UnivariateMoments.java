
package org.drip.measure.statistics;

import java.util.Map;
import java.util.TreeMap;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>UnivariateMoments</i> generates and holds the Specified Univariate Series Mean, Variance, and a few
 * 	selected Moments. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct a <i>UnivariateMoments</i> Instance for the specified Series #1</li>
 * 		<li>Construct a <i>UnivariateMoments</i> Instance for the specified Series #2</li>
 * 		<li>Retrieve the Series Name</li>
 * 		<li>Retrieve the Number of Samples</li>
 * 		<li>Retrieve the Series Mean</li>
 * 		<li>Retrieve the Series Variance</li>
 * 		<li>Retrieve the Series Standard Deviation</li>
 * 		<li>Retrieve the Series Standard Error</li>
 * 		<li>Retrieve the Moments Map</li>
 * 		<li>Compute the Series t-Statistic around the Series Hypothesis Pivot</li>
 * 		<li>Compute the Series t-Statistic for Hypothesis Pivot = 0 (e.g., the False Positive NULL Hypothesis for for Homoscedastic Univariate Linear Regression)</li>
 * 		<li>Estimate the Offset in Terms of the Number of Standard Errors</li>
 * 		<li>Retrieve the Degrees of Freedom</li>
 * 		<li>Compute the Predictive Confidence Level</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/statistics/README.md">R<sup>1</sup> R<sup>d</sup> Thin Thick Moments</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnivariateMoments
{
	private String _name = "";
	private int _sampleCount = 0;
	private double _mean = Double.NaN;
	private double _variance = Double.NaN;
	private Map<Integer, Double> _momentMap = null;

	/**
	 * Construct a <i>UnivariateMoments</i> Instance for the specified Series #1
	 * 
	 * @param name Series Name
	 * @param entryArray Series Entry
	 * @param orderStatisticArray Array of Moment Order Statistic to be Calculated
	 * 
	 * @return The <i>UnivariateMoments</i> Instance
	 */

	public static final UnivariateMoments Standard (
		final String name,
		final double[] entryArray,
		final int[] orderStatisticArray)
	{
		if (null == entryArray) {
			return null;
		}

		double mean = 0.;
		double variance = 0.;
		int sampleCount = entryArray.length;
		int momentCount = null == orderStatisticArray ? 0 : orderStatisticArray.length;
		double[] momentArray = 0 == momentCount ? null : new double[momentCount];

		Map<Integer, Double> mapMoment = 0 == momentCount ? null : new TreeMap<Integer, Double>();

		if (0 == sampleCount) {
			return null;
		}

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex) {
			if (!NumberUtil.IsValid (entryArray[sampleIndex])) {
				return null;
			}

			mean += entryArray[sampleIndex];
		}

		mean /= sampleCount;

		for (int momentIndex = 0; momentIndex < momentCount; ++momentIndex) {
			momentArray[momentIndex] = 0.;
		}

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex) {
			double error = mean - entryArray[sampleIndex];
			variance += (error * error);

			for (int momentIndex = 0; momentIndex < momentCount; ++momentIndex) {
				momentArray[momentIndex] += Math.pow (error, orderStatisticArray[momentIndex]);
			}
		}

		for (int momentIndex = 0; momentIndex < momentCount; ++momentIndex) {
			mapMoment.put (orderStatisticArray[momentIndex], momentArray[momentIndex]);
		}

		try {
			return new UnivariateMoments (name, mean, variance / sampleCount, sampleCount, mapMoment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a <i>UnivariateMoments</i> Instance for the specified Series #2
	 * 
	 * @param name Series Name
	 * @param entryArray Series Entry
	 * 
	 * @return The <i>UnivariateMoments</i> Instance
	 */

	public static final UnivariateMoments Standard (
		final String name,
		final double[] entryArray)
	{
		return Standard (name, entryArray, null);
	}

	protected UnivariateMoments (
		final String name,
		final double mean,
		final double variance,
		final int sampleCount,
		final Map<Integer, Double> momentMap)
		throws Exception
	{
		if (null == (_name = name) || _name.isEmpty() ||
			!NumberUtil.IsValid (_mean = mean) ||
			!NumberUtil.IsValid (_variance = variance) ||
			0 >= (_sampleCount = sampleCount))
		{
			throw new Exception ("UnivariateMetrics Constructor => Invalid Inputs!");
		}

		_momentMap = momentMap;
	}

	/**
	 * Retrieve the Series Name
	 * 
	 * @return The Series Name
	 */

	public String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Number of Samples
	 * 
	 * @return The Number of Samples
	 */

	public int numSample()
	{
		return _sampleCount;
	}

	/**
	 * Retrieve the Series Mean
	 * 
	 * @return The Series Mean
	 */

	public double mean()
	{
		return _mean;
	}

	/**
	 * Retrieve the Series Variance
	 * 
	 * @return The Series Variance
	 */

	public double variance()
	{
		return _variance;
	}

	/**
	 * Retrieve the Series Standard Deviation
	 * 
	 * @return The Series Standard Deviation
	 */

	public double stdDev()
	{
		return Math.sqrt (_variance);
	}

	/**
	 * Retrieve the Series Standard Error
	 * 
	 * @return The Series Standard Error
	 */

	public double stdError()
	{
		return Math.sqrt (_variance);
	}

	/**
	 * Retrieve the Moments Map
	 * 
	 * @return The Map of Moments
	 */

	public Map<Integer, Double> momentMap()
	{
		return _momentMap;
	}

	/**
	 * Compute the Series t-Statistic around the Series Hypothesis Pivot
	 * 
	 * @param hypothesisPivot The Series Hypothesis Pivot
	 * 
	 * @return The Series t-Statistic around the Series Hypothesis Pivot
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double tStatistic (
		final double hypothesisPivot)
		throws Exception
	{
		if (!NumberUtil.IsValid (hypothesisPivot)) {
			throw new Exception ("UnivariateMetrics::tStatistic => Invalid Inputs");
		}

		return (_mean - hypothesisPivot) / Math.sqrt (_variance);
	}

	/**
	 * Compute the Series t-Statistic for Hypothesis Pivot = 0 (e.g., the False Positive NULL Hypothesis for
	 * 	for Homoscedastic Univariate Linear Regression)
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 * 
	 * @return The Series t-Statistic
	 */

	public double tStatistic()
		throws Exception
	{
		return _mean / Math.sqrt (_variance);
	}

	/**
	 * Estimate the Offset in Terms of the NUmber of Standard Errors
	 * 
	 * @param x The Observation Point
	 * 
	 * @return The Offset in Terms of the NUmber of Standard Errors
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double standardErrorOffset (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("UnivariateMetrics::standardErrorOffset => Invalid Inputs");
		}

		return (_mean - x) / Math.sqrt (_variance);
	}

	/**
	 * Retrieve the Degrees of Freedom
	 * 
	 * @return The Degrees of Freedom
	 */

	public int degreesOfFreedom()
	{
		return _sampleCount - 1;
	}

	/**
	 * Compute the Predictive Confidence Level
	 * 
	 * @return The Predictive Confidence Level
	 */

	public double predictiveConfidenceLevel()
	{
		return Math.sqrt (_variance * (1. + 1. / (1. + _sampleCount)));
	}
}
