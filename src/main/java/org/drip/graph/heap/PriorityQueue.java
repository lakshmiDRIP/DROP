
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
 * <i>PriorityQueue</i> exposes the Stubs of a Priority Queue's Operations. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Brodal, G. S. (1996): Priority Queue on Parallel Machines <i>Scandinavian Workshop on Algorithm
 *  			Theory – SWAT ’96</i> 416-427
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Sanders, P., K. Mehlhorn, M. Dietzfelbinger, and R. Dementiev (2019): <i>Sequential and Parallel
 *  			Algorithms and Data Structures – A Basic Toolbox</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Sundell, H., and P. Tsigas (2005): Fast and Lock-free Concurrent Priority Queues for
 *  			Multi-threaded Systems <i>Journal of Parallel and Distributed Computing</i> <b>65 (5)</b>
 *  			609-627
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Priority Queue https://en.wikipedia.org/wiki/Priority_queue
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

public abstract class PriorityQueue<K, V>
{
	private boolean _minHeap = false;

	/**
	 * PriorityQueue Constructor
	 * 
	 * @param minHeap TRUE - Indicates that Heap is a Min Heap
	 */

	public PriorityQueue (
		final boolean minHeap)
	{
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
	 * Insert the Specified Key into the Heap
	 * 
	 * @param key Key
	 * @param value Node Value
	 * 
	 * @return TRUE - The Key successfully inserted
	 */

	public abstract boolean insert (
		final K key,
		final V value);

	/**
	 * Meld the Specified Priority Queue with the Current
	 * 
	 * @param priorityQueueOther The Specified Binary Tree Priority Queue
	 * 
	 * @return TRUE - The Specified Priority Queue successfully melded
	 */

	public abstract boolean meld (
		final org.drip.graph.heap.PriorityQueue<K, V> priorityQueueOther);

	/**
	 * Retrieve the Top from the Heap
	 * 
	 * @return The Top Key in the Heap
	 */

	public abstract org.drip.graph.heap.PriorityQueueEntry<K, V> extremum();

	/**
	 * Extract the Top from the Heap
	 * 
	 * @return The Top Key in the Heap
	 */

	public abstract org.drip.graph.heap.PriorityQueueEntry<K, V> extractExtremum();

	/**
	 * Indicate if the Heap is Empty
	 * 
	 * @return TRUE - The Heap is Empty
	 */

	public abstract boolean isEmpty();

	/**
	 * Generate the Sorted Key List
	 * 
	 * @return The Sorted Key List
	 */

	public java.util.List<K> keyList()
	{
		java.util.List<K> sortedKeyList = new java.util.ArrayList<K>();

		while (!isEmpty())
		{
			sortedKeyList.add (
				extractExtremum().key()
			);
		}

		return sortedKeyList;
	}

	/**
	 * Generate the Sorted Entry List
	 * 
	 * @return The Sorted Entry List
	 */

	public java.util.List<org.drip.graph.heap.PriorityQueueEntry<K, V>> entryList()
	{
		java.util.List<org.drip.graph.heap.PriorityQueueEntry<K, V>> sortedEntryList =
			new java.util.ArrayList<org.drip.graph.heap.PriorityQueueEntry<K, V>>();

		while (!isEmpty())
		{
			sortedEntryList.add (
				extractExtremum()
			);
		}

		return sortedEntryList;
	}
}
