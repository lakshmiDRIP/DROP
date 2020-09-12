
package org.drip.graph.astar;

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
 * <i>HeuristicOptimalPathGenerator</i> generates Optimal Path using a Heuristic-based A<sup>*</sup>
 * 	Algorithm. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Dechter, R., and J. Pearl (1985): Generalized Best-first Search Strategies and the Optimality of
 *  			A<sup>*</sup> <i>Journal of the ACM</i> <b>32 (3)</b> 505-536
 *  	</li>
 *  	<li>
 *  		Hart, P. E., N. J. Nilsson, and B. Raphael (1968): A Formal Basis for the Heuristic Determination
 *  			of the Minimum Cost Paths <i>IEEE Transactions on Systems Sciences and Cybernetics</i> <b>4
 *  			(2)</b> 100-107
 *  	</li>
 *  	<li>
 *  		Kagan, E., and I. Ben-Gal (2014): A Group Testing Algorithm with Online Informational Learning
 *  			<i>IIE Transactions</i> <b>46 (2)</b> 164-184
 *  	</li>
 *  	<li>
 *  		Russell, S. J. and P. Norvig (2018): <i>Artificial Intelligence: A Modern Approach 4<sup>th</sup>
 *  			Edition</i> <b>Pearson</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): A<sup>*</sup> Search Algorithm
 *  			https://en.wikipedia.org/wiki/A*_search_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/astar/README.md">A<sup>*</sup> Heuristic Shortest Path Family</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HeuristicOptimalPathGenerator
	extends org.drip.graph.shortestpath.OptimalPathGenerator
{
	private org.drip.graph.astar.FHeuristic _fHeuristic = null;

	/**
	 * HeuristicOptimalPathGenerator Constructor
	 * 
	 * @param graph Graph underlying the Path Generator
	 * @param shortestPath TRUE - Shortest Path Sought
	 * @param fHeuristic F Heuristic
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HeuristicOptimalPathGenerator (
		final org.drip.graph.core.DirectedGraph graph,
		final boolean shortestPath,
		final org.drip.graph.astar.FHeuristic fHeuristic)
		throws java.lang.Exception
	{
		super (
			graph,
			shortestPath
		);

		if (null == (_fHeuristic = fHeuristic))
		{
			throw new java.lang.Exception (
				"HeuristicOptimalPathGenerator Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the F Heuristic
	 * 
	 * @return The F Heuristic
	 */

	public org.drip.graph.astar.FHeuristic fHeuristic()
	{
		return _fHeuristic;
	}


	@Override public org.drip.graph.shortestpath.VertexAugmentor augmentVertexes (
		final java.lang.String sourceVertexName)
	{
		return null;
	}

	@Override public org.drip.graph.core.Path singlePair (
		final java.lang.String sourceVertexName,
		final java.lang.String destinationVertexName)
	{
		if (null == sourceVertexName || sourceVertexName.isEmpty() ||
			null == destinationVertexName || destinationVertexName.isEmpty())
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> vertexGScoreMap =
			new java.util.HashMap<java.lang.String, java.lang.Double>();

		java.util.Map<java.lang.String, java.lang.Double> vertexFScoreMap =
			new java.util.HashMap<java.lang.String, java.lang.Double>();

		java.util.Map<java.lang.String, org.drip.graph.core.Vertex> vertexMap = graph().vertexMap();

		for (java.lang.String vertexName : vertexMap.keySet())
		{
			vertexGScoreMap.put (
				vertexName,
				java.lang.Double.POSITIVE_INFINITY
			);

			vertexFScoreMap.put (
				vertexName,
				java.lang.Double.POSITIVE_INFINITY
			);
		}

		org.drip.graph.astar.VertexFunction gHeuristic = _fHeuristic.gHeuristic();

		org.drip.graph.astar.VertexFunction hHeuristic = _fHeuristic.hHeuristic();

		vertexGScoreMap.put (
			sourceVertexName,
			0.
		);

		try
		{
			vertexFScoreMap.put (
				sourceVertexName,
				hHeuristic.evaluate (
					vertexMap.get (
						sourceVertexName
					)
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		java.util.Map<java.lang.String, java.lang.String> precedingVertexMap =
			new java.util.HashMap<java.lang.String, java.lang.String>();

		java.util.List<java.lang.String> vertexStack = new java.util.ArrayList<java.lang.String>();

		while (!vertexStack.isEmpty())
		{
			java.lang.String currentVertexName = vertexStack.remove (
				vertexStack.size() - 1
			);

			if (currentVertexName.equalsIgnoreCase (
				destinationVertexName
			))
			{
				continue;
			}

			org.drip.graph.core.Vertex currentVertex = vertexMap.get (
				currentVertexName
			);

			java.util.Set<java.lang.String> neighboringVertexNameSet =
				currentVertex.neighboringVertexNameSet();

			if (neighboringVertexNameSet.isEmpty())
			{
				continue;
			}

			for (java.lang.String neighboringVertexName : neighboringVertexNameSet)
			{
				try
				{
					double tentativeGScore = gHeuristic.evaluate (
						vertexMap.get (
							currentVertexName
						)
					) + graph().edgeMap().get (
						currentVertex + "_" + neighboringVertexName
					).weight();
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		java.util.List<org.drip.graph.core.Edge> edgeList =
			new java.util.ArrayList<org.drip.graph.core.Edge>();

		try
		{
			return new org.drip.graph.core.Path (
				edgeList
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
