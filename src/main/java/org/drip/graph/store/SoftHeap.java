
package org.drip.graph.store;

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
 * <i>SoftHeap</i> implements the Soft Heap - an Approximate Priority Queue. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/store/README.md">Graph Navigation Storage Data Structures</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SoftHeap
{
	private double _r = java.lang.Double.NaN;
	private org.drip.graph.store.HEAD _tail = null;
	private org.drip.graph.store.HEAD _header = null;

	/**
	 * Create a Fresh Instance of Soft-Heap
	 * 
	 * @param headerRank The Header Rank
	 * @param r r
	 * 
	 * @return Fresh Instance of Soft-Heap
	 */

	public static final SoftHeap Create (
		final double headerRank,
		final double r)
	{
		org.drip.graph.store.HEAD header = new org.drip.graph.store.HEAD();

		if (!header.setRank (
			headerRank
		))
		{
			return null;
		}

		org.drip.graph.store.HEAD tail = new org.drip.graph.store.HEAD();

		if (!header.setNext (
				tail
			) || !tail.setPrev (
				header
			)
		)
		{
			return null;
		}

		try
		{
			return new SoftHeap (
				r,
				header,
				tail
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SoftHeap Constructor
	 * 
	 * @param r r
	 * @param header Header
	 * @param tail Tail
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SoftHeap (
		final double r,
		final org.drip.graph.store.HEAD header,
		final org.drip.graph.store.HEAD tail)
		throws java.lang.Exception
	{
		if (java.lang.Double.isNaN (
			_r = r
		))
		{
			throw new java.lang.Exception (
				"SoftHeap Constructor => Invalid Inputs"
			);
		}

		_tail = tail;
		_header = header;
	}

	/**
	 * Retrieve the Header
	 * 
	 * @return The Header
	 */

	public org.drip.graph.store.HEAD header()
	{
		return _header;
	}

	/**
	 * Retrieve the Tail
	 * 
	 * @return The Tail
	 */

	public org.drip.graph.store.HEAD tail()
	{
		return _tail;
	}

	/**
	 * Retrieve r
	 * 
	 * @return r
	 */

	public double r()
	{
		return _r;
	}

	/**
	 * Update all the suffixMin's inside the HEAD Lists
	 * 
	 * @param h The Last Header List
	 * 
	 * @return TRUE - The SuffixMin's inside the HEAD Lists successfully updated
	 */

	public boolean fixMinList (
		org.drip.graph.store.HEAD h)
	{
		if (null == h)
		{
			return false;
		}

		/*
		 * Head corresponding to Queue with Lowest CKey
		 */

		org.drip.graph.store.HEAD hNext = h.next();

		org.drip.graph.store.HEAD tmpSuffixMin = _tail == hNext ? h : hNext.suffixMin();

		while (h != _header)
		{
			if (h.queue().ckey() < tmpSuffixMin.queue().ckey())
			{
				tmpSuffixMin = h;
			}

			if (!h.setSuffixMin (
				tmpSuffixMin
			))
			{
				return false;
			}

			h = h.prev();
		}

		return true;
	}

	/**
	 * Meld the Specified Soft Queue into the current Soft Heap
	 * 
	 * @param softQueueNode The Specified Soft Queue
	 * 
	 * @return TRUE - The Specified Soft Queue successfully melded
	 */

	public boolean meld (
		org.drip.graph.store.SoftQueueNode softQueueNode)
	{

		/*
		 * Heap Rank corresponding to softQueueNode
		 */

		if (null == softQueueNode)
		{
			return false;
		}

		org.drip.graph.store.HEAD toHead = _header.next();

		while (softQueueNode.rank() > toHead.rank())
		{
			toHead = toHead.next();
		}

		org.drip.graph.store.HEAD prevHead = toHead.prev();

		/*
		 * Carry Propagation to handle Pre-existing Ranks
		 */

		while (softQueueNode.rank() == toHead.rank())
		{
			org.drip.graph.store.SoftQueueNode nextSoftQueueNode = null;
			org.drip.graph.store.SoftQueueNode childSoftQueueNode = null;

			org.drip.graph.store.SoftQueueNode toHeadSoftQueueNode = toHead.queue();

			if (toHeadSoftQueueNode.ckey() > softQueueNode.ckey())
			{
				nextSoftQueueNode = softQueueNode;
				childSoftQueueNode = toHeadSoftQueueNode;
			}
			else
			{
				childSoftQueueNode = softQueueNode;
				nextSoftQueueNode = toHeadSoftQueueNode;
			}

			softQueueNode = new org.drip.graph.store.SoftQueueNode();

			if (!softQueueNode.setCKey (
					nextSoftQueueNode.ckey()
				) || !softQueueNode.setRank (
					nextSoftQueueNode.rank() + 1.
				) || !softQueueNode.setNext (
					nextSoftQueueNode
				) || !softQueueNode.setChild (
					childSoftQueueNode
				) || !softQueueNode.setHeadItem (
					nextSoftQueueNode.headItem()
				) || !softQueueNode.setTailItem (
					nextSoftQueueNode.tailItem()
				)
			)
			{
				return false;
			}

			toHead = toHead.next();
		}

		/*
		 * Insertion of the New Queue
		 */

		org.drip.graph.store.HEAD h = prevHead == toHead.prev() ? 
			new org.drip.graph.store.HEAD() : prevHead.next();

		return h.setQueue (
			softQueueNode
		) && h.setRank (
			softQueueNode.rank()
		) && h.setPrev (
			prevHead
		) && h.setNext (
			toHead
		) && prevHead.setNext (
			h
		) && toHead.setPrev (
			h
		) && fixMinList (
			h
		);
	}

	/**
	 * Insert a Key into the Soft Heap
	 * 
	 * @param key The Key
	 * 
	 * @return TRUE - The Key successfully inserted
	 */

	public boolean insert (
		final double key)
	{
		org.drip.graph.store.ItemListEntry itemListEntry = new org.drip.graph.store.ItemListEntry();

		if (!itemListEntry.setKey (
			key
		))
		{
			return false;
		}

		org.drip.graph.store.SoftQueueNode softQueueNode = new org.drip.graph.store.SoftQueueNode();

		return softQueueNode.setRank (
			0
		) && softQueueNode.setCKey (
			key
		) && softQueueNode.setHeadItem (
			itemListEntry
		) && softQueueNode.setTailItem (
			itemListEntry
		) && meld (
			softQueueNode
		);
	}

	/**
	 * Replace the Empty-list at the Specified Soft Queue and return it
	 * 
	 * @param softQueueNode The Soft Queue
	 * 
	 * @return The Empty-list at the Specified Soft Queue successfully replaced and returned
	 */

	public org.drip.graph.store.SoftQueueNode sift (
		final org.drip.graph.store.SoftQueueNode softQueueNode)
	{
		if (!softQueueNode.setHeadItem (
				null
			) || !softQueueNode.setTailItem (
				null
			)
		)
		{
			return null;
		}

		/*
		 * Stop Recursion if the Node is a Leaf
		 */

		org.drip.graph.store.SoftQueueNode nextSoftQueueNode = softQueueNode.next();

		if (null == nextSoftQueueNode && null == softQueueNode.child())
		{
			return softQueueNode.setCKey (
				java.lang.Double.POSITIVE_INFINITY
			) ? softQueueNode : null;
		}

		if (!softQueueNode.setNext (
			sift (
				nextSoftQueueNode
			)
		))
		{
			return null;
		}

		/*
		 * Rotation to maintain Heap Ordering after Sift
		 */

		nextSoftQueueNode = softQueueNode.next();

		org.drip.graph.store.SoftQueueNode childSoftQueueNode = softQueueNode.child();

		if (nextSoftQueueNode.ckey() > childSoftQueueNode.ckey())
		{
			org.drip.graph.store.SoftQueueNode tmpNode = childSoftQueueNode;

			if (!softQueueNode.setChild (
					nextSoftQueueNode
				) || !softQueueNode.setNext (
					tmpNode
				)
			)
			{
				return null;
			}
		}

		/*
		 * Updating the Soft Queue State
		 */

		nextSoftQueueNode = softQueueNode.next();

		if (!softQueueNode.setHeadItem (
				nextSoftQueueNode.headItem()
			) || !softQueueNode.setTailItem (
				nextSoftQueueNode.tailItem()
			) || !softQueueNode.setCKey (
				nextSoftQueueNode.ckey()
			)
		)
		{
			return null;
		}

		/*
		 * Test for Secondary Recursion Criterion
		 */

		double rank = softQueueNode.rank();

		if (rank > _r && (
			1 == rank % 2 || softQueueNode.child().rank() < rank - 1.
		))
		{
			if (!softQueueNode.setNext (
					sift (
						softQueueNode.next()
					)
				)
			)
			{
				return null;
			}

			/*
			 * Rotation to maintain Heap Ordering after Sift
			 */

			nextSoftQueueNode = softQueueNode.next();

			childSoftQueueNode = softQueueNode.child();

			if (nextSoftQueueNode.ckey() > childSoftQueueNode.ckey())
			{
				org.drip.graph.store.SoftQueueNode tmpNode = childSoftQueueNode;

				if (!softQueueNode.setChild (
						nextSoftQueueNode
					) || !softQueueNode.setNext (
						tmpNode
					)
				)
				{
					return null;
				}
			}

			/*
			 * Concatenation of the Item List
			 */

			nextSoftQueueNode = softQueueNode.next();

			if (java.lang.Double.POSITIVE_INFINITY != nextSoftQueueNode.ckey() &&
				null != nextSoftQueueNode.headItem())
			{
				if (!nextSoftQueueNode.tailItem().setNext (
						softQueueNode.headItem()
					) || !softQueueNode.setHeadItem (
						nextSoftQueueNode.headItem()
					)
				)
				{
					return null;
				}

				if (null == softQueueNode.tailItem())
				{
					if (!softQueueNode.setTailItem (
						nextSoftQueueNode.tailItem()
					))
					{
						return null;
					}
				}

				if (!softQueueNode.setCKey (
					nextSoftQueueNode.ckey()
				))
				{
					return null;
				}
			}
		}

		/*
		 * Cleaning up the Soft Queue's Next and Child
		 */

		nextSoftQueueNode = softQueueNode.next();

		if (java.lang.Double.POSITIVE_INFINITY == softQueueNode.child().ckey())
		{
			if (java.lang.Double.POSITIVE_INFINITY == nextSoftQueueNode.ckey())
			{
				if (!softQueueNode.setChild (
						null
					) || !softQueueNode.setNext (
						null
					)
				)
				{
					return null;
				}
			}
			else
			{
				if (!softQueueNode.setChild (
						nextSoftQueueNode.child()
					) || !softQueueNode.setNext (
						nextSoftQueueNode.next()
					)
				)
				{
					return null;
				}
			}
		}

		return softQueueNode;
	}

	/**
	 * Return the Item with the Smallest CKey and Delete it
	 * 
	 * @return The Item with the smallest CKey
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double deleteMin()
		throws java.lang.Exception
	{

		/*
		 * Checking for the Rank Invariance Violation
		 */

		org.drip.graph.store.HEAD h = _header.next().suffixMin();

		org.drip.graph.store.SoftQueueNode tmpSoftQueueNode = h.queue();

		while (null == tmpSoftQueueNode.headItem())
		{
			int childCount = 0;

			while (null != tmpSoftQueueNode.next())
			{
				tmpSoftQueueNode = tmpSoftQueueNode.next();

				++childCount;
			}

			/*
			 * Rank Invariance Violation Handling
			 */

			if (childCount < h.rank() / 2)
			{
				org.drip.graph.store.HEAD hNext = h.next();

				org.drip.graph.store.HEAD hPrev = h.prev();

				if (!hPrev.setNext (
						hNext
					) || !hNext.setPrev (
						hPrev
					) || !fixMinList (
						hPrev
					)
				)
				{
					throw new java.lang.Exception (
						"SoftHeap::deleteMin => Cannot Fix Rank Invariance Violation"
					);
				}

				tmpSoftQueueNode = h.queue();

				while (null != tmpSoftQueueNode.next())
				{
					if (!meld (
						tmpSoftQueueNode.child()
					))
					{
						throw new java.lang.Exception (
							"SoftHeap::deleteMin => Cannot Fix Rank Invariance Violation"
						);
					}

					tmpSoftQueueNode = tmpSoftQueueNode.next();
				}
			}
			else
			{

				/*
				 * Rank Invariance Holds: Refill Item List at the Root
				 */

				org.drip.graph.store.SoftQueueNode queue = h.queue();

				if (!h.setQueue (
					sift (
						queue
					)
				))
				{
					throw new java.lang.Exception (
						"SoftHeap::deleteMin => Cannot Complete Item List Sifting"
					);
				}

				if (java.lang.Double.POSITIVE_INFINITY == h.queue().ckey())
				{
					org.drip.graph.store.HEAD hNext = h.next();

					org.drip.graph.store.HEAD hPrev = h.prev();

					if (!hPrev.setNext (
							hNext
						) || !hNext.setPrev (
							hPrev
						)
					)
					{
						throw new java.lang.Exception (
							"SoftHeap::deleteMin => Cannot Complete Item List Sifting"
						);
					}

					h = hPrev;
				}

				if (!fixMinList (
					h
				))
				{
					throw new java.lang.Exception (
						"SoftHeap::deleteMin => Cannot Complete Item List Sifting"
					);
				}
			}

			h = _header.next().suffixMin();
		}

		/*
		 * Retrieve and Delete the Minimum Key
		 */

		org.drip.graph.store.SoftQueueNode hQueue = h.queue();

		org.drip.graph.store.ItemListEntry hQueueIL = hQueue.headItem();

		double min = hQueueIL.key();

		if (!hQueue.setHeadItem (
			hQueueIL.next()
		))
		{
			throw new java.lang.Exception (
				"SoftHeap::deleteMin => Cannot Delete the Minimum Key"
			);
		}

		if (null == hQueue.headItem())
		{
			if (!hQueue.setTailItem (
				null
			))
			{
				throw new java.lang.Exception (
					"SoftHeap::deleteMin => Cannot Delete the Minimum Key"
				);
			}
		}

		return min;
	}

	@Override public java.lang.String toString()
	{
		java.lang.String display = "{r = " + _r + ";";

		display = display + " Header = " + (null == _header ? "null;" : _header.toString() + ";");

		display = display + " Tail = " + (null == _tail ? "null;" : _tail.toString() + ";");

		return display + "}";
	}
}
