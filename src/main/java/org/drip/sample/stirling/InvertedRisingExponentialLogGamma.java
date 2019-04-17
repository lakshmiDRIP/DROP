
package org.drip.sample.stirling;

import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.estimation.R1Estimate;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.loggamma.RaabeSeriesEstimator;

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
 * <i>InvertedRisingExponentialLogGamma</i> illustrates the Convergent Corrections using the Inverted Rising
 * Exponentials applied to the Rabbe's Enhancement to the Stirling's Approximation of the Log Gamma Function.
 * The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Mortici, C. (2011): Improved Asymptotic Formulas for the Gamma Function <i>Computers and
 * 				Mathematics with Applications</i> <b>61 (11)</b> 3364-3369
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2018): NIST Digital Library of Mathematical
 * 				Functions https://dlmf.nist.gov/5.11
 * 		</li>
 * 		<li>
 * 			Nemes, G. (2010): On the Coefficients of the Asymptotic Expansion of n!
 * 				https://arxiv.org/abs/1003.2907 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Toth V. T. (2016): Programmable Calculators – The Gamma Function
 * 				http://www.rskey.org/CMS/index.php/the-library/11
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Stirling's Approximation
 * 				https://en.wikipedia.org/wiki/Stirling%27s_approximation
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/stirling/README.md">Stirling Approximation Based Gamma Estimates</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InvertedRisingExponentialLogGamma
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int factorialCount = 12;

		RaabeSeriesEstimator raabeLogGamma = new RaabeSeriesEstimator (null);

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println ("\t|   RAABE INVERTED RISING EXPONENTIAL LOG GAMMA    ||");

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println ("\t|      L -> R:                                     ||");

		System.out.println ("\t|              - Factorial Index                   ||");

		System.out.println ("\t|              - Stirling's Estimate               ||");

		System.out.println ("\t|              - Raabe's Correction                ||");

		System.out.println ("\t|              - Corrected Stirling's Estimate     ||");

		System.out.println ("\t|              - Log Gamma Value                   ||");

		System.out.println ("\t|--------------------------------------------------||");

		for (int factorialIndex = 1; factorialIndex <= factorialCount; ++factorialIndex)
		{
			R1Estimate numericalApproximation = raabeLogGamma.invertedRisingExponentialCorrectionEstimate
				(factorialIndex);

			double zeroOrder = numericalApproximation.baseline();

			double totalCorrection = numericalApproximation.seriesCumulative();

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (factorialIndex, 2, 0, 1.) + " => " +
				FormatUtil.FormatDouble (zeroOrder, 2, 4, 1.) + " | " +
				FormatUtil.FormatDouble (totalCorrection, 2, 4, 1.) + " | " +
				FormatUtil.FormatDouble (zeroOrder + totalCorrection, 2, 4, 1.) + " | " +
				FormatUtil.FormatDouble (Math.log (NumberUtil.Factorial (factorialIndex - 1)), 2, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|------------------------------------------------------------||");

		System.out.println ("\t|   RAABE INVERTED RISING EXPONENTIAL LOG GAMMA CORRECTION   ||");

		System.out.println ("\t|------------------------------------------------------------||");

		System.out.println ("\t|      L -> R:                                               ||");

		System.out.println ("\t|              - Factorial Index                             ||");

		System.out.println ("\t|              - Raabe's First Order Correction              ||");

		System.out.println ("\t|              - Raabe's Second Order Correction             ||");

		System.out.println ("\t|              - Raabe's Third Order Correction              ||");

		System.out.println ("\t|              - Raabe's Fourth Order Correction             ||");

		System.out.println ("\t|------------------------------------------------------------||");

		for (int factorialIndex = 1; factorialIndex <= factorialCount; ++factorialIndex)
		{
			R1Estimate numericalApproximation = raabeLogGamma.invertedRisingExponentialCorrectionEstimate
				(factorialIndex);

			double firstOrderCorrection = numericalApproximation.orderSeries (1);

			double secondOrderCorrection = numericalApproximation.orderSeries (2);

			double thirdOrderCorrection = numericalApproximation.orderSeries (3);

			double fourthOrderCorrection = numericalApproximation.orderSeries (4);

			double totalCorrection = firstOrderCorrection + secondOrderCorrection + thirdOrderCorrection +
				fourthOrderCorrection;

			System.out.println (
				"\t|" + FormatUtil.FormatDouble (factorialIndex, 2, 0, 1.) + " => " +
				FormatUtil.FormatDouble (firstOrderCorrection, 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (secondOrderCorrection, 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (thirdOrderCorrection, 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (fourthOrderCorrection, 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (totalCorrection, 1, 5, 1.) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
