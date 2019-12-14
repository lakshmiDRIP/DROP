
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>SystemicStressShockIndicator</i> holds the Directional Indicator Settings for a given Systemic Stress
 * Shock Event. The References are:
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

public class SystemicStressShockIndicator
{

	/**
	 * Systemic Stress Shock Direction UP
	 */

	public static final int UP = 1;

	/**
	 * Systemic Stress Shock Direction DOWN
	 */

	public static final int DOWN = -1;

	/**
	 * Systemic Stress Shock Direction UNSPECIFIED
	 */

	public static final int UNSPECIFIED = 0;

	/**
	 * DEFLATIONARY Systemic Stress Scenario
	 */

	public static final java.lang.String DEFLATIONARY = "DEFLATIONARY";

	/**
	 * INFLATIONARY Systemic Stress Scenario
	 */

	public static final java.lang.String INFLATIONARY = "INFLATIONARY";

	/**
	 * Neither DEFLATIONARY nor INFLATIONARY Systemic Stress Scenario
	 */

	public static final java.lang.String NEITHER = "NEITHER";

	private int _energy = java.lang.Integer.MIN_VALUE;
	private int _commodities = java.lang.Integer.MIN_VALUE;
	private int _creditSpreads = java.lang.Integer.MIN_VALUE;
	private int _equityMarkets = java.lang.Integer.MIN_VALUE;
	private int _yieldCurveLevel = java.lang.Integer.MIN_VALUE;

	private static final boolean VerifyIndicator (
		final int indicator)
	{
		return DOWN == indicator || UP == indicator || UNSPECIFIED == indicator;
	}

	/**
	 * Construct a Deflationary Systemic Stress Shock Indicator
	 * 
	 * @return Deflationary Systemic Stress Shock Indicator
	 */

	public static final SystemicStressShockIndicator Deflationary()
	{
		try
		{
			return new SystemicStressShockIndicator (
				UP,
				DOWN,
				DOWN,
				DOWN,
				DOWN
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Inflationary Systemic Stress Shock Indicator
	 * 
	 * @return Inflationary Systemic Stress Shock Indicator
	 */

	public static final SystemicStressShockIndicator Inflationary()
	{
		try
		{
			return new SystemicStressShockIndicator (
				UP,
				DOWN,
				UP,
				UP,
				UP
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SystemicStressShockIndicator Constructor
	 * 
	 * @param creditSpreads Credit Spreads Directional Indicator
	 * @param equityMarkets Equity Markets Directional Indicator
	 * @param yieldCurveLevel Yield Curve Level Directional Indicator
	 * @param commodities Commodities Directional Indicator
	 * @param energy Energy Directional Indicator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SystemicStressShockIndicator (
		final int creditSpreads,
		final int equityMarkets,
		final int yieldCurveLevel,
		final int commodities,
		final int energy)
		throws java.lang.Exception
	{
		if (!VerifyIndicator (_creditSpreads = creditSpreads) ||
			!VerifyIndicator (_equityMarkets = equityMarkets) ||
			!VerifyIndicator (_yieldCurveLevel = yieldCurveLevel) ||
			!VerifyIndicator (_commodities = commodities) ||
			!VerifyIndicator (_energy = energy))
		{
			throw new java.lang.Exception ("SystemicStressShockIndicator Cnstructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Credit Spreads Directional Indicator
	 * 
	 * @return The Credit Spreads Directional Indicator
	 */

	public int creditSpreads()
	{
		return _creditSpreads;
	}

	/**
	 * Retrieve the Equity Markets Directional Indicator
	 * 
	 * @return The Equity Markets Directional Indicator
	 */

	public int equityMarkets()
	{
		return _equityMarkets;
	}

	/**
	 * Retrieve the Yield Curve Level Directional Indicator
	 * 
	 * @return The Yield Curve Level Directional Indicator
	 */

	public int yieldCurveLevel()
	{
		return _yieldCurveLevel;
	}

	/**
	 * Retrieve the Commodities Directional Indicator
	 * 
	 * @return The Commodities Directional Indicator
	 */

	public int commodities()
	{
		return _commodities;
	}

	/**
	 * Retrieve the Energy Directional Indicator
	 * 
	 * @return The Energy Directional Indicator
	 */

	public int energy()
	{
		return _energy;
	}

	/**
	 * Indicate if the Scenario is DEFLATIONARY
	 * 
	 * @return TRUE - The Scenario is DEFLATIONARY
	 */

	public boolean isDeflationary()
	{
		return UP == _creditSpreads &&
			DOWN == _equityMarkets &&
			DOWN == _yieldCurveLevel && 
			DOWN == _commodities &&
			DOWN == _energy;
	}

	/**
	 * Indicate if the Scenario is INFLATIONARY
	 * 
	 * @return TRUE - The Scenario is INFLATIONARY
	 */

	public boolean isInflationary()
	{
		return UP == _creditSpreads &&
			DOWN == _equityMarkets &&
			UP == _yieldCurveLevel && 
			UP == _commodities &&
			UP == _energy;
	}

	/**
	 * Indicate the Inflation Type
	 * 
	 * @return The Inflation Type
	 */

	public java.lang.String inflationType()
	{
		if (isDeflationary())
		{
			return DEFLATIONARY;
		}

		return isInflationary() ? INFLATIONARY : NEITHER;
	}
}
