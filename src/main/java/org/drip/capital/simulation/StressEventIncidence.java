
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>StressEventIncidence</i> holds the Name, the Type, and the PnL induced by a Stress Event Occurrence.
 * 	The References are:
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

public class StressEventIncidence
{
	private java.lang.String _name = "";
	private java.lang.String _type = "";
	private double _pnl = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _paaCategoryPnLDecomposition = null;

	/**
	 * StressEventIncidence Constructor
	 * 
	 * @param name Stress Event Name
	 * @param type Stress Event Type
	 * @param pnl Stress Event PnL
	 * @param paaCategoryPnLDecomposition Decomposition of the Stress Event PnL using PAA Categories
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StressEventIncidence (
		final java.lang.String name,
		final java.lang.String type,
		final double pnl,
		final java.util.Map<java.lang.String, java.lang.Double> paaCategoryPnLDecomposition)
		throws java.lang.Exception
	{
		if (null == (_name = name) || _name.isEmpty() ||
			null == (_type = type) || _type.isEmpty() ||
			!org.drip.numerical.common.NumberUtil.IsValid (_pnl = pnl))
		{
			throw new java.lang.Exception ("StressEventIncidence Constructor => Invalid Inputs");
		}

		_paaCategoryPnLDecomposition = paaCategoryPnLDecomposition;
	}

	/**
	 * Retrieve the Name/Description of the Stress Event
	 * 
	 * @return Name/Description of the Stress Event
	 */

	public java.lang.String name()
	{
		return _name;
	}

	/**
	 * Retrieve the Type of the Stress Event
	 * 
	 * @return Type of the Stress Event
	 */

	public java.lang.String type()
	{
		return _type;
	}

	/**
	 * Retrieve the PnL of the Stress Event
	 * 
	 * @return PnL of the Stress Event
	 */

	public double pnl()
	{
		return _pnl;
	}

	/**
	 * Retrieve the PnL Decomposition of the Stress Event using PAA Categories
	 * 
	 * @return PnL Decomposition of the Stress Event using PAA Categories
	 */

	public java.util.Map<java.lang.String, java.lang.Double> paaCategoryPnLDecomposition()
	{
		return _paaCategoryPnLDecomposition;
	}
}
