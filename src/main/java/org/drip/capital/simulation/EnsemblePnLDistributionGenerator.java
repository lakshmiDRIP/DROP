
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EnsemblePnLDistributionGenerator</i> exposes the Functionality to generate the PnL Distribution from
 * the Realized Path Ensemble. The References are:
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

public interface EnsemblePnLDistributionGenerator
{

	/**
	 * Generate the Gross Systemic PnL Distribution
	 * 
	 * @return The Gross Systemic PnL Distribution
	 */

	public abstract java.util.List<java.lang.Double> grossSystemicStressPnLList();

	/**
	 * Generate the Gross Idiosyncratic PnL Distribution
	 * 
	 * @return The Gross Idiosyncratic PnL Distribution
	 */

	public abstract java.util.List<java.lang.Double> grossIdiosyncraticStressPnLList();

	/**
	 * Generate the Gross FS PnL Distribution
	 * 
	 * @return The Gross FS PnL Distribution
	 */

	public abstract java.util.List<java.lang.Double> grossFSPnLList();

	/**
	 * Generate the Gross PnL Distribution
	 * 
	 * @return The Gross PnL Distribution
	 */

	public abstract java.util.List<java.lang.Double> grossPnLList();

	/**
	 * Generate the Ensemble PnL Distribution
	 * 
	 * @return The Ensemble PnL Distribution
	 */

	public abstract org.drip.capital.simulation.EnsemblePnLDistribution ensembleDistribution();
}
