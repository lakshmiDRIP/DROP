
package org.drip.xva.basel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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

public class BalanceSheetVertex {
	private double _dblAsset = java.lang.Double.NaN;
	private double _dblLiability = java.lang.Double.NaN;
	private double _dblContraAsset = java.lang.Double.NaN;
	private double _dblContraLiability = java.lang.Double.NaN;
	private double _dblRetainedEarnings = java.lang.Double.NaN;

	/**
	 * BalanceSheetVertex Constructor
	 * 
	 * @param dblAsset The Asset Account
	 * @param dblLiability The Liability Account
	 * @param dblContraAsset The Contra Asset Account
	 * @param dblContraLiability The Contra Liability Account
	 * @param dblRetainedEarnings The Retained Earnings Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BalanceSheetVertex (
		final double dblAsset,
		final double dblLiability,
		final double dblContraAsset,
		final double dblContraLiability,
		final double dblRetainedEarnings)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblAsset = dblAsset) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblLiability = dblLiability) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblContraAsset = dblContraAsset) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblContraLiability = dblContraLiability) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblRetainedEarnings =
							dblRetainedEarnings))
			throw new java.lang.Exception ("BalanceSheetVertex Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Asset Account
	 * 
	 * @return The Asset Account
	 */

	public double asset()
	{
		return _dblAsset;
	}

	/**
	 * Retrieve the Liability Account
	 * 
	 * @return The Liability Account
	 */

	public double liability()
	{
		return _dblLiability;
	}

	/**
	 * Retrieve the Contra Asset Account
	 * 
	 * @return The Contra Asset Account
	 */

	public double contraAsset()
	{
		return _dblContraAsset;
	}

	/**
	 * Retrieve the Contra Liability Account
	 * 
	 * @return The Contra Liability Account
	 */

	public double contraLiability()
	{
		return _dblContraLiability;
	}

	/**
	 * Retrieve the Retained Earnings Account
	 * 
	 * @return The Retained Earnings Account
	 */

	public double retainedEarnings()
	{
		return _dblRetainedEarnings;
	}

	/**
	 * Estimate the Equity Account
	 * 
	 * @return The Equity Account
	 */

	public double equity()
	{
		return _dblAsset - _dblLiability - _dblContraAsset + _dblContraLiability + _dblRetainedEarnings;
	}

	/**
	 * Estimate the Core Equity Tier I (CET1) Capital
	 * 
	 * @return The Core Equity Tier I (CET1) Capital
	 */

	public double cet1()
	{
		return _dblAsset - _dblLiability - _dblContraAsset + _dblRetainedEarnings;
	}
}
