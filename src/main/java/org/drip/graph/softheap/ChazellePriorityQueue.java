
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
 * <i>ChazellePriorityQueue</i> implements the Chazelle (2000) Verison of Soft Heap. The References are:
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

public class ChazellePriorityQueue
{
	private double _r = java.lang.Double.NaN;
	private org.drip.graph.softheap.ChazelleTree _tail = null;
	private org.drip.graph.softheap.ChazelleTree _head = null;

	/**
	 * Create a Fresh Instance of Soft-Heap
	 * 
	 * @param headerRank The Header Rank
	 * @param r r
	 * 
	 * @return Fresh Instance of Soft-Heap
	 */

	/* public static final ChazellePriorityQueue Create (
		final double headerRank,
		final double r)
	{
		org.drip.graph.softheap.ChazelleTree header = new org.drip.graph.softheap.ChazelleTree();

		if (!header.setRank (
			headerRank
		))
		{
			return null;
		}

		org.drip.graph.softheap.ChazelleTree tail = new org.drip.graph.softheap.ChazelleTree();

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
			return new ChazellePriorityQueue (
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
	} */

	/**
	 * ChazellePriorityQueue Constructor
	 * 
	 * @param r r
	 * @param head Header
	 * @param tail Tail
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ChazellePriorityQueue (
		final double r,
		final org.drip.graph.softheap.ChazelleTree head,
		final org.drip.graph.softheap.ChazelleTree tail)
		throws java.lang.Exception
	{
		if (0 > (_r = r) ||
			null == (_head = head) ||
			null == (_tail = tail)
		)
		{
			throw new java.lang.Exception (
				"ChazellePriorityQueue Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Head of the List of Trees
	 * 
	 * @return Head of the List of Trees
	 */

	public org.drip.graph.softheap.ChazelleTree head()
	{
		return _head;
	}

	/**
	 * Retrieve the Tail of the List of Trees
	 * 
	 * @return Tail of the List of Trees
	 */

	public org.drip.graph.softheap.ChazelleTree tail()
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
	 * Retrieve the Rank of the Heap
	 * 
	 * @return Rank of the Heap
	 */

	public double rank()
	{
		return _head.rank();
	}

	/**
	 * Meld the Specified Soft Queue into the current Soft Heap
	 * 
	 * @param softQueueNode The Specified Soft Queue
	 * 
	 * @return TRUE - The Specified Soft Queue successfully melded
	 */

	/* public boolean meld (
		org.drip.graph.softheap.ChazelleSoftQueueNode softQueueNode)
	{ */

		/*
		 * Heap Rank corresponding to softQueueNode
		 */

		/* if (null == softQueueNode)
		{
			return false;
		}

		org.drip.graph.softheap.ChazelleTree toHead = _head.next();

		while (softQueueNode.k() > toHead.rank())
		{
			toHead = toHead.next();
		}

		int r = softQueueNode.r();

		org.drip.graph.softheap.ChazelleTree prevHead = toHead.prev(); */

		/*
		 * Carry Propagation to handle Pre-existing Ranks
		 */

		/* while (softQueueNode.k() == toHead.rank())
		{
			org.drip.graph.softheap.ChazelleSoftQueueNode nextSoftQueueNode = null;
			org.drip.graph.softheap.ChazelleSoftQueueNode childSoftQueueNode = null;

			org.drip.graph.softheap.ChazelleSoftQueueNode toHeadSoftQueueNode = toHead.root();

			if (1 == toHeadSoftQueueNode.cEntry().compareTo (
				softQueueNode.cEntry()
			))
			{
				nextSoftQueueNode = softQueueNode;
				childSoftQueueNode = toHeadSoftQueueNode;
			}
			else
			{
				childSoftQueueNode = softQueueNode;
				nextSoftQueueNode = toHeadSoftQueueNode;
			}

			softQueueNode = org.drip.graph.softheap.ChazelleSoftQueueNode.LeafRoot (
				r,
				nextSoftQueueNode.cEntry()
			);

			if (!softQueueNode.setRank (
					nextSoftQueueNode.k() + 1.
				) || !softQueueNode.setNext (
					nextSoftQueueNode
				) || !softQueueNode.setChild (
					childSoftQueueNode
				) || !softQueueNode.setHeadEntry (
					nextSoftQueueNode.headEntry()
				) || !softQueueNode.setTailEntry (
					nextSoftQueueNode.tailEntry()
				)
			)
			{
				return false;
			}

			toHead = toHead.next();
		} */

		/*
		 * Insertion of the New Queue
		 */

		/* org.drip.graph.softheap.ChazelleTree h = prevHead == toHead.prev() ? 
			new org.drip.graph.softheap.ChazelleTree() : prevHead.next();

		return h.setQueue (
			softQueueNode
		) && h.setRank (
			softQueueNode.k()
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
	} */

	/**
	 * Insert a Key into the Soft Heap
	 * 
	 * @param key The Key
	 * 
	 * @return TRUE - The Key successfully inserted
	 */

	/* public boolean insert (
		final double key)
	{
		org.drip.graph.softheap.ChazelleEntryList itemListEntry = new org.drip.graph.softheap.ChazelleEntryList();

		if (!itemListEntry.setKey (
			key
		))
		{
			return false;
		}

		org.drip.graph.softheap.ChazelleSoftQueueNode softQueueNode = new org.drip.graph.softheap.ChazelleSoftQueueNode();

		return softQueueNode.setRank (
			0
		) && softQueueNode.setCKey (
			key
		) && softQueueNode.setHeadEntry (
			itemListEntry
		) && softQueueNode.setTailEntry (
			itemListEntry
		) && meld (
			softQueueNode
		);
	} */

