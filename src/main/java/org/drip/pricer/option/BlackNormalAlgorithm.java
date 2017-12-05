
package org.drip.pricer.option;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * BlackNormalAlgorithm implements the Black Normal European Call and Put Options Pricer.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BlackNormalAlgorithm extends org.drip.pricer.option.FokkerPlanckGenerator {

	/**
	 * Empty BlackNormalAlgorithm Constructor - nothing to be filled in with
	 */

	public BlackNormalAlgorithm()
	{
	}

	@Override public double payoff (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblVolatility,
		final boolean bAsPrice)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStrike) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblUnderlier) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblVolatility) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblTimeToExpiry) ||
						!org.drip.quant.common.NumberUtil.IsValid (dblRiskFreeRate))
			throw new java.lang.Exception ("BlackNormalAlgorithm::payoff => Invalid Inputs");

		double dblD1D2Diff = dblVolatility * java.lang.Math.sqrt (dblTimeToExpiry);

		double dblDF = java.lang.Math.exp (-1. * dblRiskFreeRate * dblTimeToExpiry);

		double dblForward = bIsForward ? dblUnderlier : dblUnderlier / dblDF;
		double dblD = (dblForward - dblStrike) / dblD1D2Diff;

		double dblCallPayoff = dblForward * dblD1D2Diff * java.lang.Math.exp (-0.5 * dblD * dblD) /
			java.lang.Math.sqrt (2. * java.lang.Math.PI) / dblForward - dblStrike * -1. * dblD1D2Diff * dblD
				* org.drip.measure.gaussian.NormalQuadrature.CDF (dblD) / dblStrike;

		if (!bAsPrice) return bIsPut ? dblCallPayoff + dblStrike - dblForward : dblCallPayoff;

		return bIsPut ? dblDF * (dblCallPayoff + dblStrike - dblForward) : dblDF * dblCallPayoff;
	}

	@Override public org.drip.pricer.option.Greeks greeks (
		final double dblStrike,
		final double dblTimeToExpiry,
		final double dblRiskFreeRate,
		final double dblUnderlier,
		final boolean bIsPut,
		final boolean bIsForward,
		final double dblVolatility)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStrike) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblUnderlier) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblVolatility) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblTimeToExpiry) ||
						!org.drip.quant.common.NumberUtil.IsValid (dblRiskFreeRate))
			return null;

		double dblD1D2Diff = dblVolatility * java.lang.Math.sqrt (dblTimeToExpiry);

		double dblDF = java.lang.Math.exp (-1. * dblRiskFreeRate * dblTimeToExpiry);

		double dblForward = bIsForward ? dblUnderlier : dblUnderlier / dblDF;
		double dblD = (dblForward - dblStrike) / dblD1D2Diff;

		double dblN = java.lang.Math.exp (-0.5 * dblD * dblD) / java.lang.Math.sqrt (2. * java.lang.Math.PI);

		double dblCallProb1 = dblD1D2Diff * dblN / dblForward;

		try {
			double dblCallProb2 = -1. * dblD1D2Diff * dblD * org.drip.measure.gaussian.NormalQuadrature.CDF (dblD)
				/ dblStrike;

			double dblExpectedCallPayoff = dblForward * dblCallProb1 - dblStrike * dblCallProb2;
			double dblATMCallPayoff = dblForward * (dblCallProb1 - dblCallProb2);
			double dblCallPrice = dblDF * dblExpectedCallPayoff;

			if (!bIsPut)
				return new org.drip.pricer.option.Greeks (
					dblDF,
					dblVolatility,
					dblExpectedCallPayoff,
					dblATMCallPayoff,
					dblCallPrice,
					dblCallProb1,
					dblCallProb2,
					dblCallProb1,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN
				);

			double dblPutProb1 = dblD * dblD1D2Diff * org.drip.measure.gaussian.NormalQuadrature.CDF (-1. * dblD) /
				dblForward;

			double dblPutProb2 = dblD1D2Diff * dblN / dblStrike;

			return new org.drip.pricer.option.PutGreeks (
				dblDF,
				dblVolatility,
				dblExpectedCallPayoff + dblStrike - dblForward,
				dblATMCallPayoff,
				dblDF * (-1. * dblForward * dblPutProb1 + dblStrike * dblPutProb2),
				dblCallPrice + dblDF * (dblStrike - dblForward),
				dblPutProb1,
				dblPutProb2,
				-1. * dblPutProb1,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
