
package org.drip.numerical.complex;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.matrix.R1Square;

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
 * <i>C1Square</i> implements the type and Functionality associated with a C<sup>1</sup>Square Matrix. The
 *  References are:
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

public class C1Square
{
	private C1Cartesian[][] _c1Grid = null;

	/**
	 * Construct a Standard Instance of <i>C1Square</i>
	 * 
	 * @param c1Grid Grid of <i>C1Cartesian</i> Elements
	 * 
	 * @return <i>C1Square</i> Instance
	 */

	public static C1Square Standard (
		final C1Cartesian[][] c1Grid)
	{
		if (null == c1Grid || 0 == c1Grid.length || 0 == c1Grid[0].length) {
			return null;
		}

		for (int i = 0; i < c1Grid.length; ++i) {
			for (int j = 0; j < c1Grid[i].length; ++j) {
				if (null == c1Grid[i][j]) {
					return null;
				}
			}
		}

		return new C1Square (c1Grid);
	}

	/**
	 * Construct a 2x2 Rotation C<sup>1</sup> Matrix
	 * 
	 * @param theta The Rotation Angle
	 * 
	 * @return 2x2 Rotation C<sup>1</sup> Matrix
	 */

	public static final C1Square Rotation2x2 (
		final double theta)
	{
		if (!NumberUtil.IsValid (theta)) {
			return null;
		}

		C1Cartesian c1Zero = C1Cartesian.Zero();

		C1Cartesian c1UnitImaginary = C1Cartesian.UnitImaginary();

		C1Cartesian[][] c1Grid = new C1Cartesian[2][2];
		c1Grid[1][0] = c1Zero;
		c1Grid[0][1] = c1Zero;

		c1Grid[0][0] = c1UnitImaginary.scale (theta).exponentiate();

		c1Grid[1][1] = c1UnitImaginary.scale (-1. * theta).exponentiate();

		return new C1Square (c1Grid);
	}

	/**
	 * Construct a 2x2 Rotation C<sup>1</sup> Matrix
	 * 
	 * @param theta1 The Left Rotation Angle
	 * @param theta2 The Right Rotation Angle
	 * 
	 * @return 2x2 Rotation C<sup>1</sup> Matrix
	 */

	public static final C1Square Rotation2x2 (
		final double theta1,
		final double theta2)
	{
		if (!NumberUtil.IsValid (theta1) || !NumberUtil.IsValid (theta2)) {
			return null;
		}

		C1Cartesian c1Zero = C1Cartesian.Zero();

		C1Cartesian c1UnitImaginary = C1Cartesian.UnitImaginary();

		C1Cartesian[][] c1Grid = new C1Cartesian[2][2];
		c1Grid[1][0] = c1Zero;
		c1Grid[0][1] = c1Zero;

		c1Grid[0][0] = c1UnitImaginary.scale (theta1).exponentiate();

		c1Grid[1][1] = c1UnitImaginary.scale (theta2).exponentiate();

		return new C1Square (c1Grid);
	}

	protected C1Square (
		final C1Cartesian[][] c1Grid)
	{
		_c1Grid = c1Grid;
	}

	/**
	 * Retrieve C<sup>1</sup> Array
	 * 
	 * @return C<sup>1</sup> Array
	 */

	public C1Cartesian[][] c1Grid()
	{
		return _c1Grid;
	}

	/**
	 * Retrieve the Size of the Square Matrix
	 * 
	 * @return Size of the Square Matrix
	 */

	public int size()
	{
		return _c1Grid.length;
	}

	/**
	 * Compute the Product with the other Square Matrix
	 * 
	 * @param squareMatrixOther "Other" Square Matrix
	 * 
	 * @return Resulting Product
	 */

	public C1Square product (
		final C1Square squareMatrixOther)
	{
		return null == squareMatrixOther ? null : new C1Square (
			C1MatrixUtil.UnsafeProduct (_c1Grid, squareMatrixOther.c1Grid())
		);
	}

	/**
	 * Transpose the Square Matrix
	 * 
	 * @return The Transpose of the Matrix Grid
	 */

	public C1Cartesian[][] transpose()
	{
		return C1MatrixUtil.UnsafeTranspose (_c1Grid);
	}

	/**
	 * Contract the Square <i>C1Cartesian</i> by one Row/Column
	 * 
	 * @return New <i>C1Square</i>
	 */

	public C1Square slimContract()
	{
		int slimSize = _c1Grid.length - 1;

		if (2 >= slimSize) {
			return null;
		}

		C1Cartesian[][] c1GridNew = new C1Cartesian[slimSize][slimSize];

		for (int i = 0; i < slimSize; ++i) {
			for (int j = 0; j < slimSize; ++j) {
				c1GridNew[i][j] = _c1Grid[i][j];
			}
		}

		return new C1Square (c1GridNew);
	}

	/**
	 * Determinant of with the "Other"
	 * 
	 * @return The Determinant
	 */

	public double determinant()
	{
		return C1MatrixUtil.UnsafeDeterminant (c1Grid());
	}

	/**
	 * Indicate if the Determinant is 1
	 * 
	 * @return TRUE - Determinant is 1
	 */

	public boolean isUnitDeterminant()
	{
		return NumberUtil.WithinTolerance (determinant(), 0.);
	}

	/**
	 * Indicate if the Determinant is 1
	 * 
	 * @return TRUE - Determinant is 1
	 */

	public boolean isUnitary()
	{
		return isUnitDeterminant();
	}

	/**
	 * Compute the Product with the other Square Matrix
	 * 
	 * @param r1Square R<sup>1</sup> Square Matrix
	 * 
	 * @return Resulting Product
	 */

	public C1Square product (
		final R1Square r1Square)
	{
		return null == r1Square ? null : new C1Square (
			C1MatrixUtil.UnsafeProduct (c1Grid(), r1Square.r1Grid())
		);
	}

	/**
	 * Compute the Product of the Input Matrix and the Complex Number
	 *
	 * @param c1 C<sup>1</sup>
	 * 
	 * @return The Product <i>C1Square</i>
	 */

	public C1Square product (
		final C1Cartesian c1)
	{
		return null == c1 ? null : new C1Square (C1MatrixUtil.UnsafeProduct (c1Grid(), c1));
	}
}
