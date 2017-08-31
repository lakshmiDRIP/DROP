
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
 * NonDimensionalCostEvolverCorrelated implements the Correlated HJB-based Single Step Optimal Trajectory
 *  Cost Step Evolver using the Correlated Coordinated Variation Version of the Stochastic Volatility and the
 *  Transaction Function arising from the Realization of the Market State Variable as described in the
 *  "Trading Time" Model. The References are:
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

public class NonDimensionalCostEvolverCorrelated extends
	org.drip.execution.hjb.NonDimensionalCostEvolver {

	@Override protected double advance (
		final org.drip.execution.hjb.NonDimensionalCost ndc,
		final org.drip.execution.latent.MarketState ms,
		final double[] adblMarketStateTweak,
		final double dblNonDimensionalRiskAversion)
		throws java.lang.Exception
	{
		org.drip.execution.hjb.NonDimensionalCostCorrelated ndcc =
			(org.drip.execution.hjb.NonDimensionalCostCorrelated) ndc;

		org.drip.measure.process.OrnsteinUhlenbeckPair oup2D =
			(org.drip.measure.process.OrnsteinUhlenbeckPair) ornsteinUnlenbeckProcess();

		org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck oup1DLiquidity = oup2D.reference();

		org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck oup1DVolatility = oup2D.derived();

		double dblVolatilityMarketState = ms.volatility() + adblMarketStateTweak[1];

		double dblLiquidityMarketState = ms.liquidity() + adblMarketStateTweak[0];

		double dblMu = oup1DLiquidity.relaxationTime() / oup1DVolatility.relaxationTime();

		double dblVolatilityBurstiness = oup1DVolatility.burstiness();

		double dblLiquidityBurstiness = oup1DLiquidity.burstiness();

		double dblNonDimensionalCost = ndc.realization();

		return
			dblNonDimensionalRiskAversion * dblNonDimensionalRiskAversion * java.lang.Math.exp (2. *
				dblVolatilityMarketState) -
			dblNonDimensionalCost * dblNonDimensionalCost * java.lang.Math.exp (-dblLiquidityMarketState) +
			oup2D.correlation() + java.lang.Math.sqrt (dblMu) * dblLiquidityBurstiness *
				dblVolatilityBurstiness * ndcc.liquidityVolatilityGradient() +
			0.5 * dblLiquidityBurstiness * dblLiquidityBurstiness * ndcc.liquidityJacobian() +
			0.5 * dblMu * dblVolatilityBurstiness * dblVolatilityBurstiness * ndcc.volatilityJacobian() -
			dblLiquidityMarketState * ndcc.liquidityGradient() -
			dblMu * dblVolatilityMarketState * ndcc.volatilityGradient();
	}

	/**
	 * NonDimensionalCostEvolverCorrelated Constructor
	 * 
	 * @param oup2D The 2D Ornstein-Unlenbeck Generator Process
	 * @param bAsymptoticEnhancedEulerCorrection Asymptotic Enhanced Euler Correction Application Flag
	 * @param dblAsymptoticEulerUrgencyThreshold The Asymptotic Euler Urgency Threshold
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NonDimensionalCostEvolverCorrelated (
		final org.drip.measure.process.OrnsteinUhlenbeckPair oup2D,
		final double dblAsymptoticEulerUrgencyThreshold,
		final boolean bAsymptoticEnhancedEulerCorrection)
		throws java.lang.Exception
	{
		super (oup2D, dblAsymptoticEulerUrgencyThreshold, bAsymptoticEnhancedEulerCorrection);
	}

	@Override public org.drip.execution.hjb.NonDimensionalCost evolve (
		final org.drip.execution.hjb.NonDimensionalCost ndc,
		final org.drip.execution.latent.MarketState ms,
		final double dblNonDimensionalRiskAversion,
		final double dblNonDimensionalTime,
		final double dblNonDimensionalTimeIncrement)
	{
		if (null == ndc || !(ndc instanceof org.drip.execution.hjb.NonDimensionalCostCorrelated) || null
			== ms || !org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalRiskAversion) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalTime) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalTimeIncrement))
			return null;

		double dblLiquidityMarketState = ms.liquidity();

		double dblLiquidityMarketStateIncrement = 0.01 * dblLiquidityMarketState;

		double dblVolatilityMarketStateIncrement = 0.01 * ms.volatility();

		try {
			double dblCostIncrementMid = advance (ndc, ms, new double[] {0., 0.},
				dblNonDimensionalRiskAversion) * dblNonDimensionalTimeIncrement;

			double dblCostIncrementLiquidityUp = advance (ndc, ms, new double[]
				{dblLiquidityMarketStateIncrement, 0.}, dblNonDimensionalRiskAversion) *
					dblNonDimensionalTimeIncrement;

			double dblCostIncrementLiquidityDown = advance (ndc, ms, new double[]
				{-dblLiquidityMarketStateIncrement, 0.}, dblNonDimensionalRiskAversion) *
					dblNonDimensionalTimeIncrement;

			double dblCostIncrementVolatilityUp = advance (ndc, ms, new double[] {0.,
				dblVolatilityMarketStateIncrement}, dblNonDimensionalRiskAversion) *
					dblNonDimensionalTimeIncrement;

			double dblCostIncrementVolatilityDown = advance (ndc, ms, new double[] {0.,
				-dblVolatilityMarketStateIncrement}, dblNonDimensionalRiskAversion) *
					dblNonDimensionalTimeIncrement;

			double dblCostIncrementCrossUp = advance (ndc, ms, new double[]
				{dblLiquidityMarketStateIncrement, dblVolatilityMarketStateIncrement},
					dblNonDimensionalRiskAversion) * dblNonDimensionalTimeIncrement;

			double dblCostIncrementCrossDown = advance (ndc, ms, new double[]
				{-dblLiquidityMarketStateIncrement, -dblVolatilityMarketStateIncrement},
					dblNonDimensionalRiskAversion) * dblNonDimensionalTimeIncrement;

			double dblNonDimensionalCost = ndc.realization() + dblCostIncrementMid;

			return new org.drip.execution.hjb.NonDimensionalCostCorrelated (
				dblNonDimensionalCost,
				0.5 * (dblCostIncrementLiquidityUp - dblCostIncrementLiquidityDown) /
					dblLiquidityMarketStateIncrement,
				(dblCostIncrementLiquidityUp + dblCostIncrementLiquidityDown - 2. * dblCostIncrementMid) /
					(dblLiquidityMarketStateIncrement * dblLiquidityMarketStateIncrement),
				0.5 * (dblCostIncrementVolatilityUp - dblCostIncrementVolatilityDown) /
					dblVolatilityMarketStateIncrement,
				(dblCostIncrementVolatilityUp + dblCostIncrementVolatilityDown - 2. * dblCostIncrementMid) /
					(dblVolatilityMarketStateIncrement * dblVolatilityMarketStateIncrement),
				0.25 * (dblCostIncrementCrossUp - dblCostIncrementCrossDown) /
					(dblLiquidityMarketStateIncrement * dblVolatilityMarketStateIncrement),
				dblNonDimensionalCost * java.lang.Math.exp (-dblLiquidityMarketState));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
