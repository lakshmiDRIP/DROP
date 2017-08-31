
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
 * SubStringSetExtractor contains the Functionality to extract the Full Suite of the Sub-strings contained
 * 	inside of the given String.
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
