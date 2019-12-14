
package org.drip.capital.setting;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>SimulationPnLControl</i> holds the Customization Control Parameters for the Simulation PnL. The
 * 	References are:
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

public class SimulationPnLControl
{
	private org.drip.capital.setting.HorizonTailPnLControl _stress = null;
	private org.drip.capital.setting.HorizonTailFSPnLControl _noStress = null;

	/**
	 * Construct the Standard Instance of SimulationPnLControl
	 * 
	 * @return Standard Instance of SimulationPnLControl
	 */

	public static final SimulationPnLControl Standard()
	{
		try
		{
			return new SimulationPnLControl (
				org.drip.capital.setting.HorizonTailFSPnLControl.Standard(),
				org.drip.capital.setting.HorizonTailPnLControl.StandardStress()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SimulationPnLControl Constructor
	 * 
	 * @param noStress No-stressed Horizon Tail Volatility Adjustment Control
	 * @param stress Stress Horizon Tail Adjustment Control
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SimulationPnLControl (
		final org.drip.capital.setting.HorizonTailFSPnLControl noStress,
		final org.drip.capital.setting.HorizonTailPnLControl stress)
		throws java.lang.Exception
	{
		if (null == (_noStress = noStress) ||
			null == (_stress = stress))
		{
			throw new java.lang.Exception ("SimulationPnLControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the No-stress Horizon Tail Volatility Adjustment Control
	 * 
	 * @return No-stress Horizon Tail Volatility Adjustment Control
	 */

	public org.drip.capital.setting.HorizonTailFSPnLControl noStress()
	{
		return _noStress;
	}

	/**
	 * Retrieve the Stress Horizon Tail Adjustment Control
	 * 
	 * @return Stress Horizon Tail Adjustment Control
	 */

	public org.drip.capital.setting.HorizonTailPnLControl stress()
	{
		return _stress;
	}
}
