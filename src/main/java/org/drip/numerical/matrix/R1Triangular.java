
package org.drip.numerical.matrix;

import java.util.HashMap;
import java.util.Map;

import org.drip.function.definition.R1ToR1;
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
 * <i>R1Triangular</i> implements the type and Functionality associated with an R<sup>1</sup> Triangular
 * 	Matrix. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/matrix/README.md">Implementation of R<sup>1</sup> C<sup>1</sup> Matrices</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Triangular extends R1Square
{

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

	/**
	 * Retrieve the Triangular Type of the Matrix
	 * 
	 * @param r2Array R<sup>2</sup> Array
	 * 
	 * @return The Triangular Type
	 */

	public static final int Type (
		final double[][] r2Array)
	{
		if (null == r2Array) {
			return NON_TRIANGULAR;
		}

		boolean lowerTriangular = true;
		boolean upperTriangular = true;

		if (1 >= r2Array.length || null == r2Array[0] || r2Array.length != r2Array[0].length) {
			return NON_TRIANGULAR;
		}

		for (int i = 0; i < r2Array.length; ++i) {
			for (int j = 0; j < r2Array.length; ++j) {
				if (i > j) {
					if (!NumberUtil.WithinTolerance (r2Array[i][j], 0.)) {
						upperTriangular = false;

						if (!lowerTriangular) {
							break;
						}
					}
				} else if (i < j) {
					if (!NumberUtil.WithinTolerance (r2Array[i][j], 0.)) {
						lowerTriangular = false;

						if (!upperTriangular) {
							break;
						}
					}
				}
			}
		}

		if (upperTriangular && lowerTriangular) {
			return LOWER_AND_UPPER_TRIANGULAR;
		}

		if (upperTriangular && !lowerTriangular) {
			return UPPER_TRIANGULAR;
		}

		if (!upperTriangular && lowerTriangular) {
			return LOWER_TRIANGULAR;
		}

		return NON_TRIANGULAR;
	}

	private boolean zeroDiagonalEntries()
	{
		double[][] r2Array = r1Grid();

		for (int i = 0; i < r2Array.length; ++i) {
			if (0. != r2Array[i][i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * <i>R1Triangular</i> Constructor
	 * 
	 * @param r2Array R<sup>2</sup> Array
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static R1Triangular Standard (
		final double[][] r2Array)
	{
		int type = Type (r2Array);

		return NON_TRIANGULAR == type ? null : new R1Triangular (r2Array, type);
	}

	protected R1Triangular (
		final double[][] r2Array,
		final int type)
	{
		super (r2Array);

		_type = type;
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
		return UPPER_TRIANGULAR == _type || LOWER_AND_UPPER_TRIANGULAR == _type;
	}

	/**
	 * Indicate if the Matrix is Lower Triangular
	 * 
	 * @return TRUE - Matrix is Lower Triangular
	 */

	public boolean isLower()
	{
		return LOWER_TRIANGULAR == _type || LOWER_AND_UPPER_TRIANGULAR == _type;
	}

	/**
	 * Indicate if the Matrix is Diagonal
	 * 
	 * @return TRUE - Matrix is Diagonal
	 */

	public boolean isDiagonal()
	{
		return LOWER_AND_UPPER_TRIANGULAR == _type;
	}

	/**
	 * Calculate whether the Matrix is "Triangularizable"
	 * 
	 * @return TRUE - Matrix is "Triangularizable"
	 */

	@Override public boolean isTriangularizable()
	{
		return true;
	}

	/**
	 * Indicate if the Matrix is Unitriangular
	 * 
	 * @return TRUE - Matrix is Unitriangular
	 */

	public boolean isUnitriangular()
	{
		double[][] r2Array = r1Grid();

		for (int i = 0; i < r2Array.length; ++i) {
			if (1. != r2Array[i][i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Indicate if the Matrix is "Unit" Triangular
	 * 
	 * @return TRUE - Matrix is "Unit" Triangular
	 */

	public boolean isUnit()
	{
		return isUnitriangular();
	}

	/**
	 * Indicate if the Matrix is "Normed" Triangular
	 * 
	 * @return TRUE - Matrix is "Normed" Triangular
	 */

	public boolean isNormed()
	{
		return isUnitriangular();
	}

	/**
	 * Indicate if the Matrix is Upper Unitriangular
	 * 
	 * @return TRUE - Matrix is Upper Unitriangular
	 */

	public boolean isUpperUnitriangular()
	{
		return UPPER_TRIANGULAR == _type && isUnitriangular();
	}

	/**
	 * Indicate if the Matrix is Lower Unitriangular
	 * 
	 * @return TRUE - Matrix is Lower Unitriangular
	 */

	public boolean isLowerUnitriangular()
	{
		return LOWER_TRIANGULAR == _type && isUnitriangular();
	}

	/**
	 * Indicate if the Matrix is Strictly Upper Triangular
	 * 
	 * @return TRUE - Matrix is Strictly Upper Triangular
	 */

	public boolean isStrictlyUpper()
	{
		return zeroDiagonalEntries() && UPPER_TRIANGULAR == _type;
	}

	/**
	 * Indicate if the Matrix is Strictly Lower Triangular
	 * 
	 * @return TRUE - Matrix is Strictly Lower Triangular
	 */

	public boolean isStrictlyLower()
	{
		return zeroDiagonalEntries() && LOWER_TRIANGULAR == _type;
	}

	/**
	 * Indicate if the Matrix is Lower "Atomic" Unitriangular
	 * 
	 * @return TRUE - Matrix is Lower "Atomic" Unitriangular
	 */

	public boolean isAtomicLower()
	{
		if (!isLowerUnitriangular()) {
			return false;
		}

		int unitValueColumnIndex = -1;

		double[][] r2Array = r1Grid();

		for (int columnIndex = 0; columnIndex < r2Array.length - 1; ++columnIndex) {
			if (0. != r2Array[r2Array.length - 1][columnIndex] &&
				1. != r2Array[r2Array.length - 1][columnIndex])
			{
				return false;
			}

			if (1. == r2Array[r2Array.length - 1][columnIndex]) {
				if (-1 != unitValueColumnIndex) {
					return false;
				}

				unitValueColumnIndex = columnIndex;
			}
		}

		if (-1 == unitValueColumnIndex) {
			return false;
		}

		for (int rowIndex = 0; rowIndex < r2Array.length - 1; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < rowIndex; ++columnIndex) {
				if (unitValueColumnIndex == columnIndex) {
					if (1. != r2Array[rowIndex][unitValueColumnIndex]) {
						return false;
					}
				} else if (0. != r2Array[rowIndex][columnIndex]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Indicate if the Matrix is Upper "Atomic" Unitriangular
	 * 
	 * @return TRUE - Matrix is Upper "Atomic" Unitriangular
	 */

	public boolean isAtomicUpper()
	{
		if (!isUpperUnitriangular()) {
			return false;
		}

		int unitValueColumnIndex = -1;

		double[][] r2Array = r1Grid();

		for (int columnIndex = 0; columnIndex < r2Array.length - 1; ++columnIndex) {
			if (0 != r2Array[0][columnIndex] && 1. != r2Array[0][columnIndex]) {
				return false;
			}

			if (1. == r2Array[0][columnIndex]) {
				if (-1 != unitValueColumnIndex) {
					return false;
				}

				unitValueColumnIndex = columnIndex;
			}
		}

		if (-1 == unitValueColumnIndex) {
			return false;
		}

		for (int rowIndex = 1; rowIndex < r2Array.length - 1; ++rowIndex) {
			for (int columnIndex = rowIndex + 1; columnIndex < r2Array.length; ++columnIndex) {
				if (unitValueColumnIndex == columnIndex) {
					if (1. != r2Array[rowIndex][unitValueColumnIndex]) {
						return false;
					}
				} else if (0. != r2Array[rowIndex][columnIndex]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Indicate if the Matrix is "Atomic" Unitriangular
	 * 
	 * @return TRUE - Matrix is "Atomic" Unitriangular
	 */

	public boolean isAtomic()
	{
		return isAtomicUpper() || isAtomicLower();
	}

	/**
	 * Indicate if the Matrix is Frobenius Unitriangular
	 * 
	 * @return TRUE - Matrix is Frobenius Unitriangular
	 */

	public boolean isFrobenius()
	{
		return isAtomic();
	}

	/**
	 * Indicate if the Matrix is Gauss Unitriangular
	 * 
	 * @return TRUE - Matrix is Gauss Unitriangular
	 */

	public boolean isGauss()
	{
		return isAtomic();
	}

	/**
	 * Indicate if the Matrix is Gauss Transformation Unitriangular
	 * 
	 * @return TRUE - Matrix is Gauss Transformation Unitriangular
	 */

	public boolean isGaussTransformation()
	{
		return isAtomic();
	}

	/**
	 * Compute the Determinant of the Triangular Matrix
	 * 
	 * @return Determinant of the Triangular Matrix
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	@Override public double determinant()
		throws Exception
	{
		double determinant = 1.;

		double[][] r2Array = r1Grid();

		for (int i = 0; i < r2Array.length; ++i) {
			determinant *= r2Array[i][i];
		}

		return determinant;
	}

	/**
	 * Compute the Permanent of the Triangular Matrix
	 * 
	 * @return Permanent of the Triangular Matrix
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double permanent()
		throws Exception
	{
		return determinant();
	}

	/**
	 * Retrieve the Eigenvalue Multiplicity Map
	 * 
	 * @return Eigenvalue Multiplicity Map
	 */

	public Map<Double, Integer> eigenValueMultiplicityMap()
	{
		Map<Double, Integer> eigenValueMultiplicityMap = new HashMap<Double, Integer>();

		for (double eigenValue : diagonalEntryArray()) {
			eigenValueMultiplicityMap.put (
				eigenValue,
				eigenValueMultiplicityMap.containsKey (eigenValue) ?
					eigenValueMultiplicityMap.get (eigenValue) + 1 : 1
			);
		}

		return eigenValueMultiplicityMap;
	}

	/**
	 * Compute the L<sub>Infinity</sub> Condition Number of the Matrix
	 * 
	 * @return L<sub>Infinity</sub> Condition Number of the Matrix
	 * 
	 * @throws Exception Thrown if the Condition Number cannot be calculated
	 */

	public double conditionNumberLInfinity()
		throws Exception
	{
		double[][] r2Array = r1Grid();

		double firstAbsoluteEigenvalue = Math.abs (r2Array[0][0]);

		double minimumEigenvalue = firstAbsoluteEigenvalue;
		double maximumEigenvalue = firstAbsoluteEigenvalue;

		for (int i = 1; i < r2Array.length; ++i) {
			double absoluteEigenvalue = Math.abs (r2Array[i][i]);

			if (minimumEigenvalue > absoluteEigenvalue) {
				minimumEigenvalue = absoluteEigenvalue;
			}

			if (maximumEigenvalue < absoluteEigenvalue) {
				maximumEigenvalue = absoluteEigenvalue;
			}
		}

		return maximumEigenvalue / minimumEigenvalue;
	}

	/**
	 * Retrieve the Characteristic Polynomial of the Eigenvalues
	 * 
	 * @return Characteristic Polynomial of the Eigenvalues
	 */

	@Override public R1ToR1 characteristicPolynomial()
	{
		final double[] diagonalEntryArray = diagonalEntryArray();

		return new R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				double value = 1.;

				for (double eigenValue : diagonalEntryArray) {
					value *= (x - eigenValue);
				}

				return value;
			}
		};
	}
}
