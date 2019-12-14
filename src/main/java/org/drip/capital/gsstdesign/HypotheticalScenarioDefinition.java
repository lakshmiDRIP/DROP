
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>HypotheticalScenarioDefinition</i> holds the Realizations of the Hypothetical Stress Scenarios. The
 *	References are:
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

public class HypotheticalScenarioDefinition
{
	private double _lostDecade = java.lang.Double.NaN;
	private double _deepDownturn = java.lang.Double.NaN;
	private double _dollarDecline = java.lang.Double.NaN;
	private double _interestRateShock = java.lang.Double.NaN;

	/**
	 * HypotheticalScenarioDefinition Constructor
	 * 
	 * @param dollarDecline Dollar Decline Scenario Realization
	 * @param lostDecade Lost Decade Scenario Realization
	 * @param interestRateShock Interest Rate Shock Scenario Realization
	 * @param deepDownturn Deep Down-turn Scenario Realization
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HypotheticalScenarioDefinition (
		final double dollarDecline,
		final double lostDecade,
		final double interestRateShock,
		final double deepDownturn)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dollarDecline = dollarDecline) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lostDecade = lostDecade) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_interestRateShock = interestRateShock) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_deepDownturn = deepDownturn))
		{
			throw new java.lang.Exception ("HypotheticalScenarioDefinition Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Dollar Decline Scenario Realization
	 * 
	 * @return The Dollar Decline Scenario Realization
	 */

	public double dollarDecline()
	{
		return _dollarDecline;
	}

	/**
	 * Retrieve the Lost Decade Scenario Realization
	 * 
	 * @return The Lost Decade Scenario Realization
	 */

	public double lostDecade()
	{
		return _lostDecade;
	}

	/**
	 * Retrieve the Interest Rate Shock Scenario Realization
	 * 
	 * @return The Interest Rate Shock Scenario Realization
	 */

	public double interestRateShock()
	{
		return _interestRateShock;
	}

	/**
	 * Retrieve the Deep Down-turn Scenario Realization
	 * 
	 * @return The Deep Down-turn Scenario Realization
	 */

	public double deepDownturn()
	{
		return _deepDownturn;
	}
}
