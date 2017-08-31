
package org.drip.execution.dynamics;

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
 * ArithmeticPriceEvolutionParametersBuilder constructs a variety of Arithmetic Price Evolution Parameters.
 *  The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk,
 * 		Applied Mathematical Finance 10 (1) 1-18.
 *
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 97-102.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ArithmeticPriceEvolutionParametersBuilder {

	/**
	 * Linear Expectation Version of LinearPermanentExpectationParameters Instance
	 * 
	 * @param apds The Asset Price Dynamics Settings
	 * @param bprlPermanentExpectation The Background Participation Rate Linear Permanent Expectation Market
	 * 	Impact Function
	 * @param bprlTemporaryExpectation The Background Participation Rate Linear Temporary Market Impact
	 * 	Expectation Function
	 * 
	 * @return Linear Expectation Version of LinearPermanentExpectationParameters Instance
	 */

	public static final org.drip.execution.dynamics.LinearPermanentExpectationParameters LinearExpectation (
		final org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds,
		final org.drip.execution.profiletime.BackgroundParticipationRateLinear bprlPermanentExpectation,
		final org.drip.execution.profiletime.BackgroundParticipationRateLinear bprlTemporaryExpectation)
	{
		try {
			return new org.drip.execution.dynamics.LinearPermanentExpectationParameters (apds,
				bprlPermanentExpectation, bprlTemporaryExpectation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Almgren 2003 Version of LinearPermanentExpectationParameters Instance
	 * 
	 * @param apds The Asset Price Dynamics Settings
	 * @param bprlPermanentExpectation The Background Participation Rate Linear Permanent Expectation Market
	 * 	Impact Function
	 * @param bprTemporaryExpectation The Participation Rate Power Temporary Market Impact Expectation
	 * 	Function
	 * 
	 * @return Almgren 2003 Version of LinearPermanentExpectationParameters Instance
	 */

	public static final org.drip.execution.dynamics.LinearPermanentExpectationParameters Almgren2003 (
		final org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds,
		final org.drip.execution.profiletime.BackgroundParticipationRateLinear bprlPermanentExpectation,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprTemporaryExpectation)
	{
		try {
			return new org.drip.execution.dynamics.LinearPermanentExpectationParameters (apds,
				bprlPermanentExpectation, bprTemporaryExpectation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Trading Enhanced Volatility ArithmeticPriceEvolutionParameters Instance
	 * 
	 * @param dblPriceVolatility The Daily Price Volatility Parameter
	 * @param bprlTemporaryExpectation The Background Participation Linear Temporary Market Impact
	 * 	Expectation Function
	 * @param bprlTemporaryVolatility The Background Participation Linear Temporary Market Impact
	 * 	Volatility Function
	 * 
	 * @return The Trading Enhanced Volatility /ArithmeticPriceEvolutionParameters Instance
	 */

	public static final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters
		TradingEnhancedVolatility (
			final double dblPriceVolatility,
			final org.drip.execution.profiletime.BackgroundParticipationRateLinear bprlTemporaryExpectation,
			final org.drip.execution.profiletime.BackgroundParticipationRateLinear bprlTemporaryVolatility)
	{
		try {
			return new org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters (new
				org.drip.execution.parameters.ArithmeticPriceDynamicsSettings (0., new
					org.drip.function.r1tor1.FlatUnivariate (dblPriceVolatility), 0.), new
						org.drip.execution.profiletime.UniformParticipationRate
							(org.drip.execution.impact.ParticipationRateLinear.NoImpact()),
								bprlTemporaryExpectation, new
									org.drip.execution.profiletime.UniformParticipationRate
										(org.drip.execution.impact.ParticipationRateLinear.NoImpact()),
											bprlTemporaryVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Arithmetic Price Evolution Parameters from Coordinated Variation Instance
	 * 
	 * @param r1ToR1Volatility The R^1 To R^1 Volatility Function
	 * @param cv The Coordinated Volatility/Liquidity Variation
	 * 
	 * @return The Arithmetic Price Evolution Parameters from Coordinated Variation Instance
	 */

	public static final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters CoordinatedVariation (
		final org.drip.function.definition.R1ToR1 r1ToR1Volatility,
		final org.drip.execution.tradingtime.CoordinatedVariation cv)
	{
		try {
			return new org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters (new
				org.drip.execution.parameters.ArithmeticPriceDynamicsSettings (0., r1ToR1Volatility, 0.), new
					org.drip.execution.profiletime.UniformParticipationRate
						(org.drip.execution.impact.ParticipationRateLinear.NoImpact()), new
							org.drip.execution.tradingtime.CoordinatedParticipationRateLinear (cv,
								r1ToR1Volatility), new
									org.drip.execution.profiletime.UniformParticipationRate
										(org.drip.execution.impact.ParticipationRateLinear.NoImpact()), new
											org.drip.execution.profiletime.UniformParticipationRate
												(org.drip.execution.impact.ParticipationRateLinear.NoImpact()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Arithmetic Price Evolution Parameters from a Deterministic Coordinated Variation Instance
	 * 
	 * @param dblPriceVolatility The Daily Price Volatility Parameter
	 * @param cv The Coordinated Volatility/Liquidity Variation
	 * 
	 * @return The Arithmetic Price Evolution Parameters from a Deterministic Coordinated Variation Instance
	 */

	public static final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters
		DeterministicCoordinatedVariation (
			final double dblPriceVolatility,
			final org.drip.execution.tradingtime.CoordinatedVariation cv)
	{
		try {
			return CoordinatedVariation (new org.drip.function.r1tor1.FlatUnivariate (dblPriceVolatility),
				cv);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Linear Permanent Evolution Parameters from a Deterministic Coordinated Variation Instance
	 * 
	 * @param cv The Coordinated Volatility/Liquidity Variation
	 * 
	 * @return The Linear Permanent Evolution Parameters from a Deterministic Coordinated Variation Instance
	 */

	public static final org.drip.execution.dynamics.LinearPermanentExpectationParameters
		ReferenceCoordinatedVariation (
			final org.drip.execution.tradingtime.CoordinatedVariation cv)
	{
		if (null == cv) return null;

		try {
			return new org.drip.execution.dynamics.LinearPermanentExpectationParameters (new
				org.drip.execution.parameters.ArithmeticPriceDynamicsSettings (0., new
					org.drip.function.r1tor1.FlatUnivariate (cv.referenceVolatility()), 0.), new
						org.drip.execution.profiletime.UniformParticipationRateLinear
							(org.drip.execution.impact.ParticipationRateLinear.NoImpact()), new
								org.drip.execution.profiletime.UniformParticipationRateLinear
									(org.drip.execution.impact.ParticipationRateLinear.SlopeOnly
										(cv.referenceLiquidity())));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
