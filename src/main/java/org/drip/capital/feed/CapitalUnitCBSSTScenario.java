
package org.drip.capital.feed;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnitCBSSTScenario</i> holds the CBSST Scenario Specifications of a Capital Unit. The References
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

public class CapitalUnitCBSSTScenario
{
	private java.lang.String _scenarioName = "";
	private java.lang.String _factorSensitivity = "";
	private org.drip.capital.shell.SystemicScenarioPnLSeries _scenarioPnL = null;

	/**
	 * CapitalUnitCBSSTScenario Constructor
	 * 
	 * @param factorSensitivity Factor Sensitivity
	 * @param scenarioName Scenario Name
	 * @param scenarioPnL Scenario PnL
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalUnitCBSSTScenario (
		final java.lang.String factorSensitivity,
		final java.lang.String scenarioName,
		final org.drip.capital.shell.SystemicScenarioPnLSeries scenarioPnL)
		throws java.lang.Exception
	{
		if (null == (_factorSensitivity = factorSensitivity) || _factorSensitivity.isEmpty() ||
			null == (_scenarioName = scenarioName) || _scenarioName.isEmpty() ||
			null == (_scenarioPnL = scenarioPnL))
		{
			throw new java.lang.Exception (
				"CapitalUnitCBSSTScenario Constructor => Invalid Inputs"
			);
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
	 * Retrieve the CBSST Scenario PnL
	 * 
	 * @return The CBSST Scenario PnL
	 */

	public org.drip.capital.shell.SystemicScenarioPnLSeries scenarioPnL()
	{
		return _scenarioPnL;
	}

	@Override public java.lang.String toString()
	{
		return "[" + _factorSensitivity + " | " +
			_scenarioName + " | {" +
			_scenarioPnL.toString() + "}]";
	}
}
