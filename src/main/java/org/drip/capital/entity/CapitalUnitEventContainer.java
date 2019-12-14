
package org.drip.capital.entity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnitEventContainer</i> contains all the Stress Event Specifications across all of the Event Types that
 * belong inside of the a Capital Unit. The References
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

public class CapitalUnitEventContainer
{
	private org.drip.capital.stress.GSSTEventContainer _gsstEventContainer = null;
	private org.drip.capital.stress.IBSSTEventContainer _iBSSTEventContainer = null;

	/**
	 * Empty CapitalUnitEventContainer Constructor
	 */

	public CapitalUnitEventContainer()
	{
	}

	/**
	 * Add GSST Event
	 * 
	 * @param gsstEvent The GSST Event
	 * 
	 * @return TRUE - The GSST Event successfully added
	 */

	public boolean addGSSTEvent (
		final org.drip.capital.stress.Event gsstEvent)
	{
		if (null == _gsstEventContainer)
		{
			_gsstEventContainer = new org.drip.capital.stress.GSSTEventContainer();
		}

		return _gsstEventContainer.addEvent (gsstEvent);
	}

	/**
	 * Indicate if the GSST Event is Available
	 * 
	 * @param gsstEventName GSST Event Name
	 * 
	 * @return TRUE - The GSST Event is Available
	 */

	public boolean containsGSSTEvent (
		final java.lang.String gsstEventName)
	{
		return null == _gsstEventContainer ? false : _gsstEventContainer.containsEvent (gsstEventName);
	}

	/**
	 * Retrieve the GSST Event by Name
	 * 
	 * @param gsstEventName GSST Event by Name
	 * 
	 * @return The GSST Event
	 */

	public org.drip.capital.stress.Event gsstEvent (
		final java.lang.String gsstEventName)
	{
		return null == _gsstEventContainer ? null : _gsstEventContainer.event (gsstEventName);
	}

	/**
	 * Add iBSST Event
	 * 
	 * @param iBSSTEvent The iBSST Event
	 * 
	 * @return TRUE - The iBSST Event successfully added
	 */

	public boolean addIBSSTEvent (
		final org.drip.capital.stress.Event iBSSTEvent)
	{
		if (null == _iBSSTEventContainer)
		{
			_iBSSTEventContainer = new org.drip.capital.stress.IBSSTEventContainer();
		}

		return _iBSSTEventContainer.addEvent (iBSSTEvent);
	}

	/**
	 * Indicate if the iBSST Event is Available
	 * 
	 * @param iBSSTEventName iBSST Event Name
	 * 
	 * @return TRUE - The iBSST Event is Available
	 */

	public boolean containsIBSSTEvent (
		final java.lang.String iBSSTEventName)
	{
		return null == _iBSSTEventContainer ? false : _iBSSTEventContainer.containsEvent (iBSSTEventName);
	}

	/**
	 * Retrieve the iBSST Event by Name
	 * 
	 * @param iBSSTEventName iBSST Event by Name
	 * 
	 * @return The iBSST Event
	 */

	public org.drip.capital.stress.Event iBSSTEvent (
		final java.lang.String iBSSTEventName)
	{
		return null == _iBSSTEventContainer ? null : _iBSSTEventContainer.event (iBSSTEventName);
	}

	/**
	 * Retrieve the GSST Event Container
	 * 
	 * @return The GSST Event Container
	 */

	public org.drip.capital.stress.GSSTEventContainer gsstEventContainer()
	{
		return _gsstEventContainer;
	}

	/**
	 * Retrieve the iBSST Event Container
	 * 
	 * @return The iBSST Event Container
	 */

	public org.drip.capital.stress.IBSSTEventContainer iBSSTEventContainer()
	{
		return _iBSSTEventContainer;
	}

	/**
	 * Add cBSST Stress Event PnL Series
	 * 
	 * @param gsstEventName GSST Stress Event Name
	 * @param cbsstEventName cBSST Stress Event Name
	 * @param cbsstEventPnLSeries cBSST Stress Event PnL Series
	 * 
	 * @return TRUE - The cBSST Stress Event PnL Series successfully added
	 */

	public boolean addCBSSTEvent (
		final java.lang.String gsstEventName,
		final java.lang.String cbsstEventName,
		final org.drip.capital.stress.PnLSeries cbsstEventPnLSeries)
	{
		if (null == _gsstEventContainer)
		{
			return false;
		}

		org.drip.capital.stress.Event gsstEvent = _gsstEventContainer.event (gsstEventName);

		if (null == gsstEvent)
		{
			return false;
		}

		return gsstEvent.attachStressEventPnL (
			cbsstEventName,
			cbsstEventPnLSeries
		);
	}

	/**
	 * Indicate if the cBSST Event is Available
	 * 
	 * @param gsstEventName GSST Stress Event Name
	 * @param cBSSTEventName cBSST Stress Event Name
	 * 
	 * @return TRUE - The cBSST Event is Available
	 */

	public boolean containsCBSSTEvent (
		final java.lang.String gsstEventName,
		final java.lang.String cBSSTEventName)
	{
		if (null == _gsstEventContainer)
		{
			return false;
		}

		return !_gsstEventContainer.containsEvent (gsstEventName) ? false :
			_gsstEventContainer.event (gsstEventName).containsAttachedEvent (cBSSTEventName);
	}

	/**
	 * Retrieve the cBSST Stress Event PnL
	 * 
	 * @param gsstEventName GSST Stress Event Name
	 * @param cBSSTEventName cBSST Stress Event Name
	 * 
	 * @return TRUE - The cBSST Stress Event PnL
	 */

	public org.drip.capital.stress.PnLSeries cBSSTEvent (
		final java.lang.String gsstEventName,
		final java.lang.String cBSSTEventName)
	{
		return !_gsstEventContainer.containsEvent (gsstEventName) ? null :
			_gsstEventContainer.event (gsstEventName).attachedEventPnL (cBSSTEventName);
	}
}
