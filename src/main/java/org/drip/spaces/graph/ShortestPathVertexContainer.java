
package org.drip.spaces.graph;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>ShortestPathVertexContainer</i> holds the Map of Path Vertexes by Weight and Vertex Name. The
 * 	References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Wikipedia (2018a): Graph (Abstract Data Type)
 *  			https://en.wikipedia.org/wiki/Graph_(abstract_data_type)
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018b): Graph Theory https://en.wikipedia.org/wiki/Graph_theory
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018c): Graph (Discrete Mathematics)
 *  			https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018d): Dijkstra's Algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018e): Bellman-Ford Algorithm
 *  			https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/README.md">Graph Representation and Traversal Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShortestPathVertexContainer
{
	private java.util.Map<java.lang.String, org.drip.spaces.graph.ShortestPathVertex> _shortestPathVertexMap
		= new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spaces.graph.ShortestPathVertex>();

	private java.util.Map<java.lang.Double, java.util.List<org.drip.spaces.graph.ShortestPathVertex>>
		_orderedShortestPathVertexList = new java.util.TreeMap<java.lang.Double,
			java.util.List<org.drip.spaces.graph.ShortestPathVertex>>();

	/**
	 * Generate a Shortest Path Vertex Container from the Topography and the Source using the Dijkstra Scheme
	 * 
	 * @param topography The Topography Map
	 * @param sourceVertexName The Source Vertex Name
	 * 
	 * @return The Dijkstra Shortest Path Vertex Container Instance
	 */

	public static final org.drip.spaces.graph.ShortestPathVertexContainer Dijkstra (
		final org.drip.spaces.graph.Topography topography,
		final java.lang.String sourceVertexName)
	{
		if (null == topography)
		{
			return null;
		}

		java.util.Set<java.lang.String> vertexNameSet = topography.vertexNameSet();

		if (!vertexNameSet.contains (
			sourceVertexName
		))
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> adjacencyMap = topography.adjacencyMap();

		java.util.Map<java.lang.String, java.lang.Double> egressMap = topography.vertex (
			sourceVertexName
		).egressMap();

		org.drip.spaces.graph.ShortestPathVertexContainer shortestPathVertexContainer =
			new org.drip.spaces.graph.ShortestPathVertexContainer();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> egressEntry : egressMap.entrySet())
		{
			java.lang.String egressVertexName = egressEntry.getKey();

			org.drip.spaces.graph.ShortestPathVertex shortestPathVertex = null;
			java.lang.String sourceToEgressVertexKey = sourceVertexName + "_" + egressVertexName;

			try
			{
				shortestPathVertex = new org.drip.spaces.graph.ShortestPathVertex (
					egressVertexName
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			shortestPathVertex.setPreceeding (
				sourceVertexName
			);

			shortestPathVertex.setWeightFromSource (
				adjacencyMap.get (
					sourceToEgressVertexKey
				)
			);

			shortestPathVertexContainer.addShortestPathVertex (
				shortestPathVertex
			);
		}

		for (java.lang.String vertexName : vertexNameSet)
		{
			if (!shortestPathVertexContainer.containsVertex (
				vertexName
			))
			{
				shortestPathVertexContainer.addUnitializedShortestPathVertex (
					vertexName
				);
			}
		}

		return shortestPathVertexContainer;
	}

	/**
	 * Generate a Shortest Path Vertex Container from the Topography and the Source using the Bellman-Ford
	 * 	Scheme
	 * 
	 * @param topography The Topography Map
	 * @param sourceVertexName The Source Vertex Name
	 * 
	 * @return The Bellman-Ford Shortest Path Vertex Container Instance
	 */

	public static final ShortestPathVertexContainer BellmanFord (
		final org.drip.spaces.graph.Topography topography,
		final java.lang.String sourceVertexName)
	{
		if (null == topography)
		{
			return null;
		}

		java.util.Set<java.lang.String> vertexNameSet = topography.vertexNameSet();

		if (!vertexNameSet.contains (
			sourceVertexName
		))
		{
			return null;
		}

		org.drip.spaces.graph.ShortestPathVertexContainer shortestPathVertexContainer =
			new org.drip.spaces.graph.ShortestPathVertexContainer();

		org.drip.spaces.graph.ShortestPathVertex shortestPathVertex = null;

		try
		{
			shortestPathVertex = new org.drip.spaces.graph.ShortestPathVertex (
				sourceVertexName
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		shortestPathVertex.setPreceeding (
			sourceVertexName
		);

		shortestPathVertex.setWeightFromSource (
			0.
		);

		shortestPathVertexContainer.addShortestPathVertex (
			shortestPathVertex
		);

		for (java.lang.String vertexName : vertexNameSet)
		{
			if (!vertexName.equalsIgnoreCase (
				sourceVertexName
			))
			{
				shortestPathVertexContainer.addUnitializedShortestPathVertex (
					vertexName
				);
			}
		}

		return shortestPathVertexContainer;
	}

	private org.drip.spaces.graph.ShortestPathVertex getUnvisited (
		final java.util.List<org.drip.spaces.graph.ShortestPathVertex> shortestPathVertexList)
	{
		for (org.drip.spaces.graph.ShortestPathVertex shortestPathVertex : shortestPathVertexList)
		{
			if (!shortestPathVertex.visited())
			{
				return shortestPathVertex;
			}
		}

		return null;
	}

	/**
	 * Empty ShortestPathVertexContainer Constructor
	 */

	public ShortestPathVertexContainer()
	{
	}

	/**
	 * Retrieve the Shortest Path Vertex Map
	 * 
	 * @return The Shortest Path Vertex Map
	 */

	public java.util.Map<java.lang.String, org.drip.spaces.graph.ShortestPathVertex> shortestPathVertexMap()
	{
		return _shortestPathVertexMap;
	}

	/**
	 * Retrieve the Ordered Shortest Path Vertex List Map
	 * 
	 * @return The Ordered Shortest Path Vertex List Map
	 */

	public java.util.Map<java.lang.Double, java.util.List<org.drip.spaces.graph.ShortestPathVertex>>
		orderedShortestPathVertexList()
	{
		return _orderedShortestPathVertexList;
	}

	/**
	 * Add the Shortest Path Vertex
	 * 
	 * @param shortestPathVertex The Shortest Path Vertex
	 * 
	 * @return TRUE - The Shortest Path Vertex successfully added
	 */

	public boolean addShortestPathVertex (
		final org.drip.spaces.graph.ShortestPathVertex shortestPathVertex)
	{
		if (null == shortestPathVertex)
		{
			return false;
		}

		_shortestPathVertexMap.put (
			shortestPathVertex.currentVertexName(),
			shortestPathVertex
		);

		double weightFromSource = shortestPathVertex.weightFromSource();

		if (_orderedShortestPathVertexList.containsKey (
			weightFromSource
		))
		{
			_orderedShortestPathVertexList.get (
				weightFromSource
			).add (
				shortestPathVertex
			);
		}
		else
		{
			java.util.List<org.drip.spaces.graph.ShortestPathVertex> shortestPathVertexList =
				new java.util.ArrayList<org.drip.spaces.graph.ShortestPathVertex>();

			shortestPathVertexList.add (
				shortestPathVertex
			);

			_orderedShortestPathVertexList.put (
				weightFromSource,
				shortestPathVertexList
			);
		}

		return true;
	}

	/**
	 * Add an Uninitialized Shortest Path Vertex
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - An Uninitialized Shortest Path Vertex successfully added
	 */

	public boolean addUnitializedShortestPathVertex (
		final java.lang.String vertexName)
	{
		try
		{
			return addShortestPathVertex (
				new org.drip.spaces.graph.ShortestPathVertex (
					vertexName
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Indicate of the Vertex is available in the Periphery Map
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Vertex is available in the Periphery Map
	 */

	public boolean containsVertex (
		final java.lang.String vertexName)
	{
		return null != vertexName && !vertexName.isEmpty() && _shortestPathVertexMap.containsKey (
			vertexName
		);
	}

	/**
	 * Retrieve the Shortest Path Vertex by Name
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return The Shortest Path Vertex by Name
	 */

	public org.drip.spaces.graph.ShortestPathVertex shortestPathVertex (
		final java.lang.String vertexName)
	{
		return _shortestPathVertexMap.containsKey (
			vertexName
		) ? _shortestPathVertexMap.get (
			vertexName
		) : null;
	}

	/**
	 * Retrieve the Unvisited Shortest Path Vertex with the Least Weight
	 * 
	 * @return The Unvisited Shortest Path Vertex with the Least Weight
	 */

	public org.drip.spaces.graph.ShortestPathVertex unvisitedShortestPathVertex()
	{
		for (java.util.Map.Entry<java.lang.Double, java.util.List<org.drip.spaces.graph.ShortestPathVertex>>
			orderedShortestPathVertexListEntry : _orderedShortestPathVertexList.entrySet())
		{
			org.drip.spaces.graph.ShortestPathVertex unvisitedShortestPathVertex = getUnvisited (
				orderedShortestPathVertexListEntry.getValue()
			);

			if (null == unvisitedShortestPathVertex)
			{
				continue;
			}

			unvisitedShortestPathVertex.setVisited (
				true
			);

			return unvisitedShortestPathVertex;
		}

		return null;
	}
}
