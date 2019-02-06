
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
 * <i>Ensemble</i> contains the Ensemble Collection of Statistical Samples and their Test Statistic
 * Evaluators.
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
 *  		Wetzels, R., D. Matzke, M. D. Lee, J. N. Rouder, G, J, Iverson, and E. J. Wagenmakers (2011):
 *  		Statistical Evidence in Experimental Psychology: An Empirical Comparison using 855 t-Tests
 *  		Perspectives in Psychological Science 6 (3) 291-298
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/core">Core Model Validation Support Utilities</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Ensemble
{
	private org.drip.validation.hypothesis.Sample[] _sampleArray = null;
	private org.drip.validation.hypothesis.PTestSetting _pTestSetting = null;
	private org.drip.validation.hypothesis.TestStatisticEvaluator[] _testStatisticEvaluatorArray =
		null;

	/**
	 * Ensemble Constructor
	 * 
	 * @param pTestSetting The Statistical Hypothesis p-Test Setting
	 * @param sampleArray Array of the Statistical Hypothesis Samples
	 * @param testStatisticEvaluatorArray Array of the Test Statistic Evaluators
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Ensemble (
		final org.drip.validation.hypothesis.PTestSetting pTestSetting,
		final org.drip.validation.hypothesis.Sample[] sampleArray,
		final org.drip.validation.hypothesis.TestStatisticEvaluator[] testStatisticEvaluatorArray)
		throws java.lang.Exception
	{
		if (null == (_pTestSetting = pTestSetting) ||
			null == (_sampleArray = sampleArray) ||
			null == (_testStatisticEvaluatorArray = testStatisticEvaluatorArray))
		{
			throw new java.lang.Exception ("Ensemble Constructor => Invalid Inputs");
		}

		int sampleCount = _sampleArray.length;
		int testStatisticEvaluatorCount = _testStatisticEvaluatorArray.length;

		if (0 == sampleCount || 0 == testStatisticEvaluatorCount)
		{
			throw new java.lang.Exception ("Ensemble Constructor => Invalid Inputs");
		}

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			if (null == _sampleArray[sampleIndex])
			{
				throw new java.lang.Exception ("Ensemble Constructor => Invalid Inputs");
			}
		}

		for (int testStatisticEvaluatorIndex = 0;
			testStatisticEvaluatorIndex < testStatisticEvaluatorCount;
			++testStatisticEvaluatorIndex)
		{
			if (null == _testStatisticEvaluatorArray[testStatisticEvaluatorIndex])
			{
				throw new java.lang.Exception ("Ensemble Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Statistical Hypothesis p-Test Settings
	 * 
	 * @return The Statistical Hypothesis p-Test Settings
	 */

	public org.drip.validation.hypothesis.PTestSetting pTestSetting()
	{
		return _pTestSetting;
	}

	/**
	 * Retrieve the Array of the Statistical Hypothesis Samples
	 * 
	 * @return The Array of the Statistical Hypothesis Samples
	 */

	public org.drip.validation.hypothesis.Sample[] sampleArray()
	{
		return _sampleArray;
	}

	/**
	 * Retrieve the Array of the Test Statistic Evaluators
	 * 
	 * @return The Array of the Test Statistic Evaluators
	 */

	public org.drip.validation.hypothesis.TestStatisticEvaluator[] testStatisticEvaluatorArray()
	{
		return _testStatisticEvaluatorArray;
	}

	/**
	 * Generate the Test Statistic Accumulator Array
	 * 
	 * @return The Test Statistic Accumulator Array
	 */

	public org.drip.validation.hypothesis.Test[] testArray()
	{
		int sampleCount = _sampleArray.length;
		int testStatisticEvaluatorCount = _testStatisticEvaluatorArray.length;
		org.drip.validation.hypothesis.Test[] testArray = new
			org.drip.validation.hypothesis.Test[testStatisticEvaluatorCount];

		for (int testStatisticAccumulatorIndex = 0;
			testStatisticAccumulatorIndex < testStatisticEvaluatorCount;
			++testStatisticAccumulatorIndex)
		{
			org.drip.validation.hypothesis.TestStatisticAccumulator testStatisticAccumulator = new
				org.drip.validation.hypothesis.TestStatisticAccumulator();

			for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
			{
				try
				{
					if (!testStatisticAccumulator.addTestStatistic
						(_sampleArray[sampleIndex].applyTestStatistic
							(_testStatisticEvaluatorArray[testStatisticAccumulatorIndex])))
					{
						return null;
					}
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}

			if (null == (testArray[testStatisticAccumulatorIndex] =
				testStatisticAccumulator.probabilityIntegralTransform()))
			{
				return null;
			}
		}

		return testArray;
	}
}
