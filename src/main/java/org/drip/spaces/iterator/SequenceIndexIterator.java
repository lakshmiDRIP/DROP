
package org.drip.spaces.iterator;

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
 * <i>SequenceIndexIterator</i> contains the Functionality to iterate through a List of Sequence Indexes.
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

public class SequenceIndexIterator {
	private int[] _aiMax = null;
	private int _iIndexCursor = -1;
	private boolean _bCycle = false;
	private int _iSequenceCursor = -1;

	/**
	 * Create a Standard Sequence/Index Iterator
	 * 
	 * @param iNumSequence Number Variable Sequences
	 * @param iNumIndex Number of Indexes per Variable Sequence
	 * 
	 * @return The Sequence/Index Iterator Instance
	 */

	public static final SequenceIndexIterator Standard (
		final int iNumSequence,
		final int iNumIndex)
	{
		if (0 >= iNumSequence || 0 >= iNumIndex) return null;

		int[] aiMax = new int[iNumSequence];

		for (int i = 0; i < iNumSequence; ++i)
			aiMax[i] = iNumIndex - 1;

		try {
			return new SequenceIndexIterator (aiMax, false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private int[] setFromCursor()
	{
		int iNumSequence = _aiMax.length;
		int[] aiCurrent = new int[iNumSequence];

		for (int i = 0; i < iNumSequence; ++i) {
			if (i < _iSequenceCursor)
				aiCurrent[i] = _aiMax[i];
			else if (i > _iSequenceCursor)
				aiCurrent[i] = 0;
			else
				aiCurrent[i] = _iIndexCursor;
		}

		return aiCurrent;
	}

	/**
	 * IndexIterator Constructor
	 * 
	 * @param aiMax Maximum Entries per Index
	 * @param bCycle TRUE - Cycle around the Index Entries
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are incalid
	 */

	public SequenceIndexIterator (
		final int[] aiMax,
		final boolean bCycle)
		throws java.lang.Exception
	{
		if (null == (_aiMax = aiMax))
			throw new java.lang.Exception ("SequenceIndexIterator ctr => Invalid Inputs");

		_bCycle = bCycle;
		_iIndexCursor = 0;
		_iSequenceCursor = 0;
		int iNumSequence = _aiMax.length;

		if (0 == iNumSequence) throw new java.lang.Exception ("SequenceIndexIterator ctr => Invalid Inputs");

		for (int i = 0; i < iNumSequence; ++i) {
			if (0 > _aiMax[i]) throw new java.lang.Exception ("SequenceIndexIterator ctr => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the First Cursor
	 * 
	 * @return The First Cursor
	 */

	public int[] first()
	{
		_iIndexCursor = 0;
		_iSequenceCursor = 0;

		return setFromCursor();
	}

	/**
	 * Retrieve the Next Cursor
	 * 
	 * @return The Next Cursor
	 */

	public int[] next()
	{
		if (++_iIndexCursor <= _aiMax[_iSequenceCursor]) return setFromCursor();

		_iIndexCursor = 0;

		if (++_iSequenceCursor < _aiMax.length) return setFromCursor();

		return _bCycle ? first() : null;
	}

	/**
	 * Retrieve the Size of the Iterator
	 * 
	 * @return Size of the Iterator
	 */

	public int size()
	{
		int iSize = 0;
		int iNumSequence = _aiMax.length;

		for (int i = 0; i < iNumSequence; ++i)
			iSize += _aiMax[i] + 1;

		return iSize;
	}
}
