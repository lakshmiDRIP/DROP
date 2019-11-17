
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
 * <i>ShortestPathFirstWengert maintains</i> the Intermediate Wengert Objects generated during a Single
 * Sequence of the Scheme Run. The References are:
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

public class ShortestPathFirstWengert
{
	private org.drip.spaces.graph.ShortestPathTree _vertexPeripheryMap = null;

	/**
	 * Generate a ShortestPathFirstWengert from the Topography and the Source using the Dijkstra Scheme
	 * 
	 * @param topography The Topography Map
	 * @param source The Source Vertex
	 * 
	 * @return The Dijkstra ShortestPathFirstWengert Instance
	 */

	public static final ShortestPathFirstWengert Dijkstra (
		final org.drip.spaces.graph.Topography topography,
		final java.lang.String source)
	{
		if (null == topography)
		{
			return null;
		}

		java.util.Set<java.lang.String> vertexNameSet = topography.vertexNameSet();

		if (!vertexNameSet.contains (source))
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> connectionMap = topography.connectionMap();

		java.util.Map<java.lang.String, java.lang.Double> egressMap = topography.vertex (source).egressMap();

		org.drip.spaces.graph.ShortestPathTree vertexPeripheryMap = new
			org.drip.spaces.graph.ShortestPathTree();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> egressEntry : egressMap.entrySet())
		{
			java.lang.String egressVertex = egressEntry.getKey();

			org.drip.spaces.graph.ShortestPathVertex vertexPeriphery = null;
			java.lang.String sourceToEgressVertexKey = source + "_" + egressVertex;

			try
			{
				vertexPeriphery = new org.drip.spaces.graph.ShortestPathVertex (egressVertex);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			vertexPeriphery.setPreceeding (source);

			vertexPeriphery.setWeightFromSource (connectionMap.get (sourceToEgressVertexKey));

			vertexPeripheryMap.addShortestPathVertex (vertexPeriphery);
		}

		for (java.lang.String vertexName : vertexNameSet)
		{
			if (!vertexPeripheryMap.containsVertex (vertexName))
			{
				vertexPeripheryMap.addUnitializedShortestPathVertex (vertexName);
			}
		}

		try
		{
			return new ShortestPathFirstWengert (vertexPeripheryMap);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a ShortestPathFirstWengert from the Topography and the Source using the Bellman-Ford Scheme
	 * 
	 * @param topography The Topography Map
	 * @param source The Source Vertex
	 * 
	 * @return The Bellman-Ford ShortestPathFirstWengert Instance
	 */

	public static final ShortestPathFirstWengert BellmanFord (
		final org.drip.spaces.graph.Topography topography,
		final java.lang.String source)
	{
		if (null == topography)
		{
			return null;
		}

		java.util.Set<java.lang.String> vertexNameSet = topography.vertexNameSet();

		if (!vertexNameSet.contains (source))
		{
			return null;
		}

		org.drip.spaces.graph.ShortestPathTree vertexPeripheryMap = new
			org.drip.spaces.graph.ShortestPathTree();

		org.drip.spaces.graph.ShortestPathVertex sourceVertexPeriphery = null;

		try
		{
			sourceVertexPeriphery = new org.drip.spaces.graph.ShortestPathVertex (source);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		sourceVertexPeriphery.setPreceeding (source);

		sourceVertexPeriphery.setWeightFromSource (0.);

		vertexPeripheryMap.addShortestPathVertex (sourceVertexPeriphery);

		for (java.lang.String vertexName : vertexNameSet)
		{
			if (!vertexName.equalsIgnoreCase (source))
			{
				vertexPeripheryMap.addUnitializedShortestPathVertex (vertexName);
			}
		}

		try
		{
			return new ShortestPathFirstWengert (vertexPeripheryMap);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ShortestPathFirstWengert Constructor
	 * 
	 * @param vertexPeripheryMap The Vertex Periphery Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ShortestPathFirstWengert (
		final org.drip.spaces.graph.ShortestPathTree vertexPeripheryMap)
		throws java.lang.Exception
	{
		if (null == (_vertexPeripheryMap = vertexPeripheryMap))
		{
			throw new java.lang.Exception ("ShortestPathFirstWengert Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Vertex Periphery Map
	 * 
	 * @return The Vertex Periphery Map
	 */

	public org.drip.spaces.graph.ShortestPathTree vertexPeripheryMap()
	{
		return _vertexPeripheryMap;
	}
}
