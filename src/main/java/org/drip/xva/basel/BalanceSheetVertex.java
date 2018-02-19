
package org.drip.xva.basel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * BalanceSheetVertex implements the Balance Sheet Vertex Component of the Streamlined Accounting Framework
 *  for OTC Derivatives, as described in Albanese and Andersen (2014). The References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955, eSSRN.
 *  
 *  - BCBS (2012): Consultative Document: Application of Own Credit Risk Adjustments to Derivatives, Basel
 *  	Committee on Banking Supervision.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BalanceSheetVertex
{
	private double _cash = java.lang.Double.NaN;
	private double _asset = java.lang.Double.NaN;
	private double _liability = java.lang.Double.NaN;
	private double _contraAsset = java.lang.Double.NaN;
	private double _contraLiability = java.lang.Double.NaN;
	private double _retainedEarnings = java.lang.Double.NaN;

	/**
	 * Unrealized Instance of BalanceSheetVertex
	 * 
	 * @param asset The Asset Account
	 * @param liability The Liability Account
	 * @param contraAsset The Contra Asset Account
	 * @param contraLiability The Contra Liability Account
	 * @param retainedEarnings The Retained Earnings Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final BalanceSheetVertex Unrealized (
		final double asset,
		final double liability,
		final double contraAsset,
		final double contraLiability,
		final double retainedEarnings)
		throws java.lang.Exception
	{
		try
		{
			return new BalanceSheetVertex (
				asset,
				liability,
				contraAsset,
				contraLiability,
				retainedEarnings,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BalanceSheetVertex Constructor
	 * 
	 * @param asset The Asset Account
	 * @param liability The Liability Account
	 * @param contraAsset The Contra Asset Account
	 * @param contraLiability The Contra Liability Account
	 * @param retainedEarnings The Retained Earnings Account
	 * @param cash The Cash Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BalanceSheetVertex (
		final double asset,
		final double liability,
		final double contraAsset,
		final double contraLiability,
		final double retainedEarnings,
		final double cash)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_cash = cash) ||
			!org.drip.quant.common.NumberUtil.IsValid (_asset = asset) ||
			!org.drip.quant.common.NumberUtil.IsValid (_liability = liability) ||
			!org.drip.quant.common.NumberUtil.IsValid (_contraAsset = contraAsset) ||
			!org.drip.quant.common.NumberUtil.IsValid (_contraLiability = contraLiability) ||
			!org.drip.quant.common.NumberUtil.IsValid (_retainedEarnings = retainedEarnings))
		{
			throw new java.lang.Exception ("BalanceSheetVertex Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Asset Account
	 * 
	 * @return The Asset Account
	 */

	public double asset()
	{
		return _asset;
	}

	/**
	 * Retrieve the Liability Account
	 * 
	 * @return The Liability Account
	 */

	public double liability()
	{
		return _liability;
	}

	/**
	 * Retrieve the Contra Asset Account
	 * 
	 * @return The Contra Asset Account
	 */

	public double contraAsset()
	{
		return _contraAsset;
	}

	/**
	 * Retrieve the Contra Liability Account
	 * 
	 * @return The Contra Liability Account
	 */

	public double contraLiability()
	{
		return _contraLiability;
	}

	/**
	 * Retrieve the Retained Earnings Account
	 * 
	 * @return The Retained Earnings Account
	 */

	public double retainedEarnings()
	{
		return _retainedEarnings;
	}

	/**
	 * Retrieve the Cash Account
	 * 
	 * @return The Cash Account
	 */

	public double cash()
	{
		return _cash;
	}

	/**
	 * Estimate the Portfolio Value (PFV)
	 * 
	 * @return The Portfolio Value (PFV)
	 */

	public double pfv()
	{
		return _asset - _liability;
	}

	/**
	 * Estimate the Equity Account
	 * 
	 * @return The Equity Account
	 */

	public double equity()
	{
		return _cash + _asset - _liability - _contraAsset + _contraLiability + _retainedEarnings;
	}

	/**
	 * Estimate the Core Equity Tier I (CET1) Capital
	 * 
	 * @return The Core Equity Tier I (CET1) Capital
	 */

	public double cet1()
	{
		return _cash + _asset - _liability - _contraAsset + _retainedEarnings;
	}
}
