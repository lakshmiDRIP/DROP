
package org.drip.capital.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>VolatilityScaleFactory</i> instantiates the Built-in Risk-Factor Volatility Scale Mappings. The
 * References are:
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

public class VolatilityScaleFactory
{

	/**
	 * Instantiate the Built-in VolatilityScaleContext
	 * 
	 * @return TRUE - The VolatilityScaleContext Instance
	 */

	public static org.drip.capital.shell.VolatilityScaleContext Instantiate()
	{
		java.util.Map<java.lang.String, java.lang.Double> fsTypeAdjustmentMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		fsTypeAdjustmentMap.put (
			"NOSTRESS::CMDL",
			0.74
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::CMVG",
			0.71
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::CSVG",
			1.00
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::EBID",
			0.99
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::EBSY",
			0.99
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::ECVG",
			0.99
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::EQDL",
			0.99
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::EQVG",
			0.87
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::FXDL",
			0.91
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::FXRR",
			0.97
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::FXST",
			0.97
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::FXVG",
			0.97
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::IDIO",
			1.14
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::IRDL",
			1.36
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::IRVG",
			1.36
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::ISDL",
			1.14
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::LODL",
			1.07
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::OMDL",
			4.47
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::OSDL",
			4.47
		);

		fsTypeAdjustmentMap.put (
			"NOSTRESS::PPDL",
			1.00
		);

		try
		{
			return new org.drip.capital.shell.VolatilityScaleContext (fsTypeAdjustmentMap);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
