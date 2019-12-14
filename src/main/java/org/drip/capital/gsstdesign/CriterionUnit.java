
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CriterionUnit</i> maintains a List of the Possible Criterion Units. The References are:
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

public class CriterionUnit
{

	/**
	 * The BASIS POINT Criterion Unit
	 */

	public static final int BASIS_POINT = 1;

	/**
	 * The PERCENT Criterion Unit
	 */

	public static final int PERCENT = 2;

	/**
	 * The ABSOLUTE Criterion Unit
	 */

	public static final int ABSOLUTE = 4;

	/**
	 * The PERCENTAGE POINT Criterion Unit
	 */

	public static final int PERCENT_POINT = 8;

	/**
	 * The VOLATILITY POINT Criterion Unit
	 */

	public static final int VOLATILITY_POINT = 16;

}
