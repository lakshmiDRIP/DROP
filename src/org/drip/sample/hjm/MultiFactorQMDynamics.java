
package org.drip.sample.hjm;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.MarketSurface;
import org.drip.analytics.support.Helper;
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
 * MultiFactorQMDynamics demonstrates the Construction and Usage of the 3-Factor Gaussian Model Dynamics for
 *  the Evolution of the Discount Factor Quantification Metrics - the Instantaneous Forward Rate, the LIBOR
 *  Forward Rate, the Shifted LIBOR Forward Rate, the Short Rate, the Compounded Short Rate, and the Price.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultiFactorQMDynamics {

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
		final String strTenor,
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
				strTenor
			),
			mfv,
			auForwardRate
		);
	}

	private static final ShortForwardRateUpdate InitQMSnap (
		final JulianDate dtStart,
		final String strCurrency,
		final String strViewTenor,
		final String strTenor,
		final double dblInitialForwardRate,
		final double dblInitialPrice)
		throws Exception
	{
		return ShortForwardRateUpdate.Create (
			FundingLabel.Standard (strCurrency),
			ForwardLabel.Create (
				strCurrency,
				strTenor
			),
			dtStart.julian(),
			dtStart.julian(),
			dtStart.addTenor (strViewTenor).julian(),
			dblInitialForwardRate,
			0.,
			dblInitialForwardRate,
			0.,
			dblInitialForwardRate + (365.25 / Helper.TenorToDays (strTenor)),
			0.,
			dblInitialForwardRate,
			0.,
			dblInitialForwardRate,
			0.,
			dblInitialPrice,
			0.
		);
	}

	private static final void QMEvolution (
		final MultiFactorStateEvolver hjm,
		final JulianDate dtStart,
		final String strCurrency,
		final String strViewTenor,
		final ShortForwardRateUpdate qmInitial)
		throws Exception
	{
		int iViewDate = dtStart.addTenor (strViewTenor).julian();

		int iDayStep = 2;
		ShortForwardRateUpdate qm = qmInitial;
		JulianDate dtSpot = dtStart;

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                                                               ||");

		System.out.println ("\t|    3-Factor Gaussian HJM Quantification Metric Run                                                                            ||");

		System.out.println ("\t|    -----------------------------------------------                                                                            ||");

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

			qm = (ShortForwardRateUpdate) hjm.evolve (
				iSpotDate,
				iViewDate,
				iDayStep,
				qm
			);

			System.out.println ("\t| [" + dtSpot + "] = " +
				FormatUtil.FormatDouble (qm.instantaneousForwardRate(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (qm.instantaneousForwardRateIncrement(), 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (qm.liborForwardRate(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (qm.liborForwardRateIncrement(), 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (qm.shiftedLIBORForwardRate(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (qm.shiftedLIBORForwardRateIncrement(), 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (qm.shortRate(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (qm.shortRateIncrement(), 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (qm.compoundedShortRate(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (qm.compoundedShortRateIncrement(), 1, 2, 100.) + "% || " +
				FormatUtil.FormatDouble (qm.price(), 2, 2, 100.) + " | " +
				FormatUtil.FormatDouble (qm.priceIncrement(), 1, 2, 100.) + " || "
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
		double dblFlatVol1 = 0.007;
		double dblFlatVol2 = 0.009;
		double dblFlatVol3 = 0.004;
		double dblFlatForwardRate = 0.05;
		double dblInitialPrice = 0.9875;
		String strViewTenor = "3M";
		String strTenor = "6M";

		JulianDate dtSpot = DateUtil.Today();

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
			strTenor,
			mktSurfFlatVol1,
			mktSurfFlatVol2,
			mktSurfFlatVol3,
			new FlatUnivariate (dblFlatForwardRate)
		);

		ShortForwardRateUpdate qmInitial = InitQMSnap (
			dtSpot,
			strCurrency,
			strViewTenor,
			strTenor,
			dblFlatForwardRate,
			dblInitialPrice
		);

		QMEvolution (
			hjm,
			dtSpot,
			strCurrency,
			strViewTenor,
			qmInitial
		);
	}
}