	/**
	 * Replace the Empty-list at the Specified Soft Queue and return it
	 * 
	 * @param softQueueNode The Soft Queue
	 * 
	 * @return The Empty-list at the Specified Soft Queue successfully replaced and returned
	 */

	/* public org.drip.graph.softheap.ChazelleSoftQueueNode sift (
		final org.drip.graph.softheap.ChazelleSoftQueueNode softQueueNode)
	{
		if (!softQueueNode.setHeadEntry (
				null
			) || !softQueueNode.setTailEntry (
				null
			)
		)
		{
			return null;
		} */

		/*
		 * Stop Recursion if the Node is a Leaf
		 */

		/* org.drip.graph.softheap.ChazelleSoftQueueNode nextSoftQueueNode = softQueueNode.next();

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
		} */

		/*
		 * Rotation to maintain Heap Ordering after Sift
		 */

		/* nextSoftQueueNode = softQueueNode.next();

		org.drip.graph.softheap.ChazelleSoftQueueNode childSoftQueueNode = softQueueNode.child();

		if (nextSoftQueueNode.ckey() > childSoftQueueNode.ckey())
		{
			org.drip.graph.softheap.ChazelleSoftQueueNode tmpNode = childSoftQueueNode;

			if (!softQueueNode.setChild (
					nextSoftQueueNode
				) || !softQueueNode.setNext (
					tmpNode
				)
			)
			{
				return null;
			}
		} */

		/*
		 * Updating the Soft Queue State
		 */

		/* nextSoftQueueNode = softQueueNode.next();

		if (!softQueueNode.setHeadEntry (
				nextSoftQueueNode.headEntry()
			) || !softQueueNode.setTailEntry (
				nextSoftQueueNode.tailEntry()
			) || !softQueueNode.setCKey (
				nextSoftQueueNode.ckey()
			)
		)
		{
			return null;
		} */

		/*
		 * Test for Secondary Recursion Criterion
		 */

		/* double rank = softQueueNode.k();

		if (rank > _r && (
			1 == rank % 2 || softQueueNode.child().k() < rank - 1.
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
			} */

			/*
			 * Rotation to maintain Heap Ordering after Sift
			 */

			/* nextSoftQueueNode = softQueueNode.next();

			childSoftQueueNode = softQueueNode.child();

			if (nextSoftQueueNode.ckey() > childSoftQueueNode.ckey())
			{
				org.drip.graph.softheap.ChazelleSoftQueueNode tmpNode = childSoftQueueNode;

				if (!softQueueNode.setChild (
						nextSoftQueueNode
					) || !softQueueNode.setNext (
						tmpNode
					)
				)
				{
					return null;
				}
			} */

			/*
			 * Concatenation of the Item List
			 */

			/* nextSoftQueueNode = softQueueNode.next();

			if (java.lang.Double.POSITIVE_INFINITY != nextSoftQueueNode.ckey() &&
				null != nextSoftQueueNode.headEntry())
			{
				if (!nextSoftQueueNode.tailEntry().setNext (
						softQueueNode.headEntry()
					) || !softQueueNode.setHeadEntry (
						nextSoftQueueNode.headEntry()
					)
				)
				{
					return null;
				}

				if (null == softQueueNode.tailEntry())
				{
					if (!softQueueNode.setTailEntry (
						nextSoftQueueNode.tailEntry()
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
		} */

		/*
		 * Cleaning up the Soft Queue's Next and Child
		 */

		/* nextSoftQueueNode = softQueueNode.next();

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
	} */

	/**
	 * Return the Item with the Smallest CKey and Delete it
	 * 
	 * @return The Item with the smallest CKey
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	/* public double deleteMin()
		throws java.lang.Exception
	{ */

		/*
		 * Checking for the Rank Invariance Violation
		 */

		/* org.drip.graph.softheap.ChazelleTree h = _header.next().suffixMin();

		org.drip.graph.softheap.ChazelleSoftQueueNode tmpSoftQueueNode = h.queue();

		while (null == tmpSoftQueueNode.headEntry())
		{
			int childCount = 0;

			while (null != tmpSoftQueueNode.next())
			{
				tmpSoftQueueNode = tmpSoftQueueNode.next();

				++childCount;
			} */

			/*
			 * Rank Invariance Violation Handling
			 */

			/* if (childCount < h.rank() / 2)
			{
				org.drip.graph.softheap.ChazelleTree hNext = h.next();

				org.drip.graph.softheap.ChazelleTree hPrev = h.prev();

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
			{ */

				/*
				 * Rank Invariance Holds: Refill Item List at the Root
				 */

				/* org.drip.graph.softheap.ChazelleSoftQueueNode queue = h.queue();

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
					org.drip.graph.softheap.ChazelleTree hNext = h.next();

					org.drip.graph.softheap.ChazelleTree hPrev = h.prev();

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
		} */

		/*
		 * Retrieve and Delete the Minimum Key
		 */

		/* org.drip.graph.softheap.ChazelleSoftQueueNode hQueue = h.queue();

		org.drip.graph.softheap.ChazelleEntryList hQueueIL = hQueue.headEntry();

		double min = hQueueIL.key();

		if (!hQueue.setHeadEntry (
			hQueueIL.next()
		))
		{
			throw new java.lang.Exception (
				"SoftHeap::deleteMin => Cannot Delete the Minimum Key"
			);
		}

		if (null == hQueue.headEntry())
		{
			if (!hQueue.setTailEntry (
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
	} */
}
