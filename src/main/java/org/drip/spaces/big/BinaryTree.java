
package org.drip.spaces.big;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>BinaryTree</i> contains an Implementation of the Left/Right Binary Tree.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big/README.md">Big-data In-place Manipulator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BinaryTree {
	private int _iCount = 1;
	private BinaryTree _btParent = null;
	private BinaryTree _btLeftChild = null;
	private BinaryTree _btRightChild = null;
	private double _dblNode = java.lang.Double.NaN;

	/**
	 * BinaryTree Constructor
	 * 
	 * @param dblNode The Node Value
	 * @param btParent The BinaryTree Parent
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public BinaryTree (
		final double dblNode,
		final BinaryTree btParent)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblNode = dblNode))
			throw new java.lang.Exception ("BinaryTree ctr => Invalid Inputs");

		_iCount = 1;
		_btParent = btParent;
	}

	/**
	 * Retrieve the BinaryTree Node Value
	 * 
	 * @return The BinaryTree Node Value
	 */

	public double node()
	{
		return _dblNode;
	}

	/**
	 * Retrieve the Parent BinaryTree Instance
	 * 
	 * @return The Parent BinaryTree Instance
	 */

	public BinaryTree parent()
	{
		return _btParent;
	}

	/**
	 * Retrieve the Left Child BinaryTree Instance
	 * 
	 * @return The Left Child BinaryTree Instance
	 */

	public BinaryTree leftChild()
	{
		return _btLeftChild;
	}

	/**
	 * Retrieve the Right Child BinaryTree Instance
	 * 
	 * @return The Right Child BinaryTree Instance
	 */

	public BinaryTree rightChild()
	{
		return _btRightChild;
	}

	/**
	 * Retrieve the Node Instance Count
	 * 
	 * @return The Node Instance Count
	 */

	public int count()
	{
		return _iCount;
	}

	/**
	 * Insert a Node into the Tree
	 * 
	 * @param dblNode The Node to be inserted
	 * 
	 * @return The Inserted Node
	 */

	public BinaryTree insert (
		final double dblNode)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblNode)) return null;

		if (_dblNode == dblNode) {
			++_iCount;
			return this;
		}

		try {
			if (dblNode < _dblNode)
				return null == _btLeftChild ? _btLeftChild = new BinaryTree (dblNode, this) :
					_btLeftChild.insert (dblNode);

			return null == _btRightChild ? _btRightChild = new BinaryTree (dblNode, this) :
				_btRightChild.insert (dblNode);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Left Most Child
	 * 
	 * @return The Left Most Child BinaryTree Instance
	 */

	public BinaryTree leftMostChild()
	{
		BinaryTree btParent = this;

		BinaryTree btLeftChild = leftChild();

		while (null != btLeftChild) {
			btParent = btLeftChild;

			btLeftChild = btParent.leftChild();
		}

		return btParent;
	}

	/**
	 * Retrieve the Right Most Child
	 * 
	 * @return The Right Most Child BinaryTree Instance
	 */

	public BinaryTree rightMostChild()
	{
		BinaryTree btParent = this;

		BinaryTree btRightChild = rightChild();

		while (null != btRightChild) {
			btParent = btRightChild;

			btRightChild = btParent.rightChild();
		}

		return btParent;
	}

	/**
	 * Build a Consolidated Ascending List of all the Constituent Nodes
	 * 
	 * @param lsNode The Node List
	 * 
	 * @return TRUE - The Ascending Node List Successfully Built
	 */

	public boolean ascendingNodeList (
		final java.util.List<java.lang.Double> lsNode)
	{
		if (null == lsNode) return false;

		if (null != _btLeftChild && !_btLeftChild.ascendingNodeList (lsNode)) return false;

		lsNode.add (_dblNode);

		if (null != _btRightChild && !_btRightChild.ascendingNodeList (lsNode)) return false;

		return true;
	}

	/**
	 * Build a Consolidated Ascending List of all the Constituent Nodes
	 * 
	 * @return The Node List
	 */

	public java.util.List<java.lang.Double> ascendingNodeList()
	{
		java.util.List<java.lang.Double> lsNode = new java.util.ArrayList<java.lang.Double>();

		return ascendingNodeList (lsNode) ? lsNode : null;
	}

	/**
	 * Build a Consolidated Ascending Array of all the Constituent Nodes
	 * 
	 * @param adblNode The Node Array
	 * @param iUpdateStartIndex The Update Start Index
	 * 
	 * @return TRUE - The Ascending Node Array Successfully Built
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int ascendingNodeArray (
		final double[] adblNode,
		final int iUpdateStartIndex)
		throws java.lang.Exception
	{
		if (null == adblNode || 0 == adblNode.length)
			throw new java.lang.Exception ("BinaryTree::ascendingNodeArray => Invalid Inputs");

		int iIndexToUpdate = null == _btLeftChild ? iUpdateStartIndex : _btLeftChild.ascendingNodeArray
			(adblNode, iUpdateStartIndex);

		if (iIndexToUpdate >= adblNode.length)
			throw new java.lang.Exception ("BinaryTree::ascendingNodeArray => Invalid Inputs");

		adblNode[iIndexToUpdate++] = _dblNode;

		return null == _btRightChild ? iIndexToUpdate : _btRightChild.ascendingNodeArray (adblNode,
			iIndexToUpdate);
	}

	/**
	 * Build a Consolidated Descending List of all the Constituent Nodes
	 * 
	 * @param lsNode The Node List
	 * 
	 * @return TRUE - The Descending Node List Successfully Built
	 */

	public boolean descendingNodeList (
		final java.util.List<java.lang.Double> lsNode)
	{
		if (null == lsNode) return false;

		if (null != _btRightChild && !_btRightChild.descendingNodeList (lsNode)) return false;

		lsNode.add (_dblNode);

		if (null != _btLeftChild && !_btLeftChild.descendingNodeList (lsNode)) return false;

		return true;
	}

	/**
	 * Build a Consolidated Descending Array of all the Constituent Nodes
	 * 
	 * @param adblNode The Node Array
	 * @param iIndexToUpdate The Index To Update
	 * 
	 * @return TRUE - The Descending Node Array Successfully Built
	 */

	public boolean descendingNodeArray (
		final double[] adblNode,
		final int iIndexToUpdate)
	{
		if (null == adblNode || 0 == adblNode.length) return false;

		if (null != _btLeftChild && !_btLeftChild.descendingNodeArray (adblNode, iIndexToUpdate))
			return false;

		if (iIndexToUpdate >= adblNode.length) return false;

		adblNode[iIndexToUpdate] = _dblNode;

		if (null != _btRightChild && !_btRightChild.descendingNodeArray (adblNode, iIndexToUpdate + 1))
			return false;

		return true;
	}

	/**
	 * Build a Consolidated Descending List of all the Constituent Nodes
	 * 
	 * @return The Node List
	 */

	public java.util.List<java.lang.Double> descendingNodeList()
	{
		java.util.List<java.lang.Double> lsNode = new java.util.ArrayList<java.lang.Double>();

		return descendingNodeList (lsNode) ? lsNode : null;
	}
}
