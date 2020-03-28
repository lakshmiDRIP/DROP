
package org.drip.sample.graph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.graph.core.Edge;
import org.drip.graph.core.CompleteBipartiteGraph;
import org.drip.graph.core.Graph;
import org.drip.graph.core.Tree;
import org.drip.graph.mstgreedy.KruskalGenerator;
import org.drip.graph.mstgreedy.PrimGenerator;
import org.drip.service.env.EnvManager;

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
 * <i>CompleteBipartiteProperties</i> illustrates the Characteristic Properties of a Complete Bipartite
 * 	Graph. The References are:
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

public class CompleteBipartiteProperties
{

	private static final Set<String> VertexNameSet (
		final String[] vertexNameArray)
		throws Exception
	{
		Set<String> vertexSetP = new HashSet<String>();

		for (String vertexName : vertexNameArray)
		{
			vertexSetP.add (
				vertexName
			);
		}

		return vertexSetP;
	}

	private static final void AddEdge (
		final Map<String, Edge> crossConnectMap,
		final String vertexNameP,
		final String vertexNameQ,
		final double weight)
		throws Exception
	{
		crossConnectMap.put (
			vertexNameP + "_" + vertexNameQ,
			new Edge (
				vertexNameP,
				vertexNameQ,
				weight
			)
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		String[] vertexNameArrayP = new String[]
		{
			"madras    ",
			"bangalore ",
			"hyderabad ",
			"cochin    ",
		};

		String[] vertexNameArrayQ = new String[]
		{
			"bombay    ",
			"pune      ",
			"ahmedabad ",
		};

		Map<String, Edge> crossConnectMap = new CaseInsensitiveHashMap<Edge>();

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[0], // Madras
			vertexNameArrayQ[0], // Bombay
			1279.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[0], // Madras
			vertexNameArrayQ[1], // Pune
			1087.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[0], // Madras
			vertexNameArrayQ[2], // Ahmedabad
			1771.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[1], // Bangalore
			vertexNameArrayQ[0], // Bombay
			1211.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[1], // Bangalore
			vertexNameArrayQ[1], // Pune
			1019.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[1], // Bangalore
			vertexNameArrayQ[2], // Ahmedabad
			1703.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[2], // Hyderabad
			vertexNameArrayQ[0], // Bombay
			751.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[2], // Hyderabad
			vertexNameArrayQ[1], // Pune
			559.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[2], // Hyderabad
			vertexNameArrayQ[2], // Ahmedabad
			1243.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[3], // Cochin
			vertexNameArrayQ[0], // Bombay
			1290.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[3], // Cochin
			vertexNameArrayQ[1], // Pune
			1109.
		);

		AddEdge (
			crossConnectMap,
			vertexNameArrayP[3], // Cochin
			vertexNameArrayQ[2], // Ahmedabad
			1782.
		);

		Graph graph = new CompleteBipartiteGraph (
			VertexNameSet (
				vertexNameArrayP
			),
			VertexNameSet (
				vertexNameArrayQ
			),
			crossConnectMap
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------"
		);

		System.out.println (
			"\t| Is the Graph Connected?            => " + graph.isConnected()
		);

		System.out.println (
			"\t| Leaf Vertexes                      => " + graph.leafVertexNameList()
		);

		System.out.println (
			"\t| Does the Graph contain a Cycle?    => " + graph.containsCycle()
		);

		System.out.println (
			"\t| Is the Graph Cyclical?             => " + graph.isCyclical()
		);

		System.out.println (
			"\t| Is the Graph a Tree?               => " + graph.isTree()
		);

		System.out.println (
			"\t| Is the Graph Complete?             => " + graph.isComplete()
		);

		System.out.println (
			"\t| Is the Graph Dense?                => " + graph.isDense()
		);

		System.out.println (
			"\t| Graph Vertex Degree Map            => " + graph.vertexDegreeMap()
		);

		System.out.println (
			"\t| Graph Type                         => " + graph.type()
		);

		System.out.println (
			"\t| Graph Kirchoff Spanning Tree Count => " + graph.kirchoffSpanningTreeCount()
		);

		System.out.println (
			"\t| Graph Spanning Tree Count          => " + graph.spanningTreeCount()
		);

		System.out.println (
			"\t|------------------------------------------------------------------------------------"
		);

		System.out.println();

		KruskalGenerator kruskal = new KruskalGenerator (
			graph,
			false
		);

		Map<String, Tree> minimumSpanningForest = kruskal.optimalSpanningForest().treeMap();

		for (Tree minimumSpanningTree : minimumSpanningForest.values())
		{
			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			System.out.println (
				"\t|                        KRUSKAL MINIMUM SPANNING TREE PATH                         |"
			);

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			for (Edge edge : minimumSpanningTree.edgeMap().values())
			{
				System.out.println (
					"\t| " + edge
				);
			}

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			System.out.println (
				"\t| Total MST Length => " + minimumSpanningTree.length()
			);

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);
		}

		System.out.println();

		PrimGenerator prim = new PrimGenerator (
			graph,
			false
		);

		minimumSpanningForest = prim.optimalSpanningForest().treeMap();

		for (Tree minimumSpanningTree : minimumSpanningForest.values())
		{
			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			System.out.println (
				"\t|                         PRIM MINIMUM SPANNING TREE PATH                           |"
			);

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			for (Edge edge : minimumSpanningTree.edgeMap().values())
			{
				System.out.println (
					"\t| " + edge
				);
			}

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			System.out.println (
				"\t| Total MST Length => " + minimumSpanningTree.length()
			);

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);
		}

		EnvManager.TerminateEnv();
	}
}
