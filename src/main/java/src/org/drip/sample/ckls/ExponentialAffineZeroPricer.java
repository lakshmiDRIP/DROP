
package org.drip.sample.ckls;

import org.drip.dynamics.meanreverting.R1CIRStochasticEvolver;
import org.drip.dynamics.physical.ExponentialAffineZeroCoefficients;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>ExponentialAffineZeroPricer</i> illustrates the Pricing of a Zero Coupon Bond using the R<sup>1</sup>
 * 	Cox-Ingersoll-Ross Process. The References are:
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

public class ExponentialAffineZeroPricer
{

	private static final void Price (
		final R1CIRStochasticEvolver r1CIRStochasticEvolver,
		final double[] ttmArray,
		final double[] rateArray)
		throws Exception
	{
		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t|      Mean Reversion Speed => " + r1CIRStochasticEvolver.cklsParameters().meanReversionSpeed()
		);

		System.out.println (
			"\t|      Mean Reversion Level => " + r1CIRStochasticEvolver.cklsParameters().meanReversionLevel()
		);

		System.out.println (
			"\t|      Volatility           => " + r1CIRStochasticEvolver.cklsParameters().volatilityCoefficient()
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		for (double ttm : ttmArray)
		{
			ExponentialAffineZeroCoefficients exponentialAffineZeroCoefficients =
				ExponentialAffineZeroCoefficients.FromCIR (
					r1CIRStochasticEvolver,
					ttm
				);

			String dump = "\t|" + FormatUtil.FormatDouble (ttm, 1, 1, 1.) + " => {" +
				FormatUtil.FormatDouble (exponentialAffineZeroCoefficients.a(), 2, 2, 1.) + " | " +
				FormatUtil.FormatDouble (exponentialAffineZeroCoefficients.b(), 2, 2, 1.) + "}  "; 

			for (double rate : rateArray)
			{
				dump = dump + " " + FormatUtil.FormatDouble (
					exponentialAffineZeroCoefficients.price (
						rate
					), 2, 3, 100., false
				) + " |";
			}

			System.out.println (dump + "|");
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------||"
		);

		System.out.println();
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
		EnvManager.InitEnv (
			""
		);

		double[] ttmArray =
		{
			5.0,
			4.5,
			4.0,
			3.5,
			3.0,
			2.5,
			2.0,
			1.5,
			1.0,
			0.5,
		};
		double[] rateArray =
		{
			0.08,
			0.07,
			0.06,
			0.05,
			0.04,
			0.03,
			0.02,
			0.01,
		};
		double[] meanReversionSpeedArray =
		{
			0.030,
			0.025,
			0.020,
			0.015,
			0.010,
		};
		double[] meanReversionLevelArray =
		{
			0.045,
			0.035,
			0.035,
			0.015,
			0.005,
		};
		double[] volatilityArray =
		{
			0.020,
			0.015,
			0.010,
			0.005,
		};

		for (double meanReversionLevel : meanReversionLevelArray)
		{
			for (double meanReversionSpeed : meanReversionSpeedArray)
			{
				for (double volatility : volatilityArray)
				{
					Price (
						R1CIRStochasticEvolver.Wiener (
							meanReversionSpeed,
							meanReversionLevel,
							volatility,
							0.01
						),
						ttmArray,
						rateArray
					);
				}
			}
		}

		EnvManager.TerminateEnv();
	}
}
