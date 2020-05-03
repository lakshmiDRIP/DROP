
package org.drip.sample.heap;

import java.util.List;
import java.util.Map;

import org.drip.graph.heap.BinaryTreeNode;
import org.drip.graph.heap.BinaryTreePriorityQueue;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>BinaryHeapMeld</i> illustrates the Melding of two Binary Heaps into One. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Brodal, G. S., G. Lagogiannis, and R. E. Tarjan (2012): Strict Fibonacci Heaps <i>Proceedings on
 *  			the 44<sup>th</sup> Symposium on the Theory of Computing - STOC '12</i> 1177-1184
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Hayward, R., and C. McDiarmid (1991): Average Case Analysis of Heap-building by Repeated
 *  			Insertion <i>Journal of Algorithms</i> <b>12 (1)</b> 126-153
 *  	</li>
 *  	<li>
 *  		Suchanek, M. A. (2012): Elementary yet Precise Worst-case Analysis of Floyd's Heap Construction
 *  			Program <i>Fundamenta Informaticae</i> <b>120 (1)</b> 75-92
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Binary Heap https://en.wikipedia.org/wiki/Binary_heap
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/heap/README.md">Priority Queue and Heap Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BinaryHeapMeld
{

	private static <K, V> String PrintNode (
		final BinaryTreeNode<K, V> binaryTreeNode)
		throws Exception
	{
		BinaryTreeNode<K, V> parent = binaryTreeNode.parent();

		return "[" + FormatUtil.FormatDouble (
			binaryTreeNode.key(), 1, 3, 1.
		) + " | " +
			binaryTreeNode.level() + " | " +
		(
			binaryTreeNode.isRightChild() ? "R" : "L"
		) + " | " + (
			null != parent ? FormatUtil.FormatDouble (
				parent.key(), 1, 3, 1.
			) : "     "
		) + "]";
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		int keyCount1 = 16;
		int keyCount2 = 16;

		BinaryTreePriorityQueue<Double, Double> binaryHeap1 = new BinaryTreePriorityQueue<Double, Double> (
			false
		);

		BinaryTreePriorityQueue<Double, Double> binaryHeap2 = new BinaryTreePriorityQueue<Double, Double> (
			false
		);

		for (int keyIndex = 0;
			keyIndex < keyCount1;
			++keyIndex)
		{
			double key = Math.random();

			binaryHeap1.insert (
				key
			);
		}

		for (int keyIndex = 0;
			keyIndex < keyCount2;
			++keyIndex)
		{
			double key = Math.random();

			binaryHeap2.insert (
				key
			);
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		System.out.println (
			"\t| BINARY HEAP #1"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		for (Map.Entry<Integer, List<BinaryTreeNode<Double, Double>>> bfsEntry :
			binaryHeap1.bfsLevelListMap().entrySet())
		{
			String treeDump = "\t| {" + bfsEntry.getKey() + "}";

			for (BinaryTreeNode<Double, Double> binaryTreeNode: bfsEntry.getValue())
			{
				treeDump = treeDump + PrintNode (
					binaryTreeNode
				);
			}

			System.out.println (treeDump);
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		System.out.println();

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		System.out.println (
			"\t| BINARY HEAP #2"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		for (Map.Entry<Integer, List<BinaryTreeNode<Double, Double>>> bfsEntry :
			binaryHeap2.bfsLevelListMap().entrySet())
		{
			String treeDump = "\t| {" + bfsEntry.getKey() + "}";

			for (BinaryTreeNode<Double, Double> binaryTreeNode: bfsEntry.getValue())
			{
				treeDump = treeDump + PrintNode (
					binaryTreeNode
				);
			}

			System.out.println (treeDump);
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		binaryHeap1.meld (
			binaryHeap2
		);

		System.out.println();

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		System.out.println (
			"\t| MELDED BINARY HEAP"
		);

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		for (Map.Entry<Integer, List<BinaryTreeNode<Double, Double>>> bfsEntry :
			binaryHeap1.bfsLevelListMap().entrySet())
		{
			String treeDump = "\t| {" + bfsEntry.getKey() + "}";

			for (BinaryTreeNode<Double, Double> binaryTreeNode: bfsEntry.getValue())
			{
				treeDump = treeDump + PrintNode (
					binaryTreeNode
				);
			}

			System.out.println (treeDump);
		}

		System.out.println (
			"\t|--------------------------------------------------------------------------------------------------------------------------------"
		);

		EnvManager.TerminateEnv();
	}
}
