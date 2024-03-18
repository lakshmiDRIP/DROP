
package org.drip.spaces.big;

import java.util.ArrayList;
import java.util.List;

import org.drip.spaces.iterator.IterationHelper;
import org.drip.spaces.iterator.RdExhaustiveStateSpaceScan;

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
 * <i>SubStringSetExtractor</i> contains the Functionality to extract the Full Suite of the Sub-strings
 * 	contained inside of the given String. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Locate the String Set of the Target Size using a Receding Permutation Scan</li>
 * 		<li>Locate the String Set of the Target Size using an Exhaustive Permutation Scan</li>
 * 		<li>Extract all the Contiguous Strings available inside the specified Master String</li>
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

public class SubStringSetExtractor
{

	/**
	 * Locate the String Set of the Target Size using a Receding Permutation Scan
	 * 
	 * @param master The Master String
	 * @param targetStringSize The Target String Size
	 * 
	 * @return The List of the Target String
	 */

	public static final List<String> ReceedingPermutationScan (
		final String master,
		final int targetStringSize)
	{
		int[] maxSizeArray = new int[targetStringSize];
		RdExhaustiveStateSpaceScan rdExhaustiveStateSpaceScanIterator = null;

		int masterStringSize = master.length();

		for (int i = 0; i < targetStringSize; ++i) {
			maxSizeArray[i] = masterStringSize;
		}

		try {
			rdExhaustiveStateSpaceScanIterator = new RdExhaustiveStateSpaceScan (maxSizeArray, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> targetList = new ArrayList<String>();

		int[] indexCursorArray = rdExhaustiveStateSpaceScanIterator.stateIndexCursor();

		while (null != indexCursorArray) {
			targetList.add (IterationHelper.ComposeFromIndex (master, indexCursorArray));

			indexCursorArray = rdExhaustiveStateSpaceScanIterator.nextStateIndexCursor();
		}

		return targetList;
	}

	/**
	 * Locate the String Set of the Target Size using an Exhaustive Permutation Scan
	 * 
	 * @param master The Master String
	 * @param targetStringSize The Target String Size
	 * 
	 * @return The List of the Target String
	 */

	public static final List<String> ExhaustivePermutationScan (
		final String master,
		final int targetStringSize)
	{
		int[] maxSizeArray = new int[targetStringSize];
		RdExhaustiveStateSpaceScan rdExhaustiveStateSpaceScanIterator = null;

		int masterStringSize = master.length();

		for (int i = 0; i < targetStringSize; ++i) {
			maxSizeArray[i] = masterStringSize;
		}

		try {
			rdExhaustiveStateSpaceScanIterator = new RdExhaustiveStateSpaceScan (maxSizeArray, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> targetList = new ArrayList<String>();

		int[] indexCursorArray = rdExhaustiveStateSpaceScanIterator.stateIndexCursor();

		while (null != indexCursorArray) {
			if (!IterationHelper.CheckForRepeatingIndex (indexCursorArray)) {
				targetList.add (IterationHelper.ComposeFromIndex (master, indexCursorArray));
			}

			indexCursorArray = rdExhaustiveStateSpaceScanIterator.nextStateIndexCursor();
		}

		return targetList;
	}

	/**
	 * Extract all the Contiguous Strings available inside the specified Master String
	 * 
	 * @param master The Master String
	 * 
	 * @return The Full List of Contiguous Strings
	 */

	public static final List<String> Contiguous (
		final String master)
	{
		if (null == master) {
			return null;
		}

		int masterStringLength = master.length();

		List<String> targetList = new ArrayList<String>();

		if (0 == masterStringLength) {
			return targetList;
		}

		for (int startIndex = 0; startIndex < masterStringLength; ++startIndex) {
			for (int finishIndex = startIndex + 1; finishIndex <= masterStringLength; ++finishIndex) {
				targetList.add (master.substring (startIndex, finishIndex));
			}
		}

		return targetList;
	}
}
