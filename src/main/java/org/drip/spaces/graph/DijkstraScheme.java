
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
 * <i>DijkstraScheme</i> implements the Dijkstra Algorithm for finding the Shortest Path between a Pair of
 * Vertexes in a Graph. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph">Graph</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DijkstraScheme
{
	private org.drip.spaces.graph.Topography _topography = null;

	private void visitVertex (
		final org.drip.spaces.graph.ShortestPathVertex currentVertexPeriphery,
		final org.drip.spaces.graph.ShortestPathFirstWengert spfWengert)
	{
		org.drip.spaces.graph.ShortestPathTree vertexPeripheryMap = spfWengert.vertexPeripheryMap();

		java.util.Map<java.lang.String, java.lang.Double> connectionMap = _topography.connectionMap();

		double currentWeightFromSource = currentVertexPeriphery.weightFromSource();

		java.lang.String currentVertex = currentVertexPeriphery.current();

		java.util.Map<java.lang.String, java.lang.Double> egressMap = _topography.vertex
			(currentVertex).egressMap();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> egressEntry : egressMap.entrySet())
		{
			java.lang.String egressVertex = egressEntry.getKey();

			double weightFromSourceThroughCurrent = currentWeightFromSource + connectionMap.get
				(currentVertex + "_" + egressVertex);

			org.drip.spaces.graph.ShortestPathVertex egressVertexPeriphery =
				vertexPeripheryMap.shortestPathVertex (egressVertex);

			if (egressVertexPeriphery.weightFromSource() > weightFromSourceThroughCurrent)
			{
				egressVertexPeriphery.setWeightFromSource (weightFromSourceThroughCurrent);

				egressVertexPeriphery.setPreceeding (currentVertex);
			}
		}
	}

	/**
	 * DijkstraScheme Constructor
	 * 
	 * @param topography The Topography Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DijkstraScheme (
		final org.drip.spaces.graph.Topography topography)
		throws java.lang.Exception
	{
		if (null == (_topography = topography))
		{
			throw new java.lang.Exception ("DijkstraScheme Constructor => Invalid Inputs");
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
	 * Initialize the Dijsktra Scheme
	 * 
	 * @param source The Source Vertex
	 * 
	 * @return The Initial Dijkstra Wengert
	 */

	public org.drip.spaces.graph.ShortestPathFirstWengert setup (
		final java.lang.String source)
	{
		return org.drip.spaces.graph.ShortestPathFirstWengert.Dijkstra (
			_topography,
			source
		);
	}

	/**
	 * Run the Dijsktra SPF Algorithm
	 * 
	 * @param source The Source Vertex
	 * 
	 * @return The Dijkstra Wengert
	 */

	public org.drip.spaces.graph.ShortestPathFirstWengert spf (
		final java.lang.String source)
	{
		org.drip.spaces.graph.ShortestPathFirstWengert spfWengert = setup (source);

		if (null == spfWengert)
		{
			return null;
		}

		org.drip.spaces.graph.ShortestPathTree vertexPeripheryMap = spfWengert.vertexPeripheryMap();

		org.drip.spaces.graph.ShortestPathVertex vertexPeriphery =
			vertexPeripheryMap.greedyShortestPathVertex();

		while (null != vertexPeriphery)
		{
			visitVertex (
				vertexPeriphery,
				spfWengert
			);

			vertexPeriphery = vertexPeripheryMap.greedyShortestPathVertex();
		}

		return spfWengert;
	}
}
