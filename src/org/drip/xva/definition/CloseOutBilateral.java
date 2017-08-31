
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
 * CloseOutBilateral implements the (2002) ISDA Master Agreement Bilateral Close Out Scheme to be applied to
 *  the MTM at the Bank/Counter Party Default. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs, Risk, 24 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CloseOutBilateral extends org.drip.xva.definition.CloseOutGeneral {
	private double _dblCounterPartyRecovery = java.lang.Double.NaN;;
	private double _dblBankSeniorFundingRecovery = java.lang.Double.NaN;

	/**
	 * CloseOutBilateral Constructor
	 * 
	 * @param dblBankSeniorFundingRecovery The Bank Senior Funding Recovery Rate
	 * @param dblCounterPartyRecovery Counter Party Recovery Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CloseOutBilateral (
		final double dblBankSeniorFundingRecovery,
		final double dblCounterPartyRecovery)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBankSeniorFundingRecovery =
			dblBankSeniorFundingRecovery) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblCounterPartyRecovery = dblCounterPartyRecovery))
			throw new java.lang.Exception ("CloseOutBilateral Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Bank Senior Funding Recovery Rate
	 * 
	 * @return The Bank Senior Funding Recovery Rate
	 */

	public double bankSeniorFundingRecovery()
	{
		return _dblBankSeniorFundingRecovery;
	}

	/**
	 * Retrieve the Counter Party Recovery Rate
	 * 
	 * @return The Counter Party Recovery Rate
	 */

	public double counterPartyRecovery()
	{
		return _dblCounterPartyRecovery;
	}

	@Override public double bankDefault (
		final double dblUncollateralizedExposure,
		final double dblCollateralAmount)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblUncollateralizedExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblCollateralAmount))
			throw new java.lang.Exception ("CloseOutBilateral::bankDefault => Invalid Inputs");

		double dblCollateralizedExposure = dblUncollateralizedExposure - dblCollateralAmount;

		return (dblCollateralizedExposure > 0. ? dblCollateralizedExposure : 0.) +
			_dblBankSeniorFundingRecovery * (dblCollateralizedExposure < 0. ? dblCollateralizedExposure : 0.)
				+ dblCollateralAmount;
	}

	@Override public double counterPartyDefault (
		final double dblUncollateralizedExposure,
		final double dblCollateralAmount)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblUncollateralizedExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblCollateralAmount))
			throw new java.lang.Exception ("CloseOutBilateral::counterPartyDefault => Invalid Inputs");

		double dblCounterPartyGroupCollateralizedExposure = dblUncollateralizedExposure -
			dblCollateralAmount;

		return _dblCounterPartyRecovery * (dblCounterPartyGroupCollateralizedExposure > 0. ?
			dblCounterPartyGroupCollateralizedExposure : 0.) + (dblCounterPartyGroupCollateralizedExposure <
				0. ? dblCounterPartyGroupCollateralizedExposure : 0.) + dblCollateralAmount;
	}
}
