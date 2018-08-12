
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
 * Topography holds Vertex Nodes and the Paths between them. The References are:
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
	private java.util.Map<java.lang.String, org.drip.spaces.graph.VertexNode> _vertexNodeMap = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spaces.graph.VertexNode>();

	/**
	 * Empty Topography Constructor
	 */

	public Topography()
	{
	}

	/**
	 * Retrieve The Vertex Node Name Set
	 * 
	 * @return The Vertex Node Name Set
	 */

	public java.util.Set<java.lang.String> vertexNodeNameSet()
	{
		return _vertexNodeMap.keySet();
	}

	/**
	 * Add The Vertex Node
	 * 
	 * @param vertexNodeName Name of the Vertex Node
	 * 
	 * @return TRUE - The Vertex Node successfully added
	 */

	public boolean addVertexNode (
		final java.lang.String vertexNodeName)
	{
		if (null == vertexNodeName || vertexNodeName.isEmpty())
		{
			return false;
		}

		try
		{
			_vertexNodeMap.put (
				vertexNodeName,
				new org.drip.spaces.graph.VertexNode (vertexNodeName)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Verify if the Vertex Node is available
	 * 
	 * @param vertexNodeName Name of the Vertex Node
	 * 
	 * @return TRUE - The Vertex Node is available
	 */

	public boolean vertexNodeExists (
		final java.lang.String vertexNodeName)
	{
		return null != vertexNodeName && _vertexNodeMap.containsKey (vertexNodeName);
	}

	/**
	 * Retrieve the Named Vertex Node
	 * 
	 * @param vertexNodeName Name of the Vertex Node
	 * 
	 * @return The Vertex Node
	 */

	public org.drip.spaces.graph.VertexNode vertexNode (
		final java.lang.String vertexNodeName)
	{
		return vertexNodeExists (vertexNodeName) ? _vertexNodeMap.get (vertexNodeName) : null;
	}

	/**
	 * Add Source < -- > Destination Weighted Path
	 * 
	 * @param source The Source Vertex Node
	 * @param destination The Destination Vertex Node
	 * @param weight The Weight
	 * 
	 * @return TRUE - The Path successfully added
	 */

	public boolean addPath (
		final java.lang.String source,
		final java.lang.String destination,
		final double weight)
	{
		org.drip.spaces.graph.VertexNode sourceVertexNode = vertexNode (source);

		org.drip.spaces.graph.VertexNode destinationVertexNode = vertexNode (destination);

		return null != sourceVertexNode && null != destinationVertexNode && sourceVertexNode.addEgress (
			destination,
			weight
		) && destinationVertexNode.addEgress (
			source,
			weight
		);
	}

	/**
	 * Retrieve the Map of Vertex Nodes
	 * 
	 * @return The Map of Vertex Nodes
	 */

	public java.util.Map<java.lang.String, org.drip.spaces.graph.VertexNode> vertexNodeMap()
	{
		return _vertexNodeMap;
	}

	/**
	 * Location if the Pair of Nodes are Adjacent
	 * 
	 * @param source The Source Vertex Node
	 * @param destination The Destination Vertex Node
	 * 
	 * @return TRUE - The Pair of Nodes are Adjacent
	 */

	public boolean adjacent (
		final java.lang.String source,
		final java.lang.String destination)
	{
		org.drip.spaces.graph.VertexNode sourceVertexNode = vertexNode (source);

		org.drip.spaces.graph.VertexNode destinationVertexNode = vertexNode (destination);

		if (null == sourceVertexNode || null == destinationVertexNode)
		{
			return false;
		}

		return sourceVertexNode.egressMap().containsKey (destination);
	}

	/**
	 * Compute the Weight between Source and Destination if Adjacent
	 * 
	 * @param source The Source Vertex Node
	 * @param destination The Destination Vertex Node
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
			throw new java.lang.Exception ("Topography::adjacentDistance => Invalid Adjaceny Check");
		}

		return vertexNode (source).egressMap().get (destination);
	}

	/**
	 * Generate the Connection Map between valid Pairs of Source and Destination
	 * 
	 * @return The Connection Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> connectionMap()
	{
		java.util.Set<java.lang.String> vertexNodeNameSet = _vertexNodeMap.keySet();

		java.util.Map<java.lang.String, java.lang.Double> connectionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (String source : vertexNodeNameSet)
		{
			for (String destination : vertexNodeNameSet)
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
