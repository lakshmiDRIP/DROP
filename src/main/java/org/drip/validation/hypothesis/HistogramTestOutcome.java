
package org.drip.validation.hypothesis;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>HistogramTestOutcome</i> contains the p-value Cumulative and Incremental Histograms across the Test
 * Statistic.
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
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
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

public class HistogramTestOutcome
{
	private double[] _testStatisticArray = null;
	private double[] _pValueCumulativeArray = null;
	private double[] _pValueIncrementalArray = null;
	private double _thresholdTestStatistic = java.lang.Double.NaN;

	/**
	 * HistogramTestOutcome Constructor
	 * 
	 * @param testStatisticArray Array of Test Statistics
	 * @param pValueCumulativeArray Array of Cumulative p-Values
	 * @param pValueIncrementalArray Array of Incremental p-Values
	 * @param thresholdTestStatistic The Threshold Test Statistic
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HistogramTestOutcome (
		final double[] testStatisticArray,
		final double[] pValueCumulativeArray,
		final double[] pValueIncrementalArray,
		final double thresholdTestStatistic)
		throws java.lang.Exception
	{
		if (null == (_testStatisticArray = testStatisticArray) ||
			null == (_pValueCumulativeArray = pValueCumulativeArray) ||
			null == (_pValueIncrementalArray = pValueIncrementalArray) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_thresholdTestStatistic = thresholdTestStatistic))
		{
			throw new java.lang.Exception ("HistogramTestOutcome Constructor => Invalid Inputs");
		}

		int count = _testStatisticArray.length;

		if (0 == count ||
			count != _pValueCumulativeArray.length ||
			count != _pValueIncrementalArray.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (_testStatisticArray) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_pValueCumulativeArray) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_pValueIncrementalArray))
		{
			throw new java.lang.Exception ("HistogramTestOutcome Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Test Statistics
	 * 
	 * @return The Array of Test Statistics
	 */

	public double[] testStatisticArray()
	{
		return _testStatisticArray;
	}

	/**
	 * Retrieve the Array of Cumulative p-Values
	 * 
	 * @return The Array of Cumulative p-Values
	 */

	public double[] pValueCumulativeArray()
	{
		return _pValueCumulativeArray;
	}

	/**
	 * Retrieve the Array of Incremental p-Values
	 * 
	 * @return The Array of Incremental p-Values
	 */

	public double[] pValueIncrementalArray()
	{
		return _pValueIncrementalArray;
	}

	/**
	 * Retrieve the Threshold Test Statistic
	 * 
	 * @return The Threshold Test Statistic
	 */

	public double thresholdTestStatistic()
	{
		return _thresholdTestStatistic;
	}
}
