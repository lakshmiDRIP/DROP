
package org.drip.xva.definition;

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
 * SimpleBalanceSheet implements a Simple Bank Balance Sheet Model as specified in Burgard and Kjaer (2012).
 *  The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2011): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2012): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SimpleBalanceSheet {
	private double _dblAsset = java.lang.Double.NaN;
	private double _dblLiability = java.lang.Double.NaN;

	/**
	 * SimpleBalanceSheet Constructor
	 * 
	 * @param dblAsset The Balance Sheet Asset
	 * @param dblLiability The Balance Sheet Liability
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SimpleBalanceSheet (
		final double dblAsset,
		final double dblLiability)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblAsset = dblAsset) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblLiability = dblLiability))
			throw new java.lang.Exception ("SimpleBalanceSheet Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Balance Sheet Asset
	 * 
	 * @return The Balance Sheet Asset
	 */

	public double asset()
	{
		return _dblAsset;
	}

	/**
	 * Retrieve the Balance Sheet Liability
	 * 
	 * @return The Balance Sheet Liability
	 */

	public double liability()
	{
		return _dblLiability;
	}

	/**
	 * Retrieve the Balance Sheet Implied Recovery
	 * 
	 * @return The Balance Sheet Implied Recovery
	 */

	public double impliedRecovery()
	{
		return _dblAsset / _dblLiability;
	}

	/**
	 * Generate the Updated Balance Sheet resulting from a Derivative Value Infusion
	 * 
	 * @param dblDerivativeValue The Derivative Value
	 * 
	 * @return The Updated Balance Sheet
	 */

	public SimpleBalanceSheet derivativeInfusion (
		final double dblDerivativeValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblDerivativeValue) || 0. > dblDerivativeValue)
			return null;

		try {
			return new SimpleBalanceSheet (_dblAsset + dblDerivativeValue, _dblLiability +
				dblDerivativeValue);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
