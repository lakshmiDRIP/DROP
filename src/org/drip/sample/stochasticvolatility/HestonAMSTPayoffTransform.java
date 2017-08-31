
package org.drip.sample.stochasticvolatility;

import org.drip.param.pricer.HestonOptionPricerParams;
import org.drip.pricer.option.Greeks;
import org.drip.pricer.option.HestonStochasticVolatilityAlgorithm;
import org.drip.quant.common.FormatUtil;
import org.drip.quant.fourier.PhaseAdjuster;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * HestonAMSTPayoffTransform contains an Comparison of the two ways of computing the Fourier convolution of
 *  the terminal payoff - the original Heston (1993) method, and the Albrecher, Mayer, Schoutens, and
 *  Tistaert tweak (2007).
 * 
 * @author Lakshmi Krishnamurthy
 */

public class HestonAMSTPayoffTransform {
	public static final double TestPayoffScheme (
		final double dblTimeToExpiry,
		final int iPayoffTransformScheme,
		final boolean bProb1)
		throws Exception
	{
		double dblRho = 0.3;
		double dblKappa = 1.;
		double dblSigma = 0.5;
		double dblTheta = 0.2;
		double dblLambda = 0.;

		HestonOptionPricerParams fphp = new HestonOptionPricerParams (
			iPayoffTransformScheme,
			dblRho,
			dblKappa,
			dblSigma,
			dblTheta,
			dblLambda,
			PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL
		);

		HestonStochasticVolatilityAlgorithm hsva = new HestonStochasticVolatilityAlgorithm (fphp);

		double dblStrike = 1.;
		double dblRiskFreeRate = 0.0;
		double dblSpot = 1.;
		double dblSpotVolatility = 0.1;

		Greeks greeks = hsva.greeks (
			dblStrike,
			dblTimeToExpiry,
			dblRiskFreeRate,
			dblSpot,
			false,
			false,
			dblSpotVolatility
		);

		return bProb1 ? greeks.prob1() : greeks.prob2();
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		double[] adblTTE = new double[] {
			0.5, 1., 2., 3., 4., 5., 7., 10., 12., 15., 20., 25., 30.
		};

		System.out.println ("\n\t|---------------------------|");

		System.out.println ("\t|     Prob 1 Comparison     |");

		System.out.println ("\t|---------------------------|");

		System.out.println ("\t|  TTE  =   HSTN  |   AMST  |");

		System.out.println ("\t|---------------------------|");

		for (double dblTTE : adblTTE) {
			System.out.println ("\t|" + FormatUtil.FormatDouble (dblTTE, 2, 2, 1.) + " = " +
				FormatUtil.FormatDouble (TestPayoffScheme (dblTTE,
					HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_HESTON_1993, true), 1, 4, 1.) + " | "  +
				FormatUtil.FormatDouble (TestPayoffScheme (dblTTE,
					HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_AMST_2007, true), 1, 4, 1.) + " |");
		}

		System.out.println ("\t|---------------------------|");

		System.out.println ("\n\t|---------------------------|");

		System.out.println ("\t|     Prob 2 Comparison     |");

		System.out.println ("\t|---------------------------|");

		System.out.println ("\t|  TTE  =   HSTN  |   AMST  |");

		System.out.println ("\t|---------------------------|");

		for (double dblTTE : adblTTE) {
			System.out.println ("\t|" + FormatUtil.FormatDouble (dblTTE, 2, 2, 1.) + " = " +
				FormatUtil.FormatDouble (TestPayoffScheme (dblTTE,
					HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_HESTON_1993, false), 1, 4, 1.) + " | "  +
				FormatUtil.FormatDouble (TestPayoffScheme (dblTTE,
					HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_AMST_2007, false), 1, 4, 1.) + " |");
		}

		System.out.println ("\t|---------------------------|");
	}
}
