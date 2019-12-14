
package org.drip.capital.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>RiskTypeFactory</i> instantiates the Built-in Mapping between RBC Code and iVAST Risk Type. Unmapped
 * RBC's will be excluded. The References are:
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

public class RiskTypeFactory
{

	/**
	 * Instantiate the Built-in RiskTypeContext
	 * 
	 * @return TRUE - The RiskTypeContext Instance
	 */

	public static org.drip.capital.shell.RiskTypeContext Instantiate()
	{
		java.util.Map<java.lang.String, java.lang.String> rbcRiskTypeMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

		rbcRiskTypeMap.put (
			"CGM_ACC",
			""
		);

		rbcRiskTypeMap.put (
			"CGM_AFS",
			""
		);

		rbcRiskTypeMap.put (
			"CGM_CVAA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CGM_CVAL_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CGM_CVA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CGM_HFS",
			""
		);

		rbcRiskTypeMap.put (
			"CGM_MTM",
			"Trading"
		);

		rbcRiskTypeMap.put (
			"CITIB_ACC",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_AFS",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_CRDT_MTM",
			"Trading"
		);

		rbcRiskTypeMap.put (
			"CITIB_CVAA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CITIB_CVAL_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_CVA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CITIB_HFI",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_HTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIB_IVAST_ACC",
			"AFS"
		);

		rbcRiskTypeMap.put (
			"CITIB_IVAST_AFS",
			"AFS"
		);

		rbcRiskTypeMap.put (
			"CITIB_MTM",
			"Trading"
		);

		rbcRiskTypeMap.put (
			"CITIB_NT_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_ACC",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_AFS",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_CVAA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CITIG_CVAL_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_CVA_MTM",
			"CVA"
		);

		rbcRiskTypeMap.put (
			"CITIG_HFS",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_HTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_NT_MTM",
			""
		);

		rbcRiskTypeMap.put (
			"CITIG_IVAST_ACC",
			"AFS"
		);

		rbcRiskTypeMap.put (
			"CITIG_MTM",
			"Trading"
		);

		try
		{
			return new org.drip.capital.shell.RiskTypeContext (rbcRiskTypeMap);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
