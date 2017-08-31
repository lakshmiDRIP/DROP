
package org.drip.quant.linearalgebra;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * Matrix implements Matrix manipulation routines. It exports the following functionality:
 * 	- Matrix Inversion using Closed form solutions (for low-dimension matrices), or using Gaussian
 * 		elimination
 * 	- Matrix Product
 * 	- Matrix Diagonalization and Diagonal Pivoting
 * 	- Matrix Regularization through Row Addition/Row Swap
 *
 * @author Lakshmi Krishnamurthy
 */

public class Matrix {

	/**
	 * Lower Triangular Matrix
	 */

	public static int LOWER_TRIANGULAR = 1;

	/**
	 * Upper Triangular Matrix
	 */

	public static int UPPER_TRIANGULAR = 2;

	/**
	 * Lower+Upper Triangular Matrix
	 */

	public static int LOWER_AND_UPPER_TRIANGULAR = 3;

	/**
	 * Non Triangular Matrix
	 */

	public static int NON_TRIANGULAR = 0;

	/**
	 * Diagonalize the specified row in the source matrix, and apply comparable operations to the target
	 * 
	 * @param iQ Row in the Source Matrix
	 * @param aadblZ2XJack Source Matrix
	 * @param aadblZ2YJack Target Matrix
	 * 
	 * @return TRUE - Diagonalization was successful
	 */

	public static final boolean DiagonalizeRow (
		final int iQ,
		final double[][] aadblZ2XJack,
		final double[][] aadblZ2YJack)
	{
		if (0. != aadblZ2XJack[iQ][iQ]) return true;

		int iSize = aadblZ2XJack.length;
		int iP = iSize - 1;

		while (0. == aadblZ2XJack[iP][iQ] && iP >= 0) --iP;

		if (0 > iP) return false;

		for (int j = 0; j < iSize; ++j)
			aadblZ2XJack[iQ][j] += aadblZ2XJack[iP][j];

		aadblZ2YJack[iQ][iP] += 1.;
		return true;
	}

	/**
	 * Compute the Product of an Input Matrix and a Column
	 * 
	 * @param aadblA Matrix A
	 * @param adblB Array B
	 * 
	 * @return The Product
	 */

	public static final double[] Product (
		final double[][] aadblA,
		final double[] adblB)
	{
		if (null == aadblA || null == adblB) return null;

		int iNumACol = aadblA[0].length;
		int iNumProductCol = adblB.length;
		int iNumProductRow = aadblA.length;
		double[] adblProduct = new double[iNumProductRow];

		if (0 == iNumACol || iNumACol != adblB.length || 0 == iNumProductRow || 0 == iNumProductCol)
			return null;

		for (int iRow = 0; iRow < iNumProductRow; ++iRow) {
			adblProduct[iRow] = 0.;

			for (int i = 0; i < iNumACol; ++i) {
				if (!org.drip.quant.common.NumberUtil.IsValid (aadblA[iRow][i]) ||
					!org.drip.quant.common.NumberUtil.IsValid (adblB[i]))
					return null;

				adblProduct[iRow] += aadblA[iRow][i] * adblB[i];
			}
		}

		return adblProduct;
	}

	/**
	 * Compute the Product of an input column and a matrix
	 * 
	 * @param adblA Column A
	 * @param aadblB Matrix B
	 * 
	 * @return The Product
	 */

	public static final double[][] Product (
		final double[] adblA,
		final double[][] aadblB)
	{
		if (null == adblA || null == aadblB) return null;

		int iNumACol = adblA.length;
		int iNumProductCol = aadblB.length;
		double[][] aadblProduct = new double[iNumACol][iNumProductCol];

		if (0 == iNumACol || iNumACol != aadblB.length || 0 == iNumProductCol) return null;

		for (int iRow = 0; iRow < iNumACol; ++iRow) {
			for (int iCol = 0; iCol < iNumProductCol; ++iCol) {
				aadblProduct[iRow][iCol] = 0.;

				for (int i = 0; i < iNumACol; ++i) {
					if (!org.drip.quant.common.NumberUtil.IsValid (adblA[iRow]) ||
						!org.drip.quant.common.NumberUtil.IsValid (aadblB[i][iCol]))
						return null;

					aadblProduct[iRow][iCol] += adblA[iRow] * aadblB[i][iCol];
				}
			}
		}

		return aadblProduct;
	}

