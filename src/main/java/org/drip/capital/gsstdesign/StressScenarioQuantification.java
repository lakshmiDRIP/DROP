
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>StressScenarioQuantification</i> specifies the Unit and the Type of Change for the given Market
 *	Factor/Applicability Combination. The References are:
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

public class StressScenarioQuantification
{
	private java.lang.String _typeOfChange = "";
	private int _unit = java.lang.Integer.MIN_VALUE;

	/**
	 * StressScenarioQuantification Constructor
	 * 
	 * @param typeOfChange Type of Change
	 * @param unit Unit of Change
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StressScenarioQuantification (
		final java.lang.String typeOfChange,
		final int unit)
		throws java.lang.Exception
	{
		if (null == (_typeOfChange = typeOfChange) || _typeOfChange.isEmpty() ||
			-1 >= (_unit = unit))
		{
			throw new java.lang.Exception ("StressScenarioQuantification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Unit of Change
	 * 
	 * @return The Unit of Change
	 */

	public int unit()
	{
		return _unit;
	}

	/**
	 * Retrieve the Type of Change
	 * 
	 * @return The Type of Change
	 */

	public java.lang.String typeOfChange()
	{
		return _typeOfChange;
	}
}
