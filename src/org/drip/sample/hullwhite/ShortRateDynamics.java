
package org.drip.sample.hullwhite;

import org.drip.analytics.date.*;
import org.drip.dynamics.hullwhite.SingleFactorStateEvolver;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.random.BoxMullerGaussian;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.FundingLabel;

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
 * ShortRateDynamics demonstrates the Construction and Usage of the Hull-White 1F Model Dynamics for the
 *  Evolution of the Short Rate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ShortRateDynamics {

	private static final SingleFactorStateEvolver HullWhiteEvolver (
		final String strCurrency,
		final double dblSigma,
		final double dblA,
		final double dblStartingForwardRate)
		throws Exception
	{
		return new SingleFactorStateEvolver (
			FundingLabel.Standard (strCurrency),
			dblSigma,
			dblA,
			new FlatUnivariate (dblStartingForwardRate),
			new BoxMullerGaussian (
				0.,
				1.
			)
		);
	}

	private static final void ShortRateEvolution (
		final SingleFactorStateEvolver hw,
		final JulianDate dtSpot,
		final String strCurrency,
		final String strViewTenor,
		final double dblStartingShortRate)
		throws Exception
	{
		int iDayStep = 2;
		JulianDate dtView = dtSpot;
		double dblShortRate = dblStartingShortRate;

		int iSpotDate = dtSpot.julian();

		int iEndDate = dtSpot.addTenor (strViewTenor).julian();

		System.out.println ("\n\n\t|------------------------------------------------------||");

		System.out.println ("\t|                                                      ||");

		System.out.println ("\t|    Hull-White Evolution Run                          ||");

		System.out.println ("\t|    ------------------------                          ||");

		System.out.println ("\t|                                                      ||");

		System.out.println ("\t|    L->R:                                             ||");

		System.out.println ("\t|        Date                                          ||");

		System.out.println ("\t|        Short Rate (%)                                ||");

		System.out.println ("\t|        Short Rate - Change (%)                       ||");

		System.out.println ("\t|        Alpha (%)                                     ||");

		System.out.println ("\t|        Theta (%)                                     ||");

		System.out.println ("\t|------------------------------------------------------||");

		while (dtView.julian() < iEndDate) {
			int iViewDate = dtView.julian();

			double dblAlpha = hw.alpha (
				iSpotDate,
				iViewDate
			);

			double dblTheta = hw.theta (
				iSpotDate,
				iViewDate
			);

			double dblShortRateIncrement = hw.shortRateIncrement (
				iSpotDate,
				iViewDate,
				dblShortRate,
				iDayStep
			);

			dblShortRate += dblShortRateIncrement;

			System.out.println ("\t| [" + dtView + "] = " +
				FormatUtil.FormatDouble (dblShortRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblShortRateIncrement, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblAlpha, 1, 4, 100.) + "% | " +
				FormatUtil.FormatDouble (dblTheta, 1, 4, 100.) + "% || "
			);

			dtView = dtView.addBusDays (
				iDayStep,
				strCurrency
			);
		}

		System.out.println ("\t|------------------------------------------------------||");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		String strCurrency = "USD";
		double dblStartingShortRate = 0.05;
		double dblSigma = 0.05;
		double dblA = 1.;

		SingleFactorStateEvolver hw = HullWhiteEvolver (
			strCurrency,
			dblSigma,
			dblA,
			dblStartingShortRate
		);

		ShortRateEvolution (
			hw,
			dtSpot,
			strCurrency,
			"4M",
			dblStartingShortRate
		);
	}
}
