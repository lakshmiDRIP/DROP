
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CorrelationCategoryBeta</i> exposes the Correlation Category Beta Loading and its Elasticity
 * 	(FIXED/FLOAT). The References are:
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

public class CorrelationCategoryBeta
{

	/**
	 * FIXED Beta Elasticity
	 */

	public static final int ELASTICITY_FIXED = 0;

	/**
	 * FLOAT Beta Elasticity
	 */

	public static final int ELASTICITY_FLOAT = 1;

	private double _loading = java.lang.Double.NaN;
	private int _elasticity = java.lang.Integer.MIN_VALUE;

	/**
	 * CorrelationCategoryBeta Constructor
	 * 
	 * @param elasticity Beta Elasticity
	 * @param loading Beta Loading
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CorrelationCategoryBeta (
		final int elasticity,
		final double loading)
		throws java.lang.Exception
	{
		if ((ELASTICITY_FIXED != (_elasticity = elasticity) && ELASTICITY_FLOAT != _elasticity) ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_loading = loading
			)
		)
		{
			throw new java.lang.Exception (
				"CorrelationCategoryBeta Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Beta Elasticity
	 * 
	 * @return The Beta Elasticity
	 */

	public int elasticity()
	{
		return _elasticity;
	}

	/**
	 * Retrieve the Beta Loading Value
	 * 
	 * @return The Beta Loading Value
	 */

	public double loading()
	{
		return _loading;
	}
}
