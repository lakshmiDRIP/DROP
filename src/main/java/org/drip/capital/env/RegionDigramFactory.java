
package org.drip.capital.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>RegionDigramFactory</i> instantiates the Built-in Region Digram Mapping. The References are:
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

public class RegionDigramFactory
{

	/**
	 * Instantiate the Built-in RegionDigramContext
	 * 
	 * @return TRUE - The RegionDigramContext Instance
	 */

	public static org.drip.capital.shell.RegionDigramContext Instantiate()
	{
		java.util.Map<java.lang.String, java.lang.String> regionDigramMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

		regionDigramMap.put (
			"AP",
			org.drip.capital.definition.Region.ASIA
		);

		regionDigramMap.put (
			"AU",
			org.drip.capital.definition.Region.ASIA
		);

		regionDigramMap.put (
			"CE",
			org.drip.capital.definition.Region.EMEA
		);

		regionDigramMap.put (
			"EU",
			org.drip.capital.definition.Region.EMEA
		);

		regionDigramMap.put (
			"JP",
			org.drip.capital.definition.Region.ASIA
		);

		regionDigramMap.put (
			"LA",
			org.drip.capital.definition.Region.LATIN_AMERICA
		);

		regionDigramMap.put (
			"MX",
			org.drip.capital.definition.Region.LATIN_AMERICA
		);

		regionDigramMap.put (
			"NA",
			org.drip.capital.definition.Region.NORTH_AMERICA
		);

		try
		{
			return new org.drip.capital.shell.RegionDigramContext (regionDigramMap);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
