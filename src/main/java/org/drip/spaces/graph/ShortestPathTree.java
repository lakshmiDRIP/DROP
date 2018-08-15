
package org.drip.spaces.graph;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * ShortestPathTree holds the Map of Vertex Peripheries by Weight and Vertex Name. The References are:
 *  
 *  1) Wikipedia (2018a): Graph (Abstract Data Type)
 *  	https://en.wikipedia.org/wiki/Graph_(abstract_data_type).
 *  
 *  2) Wikipedia (2018b): Graph Theory https://en.wikipedia.org/wiki/Graph_theory.
 *  
 *  3) Wikipedia (2018c): Graph (Discrete Mathematics)
 *  	https://en.wikipedia.org/wiki/Graph_(discrete_mathematics).
 *  
 *  4) Wikipedia (2018d): Dijkstra's Algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm.
 *  
 *  5) Wikipedia (2018e): Bellman-Ford Algorithm
 *  	https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm.
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
