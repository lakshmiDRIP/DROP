
package org.drip.sample.graphsearch;

import org.drip.graph.core.Edge;
import org.drip.graph.core.Directed;
import org.drip.graph.search.BreadthFirst;
import org.drip.graph.search.OrderedVertexGroup;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>BFS1</i> illustrates Construction/Usage of a Graph BFS and Vertex Ordering. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Aziz, A., and A. Prakash (2010): Algorithms for Interviews
 *  			http://users.ece.utexas.edu/~adnan/afi-samples-new.pdf
 *  	</li>
 *  	<li>
 *  		Coppin, B. (2004): <i>Artificial Intelligence Illuminated</i> <b>Jones and Bartlett Learning</b>
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Russell, S., and P. Norvig (2003): <i>Artificial Intelligence: Modern Approach 2<sup>nd</sup>
 *  			Edition</i> <b>Prentice Hall</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Breadth-first Search https://en.wikipedia.org/wiki/Breadth-first_search
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/graphsearch/README.md">Breadth/Depth First Search/Ordering</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BFS1
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		Directed graph = new Directed();

		graph.addBidirectionalEdge (
			new Edge (
				"Frankfurt",
				"Mannheim",
				85.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Frankfurt",
				"Wurzburg",
				217.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Frankfurt",
				"Kassel",
				173.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Mannheim",
				"Karlsruhe",
				80.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Stuttgart",
				"Nurnberg",
				183.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Wurzburg",
				"Nurnberg",
				103.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Wurzburg",
				"Erfurt",
				186.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Kassel",
				"Munchen",
				502.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Nurnberg",
				"Munchen",
				167.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Karlsruhe",
				"Augsburg",
				250.
			)
		);

		graph.addBidirectionalEdge (
			new Edge (
				"Augsburg",
				"Munchen",
				84.
			)
		);

		BreadthFirst dfs = new BreadthFirst (
			graph
		);

		OrderedVertexGroup nonRecursiveOrderedVertexGroup = new OrderedVertexGroup();

		dfs.nonRecursive (
			"Frankfurt",
			nonRecursiveOrderedVertexGroup
		);

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                      NON-RECURSIVE BREADTH-FIRST SEARCH VERTEX ORDERING                      ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		System.out.println ("\t| Vertex Name Set                       => " + nonRecursiveOrderedVertexGroup.vertexNameSet());

		System.out.println ("\t| Visited Vertex Name List              => " + nonRecursiveOrderedVertexGroup.vertexNameList());

		System.out.println ("\t| Pre-Ordered Vertex Name List          => " + nonRecursiveOrderedVertexGroup.preOrder());

		System.out.println ("\t| Reverse Pre-Ordered Vertex Name List  => " + nonRecursiveOrderedVertexGroup.reversePreOrder());

		System.out.println ("\t| Post-Ordered Vertex Name List         => " + nonRecursiveOrderedVertexGroup.postOrder());

		System.out.println ("\t| Reverse Post-Ordered Vertex Name List => " + nonRecursiveOrderedVertexGroup.reversePostOrder());

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		System.out.println ("\t| Lexicographical Ordering              => " + nonRecursiveOrderedVertexGroup.lexicographicalOrdering());

		System.out.println ("\t| Topological Ordering                  => " + nonRecursiveOrderedVertexGroup.topologicalSorting());

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
