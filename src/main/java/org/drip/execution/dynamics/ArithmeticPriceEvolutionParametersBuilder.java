
package org.drip.execution.dynamics;

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
 * <i>ArithmeticPriceEvolutionParametersBuilder</i> constructs a variety of Arithmetic Price Evolution
 * Parameters. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 * 		</li>
 * 		<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/dynamics/README.md">Arithmetic Price Evolution Execution Parameters</a></li>
 *  </ul>
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
