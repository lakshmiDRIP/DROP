
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

public class KaplanZwickBinaryNode
{
	private int _k = -1;
	private int _r = -1;
	private boolean _minHeap = false;
	private KaplanZwickBinaryNode _left = null;
	private double _ckey = java.lang.Double.NaN;
	private KaplanZwickBinaryNode _right = null;
	private KaplanZwickBinaryNode _parent = null;
	private java.util.List<java.lang.Double> _keyList = null;
	private org.drip.graph.softheap.KaplanZwickTargetSize _targetSize = null;

	private boolean swapChildren()
	{
		KaplanZwickBinaryNode tmp = _left;
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

		// return _keyList.size() < _targetSize.estimate() / 2;

		return _keyList.size() < java.lang.Math.ceil (
			0.5 * _targetSize.estimate()
		);
	}

	/**
	 * Combine Two Root Nodes of Rank k each to a Root Node of Rank k + 1
	 * 
	 * @param node1 Node #1
	 * @param node2 Node #2
	 * 
	 * @return The Combined Root Node of Rank k + 1
	 */

	public static final KaplanZwickBinaryNode CombineRootNodePair (
		final KaplanZwickBinaryNode node1,
		final KaplanZwickBinaryNode node2)
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
			return null;
		}

		boolean minHeap = node1.minHeap();

		if (minHeap != node2.minHeap())
		{
			return null;
		}

		KaplanZwickBinaryNode newRoot = null;

		try
		{
			newRoot = new KaplanZwickBinaryNode (
				minHeap,
				r,
				k + 1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		double ckey1 = node1.ckey();

		double ckey2 = node2.ckey();

		double ckey = ckey1 < ckey2 ? ckey1 : ckey2;

		if (!minHeap)
		{
			ckey = ckey1 > ckey2 ? ckey1 : ckey2;
		}

		return newRoot.setCKey (
			ckey
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
	 * @param minHeap Min Heap Flag
	 * @param errorRate Error Rate
	 * @param k Rank
	 * @param left Left Child
	 * @param right Right Child
	 * @param parent Parent
	 * 
	 * @return KaplanZwickBinaryNode Instance from the Error Rate
	 */

	public static final KaplanZwickBinaryNode FromErrorRate (
		final boolean minHeap,
		final double errorRate,
		final int k,
		final KaplanZwickBinaryNode left,
		final KaplanZwickBinaryNode right,
		final KaplanZwickBinaryNode parent)
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
			KaplanZwickBinaryNode node = new KaplanZwickBinaryNode (
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
	 * Construct a Leaf Root Node of a New Tree with a single Key
	 * 
	 * @param minHeap Min Heap Flag
	 * @param r The R Parameter
	 * @param key The Key
	 * 
	 * @return The Leaf Root of a New Tree with a single Key
	 */

	public static final KaplanZwickBinaryNode LeafRoot (
		final boolean minHeap,
		final int r,
		final double key)
	{
		try
		{
			KaplanZwickBinaryNode emptyRoot = new KaplanZwickBinaryNode (
				minHeap,
				r,
				0
			);

			return emptyRoot.addKey (
				key
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

		_keyList = new java.util.ArrayList<java.lang.Double>();
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

	public KaplanZwickBinaryNode left()
	{
		return _left;
	}

	/**
	 * Retrieve the Right Tree
	 * 
	 * @return The right Tree
	 */

	public KaplanZwickBinaryNode right()
	{
		return _right;
	}

	/**
	 * Retrieve the Parent Tree
	 * 
	 * @return The Parent Tree
	 */

	public KaplanZwickBinaryNode parent()
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
	 * Retrieve the List of Keys
	 * 
	 * @return The List of Keys
	 */

	public java.util.List<java.lang.Double> keyList()
	{
		return _keyList;
	}

	/**
	 * Retrieve the ckey
	 * 
	 * @return The ckey
	 */

	public double ckey()
	{
		return _ckey;
	}

	/**
	 * Retrieve the Current Size of the List
	 * 
	 * @return Current Size of the List
	 */

	public int currentSize()
	{
		return _keyList.size();
	}

	/**
	 * Set the ckey of the Current Node
	 * 
	 * @param ckey The ckey
	 * 
	 * @return TRUE - The ckey of the Current Node successfully set
	 */

	public boolean setCKey (
		final double ckey)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			ckey
		))
		{
			return false;
		}

		_ckey = ckey;
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
		final KaplanZwickBinaryNode parent)
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
		final KaplanZwickBinaryNode left)
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
		final KaplanZwickBinaryNode right)
	{
		_right = right;
		return true;
	}

	/**
	 * Add a Key to the Key List
	 * 
	 * @param key The Key
	 * 
	 * @return TRUE - The Key is successfully added to the Key List
	 */

	public boolean addKey (
		final double key)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			key
		))
		{
			return false;
		}

		_keyList.add (
			key
		);

		if (org.drip.numerical.common.NumberUtil.IsValid (
			_ckey
		))
		{
			_ckey = key;
		}
		else
		{
			if (_minHeap)
			{
				_ckey = _ckey > key ? _ckey : key;
			}
			else
			{
				_ckey = _ckey < key ? _ckey : key;
			}
		}

		return true;
	}

	/**
	 * Retrieve the Top Key
	 * 
	 * @return The Top Key
	 */

	public double removeKey()
	{
		// sift();

		return _keyList.remove (
			0
		);
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

		for (double key : _keyList)
		{
			keyCorruptionStatusList.add (
				key == _ckey
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
				_minHeap ? _left.ckey() >= _right.ckey() : _left.ckey() <= _right.ckey()
			)))
			{
				if (!swapChildren())
				{
					return false;
				}
			}

			java.util.List<java.lang.Double> leftKeyList = _left.keyList();

			int leftElementSize = leftKeyList.size();

			for (int leftElementIndex = leftElementSize - 1;
				leftElementIndex >= 0;
				--leftElementIndex)
			{
				_keyList.add (
					leftKeyList.remove (
						leftElementIndex
					)
				);
			}

			_ckey = _left.ckey();

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

	public java.util.List<KaplanZwickBinaryNode> bfsWalk()
	{
		java.util.List<KaplanZwickBinaryNode> elementList = new java.util.ArrayList<KaplanZwickBinaryNode>();

		java.util.List<KaplanZwickBinaryNode> elementQueue =
			new java.util.ArrayList<KaplanZwickBinaryNode>();

		elementQueue.add (
			this
		);

		while (!elementQueue.isEmpty())
		{
			KaplanZwickBinaryNode node = elementQueue.get (
				0
			);

			elementQueue.remove (
				0
			);

			elementList.add (
				node
			);

			KaplanZwickBinaryNode left = node.left();

			if (null != left)
			{
				elementQueue.add (
					left
				);
			}

			KaplanZwickBinaryNode right = node.right();

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

	public java.util.List<java.lang.Double> childKeyList()
	{
		java.util.List<java.lang.Double> childKeyList = new java.util.ArrayList<java.lang.Double>();

		java.util.List<KaplanZwickBinaryNode> bfsWalk = bfsWalk();

		for (KaplanZwickBinaryNode node : bfsWalk)
		{
			childKeyList.addAll (
				node.keyList()
			);
		}

		return childKeyList;
	}

	/**
	 * Compute the Implied Error Rate
	 * 
	 * @return The Implied Error Rate
	 */

	public double impliedErrorRate()
	{
		return java.lang.Math.pow (
			2,
			5. - _r
		);
	}

	@Override public java.lang.String toString()
	{
		String state = "{Rank = " + _k + "; ckey = " + _ckey + "; List = " + _keyList;

		state = state + "; Parent = " + (null == _parent ? "null" : _parent.ckey());

		state = state + "; Left = " + (
			null == _left ? "null" : _left.ckey() + " @ " + _left.childKeyList() + " @ " + _left.keyList()
		);

		state = state + "; Right = " + (
			null == _right ? "null" : _right.ckey() + " @ " + _right.childKeyList() + " @ " + _right.keyList()
		);

		return state + "}";
	}
}
