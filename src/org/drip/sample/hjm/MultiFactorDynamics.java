
package org.drip.sample.hjm;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.MarketSurface;
import org.drip.dynamics.hjm.*;
import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.random.*;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.ScenarioMarketSurfaceBuilder;
import org.drip.state.identifier.*;

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
 * MultiFactorDynamics demonstrates the Construction and Usage of the Multi-Factor Gaussian Model Dynamics
 *  for the Evolution of the Instantaneous Forward Rate, the Price, and the Short Rate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultiFactorDynamics {

	private static final MarketSurface FlatVolatilitySurface (
		final JulianDate dtStart,
		final String strCurrency,
		final double dblFlatVol)
		throws Exception
	{
		return ScenarioMarketSurfaceBuilder.CustomSplineWireSurface (
			"VIEW_TARGET_VOLATILITY_SURFACE",
			dtStart,
			strCurrency,
			new double[] {
				dtStart.julian(),
				dtStart.addYears (2).julian(),
				dtStart.addYears (4).julian(),
				dtStart.addYears (6).julian(),
				dtStart.addYears (8).julian(),
				dtStart.addYears (10).julian()
			},
			new double[] {
				dtStart.julian(),
				dtStart.addYears (2).julian(),
				dtStart.addYears (4).julian(),
				dtStart.addYears (6).julian(),
				dtStart.addYears (8).julian(),
				dtStart.addYears (10).julian()
			},
			new double[][] {
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
				{dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol, dblFlatVol},
			},
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (
					2,
					2
				),
				null,
				null
			),
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (
					2,
					2
				),
				null,
				null
			)
		);
	}

	private static final MultiFactorStateEvolver HJMInstance (
		final JulianDate dtStart,
		final String strCurrency,
		final MarketSurface mktSurfFlatVol1,
		final MarketSurface mktSurfFlatVol2,
		final MarketSurface mktSurfFlatVol3,
		final R1ToR1 auForwardRate)
		throws Exception
	{
		MultiFactorVolatility mfv = new MultiFactorVolatility (
			new MarketSurface[] {
				mktSurfFlatVol1,
				mktSurfFlatVol2,
				mktSurfFlatVol3
			},
			new PrincipalFactorSequenceGenerator (
				new UnivariateSequenceGenerator[] {
					new BoxMullerGaussian (
						0.,
						1.
					),
					new BoxMullerGaussian (
						0.,
						1.
					),
					new BoxMullerGaussian (
						0.,
						1.
					)
				},
				new double[][] {
					{1.0, 0.1, 0.2},
					{0.1, 1.0, 0.2},
					{0.2, 0.1, 1.0}
				},
				3
			)
		);

		return new MultiFactorStateEvolver (
			FundingLabel.Standard (strCurrency),
			ForwardLabel.Create (
				strCurrency,
				"6M"
			),
			mfv,
			auForwardRate
		);
	}

	private static final void Evolve (
		final MultiFactorStateEvolver hjm,
		final JulianDate dtStart,
		final String strCurrency,
		final String strViewTenor,
		final String strTargetTenor,
		final double dblStartingForwardRate,
		final double dblStartingPrice)
		throws Exception
	{
		int iViewDate = dtStart.addTenor (strViewTenor).julian();

		int iTargetDate = dtStart.addTenor (strTargetTenor).julian();

		int iDayStep = 2;
		JulianDate dtSpot = dtStart;
		double dblPrice = dblStartingPrice;
		double dblShortRate = dblStartingForwardRate;
		double dblLIBORForwardRate = dblStartingForwardRate;
		double dblInstantaneousForwardRate = dblStartingForwardRate;
		double dblContinuouslyCompoundedShortRate = dblStartingForwardRate;
		double dblShiftedLIBORForwardRate = dblStartingForwardRate + (365.25 / (iTargetDate - iViewDate));

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                                                               ||");

		System.out.println ("\t|    Heath-Jarrow-Morton Gaussian Run                                                                                           ||");

		System.out.println ("\t|    --------------------------------                                                                                           ||");

		System.out.println ("\t|                                                                                                                               ||");

		System.out.println ("\t|        L->R:                                                                                                                  ||");

		System.out.println ("\t|            Date                                                                                                               ||");

		System.out.println ("\t|            Instantaneous Forward Rate (%)                                                                                     ||");

		System.out.println ("\t|            Instantaneous Forward Rate - Change (%)                                                                            ||");

		System.out.println ("\t|            LIBOR Forward Rate (%)                                                                                             ||");

		System.out.println ("\t|            LIBOR Forward Rate - Change (%)                                                                                    ||");

		System.out.println ("\t|            Shifted LIBOR Forward Rate (%)                                                                                     ||");

		System.out.println ("\t|            Shifted LIBOR Forward Rate - Change (%)                                                                            ||");

		System.out.println ("\t|            Short Rate (%)                                                                                                     ||");

		System.out.println ("\t|            Short Rate - Change (%)                                                                                            ||");

		System.out.println ("\t|            Continuously Compounded Short Rate (%)                                                                             ||");

		System.out.println ("\t|            Continuously Compounded Short Rate - Change (%)                                                                    ||");

		System.out.println ("\t|            Price                                                                                                              ||");

		System.out.println ("\t|            Price - Change                                                                                                     ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------||");

		while (dtSpot.julian() < iViewDate) {
			int iSpotDate = dtSpot.julian();

			double dblIFRIncrement = hjm.instantaneousForwardRateIncrement (
				iViewDate,
				iTargetDate,
				iDayStep
			);

			dblInstantaneousForwardRate += dblIFRIncrement;

			double dblLIBORForwardRateIncrement = hjm.liborForwardRateIncrement (
				iSpotDate,
				iViewDate,
				iTargetDate,
				dblLIBORForwardRate,
				iDayStep
			);

			dblLIBORForwardRate += dblLIBORForwardRateIncrement;

			double dblShiftedLIBORForwardRateIncrement = hjm.shiftedLIBORForwardIncrement (
				iSpotDate,
				iViewDate,
				iTargetDate,
				dblShiftedLIBORForwardRate,
				iDayStep
			);

			dblShiftedLIBORForwardRate += dblShiftedLIBORForwardRateIncrement;

			double dblShortRateIncrement = hjm.shortRateIncrement (
				iSpotDate,
				iViewDate,
				iDayStep
			);

			dblShortRate += dblShortRateIncrement;

			double dblProportionalPriceIncrement = hjm.proportionalPriceIncrement (
				iViewDate,
				iTargetDate,
				dblShortRate,
				iDayStep
			);

			dblPrice *= (1. + dblProportionalPriceIncrement);

			double dblContinuouslyCompoundedShortRateIncrement = hjm.compoundedShortRateIncrement (
				iSpotDate,
				iViewDate,
				iTargetDate,
				dblContinuouslyCompoundedShortRate,
				dblShortRate,
				iDayStep
			);

			dblContinuouslyCompoundedShortRate += dblContinuouslyCompoundedShortRateIncrement;

			System.out.println ("\t| [" + dtSpot + "] = " +
				FormatUtil.FormatDouble (dblInstantaneousForwardRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblIFRIncrement, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblLIBORForwardRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblLIBORForwardRateIncrement, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblShiftedLIBORForwardRate, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblShiftedLIBORForwardRateIncrement, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblShortRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblShortRateIncrement, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblContinuouslyCompoundedShortRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblContinuouslyCompoundedShortRateIncrement, 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (dblPrice, 2, 2, 100.) + " | " +
				FormatUtil.FormatDouble (dblProportionalPriceIncrement, 1, 2, 100.) + " || "
			);

			dtSpot = dtSpot.addBusDays (
				iDayStep,
				strCurrency
			);
		}

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "USD";

		JulianDate dtSpot = DateUtil.Today();

		double dblFlatVol1 = 0.01;
		double dblFlatVol2 = 0.02;
		double dblFlatVol3 = 0.03;
		double dblFlatForwardRate = 0.05;
		double dblStartingPrice = 0.9875;

		MarketSurface mktSurfFlatVol1 = FlatVolatilitySurface (
			dtSpot,
			strCurrency,
			dblFlatVol1
		);

		MarketSurface mktSurfFlatVol2 = FlatVolatilitySurface (
			dtSpot,
			strCurrency,
			dblFlatVol2
		);

		MarketSurface mktSurfFlatVol3 = FlatVolatilitySurface (
			dtSpot,
			strCurrency,
			dblFlatVol3
		);

		MultiFactorStateEvolver hjm = HJMInstance (
			dtSpot,
			strCurrency,
			mktSurfFlatVol1,
			mktSurfFlatVol2,
			mktSurfFlatVol3,
			new FlatUnivariate (dblFlatForwardRate)
		);

		Evolve (
			hjm,
			dtSpot,
			strCurrency,
			"3M",
			"6M",
			dblFlatForwardRate,
			dblStartingPrice
		);
	}
}
