
package org.drip.sample.gamma;

import org.drip.function.definition.R1NumericalEstimate;
import org.drip.function.stirling.LogFactorial;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.common.NumberUtil;
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
 * <i>StirlingLogFactorialNemesCorrection</i> illustrates the Nemes Correction applied to the Stirling's
 * Approximation of the Log Factorial Function. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/gamma/README.md">Numerical Estimates of Gamma Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StirlingLogFactorialNemesCorrection
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int factorialCount = 12;

		LogFactorial logFactorial = new LogFactorial (null);

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println ("\t|      STIRLING LOG FACTORIAL NEMES CORRECTION     ||");

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println ("\t|      L -> R:                                     ||");

		System.out.println ("\t|              - Factorial Index                   ||");

		System.out.println ("\t|              - Stirling's Estimate               ||");

		System.out.println ("\t|              - Nemes' Correction                 ||");

		System.out.println ("\t|              - Corrected Stirling's Estimate     ||");

		System.out.println ("\t|              - Log Factorial Value               ||");

		System.out.println ("\t|--------------------------------------------------||");

		for (int factorialIndex = 1; factorialIndex <= factorialCount; ++factorialIndex)
		{
			R1NumericalEstimate numericalApproximation = logFactorial.nemesCorrectionEstimate
				(factorialIndex);

			double zeroOrder = numericalApproximation.zeroOrder();

			double totalCorrection = numericalApproximation.correction();

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (factorialIndex, 2, 0, 1.) + " => " +
				FormatUtil.FormatDouble (zeroOrder, 2, 4, 1.) + " | " +
				FormatUtil.FormatDouble (totalCorrection, 2, 4, 1.) + " | " +
				FormatUtil.FormatDouble (zeroOrder + totalCorrection, 2, 4, 1.) + " | " +
				FormatUtil.FormatDouble (Math.log (NumberUtil.Factorial (factorialIndex)), 2, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|------------------------------------------------------------||");

		System.out.println ("\t|             STIRLING FACTORIAL NEMES CORRECTION            ||");

		System.out.println ("\t|------------------------------------------------------------||");

		System.out.println ("\t|      L -> R:                                               ||");

		System.out.println ("\t|              - Factorial Index                             ||");

		System.out.println ("\t|              - Nemes' First Order Correction               ||");

		System.out.println ("\t|              - Nemes' Third Order Correction               ||");

		System.out.println ("\t|              - Nemes' Fifth Order Correction               ||");

		System.out.println ("\t|              - Nemes' Seventh Order Correction             ||");

		System.out.println ("\t|------------------------------------------------------------||");

		for (int factorialIndex = 1; factorialIndex <= factorialCount; ++factorialIndex)
		{
			R1NumericalEstimate numericalApproximation = logFactorial.nemesCorrectionEstimate
				(factorialIndex);

			double firstOrderCorrection = numericalApproximation.orderCorrection (1);

			double thirdOrderCorrection = numericalApproximation.orderCorrection (3);

			double fifthOrderCorrection = numericalApproximation.orderCorrection (5);

			double seventhOrderCorrection = numericalApproximation.orderCorrection (7);

			double totalCorrection = firstOrderCorrection + thirdOrderCorrection + fifthOrderCorrection +
				seventhOrderCorrection;

			System.out.println (
				"\t|" + FormatUtil.FormatDouble (factorialIndex, 2, 0, 1.) + " => " +
				FormatUtil.FormatDouble (firstOrderCorrection, 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (thirdOrderCorrection, 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (fifthOrderCorrection, 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (seventhOrderCorrection, 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (totalCorrection, 1, 5, 1.) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
