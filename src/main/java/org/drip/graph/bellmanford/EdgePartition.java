
package org.drip.graph.bellmanford;

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
 * <i>EdgePartition</i> contains the sub-graphs of the Partitioned Vertexes and their Edges from a Master
 * 	Graph. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bang-Jensen, J., and G. Gutin (2008): <i>Digraphs: Theory, Algorithms, and Applications
 *  			2<sup>nd</sup> Edition</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms</i>
 *  			3<sup>rd</sup> Edition <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Kleinberg, J., and E. Tardos (2022): <i>Algorithm Design 2<sup>nd</sup> Edition</i> <b>Pearson</b>
 *  	</li>
 *  	<li>
 *  		Sedgewick, R. and K. Wayne (2011): <i>Algorithms 4<sup>th</sup> Edition</i> <b>Addison Wesley</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Bellman-Ford Algorithm
 *  			https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/README.md">Bellman Ford Shortest Path Family</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EdgePartition
{
	private java.util.List<java.lang.String> _vertexNameList = null;
	private org.drip.graph.core.DirectedGraph _forwardDirectedGraph = null;
	private org.drip.graph.core.DirectedGraph _backwardDirectedGraph = null;
	private java.util.Map<java.lang.String, java.lang.Integer> _vertexIndexMap = null;

	private static final java.util.List<java.lang.String> ShuffleVertexNameCollection (
		final java.util.Collection<java.lang.String> vertexNameSet)
	{
		org.drip.graph.heap.PriorityQueue<java.lang.Double, java.lang.String> vertexNamePriorityQueue =
			new org.drip.graph.heap.BinomialTreePriorityQueue<java.lang.Double, java.lang.String> (
				true
			);

		for (java.lang.String vertexName : vertexNameSet)
		{
			vertexNamePriorityQueue.insert (
				java.lang.Math.random(),
				vertexName
			);
		}

		return vertexNamePriorityQueue.sortedItemList();
	}

	/**
	 * Generate the EdgePartition from a Graph
	 * 
	 * @param graph The Graph
	 * @param randomizeVertexes Randomize the Graph Vertexes
	 * 
	 * @return EdgePartition from a Graph
	 */

	public static final EdgePartition FromGraph (
		final org.drip.graph.core.DirectedGraph graph,
		final boolean randomizeVertexes)
	{
		if (null == graph)
		{
			return null;
		}

		int vertexIndex = 0;

		java.util.List<java.lang.String> vertexNameList = new java.util.ArrayList<java.lang.String>();

		java.util.Map<java.lang.String, java.lang.Integer> vertexIndexMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();

		java.util.Collection<java.lang.String> vertexNameCollection = graph.vertexMap().keySet();

		if (randomizeVertexes)
		{
			vertexNameCollection = ShuffleVertexNameCollection (
				vertexNameCollection
			);
		}

		for (java.lang.String vertexName : vertexNameCollection)
		{
			vertexNameList.add (
				vertexName
			);

			vertexIndexMap.put (
				vertexName,
				vertexIndex++
			);
		}

		org.drip.graph.core.DirectedGraph forwardDirectedGraph = new org.drip.graph.core.DirectedGraph();

		org.drip.graph.core.DirectedGraph backwardDirectedGraph = new org.drip.graph.core.DirectedGraph();

		for (org.drip.graph.core.Edge edge : graph.edgeMap().values())
		{
			if (vertexIndexMap.get (
					edge.sourceVertexName()
				) < vertexIndexMap.get (
					edge.destinationVertexName()
				)
			)
			{
				if (!forwardDirectedGraph.addEdge (
					edge
				))
				{
					return null;
				}
			}
			else
			{
				if (!backwardDirectedGraph.addEdge (
					edge
				))
				{
					return null;
				}
			}
		}

		try
		{
			return new EdgePartition (
				vertexIndexMap,
				vertexNameList,
				forwardDirectedGraph,
				backwardDirectedGraph
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EdgePartition Constructor
	 * 
	 * @param vertexIndexMap The Vertex Index Map
	 * @param vertexNameList The Vertex Name List
	 * @param forwardDirectedGraph The Forward Directed Graph
	 * @param backwardDirectedGraph The Backward Directed Graph
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EdgePartition (
		final java.util.Map<java.lang.String, java.lang.Integer> vertexIndexMap,
		final java.util.List<java.lang.String> vertexNameList,
		final org.drip.graph.core.DirectedGraph forwardDirectedGraph,
		final org.drip.graph.core.DirectedGraph backwardDirectedGraph)
		throws java.lang.Exception
	{
		if (null == (_vertexIndexMap = vertexIndexMap) ||
			null == (_vertexNameList = vertexNameList) ||
			null == (_forwardDirectedGraph = forwardDirectedGraph) ||
			null == (_backwardDirectedGraph = backwardDirectedGraph)
		)
		{
			throw new java.lang.Exception (
				"EdgePartition Constructor => Invalid Inputs"
			);
		}

		int vertexCount = _vertexIndexMap.size();

		if (0 == vertexCount ||
			vertexCount != _vertexNameList.size()
		)
		{
			throw new java.lang.Exception (
				"EdgePartition Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Vertex Index Map
	 * 
	 * @return The Vertex Index Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> vertexIndexMap()
	{
		return _vertexIndexMap;
	}

	/**
	 * Retrieve the Vertex Name List
	 * 
	 * @return The Vertex Name List
	 */

	public java.util.List<java.lang.String> vertexNameList()
	{
		return _vertexNameList;
	}

	/**
	 * Retrieve the Forward Directed Graph
	 * 
	 * @return The Forward Directed Graph
	 */

	public org.drip.graph.core.DirectedGraph forwardDirectedGraph()
	{
		return _forwardDirectedGraph;
	}

	/**
	 * Retrieve the Backward Directed Graph
	 * 
	 * @return The Backward Directed Graph
	 */

	public org.drip.graph.core.DirectedGraph backwardDirectedGraph()
	{
		return _backwardDirectedGraph;
	}
}
