
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
 * <i>BinomialTree</i> implements an Ordered Binomial Tree. The References are:
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

public class BinomialTree<K extends java.lang.Comparable<K>, V>
{
	private BinomialTree<K, V> _parent = null;
	private java.util.List<BinomialTree<K, V>> _children = null;
	private org.drip.graph.heap.PriorityQueueEntry<K, V> _entry = null;

	private static <K extends java.lang.Comparable<K>, V> boolean ChildKeyList (
		final BinomialTree<K, V> tree,
		final java.util.List<K> childKeyList)
	{
		if (null == childKeyList)
		{
			return false;
		}

		if (null == tree)
		{
			return true;
		}

		java.util.List<BinomialTree<K, V>> children = tree.children();

		if (null == children)
		{
			return true;
		}

		for (BinomialTree<K, V> child : children)
		{
			if (null != child)
			{
				childKeyList.add (
					child.entry().key()
				);

				ChildKeyList (
					child,
					childKeyList
				);
			}
		}

		return true;
	}

	/**
	 * Combine the specified Pair of Binomial Trees into one of the Higher Order
	 * 
	 * @param <K> Key Type
	 * @param <V> Value Type
	 * @param binomialTree1 Binomial Tree #1
	 * @param binomialTree2 Binomial Tree #2
	 * @param minHeap TRUE - Meld into a Minimum Binomial Heap
	 * 
	 * @return The Binomial Tree of Higher Order
	 */

	public static <K extends java.lang.Comparable<K>, V> BinomialTree<K, V> CombinePair (
		final BinomialTree<K, V> binomialTree1,
		final BinomialTree<K, V> binomialTree2,
		final boolean minHeap)
	{
		if (null == binomialTree1 || null == binomialTree2)
		{
			return null;
		}

		int order = binomialTree1.order();

		if (order != binomialTree2.order())
		{
			return null;
		}

		BinomialTree<K, V> upTree = null;
		BinomialTree<K, V> downTree = null;
		BinomialTree<K, V> combinedTree = null;

		K key1 = binomialTree1.entry().key();

		K key2 = binomialTree2.entry().key();

		if (minHeap)
		{
			if (-1 != key1.compareTo (
				key2
			))
			{
				upTree = binomialTree2;
				downTree = binomialTree1;
			}
			else
			{
				upTree = binomialTree1;
				downTree = binomialTree2;
			}
		}
		else
		{
			if (-1 != key1.compareTo (
				key2
			))
			{
				upTree = binomialTree1;
				downTree = binomialTree2;
			}
			else
			{
				upTree = binomialTree2;
				downTree = binomialTree1;
			}
		}

		java.util.List<BinomialTree<K, V>> meldedChildren = new java.util.ArrayList<BinomialTree<K, V>>();

		java.util.List<BinomialTree<K, V>> upChildren = upTree.children();

		if (null != upChildren)
		{
			int upChildrenCount = upChildren.size();

			for (int meldedChildIndex = 0;
				meldedChildIndex < upChildrenCount;
				++meldedChildIndex)
			{
				meldedChildren.add (
					upChildren.get (
						meldedChildIndex
					)
				);
			}
		}

		meldedChildren.add (
			downTree
		);

		try
		{
			combinedTree = new BinomialTree<K, V> (
				new org.drip.graph.heap.PriorityQueueEntry<K, V> (
					upTree.entry().key(),
					upTree.entry().value()
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return combinedTree.setChildren (
			meldedChildren
		) && downTree.setParent (
			combinedTree
		) ? combinedTree : null;
	}

	/**
	 * BinomialTree Constructor
	 * 
	 * @param entry Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BinomialTree (
		final org.drip.graph.heap.PriorityQueueEntry<K, V> entry)
		throws java.lang.Exception
	{
		if (null == (_entry = entry))
		{
			throw new java.lang.Exception (
				"BinomialTree Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Entry of the Binomial Tree Node
	 * 
	 * @return Entry of the Binomial Tree Node
	 */

	public org.drip.graph.heap.PriorityQueueEntry<K, V> entry()
	{
		return _entry;
	}

	/**
	 * Retrieve the Parent of the Binomial Tree
	 * 
	 * @return Parent of the Binomial Tree
	 */

	public BinomialTree<K, V> parent()
	{
		return _parent;
	}

	/**
	 * Retrieve the List of the Children
	 * 
	 * @return List of the Children
	 */

	public java.util.List<BinomialTree<K, V>> children()
	{
		return _children;
	}

	/**
	 * Retrieve the Order of the Binomial Tree
	 * 
	 * @return Order of the Binomial Tree
	 */

	public int order()
	{
		return null == _children ? 0 : _children.size();
	}

	/**
	 * Set the Parent of the Binomial Tree
	 * 
	 * @param parent Parent of the Binomial Tree
	 * 
	 * @return TRUE - The Parent of the Binomial Tree successfully set
	 */

	public boolean setParent (
		final BinomialTree<K, V> parent)
	{
		_parent = parent;
		return true;
	}

	/**
	 * Set the Children of the Binomial Tree
	 * 
	 * @param children Children of the Binomial Tree
	 * 
	 * @return TRUE - The Children of the Binomial Tree successfully set
	 */

	public boolean setChildren (
		final java.util.List<BinomialTree<K, V>> children)
	{
		if (null == children)
		{
			return true;
		}

		int childrenCount = children.size();

		if (0 == childrenCount)
		{
			return true;
		}

		for (int childIndex = 0;
			childIndex < childrenCount;
			++childIndex)
		{
			BinomialTree<K, V> child = children.get (
				0
			);

			if (null == child)
			{
				return false;
			}
		}

		_children = children;
		return true;
	}

	/**
	 * Retrieve the List of all the Child Keys
	 * 
	 * @return The List of all the Child Keys
	 */

	public java.util.List<K> childKeyList()
	{
		java.util.List<K> childKeyList = new java.util.ArrayList<K>();

		return ChildKeyList (
			this,
			childKeyList
		) ? childKeyList : null;
	}

	@Override public java.lang.String toString()
	{
		return "{" +
			"Order = " + order() + " | Key = " + entry().key()+
			" | Parent Key = " + (null == _parent ? "null" : _parent.entry().key()) +
			" | Children Keys = (" + childKeyList() + ")" +
		"}";
	}
}
