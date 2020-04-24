
package org.drip.sample.feed;

import java.util.Map;

import org.drip.capital.feed.CapitalUnitStressScenarioLoader;
import org.drip.capital.feed.CapitalUnitIdiosyncraticScenario;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnitIBSSTProcessor</i> zeds the Loading of the Capital Unit iBSST Scenarios from the specified
 * 	Input File. The References are:
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

public class CapitalUnitIBSSTProcessor
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String capitalUnitIBSSTInputFile = "I:\\CapitalUnitRuns\\IBSST_Input_Sample.csv";

		Map<String, CapitalUnitIdiosyncraticScenario> capitalUnitIBSSTScenarioMap =
			CapitalUnitStressScenarioLoader.LoadIdiosyncratic (
				capitalUnitIBSSTInputFile,
				true
			);

		for (Map.Entry<String, CapitalUnitIdiosyncraticScenario> capitalUnitIBSSTScenarioEntry :
			capitalUnitIBSSTScenarioMap.entrySet())
		{
			System.out.println (
				"\t" + capitalUnitIBSSTScenarioEntry.getKey() + " => " +
				capitalUnitIBSSTScenarioEntry.getValue()
			);
		}

		EnvManager.TerminateEnv();
	}
}
