
package org.drip.validation.evidence;

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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/evidence">Sample and Ensemble Evidence Processors</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Ensemble implements org.drip.validation.evidence.NativePITGenerator
{
	private double[][] _evaluatedSampleTestStatistic = null;
	private org.drip.validation.evidence.Sample[] _sampleArray = null;
	private org.drip.validation.evidence.TestStatisticEvaluator[] _testStatisticEvaluatorArray =
		null;
	private org.drip.validation.hypothesis.ProbabilityIntegralTransform[] _probabilityIntegralTransformArray
		= null;

	/**
	 * Ensemble Constructor
	 * 
	 * @param sampleArray Array of the Statistical Hypothesis Samples
	 * @param testStatisticEvaluatorArray Array of the Test Statistic Evaluators
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Ensemble (
		final org.drip.validation.evidence.Sample[] sampleArray,
		final org.drip.validation.evidence.TestStatisticEvaluator[] testStatisticEvaluatorArray)
		throws java.lang.Exception
	{
		if (null == (_sampleArray = sampleArray) ||
			null == (_testStatisticEvaluatorArray = testStatisticEvaluatorArray))
		{
			throw new java.lang.Exception ("Ensemble Constructor => Invalid Inputs");
		}

		int sampleCount = _sampleArray.length;
		int testStatisticEvaluatorCount = _testStatisticEvaluatorArray.length;
		_evaluatedSampleTestStatistic = new double[testStatisticEvaluatorCount][sampleCount];
		_probabilityIntegralTransformArray = new
			org.drip.validation.hypothesis.ProbabilityIntegralTransform[testStatisticEvaluatorCount];

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

			org.drip.validation.evidence.TestStatisticAccumulator testStatisticAccumulator = new
				org.drip.validation.evidence.TestStatisticAccumulator();

			for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
			{
				if (!testStatisticAccumulator.addTestStatistic
					(_evaluatedSampleTestStatistic[testStatisticEvaluatorIndex][sampleIndex] =
						_sampleArray[sampleIndex].applyTestStatistic
							(_testStatisticEvaluatorArray[testStatisticEvaluatorIndex])))
				{
					throw new java.lang.Exception ("Ensemble Constructor => Invalid Inputs");
				}
			}

			if (null == (_probabilityIntegralTransformArray[testStatisticEvaluatorIndex] =
				testStatisticAccumulator.probabilityIntegralTransform()))
			{
				throw new java.lang.Exception ("Ensemble Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Computed Ensemble Test Statistics
	 * 
	 * @return The Computed Ensemble Test Statistics
	 */

	public double[][] evaluatedSampleTestStatistic()
	{
		return _evaluatedSampleTestStatistic;
	}

	/**
	 * Retrieve the Array of the Statistical Hypothesis Samples
	 * 
	 * @return The Array of the Statistical Hypothesis Samples
	 */

	public org.drip.validation.evidence.Sample[] sampleArray()
	{
		return _sampleArray;
	}

	/**
	 * Retrieve the Array of the Test Statistic Evaluators
	 * 
	 * @return The Array of the Test Statistic Evaluators
	 */

	public org.drip.validation.evidence.TestStatisticEvaluator[] testStatisticEvaluatorArray()
	{
		return _testStatisticEvaluatorArray;
	}

	/**
	 * Retrieve the Array of Probability Integral Transforms, one for each Test Statistic
	 * 
	 * @return The Array of Probability Integral Transforms
	 */

	public org.drip.validation.hypothesis.ProbabilityIntegralTransform[] probabilityIntegralTransformArray()
	{
		return _probabilityIntegralTransformArray;
	}

	/**
	 * Construct the Test Statistic Based Significance Test Hypothesis Array
	 * 
	 * @return The Test Statistic Based Significance Test Hypothesis Array
	 */

	public org.drip.validation.hypothesis.ProbabilityIntegralTransformTest[] significanceTest()
	{
		int probabilityIntegralTransformCount = _testStatisticEvaluatorArray.length;
		org.drip.validation.hypothesis.ProbabilityIntegralTransformTest[]
			probabilityIntegralTransformTestArray = new
				org.drip.validation.hypothesis.ProbabilityIntegralTransformTest[probabilityIntegralTransformCount];

		for (int probabilityIntegralTransformIndex = 0;
			probabilityIntegralTransformIndex < probabilityIntegralTransformCount;
			++probabilityIntegralTransformIndex)
		{
			try
			{
				probabilityIntegralTransformTestArray[probabilityIntegralTransformIndex] = new
					org.drip.validation.hypothesis.ProbabilityIntegralTransformTest
						(_probabilityIntegralTransformArray[probabilityIntegralTransformIndex]);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return probabilityIntegralTransformTestArray;
	}

	/**
	 * Compute the Array of t-Test Results
	 * 
	 * @param testStatistic The Test Statistic
	 * 
	 * @return The Array of t-Test Results
	 */

	public org.drip.validation.hypothesis.TTestOutcome[] tTest (
		final double testStatistic)
	{
		int sampleCount = _sampleArray.length;
		int testStatisticEvaluatorCount = _testStatisticEvaluatorArray.length;
		org.drip.validation.hypothesis.TTestOutcome[] tTestArray = new
			org.drip.validation.hypothesis.TTestOutcome[testStatisticEvaluatorCount];

		for (int testStatisticEvaluatorIndex = 0;
			testStatisticEvaluatorIndex < testStatisticEvaluatorCount;
			++testStatisticEvaluatorIndex)
		{
			org.drip.measure.statistics.UnivariateMoments ensembleUnivariateMoments =
				org.drip.measure.statistics.UnivariateMoments.Standard (
					"UnivariateMoments",
					_evaluatedSampleTestStatistic[testStatisticEvaluatorIndex],
					null
				);

			if (null == ensembleUnivariateMoments)
			{
				return null;
			}

			try
			{
				tTestArray[testStatisticEvaluatorIndex] = new org.drip.validation.hypothesis.TTestOutcome (
					testStatistic,
					sampleCount,
					ensembleUnivariateMoments.mean(),
					ensembleUnivariateMoments.variance(),
					ensembleUnivariateMoments.stdDev(),
					ensembleUnivariateMoments.stdError(),
					ensembleUnivariateMoments.degreesOfFreedom(),
					ensembleUnivariateMoments.predictiveConfidenceLevel(),
					ensembleUnivariateMoments.tStatistic (testStatistic),
					ensembleUnivariateMoments.standardErrorOffset (testStatistic)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return tTestArray;
	}

	/**
	 * Compute the Array of Statistical Test Outcomes
	 * 
	 * @param testStatistic The Realized Test Statistic
	 * @param pTestSetting The P-Test Setting
	 * 
	 * @return The Array of Statistical Test Outcomes
	 */

	public org.drip.validation.hypothesis.StatisticalTestOutcome[] statisticalTest (
		final double testStatistic,
		final org.drip.validation.hypothesis.SignificanceTestSetting pTestSetting)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (testStatistic) || null == pTestSetting)
		{
			return null;
		}

		int sampleCount = _sampleArray.length;
		int testStatisticEvaluatorCount = _testStatisticEvaluatorArray.length;
		org.drip.validation.hypothesis.StatisticalTestOutcome[] statisticalTestOutcomeArray = new
			org.drip.validation.hypothesis.StatisticalTestOutcome[testStatisticEvaluatorCount];

		org.drip.validation.hypothesis.ProbabilityIntegralTransformTest[]
			probabilityIntegralTransformTestArray = significanceTest();

		for (int testStatisticEvaluatorIndex = 0;
			testStatisticEvaluatorIndex < testStatisticEvaluatorCount;
			++testStatisticEvaluatorIndex)
		{
			org.drip.measure.statistics.UnivariateMoments ensembleUnivariateMoments =
				org.drip.measure.statistics.UnivariateMoments.Standard (
					"UnivariateMoments",
					_evaluatedSampleTestStatistic[testStatisticEvaluatorIndex],
					null
				);

			if (null == ensembleUnivariateMoments)
			{
				return null;
			}

			try
			{
				statisticalTestOutcomeArray[testStatisticEvaluatorIndex] = new
					org.drip.validation.hypothesis.StatisticalTestOutcome (
						probabilityIntegralTransformTestArray[testStatisticEvaluatorIndex].significanceTest (
							testStatistic,
							pTestSetting
						),
						new org.drip.validation.hypothesis.TTestOutcome (
							testStatistic,
							sampleCount,
							ensembleUnivariateMoments.mean(),
							ensembleUnivariateMoments.variance(),
							ensembleUnivariateMoments.stdDev(),
							ensembleUnivariateMoments.stdError(),
							ensembleUnivariateMoments.degreesOfFreedom(),
							ensembleUnivariateMoments.predictiveConfidenceLevel(),
							ensembleUnivariateMoments.tStatistic (testStatistic),
							ensembleUnivariateMoments.standardErrorOffset (testStatistic)
						)
					);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return statisticalTestOutcomeArray;
	}

	@Override public org.drip.validation.hypothesis.ProbabilityIntegralTransform
		nativeProbabilityIntegralTransform()
	{
		org.drip.validation.evidence.TestStatisticAccumulator testStatisticAccumulator = new
			org.drip.validation.evidence.TestStatisticAccumulator();

		int sampleCount = _sampleArray.length;

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			for (double realization : _sampleArray[sampleIndex].realizationArray())
			{
				if (!testStatisticAccumulator.addTestStatistic (realization))
				{
					return null;
				}
			}
		}

		return testStatisticAccumulator.probabilityIntegralTransform();
	}
}
