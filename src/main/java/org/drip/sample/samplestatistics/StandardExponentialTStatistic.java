
package org.drip.sample.samplestatistics;

import org.drip.measure.continuous.R1UnivariateExponential;
import org.drip.measure.statistics.PopulationCentralMeasures;
import org.drip.measure.statistics.UnivariateMoments;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.common.StringUtil;
import org.drip.service.env.EnvManager;

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
 * <i>StandardExponentialTStatistic</i> illustrates the Computation of the t-statistic, z-score, and other
 * related Metrics of the Sample/Population Mean for an Empirical Standard Exponential Distribution.
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
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/hypothesistest">Statistical Hypothesis Tests</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StandardExponentialTStatistic
{

	private static final double UnivariateRandom()
		throws Exception
	{
		return R1UnivariateExponential.Standard().random();
	}

	private static final PopulationCentralMeasures PopulationMeasures()
	{
		return R1UnivariateExponential.Standard().populationCentralMeasures();
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
