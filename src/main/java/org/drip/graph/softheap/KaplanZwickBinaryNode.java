
package org.drip.graph.softheap;

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
 * <i>KaplanZwickBinaryNode</i> implements the Binary Node described in Kaplan and Zwick (2009). The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Chazelle, B. (2000): The Discrepancy Method: Randomness and Complexity
 *  			https://www.cs.princeton.edu/~chazelle/pubs/book.pdf
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): The Soft Heap: An Approximate Priority Queue with Optimal Error Rate
 *  			<i>Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1012-1027
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): A Minimum Spanning Tree Algorithm with Inverse-Ackerman Type Complexity
 *  			<i>Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1028-1047
 *  	</li>
 *  	<li>
 *  		Kaplan, H., and U. Zwick (2009): A simpler implementation and analysis of Chazelle's Soft Heaps
 *  			https://epubs.siam.org/doi/abs/10.1137/1.9781611973068.53?mobileUi=0
 *  	</li>
 *  	<li>
 *  		Pettie, S., and V. Ramachandran (2008): Randomized Minimum Spanning Tree Algorithms using
 *  			Exponentially Fewer Random Bits <i>ACM Transactions on Algorithms</i> <b>4 (1)</b> 1-27
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/softheap/README.md">Soft Heap - Approximate Priority Queue</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class KaplanZwickBinaryNode<KEY extends java.lang.Comparable<KEY>, ITEM>
{
	private int _k = -1;
	private int _r = -1;
	private boolean _minHeap = false;
	private KaplanZwickBinaryNode<KEY, ITEM> _left = null;
	private KaplanZwickBinaryNode<KEY, ITEM> _right = null;
	private KaplanZwickBinaryNode<KEY, ITEM> _parent = null;
	private org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> _cEntry = null;
	private org.drip.graph.softheap.KaplanZwickTargetSize _targetSize = null;
	private java.util.List<org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM>> _entryList = null;

	private boolean swapChildren()
	{
		KaplanZwickBinaryNode<KEY, ITEM> tmp = _left;
		_left = _right;
		_right = tmp;
		return true;
	}

	private boolean shouldBeSifted()
	{
		if (null == _left && null == _right)
		{
			return false;
		}

		return _entryList.size() < java.lang.Math.ceil (
			0.5 * _targetSize.estimate()
		);
	}

	/**
	 * Combine Two Root Nodes of Rank k each to a Root Node of Rank k + 1
	 * 
	 * @param <KEY> Key Type
	 * @param <ITEM> Item Type
	 * @param node1 Node #1
	 * @param node2 Node #2
	 * 
	 * @return The Combined Root Node of Rank k + 1
	 */

	public static <KEY extends java.lang.Comparable<KEY>, ITEM> KaplanZwickBinaryNode<KEY, ITEM>
		CombineRootNodePair (
			final KaplanZwickBinaryNode<KEY, ITEM> node1,
			final KaplanZwickBinaryNode<KEY, ITEM> node2)
	{
		if (null == node1 || null == node2 || !node1.isRoot() || !node2.isRoot())
		{
			return null;
		}

		int k = node1.k();

		if (k != node2.k())
		{
			return null;
		}

		int r = node1.r();

		if (r != node2.r())
		{
			System.out.println ("r Mismatch: " + r + " | " + node2.r());

			return null;
		}

		boolean minHeap = node1.minHeap();

		if (minHeap != node2.minHeap())
		{
			return null;
		}

		KaplanZwickBinaryNode<KEY, ITEM> newRoot = null;

		try
		{
			newRoot = new KaplanZwickBinaryNode<KEY, ITEM> (
				minHeap,
				r,
				k + 1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> cEntry1 = node1.cEntry();

		org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> cEntry2 = node2.cEntry();

		KEY ckey1 = cEntry1.key();

		KEY ckey2 = cEntry2.key();

		org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> cEntry = -1 == ckey1.compareTo (
			ckey2
		) ? cEntry1 : cEntry2;

		if (!minHeap)
		{
			cEntry = 1 == ckey1.compareTo (
				ckey2
			) ? cEntry1 : cEntry2;
		}

		return newRoot.setCEntry (
			cEntry
		) && node1.setParent (
			newRoot
		) && node2.setParent (
			newRoot
		) &&  newRoot.setLeft (
			node1
		) && newRoot.setRight (
			node2
		) && newRoot.sift() ? newRoot : null;
	}

	/**
	 * Construct an Instance of KaplanZwickBinaryNode from the Error Rate
	 * 
	 * @param <KEY> Key Type
	 * @param <ITEM> Item Type
	 * @param minHeap Min Heap Flag
	 * @param errorRate Error Rate
	 * @param k Rank
	 * @param left Left Child
	 * @param right Right Child
	 * @param parent Parent
	 * 
	 * @return KaplanZwickBinaryNode Instance from the Error Rate
	 */

	public static <KEY extends java.lang.Comparable<KEY>, ITEM> KaplanZwickBinaryNode<KEY, ITEM>
		FromErrorRate (
			final boolean minHeap,
			final double errorRate,
			final int k,
			final KaplanZwickBinaryNode<KEY, ITEM> left,
			final KaplanZwickBinaryNode<KEY, ITEM> right,
			final KaplanZwickBinaryNode<KEY, ITEM> parent)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				errorRate
			) || 0. >= errorRate || 1. <= errorRate
		)
		{
			return null;
		}

		try
		{
			KaplanZwickBinaryNode<KEY, ITEM> node = new KaplanZwickBinaryNode<KEY, ITEM> (
				minHeap,
				5 + (int) java.lang.Math.ceil (
					java.lang.Math.log (
						errorRate
					) / java.lang.Math.log (
						2.
					)
				),
				k
			);

			return node.setParent (
				parent
			) &&  node.setLeft (
				left
			) && node.setRight (
				right
			) ? node : null;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Leaf Root Node of a New Tree with a single Entry
	 * 
	 * @param <KEY> Key Type
	 * @param <ITEM> Item Type
	 * @param minHeap Min Heap Flag
	 * @param r The R Parameter
	 * @param entry The Entry
	 * 
	 * @return The Leaf Root of a New Tree with a single KeEntryy
	 */

	public static <KEY extends java.lang.Comparable<KEY>, ITEM> KaplanZwickBinaryNode<KEY, ITEM> LeafRoot (
		final boolean minHeap,
		final int r,
		final org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> entry)
	{
		try
		{
			KaplanZwickBinaryNode<KEY, ITEM> emptyRoot = new KaplanZwickBinaryNode<KEY, ITEM> (
				minHeap,
				r,
				0
			);

			return emptyRoot.addEntry (
				entry
			) ? emptyRoot : null;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * KaplanZwickBinaryNode Constructor
	 * 
	 * @param minHeap Min Heap Flag
	 * @param r R Parameter
	 * @param k Rank
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public KaplanZwickBinaryNode (
		final boolean minHeap,
		final int r,
		final int k)
		throws java.lang.Exception
	{
		if (0 > (_r = r) ||
			0 > (_k = k) ||
			null == (_targetSize =
				org.drip.graph.softheap.KaplanZwickTargetSize.Standard (
					_k,
					_r
				)
			)
		)
		{
			throw new java.lang.Exception (
				"KaplanZwickBinaryNode Constructor => Invalid Inputs"
			);
		}

		_minHeap = minHeap;

		_entryList = new java.util.ArrayList<org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM>>();
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
	 * Retrieve the Rank
	 * 
	 * @return The Rank
	 */

	public int k()
	{
		return _k;
	}

	/**
	 * Retrieve the Left Tree
	 * 
	 * @return The Left Tree
	 */

	public KaplanZwickBinaryNode<KEY, ITEM> left()
	{
		return _left;
	}

	/**
	 * Retrieve the Right Tree
	 * 
	 * @return The right Tree
	 */

	public KaplanZwickBinaryNode<KEY, ITEM> right()
	{
		return _right;
	}

	/**
	 * Retrieve the Parent Tree
	 * 
	 * @return The Parent Tree
	 */

	public KaplanZwickBinaryNode<KEY, ITEM> parent()
	{
		return _parent;
	}

	/**
	 * Retrieve the Target Size
	 * 
	 * @return The Target Size
	 */

	public org.drip.graph.softheap.KaplanZwickTargetSize targetSize()
	{
		return _targetSize;
	}

	/**
	 * Retrieve the R Parameter
	 * 
	 * @return The R Parameter
	 */

	public int r()
	{
		return _r;
	}

	/**
	 * Retrieve the List of Entries
	 * 
	 * @return The List of Entries
	 */

	public java.util.List<org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM>> entryList()
	{
		return _entryList;
	}

	/**
	 * Retrieve the cEntry
	 * 
	 * @return The cEntry
	 */

	public org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> cEntry()
	{
		return _cEntry;
	}

	/**
	 * Retrieve the Current Size of the List
	 * 
	 * @return Current Size of the List
	 */

	public int currentSize()
	{
		return _entryList.size();
	}

	/**
	 * Set the CEntry of the Current Node
	 * 
	 * @param cEntry The CEntry
	 * 
	 * @return TRUE - The cEntry of the Current Node successfully set
	 */

	public boolean setCEntry (
		final org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> cEntry)
	{
		if (null == cEntry)
		{
			return false;
		}

		_cEntry = cEntry;
		return true;
	}

	/**
	 * Set the Parent of the Current Node
	 * 
	 * @param parent The Parent
	 * 
	 * @return TRUE - The Parent of the Current Node successfully set
	 */

	public boolean setParent (
		final KaplanZwickBinaryNode<KEY, ITEM> parent)
	{
		_parent = parent;
		return true;
	}

	/**
	 * Set the Left Child of the Current Node
	 * 
	 * @param left The Left Child
	 * 
	 * @return TRUE - The Left Child of the Current Node successfully set
	 */

	public boolean setLeft (
		final KaplanZwickBinaryNode<KEY, ITEM> left)
	{
		_left = left;
		return true;
	}

	/**
	 * Set the Right Child of the Current Node
	 * 
	 * @param right The Right Child
	 * 
	 * @return TRUE - The Right Child of the Current Node successfully set
	 */

	public boolean setRight (
		final KaplanZwickBinaryNode<KEY, ITEM> right)
	{
		_right = right;
		return true;
	}

	/**
	 * Add an Entry to the Entry List
	 * 
	 * @param entry The Entry
	 * 
	 * @return TRUE - The Entry is successfully added to the Entry List
	 */

	public boolean addEntry (
		final org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> entry)
	{
		if (null == entry)
		{
			return false;
		}

		_entryList.add (
			entry
		);

		if (null == _cEntry)
		{
			_cEntry = entry;
		}
		else
		{
			if (_minHeap)
			{
				_cEntry = 1 == _cEntry.key().compareTo (
					entry.key()
				) ? _cEntry : entry;
			}
			else
			{
				_cEntry = -1 == _cEntry.key().compareTo (
					entry.key()
				) ? _cEntry : entry;
			}
		}

		return true;
	}

	/**
	 * Peek the Top Entry
	 * 
	 * @return The Top Entry
	 */

	public org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> peekTopEntry()
	{
		return _entryList.get (
			0
		);
	}

	/**
	 * Remove the Top Entry
	 * 
	 * @return The Top Entry
	 */

	public org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> removeTopEntry()
	{
		org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> topEntry = _entryList.remove (
			0
		);

		return sift() ? topEntry : null;
	}

	/**
	 * Retrieve the Key Corruption Status List
	 * 
	 * @return The Key Corruption Status List
	 */

	public java.util.List<java.lang.Boolean> keyCorruptionStatusList()
	{
		java.util.List<java.lang.Boolean> keyCorruptionStatusList =
			new java.util.ArrayList<java.lang.Boolean>();

		for (org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM> entry : _entryList)
		{
			keyCorruptionStatusList.add (
				0 == _cEntry.key().compareTo (
					entry.key()
				)
			);
		}

		return keyCorruptionStatusList;
	}

	/**
	 * Indicate if the Node is a Root
	 * 
	 * @return TRUE - The Node is a Root
	 */

	public boolean isRoot()
	{
		return null == _parent;
	}

	/**
	 * Indicate if the Node is a Leaf
	 * 
	 * @return TRUE - The Node is a Leaf
	 */

	public boolean isLeaf()
	{
		return null == _left && null == _right;
	}

	/**
	 * Sift the Node
	 * 
	 * @return TRUE - The Node has been successfully sifted
	 */

	public boolean sift()
	{
		while (shouldBeSifted())
		{
			if (null == _left || (null != _right && (
				_minHeap ? -1 != _left.cEntry().key().compareTo (
					_right.cEntry().key()
				) : 1 != _left.cEntry().key().compareTo (
					_right.cEntry().key()
				)
			)))
			{
				if (!swapChildren())
				{
					return false;
				}
			}

			java.util.List<org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM>> leftEntryList =
				_left.entryList();

			int leftElementSize = leftEntryList.size();

			for (int leftElementIndex = leftElementSize - 1;
				leftElementIndex >= 0;
				--leftElementIndex)
			{
				_entryList.add (
					leftEntryList.remove (
						leftElementIndex
					)
				);
			}

			_cEntry = _left.cEntry();

			if (_left.isLeaf())
			{
				_left = null;
			}
			else
			{
				if (!_left.sift())
				{
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Perform a BFS Walk through the Nodes and retrieve them
	 * 
	 * @return The List of Nodes
	 */

	public java.util.List<KaplanZwickBinaryNode<KEY, ITEM>> bfsWalk()
	{
		java.util.List<KaplanZwickBinaryNode<KEY, ITEM>> elementList =
			new java.util.ArrayList<KaplanZwickBinaryNode<KEY, ITEM>>();

		java.util.List<KaplanZwickBinaryNode<KEY, ITEM>> elementQueue =
			new java.util.ArrayList<KaplanZwickBinaryNode<KEY, ITEM>>();

		elementQueue.add (
			this
		);

		while (!elementQueue.isEmpty())
		{
			KaplanZwickBinaryNode<KEY, ITEM> node = elementQueue.get (
				0
			);

			elementQueue.remove (
				0
			);

			elementList.add (
				node
			);

			KaplanZwickBinaryNode<KEY, ITEM> left = node.left();

			if (null != left)
			{
				elementQueue.add (
					left
				);
			}

			KaplanZwickBinaryNode<KEY, ITEM> right = node.right();

			if (null != right)
			{
				elementQueue.add (
					right
				);
			}
		}

		return elementList;
	}

	/**
	 * Perform a BFS Walk through the Nodes and retrieve them
	 * 
	 * @return The List of Nodes
	 */

	public java.util.List<org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM>> childKeyList()
	{
		java.util.List<org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM>> childKeyList =
			new java.util.ArrayList<org.drip.graph.heap.PriorityQueueEntry<KEY, ITEM>>();

		java.util.List<KaplanZwickBinaryNode<KEY, ITEM>> bfsWalk = bfsWalk();

		for (KaplanZwickBinaryNode<KEY, ITEM> node : bfsWalk)
		{
			childKeyList.addAll (
				node.entryList()
			);
		}

		return childKeyList;
	}

	@Override public java.lang.String toString()
	{
		String state = "{Rank = " + _k + "; ckey = " + _cEntry.key() + "; List = " + _entryList;

		state = state + "; Parent = " + (null == _parent ? "null" : _parent.cEntry().key());

		state = state + "; Left = " + (
			null == _left ? "null" : _left.cEntry().key() + " @ " + _left.childKeyList() + " @ " +
				_left.entryList()
		);

		state = state + "; Right = " + (
			null == _right ? "null" : _right.cEntry().key() + " @ " + _right.childKeyList() + " @ " +
				_right.entryList()
		);

		return state + "}";
	}
}
