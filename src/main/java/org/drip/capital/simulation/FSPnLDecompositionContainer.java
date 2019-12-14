
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>FSPnLDecompositionContainer</i> holds the Series of Decomposed FS PnL's. The References are:
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

public class FSPnLDecompositionContainer
{
	private java.util.List<org.drip.capital.simulation.FSPnLDecomposition> _fsPnLDecompositionList = null;

	/**
	 * Generate a Standard Instance of FSPnLDecompositionContainer
	 * 
	 * @param notional The Notional
	 * @param count Count of the PnL List 
	 * 
	 * @return Standard Instance of FSPnLDecompositionContainer
	 */

	public static final FSPnLDecompositionContainer Standard (
		final double notional,
		final int count)
	{
		if (0 >= count)
		{
			return null;
		}

		java.util.List<org.drip.capital.simulation.FSPnLDecomposition> fsPnLDecompositionList = new
			java.util.ArrayList<org.drip.capital.simulation.FSPnLDecomposition>();

		for (int index = 0; index < count; ++index)
		{
			fsPnLDecompositionList.add (
				org.drip.capital.simulation.FSPnLDecomposition.Standard (notional)
			);
		}

		try
		{
			return new FSPnLDecompositionContainer (fsPnLDecompositionList);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FSPnLDecompositionContainer Constructor
	 * 
	 * @param fsPnLDecompositionList List of FS PnL Decomposition
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FSPnLDecompositionContainer (
		final java.util.List<org.drip.capital.simulation.FSPnLDecomposition> fsPnLDecompositionList)
		throws java.lang.Exception
	{
		if (null == (_fsPnLDecompositionList = fsPnLDecompositionList))
		{
			throw new java.lang.Exception ("FSPnLDecompositionContainer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the List of FS PnL Decomposition
	 * 
	 * @return List of FS PnL Decomposition
	 */

	public java.util.List<org.drip.capital.simulation.FSPnLDecomposition> fsPnLDecompositionList()
	{
		return _fsPnLDecompositionList;
	}

	/**
	 * Retrieve the Count of the PnL List
	 * 
	 * @return Count of the PnL List
	 */

	public int count()
	{
		return null == _fsPnLDecompositionList ? 0 : _fsPnLDecompositionList.size();
	}

	/**
	 * Apply the FS Type Specific Volatility Scaling to the PnL Decomposition
	 * 
	 * @param fsTypeAdjustmentMap FS Type Volatility Adjustment Map
	 * 
	 * @return FS Type Specific Volatility Adjusted List
	 */

	public java.util.List<java.util.Map<java.lang.String, java.lang.Double>> applyVolatilityAdjustment (
		final java.util.Map<java.lang.String, java.lang.Double> fsTypeAdjustmentMap)
	{
		java.util.List<java.util.Map<java.lang.String, java.lang.Double>>
			volatilityAdjustedFSPnLDecompositionList = new
				java.util.ArrayList<java.util.Map<java.lang.String, java.lang.Double>>();

		for (org.drip.capital.simulation.FSPnLDecomposition pnlFSDecomposition : _fsPnLDecompositionList)
		{
			java.util.Map<java.lang.String, java.lang.Double> volatilityAdjustedFSPnLDecomposition =
				pnlFSDecomposition.applyVolatilityAdjustment (
					fsTypeAdjustmentMap,
					1.
				);

			if (null == volatilityAdjustedFSPnLDecomposition)
			{
				return null;
			}

			volatilityAdjustedFSPnLDecompositionList.add (volatilityAdjustedFSPnLDecomposition);
		}

		return volatilityAdjustedFSPnLDecompositionList;
	}
}
