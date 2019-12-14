
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CreditSpreadEventContainer</i> maintains all the Credit Spread Events needed for a Full GSST Scenario
 * 	Design Run. The References are:
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

public class CreditSpreadEventContainer
{
	private int _count = 0;

	private java.util.Map<java.lang.Integer, org.drip.capital.gsstdesign.CreditSpreadEvent>
		_creditSpreadEventMap =
			new java.util.TreeMap<java.lang.Integer, org.drip.capital.gsstdesign.CreditSpreadEvent>();

	/**
	 * Empty CreditSpreadEventContainer Constructor
	 */

	public CreditSpreadEventContainer()
	{
	}

	/**
	 * Retrieve the Credit Spread Event Map
	 * 
	 * @return Credit Spread Event Map
	 */

	public java.util.Map<java.lang.Integer, org.drip.capital.gsstdesign.CreditSpreadEvent>
		creditSpreadEventMap()
	{
		return _creditSpreadEventMap;
	}

	/**
	 * Retrieve the Count of the Credit Spread Events
	 * 
	 * @return Count of the Credit Spread Events
	 */

	public int count()
	{
		return _count;
	}

	/**
	 * Add the Specified Credit Spread Event
	 * 
	 * @param creditSpreadEvent The Credit Spread Event
	 * 
	 * @return TRUE - The Credit Spread Event successfully added
	 */

	public boolean add (
		final org.drip.capital.gsstdesign.CreditSpreadEvent creditSpreadEvent)
	{
		if (null == creditSpreadEvent)
		{
			return false;
		}

		_creditSpreadEventMap.put (
			++_count,
			creditSpreadEvent
		);

		return true;
	}
}
