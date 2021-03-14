
package org.drip.spaces.big;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>ZombieMatrix</i> implements a Zombie Adjacency Migration.
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

public class ZombieMatrix
{
	private int _rowCount = -1;
	private int _columnCount = -1;

	private java.util.Set<java.lang.Integer> _uninfectedCellSet = new java.util.TreeSet<java.lang.Integer>();

	private boolean infectable (
		final int cell)
	{
		int left = cell - 1;
		int right = cell + 1;
		int up = cell - _rowCount;
		int down = cell + _rowCount;

		if (0 < up && !_uninfectedCellSet.contains (
			up
		))
		{
			return true;
		}

		if (_rowCount * _columnCount > down && !_uninfectedCellSet.contains (
			down
		))
		{
			return true;
		}

		if (0 < left && !_uninfectedCellSet.contains (
			left
		))
		{
			return true;
		}

		if (_rowCount * _columnCount > right && !_uninfectedCellSet.contains (
			right
		))
		{
			return true;
		}

		return false;
	}

	/**
	 * ZombieMatrix Constructor
	 * 
	 * @param a Zombie Matrix Grid
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ZombieMatrix (
		final boolean[][] a)
		throws java.lang.Exception
	{
		if (null == a)
		{
			throw new java.lang.Exception (
				"ZombieMatrix Constructor => Invalid Inputs"
			);
		}

		if (0 == (_rowCount = a.length))
		{
			throw new java.lang.Exception (
				"ZombieMatrix Constructor => Invalid Inputs"
			);
		}

		for (int rowIndex = 0;
			rowIndex < _rowCount;
			++rowIndex)
		{
			if (-1 == _columnCount)
			{
				if (0 == (_columnCount = a[rowIndex].length))
				{
					throw new java.lang.Exception (
						"ZombieMatrix Constructor => Invalid Inputs"
					);
				}
			}
			else if (_columnCount != a[rowIndex].length)
			{
				throw new java.lang.Exception (
					"ZombieMatrix Constructor => Invalid Inputs"
				);
			}
		}

		for (int rowIndex = 0;
			rowIndex < _rowCount;
			++rowIndex)
		{
			for (int columnIndex = 0;
				columnIndex < _columnCount;
				++columnIndex)
			{
				if (a[rowIndex][columnIndex])
				{
					_uninfectedCellSet.add (
						rowIndex * _rowCount + columnIndex
					);
				}
			}
		}
	}

	/**
	 * Retrieve the Uninfected Cell Set
	 * 
	 * @return The Uninfected Cell Set
	 */

	public java.util.Set<java.lang.Integer> uninfectedCellSet()
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

		while (!_uninfectedCellSet.isEmpty())
		{
			java.util.Set<java.lang.Integer> uninfectedCellSetUpdate =
				new java.util.TreeSet<java.lang.Integer>();

			for (int cell : _uninfectedCellSet)
			{
				if (!infectable (
					cell
				))
				{
					uninfectedCellSetUpdate.add (
						cell
					);
				}
			}

			_uninfectedCellSet = uninfectedCellSetUpdate;
			++infectionPeriod;
		}

		return infectionPeriod;
	}
}
