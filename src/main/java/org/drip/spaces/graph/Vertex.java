
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
 * Vertex implements a Single Vertex Node and the corresponding Egresses emanating from it. The References
 *  are:
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

public class Vertex
{
	private java.lang.String _name = "";

	private java.util.Map<java.lang.String, java.lang.Double> _egressMap = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * Vertex Constructor
	 * 
	 * @param name The Name of the Vertex Node
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Vertex (
		final java.lang.String name)
		throws java.lang.Exception
	{
		if (null == (_name = name) || _name.isEmpty())
		{
			throw new java.lang.Exception ("Vertex Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Name of the Vertex Node
	 * 
	 * @return The Name of the Vertex Node
	 */

	public java.lang.String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Egress Map
	 * 
	 * @return The Egress Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> egressMap()
	{
		return _egressMap;
	}

	/**
	 * Add an Egress to the Vertex Node
	 * 
	 * @param destinationName Name of the Destination Vertex Node
	 * @param weight Weight of the Egress Path
	 * 
	 * @return TRUE - The Egress successfully added to the Vertex Node
	 */

	public boolean addEgress (
		final java.lang.String destinationName,
		final double weight)
	{
		if (null == destinationName || destinationName.equalsIgnoreCase (_name) ||
			!org.drip.quant.common.NumberUtil.IsValid (weight))
		{
			return false;
		}

		_egressMap.put (
			destinationName,
			weight
		);

		return true;
	}
}
