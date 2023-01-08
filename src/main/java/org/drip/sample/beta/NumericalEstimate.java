
package org.drip.sample.beta;

import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.beta.IntegrandEstimator;
import org.drip.specialfunction.beta.LogGammaEstimator;
import org.drip.specialfunction.beta.SummationSeriesEstimator;

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
 * <i>NumericalEstimate</i> illustrates the Beta Function Estimation using Integrand Schemes. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
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
 * 			Wikipedia (2019): Beta Function https://en.wikipedia.org/wiki/Beta_function
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/beta/README.md">Estimates of the Beta Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NumericalEstimate
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

		int eulerIntegrandRightHalfPlaneTermCount = 1000;
		int trigonometricIntegrandTermCount = 1000;
		int eulerIntegrandTermCount = 1000;
		int summationTermCount = 1000000;
		int logGammaTermCount = 1000000;
		double[] xArray =
		{
			1.,
			2.,
			3.,
			4.,
			5.,
			6.,
		};
		double[] yArray =
		{
			1.,
			2.,
			3.,
			4.,
			5.,
			6.,
		};

		LogGammaEstimator logGammaEstimator = LogGammaEstimator.Weierstrass (logGammaTermCount);

		IntegrandEstimator eulerFirstIntegrandEstimator = IntegrandEstimator.EulerFirst
			(eulerIntegrandTermCount);

		IntegrandEstimator eulerFirstRightHalfPlane = IntegrandEstimator.EulerFirstRightPlane
			(eulerIntegrandRightHalfPlaneTermCount);

		IntegrandEstimator trigonometricIntegrandEstimator = IntegrandEstimator.Trigonometric
			(trigonometricIntegrandTermCount);

		SummationSeriesEstimator summationSeriesEstimator = SummationSeriesEstimator.AbramowitzStegun2007
			(summationTermCount);

		System.out.println ("\t|-----------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                 BETA FUNCTION ESTIMATION                                ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                          ||");

		System.out.println ("\t|                - x                                                                      ||");

		System.out.println ("\t|                - y                                                                      ||");

		System.out.println ("\t|                - Gamma Based Estimate                                                   ||");

		System.out.println ("\t|                - Euler Integral First Kind Estimate                                     ||");

		System.out.println ("\t|                - Euler Integral First Right Half Plane Estimate                         ||");

		System.out.println ("\t|                - Trigonometric Integral Estimate                                        ||");

		System.out.println ("\t|                - Summation Series Estimate                                              ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------||");

		for (double x : xArray)
		{
			for (double y : yArray)
			{
				System.out.println (
					"\t| [" +
					FormatUtil.FormatDouble (x, 1, 1, 1.) + "," +
					FormatUtil.FormatDouble (y, 1, 1, 1.) + "] =>" +
					FormatUtil.FormatDouble (
						Math.exp (
							logGammaEstimator.evaluate (
								x,
								y
							)
						), 1, 10, 1.
					) + " |" +
					FormatUtil.FormatDouble (
						eulerFirstIntegrandEstimator.evaluate (
							x,
							y
						), 1, 10, 1.
					) + " |" +
					FormatUtil.FormatDouble (
						eulerFirstRightHalfPlane.evaluate (
							x,
							y
						), 1, 10, 1.
					) + " |" +
					FormatUtil.FormatDouble (
						trigonometricIntegrandEstimator.evaluate (
							x,
							y
						), 1, 10, 1.
					) + " |" +
					FormatUtil.FormatDouble (
						summationSeriesEstimator.evaluate (
							x,
							y
						), 1, 10, 1.
					) + " ||"
				);
			}
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
