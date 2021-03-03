
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>SystemicEventContainer</i> contains the Scenario Stress Events' Specifications of the Systemic Stress
 * 	Scenario Event Type that belong inside of a single Coordinate. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/stress/README.md">Economic Risk Capital Stress Event Settings</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SystemicEventContainer
{
	private org.drip.capital.stress.EventProbabilityLadder _eventProbabilityLadder =
		new org.drip.capital.stress.EventProbabilityLadder();

	private java.util.Map<java.lang.String, org.drip.capital.stress.Event> _eventMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.Event>();

	/**
	 * Empty SystemicEventContainer Constructor
	 */

	public SystemicEventContainer()
	{
	}

	/**
	 * Retrieve the Stress Event Type
	 * 
	 * @return The Stress Event Type
	 */

	public java.lang.String eventType()
	{
		return org.drip.capital.definition.StressScenarioType.SYSTEMIC;
	}

	/**
	 * Add the Specified Stress Event
	 * 
	 * @param event The Stress Event
	 * 
	 * @return TRUE - The Stress Event successfully added
	 */

	public boolean addEvent (
		final org.drip.capital.stress.Event event)
	{
		if (null == event)
		{
			return false;
		}

		_eventMap.put (
			event.specification().name(),
			event
		);

		return _eventProbabilityLadder.addEvent (
			event
		);
	}

	/**
	 * Check if the Stress Event Exists
	 * 
	 * @param eventName The Stress Event Name
	 * 
	 * @return TRUE - The Stress Event exists
	 */

	public boolean containsEvent (
		final java.lang.String eventName)
	{
		return null != eventName && !eventName.isEmpty() && _eventMap.containsKey (
			eventName
		);
	}

	/**
	 * Retrieve the Stress Event
	 * 
	 * @param eventName The Stress Event Name
	 * 
	 * @return The Stress Event
	 */

	public org.drip.capital.stress.Event event (
		final java.lang.String eventName)
	{
		return containsEvent (
			eventName
		) ? _eventMap.get (
			eventName
		) : null;
	}

	/**
	 * Retrieve the Stress Event Set
	 * 
	 * @return The Stress Event Set
	 */

	public java.util.Set<java.lang.String> eventSet()
	{
		return _eventMap.keySet();
	}

	/**
	 * Realize a Stress Event Incidence Ensemble
	 * 
	 * @param stressPnLScaler The Stress PnL Scaler
	 * @param randomEventIndicator Random Stress Event Indicator
	 * 
	 * @return Realized Stress Event Incidence Ensemble
	 */

	public org.drip.capital.simulation.StressEventIncidenceEnsemble realizeIncidenceEnsemble (
		final double stressPnLScaler,
		final double randomEventIndicator)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			stressPnLScaler
		))
		{
			return null;
		}

		java.lang.String gsstEventName = _eventProbabilityLadder.realizeEvent (
			randomEventIndicator
		);

		if (org.drip.capital.stress.EventProbabilityLadder.NO_REALIZATION.equalsIgnoreCase (
			gsstEventName
		))
		{
			return null;
		}

		org.drip.capital.simulation.StressEventIncidenceEnsemble stressEventIncidenceEnsemble = new
			org.drip.capital.simulation.StressEventIncidenceEnsemble();

		org.drip.capital.stress.Event event = _eventMap.get (
			gsstEventName
		);

		try
		{
			if (!stressEventIncidenceEnsemble.addStressEventIncidence (
				new org.drip.capital.simulation.StressEventIncidence (
					gsstEventName,
					org.drip.capital.definition.StressScenarioType.SYSTEMIC,
					stressPnLScaler * event.aggregatePnLSeries().composite(),
					event.generatePnLDecompositionMap (
						stressPnLScaler
					)
				)
			))
			{
				return null;
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> attachedEventPnLMap =
			event.attachedEventPnLSeries();

		if (null == attachedEventPnLMap || 0 == attachedEventPnLMap.size())
		{
			return stressEventIncidenceEnsemble;
		}

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.stress.PnLSeries> attachedEventPnLEntry
			: attachedEventPnLMap.entrySet())
		{
			try
			{
				if (!stressEventIncidenceEnsemble.addStressEventIncidence (
					new org.drip.capital.simulation.StressEventIncidence (
						gsstEventName + "::" + attachedEventPnLEntry.getKey(),
						org.drip.capital.definition.StressScenarioType.CORRELATED,
						stressPnLScaler * attachedEventPnLEntry.getValue().composite(),
						null
					)
				))
				{
					return null;
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return stressEventIncidenceEnsemble;
	}

	/**
	 * Retrieve the Stress Event Map
	 * 
	 * @return The Stress Event Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.Event> eventMap()
	{
		return _eventMap;
	}

	/**
	 * Retrieve the Scenario Probability Event Ladder
	 * 
	 * @return The Scenario Probability Event Ladder
	 */

	public org.drip.capital.stress.EventProbabilityLadder eventProbabilityLadder()
	{
		return _eventProbabilityLadder;
	}
}
