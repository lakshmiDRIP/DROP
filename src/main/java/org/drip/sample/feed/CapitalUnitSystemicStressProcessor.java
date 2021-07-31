
package org.drip.sample.feed;

import java.util.Map;

import org.drip.capital.entity.CapitalUnitEventContainer;
import org.drip.capital.feed.CapitalUnitStressScenarioLoader;
import org.drip.capital.stress.Event;
import org.drip.capital.stress.SystemicEventContainer;
import org.drip.capital.stress.IdiosyncraticEventContainer;
import org.drip.capital.stress.PnLSeries;
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
 * <i>CapitalUnitSystemicStressProcessor</i> zeds the Loading of the Capital Unit Systemic Stress Scenarios
 * 	from the specified Set of Input Files. The References are:
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

public class CapitalUnitSystemicStressProcessor
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		boolean skipHeader = true;
		String capitalUnitGSSTInputFile =
			"I:\\CapitalUnitRuns\\GSST_Production_Citigroup_201903_PAA Buckets.csv";
		String capitalUnitCBSSTInputFile = "I:\\CapitalUnitRuns\\CBSST_Input_Sample.csv";
		String capitalUnitIBSSTInputFile = "I:\\CapitalUnitRuns\\IBSST_Input_Sample.csv";

		Map<String, CapitalUnitEventContainer> capitalUnitEventContainerMap =
			CapitalUnitStressScenarioLoader.LoadStressScenario (
				capitalUnitCBSSTInputFile,
				capitalUnitIBSSTInputFile,
				capitalUnitGSSTInputFile,
				skipHeader
			).capitalUnitEventMap();

		int count = 0;

		for (Map.Entry<String, CapitalUnitEventContainer> capitalUnitEventContainerMapEntry :
			capitalUnitEventContainerMap.entrySet())
		{
			String capitalUnitCoordinateFQN = capitalUnitEventContainerMapEntry.getKey();

			CapitalUnitEventContainer capitalUnitEventContainer =
				capitalUnitEventContainerMapEntry.getValue();

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (++count, 3, 0, 1.) + " => " + capitalUnitCoordinateFQN
			);

			IdiosyncraticEventContainer iBSSTEventContainer = capitalUnitEventContainer.idiosyncraticEventContainer();

			if (null != iBSSTEventContainer)
			{
				for (Map.Entry<String, Event> iBSSTEventMapEntry : iBSSTEventContainer.eventMap().entrySet())
				{
					System.out.println (
						"\t\tiBSST -> " + iBSSTEventMapEntry.getKey() +
						" {" + iBSSTEventMapEntry.getValue().aggregatePnLSeries().composite() + " | " +
						iBSSTEventMapEntry.getValue().specification().probability() + "}"
					);
				}
			}

			SystemicEventContainer gsstEventContainer = capitalUnitEventContainerMap.get (
				capitalUnitCoordinateFQN
			).systemicEventContainer();

			Map<String, Event> gsstEventMap = gsstEventContainer.eventMap();

			for (Map.Entry<String, Event> gsstEventMapEntry : gsstEventMap.entrySet())
			{
				System.out.println (
					"\t\tGSST -> " + gsstEventMapEntry.getKey() +
					" {" + gsstEventMapEntry.getValue().aggregatePnLSeries().composite() + " | " +
					gsstEventMapEntry.getValue().specification().probability() + " | " +
					gsstEventMapEntry.getValue().pnlSeriesDecompositionMap() + "}"
				);

				Map<String, PnLSeries> attachedStressEventPnLMap =
					gsstEventMapEntry.getValue().attachedEventPnLSeries();

				if (null != attachedStressEventPnLMap)
				{
					System.out.println ("\t\t\tCBSST -> " + attachedStressEventPnLMap.keySet());

					for (Map.Entry<String, PnLSeries> attachedStressEventPnLEntry :
						attachedStressEventPnLMap.entrySet())
					{
						System.out.println (
							"\t\t\tcBSST -> " + attachedStressEventPnLEntry.getKey() +
							" {" + attachedStressEventPnLEntry.getValue().composite() + "}"
						);
					}
				}
			}
		}

		EnvManager.TerminateEnv();
	}
}
