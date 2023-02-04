
package org.drip.sample.quantile;

import org.drip.measure.gaussian.R1UnivariateNormal;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;
import org.drip.validation.hypothesis.ProbabilityIntegralTransformTest;
import org.drip.validation.quantile.PlottingPositionGeneratorHeuristic;
import org.drip.validation.quantile.QQTestOutcome;
import org.drip.validation.quantile.QQVertex;

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
 * <i>QQTest3</i> compares the Order Statistics between 2 Similar Normal Distributions using the Bernard Bos
 * 	Levenbach (1953) Mean Based Plotting Position Generator.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Gibbons, J. D., and S. Chakraborti (2003): <i>Non-parametric Statistical Inference 4th
 *  			Edition</i> <b>CRC Press</b>
 *  	</li>
 *  	<li>
 *  		Filliben, J. J. (1975): The Probability Plot Correlation Coefficient Test for Normality
 *  			<i>Technometrics, American Society for Quality</i> <b>17 (1)</b> 111-117
 *  	</li>
 *  	<li>
 *  		Gnanadesikan, R. (1977): <i>Methods for Statistical Analysis of Multivariate Observations</i>
 *  			<b>Wiley</b>
 *  	</li>
 *  	<li>
 *  		Thode, H. C. (2002): <i>Testing for Normality</i> <b>Marcel Dekker</b> New York
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Q-Q Plot https://en.wikipedia.org/wiki/Q%E2%80%93Q_plot
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/quantile">Quantile Generation and Comparison Testing</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class QQTest3
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

	private static final void QQPlot (
		final QQTestOutcome qqTestOutcome)
		throws Exception
	{
		QQVertex[] qqVertexArray = qqTestOutcome.qqVertexArray();

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\t|          Q-Q TEST OUTCOME          ||");

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\t|    L -> R:                         ||");

		System.out.println ("\t|        - Order Statistic Ordinal   ||");

		System.out.println ("\t|        - Order Statistic Quantile  ||");

		System.out.println ("\t|        - Order Statistic X         ||");

		System.out.println ("\t|        - Order Statistic Y         ||");

		System.out.println ("\t|------------------------------------||");

		for (QQVertex qqVertex : qqVertexArray)
		{
			System.out.println (
				"\t| " + FormatUtil.FormatDouble (
					qqVertex.plottingPosition().orderStatisticOrdinal(), 2, 0, 1.
				) + " => " +
				FormatUtil.FormatDouble (qqVertex.plottingPosition().quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (qqVertex.orderStatisticX(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (qqVertex.orderStatisticY(), 1, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\t|-----------------------------------------------------||");

		System.out.println (
			"\t| Probability Plot Correlation Coefficient => " +
			FormatUtil.FormatDouble (qqTestOutcome.probabilityPlotCorrelationCoefficient(), 1, 4, 1.) + " ||"
		);

		System.out.println ("\t|-----------------------------------------------------||");
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

		int drawCount = 1000000;
		int sampleCount = 1;
		double sampleMean = 0.;
		double hypothesisMean = 0.;
		int orderStatisticCount = 25;
		double sampleVolatility = 1.0;
		double hypothesisVolatility = 1.5;

		PlottingPositionGeneratorHeuristic plottingPositionGenerator =
			PlottingPositionGeneratorHeuristic.BernardBosLevenbach1953 (orderStatisticCount);

		Sample sample = GenerateSample (
			sampleMean,
			sampleVolatility,
			drawCount
		);

		Ensemble hypothesis = GenerateEnsemble (
			hypothesisMean,
			hypothesisVolatility,
			drawCount,
			sampleCount
		);

		QQPlot (
			new ProbabilityIntegralTransformTest (
				hypothesis.nativeProbabilityIntegralTransform()
			).qqTest (
				sample.nativeProbabilityIntegralTransform(),
				plottingPositionGenerator
			)
		);

		EnvManager.TerminateEnv();
	}
}
