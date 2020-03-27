
package org.drip.sample.mst;

import java.util.Map;

import org.drip.graph.core.BidirectionalEdge;
import org.drip.graph.core.Graph;
import org.drip.graph.core.Tree;
import org.drip.graph.mstgreedy.ReverseDeleteGenerator;
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
 * <i>ReverseDeleteMinimumForestGenerator</i> illustrates the Execution of the Reverse-Delete Algorithm for
 * 	Minimum Spanning Tree. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Kleinberg, J., and E. Tardos (2006): <i>Algorithm Design</i> <b>Addison Wesley</b>
 *  	</li>
 *  	<li>
 *  		Kruskal, J. B. (1956): On the Shortest Spanning Subtree of a Graph and the Traveling Salesman
 *  			Problem <i>Proceedings of the American Mathematical Society</i> <b>7 (1)</b> 48-50
 *  	</li>
 *  	<li>
 *  		Thorup, M. (2000): Near-optimal Fully-dynamic Graph Connectivity <i>Proceedings on the 32nd ACM
 *  			Symposium on the Theory of Computing</i> 343-350
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Reverse-delete Algorithm https://en.wikipedia.org/wiki/Reverse-delete_algorithm
 *  	</li>
 *  </ul>
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/mst/README.md">Minimum Spanning Tree and Forest Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ReverseDeleteMinimumForestGenerator
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		String[] vertexArray = new String[]
		{
			"delhi     ",
			"bombay    ",
			"madras    ",
			"calcutta  ",
			"bangalore ",
			"hyderabad ",
			"cochin    ",
			"pune      ",
			"ahmedabad ",
			"jaipur    "
		};

		Graph graph = new Graph();

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[0], // Delhi
				vertexArray[1], // Bombay
				1388.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[0], // Delhi
				vertexArray[2], // Madras
				2191.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[1], // Bombay
				vertexArray[2], // Madras
				1279.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[0], // Delhi
				vertexArray[3], // Calcutta
				1341.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[1], // Bombay
				vertexArray[3], // Calcutta
				1968.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[2], // Madras
				vertexArray[3], // Calcutta
				1663.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[2], // Madras
				vertexArray[4], // Bangalore
				361.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[2], // Madras
				vertexArray[5], // Hyderabad
				784.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[2], // Madras
				vertexArray[6], // Cochin
				697.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[1], // Bombay
				vertexArray[7], // Pune
				192.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[1], // Bombay
				vertexArray[8], // Ahmedabad
				492.
			)
		);

		graph.addBidirectionalEdge (
			new BidirectionalEdge (
				vertexArray[0], // Delhi
				vertexArray[9], // Jaipur
				308.
			)
		);

		ReverseDeleteGenerator reverseDelete = new ReverseDeleteGenerator (
			graph,
			false
		);

		Map<String, Tree> minimumSpanningForest = reverseDelete.optimalSpanningForest().treeMap();

		for (Tree minimumSpanningTree : minimumSpanningForest.values())
		{
			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			System.out.println (
				"\t|                     REVERSE-DELETE MINIMUM SPANNING TREE PATH                     |"
			);

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			for (BidirectionalEdge edge : minimumSpanningTree.edgeMap().values())
			{
				System.out.println (
					"\t| " + edge
				);
			}

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			System.out.println (
				"\t| Maximum Bottleneck Edge => " + minimumSpanningTree.maximumBottleneckEdge()
			);

			System.out.println (
				"\t| Minimum Bottleneck Edge => " + minimumSpanningTree.minimumBottleneckEdge()
			);

			System.out.println (
				"\t| Total MST Length        => " + minimumSpanningTree.length()
			);

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);
		}

		EnvManager.TerminateEnv();
	}
}
