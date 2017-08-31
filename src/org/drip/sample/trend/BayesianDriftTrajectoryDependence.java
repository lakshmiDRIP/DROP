
package org.drip.sample.trend;

import org.drip.execution.bayesian.*;
import org.drip.execution.cost.LinearTemporaryImpact;
import org.drip.execution.impact.ParticipationRateLinear;
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
 * BayesianDriftTrajectoryDependence demonstrates the Dependence of the Trading Trajectory achieved from
 *  using an Optimal Trajectory for a Price Process as a Function of the Bayesian Drift Parameters. The
 *  References are:
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets 1
 * 		1-50.
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading, Journal of Finance 60 (4) 1825-1863.
 *
 * 	- Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle, Journal of Trading 1
 * 		(4) 38-46.
 * 
 * 	- Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework, Journal of Trading 1 (1)
 * 		12-21.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BayesianDriftTrajectoryDependence {

	static final void RunScenario (
		final double dblAlphaBar,
		final double dblNu,
		final double dblSigma,
		final double dblT,
		final ParticipationRateLinear prlTemporary)
		throws Exception
	{
		PriorDriftDistribution pdd = new PriorDriftDistribution (
			dblAlphaBar,
			dblNu
		);

		double dblTimeWidth = 0.5 * dblT;

		double[] adblAlpha = pdd.realizedDrift (2);

		ConditionalPriceDistribution cpd0 = new ConditionalPriceDistribution (
			adblAlpha[0],
			dblSigma,
			1.0 * dblTimeWidth
		);

		double dblPriceSwing0 = cpd0.priceVolatilitySwing();

		double dblRealizedPriceChange0 = adblAlpha[0] * dblTimeWidth + dblPriceSwing0;

		PriorConditionalCombiner pcc0 = new PriorConditionalCombiner (
			pdd,
			cpd0
		);

		LinearTemporaryImpact lti0 = LinearTemporaryImpact.Unconstrained (
			1.0 * dblTimeWidth,
			dblT,
			1.,
			pcc0,
			dblRealizedPriceChange0,
			prlTemporary
		);

		double dblInstantanenousTradeRate0 = lti0.instantaneousTradeRate();

		double dblX0 = 1. - dblInstantanenousTradeRate0 * dblTimeWidth;

		ConditionalPriceDistribution cpd1 = new ConditionalPriceDistribution (
			adblAlpha[1],
			dblSigma,
			2.0 * dblTimeWidth
		);

		double dblPriceSwing1 = cpd1.priceVolatilitySwing();

		double dblRealizedPriceChange1 = adblAlpha[1] * dblTimeWidth + dblPriceSwing1;

		PriorConditionalCombiner pcc1 = new PriorConditionalCombiner (
			pdd,
			cpd1
		);

		LinearTemporaryImpact lti1 = LinearTemporaryImpact.Unconstrained (
			1.0 * dblTimeWidth,
			dblT,
			dblX0,
			pcc1,
			dblRealizedPriceChange1,
			prlTemporary
		);

		double dblInstantanenousTradeRate1 = lti1.instantaneousTradeRate();

		double dblX1 = 1. - dblInstantanenousTradeRate1 * dblTimeWidth;

		System.out.println (
			"\t|[" +
			FormatUtil.FormatDouble (dblAlphaBar, 1, 1, 1.) + "," +
			FormatUtil.FormatDouble (dblNu, 1, 1, 1.) + "," +
			FormatUtil.FormatDouble (dblSigma, 1, 1, 1.) + "] => " +
			FormatUtil.FormatDouble (dblX0, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblX1, 1, 3, 1.) + " || " +
			FormatUtil.FormatDouble (dblInstantanenousTradeRate0, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblInstantanenousTradeRate1, 1, 3, 1.) + " || " +
			FormatUtil.FormatDouble (lti0.driftExpectationEstimate(), 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (lti1.driftExpectationEstimate(), 1, 3, 1.) + " || " +
			FormatUtil.FormatDouble (lti0.driftVolatilityEstimate(), 3, 0, 100.) + "% | " +
			FormatUtil.FormatDouble (lti1.driftVolatilityEstimate(), 3, 0, 100.) + "% || "
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblT = 1.;
		double dblEta = 0.07;

		double[] adblNu = new double[] {
			0.5,
			1.0,
			2.0
		};

		double[] adblSigma = new double[] {
			0.5,
			1.7,
			2.9
		};

		double[] adblAlphaBar = new double[] {
			0.2,
			0.7,
			1.2
		};

		ParticipationRateLinear prlTemporary = ParticipationRateLinear.SlopeOnly (dblEta);

		System.out.println();

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                     BAYESIAN GAIN INPUT DRIFT DISTRIBUTION DEPENDENCE                     ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|        Inputs L -> R:                                                                     ||");

		System.out.println ("\t|             - Alpha Bar                                                                   ||");

		System.out.println ("\t|             - Sigma                                                                       ||");

		System.out.println ("\t|             - Nu                                                                          ||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|        Outputs L -> R:                                                                    ||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|             - Phase #1 Outstanding Holdings                                               ||");

		System.out.println ("\t|             - Phase #2 Outstanding Holdings                                               ||");

		System.out.println ("\t|             - Phase #1 Instantaneous Trade Rate                                           ||");

		System.out.println ("\t|             - Phase #2 Instantaneous Trade Rate                                           ||");

		System.out.println ("\t|             - Phase #1 Drift Expectation Estimate                                         ||");

		System.out.println ("\t|             - Phase #2 Drift Expectation Estimate                                         ||");

		System.out.println ("\t|             - Phase #1 Drift Volatility Estimate                                          ||");

		System.out.println ("\t|             - Phase #2 Drift Volatility Estimate                                          ||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		for (double dblAlphaBar : adblAlphaBar) {
			for (double dblNu : adblNu) {
				for (double dblSigma : adblSigma)
					RunScenario (
						dblAlphaBar,
						dblNu,
						dblSigma,
						dblT,
						prlTemporary
					);
			}
		}

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");
	}
}
