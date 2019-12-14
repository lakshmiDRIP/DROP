
package org.drip.capital.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>RiskType</i> maintains the C<sup>1</sup> Fixings for the Risk Type Categorical Variate. The References
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

public class RiskType
{

	/**
	 * AFS Risk Type
	 */

	public static final java.lang.String AFS = "AFS";

	/**
	 * CVA Risk Type
	 */

	public static final java.lang.String CVA = "CVA";

	/**
	 * Pension Risk Type
	 */

	public static final java.lang.String PENSION = "Pension";

	/**
	 * Trading Risk Type
	 */

	public static final java.lang.String TRADING = "Trading";

}
