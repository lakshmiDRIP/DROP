
package org.drip.spaces.big;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * contained inside of the given String.
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

public class SubStringSetExtractor {

	/**
	 * Locate the String Set of the Target Size using a Receeding Permutation Scan
	 * 
	 * @param strMaster The Master String
	 * @param iTargetStringSize The Target String Size
	 * 
	 * @return The List of the Target String
	 */

	public static final java.util.List<java.lang.String> ReceedingPermutationScan (
		final java.lang.String strMaster,
		final int iTargetStringSize)
	{
		int[] aiMax = new int[iTargetStringSize];
		org.drip.spaces.iterator.RdExhaustiveStateSpaceScan mdIter = null;

		int iMasterStringSize = strMaster.length();

		for (int i = 0; i < iTargetStringSize; ++i)
			aiMax[i] = iMasterStringSize;

		try {
			mdIter = new org.drip.spaces.iterator.RdExhaustiveStateSpaceScan (aiMax, false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		java.util.List<java.lang.String> lsTarget = new java.util.ArrayList<java.lang.String>();

		int[] aiIndex = mdIter.stateIndexCursor();

		while (null != aiIndex) {
			lsTarget.add (org.drip.spaces.iterator.IterationHelper.ComposeFromIndex (strMaster, aiIndex));

			aiIndex = mdIter.nextStateIndexCursor();
		}

		return lsTarget;
	}

	/**
	 * Locate the String Set of the Target Size using an Exhaustive Permutation Scan
	 * 
	 * @param strMaster The Master String
	 * @param iTargetStringSize The Target String Size
	 * 
	 * @return The List of the Target String
	 */

	public static final java.util.List<java.lang.String> ExhaustivePermutationScan (
		final java.lang.String strMaster,
		final int iTargetStringSize)
	{
		int[] aiMax = new int[iTargetStringSize];
		org.drip.spaces.iterator.RdExhaustiveStateSpaceScan mdIter = null;

		int iMasterStringSize = strMaster.length();

		for (int i = 0; i < iTargetStringSize; ++i)
			aiMax[i] = iMasterStringSize;

		try {
			mdIter = new org.drip.spaces.iterator.RdExhaustiveStateSpaceScan (aiMax, false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		java.util.List<java.lang.String> lsTarget = new java.util.ArrayList<java.lang.String>();

		int[] aiIndex = mdIter.stateIndexCursor();

		while (null != aiIndex) {
			if (!org.drip.spaces.iterator.IterationHelper.CheckForRepeatingIndex (aiIndex))
				lsTarget.add (org.drip.spaces.iterator.IterationHelper.ComposeFromIndex (strMaster,
					aiIndex));

			aiIndex = mdIter.nextStateIndexCursor();
		}

		return lsTarget;
	}

	/**
	 * Extract all the Contiguous Strings available inside the specified Master String
	 * 
	 * @param strMaster The Master String
	 * 
	 * @return The Full Set of Contiguous Strings
	 */

	public static final java.util.List<java.lang.String> Contiguous (
		final java.lang.String strMaster)
	{
		if (null == strMaster) return null;

		int iMasterStringLength = strMaster.length();

		java.util.List<java.lang.String> lsTarget = new java.util.ArrayList<java.lang.String>();

		if (0 == iMasterStringLength) return lsTarget;

		for (int iStartIndex = 0; iStartIndex < iMasterStringLength; ++iStartIndex) {
			for (int iFinishIndex = iStartIndex + 1; iFinishIndex <= iMasterStringLength; ++iFinishIndex)
				lsTarget.add (strMaster.substring (iStartIndex, iFinishIndex));
		}

		return lsTarget;
	}
}
