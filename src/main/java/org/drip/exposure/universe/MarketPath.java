
package org.drip.exposure.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 * <i>MarketPath</i> holds the Vertex Market Realizations at the Trajectory Vertexes along the Path of a
 * Simulation. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/README.md">Universe</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketPath
{
	private org.drip.analytics.date.JulianDate[] _vertexDateArray = null;
	private org.drip.exposure.universe.MarketVertex _epochalMarketVertex = null;
	private org.drip.exposure.universe.MarketVertex _terminalMarketVertex = null;
	private java.util.Map<java.lang.Integer, org.drip.exposure.universe.MarketVertex> _marketVertexTrajectory
		= null;

	/**
	 * Generate the Market Path from Market Vertex Array
	 * 
	 * @param marketVertexArray The Market Vertex Array
	 * 
	 * @return The Market Path
	 */

	public static final MarketPath FromMarketVertexArray (
		final org.drip.exposure.universe.MarketVertex[] marketVertexArray)
	{
		if (null == marketVertexArray)
		{
			return null;
		}

		int vertexCount = marketVertexArray.length;

		if (0 == vertexCount)
		{
			return null;
		}

		java.util.Map<java.lang.Integer, org.drip.exposure.universe.MarketVertex> marketVertexTrajectory =
			new java.util.TreeMap<java.lang.Integer, org.drip.exposure.universe.MarketVertex>();

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			int marketVertexDate = marketVertexArray[vertexIndex].anchorDate().julian();

			if (marketVertexTrajectory.containsKey (marketVertexDate))
			{
				return null;
			}

			marketVertexTrajectory.put (
				marketVertexDate,
				marketVertexArray[vertexIndex]
			);
		}

		try
		{
			return new MarketPath (marketVertexTrajectory);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarketPath Constructor
	 * 
	 * @param marketVertexTrajectory Date Map of the Market Vertexes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketPath (
		final java.util.Map<java.lang.Integer, org.drip.exposure.universe.MarketVertex> marketVertexTrajectory)
		throws java.lang.Exception
	{
		if (null == (_marketVertexTrajectory = marketVertexTrajectory))
		{
			throw new java.lang.Exception ("MarketPath Constructor => Invalid Inputs");
		}

		int vertexCount = _marketVertexTrajectory.size();

		if (0 == _marketVertexTrajectory.size())
		{
			throw new java.lang.Exception ("MarketPath Constructor => Invalid Inputs");
		}

		int vertexIndex = 0;
		_vertexDateArray = new org.drip.analytics.date.JulianDate[vertexCount];

		for (java.util.Map.Entry<java.lang.Integer, org.drip.exposure.universe.MarketVertex>
			marketVertexMapEntry : _marketVertexTrajectory.entrySet())
		{
			org.drip.exposure.universe.MarketVertex marketVertex = marketVertexMapEntry.getValue();

			if (0 == vertexIndex)
			{
				_epochalMarketVertex = marketVertex;
			}

			_vertexDateArray[vertexIndex++] = marketVertex.anchorDate();

			if (vertexCount == vertexIndex)
			{
				_terminalMarketVertex = marketVertex;
			}
		}
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] anchorDates()
	{
		return _vertexDateArray;
	}

	/**
	 * Retrieve the Trajectory of the Market Vertexes
	 * 
	 * @return The Market Vertex Trajectory
	 */

	public java.util.Map<java.lang.Integer, org.drip.exposure.universe.MarketVertex> trajectory()
	{
		return _marketVertexTrajectory;
	}

	/**
	 * Retrieve the Array of the Market Vertexes
	 * 
	 * @return The Market Vertex Array
	 */

	public org.drip.exposure.universe.MarketVertex[] marketVertexArray()
	{
		int vertexCount = _marketVertexTrajectory.size();

		int vertexIndex = 0;
		org.drip.exposure.universe.MarketVertex[] marketVertexArray = new
			org.drip.exposure.universe.MarketVertex[vertexCount];

		for (java.util.Map.Entry<java.lang.Integer, org.drip.exposure.universe.MarketVertex>
			marketVertexMapEntry : _marketVertexTrajectory.entrySet())
		{
			marketVertexArray[vertexIndex++] = marketVertexMapEntry.getValue();
		}

		return marketVertexArray;
	}

	/**
	 * Retrieve the Epochal Market Vertex
	 * 
	 * @return The Epochal Market Vertex
	 */

	public org.drip.exposure.universe.MarketVertex epochalMarketVertex()
	{
		return _epochalMarketVertex;
	}

	/**
	 * Retrieve the Terminal Market Vertex
	 * 
	 * @return The Terminal Market Vertex
	 */

	public org.drip.exposure.universe.MarketVertex terminalMarketVertex()
	{
		return _terminalMarketVertex;
	}

	/**
	 * Indicate if the Market Vertex is available for the Specified Date
	 * 
	 * @param vertexDate The Vertex Date
	 * 
	 * @return TRUE - The Market Vertex is available for the Specified Date
	 */

	public boolean containsDate (
		final int vertexDate)
	{
		return _marketVertexTrajectory.containsKey (vertexDate);
	}

	/**
	 * Retrieve the Market Vertex for the Specified Date
	 * 
	 * @param vertexDate The Vertex Date
	 * 
	 * @return The Market Vertex for the Specified Date
	 */

	public org.drip.exposure.universe.MarketVertex marketVertex (
		final int vertexDate)
	{
		return containsDate (vertexDate) ? _marketVertexTrajectory.get (vertexDate) : null;
	}
}
