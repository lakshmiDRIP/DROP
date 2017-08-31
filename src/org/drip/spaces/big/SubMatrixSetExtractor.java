
package org.drip.spaces.big;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * SubMatrixSetExtractor contains the Functionality to extract the Set of the Sub-matrices contained inside
 *  of the given Matrix.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SubMatrixSetExtractor {

	/**
	 * Compute the Aggregate Composite Value of the Supplied Matrix
	 *  
	 * @param aadbl The Input Matrix
	 * 
	 * @return The Aggregate Composite Value
	 * 
	 * @throws java.lang.Exception Thrown if the Aggregate Composite Value cannot be compouted
	 */

	public static final double CompositeValue (
		final double[][] aadbl)
		throws java.lang.Exception
	{
		if (null == aadbl)
			throw new java.lang.Exception ("SubMatrixSetExtractor::CompositeValue => Invalid Inputs");

		int iSize = aadbl.length;
		double dblCompositeValue = 0.;

		if (0 == iSize || 0 == aadbl[0].length)
			throw new java.lang.Exception ("SubMatrixSetExtractor::CompositeValue => Invalid Inputs");

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				if (!org.drip.quant.common.NumberUtil.IsValid (aadbl[i][j]))
					throw new java.lang.Exception
						("SubMatrixSetExtractor::CompositeValue => Invalid Inputs");

				dblCompositeValue += aadbl[i][j];
			}
		}

		return dblCompositeValue;
	}

	/**
	 * Generate the List of all the sub-matrices contained within a specified Square Matrix starting from the
	 * 	given Row and Column
	 * 
	 * @param aadblMaster The Master Square Matrix
	 * @param iStartRow The Starting Row
	 * @param iStartColumn The Starting Column
	 * 
	 * @return The List of all the sub-matrices
	 */

	public static final java.util.List<double[][]> SquareSubMatrixList (
		final double[][] aadblMaster,
		final int iStartRow,
		final int iStartColumn)
	{
		if (null == aadblMaster) return null;

		int iMasterSize = aadblMaster.length;
		int iMaxSubMatrixSize = iMasterSize - (iStartColumn > iStartRow ? iStartColumn : iStartRow);

		if (0 == iMasterSize || 0 == aadblMaster[0].length || 0 == iMaxSubMatrixSize) return null;

		java.util.List<double[][]> lsSubMatrix = new java.util.ArrayList<double[][]>();

		for (int iSubMatrixSize = 1; iSubMatrixSize <= iMaxSubMatrixSize; ++iSubMatrixSize) {
			double[][] aadblSubMatrix = new double[iSubMatrixSize][iSubMatrixSize];

			for (int i = iStartRow; i < iStartRow + iSubMatrixSize; ++i) {
				for (int j = iStartColumn; j < iStartColumn + iSubMatrixSize; ++j)
					aadblSubMatrix[i - iStartRow][j - iStartColumn] = aadblMaster[i][j];
			}

			lsSubMatrix.add (aadblSubMatrix);
		}

		return lsSubMatrix;
	}

	/**
	 * Compute the Maximum Composite Value of all the sub-matrices contained within a specified Square Matrix
	 *  starting from the given Row and Column
	 * 
	 * @param aadblMaster The Master Square Matrix
	 * @param iStartRow The Starting Row
	 * @param iStartColumn The Starting Column
	 * 
	 * @return The List of all the sub-matrices
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double MaxCompositeSubMatrix (
		final double[][] aadblMaster,
		final int iStartRow,
		final int iStartColumn)
		throws java.lang.Exception
	{
		java.util.List<double[][]> lsSubMatrix = SquareSubMatrixList (aadblMaster, iStartRow, iStartColumn);

		if (null == lsSubMatrix || 0 == lsSubMatrix.size())
			throw new java.lang.Exception ("SubMatrixSetExtractor::MaxCompositeSubMatrix => Invalid Inputs");

		double dblMaxCompositeSubMatrix = java.lang.Double.NEGATIVE_INFINITY;

		for (double[][] aadblSubMatrix : lsSubMatrix) {
			double dblCompositeSubMatrix = CompositeValue (aadblSubMatrix);

			if (dblMaxCompositeSubMatrix < dblCompositeSubMatrix)
				dblMaxCompositeSubMatrix = dblCompositeSubMatrix;
		}

		return dblMaxCompositeSubMatrix;
	}

	/**
	 * Use the "Lean" Method to compute the Maximum Composite Value of all the sub-matrices contained within
	 *  a specified Square Matrix starting from the given Row and Column
	 * 
	 * @param aadblMaster The Master Square Matrix
	 * @param iStartRow The Starting Row
	 * @param iStartColumn The Starting Column
	 * 
	 * @return The List of all the sub-matrices
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double LeanMaxCompositeSubMatrix (
		final double[][] aadblMaster,
		final int iStartRow,
		final int iStartColumn)
		throws java.lang.Exception
	{
		if (null == aadblMaster)
			throw new java.lang.Exception
				("SubMatrixSetExtractor::LeanMaxCompositeSubMatrix => Invalid Inputs");

		double dblCompositeSubMatrix = 0.;
		int iMasterSize = aadblMaster.length;
		double dblMaxCompositeSubMatrix = java.lang.Double.NEGATIVE_INFINITY;
		int iMaxSubMatrixSize = iMasterSize - (iStartColumn > iStartRow ? iStartColumn : iStartRow);

		if (0 == iMasterSize || 0 == aadblMaster[0].length || 0 == iMaxSubMatrixSize)
			throw new java.lang.Exception
				("SubMatrixSetExtractor::LeanMaxCompositeSubMatrix => Invalid Inputs");

		for (int iSubMatrixSize = 1; iSubMatrixSize <= iMaxSubMatrixSize; ++iSubMatrixSize) {
			for (int iRow = iStartRow; iRow < iStartRow + iSubMatrixSize - 2; ++iRow)
				dblCompositeSubMatrix += aadblMaster[iRow][iStartColumn + iSubMatrixSize - 2];

			for (int iColumn = iStartColumn; iColumn < iStartColumn + iSubMatrixSize - 2; ++iColumn)
				dblCompositeSubMatrix += aadblMaster[iStartRow + iSubMatrixSize - 2][iColumn];

			dblCompositeSubMatrix +=
				aadblMaster[iStartRow + iSubMatrixSize - 1][iStartColumn + iSubMatrixSize - 1];

			if (dblMaxCompositeSubMatrix < dblCompositeSubMatrix)
				dblMaxCompositeSubMatrix = dblCompositeSubMatrix;
		}

		return dblMaxCompositeSubMatrix;
	}
}
