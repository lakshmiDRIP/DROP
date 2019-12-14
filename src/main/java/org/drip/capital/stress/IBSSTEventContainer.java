
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>IBSSTEventContainer</i> contains the Scenario Stress Events' Specifications of the iBSST Stress
 * Scenario Event Type that belong inside of a single Coordinate. The References are:
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
 * @author Lakshmi Krishnamurthy
 */

public class IBSSTEventContainer
{
	private org.drip.capital.stress.EventProbabilityContainer _eventProbabilityContainer =
		new org.drip.capital.stress.EventProbabilityContainer();

	private java.util.Map<java.lang.String, org.drip.capital.stress.Event> _eventMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.Event>();

	/**
	 * Empty IBSSTEventContainer Constructor
	 */

	public IBSSTEventContainer()
	{
	}

	/**
	 * Retrieve the Stress Event Type
	 * 
	 * @return The Stress Event Type
	 */

	public java.lang.String eventType()
	{
		return org.drip.capital.definition.StressScenarioType.IBSST;
	}

	/**
	 * Add the Specified Stress Event Specification
	 * 
	 * @param event The Stress Event Specification
	 * 
	 * @return TRUE - The Stress Event Specification successfully added
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

		return _eventProbabilityContainer.addEvent (event);
	}

	/**
	 * Check if the Stress Event Exists
	 * 
	 * @param stressEventName The Stress Event Name
	 * 
	 * @return TRUE - The Stress Event exists
	 */

	public boolean containsEvent (
		final java.lang.String stressEventName)
	{
		return null != stressEventName && !stressEventName.isEmpty() &&
			_eventMap.containsKey (stressEventName);
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
		return containsEvent (eventName) ? _eventMap.get (eventName) : null;
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
	 * Realize the Event Set in accordance with the Event Indicator Map
	 * 
	 * @param stressPnLScaler The Stress PnL Scaler
	 * @param eventIndicatorMap The Event Indicator Map
	 * 
	 * @return The Realized Event Set
	 */

	public org.drip.capital.simulation.StressEventIncidenceEnsemble realizeIncidenceEnsemble (
		final double stressPnLScaler,
		final java.util.Map<java.lang.String, java.lang.Double> eventIndicatorMap)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (stressPnLScaler))
		{
			return null;
		}

		java.util.Set<java.lang.String> eventSet = _eventProbabilityContainer.realizeEventSet
			(eventIndicatorMap);

		if (null == eventSet || 0 == eventSet.size())
		{
			return null;
		}

		org.drip.capital.simulation.StressEventIncidenceEnsemble stressEventIncidenceEnsemble = new
			org.drip.capital.simulation.StressEventIncidenceEnsemble();

		for (java.lang.String event : eventSet)
		{
			if (!_eventMap.containsKey (event))
			{
				continue;
			}

			try
			{
				if (!stressEventIncidenceEnsemble.addStressEventIncidence (
					new org.drip.capital.simulation.StressEventIncidence (
						event,
						org.drip.capital.definition.StressScenarioType.IBSST,
						stressPnLScaler * _eventMap.get (event).aggregatePnLSeries().composite(),
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
	 * Retrieve the Stress Event Specification Map
	 * 
	 * @return The Stress Event Specification Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.Event> eventMap()
	{
		return _eventMap;
	}

	/**
	 * Retrieve the Scenario Probability Event Container
	 * 
	 * @return The Scenario Probability Event Container
	 */

	public org.drip.capital.stress.EventProbabilityContainer eventProbabilityContainer()
	{
		return _eventProbabilityContainer;
	}
}
