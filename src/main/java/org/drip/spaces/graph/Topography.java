
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
 * <i>Topography</i> holds Vertexes and the Edges between them. The References are:
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

public class Topography
{
	private org.drip.spaces.graph.TopographyEdgeMap _topographyEdgeMap =
		new org.drip.spaces.graph.TopographyEdgeMap();

	private java.util.Map<java.lang.String, org.drip.spaces.graph.Vertex> _vertexMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spaces.graph.Vertex>();

	/**
	 * Empty Topography Constructor
	 */

	public Topography()
	{
	}

	/**
	 * Retrieve the Map of Vertex
	 * 
	 * @return The Map of Vertex
	 */

	public java.util.Map<java.lang.String, org.drip.spaces.graph.Vertex> vertexMap()
	{
		return _vertexMap;
	}

	/**
	 * Retrieve the Topography Edge Map
	 * 
	 * @return The Topography Edge Map
	 */

	public org.drip.spaces.graph.TopographyEdgeMap topographyEdgeMap()
	{
		return _topographyEdgeMap;
	}

	/**
	 * Retrieve The Vertex Name Set
	 * 
	 * @return The Vertex Name Set
	 */

	public java.util.Set<java.lang.String> vertexNameSet()
	{
		return _vertexMap.keySet();
	}

	/**
	 * Add The Vertex
	 * 
	 * @param vertexName Name of the Vertex
	 * 
	 * @return TRUE - The Vertex successfully added
	 */

	public boolean addVertex (
		final java.lang.String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty())
		{
			return false;
		}

		try
		{
			_vertexMap.put (
				vertexName,
				new org.drip.spaces.graph.Vertex (
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
	 * Verify if the Vertex is available
	 * 
	 * @param vertexName Name of the Vertex
	 * 
	 * @return TRUE - The Vertex is available
	 */

	public boolean vertexExists (
		final java.lang.String vertexName)
	{
		return null != vertexName && _vertexMap.containsKey (
			vertexName
		);
	}

	/**
	 * Retrieve the Named Vertex
	 * 
	 * @param vertexName Name of the Vertex
	 * 
	 * @return The Vertex
	 */

	public org.drip.spaces.graph.Vertex vertex (
		final java.lang.String vertexName)
	{
		return vertexExists (
			vertexName
		) ? _vertexMap.get (
			vertexName
		) : null;
	}

	/**
	 * Add an Edge
	 * 
	 * @param edge The Edge to be added
	 * 
	 * @return TRUE - The Edge successfully added
	 */

	public boolean addEdge (
		final org.drip.spaces.graph.Edge edge)
	{
		if (null == edge || !_topographyEdgeMap.addEdge (
			edge
		))
		{
			return false;
		}

		double weight = edge.weight();

		java.lang.String sourceVertexName = edge.sourceVertexName();

		java.lang.String destinationVertexName = edge.destinationVertexName();

		org.drip.spaces.graph.Vertex sourceVertex = vertex (
			sourceVertexName
		);

		org.drip.spaces.graph.Vertex destinationVertex = vertex (
			destinationVertexName
		);

		return null != sourceVertex && null != destinationVertex && sourceVertex.addEgress (
			destinationVertexName,
			weight
		) && destinationVertex.addEgress (
			sourceVertexName,
			weight
		);
	}

	/**
	 * Indicate if the Pair of Vertexes are Adjacent
	 * 
	 * @param sourceVertexName The Source Vertex Name
	 * @param destinationVertexName The Destination Vertex Name
	 * 
	 * @return TRUE - The Pair of Vertexes are Adjacent
	 */

	public boolean adjacent (
		final java.lang.String sourceVertexName,
		final java.lang.String destinationVertexName)
	{
		org.drip.spaces.graph.Vertex sourceVertex = vertex (
			sourceVertexName
		);

		org.drip.spaces.graph.Vertex destinationVertex = vertex (
			destinationVertexName
		);

		if (null == sourceVertex || null == destinationVertex)
		{
			return false;
		}

		return sourceVertex.egressMap().containsKey (
			destinationVertexName
		);
	}

	/**
	 * Compute the Weight between Source and Destination if Adjacent
	 * 
	 * @param sourceVertexName The Source Vertex Name
	 * @param destinationVertexName The Destination Vertex Name
	 * 
	 * @return The Weight between Source and Destination if Adjacent
	 * 
	 * @throws java.lang.Exception Thrown if the Source and the Destination are not Adjacent
	 */

	public double adjacentWeight (
		final java.lang.String sourceVertexName,
		final java.lang.String destinationVertexName)
		throws java.lang.Exception
	{
		if (!adjacent (
			sourceVertexName,
			destinationVertexName
		))
		{
			throw new java.lang.Exception (
				"Topography::adjacentWeight => Invalid Adjacency Check"
			);
		}

		return vertex (
			sourceVertexName
		).egressMap().get (
			destinationVertexName
		);
	}

	/**
	 * Generate the Map between Adjacent Pairs of Source and Destination
	 * 
	 * @return The Adjacency Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> adjacencyMap()
	{
		java.util.Set<java.lang.String> vertexNameSet = _vertexMap.keySet();

		java.util.Map<java.lang.String, java.lang.Double> adjacencyMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (String sourceVertexName : vertexNameSet)
		{
			for (String destinationVertexName : vertexNameSet)
			{
				try
				{
					if (adjacent (
						sourceVertexName,
						destinationVertexName
					))
					{
						double adjacentWeight = adjacent (
							sourceVertexName,
							destinationVertexName
						) ? adjacentWeight (
							sourceVertexName,
							destinationVertexName
						) : 0.;

							adjacencyMap.put (
							sourceVertexName + "_" + destinationVertexName,
							adjacentWeight
						);

							adjacencyMap.put (
							destinationVertexName + "_" + sourceVertexName,
							adjacentWeight
						);
					}
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return adjacencyMap;
	}
}
