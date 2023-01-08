
package org.drip.sample.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
 * <i>ShopkeeperSale</i> returns the total cost of all items.
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

public class ShopkeeperSale
{

	private static final int ProcessDiscount (
		final int[] priceArray,
		final List<Integer> discountedItemList)
	{
		int totalDiscountedPrice = 0;

		Stack<Integer> itemIndexStack = new Stack<Integer>();

		for (int itemIndex = 0;
			itemIndex < priceArray.length;
			++itemIndex)
		{
			while (!itemIndexStack.isEmpty() &&
				priceArray[itemIndexStack.peek()] >= priceArray[itemIndex])
			{
				totalDiscountedPrice = totalDiscountedPrice + priceArray[itemIndexStack.pop()] -
					priceArray[itemIndex];
			}

			itemIndexStack.push (
				itemIndex
			);
		}

		while (!itemIndexStack.isEmpty())
		{
			int itemIndex = itemIndexStack.pop();

			discountedItemList.add (
				0,
				itemIndex
			);

			totalDiscountedPrice = totalDiscountedPrice + priceArray[itemIndex];
		}

		return totalDiscountedPrice;
	}

	private static final void Test1()
		throws Exception
	{
		int[] priceArray =
		{
			2, 3, 1, 2, 4, 2
		};

		List<Integer> discountedItemList = new ArrayList<Integer>();

		System.out.println (
			ProcessDiscount (
				priceArray,
				discountedItemList
			)
		);

		System.out.println (
			discountedItemList
		);
	}

	private static final void Test2()
		throws Exception
	{
		int[] priceArray =
		{
			5, 1, 3, 4, 6, 2
		};

		List<Integer> discountedItemList = new ArrayList<Integer>();

		System.out.println (
			ProcessDiscount (
				priceArray,
				discountedItemList
			)
		);

		System.out.println (
			discountedItemList
		);
	}

	public static final void Test3()
		throws Exception
	{
		int[] priceArray =
		{
			1, 3, 3, 2, 5
		};

		List<Integer> discountedItemList = new ArrayList<Integer>();

		System.out.println (
			ProcessDiscount (
				priceArray,
				discountedItemList
			)
		);

		System.out.println (
			discountedItemList
		);
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		Test1();

		Test2();

		Test3();
	}
}
