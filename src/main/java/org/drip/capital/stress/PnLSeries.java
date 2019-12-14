
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>PnLSeries</i> contains the PnL Series of a Single Event. The References are:
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

public class PnLSeries
{
	private double[] _outcomeArray = null;

	/**
	 * Construct a Single Outcome Event PnL
	 * 
	 * @param outcome The PnL Outcome
	 * 
	 * @return The Single Outcome Event PnL
	 */

	public static final PnLSeries SingleOutcome (
		final double outcome)
	{
		try
		{
			return new PnLSeries (
				new double[]
				{
					outcome
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Single Zero Outcome Event PnL
	 * 
	 * @return The Single Zero Outcome Event PnL
	 */

	public static final PnLSeries SingleZeroOutcome()
	{
		return SingleOutcome (
			0.
		);
	}

	/**
	 * PnLSeries Constructor
	 * 
	 * @param outcomeArray Array of PnL Outcomes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PnLSeries (
		final double[] outcomeArray)
		throws java.lang.Exception
	{
		if (null == (_outcomeArray = outcomeArray) || 0 == _outcomeArray.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (_outcomeArray))
		{
			throw new java.lang.Exception ("PnLSeries Constructor => Invalid inputs");
		}
	}

	/**
	 * Retrieve the Array of PnL Outcomes
	 * 
	 * @return Array of PnL Outcomes
	 */

	public double[] outcomeArray()
	{
		return _outcomeArray;
	}

	/**
	 * Retrieve the Count of PnL Outcomes
	 * 
	 * @return Count of PnL Outcomes
	 */

	public int count()
	{
		return _outcomeArray.length;
	}

	/**
	 * Retrieve the Composite of the Outcomes
	 * 
	 * @return Composite of the Outcomes
	 */

	public double composite()
	{
		double sum = 0.;

		for (double outcome : _outcomeArray)
		{
			sum = sum + outcome;
		}

		return sum / _outcomeArray.length;
	}

	@Override public java.lang.String toString()
	{
		java.lang.String representation = "[";

		for (double outcome : _outcomeArray)
		{
			representation = representation + outcome + ",";
		}

		return representation + "]";
	}
}
