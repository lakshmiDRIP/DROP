
package org.drip.numerical.linearalgebra;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.eigen.EigenOutput;
import org.drip.numerical.eigen.QREigenComponentExtractor;

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
 * <i>SquareMatrix</i> implements the type and Functionality associated with a Square Matrix. The References
 * 	are:
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

public class SquareMatrix
{
	private double[][] _r2Array = null;

	/**
	 * Construct a Standard Instance of <i>SquareMatrix</i>
	 * 
	 * @param r2Array R<sup>2</sup> Array
	 * 
	 * @return Standard Instance of <i>SquareMatrix</i>
	 */

	public static SquareMatrix Standard (
		final double[][] r2Array)
	{
		if (!MatrixUtil.IsSquare (r2Array)) {
			return null;
		}

		for (int i = 0; i < r2Array.length; ++i) {
			if (!NumberUtil.IsValid (r2Array[i])) {
				return null;
			}
		}

		return new SquareMatrix (r2Array);
	}

	protected SquareMatrix (
		final double[][] r2Array)
	{
		_r2Array = r2Array;
	}

	/**
	 * Retrieve R<sup>2</sup> Array
	 * 
	 * @return R<sup>2</sup> Array
	 */

	public double[][] r2Array()
	{
		return _r2Array;
	}

	/**
	 * Retrieve the Size of the Square Matrix
	 * 
	 * @return Size of the Square Matrix
	 */

	public int size()
	{
		return _r2Array.length;
	}

	/**
	 * Transpose the Square Matrix
	 * 
	 * @return The Transposed Square Matrix
	 */

	public SquareMatrix transpose()
	{
		double[][] r2ArrayTranspose = new double[_r2Array.length][_r2Array.length];

		for (int i = 0; i < _r2Array.length; ++i) {
			for (int j = 0; j < _r2Array.length; ++j)
				r2ArrayTranspose[i][j] = _r2Array[j][i];
		}

		return new SquareMatrix (r2ArrayTranspose);
	}

	/**
	 * Compute the Product with the other Square Matrix
	 * 
	 * @param squareMatrixOther "Other" Square Matrix
	 * 
	 * @return Resulting Product
	 */

	public SquareMatrix product (
		final SquareMatrix squareMatrixOther)
	{
		if (null == squareMatrixOther || _r2Array.length != squareMatrixOther.size()) {
			return null;
		}

		double[][] r2ArrayOther = squareMatrixOther.r2Array();

		double[][] r2ArrayProduct = new double[_r2Array.length][_r2Array.length];

		for (int rowIndex = 0; rowIndex < _r2Array.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < _r2Array.length; ++columnIndex) {
				r2ArrayProduct[rowIndex][columnIndex] = 0.;

				for (int i = 0; i < _r2Array.length; ++i) {
					r2ArrayProduct[rowIndex][columnIndex] +=
						_r2Array[rowIndex][i] * r2ArrayOther[i][columnIndex];
				}
			}
		}

		return new SquareMatrix (r2ArrayProduct);
	}

	/**
	 * Calculate whether the Matrix is "Normal"
	 * 
	 * @return TRUE - Matrix is "Normal"
	 */

	public boolean isNormal()
	{
		SquareMatrix squareMatrixTranspose = transpose();

		double[][] r2ArrayTransposeTimesR2Array = product (squareMatrixTranspose).r2Array();

		double[][] r2ArrayTimesR2ArrayTranspose = squareMatrixTranspose.product (this).r2Array();

		for (int i = 0; i < _r2Array.length; ++i) {
			for (int j = 0; j < _r2Array.length; ++j) {
				if (!NumberUtil.WithinTolerance (
					r2ArrayTransposeTimesR2Array[i][j],
					r2ArrayTimesR2ArrayTranspose[i][j]
				))
				{
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Calculate whether the Matrix is "Triangularizable"
	 * 
	 * @return TRUE - Matrix is "Triangularizable"
	 */

	public boolean isTriangularizable()
	{
		return true;
	}

	/**
	 * Eigenize and Extract the Components of the Specified Matrix
	 * 
	 * @return The EigenComponents
	 */

	public EigenOutput eigenize()
	{
		return QREigenComponentExtractor.Standard().eigenize (_r2Array);
	}

	/**
	 * Retrieve the Array of Diagonal Entries
	 * 
	 * @return Array of Diagonal Entries
	 */

	public double[] diagonalEntryArray()
	{
		double[] diagonalEntryArray = new double[_r2Array.length];

		for (int i = 0; i < _r2Array.length; ++i) {
			diagonalEntryArray[i] = _r2Array[i][i];
		}

		return diagonalEntryArray;
	}

	/**
	 * Retrieve the Eigenvalue Multiplicity Map
	 * 
	 * @return Eigenvalue Multiplicity Map
	 */

	public Map<Double, Integer> eigenValueMultiplicityMap()
	{
		EigenOutput eigenOutput = QREigenComponentExtractor.Standard().eigenize (_r2Array);

		return null == eigenOutput ? null : eigenOutput.eigenValueMultiplicityMap();
	}

	/**
	 * Compute the Determinant of the Matrix
	 * 
	 * @return Determinant of the Matrix
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double determinant()
		throws Exception
	{
		EigenOutput eigenOutput = QREigenComponentExtractor.Standard().eigenize (_r2Array);

		if (null == eigenOutput) {
			throw new Exception ("SquareMatrix::determinant => Cannot eigenize");
		}

		return eigenOutput.determinant();
	}

	/**
	 * Retrieve the Characteristic Polynomial of the Eigenvalues
	 * 
	 * @return Characteristic Polynomial of the Eigenvalues
	 */

	public R1ToR1 characteristicPolynomial()
	{
		EigenOutput eigenOutput = QREigenComponentExtractor.Standard().eigenize (_r2Array);

		return null == eigenOutput ? null : eigenOutput.characteristicPolynomial();
	}
}
