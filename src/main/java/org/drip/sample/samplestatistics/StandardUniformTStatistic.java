
package org.drip.sample.samplestatistics;

import org.drip.measure.continuous.R1UnivariateUniform;
import org.drip.measure.statistics.PopulationCentralMeasures;
import org.drip.measure.statistics.UnivariateMoments;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.common.StringUtil;
import org.drip.service.env.EnvManager;

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
 * <i>StandardUniformTStatistic</i> illustrates the Computation of the t-statistic, z-score, and other
 * 	related Metrics of the Sample/Population Mean for an Empirical Standard Uniform Distribution.
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
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): t-statistic https://en.wikipedia.org/wiki/T-statistic
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/samplestatistics/README.md">Empirical Univariate Sample Statistical Tests</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StandardUniformTStatistic
{

	private static final double UnivariateRandom()
		throws Exception
	{
		return R1UnivariateUniform.Standard().random();
	}

	private static final PopulationCentralMeasures PopulationMeasures()
	{
		return R1UnivariateUniform.Standard().populationCentralMeasures();
	}

	private static final double SampleMeanEstimate (
		final int count)
		throws Exception
	{
		double[] univariateRandomArray = new double[count];

		for (int index = 0; index < count; ++index)
		{
			univariateRandomArray[index] = UnivariateRandom();
		}

		return UnivariateMoments.Standard (
			StringUtil.GUID(),
			univariateRandomArray,
			null
		).mean();
	}

	private static final UnivariateMoments SampleStatistics (
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		double[] sampleMeanEstimateArray = new double[sampleCount];

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			sampleMeanEstimateArray[sampleIndex] = SampleMeanEstimate (drawCount);
		}

		return UnivariateMoments.Standard (
			StringUtil.GUID(),
			sampleMeanEstimateArray,
			null
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int drawCount = 10000;
		int sampleCount = 200;

		UnivariateMoments sampleStatistics = SampleStatistics (
			drawCount,
			sampleCount
		);

		PopulationCentralMeasures populationCentralMeasures = PopulationMeasures();

		double nextDraw = UnivariateRandom();

		double updatedMean = (sampleStatistics.mean() * sampleCount + nextDraw) / (sampleCount + 1);

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println ("\t|          STANDARD UNIFORM DISTRIBUTION           ||");

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println (
			"\t| Population Mean                => " +
			FormatUtil.FormatDouble (populationCentralMeasures.mean(), 1, 8, 1.)
		);

		System.out.println (
			"\t| Population Variance            => " +
			FormatUtil.FormatDouble (populationCentralMeasures.variance(), 1, 8, 1.)
		);

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println (
			"\t| Mean                           => " +
			FormatUtil.FormatDouble (sampleStatistics.mean(), 1, 8, 1.)
		);

		System.out.println (
			"\t| Variance                       => " +
			FormatUtil.FormatDouble (sampleStatistics.variance(), 1, 8, 1.)
		);

		System.out.println (
			"\t| Sample Count                   => " +
			FormatUtil.FormatDouble (sampleStatistics.numSample(), 3, 0, 1.)
		);

		System.out.println (
			"\t| Degrees Of Freedom             => " +
			FormatUtil.FormatDouble (sampleStatistics.degreesOfFreedom(), 3, 0, 1.)
		);

		System.out.println (
			"\t| Standard Deviation             => " +
			FormatUtil.FormatDouble (sampleStatistics.stdDev(), 1, 8, 1.)
		);

		System.out.println (
			"\t| Standard Error                 => " +
			FormatUtil.FormatDouble (sampleStatistics.stdError(), 1, 8, 1.)
		);

		System.out.println (
			"\t| Predictive Confidence Interval => " +
			FormatUtil.FormatDouble (sampleStatistics.predictiveConfidenceLevel(), 1, 8, 1.)
		);

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println (
			"\t| Next Draw                      => " +
			FormatUtil.FormatDouble (nextDraw, 1, 8, 1.)
		);

		System.out.println (
			"\t| Next Draw T-Statistics         => " +
			FormatUtil.FormatDouble (sampleStatistics.tStatistic (updatedMean), 1, 8, 1.)
		);

		System.out.println (
			"\t| Standard Error Offset          => " +
			FormatUtil.FormatDouble (sampleStatistics.standardErrorOffset (nextDraw), 1, 0, 1.)
		);

		System.out.println (
			"\t| Population Z-Score             => " +
			FormatUtil.FormatDouble (populationCentralMeasures.zScore (nextDraw), 1, 8, 1.)
		);

		System.out.println ("\t|--------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
