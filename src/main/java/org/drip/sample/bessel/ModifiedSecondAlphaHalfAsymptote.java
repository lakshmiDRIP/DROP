
package org.drip.sample.bessel;

import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.bessel.ModifiedSecondHankelAsymptoteEstimator;
import org.drip.specialfunction.bessel.ModifiedSecondIntegralEstimator;
import org.drip.specialfunction.definition.ModifiedBesselSecondKindEstimator;

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
 * <i>ModifiedSecondAlphaHalfAsymptote</i> illustrates the Large z Hankel Asymptote Series Based Estimation
 * 	for the Modified Bessel Function of the Second Kind, specifically for alpha = 0.5. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bessel/README.md">Estimates of the Bessel Functions</a></li>
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
