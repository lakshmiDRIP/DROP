
package org.drip.spaces.iterator;

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
 * <i>RdSpanningStateSpaceScan</i> is the Abstract Iterator Class that contains the Functionality to perform
 * a Spanning Iterative Scan through an R<sup>d</sup> State Space.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/iterator/README.md">Iterative/Exhaustive Vector Space Scanners</a></li>
 *  </ul>
 * <br><br>
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
