
package org.drip.sample.hjm;

import org.drip.analytics.date.*;
import org.drip.dynamics.hjm.G2PlusPlus;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.random.*;
import org.drip.service.env.EnvManager;

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
 * G2PlusPlusDynamics demonstrates the Construction and Usage of the G2++ 2-Factor HJM Model Dynamics for the
 *  Evolution of the Short Rate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class G2PlusPlusDynamics {

	private static final G2PlusPlus G2PlusPlusEvolver (
		final double dblSigma,
		final double dblA,
		final double dblEta,
		final double dblB,
		final double dblRho,
		final double dblStartingForwardRate)
		throws Exception
	{
		return new G2PlusPlus (
			dblSigma,
			dblA,
			dblEta,
			dblB,
			new UnivariateSequenceGenerator[] {
				new BoxMullerGaussian (
					0.,
					1.
				),
				new BoxMullerGaussian (
					0.,
					1.
				)
			},
			dblRho,
			new FlatUnivariate (dblStartingForwardRate)
		);
	}

	private static final void ShortRateEvolution (
		final G2PlusPlus g2pp,
		final JulianDate dtStart,
		final String strCurrency,
		final String strViewTenor,
		final double dblStartingShortRate)
		throws Exception
	{
		int iDayStep = 2;
		double dblX = 0.;
		double dblY = 0.;
		JulianDate dtSpot = dtStart;
		double dblShortRate = dblStartingShortRate;

		int iStartDate = dtStart.julian();

		int iEndDate = dtStart.addTenor (strViewTenor).julian();

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println ("\t|                                                                       ||");

		System.out.println ("\t|         G2++ - 2-factor HJM Model - Short Rate Evolution Run          ||");

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println ("\t|                                                                       ||");

		System.out.println ("\t|    L->R:                                                              ||");

		System.out.println ("\t|        Date                                                           ||");

		System.out.println ("\t|        X (%)                                                          ||");

		System.out.println ("\t|        X - Increment (%)                                              ||");

		System.out.println ("\t|        Y (%)                                                          ||");

		System.out.println ("\t|        Y - Increment (%)                                              ||");

		System.out.println ("\t|        Phi (%)                                                        ||");

		System.out.println ("\t|        Short Rate (%)                                                 ||");

		System.out.println ("\t|-----------------------------------------------------------------------||");

		while (dtSpot.julian() < iEndDate) {
			int iSpotDate = dtSpot.julian();

			double dblDeltaX = g2pp.deltaX (
				iStartDate,
				iSpotDate,
				dblX,
				iDayStep
			);

			dblX += dblDeltaX;

			double dblDeltaY = g2pp.deltaY (
				iStartDate,
				iSpotDate,
				dblY,
				iDayStep
			);

			dblY += dblDeltaY;

			double dblPhi = g2pp.phi (
				iStartDate,
				iSpotDate
			);

			dblShortRate = dblX + dblY + dblPhi;

			System.out.println ("\t| [" + dtSpot + "] = " +
				FormatUtil.FormatDouble (dblX, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblDeltaX, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblY, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblDeltaY, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblPhi, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblShortRate, 1, 2, 100.) + "% || "
			);

			dtSpot = dtSpot.addBusDays (
				iDayStep,
				strCurrency
			);
		}

		System.out.println ("\t|-----------------------------------------------------------------------||");
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
		double dblA = 0.5;
		double dblEta = 0.05;
		double dblB = 0.5;
		double dblRho = 0.5;

		G2PlusPlus g2pp = G2PlusPlusEvolver (
			dblSigma,
			dblA,
			dblEta,
			dblB,
			dblRho,
			dblStartingShortRate
		);

		ShortRateEvolution (
			g2pp,
			dtSpot,
			strCurrency,
			"4M",
			dblStartingShortRate
		);
	}
}
