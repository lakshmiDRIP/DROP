
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EntityCapital</i> holds the Capital for each Entity. The References are:
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

public class EntityCapital
{
	private double _absolute = java.lang.Double.NaN;
	private double _fractional = java.lang.Double.NaN;

	/**
	 * EntityCapital Constructor
	 * 
	 * @param absolute Absolute Amount of the Entity Capital
	 * @param fractional Fractional Amount of the Entity Capital
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityCapital (
		final double absolute,
		final double fractional)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_absolute = absolute) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_fractional = fractional))
		{
			throw new java.lang.Exception (
				"EntityCapital Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Absolute Amount of the Entity Capital
	 * 
	 * @return Absolute Amount of the Entity Capital
	 */

	public double absolute()
	{
		return _absolute;
	}

	/**
	 * Retrieve the Fractional Amount of the Entity Capital
	 * 
	 * @return Fractional Amount of the Entity Capital
	 */

	public double fractional()
	{
		return _fractional;
	}

	@Override public java.lang.String toString()
	{
		return org.drip.numerical.common.FormatUtil.FormatDouble (
			_absolute,
			10,
			 0,
			 1.,
			false
		) + " | (" + org.drip.numerical.common.FormatUtil.FormatDouble (
			_fractional,
			2,
			2,
			100.,
			false
		) + "%)";
	}
}
