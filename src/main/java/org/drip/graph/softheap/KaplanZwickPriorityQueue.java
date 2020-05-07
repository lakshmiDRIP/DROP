
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
 * <i>KaplanZwickPriorityQueue</i> implements the Soft Heap described in Kaplan and Zwick (2009). The
 * 	References are:
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

public class KaplanZwickPriorityQueue
{
	private boolean _minHeap = false;
	private org.drip.graph.softheap.KaplanZwickTree _head = null;
	private org.drip.graph.softheap.KaplanZwickTree _tail = null;

	private static final boolean UpdatePrevAndCurrent (
		final org.drip.graph.softheap.KaplanZwickTree prev,
		final org.drip.graph.softheap.KaplanZwickTree current)
	{
		if (!current.setPrev (
				prev
			) || !current.setNext (
				null
			)
		)
		{
			return false;
		}

		if (null != prev &&
			!prev.setNext (
				current
			)
		)
		{
			return false;
		}

		return true;
	}

	private static final KaplanZwickPriorityQueue MakeQueue (
		final boolean minHeap,
		final org.drip.graph.softheap.KaplanZwickTree head,
		final org.drip.graph.softheap.KaplanZwickTree tail)
	{
		try
		{
			return new KaplanZwickPriorityQueue (
				minHeap,
				head,
				tail
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private static final org.drip.graph.softheap.KaplanZwickTree MergeTreeLists (
		final KaplanZwickPriorityQueue queue1,
		final KaplanZwickPriorityQueue queue2)
	{
		org.drip.graph.softheap.KaplanZwickTree head = null;
		org.drip.graph.softheap.KaplanZwickTree current = head;

		org.drip.graph.softheap.KaplanZwickTree current1 = queue1.head();

		org.drip.graph.softheap.KaplanZwickTree current2 = queue2.head();

		while (null != current1 && null != current2)
		{
			org.drip.graph.softheap.KaplanZwickTree prev = current;

			int rank1 = current1.rank();

			int rank2 = current2.rank();

			try
			{
				if (rank1 < rank2)
				{
					current = new org.drip.graph.softheap.KaplanZwickTree (
						current1.root()
					);

					current1 = current1.next();
				}
				else if (rank2 < rank1)
				{
					current = new org.drip.graph.softheap.KaplanZwickTree (
						current2.root()
					);

					current2 = current2.next();
				}
				else
				{
					current = new org.drip.graph.softheap.KaplanZwickTree (
						current1.root()
					);

					current1 = current1.next();

					UpdatePrevAndCurrent (
						prev,
						current
					);

					if (null == head)
					{
						head = current;
					}

					prev = current;

					current = new org.drip.graph.softheap.KaplanZwickTree (
						current2.root()
					);

					current2 = current2.next();
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			UpdatePrevAndCurrent (
				prev,
				current
			);

			if (null == head)
			{
				head = current;
			}
		}

		while (null != current1)
		{
			org.drip.graph.softheap.KaplanZwickTree prev = current;

			try
			{
				current = new org.drip.graph.softheap.KaplanZwickTree (
					current1.root()
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			current1 = current1.next();

			UpdatePrevAndCurrent (
				prev,
				current
			);

			if (null == head)
			{
				head = current;
			}
		}

		while (null != current2)
		{
			org.drip.graph.softheap.KaplanZwickTree prev = current;

			try
			{
				current = new org.drip.graph.softheap.KaplanZwickTree (
					current2.root()
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			current2 = current2.next();

			UpdatePrevAndCurrent (
				prev,
				current
			);

			if (null == head)
			{
				head = current;
			}
		}

		return head;
	}

	/**
	 * Meld the Queue Pair into a Queue
	 * 
	 * @param queue1 Queue #1
	 * @param queue2 Queue #2
	 * 
	 * @return The Melded Queue
	 */

	public static final KaplanZwickPriorityQueue Meld (
		final KaplanZwickPriorityQueue queue1,
		final KaplanZwickPriorityQueue queue2)
	{
		if (null == queue1)
		{
			return queue2;
		}

		if (null == queue2)
		{
			return queue1;
		}

		boolean minHeap = queue1.minHeap();

		if (minHeap != queue2.minHeap())
		{
			return null;
		}

		org.drip.graph.softheap.KaplanZwickTree mergedTreeList = MergeTreeLists (
			queue1,
			queue2
		);

		if (null == mergedTreeList)
		{
			return null;
		}

		org.drip.graph.softheap.KaplanZwickTreeMelder melder = null;
		org.drip.graph.softheap.KaplanZwickTree mergedTreeCurrent = mergedTreeList;

		try
		{
			melder = new org.drip.graph.softheap.KaplanZwickTreeMelder (
				mergedTreeCurrent
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		while (null != (mergedTreeCurrent = mergedTreeCurrent.next()))
		{
			int tailRank = melder.tailRank();

			if (tailRank < mergedTreeCurrent.rank())
			{
				if (!melder.appendToTail (
					mergedTreeCurrent.rootTree()
				))
				{
					return null;
				}

				continue;
			}

			org.drip.graph.softheap.KaplanZwickTree mergedTreeNext = mergedTreeCurrent.next();

			if (null == mergedTreeNext)
			{
				if (!melder.growTail (
					mergedTreeCurrent.rootTree().root()
				))
				{
					return null;
				}

				break;
			}

			int mergedTreeNextRank = mergedTreeNext.rank();

			if (tailRank < mergedTreeNextRank)
			{
				if (!melder.growTail (
					mergedTreeCurrent.rootTree().root()
				))
				{
					return null;
				}

				if (melder.tailRank() == mergedTreeNextRank)
				{
					if (!melder.growTail (
						mergedTreeNext.rootTree().root()
					))
					{
						return null;
					}
				}
				else
				{
					if (!melder.appendToTail (
						mergedTreeNext.rootTree()
					))
					{
						return null;
					}
				}

				mergedTreeCurrent = mergedTreeNext;
				continue;
			}

			if (!melder.appendToTail (
				org.drip.graph.softheap.KaplanZwickTree.EquiRankTreeMerge (
					mergedTreeCurrent,
					mergedTreeNext
				)
			))
			{
				return null;
			}

			mergedTreeCurrent = mergedTreeNext;
		}

		KaplanZwickPriorityQueue priorityQueue = MakeQueue (
			minHeap,
			melder.head(),
			melder.tail()
		);

		return null != priorityQueue &&
			org.drip.graph.softheap.KaplanZwickTree.UpdateSuffixExtremum (
				priorityQueue.tail()
			) ? priorityQueue : null;
	}

	/**
	 * Construct an Initial Heap with a single Key
	 * 
	 * @param minHeap TRUE - Indicates that Heap is a Min Heap
	 * @param r The R Parameter
	 * @param key The Key
	 * 
	 * @return The Initial Heap with a single Key
	 */

	public static final KaplanZwickPriorityQueue Initial (
		final boolean minHeap,
		final int r,
		final double key)
	{
		org.drip.graph.softheap.KaplanZwickTree head = org.drip.graph.softheap.KaplanZwickTree.Initial (
			minHeap,
			r,
			key
		);

		try
		{
			return new KaplanZwickPriorityQueue (
				minHeap,
				head,
				head
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * KaplanZwickPriorityQueue Constructor
	 * 
	 * @param minHeap TRUE - Indicates that Heap is a Min Heap
	 * @param head The Head Tree of the Heap
	 * @param tail The Tail Tree of the Heap
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public KaplanZwickPriorityQueue (
		final boolean minHeap,
		final org.drip.graph.softheap.KaplanZwickTree head,
		final org.drip.graph.softheap.KaplanZwickTree tail)
		throws java.lang.Exception
	{
		if (null == (_head = head) ||
			null == (_tail = tail)
		)
		{
			throw new java.lang.Exception (
				"KaplanZwickPriorityQueue Constructor => Invalid Inputs"
			);
		}

		_minHeap = minHeap;
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
	 * Retrieve the Head of the List of Trees
	 * 
	 * @return Head of the List of Trees
	 */

	public org.drip.graph.softheap.KaplanZwickTree head()
	{
		return _head;
	}

	/**
	 * Retrieve the Tail of the List of Trees
	 * 
	 * @return Tail of the List of Trees
	 */

	public org.drip.graph.softheap.KaplanZwickTree tail()
	{
		return _tail;
	}

	/**
	 * Retrieve the Rank of the Heap
	 * 
	 * @return Rank of the Heap
	 */

	public int rank()
	{
		return _head.rank();
	}

	/**
	 * Insert an Element into the Queue
	 * 
	 * @param r The R Parameter
	 * @param key They Key
	 * 
	 * @return The ELement successfully inserted into the Queue
	 */

	public boolean insert (
		final int r,
		final double key)
	{
		KaplanZwickPriorityQueue meldedQueue = Meld (
			this,
			KaplanZwickPriorityQueue.Initial (
				_minHeap,
				r,
				key
			)
		);

		if (null == meldedQueue)
		{
			return false;
		}

		_head = meldedQueue.head();

		_tail = meldedQueue.tail();

		return true;
	}

	/**
	 * Indicate if the Soft Heap is Empty
	 * 
	 * @return TRUE - The Soft Heap is Empty
	 */

	public boolean isEmpty()
	{
		if (null == _head)
		{
			return true;
		}

		org.drip.graph.softheap.KaplanZwickTree tree = _head;

		while (null != tree)
		{
			if (0 != tree.root().currentSize())
			{
				return false;
			}

			tree = tree.next();
		}

		return true;
	}

	/**
	 * Extract the Extremum from the Soft Heap
	 * 
	 * @return The Extremum Value
	 * 
	 * @throws java.lang.Exception Thrown if the Extremum cannot be extracted
	 */

	public double extractExtremum()
		throws java.lang.Exception
	{
		if (isEmpty())
		{
			throw new java.lang.Exception (
				"KaplanZwickPriorityQueue::extractExtremum => Queue is Empty"
			);
		}

		org.drip.graph.softheap.KaplanZwickTree extremumTree = _head.suffixExtremum();

		org.drip.graph.softheap.KaplanZwickBinaryNode extremumRoot = extremumTree.root();

		double extremum = extremumRoot.removeKey();

		if (!extremumRoot.sift())
		{
			throw new java.lang.Exception (
				"KaplanZwickPriorityQueue::extractExtremum => Cannot sift the Extremum Root"
			);
		}

		if (0 == extremumRoot.currentSize())
		{
			if (null == extremumTree.prev())
			{
				org.drip.graph.softheap.KaplanZwickTree nextTree = extremumTree.next();

				if (null != nextTree)
				{
					if (!(_head = nextTree).setPrev (
						null
					))
					{
						throw new java.lang.Exception (
							"KaplanZwickPriorityQueue::extractExtremum => Cannot remove Empty Root"
						);
					}
				}
			}
			else if (null == extremumTree.next())
			{
				org.drip.graph.softheap.KaplanZwickTree prevTree = extremumTree.prev();

				if (null != prevTree)
				{
					if (!(_tail = prevTree).setNext (
						null
					))
					{
						throw new java.lang.Exception (
							"KaplanZwickPriorityQueue::extractExtremum => Cannot remove Empty Root"
						);
					}
				}
			}
			else
			{
				org.drip.graph.softheap.KaplanZwickTree prevTree = extremumTree.prev();

				org.drip.graph.softheap.KaplanZwickTree nextTree = extremumTree.next();

				if (!prevTree.setNext (
						nextTree
					) || !nextTree.setNext (
						prevTree
					)
				)
				{
					throw new java.lang.Exception (
						"KaplanZwickPriorityQueue::extractExtremum => Cannot remove Empty Root"
					);
				}
			}

			if (!org.drip.graph.softheap.KaplanZwickTree.UpdateSuffixExtremum (
				_tail
			))
			{
				throw new java.lang.Exception (
					"KaplanZwickPriorityQueue::extractExtremum => Cannot update Extremum Suffix"
				);
			}
		}

		return extremum;
	}

	@Override public java.lang.String toString()
	{
		String state = "<";
		org.drip.graph.softheap.KaplanZwickTree tree = _head;

		while (null != tree)
		{
			state = state + tree.root().toString() + " | ";

			tree = tree.next();
		}

		return state + ">";
	}
}
