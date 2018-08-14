
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
 * DijkstraScheme implements the Dijkstra Algorithm for finding the Shortest Path between a Pair of Vertexes
 *  in a Graph. The References are:
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

public class DijkstraScheme
{
	private org.drip.spaces.graph.Topography _topography = null;

	private void visitVertex (
		final java.lang.String currentVertex,
		final org.drip.spaces.graph.DijkstraWengert dijkstraWengert)
	{
		java.util.Map<java.lang.String, java.lang.Double> egressMap = _topography.vertexNode
			(currentVertex).egressMap();

		org.drip.spaces.graph.VertexPeripheryMap vertexPeripheryMap = dijkstraWengert.vertexPeripheryMap();

		org.drip.spaces.graph.VertexPeriphery currentVertexPeriphery = vertexPeripheryMap.retrieve
			(currentVertex);

		java.util.Map<java.lang.String, java.lang.Double> connectionMap = dijkstraWengert.connectionMap();

		double currentWeightFromSource = currentVertexPeriphery.weightFromSource();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> egressEntry : egressMap.entrySet())
		{
			java.lang.String egressVertex = egressEntry.getKey();

			double weightFromSourceThroughCurrent = currentWeightFromSource + connectionMap.get
				(currentVertex + "_" + egressVertex);

			org.drip.spaces.graph.VertexPeriphery egressVertexPeriphery = vertexPeripheryMap.retrieve
				(egressVertex);

			if (egressVertexPeriphery.weightFromSource() > weightFromSourceThroughCurrent)
			{
				egressVertexPeriphery.setWeightFromSource (weightFromSourceThroughCurrent);

				egressVertexPeriphery.setPreceedingNode (currentVertex);
			}
		}

		dijkstraWengert.nodeVisited (currentVertex);
	}

	/**
	 * Dijkstra Constructor
	 * 
	 * @param topography The Topography Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DijkstraScheme (
		final org.drip.spaces.graph.Topography topography)
		throws java.lang.Exception
	{
		if (null == (_topography = topography))
		{
			throw new java.lang.Exception ("DijkstraScheme Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Topography Map
	 * 
	 * @return The Topography Map
	 */

	public org.drip.spaces.graph.Topography topography()
	{
		return _topography;
	}

	/**
	 * Initialize the Dijsktra Scheme
	 * 
	 * @param source The Source Vertex Node
	 * 
	 * @return The Initial Dijkstra Wengert
	 */

	public org.drip.spaces.graph.DijkstraWengert setup (
		final java.lang.String source)
	{
		return org.drip.spaces.graph.DijkstraWengert.Standard (
			_topography,
			source
		);
	}

	/**
	 * Run the Dijsktra SPF Algorithm
	 * 
	 * @param source The Source Vertex Node
	 * @param destination The Destination Vertex Node
	 * 
	 * @return The Dijkstra Wengert
	 */

	public org.drip.spaces.graph.DijkstraWengert spf (
		final java.lang.String source,
		final java.lang.String destination)
	{
		org.drip.spaces.graph.DijkstraWengert dijkstraWengert = setup (source);

		if (null == dijkstraWengert)
		{
			return null;
		}

		visitVertex (source, null);

		return dijkstraWengert;
	}
}
