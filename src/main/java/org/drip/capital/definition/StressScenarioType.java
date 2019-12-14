
package org.drip.capital.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>StressScenarioType</i> contains the Stress Scenario Types - GSST, cBSST, and iBSST. The References are:
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

public class StressScenarioType
{

	/**
	 * Stress Scenario Type - GSST
	 */

	public static final java.lang.String GSST = "GSST";

	/**
	 * Stress Scenario Type - cBSST
	 */

	public static final java.lang.String CBSST = "cBSST";

	/**
	 * Stress Scenario Type - iBSST
	 */

	public static final java.lang.String IBSST = "iBSST";

}
