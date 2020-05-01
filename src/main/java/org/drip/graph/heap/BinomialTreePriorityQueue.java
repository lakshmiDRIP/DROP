
package org.drip.graph.heap;

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
 * <i>BinomialTreePriorityQueue</i> implements an Binomial Tree Based Priority Queue. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Brodal, G. S., and C. Okasaki (1996): Optimal Purely Functional Priority Queues <i>Journal of
 *  			Functional Programming</i> <b>6 (6)</b> 839-857
 *  	</li>
 *  	<li>
 *  		Brown, M. R. (1978): Implementation and Analysis of Binomial Queue Algorithms <i>SIAM Journal on
 *  			Computing</i> <b>7 (3)</b> 298-319
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Vuillemin, J. (1978): A Data Structure for Manipulating Priority Queues <i>Communications of the
 *  			ACM</i> <b>21 (4)</b> 309-315
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Binomial Heap https://en.wikipedia.org/wiki/Binomial_heap
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/heap/README.md">Heap Based Priority Queue Implementations</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BinomialTreePriorityQueue
{
	private boolean _minHeap = false;
	private java.util.List<org.drip.graph.heap.BinomialTree> _binomialTreeList = null;

	/**
	 * BinomialTreePriorityQueue Constructor
	 * 
	 * @param minHeap TRUE - Indicates that Heap is a Min Heap
	 */

	public BinomialTreePriorityQueue (
		final boolean minHeap)
	{
		_minHeap = minHeap;
	}

	/**
	 * Indicate if the Binary Heap is a Min Heap
	 * 
	 * @return TRUE - The Binary Heap is a Min Heap
	 */

	public boolean minHeap()
	{
		return _minHeap;
	}

	/**
	 * Retrieve the List of Binomial Trees
	 * 
	 * @return List of Binomial Trees
	 */

	public java.util.List<org.drip.graph.heap.BinomialTree> binomialTreeList()
	{
		return _binomialTreeList;
	}

	/**
	 * Insert the Specified Key into the Heap
	 * 
	 * @param key Key
	 * 
	 * @return TRUE - The Key successfully inserted
	 */

	public boolean insert (
		final double key)
	{
		int currentOrder = 0;
		org.drip.graph.heap.BinomialTree meldedTree = null;

		try
		{
			meldedTree = new org.drip.graph.heap.BinomialTree (
				key
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		if (null == _binomialTreeList)
		{
			_binomialTreeList = new java.util.ArrayList<org.drip.graph.heap.BinomialTree>();
		}

		boolean insertFlag = currentOrder >= _binomialTreeList.size();

		org.drip.graph.heap.BinomialTree orderedTree = insertFlag ? null :
			_binomialTreeList.get (
				currentOrder
			);

		if (null == orderedTree)
		{
			if (insertFlag)
			{
				_binomialTreeList.add (
					meldedTree
				);
			}
			else
			{
				_binomialTreeList.set (
					currentOrder,
					meldedTree
				);
			}

			return true;
		}

		orderedTree = _binomialTreeList.get (
			currentOrder
		);

		while (null != orderedTree)
		{
			meldedTree = org.drip.graph.heap.BinomialTree.MeldPair (
				meldedTree,
				orderedTree,
				_minHeap
			);

			insertFlag = ++currentOrder >= _binomialTreeList.size();

			orderedTree = insertFlag ? null : _binomialTreeList.get (
				currentOrder
			);
		}

		if (insertFlag)
		{
			_binomialTreeList.add (
				meldedTree
			);
		}
		else
		{
			_binomialTreeList.set (
				currentOrder,
				meldedTree
			);
		}

		return true;
	}
}
