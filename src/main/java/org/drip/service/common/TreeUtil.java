
package org.drip.service.common;

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
 * <i>TreeUtil</i> implements a Tree Utility Functions.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common">Assorted Data Structures Support Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TreeUtil
{

	public class TreeNode
	{
		private TreeNode _left = null;
		private TreeNode _right = null;
		private double _value = java.lang.Double.NaN;

		public TreeNode (
			final double value,
			final TreeNode left,
			final TreeNode right)
		{
			_left = left;
			_right = right;
			_value = value;
		}

		public double value()
		{
			return _value;
		}

		public TreeNode left()
		{
			return _left;
		}

		public TreeNode right()
		{
			return _right;
		}
	}

	public static class DiameterHeightPair
	{
		private int _height = -1;
		private int _diameter = 0;

		public DiameterHeightPair (
			final int height,
			final int diameter)
			throws java.lang.Exception
		{
			if (-1 > (_height = height) ||
				0 > (_diameter = diameter))
			{
				throw new java.lang.Exception (
					"DiameterHeightPair Constructor => Invalid Inputs"
				);
			}
		}

		public int height()
		{
			return _height;
		}

		public int diameter()
		{
			return _diameter;
		}
	}

	public static final java.util.List<java.lang.Double> RightSideView (
		final TreeNode root)
	{
		java.util.List<java.lang.Double> rightNodeList = new java.util.ArrayList<java.lang.Double>();

		if (null == root)
		{
			return rightNodeList;
		}

		java.util.Deque<TreeNode> nodeQueue = new java.util.ArrayDeque<TreeNode>();

		nodeQueue.add (
			root
		);

		while (!nodeQueue.isEmpty())
		{
			int queueSize = nodeQueue.size();

			for (int i = 0;
				i < queueSize;
				++i)
			{
				TreeNode currentNode = nodeQueue.poll();

				if (i == queueSize - 1)
				{
					rightNodeList.add (
						currentNode.value()
					);

					TreeNode left = currentNode.left();

					if (null != left)
					{
						nodeQueue.add (
							left
						);
					}

					TreeNode right = currentNode.right();

					if (null != right)
					{
						nodeQueue.add (
							right
						);
					}
				}
			}
		}

		return rightNodeList;
	}

	public static final DiameterHeightPair TreeDiameter (
		final TreeNode root)
	{
		if (null == root)
		{
			try
			{
				return new DiameterHeightPair (
					-1,
					0
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}

			return null;
		}

		DiameterHeightPair leftPair = TreeDiameter (
			root.left()
		);

		DiameterHeightPair rightPair = TreeDiameter (
			root.right()
		);

		try
		{
			return new DiameterHeightPair (
				java.lang.Math.max (
					leftPair.height(), rightPair.height()
				) + 1,
				java.lang.Math.max (
					java.lang.Math.max (
						leftPair.diameter(),
						rightPair.diameter()
					),
					leftPair.height() + rightPair.height() + 2
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Given a binary tree, return the minimum number of edits to make the value of each node equal to the
	 *  average of its direct children's. Note that you can only update the value of each tree node, and
	 *  changing the tree structure is not allowed.
	 * 
	 * @param node The Root Node
	 * 
	 * @return Minimum Edits for the "Average" Tree
	 */

	public static final int MinimumEditsForAverage (
		final TreeNode node)
	{
		if (null == node)
		{
			return 0;
		}

		TreeNode left = node.left();

		TreeNode right = node.right();

		if (null == left && null == right)
		{
			return 0;
		}

		int editCount = MinimumEditsForAverage (
			left
		) + MinimumEditsForAverage (
			right
		);

		if (null == left)
		{
			double rightValue = right._value;

			if (node._value != rightValue)
			{
				node._value = rightValue;
				return editCount + 1;
			}

			return editCount;
		}

		if (null == right)
		{
			double leftValue = left._value;

			if (node._value != leftValue)
			{
				node._value = leftValue;
				return editCount + 1;
			}

			return editCount;
		}

		double averageValue = (right._value + left._value) / 2;

		if (node._value != averageValue)
		{
			node._value = averageValue;
			return editCount + 1;
		}

		return editCount;
	}

	/**
	 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
	 * 
	 * A <b>valid BST</b> is defined as follows:
	 * 
	 *  The left subtree of a node contains only nodes with keys <b>less than</b> the node's key.
	 *  The right subtree of a node contains only nodes with keys <b>greater than</b> the node's key.
	 *  Both the left and right subtrees must also be binary search trees.
	 * 
	 * @param node Current Node
	 * 
	 * @return TRUE - Node represents a Strict BST
	 */

	public static final boolean ValidateIsStrictBST (
		final TreeNode node)
	{
		if (null == node) return true;

		if (null != node._left && node._left._value >= node._value) return false;

		if (!ValidateIsStrictBST (node._left)) return false;

		if (null != node._right && node._right._value <= node._value) return false;

		return ValidateIsStrictBST (node._left);
	}
}
