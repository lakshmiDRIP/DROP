
package org.drip.execution.adaptive;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * Cost Trajectory using the Coordinated Variation Version of the Stochastic Volatility and the Transaction
 * Function arising from the Realization of the Market State Variable as described in the "Trading Time"
 * Model. The References are:
 * 
 * 	<br><br>
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/README.md">Coordinated Variation Based Adaptive Execution</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CoordinatedVariationTrajectoryGenerator {

	/**
	 * Flag Indicating Trade Rate Initialization from Static Trajectory
	 */

	public static final int TRADE_RATE_STATIC_INITIALIZATION = 1;

	/**
	 * Flag Indicating Trade Rate Initialization to Zero Initial Value
	 */

	public static final int TRADE_RATE_ZERO_INITIALIZATION = 2;

	private org.drip.execution.strategy.OrderSpecification _os = null;
	private int _iTradeRateInitializer = TRADE_RATE_ZERO_INITIALIZATION;
	private org.drip.execution.tradingtime.CoordinatedVariation _cv = null;
	private org.drip.execution.risk.MeanVarianceObjectiveUtility _mvou = null;
	private org.drip.execution.hjb.NonDimensionalCostEvolver _ndce = null;

	private org.drip.execution.dynamics.LinearPermanentExpectationParameters realizedLPEP (
		final double dblMarketState)
	{
		try {
			return new org.drip.execution.dynamics.LinearPermanentExpectationParameters (new
				org.drip.execution.parameters.ArithmeticPriceDynamicsSettings (0., new
					org.drip.function.r1tor1operator.Flat (_cv.referenceVolatility() * java.lang.Math.exp
						(-0.5 * dblMarketState)), 0.), new
							org.drip.execution.profiletime.UniformParticipationRateLinear
								(org.drip.execution.impact.ParticipationRateLinear.NoImpact()), new
									org.drip.execution.profiletime.UniformParticipationRateLinear
										(org.drip.execution.impact.ParticipationRateLinear.SlopeOnly
											(_cv.referenceLiquidity() * java.lang.Math.exp
												(dblMarketState))));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CoordinatedVariationTrajectoryGenerator Constructor
	 * 
	 * @param os The Order Specification
	 * @param cv The Coordinated Variation Instance
	 * @param mvou  The Mean Variance Objective Utility Function
	 * @param ndce The Non Dimensional Cost Evolver
	 * @param iTradeRateInitializer The Trade Rate Initialization Indicator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CoordinatedVariationTrajectoryGenerator (
		final org.drip.execution.strategy.OrderSpecification os,
		final org.drip.execution.tradingtime.CoordinatedVariation cv,
		final org.drip.execution.risk.MeanVarianceObjectiveUtility mvou,
		final org.drip.execution.hjb.NonDimensionalCostEvolver ndce,
		final int iTradeRateInitializer)
		throws java.lang.Exception
	{
		if (null == (_os = os) || null == (_cv = cv) || null == (_mvou = mvou) || null == (_ndce = ndce) ||
			(TRADE_RATE_STATIC_INITIALIZATION != (_iTradeRateInitializer = iTradeRateInitializer) &&
				TRADE_RATE_ZERO_INITIALIZATION != _iTradeRateInitializer))
			throw new java.lang.Exception
				("CoordinatedVariationTrajectoryGenerator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Trade Rate Initialization Indicator
	 * 
	 * @return The Trade Rate Initialization Indicator
	 */

	public int tradeRateInitializer()
	{
		return _iTradeRateInitializer;
	}

	/**
	 * Retrieve the Order Specification
	 * 
	 * @return The Order Specification
	 */

	public org.drip.execution.strategy.OrderSpecification orderSpecification()
	{
		return _os;
	}

	/**
	 * Retrieve the Coordinated Variation Instance
	 * 
	 * @return The Coordinated Variation Instance
	 */

	public org.drip.execution.tradingtime.CoordinatedVariation coordinatedVariationConstraint()
	{
		return _cv;
	}

	/**
	 * Retrieve the Non Dimensional Cost Evolver
	 * 
	 * @return The Non Dimensional Cost Evolver
	 */

	public org.drip.execution.hjb.NonDimensionalCostEvolver evolver()
	{
		return _ndce;
	}

	/**
	 * Retrieve the Mean Variance Objective Utility Function
	 * 
	 * @return The Mean Variance Objective Utility Function
	 */

	public org.drip.execution.risk.MeanVarianceObjectiveUtility objectiveUtility()
	{
		return _mvou;
	}

	/**
	 * Compute The Coordinated Variation Trajectory Determinant Instance
	 * 
	 * @return The Coordinated Variation Trajectory Determinant Instance
	 */

	public org.drip.execution.adaptive.CoordinatedVariationTrajectoryDeterminant trajectoryDeterminant()
	{
		double dblExecutionSize = _os.size();

		double dblReferenceLiquidity = _cv.referenceLiquidity();

		double dblReferenceVolatility = _cv.referenceVolatility();

		double dblRelaxationTime = _ndce.ornsteinUnlenbeckProcess().referenceRelaxationTime();

		double dblMeanMarketUrgency = _cv.referenceVolatility() * java.lang.Math.sqrt (_mvou.riskAversion() /
			dblReferenceLiquidity);

		double dblTradeRateScale = dblExecutionSize / dblRelaxationTime;

		try {
			return new org.drip.execution.adaptive.CoordinatedVariationTrajectoryDeterminant
				(dblExecutionSize, dblRelaxationTime, dblReferenceLiquidity * dblExecutionSize *
					dblExecutionSize / dblTradeRateScale, dblTradeRateScale, dblMeanMarketUrgency,
						dblMeanMarketUrgency * dblRelaxationTime, dblReferenceLiquidity * dblExecutionSize /
							dblReferenceVolatility * java.lang.Math.pow (_os.maxExecutionTime(), -1.5));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Initial Non Dimensional Cost
	 * 
	 * @param ms The Initial Market State
	 * @param dblTradeRateScale The Trade Rate Scale
	 * 
	 * @return The Initial Non Dimensional Cost
	 */

	public org.drip.execution.hjb.NonDimensionalCost initializeNonDimensionalCost (
		final org.drip.execution.latent.MarketState ms,
		final double dblTradeRateScale)
	{
		if (TRADE_RATE_ZERO_INITIALIZATION == _iTradeRateInitializer)
			return org.drip.execution.hjb.NonDimensionalCostSystemic.Zero();

		if (null == ms || !org.drip.numerical.common.NumberUtil.IsValid (dblTradeRateScale)) return null;

		try {
			org.drip.execution.strategy.ContinuousTradingTrajectory ctt =
				(org.drip.execution.strategy.ContinuousTradingTrajectory) new
					org.drip.execution.nonadaptive.ContinuousAlmgrenChriss (_os,
						org.drip.execution.dynamics.ArithmeticPriceEvolutionParametersBuilder.ReferenceCoordinatedVariation
						(_cv), _mvou).generate();

			if (null == ctt) return null;

			double dblNonDimensionalInstantTradeRate = ctt.tradeRate().evaluate (0.) / dblTradeRateScale;

			double dblNonDimensionalCostSensitivity = java.lang.Math.exp (ms.liquidity()) *
				dblNonDimensionalInstantTradeRate;

			return new org.drip.execution.hjb.NonDimensionalCostSystemic (0., dblNonDimensionalCostSensitivity,
				dblNonDimensionalCostSensitivity, dblNonDimensionalInstantTradeRate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Continuous Coordinated Variation Dynamic Adaptive Trajectory
	 * 
	 * @param aMS Array of Realized Market States
	 * 
	 * @return The Continuous Coordinated Variation Dynamic Adaptive Trajectory
	 */

	public org.drip.execution.adaptive.CoordinatedVariationDynamic adaptive (
		final org.drip.execution.latent.MarketState[] aMS)
	{
		if (null == aMS) return null;

		int iNumTimeNode = aMS.length;

		if (1 >= iNumTimeNode) return null;

		double dblExecutionSize = _os.size();

		double dblReferenceLiquidity = _cv.referenceLiquidity();

		double dblReferenceVolatility = _cv.referenceVolatility();

		double dblRelaxationTime = _ndce.ornsteinUnlenbeckProcess().referenceRelaxationTime();

		double dblNonDimensionalTimeIncrement = _os.maxExecutionTime() / (iNumTimeNode - 1) /
			dblRelaxationTime;

		double dblMeanMarketUrgency = dblReferenceVolatility * java.lang.Math.sqrt (_mvou.riskAversion() /
			dblReferenceLiquidity);

		org.drip.execution.hjb.NonDimensionalCost[] aNDC = new
			org.drip.execution.hjb.NonDimensionalCost[iNumTimeNode];
		double[] adblNonDimensionalScaledTradeRate = new double[iNumTimeNode];
		double dblTradeRateScale = dblExecutionSize / dblRelaxationTime;
		double[] adblNonDimensionalHoldings = new double[iNumTimeNode];
		adblNonDimensionalScaledTradeRate[0] = 0.;
		adblNonDimensionalHoldings[0] = 1.;

		if (null == (aNDC[0] = initializeNonDimensionalCost (aMS[0], dblTradeRateScale))) return null;

		for (int i = 1; i < iNumTimeNode; ++i) {
			if (null == (aNDC[i] = _ndce.evolve (aNDC[i - 1], aMS[i], dblMeanMarketUrgency *
				dblRelaxationTime, (iNumTimeNode - i) * dblNonDimensionalTimeIncrement,
					dblNonDimensionalTimeIncrement)))
				return null;

			adblNonDimensionalScaledTradeRate[i] = adblNonDimensionalHoldings[i - 1] *
				aNDC[i].nonDimensionalTradeRate();

			adblNonDimensionalHoldings[i] = adblNonDimensionalHoldings[i - 1] -
				adblNonDimensionalScaledTradeRate[i] * dblNonDimensionalTimeIncrement;
		}

		try {
			return new org.drip.execution.adaptive.CoordinatedVariationDynamic (new
				org.drip.execution.adaptive.CoordinatedVariationTrajectoryDeterminant (dblExecutionSize,
					dblRelaxationTime, dblReferenceLiquidity * dblExecutionSize * dblExecutionSize /
						dblTradeRateScale, dblTradeRateScale, dblMeanMarketUrgency, dblMeanMarketUrgency *
							dblRelaxationTime, dblReferenceLiquidity * dblExecutionSize /
								dblReferenceVolatility * java.lang.Math.pow (_os.maxExecutionTime(), -1.5)),
									adblNonDimensionalHoldings, adblNonDimensionalScaledTradeRate, aNDC);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Static, Non-adaptive Trading Trajectory Instance
	 * 
	 * @return The Static, Non-adaptive Trading Trajectory Instance
	 */

	public org.drip.execution.adaptive.CoordinatedVariationStatic nonAdaptive()
	{
		try {
			return new org.drip.execution.adaptive.CoordinatedVariationStatic (trajectoryDeterminant(),
				(org.drip.execution.optimum.EfficientTradingTrajectoryContinuous) new
					org.drip.execution.nonadaptive.ContinuousAlmgrenChriss (_os,
						org.drip.execution.dynamics.ArithmeticPriceEvolutionParametersBuilder.ReferenceCoordinatedVariation
						(_cv), _mvou).generate());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Continuous Coordinated Variation Rolling Horizon Trajectory
	 * 
	 * @param aMS Array of Realized Market States
	 * 
	 * @return The Continuous Coordinated Variation Rolling Horizon Trajectory
	 */

	public org.drip.execution.adaptive.CoordinatedVariationRollingHorizon rollingHorizon (
		final org.drip.execution.latent.MarketState[] aMS)
	{
		if (null == aMS) return null;

		int iNumTimeNode = aMS.length;
		double[] adblNonDimensionalCost = 0 == iNumTimeNode ? null : new double[iNumTimeNode];
		double[] adblNonDimensionalHoldings = 0 == iNumTimeNode ? null : new double[iNumTimeNode];
		double[] adblNonDimensionalTradeRate = 0 == iNumTimeNode ? null : new double[iNumTimeNode];

		if (0 == iNumTimeNode) return null;

		double dblExecutionSize = _os.size();

		double dblRiskAversion = _mvou.riskAversion();

		double dblExecutionTime = _os.maxExecutionTime();

		double dblReferenceLiquidity = _cv.referenceLiquidity();

		double dblReferenceVolatility = _cv.referenceVolatility();

		double dblRelaxationTime = _ndce.ornsteinUnlenbeckProcess().referenceRelaxationTime();

		double dblMeanMarketUrgency = dblReferenceVolatility * java.lang.Math.sqrt (dblRiskAversion /
			dblReferenceLiquidity);

		double dblNonDimensionalTimeIncrement = dblExecutionTime / (iNumTimeNode - 1) / dblRelaxationTime;
		double dblNonDimensionalExecutionTime = dblExecutionTime / dblRelaxationTime;
		double dblTradeRateScale = dblExecutionSize / dblRelaxationTime;
		adblNonDimensionalTradeRate[iNumTimeNode - 1] = 0.;
		adblNonDimensionalHoldings[0] = 1.;
		adblNonDimensionalCost[0] = 0.;

		for (int i = 0; i < iNumTimeNode - 1; ++i) {
			org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep = realizedLPEP
				(aMS[i].liquidity());

			if (null == lpep) return null;

			try {
				double dblRealizedVolatility = lpep.arithmeticPriceDynamicsSettings().epochVolatility();

				org.drip.execution.strategy.ContinuousTradingTrajectory ctt =
					(org.drip.execution.strategy.ContinuousTradingTrajectory) new
						org.drip.execution.nonadaptive.ContinuousAlmgrenChriss (new
							org.drip.execution.strategy.OrderSpecification (adblNonDimensionalHoldings[i],
								dblNonDimensionalExecutionTime - i * dblNonDimensionalTimeIncrement), lpep,
									_mvou).generate();

				if (null == ctt) return null;

				adblNonDimensionalTradeRate[i] = ctt.tradeRate().evaluate (0.);

				adblNonDimensionalHoldings[i + 1] = adblNonDimensionalHoldings[i] -
					adblNonDimensionalTradeRate[i] * dblNonDimensionalTimeIncrement;
				adblNonDimensionalCost[i + 1] = adblNonDimensionalCost[i] + (dblRiskAversion *
					dblRealizedVolatility * dblRealizedVolatility * adblNonDimensionalHoldings[i] *
						adblNonDimensionalHoldings[i] +
							lpep.linearPermanentExpectation().epochLiquidityFunction().slope() *
								adblNonDimensionalTradeRate[i] * adblNonDimensionalTradeRate[i]) *
									dblNonDimensionalTimeIncrement;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new org.drip.execution.adaptive.CoordinatedVariationRollingHorizon (new
				org.drip.execution.adaptive.CoordinatedVariationTrajectoryDeterminant (dblExecutionSize,
					dblRelaxationTime, dblReferenceLiquidity * dblExecutionSize * dblExecutionSize /
						dblTradeRateScale, dblTradeRateScale, dblMeanMarketUrgency, dblMeanMarketUrgency *
							dblRelaxationTime, dblReferenceLiquidity * dblExecutionSize /
								dblReferenceVolatility * java.lang.Math.pow (_os.maxExecutionTime(), -1.5)),
									adblNonDimensionalHoldings, adblNonDimensionalTradeRate,
										adblNonDimensionalCost);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
