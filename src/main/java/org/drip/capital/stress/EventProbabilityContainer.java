
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EventProbabilityContainer</i> contains the Map of the Named Stress Event Probabilities. The References
 * 	are:
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

public class EventProbabilityContainer
{
	private java.util.Map<java.lang.String, java.lang.Double> _map = null;

	/**
	 * Empty EventProbabilityContainer
	 */

	public EventProbabilityContainer()
	{
	}

	/**
	 * Add the Stress Event
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

		if (null == _map)
		{
			_map = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		org.drip.capital.stress.EventSpecification stressEventSpecification = event.specification();

		_map.put (
			stressEventSpecification.name(),
			stressEventSpecification.probability()
		);

		return true;
	}

	/**
	 * Realize the Event Set in accordance with the Random Event Indicator Map
	 * 
	 * @param eventIndicatorMap The Event Indicator Map
	 * 
	 * @return The Realized Event Set
	 */

	public java.util.Set<java.lang.String> realizeEventSet (
		final java.util.Map<java.lang.String, java.lang.Double> eventIndicatorMap)
	{
		if (null == eventIndicatorMap || 0 == eventIndicatorMap.size())
		{
			return null;
		}

		java.util.Set<java.lang.String> eventSet = new java.util.HashSet<java.lang.String>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> eventMapEntry : _map.entrySet())
		{
			java.lang.String eventName = eventMapEntry.getKey();

			if (eventIndicatorMap.containsKey (eventName) &&
				eventMapEntry.getValue() > eventIndicatorMap.get (eventName))
			{
				eventSet.add (eventName);
			}
		}

		return eventSet;
	}

	/**
	 * Retrieve the Probability Event Map
	 * 
	 * @return The Probability Event Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> map()
	{
		return _map;
	}
}
