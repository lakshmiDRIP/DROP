
package org.drip.sample.feed;

import java.util.Map;

import org.drip.capital.entity.CapitalUnitEventContainer;
import org.drip.capital.feed.CapitalUnitStressScenarioLoader;
import org.drip.capital.stress.Event;
import org.drip.capital.stress.GSSTEventContainer;
import org.drip.capital.stress.IBSSTEventContainer;
import org.drip.capital.stress.PnLSeries;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
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

			IBSSTEventContainer iBSSTEventContainer = capitalUnitEventContainer.iBSSTEventContainer();

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

			GSSTEventContainer gsstEventContainer = capitalUnitEventContainerMap.get (
				capitalUnitCoordinateFQN
			).gsstEventContainer();

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
