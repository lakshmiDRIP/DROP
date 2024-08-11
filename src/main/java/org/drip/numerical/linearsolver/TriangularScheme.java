
package org.drip.numerical.linearsolver;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.matrix.R1Triangular;

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
 * <i>TriangularScheme</i> exposes the O(n<sup>2</sup>) solver functionality for solving Triangular Matrices.
 * 	The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearalgebra/README.md">Linear Algebra Matrix Transform Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TriangularScheme
{
	private double[] _rhsArray = null;
	private R1Triangular _triangularMatrix = null;

	private double[] forwardSubstitution()
	{
		double[][] r2Array = _triangularMatrix.r1Grid();

		double[] solutionArray = new double[_rhsArray.length];

		for (int rowIndex = 0; rowIndex < _rhsArray.length; ++rowIndex) {
			double cumulativeRowSolutionProduct = 0.;

			for (int columnIndex = 0; columnIndex < rowIndex; ++columnIndex) {
				cumulativeRowSolutionProduct += r2Array[rowIndex][columnIndex] * solutionArray[columnIndex];
			}

			solutionArray[rowIndex] = (_rhsArray[rowIndex] - cumulativeRowSolutionProduct) /
				r2Array[rowIndex][rowIndex];
		}

		return solutionArray;
	}

	private double[] backSubstitution()
	{
		double[][] r2Array = _triangularMatrix.r1Grid();

		double[] solutionArray = new double[_rhsArray.length];

		for (int rowIndex = _rhsArray.length - 1; rowIndex >= 0; --rowIndex) {
			double cumulativeRowSolutionProduct = 0.;

			for (int columnIndex = rowIndex + 1; columnIndex < _rhsArray.length; ++columnIndex) {
				cumulativeRowSolutionProduct += r2Array[rowIndex][columnIndex] * solutionArray[columnIndex];
			}

			solutionArray[rowIndex] = (_rhsArray[rowIndex] - cumulativeRowSolutionProduct) /
				r2Array[rowIndex][rowIndex];
		}

		return solutionArray;
	}

	/**
	 * Construct an Instance of <i>TriangularScheme</i>
	 * 
	 * @param triangularMatrix Triangular Matrix
	 * @param rhsArray RHS Array
	 * 
	 * @throws Thrown if the Inputs are Invalid
	 */

	public TriangularScheme (
		final R1Triangular triangularMatrix,
		final double[] rhsArray)
		throws Exception
	{
		if (null == (_triangularMatrix = triangularMatrix) ||
			null == (_rhsArray = rhsArray) ||
			_triangularMatrix.size() != _rhsArray.length)
		{
			throw new Exception ("TriangularScheme Constructor => Matrix not Tridiagonal");
		}
	}

	/**
	 * Retrieve the Triangular Matrix
	 * 
	 * @return Triangular Matrix
	 */

	public R1Triangular matrix()
	{
		return _triangularMatrix;
	}

	/**
	 * Retrieve the RHS Array
	 * 
	 * @return Square Matrix
	 */

	public double[] rhsArray()
	{
		return _rhsArray;
	}

	/**
	 * Solve the Triangular System given the RHS
	 * 
	 * @return The Solution
	 */

	public double[] solve()
	{
		if (_triangularMatrix.isUpper()) {
			return backSubstitution();
		}

		if (_triangularMatrix.isLower()) {
			return forwardSubstitution();
		}

		return null;
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		double[][] lowerTriangularMatrix = {
			{1., 0., 0.,  0.,},
			{2., 3., 0.,  0.,},
			{4., 5., 6.,  0.,},
			{7., 8., 9., 10.,},
		};

		double[] lowerRHSArray = {1., 8., 32., 90.};

		TriangularScheme lowerTriangularScheme = new TriangularScheme (
			R1Triangular.Standard (lowerTriangularMatrix),
			lowerRHSArray
		);

		System.out.println (NumberUtil.ArrayRow (lowerTriangularScheme.solve(), 2, 4, false));

		double[][] upperTriangularMatrix = {
			{10., 9., 8., 7.,},
			{ 0., 6., 5., 4.,},
			{ 0., 0., 3., 2.,},
			{ 0., 0., 0., 1.,},
		};

		double[] upperRHSArray = {90., 32., 8., 1.};

		TriangularScheme upperTriangularScheme = new TriangularScheme (
			R1Triangular.Standard (upperTriangularMatrix),
			upperRHSArray
		);

		System.out.println (NumberUtil.ArrayRow (upperTriangularScheme.solve(), 2, 4, false));
	}
}
