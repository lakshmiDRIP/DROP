
package org.drip.graph.mst;

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
 * <i>Kruskal</i> implements the Kruskal Algorithm for generating a Minimum Spanning Tree. The References
 * 	are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Grama, A., A. Gupta, G. Karypis, and V. Kumar (2003): <i>Introduction to Parallel Computing
 *  			2<sup>nd</sup> Edition</i> <b>Addison Wesley</b>
 *  	</li>
 *  	<li>
 *  		Osipov, V., P. Sanders, and J. Singler (2009): The Filter-Kruskal Minimum Spanning Tree Algorithm
 *  			http://algo2.iti.kit.edu/documents/fkruskal.pdf
 *  	</li>
 *  	<li>
 *  		Quinn, M. J., and N. Deo (1984): Parallel Graph Algorithms <i>ACM Computing Surveys</i> <b>16
 *  			(3)</b> 319-348
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Kruskal's Algorithm https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mst/README.md">Algorithms for Generating Minimum Spanning Trees</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Kruskal
{
	private org.drip.graph.core.Graph _graph = null;

	/**
	 * Kruskal Constructor
	 * 
	 * @param graph The Graph
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Kruskal (
		final org.drip.graph.core.Graph graph)
		throws java.lang.Exception
	{
		if (null == (_graph = graph))
		{
			throw new java.lang.Exception (
				"Kruskal Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Graph
	 * 
	 * @return The Graph
	 */

	public org.drip.graph.core.Graph graph()
	{
		return _graph;
	}

	/**
	 * Generate the Map of the Minimum Spanning Trees from the Initial Vertex
	 * 
	 * @return Map of the Minimum Spanning Trees
	 */

	public org.drip.graph.core.Forest minimumSpanningForest()
	{
		org.drip.graph.core.Forest forest = new org.drip.graph.core.Forest();

		for (java.lang.String vertexName : _graph.vertexMap().keySet())
		{
			if (!forest.unitVertexTree (
				vertexName
			))
			{
				return null;
			}
		}

		java.util.TreeMap<java.lang.Double, org.drip.graph.core.BidirectionalEdge> orderedEdgeMap =
			_graph.orderedEdgeMap();

		while (!orderedEdgeMap.isEmpty())
		{
			java.util.Map.Entry<java.lang.Double, org.drip.graph.core.BidirectionalEdge> firstEntry =
				orderedEdgeMap.firstEntry();

			orderedEdgeMap.remove (
				firstEntry.getKey()
			);

			org.drip.graph.core.BidirectionalEdge currentEdge = firstEntry.getValue();

			java.lang.String firstVertexName = currentEdge.firstVertexName();

			org.drip.graph.core.Tree sourceContainerTree = forest.containingTree (
				firstVertexName
			);

			java.lang.String destinationVertexName = currentEdge.secondVertexName();

			org.drip.graph.core.Tree destinationContainerTree = forest.containingTree (
				destinationVertexName
			);

			if (null != destinationContainerTree &&
				sourceContainerTree != destinationContainerTree)
			{
				if (!sourceContainerTree.absorbTreeAndEdge (
					destinationContainerTree,
					currentEdge
				) || !forest.removeTree (
					destinationVertexName
				))
				{
					return null;
				}
			}
		}

		return forest;
	}
}
