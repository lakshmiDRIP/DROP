
package org.drip.spaces.big;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * BinaryTree contains an Implementation of the Left/Right Binary Tree.
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
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblNode = dblNode))
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblNode)) return null;

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
