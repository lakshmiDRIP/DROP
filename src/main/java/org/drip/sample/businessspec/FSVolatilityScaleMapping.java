
package org.drip.sample.businessspec;

import org.drip.capital.env.CapitalEstimationContextManager;
import org.drip.capital.shell.VolatilityScaleContext;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>FSVolatilityScaleMapping</i> zeds the FS Type to their Volatility Scales. The References are:
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

public class FSVolatilityScaleMapping
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] fsTypeArray =
		{
			"CMDL",
			"CMVG",
			"CSVG",
			"EBID",
			"EBSY",
			"ECVG",
			"EQDL",
			"EQVG",
			"FXDL",
			"FXRR",
			"FXST",
			"FXVG",
			"IDIO",
			"IRDL",
			"IRVG",
			"ISDL",
			"LODL",
			"OMDL",
			"OSDL",
			"PPDL",
		};

		VolatilityScaleContext volatilityScaleContext = CapitalEstimationContextManager.ContextContainer().volatilityScaleContext();

		System.out.println ("\t|------------------------||");

		System.out.println ("\t| FS TYPE VOL ADJUST MAP ||");

		System.out.println ("\t|------------------------||");

		System.out.println ("\t|    L -> R:             ||");

		System.out.println ("\t|        - FS Type       ||");

		System.out.println ("\t|        - Vol Adjust    ||");

		System.out.println ("\t|------------------------||");

		for (String fsType : fsTypeArray)
		{
			System.out.println (
				"\t| " + fsType + " => " +
				FormatUtil.FormatDouble (volatilityScaleContext.volatilityAdjustment (fsType), 1, 2, 1.) +
				"          ||"
			);
		}

		System.out.println ("\t|------------------------||");

		EnvManager.TerminateEnv();
	}
}
