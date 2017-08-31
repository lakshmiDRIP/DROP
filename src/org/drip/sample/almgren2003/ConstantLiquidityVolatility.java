
package org.drip.sample.almgren2003;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.ParticipationRateLinear;
import org.drip.execution.nonadaptive.ContinuousConstantTradingEnhanced;
import org.drip.execution.optimum.EfficientTradingTrajectoryContinuous;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.function.definition.R1ToR1;
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
 * ConstantLiquidityVolatility demonstrates the Dependence of the Optimal Trading Trajectory as a Function of
 *  Constant Trading Enhanced Volatilities. The References are:
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

public class ConstantLiquidityVolatility {

	private static final void AlphaRun (
		final double dblAlpha,
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
					dblAlpha,
					0.
				)
			)
		);

		ContinuousConstantTradingEnhanced ccte = ContinuousConstantTradingEnhanced.Standard (
			dblX,
			dblT,
			apep,
			dblLambda
		);

		EfficientTradingTrajectoryContinuous ettc = (EfficientTradingTrajectoryContinuous) ccte.generate();

		R1ToR1 r1ToR1Holdings = ettc.holdings();

		double[] adblHoldings = new double[iNumInterval];
		double[] adblExecutionTime = new double[iNumInterval];

		for (int i = 1; i <= iNumInterval; ++i) {
			adblExecutionTime[i - 1] = dblT * i / iNumInterval;

			adblHoldings[i - 1] = r1ToR1Holdings.evaluate (adblExecutionTime[i - 1]);
		}

		String strDump = "\t|" + FormatUtil.FormatDouble (dblAlpha, 1, 1, 1.) + " =>";

		for (int i = 0; i < adblExecutionTime.length; ++i)
			strDump = strDump + FormatUtil.FormatDouble (adblHoldings[i] / dblX, 2, 1, 100.) + "% ";

		strDump = strDump + FormatUtil.FormatDouble (ettc.transactionCostExpectation(), 5, 0, 1.) + " | ";

		strDump = strDump + FormatUtil.FormatDouble (ettc.transactionCostVariance(), 5, 0, 1.e-06) + " | ";

		strDump = strDump + FormatUtil.FormatDouble (ettc.characteristicTime(), 1, 3, 1.) + " ||";

		System.out.println (strDump);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblT = 5.;
		int iNumInterval = 10;

		double[] adblAlpha = new double[] {
			0.0,
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

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                   ALMGREN (2003) CONSTANT TEMPORARY IMPACT VOLATILITY - OFFSET DEPENDENCE                    ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|      L -> R:                                                                                                 ||");

		System.out.println ("\t|              Alpha Level                                                                                     ||");

		System.out.println ("\t|              Outstanding Trajectory (%)                                                                      ||");

		System.out.println ("\t|              Transaction Cost Expectation                                                                    ||");

		System.out.println ("\t|              Transaction Cost Variance (X 10^-06)                                                            ||");

		System.out.println ("\t|              Characteristic Time (Days)                                                                      ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------||");

		String strTimeNode = "\t|        ";

		for (int i = 0; i <= iNumInterval; ++i)
			strTimeNode = strTimeNode + FormatUtil.FormatDouble (dblT * i / iNumInterval, 1, 2, 1.) + "  ";

		System.out.println (strTimeNode);

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------||");

		for (double dblAlpha : adblAlpha)
			AlphaRun (
				dblAlpha,
				dblT,
				iNumInterval
			);

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------||");
	}
}
