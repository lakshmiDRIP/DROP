
package org.drip.sample.burgard2012;

import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * CounterPartyHazardHigh estimates the CVA Relative to V for a Call Option bought by the Bank for different
 * 	Close Outs and Funding Spreads using the Burgard and Kjaer (2011) Methodology for the Case where the
 * 	Counter Party Hazard is High (5%). The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CounterPartyHazardHigh {

	private static final void CVA (
		final double dblT,
		final double dblRB,
		final double dblRC,
		final double dblLambdaB,
		final double dblLambdaC)
		throws Exception
	{
		double dblMTM_XVA___Funding_0    = -1. * (1. - Math.exp (-1. * (1. - dblRC) * dblLambdaC * dblT));

		double dblMTM_XVA___Funding_Bank = -1. * (1. - Math.exp (-1. * ((1. - dblRB) * dblLambdaB + (1. - dblRC) * dblLambdaC) * dblT));

		double dblMTM_Fair__Funding_0   = -1. * (1. - dblRC) * dblLambdaC *
			(1. - Math.exp (-1. * (dblLambdaB + dblLambdaC) * dblT)) /
			(dblLambdaB + dblLambdaC);

		double dblMTM_Fair__Funding_Bank = -1. * ((1. - dblRB) * dblLambdaB + (1. - dblRC) * dblLambdaC) *
			(1. - Math.exp (-1. * (dblLambdaB + dblLambdaC) * dblT)) /
			(dblLambdaB + dblLambdaC);

		System.out.println ("\t|| " +
			FormatUtil.FormatDouble (dblLambdaB   , 1, 1, 100.) + "% => " +
			FormatUtil.FormatDouble (dblMTM_XVA___Funding_0   , 2, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (dblMTM_XVA___Funding_Bank, 2, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (dblMTM_Fair__Funding_0   , 2, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (dblMTM_Fair__Funding_Bank, 2, 2, 100.) + "% ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblLambdaC = 0.05;

		double dblRB = 0.4;
		double dblRC = 0.4;
		double dblT = 5.;

		double[] adblLambdaB = new double[] {
			0.00001,
			0.005,
			0.01,
			0.015,
			0.02,
			0.025,
			0.03,
			0.035,
			0.04,
			0.045,
			0.05
		};

		System.out.println();

		System.out.println ("\t||------------------------------------------------||");

		System.out.println ("\t||       CVA UNDER HIGH COUNTER PARTY HAZARD      ||");

		System.out.println ("\t||------------------------------------------------||");

		System.out.println ("\t|| L -> R:                                        ||");

		System.out.println ("\t||        - Close Out      => MTM XVA             ||");

		System.out.println ("\t||        - Funding Spread => None                ||");

		System.out.println ("\t||        - Close Out      => MTM Fair Value      ||");

		System.out.println ("\t||        - Funding Spread => Bank                ||");

		System.out.println ("\t||------------------------------------------------||");

		for (double dblLambdaB : adblLambdaB)
			CVA (
				dblT,
				dblRB,
				dblRC,
				dblLambdaB,
				dblLambdaC
			);

		System.out.println ("\t||------------------------------------------------||");

		System.out.println();
	}
}
