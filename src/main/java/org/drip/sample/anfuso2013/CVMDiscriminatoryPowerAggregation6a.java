
package org.drip.sample.anfuso2013;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.analytics.support.Helper;
import org.drip.measure.gaussian.R1UnivariateNormal;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.distance.GapLossFunction;
import org.drip.validation.distance.GapLossWeightFunction;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;
import org.drip.validation.hypothesis.ProbabilityIntegralTransform;
import org.drip.validation.riskfactor.DiscriminatoryPowerAnalyzerAggregate;
import org.drip.validation.riskfactor.DiscriminatoryPowerAnalyzerSetting;
import org.drip.validation.riskfactor.EventAggregationWeightFunction;
import org.drip.validation.riskfactor.HypothesisOutcomeSuiteAggregate;
import org.drip.validation.riskfactor.HypothesisSuiteAggregate;

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
 * <i>CVMDiscriminatoryPowerAggregation6a</i> demonstrates Multi-Horizon Discriminatory Power Aggregation
 * illustrated in Table 6a of Anfuso, Karyampas, and Nawroth (2013).
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationLibrary.md">Model Validation Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/anfuso2013">Anfuso, Karyampas, and Nawroth (2013) Replications</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CVMDiscriminatoryPowerAggregation6a
{

	private static final double UnivariateRandom (
		final double mean,
		final double volatility)
		throws Exception
	{
		return new R1UnivariateNormal (
			mean,
			volatility
		).random();
	}

	private static final Sample GenerateSample (
		final double annualMean,
		final double annualVolatility,
		final String horizonTenor,
		final int drawCount)
		throws Exception
	{
		double[] univariateRandomArray = new double[drawCount];

		double horizonYF = Helper.TenorToYearFraction (horizonTenor);

		double horizonYFSQRT = Math.sqrt (horizonYF);

		for (int drawIndex = 0; drawIndex < drawCount; ++drawIndex)
		{
			univariateRandomArray[drawIndex] = UnivariateRandom (
				annualMean * horizonYF,
				annualVolatility * horizonYFSQRT
			);
		}

		return new Sample (univariateRandomArray);
	}

	private static final Map<String, ProbabilityIntegralTransform> EventSamplePITMap (
		final double annualMean,
		final double annualVolatility,
		final String[] horizonTenorArray,
		final int drawCount)
		throws Exception
	{
		Map<String, ProbabilityIntegralTransform> eventSamplePITMap = new
			CaseInsensitiveHashMap<ProbabilityIntegralTransform>();

		for (int horizonIndex = 0; horizonIndex < horizonTenorArray.length; ++horizonIndex)
		{
			eventSamplePITMap.put (
				horizonTenorArray[horizonIndex],
				GenerateSample (
					annualMean,
					annualVolatility,
					horizonTenorArray[horizonIndex],
					drawCount
				).nativeProbabilityIntegralTransform()
			);
		}

		return eventSamplePITMap;
	}

	private static final Sample[] GenerateSampleArray (
		final double annualMean,
		final double annualVolatility,
		final String horizonTenor,
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		Sample[] sampleArray = new Sample[sampleCount];

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			sampleArray[sampleIndex] = GenerateSample (
				annualMean,
				annualVolatility,
				horizonTenor,
				drawCount
			);
		}

		return sampleArray;
	}

	private static final Ensemble GenerateEnsemble (
		final double hypothesisAnnualMean,
		final double hypothesisAnnualVolatility,
		final String horizonTenor,
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		return new Ensemble (
			GenerateSampleArray (
				hypothesisAnnualMean,
				hypothesisAnnualVolatility,
				horizonTenor,
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

	private static final HypothesisSuiteAggregate HypothesisEventMap (
		final double[] hypothesisAnnualMeanArray,
		final double[] hypothesisAnnualVolatilityArray,
		final String[] horizonTenorArray,
		final int drawCount,
		final int sampleCount)
		throws Exception
	{
		HypothesisSuiteAggregate hypothesisSuiteAggregate = new HypothesisSuiteAggregate();

		for (double hypothesisAnnualMean : hypothesisAnnualMeanArray)
		{
			for (double hypothesisAnnualVolatility : hypothesisAnnualVolatilityArray)
			{
				String hypothesisID = "HYPOTHESIS_" +
					FormatUtil.FormatDouble (hypothesisAnnualMean, 2, 4, 1.) + "_" +
					FormatUtil.FormatDouble (hypothesisAnnualVolatility, 2, 4, 1.);

				for (String horizonTenor : horizonTenorArray)
				{
					hypothesisSuiteAggregate.add (
						hypothesisID,
						horizonTenor,
						GenerateEnsemble (
							hypothesisAnnualMean,
							hypothesisAnnualVolatility,
							horizonTenor,
							drawCount,
							sampleCount
						)
					);
				}
			}
		}

		return hypothesisSuiteAggregate;
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int drawCount = 500;
		int sampleCount = 50;
		double sampleAnnualMean = 0.;
		double sampleAnnualVolatility = 0.1;
		String[] horizonTenorArray =
		{
			"3M",
			"6M",
			"1Y"
		};
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

		DiscriminatoryPowerAnalyzerSetting discriminatoryPowerAnalyzerSetting = new
			DiscriminatoryPowerAnalyzerSetting (
				GapLossFunction.AnfusoKaryampasNawroth(),
				GapLossWeightFunction.CramersVonMises()
			);

		EventAggregationWeightFunction eventAggregationWeightFunction =
			EventAggregationWeightFunction.AnfusoKaryampasNawroth();

		Map<String, ProbabilityIntegralTransform> eventSamplePITMap = EventSamplePITMap (
			sampleAnnualMean,
			sampleAnnualVolatility,
			horizonTenorArray,
			drawCount
		);

		DiscriminatoryPowerAnalyzerAggregate discriminatoryPowerAnalyzerAggregate = new
			DiscriminatoryPowerAnalyzerAggregate (
				eventSamplePITMap,
				discriminatoryPowerAnalyzerSetting,
				eventAggregationWeightFunction
			);

		HypothesisSuiteAggregate hypothesisSuiteAggregate = HypothesisEventMap (
			hypothesisAnnualMeanArray,
			hypothesisAnnualVolatilityArray,
			horizonTenorArray,
			drawCount,
			sampleCount
		);

		System.out.println (hypothesisSuiteAggregate);

		HypothesisOutcomeSuiteAggregate hypothesisOutcomeSuiteAggregate =
			discriminatoryPowerAnalyzerAggregate.hypothesisGapTest (hypothesisSuiteAggregate);

		System.out.println (hypothesisOutcomeSuiteAggregate);

		EnvManager.TerminateEnv();
	}
}
