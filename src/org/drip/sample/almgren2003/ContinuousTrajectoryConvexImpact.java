
package org.drip.sample.almgren2003;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.*;
import org.drip.execution.nonadaptive.ContinuousPowerImpact;
import org.drip.execution.optimum.PowerImpactContinuous;
import org.drip.execution.parameters.*;
import org.drip.execution.profiletime.*;
import org.drip.function.r1tor1.FlatUnivariate;
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
 * ContinuousTrajectoryConvexImpact reconciles the Characteristic Times of the Optimal Continuous Trading
 *  Trajectory resulting from the Application of the Almgren (2003) Scheme to a Convex Power Law Temporary
 *  Market Impact Function. The Power Exponent Considered here is k=2.0. The References are:
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

public class ContinuousTrajectoryConvexImpact {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblS0 = 50.;
		double dblDailyVolume = 1000000.;
		double dblBidAskSpread = 0.;
		double dblPermanentImpactFactor = 0.;
		double dblTemporaryImpactFactor = 0.01;
		double dblK = 2.0;
		double dblGamma = 0.;
		double dblDailyVolumeExecutionFactor = 0.1;
		double dblDrift = 0.;
		double dblVolatility = 1.;
		double dblSerialCorrelation = 0.;
		double dblX = 100000.;
		double dblFinishTime = 1.;

		double[] adblLambda = new double[] {
			1.e-03,
			1.e-04,
			1.e-05,
			1.e-06,
			1.e-07
		};

		double[][] aadblAlmgren2003Reconciler = new double[][] {
			{0.22, 462.,  30.},
			{0.46,  99.,  45.},
			{1.00,  21.,  65.},
			{2.15,   5.,  96.},
			{4.64,   1., 141.}
		};

		PriceMarketImpactPower pmip = new PriceMarketImpactPower (
			new AssetTransactionSettings (
				dblS0,
				dblDailyVolume,
				dblBidAskSpread
			),
			dblPermanentImpactFactor,
			dblTemporaryImpactFactor,
			dblDailyVolumeExecutionFactor,
			dblK
		);

		LinearPermanentExpectationParameters lpep = ArithmeticPriceEvolutionParametersBuilder.Almgren2003 (
			new ArithmeticPriceDynamicsSettings (
				dblDrift,
				new FlatUnivariate (dblVolatility),
				dblSerialCorrelation
			),
			new UniformParticipationRateLinear (
				new ParticipationRateLinear (
					0.,
					dblGamma
				)
			),
			new UniformParticipationRate ((ParticipationRatePower) pmip.temporaryTransactionFunction())
		);

		System.out.println ("\n\t|-------------------------------------------||");

		System.out.println ("\t|                  COMPUTED                 ||");

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t| LAMBDAINV || T_STAR | COST_EXP | COST_STD ||");

		System.out.println ("\t|-------------------------------------------||");

		for (int i = 0; i < adblLambda.length; ++i) {
			ContinuousPowerImpact cpi = ContinuousPowerImpact.Standard (
				dblX,
				dblFinishTime,
				lpep,
				adblLambda[i]
			);

			PowerImpactContinuous pic = (PowerImpactContinuous) cpi.generate();

			System.out.println ("\t|  " +
				FormatUtil.FormatDouble (1. / adblLambda[i], 5, 0, 1.e-03) + "   || " +
				FormatUtil.FormatDouble (pic.characteristicTime(), 1, 2, 1.) + "      " +
				FormatUtil.FormatDouble (pic.transactionCostExpectation(), 3, 0, 1.e-03) + "       " +
				FormatUtil.FormatDouble (Math.sqrt (pic.transactionCostVariance()), 3, 0, 1.e-03) + "   ||"
			);
		}

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\n\t|-------------------------------------------||");

		System.out.println ("\t|               ALMGREN (2003)              ||");

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t| LAMBDAINV || T_STAR | COST_EXP | COST_STD ||");

		System.out.println ("\t|-------------------------------------------||");

		for (int i = 0; i < adblLambda.length; ++i)
			System.out.println ("\t|  " +
				FormatUtil.FormatDouble (1. / adblLambda[i], 5, 0, 1.e-03) + "   || " +
				FormatUtil.FormatDouble (aadblAlmgren2003Reconciler[i][0], 1, 2, 1.) + "      " +
				FormatUtil.FormatDouble (aadblAlmgren2003Reconciler[i][1], 3, 0, 1.) + "       " +
				FormatUtil.FormatDouble (aadblAlmgren2003Reconciler[i][2], 3, 0, 1.) + "   ||"
			);

		System.out.println ("\t|-------------------------------------------||");
	}
}
