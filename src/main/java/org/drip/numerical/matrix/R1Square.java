
package org.drip.numerical.matrix;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.decomposition.QR;
import org.drip.numerical.eigenization.EigenOutput;
import org.drip.numerical.eigenization.QREigenComponentExtractor;
import org.drip.numerical.linearalgebra.R1MatrixUtil;

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
 * <i>R1Square</i> implements the type and Functionality associated with a R<sup>1</sup>Square Matrix. The
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/matrix/README.md">Implementation of R<sup>1</sup> C<sup>1</sup> Matrices</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Square
{
	private double[][] _r1Grid = null;

	/**
	 * Construct a Standard Instance of <i>R1Square</i>
	 * 
	 * @param r1Grid R<sup>1</sup> Grid
	 * 
	 * @return Standard Instance of <i>R1Square</i>
	 */

	public static R1Square Standard (
		final double[][] r1Grid)
	{
		if (!R1MatrixUtil.IsSquare (r1Grid)) {
			return null;
		}

		for (int i = 0; i < r1Grid.length; ++i) {
			if (!NumberUtil.IsValid (r1Grid[i])) {
				return null;
			}
		}

		return new R1Square (r1Grid);
	}

	protected R1Square (
		final double[][] r1Grid)
	{
		_r1Grid = r1Grid;
	}

	/**
	 * Retrieve R<sup>1</sup> Grid
	 * 
	 * @return R<sup>1</sup> Grid
	 */

	public double[][] r1Grid()
	{
		return _r1Grid;
	}

	/**
	 * Retrieve the Size of the Square Matrix
	 * 
	 * @return Size of the Square Matrix
	 */

	public int size()
	{
		return _r1Grid.length;
	}

	/**
	 * Compute the Trace
	 * 
	 * @return The Trace
	 */

	public double trace()
	{
		double trace = 0.;

		for (int i = 0; i < _r1Grid.length; ++i) {
			trace += _r1Grid[i][i];
		}

		return trace;
	}

	/**
	 * Transpose the Square Matrix
	 * 
	 * @return The Transposed Square Matrix
	 */

	public R1Square transpose()
	{
		return new R1Square (R1MatrixUtil.UnsafeTranspose (_r1Grid));
	}

	/**
	 * Compute the Addition with the other Square Matrix
	 * 
	 * @param squareMatrixOther "Other" Square Matrix
	 * 
	 * @return Resulting Matrix
	 */

	public R1Square add (
		final R1Square squareMatrixOther)
	{
		if (null == squareMatrixOther || _r1Grid.length != squareMatrixOther.size()) {
			return null;
		}

		double[][] r1GridOther = squareMatrixOther.r1Grid();

		double[][] r1GridAddition = new double[_r1Grid.length][_r1Grid.length];

		for (int rowIndex = 0; rowIndex < _r1Grid.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < _r1Grid.length; ++columnIndex) {
				r1GridAddition[rowIndex][columnIndex] =
					_r1Grid[rowIndex][columnIndex] + r1GridOther[rowIndex][columnIndex];
			}
		}

		return new R1Square (r1GridAddition);
	}

	/**
	 * Scale the Square Matrix
	 * 
	 * @param scale Scalar to Scale by
	 * 
	 * @return Resulting Matrix
	 */

	public R1Square scale (
		final double scale)
	{
		if (!NumberUtil.IsValid (scale)) {
			return null;
		}

		double[][] r1GridScale = new double[_r1Grid.length][_r1Grid.length];

		for (int rowIndex = 0; rowIndex < _r1Grid.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < _r1Grid.length; ++columnIndex) {
				r1GridScale[rowIndex][columnIndex] = _r1Grid[rowIndex][columnIndex] * scale;
			}
		}

		return new R1Square (r1GridScale);
	}

	/**
	 * Compute the Subtraction with the other Square Matrix
	 * 
	 * @param squareMatrixOther "Other" Square Matrix
	 * 
	 * @return Resulting Matrix
	 */

	public R1Square subtract (
		final R1Square squareMatrixOther)
	{
		if (null == squareMatrixOther || _r1Grid.length != squareMatrixOther.size()) {
			return null;
		}

		double[][] r1GridOther = squareMatrixOther.r1Grid();

		double[][] r1GridSubtraction = new double[_r1Grid.length][_r1Grid.length];

		for (int rowIndex = 0; rowIndex < _r1Grid.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < _r1Grid.length; ++columnIndex) {
				r1GridSubtraction[rowIndex][columnIndex] =
					_r1Grid[rowIndex][columnIndex] - r1GridOther[rowIndex][columnIndex];
			}
		}

		return new R1Square (r1GridSubtraction);
	}

	/**
	 * Compute the Product with the other Square Matrix
	 * 
	 * @param squareMatrixOther "Other" Square Matrix
	 * 
	 * @return Resulting Matrix
	 */

	public R1Square multiply (
		final R1Square squareMatrixOther)
	{
		if (null == squareMatrixOther || _r1Grid.length != squareMatrixOther.size()) {
			return null;
		}

		double[][] r1GridOther = squareMatrixOther.r1Grid();

		double[][] r1GridProduct = new double[_r1Grid.length][_r1Grid.length];

		for (int rowIndex = 0; rowIndex < _r1Grid.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < _r1Grid.length; ++columnIndex) {
				r1GridProduct[rowIndex][columnIndex] = 0.;

				for (int i = 0; i < _r1Grid.length; ++i) {
					r1GridProduct[rowIndex][columnIndex] +=
						_r1Grid[rowIndex][i] * r1GridOther[i][columnIndex];
				}
			}
		}

		return new R1Square (r1GridProduct);
	}

	/**
	 * Compute the Product with the Vector
	 * 
	 * @param r1Array Vector Array
	 * 
	 * @return Resulting Matrix
	 */

	public R1Square multiply (
		final double[] r1Array)
	{
		if (null == r1Array || 0 == r1Array.length || r1Array.length != _r1Grid.length) {
			return null;
		}

		double[][] r1GridProduct = new double[_r1Grid.length][_r1Grid.length];

		for (int rowIndex = 0; rowIndex < r1Array.length; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < _r1Grid.length; ++columnIndex) {
				r1GridProduct[rowIndex][columnIndex] = 0.;

				for (int i = 0; i < r1Array.length; ++i) {
					if (!NumberUtil.IsValid (r1Array[rowIndex])) {
						return null;
					}

					r1GridProduct[rowIndex][columnIndex] += r1Array[rowIndex] * _r1Grid[i][columnIndex];
				}
			}
		}

		return new R1Square (r1GridProduct);
	}

	/**
	 * Calculate whether the Matrix is "Normal"
	 * 
	 * @return TRUE - Matrix is "Normal"
	 */

	public boolean isNormal()
	{
		R1Square squareMatrixTranspose = transpose();

		double[][] r1GridTransposeTimesR1Grid = multiply (squareMatrixTranspose).r1Grid();

		double[][] r1GridTimesR1GridTranspose = squareMatrixTranspose.multiply (this).r1Grid();

		for (int i = 0; i < _r1Grid.length; ++i) {
			for (int j = 0; j < _r1Grid.length; ++j) {
				if (!NumberUtil.WithinTolerance (
					r1GridTransposeTimesR1Grid[i][j],
					r1GridTimesR1GridTranspose[i][j]
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
	 * Generate the QR Decomposition of the Square Matrix
	 * 
	 * @return QR Decomposition
	 */

	public QR qrDecomposition()
	{
		return R1MatrixUtil.QRDecomposition (_r1Grid);
	}

	/**
	 * Generate the RQ Decomposition of the Square Matrix
	 * 
	 * @return RQ Decomposition
	 */

	public QR rqDecomposition()
	{
		return R1MatrixUtil.RQDecomposition (_r1Grid);
	}

	/**
	 * Eigenize and Extract the Components of the Specified Matrix
	 * 
	 * @return The EigenComponents
	 */

	public EigenOutput eigenize()
	{
		return QREigenComponentExtractor.Standard().eigenize (_r1Grid);
	}

	/**
	 * Perform Singular Value Decomposition and Extract the Components of the Specified Matrix
	 * 
	 * @return The Singular Value Decomposition Components
	 */

	public EigenOutput svd()
	{
		return QREigenComponentExtractor.Standard().eigenize (_r1Grid);
	}

	/**
	 * Compute the Frobenius Norm of the Eigenvalues
	 * 
	 * @return Frobenius Norm of the Eigenvalues
	 * 
	 * @throws Exception Thrown if the R<sup>1</sup> Square Matrix cannot be eigenized
	 */

	public double svdBasedFrobeniusNorm()
		throws Exception
	{
		EigenOutput svdEigenOutput = svd();

		if (null == svdEigenOutput) {
			throw new Exception ("R1Square::svdBasedFrobeniusNorm => Cannot eigenize");
		}

		return svdEigenOutput.frobeniusNorm();
	}

	/**
	 * Retrieve the Array of Diagonal Entries
	 * 
	 * @return Array of Diagonal Entries
	 */

	public double[] diagonalEntryArray()
	{
		double[] diagonalEntryArray = new double[_r1Grid.length];

		for (int i = 0; i < _r1Grid.length; ++i) {
			diagonalEntryArray[i] = _r1Grid[i][i];
		}

		return diagonalEntryArray;
	}

	/**
	 * Compute the k<sup>th</sup> Power
	 * 
	 * @param k Power k
	 * 
	 * @return k<sup>th</sup> Power
	 */

	public R1Square power (
		final int k)
	{
		double[][] powerR1Grid = R1MatrixUtil.UnsafePower (_r1Grid, k);

		return null == powerR1Grid ? null : new R1Square (powerR1Grid);
	}

	/**
	 * Retrieve the Eigenvalue Multiplicity Map
	 * 
	 * @return Eigenvalue Multiplicity Map
	 */

	public Map<Double, Integer> eigenValueMultiplicityMap()
	{
		EigenOutput eigenOutput = QREigenComponentExtractor.Standard().eigenize (_r1Grid);

		return null == eigenOutput ? null : eigenOutput.eigenValueMultiplicityMap();
	}

	/**
	 * Compute the Determinant of the Matrix
	 * 
	 * @return Determinant of the Matrix
	 * 
	 * @throws Exception Thrown if the Determinant cannot be calculated
	 */

	public double determinant()
		throws Exception
	{
		EigenOutput eigenOutput = QREigenComponentExtractor.Standard().eigenize (_r1Grid);

		if (null == eigenOutput) {
			throw new Exception ("R1Square::determinant => Cannot eigenize");
		}

		return eigenOutput.determinant();
	}

	/**
	 * Compute the L<sub>2</sub> Condition Number of the Matrix
	 * 
	 * @return L<sub>2</sub> Condition Number of the Matrix
	 * 
	 * @throws Exception Thrown if the Condition Number cannot be calculated
	 */

	public double conditionNumberL2()
		throws Exception
	{
		EigenOutput eigenOutput = svd();

		if (null == eigenOutput) {
			throw new Exception ("R1Square::conditionNumberL2 => Cannot compute SVD");
		}

		return eigenOutput.conditionNumber();
	}

	/**
	 * Compute the Default Condition Number of the Matrix
	 * 
	 * @return Default Condition Number of the Matrix
	 * 
	 * @throws Exception Thrown if the Default Condition Number cannot be calculated
	 */

	public double defaultConditionNumber()
		throws Exception
	{
		return conditionNumberL2();
	}

	/**
	 * Retrieve the Characteristic Polynomial of the Eigenvalues
	 * 
	 * @return Characteristic Polynomial of the Eigenvalues
	 */

	public R1ToR1 characteristicPolynomial()
	{
		EigenOutput eigenOutput = QREigenComponentExtractor.Standard().eigenize (_r1Grid);

		return null == eigenOutput ? null : eigenOutput.characteristicPolynomial();
	}

	/**
	 * Compute the Spectral Radius of the Matrix
	 * 
	 * @return Spectral Radius of the Matrix
	 * 
	 * @throws Exception Thrown if the Spectral Radius cannot be calculated
	 */

	public double spectralRadius()
		throws Exception
	{
		EigenOutput eigenOutput = QREigenComponentExtractor.Standard().eigenize (_r1Grid);

		if (null == eigenOutput) {
			throw new Exception ("R1Square::spectralRadius => Cannot eigenize");
		}

		return eigenOutput.spectralRadius();
	}
}
