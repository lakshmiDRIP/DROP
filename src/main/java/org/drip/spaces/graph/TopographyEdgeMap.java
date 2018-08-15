
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
 * TopographyEdgeMap maintains a Map of the Topography Connection Edges. The References are:
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
