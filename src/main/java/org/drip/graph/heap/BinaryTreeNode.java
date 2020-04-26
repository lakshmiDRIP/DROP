
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
 * <i>BinaryTreeNode</i> implements a Node in a Binary Tree. The References are:
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

public class BinaryTreeNode
{
	private int _level = 0;
	private BinaryTreeNode _left = null;
	private BinaryTreeNode _right = null;
	private BinaryTreeNode _parent = null;
	private boolean _isRightChild = false;
	private double _key = java.lang.Double.NaN;

	/**
	 * BinaryTreeNode Constructor
	 * 
	 * @param key Node Key
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BinaryTreeNode (
		final double key)
		throws java.lang.Exception
	{
		if (java.lang.Double.isNaN (
				_key = key
			)
		)
		{
			throw new java.lang.Exception (
				"BinaryTreeNode Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Level
	 * 
	 * @return The Level
	 */

	public int level()
	{
		return _level;
	}

	/**
	 * Retrieve the Parent
	 * 
	 * @return The Parent
	 */

	public BinaryTreeNode parent()
	{
		return _parent;
	}

	/**
	 * Retrieve the Left Child
	 * 
	 * @return The Left Child
	 */

	public BinaryTreeNode left()
	{
		return _left;
	}

	/**
	 * Retrieve the Right Child
	 * 
	 * @return The Right Child
	 */

	public BinaryTreeNode right()
	{
		return _right;
	}

	/**
	 * Retrieve the Node Key
	 * 
	 * @return The Node Key
	 */

	public double key()
	{
		return _key;
	}

	/**
	 * Indicate if the Node is a Right Child of the Parent
	 * 
	 * @return TRUE - Node is a Right Child
	 */

	public boolean isRightChild()
	{
		return _isRightChild;
	}

	/**
	 * Set the Key of the Node
	 * 
	 * @param key The Key
	 * 
	 * @return TRUE - The Node's Key successfully set
	 */

	public boolean setKey (
		final double key)
	{
		if (java.lang.Double.isNaN (
				key
			)
		)
		{
			return false;
		}

		_key = key;
		return true;
	}

	/**
	 * Set the Level of the Node
	 * 
	 * @param level The Level
	 * 
	 * @return TRUE - The Node's Level successfully set
	 */

	public boolean setLevel (
		final int level)
	{
		if (0 > level)
		{
			return false;
		}

		_level = level;
		return true;
	}

	/**
	 * Set the Parent of the Node
	 * 
	 * @param parent The Parent
	 * 
	 * @return TRUE - The Node's Parent successfully set
	 */

	public boolean setParent (
		final BinaryTreeNode parent)
	{
		_parent = parent;
		return true;
	}

	/**
	 * Set the Left Child of the Node
	 * 
	 * @param left The Left Child
	 * 
	 * @return TRUE - The Node's Left Child successfully set
	 */

	public boolean setLeft (
		final BinaryTreeNode left)
	{
		_left = left;
		return true;
	}

	/**
	 * Set the Right Child of the Node
	 * 
	 * @param right The Right Child
	 * 
	 * @return TRUE - The Node's Right Child successfully set
	 */

	public boolean setRight (
		final BinaryTreeNode right)
	{
		_right = right;
		return true;
	}

	/**
	 * Set the Node as the Right Child of the Parent
	 * 
	 * @param isRightChild TRUE - The Node is a Right Child
	 * 
	 * @return TRUE - The Node as the Right Child of the Parent is successfully set
	 */

	public boolean setAsRightChild (
		final boolean isRightChild)
	{
		_isRightChild = isRightChild;
		return true;
	}

	/**
	 * Indicate if the Node has 0 or 1 Children
	 * 
	 * @return TRUE - The Node has 0 or 1 Children
	 */

	public boolean zeroOrOneChildren()
	{
		return null == _left || null == _right;
	}

	/**
	 * Indicate if the Node is Leaf
	 * 
	 * @return TRUE - The Node is Leaf
	 */

	public boolean isLeaf()
	{
		return null == _left && null == _right;
	}

	/**
	 * Retrieve the Child Node with the Smaller Value
	 * 
	 * @return The Child Node with the Smaller Value
	 */

	public BinaryTreeNode smallerChild()
	{
		if (null == _left && null == _right)
		{
			return null;
		}

		if (null == _left)
		{
			return _right;
		}

		if (null == _right)
		{
			return _left;
		}

		return _left.key() < _right.key() ? _left : _right;
	}

	/**
	 * Retrieve the Child Node with the Larger Value
	 * 
	 * @return The Child Node with the Larger Value
	 */

	public BinaryTreeNode largerChild()
	{
		if (null == _left && null == _right)
		{
			return null;
		}

		if (null == _left)
		{
			return _right;
		}

		if (null == _right)
		{
			return _left;
		}

		return _left.key() > _right.key() ? _left : _right;
	}

	@Override public java.lang.String toString()
	{
		return "{"
			+ _key + " | " + _level + " | " + (_isRightChild ? "R" : "L") + " | " +
				(null == _parent ? "" : _parent.key()) +
		"}";
	}
}
