
package org.drip.spaces.big;

import java.util.ArrayList;
import java.util.List;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * 	inside of the given Matrix. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Compute the Aggregate Composite Value of the Supplied Matrix</li>
 * 		<li>Generate the List of all the sub-matrices contained within a specified Square Matrix starting from the given Row and Column</li>
 * 		<li>Compute the Maximum Composite Value of all the sub-matrices contained within a specified Square Matrix starting from the given Row and Column</li>
 * 		<li>Use the "Lean" Method to compute the Maximum Composite Value of all the sub-matrices contained within a specified Square Matrix starting from the given Row and Column</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big/README.md">Big-data In-place Manipulator</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SubMatrixSetExtractor
{

	/**
	 * Compute the Aggregate Composite Value of the Supplied Matrix
	 *  
	 * @param matrix The Input Matrix
	 * 
	 * @return The Aggregate Composite Value
	 * 
	 * @throws Exception Thrown if the Aggregate Composite Value cannot be computed
	 */

	public static final double CompositeValue (
		final double[][] matrix)
		throws Exception
	{
		if (null == matrix) {
			throw new Exception ("SubMatrixSetExtractor::CompositeValue => Invalid Inputs");
		}

		int size = matrix.length;
		double compositeValue = 0.;

		if (0 == size || 0 == matrix[0].length) {
			throw new Exception ("SubMatrixSetExtractor::CompositeValue => Invalid Inputs");
		}

		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				if (!NumberUtil.IsValid (matrix[i][j])) {
					throw new Exception ("SubMatrixSetExtractor::CompositeValue => Invalid Inputs");
				}

				compositeValue += matrix[i][j];
			}
		}

		return compositeValue;
	}

	/**
	 * Generate the List of all the sub-matrices contained within a specified Square Matrix starting from the
	 * 	given Row and Column
	 * 
	 * @param masterMatrix The Master Square Matrix
	 * @param startRow The Starting Row
	 * @param startColumn The Starting Column
	 * 
	 * @return The List of all the sub-matrices
	 */

	public static final List<double[][]> SquareSubMatrixList (
		final double[][] masterMatrix,
		final int startRow,
		final int startColumn)
	{
		if (null == masterMatrix) {
			return null;
		}

		int masterSize = masterMatrix.length;
		int maxSubMatrixSize = masterSize - (startColumn > startRow ? startColumn : startRow);

		if (0 == masterSize || 0 == masterMatrix[0].length || 0 == maxSubMatrixSize) {
			return null;
		}

		List<double[][]> subMatrixList = new ArrayList<double[][]>();

		for (int subMatrixSize = 1; subMatrixSize <= maxSubMatrixSize; ++subMatrixSize) {
			double[][] subMatrix = new double[subMatrixSize][subMatrixSize];

			for (int i = startRow; i < startRow + subMatrixSize; ++i) {
				for (int j = startColumn; j < startColumn + subMatrixSize; ++j) {
					subMatrix[i - startRow][j - startColumn] = masterMatrix[i][j];
				}
			}

			subMatrixList.add (subMatrix);
		}

		return subMatrixList;
	}

	/**
	 * Compute the Maximum Composite Value of all the sub-matrices contained within a specified Square Matrix
	 *  starting from the given Row and Column
	 * 
	 * @param masterMatrix The Master Square Matrix
	 * @param startRow The Starting Row
	 * @param startColumn The Starting Column
	 * 
	 * @return Maximum of the Composite sub-matrices
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double MaxCompositeSubMatrix (
		final double[][] masterMatrix,
		final int startRow,
		final int startColumn)
		throws Exception
	{
		List<double[][]> subMatrixList = SquareSubMatrixList (masterMatrix, startRow, startColumn);

		if (null == subMatrixList || 0 == subMatrixList.size()) {
			throw new Exception ("SubMatrixSetExtractor::MaxCompositeSubMatrix => Invalid Inputs");
		}

		double maxCompositeSubMatrix = Double.NEGATIVE_INFINITY;

		for (double[][] subMatrix : subMatrixList) {
			double compositeSubMatrixValue = CompositeValue (subMatrix);

			if (maxCompositeSubMatrix < compositeSubMatrixValue) {
				maxCompositeSubMatrix = compositeSubMatrixValue;
			}
		}

		return maxCompositeSubMatrix;
	}

	/**
	 * Use the "Lean" Method to compute the Maximum Composite Value of all the sub-matrices contained within
	 *  a specified Square Matrix starting from the given Row and Column
	 * 
	 * @param masterMatrix The Master Square Matrix
	 * @param startRow The Starting Row
	 * @param startColumn The Starting Column
	 * 
	 * @return Maximum of the Composite sub-matrices
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double LeanMaxCompositeSubMatrix (
		final double[][] masterMatrix,
		final int startRow,
		final int startColumn)
		throws Exception
	{
		if (null == masterMatrix) {
			throw new Exception ("SubMatrixSetExtractor::LeanMaxCompositeSubMatrix => Invalid Inputs");
		}

		double compositeSubMatrix = 0.;
		int masterSize = masterMatrix.length;
		double maxCompositeSubMatrix = Double.NEGATIVE_INFINITY;
		int maxSubMatrixSize = masterSize - (startColumn > startRow ? startColumn : startRow);

		if (0 == masterSize || 0 == masterMatrix[0].length || 0 == maxSubMatrixSize) {
			throw new Exception ("SubMatrixSetExtractor::LeanMaxCompositeSubMatrix => Invalid Inputs");
		}

		for (int subMatrixSize = 1; subMatrixSize <= maxSubMatrixSize; ++subMatrixSize) {
			for (int row = startRow; row < startRow + subMatrixSize - 2; ++row) {
				compositeSubMatrix += masterMatrix[row][startColumn + subMatrixSize - 2];
			}

			for (int column = startColumn; column < startColumn + subMatrixSize - 2; ++column) {
				compositeSubMatrix += masterMatrix[startRow + subMatrixSize - 2][column];
			}

			compositeSubMatrix +=
				masterMatrix[startRow + subMatrixSize - 1][startColumn + subMatrixSize - 1];

			if (maxCompositeSubMatrix < compositeSubMatrix) {
				maxCompositeSubMatrix = compositeSubMatrix;
			}
		}

		return maxCompositeSubMatrix;
	}
}
