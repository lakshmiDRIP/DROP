
package org.drip.graph.subarray;

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
 * <i>Kadane</i> implements the Kadane Algorithm for the Maximum Sub-array Problem. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bentley, J. (1984): Programming Pearls: Algorithm Design Techniques <i>Communications of the
 *  			ACM</i> <b>27 (9)</b> 865-873
 *  	</li>
 *  	<li>
 *  		Bentley, J. (1989): <i>Programming Pearls <sup>nd</sup> Edition</i> <b>Addison-Wesley</b> Reading
 *  			MA
 *  	</li>
 *  	<li>
 *  		Gries, D. (1982): A Note on a Standard Strategy for developing Loop Invariants and Loops
 *  			<i>Science of Computer Programming</i> <b>2 (3)</b> 207-214
 *  	</li>
 *  	<li>
 *  		Takaoka, T. (2002): Efficient Algorithms for the Maximum Sub-array Problem by Distance Matrix
 *  			Multiplication https://www.sciencedirect.com/science/article/pii/S1571066104003135?via%3Dihub
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Maximum Sub-array Problem
 *  			https://en.wikipedia.org/wiki/Maximum_subarray_problem
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/README.md">Sub-set Sum, k-Sum, and Maximum Sub-array Problems</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Kadane
{
	private int[] _numberArray = null;
	private boolean _disallowEmptyArray = false;

	/**
	 * Kadane Constructor
	 * 
	 * @param numberArray The Input Number Array
	 * @param disallowEmptyArray TRUE - Disallow Empty Sub-array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Kadane (
		final int[] numberArray,
		final boolean disallowEmptyArray)
		throws java.lang.Exception
	{
		_numberArray = numberArray;

		if ((_disallowEmptyArray = disallowEmptyArray) && (null == _numberArray || 0 == _numberArray.length))
		{
			throw new java.lang.Exception (
				"Kadane Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Number Array
	 * 
	 * @return The Number Array
	 */

	public int[] numberArray()
	{
		return _numberArray;
	}

	/**
	 * Retrieve the Flag indicating whether Empty Array should be allowed
	 * 
	 * @return TRUE - Empty Array should be disallowed
	 */

	public boolean disallowEmptyArray()
	{
		return _disallowEmptyArray;
	}

	/**
	 * Compute the Maximum Sub-array Sum
	 * 
	 * @return The Maximum Sub-array Sum
	 */

	public int maximumSubArraySum()
	{
		if (null == _numberArray || 0 == _numberArray.length)
		{
			return 0;
		}

		int currentSum = 0;
		int maximumSubArraySum = _disallowEmptyArray ? java.lang.Integer.MIN_VALUE : 0;

		for (int number : _numberArray)
		{
			maximumSubArraySum = java.lang.Math.max (
				maximumSubArraySum,
				currentSum = java.lang.Math.max (
					_disallowEmptyArray ? number : 0,
					currentSum + number
				)
			);
		}

		return maximumSubArraySum;
	}

	/**
	 * Retrieve the Start/End Indexes of the Maximum Sub-array Sequence
	 * 
	 * @return Start/End Indexes of the Maximum Sub-array Sequence
	 */

	public int[] maximumSubarraySequence()
	{
		int currentSum = 0;
		int maximumSumEnd = 0;
		int currentSumStart = 0;
		int maximumSumStart = 0;
		int arrayLength = _numberArray.length;
		int maximumSubArraySum = _disallowEmptyArray ? java.lang.Integer.MIN_VALUE : 0;

		for (int index = 0;
			index < arrayLength;
			++index)
		{
			int newSum = currentSum + _numberArray[index];
			int adjustedNumber = _disallowEmptyArray ? _numberArray[index] : 0;

			if (adjustedNumber > newSum)
			{
				currentSumStart = index;
				currentSum = adjustedNumber;
			}
			else
			{
				currentSum = newSum;
			}

			if (maximumSubArraySum < currentSum)
			{
				maximumSumEnd = index;
				maximumSubArraySum = currentSum;
				maximumSumStart = currentSumStart;
			}
		}

		return new int[]
		{
			maximumSumStart,
			maximumSumEnd
		};
	}
}
