
package org.drip.sample.stirling;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.estimation.R1Estimate;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.loggamma.StirlingSeriesEstimator;

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
 * <i>LogFactorialEstimateNemesCorrection</i> illustrates the Nemes Correction applied to the Stirling's
 * 	Approximation of the Log Factorial Function. The References are:
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
 * 			Toth V. T. (2016): Programmable Calculators � The Gamma Function
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stirling/README.md">Stirling Approximation Based Gamma Estimates</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LogFactorialEstimateNemesCorrection
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

		int factorialCount = 12;

		StirlingSeriesEstimator logFactorial = new StirlingSeriesEstimator (null);

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
			R1Estimate numericalApproximation = logFactorial.nemesCorrectionEstimate
				(factorialIndex);

			double zeroOrder = numericalApproximation.baseline();

			double totalCorrection = numericalApproximation.seriesCumulative();

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
			R1Estimate numericalApproximation = logFactorial.nemesCorrectionEstimate
				(factorialIndex);

			double firstOrderCorrection = numericalApproximation.orderSeries (1);

			double thirdOrderCorrection = numericalApproximation.orderSeries (3);

			double fifthOrderCorrection = numericalApproximation.orderSeries (5);

			double seventhOrderCorrection = numericalApproximation.orderSeries (7);

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
