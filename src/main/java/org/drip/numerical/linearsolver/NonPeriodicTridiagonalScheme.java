
package org.drip.numerical.linearsolver;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.linearalgebra.MatrixUtil;
import org.drip.numerical.linearalgebra.NonPeriodicTridiagonalMatrix;

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
 * <i>NonPeriodicTridiagonalScheme</i> implements the O(n) solver for a Non-Periodic Tridiagonal Matrix. The
 * 	References are:
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
 * 			Niyogi, P. (2006): <i>Introduction to Computational Fluid Dynamics</i> <b>Pearson</b> London, UK
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearalgebra/README.md">Linear Algebra Matrix Transform Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NonPeriodicTridiagonalScheme extends TridiagonalScheme
{

	/**
	 * Make a Standard Instance of <i>NonPeriodicTridiagonalScheme</i>
	 * 
	 * @param r2Array R<cup>2</sup> Array
	 * @param rhsArray RHS Array
	 * 
	 * @return Standard Instance of <i>NonPeriodicTridiagonalScheme</i>
	 */

	public static NonPeriodicTridiagonalScheme Standard (
		final double[][] r2Array,
		final double[] rhsArray)
	{
		try {
			return MatrixUtil.IsTridiagonal (r2Array) ? new NonPeriodicTridiagonalScheme (
				NonPeriodicTridiagonalMatrix.Standard (r2Array),
				rhsArray
			): null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected NonPeriodicTridiagonalScheme (
		final NonPeriodicTridiagonalMatrix nonPeriodicTridiagonalMatrix,
		final double[] rhsArray)
		throws Exception
	{
		super (nonPeriodicTridiagonalMatrix, rhsArray);
	}

	/**
	 * Solve the Strictly Tridiagonal System given the RHS
	 * 
	 * @return The Solution
	 */

	public double[] forwardSweepBackSubstitution()
	{
		double[] rhsArray = rhsArray();

		double[][] squareMatrix = matrix().r2Array();

		int matrixSize = squareMatrix.length;
		double[] solutionArray = new double[matrixSize];
		double[] modifiedRHSArray = new double[matrixSize];
		modifiedRHSArray[0] = rhsArray[0] / squareMatrix[0][0];
		double[] modifiedSupraDiagonalArray = new double[matrixSize - 1];
		modifiedSupraDiagonalArray[0] = squareMatrix[0][1] / squareMatrix[0][0];

		for (int i = 1; i < matrixSize - 1; ++i) {
			modifiedSupraDiagonalArray[i] = squareMatrix[i][i + 1] / (
				squareMatrix[i][i] - squareMatrix[i][i - 1] * modifiedSupraDiagonalArray[i - 1]
			);
		}

		for (int i = 1; i < matrixSize; ++i) {
			if (!NumberUtil.IsValid (
				modifiedRHSArray[i] = (rhsArray[i] - squareMatrix[i][i - 1] * modifiedRHSArray[i - 1]) / (
					squareMatrix[i][i] - squareMatrix[i][i - 1] * modifiedSupraDiagonalArray[i - 1]
				)
			))
			{
				return null;
			}
		}

		solutionArray[matrixSize - 1] = modifiedRHSArray[matrixSize - 1];

		for (int i = matrixSize - 2; i >= 0; --i) {
			solutionArray[i] = modifiedRHSArray[i] - modifiedSupraDiagonalArray[i] * solutionArray[i + 1];
		}

		return solutionArray;
	}

	/**
	 * Solve the Tridiagonal System given the RHS
	 * 
	 * @return The Solution
	 */

	public double[] solve()
	{
		return forwardSweepBackSubstitution();
	}
}
