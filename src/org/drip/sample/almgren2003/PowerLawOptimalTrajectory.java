
package org.drip.sample.almgren2003;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.*;
import org.drip.execution.nonadaptive.ContinuousPowerImpact;
import org.drip.execution.optimum.PowerImpactContinuous;
import org.drip.execution.parameters.ArithmeticPriceDynamicsSettings;
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
 * PowerLawOptimalTrajectory sketches out the Optimal Trajectories for 3 different values of k - representing
 * 	Concave, Linear, and Convex Power's respectively. The References are:
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

public class PowerLawOptimalTrajectory {

	private static final void RiskAversionRun (
		final double dblLambda)
		throws Exception
	{
		double dblGamma = 0.;
		double dblHRef = 0.50;
		double dblVRef = 100000.;
		double dblDrift = 0.;
		double dblVolatility = 1.;
		double dblSerialCorrelation = 0.;
		double dblX = 100000.;
		double dblFinishTime = 10.;
		int iNumInterval = 10;

		double[] adblK = new double[] {
			0.25,
			0.50,
			0.75,
			1.00,
			1.25,
			1.50,
			1.75,
			2.00,
			2.25,
			2.50,
			2.75,
			3.00
		};

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|\tPOWER LAW OPTIMAL TRAJECTORY; RISK TOLERANCE (thousands) => " + FormatUtil.FormatDouble (1. / dblLambda, 1, 0, 1.e-03));

		System.out.println ("\t|");

		System.out.println ("\t|\t\tL -> R:");

		System.out.println ("\t|\t\t\tTime Node Trajectory Realization (Percent)");

		System.out.println ("\t|\t\t\tCharacteristic Time (Days)");

		System.out.println ("\t|\t\t\tMaximum Execution Time (Days)");

		System.out.println ("\t|\t\t\tTransaction Cost Expectation (Thousands)");

		System.out.println ("\t|\t\t\tTransaction Cost Variance (Thousands)");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------||");

		ArithmeticPriceDynamicsSettings apds = new ArithmeticPriceDynamicsSettings (
			dblDrift,
			new FlatUnivariate (dblVolatility),
			dblSerialCorrelation
		);

		ParticipationRateLinear prlPermanent = new ParticipationRateLinear (
			0.,
			dblGamma
		);

		double[] adblExecutionTime = new double[iNumInterval];

		for (int i = 1; i <= iNumInterval; ++i)
			adblExecutionTime[i - 1] = ((double) i) / ((double) iNumInterval);

		for (int i = 0; i < adblK.length; ++i) {
			double dblEta = dblHRef / java.lang.Math.pow (dblVRef, adblK[i]);

			LinearPermanentExpectationParameters lpep = ArithmeticPriceEvolutionParametersBuilder.Almgren2003 (
				apds,
				new UniformParticipationRateLinear (prlPermanent),
				new UniformParticipationRate (
					new ParticipationRatePower (
						dblEta,
						adblK[i]
					)
				)
			);

			ContinuousPowerImpact cpi = ContinuousPowerImpact.Standard (
				dblX,
				dblFinishTime,
				lpep,
				dblLambda
			);

			PowerImpactContinuous pic = (PowerImpactContinuous) cpi.generate();

			if (0 == i) {
				String strExecutionTime = "\t|          |  ";

				for (int j = 0; j < adblExecutionTime.length; ++j)
					strExecutionTime = strExecutionTime + "   " + FormatUtil.FormatDouble (adblExecutionTime[j], 1, 2, 1.);

				System.out.println (strExecutionTime);

				System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------||");
			}

			R1ToR1 r1ToR1Holdings = pic.holdings();

			String strHoldings = "\t| k =" + FormatUtil.FormatDouble (adblK[i], 1, 2, 1.) + " | ";

			for (int j = 0; j < iNumInterval; ++j)
				strHoldings = strHoldings + "  " + FormatUtil.FormatDouble (r1ToR1Holdings.evaluate (adblExecutionTime[j]) / dblX, 2, 2, 100.);

			double dblExecutionTimeUpperBound = pic.executionTimeUpperBound();

			System.out.println (
				strHoldings + " | " +
				FormatUtil.FormatDouble (pic.characteristicTime(), 2, 1, 1.) + " | " +
				FormatUtil.FormatDouble (Double.isNaN (dblExecutionTimeUpperBound) ? 0. : dblExecutionTimeUpperBound, 2, 1, 1.) + " | " +
				FormatUtil.FormatDouble (pic.transactionCostExpectation(), 3, 0, 1.e-03) + " | " +
				FormatUtil.FormatDouble (Math.sqrt (pic.transactionCostVariance()), 3, 0, 1.e-03) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] adblLambda = new double[] {
			1.e-04,
			5.e-06,
			5.e-07
		};

		for (double dblLambda : adblLambda)
			RiskAversionRun (dblLambda);
	}
}
