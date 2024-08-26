
package org.drip.graph.mstgreedy;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>BoruvkaForest</i> implements the Extensions to a Forest required by the Boruvka MSF Generator. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bollobas, B. (1998): <i>Modern Graph Theory</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Eppstein, D. (1999): Spanning Trees and Spanners
 *  			https://www.ics.uci.edu/~eppstein/pubs/Epp-TR-96-16.pdf
 *  	</li>
 *  	<li>
 *  		Gross, J. L., and J. Yellen (2005): <i>Graph Theory and its Applications</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Kocay, W., and D. L. Kreher (2004): <i>Graphs, Algorithms, and Optimizations</i> <b>CRC Press</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Spanning Tree https://en.wikipedia.org/wiki/Spanning_tree
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mstgreedy/README.md">Greedy Algorithms for MSTs and Forests</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoruvkaForest<V>
	extends org.drip.graph.mstgreedy.KruskalForest<V>
{
	private java.util.Map<java.lang.String, java.lang.Double> _treeNameDistanceMap = null;
	private org.drip.graph.heap.PriorityQueue<java.lang.Double, org.drip.graph.core.Tree<?>>
		_orderedTreePriorityQueue = null;

	private boolean updateOrderedTreeMap (
		final java.lang.String treeName,
		final org.drip.graph.core.Tree<?> tree,
		final org.drip.graph.core.Directed<?> graph,
		final boolean descending)
	{
		org.drip.graph.heap.PriorityQueue<java.lang.Double, org.drip.graph.core.Edge> edgePriorityQueue =
			tree.edgePriorityQueue (
				graph,
				_orderedTreePriorityQueue.minHeap()
			);

		if (null != edgePriorityQueue && !edgePriorityQueue.isEmpty())
		{
			double distance = edgePriorityQueue.extremum().item().weight();

			_orderedTreePriorityQueue.insert (
				distance,
				tree
			);

			_treeNameDistanceMap.put (
				treeName,
				distance
			);
		}

		return true;
	}

	private boolean updateOrderedTreeMap (
		final java.lang.String sourceTreeName,
		final java.lang.String destinationTreeName,
		final org.drip.graph.core.Directed<?> graph,
		final boolean minHeap)
	{
		_orderedTreePriorityQueue.delete (
			_treeNameDistanceMap.get (
				sourceTreeName
			)
		);

		_orderedTreePriorityQueue.delete (
			_treeNameDistanceMap.get (
				destinationTreeName
			)
		);

		_treeNameDistanceMap.remove (
			destinationTreeName
		);

		org.drip.graph.core.Tree<?> sourceTree = treeMap().get (
			sourceTreeName
		);

		org.drip.graph.heap.PriorityQueue<java.lang.Double, org.drip.graph.core.Edge>
			sourceTreeEdgePriorityQueue = sourceTree.edgePriorityQueue (
				graph,
				_orderedTreePriorityQueue.minHeap()
			);

		if (null != sourceTreeEdgePriorityQueue && !sourceTreeEdgePriorityQueue.isEmpty())
		{
			double distance = sourceTreeEdgePriorityQueue.extremum().item().weight();

			_orderedTreePriorityQueue.insert (
				distance,
				sourceTree
			);

			_treeNameDistanceMap.put (
				sourceTreeName,
				distance
			);
		}

		return true;
	}

	/**
	 * BoruvkaForest Constructor
	 * 
	 * @param minHeap TRUE - Minimum Heap
	 */

	public BoruvkaForest (
		final boolean minHeap)
	{
		super();

		_treeNameDistanceMap = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		_orderedTreePriorityQueue =
			new org.drip.graph.heap.BinomialTreePriorityQueue<java.lang.Double, org.drip.graph.core.Tree<?>> (
				minHeap
			);
	}

	/**
	 * Create and add a Unit Vertex Tree into the Forest
	 * 
	 * @param vertexName Vertex Name
	 * @param graph Parent Graph
	 * 
	 * @return TRUE - The Unit Vertex Tree successfully added into the Forest
	 */

	public boolean unitVertexTree (
		final java.lang.String vertexName,
		final org.drip.graph.core.Directed<?> graph)
	{
		return super.unitVertexTree (
			vertexName,
			graph
		) && updateOrderedTreeMap (
			vertexName,
			treeMap().get (
				vertexName
			),
			graph,
			false
		);
	}

	/**
	 * Add a Named Tree to the Forest
	 *  
	 * @param treeName The Tree Name
	 * @param tree The Tree
	 * @param graph The Graph
	 * @param descending Tree Neighbor Set in Descending Order
	 * 
	 * @return TRUE - The Keyed Tree successfully added to the Forest
	 */

	public boolean addTree (
		final java.lang.String treeName,
		final org.drip.graph.core.Tree<?> tree,
		final org.drip.graph.core.Directed<?> graph,
		final boolean descending)
	{
		return super.addTree (
			treeName,
			tree,
			graph
		) && updateOrderedTreeMap (
			treeName,
			tree,
			graph,
			descending
		);
	}

	/**
	 * Perform a Conditional Boruvka Merge Using the Graph
	 * 
	 * @param graph The Graph
	 * @param maximum TRUE - The Maximum Spanning Forest is to be generated
	 * 
	 * @return TRUE - The Boruvka Merge finished successfully
	 */

	public boolean conditionalBoruvkaMerge (
		final org.drip.graph.core.Directed<?> graph,
		final boolean maximum)
	{
		org.drip.graph.core.Edge edge = _orderedTreePriorityQueue.extremum().item().edgePriorityQueue (
			graph,
			_orderedTreePriorityQueue.minHeap()
		).extremum().item();

		if (null == edge ||
			!super.conditionalMerge (
				edge,
				graph
			)
		)
		{
			return false;
		}

		java.util.Map<java.lang.String, java.lang.String> containingTreeNameMap = containingTreeNameMap();

		return updateOrderedTreeMap (
			containingTreeNameMap.get (
				edge.sourceVertexName()
			),
			containingTreeNameMap.get (
				edge.destinationVertexName()
			),
			graph,
			_orderedTreePriorityQueue.minHeap()
		);
	}
}
