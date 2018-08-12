
package org.drip.sample.graph;

import org.drip.service.env.EnvManager;
import org.drip.spaces.graph.DijkstraScheme;
import org.drip.spaces.graph.Topography;

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
 * Dijkstra illustrates the Execution of the Dijkstra Algorithm. The References are:
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

public class Dijkstra
{

	private static final Topography SetTopography()
		throws Exception
	{
		String[] vertexNodeArray = new String[]
		{
			// "Delhi     ",
			// "Bombay    ",
			"Madras    ",
			"Calcutta  ",
			"Bangalore ",
			// "Hyderabad ",
			// "Cochin    ",
			// "Pune      ",
			// "Ahmedabad ",
			// "Jaipur    "
		};

		Topography topography = new Topography();

		for (String vertexNodeName : vertexNodeArray)
		{
			topography.addVertexNode (vertexNodeName);
		}

		/* topography.addPath (
			vertexNodeArray[0], // Delhi
			vertexNodeArray[1], // Bombay
			1388.
		);

		topography.addPath (
			vertexNodeArray[0], // Delhi
			vertexNodeArray[2], // Madras
			2191.
		);

		topography.addPath (
			vertexNodeArray[1], // Bombay
			vertexNodeArray[2], // Madras
			1279.
		);

		topography.addPath (
			vertexNodeArray[0], // Delhi
			vertexNodeArray[3], // Calcutta
			1341.
		);

		topography.addPath (
			vertexNodeArray[1], // Bombay
			vertexNodeArray[3], // Calcutta
			1968.
		); */

		topography.addPath (
			vertexNodeArray[0], // Madras
			vertexNodeArray[1], // Calcutta
			// vertexNodeArray[2], // Madras
			// vertexNodeArray[3], // Calcutta
			1663.
		);

		topography.addPath (
			vertexNodeArray[0], // Madras
			vertexNodeArray[2], // Bangalore
			// vertexNodeArray[2], // Madras
			// vertexNodeArray[4], // Bangalore
			361.
		);

		/* topography.addPath (
			vertexNodeArray[2], // Madras
			vertexNodeArray[5], // Hyderabad
			784.
		);

		topography.addPath (
			vertexNodeArray[2], // Madras
			vertexNodeArray[6], // Cochin
			697.
		);

		topography.addPath (
			vertexNodeArray[1], // Bombay
			vertexNodeArray[7], // Pune
			192.
		);

		topography.addPath (
			vertexNodeArray[1], // Bombay
			vertexNodeArray[8], // Ahmedabad
			492.
		);

		topography.addPath (
			vertexNodeArray[0], // Delhi
			vertexNodeArray[9], // Jaipur
			308.
		); */

		return topography;
	}

	public static void main (
		final String[] inputArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		Topography topography = SetTopography();

		String source = "Bangalore ";
		String destination = "Calcutta  ";

		DijkstraScheme dijkstraScheme = new DijkstraScheme (topography);

		System.out.println (
			dijkstraScheme.spf (
				source,
				destination
			)
		);

		EnvManager.TerminateEnv();
	}
}
