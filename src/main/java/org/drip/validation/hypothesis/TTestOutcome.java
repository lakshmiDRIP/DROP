
package org.drip.validation.hypothesis;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/README.md">Risk Factor and Hypothesis Validation, Evidence Processing, and Model Testing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/hypothesis/README.md">Statistical Hypothesis Validation Test Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TTestOutcome
{
	private double _ensembleMean = Double.NaN;
	private double _testStatistic = Double.NaN;
	private int _sampleCount = Integer.MIN_VALUE;
	private double _ensembleVariance = Double.NaN;
	private double _ensembleTStatistics = Double.NaN;
	private double _ensembleStandardError = Double.NaN;
	private double _ensembleStandardDeviation = Double.NaN;
	private double _ensembleStandardErrorOffset = Double.NaN;
	private int _ensembleDegreesOfFreedom = Integer.MIN_VALUE;
	private double _ensemblePredictiveConfidenceInterval = Double.NaN;

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
	 * @throws Exception Thrown if the Inputs are Invalid
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
		if (!NumberUtil.IsValid (_testStatistic = testStatistic) || 0 >= (_sampleCount = sampleCount) ||
			!NumberUtil.IsValid (_ensembleMean = ensembleMean) ||
			!NumberUtil.IsValid (_ensembleVariance = ensembleVariance) ||
			!NumberUtil.IsValid (_ensembleStandardDeviation = ensembleStandardDeviation) ||
			!NumberUtil.IsValid (_ensembleStandardError = ensembleStandardError) ||
				0 > (_ensembleDegreesOfFreedom = ensembleDegreesOfFreedom) ||
			!NumberUtil.IsValid (_ensemblePredictiveConfidenceInterval =
				ensemblePredictiveConfidenceInterval) ||
			!NumberUtil.IsValid (_ensembleTStatistics = ensembleTStatistics) ||
			!NumberUtil.IsValid (_ensembleStandardErrorOffset = ensembleStandardErrorOffset)) {
			throw new Exception ("TTestOutcome Constructor => Invalid Inputs");
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
