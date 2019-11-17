
package org.drip.spaces.graph;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>TopographyEdgeMap</i> maintains a Map of the Topography Connection Edges. The References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Wikipedia (2018a): Graph (Abstract Data Type)
 *  			https://en.wikipedia.org/wiki/Graph_(abstract_data_type)
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018b): Graph Theory https://en.wikipedia.org/wiki/Graph_theory
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018c): Graph (Discrete Mathematics)
 *  			https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018d): Dijkstra's Algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018e): Bellman-Ford Algorithm
 *  			https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/README.md">Graph Representation and Traversal Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TopographyEdgeMap
{
	private java.util.Map<java.lang.String, org.drip.spaces.graph.Edge> _edgeMap = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spaces.graph.Edge>();

	/**
	 * Empty TopographyEdgeMap Constructor
	 */

	public TopographyEdgeMap()
	{
	}

	/**
	 * Add an Edge
	 * 
	 * @param edge The Edge
	 * 
	 * @return TRUE - The Edge successfully added
	 */

	public boolean addEdge (
		final org.drip.spaces.graph.Edge edge)
	{
		if (null == edge)
		{
			return false;
		}

		java.lang.String source = edge.source();

		java.lang.String destination = edge.destination();

		_edgeMap.put (
			source + "_" + destination,
			edge
		);

		try
		{
			_edgeMap.put (
				destination + "_" + source,
				new org.drip.spaces.graph.Edge (
					destination,
					source,
					edge.weight()
				)
			);

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Edge Map
	 * 
	 * @return The Edge Map
	 */

	public java.util.Map<java.lang.String, org.drip.spaces.graph.Edge> edgeMap()
	{
		return _edgeMap;
	}

	/**
	 * Retrieve the Edge connecting the Source and the Destination
	 * 
	 * @param source The Source
	 * @param destination The Destination
	 * 
	 * @return The Edge connecting the Source and the Destination
	 */

	public org.drip.spaces.graph.Edge edge (
		final java.lang.String source,
		final java.lang.String destination)
	{
		if (null == source || source.isEmpty() ||
			null == destination || destination.isEmpty())
		{
			return null;
		}

		java.lang.String key = source + "_" + destination;

		return _edgeMap.containsKey (key) ? _edgeMap.get (key) : null;
	}

	/**
	 * Retrieve all the Edges corresponding to the Source Vertex
	 * 
	 * @param source The Source Vertex
	 * 
	 * @return Edges corresponding to the Source Vertex
	 */

	public java.util.List<org.drip.spaces.graph.Edge> edgeList (
		final java.lang.String source)
	{
		if (null == source || source.isEmpty())
		{
			return null;
		}

		java.util.List<org.drip.spaces.graph.Edge> edgeList = new
			java.util.ArrayList<org.drip.spaces.graph.Edge>();

		for (java.util.Map.Entry<java.lang.String, org.drip.spaces.graph.Edge> edgeMapEntry :
			_edgeMap.entrySet())
		{
			java.lang.String key = edgeMapEntry.getKey();

			if (key.startsWith (source))
			{
				edgeList.add (edgeMapEntry.getValue());
			}
		}

		return edgeList;
	}
}
