
package org.drip.graph.shortestpath;

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
 * <i>YenEdgePartitionGenerator</i> generates the Shortest Path for a Directed Graph using the Bellman-Ford
 * 	Algorithm with the Edge Partitioning Scheme applied to improve the Worst-Case Behavior. The References
 * 	are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/shortestpath/README.md">Shortest Path Generation Algorithm Family</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class YenEdgePartitionGenerator
	extends org.drip.graph.shortestpath.BellmanFordGenerator
{
	private java.util.List<java.lang.String> _vertexNameList = null;
	private org.drip.graph.core.DirectedGraph _forwardDirectedGraph = null;
	private org.drip.graph.core.DirectedGraph _backwardDirectedGraph = null;
	private java.util.Map<java.lang.String, java.lang.Integer> _vertexIndexMap = null;

	private static final boolean ProcessVertex (
		final org.drip.graph.core.Vertex vertex,
		final org.drip.graph.shortestpath.VertexAugmentor vertexAugmentor,
		final boolean shortestPath)
	{
		org.drip.graph.heap.PriorityQueue<java.lang.Double, org.drip.graph.core.Edge> edgePriorityQueue =
			new org.drip.graph.heap.BinomialTreePriorityQueue<java.lang.Double, org.drip.graph.core.Edge> (
				shortestPath
			);

		if (!edgePriorityQueue.meld (
			vertex.adjacencyPriorityQueue (
				shortestPath
			)
		))
		{
			return false;
		}

		while (!edgePriorityQueue.isEmpty())
		{
			if (!vertexAugmentor.updateAugmentedVertex (
				edgePriorityQueue.extractExtremum().item()
			))
			{
				return false;
			}
		}

		return true;
	}

	private boolean processSubgraph (
		final int vertexCount,
		final boolean forward,
		final org.drip.graph.core.DirectedGraph graph,
		final org.drip.graph.shortestpath.VertexAugmentor vertexAugmentor,
		final boolean shortestPath)
	{
		int vertexIndex = forward ? 0 : vertexCount - 1;
		int finalVertexIndex = forward ? vertexCount - 1 : 0;

		java.util.Map<java.lang.String, org.drip.graph.core.Vertex> forwardVertexMap = graph.vertexMap();

		while (vertexIndex != finalVertexIndex)
		{
			java.lang.String vertexName = _vertexNameList.get (
				vertexIndex
			);

			if (graph.containsVertex (
				vertexName
			))
			{
				if (!ProcessVertex (
					forwardVertexMap.get (
						vertexName
					),
					vertexAugmentor,
					shortestPath
				))
				{
					return false;
				}
			}

			if (forward)
			{
				++vertexIndex;
			}
			else
			{
				--vertexIndex;
			}
		}

		return true;
	}

	/**
	 * YenEdgePartitionGenerator Constructor
	 * 
	 * @param graph Graph underlying the Path Generator
	 * @param shortestPath TRUE - Shortest Path Sought
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public YenEdgePartitionGenerator (
		final org.drip.graph.core.DirectedGraph graph,
		final boolean shortestPath)
		throws java.lang.Exception
	{
		super (
			graph,
			shortestPath
		);

		int vertexIndex = 0;

		_vertexNameList = new java.util.ArrayList<java.lang.String>();

		_vertexIndexMap = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();

		for (java.lang.String vertexName : graph.vertexMap().keySet())
		{
			_vertexNameList.add (
				vertexName
			);

			_vertexIndexMap.put (
				vertexName,
				vertexIndex++
			);
		}

		_forwardDirectedGraph = new org.drip.graph.core.DirectedGraph();

		_backwardDirectedGraph = new org.drip.graph.core.DirectedGraph();

		for (org.drip.graph.core.Edge edge : graph.edgeMap().values())
		{
			if (_vertexIndexMap.get (
					edge.sourceVertexName()
				) < _vertexIndexMap.get (
					edge.destinationVertexName()
				)
			)
			{
				if (!_forwardDirectedGraph.addEdge (
					edge
				))
				{
					throw new java.lang.Exception (
						"YenEdgePartitionGenerator Constructor => Invalid Inputs"
					);
				}
			}
			else
			{
				if (!_backwardDirectedGraph.addEdge (
					edge
				))
				{
					throw new java.lang.Exception (
						"YenEdgePartitionGenerator Constructor => Invalid Inputs"
					);
				}
			}
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
	 * @return Forward Directed Graph
	 */

	public org.drip.graph.core.DirectedGraph forwardDirectedGraph()
	{
		return _forwardDirectedGraph;
	}

	/**
	 * Retrieve the Backward Directed Graph
	 * 
	 * @return Backward Directed Graph
	 */

	public org.drip.graph.core.DirectedGraph backwardDirectedGraph()
	{
		return _backwardDirectedGraph;
	}

	@Override protected org.drip.graph.shortestpath.VertexAugmentor augmentVertexes (
		final java.lang.String sourceVertexName)
	{
		if (null == sourceVertexName || sourceVertexName.isEmpty())
		{
			return null;
		}

		boolean shortestPath = shortestPath();

		int vertexCount = _vertexIndexMap.size();

		org.drip.graph.core.DirectedGraph graph = graph();

		int iterationCount = vertexCount;
		org.drip.graph.shortestpath.VertexAugmentor vertexAugmentor = null;
		org.drip.graph.shortestpath.VertexRelaxationControl vertexRelaxationControl = null;

		try
		{
			vertexAugmentor = new org.drip.graph.shortestpath.VertexAugmentor (
				sourceVertexName,
				shortestPath
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		if (!vertexAugmentor.initializeVertexNameSet (
			graph.vertexNameSet()
		))
		{
			return null;
		}

		while (0 < iterationCount--)
		{
			if (!processSubgraph (
					vertexCount,
					true,
					_forwardDirectedGraph,
					vertexAugmentor,
					shortestPath
				) || !processSubgraph (
					vertexCount,
					false,
					_backwardDirectedGraph,
					vertexAugmentor,
					shortestPath
				)
			)
			{
				return null;
			}

			if (null == vertexRelaxationControl)
			{
				try
				{
					vertexRelaxationControl = new org.drip.graph.shortestpath.VertexRelaxationControl (
						vertexAugmentor.augmentedVertexMap()
					);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
			else
			{
				if (!vertexRelaxationControl.relaxAndUpdateVertexes (
					vertexAugmentor.augmentedVertexMap()
				))
				{
					return null;
				}
			}
		}

		org.drip.graph.heap.PriorityQueue<java.lang.Double, java.lang.String> edgePriorityQueue =
			new org.drip.graph.heap.BinomialTreePriorityQueue<java.lang.Double, java.lang.String> (
				shortestPath
			);

		return edgePriorityQueue.meld (
			graph.edgePriorityQueue (
				shortestPath
			)
		) && verifyNegativeCycle (
			vertexRelaxationControl,
			edgePriorityQueue
		) ? vertexAugmentor : null;
	}
}
