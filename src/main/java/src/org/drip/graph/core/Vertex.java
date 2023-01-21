
package org.drip.graph.core;

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
 * <i>Vertex</i> implements a Single Vertex Node and the corresponding Egresses emanating from it. The
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/core/README.md">Vertexes, Edges, Trees, and Graphs</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Vertex
{
	private java.lang.String _name = "";
	private java.util.Map<java.lang.String, org.drip.graph.core.Edge> _edgeMap = null;
	private java.util.Map<java.lang.String, java.lang.Integer> _destinationCounterMap = null;


	private int incrementDestinationEdgeCounterMap (
		final java.lang.String destinationVertexName)
	{
		int destinationCounter = 1;

		if (_destinationCounterMap.containsKey (
			destinationVertexName
		))
		{
			destinationCounter = _destinationCounterMap.get (
				destinationVertexName
			) + 1;
		}

		_destinationCounterMap.put (
			destinationVertexName,
			destinationCounter
		);

		return destinationCounter;
	}

	private boolean decrementDestinationEdgeCounterMap (
		final java.lang.String destinationVertexName)
	{
		if (!_destinationCounterMap.containsKey (
			destinationVertexName
		))
		{
			return false;
		}

		int destinationCounter = _destinationCounterMap.get (
			destinationVertexName
		) - 1;

		if (0 >= destinationCounter)
		{
			_destinationCounterMap.remove (
				destinationVertexName
			);
		}
		else
		{
			_destinationCounterMap.put (
				destinationVertexName,
				destinationCounter
			);
		}

		return true;
	}

	/**
	 * Vertex Constructor
	 * 
	 * @param name The Vertex Name
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Vertex (
		final java.lang.String name)
		throws java.lang.Exception
	{
		if (null == (_name = name) || _name.isEmpty())
		{
			throw new java.lang.Exception (
				"Vertex Constructor => Invalid Inputs"
			);
		}

		_edgeMap = new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.graph.core.Edge>();

		_destinationCounterMap = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
	}

	/**
	 * Retrieve the Vertex Name
	 * 
	 * @return The Vertex Name
	 */

	public java.lang.String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Destination Edge Counter Map
	 * 
	 * @return The Destination Edge Counter Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> destinationCounterMap()
	{
		return _destinationCounterMap;
	}

	/**
	 * Retrieve the Edge Map
	 * 
	 * @return The Edge Map
	 */

	public java.util.Map<java.lang.String, org.drip.graph.core.Edge> edgeMap()
	{
		return _edgeMap;
	}

	/**
	 * Add an Edge
	 * 
	 * @param edge The Edge
	 * 
	 * @return The Edge Key
	 */

	public java.lang.String addEdge (
		final org.drip.graph.core.Edge edge)
	{
		if (null == edge)
		{
			return "";
		}

		java.lang.String destinationVertexName = edge.destinationVertexName();

		java.lang.String edgeKey = edge.sourceVertexName() + "_" + destinationVertexName + "@" +
			incrementDestinationEdgeCounterMap (
				destinationVertexName
			);

		_edgeMap.put (
			edgeKey,
			edge
		);

		return edgeKey;
	}

	/**
	 * Remove the Edge from the Edge Map
	 * 
	 * @param edgeKey The Edge Key
	 * 
	 * @return TRUE - The Edge represented by the Key successfully removed
	 */

	public boolean removeEdge (
		final java.lang.String edgeKey)
	{
		if (null == edgeKey ||
			!_edgeMap.containsKey (
				edgeKey
			)
		)
		{
			return false;
		}

		org.drip.graph.core.Edge edge = _edgeMap.get (
			edgeKey
		);

		if (!decrementDestinationEdgeCounterMap (
			edge.destinationVertexName()
		))
		{
			return false;
		}

		_edgeMap.remove (
			edgeKey
		);

		return true;
	}

	/**
	 * Retrieve the Ordered Adjacency Priority Queue
	 * 
	 * @param minHeap TRUE - The Priority Queue is in the Ascending Order of Weight
	 * 
	 * @return The Ordered Adjacency Priority Queue
	 */

	public org.drip.graph.heap.PriorityQueue<java.lang.Double, org.drip.graph.core.Edge>
		adjacencyPriorityQueue (
			final boolean minHeap)
	{
		org.drip.graph.heap.PriorityQueue<java.lang.Double, org.drip.graph.core.Edge> edgePriorityQueue =
			new org.drip.graph.heap.BinomialTreePriorityQueue<java.lang.Double, org.drip.graph.core.Edge> (
				minHeap
			);

		for (java.util.Map.Entry<java.lang.String, org.drip.graph.core.Edge> edgeMapEntry :
			_edgeMap.entrySet())
		{
			org.drip.graph.core.Edge edge = edgeMapEntry.getValue();

			edgePriorityQueue.insert (
				edge.weight(),
				edge
			);
		}

		return edgePriorityQueue;
	}

	/**
	 * Generate the List of Edges between the Source and the Destination
	 * 
	 * @param sourceVertexName Source Vertex Name
	 * @param destinationVertexName Destination Vertex Name
	 * 
	 * @return List of Edges between the Source and the Destination
	 */

	public java.util.List<org.drip.graph.core.Edge> destinationEdgeList (
		java.lang.String sourceVertexName,
		java.lang.String destinationVertexName)
	{
		if (null == sourceVertexName || sourceVertexName.isEmpty() ||
			null == destinationVertexName || destinationVertexName.isEmpty() ||
			!_destinationCounterMap.containsKey (
				destinationVertexName
			)
		)
		{
			return null;
		}

		java.util.List<org.drip.graph.core.Edge> destinationEdgeList =
			new java.util.ArrayList<org.drip.graph.core.Edge>();

		int destinationEdgeCount = _destinationCounterMap.get (
			destinationVertexName
		);

		for (int destinationEdgeIndex = 0;
			destinationEdgeIndex < destinationEdgeCount;
			++destinationEdgeIndex)
		{
			java.lang.String edgeKey = sourceVertexName + "_" + destinationVertexName + "_" +
				destinationEdgeIndex;

			if (!_edgeMap.containsKey (
				edgeKey
			))
			{
				return null;
			}

			destinationEdgeList.add (
				_edgeMap.get (
					edgeKey
				)
			);
		}

		return destinationEdgeList;
	}

	/**
	 * Retrieve the Set of Neighboring Vertex Names
	 * 
	 * @return The Set of Neighboring Vertex Names
	 */

	public java.util.Set<java.lang.String> neighboringVertexNameSet()
	{
		return _destinationCounterMap.keySet();
	}

	/**
	 * Retrieve the Out-Degree of the Vertex
	 * 
	 * @return Out-Degree of the Vertex
	 */

	public int outDegree()
	{
		return _edgeMap.size();
	}

	/**
	 * Retrieve the Branching Factor of the Vertex
	 * 
	 * @return Branching Factor of the Vertex
	 */

	public int branchingFactor()
	{
		return outDegree();
	}

	/**
	 * Indicate if the Vertex is a Leaf
	 * 
	 * @return TRUE - The Vertex is a Leaf
	 */

	public boolean isLeaf()
	{
		return 0 == _edgeMap.size();
	}
}
