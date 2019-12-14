
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>Event</i> holds the Coordinate-Level Parameterization of a Stress Event. The References are:
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

public class Event
{
	private org.drip.capital.stress.PnLSeries _aggregatePnLSeries = null;
	private org.drip.capital.stress.EventSpecification _specification = null;
	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> _attachedEventPnLSeries =
		null;
	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> _pnlSeriesDecompositionMap =
		null;

	/**
	 * Event Constructor
	 * 
	 * @param specification Stress Event Specification
	 * @param aggregatePnLSeries Event Aggregate PnL Series
	 * @param pnlSeriesDecompositionMap PnL Series Decomposition Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Event (
		final org.drip.capital.stress.EventSpecification specification,
		final org.drip.capital.stress.PnLSeries aggregatePnLSeries,
		final java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> pnlSeriesDecompositionMap)
		throws java.lang.Exception
	{
		if (null == (_specification = specification) ||
			null == (_aggregatePnLSeries = aggregatePnLSeries))
		{
			throw new java.lang.Exception ("Event Constructor => Invalid inputs");
		}

		_pnlSeriesDecompositionMap = pnlSeriesDecompositionMap;
	}

	/**
	 * Retrieve the Stress Event Specification
	 * 
	 * @return Stress Event Specification
	 */

	public org.drip.capital.stress.EventSpecification specification()
	{
		return _specification;
	}

	/**
	 * Retrieve the Event Aggregate PnL Series
	 * 
	 * @return The Event Aggregate PnL Series
	 */

	public org.drip.capital.stress.PnLSeries aggregatePnLSeries()
	{
		return _aggregatePnLSeries;
	}

	/**
	 * Retrieve the Attached Event PnL Series Map
	 * 
	 * @return The Attached Event PnL Series Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> attachedEventPnLSeries()
	{
		return _attachedEventPnLSeries;
	}

	/**
	 * Retrieve the PnL Series Decomposition Map
	 * 
	 * @return The PnL Series Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> pnlSeriesDecompositionMap()
	{
		return _pnlSeriesDecompositionMap;
	}

	/**
	 * Attach the Specified Stress Event PnL
	 * 
	 * @param stressEventName The Stress Event Name
	 * @param stressEventPnL The Stress Event PnL
	 * 
	 * @return TRUE - The Stress Event PnL successfully attached
	 */

	public boolean attachStressEventPnL (
		final java.lang.String stressEventName,
		final org.drip.capital.stress.PnLSeries stressEventPnL)
	{
		if (null == stressEventName || stressEventName.isEmpty() ||
			null == stressEventPnL)
		{
			return false;
		}

		if (null == _attachedEventPnLSeries)
		{
			_attachedEventPnLSeries = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();
		}

		_attachedEventPnLSeries.put (
			stressEventName,
			stressEventPnL
		);

		return true;
	}

	/**
	 * Indicate if the Named Attached Event is available
	 * 
	 * @param attachedEventName The Attached Event Name
	 *
	 * @return TRUE - The Attached Event is available
	 */

	public boolean containsAttachedEvent (
		final java.lang.String attachedEventName)
	{
		return null != attachedEventName && !attachedEventName.isEmpty() &&
			_attachedEventPnLSeries.containsKey (attachedEventName);
	}

	/**
	 * Retrieve the Specified Attached Event PnL
	 * 
	 * @param attachedEventName Attached Event Name
	 * 
	 * @return Attached Event PnL
	 */

	public org.drip.capital.stress.PnLSeries attachedEventPnL (
		final java.lang.String attachedEventName)
	{
		return !containsAttachedEvent (attachedEventName) ? null : _attachedEventPnLSeries.get
			(attachedEventName);
	}

	/**
	 * Generate the PnL Decomposition Map
	 * 
	 * @param scaler PnL Scaler
	 * 
	 * @return PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> generatePnLDecompositionMap (
		final double scaler)
	{
		if (null == _pnlSeriesDecompositionMap ||
			!org.drip.numerical.common.NumberUtil.IsValid (scaler))
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> pnlDecompositionMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.stress.PnLSeries>
			pnlSeriesDecompositionEntry : _pnlSeriesDecompositionMap.entrySet())
		{
			pnlDecompositionMap.put (
				pnlSeriesDecompositionEntry.getKey(),
				scaler * pnlSeriesDecompositionEntry.getValue().composite()
			);
		}

		return pnlDecompositionMap;
	}

	/**
	 * Generate a Stress Event Incidence of a given Type
	 * 
	 * @param stressEventType The Stress Event Type
	 * 
	 * @return The Generated Stress Event Incidence
	 */

	public org.drip.capital.simulation.StressEventIncidence generateIncidence (
		final java.lang.String stressEventType)
	{
		try
		{
			return new org.drip.capital.simulation.StressEventIncidence (
				_specification.name(),
				stressEventType,
				_aggregatePnLSeries.composite(),
				generatePnLDecompositionMap (1.)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Stress Event Ensemble of a given Type
	 * 
	 * @param parentStressEventType The Parent Stress Event Type
	 * @param childStressEventType The Child Stress Event Type
	 * 
	 * @return The Generated Stress Event Ensemble
	 */

	public org.drip.capital.simulation.StressEventIncidenceEnsemble generateEnsemble (
		final java.lang.String parentStressEventType,
		final java.lang.String childStressEventType)
	{
		org.drip.capital.simulation.StressEventIncidenceEnsemble stressEventIncidenceEnsemble = new
			org.drip.capital.simulation.StressEventIncidenceEnsemble();

		try
		{
			if (!stressEventIncidenceEnsemble.addStressEventIncidence (
				new org.drip.capital.simulation.StressEventIncidence (
					_specification.name(),
					parentStressEventType,
					_aggregatePnLSeries.composite(),
					generatePnLDecompositionMap (1.)
				)
			))
			{
				return null;
			}

			if (null != _attachedEventPnLSeries && 0 != _attachedEventPnLSeries.size())
			{
				for (java.util.Map.Entry<java.lang.String, org.drip.capital.stress.PnLSeries>
					attachedEventPnLSeriesEntry : _attachedEventPnLSeries.entrySet())
				{
					if (!stressEventIncidenceEnsemble.addStressEventIncidence (
						new org.drip.capital.simulation.StressEventIncidence (
							attachedEventPnLSeriesEntry.getKey(),
							childStressEventType,
							attachedEventPnLSeriesEntry.getValue().composite(),
							null
						)
					))
					{
						return null;
					}
				}
			}

			return stressEventIncidenceEnsemble;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
