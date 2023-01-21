
package org.drip.sample.gamma;

import org.drip.service.common.FormatUtil;
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
 * <i>ComparativeEstimate</i> demonstrates the Comparisons across several Estimation Techniques of the Gamma
 * 	Function. The References are:
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

public class ComparativeEstimate
{

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
		EnvManager.InitEnv ("");

		int eulerTermCount = 1638400;
		int weierstrassTermCount = 1638400;
		double[] sArray =
		{
			0.01000,
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
