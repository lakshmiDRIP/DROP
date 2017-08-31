
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
 * LinearSystemSolver implements the solver for a system of linear equations given by A * x = B, where A is
 * 	the matrix, x the set of variables, and B is the result to be solved for. It exports the following
 * 	functions:
 * 	- Row Regularization and Diagonal Pivoting
 * 	- Check for Diagonal Dominance
 * 	- Solving the linear system using any one of the following: Gaussian Elimination, Gauss Seidel reduction,
 * 		or matrix inversion.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LinearSystemSolver {

	/**
	 * Regularize (i.e., convert the diagonal entries of the given cell to non-zero using suitable linear
	 * 	transformations)
	 * 
	 * @param aadblA In/Out Matrix to be regularized
	 * @param adblSolution In/out RHS
	 * @param iInnerRow Matrix Cell Row that needs to be regularized
	 * @param iOuter Matrix Cell Column that needs to be regularized
	 * 
	 * @return TRUE - Matrix has been successfully regularized
	 */

	public static final boolean RegulariseRow (
		final double[][] aadblA,
		final double[] adblSolution,
		final int iInnerRow,
		final int iOuter)
	{
		double dblInnerScaler = aadblA[iInnerRow][iOuter];

		if (0. != dblInnerScaler) return true;

		int iSize = aadblA.length;
		int iProxyRow = iSize - 1;

		while (0. == aadblA[iProxyRow][iOuter] && iProxyRow >= 0) --iProxyRow;

		if (iProxyRow < 0) return false;

		adblSolution[iInnerRow] += adblSolution[iProxyRow];

		for (int i = 0; i < iSize; ++i)
			aadblA[iInnerRow][i] += aadblA[iProxyRow][i];

		return 0. != aadblA[iInnerRow][iOuter];
	}

	/**
	 * Check to see if the matrix is diagonally dominant.
	 * 
	 * @param aadblA Input Matrix
	 * @param bCheckForStrongDominance TRUE - Fail if the matrix is not strongly diagonally dominant.
	 * 
	 * @return TRUE - Strongly or weakly Diagonally Dominant
	 */

	public static final boolean IsDiagonallyDominant (
		final double[][] aadblA,
		final boolean bCheckForStrongDominance)
	{
		if (null == aadblA) return false;

		int iSize = aadblA.length;

		if (0 == iSize || null == aadblA[0] || iSize != aadblA[0].length) return false;

		for (int i = 0; i < iSize; ++i) {
			double dblAbsoluteDiagonalEntry = java.lang.Math.abs (aadblA[i][i]);

			for (int j = 0; j < iSize; ++j) {
				if (i != j) {
					if ((bCheckForStrongDominance && dblAbsoluteDiagonalEntry <= java.lang.Math.abs
						(aadblA[i][j])) || (!bCheckForStrongDominance && dblAbsoluteDiagonalEntry <
							java.lang.Math.abs (aadblA[i][j])))
						return false;
				}
			}
		}

		return true;
	}

	/**
	 * Pivots the matrix A (Refer to wikipedia to find out what "pivot a matrix" means ;))
	 * 
	 * @param aadblA Input Matrix
	 * @param adblB Input RHS
	 * 
	 * @return The pivoted input matrix and the re-jigged input RHS
	 */

	public static final double[] Pivot (
		final double[][] aadblA,
		final double[] adblB)
	{
		if (null == aadblA || null == adblB) return null;

		int iSize = aadblA.length;
		double[] adblSolution = new double[iSize];

		if (0 == iSize || null == aadblA[0] || iSize != aadblA[0].length || iSize != adblB.length)
			return null;

		for (int i = 0; i < iSize; ++i)
			adblSolution[i] = adblB[i];

		for (int iDiagonal = 0; iDiagonal < iSize; ++iDiagonal) {
			if (!RegulariseRow (aadblA, adblSolution, iDiagonal, iDiagonal)) return null;
		}

		return adblSolution;
	}

	/**
	 * Solve the Linear System using Matrix Inversion from the Set of Values in the Array
	 * 
	 * @param aadblAIn Input Matrix
	 * @param adblB The Array of Values to be calibrated to
	 * 
	 * @return The Linear System Solution for the Coefficients
	 */

	public static final org.drip.quant.linearalgebra.LinearizationOutput SolveUsingMatrixInversion (
		final double[][] aadblAIn,
		final double[] adblB)
	{
		if (null == aadblAIn || null == adblB) return null;

		int iSize = aadblAIn.length;
		double[] adblSolution = new double[iSize];

		if (0 == iSize || null == aadblAIn[0] || iSize != aadblAIn[0].length) return null;

		if (adblB.length != iSize) return null;

		double[][] aadblInv = org.drip.quant.linearalgebra.Matrix.InvertUsingGaussianElimination (aadblAIn);

		if (null == aadblInv) return null;

		double[] adblProduct = org.drip.quant.linearalgebra.Matrix.Product (aadblInv, adblB);

		if (null == adblProduct || iSize != adblProduct.length) return null;

		for (int i = 0; i < iSize; ++i)
			adblSolution[i] = adblProduct[i];

		try {
			return new LinearizationOutput (adblSolution, aadblInv, "GaussianElimination");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Solve the Linear System using Gaussian Elimination from the Set of Values in the Array
	 * 
	 * @param aadblAIn Input Matrix
	 * @param adblB The Array of Values to be calibrated to
	 * 
	 * @return The Linear System Solution for the Coefficients
	 */

	public static final org.drip.quant.linearalgebra.LinearizationOutput SolveUsingGaussianElimination (
		final double[][] aadblAIn,
		final double[] adblB)
	{
		if (null == aadblAIn || null == adblB) return null;

		int iSize = aadblAIn.length;
		double[][] aadblA = new double[iSize][iSize];

		if (0 == iSize || null == aadblAIn[0] || iSize != aadblAIn[0].length) return null;

		if (adblB.length != iSize) return null;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j)
				aadblA[i][j] = aadblAIn[i][j];
		}

		double[] adblSolution = Pivot (aadblA, adblB);

		if (null == adblSolution || adblSolution.length != iSize) return null;

		for (int iEliminationDiagonalPivot = iSize - 1; iEliminationDiagonalPivot >= 0;
			--iEliminationDiagonalPivot) {
			for (int iRow = 0; iRow < iSize; ++iRow) {
				if (iRow == iEliminationDiagonalPivot) continue;

				if (0. == aadblA[iRow][iEliminationDiagonalPivot]) continue;

				double dblEliminationRatio = aadblA[iEliminationDiagonalPivot][iEliminationDiagonalPivot] /
					aadblA[iRow][iEliminationDiagonalPivot];
				adblSolution[iRow] = adblSolution[iRow] * dblEliminationRatio -
					adblSolution[iEliminationDiagonalPivot];

				for (int iCol = 0; iCol < iSize; ++iCol)
					aadblA[iRow][iCol] = aadblA[iRow][iCol] * dblEliminationRatio -
						aadblA[iEliminationDiagonalPivot][iCol];
			}
		}

		for (int i = iSize - 1; i >= 0; --i) {
			for (int j = iSize - 1; j > i; --j)
				adblSolution[i] -= adblSolution[j] * aadblA[i][j];

			adblSolution[i] /= aadblA[i][i];
		}

		try {
			return new LinearizationOutput (adblSolution, aadblA, "GaussianElimination");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Solve the Linear System using the Gauss-Seidel algorithm from the Set of Values in the Array
	 * 
	 * @param aadblAIn Input Matrix
	 * @param adblB The Array of Values to be calibrated to
	 * 
	 * @return The Linear System Solution for the Coefficients
	 */

	public static final org.drip.quant.linearalgebra.LinearizationOutput SolveUsingGaussSeidel (
		final double[][] aadblAIn,
		final double[] adblB)
	{
		if (null == aadblAIn || null == adblB) return null;

		int NUM_SIM = 5;
		int iSize = aadblAIn.length;
		double[] adblSolution = new double[iSize];
		double[][] aadblA = new double[iSize][iSize];

		if (0 == iSize || null == aadblAIn[0] || iSize != aadblAIn[0].length || iSize != adblB.length)
			return null;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j)
				aadblA[i][j] = aadblAIn[i][j];
		}

		double[] adblRHS = Pivot (aadblA, adblB);

		if (null == adblRHS || iSize != adblRHS.length ||
			!org.drip.quant.linearalgebra.LinearSystemSolver.IsDiagonallyDominant (aadblA, true))
			return null;

		for (int i = 0; i < iSize; ++i)
			adblSolution[i] = 0.;

		for (int k = 0; k < NUM_SIM; ++k) {
			for (int i = 0; i < iSize; ++i) {
				adblSolution[i] = adblRHS[i];

				for (int j = 0; j < iSize; ++j) {
					if (j != i) adblSolution[i] -= aadblA[i][j] * adblSolution[j];
				}

				adblSolution[i] /= aadblA[i][i];
			}
		}

		try {
			return new LinearizationOutput (adblSolution, aadblA, "GaussianSeidel");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
