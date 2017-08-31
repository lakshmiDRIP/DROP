
package org.drip.sample.principal;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.*;
import org.drip.execution.nonadaptive.ContinuousPowerImpact;
import org.drip.execution.optimum.PowerImpactContinuous;
import org.drip.execution.parameters.*;
import org.drip.execution.principal.Almgren2003Estimator;
import org.drip.execution.profiletime.*;
import org.drip.function.definition.R1ToR1;
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
 * OptimalTrajectoryMeasures demonstrates the Trade Scheduling using the Equity Market Impact Functions
 *  determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 *  (2003) for IBM. It generates the Transaction Cost/Principal Discount Measures from the Run The References
 *  are:
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
 * 	- Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact, Risk 18 (7) 57-62.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OptimalTrajectoryMeasures {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblS0 = 50.;
		double dblX = 100000.;
		double dblVolatility = 1.;
		double dblDailyVolume = 1000000.;
		double dblDailyVolumeExecutionFactor = 0.1;
		double dblPermanentImpactFactor = 0.;
		double dblTemporaryImpactFactor = 0.01;
		double dblT = 5.;
		int iNumInterval = 20;
		double dblLambda = 1.e-06;
		double dblK = 1.;
		double dblPrincipalDiscount = 0.15;

		PriceMarketImpactPower pmip = new PriceMarketImpactPower (
			new AssetTransactionSettings (
				dblS0,
				dblDailyVolume,
				0.
			),
			dblPermanentImpactFactor,
			dblTemporaryImpactFactor,
			dblDailyVolumeExecutionFactor,
			dblK
		);

		LinearPermanentExpectationParameters lpep = ArithmeticPriceEvolutionParametersBuilder.Almgren2003 (
			new ArithmeticPriceDynamicsSettings (
				0.,
				new FlatUnivariate (dblVolatility),
				0.
			),
			new UniformParticipationRateLinear ((ParticipationRateLinear) pmip.permanentTransactionFunction()),
			new UniformParticipationRate ((ParticipationRatePower) pmip.temporaryTransactionFunction())
		);

		ContinuousPowerImpact cpi = ContinuousPowerImpact.Standard (
			dblX,
			dblT,
			lpep,
			dblLambda
		);

		PowerImpactContinuous pic = (PowerImpactContinuous) cpi.generate();

		R1ToR1 r1ToR1Holdings = pic.holdings();

		double[] adblHoldings = new double[iNumInterval];
		double[] adblExecutionTime = new double[iNumInterval];

		for (int i = 1; i <= iNumInterval; ++i) {
			adblExecutionTime[i - 1] = dblT * i / iNumInterval;

			adblHoldings[i - 1] = r1ToR1Holdings.evaluate (adblExecutionTime[i - 1]);
		}

		Almgren2003Estimator a2003e = new Almgren2003Estimator (
			pic,
			lpep
		);

		System.out.println();

		System.out.println ("\t|----------------------------------||");

		System.out.println ("\t| IBM ATHL 2005 Optimal Trajectory ||");

		System.out.println ("\t|----------------------------------||");

		System.out.println ("\t|     L -> R:                      ||");

		System.out.println ("\t|           - Execution Time Node  ||");

		System.out.println ("\t|           - Holdings Remaining   ||");

		System.out.println ("\t|           - Trade List Amount    ||");

		System.out.println ("\t|           - Holdings (%)         ||");

		System.out.println ("\t|----------------------------------||");

		for (int i = 1; i < adblExecutionTime.length; ++i)
			System.out.println (
				"\t| " +
				FormatUtil.FormatDouble (adblExecutionTime[i], 1, 2, 1.) + " | " +
				FormatUtil.FormatDouble (adblHoldings[i], 5, 0, 1.) + " | " + 
				FormatUtil.FormatDouble (adblHoldings[i] - adblHoldings[i - 1], 5, 0, 1.) + " | " + 
				FormatUtil.FormatDouble (adblHoldings[i] / dblX, 2, 1, 100.) + "% ||"
			);

		System.out.println ("\t|----------------------------------||");

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println ("\t|       IBM ATHL 2005 Optimal Trajectory Transaction Cost Measures      ||");

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println (
			"\t| Transaction Cost Expectation ( X 10^-03)                  : " +
			FormatUtil.FormatDouble (pic.transactionCostExpectation(), 5, 2, 1.e-03) + " ||"
		);

		System.out.println (
			"\t| Transaction Cost Variance ( X 10^-06)                     : " +
			FormatUtil.FormatDouble (pic.transactionCostVariance(), 5, 2, 1.e-06) + " ||"
		);

		System.out.println (
			"\t| Characteristic Time                                       : " +
			FormatUtil.FormatDouble (pic.characteristicTime(), 5, 2, 1.) + " ||"
		);

		System.out.println (
			"\t| Efficient Frontier Hyperboloid Boundary Value ( X 10^-12) : " +
			FormatUtil.FormatDouble (pic.hyperboloidBoundaryValue(), 5, 2, 1.e-12) + " ||"
		);

		System.out.println (
			"\t| Break-even Principal Discount (cents per unit)            : " +
			FormatUtil.FormatDouble (a2003e.breakevenPrincipalDiscount(), 5, 2, 100.) + " ||"
		);

		System.out.println (
			"\t| Gross Profit Expectation                                  : " +
			FormatUtil.FormatDouble (a2003e.principalMeasure (dblPrincipalDiscount).mean(), 5, 2, 1.) + " ||"
		);

		System.out.println (
			"\t| Gross Profit Variance ( X 10^-06)                         : " +
			FormatUtil.FormatDouble (a2003e.principalMeasure (dblPrincipalDiscount).variance(), 5, 2, 1.e-06) + " ||"
		);

		System.out.println (
			"\t| Gross Returns Expectation                                 : " +
			FormatUtil.FormatDouble (a2003e.horizonPrincipalMeasure (dblPrincipalDiscount).mean(), 5, 2, 1.) + " ||"
		);

		System.out.println (
			"\t| Gross Returns Variance ( X 10^-06)                        : " +
			FormatUtil.FormatDouble (a2003e.horizonPrincipalMeasure (dblPrincipalDiscount).variance(), 5, 2, 1.e-06) + " ||"
		);

		System.out.println (
			"\t| Information Ratio ( X 10^+03)                             : " +
			FormatUtil.FormatDouble (a2003e.informationRatio (dblPrincipalDiscount), 5, 2, 1.e+03) + " ||"
		);

		System.out.println (
			"\t| Optimal Information Ratio ( X 10^+03)                     : " +
			FormatUtil.FormatDouble (a2003e.optimalInformationRatio (dblPrincipalDiscount), 5, 2, 1.e+03) + " ||"
		);

		System.out.println (
			"\t| Optimal Information Ratio Horizon                         : " +
			FormatUtil.FormatDouble (a2003e.optimalInformationRatioHorizon (dblPrincipalDiscount), 5, 2, 1.) + " ||"
		);

		System.out.println ("\t|-----------------------------------------------------------------------||");
	}
}
