
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
 * <i>ChazelleSoftQueueNode</i> implements a Soft-Queue Node's CKey and its Rank in the Master Tree. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Blum, M., R. W. Floyd, V. Pratt, R. L. Rivest, and R. E. Tarjan (1973): Time Bounds for Selection
 *  			<i> Journal of Computer and System Sciences</i> <b>7 (4)</b> 448-461
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
 *  		Fredman, M. L., and R. E. Tarjan (1987): Fibonacci Heaps and their Uses in Improved Network
 *  			Optimization Algorithms <i>Journal of the Association for Computing Machinery</i> <b>34
 *  			(3)</b> 596-615
 *  	</li>
 *  	<li>
 *  		Vuillemin, J. (2000): A Data Structure for Manipulating Priority Queues <i>Communications of the
 *  			ACM</i> <b>21 (4)</b> 309-315
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

public class ChazelleSoftQueueNode
{
	private int _r = -1;
	private ChazelleSoftQueueNode _next = null;
	private ChazelleSoftQueueNode _child = null;
	private double _k = java.lang.Double.POSITIVE_INFINITY;
	private java.util.List<java.lang.Double> _entryList = null;
	private java.lang.Double _cEntry = java.lang.Double.POSITIVE_INFINITY;

	/**
	 * Construct a Leaf Root Node of a New Tree with a single Entry
	 * 
	 * @param r The R Parameter
	 * @param entry The Entry
	 * 
	 * @return The Leaf Root of a New Tree with a single KeEntryy
	 */

	public static final ChazelleSoftQueueNode LeafRoot (
		final int r,
		final java.lang.Double entry)
	{
		try
		{
			ChazelleSoftQueueNode emptyRoot = new ChazelleSoftQueueNode (
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
	 * ChazelleSoftQueueNode Constructor
	 * 
	 * @param r R Parameter
	 * @param k Rank
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ChazelleSoftQueueNode (
		final int r,
		final int k)
		throws java.lang.Exception
	{
		if (0 > (_r = r) ||
			0 > (_k = k)
		)
		{
			throw new java.lang.Exception (
				"ChazelleSoftQueueNode Constructor => Invalid Inputs"
			);
		}

		_entryList = new java.util.ArrayList<java.lang.Double>();
	}

	/**
	 * Retrieve the Rank
	 * 
	 * @return The Rank
	 */

	public double k()
	{
		return _k;
	}

	/**
	 * Retrieve the Next Soft Queue Node
	 * 
	 * @return The Next Soft Queue Node
	 */

	public ChazelleSoftQueueNode next()
	{
		return _next;
	}

	/**
	 * Retrieve the Child Soft Queue Node
	 * 
	 * @return The Child Soft Queue Node
	 */

	public ChazelleSoftQueueNode child()
	{
		return _child;
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

	public java.util.List<java.lang.Double> entryList()
	{
		return _entryList;
	}

	/**
	 * Retrieve the cEntry
	 * 
	 * @return The cEntry
	 */

	public java.lang.Double cEntry()
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
	 * Set the cEntry
	 * 
	 * @param cEntry The CEntry
	 * 
	 * @return TRUE - The CEntry successfully set
	 */

	public boolean setCEntry (
		final java.lang.Double cEntry)
	{
		if (null == cEntry)
		{
			return false;
		}

		_cEntry = cEntry;
		return true;
	}

	/**
	 * Set the Rank
	 * 
	 * @param k The Rank
	 * 
	 * @return TRUE - The Rank successfully set
	 */

	public boolean setRank (
		final double k)
	{
		if (java.lang.Double.isNaN (
			k
		))
		{
			return false;
		}

		_k = k;
		return true;
	}

	/**
	 * Set the Child Soft Queue Node
	 * 
	 * @param child The Child Soft Queue Node
	 * 
	 * @return TRUE - The Child Soft Queue Node successfully set
	 */

	public boolean setChild (
		final org.drip.graph.softheap.ChazelleSoftQueueNode child)
	{
		_child = child;
		return true;
	}

	/**
	 * Set the Next Soft Queue Node
	 * 
	 * @param next The Next Soft Queue Node
	 * 
	 * @return TRUE - The Next Soft Queue Node successfully set
	 */

	public boolean setNext (
		final org.drip.graph.softheap.ChazelleSoftQueueNode next)
	{
		_next = next;
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
		final java.lang.Double entry)
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
			_cEntry = 1 == _cEntry.compareTo (
				entry
			) ? _cEntry : entry;
		}

		return true;
	}

	/**
	 * Peek the Top Entry
	 * 
	 * @return The Top Entry
	 */

	public java.lang.Double peekTopEntry()
	{
		return _entryList.get (
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

		for (java.lang.Double entry : _entryList)
		{
			keyCorruptionStatusList.add (
				0 == _cEntry.compareTo (
					entry
				)
			);
		}

		return keyCorruptionStatusList;
	}

	/**
	 * Indicate if the Node is a Leaf
	 * 
	 * @return TRUE - The Node is a Leaf
	 */

	public boolean isLeaf()
	{
		return null == _child && null == _next;
	}

	/**
	 * Perform a BFS Walk through the Nodes and retrieve them
	 * 
	 * @return The List of Nodes
	 */

	public java.util.List<ChazelleSoftQueueNode> bfsWalk()
	{
		java.util.List<ChazelleSoftQueueNode> elementList = new java.util.ArrayList<ChazelleSoftQueueNode>();

		java.util.List<ChazelleSoftQueueNode> elementQueue =
			new java.util.ArrayList<ChazelleSoftQueueNode>();

		elementQueue.add (
			this
		);

		while (!elementQueue.isEmpty())
		{
			ChazelleSoftQueueNode node = elementQueue.get (
				0
			);

			elementQueue.remove (
				0
			);

			elementList.add (
				node
			);

			ChazelleSoftQueueNode left = node.child();

			if (null != left)
			{
				elementQueue.add (
					left
				);
			}

			ChazelleSoftQueueNode right = node.next();

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
		java.util.List<java.lang.Double> childKeyList =
			new java.util.ArrayList<java.lang.Double>();

		java.util.List<ChazelleSoftQueueNode> bfsWalk = bfsWalk();

		for (ChazelleSoftQueueNode node : bfsWalk)
		{
			childKeyList.addAll (
				node.entryList()
			);
		}

		return childKeyList;
	}

	@Override public java.lang.String toString()
	{
		String state = "{Rank = " + _k + "; ckey = " + _cEntry + "; List = " + _entryList;

		// state = state + "; Parent = " + (null == _parent ? "null" : _parent.cEntry().key());

		state = state + "; Child = " + (
			null == _child ? "null" : _child.cEntry() + " @ " + _child.childKeyList() + " @ " +
				_child.entryList()
		);

		state = state + "; Next = " + (
			null == _next ? "null" : _next.cEntry() + " @ " + _next.childKeyList() + " @ " +
				_next.entryList()
		);

		return state + "}";
	}
}
