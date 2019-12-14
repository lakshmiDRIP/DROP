
package org.drip.capital.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>GSSTDesignContextManager</i> sets up the Credit Spread Event Container. The References are:
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

public class GSSTDesignContextManager
{
	private static org.drip.capital.shell.CreditSpreadEventContainer s_CreditSpreadEventContainer = null;

	/**
	 * Initialize the GSST Design Context Manager
	 * 
	 * @return TRUE - The GSST Design Context Manager successfully initialized
	 */

	public static final boolean Init()
	{
		s_CreditSpreadEventContainer = new org.drip.capital.shell.CreditSpreadEventContainer();

		if (!s_CreditSpreadEventContainer.add (
			org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
				"NOV-2008",
				 371.,
				 -39.5,
				-197.,
				 129.,
				   7.8,
				 -55.0,
				 -28.5,
				org.drip.capital.gsstdesign.SystemicStressShockIndicator.Deflationary()
			)
		))
		{
			return false;
		}

		try
		{
			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"APR-1932",
					332.,
					-61.4,
					 42.,
					 96.,
					java.lang.Double.NaN,
					 33.8,
					java.lang.Double.NaN,
					new org.drip.capital.gsstdesign.SystemicStressShockIndicator (
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UNSPECIFIED,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UNSPECIFIED
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"JAN-1975",
					 221.,
					 -29.7,
					 183.,
					 -53.,
					  -8.5,
					 159.0,
					  49.2,
					org.drip.capital.gsstdesign.SystemicStressShockIndicator.Inflationary()
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"APR-1938",
					180.,
					-40.5,
					-17.,
					 30.,
					java.lang.Double.NaN,
					 -4.2,
					java.lang.Double.NaN,
					new org.drip.capital.gsstdesign.SystemicStressShockIndicator (
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UNSPECIFIED
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"AUG-1982",
					 161.,
					  -2.7,
					-256.,
					 520.,
					  -0.8,
					  -6.0,
					  -7.8,
					org.drip.capital.gsstdesign.SystemicStressShockIndicator.Deflationary()
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"APR-1980",
					 147.,
					   4.5,
					 259.,
					-135.,
					  -1.0,
					 149.0,
					   4.1,
					org.drip.capital.gsstdesign.SystemicStressShockIndicator.Inflationary()
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"NOV-1970",
					 140.,
					  -7.0,
					-106.,
					 169.,
					java.lang.Double.NaN,
					  -1.0,
					   3.9,
					new org.drip.capital.gsstdesign.SystemicStressShockIndicator (
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"JAN-2001",
					 125.,
					  -2.0,
					-172.,
					-148.,
					   8.1,
					   9.0,
					   3.2,
					new org.drip.capital.gsstdesign.SystemicStressShockIndicator (
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UNSPECIFIED,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"APR-1931",
					 113.,
					 -39.4,
					 -11.,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					 -45.4,
					java.lang.Double.NaN,
					new org.drip.capital.gsstdesign.SystemicStressShockIndicator (
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UNSPECIFIED,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UNSPECIFIED
					)
				)
			))
			{
				return false;
			}

			if (!s_CreditSpreadEventContainer.add (
				org.drip.capital.gsstdesign.CreditSpreadEvent.Standard (
					"DEC-1966",
					  94.,
					 -13.1,
					  28.,
					 -36.,
					java.lang.Double.NaN,
					   2.0,
					java.lang.Double.NaN,
					new org.drip.capital.gsstdesign.SystemicStressShockIndicator (
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.DOWN,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UP,
						org.drip.capital.gsstdesign.SystemicStressShockIndicator.UNSPECIFIED
					)
				)
			))
			{
				return false;
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Built-in Credit Spread Event Container
	 * 
	 * @return The Built-in Credit Spread Event Container
	 */

	public static final org.drip.capital.shell.CreditSpreadEventContainer CreditSpreadEventContainer()
	{
		return s_CreditSpreadEventContainer;
	}
}
