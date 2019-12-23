
package org.drip.sample.anfuso2017;

import org.drip.measure.gaussian.R1UnivariateNormal;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.distance.GapLossWeightFunction;
import org.drip.validation.distance.GapTestOutcome;
import org.drip.validation.distance.GapTestSetting;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;
import org.drip.validation.riskfactorsingle.DiscriminatoryPowerAnalyzer;

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
 * <i>ADDiscriminatoryPowerAnalysis4b</i> demonstrates the Discriminatory Power Analysis illustrated in Table
 * 4b of Anfuso, Karyampas, and Nawroth (2013).
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/anfuso2017/README.md">Anfuso, Karyampas, and Nawroth (2013) Replications</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ADDiscriminatoryPowerAnalysis4b
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

	private static final double DistanceTest (
		final GapTestOutcome gapTestOutcome)
		throws Exception
	{
		return gapTestOutcome.distance();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int drawCount = 3780;
		int sampleCount = 100;
		double horizon = 3. / 12;
		double sampleAnnualMean = 0.;
		double sampleAnnualVolatility = 0.1;
		double[] hypothesisAnnualMeanArray = {
			-0.050,
			-0.025,
			 0.000,
			 0.025,
			 0.050
		};
		double[] hypothesisAnnualVolatilityArray = {
			0.050,
			0.075,
			0.100,
			0.125,
			0.150
		};

		double hypothesisHorizonSQRT = Math.sqrt (horizon);

		Sample sample = GenerateSample (
			sampleAnnualMean,
			sampleAnnualVolatility * hypothesisHorizonSQRT,
			drawCount
		);

		DiscriminatoryPowerAnalyzer discriminatoryPowerAnalysis = DiscriminatoryPowerAnalyzer.FromSample (
			sample,
			GapTestSetting.RiskFactorLossTest (
				GapLossWeightFunction.AndersonDarling()
			)
		);

		System.out.println ("\t|--------------------------------||");

		System.out.println ("\t|     DISCRIMINANT GRID SCAN     ||");

		System.out.println ("\t|--------------------------------||");

		System.out.println ("\t|    L -> R:                     ||");

		System.out.println ("\t|        - Hypothesis Mean       ||");

		System.out.println ("\t|        - Hypothesis Sigma      ||");

		System.out.println ("\t|        - Distance Metric       ||");

		System.out.println ("\t|--------------------------------||");

		for (double hypothesisAnnualMean : hypothesisAnnualMeanArray)
		{
			for (double hypothesisAnnualVolatility : hypothesisAnnualVolatilityArray)
			{
				Ensemble hypothesis = GenerateEnsemble (
					hypothesisAnnualMean * horizon,
					hypothesisAnnualVolatility * hypothesisHorizonSQRT,
					drawCount,
					sampleCount
				);

				GapTestOutcome gapTestOutcome = discriminatoryPowerAnalysis.gapTest (hypothesis);

				System.out.println (
					"\t| " +
					FormatUtil.FormatDouble (hypothesisAnnualMean, 1, 3, 1.) + " | " +
					FormatUtil.FormatDouble (hypothesisAnnualVolatility, 1, 3, 1.) + " => " +
					FormatUtil.FormatDouble (DistanceTest (gapTestOutcome), 1, 8, 1.) + " ||"
				);
			}
		}

		System.out.println ("\t|--------------------------------||");

		EnvManager.TerminateEnv();
	}
}
