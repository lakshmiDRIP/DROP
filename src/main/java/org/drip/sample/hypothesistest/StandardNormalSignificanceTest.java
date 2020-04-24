
package org.drip.sample.hypothesistest;

import org.drip.measure.gaussian.R1UnivariateNormal;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.hypothesis.SignificanceTestSetting;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;
import org.drip.validation.hypothesis.SignificanceTestOutcome;
import org.drip.validation.hypothesis.ProbabilityIntegralTransformTest;

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
 * <i>StandardNormalSignificanceTest</i> illustrates Significance Test for a Standard Normal Ensemble.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Bhattacharya, B., and D. Habtzghi (2002): Median of the p-value under the Alternate Hypothesis
 *  			<i>American Statistician</i> 56 <b>(3)</b> 202-206
 *  	</li>
 *  	<li>
 *  		Head, M. L., L. Holman, R, Lanfear, A. T. Kahn, and M. D. Jennions (2015): The Extent and
 *  			Consequences of p-Hacking in Science <i>PLoS Biology</i> <b>13 (3)</b> e1002106
 *  	</li>
 *  	<li>
 *  		Wasserstein, R. L., and N. A. Lazar (2016): The ASA’s Statement on p-values: Context, Process,
 *  			and Purpose <i>American Statistician</i> <b>70 (2)</b> 129-133
 *  	</li>
 *  	<li>
 *  		Wetzels, R., D. Matzke, M. D. Lee, J. N. Rouder, G, J, Iverson, and E. J. Wagenmakers (2011):
 *  		Statistical Evidence in Experimental Psychology: An Empirical Comparison using 855 t-Tests
 *  		<i>Perspectives in Psychological Science</i> <b>6 (3)</b> 291-298
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/hypothesistest">Sample/Ensemble Statistical Hypothesis Tests</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StandardNormalSignificanceTest
{

	private static final double UnivariateRandom()
		throws Exception
	{
		return R1UnivariateNormal.Standard().random();
	}

	private static final Sample GenerateSample (
		final int drawCount)
		throws Exception
	{
		double[] univariateRandomArray = new double[drawCount];

		for (int drawIndex = 0; drawIndex < drawCount; ++drawIndex)
		{
			univariateRandomArray[drawIndex] = UnivariateRandom();
		}

		return new Sample (univariateRandomArray);
	}

	private static final Sample[] GenerateSampleArray (
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		Sample[] sampleArray = new Sample[sampleCount];

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			sampleArray[sampleIndex] = GenerateSample (drawCount);
		}

		return sampleArray;
	}

	private static final TestStatisticEvaluator[] MakeTestStatisticEvaluatorArray()
		throws Exception
	{
		return new TestStatisticEvaluator[]
		{
			new TestStatisticEvaluator()
			{
				public double evaluate (
					final double[] drawArray)
					throws Exception
				{
					double mean = 0.;
					int drawCount = drawArray.length;

					for (int drawIndex = 0; drawIndex < drawCount; ++drawIndex)
					{
						mean += drawArray[drawIndex];
					}

					return mean / drawCount;
				}
			}
		};
	}

	private static final void SignificanceTest (
		final ProbabilityIntegralTransformTest test,
		final int drawCount,
		final TestStatisticEvaluator testStatisticEvaluator,
		final SignificanceTestSetting pTestSetting)
		throws Exception
	{
		Sample testSample = GenerateSample (drawCount);

		SignificanceTestOutcome significanceTest = test.significanceTest (
			testSample.applyTestStatistic (testStatisticEvaluator),
			pTestSetting
		);

		System.out.println (
			"\t| " + FormatUtil.FormatDouble (significanceTest.testStatistic(), 1, 8, 1.) +
			" | " + FormatUtil.FormatDouble (significanceTest.rightTailPValue(), 1, 8, 1.) +
			" | " + FormatUtil.FormatDouble (significanceTest.leftTailPValue(), 1, 8, 1.) +
			" | " + significanceTest.pass()
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int drawCount = 10000;
		int sampleCount = 100;
		int significanceTestCount = 20;

		TestStatisticEvaluator[] testStatisticEvaluatorArray = MakeTestStatisticEvaluatorArray();

		Ensemble ensemble = new Ensemble (
			GenerateSampleArray (
				drawCount,
				sampleCount
			),
			testStatisticEvaluatorArray
		);

		ProbabilityIntegralTransformTest test = ensemble.significanceTest()[0];

		SignificanceTestSetting significanceTestSettingRightTail = SignificanceTestSetting.FisherRightTail();

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|      ENSEMBLE SIGNIFICANCE RIGHT TAIL TEST      ||");

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                      ||");

		System.out.println ("\t|        - Test Statistic                         ||");

		System.out.println ("\t|        - Right Tail p-Value                     ||");

		System.out.println ("\t|        - Left Tail p-Value                      ||");

		System.out.println ("\t|        - Significance Test Status               ||");

		System.out.println ("\t|-------------------------------------------------||");

		for (int significanceTest = 0; significanceTest < significanceTestCount; ++significanceTest)
		{
			SignificanceTest (
				test,
				drawCount,
				testStatisticEvaluatorArray[0],
				significanceTestSettingRightTail
			);
		}

		System.out.println ("\t|-------------------------------------------------||");

		SignificanceTestSetting significanceTestSettingLeftTail = SignificanceTestSetting.FisherLeftTail();

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|      ENSEMBLE SIGNIFICANCE RIGHT TAIL TEST      ||");

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                      ||");

		System.out.println ("\t|        - Test Statistic                         ||");

		System.out.println ("\t|        - Right Tail p-Value                     ||");

		System.out.println ("\t|        - Left Tail p-Value                      ||");

		System.out.println ("\t|        - Significance Test Status               ||");

		System.out.println ("\t|-------------------------------------------------||");

		for (int significanceTest = 0; significanceTest < significanceTestCount; ++significanceTest)
		{
			SignificanceTest (
				test,
				drawCount,
				testStatisticEvaluatorArray[0],
				significanceTestSettingLeftTail
			);
		}

		System.out.println ("\t|-------------------------------------------------||");

		SignificanceTestSetting significanceTestSettingDoubleTail = SignificanceTestSetting.FisherDoubleTail();

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|      ENSEMBLE SIGNIFICANCE DOUBLE TAIL TEST     ||");

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                      ||");

		System.out.println ("\t|        - Test Statistic                         ||");

		System.out.println ("\t|        - Right Tail p-Value                     ||");

		System.out.println ("\t|        - Left Tail p-Value                      ||");

		System.out.println ("\t|        - Significance Test Status               ||");

		System.out.println ("\t|-------------------------------------------------||");

		for (int significanceTest = 0; significanceTest < significanceTestCount; ++significanceTest)
		{
			SignificanceTest (
				test,
				drawCount,
				testStatisticEvaluatorArray[0],
				significanceTestSettingDoubleTail
			);
		}

		System.out.println ("\t|-------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
