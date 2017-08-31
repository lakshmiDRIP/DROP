
package org.drip.sample.almgren2009;

import org.drip.execution.hjb.*;
import org.drip.execution.latent.MarketStateSystemic;
import org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
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
 * AdaptiveOptimalCostTrajectory traces a Sample Realization of the Adaptive Cost Strategy using the Market
 *  State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck Evolution Dynamics. The References are:
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
 * 	- Walia, N. (2006): Optimal Trading: Dynamic Stock Liquidation Strategies, Senior Thesis, Princeton
 * 		University.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdaptiveOptimalCostTrajectory {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblTime = 0.;
		double dblBurstiness = 1.;
		double dblDimensionlessRiskAversion = 0.1;
		double dblRelaxationTime = 1.;
		double dblSimulationTime = 10.;
		double dblTimeInterval = 0.25;
		double dblInitialMarketState = -0.5;

		double dblNonDimensionalHoldings = 1.;
		int iNumTimeNode = (int) (dblSimulationTime / dblTimeInterval);
		MarketStateSystemic[] aMSS = new MarketStateSystemic[iNumTimeNode + 1];

		aMSS[0] = new MarketStateSystemic (dblInitialMarketState);

		DiffusionEvaluatorOrnsteinUhlenbeck deou = DiffusionEvaluatorOrnsteinUhlenbeck.ZeroMean (
			dblBurstiness,
			dblRelaxationTime
		);

		DiffusionEvolver oup1D = new DiffusionEvolver (deou);

		for (int i = 0; i < iNumTimeNode; ++i) {
			JumpDiffusionEdge gi = oup1D.weinerIncrement (
				new JumpDiffusionVertex (
					dblTime,
					aMSS[i].common(),
					0.,
					false
				),
				dblTimeInterval
			);

			dblTime += dblTimeInterval;

			aMSS[i + 1] = new MarketStateSystemic (aMSS[i].common() + gi.deterministic() + gi.diffusionStochastic());
		}

		NonDimensionalCostEvolverSystemic ndces = NonDimensionalCostEvolverSystemic.Standard (deou);

		NonDimensionalCostSystemic ndcs = NonDimensionalCostSystemic.Zero();

		System.out.println();

		System.out.println ("\t||-------------------------------------------------------------------||");

		System.out.println ("\t||      L -> R:                                                      ||");

		System.out.println ("\t||              - Non Dimensional Time                               ||");

		System.out.println ("\t||              - Realized Market State                              ||");

		System.out.println ("\t||              - Non Dimensional Cost                               ||");

		System.out.println ("\t||              - Non Dimensional Cost Gradient                      ||");

		System.out.println ("\t||              - Non Dimensional Cost Jacobian                      ||");

		System.out.println ("\t||              - Non Dimensional Cost Trade Velocity                ||");

		System.out.println ("\t||              - Non Dimensional Outstanding Holdings               ||");

		System.out.println ("\t||-------------------------------------------------------------------||");

		System.out.println ("\t||" + 
			FormatUtil.FormatDouble (0., 1, 2, 1.) + " => " +
			FormatUtil.FormatDouble (aMSS[0].common(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ndcs.realization(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ndcs.gradient(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ndcs.jacobian(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ndcs.nonDimensionalTradeRate(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (dblNonDimensionalHoldings, 1, 4, 1.) + " ||"
		);

		for (int i = 1; i < iNumTimeNode; ++i) {
			ndcs = (NonDimensionalCostSystemic) ndces.evolve (
				ndcs,
				aMSS[i],
				dblDimensionlessRiskAversion,
				(iNumTimeNode - i) * dblTimeInterval,
				dblTimeInterval
			);

			double dblNonDimensionalTradeRate = dblNonDimensionalHoldings * ndcs.nonDimensionalTradeRate();

			dblNonDimensionalHoldings = dblNonDimensionalHoldings - dblNonDimensionalTradeRate * dblTimeInterval;

			System.out.println ("\t||" + 
				FormatUtil.FormatDouble (dblTimeInterval * i, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (aMSS[i].common(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (ndcs.realization(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (ndcs.gradient(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (ndcs.jacobian(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblNonDimensionalTradeRate, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblNonDimensionalHoldings, 1, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t||-------------------------------------------------------------------||");

		System.out.println();
	}
}
