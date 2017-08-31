
package org.drip.execution.hjb;

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
 * NonDimensionalCostEvolverSystemic implements the 1D HJB-based Single Step Optimal Trajectory Cost Step
 *  Evolver using the Systemic Coordinated Variation Version of the Stochastic Volatility and the Transaction
 *  Function arising from the Realization of the Market State Variable as described in the "Trading Time"
 *  Model. The References are:
 * 
 * 	- Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3
 * 		(2) 5-39.
 *
 * 	- Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 		https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf.
 *
 * 	- Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility, SIAM Journal of
 * 		Financial Mathematics  3 (1) 163-181.
 * 
 * 	- Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes, Mathematical Finance 11 (1)
 * 		79-96.
 * 
 * 	- Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility, Review of
 * 		Financial Studies 7 (4) 631-651.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NonDimensionalCostEvolverSystemic extends org.drip.execution.hjb.NonDimensionalCostEvolver
{

	/**
	 * Construct a Standard NonDimensionalCostEvolverSystemic Instance
	 * 
	 * @param ou The Underlying Ornstein-Unlenbeck Reference Process
	 * 
	 * @return The Standard NonDimensionalCostEvolverSystemic Instance
	 */

	public static final NonDimensionalCostEvolverSystemic Standard (
		final org.drip.measure.process.OrnsteinUhlenbeck ou)
	{
		try {
			return new NonDimensionalCostEvolverSystemic (ou,
				org.drip.execution.hjb.NonDimensionalCostEvolver.SINGULAR_URGENCY_THRESHOLD, true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override protected double advance (
		final org.drip.execution.hjb.NonDimensionalCost ndc,
		final org.drip.execution.latent.MarketState ms,
		final double[] adblMarketStateTweak,
		final double dblNonDimensionalRiskAversion)
		throws java.lang.Exception
	{
		double dblNonDimensionalCost = ndc.realization();

		double dblMarketState = ms.liquidity() + adblMarketStateTweak[0];

		double dblBurstiness = ornsteinUnlenbeckProcess().referenceBurstiness();

		org.drip.execution.hjb.NonDimensionalCostSystemic ndcs =
			(org.drip.execution.hjb.NonDimensionalCostSystemic) ndc;

		return java.lang.Math.exp (-dblMarketState) * (dblNonDimensionalRiskAversion *
			dblNonDimensionalRiskAversion - dblNonDimensionalCost * dblNonDimensionalCost) + 0.5 *
				dblBurstiness * dblBurstiness * ndcs.jacobian() - dblMarketState * ndcs.gradient();
	}

	/**
	 * NonDimensionalCostEvolverSystemic Constructor
	 * 
	 * @param ou The Underlying Ornstein-Unlenbeck Reference Process
	 * @param bAsymptoticEnhancedEulerCorrection Asymptotic Enhanced Euler Correction Application Flag
	 * @param dblAsymptoticEulerUrgencyThreshold The Asymptotic Euler Urgency Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NonDimensionalCostEvolverSystemic (
		final org.drip.measure.process.OrnsteinUhlenbeck ou,
		final double dblAsymptoticEulerUrgencyThreshold,
		final boolean bAsymptoticEnhancedEulerCorrection)
		throws java.lang.Exception
	{
		super (ou, dblAsymptoticEulerUrgencyThreshold, bAsymptoticEnhancedEulerCorrection);
	}

	@Override public org.drip.execution.hjb.NonDimensionalCost evolve (
		final org.drip.execution.hjb.NonDimensionalCost ndc,
		final org.drip.execution.latent.MarketState ms,
		final double dblNonDimensionalRiskAversion,
		final double dblNonDimensionalTime,
		final double dblNonDimensionalTimeIncrement)
	{
		if (null == ndc || !(ndc instanceof org.drip.execution.hjb.NonDimensionalCostSystemic) || null
			== ms || !org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalRiskAversion) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalTime) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalTimeIncrement))
			return null;

		double dblMarketState = ms.liquidity();

		double dblMarketStateIncrement = 0.01 * dblMarketState;

		double dblMarketStateExponentiation = java.lang.Math.exp (dblMarketState);

		if (asymptoticEulerUrgencyThreshold() * dblNonDimensionalTime < 1.) {
			if (!asymptoticEnhancedEulerCorrection())
				return org.drip.execution.hjb.NonDimensionalCostSystemic.LinearThreshold
					(dblMarketStateExponentiation, dblNonDimensionalTime);

			double dblBurstiness = ornsteinUnlenbeckProcess().referenceBurstiness();

			double dblNonDimensionalCostCross = -0.5 * dblMarketState * dblMarketStateExponentiation;

			return org.drip.execution.hjb.NonDimensionalCostSystemic.EulerEnhancedLinearThreshold
				(dblMarketState, ((1. / dblNonDimensionalTimeIncrement) + 0.25 * dblBurstiness *
					dblBurstiness) * java.lang.Math.exp (dblMarketState) + dblNonDimensionalCostCross,
						dblNonDimensionalCostCross);
		}

		try {
			double dblCostIncrementMid = advance (ndc, ms, new double[] {0.}, dblNonDimensionalRiskAversion)
				* dblNonDimensionalTimeIncrement;

			double dblCostIncrementUp = advance (ndc, ms, new double[] {dblMarketStateIncrement},
				dblNonDimensionalRiskAversion) * dblNonDimensionalTimeIncrement;

			double dblCostIncrementDown = advance (ndc, ms, new double[] {-1. * dblMarketStateIncrement},
				dblNonDimensionalRiskAversion) * dblNonDimensionalTimeIncrement;

			double dblCost = ndc.realization() + dblCostIncrementMid;

			return new org.drip.execution.hjb.NonDimensionalCostSystemic (dblCost, 0.5 *
				(dblCostIncrementUp - dblCostIncrementDown) / dblMarketStateIncrement, (dblCostIncrementUp +
					dblCostIncrementDown - 2. * dblCostIncrementMid) / (dblMarketStateIncrement *
						dblMarketStateIncrement), dblCost / dblMarketStateExponentiation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
