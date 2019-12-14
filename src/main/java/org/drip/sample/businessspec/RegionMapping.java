
package org.drip.sample.businessspec;

import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.shell.RegionDigramContext;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>RegionMapping</i> zeds the Region Digrams to the Full Region Names. The References are:
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

public class RegionMapping
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] regionDigramArray =
		{
			"AP",
			"AU",
			"CE",
			"EU",
			"JP",
			"LA",
			"MX",
			"NA",
		};

		RegionDigramContext regionDigramContext = CapitalEstimationContextManager.ContextContainer().regionDigramContext();

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|   REGION DIGRAM MAP    ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|    L -> R:             ||");

		System.out.println ("\t|        - Digram        ||");

		System.out.println ("\t|        - Region        ||");

		System.out.println ("\t|------------------------||");

		for (String digram : regionDigramArray)
		{
			System.out.println ("\t| " + digram + " => " + regionDigramContext.region (digram));
		}

		System.out.println ("\t|------------------------||");

		EnvManager.TerminateEnv();
	}
}
