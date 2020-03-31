
package org.drip.sample.gaussquadrature;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.function.e2erf.BuiltInEntry;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.integration.GaussLegendreQuadratureGenerator;
import org.drip.service.env.EnvManager;

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
 * <i>ERFCCraig1991GaussLegendre</i> computes the R<sup>1</sup> Numerical Estimate of the erfc Integrand
 * 	using the Gauss-Legendre Integration Quadrature Scheme. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Gil, A., J. Segura, and N. M. Temme (2007): <i>Numerical Methods for Special Functions</i>
 * 				<b>Society for Industrial and Applied Mathematics</b> Philadelphia
 * 		</li>
 * 		<li>
 * 			Press, W. H., S. A. Teukolsky, W. T. Vetterling, and B. P. Flannery (2007): <i>Numerical Recipes:
 * 				The Art of Scientific Computing 3rd Edition</i> <b>Cambridge University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Stoer, J., and R. Bulirsch (2002): <i>Introduction to Numerical Analysis 3rd Edition</i>
 * 				<b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gaussian Quadrature https://en.wikipedia.org/wiki/Gaussian_quadrature
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/gaussquadrature/README.md">R<sup>1</sup> Gauss-Legendre Gauss-Lobatto Quadratures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ERFCCraig1991GaussLegendre
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		Map<Double, BuiltInEntry> builtInTable = BuiltInEntry.Table();

		System.out.println ("\t|--------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                     Craig 1991 erfc Estimate                                     ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                   ||");

		System.out.println ("\t|                - x                                                                               ||");

		System.out.println ("\t|                - Built-in Estimate                                                               ||");

		System.out.println ("\t|                - 5P Estimate                                                                     ||");

		System.out.println ("\t|                - 4P Estimate                                                                     ||");

		System.out.println ("\t|                - 3P Estimate                                                                     ||");

		System.out.println ("\t|                - 2P Estimate                                                                     ||");

		System.out.println ("\t|                - 1P Estimate                                                                     ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------||");

		for (Map.Entry<Double, BuiltInEntry> builtInTableEntry : builtInTable.entrySet())
		{
			final double x = builtInTableEntry.getKey();

			double erfcTable = builtInTableEntry.getValue().erfc();

			R1ToR1 erfcIntegrand = new R1ToR1 (null)
			{
				@Override public double evaluate (
					final double theta)
					throws java.lang.Exception
				{
					if (0. == theta)
					{
						return 0.;
					}

					double sinTheta = java.lang.Math.sin (theta);

					return 2. * java.lang.Math.exp (-1. * x * x / (sinTheta * sinTheta)) / Math.PI;
				}
			};

			double erfcEstimate5P = GaussLegendreQuadratureGenerator.FivePoint (
				0.,
				0.5 * Math.PI
			).integrate (erfcIntegrand);

			double erfcEstimate4P = GaussLegendreQuadratureGenerator.FourPoint (
				0.,
				0.5 * Math.PI
			).integrate (erfcIntegrand);

			double erfcEstimate3P = GaussLegendreQuadratureGenerator.ThreePoint (
				0.,
				0.5 * Math.PI
			).integrate (erfcIntegrand);

			double erfcEstimate2P = GaussLegendreQuadratureGenerator.TwoPoint (
				0.,
				0.5 * Math.PI
			).integrate (erfcIntegrand);

			double erfcEstimate1P = GaussLegendreQuadratureGenerator.OnePoint (
				0.,
				0.5 * Math.PI
			).integrate (erfcIntegrand);

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (x, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (erfcTable, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfcEstimate5P, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfcEstimate4P, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfcEstimate3P, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfcEstimate2P, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfcEstimate1P, 1, 9, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
