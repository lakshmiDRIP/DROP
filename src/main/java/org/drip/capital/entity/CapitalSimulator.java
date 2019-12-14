
package org.drip.capital.entity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalSimulator</i> exposes the Simulator for the VaR and the Stress Functionality for a given Capital
 * Entity - Segment or Unit. The References are:
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

public interface CapitalSimulator
{

	/**
	 * Generate the Array of Path PnL Realizations
	 * 
	 * @param simulationControl Simulation Settings
	 * @param simulationPnLControl PnL Settings
	 * 
	 * @return Array of Path PnL Realizations
	 */

	public abstract org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl);

	/**
	 * Generate the Simulation Path Ensemble
	 * 
	 * @param simulationControl Simulation Settings
	 * @param simulationPnLControl PnL Settings
	 * 
	 * @return The Simulation Path Ensemble
	 */

	public abstract org.drip.capital.simulation.PathEnsemble pathEnsemble (
		final org.drip.capital.setting.SimulationControl simulationControl,
		final org.drip.capital.setting.SimulationPnLControl simulationPnLControl);
}
