
package org.drip.sample.systemicstress;

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
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/systemicstress/README.md">Built-in GSST Scenario Examination</a></li>
 *  </ul>
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
