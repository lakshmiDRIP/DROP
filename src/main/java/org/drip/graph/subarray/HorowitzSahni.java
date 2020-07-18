
package org.drip.graph.subarray;

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
 * <i>HorowitzSahni</i> implements the Sub-set Sum Check using the Horowitz-Sahni Scheme. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bringmann, K. (2017): A near-linear Pseudo-polynomial Time Algorithm for Subset Sums
 *  			<i>Proceedings of the 28<sup>th</sup> Annual ACM SIAM Symposium on Discrete Algorithms</i>
 *  			1073-1084
 *  	</li>
 *  	<li>
 *  		Horowitz, E., and S. Sahni (1974): Computing Partitions with Applications to the Knapsack Problem
 *  			<i>Journal of the ACM</i> <b>21 (2)</b> 277-292
 *  	</li>
 *  	<li>
 *  		Kleinberg, J., and E. Tardos (2022): <i>Algorithm Design 2<sup>nd</sup> Edition</i>
 *  			<b>Pearson</b>
 *  	</li>
 *  	<li>
 *  		Koiliaris, K., and C. Xu (2016): A Faster Pseudo-polynomial Time Algorithm for Subset Sum
 *  			https://arxiv.org/abs/1507.02318 <b>arXiV</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Subset Sum Problem https://en.wikipedia.org/wiki/Subset_sum_problem
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

public class HorowitzSahni
	extends org.drip.graph.subarray.SubsetSum
{

	private static final java.util.Set<java.lang.Integer> SumSet (
		final int[] numberArray,
		final java.util.Set<java.lang.Integer> prevSumSet,
		final int currentIndex)
	{
		int arrayLength = numberArray.length;

		if (currentIndex >= arrayLength)
		{
			return prevSumSet;
		}

		int prevSumSetSize = prevSumSet.size();

		java.util.TreeSet<java.lang.Integer> compositeCurrentSumSet =
			new java.util.TreeSet<java.lang.Integer>();

		for (int nextIndex = currentIndex;
			nextIndex < arrayLength;
			++nextIndex)
		{
			java.util.TreeSet<java.lang.Integer> currentSumSet = new java.util.TreeSet<java.lang.Integer>();

			if (0 != prevSumSetSize)
			{
				for (int prevSum : prevSumSet)
				{
					currentSumSet.add (
						prevSum + numberArray[nextIndex]
					);
				}
			}
			else
			{
				currentSumSet.add (
					numberArray[nextIndex]
				);
			}

			compositeCurrentSumSet.addAll (
				currentSumSet
			);

			compositeCurrentSumSet.addAll (
				SumSet (
					numberArray,
					currentSumSet,
					nextIndex + 1
				)
			);
		}

		return compositeCurrentSumSet;
	}

	/**
	 * HorowitzSahni Constructor
	 * 
	 * @param numberArray The Input Number Array
	 * @param target The Sum Target
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HorowitzSahni (
		final int[] numberArray,
		final int target)
		throws java.lang.Exception
	{
		super (
			numberArray,
			target
		);
	}

	@Override public boolean targetSumExists()
	{
		int[] numberArray = numberArray();

		int target = target();

		int subArray1Index = 0;
		int subArray2Index = 0;
		int arrayLength = numberArray.length;
		int[] subArray2 = new int[arrayLength / 2];
		int prevNumber2 = java.lang.Integer.MIN_VALUE;
		int[] subArray1 = new int[arrayLength - arrayLength / 2];

		for (int index = 0;
			index < numberArray.length;
			++index)
		{
			if (0 == index % 2)
				subArray1[subArray1Index++] = numberArray[index];
			else
				subArray2[subArray2Index++] = numberArray[index];
		}

		java.util.Set<java.lang.Integer> ascendingSumSet1 = new java.util.TreeSet<java.lang.Integer>();

		java.util.Set<java.lang.Integer> descendingSumSet2 = new java.util.TreeSet<java.lang.Integer>();

		ascendingSumSet1 = SumSet (
			numberArray,
			ascendingSumSet1,
			0
		);

		descendingSumSet2 = ((java.util.TreeSet<java.lang.Integer>) SumSet (
			numberArray,
			descendingSumSet2,
			0
		)).descendingSet();

		for (int number1 : ascendingSumSet1)
		{
			for (int number2 : descendingSumSet2)
			{
				if (number2 >= prevNumber2)
				{
					if (target == number1 + number2)
					{
						return true;
					}

					number2 = prevNumber2;
				}
			}
		}

		return false;
	}
}
