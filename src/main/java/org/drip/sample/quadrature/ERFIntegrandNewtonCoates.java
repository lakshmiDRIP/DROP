
package org.drip.sample.quadrature;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.function.e2erf.BuiltInEntry;
import org.drip.function.e2erf.ErrorFunction;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.integration.NewtonCotesQuadrature;
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
 * <i>ERFIntegrandNewtonCoates</i> computes the R<sup>1</sup> Numerical Estimate of the erf Integrand using
 * Newton-Cotes Grids. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Briol, F. X., C. J. Oates, M. Girolami, and M. A. Osborne (2015): <i>Frank-Wolfe Bayesian
 * 				Quadrature: Probabilistic Integration with Theoretical Guarantees</i> <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Forsythe, G. E., M. A. Malcolm, and C. B. Moler (1977): <i>Computer Methods for Mathematical
 * 				Computation</i> <b>Prentice Hall</b> Englewood Cliffs NJ
 * 		</li>
 * 		<li>
 * 			Leader, J. J. (2004): <i>Numerical Analysis and Scientific Computation</i> <b>Addison Wesley</b>
 * 		</li>
 * 		<li>
 * 			Stoer, J., and R. Bulirsch (1980): <i>Introduction to Numerical Analysis</i>
 * 				<b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Numerical Integration https://en.wikipedia.org/wiki/Numerical_integration
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/quadrature/README.md">R<sup>1</sup> Numerical Integration Quadrature Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ERFIntegrandNewtonCoates
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

		int nodeCount10 = 10;
		int nodeCount50 = 50;
		int nodeCount100 = 100;

		NewtonCotesQuadrature newtonCotesQuadrature10 = new NewtonCotesQuadrature (
			erfIntegrand,
			nodeCount10
		);

		NewtonCotesQuadrature newtonCotesQuadrature50 = new NewtonCotesQuadrature (
			erfIntegrand,
			nodeCount50
		);

		NewtonCotesQuadrature newtonCotesQuadrature100 = new NewtonCotesQuadrature (
			erfIntegrand,
			nodeCount100
		);

		Map<Double, BuiltInEntry> builtInTable = BuiltInEntry.Table();

		System.out.println ("\t|--------------------------------------------------------------------||");

		System.out.println ("\t|                     Newton Coates erf Estimate                     ||");

		System.out.println ("\t|--------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                     ||");

		System.out.println ("\t|                - x                                                 ||");

		System.out.println ("\t|                - Built-in Estimate                                 ||");

		System.out.println ("\t|                - Newton Coates Estimate (10 Nodes)                 ||");

		System.out.println ("\t|                - Newton Coates Estimate (50 Nodes)                 ||");

		System.out.println ("\t|                - Newton Coates Estimate (100 Nodes)                ||");

		System.out.println ("\t|--------------------------------------------------------------------||");

		for (Map.Entry<Double, BuiltInEntry> builtInTableEntry : builtInTable.entrySet())
		{
			double x = builtInTableEntry.getKey();

			double erfTable = builtInTableEntry.getValue().erf();

			double erfEstimate10 = newtonCotesQuadrature10.integrate (
				0.,
				x
			);

			double erfEstimate50 = newtonCotesQuadrature50.integrate (
				0.,
				x
			);

			double erfEstimate100 = newtonCotesQuadrature100.integrate (
				0.,
				x
			);

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (x, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (erfTable, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfEstimate10, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfEstimate50, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (erfEstimate100, 1, 9, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
