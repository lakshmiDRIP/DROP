
package org.drip.execution.hjb;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>NonDimensionalCostEvolverCorrelated</i> implements the Correlated HJB-based Single Step Optimal
 * Trajectory Cost Step Evolver using the Correlated Coordinated Variation Version of the Stochastic
 * Volatility and the Transaction Function arising from the Realization of the Market State Variable as
 * described in the "Trading Time" Model. The References are:
 * 
 * <br><br>
 *  <ul>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 			of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 		<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 * 		</li>
 * 		<li>
 * 			Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 * 				Financial Studies</i> <b>7 (4)</b> 631-651
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb/README.md">Hamilton Jacobin Bellman Based Optimal Evolution</a></li>
 *  </ul>
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
			== ms || !org.drip.numerical.common.NumberUtil.IsValid (dblNonDimensionalRiskAversion) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblNonDimensionalTime) ||
					!org.drip.numerical.common.NumberUtil.IsValid (dblNonDimensionalTimeIncrement))
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
