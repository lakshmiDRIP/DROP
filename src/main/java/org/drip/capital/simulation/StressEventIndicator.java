
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
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
