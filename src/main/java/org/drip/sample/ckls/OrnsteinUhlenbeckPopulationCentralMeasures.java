
package org.drip.sample.ckls;

import org.drip.dynamics.meanreverting.R1OrnsteinUhlenbeckStochasticEvolver;
import org.drip.dynamics.process.R1StochasticEvolver;
import org.drip.measure.statistics.PopulationCentralMeasures;
import org.drip.service.common.FormatUtil;
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
 * <i>OrnsteinUhlenbeckPopulationCentralMeasures</i> illustrates the Aging of Population Central Measures,
 * 	both Temporal and Steady-State, of an Evolving R<sup>1</sup> Ornstein-Uhlenbeck Process. The References
 * 	are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Bogoliubov, N. N., and D. P. Sankevich (1994): N. N. Bogoliubov and Statistical Mechanics
 * 				<i>Russian Mathematical Surveys</i> <b>49 (5)</b> 19-49
 * 		</li>
 * 		<li>
 * 			Holubec, V., K. Kroy, and S. Steffenoni (2019): Physically Consistent Numerical Solver for
 * 				Time-dependent Fokker-Planck Equations <i>Physical Review E</i> <b>99 (4)</b> 032117
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Ottinger, H. C. (1996): <i>Stochastic Processes in Polymeric Fluids</i> <b>Springer-Verlag</b>
 * 				Berlin-Heidelberg
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Fokker-Planck Equation
 * 				https://en.wikipedia.org/wiki/Fokker%E2%80%93Planck_equation
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/ckls/README.md">Analysis of CKLS Process Variants</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OrnsteinUhlenbeckPopulationCentralMeasures
{

	private static final void TemporalCentralMeasures (
		final double meanReversionSpeed,
		final double volatility,
		final double[] tArray)
		throws Exception
	{
		R1StochasticEvolver r1StochasticEvolver = R1OrnsteinUhlenbeckStochasticEvolver.Wiener (
			meanReversionSpeed,
			volatility,
			0.01
		);

		java.lang.String dump = "\t| [" + FormatUtil.FormatDouble (meanReversionSpeed, 1, 1, 1.) + "," +
			FormatUtil.FormatDouble (volatility, 1, 1, 1.) + "] =>";

		for (double t : tArray)
		{
			PopulationCentralMeasures populationCentralMeasures =
				r1StochasticEvolver.temporalPopulationCentralMeasures (
					meanReversionSpeed,
					t
				);

			dump = dump + " {" + FormatUtil.FormatDouble (
				populationCentralMeasures.mean(), 1, 4, 1.
			) + " |" + FormatUtil.FormatDouble (
				populationCentralMeasures.variance(), 1, 4, 1.
			) + "}";
		}

		System.out.println (dump);
	}

	private static final void SteadyStateCentralMeasures (
		final double meanReversionSpeed,
		final double volatility)
		throws Exception
	{
		PopulationCentralMeasures populationCentralMeasures = R1OrnsteinUhlenbeckStochasticEvolver.Wiener (
			meanReversionSpeed,
			volatility,
			0.01
		).steadyStatePopulationCentralMeasures (
			meanReversionSpeed
		);

		System.out.println (
			"\t| [" + FormatUtil.FormatDouble (
				meanReversionSpeed, 1, 1, 1.
			) + "," + FormatUtil.FormatDouble (
				volatility, 1, 1, 1.
			) + "] =>" + " {" + FormatUtil.FormatDouble (
				populationCentralMeasures.mean(), 1, 4, 1.
			) + " |" + FormatUtil.FormatDouble (
				populationCentralMeasures.variance(), 1, 4, 1.
			) + "}"
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);
		double[] tArray =
		{
			 1.0,
			 2.0,
			 3.0,
			 4.0,
			 5.0,
			 6.0,
			 7.0,
		};
		double[] volatilityArray =
		{
			 0.5,
			 1.0,
			 1.5,
			 2.0,
			 2.5,
		};
		double[] meanReversionSpeedArray =
		{
			 0.5,
			 1.0,
			 1.5,
			 2.0,
			 2.5,
		};

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                          ORNSTEIN-UHLENBECK FOKKER-PLANCK PDF TEMPORAL CENTRAL MEASURES                                           ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                                                    ||");

		System.out.println ("\t|                - Mean Reversion Level                                                                                                             ||");

		System.out.println ("\t|                - Volatility                                                                                                                       ||");

		System.out.println ("\t|                - Row of PDF {Mean | Variance} Tuple over t                                                                                        ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (double meanReversionSpeed : meanReversionSpeedArray)
		{
			for (double volatility : volatilityArray)
			{
				TemporalCentralMeasures (
					meanReversionSpeed,
					volatility,
					tArray
				);
			}
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                        ORNSTEIN-UHLENBECK FOKKER-PLANCK PDF STEADY STATE CENTRAL MEASURES                                         ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                                                    ||");

		System.out.println ("\t|                - Mean Reversion Level                                                                                                             ||");

		System.out.println ("\t|                - Volatility                                                                                                                       ||");

		System.out.println ("\t|                - PDF {Mean | Variance} Tuple                                                                                                      ||");

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (double meanReversionSpeed : meanReversionSpeedArray)
		{
			for (double volatility : volatilityArray)
			{
				SteadyStateCentralMeasures (
					meanReversionSpeed,
					volatility
				);
			}
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
