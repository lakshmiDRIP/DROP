
package org.drip.sample.sabr;

import org.drip.analytics.date.*;
import org.drip.dynamics.sabr.*;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.random.BoxMullerGaussian;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * ForwardRateEvolution demonstrates the Construction and Usage of the SABR Model Dynamics for the Evolution
 *  of Forward Rate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ForwardRateEvolution {

	private static StochasticVolatilityStateEvolver SABREvolver (
		final double dblBeta,
		final double dblRho,
		final double dblVolatilityOfVolatility)
		throws Exception
	{
		return new StochasticVolatilityStateEvolver (
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			dblBeta,
			dblRho,
			dblVolatilityOfVolatility,
			new BoxMullerGaussian (
				0.,
				1.
			),
			new BoxMullerGaussian (
				0.,
				1.
			)
		);
	}

	private static void SABREvolution (
		final StochasticVolatilityStateEvolver seSABR1,
		final StochasticVolatilityStateEvolver seSABR2,
		final StochasticVolatilityStateEvolver seSABR3,
		final int iSpotDate,
		final int iTerminalDate,
		final ForwardRateUpdate lsqmInitial1,
		final ForwardRateUpdate lsqmInitial2,
		final ForwardRateUpdate lsqmInitial3)
		throws Exception
	{
		int iDayStep = 2;
		int iDate = iSpotDate;
		ForwardRateUpdate lsqm1 = lsqmInitial1;
		ForwardRateUpdate lsqm2 = lsqmInitial2;
		ForwardRateUpdate lsqm3 = lsqmInitial3;

		System.out.println ("\n\t||---------------------------------------------------------------------------------||");

		System.out.println ("\t||     SABR  EVOLUTION  DYNAMICS                                                   ||");

		System.out.println ("\t||---------------------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                                      ||");

		System.out.println ("\t||        Forward Rate (%)  - Gaussian (beta = 0.0)                                ||");

		System.out.println ("\t||        Forward Rate Vol (%)  - Gaussian (beta = 0.0)                            ||");

		System.out.println ("\t||        Forward Rate (%)  - beta = 0.5                                           ||");

		System.out.println ("\t||        Forward Rate Vol (%)  - beta = 0.5                                       ||");

		System.out.println ("\t||        Forward Rate (%)  - Lognormal (beta = 1.0)                               ||");

		System.out.println ("\t||        Forward Rate Vol (%)  - Lognormal (beta = 1.0)                           ||");

		System.out.println ("\t||---------------------------------------------------------------------------------||");

		while (iDate < iTerminalDate) {
			lsqm1 = (ForwardRateUpdate) seSABR1.evolve (
				iSpotDate,
				iDate,
				iDayStep,
				lsqm1
			);

			lsqm2 = (ForwardRateUpdate) seSABR2.evolve (
				iSpotDate,
				iDate,
				iDayStep,
				lsqm2
			);

			lsqm3 = (ForwardRateUpdate) seSABR3.evolve (
				iSpotDate,
				iDate,
				iDayStep,
				lsqm3
			);

			System.out.println (
				"\t|| " + new JulianDate (iDate) + " => " +
				FormatUtil.FormatDouble (lsqm1.forwardRate(), 1, 4, 100.) + " % | " +
				FormatUtil.FormatDouble (lsqm1.forwardRateVolatility(), 1, 2, 100.) + " % || " +
				FormatUtil.FormatDouble (lsqm2.forwardRate(), 1, 4, 100.) + " % | " +
				FormatUtil.FormatDouble (lsqm2.forwardRateVolatility(), 1, 1, 100.) + " % || " +
				FormatUtil.FormatDouble (lsqm3.forwardRate(), 1, 4, 100.) + " % | " +
				FormatUtil.FormatDouble (lsqm3.forwardRateVolatility(), 1, 1, 100.) + " % ||"
			);

			iDate += iDayStep;
		}

		System.out.println ("\t||---------------------------------------------------------------------------------||");
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		double dblRho = 0.1;
		double dblForwardRate = 0.04;
		double dblVolatilityOfVolatility = 0.59;
		String strViewTenor = "3M";
		double[] adblBeta = {
			0.00, 0.50, 1.00
		};
		double[] adblForwardRateVolatility = {
			0.03, 0.26, 0.51
		};

		int iViewDate = dtSpot.addTenor (strViewTenor).julian();

		StochasticVolatilityStateEvolver seSABR1 = SABREvolver (
			adblBeta[0],
			dblRho,
			dblVolatilityOfVolatility
		);

		StochasticVolatilityStateEvolver seSABR2 = SABREvolver (
			adblBeta[1],
			dblRho,
			dblVolatilityOfVolatility
		);

		StochasticVolatilityStateEvolver seSABR3 = SABREvolver (
			adblBeta[2],
			dblRho,
			dblVolatilityOfVolatility
		);

		ForwardRateUpdate lsqmInitial1 = ForwardRateUpdate.Create (
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			dtSpot.julian(),
			dtSpot.julian(),
			iViewDate,
			dblForwardRate,
			0.,
			adblForwardRateVolatility[0],
			0.
		);

		ForwardRateUpdate lsqmInitial2 = ForwardRateUpdate.Create (
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			dtSpot.julian(),
			dtSpot.julian(),
			iViewDate,
			dblForwardRate,
			0.,
			adblForwardRateVolatility[1],
			0.
		);

		ForwardRateUpdate lsqmInitial3 = ForwardRateUpdate.Create (
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			dtSpot.julian(),
			dtSpot.julian(),
			iViewDate,
			dblForwardRate,
			0.,
			adblForwardRateVolatility[2],
			0.
		);

		SABREvolution (
			seSABR1,
			seSABR2,
			seSABR3,
			dtSpot.julian(),
			iViewDate,
			lsqmInitial1,
			lsqmInitial2,
			lsqmInitial3
		);
	}
}
