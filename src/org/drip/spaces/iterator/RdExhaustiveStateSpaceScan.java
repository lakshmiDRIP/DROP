
package org.drip.spaces.iterator;

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
 * RdExhaustiveStateSpaceScan contains the Functionality to iterate exhaustively through the R^d Space.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RdExhaustiveStateSpaceScan extends org.drip.spaces.iterator.RdSpanningStateSpaceScan {

	/**
	 * RdExhaustiveStateSpaceScan Constructor
	 * 
	 * @param aiTerminalStateIndex Upper Array Bounds for each Dimension
	 * @param bCyclicalScan TRUE - Cycle Post a Full Scan
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdExhaustiveStateSpaceScan (
		final int[] aiTerminalStateIndex,
		final boolean bCyclicalScan)
		throws java.lang.Exception
	{
		super (aiTerminalStateIndex, bCyclicalScan);
	}

	@Override public int[] resetStateIndexCursor()
	{
		int iDimension = dimension();

		int[] aiStateIndexCursor = stateIndexCursor();

		for (int i = 0; i < iDimension; ++i)
			aiStateIndexCursor[i] = 0;

		return setStateIndexCursor (aiStateIndexCursor) ? aiStateIndexCursor : null;
	}

	@Override public int[] nextStateIndexCursor()
	{
		int iDimension = dimension();

		int iStateIndexToUpdate = -1;

		int[] aiStateIndexCursor = stateIndexCursor();

		int[] aiTerminalStateIndex = terminalStateIndex();

		for (int i = iDimension - 1; i >= 0; --i) {
			if (aiStateIndexCursor[i] != aiTerminalStateIndex[i] - 1) {
				iStateIndexToUpdate = i;
				break;
			}
		}

		if (-1 == iStateIndexToUpdate) return cyclicalScan() ? resetStateIndexCursor() : null;

		aiStateIndexCursor[iStateIndexToUpdate] = aiStateIndexCursor[iStateIndexToUpdate] + 1;

		for (int i = iStateIndexToUpdate + 1; i < iDimension; ++i)
			aiStateIndexCursor[i] = 0;

		return setStateIndexCursor (aiStateIndexCursor) ? aiStateIndexCursor : null;
	}
}
