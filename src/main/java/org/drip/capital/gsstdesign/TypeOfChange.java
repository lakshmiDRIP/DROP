
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>TypeOfChange</i> maintains a List of the Possible Types of Change. The References are:
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

public class TypeOfChange
{

	/**
	 * No CHange
	 */

	public static final java.lang.String NONE = "None";

	/**
	 * Scenario GDP Growth Q/Q-4 Change Type
	 */

	public static final java.lang.String SCENARIO_GDP_GROWTH = "Scenario GDP Growth Q/Q-4";

	/**
	 * Peak vs. Current Level Change Type
	 */

	public static final java.lang.String PEAK_VS_CURRENT_LEVEL = "Peak vs. Current Level";

	/**
	 * Change vs. Current Q/Q-4 Change Type
	 */

	public static final java.lang.String CHANGE_VS_CURRENT = "Change vs. Current Q/Q-4";

	/**
	 * Change vs. 4 Q Forward Change Type
	 */

	public static final java.lang.String CHANGE_VS_4_Q_FORWARD = "Change vs. 4 Q Forward";

	/**
	 * Change as % of Calendar 2008 Spread Widening Change Type
	 */

	public static final java.lang.String CHANGE_AS_PERCENT_OF_CALENDAR_2008_SPREAD_WIDENING =
		"Change as % of Calendar 2008 Spread Widening";

	/**
	 * Volatility Point Change as % Calendar 2008 Volatility Point Change Type
	 */

	public static final java.lang.String VOLATILITY_POINT_CHANGE_AS_PERCENT_OF_2008_VOLATILITY_POINT_CHANGE =
		"Volatility Point Change as % Calendar 2008 Volatility Point Change";

}
