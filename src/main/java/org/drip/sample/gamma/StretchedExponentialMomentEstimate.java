
package org.drip.sample.gamma;

import org.drip.gamma.derived.StretchedExponentialMoment;
import org.drip.numerical.common.FormatUtil;
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/gamma/README.md">Integrand Estimates of Gamma Functions</a></li>
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
				Math.exp (stretchedExponentialMomentWeierstrass.evaluate (moment)), 7, 2, 1.
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
