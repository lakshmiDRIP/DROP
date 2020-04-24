
package org.drip.sample.systemicstress;

import java.util.Map;

import org.drip.capital.env.SystemicScenarioDesignContextManager;
import org.drip.capital.systemicscenario.CreditSpreadEvent;
import org.drip.capital.systemicscenario.Criterion;
import org.drip.numerical.common.FormatUtil;
import org.drip.numerical.common.NumberUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CreditSpreadEventDesign</i> zeds the Built-in Credit Spread Events used for GSST Scenario Design. The
 * 	References are:
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

public class CreditSpreadEventDesign
{

	private static final String CriterionValue (
		final double value,
		final int lefttOfDecimal,
		final int rightOfDecimal)
		throws Exception
	{
		return !NumberUtil.IsValid (value) ? " N/A" :
			FormatUtil.FormatDouble (value, lefttOfDecimal, rightOfDecimal, 1.);
	}

	private static final void DisplayCriterion (
		final Criterion criterion)
		throws Exception
	{
		System.out.println (
			"\t|            - " + criterion.name() + " => " + criterion.description()
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		System.out.println ("\t|---------------------------------------------------------------------------|");

		System.out.println ("\t|                 GSST SCENARIO DESIGN CREDIT SPREAD EVENTS                 |");

		System.out.println ("\t|---------------------------------------------------------------------------|");

		boolean headerPass = true;

		for (Map.Entry<Integer, CreditSpreadEvent> creditSpreadEventMapEntry :
			SystemicScenarioDesignContextManager.CreditSpreadEventContainer().creditSpreadEventMap().entrySet())
		{
			CreditSpreadEvent creditSpreadEvent = creditSpreadEventMapEntry.getValue();

			if (headerPass)
			{
				headerPass = false;

				System.out.println ("\t|    L -> R:");

				System.out.println ("\t|            - Credit Spread Event Index");

				System.out.println ("\t|            - Credit Spread Event Scenario Name");

				DisplayCriterion (
					creditSpreadEvent.baaSpreadChange()
				);

				DisplayCriterion (
					creditSpreadEvent.snp500Return()
				);

				DisplayCriterion (
					creditSpreadEvent.ust5YChange()
				);

				DisplayCriterion (
					creditSpreadEvent.ust10YMinus3MChange()
				);

				DisplayCriterion (
					creditSpreadEvent.fxChange()
				);

				DisplayCriterion (
					creditSpreadEvent.wtiSpotReturn()
				);

				DisplayCriterion (
					creditSpreadEvent.snpGSCI()
				);

				System.out.println ("\t|---------------------------------------------------------------------------|");
			}

			System.out.println (
				"\t|" + FormatUtil.FormatDouble (creditSpreadEventMapEntry.getKey(), 2, 0, 1.) + " => " +
				creditSpreadEvent.scenario() + " | " +
				FormatUtil.FormatDouble (creditSpreadEvent.baaSpreadChange().value(), 3, 0, 1., false) + " | " +
				CriterionValue (creditSpreadEvent.snp500Return().value(), 3, 1) + "% | " +
				CriterionValue (creditSpreadEvent.ust5YChange().value(), 3, 0) + " | " +
				CriterionValue (creditSpreadEvent.ust10YMinus3MChange().value(), 3, 0) + " | " +
				CriterionValue (creditSpreadEvent.fxChange().value(), 1, 1) + "% | " +
				CriterionValue (creditSpreadEvent.wtiSpotReturn().value(), 3, 1) + "% | " +
				CriterionValue (creditSpreadEvent.snpGSCI().value(), 2, 1) + "%"
			);
		}

		System.out.println ("\t|---------------------------------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
