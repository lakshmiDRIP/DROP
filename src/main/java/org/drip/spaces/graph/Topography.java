
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
 * Topography holds Vertexes and the Paths between them. The References are:
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

public class Topography
{
	private java.util.Map<java.lang.String, org.drip.spaces.graph.Vertex> _vertexMap = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spaces.graph.Vertex>();

	/**
	 * Empty Topography Constructor
	 */

	public Topography()
	{
	}

	/**
	 * Retrieve The Vertex Name Set
	 * 
	 * @return The Vertex Name Set
	 */

	public java.util.Set<java.lang.String> vertexNameSet()
	{
		return _vertexMap.keySet();
	}

	/**
	 * Add The Vertex
	 * 
	 * @param vertexName Name of the Vertex
	 * 
	 * @return TRUE - The Vertex successfully added
	 */

	public boolean addVertex (
		final java.lang.String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty())
		{
			return false;
		}

		try
		{
			_vertexMap.put (
				vertexName,
				new org.drip.spaces.graph.Vertex (vertexName)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Verify if the Vertex is available
	 * 
	 * @param vertexName Name of the Vertex
	 * 
	 * @return TRUE - The Vertex is available
	 */

	public boolean vertexExists (
		final java.lang.String vertexName)
	{
		return null != vertexName && _vertexMap.containsKey (vertexName);
	}

	/**
	 * Retrieve the Named Vertex
	 * 
	 * @param vertexName Name of the Vertex
	 * 
	 * @return The Vertex
	 */

	public org.drip.spaces.graph.Vertex vertex (
		final java.lang.String vertexName)
	{
		return vertexExists (vertexName) ? _vertexMap.get (vertexName) : null;
	}

	/**
	 * Add an Edge
	 * 
	 * @param edge The Edge to be added
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

		double weight = edge.weight();

		java.lang.String source = edge.source();

		java.lang.String destination = edge.destination();

		org.drip.spaces.graph.Vertex sourceVertex = vertex (source);

		org.drip.spaces.graph.Vertex destinationVertex = vertex (destination);

		return null != sourceVertex && null != destinationVertex && sourceVertex.addEgress (
			destination,
			weight
		) && destinationVertex.addEgress (
			source,
			weight
		);
	}

	/**
	 * Retrieve the Map of Vertex
	 * 
	 * @return The Map of Vertex
	 */

	public java.util.Map<java.lang.String, org.drip.spaces.graph.Vertex> vertexMap()
	{
		return _vertexMap;
	}

	/**
	 * Indicate if the Pair of Vertexes are Adjacent
	 * 
	 * @param source The Source Vertex
	 * @param destination The Destination Vertex
	 * 
	 * @return TRUE - The Pair of Vertexes are Adjacent
	 */

	public boolean adjacent (
		final java.lang.String source,
		final java.lang.String destination)
	{
		org.drip.spaces.graph.Vertex sourceVertex = vertex (source);

		org.drip.spaces.graph.Vertex destinationVertex = vertex (destination);

		if (null == sourceVertex || null == destinationVertex)
		{
			return false;
		}

		return sourceVertex.egressMap().containsKey (destination);
	}

	/**
	 * Compute the Weight between Source and Destination if Adjacent
	 * 
	 * @param source The Source Vertex
	 * @param destination The Destination Vertex
	 * 
	 * @return The Weight between Source and Destination if Adjacent
	 * 
	 * @throws java.lang.Exception Thrown if the Source and the Destination are not Adjacent
	 */

	public double adjacentWeight (
		final java.lang.String source,
		final java.lang.String destination)
		throws java.lang.Exception
	{
		if (!adjacent (
			source,
			destination
		))
		{
			throw new java.lang.Exception ("Topography::adjacentDistance => Invalid Adjacency Check");
		}

		return vertex (source).egressMap().get (destination);
	}

	/**
	 * Generate the Connection Map between valid Pairs of Source and Destination
	 * 
	 * @return The Connection Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> connectionMap()
	{
		java.util.Set<java.lang.String> vertexNameSet = _vertexMap.keySet();

		java.util.Map<java.lang.String, java.lang.Double> connectionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (String source : vertexNameSet)
		{
			for (String destination : vertexNameSet)
			{
				try
				{
					if (adjacent (
						source,
						destination
					))
					{
						double adjacentWeight = adjacent (
							source,
							destination
						) ? adjacentWeight (
							source,
							destination
						) : 0.;

						connectionMap.put (
							source + "_" + destination,
							adjacentWeight
						);

						connectionMap.put (
							destination + "_" + source,
							adjacentWeight
						);
					}
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return connectionMap;
	}
}
