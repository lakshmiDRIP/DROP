
package org.drip.portfolioconstruction.allocator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * RiskUtilitySettingsEstimator contains Utility Functions that help estimate the CustomRiskUtilitySettings
 *  Inputs Parameters.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RiskUtilitySettingsEstimator {

	/**
	 * Compute the Equilibrium Risk Aversion from the Portfolio Equilibrium Returns/Variance and the Risk
	 *  Free Rate
	 * 
	 * @param dblEquilibriumReturns The Portfolio Equilibrium Returns
	 * @param dblRiskFreeRate The Risk Free Rate
	 * @param dblEquilibriumVariance The Portfolio Equilibrium Variance
	 * 
	 * @return The Estimated Equilibrium Risk Aversion Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double EquilibriumRiskAversion (
		final double dblEquilibriumReturns,
		final double dblRiskFreeRate,
		final double dblEquilibriumVariance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblEquilibriumReturns) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRiskFreeRate) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblEquilibriumVariance))
			throw new java.lang.Exception
				("RiskUtilitySettingsEstimator::EquilibriumRiskAversion => Invalid Inputs");

		return (dblEquilibriumReturns - dblRiskFreeRate) / dblEquilibriumVariance;
	}

	/**
	 * Compute the Equilibrium Risk Aversion from the Portfolio Equilibrium Returns/Variance
	 * 
	 * @param dblEquilibriumReturns The Portfolio Equilibrium Returns
	 * @param dblEquilibriumVariance The Portfolio Equilibrium Variance
	 * 
	 * @return The Estimated Equilibrium Risk Aversion Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double EquilibriumRiskAversion (
		final double dblEquilibriumReturns,
		final double dblEquilibriumVariance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblEquilibriumReturns) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblEquilibriumVariance))
			throw new java.lang.Exception
				("RiskUtilitySettingsEstimator::EquilibriumRiskAversion => Invalid Inputs");

		return dblEquilibriumReturns / dblEquilibriumVariance;
	}
}
