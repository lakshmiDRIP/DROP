
package org.drip.sample.businessspec;

import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.shell.RiskTypeContext;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>RBCRiskTypeMapping</i> zeds the RBC to the iVAST Risk Type Mapping. It also indicates if the RBC is to
 * be excluded from Consideration. The References are:
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

public class RBCRiskTypeMapping
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] rbcArray =
		{
			"CGM_ACC",
			"CGM_AFS",
			"CGM_CVAA_MTM",
			"CGM_CVAL_MTM",
			"CGM_CVA_MTM",
			"CGM_HFS", 
			"CGM_MTM",
			"CITIB_ACC",
			"CITIB_AFS",
			"CITIB_CRDT_MTM",
			"CITIB_CVAA_MTM",
			"CITIB_CVAL_MTM",
			"CITIB_CVA_MTM",
			"CITIB_HFI",
			"CITIB_HTM",
			"CITIB_IVAST_ACC",
			"CITIB_IVAST_AFS",
			"CITIB_MTM",
			"CITIB_NT_MTM",
			"CITIG_ACC",
			"CITIG_AFS",
			"CITIG_CVAA_MTM",
			"CITIG_CVAL_MTM",
			"CITIG_CVA_MTM",
			"CITIG_HFS",
			"CITIG_HTM",
			"CITIG_IVAST_ACC",
			"CITIG_MTM",
			"CITIG_NT_MTM",
		};

		RiskTypeContext riskTypeContext = CapitalEstimationContextManager.ContextContainer().riskTypeContext();

		System.out.println ("\t|---------------------------------------||");

		System.out.println ("\t|        RBC CODE RISK TYPE MAP         ||");

		System.out.println ("\t|---------------------------------------||");

		System.out.println ("\t|    L -> R:                            ||");

		System.out.println ("\t|        - RBC                          ||");

		System.out.println ("\t|        - Exclude/Include              ||");

		System.out.println ("\t|        - Risk Type                    ||");

		System.out.println ("\t|---------------------------------------||");

		for (String rbc : rbcArray)
		{
			System.out.println (
				"\t| " + rbc + " => " +
				(riskTypeContext.rbcExclusionCheck (rbc) ? "EXCLUDE" : "INCLUDE") + " | " +
				riskTypeContext.riskType (rbc) + " ||"
			);
		}

		System.out.println ("\t|---------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
