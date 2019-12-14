
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>RegionDigramContext</i> maintains the Loaded Region Digram Mapping. The References
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

public class RegionDigramContext
{
	private java.util.Map<java.lang.String, java.lang.String> _regionDigramMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

	/**
	 * RegionDigramContext Constructor
	 * 
	 * @param regionDigramMap Region Digram Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RegionDigramContext (
		final java.util.Map<java.lang.String, java.lang.String> regionDigramMap)
		throws java.lang.Exception
	{
		if (null == (_regionDigramMap = regionDigramMap) || 0 == _regionDigramMap.size())
		{
			throw new java.lang.Exception ("RegionDigramContext Constructor => Invalid Inputs");
		}
	}

	/**
	 * Check for the Existence of the Region Digram
	 * 
	 * @param digram Region Digram
	 * 
	 * @return TRUE - The Region Digram exists
	 */

	public boolean containsDigram (
		final java.lang.String digram)
	{
		return null != digram && !digram.isEmpty() && _regionDigramMap.containsKey (digram);
	}

	/**
	 * Retrieve the Region corresponding to the Digram
	 * 
	 * @param digram The Digram
	 * 
	 * @return Region corresponding to the Digram
	 */

	public java.lang.String region (
		final java.lang.String digram)
	{
		return containsDigram (digram) ? _regionDigramMap.get (digram) : "";
	}

	/**
	 * Retrieve the Region Digram Map
	 * 
	 * @return The Region Digram Map
	 */

	public java.util.Map<java.lang.String, java.lang.String> regionDigramMap()
	{
		return _regionDigramMap;
	}
}
