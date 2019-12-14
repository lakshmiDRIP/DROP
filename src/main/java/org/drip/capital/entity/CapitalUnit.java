
package org.drip.capital.entity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnit</i> implements the VaR and the Stress Functionality for the specified Capital Unit. The
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

public class CapitalUnit
	implements org.drip.capital.entity.CapitalSimulator
{
	private double _notional = java.lang.Double.NaN;
	private org.drip.capital.label.Coordinate _coordinate = null;
	private org.drip.capital.entity.CapitalUnitEventContainer _stressEventContainer = null;

	private org.drip.capital.simulation.PathPnLRealization pathPnLRealization (
		final int pathIndex,
		final double pnlScaler,
		final org.drip.capital.simulation.FSPnLDecomposition fsPnLDecomposition,
		final java.util.Map<java.lang.String, java.lang.Double> fsTypeVolatilityAjustmentMap,
		final double stressPnLScaler,
		final org.drip.capital.simulation.StressEventIndicator stressEventIndicator)
	{
		if (null == fsPnLDecomposition || null == stressEventIndicator)
		{
			return null;
		}

		org.drip.capital.stress.GSSTEventContainer gsstEventContainer =
			_stressEventContainer.gsstEventContainer();

		org.drip.capital.stress.IBSSTEventContainer iBSSTEventContainer =
			_stressEventContainer.iBSSTEventContainer();

		try
		{
			return new org.drip.capital.simulation.PathPnLRealization (
				pathIndex,
				fsPnLDecomposition.applyVolatilityAdjustment (
					fsTypeVolatilityAjustmentMap,
					pnlScaler
				),
				null == gsstEventContainer ? null : gsstEventContainer.realizeIncidenceEnsemble (
					stressPnLScaler,
					stressEventIndicator.systemic()
				),
				null == iBSSTEventContainer ? null : iBSSTEventContainer.realizeIncidenceEnsemble (
					stressPnLScaler,
					stressEventIndicator.idiosyncraticMap()
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CapitalUnit Constructor
	 * 
	 * @param coordinate Capital Unit Coordinate
	 * @param stressEventContainer Capital Unit Stress Event Container
	 * @param notional The Capital Unit Notional
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalUnit (
		final org.drip.capital.label.Coordinate coordinate,
		final org.drip.capital.entity.CapitalUnitEventContainer stressEventContainer,
		final double notional)
		throws java.lang.Exception
	{
		if (null == (_coordinate = coordinate) ||
			null == (_stressEventContainer = stressEventContainer) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_notional = notional))
		{
			throw new java.lang.Exception ("CapitalUnit Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Capital Unit Coordinate
	 * 
	 * @return The Capital Unit Coordinate
	 */

	public org.drip.capital.label.Coordinate coordinate()
	{
		return _coordinate;
	}

	/**
	 * Retrieve the Capital Unit Stress Event Container
	 * 
	 * @return The Capital Unit Stress Event Container
	 */

	public org.drip.capital.entity.CapitalUnitEventContainer stressEventContainer()
	{
		return _stressEventContainer;
	}

	/**
	 * Retrieve the Capital Unit Notional
	 * 
	 * @return The Capital Unit Notional
	 */

	public double notional()
	{
		return _notional;
	}

	@Override public org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl)
	{
		if (null == simulationControl ||
			null == simulationPnLControl)
		{
			return null;
		}

		org.drip.capital.stress.IBSSTEventContainer iBSSTEventContainer =
			_stressEventContainer.iBSSTEventContainer();

		java.util.Set<java.lang.String> iBSSTEventSet = null == iBSSTEventContainer ? null :
			iBSSTEventContainer.eventMap().keySet();

		org.drip.capital.setting.HorizonTailFSPnLControl pnlControl = simulationPnLControl.noStress();

		java.util.Map<java.lang.String, java.lang.Double> fsTypeVolatilityAjustmentMap =
			pnlControl.fsTypeVolatilityAjustmentMap();

		int systemicStressIncidenceSampling = simulationControl.systemicStressIncidenceSampling();

		double stressPnLScaler = simulationPnLControl.stress().grossScaler();

		int pathCount = simulationControl.pathCount();

		double pnlScaler = pnlControl.grossScaler();

		org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray =
			new org.drip.capital.simulation.PathPnLRealization[pathCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			if (null == (
				pathPnLRealizationArray[pathIndex] = pathPnLRealization (
					pathIndex,
					pnlScaler,
					org.drip.capital.simulation.FSPnLDecomposition.Standard (_notional),
					fsTypeVolatilityAjustmentMap,
					stressPnLScaler,
					org.drip.capital.setting.SimulationControl.SYSTEMIC_STRESS_INCIDENCE_RANDOM_SAMPLING
						== systemicStressIncidenceSampling ?
						org.drip.capital.simulation.StressEventIndicator.RandomSystemic (
							iBSSTEventSet
						) : org.drip.capital.simulation.StressEventIndicator.CustomSystemic (
							iBSSTEventSet,
							((double) pathIndex) / ((double) pathCount)
						)
					)
				))
			{
				return null;
			}
		}

		return pathPnLRealizationArray;
	}

	@Override public org.drip.capital.simulation.CapitalUnitPathEnsemble pathEnsemble (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl)
	{
		org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray = pathPnLRealizationArray
		(
			simulationControl,
			simulationPnLControl
		);

		if (null == pathPnLRealizationArray)
		{
			return null;
		}

		org.drip.capital.simulation.CapitalUnitPathEnsemble capitalUnitPathEnsemble =
			new org.drip.capital.simulation.CapitalUnitPathEnsemble();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : pathPnLRealizationArray)
		{
			if (!capitalUnitPathEnsemble.addPathPnLRealization (pathPnLRealization))
			{
				return null;
			}
		}

		return capitalUnitPathEnsemble;
	}
}
