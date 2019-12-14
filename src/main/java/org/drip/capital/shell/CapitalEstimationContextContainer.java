
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalEstimationContextContainer</i> maintains all the Context Entities needed for a Full iVAST
 * 	Capital Estimation Run. The References are:
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

public class CapitalEstimationContextContainer
{
	private org.drip.capital.shell.RiskTypeContext _riskTypeContext = null;
	private org.drip.capital.shell.RegionDigramContext _regionDigramContext = null;
	private org.drip.capital.shell.AccountBusinessContext _accountBusinessContext = null;
	private org.drip.capital.shell.VolatilityScaleContext _volatilityScaleContext = null;
	private org.drip.capital.shell.BusinessGroupingContext _businessGroupingContext = null;
	private org.drip.capital.shell.CapitalUnitStressEventContext _capitalUnitStressEventContext = null;

	/**
	 * CapitalEstimationContextContainer Constructor
	 * 
	 * @param accountBusinessContext Account Business Context
	 * @param businessGroupingContext Business Grouping Context
	 * @param capitalUnitStressEventContext Capital Unit Stress Event Context
	 * @param regionDigramContext Region Digram Context
	 * @param riskTypeContext Risk Type Context
	 * @param volatilityScaleContext Volatility Scale Context
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalEstimationContextContainer (
		final org.drip.capital.shell.AccountBusinessContext accountBusinessContext,
		final org.drip.capital.shell.BusinessGroupingContext businessGroupingContext,
		final org.drip.capital.shell.CapitalUnitStressEventContext capitalUnitStressEventContext,
		final org.drip.capital.shell.RegionDigramContext regionDigramContext,
		final org.drip.capital.shell.RiskTypeContext riskTypeContext,
		final org.drip.capital.shell.VolatilityScaleContext volatilityScaleContext)
		throws java.lang.Exception
	{
		if (null == (_accountBusinessContext = accountBusinessContext) ||
			null == (_businessGroupingContext = businessGroupingContext) ||
			null == (_capitalUnitStressEventContext = capitalUnitStressEventContext) ||
			null == (_regionDigramContext = regionDigramContext) ||
			null == (_riskTypeContext = riskTypeContext) ||
			null == (_volatilityScaleContext = volatilityScaleContext))
		{
			throw new java.lang.Exception ("CapitalEstimationContextContainer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Account Business Context
	 * 
	 * @return Account Business Context
	 */

	public org.drip.capital.shell.AccountBusinessContext accountBusinessContext()
	{
		return _accountBusinessContext;
	}

	/**
	 * Retrieve the Business Grouping Context
	 * 
	 * @return Business Grouping Context
	 */

	public org.drip.capital.shell.BusinessGroupingContext businessGroupingContext()
	{
		return _businessGroupingContext;
	}

	/**
	 * Retrieve the Capital Unit Stress Event Context
	 * 
	 * @return Capital Unit Stress Event Context
	 */

	public org.drip.capital.shell.CapitalUnitStressEventContext capitalUnitStressEventContext()
	{
		return _capitalUnitStressEventContext;
	}

	/**
	 * Retrieve the Region Digram Context
	 * 
	 * @return Region Digram Context
	 */

	public org.drip.capital.shell.RegionDigramContext regionDigramContext()
	{
		return _regionDigramContext;
	}

	/**
	 * Retrieve the Risk Type Context
	 * 
	 * @return Risk Type Context
	 */

	public org.drip.capital.shell.RiskTypeContext riskTypeContext()
	{
		return _riskTypeContext;
	}

	/**
	 * Retrieve the Volatility Scale Context
	 * 
	 * @return Volatility Scale Context
	 */

	public org.drip.capital.shell.VolatilityScaleContext volatilityScaleContext()
	{
		return _volatilityScaleContext;
	}
}
