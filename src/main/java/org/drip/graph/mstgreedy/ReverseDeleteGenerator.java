
package org.drip.graph.mstgreedy;

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
 * <i>ReverseDeleteGenerator</i> implements the Reverse-Delete Algorithm for generating a Minimum Spanning
 * 	Tree. The References are:
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
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mstgreedy/README.md">Greedy Algorithms for MSTs and Forests</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ReverseDeleteGenerator<V>
	extends org.drip.graph.treebuilder.OptimalSpanningForestGenerator
{

	/**
	 * ReverseDeleteGenerator Constructor
	 * 
	 * @param graph The Graph
	 * @param maximum TRUE - The Maximum Spanning Forest is to be generated
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ReverseDeleteGenerator (
		final org.drip.graph.core.Directed<?> graph,
		final boolean maximum)
		throws java.lang.Exception
	{
		super (
			graph,
			maximum
		);
	}

	@Override public org.drip.graph.core.Forest<?> optimalSpanningForest()
	{
		if (!_graph.isConnected())
		{
			return null;
		}

		boolean maximum = maximum();

		java.util.Map<java.lang.String, org.drip.graph.core.Edge> graphEdgeMap = _graph.edgeMap();

		org.drip.graph.heap.PriorityQueue<java.lang.Double, java.lang.String> edgePriorityQueue =
			_graph.edgePriorityQueue (
				maximum
			);

		while (!edgePriorityQueue.isEmpty())
		{
			java.lang.String edgeKey = edgePriorityQueue.extractExtremum().item();

			org.drip.graph.core.Edge edge = graphEdgeMap.get (
				edgeKey
			);

			if (!_graph.removeEdge (
				edgeKey
			))
			{
				return null;
			}

			if (!_graph.isConnected())
			{
				_graph.addEdge (
					edge
				);
			}
		}

		try
		{
			return new org.drip.graph.mstgreedy.KruskalGenerator<V> (
				_graph,
				maximum
			).optimalSpanningForest();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
