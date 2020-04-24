
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
 * <i>BinaryHeap</i> implements a Binary Heap Based Priority Queue. The References are:
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

public class BinaryHeap
{
	private org.drip.graph.heap.BinaryNode _top = null;

	private double replaceTop (
		final org.drip.graph.heap.BinaryNode node)
	{
		double value = _top.value();

		if (node == _top)
		{
			_top = null;
		}
		else
		{
			_top.setValue (
				node.value()
			);
		}

		return value;
	}

	private boolean removeLeafNode (
		final org.drip.graph.heap.BinaryNode leafNode)
	{
		org.drip.graph.heap.BinaryNode parent = leafNode.parent();

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
		final org.drip.graph.heap.BinaryNode node5,
		final org.drip.graph.heap.BinaryNode node4)
	{
		// System.out.println ("\t\t\t\t" + node5 + " || " + node4);

		if (null == node5 || null == node4)
		{
			return false;
		}

		int oldNode5Level = node5.level();

		boolean isOldNode5RightChild = node5.isRightChild();

		boolean isOldNode4RightChild = node4.isRightChild();

		org.drip.graph.heap.BinaryNode oldNode5Left = node5.left();

		org.drip.graph.heap.BinaryNode oldNode5Right = node5.right();

		org.drip.graph.heap.BinaryNode oldNode4Parent = node4.parent();

		node5.setParent (
			oldNode4Parent
		);

		node5.setLevel (
			node4.level()
		);

		if (isOldNode5RightChild)
		{
			node5.setLeft (
				node4.left()
			);

			node5.setRight (
				node4
			);
		}
		else
		{
			node5.setLeft (
				node4
			);

			node5.setRight (
				node4.right()
			);
		}

		node5.setAsRightChild (
			isOldNode4RightChild
		);

		if (null != oldNode4Parent)
		{
			if (isOldNode4RightChild)
			{
				oldNode4Parent.setRight (
					node5
				);
			}
			else
			{
				oldNode4Parent.setLeft (
					node5
				);
			}
		}
		else
		{
			_top = node5;
		}

		node4.setParent (
			node5
		);

		node4.setLevel (
			oldNode5Level
		);

		node4.setLeft (
			oldNode5Left
		);

		node4.setRight (
			oldNode5Right
		);

		node4.setAsRightChild (
			isOldNode5RightChild
		);

		if (null != oldNode5Left)
		{
			oldNode5Left.setParent (
				node4
			);
		}

		if (null != oldNode5Right)
		{
			oldNode5Right.setParent (
				node4
			);
		}

		return true;
	}

	/**
	 * BinaryHeap Constructor
	 */

	public BinaryHeap()
	{
	}

	/**
	 * Retrieve the Top Node
	 * 
	 * @return The Top Node
	 */

	public org.drip.graph.heap.BinaryNode top()
	{
		return _top;
	}

	/**
	 * Maintain the Binary Heap Property from the Node to the Top
	 * 
	 * @param node Specified Node
	 * 
	 * @return TRUE - The Heap Property is successfully maintained
	 */

	public boolean maintainHeapPropertyBottomUp (
		org.drip.graph.heap.BinaryNode node)
	{
		if (null == node)
		{
			return true;
		}

		org.drip.graph.heap.BinaryNode parent = node.parent();

		while (null != parent && node.value() >= parent.value())
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
		org.drip.graph.heap.BinaryNode node)
	{
		if (null == node)
		{
			return true;
		}

		org.drip.graph.heap.BinaryNode largerChild = node.largerChild();

		while (null != largerChild && node.value() <= largerChild.value())
		{
			if (!swapNodeAndParent (
				largerChild,
				node
			))
			{
				return false;
			}

			largerChild = node.largerChild();
		}

		return true;
	}

	/**
	 * Insert the Specified Value into the Heap
	 * 
	 * @param value Value
	 * 
	 * @return TRUE - The Value successfully inserted
	 */

	public boolean insert (
		final double value)
	{
		if (java.lang.Double.isNaN (
			value
		))
		{
			return false;
		}

		if (null == _top)
		{
			try
			{
				_top = new org.drip.graph.heap.BinaryNode (
					value
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

		org.drip.graph.heap.BinaryNode node = null;
		org.drip.graph.heap.BinaryNode newNode = null;

		java.util.List<org.drip.graph.heap.BinaryNode> elementQueue =
			new java.util.ArrayList<org.drip.graph.heap.BinaryNode>();

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
			newNode = new org.drip.graph.heap.BinaryNode (
				value
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
	 * Extract the Maximum from the Heap
	 * 
	 * @return The Maximum Value in the Heap
	 * 
	 * @throws java.lang.Exception Thrown if the Heap Maximum cannot be extracted
	 */

	public double extractMax()
		throws java.lang.Exception
	{
		if (null == _top)
		{
			throw new java.lang.Exception (
				"BinaryHeap::extractMax => Heap is Empty"
			);
		}

		org.drip.graph.heap.BinaryNode node = null;

		java.util.List<org.drip.graph.heap.BinaryNode> elementQueue =
			new java.util.ArrayList<org.drip.graph.heap.BinaryNode>();

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

			org.drip.graph.heap.BinaryNode left = node.left();

			if (null != left)
			{
				elementQueue.add (
					left
				);
			}

			org.drip.graph.heap.BinaryNode right = node.right();

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
				"BinaryHeap::extractMax => Cannot remove Leaf Node"
			);
		}

		double topNodeValue = replaceTop (
			node
		);

		if (null != _top)
		{
			if (!maintainHeapPropertyTopDown (
				_top
			))
			{
				throw new java.lang.Exception (
					"BinaryHeap::extractMax => Cannot maintain Heap Property"
				);
			}
		}

		return topNodeValue;
	}

	/**
	 * Perform a BFS Walk through the Heap and retrieve the Nodes
	 * 
	 * @return The List of Nodes
	 */

	public java.util.List<org.drip.graph.heap.BinaryNode> bfsWalk()
	{
		java.util.List<org.drip.graph.heap.BinaryNode> elementList =
			new java.util.ArrayList<org.drip.graph.heap.BinaryNode>();

		if (null == _top)
		{
			return elementList;
		}

		java.util.List<org.drip.graph.heap.BinaryNode> elementQueue =
			new java.util.ArrayList<org.drip.graph.heap.BinaryNode>();

		elementQueue.add (
			_top
		);

		while (!elementQueue.isEmpty())
		{
			org.drip.graph.heap.BinaryNode node = elementQueue.get (
				0
			);

			elementQueue.remove (
				0
			);

			elementList.add (
				node
			);

			org.drip.graph.heap.BinaryNode left = node.left();

			if (null != left)
			{
				elementQueue.add (
					left
				);
			}

			org.drip.graph.heap.BinaryNode right = node.right();

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
}
