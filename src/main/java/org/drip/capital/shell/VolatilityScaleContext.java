
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>VolatilityScaleContext</i> maintains the Loaded Risk-Factor Volatility Scale Mappings. The References
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

public class VolatilityScaleContext
{
	private java.util.Map<java.lang.String, java.lang.Double> _fsTypeAdjustmentMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * VolatilityScaleContext Constructor
	 * 
	 * @param fsTypeAdjustmentMap FS Type to Volatility Adjustment Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public VolatilityScaleContext (
		final java.util.Map<java.lang.String, java.lang.Double> fsTypeAdjustmentMap)
		throws java.lang.Exception
	{
		if (null == (_fsTypeAdjustmentMap = fsTypeAdjustmentMap) || 0 == _fsTypeAdjustmentMap.size())
		{
			throw new java.lang.Exception ("VolatilityScaleContext Constructor => Invalid Inputs");
		}
	}

	/**
	 * Check for the Existence of the FS Type
	 * 
	 * @param fsType FS Type
	 * 
	 * @return TRUE - The FS Type exists
	 */

	public boolean containsFSType (
		final java.lang.String fsType)
	{
		return null != fsType && !fsType.isEmpty() && _fsTypeAdjustmentMap.containsKey (fsType);
	}

	/**
	 * Retrieve the Volatility Adjustment corresponding to the FS Type
	 * 
	 * @param fsType FS Type
	 * 
	 * @return Volatility Adjustment corresponding to the FS Type
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double volatilityAdjustment (
		final java.lang.String fsType)
		throws java.lang.Exception
	{
		if (!containsFSType (fsType))
		{
			throw new java.lang.Exception ("VolatilityScaleContext::volatilityAdjustment => Invalid Inputs");
		}

		return _fsTypeAdjustmentMap.get (fsType);
	}

	/**
	 * Retrieve the FS Type to Volatility Adjustment Map
	 * 
	 * @return The FS Type to Volatility Adjustment Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> fsTypeAdjustmentMap()
	{
		return _fsTypeAdjustmentMap;
	}
}
