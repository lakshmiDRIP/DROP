
package org.drip.sample.bessel;

import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.bessel.ModifiedSecondHankelAsymptoteEstimator;
import org.drip.specialfunction.bessel.ModifiedSecondIntegralEstimator;
import org.drip.specialfunction.definition.ModifiedBesselSecondKindEstimator;

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
 * <i>ModifiedSecondAlphaHalfAsymptote</i> illustrates the Large z Hankel Asymptote Series Based Estimation
 * for the Modified Bessel Function of the Second Kind, specifically for alpha = 0.5. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Arfken, G. B., and H. J. Weber (2005): <i>Mathematical Methods for Physicists 6<sup>th</sup>
 * 				Edition</i> <b>Harcourt</b> San Diego
 * 		</li>
 * 		<li>
 * 			Temme N. M. (1996): <i>Special Functions: An Introduction to the Classical Functions of
 * 				Mathematical Physics 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York
 * 		</li>
 * 		<li>
 * 			Watson, G. N. (1995): <i>A Treatise on the Theory of Bessel Functions</i> <b>Cambridge University
 * 				Press</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Bessel Function https://en.wikipedia.org/wiki/Bessel_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Project</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/ode/README.md">Special Function Ordinary Differential Equations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ModifiedSecondAlphaHalfAsymptote
{

	private static final void BesselK (
		final ModifiedBesselSecondKindEstimator modifiedBesselSecondKindEstimatorIntegral,
		final ModifiedBesselSecondKindEstimator modifiedBesselSecondKindEstimatorAsymptote,
		final double[] zArray)
		throws Exception
	{
		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println ("\t|     MODIFIED BESSEL SECOND KIND ALPHA = 0.5 ESTIMATE    ||");

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                          ||");

		System.out.println ("\t|                - z                                      ||");

		System.out.println ("\t|                - Integral Estimate                      ||");

		System.out.println ("\t|                - Hankel Asymptote Estimate              ||");

		System.out.println ("\t|                - Exponential Estimate                   ||");

		System.out.println ("\t|---------------------------------------------------------||");

		for (double z : zArray)
		{
			System.out.println ("\t| [" + FormatUtil.FormatDouble (z, 2, 1, 1., false) + "] => " +
				FormatUtil.FormatDouble (
					modifiedBesselSecondKindEstimatorIntegral.bigK (
						0.5,
						z
					), 1, 10, 1.
				) + " | " +
				FormatUtil.FormatDouble (
					modifiedBesselSecondKindEstimatorAsymptote.bigK (
						0.5,
						z
					), 1, 10, 1.
				) + " | " +
				FormatUtil.FormatDouble (
					Math.sqrt (0.5 * Math.PI / z) * Math.exp (-z), 1, 10, 1.
				) + " ||"
			);
		}

		System.out.println ("\t|---------------------------------------------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int quadratureCount = 40;
		int hankelAsymptoteTermCount = 5;
		double[] zArray =
		{
			   0.5,
			   1.0,
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
			  10.0,
			  10.5,
			  11.0,
		};

		BesselK (
			ModifiedSecondIntegralEstimator.Standard (quadratureCount),
			ModifiedSecondHankelAsymptoteEstimator.Standard (hankelAsymptoteTermCount),
			zArray
		);

		EnvManager.TerminateEnv();
	}
}
