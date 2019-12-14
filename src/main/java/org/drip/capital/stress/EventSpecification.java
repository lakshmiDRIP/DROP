
package org.drip.capital.stress;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EventSpecification</i> contains the Name of a Stress Event and its Probability. The References are:
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

public class EventSpecification
{
	private java.lang.String _name = "";
	private double _probability = java.lang.Double.NaN;

	/**
	 * Construct the 2008 Baseline Version of the GSST Stress Event Specification
	 * 
	 * @return The 2008 Baseline Version of GSST Stress Event Specification
	 */

	public static final EventSpecification GSST2008Baseline()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.GSSTDefinition.BASELINE_2008,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the 1974 Baseline Version of the GSST Stress Event Specification
	 * 
	 * @return The 1974 Baseline Version of GSST Stress Event Specification
	 */

	public static final EventSpecification GSST1974Baseline()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.GSSTDefinition.BASELINE_1974,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Deep Down-turn Version of the GSST Stress Event Specification
	 * 
	 * @return The Deep Down-turn Version of GSST Stress Event Specification
	 */

	public static final EventSpecification GSSTDeepDownturn()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.GSSTDefinition.DEEP_DOWNTURN,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Dollar Decline Version of the GSST Stress Event Specification
	 * 
	 * @return The Dollar Decline Version of GSST Stress Event Specification
	 */

	public static final EventSpecification GSSTDollarDecline()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.GSSTDefinition.DOLLAR_DECLINE,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Interest Rate Shock Version of the GSST Stress Event Specification
	 * 
	 * @return The Interest Rate Shock Version of GSST Stress Event Specification
	 */

	public static final EventSpecification GSSTInterestRateShock()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.GSSTDefinition.INTEREST_RATE_SHOCK,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Lost Decade Version of the GSST Stress Event Specification
	 * 
	 * @return The Lost Decade Version of GSST Stress Event Specification
	 */

	public static final EventSpecification GSSTLostDecade()
	{
		try
		{
			return new EventSpecification (
				org.drip.capital.definition.GSSTDefinition.LOST_DECADE,
				0.02
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EventSpecification Constructor
	 * 
	 * @param name The Stress Event Name
	 * @param probability The Stress Event Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EventSpecification (
		final java.lang.String name,
		final double probability)
		throws java.lang.Exception
	{
		if (null == (_name = name) || _name.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (_probability = probability) ||
				0. > _probability || 1. < _probability)
		{
			throw new java.lang.Exception ("EventSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Name of the Stress Event
	 * 
	 * @return Name of the Stress Event
	 */

	public java.lang.String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Probability of the Stress Event
	 * 
	 * @return Probability of the Stress Event
	 */

	public double probability()
	{
		return _probability;
	}
}
