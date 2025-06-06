
package org.drip.sample.distancetest;

import org.drip.measure.continuous.R1UnivariateUniform;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.distance.GapTestOutcome;
import org.drip.validation.distance.GapTestSetting;
import org.drip.validation.distance.GapLossWeightFunction;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;
import org.drip.validation.hypothesis.HistogramTestOutcome;
import org.drip.validation.hypothesis.HistogramTestSetting;
import org.drip.validation.hypothesis.ProbabilityIntegralTransformTest;
import org.drip.validation.quantile.PlottingPositionGenerator;
import org.drip.validation.quantile.PlottingPositionGeneratorHeuristic;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>UniformCramersVonMisesGapAnalysis</i> demonstrates the Generation of the Sample Distance Metrics for
 * Different Ensemble Hypotheses.
 * 
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		<b>Reference Distribution  </b> - <i>Univariate Uniform</i>
 *  	</li>
 *  	<li>
 *  		<b>Gap Loss Function       </b> - <i>Anfuso, Karyampas, and Nawroth (2017)</i>
 *  	</li>
 *  	<li>
 *  		<b>Gap Loss Weight Function</b> - <i>Cramers and von Mises</i>
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for
 *  			Back-testing Credit Exposure Models
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with
 *  			Applications to Financial Risk Management, International Economic Review 39 (4) 863-883
 *  	</li>
 *  	<li>
 *  		Kenyon, C., and R. Stamm (2012): Discounting, LIBOR, CVA, and Funding: Interest Rate and Credit
 *  			Pricing, Palgrave Macmillan
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/validation">Empirical Univariate Gap Distance Tests</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UniformCramersVonMisesGapAnalysis
{

	private static final double UnivariateRandom (
		final double leftSupport,
		final double rightSupport)
		throws Exception
	{
		return new R1UnivariateUniform (
			leftSupport,
			rightSupport
		).random();
	}

	private static final Sample GenerateSample (
		final double leftSupport,
		final double rightSupport,
		final int drawCount)
		throws Exception
	{
		double[] univariateRandomArray = new double[drawCount];

		for (int drawIndex = 0; drawIndex < drawCount; ++drawIndex)
		{
			univariateRandomArray[drawIndex] = UnivariateRandom (
				leftSupport,
				rightSupport
			);
		}

		return new Sample (univariateRandomArray);
	}

	private static final Sample[] GenerateSampleArray (
		final double leftSupport,
		final double rightSupport,
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		Sample[] sampleArray = new Sample[sampleCount];

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			sampleArray[sampleIndex] = GenerateSample (
				leftSupport,
				rightSupport,
				drawCount
			);
		}

		return sampleArray;
	}

	private static final Ensemble GenerateEnsemble (
		final double leftSupport,
		final double rightSupport,
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		return new Ensemble (
			GenerateSampleArray (
				leftSupport,
				rightSupport,
				drawCount,
				sampleCount
			),
			new TestStatisticEvaluator[]
			{
				new TestStatisticEvaluator()
				{
					public double evaluate (
						final double[] drawArray)
						throws Exception
					{
						return 1.;
					}
				}
			}
		);
	}

	private static final GapTestOutcome DistanceTest (
		final Sample sample,
		final Ensemble ensemble,
		final GapTestSetting gapTestSetting)
		throws Exception
	{
		return new ProbabilityIntegralTransformTest (
			ensemble.nativeProbabilityIntegralTransform()
		).distanceTest (
			sample.nativeProbabilityIntegralTransform(),
			gapTestSetting
		);
	}

	private static final void DistanceTest (
		final double hypothesisLeftSupport,
		final double hypothesisRightSupport,
		final int drawCount,
		final int sampleCount,
		final Sample sample,
		final GapTestSetting gapTestSetting,
		final PlottingPositionGenerator plottingPositionGenerator)
		throws Exception
	{
		Ensemble hypothesis = GenerateEnsemble (
			hypothesisLeftSupport,
			hypothesisRightSupport,
			drawCount,
			sampleCount
		);

		GapTestOutcome gapTestOutcome = DistanceTest (
			sample,
			hypothesis,
			gapTestSetting
		);

		HistogramTestOutcome histogram = new ProbabilityIntegralTransformTest (
			gapTestOutcome.probabilityIntegralTransformWeighted()
		).histogramTest (
			HistogramTestSetting.AnfusoKaryampasNawroth2017 (
				plottingPositionGenerator
			)
		);

		double[] pValueIncrementalArray = histogram.pValueIncrementalArray();

		double[] pValueCumulativeArray = histogram.pValueCumulativeArray();

		double thresholdTestStatistic = histogram.thresholdTestStatistic();

		double[] gapArray = histogram.testStatisticArray();

		double distance = gapTestOutcome.distance();

		System.out.println ("\t|--------------------------------------------------------------------||");

		System.out.println ("\t|           Uniform Anfuso Karyampas Nawroth Distance Test           ||");

		System.out.println ("\t|--------------------------------------------------------------------||");

		System.out.println (
			"\t|    Left => [" + FormatUtil.FormatDouble (hypothesisLeftSupport, 1, 8, 1.) +
			"]  |  Right => [" + FormatUtil.FormatDouble (hypothesisRightSupport, 1, 8, 1.) + "]                ||"
		);

		System.out.println ("\t|--------------------------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                                         ||");

		System.out.println ("\t|        - Weighted Distance Metric                                  ||");

		System.out.println ("\t|        - Cumulative p-Value                                        ||");

		System.out.println ("\t|        - Incremental p-Value                                       ||");

		System.out.println ("\t|        - Ensemble Weighted Distance                                ||");

		System.out.println ("\t|        - p-Value Threshold Distance                                ||");

		System.out.println ("\t|--------------------------------------------------------------------||");

		for (int histogramIndex = 0;
			histogramIndex <= plottingPositionGenerator.orderStatisticCount() + 1;
			++histogramIndex)
		{
			System.out.println (
				"\t|" +
				FormatUtil.FormatDouble (gapArray[histogramIndex], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (pValueCumulativeArray[histogramIndex], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (pValueIncrementalArray[histogramIndex], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (distance, 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (thresholdTestStatistic, 1, 8, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------------------------------------||");
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int drawCount = 2000;
		int sampleCount = 600;
		double sampleLeftSupport = 0.;
		double sampleRightSupport = 1.;
		int orderStatisticsCount = 20;
		double[] hypothesisLeftSupportArray = {
			-0.50,
			-0.25,
			 0.00,
			 0.25,
			 0.50
		};
		double[] hypothesisRightSupportArray = {
			0.75,
			1.00,
			1.25,
			1.50,
			1.75
		};

		GapTestSetting gapTestSetting = GapTestSetting.RiskFactorLossTest (
			GapLossWeightFunction.AndersonDarling()
		);

		PlottingPositionGenerator plottingPositionGenerator = PlottingPositionGeneratorHeuristic.NIST2013
			(orderStatisticsCount);

		Sample sample = GenerateSample (
			sampleLeftSupport,
			sampleRightSupport,
			drawCount
		);

		for (double hypothesisLeftSupport : hypothesisLeftSupportArray)
		{
			for (double hypothesisRightSupport : hypothesisRightSupportArray)
			{
				DistanceTest (
					hypothesisLeftSupport,
					hypothesisRightSupport,
					drawCount,
					sampleCount,
					sample,
					gapTestSetting,
					plottingPositionGenerator
				);
			}
		}

		EnvManager.TerminateEnv();
	}
}
