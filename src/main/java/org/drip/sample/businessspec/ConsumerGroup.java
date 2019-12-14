
package org.drip.sample.businessspec;

import java.util.Set;

import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.label.BusinessGrouping;
import org.drip.capital.shell.BusinessGroupingContext;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>ConsumerGroup</i> zeds the Businesses belonging to the Consumer Group. The References are:
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

public class ConsumerGroup
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String group = "CitiCorp - Consumer";

		BusinessGroupingContext businessGroupingContext = CapitalEstimationContextManager.ContextContainer().businessGroupingContext();

		Set<String> businessSet = businessGroupingContext.businessSetFromGroup (group);

		System.out.println ("\t|--------------------------------------------------------------------||");

		System.out.println ("\t|              CITIGROUP CONSUMER GROUP BUSINESS UNITS               ||");

		System.out.println ("\t|--------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                     ||");

		System.out.println ("\t|                - Group                                             ||");

		System.out.println ("\t|                - Product                                           ||");

		System.out.println ("\t|                - Business                                          ||");

		System.out.println ("\t|--------------------------------------------------------------------||");

		for (String business : businessSet)
		{
			BusinessGrouping businessGrouping = businessGroupingContext.businessGrouping (business);

			System.out.println (
				"\t| " +
				businessGrouping.group() + " | " +
				businessGrouping.product() + " | " +
				businessGrouping.business()
			);
		}

		System.out.println ("\t|--------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
