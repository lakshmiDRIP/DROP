
package org.drip.sample.mst;

import java.util.Map;

import org.drip.graph.core.BidirectionalEdge;
import org.drip.graph.core.Graph;
import org.drip.graph.core.Tree;
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
 * <i>PrimMaximumForestGenerator</i> illustrates the Execution of the Prim Maximum Spanning Forest Algorithm.
 * 	The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Grama, A., A. Gupta, G. Karypis, and V. Kumar (2003): <i>Introduction to Parallel Computing
 *  			2<sup>nd</sup> Edition</i> <b>Addison Wesley</b>
 *  	</li>
 *  	<li>
 *  		Kepner, J., and J. Gilbert (2011): <i>Graph Algorithms in the Language of Linear Algebra</i>
 *  			<b>Society for Industrial and Applied Mathematics</b>
 *  	</li>
 *  	<li>
 *  		Pettie, S., and V. Ramachandran (2002): An Optimal Minimum Spanning Tree <i>Algorithm Journal of
 *  			the ACM</i> <b>49 (1)</b> 16-34
 *  	</li>
 *  	<li>
 *  		Sedgewick, R. E., and K. D. Wayne (2011): <i>Algorithms 4<sup>th</sup> Edition</i>
 *  			<b>Addison-Wesley</b>
 *  	</li>
 *  	<li>
 *  		Setia, R., A. Nedunchezhian, and S. Balachandran (2015): A New Parallel Algorithm for Minimum
 *  			Spanning Tree Problem
 *  			https://hipcor.fatcow.com/hipc2009/documents/HIPCSS09Papers/1569250351.pdf
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Prim's Algorithm https://en.wikipedia.org/wiki/Prim%27s_algorithm
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

public class PrimMaximumForestGenerator
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

		PrimGenerator prim = new PrimGenerator (
			graph,
			true
		);

		Map<String, Tree> maximumSpanningForest = prim.optimalSpanningForest().treeMap();

		for (Tree maximumSpanningTree : maximumSpanningForest.values())
		{
			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			System.out.println (
				"\t|                         PRIM MAXIMUM SPANNING TREE PATH                           |"
			);

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			for (BidirectionalEdge edge : maximumSpanningTree.edgeMap().values())
			{
				System.out.println (
					"\t| " + edge
				);
			}

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);

			System.out.println (
				"\t| Maximum Bottleneck Edge => " + maximumSpanningTree.maximumBottleneckEdge()
			);

			System.out.println (
				"\t| Minimum Bottleneck Edge => " + maximumSpanningTree.minimumBottleneckEdge()
			);

			System.out.println (
				"\t| Total MST Length        => " + maximumSpanningTree.length()
			);

			System.out.println (
				"\t|-----------------------------------------------------------------------------------|"
			);
		}

		EnvManager.TerminateEnv();
	}
}
