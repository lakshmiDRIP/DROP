
package org.drip.sample.systemicstress;

import java.util.Map;

import org.drip.capital.entity.CapitalUnitEventContainer;
import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.shell.CapitalUnitStressEventContext;
import org.drip.capital.stress.Event;
import org.drip.capital.stress.SystemicEventContainer;
import org.drip.capital.stress.PnLSeries;
import org.drip.capital.stress.EventSpecification;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>CVANORTHAMERICA</i> zeds the Child Coordinates and their corresponding GSST Dump - Scenario Names, Loss
 * Amount, and Probability for the following Coordinates:
 *  
 *    - REGION    == NORTH AMERICA
 *    - RISK TYPE == CVA
 *     
 * The References are:
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

public class CVANORTHAMERICA
{

	private static final String DisplayStressEventPnL (
		final PnLSeries stressEventPnL)
		throws Exception
	{
		String stressEventPnLDisplay = " ";

		for (double pnlOutcome : stressEventPnL.outcomeArray())
		{
			stressEventPnLDisplay = stressEventPnLDisplay +
				FormatUtil.FormatDouble (pnlOutcome, 3, 1, 1.) + " | ";
		}

		return stressEventPnLDisplay + FormatUtil.FormatDouble (stressEventPnL.composite(), 3, 1, 1.);
	}

	private static final void DisplayStressScenario (
		final Map<String, CapitalUnitEventContainer> coordinateStressScenarioMap,
		final java.lang.String coordinateFQN)
		throws Exception
	{
		SystemicEventContainer coordinateStressScenarioEvents =
			coordinateStressScenarioMap.get (coordinateFQN).systemicEventContainer();

		System.out.println ("\t|----------------------------------||");

		System.out.println (
			"\t|[" + coordinateFQN + "] => " +
			coordinateStressScenarioEvents.eventType()
		);

		System.out.println ("\t|----------------------------------||");

		System.out.println ("\t|------------------------------------------------------------------------||");

		Map<String, Event> eventMap = coordinateStressScenarioEvents.eventMap();

		for (Map.Entry<String, Event> eventMapEntry : eventMap.entrySet())
		{
			Event coordinateStressEvent = eventMapEntry.getValue();

			EventSpecification stressEvent = coordinateStressEvent.specification();

			System.out.println (
				"\t\t[Name => " + stressEvent.name() + "] | " +
				"[Probability => " + FormatUtil.FormatDouble (stressEvent.probability(), 1, 4, 1.) + "] | " +
				"[PnL => " + DisplayStressEventPnL (coordinateStressEvent.aggregatePnLSeries()) + "] ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------||");
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String region = "NORTH AMERICA";
		String riskType = "CVA";

		CapitalUnitStressEventContext gocStressEventContext =
			CapitalEstimationContextManager.ContextContainer().capitalUnitStressEventContext();

		Map<String, CapitalUnitEventContainer> gocEventContainerMap = gocStressEventContext.capitalUnitEventMap();

		for (String fqn : gocStressEventContext.matchingCapitalUnitCoordinateSet (
			region,
			riskType
		))
		{
			DisplayStressScenario (
				gocEventContainerMap,
				fqn
			);
		}

		EnvManager.TerminateEnv();
	}
}
