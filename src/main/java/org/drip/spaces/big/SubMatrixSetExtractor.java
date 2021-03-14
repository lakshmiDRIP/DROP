
package org.drip.spaces.big;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>SubMatrixSetExtractor</i> contains the Functionality to extract the Set of the Sub-matrices contained
 * inside of the given Matrix.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big/README.md">Big-data In-place Manipulator</a></li>
 *  </ul>
 * <br><br>
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (aadbl[i][j]))
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
