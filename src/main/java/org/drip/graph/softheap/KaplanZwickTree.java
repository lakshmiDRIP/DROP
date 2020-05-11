
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
 * <i>KaplanZwickTree</i> implements the Tree described in Kaplan and Zwick (2009). The References are:
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

public class KaplanZwickTree<K extends java.lang.Comparable<K>, V>
{
	private KaplanZwickTree<K, V> _next = null;
	private KaplanZwickTree<K, V> _prev = null;
	private KaplanZwickTree<K, V> _suffixExtremum = null;
	private org.drip.graph.softheap.KaplanZwickBinaryNode<K, V> _root = null;

	/**
	 * Update the Suffix Extremum of all Trees at and preceding the specified Tree
	 * 
	 * @param <K> Key Type
	 * @param <V> Value Type
	 * @param tree Specified Tree
	 * 
	 * @return TRUE - Update Suffix Extremum successfully completed
	 */

	public static <K extends java.lang.Comparable<K>, V> boolean UpdateSuffixExtremum (
		KaplanZwickTree<K, V> tree)
	{
		if (null == tree)
		{
			return false;
		}

		while (null != tree)
		{
			KaplanZwickTree<K, V> next = tree.next();

			org.drip.graph.softheap.KaplanZwickBinaryNode<K, V> currentRoot = tree.root();

			KaplanZwickTree<K, V> nextSuffixExtremum = null == next ? null : next.suffixExtremum();

			if (currentRoot.minHeap())
			{
				if (null == nextSuffixExtremum ||
					1 != currentRoot.cEntry().key().compareTo (
						nextSuffixExtremum.root().cEntry().key()
					)
				)
				{
					tree.setSuffixExtremum (
						tree
					);
				}
				else
				{
					tree.setSuffixExtremum (
						nextSuffixExtremum
					);
				}
			}
			else
			{
				if (null == nextSuffixExtremum ||
					-1 != currentRoot.cEntry().key().compareTo (
						nextSuffixExtremum.root().cEntry().key()
					)
				)
				{
					tree.setSuffixExtremum (
						tree
					);
				}
				else
				{
					tree.setSuffixExtremum (
						nextSuffixExtremum
					);
				}
			}

			tree = tree.prev();
		}

		return true;
	}

	/**
	 * Merge Two Trees with Identical Ranks
	 * 
	 * @param <K> Key Type
	 * @param <V> Value Type
	 * @param tree1 Tree #1
	 * @param tree2 Tree #2
	 * 
	 * @return The Merged Tree
	 */

	public static <K extends java.lang.Comparable<K>, V> KaplanZwickTree<K, V> EquiRankTreeMerge (
		final KaplanZwickTree<K, V> tree1,
		final KaplanZwickTree<K, V> tree2)
	{
		try
		{
			return null == tree1 || null == tree2 ? null : new KaplanZwickTree<K, V> (
				org.drip.graph.softheap.KaplanZwickBinaryNode.CombineRootNodePair (
					tree1.root(),
					tree2.root()
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
	 * Construct an Initial Tree with a single Entry
	 * 
	 * @param <K> Key Type
	 * @param <V> Value Type
	 * @param minHeap Min Heap Flag
	 * @param r The R Parameter
	 * @param entry The Entry
	 * 
	 * @return The Initial Tree with a single Entry
	 */

	public static <K extends java.lang.Comparable<K>, V> KaplanZwickTree<K, V> Initial (
		final boolean minHeap,
		final int r,
		final org.drip.graph.heap.PriorityQueueEntry<K, V> entry)
	{
		try
		{
			KaplanZwickTree<K, V> newTree = new KaplanZwickTree<K, V> (
				org.drip.graph.softheap.KaplanZwickBinaryNode.LeafRoot (
					minHeap,
					r,
					entry
				)
			);

			return newTree.setSuffixExtremum (
				newTree
			) ? newTree : null;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * KaplanZwickTree Constructor
	 * 
	 * @param root Root of the Current Tree
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public KaplanZwickTree (
		final org.drip.graph.softheap.KaplanZwickBinaryNode<K, V> root)
		throws java.lang.Exception
	{
		if (null == (_root = root))
		{
			throw new java.lang.Exception (
				"KaplanZwickTree Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Root of the Tree
	 * 
	 * @return The Root
	 */

	public org.drip.graph.softheap.KaplanZwickBinaryNode<K, V> root()
	{
		return _root;
	}

	/**
	 * Retrieve the Previous Tree in the List
	 * 
	 * @return The Previous Tree in the List
	 */

	public KaplanZwickTree<K, V> prev()
	{
		return _prev;
	}

	/**
	 * Retrieve the Next Tree in the List
	 * 
	 * @return The Next Tree in the List
	 */

	public KaplanZwickTree<K, V> next()
	{
		return _next;
	}

	/**
	 * Retrieve the Extremum ckey Tree among those following this in the List
	 * 
	 * @return The Extremum ckey Tree among those following this in the List
	 */

	public KaplanZwickTree<K, V> suffixExtremum()
	{
		return _suffixExtremum;
	}

	/**
	 * Retrieve the Rank of the Tree
	 * 
	 * @return Rank of the Tree
	 */

	public int rank()
	{
		return _root.k();
	}

	/**
	 * (Re-)set the Root
	 * 
	 * @param root The Root
	 * 
	 * @return TRUE - The Root successfully (re-)set
	 */

	public boolean setRoot (
		final org.drip.graph.softheap.KaplanZwickBinaryNode<K, V> root)
	{
		if (null == root)
		{
			return false;
		}

		_root = root;
		return true;
	}

	/**
	 * (Re-)set the Previous Tree
	 * 
	 * @param prev The Previous Tree
	 * 
	 * @return TRUE - The Previous Tree successfully (re-)set
	 */

	public boolean setPrev (
		final KaplanZwickTree<K, V> prev)
	{
		_prev = prev;
		return true;
	}

	/**
	 * (Re-)set the Next Tree
	 * 
	 * @param next The Next Tree
	 * 
	 * @return TRUE - The Next Tree successfully (re-)set
	 */

	public boolean setNext (
		final KaplanZwickTree<K, V> next)
	{
		_next = next;
		return true;
	}

	/**
	 * (Re-)set the Suffix Extremum Tree
	 * 
	 * @param suffixExtremum The Suffix Extremum
	 * 
	 * @return TRUE - The Suffix Extremum Tree successfully (re-)set
	 */

	public boolean setSuffixExtremum (
		final KaplanZwickTree<K, V> suffixExtremum)
	{
		if (null == suffixExtremum)
		{
			return false;
		}

		_suffixExtremum = suffixExtremum;
		return true;
	}

	/**
	 * Generate a Stand-alone Tree with the Root Node alone in its List
	 *  
	 * @return Stand-alone Tree with the Root Node alone in its List
	 */

	public KaplanZwickTree<K, V> rootTree()
	{
		try
		{
			return new KaplanZwickTree<K, V> (
				_root
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
