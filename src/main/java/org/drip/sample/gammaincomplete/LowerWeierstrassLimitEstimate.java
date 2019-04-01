
package org.drip.sample.gammaincomplete;

import org.drip.function.gammaincomplete.LowerEulerIntegral;
import org.drip.function.gammaincomplete.LowerSFixed;
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
 * <i>LowerWeierstrassLimitEstimate</i> illustrates the Estimation of the Lower Incomplete Gamma Function
 * using the Weierstrass Limit Series. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Geddes, K. O., M. L. Glasser, R. A. Moore, and T. C. Scott (1990): Evaluation of Classes of
 * 				Definite Integrals involving Elementary Functions via Differentiation of Special Functions
 * 				<i>Applicable Algebra in Engineering, Communications, and </i> <b>1 (2)</b> 149-165
 * 		</li>
 * 		<li>
 * 			Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 * 				<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b>
 * 		</li>
 * 		<li>
 * 			Mathar, R. J. (2010): Numerical Evaluation of the Oscillatory Integral over
 *				e<sup>iπx</sup> x<sup>(1/x)</sup> between 1 and ∞
 *				https://arxiv.org/pdf/0912.3844.pdf <b>arXiV</b>
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019a): Incomplete Gamma and Related Functions
 * 				https://dlmf.nist.gov/8
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Incomplete Gamma Function
 * 				https://en.wikipedia.org/wiki/Incomplete_gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/gammaincomplete/README.md">Estimates of Incomplete Gamma Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LowerWeierstrassLimitEstimate
{

	private static final void EulerIntegralComparison (
		final int termCount,
		final double s,
		final double[] zArray)
		throws Exception
	{
		LowerSFixed lowerSFixed = LowerSFixed.WeierstrassLimit (
			s,
			termCount
		);

		System.out.println ("\t|-------------------------------------------------------------------------||");

		System.out.println ("\t|                              TERM COUNT => " + FormatUtil.FormatDouble (termCount, 2, 0, 1.));

		System.out.println ("\t|-------------------------------------------------------------------------||");

		System.out.println ("\t|    L - R:                                                               ||");

		System.out.println ("\t|            - Weierstrass Limit                                          ||");

		System.out.println ("\t|            - Scaled Lower Incomplete Gamma                              ||");

		System.out.println ("\t|            - Unscaled Lower Incomplete Gamma                            ||");

		System.out.println ("\t|-------------------------------------------------------------------------||");

		for (double z : zArray)
		{
			LowerEulerIntegral lowerEulerIntegral = new LowerEulerIntegral (
				null,
				z
			);

			System.out.println (
				"\t|" + FormatUtil.FormatDouble (z, 2, 0, 1.) + " => " +
				FormatUtil.FormatDouble (lowerSFixed.weierstrassLimit (z), 1, 10, 1.) + " | " +
				FormatUtil.FormatDouble (lowerSFixed.nonDimensional (z), 1, 10, 1.) + " | " +
				FormatUtil.FormatDouble (lowerSFixed.evaluate (z), 3, 10, 1.) + " | " +
				FormatUtil.FormatDouble (lowerEulerIntegral.evaluate (s), 3, 10, 1.) + " ||"
			);
		}

		System.out.println ("\t|-------------------------------------------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double s = 6.;
		int[] termCountArray =
		{
			20,
			40,
			60,
			80,
		};
		double[] zArray =
		{
			20.0,
			18.0,
			16.0,
			14.0,
			12.0,
			10.0,
			 8.0,
			 6.0,
			 4.0,
			 2.0
		};

		for (int termCount : termCountArray)
		{
			EulerIntegralComparison (
				termCount,
				s,
				zArray
			);
		}

		EnvManager.TerminateEnv();
	}
}
