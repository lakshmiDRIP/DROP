
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
 * <i>BinomialTree</i> implements an Ordered Binomial Tree. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Brodal, G. S., and C. Okasaki (1996): Optimal Purely Functional Priority Queues <i>Journal of
 *  			Functional Programming</i> <b>6 (6)</b> 839-857
 *  	</li>
 *  	<li>
 *  		Brown, M. R. (1978): Implementation and Analysis of Binomial Queue Algorithms <i>SIAM Journal on
 *  			Computing</i> <b>7 (3)</b> 298-319
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Vuillemin, J. (1978): A Data Structure for Manipulating Priority Queues <i>Communications of the
 *  			ACM</i> <b>21 (4)</b> 309-315
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Binomial Heap https://en.wikipedia.org/wiki/Binomial_heap
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

public class BinomialTree
{
	private BinomialTree _parent = null;
	private double _key = java.lang.Double.NaN;
	private java.util.List<BinomialTree> _children = null;

	/**
	 * Meld the specified Pair of Binomial Trees into one of the Higher Order
	 * 
	 * @param binomialTree1 Binomial Tree #1
	 * @param binomialTree2 Binomial Tree #2
	 * @param minHeap TRUE - Meld into a Minimum Binomial Heap
	 * 
	 * @return The Binomial Tree of Higher Order
	 */

	public static final BinomialTree MeldPair (
		final BinomialTree binomialTree1,
		final BinomialTree binomialTree2,
		final boolean minHeap)
	{
		if (null == binomialTree1 || null == binomialTree2)
		{
			return null;
		}

		int order = binomialTree1.order();

		if (order != binomialTree2.order())
		{
			return null;
		}

		BinomialTree upTree = null;
		BinomialTree downTree = null;
		BinomialTree meldedTree = null;

		double key1 = binomialTree1.key();

		double key2 = binomialTree2.key();

		if (minHeap)
		{
			if (key1 >= key2)
			{
				upTree = binomialTree2;
				downTree = binomialTree1;
			}
			else
			{
				upTree = binomialTree1;
				downTree = binomialTree2;
			}
		}
		else
		{
			if (key1 >= key2)
			{
				upTree = binomialTree1;
				downTree = binomialTree2;
			}
			else
			{
				upTree = binomialTree2;
				downTree = binomialTree1;
			}
		}

		java.util.List<BinomialTree> meldedChildren = new java.util.ArrayList<BinomialTree>();

		java.util.List<BinomialTree> upChildren = upTree.children();

		if (null != upChildren)
		{
			int upChildrenCount = upChildren.size();

			for (int meldedChildIndex = 0;
				meldedChildIndex < upChildrenCount;
				++meldedChildIndex)
			{
				meldedChildren.add (
					upChildren.get (
						meldedChildIndex
					)
				);
			}
		}

		meldedChildren.add (
			downTree
		);

		try
		{
			meldedTree = new BinomialTree (
				upTree.key()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return meldedTree.setChildren (
			meldedChildren
		) && downTree.setParent (
			meldedTree
		) ? meldedTree : null;
	}

	/**
	 * BinomialTree Constructor
	 * 
	 * @param key Key
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BinomialTree (
		final double key)
		throws java.lang.Exception
	{
		if (java.lang.Double.isNaN (
			_key = key
		))
		{
			throw new java.lang.Exception (
				"BinomialTree Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Key of the Binomial Tree
	 * 
	 * @return Key of the Binomial Tree
	 */

	public double key()
	{
		return _key;
	}

	/**
	 * Retrieve the Parent of the Binomial Tree
	 * 
	 * @return Parent of the Binomial Tree
	 */

	public BinomialTree parent()
	{
		return _parent;
	}

	/**
	 * Retrieve the List of the Children
	 * 
	 * @return List of the Children
	 */

	public java.util.List<BinomialTree> children()
	{
		return _children;
	}

	/**
	 * Retrieve the Order of the Binomial Tree
	 * 
	 * @return Order of the Binomial Tree
	 */

	public int order()
	{
		return null == _children ? 0 : _children.size();
	}

	/**
	 * Set the Parent of the Binomial Tree
	 * 
	 * @param parent Parent of the Binomial Tree
	 * 
	 * @return TRUE - The Parent of the Binomial Tree successfully set
	 */

	public boolean setParent (
		final BinomialTree parent)
	{
		_parent = parent;
		return true;
	}

	/**
	 * Set the Children of the Binomial Tree
	 * 
	 * @param children Children of the Binomial Tree
	 * 
	 * @return TRUE - The Children of the Binomial Tree successfully set
	 */

	public boolean setChildren (
		final java.util.List<BinomialTree> children)
	{
		if (null == children)
		{
			return false;
		}

		int childrenCount = children.size();

		if (0 == childrenCount)
		{
			return false;
		}

		for (int childIndex = 0;
			childIndex < childrenCount;
			++childIndex)
		{
			BinomialTree child = children.get (
				0
			);

			if (null == child || childIndex == child.order())
			{
				return false;
			}
		}

		_children = children;
		return true;
	}

	@Override public java.lang.String toString()
	{
		return "[" + key() + " | " + _parent.key() + "]";
	}
}
