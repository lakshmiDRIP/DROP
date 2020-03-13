
package org.drip.sample.gamma;

import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.derived.StretchedExponentialMoment;

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
 * <i>StretchedExponentialMomentEstimate</i> demonstrates the Estimation of the Moments of the Stretched
 * Exponential Function. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Blagouchine, I. V. (2014): Re-discovery of Malmsten's Integrals, their Evaluation by Contour
 * 				Integration Methods, and some Related Results <i>Ramanujan Journal</i> <b>35 (1)</b> 21-110
 * 		</li>
 * 		<li>
 * 			Borwein, J. M., and R. M. Corless (2017): Gamma Function and the Factorial in the Monthly
 * 				https://arxiv.org/abs/1703.05349 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Davis, P. J. (1959): Leonhard Euler's Integral: A Historical Profile of the Gamma Function
 * 				<i>American Mathematical Monthly</i> <b>66 (10)</b> 849-869
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/gamma/README.md">Estimates of the Gamma Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StretchedExponentialMomentEstimate
{

	private static final void Estimate (
		final double tau,
		final double beta,
		final double[] momentArray)
		throws Exception
	{
		StretchedExponentialMoment stretchedExponentialMomentIntegral = new StretchedExponentialMoment (
			null,
			tau,
			beta
		);

		StretchedExponentialMoment stretchedExponentialMomentWeierstrass =
			StretchedExponentialMoment.Weierstrass (
				tau,
				beta,
				1638400
			);

		String display = "\t|[" + FormatUtil.FormatDouble (tau, 1, 1, 1.) +
			"," + FormatUtil.FormatDouble (beta, 1, 1, 1.) + "] => ";

		for (double moment : momentArray)
		{
			display = display + "{" + FormatUtil.FormatDouble (
				stretchedExponentialMomentIntegral.evaluate (moment), 7, 2, 1.
			) + " |" + FormatUtil.FormatDouble (
				stretchedExponentialMomentWeierstrass.evaluate (moment), 7, 2, 1.
			) + "}";
		}

		System.out.println (display + "|");
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] tauArray =
		{
			0.5,
			1.0,
			1.5,
			2.0,
		};
		double[] betaArray =
		{
			0.5,
			1.0,
			1.5,
			2.0,
		};
		double[] momentArray =
		{
			1.,
			2.,
			3.,
			4.,
			5.,
		};

		System.out.println
			("\t|-------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println
			("\t|                                             STRETCHED EXPONENTIAL INTEGRAL vs. WERERSTRASS ESTIMATE                                             |");

		System.out.println
			("\t|-------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println
			("\t|        L -> R:                                                                                                                                  |");

		System.out.println
			("\t|                - Tau                                                                                                                            |");

		System.out.println
			("\t|                - Beta                                                                                                                           |");

		System.out.println
			("\t|                - Integral vs. Weierstrass Moment Comparison                                                                                     |");

		System.out.println
			("\t|-------------------------------------------------------------------------------------------------------------------------------------------------|");

		for (double tau : tauArray)
		{
			for (double beta : betaArray)
			{
				Estimate (
					tau,
					beta,
					momentArray
				);
			}
		}

		System.out.println
			("\t|-------------------------------------------------------------------------------------------------------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
