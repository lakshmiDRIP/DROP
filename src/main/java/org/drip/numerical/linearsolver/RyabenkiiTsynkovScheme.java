
package org.drip.numerical.linearsolver;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.numerical.matrix.R1PeriodicTridiagonal;

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
 * <i>RyabenkiiTsynkovScheme</i> implements the O(n) solver for a Tridiagonal Matrix with Periodic Boundary
 * 	Conditions. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearalgebra/README.md">Linear Algebra Matrix Transform Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RyabenkiiTsynkovScheme extends PeriodicTridiagonalScheme
{

	/**
	 * Construct a Standard Instance of <i>RyabenkiiTsynkovScheme</i>
	 * 
	 * @param r2Array R<sup>2</sup> Array
	 * @param rhsArray RHS Array
	 * 
	 * @return Standard Instance of <i>RyabenkiiTsynkovScheme</i>
	 */

	public static RyabenkiiTsynkovScheme Standard (
		final double[][] r2Array,
		final double[] rhsArray)
	{
		try {
			return R1MatrixUtil.IsPeriodicTridiagonal (r2Array) ? new RyabenkiiTsynkovScheme (
				R1PeriodicTridiagonal.Standard (r2Array),
				rhsArray
			) : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected RyabenkiiTsynkovScheme (
		final R1PeriodicTridiagonal periodicTridiagonalMatrix,
		final double[] rhsArray)
		throws Exception
	{
		super (periodicTridiagonalMatrix, rhsArray);
	}

	/**
	 * Construct the Common U/V Tridiagonal Matrix
	 * 
	 * @return Common U/V Tridiagonal Matrix
	 */

	public double[][] tridiagonalMatrix()
	{
		double[][] r2Array = matrix().r1Grid();

		int size = r2Array.length - 1;
		double[][] uTridiagonalMatrix = new double[size][size];

		for (int i = 1; i <= size; ++i) {
			for (int j = 1; j <= size; ++j) {
				uTridiagonalMatrix[i - 1][j - 1] = r2Array[i][j];
			}
		}

		return uTridiagonalMatrix;
	}

	/**
	 * Construct the <code>U</code> RHS Array
	 * 
	 * @return <code>U</code> RHS Array
	 */

	public double[] uRHSArray()
	{
		double[] rhsArray = rhsArray();

		int size = rhsArray.length - 1;
		double[] uRHSArray = new double[size];

		for (int i = 1; i <= size; ++i) {
			uRHSArray[i - 1] = rhsArray[i];
		}

		return uRHSArray;
	}

	/**
	 * Construct the <code>V</code> RHS Array
	 * 
	 * @return <code>V</code> RHS Array
	 */

	public double[] vRHSArray()
	{
		double[][] r2Array = matrix().r1Grid();

		int size = r2Array.length - 1;
		double[] vRHSArray = new double[size];
		vRHSArray[0] = -1. * r2Array[1][0];
		vRHSArray[size - 1] = -1. * r2Array[size][0];

		for (int i = 2; i <= size - 1; ++i) {
			vRHSArray[i - 1] = 0.;
		}

		return vRHSArray;
	}

	/**
	 * Compute the U Solution Array
	 * 
	 * @return U Solution Array
	 */

	public double[] uSolutionArray()
	{
		NonPeriodicTridiagonalScheme nonPeriodicTridiagonalScheme = NonPeriodicTridiagonalScheme.Standard (
			tridiagonalMatrix(),
			uRHSArray()
		);

		return null == nonPeriodicTridiagonalScheme ? null :
			nonPeriodicTridiagonalScheme.forwardSweepBackSubstitution();
	}

	/**
	 * Compute the V Solution Array
	 * 
	 * @return V Solution Array
	 */

	public double[] vSolutionArray()
	{
		NonPeriodicTridiagonalScheme nonPeriodicTridiagonalScheme = NonPeriodicTridiagonalScheme.Standard (
			tridiagonalMatrix(),
			vRHSArray()
		);

		return null == nonPeriodicTridiagonalScheme ? null :
			nonPeriodicTridiagonalScheme.forwardSweepBackSubstitution();
	}

	/**
	 * Compute the Solution Array based on U/V Scheme
	 * 
	 * @return Solution Array
	 */

	public double[] uvSolver()
	{
		double[] rhsArray = rhsArray();

		int size = rhsArray.length - 1;
		double[] solutionArray = new double[size + 1];

		double[][] tridiagonalMatrix = tridiagonalMatrix();

		if (null == tridiagonalMatrix) {
			return null;
		}

		NonPeriodicTridiagonalScheme uRHSTridiagonalScheme = NonPeriodicTridiagonalScheme.Standard (
			tridiagonalMatrix,
			uRHSArray()
		);

		if (null == uRHSTridiagonalScheme) {
			return null;
		}

		double[] uSolutionArray = uRHSTridiagonalScheme.forwardSweepBackSubstitution();

		if (null == uSolutionArray) {
			return null;
		}

		NonPeriodicTridiagonalScheme vRHSTridiagonalScheme = NonPeriodicTridiagonalScheme.Standard (
			tridiagonalMatrix,
			vRHSArray()
		);

		if (null == vRHSTridiagonalScheme) {
			return null;
		}

		double[] vSolutionArray = vRHSTridiagonalScheme.forwardSweepBackSubstitution();

		if (null == vSolutionArray) {
			return null;
		}

		double[][] r2Array = matrix().r1Grid();

		if (!NumberUtil.IsValid (
			solutionArray[0] = (
				rhsArray[0] - r2Array[0][size] * uSolutionArray[size - 1] - r2Array[0][1] * uSolutionArray[0]
			) / (
				r2Array[0][0] + r2Array[0][size] * vSolutionArray[size - 1] +
					r2Array[0][1] * vSolutionArray[0]
			)
		))
		{
			return null;
		}

		for (int i = size; i >= 1; --i) {
			solutionArray[i] = uSolutionArray[i - 1] + solutionArray[0] * vSolutionArray[i - 1];
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
		return uvSolver();
	}
}
