
package org.drip.xva.hypothecation;

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
 * BurgardKjaerVertexExposure holds the Credit, the Debt, and the Funding Exposures, as well as the
 *  Collateral Balances at each Re-hypothecation Collateral Group using the Burgard Kjaer (2014) Scheme. The
 *  References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
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

public class BurgardKjaerVertexExposure implements
	org.drip.xva.hypothecation.CollateralGroupVertexExposureComponent {
	private double _dblDebt = java.lang.Double.NaN;
	private double _dblCredit = java.lang.Double.NaN;
	private double _dblFunding = java.lang.Double.NaN;
	private double _dblCollateralBalance = java.lang.Double.NaN;

	/**
	 * Generate an Initial Instance of Burgard Kjaer Vertex Exposure
	 * 
	 * @param dblUncollateralizedExposure The Uncollateralized Exposure
	 * @param cgvco Collateral Group Vertex Close Out
	 * 
	 * @return Initial Instance of Burgard Kjaer Vertex Exposure
	 */

	public static final BurgardKjaerVertexExposure Initial (
		final double dblUncollateralizedExposure,
		final org.drip.xva.hypothecation.CollateralGroupVertexCloseOut cgvco)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblUncollateralizedExposure) || null == cgvco)
			return null;

		try {
			return new BurgardKjaerVertexExposure (dblUncollateralizedExposure - cgvco.counterParty(),
				dblUncollateralizedExposure - cgvco.bank(), 0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BurgardKjaerVertexExposure Constructor
	 * 
	 * @param dblCredit The Credit Exposure of the Collateral Group
	 * @param dblDebt The Debt Exposure of the Collateral Group
	 * @param dblFunding The Funding Exposure of the Collateral Group
	 * @param dblCollateralBalance The Collateral Balance of the Collateral Group
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaerVertexExposure (
		final double dblCredit,
		final double dblDebt,
		final double dblFunding,
		final double dblCollateralBalance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCredit = dblCredit) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblDebt = dblDebt) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblFunding = dblFunding) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCollateralBalance = dblCollateralBalance))
			throw new java.lang.Exception ("BurgardKjaerVertexExposure Constructor => Invalid Inputs");
	}

	@Override public double credit()
	{
		return _dblCredit;
	}

	@Override public double debt()
	{
		return _dblDebt;
	}

	@Override public double funding()
	{
		return _dblFunding;
	}

	@Override public double collateralBalance()
	{
		return _dblCollateralBalance;
	}
}
