
package org.drip.capital.setting;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>HorizonTailFSPnLControl</i> holds the Horizon, Tail, and Risk Factor FS Volatility Adjustment Control
 * Parameters. The References are:
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

public abstract class HorizonTailFSPnLControl extends org.drip.capital.setting.HorizonTailPnLControl
{
	private java.util.Map<java.lang.String, java.lang.Double> _fsTypeVolatilityAjustmentMap = null;

	/**
	 * Construct the Standard Instance of HorizonTailFSPnLControl
	 * 
	 * @return Standard Instance of HorizonTailFSPnLControl
	 */

	public static final HorizonTailFSPnLControl Standard()
	{
		try
		{
			return new HorizonTailFSPnLControl (
				260,
				java.lang.Double.POSITIVE_INFINITY,
				0.9997,
				0.98,
				org.drip.capital.env.CapitalEstimationContextManager.ContextContainer().volatilityScaleContext().fsTypeAdjustmentMap()
			)
			{
				@Override public double tailDistributionScaler()
				{
					return 1.414;
				}

				@Override public double grossScaler()
				{
					return 22.8;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * HorizonTailFSPnLControl Constructor
	 * 
	 * @param horizon Horizon
	 * @param degreesOfFreedom Degrees of Freedom
	 * @param varConfidenceLevel VaR Confidence Level
	 * @param expectedShortfallConfidenceLevel Expected Short-fall Confidence Level
	 * @param fsTypeVolatilityAjustmentMap FS Type Volatility Adjustment Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HorizonTailFSPnLControl (
		final int horizon,
		final double degreesOfFreedom,
		final double varConfidenceLevel,
		final double expectedShortfallConfidenceLevel,
		final java.util.Map<java.lang.String, java.lang.Double> fsTypeVolatilityAjustmentMap)
		throws java.lang.Exception
	{
		super (
			horizon,
			degreesOfFreedom,
			varConfidenceLevel,
			expectedShortfallConfidenceLevel
		);

		if (null == (_fsTypeVolatilityAjustmentMap = fsTypeVolatilityAjustmentMap))
		{
			throw new java.lang.Exception ("HorizonTailFSPnLControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the FS Type Volatility Adjustment Map
	 * 
	 * @return FS Type Volatility Adjustment Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> fsTypeVolatilityAjustmentMap()
	{
		return _fsTypeVolatilityAjustmentMap;
	}
}
