
package org.drip.graph.heap;

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

public class BinomialTreePriorityQueue<KEY extends java.lang.Comparable<KEY>, ITEM>
	extends org.drip.graph.heap.PriorityQueue<KEY, ITEM>
{
	private java.util.List<org.drip.graph.heap.BinomialTree<KEY, ITEM>> _binomialTreeList = null;

	private boolean trimEmptyTrailer()
	{
		int reverseIndex = null == _binomialTreeList ? 0 : _binomialTreeList.size() - 1;

		while (reverseIndex >= 0 &&
			null == _binomialTreeList.get (
				reverseIndex
			)
		)
		{
			_binomialTreeList.remove (
				reverseIndex--
			);
		}

		return true;
	}

	private int extremumIndex()
		throws java.lang.Exception
	{
		if (isEmpty())
		{
			throw new java.lang.Exception (
				"BinomialTreePriorityQueue::extremumIndex => The Extremum (Minimum/Maximum) Index cannot be computed"
			);
		}

		int extremumIndex = 0;

		while (extremumIndex < _binomialTreeList.size() &&
			null == _binomialTreeList.get (
				extremumIndex
			)
		)
		{
			++extremumIndex;
		}

		int size = _binomialTreeList.size();

		if (extremumIndex >= size)
		{
			--extremumIndex;
		}

		KEY extremumKey = _binomialTreeList.get (
			extremumIndex
		).entry().key();

		boolean minHeap = minHeap();

		for (int index = extremumIndex + 1;
			index < size;
			++index)
		{
			org.drip.graph.heap.BinomialTree<KEY, ITEM> current = _binomialTreeList.get (
				index
			);

			if (null != current)
			{
				KEY currentKey = _binomialTreeList.get (
					index
				).entry().key();

				if (minHeap)
				{
					if (1 != currentKey.compareTo (
						extremumKey
					))
					{
						extremumKey = currentKey;
						extremumIndex = index;
					}
				}
				else
				{
					if (-1 != currentKey.compareTo (
						extremumKey
					))
					{
						extremumKey = currentKey;
						extremumIndex = index;
					}
				}
			}
		}

		return extremumIndex;
	}

	/**
	 * BinomialTreePriorityQueue Constructor
	 * 
	 * @param minHeap TRUE - Indicates that Heap is a Min Heap
	 */

	public BinomialTreePriorityQueue (
		final boolean minHeap)
	{
		super (
			minHeap
		);
	}

	/**
	 * Retrieve the List of Binomial Trees
	 * 
	 * @return List of Binomial Trees
	 */

	public java.util.List<org.drip.graph.heap.BinomialTree<KEY, ITEM>> binomialTreeList()
	{
		return _binomialTreeList;
	}

	/**
	 * Meld the Specified Tree into the Heap
	 * 
	 * @param tree The Tree to Meld
	 * 
	 * @return TRUE - The Tree successfully melded into the Heap
	 */

	public boolean meld (
		final org.drip.graph.heap.BinomialTree<KEY, ITEM> tree)
	{
		if (null == tree)
		{
			return true;
		}

		if (null == _binomialTreeList)
		{
			_binomialTreeList = new java.util.ArrayList<org.drip.graph.heap.BinomialTree<KEY, ITEM>>();
		}

		int currentOrder = tree.order();

		while (_binomialTreeList.size() < currentOrder)
		{
			_binomialTreeList.add (
				null
			);
		}

		boolean insertFlag = currentOrder >= _binomialTreeList.size();

		org.drip.graph.heap.BinomialTree<KEY, ITEM> orderedTree = insertFlag ? null :
			_binomialTreeList.get (
				currentOrder
			);

		org.drip.graph.heap.BinomialTree<KEY, ITEM> combinedTree = tree;

		if (null == orderedTree)
		{
			if (insertFlag)
			{
				_binomialTreeList.add (
					combinedTree
				);
			}
			else
			{
				_binomialTreeList.set (
					currentOrder,
					combinedTree
				);
			}

			return true;
		}

		orderedTree = _binomialTreeList.get (
			currentOrder
		);

		boolean minHeap = minHeap();

		while (null != orderedTree)
		{
			_binomialTreeList.set (
				currentOrder,
				null
			);

			combinedTree = org.drip.graph.heap.BinomialTree.CombinePair (
				combinedTree,
				orderedTree,
				minHeap
			);

			insertFlag = ++currentOrder >= _binomialTreeList.size();

			orderedTree = insertFlag ? null : _binomialTreeList.get (
				currentOrder
			);
		}

		if (insertFlag)
		{
			_binomialTreeList.add (
				combinedTree
			);
		}
		else
		{
			_binomialTreeList.set (
				currentOrder,
				combinedTree
			);
		}

		return trimEmptyTrailer();
	}

	@Override public boolean meld (
		final org.drip.graph.heap.PriorityQueue<KEY, ITEM> priorityQueueOther)
	{
		if (null == priorityQueueOther ||
			priorityQueueOther.isEmpty() ||
			!(priorityQueueOther instanceof BinomialTreePriorityQueue<?, ?>)
		)
		{
			return true;
		}

		BinomialTreePriorityQueue<KEY, ITEM> binomialHeapOther =
			(BinomialTreePriorityQueue<KEY, ITEM>) priorityQueueOther;

		java.util.List<org.drip.graph.heap.BinomialTree<KEY, ITEM>> binomialTreeListOther =
			binomialHeapOther.binomialTreeList();

		if (isEmpty())
		{
			_binomialTreeList = binomialTreeListOther;
			return true;
		}

		for (org.drip.graph.heap.BinomialTree<KEY, ITEM> tree : binomialTreeListOther)
		{
			if (!meld (
				tree
			))
			{
				return false;
			}
		}

		return true;
	}

	@Override public boolean insert (
		final KEY key,
		final ITEM item)
	{
		try
		{
			return meld (
				new org.drip.graph.heap.BinomialTree<KEY, ITEM> (
					new org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> (
						key,
						item
					)
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@Override public org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> extractExtremum()
	{
		int extremumIndex = -1;

		try
		{
			extremumIndex = extremumIndex();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		org.drip.graph.heap.BinomialTree<KEY, ITEM> extremumTree = _binomialTreeList.get (
			extremumIndex
		);

		_binomialTreeList.set (
			extremumIndex,
			null
		);

		java.util.List<org.drip.graph.heap.BinomialTree<KEY, ITEM>> binomialTreeChildren =
			extremumTree.children();

		if (null != binomialTreeChildren)
		{
			for (org.drip.graph.heap.BinomialTree<KEY, ITEM> child : binomialTreeChildren)
			{
				if (!meld (
					child
				))
				{
					return null;
				}
			}
		}

		return trimEmptyTrailer() ? extremumTree.entry() : null;
	}

	@Override public org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> extremum()
	{
		int extremumIndex = -1;

		try
		{
			extremumIndex = extremumIndex();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return _binomialTreeList.get (
			extremumIndex
		).entry();
	}

	@Override public boolean isEmpty()
	{
		return null == _binomialTreeList || 0 == _binomialTreeList.size();
	}

	@Override public java.lang.String toString()
	{
		return null == _binomialTreeList ? "[]" : _binomialTreeList.toString();
	}
}