	/**
	 * Compute the Product of the input matrices
	 * 
	 * @param aadblA Matrix A
	 * @param aadblB Matrix B
	 * 
	 * @return The Product
	 */

	public static final double[][] Product (
		final double[][] aadblA,
		final double[][] aadblB)
	{
		if (null == aadblA || null == aadblB) return null;

		int iNumACol = aadblA[0].length;
		int iNumProductRow = aadblA.length;
		int iNumProductCol = aadblB[0].length;
		double[][] aadblProduct = new double[iNumProductRow][iNumProductCol];

		if (0 == iNumACol || iNumACol != aadblB.length || 0 == iNumProductRow || 0 == iNumProductCol)
			return null;

		for (int iRow = 0; iRow < iNumProductRow; ++iRow) {
			for (int iCol = 0; iCol < iNumProductCol; ++iCol) {
				aadblProduct[iRow][iCol] = 0.;

				for (int i = 0; i < iNumACol; ++i) {
					if (!org.drip.quant.common.NumberUtil.IsValid (aadblA[iRow][i]) ||
						!org.drip.quant.common.NumberUtil.IsValid (aadblB[i][iCol]))
						return null;

					aadblProduct[iRow][iCol] += aadblA[iRow][i] * aadblB[i][iCol];
				}
			}
		}

		return aadblProduct;
	}

	/**
	 * Make a Square Diagonal Matrix from a Row
	 * 
	 * @param adblA The Row Array
	 * 
	 * @return The corresponding Square Diagonal Matrix
	 */

	public static final double[][] MakeSquareDiagonal (
		final double[] adblA)
	{
		if (null == adblA) return null;

		int iNumElement = adblA.length;
		double[][] aadblDiagonal = 0 == iNumElement ? null : new double[iNumElement][iNumElement];

		if (0 == iNumElement) return null;

		for (int i = 0; i < iNumElement; ++i) {
			for (int j = 0; j < iNumElement; ++j)
				aadblDiagonal[i][j] = i == j ? adblA[i] : 0.;
		}

		return aadblDiagonal;
	}

	/**
	 * Invert a 2D Matrix using Cramer's Rule
	 * 
	 * @param aadblA Input 2D Matrix
	 * 
	 * @return The Inverted Matrix
	 */

