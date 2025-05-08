
package org.drip.service.common;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>TreeUtil</i> implements Tree Utility Functions. It implements the following Functions:
 * <ul>
 * 		<li><i>TreeNode</i> implements Linked Tree Node</li>
 * 		<ul>
 * 			<li><i>TreeNode</i> Constructor</li>
 * 			<li>Retrieve the Tree Node Value</li>
 * 			<li>Retrieve the Left Tree Node</li>
 * 			<li>Retrieve the Right Tree Node</li>
 * 		</ul>
 * 		<li><i>DiameterHeightPair</i> implements Diameter Height Duo</li>
 * 		<ul>
 * 			<li><i>DiameterHeightPair</i> Constructor</li>
 * 			<li>Retrieve the Height</li>
 * 			<li>Retrieve the Diameter</li>
 * 		</ul>
 * 		<li>Retrieve the Right-side View of the Tree</li>
 * 		<li>Generate the <i>DiameterHeightPair</i> Instance from the Root
 * 		<li>Given a binary tree, return the minimum number of edits to make the value of each node equal to the average of its direct children's. Note that you can only update the value of each tree node, and changing the tree structure is not allowed</li>
 * 		<li>Given the root of a binary tree, determine if it is a valid binary search tree (BST)</li>
 * 		<ul>
 * 			<li>A <b>valid BST</b> is defined as follows:</li>
 * 			<ul>
 *  			<li>The left subtree of a node contains only nodes with keys <b>less than</b> the node's key</li>
 *  			<li>The right subtree of a node contains only nodes with keys <b>greater than</b> the node's key</li>
 *  			<li>Both the left and right subtrees must also be binary search trees</li>
 * 			</ul>
 * 		</ul>
 *  	</li>
 * </ul>
 * 
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common/README.md">Assorted Data Structures Support Utilities</a></td></tr>
 *  </table>
 * <br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TreeUtil
{

	/**
	 * <i>TreeNode</i> implements Linked Tree Node.
	 */

	public class TreeNode
	{
		private TreeNode _left = null;
		private TreeNode _right = null;
		private double _value = Double.NaN;

		/**
		 * <i>TreeNode</i> Constructor
		 * 
		 * @param value Node Value
		 * @param left Left Node
		 * @param right Right Node
		 */

		public TreeNode (
			final double value,
			final TreeNode left,
			final TreeNode right)
		{
			_left = left;
			_right = right;
			_value = value;
		}

		/**
		 * Retrieve the Tree Node Value
		 * 
		 * @return The Tree Node Value
		 */

		public double value()
		{
			return _value;
		}

		/**
		 * Retrieve the Left Tree Node
		 * 
		 * @return The Left Tree Node
		 */

		public TreeNode left()
		{
			return _left;
		}

		/**
		 * Retrieve the Right Tree Node
		 * 
		 * @return The Right Tree Node
		 */

		public TreeNode right()
		{
			return _right;
		}
	}

	/**
	 * <i>DiameterHeightPair</i> implements Diameter Height Duo
	 */

	public static class DiameterHeightPair
	{
		private int _height = -1;
		private int _diameter = 0;

		/**
		 * <i>DiameterHeightPair</i> Constructor
		 * 
		 * @param height Height
		 * @param diameter Diameter
		 * 
		 * @throws Exception Thrown if Inputs are Invalid
		 */

		public DiameterHeightPair (
			final int height,
			final int diameter)
			throws Exception
		{
			if (-1 > (_height = height) || 0 > (_diameter = diameter)) {
				throw new Exception ("DiameterHeightPair Constructor => Invalid Inputs");
			}
		}

		/**
		 * Retrieve the Height
		 * 
		 * @return The Height
		 */

		public int height()
		{
			return _height;
		}

		/**
		 * Retrieve the Diameter
		 * 
		 * @return The Diameter
		 */

		public int diameter()
		{
			return _diameter;
		}
	}

	/**
	 * Retrieve the Right-side View of the Tree
	 * 
	 * @param root Root of the Tree
	 * 
	 * @return The Right-side View of the Tree
	 */

	public static final List<Double> RightSideView (
		final TreeNode root)
	{
		List<Double> rightNodeList = new ArrayList<Double>();

		if (null == root) {
			return rightNodeList;
		}

		Deque<TreeNode> nodeQueue = new ArrayDeque<TreeNode>();

		nodeQueue.add (root);

		while (!nodeQueue.isEmpty()) {
			int queueSize = nodeQueue.size();

			for (int i = 0; i < queueSize; ++i) {
				TreeNode currentNode = nodeQueue.poll();

				if (i == queueSize - 1) {
					rightNodeList.add (currentNode.value());

					TreeNode leftNode = currentNode.left();

					if (null != leftNode) {
						nodeQueue.add (leftNode);
					}

					TreeNode rightNode = currentNode.right();

					if (null != rightNode) {
						nodeQueue.add (rightNode);
					}
				}
			}
		}

		return rightNodeList;
	}

	/**
	 * Generate the <i>DiameterHeightPair</i> Instance from the Root
	 * 
	 * @param rootNode The Root Node
	 * 
	 * @return The <i>DiameterHeightPair</i> Instance from the Root
	 */

	public static final DiameterHeightPair TreeDiameter (
		final TreeNode rootNode)
	{
		if (null == rootNode) {
			try {
				return new DiameterHeightPair (-1, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		DiameterHeightPair rightDiameterHeightPair = TreeDiameter (rootNode.right());

		DiameterHeightPair leftDiameterHeightPair = TreeDiameter (rootNode.left());

		int rightHeight = rightDiameterHeightPair.height();

		int leftHeight = leftDiameterHeightPair.height();

		try {
			return new DiameterHeightPair (
				Math.max (leftHeight, rightHeight) + 1,
				Math.max (
					Math.max (leftDiameterHeightPair.diameter(), rightDiameterHeightPair.diameter()),
					leftHeight + rightHeight + 2
				)
			);
		} catch (Exception e) {
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
		if (null == node) {
			return 0;
		}

		TreeNode leftNode = node.left();

		TreeNode rightNode = node.right();

		if (null == leftNode && null == rightNode) {
			return 0;
		}

		int editCount = MinimumEditsForAverage (leftNode) + MinimumEditsForAverage (rightNode);

		if (null == leftNode) {
			double rightValue = rightNode._value;

			if (node._value != rightValue) {
				node._value = rightValue;
				return editCount + 1;
			}

			return editCount;
		}

		if (null == rightNode) {
			double leftValue = leftNode._value;

			if (node._value != leftValue) {
				node._value = leftValue;
				return editCount + 1;
			}

			return editCount;
		}

		double averageValue = 0.5 * (rightNode._value + leftNode._value);

		if (node._value != averageValue) {
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
		if (null == node) {
			return true;
		}

		if (null != node._left && node._left._value >= node._value) {
			return false;
		}

		if (!ValidateIsStrictBST (node._left)) {
			return false;
		}

		if (null != node._right && node._right._value <= node._value) {
			return false;
		}

		return ValidateIsStrictBST (node._left);
	}
}
