
package org.drip.sample.distancetest;

import org.drip.measure.gaussian.R1UnivariateNormal;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.distance.GapLossFunction;
import org.drip.validation.distance.GapTestOutcome;
import org.drip.validation.distance.GapLossWeightFunction;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;
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
 * <i>NormalCramersVonMisesGapDiscriminant</i> demonstrates the Generation of the Sample Distance
 * Discriminant Metrics for Different Ensemble Hypotheses.
 * 
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		<b>Reference Distribution  </b> - <i>Univariate Normal</i>
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

public class NormalCramersVonMisesGapDiscriminant
{

	private static final double UnivariateRandom (
		final double mean,
		final double sigma)
		throws Exception
	{
		return new R1UnivariateNormal (
			mean,
			sigma
		).random();
	}

	private static final Sample GenerateSample (
		final double mean,
		final double sigma,
		final int drawCount)
		throws Exception
	{
		double[] univariateRandomArray = new double[drawCount];

		for (int drawIndex = 0; drawIndex < drawCount; ++drawIndex)
		{
			univariateRandomArray[drawIndex] = UnivariateRandom (
				mean,
				sigma
			);
		}

		return new Sample (univariateRandomArray);
	}

	private static final Sample[] GenerateSampleArray (
		final double mean,
		final double sigma,
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		Sample[] sampleArray = new Sample[sampleCount];

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			sampleArray[sampleIndex] = GenerateSample (
				mean,
				sigma,
				drawCount
			);
		}

		return sampleArray;
	}

	private static final Ensemble GenerateEnsemble (
		final double mean,
		final double sigma,
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		return new Ensemble (
			GenerateSampleArray (
				mean,
				sigma,
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
		final GapLossFunction gapLossFunction,
		final GapLossWeightFunction gapLossWeightFunction)
		throws Exception
	{
		return new ProbabilityIntegralTransformTest (
			ensemble.nativeProbabilityIntegralTransform()
		).distanceTest (
			sample.nativeProbabilityIntegralTransform(),
			gapLossFunction,
			gapLossWeightFunction
		);
	}

	private static final double DistanceTest (
		final double hypothesisMean,
		final double hypothesisSigma,
		final int drawCount,
		final int sampleCount,
		final Sample sample,
		final GapLossFunction gapLossFunction,
		final GapLossWeightFunction gapLossWeightFunction,
		final int quantileCount)
		throws Exception
	{
		return DistanceTest (
			sample,
			GenerateEnsemble (
				hypothesisMean,
				hypothesisSigma,
				drawCount,
				sampleCount
			),
			gapLossFunction,
			gapLossWeightFunction
		).distance();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int drawCount = 2000;
		int sampleCount = 600;
		double sampleMean = 0.;
		double sampleSigma = 1.;
		int quantileCount = 20;
		double[] hypothesisMeanArray = {
			-0.50,
			-0.25,
			 0.00,
			 0.25,
			 0.50
		};
		double[] hypothesisSigmaArray = {
			0.50,
			0.75,
			1.00,
			1.25,
			1.50
		};

		GapLossFunction gapLossFunction = GapLossFunction.AnfusoKaryampasNawroth();

		GapLossWeightFunction gapLossWeightFunction = GapLossWeightFunction.CramersVonMises();

		Sample sample = GenerateSample (
			sampleMean,
			sampleSigma,
			drawCount
		);

		System.out.println ("\t|------------------------------||");

		System.out.println ("\t|    DISCRIMINANT GRID SCAN    ||");

		System.out.println ("\t|------------------------------||");

		System.out.println ("\t|    L -> R:                   ||");

		System.out.println ("\t|        - Hypothesis Mean     ||");

		System.out.println ("\t|        - Hypothesis Sigma    ||");

		System.out.println ("\t|        - Distance Metric     ||");

		System.out.println ("\t|------------------------------||");

		for (double hypothesisMean : hypothesisMeanArray)
		{
			for (double hypothesisSigma : hypothesisSigmaArray)
			{
				System.out.println (
					"\t| " +
					FormatUtil.FormatDouble (hypothesisMean, 1, 2, 1.) + " | " +
					FormatUtil.FormatDouble (hypothesisSigma, 1, 2, 1.) + " => " +
					FormatUtil.FormatDouble (
						DistanceTest (
							hypothesisMean,
							hypothesisSigma,
							drawCount,
							sampleCount,
							sample,
							gapLossFunction,
							gapLossWeightFunction,
							quantileCount
						),
						1, 8, 1.
					) + " ||"
				);
			}
		}

		System.out.println ("\t|------------------------------||");

		EnvManager.TerminateEnv();
	}
}
