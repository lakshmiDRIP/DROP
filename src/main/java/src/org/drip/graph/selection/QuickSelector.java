
package org.drip.graph.selection;

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
 * <i>QuickSelector</i> implements the Hoare's QuickSelect Algorithm. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Eppstein, D. (2007): Blum-style Analysis of Quickselect
 *  			https://11011110.github.io/blog/2007/10/09/blum-style-analysis-of.html
 *  	</li>
 *  	<li>
 *  		Hoare, C. A. R. (1961): Algorithm 65: Find <i>Communications of the ACM</i> <b>4 (1)</b> 321-322
 *  	</li>
 *  	<li>
 *  		Knuth, D. (1997): <i>The Art of Computer Programming 3<sup>rd</sup> Edition</i>
 *  			<b>Addison-Wesley</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Quickselect https://en.wikipedia.org/wiki/Quickselect
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Selection Algorithm https://en.wikipedia.org/wiki/Selection_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/README.md">k<sup>th</sup> Order Statistics Selection Scheme</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class QuickSelector<K extends java.lang.Comparable<K>>
	extends org.drip.graph.selection.OrderStatisticSelector<K>
{
	private boolean _tailCallOptimizationOn = false;
	private org.drip.graph.selection.IntroselectControl _introselectControl = null;

	protected int pivotIndex (
		final int leftIndex,
		final int rightIndex)
		throws java.lang.Exception
	{
		return leftIndex + (int) (java.lang.Math.random() * (rightIndex - leftIndex + 1));
	}

	protected int recursiveIndexSelect (
		int leftIndex,
		int rightIndex,
		final int k)
		throws java.lang.Exception
	{
		K[] elementArray = elementArray();

		int arraySize = elementArray.length;

		if (leftIndex < 0 ||
			rightIndex >= arraySize || rightIndex < leftIndex ||
			0 > k || k >= arraySize
		)
		{
			throw new java.lang.Exception (
				"QuickSelector::recursiveIndexSelect => Invalid Inputs"
			);
		}

		if (leftIndex == rightIndex)
		{
			return leftIndex;
		}

		int pivotIndex = partition (
			leftIndex,
			rightIndex,
			pivotIndex (
				leftIndex,
				rightIndex
			)
		);

		if (k == pivotIndex)
		{
			return k;
		}

		if (k < pivotIndex)
		{
			rightIndex = pivotIndex - 1;
		}
		else
		{
			leftIndex = pivotIndex + 1;
		}

		if (null != _introselectControl)
		{
			if (!_introselectControl.progressCheck (
				rightIndex - leftIndex + 1
			))
			{
				return -1;
			}
		}

		return recursiveIndexSelect (
			leftIndex,
			rightIndex,
			k
		);
	}

	protected int iterativeIndexSelect (
		int leftIndex,
		int rightIndex,
		final int k)
		throws java.lang.Exception
	{
		K[] elementArray = elementArray();

		int arraySize = elementArray.length;

		if (leftIndex < 0 ||
			rightIndex >= arraySize || rightIndex < leftIndex ||
			0 > k || k >= arraySize
		)
		{
			throw new java.lang.Exception (
				"QuickSelector::iterativeIndexSelect => Invalid Inputs"
			);
		}

		while (true)
		{
			if (leftIndex == rightIndex)
			{
				return leftIndex;
			}

			int pivotIndex = partition (
				leftIndex,
				rightIndex,
				pivotIndex (
					leftIndex,
					rightIndex
				)
			);

			if (k == pivotIndex)
			{
				return k;
			}

			if (k < pivotIndex)
			{
				rightIndex = pivotIndex - 1;
			}
			else
			{
				leftIndex = pivotIndex + 1;
			}

			if (null != _introselectControl)
			{
				if (!_introselectControl.progressCheck (
					rightIndex - leftIndex + 1
				))
				{
					return -1;
				}
			}
		}
	}

	/**
	 * QuickSelector Constructor
	 * 
	 * @param elementArray Array of Elements
	 * @param tailCallOptimizationOn TRUE - Tail Call Optimization is Turned On
	 * @param introselectControl The Introselect Control
	 * 
	 * @throws java.lang.Exception Thrown if the Input is Invalid
	 */

	public QuickSelector (
		final K[] elementArray,
		final boolean tailCallOptimizationOn,
		final org.drip.graph.selection.IntroselectControl introselectControl)
		throws java.lang.Exception
	{
		super (
			elementArray
		);

		_introselectControl = introselectControl;
		_tailCallOptimizationOn = tailCallOptimizationOn;
	}

	/**
	 * Retrieve the Tail Call Optimization Status
	 * 
	 * @return TRUE - Tail Call Optimization is Turned On
	 */

	public boolean tailCallOptimizationOn()
	{
		return _tailCallOptimizationOn;
	}

	/**
	 * Retrieve the Introselect Control
	 * 
	 * @return The Introselect Control
	 */

	public org.drip.graph.selection.IntroselectControl introselectControl()
	{
		return _introselectControl;
	}

	/**
	 * Partition the Array into Elements Lower and Higher than the Pivot Value inside of the Index Range
	 * 
	 * @param leftIndex Range Left Index
	 * @param rightIndex Range Right Index
	 * @param pivotIndex Pivot Index
	 * 
	 * @return The Index corresponding to the Partitioned Array
	 * 
	 * @throws java.lang.Exception Thrown if the Partition cannot be computed
	 */

	public int partition (
		final int leftIndex,
		final int rightIndex,
		final int pivotIndex)
		throws java.lang.Exception
	{
		K[] elementArray = elementArray();

		int arraySize = elementArray.length;

		if (leftIndex < 0 ||
			rightIndex >= arraySize || rightIndex < leftIndex ||
			pivotIndex < leftIndex || pivotIndex > rightIndex
		)
		{
			throw new java.lang.Exception (
				"QuickSelector::partition => Invalid Inputs"
			);
		}

		K pivotValue = elementArray[pivotIndex];

		if (!swapLocations (
			pivotIndex,
			rightIndex
		))
		{
			throw new java.lang.Exception (
				"QuickSelector::partition => Cannot Swap Locations"
			);
		}

		int index = leftIndex;
		int storeIndex = leftIndex;

		while (index < rightIndex)
		{
			if (-1 == elementArray[index].compareTo (
				pivotValue
			))
			{
				if (!swapLocations (
					storeIndex,
					index
				))
				{
					throw new java.lang.Exception (
						"QuickSelector::partition => Cannot Swap Locations"
					);
				}

				++storeIndex;
			}

			++index;
		}

		while (index < rightIndex)
		{
			if (-1 == elementArray[index].compareTo (
				pivotValue
			))
			{
				if (!swapLocations (
					storeIndex,
					index
				))
				{
					throw new java.lang.Exception (
						"QuickSelector::partition => Cannot Swap Locations"
					);
				}

				++storeIndex;
			}

			++index;
		}

		if (!swapLocations (
			rightIndex,
			storeIndex
		))
		{
			throw new java.lang.Exception (
				"QuickSelector::partition => Cannot Swap Locations"
			);
		}

		return storeIndex;
	}

	/**
	 * Select the Index corresponding the k<sup>th</sup> Order Statistic on the Array
	 * 
	 * @param leftIndex Range Left Index
	 * @param rightIndex Range Right Index
	 * @param k The Order Statistic
	 * 
	 * @return Index corresponding the k<sup>th</sup> Order Statistic
	 * 
	 * @throws java.lang.Exception Thrown if the Index cannot be Selected
	 */

	public int selectIndex (
		final int leftIndex,
		final int rightIndex,
		final int k)
		throws java.lang.Exception
	{
		if (null != _introselectControl)
		{
			if (!_introselectControl.init (
				rightIndex - leftIndex + 1
			))
			{
				throw new java.lang.Exception (
					"QuickSelector::iterativeIndexSelect => Cannot initialize Introselect Control"
				);
			}
		};

		return _tailCallOptimizationOn ? recursiveIndexSelect (
			leftIndex,
			rightIndex,
			k
		) : iterativeIndexSelect (
			leftIndex,
			rightIndex,
			k
		);
	}

	/**
	 * Perform a Selection for the k<sup>th</sup> Order Statistic on the Array
	 * 
	 * @param leftIndex Range Left Index
	 * @param rightIndex Range Right Index
	 * @param k The Order Statistic
	 * 
	 * @return The k<sup>th</sup> Order Statistic
	 * 
	 * @throws java.lang.Exception Thrown if the Selection cannot be performed
	 */

	public K select (
		final int leftIndex,
		final int rightIndex,
		final int k)
		throws java.lang.Exception
	{
		int selectIndex = selectIndex (
			leftIndex,
			rightIndex,
			k
		);

		if (-1 == selectIndex)
		{
			throw new java.lang.Exception (
				"QuickSelector::select => Invalid Selected Index"
			);
		}

		return elementArray()[selectIndex];
	}

	@Override public K select (
		final int k)
	{
		try
		{
			return select (
				0,
				elementArray().length - 1,
				k
			);
		}
		catch (java.lang.Exception e)
		{
			// e.printStackTrace();
		}

		return null;
	}
}
