
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
 * <i>BellmanFordGenerator</i> generates the Shortest Path for a Directed Graph using the Bellman-Ford
 * 	Algorithm. The References are:
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

public class BellmanFordGenerator
{
	private org.drip.graph.core.DirectedGraph _graph = null;

	private org.drip.graph.shortestpath.VertexAugmentor shortestPath (
		final java.lang.String sourceVertexName)
	{
		if (null == sourceVertexName || sourceVertexName.isEmpty())
		{
			return null;
		}

		org.drip.graph.shortestpath.VertexAugmentor vertexAugmentor = null;

		try
		{
			vertexAugmentor = new org.drip.graph.shortestpath.VertexAugmentor (
				sourceVertexName
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		java.util.Set<java.lang.String> vertexNameSet = _graph.vertexNameSet();

		if (!vertexAugmentor.initializeVertexNameSet (
			vertexNameSet
		))
		{
			return null;
		}

		int vertexCount = vertexNameSet.size();

		java.util.Map<java.lang.String, org.drip.graph.core.Edge> edgeMap = _graph.edgeMap();

		org.drip.graph.heap.PriorityQueue<java.lang.Double, java.lang.String> edgePriorityQueue =
			new org.drip.graph.heap.BinomialTreePriorityQueue<java.lang.Double, java.lang.String> (
				true
			);

		while (0 < vertexCount--)
		{
			if (!edgePriorityQueue.meld (
				_graph.edgePriorityQueue (
					true
				)
			))
			{
				return null;
			}

			while (!edgePriorityQueue.isEmpty())
			{
				if (!vertexAugmentor.updateAugmentedVertex (
					edgeMap.get (
						edgePriorityQueue.extractExtremum().item()
					)
				))
				{
					return null;
				}
			}
		}

		return vertexAugmentor;
	}

	/**
	 * BellmanFordGenerator Constructor
	 * 
	 * @param graph Graph underlying the Path Generator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BellmanFordGenerator (
		final org.drip.graph.core.DirectedGraph graph)
		throws java.lang.Exception
	{
		if (null == (_graph = graph))
		{
			throw new java.lang.Exception (
				"BellmanFordGenerator Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Graph underlying the Path Generator
	 * 
	 * @return Graph underlying the Path Generator
	 */

	public org.drip.graph.core.DirectedGraph graph()
	{
		return _graph;
	}

	/**
	 * Generate the Shortest Path from the Source to the Destination
	 * 
	 * @param sourceVertexName Source Vertex Name
	 * @param destinationVertexName Destination Vertex Name
	 * 
	 * @return Shortest Path from the Source to the Destination
	 */

	public org.drip.graph.core.Path singlePair (
		final java.lang.String sourceVertexName,
		final java.lang.String destinationVertexName)
	{
		org.drip.graph.shortestpath.VertexAugmentor vertexAugmentor = shortestPath (
			sourceVertexName
		);

		return null == vertexAugmentor ? null : vertexAugmentor.generatePath (
			destinationVertexName
		);
	}

	/**
	 * Generate the List of the Shortest Path from the Source to all Destinations
	 * 
	 * @param sourceVertexName Source Vertex Name
	 * 
	 * @return List of the Shortest Path from the Source to all Destinations
	 */

	public java.util.List<org.drip.graph.core.Path> singleSource (
		final java.lang.String sourceVertexName)
	{
		org.drip.graph.shortestpath.VertexAugmentor vertexAugmentor = shortestPath (
			sourceVertexName
		);

		if (null == vertexAugmentor)
		{
			return null;
		}

		java.util.List<org.drip.graph.core.Path> pathList =
			new java.util.ArrayList<org.drip.graph.core.Path>();

		for (java.lang.String destinationVertexName : _graph.vertexNameSet())
		{
			if (!destinationVertexName.equalsIgnoreCase (
				sourceVertexName
			))
			{
				org.drip.graph.core.Path path = vertexAugmentor.generatePath (
					destinationVertexName
				);

				if (null == path)
				{
					return null;
				}

				pathList.add (
					path
				);
			}
		}

		return pathList;
	}
}
