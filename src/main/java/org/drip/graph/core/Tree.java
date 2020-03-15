
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
 * <i>Tree</i> holds the Vertexes and the Edges associated with a Tree. The References are:
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

public class Tree
	extends org.drip.graph.core.Network
{

	/**
	 * Tree Constructor
	 */

	public Tree()
	{
		super();
	}

	/**
	 * Add a Stand-alone Vertex to the Network
	 *  
	 * @param vertexName The Stand-alone Vertex Name
	 * 
	 * @return TRUE - The Stand-alone Vertex successfully added to the Network
	 */

	public boolean addVertex (
		final java.lang.String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty() ||
			!_vertexMap.isEmpty() ||
			!_edgeMap.isEmpty()
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
	 * Absorb the Specified Tree and Edge
	 * 
	 * @param tree The Tree
	 * @param edge The Edge
	 * 
	 * @return TRUE - The Tree and Edge successfully absorbed
	 */

	public boolean absorbTreeAndEdge (
		final org.drip.graph.core.Tree tree,
		final org.drip.graph.core.BidirectionalEdge edge)
	{
		if (null == tree || !addBidirectionalEdge (
			edge
		))
		{
			return false;
		}

		for (org.drip.graph.core.BidirectionalEdge treeEdge : tree.edgeMap().values())
		{
			if (!addBidirectionalEdge (
				treeEdge
			))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Construct and Retrieve the Tree Adjacency Map from the Graph
	 * 
	 * @param graph The Graph
	 * 
	 * @return The Tree Adjacency Map
	 */

	public java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> adjacencyMap (
		final org.drip.graph.core.Graph graph)
	{
		if (null == graph)
		{
			return null;
		}

		java.util.Map<java.lang.String, org.drip.graph.core.Vertex> graphVertexMap = graph.vertexMap();

		java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> adjacencyMap =
			new java.util.TreeMap<java.lang.Double, org.drip.graph.core.BidirectionalEdge>();

		for (java.lang.String vertexName : _vertexMap.keySet())
		{
			if (!graphVertexMap.containsKey (
				vertexName
			))
			{
				return null;
			}

			org.drip.graph.core.Vertex vertex = graphVertexMap.get (
				vertexName
			);

			java.util.Collection<org.drip.graph.core.BidirectionalEdge> edgeCollection =
				vertex.adjacencyMap().values();

			if (null == edgeCollection || 0 == edgeCollection.size())
			{
				continue;
			}

			for (org.drip.graph.core.BidirectionalEdge edge : edgeCollection)
			{
				java.lang.String sourceVertexName = edge.firstVertexName();

				java.lang.String destinationVertexName = edge.secondVertexName();

				if (_vertexMap.containsKey (
					sourceVertexName
				) && _vertexMap.containsKey (
					destinationVertexName
				))
				{
					continue;
				}

				adjacencyMap.put (
					edge.distance(),
					edge
				);
			}
		}

		return adjacencyMap;
	}
}
