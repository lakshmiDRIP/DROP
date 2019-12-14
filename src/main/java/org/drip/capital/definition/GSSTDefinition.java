
package org.drip.capital.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>GSSTDefinition</i> holds the various GSST Definitions. The References are:
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

public class GSSTDefinition
{

	/**
	 * 2008 Baseline GSST Scenario
	 */

	public static final java.lang.String BASELINE_2008 = "2008 Baseline";

	/**
	 * 1974 Baseline GSST Scenario
	 */

	public static final java.lang.String BASELINE_1974 = "1974 Baseline";

	/**
	 * Deep Down-turn GSST Scenario
	 */

	public static final java.lang.String DEEP_DOWNTURN = "Deep Downturn";

	/**
	 * Dollar Decline GSST Scenario
	 */

	public static final java.lang.String DOLLAR_DECLINE = "Dollar Decline";

	/**
	 * Interest-Rate Shock GSST Scenario
	 */

	public static final java.lang.String INTEREST_RATE_SHOCK = "Interest Rate Shock";

	/**
	 * Lost Decade GSST Scenario
	 */

	public static final java.lang.String LOST_DECADE = "Lost Decade";

}
