
package org.drip.execution.adaptive;

import org.drip.execution.dynamics.ArithmeticPriceEvolutionParametersBuilder;
import org.drip.execution.dynamics.LinearPermanentExpectationParameters;
import org.drip.execution.hjb.NonDimensionalCost;
import org.drip.execution.hjb.NonDimensionalCostEvolver;
import org.drip.execution.hjb.NonDimensionalCostSystemic;
import org.drip.execution.impact.ParticipationRateLinear;
import org.drip.execution.latent.MarketState;
import org.drip.execution.nonadaptive.ContinuousAlmgrenChriss;
import org.drip.execution.optimum.EfficientTradingTrajectoryContinuous;
import org.drip.execution.parameters.ArithmeticPriceDynamicsSettings;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.execution.risk.MeanVarianceObjectiveUtility;
import org.drip.execution.strategy.ContinuousTradingTrajectory;
import org.drip.execution.strategy.OrderSpecification;
import org.drip.execution.tradingtime.CoordinatedVariation;
import org.drip.function.r1tor1operator.Flat;
import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>CoordinatedVariationTrajectoryGenerator</i> implements the Continuous HJB-based Single Step Optimal
 * 	Cost Trajectory using the Coordinated Variation Version of the Stochastic Volatility and the Transaction
 * 	Function arising from the Realization of the Market State Variable as described in the "Trading Time"
 * 	Model. It provides the following Functions:
 * 	<ul>
 * 		<li>Flag Indicating Trade Rate Initialization from Static Trajectory</li>
 * 		<li>Flag Indicating Trade Rate Initialization to Zero Initial Value</li>
 * 		<li><i>CoordinatedVariationTrajectoryGenerator</i> Constructor</li>
 * 		<li>Retrieve the Trade Rate Initialization Indicator</li>
 * 		<li>Retrieve the Order Specification</li>
 * 		<li>Retrieve the Coordinated Variation Instance</li>
 * 		<li>Retrieve the Non Dimensional Cost Evolver</li>
 * 		<li>Retrieve the Mean Variance Objective Utility Function</li>
 * 		<li>Compute The Coordinated Variation Trajectory Determinant Instance</li>
 * 		<li>Retrieve the Initial Non Dimensional Cost</li>
 * 		<li>Generate the Continuous Coordinated Variation Dynamic Adaptive Trajectory</li>
 * 		<li>Generate a Static, Non-adaptive Trading Trajectory Instance</li>
 * 		<li>Generate the Continuous Coordinated Variation Rolling Horizon Trajectory</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
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
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/README.md">Coordinated Variation Based Adaptive Execution</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CoordinatedVariationTrajectoryGenerator
{

	/**
	 * Flag Indicating Trade Rate Initialization from Static Trajectory
	 */

	public static final int TRADE_RATE_STATIC_INITIALIZATION = 1;

	/**
	 * Flag Indicating Trade Rate Initialization to Zero Initial Value
	 */

	public static final int TRADE_RATE_ZERO_INITIALIZATION = 2;

	private OrderSpecification _orderSpecification = null;
	private CoordinatedVariation _coordinatedVariation = null;
	private int _tradeRateInitializer = TRADE_RATE_ZERO_INITIALIZATION;
	private NonDimensionalCostEvolver _nonDimensionalCostEvolver = null;
	private MeanVarianceObjectiveUtility _meanVarianceObjectiveUtility = null;

	private LinearPermanentExpectationParameters realizedLinearPermanentExpectationParameters (
		final double marketState)
	{
		try {
			return new LinearPermanentExpectationParameters (
				new ArithmeticPriceDynamicsSettings (
					0.,
					new Flat (_coordinatedVariation.referenceVolatility() * Math.exp (-0.5 * marketState)),
					0.
				),
				new UniformParticipationRateLinear (ParticipationRateLinear.NoImpact()),
				new UniformParticipationRateLinear (
					ParticipationRateLinear.SlopeOnly (
						_coordinatedVariation.referenceLiquidity() * Math.exp (marketState)
					)
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>CoordinatedVariationTrajectoryGenerator</i> Constructor
	 * 
	 * @param orderSpecification The Order Specification
	 * @param coordinatedVariation The Coordinated Variation Instance
	 * @param meanVarianceObjectiveUtility The Mean Variance Objective Utility Function
	 * @param nonDimensionalCostEvolver The Non Dimensional Cost Evolver
	 * @param tradeRateInitializer The Trade Rate Initialization Indicator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CoordinatedVariationTrajectoryGenerator (
		final OrderSpecification orderSpecification,
		final CoordinatedVariation coordinatedVariation,
		final MeanVarianceObjectiveUtility meanVarianceObjectiveUtility,
		final NonDimensionalCostEvolver nonDimensionalCostEvolver,
		final int tradeRateInitializer)
		throws Exception
	{
		if (null == (_orderSpecification = orderSpecification) ||
			null == (_coordinatedVariation = coordinatedVariation) ||
			null == (_meanVarianceObjectiveUtility = meanVarianceObjectiveUtility) ||
			null == (_nonDimensionalCostEvolver = nonDimensionalCostEvolver) || (
				TRADE_RATE_STATIC_INITIALIZATION != (_tradeRateInitializer = tradeRateInitializer) &&
				TRADE_RATE_ZERO_INITIALIZATION != _tradeRateInitializer)
			)
		{
			throw new Exception ("CoordinatedVariationTrajectoryGenerator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Trade Rate Initialization Indicator
	 * 
	 * @return The Trade Rate Initialization Indicator
	 */

	public int tradeRateInitializer()
	{
		return _tradeRateInitializer;
	}

	/**
	 * Retrieve the Order Specification
	 * 
	 * @return The Order Specification
	 */

	public OrderSpecification orderSpecification()
	{
		return _orderSpecification;
	}

	/**
	 * Retrieve the Coordinated Variation Instance
	 * 
	 * @return The Coordinated Variation Instance
	 */

	public CoordinatedVariation coordinatedVariationConstraint()
	{
		return _coordinatedVariation;
	}

	/**
	 * Retrieve the Non Dimensional Cost Evolver
	 * 
	 * @return The Non Dimensional Cost Evolver
	 */

	public NonDimensionalCostEvolver costEvolver()
	{
		return _nonDimensionalCostEvolver;
	}

	/**
	 * Retrieve the Mean Variance Objective Utility Function
	 * 
	 * @return The Mean Variance Objective Utility Function
	 */

	public MeanVarianceObjectiveUtility objectiveUtility()
	{
		return _meanVarianceObjectiveUtility;
	}

	/**
	 * Compute The Coordinated Variation Trajectory Determinant Instance
	 * 
	 * @return The Coordinated Variation Trajectory Determinant Instance
	 */

	public CoordinatedVariationTrajectoryDeterminant trajectoryDeterminant()
	{
		double executionSize = _orderSpecification.size();

		double referenceLiquidity = _coordinatedVariation.referenceLiquidity();

		double referenceVolatility = _coordinatedVariation.referenceVolatility();

		double relaxationTime =
			_nonDimensionalCostEvolver.ornsteinUnlenbeckProcess().referenceRelaxationTime();

		double meanMarketUrgency = referenceVolatility * Math.sqrt (
			_meanVarianceObjectiveUtility.riskAversion() / referenceLiquidity
		);

		double tradeRateScale = executionSize / relaxationTime;

		try {
			return new CoordinatedVariationTrajectoryDeterminant (
				executionSize,
				relaxationTime,
				referenceLiquidity * executionSize * executionSize / tradeRateScale,
				tradeRateScale,
				meanMarketUrgency,
				meanMarketUrgency * relaxationTime,
				referenceLiquidity * executionSize / referenceVolatility * Math.pow (
					_orderSpecification.maxExecutionTime(),
					-1.5
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Initial Non Dimensional Cost
	 * 
	 * @param initialMarketState The Initial Market State
	 * @param tradeRateScale The Trade Rate Scale
	 * 
	 * @return The Initial Non Dimensional Cost
	 */

	public NonDimensionalCost initializeNonDimensionalCost (
		final MarketState initialMarketState,
		final double tradeRateScale)
	{
		if (TRADE_RATE_ZERO_INITIALIZATION == _tradeRateInitializer) {
			return NonDimensionalCostSystemic.Zero();
		}

		if (null == initialMarketState || !NumberUtil.IsValid (tradeRateScale)) {
			return null;
		}

		try {
			ContinuousTradingTrajectory continuousTradingTrajectory =
				(ContinuousTradingTrajectory) new ContinuousAlmgrenChriss (
					_orderSpecification,
					ArithmeticPriceEvolutionParametersBuilder.ReferenceCoordinatedVariation (
						_coordinatedVariation
					),
					_meanVarianceObjectiveUtility
				).generate();

			if (null == continuousTradingTrajectory) {
				return null;
			}

			double nonDimensionalInstantTradeRate =
				continuousTradingTrajectory.tradeRate().evaluate (0.) / tradeRateScale;

			double nonDimensionalCostSensitivity =
				Math.exp (initialMarketState.liquidity()) * nonDimensionalInstantTradeRate;

			return new NonDimensionalCostSystemic (
				0.,
				nonDimensionalCostSensitivity,
				nonDimensionalCostSensitivity,
				nonDimensionalInstantTradeRate
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Continuous Coordinated Variation Dynamic Adaptive Trajectory
	 * 
	 * @param marketStateArray Array of Realized Market States
	 * 
	 * @return The Continuous Coordinated Variation Dynamic Adaptive Trajectory
	 */

	public CoordinatedVariationDynamic adaptive (
		final MarketState[] marketStateArray)
	{
		if (null == marketStateArray) {
			return null;
		}

		int timeNodeCount = marketStateArray.length;

		if (1 >= timeNodeCount) {
			return null;
		}

		double executionSize = _orderSpecification.size();

		double referenceLiquidity = _coordinatedVariation.referenceLiquidity();

		double referenceVolatility = _coordinatedVariation.referenceVolatility();

		double relaxationTime =
			_nonDimensionalCostEvolver.ornsteinUnlenbeckProcess().referenceRelaxationTime();

		double nonDimensionalTimeIncrement =
			_orderSpecification.maxExecutionTime() / (timeNodeCount - 1) / relaxationTime;

		double meanMarketUrgency = referenceVolatility * Math.sqrt (
			_meanVarianceObjectiveUtility.riskAversion() / referenceLiquidity
		);

		NonDimensionalCost[] nonDimensionalCostArray = new NonDimensionalCost[timeNodeCount];
		double[] nonDimensionalScaledTradeRateArray = new double[timeNodeCount];
		double[] nonDimensionalHoldingsArray = new double[timeNodeCount];
		double tradeRateScale = executionSize / relaxationTime;
		nonDimensionalScaledTradeRateArray[0] = 0.;
		nonDimensionalHoldingsArray[0] = 1.;

		if (null == (
			nonDimensionalCostArray[0] = initializeNonDimensionalCost (marketStateArray[0], tradeRateScale)
		))
		{
			return null;
		}

		for (int timeNodeIndex = 1; timeNodeIndex < timeNodeCount; ++timeNodeIndex) {
			if (null == (
				nonDimensionalCostArray[timeNodeIndex] = _nonDimensionalCostEvolver.evolve (
					nonDimensionalCostArray[timeNodeIndex - 1],
					marketStateArray[timeNodeIndex],
					meanMarketUrgency * relaxationTime,
					(timeNodeCount - timeNodeIndex) * nonDimensionalTimeIncrement,
					nonDimensionalTimeIncrement
				)
			))
			{
				return null;
			}

			nonDimensionalScaledTradeRateArray[timeNodeIndex] =
				nonDimensionalHoldingsArray[timeNodeIndex - 1] *
				nonDimensionalCostArray[timeNodeIndex].nonDimensionalTradeRate();

			nonDimensionalHoldingsArray[timeNodeIndex] =
				nonDimensionalHoldingsArray[timeNodeIndex - 1] -
				nonDimensionalScaledTradeRateArray[timeNodeIndex] * nonDimensionalTimeIncrement;
		}

		try {
			return new CoordinatedVariationDynamic (
				new CoordinatedVariationTrajectoryDeterminant (
					executionSize,
					relaxationTime,
					referenceLiquidity * executionSize * executionSize / tradeRateScale,
					tradeRateScale,
					meanMarketUrgency,
					meanMarketUrgency * relaxationTime,
					referenceLiquidity * executionSize / referenceVolatility * Math.pow (
						_orderSpecification.maxExecutionTime(),
						-1.5
					)
				),
				nonDimensionalHoldingsArray,
				nonDimensionalScaledTradeRateArray,
				nonDimensionalCostArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Static, Non-adaptive Trading Trajectory Instance
	 * 
	 * @return The Static, Non-adaptive Trading Trajectory Instance
	 */

	public CoordinatedVariationStatic nonAdaptive()
	{
		try {
			return new CoordinatedVariationStatic (
				trajectoryDeterminant(),
				(EfficientTradingTrajectoryContinuous) new ContinuousAlmgrenChriss (
					_orderSpecification,
					ArithmeticPriceEvolutionParametersBuilder.ReferenceCoordinatedVariation (
						_coordinatedVariation
					),
					_meanVarianceObjectiveUtility
				).generate()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Continuous Coordinated Variation Rolling Horizon Trajectory
	 * 
	 * @param marketStateArray Array of Realized Market States
	 * 
	 * @return The Continuous Coordinated Variation Rolling Horizon Trajectory
	 */

	public CoordinatedVariationRollingHorizon rollingHorizon (
		final MarketState[] marketStateArray)
	{
		if (null == marketStateArray) {
			return null;
		}

		int timeNodeCount = marketStateArray.length;
		double[] nonDimensionalCostArray = 0 == timeNodeCount ? null : new double[timeNodeCount];
		double[] nonDimensionalHoldingsArray = 0 == timeNodeCount ? null : new double[timeNodeCount];
		double[] nonDimensionalTradeRateArray = 0 == timeNodeCount ? null : new double[timeNodeCount];

		if (0 == timeNodeCount) {
			return null;
		}

		double executionSize = _orderSpecification.size();

		double executionTime = _orderSpecification.maxExecutionTime();

		double riskAversion = _meanVarianceObjectiveUtility.riskAversion();

		double referenceLiquidity = _coordinatedVariation.referenceLiquidity();

		double referenceVolatility = _coordinatedVariation.referenceVolatility();

		double relaxationTime =
			_nonDimensionalCostEvolver.ornsteinUnlenbeckProcess().referenceRelaxationTime();

		double meanMarketUrgency = referenceVolatility * Math.sqrt (riskAversion / referenceLiquidity);

		double nonDimensionalTimeIncrement = executionTime / (timeNodeCount - 1) / relaxationTime;
		double nonDimensionalExecutionTime = executionTime / relaxationTime;
		double tradeRateScale = executionSize / relaxationTime;
		nonDimensionalTradeRateArray[timeNodeCount - 1] = 0.;
		nonDimensionalHoldingsArray[0] = 1.;
		nonDimensionalCostArray[0] = 0.;

		for (int timeNodeIndex = 0; timeNodeIndex < timeNodeCount - 1; ++timeNodeIndex) {
			LinearPermanentExpectationParameters linearPermanentExpectationParameters =
				realizedLinearPermanentExpectationParameters (marketStateArray[timeNodeIndex].liquidity());

			if (null == linearPermanentExpectationParameters) {
				return null;
			}

			try {
				double realizedVolatility =
					linearPermanentExpectationParameters.arithmeticPriceDynamicsSettings().epochVolatility();

				ContinuousTradingTrajectory continuousTradingTrajectory =
					(ContinuousTradingTrajectory) new ContinuousAlmgrenChriss (
						new OrderSpecification (
							nonDimensionalHoldingsArray[timeNodeIndex],
							nonDimensionalExecutionTime - timeNodeIndex * nonDimensionalTimeIncrement
						),
						linearPermanentExpectationParameters,
						_meanVarianceObjectiveUtility
					).generate();

				if (null == continuousTradingTrajectory) {
					return null;
				}

				nonDimensionalTradeRateArray[timeNodeIndex] =
					continuousTradingTrajectory.tradeRate().evaluate (0.);

				nonDimensionalHoldingsArray[timeNodeIndex + 1] =
					nonDimensionalHoldingsArray[timeNodeIndex] -
					nonDimensionalTradeRateArray[timeNodeIndex] * nonDimensionalTimeIncrement;
				nonDimensionalCostArray[timeNodeIndex + 1] =
					nonDimensionalCostArray[timeNodeIndex] + (
						riskAversion * realizedVolatility * realizedVolatility *
						nonDimensionalHoldingsArray[timeNodeIndex] *
						nonDimensionalHoldingsArray[timeNodeIndex] +
						linearPermanentExpectationParameters.linearPermanentExpectation().epochLiquidityFunction().slope()
						* nonDimensionalTradeRateArray[timeNodeIndex] *
						nonDimensionalTradeRateArray[timeNodeIndex]
					) *
						nonDimensionalTimeIncrement;
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new CoordinatedVariationRollingHorizon (
				new CoordinatedVariationTrajectoryDeterminant (
					executionSize,
					relaxationTime,
					referenceLiquidity * executionSize * executionSize / tradeRateScale,
					tradeRateScale,
					meanMarketUrgency,
					meanMarketUrgency * relaxationTime,
					referenceLiquidity * executionSize / referenceVolatility * Math.pow (
						_orderSpecification.maxExecutionTime(),
						-1.5
					)
				),
				nonDimensionalHoldingsArray,
				nonDimensionalTradeRateArray,
				nonDimensionalCostArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
