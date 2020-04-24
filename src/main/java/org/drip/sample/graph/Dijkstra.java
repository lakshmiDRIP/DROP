
package org.drip.sample.graph;

import java.util.Set;

import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spaces.graph.DijkstraScheme;
import org.drip.spaces.graph.Edge;
import org.drip.spaces.graph.Topography;
import org.drip.spaces.graph.ShortestPathVertex;
import org.drip.spaces.graph.ShortestPathVertexContainer;

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
 * <i>Dijkstra</i> illustrates the Execution of the Dijkstra Algorithm. The References are:
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/graph/README.md">Graph Traversal and Navigation Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Dijkstra
{

	private static final Topography SetTopography()
		throws Exception
	{
		String[] vertexArray = new String[]
		{
			"Delhi     ",
			"Bombay    ",
			"Madras    ",
			"Calcutta  ",
			"Bangalore ",
			"Hyderabad ",
			"Cochin    ",
			"Pune      ",
			"Ahmedabad ",
			"Jaipur    "
		};

		Topography topography = new Topography();

		for (String vertexName : vertexArray)
		{
			topography.addVertex (vertexName);
		}

		topography.addEdge (
			new Edge (
				vertexArray[0], // Delhi
				vertexArray[1], // Bombay
				1388.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[0], // Delhi
				vertexArray[2], // Madras
				2191.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[1], // Bombay
				vertexArray[2], // Madras
				1279.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[0], // Delhi
				vertexArray[3], // Calcutta
				1341.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[1], // Bombay
				vertexArray[3], // Calcutta
				1968.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[2], // Madras
				vertexArray[3], // Calcutta
				1663.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[2], // Madras
				vertexArray[4], // Bangalore
				361.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[2], // Madras
				vertexArray[5], // Hyderabad
				784.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[2], // Madras
				vertexArray[6], // Cochin
				697.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[1], // Bombay
				vertexArray[7], // Pune
				192.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[1], // Bombay
				vertexArray[8], // Ahmedabad
				492.
			)
		);

		topography.addEdge (
			new Edge (
				vertexArray[0], // Delhi
				vertexArray[9], // Jaipur
				308.
			)
		);

		return topography;
	}

	private static final String PathVertexes (
		final String source,
		final String destination,
		final ShortestPathVertexContainer vertexPeripheryMap)
		throws Exception
	{
		String path = "";
		String vertex = destination;

		ShortestPathVertex vertexPeriphery = vertexPeripheryMap.shortestPathVertex (vertex);

		while (!source.equalsIgnoreCase (vertexPeriphery.currentVertexName()))
		{
			path = path + vertexPeriphery.currentVertexName() + " <- ";

			vertexPeriphery = vertexPeripheryMap.shortestPathVertex (vertexPeriphery.preceedingVertexName());
		}

		path = path + source;
		return path;
	}

	public static void main (
		final String[] inputArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		Topography topography = SetTopography();

		DijkstraScheme dijkstraScheme = new DijkstraScheme (topography);

		Set<String> vertexNameSet = topography.vertexNameSet();

		for (String source : vertexNameSet)
		{
			ShortestPathVertexContainer vertexPeripheryMap = dijkstraScheme.spf (source);

			System.out.println ("\t||------------------------------------------------------------||");

			for (String vertex : vertexNameSet)
			{
				if (!vertex.equalsIgnoreCase(source))
				{
					ShortestPathVertex vertexPeriphery = vertexPeripheryMap.shortestPathVertex (vertex);

					System.out.println (
						"\t|| " + source + " to " + vertex + " is " +
						FormatUtil.FormatDouble (vertexPeriphery.weightFromSource(), 4, 0, 1.) +
						" | Previous is " + vertexPeriphery.preceedingVertexName() + " || " + PathVertexes (
							source,
							vertex,
							vertexPeripheryMap
						)
					);
				}
			}

			System.out.println ("\t||------------------------------------------------------------||\n");
		}

		EnvManager.TerminateEnv();
	}
}
