
package org.drip.sample.almgren2009;

import org.drip.execution.tradingtime.*;
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
 * CoordinatedMarketStateTrajectory traces a Sample Realization of the Market State Trajectory the follows
 *  the Zero Mean Ornstein-Uhlenbeck Evolution Dynamics. The References are:
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

public class CoordinatedMarketStateTrajectory {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblBurstiness = 1.;
		double dblRelaxationTime = 1.;
		double dblSimulationTime = 9.75;
		int iNumSimulation = 39;
		double dblReferenceLiquidity = 1.0;
		double dblReferenceVolatility = 1.0;
		double dblInitialMarketState = -0.5;

		double dblTime = 0.;
		double dblMarketState = dblInitialMarketState;
		double dblTimeInterval = dblSimulationTime / iNumSimulation;

		DiffusionEvaluatorOrnsteinUhlenbeck oup1D = DiffusionEvaluatorOrnsteinUhlenbeck.ZeroMean (
			dblBurstiness,
			dblRelaxationTime
		);

		DiffusionEvolver de = new DiffusionEvolver (oup1D);

		CoordinatedMarketState cms = new CoordinatedMarketState (
			new CoordinatedVariation (
				dblReferenceVolatility,
				dblReferenceLiquidity
			)
		);

		double dblLiquidity = cms.liquidity (dblMarketState);

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                       ||");

		System.out.println ("\t||            - Realized Market State                               ||");

		System.out.println ("\t||            - Realized Volatility                                 ||");

		System.out.println ("\t||            - Realized Liquidity                                  ||");

		System.out.println ("\t||            - Liquidity/Volatility Status                         ||");

		System.out.println ("\t||------------------------------------------------------------------||");

		System.out.println ("\t|| [" + 
			FormatUtil.FormatDouble (0., 1, 2, 1.) + "] => " +
			FormatUtil.FormatDouble (dblMarketState, 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (cms.volatility (dblMarketState), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (cms.liquidity (dblMarketState), 1, 4, 1.) + " | " +
			(
				dblLiquidity < dblReferenceLiquidity ?
				"  LIQUID,     VOLATILE " :
				"ILLIQUID, NON-VOLATILE "
			) +
			" ||"
		);

		for (int i = 0; i < iNumSimulation; ++i) {
			JumpDiffusionEdge gi = de.weinerIncrement (
				new JumpDiffusionVertex (
					dblTime,
					dblMarketState,
					0.,
					false
				),
				dblTimeInterval
			);

			dblTime += dblTimeInterval;

			dblMarketState += gi.deterministic() + gi.diffusionStochastic();

			dblLiquidity = cms.liquidity (dblMarketState);

			System.out.println ("\t|| [" + 
				FormatUtil.FormatDouble (dblTimeInterval * (i + 1), 1, 2, 1.) + "] => " +
				FormatUtil.FormatDouble (dblMarketState, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (cms.volatility (dblMarketState), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblLiquidity, 1, 4, 1.) + " | " +
				(
					dblLiquidity < dblReferenceLiquidity ?
					"  LIQUID,     VOLATILE " :
					"ILLIQUID, NON-VOLATILE "
				) +
				" ||"
			);
		}

		System.out.println ("\t||------------------------------------------------------------------||");

		System.out.println();
	}
}
