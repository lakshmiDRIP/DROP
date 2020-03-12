
package org.drip.graph.spanningtree;

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
 * <i>Prim</i> implements the Prim's Algorithm for generating a Minimum Spanning Tree. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Wikipedia (2019a): Kruskal's Algorithm https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019b): Prim's Algorithm https://en.wikipedia.org/wiki/Prim%27s_algorithm
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020a): Breadth-First Search https://en.wikipedia.org/wiki/Breadth-first_search
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020b): Depth-First Search https://en.wikipedia.org/wiki/Depth-first_search
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/spanningtree/README.md">Algorithms for Spanning Tree Analysis</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Prim
{
	private org.drip.graph.core.Graph _graph = null;

	private boolean updateEdgeTreeMap (
		final java.util.TreeMap<java.lang.Double, org.drip.graph.core.BidirectionalEdge> edgeTreeMap,
		final java.util.Set<java.lang.String> visitedVertexSet,
		final java.lang.String currentVertexName)
	{
		org.drip.graph.core.Vertex currentVertex = _graph.vertexMap().get (
			currentVertexName
		);

		java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> adjacencyMap =
			currentVertex.adjacencyMap();

		if (null == adjacencyMap || 0 == adjacencyMap.size())
		{
			return false;
		}

		for (java.util.Map.Entry<java.lang.Double, org.drip.graph.core.BidirectionalEdge> egressEntry :
			adjacencyMap.entrySet())
		{
			org.drip.graph.core.BidirectionalEdge edge = egressEntry.getValue();

			edgeTreeMap.put (
				edge.distance(),
				edge
			);
		}

		visitedVertexSet.add (
			currentVertexName
		);

		return true;
	}

	/**
	 * Prim Constructor
	 * 
	 * @param graph The Graph
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Prim (
		final org.drip.graph.core.Graph graph)
		throws java.lang.Exception
	{
		if (null == (_graph = graph))
		{
			throw new java.lang.Exception (
				"Prim Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Graph
	 * 
	 * @return The Graph
	 */

	public org.drip.graph.core.Graph graph()
	{
		return _graph;
	}

	/**
	 * Generate the Minimum Spanning Tree from the Initial Vertex
	 * 
	 * @param initialVertexName The Initial Vertex Name
	 * 
	 * @return The Minimum Spanning Tree
	 */

	public org.drip.graph.core.Tree minimumSpanningTree (
		final java.lang.String initialVertexName)
	{
		if (null == initialVertexName || initialVertexName.isEmpty())
		{
			return null;
		}

		org.drip.graph.core.Tree minimumSpanningTree =
			new org.drip.graph.core.Tree();

		java.util.Set<java.lang.String> visitedVertexSet = new java.util.HashSet<java.lang.String>();

		java.util.TreeMap<java.lang.Double, org.drip.graph.core.BidirectionalEdge> edgeTreeMap =
			new java.util.TreeMap<java.lang.Double, org.drip.graph.core.BidirectionalEdge>();

		if (!updateEdgeTreeMap (
				edgeTreeMap,
				visitedVertexSet,
				initialVertexName
			)
		)
		{
			return minimumSpanningTree;
		}

		while (!edgeTreeMap.isEmpty())
		{
			java.util.Map.Entry<java.lang.Double, org.drip.graph.core.BidirectionalEdge> firstEntry =
				edgeTreeMap.firstEntry();

			edgeTreeMap.remove (
				firstEntry.getKey()
			);

			org.drip.graph.core.BidirectionalEdge processedEdge = firstEntry.getValue();

			java.lang.String currentVertexName = processedEdge.secondVertexName();

			if (visitedVertexSet.contains (
				currentVertexName
			))
			{
				continue;
			}

			if (!minimumSpanningTree.addBidirectionalEdge (
				processedEdge
			))
			{
				return null;
			}

			updateEdgeTreeMap (
				edgeTreeMap,
				visitedVertexSet,
				currentVertexName
			);
		}

		return minimumSpanningTree;
	}
}
