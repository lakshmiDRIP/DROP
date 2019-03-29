
package org.drip.sample.gaussquadrature;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.integration.GaussLobattoQuadratureGenerator;
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
 * <i>CubicPolyGaussLobatto</i> computes the R<sup>1</sup> Numerical Estimate of the Cubic Polynomial
 * Integrand using the Gauss-Lobatto Integration Quadrature Scheme. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/gaussquadrature/README.md">R<sup>1</sup> Gauss-Legendre Gauss-Lobatto Quadratures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CubicPolyGaussLobatto
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		R1ToR1 cubicPolyIntegrand = new R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				return 7. * x * x * x - 8. * x * x - 3. * x + 3;
			}
		};

		double[] xArray =
		{
			-0.90,
			-0.80,
			-0.70,
			-0.60,
			-0.50,
			-0.40,
			-0.30,
			-0.20,
			-0.10,
			 0.00,
			 0.10,
			 0.20,
			 0.30,
			 0.40,
			 0.50,
			 0.60,
			 0.70,
			 0.80,
			 0.90,
			 1.00,
		};

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		System.out.println ("\t|                             Cubic Polynomial Estimate                             ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                    ||");

		System.out.println ("\t|                - x                                                                ||");

		System.out.println ("\t|                - 7P Estimate                                                      ||");

		System.out.println ("\t|                - 6P Estimate                                                      ||");

		System.out.println ("\t|                - 5P Estimate                                                      ||");

		System.out.println ("\t|                - 4P Estimate                                                      ||");

		System.out.println ("\t|                - 3P Estimate                                                      ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		for (double x : xArray)
		{
			double estimate7P = GaussLobattoQuadratureGenerator.SevenPoint (
				-1.,
				x
			).integrate (cubicPolyIntegrand);

			double estimate6P = GaussLobattoQuadratureGenerator.SixPoint (
				-1.,
				x
			).integrate (cubicPolyIntegrand);

			double estimate5P = GaussLobattoQuadratureGenerator.FivePoint (
				-1.,
				x
			).integrate (cubicPolyIntegrand);

			double estimate4P = GaussLobattoQuadratureGenerator.FourPoint (
				-1.,
				x
			).integrate (cubicPolyIntegrand);

			double estimate3P = GaussLobattoQuadratureGenerator.ThreePoint (
				-1.,
				x
			).integrate (cubicPolyIntegrand);

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (x, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (estimate7P, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (estimate6P, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (estimate5P, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (estimate4P, 1, 9, 1.) + " | " +
				FormatUtil.FormatDouble (estimate3P, 1, 9, 1.) + " ||"
			);
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
