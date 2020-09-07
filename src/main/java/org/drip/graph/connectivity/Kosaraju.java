
package org.drip.graph.connectivity;

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
 * <i>Kosaraju</i> implements the 2-pass Kosaraju Strongly Connected Components Algorithm. The References
 * 	are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Aho, A. V., J. E. Hopcroft, and J. D. Ullman (1983): Data Structures and Algorithms <b>Addison
 *  			Wesley</b>
 *  	</li>
 *  	<li>
 *  		Blelloch, G. E., Y. Gu, J. Shun, and Y. Sun (2016): Parallelism in Randomized Incremental
 *  			Algorithms <i>Proceedings of the 28<sup>th</sup> ACM Symposium on Parallelism in Algorithms
 *  			and Architectures</i> 467-478
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms</i>
 *  			3<sup>rd</sup> Edition <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Sharir, M. (1981): A Strong-connectivity Algorithm and its Applications to Data Flow Analysis
 *  			<i>Computer and Mathematics with Applications</i> <b>7 (1)</b> 67-72
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Kosaraju's Algorithm https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/connectivity/README.md">Graph Connectivity and Connected Components</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Kosaraju
{
	private org.drip.graph.core.Network _graph = null;

	private boolean updateVisitation (
		final org.drip.graph.core.Vertex vertex,
		final java.util.Map<java.lang.String, java.lang.Boolean> vertexVisitationIndicatorMap,
		final java.util.List<java.lang.String> componentVertexNameList)
	{
		java.lang.String vertexName = vertex.name();

		java.util.Map<java.lang.String, org.drip.graph.core.Vertex> vertexMap = _graph.vertexMap();

		if (!vertexVisitationIndicatorMap.get (
			vertexName
		))
		{
			vertexVisitationIndicatorMap.put (
				vertexName,
				true
			);

			java.util.Set<java.lang.String> neighboringVertexNameSet = vertex.neighboringVertexNameSet();

			if (!neighboringVertexNameSet.isEmpty())
			{
				for (java.lang.String neighboringVertexName : neighboringVertexNameSet)
				{
					if (!updateVisitation (
						vertexMap.get (
							neighboringVertexName
						),
						vertexVisitationIndicatorMap,
						componentVertexNameList
					))
					{
						return false;
					}
				}

				componentVertexNameList.add (
					0,
					vertexName
				);
			}
		}

		return true;
	}

	/**
	 * Kosaraju Constructor
	 * 
	 * @param graph The Graph Network
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Kosaraju (
		final org.drip.graph.core.Network graph)
		throws java.lang.Exception
	{
		if (null == (_graph = graph))
		{
			throw new java.lang.Exception (
				"Kosaraju Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Network Graph
	 * 
	 * @return The Network Graph
	 */

	public org.drip.graph.core.Network graph()
	{
		return _graph;
	}
}
