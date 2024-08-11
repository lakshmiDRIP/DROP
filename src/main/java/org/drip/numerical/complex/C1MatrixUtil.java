
package org.drip.numerical.complex;

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
 * <i>C1MatrixUtil</i> implements a C<sup>1</sup> Complex Number Matrix Manipulation Utilities. The
 * 	References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Fuhr, H., and Z. Rzeszotnik (2018): A Note on Factoring Unitary Matrices <i>Linear Algebra and
 * 				its Applications</i> <b>547</b> 32-44
 * 		</li>
 * 		<li>
 * 			Horn, R. A., and C. R. Johnson (2013): <i>Matrix Analysis</i> <b>Cambridge University Press</b>
 * 				Cambridge UK
 * 		</li>
 * 		<li>
 * 			Li, C. K., and E. Poon (2002): Additive Decomposition of Real Matrices <i>Linear and Multilinear
 * 				Algebra</i> <b>50 (4)</b> 321-326
 * 		</li>
 * 		<li>
 * 			Marvian, I. (2022): Restrictions on realizable Unitary Operations imposed by Symmetry and
 * 				Locality <i>Nature Science</i> <b>18 (3)</b> 283-289
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Unitary Matrix https://en.wikipedia.org/wiki/Unitary_matrix
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/complex/README.md">Implementation of Complex Number Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class C1MatrixUtil
{

	/**
	 * Transpose the specified C<sup>1</sup> Square Matrix. Unsafe Methods do not validate the Input
	 * 	Arguments, so <b>use caution</b> in applying these Methods
	 * 
	 * @param c1Grid The Input C<sup>1</sup> Matrix Grid
	 * 
	 * @return The Transpose of the Input C<sup>1</sup> Matrix Grid
	 */

	public static final C1Cartesian[][] UnsafeTranspose (
		final C1Cartesian[][] c1Grid)
	{
		C1Cartesian[][] c1GridTranspose = new C1Cartesian[c1Grid[0].length][c1Grid.length];

		for (int i = 0; i < c1Grid[0].length; ++i) {
			for (int j = 0; j < c1Grid.length; ++j)
				c1GridTranspose[i][j] = c1Grid[j][i];
		}

		return c1GridTranspose;
	}

	/**
	 * Compute the Product of the Input Matrices. Unsafe Methods do not validate the Input Arguments, so
	 * 	<b>use caution</b> in applying these Methods
	 *
	 * @param c1GridA Grid of C<sup>1</sup> A
	 * @param c1GridB Grid of C<sup>1</sup> B
	 * 
	 * @return The Product Matrix
	 */

	public static final C1Cartesian[][] UnsafeProduct (
		final C1Cartesian[][] c1GridA,
		final C1Cartesian[][] c1GridB)
	{
		C1Cartesian[][] product = new C1Cartesian[c1GridA.length][c1GridB[0].length];

		for (int rowIndex = 0; rowIndex < c1GridA.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < c1GridB[0].length; ++columnIndex) {
				product[rowIndex][columnIndex] = C1Cartesian.Zero();
			}
		}

		for (int rowIndex = 0; rowIndex < c1GridA.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < c1GridB[0].length; ++columnIndex) {
				for (int k = 0; k < c1GridA[0].length; ++k) {
					if (null == product[rowIndex][columnIndex].add (
						C1Util.Multiply (c1GridA[rowIndex][k], c1GridB[k][columnIndex])
					))
					{
						return null;
					}
				}
			}
		}

		return product;
	}

	/**
	 * Compute the Product of the Input Matrices. Unsafe Methods do not validate the Input Arguments, so
	 * 	<b>use caution</b> in applying these Methods
	 *
	 * @param c1GridA Grid of C<sup>1</sup>
	 * @param r1GridB Grid of R<sup>1</sup>
	 * 
	 * @return The Product Matrix
	 */

	public static final C1Cartesian[][] UnsafeProduct (
		final C1Cartesian[][] c1GridA,
		final double[][] r1GridB)
	{
		C1Cartesian[][] product = new C1Cartesian[c1GridA.length][r1GridB[0].length];

		for (int rowIndex = 0; rowIndex < c1GridA.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < r1GridB[0].length; ++columnIndex) {
				product[rowIndex][columnIndex] = C1Cartesian.Zero();
			}
		}

		for (int rowIndex = 0; rowIndex < c1GridA.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < r1GridB[0].length; ++columnIndex) {
				for (int k = 0; k < c1GridA[0].length; ++k) {
					if (null == product[rowIndex][columnIndex].add (
						c1GridA[rowIndex][k].scale (r1GridB[k][columnIndex])
					))
					{
						return null;
					}
				}
			}
		}

		return product;
	}

	/**
	 * Indicate the C<sup>1</sup> Vector is Valid
	 * 
	 * @param c1Vector C<sup>1</sup> Vector
	 * 
	 * @return TRUE - The C<sup>1</sup> Vector is Valid
	 */

	public static final boolean IsVectorValid (
		final C1Cartesian[] c1Vector)
	{
		if (null == c1Vector || 0 == c1Vector.length) {
			return false;
		}

		for (int i = 0; i < c1Vector.length; ++i) {
			if (null == c1Vector[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Indicate the C<sup>1</sup> Grid is Valid
	 * 
	 * @param c1Grid C<sup>1</sup> Grid
	 * 
	 * @return TRUE - The C<sup>1</sup> Grid is Valid
	 */

	public static final boolean IsGridValid (
		final C1Cartesian[][] c1Grid)
	{
		if (null == c1Grid || null == c1Grid[0]) {
			return false;
		}

		for (int i = 0; i < c1Grid.length; ++i) {
			if (null == c1Grid[i] || 0 == c1Grid[i].length) {
				return false;
			}

			for (int j = 0; j < c1Grid[i].length; ++j) {
				if (null == c1Grid[i][j]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Transpose the specified C<sup>1</sup> Square Matrix
	 * 
	 * @param c1Grid The Input C<sup>1</sup> Matrix Grid
	 * 
	 * @return The Transpose of the Input C<sup>1</sup> Matrix Grid
	 */

	public static final C1Cartesian[][] Transpose (
		final C1Cartesian[][] c1Grid)
	{
		return !IsGridValid (c1Grid) ? null : UnsafeTranspose (c1Grid);
	}

	/**
	 * Compute the Product of the Input Matrices
	 * 
	 * @param c1GridA Grid of C<sup>1</sup> A
	 * @param c1GridB Grid of C<sup>1</sup> B
	 * 
	 * @return The Product Matrix
	 */

	public static final C1Cartesian[][] Product (
		final C1Cartesian[][] c1GridA,
		final C1Cartesian[][] c1GridB)
	{
		return !IsGridValid (c1GridA) || !IsGridValid (c1GridB) ? null : UnsafeProduct (c1GridA, c1GridB);
	}

	/**
	 * Compute the Product of the Input Matrices
	 *
	 * @param c1GridA Grid of C<sup>1</sup>
	 * @param r1GridB Grid of R<sup>1</sup>
	 * 
	 * @return The Product Matrix
	 */

	public static final C1Cartesian[][] Product (
		final C1Cartesian[][] c1GridA,
		final double[][] r1GridB)
	{
		if (!IsGridValid (c1GridA) ||
			null == r1GridB || 0 == r1GridB.length ||
			null == r1GridB[0] || 0 == r1GridB[0].length)
		{
			return null;
		};

		C1Cartesian[][] product = new C1Cartesian[c1GridA.length][r1GridB[0].length];

		for (int rowIndex = 0; rowIndex < c1GridA.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < r1GridB[0].length; ++columnIndex) {
				product[rowIndex][columnIndex] = C1Cartesian.Zero();
			}
		}

		for (int rowIndex = 0; rowIndex < c1GridA.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < r1GridB[0].length; ++columnIndex) {
				for (int k = 0; k < c1GridA[0].length; ++k) {
					if (null == product[rowIndex][columnIndex].add (
						c1GridA[rowIndex][k].scale (r1GridB[k][columnIndex])
					))
					{
						return null;
					}
				}
			}
		}

		return product;
	}
}
