
package org.drip.capital.setting;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>SimulationControl</i> holds the Parameters guiding the Monte-Carlo Simulation Settings. The References
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

public class SimulationControl
{

	/**
	 * Systemic Stress Incidence Sampling - Random
	 */

	public static final int SYSTEMIC_STRESS_INCIDENCE_RANDOM_SAMPLING = 1;

	/**
	 * Systemic Stress Incidence Sampling - Stratified
	 */

	public static final int SYSTEMIC_STRESS_INCIDENCE_STRATIFIED_SAMPLING = 2;

	private int _pathCount = -1;
	private int _systemicStressIncidenceSampling = -1;

	/**
	 * Construct the Standard Instance of the SimulationControl
	 * 
	 * @return Standard Instance of the SimulationControl
	 */

	public static final SimulationControl Standard()
	{
		try
		{
			return new SimulationControl (
				10000,
				SYSTEMIC_STRESS_INCIDENCE_STRATIFIED_SAMPLING
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SimulationControl Constructor
	 * 
	 * @param pathCount Total Path Count
	 * @param systemicStressIncidenceSampling Systemic Stress Incidence Sampling Flag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SimulationControl (
		final int pathCount,
		final int systemicStressIncidenceSampling)
		throws java.lang.Exception
	{
		if (0 >= (_pathCount = pathCount) ||
			0 >= (_systemicStressIncidenceSampling = systemicStressIncidenceSampling))
		{
			throw new java.lang.Exception ("SimulationControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Number of Paths guiding the Simulation
	 * 
	 * @return Number of Paths guiding the Simulation
	 */

	public int pathCount()
	{
		return _pathCount;
	}

	/**
	 * Retrieve the Systemic Stress Incidence Sampling Indicator
	 * 
	 * @return Systemic Stress Incidence Sampling Indicator
	 */

	public int systemicStressIncidenceSampling()
	{
		return _systemicStressIncidenceSampling;
	}
}
