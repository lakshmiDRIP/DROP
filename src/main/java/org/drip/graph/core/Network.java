
package org.drip.graph.core;

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
 * <i>Network</i> implements a Generic Topological Network containing Discrete Vertexes and Edges. The
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
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/core/README.md">Vertexes, Edges, Trees, and Graphs</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

abstract public class Network
{
	protected java.util.Map<java.lang.String, org.drip.graph.core.Vertex> _vertexMap = null;
	protected java.util.Map<java.lang.String, org.drip.graph.core.BidirectionalEdge> _edgeMap = null;

	protected boolean addVertexEdge (
		final java.lang.String vertexName,
		final org.drip.graph.core.BidirectionalEdge edge)
	{
		if (_vertexMap.containsKey (
			vertexName
		))
		{
			return _vertexMap.get (
				vertexName
			).addEdge (
				edge
			);
		}

		try
		{
			org.drip.graph.core.Vertex vertex = new org.drip.graph.core.Vertex (
				vertexName
			);

			if (!vertex.addEdge (
				edge
			))
			{
				return false;
			}

			_vertexMap.put (
				vertexName,
				vertex
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	protected Network()
	{
		_edgeMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.graph.core.BidirectionalEdge>();

		_vertexMap =
			new org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.graph.core.Vertex>();
	}

	/**
	 * Retrieve the Vertex Map
	 * 
	 * @return The Vertex Map
	 */

	public java.util.Map<java.lang.String, org.drip.graph.core.Vertex> vertexMap()
	{
		return _vertexMap;
	}

	/**
	 * Retrieve the Edge Map
	 * 
	 * @return The Edge Map
	 */

	public java.util.Map<java.lang.String, org.drip.graph.core.BidirectionalEdge> edgeMap()
	{
		return _edgeMap;
	}

	/**
	 * Retrieve the Count of the Edges
	 * 
	 * @return The Count of the Edges
	 */

	public int edgeCount()
	{
		return _edgeMap.size();
	}

	/**
	 * Retrieve the Count of the Vertexes
	 * 
	 * @return The Count of the Vertexes
	 */

	public int vertexCount()
	{
		return _vertexMap.size();
	}

	/**
	 * Retrieve the Set of Vertexes
	 * 
	 * @return The Set of Vertexes
	 */

	public java.util.Set<java.lang.String> vertexSet()
	{
		return _vertexMap.keySet();
	}

	/**
	 * Add a Vertex to the Network
	 *  
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Vertex successfully added to the Network
	 */

	public boolean addVertex (
		final java.lang.String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty() ||
			!_vertexMap.containsKey (
				vertexName
			)
		)
		{
			return false;
		}

		try
		{
			_vertexMap.put (
				vertexName,
				new org.drip.graph.core.Vertex (
					vertexName
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Add a Bidirectional Edge to the Network
	 * 
	 * @param bidirectionalEdge The Bidirectional Edge
	 * 
	 * @return TRUE - The Bidirectional Edge successfully added
	 */

	public boolean addBidirectionalEdge (
		final org.drip.graph.core.BidirectionalEdge bidirectionalEdge)
	{
		if (null == bidirectionalEdge)
		{
			return false;
		}

		java.lang.String firstVertexName = bidirectionalEdge.firstVertexName();

		java.lang.String secondVertexName = bidirectionalEdge.secondVertexName();

		if (!addVertexEdge (
			firstVertexName,
			bidirectionalEdge
		) || !addVertexEdge (
			secondVertexName,
			bidirectionalEdge
		))
		{
			return false;
		}

		_edgeMap.put (
			firstVertexName + "_" + secondVertexName,
			bidirectionalEdge
		);

		_edgeMap.put (
			secondVertexName + "_" + firstVertexName,
			bidirectionalEdge
		);

		return true;
	}

	/**
	 * Construct an Ordered Edge Map
	 * 
	 * @return The Ordered Edge Map
	 */

	public java.util.TreeMap<java.lang.Double, org.drip.graph.core.BidirectionalEdge> orderedEdgeMap()
	{
		java.util.TreeMap<java.lang.Double, org.drip.graph.core.BidirectionalEdge> orderedEdgeMap =
			new java.util.TreeMap<java.lang.Double, org.drip.graph.core.BidirectionalEdge>();

		for (org.drip.graph.core.BidirectionalEdge edge : _edgeMap.values())
		{
			orderedEdgeMap.put (
				edge.distance(),
				edge
			);
		}

		return orderedEdgeMap;
	}

	/**
	 * Indicate if the Network is Empty
	 * 
	 * @return TRUE - The Network is Empty
	 */

	public boolean isEmpty()
	{
		return _vertexMap.isEmpty();
	}

	/**
	 * Indicate if the Vertex is Contained in the Network
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Vertex is Contained in the Network
	 */

	public boolean containsVertex (
		final java.lang.String vertexName)
	{
		return null != vertexName && _vertexMap.containsKey (
			vertexName
		);
	}

	/**
	 * Retrieve the Length of the Discrete Object
	 * 
	 * @return Length of the Discrete Object
	 */

	public double length()
	{
		double length = 0.;

		for (org.drip.graph.core.BidirectionalEdge edge : _edgeMap.values())
		{
			length = length + (edge instanceof org.drip.graph.core.BidirectionalEdge ? 0.5 : 1.) *
				edge.distance();
		}

		return length;
	}

	/**
	 * Generate the Vertex Set using a Recursive Depth-First Search
	 * 
	 * @param vertexName Vertex Name
	 * @param orderedSearch The Ordered Search Holder
	 * 
	 * @return The Vertex Set using a Recursive Depth-First Search
	 */

	public boolean dfsRecursive (
		final java.lang.String vertexName,
		final org.drip.graph.core.OrderedSearch orderedSearch)
	{
		if (null == orderedSearch ||
			!_vertexMap.containsKey (
				vertexName
			)
		)
		{
			return false;
		}

		orderedSearch.addVertex (
			vertexName
		);

		org.drip.graph.core.Vertex vertex = _vertexMap.get (
			vertexName
		);

		java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> adjacencyMap = vertex.adjacencyMap();

		if (null == adjacencyMap || 0 == adjacencyMap.size())
		{
			return true;
		}

		for (java.util.Map.Entry<java.lang.Double, org.drip.graph.core.BidirectionalEdge> egressMapEntry :
			adjacencyMap.entrySet())
		{
			java.lang.String secondVertexName = egressMapEntry.getValue().secondVertexName();

			if (!orderedSearch.vertexPresent (
				secondVertexName
			))
			{
				if (!dfsRecursive (
					secondVertexName,
					orderedSearch
				))
				{
					return false;
				}
			}
			else
			{
				orderedSearch.setContainsCycle();
			}
		}

		return true;
	}

	/**
	 * Generate the Vertex Set using a Non-recursive Depth-First Search
	 * 
	 * @param vertexName Vertex Name
	 * @param orderedSearch The Ordered Search Holder
	 * 
	 * @return The Vertex Set using a Non-recursive Depth-First Search
	 */

	public boolean dfsNonRecursive (
		final java.lang.String vertexName,
		final org.drip.graph.core.OrderedSearch orderedSearch)
	{
		if (null == orderedSearch ||
			!_vertexMap.containsKey (
				vertexName
			)
		)
		{
			return false;
		}

		java.util.List<java.lang.String> processVertexList = new java.util.ArrayList<java.lang.String>();

		processVertexList.add (
			vertexName
		);

		while (!processVertexList.isEmpty())
		{
			int lastIndex = processVertexList.size() - 1;

			java.lang.String currentVertexName = processVertexList.get (
				lastIndex
			);

			if (!orderedSearch.addVertex (
				currentVertexName
			))
			{
				return false;
			}

			org.drip.graph.core.Vertex currentVertex = _vertexMap.get (
				currentVertexName
			);

			java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> egressMap =
				currentVertex.adjacencyMap();

			if (null != egressMap && 0 != egressMap.size())
			{
				for (java.util.Map.Entry<java.lang.Double, org.drip.graph.core.BidirectionalEdge>
					egressMapEntry : egressMap.entrySet())
				{
					java.lang.String secondVertexName = egressMapEntry.getValue().secondVertexName();

					if (!orderedSearch.vertexPresent (
						secondVertexName
					))
					{
						processVertexList.add (
							secondVertexName
						);
					}
					else
					{
						orderedSearch.setContainsCycle();
					}
				}
			}

			processVertexList.remove (
				lastIndex
			);
		}

		return true;
	}

	/**
	 * Generate the Vertex Set using a Breadth-First Search
	 * 
	 * @param vertexName Vertex Name
	 * @param orderedSearch The Ordered Search Holder
	 * 
	 * @return The Vertex Set using a Breadth-First Search
	 */

	public boolean bfs (
		final java.lang.String vertexName,
		final org.drip.graph.core.OrderedSearch orderedSearch)
	{
		if (null == orderedSearch ||
			!_vertexMap.containsKey (
				vertexName
			)
		)
		{
			return false;
		}

		java.util.List<java.lang.String> processVertexList = new java.util.ArrayList<java.lang.String>();

		processVertexList.add (
			vertexName
		);

		while (!processVertexList.isEmpty())
		{
			java.lang.String currentVertexName = processVertexList.get (
				0
			);

			if (!orderedSearch.addVertex (
				currentVertexName
			))
			{
				return false;
			}

			org.drip.graph.core.Vertex currentVertex = _vertexMap.get (
				currentVertexName
			);

			java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> adjacencyMap =
				currentVertex.adjacencyMap();

			if (null != adjacencyMap && 0 != adjacencyMap.size())
			{
				for (java.util.Map.Entry<java.lang.Double, org.drip.graph.core.BidirectionalEdge>
					adjacencyMapEntry : adjacencyMap.entrySet())
				{
					java.lang.String secondVertexName = adjacencyMapEntry.getValue().secondVertexName();

					if (!orderedSearch.vertexPresent (
						secondVertexName
					))
					{
						processVertexList.add (
							secondVertexName
						);
					}
					else
					{
						orderedSearch.setContainsCycle();
					}
				}
			}

			processVertexList.remove (
				0
			);
		}

		return true;
	}

	/**
	 * Indicate if the Edge forms a Cycle with the Network
	 * 
	 * @param edge The Edge
	 * 
	 * @return TRUE - The Edge forms a Cycle with the Network
	 */

	public boolean isEdgeACycle (
		final org.drip.graph.core.BidirectionalEdge edge)
	{
		return null == edge ? false : _vertexMap.containsKey (
			edge.firstVertexName()
		) && _vertexMap.containsKey (
			edge.secondVertexName()
		);
	}

	/**
	 * Indicate if the Specified Edge matches with any Edges in the Network
	 * 
	 * @param edgeOther The "Other" Edge
	 * 
	 * @return TRUE - The Specified Edge matches with any Edges in the Network
	 */

	public boolean containsEdge (
		final org.drip.graph.core.BidirectionalEdge edgeOther)
	{
		if (null == edgeOther)
		{
			return false;
		}

		for (org.drip.graph.core.BidirectionalEdge edge : _edgeMap.values())
		{
			if (edge.compareWith (
				edgeOther
			))
			{
				return true;
			}
		}

		return false;
	}
}
