
package org.drip.sample.distancetest;

import org.drip.measure.continuous.R1UnivariateUniform;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.distance.GapTestOutcome;
import org.drip.validation.distance.GapTestSetting;
import org.drip.validation.distance.GapLossWeightFunction;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;
import org.drip.validation.hypothesis.ProbabilityIntegralTransformHistogram;
import org.drip.validation.hypothesis.ProbabilityIntegralTransformTest;

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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation">Model Validation Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/core">Core Model Validation Support Utilities</a></li>
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
		final int quantileCount,
		final double pValueThreshold)
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

		ProbabilityIntegralTransformHistogram histogram =
			gapTestOutcome.probabilityIntegralTransformWeighted().histogram (
				quantileCount,
				pValueThreshold
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

		for (int quantileIndex = 0; quantileIndex <= quantileCount; ++quantileIndex)
		{
			System.out.println (
				"\t|" +
				FormatUtil.FormatDouble (gapArray[quantileIndex], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (pValueCumulativeArray[quantileIndex], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (pValueIncrementalArray[quantileIndex], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (distance, 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (thresholdTestStatistic, 1, 8, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int drawCount = 2000;
		int sampleCount = 600;
		double sampleLeftSupport = 0.;
		double sampleRightSupport = 1.;
		int quantileCount = 20;
		double pValueThreshold = 0.99;
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

		GapTestSetting gapTestSetting = GapTestSetting.AnfusoKaryampasNawroth2017
			(GapLossWeightFunction.CramersVonMises());

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
					quantileCount,
					pValueThreshold
				);
			}
		}

		EnvManager.TerminateEnv();
	}
}
