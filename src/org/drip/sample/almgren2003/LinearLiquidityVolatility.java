
package org.drip.sample.almgren2003;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.ParticipationRateLinear;
import org.drip.execution.nonadaptive.DiscreteLinearTradingEnhanced;
import org.drip.execution.optimum.TradingEnhancedDiscrete;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * LinearLiquidityVolatility demonstrates the Dependence of the Optimal Trading Trajectory as a Function of
 *  Linear Trading Enhanced Volatilities. The References are:
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

public class LinearLiquidityVolatility {

	private static final void BetaRun (
		final double dblBeta,
		final double dblT,
		final int iNumInterval)
		throws Exception
	{
		double dblEta = 5.e-06;
		double dblSigma = 1.;
		double dblLambda = 1.e-05;
		double dblX = 100000.;

		ArithmeticPriceEvolutionParameters apep = ArithmeticPriceEvolutionParametersBuilder.TradingEnhancedVolatility (
			dblSigma,
			new UniformParticipationRateLinear (ParticipationRateLinear.SlopeOnly (dblEta)),
			new UniformParticipationRateLinear (
				new ParticipationRateLinear (
					0.,
					dblBeta
				)
			)
		);

		DiscreteLinearTradingEnhanced dlte = DiscreteLinearTradingEnhanced.Standard (
			dblX,
			dblT,
			iNumInterval,
			apep,
			dblLambda
		);

		TradingEnhancedDiscrete ted = (TradingEnhancedDiscrete) dlte.generate();

		double[] adblExecutionTimeNode = ted.executionTimeNode();

		double[] adblHoldings = ted.holdings();

		String strDump = "\t|" + FormatUtil.FormatDouble (dblBeta, 1, 1, 1.e+06) + " =>";

		for (int i = 0; i < adblExecutionTimeNode.length; ++i)
			strDump = strDump + FormatUtil.FormatDouble (adblHoldings[i] / dblX, 2, 1, 100.) + "% ";

		strDump = strDump + FormatUtil.FormatDouble (ted.transactionCostExpectation(), 5, 0, 1.) + " | ";

		strDump = strDump + FormatUtil.FormatDouble (ted.transactionCostVariance(), 5, 0, 1.e-06) + " | ";

		strDump = strDump + FormatUtil.FormatDouble (ted.characteristicTime(), 1, 3, 1.) + " | ";

		strDump = strDump + FormatUtil.FormatDouble (ted.characteristicSize(), 6, 0, 1.) + " ||";

		System.out.println (strDump);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblT = 5.;
		int iNumInterval = 10;

		double[] adblBeta = new double[] {
			0.1,
			0.2,
			0.3,
			0.4,
			0.5,
			0.6,
			0.7,
			0.8,
			0.9,
			1.0,
			1.1,
			1.2,
			1.3,
			1.4,
			1.5,
			1.6,
			1.7,
			1.8,
			1.9,
			2.0
		};

		System.out.println();

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                        ALMGREN (2003) LINEAR TEMPORARY IMPACT VOLATILITY - OFFSET DEPENDENCE                           ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|      L -> R:                                                                                                           ||");

		System.out.println ("\t|              Beta Level (X 10^06)                                                                                      ||");

		System.out.println ("\t|              Outstanding Trajectory (%)                                                                                ||");

		System.out.println ("\t|              Transaction Cost Expectation                                                                              ||");

		System.out.println ("\t|              Transaction Cost Variance (X 10^-06)                                                                      ||");

		System.out.println ("\t|              Characteristic Time (Days)                                                                                ||");

		System.out.println ("\t|              Characteristic Size                                                                                       ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------||");

		String strTimeNode = "\t|        ";

		for (int i = 0; i <= iNumInterval; ++i)
			strTimeNode = strTimeNode + FormatUtil.FormatDouble (dblT * i / iNumInterval, 1, 2, 1.) + "  ";

		System.out.println (strTimeNode);

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------||");

		for (double dblBeta : adblBeta)
			BetaRun (
				dblBeta * 1.e-06,
				dblT,
				iNumInterval
			);

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------||");
	}
}
