
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EnsemblePnLDistribution</i> contains the PnL Distribution from Realized Path Ensemble. The References
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

public class EnsemblePnLDistribution
{
	private java.util.List<java.lang.Double> _grossPnLList = null;
	private java.util.List<java.lang.Double> _grossFSPnLList = null;
	private java.util.List<java.lang.Double> _grossSystemicStressPnLList = null;
	private java.util.List<java.lang.Double> _grossIdiosyncraticStressPnLList = null;

	/**
	 * EnsemblePnLDistribution Constructor
	 * 
	 * @param grossSystemicStressPnLList The Gross Systemic Stress PnL List
	 * @param grossIdiosyncraticStressPnLList The Gross Idiosyncratic Stress PnL List
	 * @param grossFSPnLList The Gross FS PnL List
	 * @param grossPnLList The Gross PnL List
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EnsemblePnLDistribution (
		final java.util.List<java.lang.Double> grossSystemicStressPnLList,
		final java.util.List<java.lang.Double> grossIdiosyncraticStressPnLList,
		final java.util.List<java.lang.Double> grossFSPnLList,
		final java.util.List<java.lang.Double> grossPnLList)
		throws java.lang.Exception
	{
		if (null == (_grossSystemicStressPnLList = grossSystemicStressPnLList) ||
			null == (_grossIdiosyncraticStressPnLList = grossIdiosyncraticStressPnLList) ||
			null == (_grossFSPnLList = grossFSPnLList) ||
			null == (_grossPnLList = grossPnLList))
		{
			throw new java.lang.Exception ("EnsemblePnLDistribution Constructor => Invalid Inputs");
		}

		int realizationCount = _grossSystemicStressPnLList.size();

		if (0 == realizationCount ||
			realizationCount != _grossIdiosyncraticStressPnLList.size() ||
			realizationCount != _grossFSPnLList.size() ||
			realizationCount != _grossPnLList.size())
		{
			throw new java.lang.Exception ("EnsemblePnLDistribution Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Gross Systemic Stress PnL List
	 * 
	 * @return The Gross Systemic Stress PnL List
	 */

	public java.util.List<java.lang.Double> grossSystemicStressPnLList()
	{
		return _grossSystemicStressPnLList;
	}

	/**
	 * Retrieve the Gross Idiosyncratic Stress PnL List
	 * 
	 * @return The Gross Idiosyncratic Stress PnL List
	 */

	public java.util.List<java.lang.Double> grossIdiosyncraticStressPnLList()
	{
		return _grossIdiosyncraticStressPnLList;
	}

	/**
	 * Retrieve the Gross FS PnL List
	 * 
	 * @return The Gross FS PnL List
	 */

	public java.util.List<java.lang.Double> grossFSPnLList()
	{
		return _grossFSPnLList;
	}

	/**
	 * Retrieve the Gross PnL List
	 * 
	 * @return The Gross PnL List
	 */

	public java.util.List<java.lang.Double> grossPnLList()
	{
		return _grossPnLList;
	}
}
