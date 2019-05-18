
package org.drip.sample.hypergeometric;

import org.drip.function.definition.R1PropertyVerification;
import org.drip.function.definition.R1ToR1Property;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.property.HypergeometricEqualityLemma;

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
 * <i>VidunasHigherOrderTransformationProperty</i> verifies the Vidunas Higher-Order Transformation Identity
 * Lemma. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/hypergeometric/README.md">Estimates of Hyper-geometric Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VidunasHigherOrderTransformationProperty
{

	private static final void Verifier (
		final double[] zArray,
		final R1ToR1Property vidunasHigherOrderTransformationProperty)
		throws Exception
	{
		System.out.println ("\t|----------------------------------------------||");

		System.out.println ("\t| VIDUNAS HIGHER ORDER TRANSFORMATION PROPERTY ||");

		System.out.println ("\t|----------------------------------------------||");

		System.out.println ("\t|        L -> R:                               ||");

		System.out.println ("\t|                - z                           ||");

		System.out.println ("\t|                - LHS Value                   ||");

		System.out.println ("\t|                - RHS Value                   ||");

		System.out.println ("\t|                - Verification Success?       ||");

		System.out.println ("\t|----------------------------------------------||");

		for (double z : zArray)
		{
			R1PropertyVerification propertyVerification = vidunasHigherOrderTransformationProperty.verify
				(z);

			System.out.println (
				"\t|" + FormatUtil.FormatDouble (z, 1, 2, 1.) + " => " +
					FormatUtil.FormatDouble (propertyVerification.lValue(), 1, 10, 1.) + " | " +
					FormatUtil.FormatDouble (propertyVerification.rValue(), 1, 10, 1.) + " | " +
					propertyVerification.verified() + " ||"
			);
		}

		System.out.println ("\t|----------------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] zArray =
		{
			-0.99,
			-0.90,
			-0.80,
			-0.70,
			-0.60,
			-0.50,
			-0.40,
			-0.30,
			-0.20,
			-0.10,
			0.00,
			0.90,
			0.99
		};

		Verifier (
			zArray,
			HypergeometricEqualityLemma.VidunasHigherOrderTransformation()
		);

		EnvManager.TerminateEnv();
	}
}
