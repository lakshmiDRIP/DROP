
package org.drip.sample.feed;

import java.util.Map;

import org.drip.capital.feed.CapitalUnitStressScenarioLoader;
import org.drip.capital.feed.CapitalUnitCBSSTScenario;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalUnitCBSSTProcessor</i> zeds the Loading of the Capital Unit cBSST Scenarios from the specified
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

public class CapitalUnitCBSSTProcessor
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String capitalUnitCBSSTInputFile = "I:\\CapitalUnitRuns\\CBSST_Input_Sample.csv";

		Map<String, CapitalUnitCBSSTScenario> capitalUnitCBSSTScenarioMap =
			CapitalUnitStressScenarioLoader.LoadCBSST (
				capitalUnitCBSSTInputFile,
				true
			);

		for (Map.Entry<String, CapitalUnitCBSSTScenario> capitalUnitCBSSTScenarioEntry :
			capitalUnitCBSSTScenarioMap.entrySet())
		{
			System.out.println (
				"\t" + capitalUnitCBSSTScenarioEntry.getKey() + " => " +
				capitalUnitCBSSTScenarioEntry.getValue()
			);
		}

		EnvManager.TerminateEnv();
	}
}
