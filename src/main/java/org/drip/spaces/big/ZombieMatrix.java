
package org.drip.spaces.big;

import java.util.Set;
import java.util.TreeSet;

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
 * <i>ZombieMatrix</i> implements a Zombie Adjacency Migration. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>ZombieMatrix</i> Constructor</li>
 * 		<li>Retrieve the Uninfected Cell Set</li>
 * 		<li>Retrieve the Row Count</li>
 * 		<li>Retrieve the Column Count</li>
 * 		<li>Compute the Period for Full Infection</li>
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

public class ZombieMatrix
{
	private int _rowCount = -1;
	private int _columnCount = -1;

	private Set<Integer> _uninfectedCellSet = new TreeSet<Integer>();

	private boolean infectable (
		final int cell)
	{
		int left = cell - 1;
		int right = cell + 1;
		int up = cell - _rowCount;
		int down = cell + _rowCount;

		if (0 < up && !_uninfectedCellSet.contains (up)) {
			return true;
		}

		if (_rowCount * _columnCount > down && !_uninfectedCellSet.contains (down)) {
			return true;
		}

		if (0 < left && !_uninfectedCellSet.contains (left)) {
			return true;
		}

		if (_rowCount * _columnCount > right && !_uninfectedCellSet.contains (right)) {
			return true;
		}

		return false;
	}

	/**
	 * <i>ZombieMatrix</i> Constructor
	 * 
	 * @param matrix Zombie Matrix Grid
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ZombieMatrix (
		final boolean[][] matrix)
		throws Exception
	{
		if (null == matrix) {
			throw new Exception ("ZombieMatrix Constructor => Invalid Inputs");
		}

		if (0 == (_rowCount = matrix.length)) {
			throw new Exception ("ZombieMatrix Constructor => Invalid Inputs");
		}

		for (int rowIndex = 0; rowIndex < _rowCount; ++rowIndex) {
			if (-1 == _columnCount) {
				if (0 == (_columnCount = matrix[rowIndex].length)) {
					throw new Exception ("ZombieMatrix Constructor => Invalid Inputs");
				}
			} else if (_columnCount != matrix[rowIndex].length) {
				throw new Exception ("ZombieMatrix Constructor => Invalid Inputs");
			}
		}

		for (int rowIndex = 0; rowIndex < _rowCount; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < _columnCount; ++columnIndex) {
				if (matrix[rowIndex][columnIndex]) {
					_uninfectedCellSet.add (rowIndex * _rowCount + columnIndex);
				}
			}
		}
	}

	/**
	 * Retrieve the Uninfected Cell Set
	 * 
	 * @return The Uninfected Cell Set
	 */

	public Set<Integer> uninfectedCellSet()
	{
		return _uninfectedCellSet;
	}

	/**
	 * Retrieve the Row Count
	 * 
	 * @return The Row Count
	 */

	public int rowCount()
	{
		return _rowCount;
	}

	/**
	 * Retrieve the Column Count
	 * 
	 * @return The Column Count
	 */

	public int columnCount()
	{
		return _columnCount;
	}

	/**
	 * Compute the Period for Full Infection
	 * 
	 * @return The Period for Full Infection
	 */

	public int infectionPeriod()
	{
		int infectionPeriod = 0;

		while (!_uninfectedCellSet.isEmpty()) {
			Set<Integer> uninfectedCellSetUpdate = new TreeSet<Integer>();

			for (int cell : _uninfectedCellSet) {
				if (!infectable (cell)) {
					uninfectedCellSetUpdate.add (cell);
				}
			}

			_uninfectedCellSet = uninfectedCellSetUpdate;
			++infectionPeriod;
		}

		return infectionPeriod;
	}
}
