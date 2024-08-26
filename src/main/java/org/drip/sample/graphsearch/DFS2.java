
package org.drip.sample.graphsearch;

import org.drip.graph.core.Edge;
import org.drip.graph.core.Directed;
import org.drip.graph.search.DepthFirst;
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
 * <i>DFS2</i> illustrates Construction/Usage of a Graph DFS and Vertex Ordering. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		de Fraysseix, H., O. de Mendez, and P. Rosenstiehl (2006): Tremaux Trees and Planarity
 *  			<i>International Journal of Foundations of Computer Science</i> <b>17 (5)</b> 1017-1030
 *  	</li>
 *  	<li>
 *  		Mehlhorn, K., and P. Sanders (2008): <i>Algorithms and Data Structures: The Basic Tool-box</i>
 *  			<b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Reif, J. H. (1985): Depth-first Search is inherently Sequential <i>Information Processing
 *  			Letters</i> <b>20 (5)</b> 229-234
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Depth-first Search https://en.wikipedia.org/wiki/Depth-first_search
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

public class DFS2
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

		graph.addEdge (
			new Edge (
				"A",
				"B",
				1.
			)
		);

		graph.addEdge (
			new Edge (
				"B",
				"D",
				2.
			)
		);

		graph.addEdge (
			new Edge (
				"A",
				"C",
				3.
			)
		);

		graph.addEdge (
			new Edge (
				"C",
				"D",
				4.
			)
		);

		DepthFirst dfs = new DepthFirst (
			graph
		);

		OrderedVertexGroup recursiveOrderedVertexGroup = new OrderedVertexGroup();

		dfs.recursive (
			"A",
			recursiveOrderedVertexGroup
		);

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                         RECURSIVE DEPTH-FIRST SEARCH VERTEX ORDERING                         ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		System.out.println ("\t| Vertex Name Set                       => " + recursiveOrderedVertexGroup.vertexNameSet());

		System.out.println ("\t| Visited Vertex Name List              => " + recursiveOrderedVertexGroup.vertexNameList());

		System.out.println ("\t| Pre-Ordered Vertex Name List          => " + recursiveOrderedVertexGroup.preOrder());

		System.out.println ("\t| Reverse Pre-Ordered Vertex Name List  => " + recursiveOrderedVertexGroup.reversePreOrder());

		System.out.println ("\t| Post-Ordered Vertex Name List         => " + recursiveOrderedVertexGroup.postOrder());

		System.out.println ("\t| Reverse Post-Ordered Vertex Name List => " + recursiveOrderedVertexGroup.reversePostOrder());

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		System.out.println ("\t| Lexicographical Ordering              => " + recursiveOrderedVertexGroup.lexicographicalOrdering());

		System.out.println ("\t| Topological Ordering                  => " + recursiveOrderedVertexGroup.topologicalSorting());

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		System.out.println();

		OrderedVertexGroup nonRecursiveOrderedVertexGroup = new OrderedVertexGroup();

		dfs.nonRecursive (
			"A",
			nonRecursiveOrderedVertexGroup
		);

		System.out.println ("\t|----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                       NON-RECURSIVE DEPTH-FIRST SEARCH VERTEX ORDERING                       ||");

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
