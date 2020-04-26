
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
 * <i>BinaryTreePriorityQueue</i> implements a Binary Heap Based off of a Binary Tree. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/heap/README.md">Heap Based Priority Queue Implementations</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BinaryTreePriorityQueue
{
	private boolean _minHeap = false;
	private org.drip.graph.heap.BinaryTreeNode _top = null;

	private boolean heapPropertyValid (
		final org.drip.graph.heap.BinaryTreeNode above,
		final org.drip.graph.heap.BinaryTreeNode below)
	{
		return _minHeap ? above.key() <= below.key() : above.key() >= below.key();
	}

	private double replaceTop (
		final org.drip.graph.heap.BinaryTreeNode node)
	{
		double key = _top.key();

		if (node == _top)
		{
			_top = null;
		}
		else
		{
			_top.setKey (
				node.key()
			);
		}

		return key;
	}

	private boolean removeLeafNode (
		final org.drip.graph.heap.BinaryTreeNode leafNode)
	{
		org.drip.graph.heap.BinaryTreeNode parent = leafNode.parent();

		if (null == parent)
		{
			return true;
		}

		if (!leafNode.setParent (
			null
		))
		{
			return false;
		}

		return leafNode.isRightChild() ? parent.setRight (
			null
		) : parent.setLeft (
			null
		);
	}

	private final boolean swapNodeAndParent (
		final org.drip.graph.heap.BinaryTreeNode node,
		final org.drip.graph.heap.BinaryTreeNode parent)
	{
		if (null == node || null == parent)
		{
			return false;
		}

		int nodeOldLevel = node.level();

		int parentOldLevel = parent.level();

		boolean nodeOldIsRightChild = node.isRightChild();

		boolean parentOldIsRightChild = parent.isRightChild();

		org.drip.graph.heap.BinaryTreeNode nodeOldLeft = node.left();

		org.drip.graph.heap.BinaryTreeNode nodeOldRight = node.right();

		org.drip.graph.heap.BinaryTreeNode parentOldLeft = parent.left();

		org.drip.graph.heap.BinaryTreeNode parentOldRight = parent.right();

		org.drip.graph.heap.BinaryTreeNode parentOldParent = parent.parent();

		if (!node.setLevel (
			parentOldLevel
		))
		{
			return false;
		}

		if (!node.setParent (
			parentOldParent
		))
		{
			return false;
		}

		if (!node.setAsRightChild (
			parentOldIsRightChild
		))
		{
			return false;
		}

		if (null == parentOldParent)
		{
			_top = node;
		}
		else
		{
			if (parentOldIsRightChild)
			{
				if (!parentOldParent.setRight (
					node
				))
				{
					return false;
				}
			}
			else
			{
				if (!parentOldParent.setLeft (
					node
				))
				{
					return false;
				}
			}
		}

		if (nodeOldIsRightChild)
		{
			if (!node.setLeft (
				parentOldLeft
			))
			{
				return false;
			}

			if (null != parentOldLeft)
			{
				if (!parentOldLeft.setParent (
					node
				))
				{
					return false;
				}
			}

			if (!node.setRight (
				parent
			))
			{
				return false;
			}
		}
		else
		{
			if (!node.setLeft (
				parent
			))
			{
				return false;
			}

			if (!node.setRight (
				parentOldRight
			))
			{
				return false;
			}

			if (null != parentOldRight)
			{
				if (!parentOldRight.setParent (
					node
				))
				{
					return false;
				}
			}
		}

		if (!parent.setLevel (
			nodeOldLevel
		))
		{
			return false;
		}

		if (!parent.setParent (
			node
		))
		{
			return false;
		}

		if (!parent.setAsRightChild (
			nodeOldIsRightChild
		))
		{
			return false;
		}

		if (!parent.setLeft (
			nodeOldLeft
		))
		{
			return false;
		}

		if (!parent.setRight (
			nodeOldRight
		))
		{
			return false;
		}

		if (null != nodeOldLeft)
		{
			if (!nodeOldLeft.setParent (
				parent
			))
			{
				return false;
			}
		}

		if (null != nodeOldRight)
		{
			if (!nodeOldRight.setParent (
				parent
			))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * BinaryTreePriorityQueue Constructor
	 * 
	 * @param minHeap TRUE - Indicates that Heap is a Min Heap
	 */

	public BinaryTreePriorityQueue (
		final boolean minHeap)
	{
		_minHeap = minHeap;
	}

	/**
	 * Retrieve the Top Node
	 * 
	 * @return The Top Node
	 */

	public org.drip.graph.heap.BinaryTreeNode top()
	{
		return _top;
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
	 * Maintain the Binary Heap Property from the Node to the Top
	 * 
	 * @param node Specified Node
	 * 
	 * @return TRUE - The Heap Property is successfully maintained
	 */

	public boolean maintainHeapPropertyBottomUp (
		org.drip.graph.heap.BinaryTreeNode node)
	{
		if (null == node)
		{
			return true;
		}

		org.drip.graph.heap.BinaryTreeNode parent = node.parent();

		while (null != parent &&
			!heapPropertyValid (
				parent,
				node
			)
		)
		{
			if (!swapNodeAndParent (
				node,
				parent
			))
			{
				return false;
			}

			parent = node.parent();
		}

		return true;
	}

	/**
	 * Maintain the Binary Heap Property from the Node to the Bottom
	 * 
	 * @param node Specified Node
	 * 
	 * @return TRUE - The Heap Property is successfully maintained
	 */

	public boolean maintainHeapPropertyTopDown (
		org.drip.graph.heap.BinaryTreeNode node)
	{
		if (null == node)
		{
			return true;
		}

		org.drip.graph.heap.BinaryTreeNode nextChild = _minHeap ? node.smallerChild() : node.largerChild();

		while (null != nextChild &&
			!heapPropertyValid (
				node,
				nextChild
			)
		)
		{
			if (!swapNodeAndParent (
				nextChild,
				node
			))
			{
				return false;
			}

			nextChild = _minHeap ? node.smallerChild() : node.largerChild();
		}

		return true;
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
		if (null == _top)
		{
			try
			{
				_top = new org.drip.graph.heap.BinaryTreeNode (
					key
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return false;
			}

			return _top.setParent (
				null
			) && _top.setLevel (
				0
			) && _top.setLeft (
				null
			) && _top.setRight (
				null
			);
		}

		org.drip.graph.heap.BinaryTreeNode node = null;
		org.drip.graph.heap.BinaryTreeNode newNode = null;

		java.util.List<org.drip.graph.heap.BinaryTreeNode> elementQueue =
			new java.util.ArrayList<org.drip.graph.heap.BinaryTreeNode>();

		elementQueue.add (
			_top
		);

		while (!elementQueue.isEmpty())
		{
			node = elementQueue.get (
				0
			);

			elementQueue.remove (
				0
			);

			if (node.zeroOrOneChildren())
			{
				break;
			}

			elementQueue.add (
				node.left()
			);

			elementQueue.add (
				node.right()
			);
		}

		boolean isRightChild = null == node.right();

		try
		{
			newNode = new org.drip.graph.heap.BinaryTreeNode (
				key
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		if (!newNode.setParent (
				node
			) || !newNode.setLevel (
				node.level() + 1
			) || !newNode.setLeft (
				null
			) || !newNode.setRight (
				null
			) || !newNode.setAsRightChild (
				isRightChild
			)
		)
		{
			return false;
		}

		if (isRightChild)
		{
			if (!node.setRight (
				newNode
			))
			{
				return false;
			}
		}
		else
		{
			if (!node.setLeft (
				newNode
			))
			{
				return false;
			}
		}

		return maintainHeapPropertyBottomUp (
			newNode
		);
	}

	/**
	 * Extract the Top from the Heap
	 * 
	 * @return The Top Key in the Heap
	 * 
	 * @throws java.lang.Exception Thrown if the Heap Maximum cannot be extracted
	 */

	public double extractTop()
		throws java.lang.Exception
	{
		if (null == _top)
		{
			throw new java.lang.Exception (
				"BinaryHeap::extractTop => Heap is Empty"
			);
		}

		org.drip.graph.heap.BinaryTreeNode node = null;

		java.util.List<org.drip.graph.heap.BinaryTreeNode> elementQueue =
			new java.util.ArrayList<org.drip.graph.heap.BinaryTreeNode>();

		elementQueue.add (
			_top
		);

		while (!elementQueue.isEmpty())
		{
			node = elementQueue.get (
				0
			);

			elementQueue.remove (
				0
			);

			if (node.isLeaf())
			{
				break;
			}

			org.drip.graph.heap.BinaryTreeNode left = node.left();

			if (null != left)
			{
				elementQueue.add (
					left
				);
			}

			org.drip.graph.heap.BinaryTreeNode right = node.right();

			if (null != right)
			{
				elementQueue.add (
					right
				);
			}
		}

		if (!removeLeafNode (
			node
		))
		{
			throw new java.lang.Exception (
				"BinaryHeap::extractTop => Cannot remove Leaf Node"
			);
		}

		double topNodeKey = replaceTop (
			node
		);

		if (null != _top)
		{
			if (!maintainHeapPropertyTopDown (
				_top
			))
			{
				throw new java.lang.Exception (
					"BinaryHeap::extractTop => Cannot maintain Heap Property"
				);
			}
		}

		return topNodeKey;
	}

	/**
	 * Perform a BFS Walk through the Heap and retrieve the Nodes
	 * 
	 * @return The List of Nodes
	 */

	public java.util.List<org.drip.graph.heap.BinaryTreeNode> bfsWalk()
	{
		java.util.List<org.drip.graph.heap.BinaryTreeNode> elementList =
			new java.util.ArrayList<org.drip.graph.heap.BinaryTreeNode>();

		if (null == _top)
		{
			return elementList;
		}

		java.util.List<org.drip.graph.heap.BinaryTreeNode> elementQueue =
			new java.util.ArrayList<org.drip.graph.heap.BinaryTreeNode>();

		elementQueue.add (
			_top
		);

		while (!elementQueue.isEmpty())
		{
			org.drip.graph.heap.BinaryTreeNode node = elementQueue.get (
				0
			);

			elementQueue.remove (
				0
			);

			elementList.add (
				node
			);

			org.drip.graph.heap.BinaryTreeNode left = node.left();

			if (null != left)
			{
				elementQueue.add (
					left
				);
			}

			org.drip.graph.heap.BinaryTreeNode right = node.right();

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
	 * Indicate if the Heap is Empty
	 * 
	 * @return TRUE - The Heap is Empty
	 */

	public boolean isEmpty()
	{
		return null == _top;
	}

	/**
	 * Retrieve the Node with a Value Corresponding to the Input
	 * 
	 * @param value The Input Value
	 * 
	 * @return TRUE - The Node with a Value Corresponding to the Input
	 */

	public org.drip.graph.heap.BinaryTreeNode nodeWithValue (
		final double value)
	{
		if (java.lang.Double.isNaN (
			value
		))
		{
			return null;
		}

		if (null == _top)
		{
			return null;
		}

		java.util.List<org.drip.graph.heap.BinaryTreeNode> elementQueue =
			new java.util.ArrayList<org.drip.graph.heap.BinaryTreeNode>();

		elementQueue.add (
			_top
		);

		while (!elementQueue.isEmpty())
		{
			org.drip.graph.heap.BinaryTreeNode node = elementQueue.get (
				0
			);

			elementQueue.remove (
				0
			);

			if (value == node.key())
			{
				return node;
			}

			org.drip.graph.heap.BinaryTreeNode left = node.left();

			if (null != left)
			{
				elementQueue.add (
					left
				);
			}

			org.drip.graph.heap.BinaryTreeNode right = node.right();

			if (null != right)
			{
				elementQueue.add (
					right
				);
			}
		}

		return null;
	}
}
