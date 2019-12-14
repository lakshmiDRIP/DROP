
package org.drip.capital.gsstdesign;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalBaselineDefinition</i> holds the Capital Baseline Estimates for the Historical Scenarios. The
 *	References are:
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

public class CapitalBaselineDefinition
{
	private double _fy1974 = java.lang.Double.NaN;
	private double _fy2008 = java.lang.Double.NaN;

	/**
	 * CapitalBaselineDefinition Constructor
	 * 
	 * @param fy1974 FY 1974 Historical Realization
	 * @param fy2008 FY 2008 Historical Realization
	 */

	public CapitalBaselineDefinition (
		final double fy1974,
		final double fy2008)
	{
		_fy1974 = fy1974;
		_fy2008 = fy2008;
	}

	/**
	 * Retrieve the FY 1974 Historical Realization
	 * 
	 * @return The FY 1974 Historical Realization
	 */

	public double fy1974()
	{
		return _fy1974;
	}

	/**
	 * Retrieve the FY 2008 Historical Realization
	 * 
	 * @return The FY 2008 Historical Realization
	 */

	public double fy2008()
	{
		return _fy2008;
	}
}
