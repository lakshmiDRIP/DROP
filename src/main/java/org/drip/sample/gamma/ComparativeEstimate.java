
package org.drip.sample.gamma;

import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.gamma.EulerIntegralSecondKind;
import org.drip.specialfunction.gamma.LogReciprocal;
import org.drip.specialfunction.gamma.NemesAnalytic;
import org.drip.specialfunction.gamma.RamanujanSeries;
import org.drip.specialfunction.gamma.StirlingSeries;
import org.drip.specialfunction.gamma.WindschitlTothAnalytic;
import org.drip.specialfunction.loggamma.BinetIntegralFirstKindEstimator;
import org.drip.specialfunction.loggamma.BinetIntegralSecondKindEstimator;
import org.drip.specialfunction.loggamma.InfiniteSumEstimator;

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
 * <i>ComparativeEstimate</i> demonstrates the Comparisons across several Estimation Techniques of the Gamma
 * Function. The References are:
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

public class ComparativeEstimate
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int eulerTermCount = 1638400;
		int weierstrassTermCount = 1638400;
		double[] sArray =
		{
			0.05000,
			0.10000,
			0.15000,
			0.20000,
			0.25000,
			0.30000,
			0.35000,
			0.40000,
			0.45000,
			0.50000,
			0.60000,
			0.70000,
			0.80000,
			0.90000,
			1.00000,
			1.10000,
			1.20000,
			1.30000,
			1.40000,
			1.46163,
			1.50000,
			2.00000,
			2.50000,
			3.00000,
			3.50000,
			4.00000,
			4.50000,
			5.00000,
			5.50000,
			6.00000,
			6.50000,
			7.00000,
		};

		StirlingSeries factorial = new StirlingSeries (null);

		NemesAnalytic nemesGamma = new NemesAnalytic (null);

		LogReciprocal logReciprocal = new LogReciprocal (null);

		RamanujanSeries ramanujanGamma = new RamanujanSeries (null);

		WindschitlTothAnalytic windschitlTothGamma = new WindschitlTothAnalytic (null);

		InfiniteSumEstimator eulerInfiniteProduct = InfiniteSumEstimator.Euler (eulerTermCount);

		BinetIntegralFirstKindEstimator binetIntegralFirstKind = new BinetIntegralFirstKindEstimator (null);

		BinetIntegralSecondKindEstimator binetIntegralSecondKind = new BinetIntegralSecondKindEstimator (null);

		EulerIntegralSecondKind eulerIntegralSecondKind = new EulerIntegralSecondKind (null);

		InfiniteSumEstimator weierstrassInfiniteProduct = InfiniteSumEstimator.Weierstrass (weierstrassTermCount);

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                               GAMMA FUNCTION ESTIMATE                                                                ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                                                       ||");

		System.out.println ("\t|                - s                                                                                                                                   ||");

		System.out.println ("\t|                - Windschitl-Toth                                                                                                                     ||");

		System.out.println ("\t|                - Nemes                                                                                                                               ||");

		System.out.println ("\t|                - Euler Infinite Product Series                                                                                                       ||");

		System.out.println ("\t|                - Weierstrass Infinite Product Series                                                                                                 ||");

		System.out.println ("\t|                - Ramanujan                                                                                                                           ||");

		System.out.println ("\t|                - Stirling                                                                                                                            ||");

		System.out.println ("\t|                - Euler Integral Second Kind                                                                                                          ||");

		System.out.println ("\t|                - Log Reciprocal                                                                                                                      ||");

		System.out.println ("\t|                - Binet Integral First Kind                                                                                                           ||");

		System.out.println ("\t|                - Binet Integral Second Kind                                                                                                          ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (double s : sArray)
		{
			System.out.println (
				"\t|" + FormatUtil.FormatDouble (s, 1, 5, 1.) + " => " +
				FormatUtil.FormatDouble (windschitlTothGamma.evaluate (s), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (nemesGamma.evaluate (s), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (Math.exp (eulerInfiniteProduct.evaluate (s)), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (Math.exp (weierstrassInfiniteProduct.evaluate (s)), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (ramanujanGamma.evaluate (s), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (factorial.evaluate (s), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (eulerIntegralSecondKind.evaluate (s), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (logReciprocal.evaluate (s), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (Math.exp (binetIntegralFirstKind.evaluate (s)), 3, 6, 1.) + " | " +
				FormatUtil.FormatDouble (Math.exp (binetIntegralSecondKind.evaluate (s)), 3, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
