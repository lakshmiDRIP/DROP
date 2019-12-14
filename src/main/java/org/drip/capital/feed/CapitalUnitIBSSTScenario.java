
package org.drip.capital.feed;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnitIBSSTScenario</i> holds the iBSST Scenario Specifications of a Capital Unit. The References
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

public class CapitalUnitIBSSTScenario
{
	private double _pnl = java.lang.Double.NaN;
	private java.lang.String _scenarioName = "";
	private java.lang.String _factorSensitivity = "";
	private double _probability = java.lang.Double.NaN;

	/**
	 * CapitalUnitIBSSTScenario Constructor
	 * 
	 * @param factorSensitivity Factor Sensitivity
	 * @param scenarioName Scenario Name
	 * @param pnl Scenario PnL
	 * @param probability Scenario Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalUnitIBSSTScenario (
		final java.lang.String factorSensitivity,
		final java.lang.String scenarioName,
		final double pnl,
		final double probability)
		throws java.lang.Exception
	{
		if (null == (_factorSensitivity = factorSensitivity) || _factorSensitivity.isEmpty() ||
			null == (_scenarioName = scenarioName) || _scenarioName.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (_pnl = pnl) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_probability = probability))
		{
			throw new java.lang.Exception ("CapitalUnitIBSSTScenario Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Factor Sensitivity
	 * 
	 * @return The Factor Sensitivity
	 */

	public java.lang.String factorSensitivity()
	{
		return _factorSensitivity;
	}

	/**
	 * Retrieve the Scenario Name
	 * 
	 * @return The Scenario Name
	 */

	public java.lang.String scenarioName()
	{
		return _scenarioName;
	}

	/**
	 * Retrieve the Scenario PnL
	 * 
	 * @return The Scenario PnL
	 */

	public double pnl()
	{
		return _pnl;
	}

	/**
	 * Retrieve the Scenario Probability
	 * 
	 * @return The Scenario Probability
	 */

	public double probability()
	{
		return _probability;
	}

	@Override public java.lang.String toString()
	{
		return "[" + _factorSensitivity + " | " +
			_scenarioName + " | " +
			_pnl + " | " +
			_probability + "]";
	}
}
