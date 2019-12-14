
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>FSPnLDecomposition</i> holds the Per FS PnL Decomposition. The References are:
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

public class FSPnLDecomposition
{
	private java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> _fsMap = null;

	/**
	 * Construct a Standard Instance of FSPnLDecomposition
	 * 
	 * @param notional Notional the PnL is based upon
	 * 
	 * @return Standard Instance of FSPnLDecomposition
	 */

	public static final FSPnLDecomposition Standard (
		final double notional)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (notional))
		{
			return null;
		}

		java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> fsMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.stress.PnLSeries>();

		java.util.Set<java.lang.String> fsTypeSet =
			org.drip.capital.env.CapitalEstimationContextManager.ContextContainer().volatilityScaleContext().fsTypeAdjustmentMap().keySet();

		for (java.lang.String fsType : fsTypeSet)
		{
			try
			{
				fsMap.put (
					fsType,
					new org.drip.capital.stress.PnLSeries (
						new double[]
						{
							notional * (java.lang.Math.random() - 0.5),
							notional * (java.lang.Math.random() - 0.5),
							notional * (java.lang.Math.random() - 0.5),
						}
					)
					{
						@Override public double composite()
						{
							double sum = 0.;
	
							double[] outcomeArray = outcomeArray();

							for (double outcome : outcomeArray)
							{
								sum = sum + outcome;
							}
	
							return sum / java.lang.Math.sqrt (outcomeArray.length);
						}
					}
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		try
		{
			return new FSPnLDecomposition (fsMap);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FSPnLDecomposition Constructor
	 * 
	 * @param fsMap FS PnL Decomposition Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FSPnLDecomposition (
		final java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> fsMap)
		throws java.lang.Exception
	{
		if (null == (_fsMap = fsMap))
		{
			throw new java.lang.Exception ("FSPnLDecomposition Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the FS PnL Decomposition Map
	 * 
	 * @return FS PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.stress.PnLSeries> fsMap()
	{
		return _fsMap;
	}

	/**
	 * Retrieve the Cross-RF Gross PnL
	 * 
	 * @return Cross-RF Gross PnL
	 */

	public double grossPnL()
	{
		if (null == _fsMap || 0 == _fsMap.size())
		{
			return 0.;
		}

		double total = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.stress.PnLSeries> fsEntry :
			_fsMap.entrySet())
		{
			total = total + fsEntry.getValue().composite();
		}

		return total;
	}

	/**
	 * Apply the FS Type Specific Volatility Scaling to the PnL Decomposition
	 * 
	 * @param fsTypeAdjustmentMap FS Type Volatility Adjustment Map
	 * @param pnlScaler The PnL Scaler
	 * 
	 * @return Volatility Adjusted FS PnL Decomposition Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> applyVolatilityAdjustment (
		final java.util.Map<java.lang.String, java.lang.Double> fsTypeAdjustmentMap,
		final double pnlScaler)
	{
		if (null == fsTypeAdjustmentMap || 0 == fsTypeAdjustmentMap.size() ||
			!org.drip.numerical.common.NumberUtil.IsValid (pnlScaler))
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> volatilityAdjustedFSMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.stress.PnLSeries> fsMapEntry :
			_fsMap.entrySet())
		{
			java.lang.String fsType = fsMapEntry.getKey();

			if (!fsTypeAdjustmentMap.containsKey (fsType))
			{
				return null;
			}

			volatilityAdjustedFSMap.put (
				fsType,
				fsMapEntry.getValue().composite() * fsTypeAdjustmentMap.get (fsType) * pnlScaler
			);
		}

		return volatilityAdjustedFSMap;
	}
}
