
package org.drip.validation.hypothesis;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>TTestOutcome</i> holds the Results of a Statistic Hypothesis t-Test.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Bhattacharya, B., and D. Habtzghi (2002): Median of the p-value under the Alternate Hypothesis
 *  			American Statistician 56 (3) 202-206
 *  	</li>
 *  	<li>
 *  		Head, M. L., L. Holman, R, Lanfear, A. T. Kahn, and M. D. Jennions (2015): The Extent and
 *  			Consequences of p-Hacking in Science PLoS Biology 13 (3) e1002106
 *  	</li>
 *  	<li>
 *  		Wasserstein, R. L., and N. A. Lazar (2016): The ASA’s Statement on p-values: Context, Process,
 *  			and Purpose American Statistician 70 (2) 129-133
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): t-statistic https://en.wikipedia.org/wiki/T-statistic
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation">Model Validation Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis">Statistical Hypothesis Validation Test Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TTestOutcome
{
	private double _ensembleMean = java.lang.Double.NaN;
	private double _testStatistic = java.lang.Double.NaN;
	private int _sampleCount = java.lang.Integer.MIN_VALUE;
	private double _ensembleVariance = java.lang.Double.NaN;
	private double _ensembleTStatistics = java.lang.Double.NaN;
	private double _ensembleStandardError = java.lang.Double.NaN;
	private double _ensembleStandardDeviation = java.lang.Double.NaN;
	private double _ensembleStandardErrorOffset = java.lang.Double.NaN;
	private int _ensembleDegreesOfFreedom = java.lang.Integer.MIN_VALUE;
	private double _ensemblePredictiveConfidenceInterval = java.lang.Double.NaN;

	/**
	 * TTestOutcome Constructor
	 * 
	 * @param testStatistic Sample Test Statistic
	 * @param sampleCount Number of Samples in the Ensemble
	 * @param ensembleMean Ensemble Mean
	 * @param ensembleVariance Ensemble Variance
	 * @param ensembleStandardDeviation Ensemble Standard Deviation
	 * @param ensembleStandardError Ensemble Standard Error
	 * @param ensembleDegreesOfFreedom Ensemble Degrees of Freedom
	 * @param ensemblePredictiveConfidenceInterval Ensemble Predictive Confidence Interval
	 * @param ensembleTStatistics Ensemble t-Statistics
	 * @param ensembleStandardErrorOffset  Ensemble Standard Error Offset
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TTestOutcome (
		final double testStatistic,
		final int sampleCount,
		final double ensembleMean,
		final double ensembleVariance,
		final double ensembleStandardDeviation,
		final double ensembleStandardError,
		final int ensembleDegreesOfFreedom,
		final double ensemblePredictiveConfidenceInterval,
		final double ensembleTStatistics,
		final double ensembleStandardErrorOffset)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_testStatistic = testStatistic) ||
			0 >= (_sampleCount = sampleCount) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_ensembleMean = ensembleMean) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_ensembleVariance = ensembleVariance) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_ensembleStandardDeviation =
				ensembleStandardDeviation) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_ensembleStandardError = ensembleStandardError) ||
			0 > (_ensembleDegreesOfFreedom = ensembleDegreesOfFreedom) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_ensemblePredictiveConfidenceInterval =
				ensemblePredictiveConfidenceInterval) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_ensembleTStatistics = ensembleTStatistics) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_ensembleStandardErrorOffset =
				ensembleStandardErrorOffset))
		{
			throw new java.lang.Exception ("TTestOutcome Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Sample Test Statistic
	 * 
	 * @return The Sample Test Statistic
	 */

	public double testStatistic()
	{
		return _testStatistic;
	}

	/**
	 * Retrieve the Sample Count
	 * 
	 * @return The Sample Count
	 */

	public int sampleCount()
	{
		return _sampleCount;
	}

	/**
	 * Retrieve the Ensemble Mean
	 * 
	 * @return The Ensemble Mean
	 */

	public double ensembleMean()
	{
		return _ensembleMean;
	}

	/**
	 * Retrieve the Ensemble Variance
	 * 
	 * @return The Ensemble Variance
	 */

	public double ensembleVariance()
	{
		return _ensembleVariance;
	}

	/**
	 * Retrieve the Ensemble Standard Deviation
	 * 
	 * @return The Ensemble Standard Deviation
	 */

	public double ensembleStandardDeviation()
	{
		return _ensembleStandardDeviation;
	}

	/**
	 * Retrieve the Ensemble Standard Error
	 * 
	 * @return The Ensemble Standard Error
	 */

	public double ensembleStandardError()
	{
		return _ensembleStandardError;
	}

	/**
	 * Retrieve the Ensemble Degrees of Freedom
	 * 
	 * @return The Ensemble Degrees of Freedom
	 */

	public int ensembleDegreesOfFreedom()
	{
		return _ensembleDegreesOfFreedom;
	}

	/**
	 * Retrieve the Ensemble Predictive Confidence Interval
	 * 
	 * @return The Ensemble Predictive Confidence Interval
	 */

	public double ensemblePredictiveConfidenceInterval()
	{
		return _ensemblePredictiveConfidenceInterval;
	}

	/**
	 * Retrieve the Ensemble t-Statistics
	 * 
	 * @return The Ensemble t-Statistics
	 */

	public double ensembleTStatistics()
	{
		return _ensembleTStatistics;
	}

	/**
	 * Retrieve the Ensemble Standard Error Offset
	 * 
	 * @return The Ensemble Standard Error Offset
	 */

	public double ensembleStandardErrorOffset()
	{
		return _ensembleStandardErrorOffset;
	}
}
