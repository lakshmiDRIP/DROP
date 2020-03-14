
package org.drip.sample.gammaincomplete;

import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.integration.NewtonCotesQuadratureGenerator;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.incompletegamma.UpperLimitPowerIntegrand;

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
 * <i>UpperLimitPowerEstimate</i> illustrates the Estimation of the Integral of the Product of the Limit
 * Raised to an Exponent and the corresponding Upper Incomplete Gamma Function. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/gammaincomplete/README.md">Estimates of Incomplete Gamma Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UpperLimitPowerEstimate
{

	private static final void QuadratureComparison (
		final UpperLimitPowerIntegrand upperLimitPowerIntegrand,
		final double s,
		final double limitExponent,
		final double[] leftArray,
		final double[] rightArray,
		final int intermediatePointCount)
		throws Exception
	{
		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                         QUADRATURE COMPARISON - s => " + FormatUtil.FormatDouble (s, 1, 1, 1.) + " | Exponent => " + FormatUtil.FormatDouble (limitExponent, 1, 1, 1.));

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                           ||");

		System.out.println ("\t|                - s                                                                                                       ||");

		System.out.println ("\t|                - [Comparison Pair]                                                                                       ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------||");

		for (double left : leftArray)
		{
			String display = "\t|" + FormatUtil.FormatDouble (left, 1, 1, 1.) + " => ";

			for (double right : rightArray)
			{
				display = display + "[" + FormatUtil.FormatDouble (
					NewtonCotesQuadratureGenerator.Zero_PlusOne (
						left,
						right,
						intermediatePointCount
					).integrate (upperLimitPowerIntegrand), 4, 2, 1.
				) + " - " + FormatUtil.FormatDouble (
					upperLimitPowerIntegrand.integrate (
						left,
						right
					), 4, 2, 1.
				) + "] |";
			}

			System.out.println (display + "|");
		}

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int intermediatePointCount = 100;
		double[] leftArray =
		{
			1.,
			2.,
			3.,
			4.,
			5.,
		};
		double[] rightArray =
		{
			10.,
			15.,
			20.,
			25.,
			30.,
		};
		double[] sArray =
		{
			3.,
			4.,
			5.,
			6.,
			7.,
		};
		double[] limitExponentArray =
		{
			3.,
			4.,
			5.,
			6.,
			7.,
		};

		for (double s : sArray)
		{
			for (double limitExponent : limitExponentArray)
			{
				UpperLimitPowerIntegrand upperLimitPowerIntegrand = new UpperLimitPowerIntegrand (
					null,
					s,
					limitExponent
				);

				QuadratureComparison (
					upperLimitPowerIntegrand,
					s,
					limitExponent,
					leftArray,
					rightArray,
					intermediatePointCount
				);
			}
		}

		EnvManager.TerminateEnv();
	}
}
