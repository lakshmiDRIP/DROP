
package org.drip.sample.tridiagonal;

import java.util.Date;

import org.drip.measure.crng.RdRandomSequence;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.linearalgebra.MatrixUtil;
import org.drip.numerical.linearsolver.RyabenkiiTsynkovScheme;
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
 * <i>PeriodicRyabenkiiTsynkovSolverSuite</i> tests the Application of the Ryabenkii-Tsynkov Solver for a
 * 	variety of Periodic Tridiagonal Matrices. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Batista, M., and A. R. A. Ibrahim-Karawia (2009): The use of Sherman-Morrison-Woodbury formula
 * 				to solve cyclic block tridiagonal and cyclic block penta-diagonal linear systems of equations
 * 				<i>Applied Mathematics of Computation</i> <b>210 (2)</b> 558-563
 * 		</li>
 * 		<li>
 * 			Datta, B. N. (2010): <i>Numerical Linear Algebra and Applications 2<sup>nd</sup> Edition</i>
 * 				<b>SIAM</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Gallopoulos, E., B. Phillippe, and A. H. Sameh (2016): <i>Parallelism in Matrix Computations</i>
 * 				<b>Spring</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Ryaben’kii, V. S., and S. V. Tsynkov (2006): <i>Theoretical Introduction to Numerical
 * 				Analysis</i> <b>Wolters Kluwer</b> Aalphen aan den Rijn, Netherlands
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Tridiagonal Matrix Algorithm
 * 				https://en.wikipedia.org/wiki/Tridiagonal_matrix_algorithm
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/tridiagonal/README.md">Regular/Periodic Tridiagonal Solver Schemes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PeriodicRyabenkiiTsynkovSolverSuite
{

	private static final void Trial (
		final int elementCount,
		final double maximumElement)
		throws Exception
	{
		double[] xArray = RdRandomSequence.OneD (elementCount, maximumElement, true);

		double[][] periodicTridiagonalMatrix = RdRandomSequence.PeriodicTridiagonal (
			elementCount,
			maximumElement,
			true
			);

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println ("\t| Trial at " + new Date());

		System.out.println ("\t|-----------------------------------------------------------------||");

		for (int i = 0; i < periodicTridiagonalMatrix.length; ++i) {
			System.out.println (
				"\t| Tridiagonal " + elementCount + " x " + elementCount + " => [" + NumberUtil.ArrayRow (
					periodicTridiagonalMatrix[i],
					2,
					1,
					false
				) + " ]||"
			);
		}

		double[] rhsArray = MatrixUtil.Product (periodicTridiagonalMatrix, xArray);

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println (
			"\t| RHS Input      {" + NumberUtil.ArrayRow (rhsArray, 5, 0, false) + "} ||"
		);

		System.out.println ("\t|-----------------------------------------------------------------||");

		System.out.println (
			"\t| X Input           =>  " + NumberUtil.ArrayRow (xArray, 2, 1, false) + "  ||"
		);

		System.out.println (
			"\t| Expected          =>  " +
			NumberUtil.ArrayRow (
				RyabenkiiTsynkovScheme.Standard (periodicTridiagonalMatrix, rhsArray).uvSolver(),
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
