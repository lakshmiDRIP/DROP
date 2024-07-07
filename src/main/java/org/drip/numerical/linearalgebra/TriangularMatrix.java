
package org.drip.numerical.linearalgebra;

import org.drip.numerical.common.NumberUtil;

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
 * <i>TriangularMatrix</i> implements the type and Functionality associated with a Triangular Matrix. The
 *  References are:
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

public class TriangularMatrix
{

	/**
	 * Retrieve the Triangular Type of the Matrix
	 * 
	 * @param squareMatrix Input Square Matrix
	 * 
	 * @return The Triangular Type
	 */

	public static final int Type (
		final double[][] squareMatrix)
	{
		if (null == squareMatrix) {
			return NON_TRIANGULAR;
		}

		boolean lowerTriangular = true;
		boolean upperTriangular = true;
		int size = squareMatrix.length;

		if (1 >= size || null == squareMatrix[0] || size != squareMatrix[0].length) {
			return NON_TRIANGULAR;
		}

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				if (i > j) {
					if (NumberUtil.WithinTolerance (squareMatrix[i][j], 0.)) {
						lowerTriangular = false;

						if (!upperTriangular) {
							break;
						}
					}
				} else if (i < j) {
					if (NumberUtil.WithinTolerance (squareMatrix[i][j], 0.)) {
						upperTriangular = false;

						if (!lowerTriangular) {
							break;
						}
					}
				}
			}
		}

		if (lowerTriangular && upperTriangular) {
			return LOWER_AND_UPPER_TRIANGULAR;
		}

		if (lowerTriangular && !upperTriangular) {
			return LOWER_TRIANGULAR;
		}

		if (!lowerTriangular && upperTriangular) {
			return UPPER_TRIANGULAR;
		}

		return NON_TRIANGULAR;
	}

	/**
	 * Lower Triangular Matrix
	 */

	public static int LOWER_TRIANGULAR = 1;

	/**
	 * Upper Triangular Matrix
	 */

	public static int UPPER_TRIANGULAR = 2;

	/**
	 * Lower + Upper Triangular Matrix
	 */

	public static int LOWER_AND_UPPER_TRIANGULAR = 3;

	/**
	 * Non Triangular Matrix
	 */

	public static int NON_TRIANGULAR = 0;

	private int _type = Integer.MIN_VALUE;
	private double[][] _squareMatrix = null;

	private boolean zeroDiagonalEntries()
	{
		for (int i = 0; i < _squareMatrix.length; ++i) {
			if (0. != _squareMatrix[i][i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * <i>TriangularMatrix</i> Constructor
	 * 
	 * @param squareMatrix Input Square Matrix
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public TriangularMatrix (
		final double[][] squareMatrix)
		throws Exception
	{
		if (NON_TRIANGULAR == (_type = Type (_squareMatrix = squareMatrix))) {
			throw new Exception ("TriangularMatrix Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Square Matrix
	 * 
	 * @return Square Matrix
	 */

	public double[][] squareMatrix()
	{
		return _squareMatrix;
	}

	/**
	 * Retrieve the Matrix Type
	 * 
	 * @return Matrix Type
	 */

	public int type()
	{
		return _type;
	}

	/**
	 * Indicate if the Matrix is Upper Triangular
	 * 
	 * @return TRUE - Matrix is Upper Triangular
	 */

	public boolean isUpper()
	{
		return UPPER_TRIANGULAR == _type;
	}

	/**
	 * Indicate if the Matrix is Lower Triangular
	 * 
	 * @return TRUE - Matrix is Lower Triangular
	 */

	public boolean isLower()
	{
		return LOWER_TRIANGULAR == _type;
	}

	/**
	 * Indicate if the Matrix is Diagonal
	 * 
	 * @return TRUE - Matrix is Diagonal
	 */

	public boolean isDiagonal()
	{
		return LOWER_TRIANGULAR == _type && UPPER_TRIANGULAR == _type;
	}

	/**
	 * Indicate if the Matrix is not a Valid Triangular Matrix
	 * 
	 * @return TRUE - Matrix is not a Valid Triangular Matrix
	 */

	public boolean isInvalid()
	{
		return LOWER_TRIANGULAR != _type && UPPER_TRIANGULAR != _type;
	}

	/**
	 * Indicate if the Matrix is Unitriangular
	 * 
	 * @return TRUE - Matrix is Unitriangular
	 */

	public boolean isUnitriangular()
	{
		for (int i = 0; i < _squareMatrix.length; ++i) {
			if (1. != _squareMatrix[i][i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Indicate if the Matrix is Strictly Upper Triangular
	 * 
	 * @return TRUE - Matrix is Strictly Upper Triangular
	 */

	public boolean strictlyUpper()
	{
		return zeroDiagonalEntries() && UPPER_TRIANGULAR == _type;
	}

	/**
	 * Indicate if the Matrix is Strictly Lower Triangular
	 * 
	 * @return TRUE - Matrix is Strictly Lower Triangular
	 */

	public boolean strictlyLower()
	{
		return zeroDiagonalEntries() && LOWER_TRIANGULAR == _type;
	}

	/**
	 * Compute the Determinant of the Triangular Matrix
	 * 
	 * @return Determinant of the Triangular Matrix
	 */

	public double determinant()
	{
		double determinant = 1.;

		for (int i = 0; i < _squareMatrix.length; ++i) {
			determinant *= _squareMatrix[i][i];
		}

		return determinant;
	}

	/**
	 * Compute the Permanent of the Triangular Matrix
	 * 
	 * @return Permanent of the Triangular Matrix
	 */

	public double permanent()
	{
		return determinant();
	}
}
