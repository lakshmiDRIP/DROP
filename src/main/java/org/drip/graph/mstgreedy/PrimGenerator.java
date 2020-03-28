
package org.drip.graph.mstgreedy;

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
 * <i>PrimGenerator</i> implements the Prim's Algorithm for generating a Minimum Spanning Tree. The
 * 	References are:
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

public class PrimGenerator
	extends org.drip.graph.treebuilder.OptimalSpanningForestGenerator
{
	private boolean updateEdgeTreeMap (
		final java.util.TreeMap<java.lang.Double, org.drip.graph.core.Edge> edgeTreeMap,
		final java.util.Set<java.lang.String> visitedVertexSet,
		final java.lang.String currentVertexName)
	{
		org.drip.graph.core.Vertex currentVertex = _graph.vertexMap().get (
			currentVertexName
		);

		java.util.Map<java.lang.Double, org.drip.graph.core.Edge> adjacencyMap =
			currentVertex.adjacencyMap();

		if (null == adjacencyMap || 0 == adjacencyMap.size())
		{
			return false;
		}

		for (java.util.Map.Entry<java.lang.Double, org.drip.graph.core.Edge> egressEntry :
			adjacencyMap.entrySet())
		{
			org.drip.graph.core.Edge edge = egressEntry.getValue();

			edgeTreeMap.put (
				edge.weight(),
				edge
			);
		}

		visitedVertexSet.add (
			currentVertexName
		);

		return true;
	}

	private java.lang.String uncoveredVertex (
		final org.drip.graph.core.Forest forest)
	{
		java.util.Set<java.lang.String> graphVertexNameSet = _graph.vertexNameSet();

		java.util.Set<java.lang.String> forestVertexNameSet = forest.vertexSet();

		for (java.lang.String graphVertexName : graphVertexNameSet)
		{
			if (!forestVertexNameSet.contains (
				graphVertexName
			))
			{
				return graphVertexName;
			}
		}

		return "";
	}

	/**
	 * PrimGenerator Constructor
	 * 
	 * @param graph The Graph
	 * @param maximum TRUE - The Maximum Spanning Forest is to be generated
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PrimGenerator (
		final org.drip.graph.core.Graph graph,
		final boolean maximum)
		throws java.lang.Exception
	{
		super (
			graph,
			maximum
		);
	}

	/**
	 * Generate the Minimum Spanning Tree from the Initial Vertex
	 * 
	 * @param initialVertexName The Initial Vertex Name
	 * 
	 * @return The Minimum Spanning Tree
	 */

	public org.drip.graph.core.Tree minimumSpanningTree (
		final java.lang.String initialVertexName)
	{
		if (null == initialVertexName || initialVertexName.isEmpty())
		{
			return null;
		}

		org.drip.graph.core.Tree minimumSpanningTree = new org.drip.graph.core.Tree();

		java.util.Set<java.lang.String> visitedVertexSet = new java.util.HashSet<java.lang.String>();

		java.util.TreeMap<java.lang.Double, org.drip.graph.core.Edge> edgeTreeMap =
			new java.util.TreeMap<java.lang.Double, org.drip.graph.core.Edge>();

		if (!updateEdgeTreeMap (
				edgeTreeMap,
				visitedVertexSet,
				initialVertexName
			)
		)
		{
			return minimumSpanningTree;
		}

		boolean maximum = maximum();

		while (!edgeTreeMap.isEmpty())
		{
			java.util.Map.Entry<java.lang.Double, org.drip.graph.core.Edge> entry =
				maximum ? edgeTreeMap.lastEntry() : edgeTreeMap.firstEntry();

			edgeTreeMap.remove (
				entry.getKey()
			);

			org.drip.graph.core.Edge processedEdge = entry.getValue();

			java.lang.String currentVertexName = processedEdge.secondVertexName();

			if (visitedVertexSet.contains (
				currentVertexName
			))
			{
				continue;
			}

			if (!minimumSpanningTree.addBidirectionalEdge (
				processedEdge
			))
			{
				return null;
			}

			updateEdgeTreeMap (
				edgeTreeMap,
				visitedVertexSet,
				currentVertexName
			);
		}

		return minimumSpanningTree;
	}

	/**
	 * Generate the Minimum Spanning Forest from the specified Initial Vertex
	 * 
	 * @param initialVertexName Initial Vertex Name
	 * 
	 * @return The Minimum Spanning Forest
	 */

	public org.drip.graph.core.Forest minimumSpanningForest (
		final java.lang.String initialVertexName)
	{
		org.drip.graph.core.Tree minimumSpanningTreeInitial = minimumSpanningTree (
			initialVertexName
		);

		if (null == minimumSpanningTreeInitial)
		{
			return null;
		}

		org.drip.graph.core.Forest forest = new org.drip.graph.core.Forest();

		if (!forest.addTree (
			initialVertexName,
			minimumSpanningTreeInitial,
			_graph
		))
		{
			return null;
		}

		java.lang.String uncoveredVertex = uncoveredVertex (
			forest
		);

		while (!uncoveredVertex.isEmpty())
		{
			org.drip.graph.core.Tree minimumSpanningTree = minimumSpanningTree (
				initialVertexName
			);

			if (null == minimumSpanningTree ||
				!forest.addTree (
					initialVertexName,
					minimumSpanningTreeInitial,
					_graph
				)
			)
			{
				return null;
			}

			uncoveredVertex = uncoveredVertex (
				forest
			);
		}

		return forest;
	}

	@Override public org.drip.graph.core.Forest optimalSpanningForest()
	{
		java.lang.String initialVertexName = _graph.initialVertexName();

		return initialVertexName.isEmpty() ? null : minimumSpanningForest (
			initialVertexName
		);
	}
}
