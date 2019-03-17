
package org.drip.sample.anfuso2017;

import java.util.ArrayList;
import java.util.List;

import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.EntityEquityLabel;
import org.drip.state.identifier.FXLabel;
import org.drip.validation.distance.GapLossWeightFunction;
import org.drip.validation.distance.GapTestOutcome;
import org.drip.validation.distance.GapTestSetting;
import org.drip.validation.evidence.Ensemble;
import org.drip.validation.evidence.Sample;
import org.drip.validation.evidence.TestStatisticEvaluator;
import org.drip.validation.riskfactorjoint.NormalSampleCohort;
import org.drip.validation.riskfactorsingle.DiscriminatoryPowerAnalyzer;

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
 * <i>ADCorrelationDiscriminatoryPowerAnalysis9e</i> demonstrates the Correlation Discriminatory Power
 * Analysis on an Ensemble of Hypothesis as seen in Table 9e of Anfuso, Karyampas, and Nawroth (2017).
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

public class ADCorrelationDiscriminatoryPowerAnalysis9e
{

	private static final double[][] CorrelationMatrix (
		final double correlation)
	{
		return new double[][]
		{
			{1.,          correlation},
			{correlation, 1.         }
		};
	}

	private static final Ensemble Hypothesis (
		final List<String> labelList,
		final double[] annualStateMeanArray,
		final double[] annualStateVolatilityArray,
		final double[][] correlationMatrix,
		final int vertexCount,
		final int sampleCount,
		final double horizon,
		final String label1,
		final String label2)
		throws Exception
	{
		Sample[] sampleArray = new Sample[sampleCount];

		for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex)
		{
			sampleArray[sampleIndex] = NormalSampleCohort.Correlated (
				labelList,
				annualStateMeanArray,
				annualStateVolatilityArray,
				correlationMatrix,
				vertexCount,
				horizon
			).reduce (
				label1,
				label2
			);
		}

		return new Ensemble (
			sampleArray,
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

		int sampleCount = 26;
		int vertexCount = 780;
		String currency = "USD";
		double horizon = 3. / 12.;
		double sampleCorrelation = 0.50;
		String equityEntity = "SNP500";
		String fxCurrencyPair = "CHF/USD";
		double[] annualStateMeanArray =
		{
			0.06,
			0.01
		};
		double[] annualStateVolatilityArray =
		{
			0.1,
			0.1
		};
		double[] hypothesisCorrelationArray =
		{
			-0.99,
			-0.50,
			 0.00,
			 0.50,
			 0.99
		};

		List<String> labelList = new ArrayList<String>();

		String snp500Label = EntityEquityLabel.Standard (
			equityEntity,
			currency
		).fullyQualifiedName();

		String chfusdLabel = FXLabel.Standard (fxCurrencyPair).fullyQualifiedName();

		labelList.add (snp500Label);

		labelList.add (chfusdLabel);

		Sample sample = NormalSampleCohort.Correlated (
			labelList,
			annualStateMeanArray,
			annualStateVolatilityArray,
			CorrelationMatrix (sampleCorrelation),
			vertexCount,
			horizon
		).reduce (
			snp500Label,
			chfusdLabel
		);

		DiscriminatoryPowerAnalyzer discriminatoryPowerAnalysis = DiscriminatoryPowerAnalyzer.FromSample (
			sample,
			GapTestSetting.RiskFactorLossTest (GapLossWeightFunction.AndersonDarling())
		);

		System.out.println ("\t|-----------------------||");

		System.out.println ("\t|   CORRELATION SCAN    ||");

		System.out.println ("\t|-----------------------||");

		System.out.println ("\t|    L -> R:            ||");

		System.out.println ("\t|        - Correlation  ||");

		System.out.println ("\t|        - Distance     ||");

		System.out.println ("\t|-----------------------||");

		for (double hypothesisCorrelation : hypothesisCorrelationArray)
		{
			Ensemble hypothesis = Hypothesis (
				labelList,
				annualStateMeanArray,
				annualStateVolatilityArray,
				CorrelationMatrix (hypothesisCorrelation),
				vertexCount,
				sampleCount,
				horizon,
				snp500Label,
				chfusdLabel
			);

			GapTestOutcome gapTestOutcome = discriminatoryPowerAnalysis.gapTest (hypothesis);

			System.out.println (
				"\t| " +
				FormatUtil.FormatDouble (hypothesisCorrelation, 1, 3, 1.) + " => " +
				FormatUtil.FormatDouble (DistanceTest (gapTestOutcome), 1, 8, 1.) + " ||"
			);
		}

		System.out.println ("\t|-----------------------||");

		EnvManager.TerminateEnv();
	}
}
