
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
 * RdSpanningStateSpaceScan is the Abstract Iterator Class that contains the Functionality to perform a
 *  Spanning Iterative Scan through an R^d State Space.
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class RdSpanningStateSpaceScan {
	private boolean _bCyclicalScan = false;
	private int[] _aiStateIndexCursor = null;
	private int[] _aiTerminalStateIndex = null;

	protected RdSpanningStateSpaceScan (
		final int[] aiTerminalStateIndex,
		final boolean bCyclicalScan)
		throws java.lang.Exception
	{
		if (null == aiTerminalStateIndex)
			throw new java.lang.Exception ("RdSpanningStateSpaceScan ctr: Invalid Input");

		int iDimension = aiTerminalStateIndex.length;
		_aiTerminalStateIndex = new int[iDimension];
		_aiStateIndexCursor = new int[iDimension];
		_bCyclicalScan = bCyclicalScan;

		if (0 == iDimension)
			throw new java.lang.Exception ("RdSpanningStateSpaceScan ctr: Invalid Input");

		for (int i = 0; i < iDimension; ++i) {
			if (0 >= (_aiTerminalStateIndex[i] = aiTerminalStateIndex[i]))
				throw new java.lang.Exception ("RdSpanningStateSpaceScan ctr: Invalid Input");

			_aiStateIndexCursor[i] = 0;
		}
	}

	protected boolean setStateIndexCursor (
		final int[] aiStateIndexCursor)
	{
		if (null == _aiStateIndexCursor || _aiStateIndexCursor.length != _aiTerminalStateIndex.length)
			return false;

		_aiStateIndexCursor = aiStateIndexCursor;
		return true;
	}

	/**
	 * Retrieve the Array of the Terminal State Indexes
	 * 
	 * @return The Array of the Terminal State Indexes
	 */

	public int[] terminalStateIndex()
	{
		return _aiTerminalStateIndex;
	}

	/**
	 * Retrieve the Dimension
	 * 
	 * @return The Dimension
	 */

	public int dimension()
	{
		return _aiTerminalStateIndex.length;
	}

	/**
	 * Retrieve the State Index Cursor
	 * 
	 * @return The State Index Cursor
	 */

	public int[] stateIndexCursor()
	{
		return _aiStateIndexCursor;
	}

	/**
	 * Retrieve the Cyclical Scan Flag
	 * 
	 * @return The Cyclical Scan Flag
	 */

	public boolean cyclicalScan()
	{
		return _bCyclicalScan;
	}

	/**
	 * Reset and retrieve the State Index Cursor
	 * 
	 * @return The Reset State Index Cursor
	 */

	public abstract int[] resetStateIndexCursor();

	/**
	 * Move to the Subsequent Index Cursor
	 * 
	 * @return The Subsequent Index Cursor
	 */

	public abstract int[] nextStateIndexCursor();
}
