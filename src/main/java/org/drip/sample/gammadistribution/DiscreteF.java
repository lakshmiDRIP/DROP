
package org.drip.sample.gammadistribution;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.measure.gamma.R1ShapeScaleComposite;
import org.drip.measure.gamma.R1ShapeScaleDiscrete;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.digamma.CumulativeSeriesEstimator;
import org.drip.specialfunction.gamma.EulerIntegralSecondKind;
import org.drip.specialfunction.incompletegamma.LowerEulerIntegral;

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
 * <i>DiscreteF</i> illustrates the Generation of Discrete F Random Numbers using the Ahlers-Dieter and the
 * 	Marsaglia Schemes. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Gamma Distribution (2019): Gamma Distribution
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Louzada, F., P. L. Ramos, and E. Ramos (2019): A Note on Bias of Closed-Form Estimators for the
 * 				Gamma Distribution Derived From Likelihood Equations <i>The American Statistician</i> <b>73
 * 				(2)</b> 195-199
 * 		</li>
 * 		<li>
 * 			Minka, T. (2002): Estimating a Gamma distribution https://tminka.github.io/papers/minka-gamma.pdf
 * 		</li>
 * 		<li>
 * 			Ye, Z. S., and N. Chen (2017): Closed-Form Estimators for the Gamma Distribution Derived from
 * 				Likelihood Equations <i>The American Statistician</i> <b>71 (2)</b> 177-181
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/gammadistribution/README.md">R<sup>1</sup> Gamma Distribution Usage/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiscreteF
{

	private static final R2ToR1 LowerIncompleteGamma()
		throws Exception
	{
		return new R2ToR1()
		{
			@Override public double evaluate (
				final double s,
				final double t)
				throws Exception
			{
				return new LowerEulerIntegral (
					null,
					t
				).evaluate (
					s
				);
			}
		};
	}

	private static final void StatisticsArray (
		final UnivariateDiscreteThin ahrensDieterThinStatistics,
		final UnivariateDiscreteThin marsagliaThinStatistics)
		throws Exception
	{
		System.out.println (
			"\t| Average => " + FormatUtil.FormatDouble (
				ahrensDieterThinStatistics.average(), 2, 6, 1.
			) + " | " + FormatUtil.FormatDouble (
				marsagliaThinStatistics.average(), 2, 6, 1.
			) + " ||"
		);

		System.out.println (
			"\t| Error   => " + FormatUtil.FormatDouble (
				ahrensDieterThinStatistics.error(), 2, 6, 1.
			) + " | " + FormatUtil.FormatDouble (
				marsagliaThinStatistics.error(), 2, 6, 1.
			) + " ||"
		);

		System.out.println (
			"\t| Maximum => " + FormatUtil.FormatDouble (
				ahrensDieterThinStatistics.maximum(), 2, 6, 1.
			) + " | " + FormatUtil.FormatDouble (
				marsagliaThinStatistics.maximum(), 2, 6, 1.
			) + " ||"
		);

		System.out.println (
			"\t| Minimum => " + FormatUtil.FormatDouble (
				ahrensDieterThinStatistics.minimum(), 2, 6, 1.
			) + " | " + FormatUtil.FormatDouble (
				marsagliaThinStatistics.minimum(), 2, 6, 1.
			) + " ||"
		);
	}

	private static final void GenerateAndComputeStatistics (
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator,
		final double k1,
		final double theta1,
		final double k2,
		final double theta2,
		final int simulationCount)
		throws Exception
	{
		double[] marsagliaRandomArray = new double[simulationCount];
		double[] ahrensDieterRandomArray = new double[simulationCount];

		R1ShapeScaleDiscrete ahrensDieterGammaDiscrete1 = new R1ShapeScaleDiscrete (
			k1,
			theta1,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator,
			R1ShapeScaleDiscrete.DISCRETE_RANDOM_FROM_AHRENS_DIETER
		);

		R1ShapeScaleDiscrete ahrensDieterGammaDiscrete2 = new R1ShapeScaleDiscrete (
			k2,
			theta2,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator,
			R1ShapeScaleDiscrete.DISCRETE_RANDOM_FROM_AHRENS_DIETER
		);

		R1ShapeScaleDiscrete marsagilaGammaDiscrete1 = new R1ShapeScaleDiscrete (
			k1,
			theta1,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator,
			R1ShapeScaleDiscrete.DISCRETE_RANDOM_FROM_MARSAGLIA
		);

		R1ShapeScaleDiscrete marsagilaGammaDiscrete2 = new R1ShapeScaleDiscrete (
			k2,
			theta2,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator,
			R1ShapeScaleDiscrete.DISCRETE_RANDOM_FROM_MARSAGLIA
		);

		for (int simulationIndex = 0;
			simulationIndex < simulationCount;
			++simulationIndex)
		{
			marsagliaRandomArray[simulationIndex] = R1ShapeScaleComposite.RandomF (
				marsagilaGammaDiscrete1,
				marsagilaGammaDiscrete2
			);

			ahrensDieterRandomArray[simulationIndex] = R1ShapeScaleComposite.RandomF (
				ahrensDieterGammaDiscrete1,
				ahrensDieterGammaDiscrete2
			);
		}

		System.out.println (
			"\t|-------------------------------------------------||" 
		);

		System.out.println (
			"\t|       DISCRETE F RANDOM NUMBER GENERATION       ||" 
		);

		System.out.println (
			"\t|-------------------------------------------------||" 
		);

		System.out.println (
			"\t|          k1      => " + k1
		);

		System.out.println (
			"\t|          theta1  => " + theta1
		);

		System.out.println (
			"\t|          k2      => " + k2
		);

		System.out.println (
			"\t|          theta2  => " + theta2
		);

		System.out.println (
			"\t|-------------------------------------------------||" 
		);

		System.out.println (
			"\t|          - Using Ahrens-Dieter (1982)           ||" 
		);

		System.out.println (
			"\t|          - Using Marsaglia (1977)               ||" 
		);

		System.out.println (
			"\t|-------------------------------------------------||" 
		);

		StatisticsArray (
			new UnivariateDiscreteThin (
				ahrensDieterRandomArray
			),
			new UnivariateDiscreteThin (
				marsagliaRandomArray
			)
		);

		System.out.println (
			"\t|-------------------------------------------------||" 
		);

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		double[] k1Array = {
			1.5,
			1.0,
			0.5,
		};
		double[] k2Array = {
			1.5,
			1.0,
			0.5,
		};
		double[] theta1Array = {
			0.5,
			1.0,
			2.0,
		};
		double[] theta2Array = {
			0.5,
			1.0,
			2.0,
		};
		int simulationCount = 100000;
		int digammaTermCount = 1000;

		R1ToR1 gammaEstimator = new EulerIntegralSecondKind (
			null
		);

		R2ToR1 lowerIncompleteGammaEstimator = LowerIncompleteGamma();

		R1ToR1 digammaEstimator = CumulativeSeriesEstimator.AbramowitzStegun2007 (
			digammaTermCount
		);

		for (double k1 : k1Array)
		{
			for (double theta1 : theta1Array)
			{
				for (double k2 : k2Array)
				{
					for (double theta2 : theta2Array)
					{
						GenerateAndComputeStatistics (
							gammaEstimator,
							digammaEstimator,
							lowerIncompleteGammaEstimator,
							k1,
							theta1,
							k2,
							theta2,
							simulationCount
						);
					}
				}
			}
		}

		EnvManager.TerminateEnv();
	}
}
