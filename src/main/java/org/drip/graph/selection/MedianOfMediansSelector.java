
package org.drip.graph.selection;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>MedianOfMediansSelector</i> implements the QuickSelect Algorithm using the Median-of-Medians Pivot
 * 	Generation Strategy. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Blum, M., R. W. Floyd, V. Pratt, R. L. Rivest, and R. E. Tarjan (1973): Time Bounds for Selection
 *  			<i>Journal of Computer and System Sciences</i> <b>7 (4)</b> 448-461
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Hoare, C. A. R. (1961): Algorithm 65: Find <i>Communications of the ACM</i> <b>4 (1)</b> 321-322
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Quickselect https://en.wikipedia.org/wiki/Quickselect
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Median Of Medians https://en.wikipedia.org/wiki/Median_of_medians
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

public class MedianOfMediansSelector<K extends java.lang.Comparable<K>>
	extends org.drip.graph.selection.QuickSelector<K>
{
	private int _groupElementCount = -1;

	private int groupMedianIndex (
		final int leftIndex,
		final int rightIndex)
		throws java.lang.Exception
	{
		K[] elementArray = elementArray();

		int indexI = leftIndex + 1;

		while (indexI <= rightIndex)
		{
			int indexJ = indexI;

			while (indexJ > leftIndex &&
				-1 == elementArray[indexJ - 1].compareTo (
					elementArray[indexJ]
				)
			)
			{
				if (!swapLocations (
					indexJ - 1,
					indexJ
				))
				{
					throw new java.lang.Exception (
						"MedianOfMediansSelector::groupMedianIndex => Cannot Swap Locations"
					);
				}

				--indexJ;
			}

			++indexI;
		}

		return (leftIndex + rightIndex) / 2;
	}

	@Override protected int pivotIndex (
		final int leftIndex,
		final int rightIndex)
		throws java.lang.Exception
	{
		if (rightIndex - leftIndex < _groupElementCount)
		{
			return groupMedianIndex (
				leftIndex,
				rightIndex
			);
		}

		for (int index = leftIndex;
			index <= rightIndex;
			index = index + _groupElementCount)
		{
			int subRightIndex = index + _groupElementCount - 1;

			if (subRightIndex > rightIndex)
			{
				subRightIndex = rightIndex;
			}

			if (!swapLocations (
				groupMedianIndex (
					index,
					subRightIndex
				),
				leftIndex + (index - leftIndex) / _groupElementCount
			))
			{
				throw new java.lang.Exception (
					"MedianOfMediansSelector::pivotIndex => Cannot Swap Locations"
				);
			}
		}

		return selectIndex (
			leftIndex,
			leftIndex + (rightIndex - leftIndex) / _groupElementCount,
			(rightIndex - leftIndex) / 2 / _groupElementCount + leftIndex + 1
		);
	}

	/**
	 * MedianOfMediansSelector Constructor
	 * 
	 * @param elementArray Array of Elements
	 * @param tailCallOptimizationOn TRUE - Tail Call Optimization is Turned On
	 * @param groupElementCount Group Element Count
	 * 
	 * @throws java.lang.Exception Thrown if the Input is Invalid
	 */

	public MedianOfMediansSelector (
		final K[] elementArray,
		final boolean tailCallOptimizationOn,
		final int groupElementCount)
		throws java.lang.Exception
	{
		super (
			elementArray,
			tailCallOptimizationOn
		);

		if (5 > (_groupElementCount = groupElementCount))
		{
			throw new java.lang.Exception (
				"MedianOfMediansSelector Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Group Element Count
	 * 
	 * @return The Group Element Count
	 */

	public int groupElementCount()
	{
		return _groupElementCount;
	}
}
