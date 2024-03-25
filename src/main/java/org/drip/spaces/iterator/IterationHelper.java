
package org.drip.spaces.iterator;

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
 * <i>IterationHelper</i> contains the Functionality that helps perform Checked Multidimensional Iterative
 * 	Scans.
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Scan through the Integer Array looking for a repeating Index</li>
 * 		<li>Display the Contents of the Index Array</li>
 * 		<li>Compose a String constructed from the specified Array Index</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/iterator/README.md">Iterative/Exhaustive Vector Space Scanners</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IterationHelper
{

	/**
	 * Scan through the Integer Array looking for a repeating Index
	 * 
	 * @param indexArray The Index Array
	 * 
	 * @return TRUE - A Repeating Index exists
	 */

	public static final boolean CheckForRepeatingIndex (
		final int[] indexArray)
	{
		if (null == indexArray) {
			return false;
		}

		int cursorLength = indexArray.length;

		if (1 >= cursorLength) {
			return false;
		}

		for (int i = 0; i < cursorLength; ++i) {
			for (int j = i + 1; j < cursorLength; ++j) {
				if (indexArray[i] == indexArray[j]) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Display the Contents of the Index Array
	 * 
	 * @param prefix The Dump Prefix
	 * @param indexArray The Index Array
	 */

	public static final void DumpIndexArray (
		final String prefix,
		final int[] indexArray)
	{
		if (null == indexArray) {
			return;
		}

		String indexArrayString = prefix;
		int indexCount = indexArray.length;

		if (0 >= indexCount) {
			return;
		}

		for (int i = 0; i < indexCount; ++i) {
			indexArrayString += (0 == i ? "[" : ",") + indexArray[i];
		}

		System.out.println (indexArrayString + "]");
	}

	/**
	 * Compose a String constructed from the specified Array Index
	 * 
	 * @param master The Master String
	 * @param indexArray The Index Array
	 * 
	 * @return The Composed String
	 */

	public static final String ComposeFromIndex (
		final String master,
		final int[] indexArray)
	{
		if (null == indexArray) {
			return null;
		}

		String compositionFromIndex = "";
		int indexCount = indexArray.length;

		if (0 >= indexCount) {
			return null;
		}

		for (int i = 0; i < indexCount; ++i) {
			compositionFromIndex += master.charAt (indexArray[i]);
		}

		return compositionFromIndex;
	}
}
