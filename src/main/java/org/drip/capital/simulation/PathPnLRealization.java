
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>PathPnLRealization</i> holds the Realized PnL and its Components along a Simulated Path. The References
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

public class PathPnLRealization
{
	private int _pathIndex = -1;
	private org.drip.capital.simulation.StressEventIncidenceEnsemble _systemic = null;
	private java.util.Map<java.lang.String, java.lang.Double> _fsPnLDecompositionMap = null;
	private org.drip.capital.simulation.StressEventIncidenceEnsemble _idiosyncratic = null;

	/**
	 * Combine the Path Realizations onto One
	 * 
	 * @param pathPnLRealizationArray Array of Path PnL Realizations
	 * 
	 * @return The Path Realizations combined into One
	 */

	public static final PathPnLRealization Combine (
		final PathPnLRealization[] pathPnLRealizationArray)
	{
		if (null == pathPnLRealizationArray)
		{
			return null;
		}

		int pathPnLRealizationCount = pathPnLRealizationArray.length;

		if (0 == pathPnLRealizationCount)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<>();

		org.drip.capital.simulation.StressEventIncidenceEnsemble systemicStressEventIncidenceEnsemble =
			new org.drip.capital.simulation.StressEventIncidenceEnsemble();

		org.drip.capital.simulation.StressEventIncidenceEnsemble idiosyncraticStressEventIncidenceEnsemble
			= new org.drip.capital.simulation.StressEventIncidenceEnsemble();

		for (int pathPnLRealizationIndex = 0;
			pathPnLRealizationIndex < pathPnLRealizationCount;
			++pathPnLRealizationIndex)
		{
			if (null == pathPnLRealizationArray[pathPnLRealizationIndex])
			{
				return null;
			}

			java.util.Map<java.lang.String, java.lang.Double> pathFSPnLDecompositionMap =
				pathPnLRealizationArray[pathPnLRealizationIndex].fsPnLDecompositionMap();

			if (null != pathFSPnLDecompositionMap)
			{
				java.util.Set<java.lang.String> fsPnLDecompositionKeySet = pathFSPnLDecompositionMap.keySet();

				for (java.lang.String fsPnLDecompositionKey : fsPnLDecompositionKeySet)
				{
					if (fsPnLDecompositionMap.containsKey (fsPnLDecompositionKey))
					{
						fsPnLDecompositionMap.put (
							fsPnLDecompositionKey,
							fsPnLDecompositionMap.get (fsPnLDecompositionKey) +
								pathFSPnLDecompositionMap.get (fsPnLDecompositionKey)
						);
					}
					else
					{
						fsPnLDecompositionMap.put (
							fsPnLDecompositionKey,
							pathFSPnLDecompositionMap.get (fsPnLDecompositionKey)
						);
					}
				}
			}

			org.drip.capital.simulation.StressEventIncidenceEnsemble
				pathSystemicStressEventIncidenceEnsemble =
					pathPnLRealizationArray[pathPnLRealizationIndex].systemic();

			if (null != pathSystemicStressEventIncidenceEnsemble)
			{
				java.util.List<org.drip.capital.simulation.StressEventIncidence>
					pathSystemicStressEventIncidenceList =
						pathSystemicStressEventIncidenceEnsemble.stressEventIncidenceList();

				for (org.drip.capital.simulation.StressEventIncidence pathSystemicStressEventIncidence :
					pathSystemicStressEventIncidenceList)
				{
					systemicStressEventIncidenceEnsemble.addStressEventIncidence (
						pathSystemicStressEventIncidence
					);
				}
			}

			org.drip.capital.simulation.StressEventIncidenceEnsemble
				pathIdiosyncraticStressEventIncidenceEnsemble =
					pathPnLRealizationArray[pathPnLRealizationIndex].idiosyncratic();

			if (null != pathIdiosyncraticStressEventIncidenceEnsemble)
			{
				java.util.List<org.drip.capital.simulation.StressEventIncidence>
					pathIdiosyncraticStressEventIncidenceList =
						pathIdiosyncraticStressEventIncidenceEnsemble.stressEventIncidenceList();

				for (org.drip.capital.simulation.StressEventIncidence pathIdiosyncraticStressEventIncidence
					: pathIdiosyncraticStressEventIncidenceList)
				{
					idiosyncraticStressEventIncidenceEnsemble.addStressEventIncidence (
						pathIdiosyncraticStressEventIncidence
					);
				}
			}
		}

		try
		{
			return new PathPnLRealization (
				pathPnLRealizationArray[0].pathIndex(),
				fsPnLDecompositionMap,
				systemicStressEventIncidenceEnsemble,
				idiosyncraticStressEventIncidenceEnsemble
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathPnLRealization Constructor
	 * 
	 * @param pathIndex Index of the Realized Path
	 * @param fsPnLDecompositionMap Single Path PnL Decomposition Map by FS Type
	 * @param systemic Systemic Stress Event Incidence Ensemble
	 * @param idiosyncratic Idiosyncratic Stress Event Incidence Ensemble
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathPnLRealization (
		final int pathIndex,
		final java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionMap,
		final org.drip.capital.simulation.StressEventIncidenceEnsemble systemic,
		final org.drip.capital.simulation.StressEventIncidenceEnsemble idiosyncratic)
		throws java.lang.Exception
	{
		if (-1 >= (_pathIndex = pathIndex))
		{
			throw new java.lang.Exception ("PathPnLRealization Constructor => Invalid Inputs");
		}

		_systemic = systemic;
		_idiosyncratic = idiosyncratic;
		_fsPnLDecompositionMap = fsPnLDecompositionMap;
	}

	/**
	 * Retrieve the Path Index
	 * 
	 * @return Path index
	 */

	public int pathIndex()
	{
		return _pathIndex;
	}

	/**
	 * Retrieve the Path FS PnL Decomposition
	 * 
	 * @return Path FS PnL Decomposition
	 */

	public java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionMap()
	{
		return _fsPnLDecompositionMap;
	}

	/**
	 * Retrieve the Idiosyncratic Stress Event Incidence Ensemble
	 * 
	 * @return The Idiosyncratic Stress Event Incidence Ensemble
	 */

	public org.drip.capital.simulation.StressEventIncidenceEnsemble idiosyncratic()
	{
		return _idiosyncratic;
	}

	/**
	 * Retrieve the Systemic Stress Event Incidence Ensemble
	 * 
	 * @return The Systemic Stress Event Incidence Ensemble
	 */

	public org.drip.capital.simulation.StressEventIncidenceEnsemble systemic()
	{
		return _systemic;
	}

	/**
	 * Retrieve the Realized Systemic Stress PnL
	 * 
	 * @return The Realized Systemic Stress PnL
	 */

	public double grossSystemicStressPnL()
	{
		return null != _systemic ? _systemic.grossPnL() : 0.;
	}

	/**
	 * Retrieve the Realized Idiosyncratic Stress PnL
	 * 
	 * @return The Realized Idiosyncratic Stress PnL
	 */

	public double grossIdiosyncraticStressPnL()
	{
		return null != _idiosyncratic ? _idiosyncratic.grossPnL() : 0.;
	}

	/**
	 * Retrieve the Realized FS Gross PnL
	 * 
	 * @return The Realized FS Gross PnL
	 */

	public double grossFSPnL()
	{
		if (null == _fsPnLDecompositionMap)
		{
			return 0.;
		}

		double grossFSPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> fsPnLDecompositionEntry :
			_fsPnLDecompositionMap.entrySet())
		{
			grossFSPnL = grossFSPnL + fsPnLDecompositionEntry.getValue();
		}

		return grossFSPnL;
	}

	/**
	 * Retrieve the Total Realized PnL
	 * 
	 * @return The Total Realized PnL
	 */

	public double grossPnL()
	{
		return grossSystemicStressPnL() + grossIdiosyncraticStressPnL() + grossFSPnL();
	}
}
