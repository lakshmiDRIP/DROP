
package org.drip.sample.hypergeometric;

import org.drip.function.definition.R1PropertyVerification;
import org.drip.function.definition.R2ToR1Property;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.property.HypergeometricEqualityLemma;

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
 * <i>GaussBaileyProperty</i> verifies the Gauss Bailey Identity Property Lemma for z = +0.5. The References
 * are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Gessel, I., and D. Stanton (1982): Strange Evaluations of Hyper-geometric Series <i>SIAM Journal
 * 				on Mathematical Analysis</i> <b>13 (2)</b> 295-308
 * 		</li>
 * 		<li>
 * 			Koepf, W (1995): Algorithms for m-fold Hyper-geometric Summation <i>Journal of Symbolic
 * 				Computation</i> <b>20 (4)</b> 399-417
 * 		</li>
 * 		<li>
 * 			Lavoie, J. L., F. Grondin, and A. K. Rathie (1996): Generalization of Whipple’s Theorem on the
 * 				Sum of a (_2^3)F(a,b;c;z) <i>Journal of Computational and Applied Mathematics</i> <b>72</b>
 * 				293-300
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Hyper-geometric Function
 * 				https://dlmf.nist.gov/15
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Hyper-geometric Function https://en.wikipedia.org/wiki/Hypergeometric_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/hypergeometric/README.md">Estimates of Hyper-geometric Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GaussBaileyProperty
{

	private static final void Verifier (
		final double a,
		final double c,
		final R2ToR1Property gaussBaileyProperty)
		throws Exception
	{
		R1PropertyVerification propertyVerification = gaussBaileyProperty.verify (
			a,
			c
		);

		System.out.println (
			"\t| {" + FormatUtil.FormatDouble (a, 1, 2, 1., false) + ", " +
			 	FormatUtil.FormatDouble (c, 1, 2, 1., false) + "} => " +
				FormatUtil.FormatDouble (propertyVerification.lValue(), 1, 10, 1.) + " | " +
				FormatUtil.FormatDouble (propertyVerification.rValue(), 1, 10, 1.) + " | " +
				propertyVerification.verified() + " ||"
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] aArray =
		{
			0.10,
			0.20,
			0.30,
			0.40,
			0.50,
			0.60,
			0.70,
			0.80,
			0.90,
		};
		double[] cArray =
		{
			1.0,
			2.0,
			3.0,
			4.0,
			5.0,
		};

		R2ToR1Property gaussBaileyProperty = HypergeometricEqualityLemma.GaussBaileyZPlusHalf();

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println ("\t|            GAUSS BAILEY PROPERTY VERIFIER            ||");

		System.out.println ("\t|------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                       ||");

		System.out.println ("\t|                - a                                   ||");

		System.out.println ("\t|                - c                                   ||");

		System.out.println ("\t|                - LHS                                 ||");

		System.out.println ("\t|                - RHS                                 ||");

		System.out.println ("\t|                - Verified?                           ||");

		System.out.println ("\t|------------------------------------------------------||");

		for (double a : aArray)
		{
			for (double c : cArray)
			{
				Verifier (
					a,
					c,
					gaussBaileyProperty
				);
			}
		}

		System.out.println ("\t|------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
