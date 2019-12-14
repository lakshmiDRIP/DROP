
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>StressScenarioSpecification</i> specifies the Full Stress Scenario Specification for the given Market
 *	Factor/Applicability Combination. The References are:
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

public class StressScenarioSpecification
{
	private org.drip.capital.gsstdesign.CapitalBaselineDefinition _capitalBaselineDefinition = null;
	private org.drip.capital.gsstdesign.HistoricalScenarioDefinition _historicalScenarioDefinition = null;
	private org.drip.capital.gsstdesign.StressScenarioQuantification _stressScenarioQuantification = null;
	private org.drip.capital.gsstdesign.HypotheticalScenarioDefinition _hypotheticalScenarioDefinition =
		null;

	/**
	 * StressScenarioSpecification Constructor
	 * 
	 * @param stressScenarioQuantification Stress Scenario Quantification
	 * @param hypotheticalScenarioDefinition Hypothetical Scenario Definition
	 * @param historicalScenarioDefinition Historical Scenario Definition
	 * @param capitalBaselineDefinition Capital Baseline Definition
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StressScenarioSpecification (
		final org.drip.capital.gsstdesign.StressScenarioQuantification stressScenarioQuantification,
		final org.drip.capital.gsstdesign.HypotheticalScenarioDefinition hypotheticalScenarioDefinition,
		final org.drip.capital.gsstdesign.HistoricalScenarioDefinition historicalScenarioDefinition,
		final org.drip.capital.gsstdesign.CapitalBaselineDefinition capitalBaselineDefinition)
		throws java.lang.Exception
	{
		if (null == (_stressScenarioQuantification = stressScenarioQuantification) ||
			null == (_hypotheticalScenarioDefinition = hypotheticalScenarioDefinition) ||
			null == (_historicalScenarioDefinition = historicalScenarioDefinition) ||
			null == (_capitalBaselineDefinition = capitalBaselineDefinition))
		{
			throw new java.lang.Exception ("StressScenarioSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Stress Scenario Quantification
	 * 
	 * @return The Stress Scenario Quantification
	 */

	public org.drip.capital.gsstdesign.StressScenarioQuantification stressScenarioQuantification()
	{
		return _stressScenarioQuantification;
	}

	/**
	 * Retrieve the Hypothetical Scenario Definition
	 * 
	 * @return The Hypothetical Scenario Definition
	 */

	public org.drip.capital.gsstdesign.HypotheticalScenarioDefinition hypotheticalScenarioDefinition()
	{
		return _hypotheticalScenarioDefinition;
	}

	/**
	 * Retrieve the Historical Scenario Definition
	 * 
	 * @return The Historical Scenario Definition
	 */

	public org.drip.capital.gsstdesign.HistoricalScenarioDefinition historicalScenarioDefinition()
	{
		return _historicalScenarioDefinition;
	}

	/**
	 * Retrieve the Capital Baseline Definition
	 * 
	 * @return The Capital Baseline Definition
	 */

	public org.drip.capital.gsstdesign.CapitalBaselineDefinition capitalBaselineDefinition()
	{
		return _capitalBaselineDefinition;
	}
}
