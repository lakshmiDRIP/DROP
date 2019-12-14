
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>PathEnsemble</i> exposes the Ensemble of Capital Paths from the Simulation PnL Realizations. The
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

public interface PathEnsemble
	extends org.drip.capital.simulation.EnsemblePnLDistributionGenerator
{

	/**
	 * Add the specified Path PnL Realization
	 * 
	 * @param pathPnLRealization Path PnL Realization
	 * 
	 * @return The Path PnL Realization successfully added
	 */

	public abstract boolean addPathPnLRealization (
		final org.drip.capital.simulation.PathPnLRealization pathPnLRealization);

	/**
	 * Retrieve the PnL List Map
	 * 
	 * @return The PnL List Map
	 */

	public abstract java.util.Map<java.lang.Double, java.util.List<java.lang.Integer>> pnlListMap();

	/**
	 * Retrieve the Systemic Event Incidence Count Map
	 * 
	 * @return The Systemic Event Incidence Count Map
	 */

	public abstract java.util.Map<java.lang.String, java.lang.Integer> systemicEventIncidenceCountMap();

	/**
	 * Retrieve the Idiosyncratic Event Incidence Count Map
	 * 
	 * @return The Idiosyncratic Event Incidence Count Map
	 */

	public abstract java.util.Map<java.lang.String, java.lang.Integer> idiosyncraticEventIncidenceCountMap();

	/**
	 * Retrieve the Number of Paths Simulated
	 * 
	 * @return The Number of Paths Simulated
	 */

	public abstract int count();

	/**
	 * Retrieve the Occurrence Count for the specified Systemic Event
	 * 
	 * @param event The Systemic Event
	 * 
	 * @return Occurrence Count for the specified Systemic Event
	 */

	public abstract int systemicEventIncidenceCount (
		final java.lang.String event);

	/**
	 * Retrieve the Occurrence Count for the specified Idiosyncratic Event
	 * 
	 * @param event The Idiosyncratic Event
	 * 
	 * @return Occurrence Count for the specified Idiosyncratic Event
	 */

	public abstract int idiosyncraticEventIncidenceCount (
		final java.lang.String event);

	/**
	 * Compute VaR given the Confidence Level by Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return VaR
	 * 
	 * @throws java.lang.Exception Thrown if the VaR cannot be computed
	 */

	public abstract double var (
		final int confidenceCount)
		throws java.lang.Exception;

	/**
	 * Compute VaR given the Confidence Level by Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Percentage
	 * 
	 * @return VaR
	 * 
	 * @throws java.lang.Exception Thrown if the VaR cannot be computed
	 */

	public abstract double var (
		final double confidenceLevel)
		throws java.lang.Exception;

	/**
	 * Compute Expected Short-fall given the Confidence Level by Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return Expected Short-fall
	 * 
	 * @throws java.lang.Exception Thrown if the VaR cannot be computed
	 */

	public abstract double expectedShortfall (
		final int confidenceCount)
		throws java.lang.Exception;

	/**
	 * Compute Expected Short-fall given the Confidence Level by Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Percentage
	 * 
	 * @return Expected Short-fall
	 * 
	 * @throws java.lang.Exception Thrown if the VaR cannot be computed
	 */

	public abstract double expectedShortfall (
		final double confidenceLevel)
		throws java.lang.Exception;

	/**
	 * Construct the Contributing PnL Attribution given the Confidence Level by Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return The Contributing PnL Attribution
	 */

	public abstract org.drip.capital.explain.CapitalUnitPnLAttribution pnlAttribution (
		final int confidenceCount);

	/**
	 * Construct the Contributing PnL Attribution given the Confidence Level by Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Percentage
	 * 
	 * @return The Contributing PnL Attribution
	 */

	public abstract org.drip.capital.explain.CapitalUnitPnLAttribution pnlAttribution (
		final double confidenceLevel);

	/**
	 * Construct the Contributing Path Attribution given the Path Index List
	 * 
	 * @param pathIndexList Path Index List
	 * 
	 * @return The Contributing Path Attribution
	 */

	public abstract org.drip.capital.explain.CapitalUnitPnLAttribution pnlAttribution (
		final java.util.List<java.lang.Integer> pathIndexList);
}
