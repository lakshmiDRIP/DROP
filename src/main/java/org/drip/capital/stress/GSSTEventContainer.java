
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>GSSTEventContainer</i> contains the Scenario Stress Events' Specifications of the GSST Stress Scenario
 * Event Type that belong inside of a single Coordinate. The References are:
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

public class GSSTEventContainer
{
	private org.drip.capital.stress.EventProbabilityLadder _eventProbabilityLadder =
		new org.drip.capital.stress.EventProbabilityLadder();

	private java.util.Map<java.lang.String, org.drip.capital.stress.Event> _eventMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.Event>();

	/**
	 * Empty GSSTEventContainer Constructor
	 */

	public GSSTEventContainer()
	{
	}

	/**
	 * Retrieve the Stress Event Type
	 * 
	 * @return The Stress Event Type
	 */

	public java.lang.String eventType()
	{
		return org.drip.capital.definition.StressScenarioType.GSST;
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

		return _eventProbabilityLadder.addEvent (event);
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
		return null != eventName && !eventName.isEmpty() && _eventMap.containsKey (eventName);
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (stressPnLScaler))
		{
			return null;
		}

		java.lang.String gsstEventName = _eventProbabilityLadder.realizeEvent (randomEventIndicator);

		if (org.drip.capital.stress.EventProbabilityLadder.NO_REALIZATION.equalsIgnoreCase (gsstEventName))
		{
			return null;
		}

		org.drip.capital.simulation.StressEventIncidenceEnsemble stressEventIncidenceEnsemble = new
			org.drip.capital.simulation.StressEventIncidenceEnsemble();

		org.drip.capital.stress.Event event = _eventMap.get (gsstEventName);

		try
		{
			if (!stressEventIncidenceEnsemble.addStressEventIncidence (
				new org.drip.capital.simulation.StressEventIncidence (
					gsstEventName,
					org.drip.capital.definition.StressScenarioType.GSST,
					stressPnLScaler * event.aggregatePnLSeries().composite(),
					event.generatePnLDecompositionMap (stressPnLScaler)
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
						org.drip.capital.definition.StressScenarioType.CBSST,
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
