
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
 * VertexPeripheryMap holds the Map of Vertex Peripheries by Weight and Vertex Name. The References are:
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

public class VertexPeripheryMap
{
	private java.util.Map<java.lang.String, org.drip.spaces.graph.VertexPeriphery> _nameIndex = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spaces.graph.VertexPeriphery>();

	private java.util.Map<java.lang.Double, org.drip.spaces.graph.VertexPeriphery> _weightIndex = new
		java.util.TreeMap<java.lang.Double, org.drip.spaces.graph.VertexPeriphery>();

	/**
	 * Empty VertexPeripheryMap Constructor
	 */

	public VertexPeripheryMap()
	{
	}

	/**
	 * Add a Vertex Periphery
	 * 
	 * @param vertexPeriphery The Vertex Periphery
	 * 
	 * @return TRUE - The Vertex Periphery successfully added
	 */

	public boolean addVertexPeriphery (
		final org.drip.spaces.graph.VertexPeriphery vertexPeriphery)
	{
		if (null == vertexPeriphery)
		{
			return false;
		}

		_nameIndex.put (
			vertexPeriphery.current(),
			vertexPeriphery
		);

		_weightIndex.put (
			vertexPeriphery.weightFromSource(),
			vertexPeriphery
		);

		return true;
	}

	/**
	 * Add an Uninitialized Vertex Periphery
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - An Uninitialized Vertex Periphery successfully added
	 */

	public boolean addUnitializedVertexPeriphery (
		final java.lang.String vertexName)
	{
		org.drip.spaces.graph.VertexPeriphery vertexPeriphery = null;

		try
		{
			vertexPeriphery = new org.drip.spaces.graph.VertexPeriphery (vertexName);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		_nameIndex.put (
			vertexPeriphery.current(),
			vertexPeriphery
		);

		_weightIndex.put (
			vertexPeriphery.weightFromSource(),
			vertexPeriphery
		);

		return true;
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

	public org.drip.spaces.graph.VertexPeriphery retrieve (
		final java.lang.String vertexName)
	{
		return _nameIndex.containsKey (vertexName) ? _nameIndex.get (vertexName) : null;
	}

	/**
	 * Retrieve the Vertex Periphery with the least Weight
	 * 
	 * @return The Vertex Periphery with the least Weight
	 */

	public org.drip.spaces.graph.VertexPeriphery greedyRetrieval()
	{
		for (java.util.Map.Entry<java.lang.Double, org.drip.spaces.graph.VertexPeriphery> weightIndexEntry :
			_weightIndex.entrySet())
		{
			return weightIndexEntry.getValue();
		}

		return null;
	}

	/**
	 * Retrieve the Name Indexed Vertex Periphery Map
	 * 
	 * @return The Name Indexed Vertex Periphery Map
	 */

	public java.util.Map<java.lang.String, org.drip.spaces.graph.VertexPeriphery> nameIndex()
	{
		return _nameIndex;
	}

	/**
	 * Retrieve the Weight Indexed Vertex Periphery Map
	 * 
	 * @return The Weight Indexed Vertex Periphery Map
	 */

	public java.util.Map<java.lang.Double, org.drip.spaces.graph.VertexPeriphery> weightIndex()
	{
		return _weightIndex;
	}
}
