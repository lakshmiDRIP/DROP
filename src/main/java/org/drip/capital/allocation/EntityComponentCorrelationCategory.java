
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EntityComponentCorrelationCategory</i> holds the Indicators of different Correlation Categories used
 * 	under the BETA Capital Allocation Scheme. The References are:
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

public class EntityComponentCorrelationCategory
{

	/**
	 * Set the HEDGE Correlation Category
	 */

	public static final int HEDGE = 0;

	/**
	 * Set the HIGH Historical Revenue Correlation Category
	 */

	public static final int HIGH_CORRELATION = 1;

	/**
	 * Set the MEDIUM Historical Revenue Correlation Category
	 */

	public static final int MEDIUM_CORRELATION = 2;

	/**
	 * Set the LOW Historical Revenue Correlation Category
	 */

	public static final int LOW_CORRELATION = 3;

	/**
	 * Defer the Correlation Category to that of the Entity
	 */

	public static final int ENTITY_CORRELATION = 4;

}
