
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
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
 * <i>StressEventIndicator</i> holds the Systemic and the Idiosyncratic Stress Event Indicators corresponding
 * to the specified Entity. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/README.md">Economic Risk Capital Simulation Ensemble</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StressEventIndicator
{
	private double _systemic = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _idiosyncraticMap = null;

	/**
	 * Construct the Instance of StressEventIndicator where the Systemic Indicator is Random
	 * 
	 * @param idiosyncraticEventSet The Set of Idiosyncratic Events
	 * 
	 * @return Instance of StressEventIndicator where the Systemic Indicator is Random
	 */

	public static final StressEventIndicator RandomSystemic (
		final java.util.Set<java.lang.String> idiosyncraticEventSet)
	{
		java.util.Map<java.lang.String, java.lang.Double> idiosyncraticMap = null;

		if (null != idiosyncraticEventSet && 0 != idiosyncraticEventSet.size())
		{
			idiosyncraticMap = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

			for (java.lang.String event : idiosyncraticEventSet)
			{
				idiosyncraticMap.put (
					event,
					java.lang.Math.random()
				);
			}
		}

		try
		{
			return new StressEventIndicator (
				java.lang.Math.random(),
				idiosyncraticMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Instance of StressEventIndicator where the Systemic Indicator is Custom
	 * 
	 * @param idiosyncraticEventSet The Set of Idiosyncratic Events
	 * @param systemicEventIndicator Systemic Event Indicator
	 * 
	 * @return Instance of StressEventIndicator where the Systemic Indicator is Custom
	 */

	public static final StressEventIndicator CustomSystemic (
		final java.util.Set<java.lang.String> idiosyncraticEventSet,
		final double systemicEventIndicator)
	{
		java.util.Map<java.lang.String, java.lang.Double> idiosyncraticMap = null;

		if (null != idiosyncraticEventSet && 0 != idiosyncraticEventSet.size())
		{
			idiosyncraticMap = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

			for (java.lang.String event : idiosyncraticEventSet)
			{
				idiosyncraticMap.put (
					event,
					java.lang.Math.random()
				);
			}
		}

		try
		{
			return new StressEventIndicator (
				systemicEventIndicator,
				idiosyncraticMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * StressEventIndicator Constructor
	 * 
	 * @param systemic Systemic Random Event Indicator
	 * @param idiosyncraticMap Idiosyncratic Random Event Indicator Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StressEventIndicator (
		final double systemic,
		final java.util.Map<java.lang.String, java.lang.Double> idiosyncraticMap)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_systemic = systemic) ||
			0. > _systemic || 1. < _systemic)
		{
			throw new java.lang.Exception ("StressEventIndicator Constructor => Invalid Inputs");
		}

		_idiosyncraticMap = idiosyncraticMap;
	}

	/**
	 * Retrieve the Systemic Random Event Indicator
	 * 
	 * @return The Systemic Random Event Indicator
	 */

	public double systemic()
	{
		return _systemic;
	}

	/**
	 * Retrieve the Idiosyncratic Random Event Indicator Map
	 * 
	 * @return The Idiosyncratic Random Event Indicator Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> idiosyncraticMap()
	{
		return _idiosyncraticMap;
	}

	/**
	 * Indicate if the Idiosyncratic Named Event contains a Random Entry
	 * 
	 * @param eventName The Idiosyncratic Event
	 * 
	 * @return TRUE - The Idiosyncratic Named Event contains a Random Entry
	 */

	public boolean containsIdiosyncratic (
		final java.lang.String eventName)
	{
		return null != eventName && !eventName.isEmpty() &&
			null != _idiosyncraticMap && _idiosyncraticMap.containsKey (eventName);
	}

	/**
	 * Retrieve the Entry corresponding to the Idiosyncratic Named Event
	 * 
	 * @param eventName The Idiosyncratic Event
	 * 
	 * @return The Entry corresponding to the Idiosyncratic Named Event
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double idiosyncratic (
		final java.lang.String eventName)
		throws java.lang.Exception
	{
		if (!containsIdiosyncratic (eventName))
		{
			throw new java.lang.Exception ("StressEventIndicator::idiosyncratic => Invalid Input");
		}

		return _idiosyncraticMap.get (eventName);
	}
}
