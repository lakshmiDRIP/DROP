
package org.drip.execution.nonadaptive;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>ContinuousCoordinatedVariationStochastic</i> uses the Coordinated Variation Version of the Linear
 *  Participation Rate Transaction Function as described in the "Trading Time" Model to construct an Optimal
 *  Trading Trajectory in the T To Infinite Limit. The References are:
 * 
 * 	<br>
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
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/nonadaptive">Non-Adaptive</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/TransactionCost">Transaction Cost Analytics</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ContinuousCoordinatedVariationStochastic extends
	org.drip.execution.nonadaptive.StaticOptimalSchemeContinuous {

	/**
	 * Create the Standard ContinuousCoordinatedVariationStochastic Instance
	 * 
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param dblFinishTime Trajectory Finish Time
	 * @param apep The Arithmetic Price Evolution Parameters
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The ContinuousCoordinatedVariationStochastic Instance
	 */

	public static final ContinuousCoordinatedVariationStochastic Standard (
		final double dblStartHoldings,
		final double dblFinishTime,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep,
		final double dblRiskAversion)
	{
		try {
			return new ContinuousCoordinatedVariationStochastic (new
				org.drip.execution.strategy.OrderSpecification (dblStartHoldings, dblFinishTime), apep, new
					org.drip.execution.risk.MeanVarianceObjectiveUtility (dblRiskAversion));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private ContinuousCoordinatedVariationStochastic (
		final org.drip.execution.strategy.OrderSpecification os,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep,
		final org.drip.execution.risk.MeanVarianceObjectiveUtility mvou)
		throws java.lang.Exception
	{
		super (os, apep, mvou);
	}

	@Override public org.drip.execution.optimum.EfficientTradingTrajectory generate()
	{
		org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep =
			(org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters) priceEvolutionParameters();

		org.drip.execution.profiletime.BackgroundParticipationRate bprTemporary =
			apep.temporaryExpectation();

		if (!(bprTemporary instanceof org.drip.execution.profiletime.BackgroundParticipationRateLinear))
			return null;

		double dblInitialVolatility = java.lang.Double.NaN;
		final org.drip.execution.profiletime.BackgroundParticipationRateLinear bprlTemporary =
			(org.drip.execution.profiletime.BackgroundParticipationRateLinear) bprTemporary;

		org.drip.execution.impact.TransactionFunctionLinear tflTemporaryExpectation =
			bprlTemporary.epochLiquidityFunction();

		try {
			dblInitialVolatility = apep.arithmeticPriceDynamicsSettings().epochVolatility();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		final double dblEpochVolatility = dblInitialVolatility;

		final double dblEpochLiquidity = tflTemporaryExpectation.slope();

		final double dblLambda = ((org.drip.execution.risk.MeanVarianceObjectiveUtility)
			objectiveUtility()).riskAversion();

		double dblEpochUrgency = java.lang.Math.sqrt (dblLambda * dblEpochVolatility * dblEpochVolatility /
			dblEpochLiquidity);

		final org.drip.function.definition.R1ToR1 r1ToR1VolatilityFunction =
			apep.arithmeticPriceDynamicsSettings().volatilityFunction();

		org.drip.execution.strategy.OrderSpecification os = orderSpecification();

		final double dblT = os.maxExecutionTime();

		final double dblX = os.size();

		final org.drip.function.definition.R1ToR1 r1ToR1Holdings = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblVolatility = r1ToR1VolatilityFunction.evaluate (dblTime);

				double dblKappa = java.lang.Math.sqrt (dblLambda * dblVolatility * dblVolatility /
					bprlTemporary.liquidityFunction (dblTime).slope());

				return java.lang.Math.sinh (dblKappa * (dblT - dblTime)) / java.lang.Math.sinh (dblKappa *
					dblT) * dblX;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TradeRate = new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblVolatility = r1ToR1VolatilityFunction.evaluate (dblTime);

				return java.lang.Math.sqrt (dblLambda * dblVolatility * dblVolatility /
					bprlTemporary.liquidityFunction (dblTime).slope()) * r1ToR1Holdings.evaluate (dblTime);
			}
		};

		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectationRate = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblHoldings = r1ToR1Holdings.evaluate (dblTime);

				double dblVolatility = r1ToR1VolatilityFunction.evaluate (dblTime);

				return java.lang.Math.sqrt (dblLambda * dblVolatility * dblVolatility *
					bprlTemporary.liquidityFunction (dblTime).slope()) * dblHoldings * dblHoldings;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectation = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return r1ToR1TransactionCostExpectationRate.integrate (dblTime, dblT);
			}
		};

		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVarianceRate = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblHoldings = r1ToR1Holdings.evaluate (dblTime);

				double dblVolatility = r1ToR1VolatilityFunction.evaluate (dblTime);

				return dblVolatility * dblVolatility * dblHoldings * dblHoldings;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVariance = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return r1ToR1TransactionCostVarianceRate.integrate (dblTime, dblT);
			}
		};

		try {
			return new org.drip.execution.optimum.EfficientTradingTrajectoryContinuous (dblT,
				dblEpochLiquidity * dblEpochUrgency * dblX * dblX / java.lang.Math.tanh (dblEpochUrgency *
					dblT), r1ToR1TransactionCostExpectation.evaluate (0.), 1. / dblEpochUrgency,
						dblEpochLiquidity * dblX / (dblT * dblEpochVolatility * java.lang.Math.sqrt (dblT)),
							r1ToR1Holdings, r1ToR1TradeRate, r1ToR1TransactionCostExpectation,
								r1ToR1TransactionCostVariance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
