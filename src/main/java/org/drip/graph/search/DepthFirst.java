
package org.drip.graph.search;

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
 * <i>DepthFirst</i> implements the Recursive and Iterative Depth-first Search Schemes. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/search/README.md">BFS, DFS, and Ordered Vertexes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DepthFirst
{
	private org.drip.graph.core.Network<?> _network = null;

	/**
	 * DepthFirst Constructor
	 * 
	 * @param network Graph Network
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DepthFirst (
		final org.drip.graph.core.Network<?> network)
		throws java.lang.Exception
	{
		if (null == (_network = network))
		{
			throw new java.lang.Exception (
				"DepthFirst Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Graph Network
	 * 
	 * @return The Graph Network
	 */

	public org.drip.graph.core.Network<?> network()
	{
		return _network;
	}

	/**
	 * Generate the Vertex Set using a Recursive Depth-First Search
	 *
	 * @param vertexName Vertex Name
	 * @param orderedVertexGroup The Ordered Vertex Group
	 * 
	 * @return The Vertex Set using a Recursive Depth-First Search
	 */

	public boolean recursive (
		final java.lang.String vertexName,
		final org.drip.graph.search.OrderedVertexGroup orderedVertexGroup)
	{
		if (!_network.containsVertex (
				vertexName
			) || null == orderedVertexGroup
		)
		{
			return false;
		}

		orderedVertexGroup.addVertexName (
			vertexName
		);

		org.drip.graph.core.Vertex<?> vertex = _network.vertexMap().get (
			vertexName
		);

		java.util.Set<java.lang.String> neighboringVertexNameSet = vertex.neighboringVertexNameSet();

		if (null == neighboringVertexNameSet || 0 == neighboringVertexNameSet.size())
		{
			return true;
		}

		for (java.lang.String secondVertexName : neighboringVertexNameSet)
		{
			if (!orderedVertexGroup.vertexPresent (
				secondVertexName
			))
			{
				if (!recursive (
					secondVertexName,
					orderedVertexGroup
				))
				{
					return false;
				}
			}
			else
			{
				orderedVertexGroup.touchVertex (
					secondVertexName
				);
			}
		}

		return true;
	}

	/**
	 * Generate the Vertex Set using a Non-recursive Depth-First Search
	 * 
	 * @param vertexName Vertex Name
	 * @param orderedVertexGroup The Ordered Vertex Group
	 * 
	 * @return The Vertex Set using a Non-recursive Depth-First Search
	 */

	public boolean nonRecursive (
		final java.lang.String vertexName,
		final org.drip.graph.search.OrderedVertexGroup orderedVertexGroup)
	{
		if (null == orderedVertexGroup ||
			!_network.containsVertex (
				vertexName
			)
		)
		{
			return false;
		}

		java.util.List<java.lang.String> processVertexList = new java.util.ArrayList<java.lang.String>();

		processVertexList.add (
			vertexName
		);

		java.util.Map<java.lang.String, org.drip.graph.core.Vertex<?>> vertexMap = _network.vertexMap();

		while (!processVertexList.isEmpty())
		{
			int lastIndex = processVertexList.size() - 1;

			java.lang.String currentVertexName = processVertexList.get (
				lastIndex
			);

			processVertexList.remove (
				lastIndex
			);

			if (!orderedVertexGroup.addVertexName (
				currentVertexName
			))
			{
				return false;
			}

			org.drip.graph.core.Vertex<?> currentVertex = vertexMap.get (
				currentVertexName
			);

			java.util.Set<java.lang.String> neighboringVertexNameSet =
				currentVertex.neighboringVertexNameSet();

			if (null != neighboringVertexNameSet && 0 != neighboringVertexNameSet.size())
			{
				for (java.lang.String destinationVertexName : neighboringVertexNameSet)
				{
					if (!orderedVertexGroup.vertexPresent (
						destinationVertexName
					))
					{
						if (!processVertexList.contains (
							destinationVertexName
						))
						{
							processVertexList.add (
								destinationVertexName
							);
						}
					}
					else
					{
						orderedVertexGroup.touchVertex (
							destinationVertexName
						);
					}
				}
			}
		}

		return true;
	}
}
