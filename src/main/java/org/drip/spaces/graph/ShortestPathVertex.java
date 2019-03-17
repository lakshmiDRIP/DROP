
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
 * <i>ShortestPathVertex</i> holds the given Vertex's Previous Traversal Vertex and the Weight from the
 * Source. The References are:
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

public class ShortestPathVertex
{
	private boolean _visited = false;
	private java.lang.String _current = "";
	private java.lang.String _preceeding = "";
	private double _weightFromSource = java.lang.Double.POSITIVE_INFINITY;

	/**
	 * ShortestPathVertex Constructor
	 * 
	 * @param current The Current Node
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ShortestPathVertex (
		final java.lang.String current)
		throws java.lang.Exception
	{
		if (null == (_current = current) || _current.isEmpty())
		{
			throw new java.lang.Exception ("ShortestPathVertex Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Current Vertex
	 * 
	 * @return The Current Vertex
	 */

	public java.lang.String current()
	{
		return _current;
	}

	/**
	 * Retrieve the Preceeding Traversal Vertex
	 * 
	 * @return The Preceeding Traversal Vertex
	 */

	public java.lang.String preceeding()
	{
		return _preceeding;
	}

	/**
	 * Set the Preceeding Traversal Vertex
	 * 
	 * @param preceeding The Preceeding Traversal Vertex
	 * 
	 * @return TRUE - The Preceeding Vertex successfully set
	 */

	public boolean setPreceeding (
		final java.lang.String preceeding)
	{
		if (null == preceeding || preceeding.isEmpty())
		{
			return false;
		}

		_preceeding = preceeding;
		return true;
	}

	/**
	 * Retrieve the Weight From the Source
	 * 
	 * @return The Weight From the Source
	 */

	public double weightFromSource()
	{
		return _weightFromSource;
	}

	/**
	 * Set the Weight From Source
	 * 
	 * @param weightFromSource The Weight From Source
	 * 
	 * @return TRUE - The Weight From Source successfully set
	 */

	public boolean setWeightFromSource (
		final double weightFromSource)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (weightFromSource))
		{
			return false;
		}

		_weightFromSource = weightFromSource;
		return true;
	}

	/**
	 * Indicate if the Vertex has been Visited
	 * 
	 * @return TRUE - The Vertex has been Visited
	 */
	
	public boolean visited()
	{
		return _visited;
	}

	/**
	 * Set the Visitation Status of the Vertex
	 * 
	 * @param visited The Visitation Status
	 * 
	 * @return TRUE - The Visitation Status successfully set
	 */

	public boolean setVisited (
		final boolean visited)
	{
		_visited = visited;
		return true;
	}
}
