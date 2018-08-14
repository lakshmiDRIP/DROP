
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
 * DijkstraWengert maintains the Intermediate Wengert Objects generated during a Single Sequence of the
 *  Scheme Run. The References are:
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

public class DijkstraWengert
{
	private org.drip.spaces.graph.VertexPeripheryMap _vertexPeripheryMap = null;
	private java.util.Map<java.lang.String, java.lang.Double> _connectionMap = null;
	private java.util.Map<java.lang.String, java.lang.Boolean> _vertexVisitationStatus = null;

	/**
	 * Generate a Standard DijkstraWengert from the Topography and the Source
	 * 
	 * @param topography The Topography Map
	 * @param source The Source Vertex Node
	 * 
	 * @return The DijkstraWengert Instance
	 */

	public static final DijkstraWengert Standard (
		final org.drip.spaces.graph.Topography topography,
		final java.lang.String source)
	{
		if (null == topography)
		{
			return null;
		}

		java.util.Set<java.lang.String> vertexNodeNameSet = topography.vertexNodeNameSet();

		if (!vertexNodeNameSet.contains (source))
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Boolean> vertexVisitationStatus = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Boolean>();

		for (java.lang.String vertexNodeName : vertexNodeNameSet)
		{
			vertexVisitationStatus.put (
				vertexNodeName,
				vertexNodeName.equalsIgnoreCase (source)
			);
		}

		java.util.Map<java.lang.String, java.lang.Double> connectionMap = topography.connectionMap();

		java.util.Map<java.lang.String, java.lang.Double> egressMap = topography.vertexNode
			(source).egressMap();

		org.drip.spaces.graph.VertexPeripheryMap vertexPeripheryMap = new
			org.drip.spaces.graph.VertexPeripheryMap();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> egressEntry : egressMap.entrySet())
		{
			java.lang.String egressNode = egressEntry.getKey();

			org.drip.spaces.graph.VertexPeriphery vertexPeriphery = null;
			java.lang.String sourceToEgressNodeKey = source + "_" + egressNode;

			try
			{
				vertexPeriphery = new org.drip.spaces.graph.VertexPeriphery (egressNode);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			vertexPeriphery.setPreceedingNode (source);

			vertexPeriphery.setWeightFromSource (connectionMap.get (sourceToEgressNodeKey));

			vertexPeripheryMap.addVertexPeriphery (vertexPeriphery);
		}

		for (java.lang.String vertexName : vertexNodeNameSet)
		{
			if (!vertexPeripheryMap.containsVertex (vertexName))
			{
				vertexPeripheryMap.addUnitializedVertexPeriphery (vertexName);
			}
		}

		try
		{
			return new DijkstraWengert (
				vertexVisitationStatus,
				connectionMap,
				vertexPeripheryMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * DijkstraWengert Constructor
	 * 
	 * @param vertexVisitationStatus The Vertex Visitation Status Map
	 * @param connectionMap The Inter-Nodal Connection Map
	 * @param vertexPeripheryMap The Vertex Periphery Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DijkstraWengert (
		final java.util.Map<java.lang.String, java.lang.Boolean> vertexVisitationStatus,
		final java.util.Map<java.lang.String, java.lang.Double> connectionMap,
		final org.drip.spaces.graph.VertexPeripheryMap vertexPeripheryMap)
		throws java.lang.Exception
	{
		if (null == (_vertexVisitationStatus = vertexVisitationStatus) || 0 == _vertexVisitationStatus.size()
			|| null == (_connectionMap = connectionMap) || 0 == _connectionMap.size() ||
			null == (_vertexPeripheryMap = vertexPeripheryMap))
		{
			throw new java.lang.Exception ("DijkstraWengert Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Vertex Visitation Status Map
	 * 
	 * @return The Vertex Visitation Status Map
	 */

	public java.util.Map<java.lang.String, java.lang.Boolean> vertexVisitationStatus()
	{
		return _vertexVisitationStatus;
	}

	/**
	 * Retrieve the Inter-Nodal Connection Map
	 * 
	 * @return The Inter-Nodal Connection Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> connectionMap()
	{
		return _connectionMap;
	}

	/**
	 * Retrieve the Vertex Periphery Map
	 * 
	 * @return The Vertex Periphery Map
	 */

	public org.drip.spaces.graph.VertexPeripheryMap vertexPeripheryMap()
	{
		return _vertexPeripheryMap;
	}

	/**
	 * Set the Vertex as Visited
	 * 
	 * @param vertexName The Vertex Node Name
	 * 
	 * @return TRUE - The Vertex Visitation Status successfully set
	 */

	public boolean setNodeAsVisited (
		final java.lang.String vertexName)
	{
		if (null == vertexName)
		{
			return false;
		}

		_vertexVisitationStatus.put (
			vertexName,
			true
		);

		return true;
	}

	/**
	 * Indicate if the Vertex has been Visited
	 * 
	 * @param vertexName The Vertex Node Name
	 * 
	 * @return TRUE - The Node has been Visited
	 */

	public boolean nodeVisited (
		final java.lang.String vertexName)
	{
		return null != vertexName && _vertexVisitationStatus.containsKey (vertexName) &&
			_vertexVisitationStatus.get (vertexName);
	}

	/**
	 * Indicate if all the Vertexes have been hit
	 * 
	 * @return TRUE - All the Vertexes have been hit
	 */

	public boolean navigationComplete()
	{
		for (java.util.Map.Entry<java.lang.String, java.lang.Boolean> vertexVisitationEntry :
			_vertexVisitationStatus.entrySet())
		{
			if (!vertexVisitationEntry.getValue())
			{
				return false;
			}
		}

		return true;
	}
}
