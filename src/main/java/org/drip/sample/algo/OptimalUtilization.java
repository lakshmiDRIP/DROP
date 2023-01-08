
package org.drip.sample.algo;

import java.util.ArrayList;
import java.util.List;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>OptimalUtilization</i> finds all elements from each of two arrays such that the sum of their values is
 *  less or equal to target and as close to target as possible.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/algo/README.md">C<sup>x</sup> R<sup>x</sup> In-Place Manipulation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OptimalUtilization
{

	private static final List<List<Integer>> ClosestTargetPairList (
		final int[][] gridA,
		final int[][] gridB,
		final int target)
	{
		int optimalTargetGap = Integer.MAX_VALUE;

		List<Integer> gridAOptimalIndexList = new ArrayList<Integer>();

		List<Integer> gridBOptimalIndexList = new ArrayList<Integer>();

		for (int gridAIndex = 0;
			gridAIndex < gridA.length;
			++gridAIndex)
		{
			for (int gridBIndex = 0;
				gridBIndex < gridB.length;
				++gridBIndex)
			{
				int targetGap = target - gridA[gridAIndex][1] - gridB[gridBIndex][1];

				if (targetGap < 0)
				{
					continue;
				}

				if (targetGap < optimalTargetGap)
				{
					gridAOptimalIndexList.clear();

					gridBOptimalIndexList.clear();

					gridAOptimalIndexList.add (
						gridAIndex
					);

					gridBOptimalIndexList.add (
						gridBIndex
					);

					optimalTargetGap = targetGap;
				}
				else if (targetGap == optimalTargetGap)
				{
					gridAOptimalIndexList.add (
						gridAIndex
					);

					gridBOptimalIndexList.add (
						gridBIndex
					);
				}
			}
		}

		List<List<Integer>> closestTargetPairList = new ArrayList<List<Integer>>();

		if (gridAOptimalIndexList.isEmpty())
		{
			return closestTargetPairList;
		}

		for (int optimalPairIndex = 0;
			optimalPairIndex < gridAOptimalIndexList.size();
			++optimalPairIndex)
		{
			List<Integer> optimalPairList = new ArrayList<Integer>();

			optimalPairList.add (
				gridA[gridAOptimalIndexList.get (optimalPairIndex)][0]
			);

			optimalPairList.add (
				gridB[gridBOptimalIndexList.get (optimalPairIndex)][0]
			);

			closestTargetPairList.add (
				optimalPairList
			);
		}

		return closestTargetPairList;
	}

	private static final void Test1()
	{
		int[][] gridA =
		{
			{1, 2},
			{2, 4},
			{3, 6},
		};
		int[][] gridB =
		{
			{1, 2},
		};

		System.out.println (
			ClosestTargetPairList (
				gridA,
				gridB,
				7
			)
		);
	}

	private static final void Test2()
	{
		int[][] gridA =
		{
			{1, 3},
			{2, 5},
			{3, 7},
			{4, 10},
		};
		int[][] gridB =
		{
			{1, 2},
			{2, 3},
			{3, 4},
			{4, 5}
		};

		System.out.println (
			ClosestTargetPairList (
				gridA,
				gridB,
				10
			)
		);
	}

	private static final void Test3()
	{
		int[][] gridA =
		{
			{1, 8},
			{2, 7},
			{3, 14},
		};
		int[][] gridB =
		{
			{1, 5},
			{2, 10},
			{3, 14},
		};

		System.out.println (
			ClosestTargetPairList (
				gridA,
				gridB,
				20
			)
		);
	}

	private static final void Test4()
	{
		int[][] gridA =
		{
			{1, 8},
			{2, 15},
			{3, 9},
		};
		int[][] gridB =
		{
			{1, 8},
			{2, 11},
			{3, 12},
		};

		System.out.println (
			ClosestTargetPairList (
				gridA,
				gridB,
				20
			)
		);
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static void main (
		final String[] argumentArray)
		throws Exception
	{
		Test1();

		Test2();

		Test3();

		Test4();
	}
}
