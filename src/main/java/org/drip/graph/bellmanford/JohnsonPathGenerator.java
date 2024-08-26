
package org.drip.graph.bellmanford;

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
 * <i>JohnsonPathGenerator</i> generates the Shortest Path for a Directed Graph using the Johnson Algorithm.
 * 	The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Black, P. E. (2004): Johnson's Algorithm https://xlinux.nist.gov/dads/HTML/johnsonsAlgorithm.html
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms</i>
 *  			3<sup>rd</sup> Edition <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Johnson, D. B. (1977): Efficient Algorithms for Shortest Paths in Sparse Networks <i>Journal of
 *  			the ACM</i> <b>24 (1)</b> 1-13
 *  	</li>
 *  	<li>
 *  		Suurballe, J. W. (1974): Disjoint Paths in a Network <i>Networks</i> <b>14 (2)</b> 125-145
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Johnson's Algorithm https://en.wikipedia.org/wiki/Johnson%27s_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/bellmanford/README.md">Bellman Ford Shortest Path Family</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class JohnsonPathGenerator<V>
	extends org.drip.graph.shortestpath.OptimalPathGenerator
{

	/**
	 * JohnsonPathGenerator Constructor
	 * 
	 * @param graph Graph underlying the Path Generator
	 * @param shortestPath TRUE - Shortest Path Sought
	 * @param fHeuristic F Heuristic
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JohnsonPathGenerator (
		final org.drip.graph.core.Directed<?> graph,
		final boolean shortestPath,
		final org.drip.graph.astar.FHeuristic fHeuristic)
		throws java.lang.Exception
	{
		super (
			graph,
			shortestPath,
			fHeuristic
		);
	}

	@Override public org.drip.graph.shortestpath.VertexAugmentor augmentVertexes (
		final java.lang.String sourceVertexName)
	{
		if (null == sourceVertexName || sourceVertexName.isEmpty())
		{
			return null;
		}

		org.drip.graph.core.Directed<?> graph = graph();

		org.drip.graph.core.Directed<?> graphClone = graph.clone();

		java.util.Set<java.lang.String> vertexNameSet = graph.vertexNameSet();

		org.drip.graph.shortestpath.VertexAugmentor bellmanFordVertexAugmentor = null;

		java.lang.String johnsonQVertexName = org.drip.service.common.StringUtil.GUID();

		for (java.lang.String vertexName : vertexNameSet)
		{
			try
			{
				if (!graphClone.addEdge (
					new org.drip.graph.core.Edge (
						johnsonQVertexName,
						vertexName,
						0.
					)
				))
				{
					return null;
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			bellmanFordVertexAugmentor = new org.drip.graph.bellmanford.EdgeRelaxationPathGenerator (
				graphClone,
				shortestPath(),
				fHeuristic()
			).augmentVertexes (
				johnsonQVertexName
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		org.drip.graph.core.Directed<?> bellmanFordGraph = new org.drip.graph.core.Directed<V>();

		java.util.Map<java.lang.String, org.drip.graph.shortestpath.AugmentedVertex> augmentedVertexMap =
			bellmanFordVertexAugmentor.augmentedVertexMap();

		for (java.util.Map.Entry<java.lang.String, org.drip.graph.core.Edge> edgeMapEntry :
			graphClone.edgeMap().entrySet()
		)
		{
			org.drip.graph.core.Edge edge = edgeMapEntry.getValue();

			if (!edge.sourceVertexName().equalsIgnoreCase (
				johnsonQVertexName
			))
			{
				try
				{
					java.lang.String edgeSourceVertexName = edge.sourceVertexName();

					java.lang.String edgeDestinationVertexName = edge.destinationVertexName();

					if (!bellmanFordGraph.addEdge (
						new org.drip.graph.core.Edge (
							edgeSourceVertexName,
							edgeDestinationVertexName,
							edge.weight() + augmentedVertexMap.get (
								edgeSourceVertexName
							).gScore() - augmentedVertexMap.get (
								edgeDestinationVertexName
							).gScore()
						)
					))
					{
						return null;
					}
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		try
		{
			return new org.drip.graph.shortestpath.DijkstraPathGenerator (
				bellmanFordGraph,
				shortestPath(),
				fHeuristic()
			).augmentVertexes (
				sourceVertexName
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
