
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>RiskTypeContext</i> maintains the Loaded Mapping between RBC Code and iVAST Risk Type. Unmapped RBC's
 * will be excluded. The References
 * 	are:
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

public class RiskTypeContext
{
	private java.util.Map<java.lang.String, java.lang.String> _rbcRiskTypeMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

	/**
	 * RiskTypeContext Constructor
	 * 
	 * @param rbcRiskTypeMap RBC - Risk Type Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskTypeContext (
		final java.util.Map<java.lang.String, java.lang.String> rbcRiskTypeMap)
		throws java.lang.Exception
	{
		if (null == (_rbcRiskTypeMap = rbcRiskTypeMap) || 0 == _rbcRiskTypeMap.size())
		{
			throw new java.lang.Exception ("RiskTypeContext Constructor => Invalid Inputs");
		}
	}

	/**
	 * Check if the RBC Code exists in the Risk Type Map
	 * 
	 * @param rbc The RBC Code
	 * 
	 * @return TRUE - The RBC Code Exists
	 */

	public boolean containsRBC (
		final java.lang.String rbc)
	{
		return null != rbc && !rbc.isEmpty() && _rbcRiskTypeMap.containsKey (rbc);
	}

	/**
	 * Retrieve the Risk Type given the RBC Code
	 * 
	 * @param rbc The RBC Code
	 * 
	 * @return Risk Type given the RBC Code
	 */

	public java.lang.String riskType (
		final java.lang.String rbc)
	{
		return containsRBC (rbc) ? _rbcRiskTypeMap.get (rbc) : "";
	}

	/**
	 * Check if the RBC Code is to be excluded
	 * 
	 * @param rbc The RBC Code
	 * 
	 * @return TRUE - The RBC Code must be excluded
	 */

	public boolean rbcExclusionCheck (
		final java.lang.String rbc)
	{
		return "".equalsIgnoreCase (riskType (rbc));
	}

	/**
	 * Retrieve the RBC - Risk Type Map
	 * 
	 * @return The RBC - Risk Type Map
	 */

	public java.util.Map<java.lang.String, java.lang.String> rbcRiskTypeMap()
	{
		return _rbcRiskTypeMap;
	}
}
