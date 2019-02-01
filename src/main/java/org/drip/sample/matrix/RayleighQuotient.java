
package org.drip.sample.matrix;

import org.drip.quant.common.FormatUtil;
import org.drip.quant.common.NumberUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>RayleighQuotient</i> demonstrates the Computation of an Approximate to the Eigenvalue using the
 * Rayleigh Quotient. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Wikipedia - Power Iteration (2018): https://en.wikipedia.org/wiki/Power_iteration
 *  	</li>
 *  	<li>
 *  		Wikipedia - Rayleigh Quotient Iteration (2018):
 *  			https://en.wikipedia.org/wiki/Rayleigh_quotient_iteration
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalSupportLibrary.md">Numerical Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/matrix/README.md">Linear Algebra and Matrix Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RayleighQuotient
{

	private static final void EigenDump (
		final int iteration,
		final double[] eigenvector,
		final double eigenvalue)
		throws Exception
	{
		java.lang.String strDump = "\t|| Iteration => " + FormatUtil.FormatDouble (iteration, 2, 0, 1.) +
			"[" + FormatUtil.FormatDouble (eigenvalue, 3, 4, 1.) + "] => ";

		for (int i = 0; i < eigenvector.length; ++i)
			strDump += FormatUtil.FormatDouble (eigenvector[i], 1, 4, 1.) + " | ";

		System.out.println ("\t" + strDump);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iterationCount = 5;
		double eigenvalue = 200.;
		double[][] a = {
			{1., 2., 3.},
			{1., 2., 1.},
			{3., 2., 1.},
		};
		double[] eigenvector = {
			1. / Math.sqrt (3.),
			1. / Math.sqrt (3.),
			1. / Math.sqrt (3.)
		};

		NumberUtil.PrintMatrix (
			"\t|| A ",
			a
		);

		EigenDump (
			0,
			eigenvector,
			eigenvalue
		);

		int iterationIndex = 0;

		while (++iterationIndex < iterationCount)
		{
			double[][] deDiagonalized = new double[a.length][a.length];

			for (int row = 0; row < a.length; ++row)
			{
				for (int column = 0; column < a.length; ++column)
				{
					deDiagonalized[row][column] = a[row][column];

					if (row == column)
					{
						deDiagonalized[row][column] -= eigenvalue;
					}
				}
			}

			eigenvector = Matrix.Normalize (
				Matrix.Product (
					Matrix.InvertUsingGaussianElimination (deDiagonalized),
					eigenvector
				)
			);

			eigenvalue = Matrix.DotProduct (
				eigenvector,
				Matrix.Product (
					a,
					eigenvector
				)
			);

			EigenDump (
				iterationIndex,
				eigenvector,
				eigenvalue
			);
		}

		EnvManager.TerminateEnv();
	}
}
