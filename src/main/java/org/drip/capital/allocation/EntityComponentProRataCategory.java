
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EntityComponentProRataCategory</i> holds the Indicators of different Pro-Rata Categories used under the
 * 	PRO-RATA Capital Allocation Scheme. The References are:
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

public class EntityComponentProRataCategory
{

	/**
	 * Set the MARGINAL PRO-RATA Category
	 */

	public static final int MARGINAL = 1;

	/**
	 * Set the STANDALONE PRO-RATA Category
	 */

	public static final int STANDALONE = 2;

	/**
	 * Set the STANDALONE TO WORST PRO-RATA Category
	 */

	public static final int STANDALONE_TO_WORST = 3;

}
