
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>MarketSegment</i> maintains a List of the Applicable Market Segments. The References are:
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

public class MarketSegment
{

	/**
	 * The DM Market Segment
	 */

	public static final java.lang.String DM = "DM";

	/**
	 * The EM Market Segment
	 */

	public static final java.lang.String EM = "EM";

	/**
	 * The DM Low Volatility Market Segment
	 */

	public static final java.lang.String DM_LO_VOL = "DM_LO_VOL";

	/**
	 * The DM High Volatility Market Segment
	 */

	public static final java.lang.String DM_HI_VOL = "DM_HI_VOL";

	/**
	 * The EM Low Volatility Market Segment
	 */

	public static final java.lang.String EM_LO_VOL = "EM_LO_VOL";

	/**
	 * The EM High Volatility Market Segment
	 */

	public static final java.lang.String EM_HI_VOL = "EM_HI_VOL";

}
