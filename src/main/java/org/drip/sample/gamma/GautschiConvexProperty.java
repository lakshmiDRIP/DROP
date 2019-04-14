
package org.drip.sample.gamma;

import org.drip.function.definition.R1ToR1Property;
import org.drip.function.definition.R1ToR1PropertyVerification;
import org.drip.function.gamma.InequalityProperties;
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
 * <i>GautschiConvexProperty</i> demonstrates the Verification of the Gautschi Double-Bound Convex Property
 * of the Gamma Function. The References are:
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

public class GautschiConvexProperty
{

	private static final void Verifier (
		final double s,
		final double[] zArray)
		throws Exception
	{
		System.out.println ("\t|----------------------------------------------------------------------||");

		System.out.println ("\t|               GAMMA FUNCTION GAUTSCHI CONVEX PROPERTY                ||");

		System.out.println ("\t|                               s = " + FormatUtil.FormatDouble (s, 1, 1, 1.));

		System.out.println ("\t|----------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                       ||");

		System.out.println ("\t|                - z                                                   ||");

		System.out.println ("\t|                - LHS Value                                           ||");

		System.out.println ("\t|                - Middle Value                                        ||");

		System.out.println ("\t|                - RHS Value                                           ||");

		System.out.println ("\t|                - Left Verification Success?                          ||");

		System.out.println ("\t|                - Right Verification Success?                         ||");

		System.out.println ("\t|----------------------------------------------------------------------||");

		R1ToR1Property gautschiLeftProperty = InequalityProperties.GautschiLeft (s);

		R1ToR1Property gautschiRightProperty = InequalityProperties.GautschiRight (s);

		for (double z : zArray)
		{
			R1ToR1PropertyVerification leftVerification = gautschiLeftProperty.verify (z);

			R1ToR1PropertyVerification rightVerification = gautschiRightProperty.verify (z);

			System.out.println (
				"\t|" + FormatUtil.FormatDouble (z, 2, 2, 1.) + " => " +
					FormatUtil.FormatDouble (leftVerification.lValue(), 1, 10, 1.) + " | " +
					FormatUtil.FormatDouble (leftVerification.rValue(), 1, 10, 1.) + " | " +
					FormatUtil.FormatDouble (rightVerification.rValue(), 1, 10, 1.) + " | " +
					leftVerification.verified() + " | " +
					rightVerification.verified() + " ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] sArray =
		{
			0.1,
			0.2,
			0.3,
			0.4,
			0.5,
			0.6,
			0.7,
			0.8,
			0.9,
		};
		double[] zArray =
		{
			1.5,
			2.0,
			2.5,
			3.0,
			3.5,
			4.0,
			4.5,
			5.0,
			5.5,
			6.0,
			6.5,
			7.0,
			7.5,
			8.0,
			8.5,
			9.0,
			9.5,
		};

		for (double s : sArray)
		{
			Verifier (
				s,
				zArray
			);
		}

		EnvManager.TerminateEnv();
	}
}