	public static final double[][] Invert2DMatrixUsingCramerRule (
		final double[][] aadblA)
	{
		if (null == aadblA || 2 != aadblA.length || 2 != aadblA[0].length) return null;

		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 2; ++j) {
				if (!org.drip.quant.common.NumberUtil.IsValid (aadblA[i][j])) return null;
			}
		}

		double dblScale = aadblA[0][0] * aadblA[1][1] - aadblA[0][1] * aadblA[1][0];

		if (0. == dblScale) return null;

		return new double[][] {{aadblA[1][1] / dblScale, -1. * aadblA[0][1] / dblScale}, {-1. * aadblA[1][0]
			/ dblScale, aadblA[0][0] / dblScale}};
	}

	/**
	 * Regularize the specified diagonal entry of the input matrix using Row Swapping
	 * 
	 * @param mct The Input Matrix Complement Transform
	 * 
	 * @return The Regularization was successful
	 */

	public static final boolean RegularizeUsingRowSwap (
		final org.drip.quant.linearalgebra.MatrixComplementTransform mct)
	{
		if (null == mct) return false;

		int iSize = mct.size();

		double[][] aadblSource = mct.getSource();

		double[][] aadblComplement = mct.getComplement();

		for (int iDiagonal = 0; iDiagonal < iSize; ++iDiagonal) {
			if (0. == aadblSource[iDiagonal][iDiagonal]) {
				int iSwapRow = iSize - 1;

				while (iSwapRow >= 0 && (0. == aadblSource[iSwapRow][iDiagonal] || 0. ==
					aadblSource[iDiagonal][iSwapRow]))
					--iSwapRow;

				if (0 > iSwapRow) {
					iSwapRow = 0;

					while (iSwapRow < iSize && 0. == aadblSource[iSwapRow][iDiagonal])
						++iSwapRow;

					if (iSwapRow >= iSize) return false;
				}

				for (int iCol = 0; iCol < iSize; ++iCol) {
					double dblComplementDiagonalEntry = aadblComplement[iDiagonal][iCol];
					aadblComplement[iDiagonal][iCol] = aadblComplement[iSwapRow][iCol];
					aadblComplement[iSwapRow][iCol] = dblComplementDiagonalEntry;
					double dblSourceDiagonalEntry = aadblSource[iDiagonal][iCol];
					aadblSource[iDiagonal][iCol] = aadblSource[iSwapRow][iCol];
					aadblSource[iSwapRow][iCol] = dblSourceDiagonalEntry;
				}
			}
		}

		/* for (int iDiagonal = 0; iDiagonal < iSize; ++iDiagonal) {
			if (0. == aadblSource[iDiagonal][iDiagonal]) {
				org.drip.quant.common.NumberUtil.Print2DArray ("ZERO DIAG!", aadblSource, false);

				return false;
			}
		} */

		return true;
	}

	/**
	 * Regularize the specified diagonal entry of the input matrix using Row Addition
	 * 
	 * @param mct The Input Matrix Complement Transform
	 * 
	 * @return The Regularization was successful
	 */

	public static final boolean RegularizeUsingRowAddition (
		final org.drip.quant.linearalgebra.MatrixComplementTransform mct)
	{
		if (null == mct) return false;

		int iSize = mct.size();

		double[][] aadblSource = mct.getSource();

		double[][] aadblComplement = mct.getComplement();

		for (int iDiagonal = 0; iDiagonal < iSize; ++iDiagonal) {
			if (0. == aadblSource[iDiagonal][iDiagonal]) {
				int iPivotRow = iSize - 1;

				while (0. == aadblSource[iPivotRow][iDiagonal] && iPivotRow >= 0) --iPivotRow;

				if (0 > iPivotRow) return false;

				for (int iCol = 0; iCol < iSize; ++iCol) {
					aadblSource[iDiagonal][iCol] += aadblSource[iPivotRow][iCol];
					aadblComplement[iDiagonal][iCol] += aadblComplement[iPivotRow][iCol];
				}
			}
		}

		return true;
	}

	/**
	 * Pivot the Diagonal of the Input Matrix
	 * 
	 * @param aadblA The Input Matrix
	 * 
	 * @return The Matrix Complement Transform Instance
	 */

	public static final org.drip.quant.linearalgebra.MatrixComplementTransform PivotDiagonal (
		final double[][] aadblA)
	{
		if (null == aadblA) return null;

		int iSize = aadblA.length;
		double[][] aadblSource = new double[iSize][iSize];
		double[][] aadblComplement = new double[iSize][iSize];
		org.drip.quant.linearalgebra.MatrixComplementTransform mctOut = null;

		if (0 == iSize || null == aadblA[0] || iSize != aadblA[0].length) return null;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				aadblSource[i][j] = aadblA[i][j];
				aadblComplement[i][j] = i == j ? 1. : 0.;
			}
		}

		try {
			mctOut = new org.drip.quant.linearalgebra.MatrixComplementTransform (aadblSource,
				aadblComplement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return RegularizeUsingRowSwap (mctOut) ? mctOut : null;
	}

	/**
	 * Invert the Source Matrix using Gaussian Elimination
	 * 
	 * @param aadblSource Source Matrix
	 * 
	 * @return The Inverted Matrix
	 */

	public static final double[][] InvertUsingGaussianElimination (
		final double[][] aadblSource)
	{
		org.drip.quant.linearalgebra.MatrixComplementTransform mctRegularized =
			org.drip.quant.linearalgebra.Matrix.PivotDiagonal (aadblSource);

		if (null == mctRegularized) return null;

		double[][] aadblRegularizedSource = mctRegularized.getSource();

		double[][] aadblRegularizedInverse = mctRegularized.getComplement();

		int iSize = aadblRegularizedSource.length;

		for (int iDiagonal = 0; iDiagonal < iSize; ++iDiagonal) {
			if (0. == aadblRegularizedSource[iDiagonal][iDiagonal]) return null;

			for (int iRow = 0; iRow < iSize; ++iRow) {
				if (iRow == iDiagonal || 0. == aadblRegularizedSource[iRow][iDiagonal]) continue;

				double dblColEntryEliminatorRatio = aadblRegularizedSource[iDiagonal][iDiagonal] /
					aadblRegularizedSource[iRow][iDiagonal];

				for (int iCol = 0; iCol < iSize; ++iCol) {
					aadblRegularizedSource[iRow][iCol] = aadblRegularizedSource[iRow][iCol] *
						dblColEntryEliminatorRatio - aadblRegularizedSource[iDiagonal][iCol];
					aadblRegularizedInverse[iRow][iCol] = aadblRegularizedInverse[iRow][iCol] *
						dblColEntryEliminatorRatio - aadblRegularizedInverse[iDiagonal][iCol];
				}
			}
		}

		for (int iDiagonal = 0; iDiagonal < iSize; ++iDiagonal) {
			double dblDiagScaleDown = aadblRegularizedSource[iDiagonal][iDiagonal];

			if (0. == dblDiagScaleDown) return null;

			for (int iCol = 0; iCol < iSize; ++iCol) {
				aadblRegularizedSource[iDiagonal][iCol] /= dblDiagScaleDown;
				aadblRegularizedInverse[iDiagonal][iCol] /= dblDiagScaleDown;
			}
		}

		return aadblRegularizedInverse;
	}

	/**
	 * Invert the input matrix using the specified Method
	 * 
	 * @param aadblA Input Matrix
	 * @param strMethod The Inversion Method
	 * 
	 * @return The Inverted Matrix
	 */

	public static final double[][] Invert (
		final double[][] aadblA,
		final java.lang.String strMethod)
	{
		if (null == aadblA) return null;

		int iSize = aadblA.length;
		double[][] aadblAInv = null;
		double[][] aadblASource = new double[iSize][iSize];
		double[][] aadblZ2YJack = new double[iSize][iSize];

		if (0 == iSize || iSize != aadblA[0].length) return null;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				if (!org.drip.quant.common.NumberUtil.IsValid (aadblASource[i][j] = aadblA[i][j]))
					return null;

				aadblZ2YJack[i][j] = i == j ? 1. : 0.;
			}
		}

		for (int i = 0; i < iSize; ++i) {
			if (0. == aadblASource[i][i] && !DiagonalizeRow (i, aadblASource, aadblZ2YJack)) return null;
		}

		if (null == strMethod || strMethod.isEmpty() || strMethod.equalsIgnoreCase ("GaussianElimination"))
			aadblAInv = InvertUsingGaussianElimination (aadblASource);

		if (null == aadblAInv || iSize != aadblAInv.length || iSize != aadblAInv[0].length) return null;

		return Product (aadblAInv, aadblZ2YJack);
	}

	/**
	 * Compute the Rank of the Matrix
	 * 
	 * @param aadblSource Source Matrix
	 * 
	 * @return The Rank of the Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Rank Cannot be computed
	 */

	public static final int Rank (
		final double[][] aadblSource)
		throws java.lang.Exception
	{
		if (null == aadblSource) return 0;

		int iNumRow = aadblSource.length;

		if (iNumRow == 0) return 0;

		int iNumCol = aadblSource[0].length;

		for (int iScanRow = 0; iScanRow < iNumRow; ++iScanRow) {
			if (!org.drip.quant.common.NumberUtil.IsValid (aadblSource[iScanRow]))
				throw new java.lang.Exception ("Matrix::Rank => Invalid Inputs");
		}

		double[][] aadblRegularizedSource = iNumRow < iNumCol ?
			org.drip.quant.linearalgebra.Matrix.Transpose (aadblSource) : aadblSource;

		int iNumDependentRow = 0;
		int iProcessedRow = aadblRegularizedSource.length;
		int iProcessedCol = aadblRegularizedSource[0].length;

		if (1 == iNumRow || 1 == iNumCol) return iProcessedRow;

		for (int iScanRow = 0; iScanRow < iProcessedCol; ++iScanRow) {
			for (int iRow = 0; iRow < iProcessedCol; ++iRow) {
				if (iRow == iScanRow || 0. == aadblRegularizedSource[iRow][iScanRow]) continue;

				double dblColEntryEliminatorRatio = aadblRegularizedSource[iScanRow][iScanRow] /
					aadblRegularizedSource[iRow][iScanRow];

				for (int iCol = 0; iCol < iProcessedCol; ++iCol)
					aadblRegularizedSource[iRow][iCol] = aadblRegularizedSource[iRow][iCol] *
						dblColEntryEliminatorRatio - aadblRegularizedSource[iScanRow][iCol];
			}
		}

		for (int iScanRow = 0; iScanRow < iProcessedCol; ++iScanRow) {
			if (0. == org.drip.quant.linearalgebra.Matrix.Modulus (aadblRegularizedSource[iScanRow]))
				++iNumDependentRow;
		}

		return iProcessedRow - iNumDependentRow;
	}

	/**
	 * Transpose the specified Square Matrix
	 * 
	 * @param aadblA The Input Square Matrix
	 * 
	 * @return The Transpose of the Square Matrix
	 */

	public static final double[][] Transpose (
		final double[][] aadblA)
	{
		if (null == aadblA) return null;

		int iRowSize = aadblA.length;

		if (0 == iRowSize || null == aadblA[0]) return null;

		int iColSize = aadblA[0].length;
		double[][] aadblATranspose = new double[iColSize][iRowSize];

		if (0 == iColSize) return null;

		for (int i = 0; i < iColSize; ++i) {
			for (int j = 0; j < iRowSize; ++j)
				aadblATranspose[i][j] = aadblA[j][i];
		}

		return aadblATranspose;
	}

	/**
	 * Compute the Cholesky-Banachiewicz Factorization of the specified Matrix.
	 * 
	 * @param aadblA The Input Matrix
	 * 
	 * @return The Factorized Matrix
	 */

	public static final double[][] CholeskyBanachiewiczFactorization (
		final double[][] aadblA)
	{
		if (null == aadblA) return null;

		int iSize = aadblA.length;
		double[][] aadblL = new double[iSize][iSize];

		if (0 == iSize || null == aadblA[0] || iSize != aadblA[0].length) return null;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				aadblL[i][j] = 0.;

				if (i == j) {
					for (int k = 0; k < j; ++k)
						aadblL[j][j] -= aadblL[j][k] * aadblL[j][k];

					aadblL[j][j] = java.lang.Math.sqrt (aadblL[j][j] + aadblA[j][j]);
				} else if (i > j) {
					for (int k = 0; k < j; ++k)
						aadblL[i][j] -= aadblL[i][k] * aadblL[j][k];

					aadblL[i][j] = (aadblA[i][j] + aadblL[i][j]) / aadblL[j][j];
				}
			}
		}

		return aadblL;
	}

	/**
	 * Dot Product of Vectors A and E
	 * 
	 * @param adblA Vector A
	 * @param adblE Vector E
	 * 
	 * @return The Dot Product
	 * 
	 * @throws java.lang.Exception Thrown if the Dot-Product cannot be computed
	 */

	public static final double DotProduct (
		final double[] adblA,
		final double[] adblE)
		throws java.lang.Exception
	{
		if (null == adblA || null == adblE)
			throw new java.lang.Exception ("Matrix::DotProduct => Invalid Inputs!");

		int iSize = adblA.length;
		double dblDotProduct = 0.;

		if (0 == iSize || iSize != adblE.length)
			throw new java.lang.Exception ("Matrix::DotProduct => Invalid Inputs!");

		for (int i = 0; i < iSize; ++i)
			dblDotProduct += adblE[i] * adblA[i];

		return dblDotProduct;
	}

	/**
	 * Project the Vector A along the Vector E
	 * 
	 * @param adblA Vector A
	 * @param adblE Vector E
	 * 
	 * @return The Vector of Projection of A along E
	 */

	public static final double[] Project (
		final double[] adblA,
		final double[] adblE)
	{
		if (null == adblA || null == adblE) return null;

		int iSize = adblA.length;
		double dblProjection = java.lang.Double.NaN;
		double[] adblProjectAOnE = new double[iSize];

		if (0 == iSize || iSize != adblE.length) return null;

		try {
			dblProjection = DotProduct (adblA, adblE) / DotProduct (adblE, adblE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < iSize; ++i)
			adblProjectAOnE[i] = adblE[i] * dblProjection;

		return adblProjectAOnE;
	}

	/**
	 * Compute the Modulus of the Input Vector
	 * 
	 * @param adbl The Input Vector
	 * 
	 * @return TRUE - The Modulus of the Input Vector
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double Modulus (
		final double[] adbl)
		throws java.lang.Exception
	{
		if (null == adbl || !org.drip.quant.common.NumberUtil.IsValid (adbl))
			throw new java.lang.Exception ("Matrix::Modulus => Invalid Inputs");

		double dblModulus = 0.;
		int iSize = adbl.length;

		if (0 == iSize) throw new java.lang.Exception ("Matrix::Modulus => Invalid Inputs");

		for (int i = 0; i < iSize; ++i)
			dblModulus += adbl[i] * adbl[i];

		return java.lang.Math.sqrt (dblModulus);
	}

	/**
	 * Indicate if the Array Entries are Positive or Zero
	 * 
	 * @param adbl The Array
	 * 
	 * @return TRUE - The Array Entries are Positive or Zero
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean PositiveOrZero (
		final double[] adbl)
		throws java.lang.Exception
	{
		if (null == adbl || !org.drip.quant.common.NumberUtil.IsValid (adbl))
			throw new java.lang.Exception ("Matrix::PositiveOrZero => Invalid Inputs");

		int iSize = adbl.length;

		if (0 == iSize) throw new java.lang.Exception ("Matrix::PositiveOrZero => Invalid Inputs");

		for (int i = 0; i < iSize; ++i) {
			if (0. > adbl[i]) return false;
		}

		return true;
	}

	/**
	 * Indicate if the Array Entries are Negative or Zero
	 * 
	 * @param adbl The Array
	 * 
	 * @return The Array Entries are Negative or Zero
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean NegativeOrZero (
		final double[] adbl)
		throws java.lang.Exception
	{
		if (null == adbl || !org.drip.quant.common.NumberUtil.IsValid (adbl))
			throw new java.lang.Exception ("Matrix::NegativeOrZero => Invalid Inputs");

		int iSize = adbl.length;

		if (0 == iSize)  throw new java.lang.Exception ("Matrix::NegativeOrZero => Invalid Inputs");

		for (int i = 0; i < iSize; ++i) {
			if (0. < adbl[i]) return false;
		}

		return true;
	}

	/**
	 * Indicate if the Array Entries are Positive Linearly Independent
	 * 
	 * @param adbl The Array
	 * 
	 * @return TRUE - The Array Entries are Positive Linearly Independent
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean PositiveLinearlyIndependent (
		final double[] adbl)
		throws java.lang.Exception
	{
		return !PositiveOrZero (adbl) && !NegativeOrZero (adbl);
	}

	/**
	 * Normalize the Input Vector
	 * 
	 * @param adbl The Input Vector
	 * 
	 * @return The Normalized Vector
	 */

	public static final double[] Normalize (
		final double[] adbl)
	{
		if (null == adbl) return null;

		double dblNorm = 0.;
		int iSize = adbl.length;
		double[] adblNormalized = new double[iSize];

		if (0 == iSize) return null;

		for (int i = 0; i < iSize; ++i)
			dblNorm += adbl[i] * adbl[i];

		dblNorm = java.lang.Math.sqrt (dblNorm);

		for (int i = 0; i < iSize; ++i)
			adblNormalized[i] = adbl[i] / dblNorm;

		return adblNormalized;
	}

	/**
	 * Orthogonalize the Specified Matrix Using the Graham-Schmidt Method
	 * 
	 * @param aadblV The Input Matrix
	 * 
	 * @return The Orthogonalized Matrix
	 */

	public static final double[][] GrahamSchmidtOrthogonalization (
		final double[][] aadblV)
	{
		if (null == aadblV) return null;

		int iSize = aadblV.length;
		double[][] aadblU = new double[iSize][iSize];

		if (0 == iSize || null == aadblV[0] || iSize != aadblV[0].length) return null;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j)
				aadblU[i][j] = aadblV[i][j];

			for (int j = 0; j < i; ++j) {
				double dblProjectionAmplitude = java.lang.Double.NaN;

				try {
					dblProjectionAmplitude = DotProduct (aadblV[i], aadblU[j]) / DotProduct (aadblU[j],
						aadblU[j]);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}

				for (int k = 0; k < iSize; ++k)
					aadblU[i][k] -= dblProjectionAmplitude * aadblU[j][k];
			}
		}

		return aadblU;
	}

	/**
	 * Orthonormalize the Specified Matrix Using the Graham-Schmidt Method
	 * 
	 * @param aadblV The Input Matrix
	 * 
	 * @return The Orthonormalized Matrix
	 */

	public static final double[][] GrahamSchmidtOrthonormalization (
		final double[][] aadblV)
	{
		double[][] aadblVOrthogonal = GrahamSchmidtOrthogonalization (aadblV);

		if (null == aadblVOrthogonal) return null;

		int iSize = aadblVOrthogonal.length;

		double[][] aadblVOrthonormal = new double[iSize][];

		for (int i = 0; i < iSize; ++i)
			aadblVOrthonormal[i] = Normalize (aadblVOrthogonal[i]);

		return aadblVOrthonormal;
	}

	/**
	 * Perform a QR Decomposition on the Input Matrix
	 * 
	 * @param aadblA The Input Matrix
	 * 
	 * @return The Output of QR Decomposition
	 */

	public static final org.drip.quant.linearalgebra.QR QRDecomposition (
		final double[][] aadblA)
	{
		double[][] aadblQ = GrahamSchmidtOrthonormalization (aadblA);

		if (null == aadblQ) return null;

		int iSize = aadblQ.length;
		double[][] aadblR = new double[iSize][iSize];

		try {
			for (int i = 0; i < iSize; ++i) {
				for (int j = 0; j < iSize; ++j)
					aadblR[i][j] = i > j ? DotProduct (aadblQ[i], aadblA[j]) : 0.;
			}

			return new org.drip.quant.linearalgebra.QR (aadblQ, aadblR);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Triangular Type of the Matrix
	 * 
	 * @param aadblA The Input Matrix
	 * @param dblFloor The Floor Level that means "Zero"
	 * 
	 * @return The Triangular Type
	 */

	public static final int TriangularType (
		final double[][] aadblA,
		final double dblFloor)
	{
		if (null == aadblA || !org.drip.quant.common.NumberUtil.IsValid (dblFloor) || dblFloor < 0.)
			return NON_TRIANGULAR;

		int iSize = aadblA.length;
		boolean bLowerTriangular = true;
		boolean bUpperTriangular = true;

		if (1 >= iSize || null == aadblA[0] || iSize != aadblA[0].length) return NON_TRIANGULAR;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				if (i > j) {
					if (java.lang.Math.abs (aadblA[i][j]) > dblFloor) bLowerTriangular = false;
				} else if (i < j) {
					if (java.lang.Math.abs (aadblA[i][j]) > dblFloor) bUpperTriangular = false;
				}
			}
		}

		if (bLowerTriangular && bUpperTriangular) return LOWER_AND_UPPER_TRIANGULAR;

		if (bLowerTriangular && !bUpperTriangular) return LOWER_TRIANGULAR;

		if (!bLowerTriangular && bUpperTriangular) return UPPER_TRIANGULAR;

		return NON_TRIANGULAR;
	}
}
