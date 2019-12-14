
package org.drip.capital.entity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalSegment</i> exposes the VaR and the Stress Functionality for a Capital Segment. The References
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

public abstract class CapitalSegment
	implements org.drip.capital.entity.CapitalSimulator
{
	private org.drip.capital.label.CapitalSegmentCoordinate _coordinate = null;

	protected CapitalSegment (
		final org.drip.capital.label.CapitalSegmentCoordinate coordinate)
		throws java.lang.Exception
	{
		if (null == (_coordinate = coordinate))
		{
			throw new java.lang.Exception ("CapitalSegment Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Capital Segment Coordinate
	 * 
	 * @return Capital Segment Coordinate
	 */

	public org.drip.capital.label.CapitalSegmentCoordinate coordinate()
	{
		return _coordinate;
	}

	/**
	 * Retrieve the Array of Capital Units
	 * 
	 * @return Array of Capital Units
	 */

	public abstract org.drip.capital.entity.CapitalUnit[] capitalUnitArray();

	/**
	 * Generate the Grid of Capital Unit Path Realizations
	 * 
	 * @param simulationControl Simulation Settings
	 * @param simulationPnLControl PnL Settings
	 * 
	 * @return Grid of Capital Unit Path Realizations
	 */

	public org.drip.capital.simulation.PathPnLRealization[][] capitalUnitPathPnLRealizationGrid (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl)
	{
		org.drip.capital.entity.CapitalUnit[] capitalUnitArray = capitalUnitArray();

		int capitalUnitCount = capitalUnitArray.length;
		org.drip.capital.simulation.PathPnLRealization[][] capitalUnitPathPnLRealizationGrid = new
			org.drip.capital.simulation.PathPnLRealization[capitalUnitCount][];

		for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
		{
			if (null == (capitalUnitPathPnLRealizationGrid[capitalUnitIndex] =
				capitalUnitArray[capitalUnitIndex].pathPnLRealizationArray (
					simulationControl,
					simulationPnLControl
				)
			))
			{
				return null;
			}
		}

		return capitalUnitPathPnLRealizationGrid;
	}

	@Override public org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl)
	{
		int pathCount = simulationControl.pathCount();

		org.drip.capital.simulation.PathPnLRealization[][] capitalUnitPathPnLRealizationGrid =
			capitalUnitPathPnLRealizationGrid (
				simulationControl,
				simulationPnLControl
			);

		if (null == capitalUnitPathPnLRealizationGrid)
		{
			return null;
		}

		org.drip.capital.entity.CapitalUnit[] capitalUnitArray = capitalUnitArray();

		int capitalUnitCount = capitalUnitArray.length;
		org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray =
			new org.drip.capital.simulation.PathPnLRealization[pathCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			org.drip.capital.simulation.PathPnLRealization[] singlePathPnLRealizationArray =
				new org.drip.capital.simulation.PathPnLRealization[capitalUnitCount];

			for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
			{
				singlePathPnLRealizationArray[capitalUnitIndex] =
					capitalUnitPathPnLRealizationGrid[capitalUnitIndex][pathIndex];
			}

			if (null == (pathPnLRealizationArray[pathIndex] =
					org.drip.capital.simulation.PathPnLRealization.Combine (
						singlePathPnLRealizationArray
					)
				)
			)
			{
				return null;
			}
		}

		return pathPnLRealizationArray;
	}

	@Override public org.drip.capital.simulation.CapitalSegmentPathEnsemble pathEnsemble (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl)
	{
		org.drip.capital.simulation.PathPnLRealization[][] capitalUnitPathPnLRealizationGrid =
			capitalUnitPathPnLRealizationGrid (
				simulationControl,
				simulationPnLControl
			);

		if (null == capitalUnitPathPnLRealizationGrid)
		{
			return null;
		}

		int pathCount = simulationControl.pathCount();

		java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble> pathEnsembleMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.simulation.PathEnsemble>();

		org.drip.capital.entity.CapitalUnit[] capitalUnitArray = capitalUnitArray();

		int capitalUnitCount = capitalUnitArray.length;
		org.drip.capital.simulation.CapitalSegmentPathEnsemble capitalSegmentPathEnsemble = null;
		org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray = new
			org.drip.capital.simulation.PathPnLRealization[pathCount];

		for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
		{
			org.drip.capital.simulation.CapitalUnitPathEnsemble capitalUnitPathEnsemble =
				new org.drip.capital.simulation.CapitalUnitPathEnsemble();

			for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization :
				capitalUnitPathPnLRealizationGrid[capitalUnitIndex])
			{
				if (!capitalUnitPathEnsemble.addPathPnLRealization (pathPnLRealization))
				{
					return null;
				}
			}

			pathEnsembleMap.put (
				capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName(),
				capitalUnitPathEnsemble
			);
		}

		try
		{
			capitalSegmentPathEnsemble = new org.drip.capital.simulation.CapitalSegmentPathEnsemble (
				pathEnsembleMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			org.drip.capital.simulation.PathPnLRealization[] singlePathPnLRealizationArray =
				new org.drip.capital.simulation.PathPnLRealization[capitalUnitCount];

			for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
			{
				singlePathPnLRealizationArray[capitalUnitIndex] =
					capitalUnitPathPnLRealizationGrid[capitalUnitIndex][pathIndex];
			}

			if (null == (pathPnLRealizationArray[pathIndex] =
					org.drip.capital.simulation.PathPnLRealization.Combine (
						singlePathPnLRealizationArray
					)
				)
			)
			{
				return null;
			}
		}

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : pathPnLRealizationArray)
		{
			if (!capitalSegmentPathEnsemble.addPathPnLRealization (pathPnLRealization))
			{
				return null;
			}
		}

		return capitalSegmentPathEnsemble;
	}

	/**
	 * Generate the Simulation Path Ensemble Constricted to the specified Path Ensemble Map
	 * 
	 * @param pathEnsembleMap The Path Ensemble Constriction Map
	 * 
	 * @return The Constricted Simulation Path Ensemble
	 */

	public org.drip.capital.simulation.CapitalSegmentPathEnsemble pathEnsemble (
		final java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble> pathEnsembleMap)
	{
		if (null == pathEnsembleMap || 0 == pathEnsembleMap.size())
		{
			return null;
		}

		java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble>
			constrictedPathEnsembleMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.simulation.PathEnsemble>();

		org.drip.capital.entity.CapitalUnit[] capitalUnitArray = capitalUnitArray();

		int pathCount = -1;
		int capitalUnitCount = capitalUnitArray.length;
		org.drip.capital.simulation.PathPnLRealization[][] capitalUnitPathPnLRealizationGrid =
			new org.drip.capital.simulation.PathPnLRealization[capitalUnitCount][];
		org.drip.capital.simulation.CapitalSegmentPathEnsemble constrictedCapitalSegmentPathEnsemble =
			null;

		for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
		{
			java.lang.String capitalUnitCoordinateFQN =
				capitalUnitArray[capitalUnitIndex].coordinate().fullyQualifiedName();

			if (!pathEnsembleMap.containsKey (capitalUnitCoordinateFQN))
			{
				return null;
			}

			org.drip.capital.simulation.PathEnsemble pathEnsemble = pathEnsembleMap.get (
				capitalUnitCoordinateFQN
			);

			constrictedPathEnsembleMap.put (
				capitalUnitCoordinateFQN,
				pathEnsemble
			);

			capitalUnitPathPnLRealizationGrid[capitalUnitIndex] = (
				(org.drip.capital.simulation.CapitalUnitPathEnsemble) pathEnsemble
			).pathPnLRealizationArray();

			if (0 == capitalUnitIndex)
			{
				pathCount = pathEnsemble.count();
			}
		}

		try
		{
			constrictedCapitalSegmentPathEnsemble = new
				org.drip.capital.simulation.CapitalSegmentPathEnsemble (
					constrictedPathEnsembleMap
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray =
			new org.drip.capital.simulation.PathPnLRealization[pathCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			org.drip.capital.simulation.PathPnLRealization[] singlePathPnLRealizationArray =
				new org.drip.capital.simulation.PathPnLRealization[capitalUnitCount];

			for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
			{
				singlePathPnLRealizationArray[capitalUnitIndex] =
					capitalUnitPathPnLRealizationGrid[capitalUnitIndex][pathIndex];
			}

			if (null == (pathPnLRealizationArray[pathIndex] =
					org.drip.capital.simulation.PathPnLRealization.Combine (
						singlePathPnLRealizationArray
					)
				)
			)
			{
				return null;
			}
		}

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : pathPnLRealizationArray)
		{
			if (!constrictedCapitalSegmentPathEnsemble.addPathPnLRealization (pathPnLRealization))
			{
				return null;
			}
		}

		return constrictedCapitalSegmentPathEnsemble;
	}
}
