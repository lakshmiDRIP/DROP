
package org.drip.sample.gsst;

import java.util.List;
import java.util.Map;

import org.drip.capital.env.SystemicScenarioDefinitionContextManager;
import org.drip.capital.shell.PredictorScenarioSpecificationContainer;
import org.drip.capital.systemicscenario.CapitalBaselineDefinition;
import org.drip.capital.systemicscenario.HistoricalScenarioDefinition;
import org.drip.capital.systemicscenario.HypotheticalScenarioDefinition;
import org.drip.capital.systemicscenario.PredictorScenarioSpecification;
import org.drip.capital.systemicscenario.StressScenarioQuantification;
import org.drip.capital.systemicscenario.StressScenarioSpecification;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>StressScenarioDefinition</i> zeds the Built-in Stress Scenario Definitions used for GSST Scenario
 * 	Design. The References are:
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

public class StressScenarioDefinition
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		PredictorScenarioSpecificationContainer predictorScenarioSpecificationContainer =
			SystemicScenarioDefinitionContextManager.PredictorScenarioSpecificationContainer();

		Map<String, List<String>> categoryPredictorListMap =
			predictorScenarioSpecificationContainer.categoryPredictorListMap();

		System.out.println ("\t|----------------------------------------------------------|");

		System.out.println ("\t|             CATEGORIES AND THEIR PREDICTORS              |");

		System.out.println ("\t|----------------------------------------------------------|");

		for (Map.Entry<String, List<String>> categoryPredictorListMapEntry :
			categoryPredictorListMap.entrySet())
		{
			System.out.println ("\t| CATEGORY => " + categoryPredictorListMapEntry.getKey());

			for (String predictor : categoryPredictorListMapEntry.getValue())
			{
				System.out.println ("\t| \t" + predictor);
			}

			System.out.println ("\t|----------------------------------------------------------|");
		}

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------------------|");

		System.out.println ("\t|                 PREDICTORS AND STRESS SCENARIO DEFINITIONS                  |");

		System.out.println ("\t|-----------------------------------------------------------------------------|");

		for (Map.Entry<String, PredictorScenarioSpecification> predictorScenarioSpecificationMapEntry :
			predictorScenarioSpecificationContainer.predictorScenarioSpecificationMap().entrySet())
		{
			PredictorScenarioSpecification predictorScenarioSpecification =
				predictorScenarioSpecificationMapEntry.getValue();

			System.out.println (
				"\t| " + predictorScenarioSpecificationMapEntry.getKey() + " => " +
					predictorScenarioSpecification.category()
			);

			System.out.println ("\t| ------------------------------------------------------");

			for (Map.Entry<String, StressScenarioSpecification> stressScenarioSpecificationMapEntry :
				predictorScenarioSpecification.segmentScenarioSpecificationMap().entrySet())
			{
				StressScenarioSpecification stressScenarioSpecification =
					stressScenarioSpecificationMapEntry.getValue();

				StressScenarioQuantification stressScenarioQuantification =
					stressScenarioSpecification.stressScenarioQuantification();

				HypotheticalScenarioDefinition hypotheticalScenarioDefinition =
					stressScenarioSpecification.hypotheticalScenarioDefinition();

				HistoricalScenarioDefinition historicalScenarioDefinition =
					stressScenarioSpecification.historicalScenarioDefinition();

				CapitalBaselineDefinition capitalBaselineDefinition =
					stressScenarioSpecification.capitalBaselineDefinition();

				System.out.println (
					"\t| \t " + stressScenarioSpecificationMapEntry.getKey() + " => "
				);

				System.out.println (
					"\t| \t \t[QUANTIFICATION: " +
						stressScenarioQuantification.typeOfChange() + " | " +
						stressScenarioQuantification.unit() +
					"]"
				);

				System.out.println (
					"\t| \t \t[HYPOTHETICAL SCENARIO: " +
						FormatUtil.FormatDouble (hypotheticalScenarioDefinition.dollarDecline(), 3, 1, 1.) + " | " +
						FormatUtil.FormatDouble (hypotheticalScenarioDefinition.lostDecade(), 3, 1, 1.) + " | " +
						FormatUtil.FormatDouble (hypotheticalScenarioDefinition.interestRateShock(), 3, 1, 1.) + " | " +
						FormatUtil.FormatDouble (hypotheticalScenarioDefinition.deepDownturn(), 3, 1, 1.) +
					"]"
				);

				System.out.println (
					"\t| \t \t[HISTORICAL SCENARIO: " +
						FormatUtil.FormatDouble (historicalScenarioDefinition.fy1974(), 3, 1, 1.) + " | " +
						FormatUtil.FormatDouble (historicalScenarioDefinition.fy2008(), 3, 1, 1.) +
					"]"
				);

				System.out.println (
					"\t| \t \t[CAPITAL BASELINE: " +
						FormatUtil.FormatDouble (capitalBaselineDefinition.fy1974(), 3, 1, 1.) + " | " +
						FormatUtil.FormatDouble (capitalBaselineDefinition.fy2008(), 3, 1, 1.) +
					"]"
				);
			}

			System.out.println ("\t|-----------------------------------------------------------------------------|");
		}

		EnvManager.TerminateEnv();
	}
}
