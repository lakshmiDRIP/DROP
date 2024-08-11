
package org.drip.sample.triangular;

import java.util.Date;

import org.drip.measure.crng.RandomMatrixGenerator;
import org.drip.measure.crng.RdRandomSequence;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.numerical.linearsolver.TriangularScheme;
import org.drip.numerical.matrix.R1Triangular;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>LowerSolverSuite</i> shows the Construction and the Solution of a Lower Triangular Matrix. The
 * 	References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Axler, S. J. (1997): <i>Linear Algebra Done Right 2<sup>nd</sup> Edition</i> <b>Springer</b>
 * 				New York NY
 * 		</li>
 * 		<li>
 * 			Bernstein, D. S. (2009): <i>Matrix Mathematics: Theory, Facts, and Formulas 2<sup>nd</sup>
 * 				Edition</i> <b>Princeton University Press</b> Princeton NJ
 * 		</li>
 * 		<li>
 * 			Herstein, I. N. (1975): <i>Topics in Algebra 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York NY
 * 		</li>
 * 		<li>
 * 			Prasolov, V. V. (1994): <i>Topics in Algebra</i> <b>American Mathematical Society</b> Providence
 * 				RI
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Triangular Matrix https://en.wikipedia.org/wiki/Triangular_matrix
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/triangular/README.md">Triangular Matrix Variants and Solutions</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LowerSolverSuite
{

	private static final void Trial (
		final int elementCount,
		final double maximumElement)
		throws Exception
	{
		double[] xArray = RdRandomSequence.OneD (elementCount, maximumElement, true);

		R1Triangular lowerTriangularMatrix = RandomMatrixGenerator.LowerTriangular (
			elementCount,
			maximumElement,
			true
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		System.out.println ("\t| Trial at " + new Date());

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		double[][] lowerTriangularR2Array = lowerTriangularMatrix.r1Grid();

		for (int i = 0; i < lowerTriangularR2Array.length; ++i) {
			System.out.println (
				"\t| Lower Trangular " + elementCount + " x " + elementCount + "              => [" +
					NumberUtil.ArrayRow (
						lowerTriangularR2Array[i],
						2,
						1,
						false
					) + " ]||"
			);
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		double[] rhsArray = R1MatrixUtil.Product (lowerTriangularR2Array, xArray);

		System.out.println (
			"\t| RHS Input      {" + NumberUtil.ArrayRow (rhsArray, 5, 0, false) + "} ||"
		);

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println (
			"\t| X Input           =>  " + NumberUtil.ArrayRow (xArray, 2, 1, false) + "  ||"
		);

		System.out.println (
			"\t| Expected          =>  " +
			NumberUtil.ArrayRow (
				new TriangularScheme (
					R1Triangular.Standard (lowerTriangularR2Array),
					rhsArray
				).solve(),
				2,
				1,
				false
			) + "  ||"
		);

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println();
	}

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
		EnvManager.InitEnv (
			""
		);

		int elementCount = 6;
		double maximumElement = 99.;

		Trial (elementCount, maximumElement);

		Trial (elementCount, maximumElement);

		Trial (elementCount, maximumElement);

		Trial (elementCount, maximumElement);

		Trial (elementCount, maximumElement);

		EnvManager.TerminateEnv();
	}
}
