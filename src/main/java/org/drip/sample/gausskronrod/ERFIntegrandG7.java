
package org.drip.sample.gausskronrod;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.function.e2erf.BuiltInEntry;
import org.drip.function.e2erf.ErrorFunction;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.integration.GaussKronrodQuadratureGenerator;
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
 * <i>ERFIntegrandG7</i> computes the R<sup>1</sup> Numerical Estimate of the erf Integrand using the G7
 * Gaussian Quadrature Scheme. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/gausskronrod/README.md">R<sup>1</sup> Gauss-Kronrod Quadrature Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ERFIntegrandG7
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		R1ToR1 erfIntegrand = new ErrorFunction (
			null,
			null
		).integrand();

		Map<Double, BuiltInEntry> builtInTable = BuiltInEntry.Table();

		System.out.println ("\t|--------------------------------------||");

		System.out.println ("\t|       erf Integrand G7 Estimate      ||");

		System.out.println ("\t|--------------------------------------||");

		System.out.println ("\t|        L -> R:                       ||");

		System.out.println ("\t|                - x                   ||");

		System.out.println ("\t|                - Built-in Estimate   ||");

		System.out.println ("\t|                - G7 Estimate         ||");

		System.out.println ("\t|--------------------------------------||");

		for (Map.Entry<Double, BuiltInEntry> builtInTableEntry : builtInTable.entrySet())
		{
			double x = builtInTableEntry.getKey();

			double erfTable = builtInTableEntry.getValue().erf();

			double erfEstimate = GaussKronrodQuadratureGenerator.G7 (
				0.,
				x
			).integrate (erfIntegrand);

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (x, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (erfTable, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfEstimate, 1, 9, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
