
package org.drip.spaces.graph;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>ShortestPathTree</i> holds the Map of Vertex Peripheries by Weight and Vertex Name. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph">Graph</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShortestPathTree
{
	private java.util.Map<java.lang.String, org.drip.spaces.graph.ShortestPathVertex> _nameIndex = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spaces.graph.ShortestPathVertex>();

	private java.util.Map<java.lang.Double, java.util.List<org.drip.spaces.graph.ShortestPathVertex>>
		_weightIndex = new java.util.TreeMap<java.lang.Double,
			java.util.List<org.drip.spaces.graph.ShortestPathVertex>>();

	private org.drip.spaces.graph.ShortestPathVertex getUnvisited (
		final java.util.List<org.drip.spaces.graph.ShortestPathVertex> shortestPathVertexList)
	{
		for (org.drip.spaces.graph.ShortestPathVertex shortestPathVertex : shortestPathVertexList)
		{
			if (!shortestPathVertex.visited())
			{
				return shortestPathVertex;
			}
		}

		return null;
	}

	/**
	 * Empty ShortestPathTree Constructor
	 */

	public ShortestPathTree()
	{
	}

	/**
	 * Add a shortestPathVertex
	 * 
	 * @param shortestPathVertex The shortestPathVertex
	 * 
	 * @return TRUE - The shortestPathVertex successfully added
	 */

	public boolean addShortestPathVertex (
		final org.drip.spaces.graph.ShortestPathVertex shortestPathVertex)
	{
		if (null == shortestPathVertex)
		{
			return false;
		}

		_nameIndex.put (
			shortestPathVertex.current(),
			shortestPathVertex
		);

		double weightFromSource = shortestPathVertex.weightFromSource();

		if (_weightIndex.containsKey (weightFromSource))
		{
			_weightIndex.get (shortestPathVertex.weightFromSource()).add (shortestPathVertex);
		}
		else
		{
			java.util.List<org.drip.spaces.graph.ShortestPathVertex> shortestPathVertexList = new
				java.util.ArrayList<org.drip.spaces.graph.ShortestPathVertex>();

			shortestPathVertexList.add (shortestPathVertex);

			_weightIndex.put (
				weightFromSource,
				shortestPathVertexList
			);
		}

		return true;
	}

	/**
	 * Add an Uninitialized ShortestPathVertex
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - An Uninitialized ShortestPathVertex successfully added
	 */

	public boolean addUnitializedShortestPathVertex (
		final java.lang.String vertexName)
	{
		try
		{
			return addShortestPathVertex (new org.drip.spaces.graph.ShortestPathVertex (vertexName));
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Indicate of the Vertex is available in the Periphery Map
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Vertex is available in the Periphery Map
	 */

	public boolean containsVertex (
		final java.lang.String vertexName)
	{
		return null != vertexName && !vertexName.isEmpty() && _nameIndex.containsKey (vertexName);
	}

	/**
	 * Retrieve the Vertex Periphery by Name
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return The Vertex Periphery by Name
	 */

	public org.drip.spaces.graph.ShortestPathVertex shortestPathVertex (
		final java.lang.String vertexName)
	{
		return _nameIndex.containsKey (vertexName) ? _nameIndex.get (vertexName) : null;
	}

	/**
	 * Retrieve the Vertex Periphery with the least Weight
	 * 
	 * @return The Vertex Periphery with the least Weight
	 */

	public org.drip.spaces.graph.ShortestPathVertex greedyShortestPathVertex()
	{
		for (java.util.Map.Entry<java.lang.Double, java.util.List<org.drip.spaces.graph.ShortestPathVertex>>
			weightIndexEntry : _weightIndex.entrySet())
		{
			org.drip.spaces.graph.ShortestPathVertex vertexPeriphery = getUnvisited
				(weightIndexEntry.getValue());

			if (null == vertexPeriphery)
			{
				continue;
			}

			vertexPeriphery.setVisited (true);

			return vertexPeriphery;
		}

		return null;
	}

	/**
	 * Retrieve the Name Indexed Vertex Periphery Map
	 * 
	 * @return The Name Indexed Vertex Periphery Map
	 */

	public java.util.Map<java.lang.String, org.drip.spaces.graph.ShortestPathVertex> nameIndex()
	{
		return _nameIndex;
	}

	/**
	 * Retrieve the Weight Indexed Vertex Periphery Map
	 * 
	 * @return The Weight Indexed Vertex Periphery Map
	 */

	public java.util.Map<java.lang.Double, java.util.List<org.drip.spaces.graph.ShortestPathVertex>>
		weightIndex()
	{
		return _weightIndex;
	}
}
