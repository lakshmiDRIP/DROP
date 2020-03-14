
package org.drip.sample.gausskronrod;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.function.e2erf.BuiltInEntry;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.integration.GaussKronrodQuadratureGenerator;
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
 * <i>ERFCCraig1991G7</i> computes the R<sup>1</sup> Numerical Estimate of the erfc Integrand using the G7
 * Gaussian Integration Quadrature Scheme. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Holoborodko, P. (2011): Gauss-Kronrod Quadrature Nodes and Weights
 * 				https://www.advanpix.com/2011/11/07/gauss-kronrod-quadrature-nodes-weights/
 * 		</li>
 * 		<li>
 * 			Kahaner, D., C. Moler, and S. Nash (1989): <i>Numerical Methods and Software</i> <b>Prentice
 * 				Hall</b>
 * 		</li>
 * 		<li>
 * 			Laurie, D. (1997): Calculation of Gauss-Kronrod Quadrature Rules <i>Mathematics of
 * 				Computation</i> <b>66 (219)</b> 1133-1145
 * 		</li>
 * 		<li>
 * 			Piessens, R., E. de Doncker-Kapenga, C. W. Uberhuber, and D. K. Kahaner (1983): <i>QUADPACK – A
 * 				Subroutine Package for Automatic Integration</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gauss-Kronrod Quadrature Formula
 * 				https://en.wikipedia.org/wiki/Gauss%E2%80%93Kronrod_quadrature_formula
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/gausskronrod/README.md">R<sup>1</sup> Gauss-Kronrod Quadrature Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ERFCCraig1991G7
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		Map<Double, BuiltInEntry> builtInTable = BuiltInEntry.Table();

		System.out.println ("\t|--------------------------------------||");

		System.out.println ("\t|       Craig 1991 erfc Estimate       ||");

		System.out.println ("\t|--------------------------------------||");

		System.out.println ("\t|        L -> R:                       ||");

		System.out.println ("\t|                - x                   ||");

		System.out.println ("\t|                - Built-in Estimate   ||");

		System.out.println ("\t|                - G7 Estimate         ||");

		System.out.println ("\t|--------------------------------------||");

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

			double erfcEstimate = GaussKronrodQuadratureGenerator.G7 (
				0.,
				0.5 * Math.PI
			).integrate (erfcIntegrand);

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (x, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (erfcTable, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfcEstimate, 1, 9, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
