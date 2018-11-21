
package org.drip.spaces.graph;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>BellmanFordScheme</i> implements the Bellman Ford Algorithm for finding the Shortest Path between a
 * Pair of Vertexes in a Graph. The References are:
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph">Graph</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BellmanFordScheme
{
	private org.drip.spaces.graph.Topography _topography = null;

	private void visitEdge (
		final org.drip.spaces.graph.Edge edge,
		final org.drip.spaces.graph.ShortestPathFirstWengert spfWengert)
	{
		java.lang.String leftVertex = edge.source();

		org.drip.spaces.graph.ShortestPathTree vertexPeripheryMap = spfWengert.vertexPeripheryMap();

		org.drip.spaces.graph.ShortestPathVertex vertexPeripheryLeft = vertexPeripheryMap.shortestPathVertex
			(leftVertex);

		org.drip.spaces.graph.ShortestPathVertex vertexPeripheryRight = vertexPeripheryMap.shortestPathVertex
			(edge.destination());

		double sourceToRightThroughLeft = vertexPeripheryLeft.weightFromSource() + edge.weight();

		if (sourceToRightThroughLeft < vertexPeripheryRight.weightFromSource())
		{
			vertexPeripheryRight.setWeightFromSource (sourceToRightThroughLeft);

			vertexPeripheryRight.setPreceeding (leftVertex);
		}
	}

	/**
	 * BellmanFordScheme Constructor
	 * 
	 * @param topography The Topography Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BellmanFordScheme (
		final org.drip.spaces.graph.Topography topography)
		throws java.lang.Exception
	{
		if (null == (_topography = topography))
		{
			throw new java.lang.Exception ("BellmanFordScheme Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Topography Map
	 * 
	 * @return The Topography Map
	 */

	public org.drip.spaces.graph.Topography topography()
	{
		return _topography;
	}

	/**
	 * Initialize the Bellman Ford Scheme
	 * 
	 * @param source The Source Vertex
	 * 
	 * @return The Initial Bellman Ford Wengert
	 */

	public org.drip.spaces.graph.ShortestPathFirstWengert setup (
		final java.lang.String source)
	{
		return org.drip.spaces.graph.ShortestPathFirstWengert.BellmanFord (
			_topography,
			source
		);
	}

	/**
	 * Run the Bellman Ford SPF Algorithm
	 * 
	 * @param source The Source Vertex
	 * 
	 * @return The Bellman Ford Wengert
	 */

	public org.drip.spaces.graph.ShortestPathFirstWengert spf (
		final java.lang.String source)
	{
		org.drip.spaces.graph.ShortestPathFirstWengert spfWengert = setup (source);

		if (null == spfWengert)
		{
			return null;
		}

		int vertexCount = _topography.vertexNameSet().size();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			java.util.Map<java.lang.String, org.drip.spaces.graph.Edge> edgeMap =
				_topography.topographyEdgeMap().edgeMap();

			for (java.util.Map.Entry<java.lang.String, org.drip.spaces.graph.Edge> edgeEntry :
				edgeMap.entrySet())
			{
				visitEdge (
					edgeEntry.getValue(),
					spfWengert
				);
			}
		}

		return spfWengert;
	}
}
