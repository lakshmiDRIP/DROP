
package org.drip.capital.setting;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>HorizonTailPnLControl</i> holds the Horizon/Tail Adjustment Control Parameters. The References are:
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

public abstract class HorizonTailPnLControl
{
	private int _horizon = -1;
	private double _degreesOfFreedom = java.lang.Double.NaN;
	private double _varConfidenceLevel = java.lang.Double.NaN;
	private double _expectedShortfallConfidenceLevel = java.lang.Double.NaN;

	/**
	 * Construct the Standard Stress Instance of HorizonTailPnLControl
	 * 
	 * @return Standard Stress Instance of HorizonTailPnLControl
	 */

	public static final HorizonTailPnLControl StandardStress()
	{
		try
		{
			return new HorizonTailPnLControl (
				1,
				5.,
				0.9997,
				0.98
			)
			{
				@Override public double tailDistributionScaler()
				{
					return 2.;
				}

				@Override public double grossScaler()
				{
					return 2.;
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
	 * HorizonTailPnLControl Constructor
	 * 
	 * @param horizon Horizon
	 * @param degreesOfFreedom PnL Distribution Degrees of Freedom
	 * @param varConfidenceLevel VaR Confidence Level
	 * @param expectedShortfallConfidenceLevel Expected Short-fall Confidence Level
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HorizonTailPnLControl (
		final int horizon,
		final double degreesOfFreedom,
		final double varConfidenceLevel,
		final double expectedShortfallConfidenceLevel)
		throws java.lang.Exception
	{
		if (0 >= (_horizon = horizon) ||
			java.lang.Double.isNaN (_degreesOfFreedom = degreesOfFreedom) || 0. >= _degreesOfFreedom ||
			!org.drip.numerical.common.NumberUtil.IsValid (_varConfidenceLevel = varConfidenceLevel) ||
				0. >= _varConfidenceLevel || 1. <= _varConfidenceLevel ||
			!org.drip.numerical.common.NumberUtil.IsValid
				(_expectedShortfallConfidenceLevel = expectedShortfallConfidenceLevel) ||
				0. >= _expectedShortfallConfidenceLevel || 1. <= _expectedShortfallConfidenceLevel)
		{
			throw new java.lang.Exception ("HorizonTailPnLControl Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Horizon in Days
	 * 
	 * @return Horizon in Days
	 */

	public int horizon()
	{
		return _horizon;
	}

	/**
	 * Retrieve the PnL Distribution Degrees of Freedom
	 * 
	 * @return PnL Distribution Degrees of Freedom
	 */

	public double degreesOfFreedom()
	{
		return _degreesOfFreedom;
	}

	/**
	 * Retrieve the VaR Confidence Level
	 * 
	 * @return VaR Confidence Level
	 */

	public double varConfidenceLevel()
	{
		return _varConfidenceLevel;
	}

	/**
	 * Retrieve the Expected Short-fall Confidence Level
	 * 
	 * @return Expected Short-fall Confidence Level
	 */

	public double expectedShortfallConfidenceLevel()
	{
		return _expectedShortfallConfidenceLevel;
	}

	/**
	 * Retrieve the Horizon Scaler
	 * 
	 * @return Horizon Scaler
	 */

	public double horizonScaler()
	{
		return java.lang.Math.sqrt (_horizon);
	}

	/**
	 * Retrieve the Tail Distribution Scaler
	 * 
	 * @return Tail Distribution Scaler
	 */

	public abstract double tailDistributionScaler();

	/**
	 * Retrieve the Gross (Horizon X Tail) Scaler
	 * 
	 * @return Gross (Horizon X Tail) Scaler
	 */

	public double grossScaler()
	{
		return horizonScaler() * tailDistributionScaler();
	}
}
