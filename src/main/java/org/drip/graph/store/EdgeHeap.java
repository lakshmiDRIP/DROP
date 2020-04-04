
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
 * <i>EdgeHeap</i> implements a Heap of Edges that Fan out of a Vertex. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Chazelle, B. (2000): A Minimum Spanning Tree Algorithm with Inverse-Ackerman Type Complexity
 *  			<i> Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1028-1047
 *  	</li>
 *  	<li>
 *  		Eppstein, D. (1999): Spanning Trees and Spanners
 *  			https://www.ics.uci.edu/~eppstein/pubs/Epp-TR-96-16.pdf
 *  	</li>
 *  	<li>
 *  		Gross, J. L., and J. Yellen (2005): <i>Graph Theory and its Applications</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Karger, D. R., P. N. Klein, and R. E. Tarjan (1995): A Randomized Linear-Time Algorithm to find
 *  			Minimum Spanning Trees <i> Journal of the Association for Computing Machinery</i> <b>42
 *  			(2)</b> 321-328
 *  	</li>
 *  	<li>
 *  		Pettie, S., and V. Ramachandran (2002): An Optimal Minimum Spanning Tree <i>Algorithm Journal of
 *  			the ACM</i> <b>49 (1)</b> 16-34
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

public class EdgeHeap
	implements org.drip.graph.store.PriorityHeap<org.drip.graph.core.Edge, java.lang.String>
{
	private java.util.Map<java.lang.String, org.drip.graph.core.Edge> _edgeMap = null;
	private java.util.Map<java.lang.String, java.lang.Integer> _destinationCounterMap = null;
	private java.util.TreeMap<java.lang.Double, java.util.List<java.lang.String>> _adjacentEdgeKeyListMap =
		null;

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

	private boolean addToAdjacencyEdgeKeyMap (
		final java.lang.String edgeKey,
		final double weight)
	{
		if (_adjacentEdgeKeyListMap.containsKey (
			weight
		))
		{
			_adjacentEdgeKeyListMap.get (
				weight
			).add (
				edgeKey
			);
		}
		else
		{
			java.util.List<java.lang.String> edgeKeyList = new java.util.ArrayList<java.lang.String>();

			edgeKeyList.add (
				edgeKey
			);

			_adjacentEdgeKeyListMap.put (
				weight,
				edgeKeyList
			);
		}

		return true;
	}

	private boolean removeFromAdjacencyEdgeKeyMap (
		final java.lang.String edgeKey,
		final double weight)
	{
		if (!_adjacentEdgeKeyListMap.containsKey (
			weight
		))
		{
			return false;
		}

		java.util.List<java.lang.String> adjacencyEdgeKeyList = _adjacentEdgeKeyListMap.get (
			weight
		);

		if (!adjacencyEdgeKeyList.contains (
			edgeKey
		))
		{
			return false;
		}

		adjacencyEdgeKeyList.remove (
			edgeKey
		);

		if (0 == adjacencyEdgeKeyList.size())
		{
			_adjacentEdgeKeyListMap.remove (
				weight
			);
		}

		return true;
	}

	/**
	 * EdgeHeap Constructor
	 */

	public EdgeHeap()
	{
		_edgeMap = new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.graph.core.Edge>();

		_destinationCounterMap = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();

		_adjacentEdgeKeyListMap =
			new java.util.TreeMap<java.lang.Double, java.util.List<java.lang.String>>();
	}

	/**
	 * Retrieve the Weight Order Map of Adjacent Edge Keys List
	 * 
	 * @return The Weight Order Map of Adjacent Edges Keys List
	 */

	public java.util.TreeMap<java.lang.Double, java.util.List<java.lang.String>> adjacentEdgeKeyListMap()
	{
		return _adjacentEdgeKeyListMap;
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
	 * Retrieve the Ordered Adjacency Key List
	 * 
	 * @param descending TRUE - The Edge List is in the Descending Order of Distance
	 * 
	 * @return The Ordered Adjacency Key List
	 */

	public java.util.List<java.lang.String> adjacencyKeyList (
		final boolean descending)
	{
		java.util.List<java.lang.String> adjacencyKeyList = new java.util.ArrayList<java.lang.String>();

		for (double distance : _adjacentEdgeKeyListMap.keySet())
		{
			for (java.lang.String edgeKey : _adjacentEdgeKeyListMap.get (
				distance
			))
			{
				if (descending)
				{
					adjacencyKeyList.add (
						0,
						edgeKey
					);
				}
				else
				{
					adjacencyKeyList.add (
						edgeKey
					);
				}
			}
		}

		return adjacencyKeyList;
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

		return addToAdjacencyEdgeKeyMap (
			edgeKey,
			edge.weight()
		) ? edgeKey : "";
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

		if (!removeFromAdjacencyEdgeKeyMap (
			edgeKey,
			edge.weight()
		))
		{
			return false;
		}

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
		int outDegree = 0;

		for (java.util.Map.Entry<java.lang.Double, java.util.List<java.lang.String>> adjacentEdgeKeyListEntry
			: _adjacentEdgeKeyListMap.entrySet())
		{
			outDegree = outDegree + adjacentEdgeKeyListEntry.getValue().size();
		}

		return outDegree;
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

	@Override public java.lang.String insert (
		final org.drip.graph.core.Edge edge)
	{
		return addEdge (
			edge
		);
	}

	@Override public boolean meld (
		final org.drip.graph.store.PriorityHeap<org.drip.graph.core.Edge, java.lang.String>
			priorityHeapOther)
	{
		if (!(priorityHeapOther instanceof EdgeHeap))
		{
			return false;
		}

		for (org.drip.graph.core.Edge edge : ((EdgeHeap) priorityHeapOther).edgeMap().values())
		{
			java.lang.String edgeKey = addEdge (
				edge
			);

			if (null == edgeKey || edgeKey.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override public org.drip.graph.core.Edge delete (
		final java.lang.String edgeKey)
	{
		if (null == edgeKey ||
			!_edgeMap.containsKey (
				edgeKey
			)
		)
		{
			return null;
		}

		org.drip.graph.core.Edge edge = _edgeMap.get (
			edgeKey
		);

		if (!removeFromAdjacencyEdgeKeyMap (
			edgeKey,
			edge.weight()
		))
		{
			return null;
		}

		if (!decrementDestinationEdgeCounterMap (
			edge.destinationVertexName()
		))
		{
			return null;
		}

		_edgeMap.remove (
			edgeKey
		);

		return edge;
	}

	@Override public org.drip.graph.core.Edge findMinimum()
	{
		java.util.Map.Entry<java.lang.Double, java.util.List<java.lang.String>> minimumEntry =
			_adjacentEdgeKeyListMap.firstEntry();

		return null == minimumEntry ? null : _edgeMap.get (
			minimumEntry.getValue().get (
				0
			)
		);
	}

	@Override public org.drip.graph.core.Edge deleteMinimum()
	{
		java.util.Map.Entry<java.lang.Double, java.util.List<java.lang.String>> minimumEntry =
			_adjacentEdgeKeyListMap.firstEntry();

		return null == minimumEntry ? null : delete (
			minimumEntry.getValue().get (
				0
			)
		);
	}

	@Override public org.drip.graph.core.Edge findMaximum()
	{
		java.util.Map.Entry<java.lang.Double, java.util.List<java.lang.String>> maximumEntry =
			_adjacentEdgeKeyListMap.lastEntry();

		return null == maximumEntry ? null : _edgeMap.get (
			maximumEntry.getValue().get (
				0
			)
		);
	}

	@Override public org.drip.graph.core.Edge deleteMaximum()
	{
		java.util.Map.Entry<java.lang.Double, java.util.List<java.lang.String>> maximumEntry =
			_adjacentEdgeKeyListMap.lastEntry();

		return null == maximumEntry ? null : delete (
			maximumEntry.getValue().get (
				0
			)
		);
	}
}
