
package org.drip.capital.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalEstimationContextManager</i> initializes the iVAST Capital Estimation Context Settings. The
 * 	References are:
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

public class CapitalEstimationContextManager
{
	private static org.drip.capital.shell.CapitalEstimationContextContainer
		s_CapitalEstimationContextContainer = null;

	/**
	 * Initialize the Capital Estimation Context Manager
	 * 
	 * @return TRUE - The Capital Estimation Context Manager successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_CapitalEstimationContextContainer =
				new org.drip.capital.shell.CapitalEstimationContextContainer (
					org.drip.capital.env.AccountBusinessFactory.Instantiate(),
					org.drip.capital.env.BusinessGroupingFactory.Instantiate(),
					org.drip.capital.env.CapitalUnitStressEventFactory.Instantiate(),
					org.drip.capital.env.RegionDigramFactory.Instantiate(),
					org.drip.capital.env.RiskTypeFactory.Instantiate(),
					org.drip.capital.env.VolatilityScaleFactory.Instantiate()
				);

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Built-in Capital Estimation Context Container
	 * 
	 * @return The Built-in Capital Estimation Context Container
	 */

	public static final org.drip.capital.shell.CapitalEstimationContextContainer ContextContainer()
	{
		return s_CapitalEstimationContextContainer;
	}
}
