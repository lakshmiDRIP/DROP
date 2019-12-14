
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EventProbabilityLadder</i> contains the Probabilities and their corresponding Event Steps in a Ladder
 * Progression. The References are:
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

public class EventProbabilityLadder
{

	/**
	 * Designation for "No Realization"
	 */

	public static final java.lang.String NO_REALIZATION = "";

	private double _highWaterMark = 0.;
	private java.util.Map<java.lang.Double, java.lang.String> _stepMap = null;

	/**
	 * Empty EventProbabilityLadder Constructor
	 */

	public EventProbabilityLadder()
	{
	}

	/**
	 * Retrieve the Probability Event Step Map
	 * 
	 * @return The Probability Event Step Map
	 */

	public java.util.Map<java.lang.Double, java.lang.String> stepMap()
	{
		return _stepMap;
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

		if (null == _stepMap)
		{
			_stepMap = new java.util.TreeMap<java.lang.Double, java.lang.String>();
		}

		if (1. < (_highWaterMark = _highWaterMark + event.specification().probability()))
		{
			return false;
		}

		_stepMap.put (
			_highWaterMark,
			event.specification().name()
		);

		return true;
	}

	/**
	 * Realize the Event in accordance with the Indicator
	 * 
	 * @param eventIndicator The Event Indicator
	 * 
	 * @return The Realized Event
	 */

	public java.lang.String realizeEvent (
		final double eventIndicator)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (eventIndicator))
		{
			return NO_REALIZATION;
		}

		for (java.util.Map.Entry<java.lang.Double, java.lang.String> eventStepEntry : _stepMap.entrySet())
		{
			if (eventStepEntry.getKey() > eventIndicator)
			{
				return eventStepEntry.getValue();
			}
		}

		return NO_REALIZATION;
	}
}
