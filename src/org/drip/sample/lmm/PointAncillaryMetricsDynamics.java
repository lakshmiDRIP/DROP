
package org.drip.sample.lmm;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.MarketSurface;
import org.drip.dynamics.lmm.*;
import org.drip.quant.common.FormatUtil;
import org.drip.sequence.random.*;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
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
 * PointAncillaryMetricsDynamics demonstrates the Construction and Usage of the Point LIBOR State Evolver,
 *  and the eventual Evolution of the related Ancillary bDiscount/Forward Latent State Quantification
 *   Metrics. The References are:
 * 
 *  1) Goldys, B., M. Musiela, and D. Sondermann (1994): Log-normality of Rates and Term Structure Models,
 *  	The University of New South Wales.
 * 
 *  2) Musiela, M. (1994): Nominal Annual Rates and Log-normal Volatility Structure, The University of New
 *   	South Wales.
 * 
 * 	3) Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics, Mathematical
 * 		Finance 7 (2), 127-155.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PointAncillaryMetricsDynamics {

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

	private static final LognormalLIBORVolatility LLVInstance (
		final int iSpotDate,
		final ForwardLabel forwardLabel,
		final MarketSurface[] aMS,
		final double[][] aadblCorrelation,
		final int iNumFactor)
		throws Exception
	{
		UnivariateSequenceGenerator[] aUSG = new UnivariateSequenceGenerator[aMS.length];

		for (int i = 0; i < aUSG.length; ++i)
			aUSG[i] = new BoxMullerGaussian (0., 1.);

		return new LognormalLIBORVolatility (
			iSpotDate,
			forwardLabel,
			aMS,
			new PrincipalFactorSequenceGenerator (
				aUSG,
				aadblCorrelation,
				iNumFactor
			)
		);
	}

	private static final void DisplayRunSnap (
		final BGMPointUpdate bgmRunSnap)
		throws Exception
	{
		System.out.println (
			"\t| [" + new JulianDate (bgmRunSnap.evolutionStartDate()) +
			" -> " + new JulianDate (bgmRunSnap.evolutionFinishDate()) +
			"]  => " + FormatUtil.FormatDouble (bgmRunSnap.libor(), 1, 2, 100.) +
			"% | " + FormatUtil.FormatDouble (bgmRunSnap.liborIncrement(), 2, 0, 10000.) +
			" | " + FormatUtil.FormatDouble (bgmRunSnap.instantaneousEffectiveForwardRate(), 1, 2, 100.) +
			"% | " + FormatUtil.FormatDouble (bgmRunSnap.instantaneousNominalForwardRate(), 1, 2, 100.) +
			"% | "
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strTenor = "3M";
		String strCurrency = "USD";
		double dblFlatVol1 = 0.35;
		double dblFlatVol2 = 0.42;
		double dblFlatVol3 = 0.27;
		double dblZeroRate = 0.02;
		double dblFlatForwardRate = 0.02;
		int iNumRun = 20;

		int[] aiNumFactor = {
			1, 2, 3
		};

		double[][] aadblCorrelation = new double[][] {
			{1.0, 0.1, 0.2},
			{0.1, 1.0, 0.2},
			{0.2, 0.1, 1.0}
		};

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		FundingLabel fundingLabel = FundingLabel.Standard (
			strCurrency
		);

		JulianDate dtSpot = org.drip.analytics.date.DateUtil.Today();

		MarketSurface[] aMS = new MarketSurface[] {
			FlatVolatilitySurface (
				dtSpot,
				strCurrency,
				dblFlatVol1
			),
			FlatVolatilitySurface (
				dtSpot,
				strCurrency,
				dblFlatVol2
			),
			FlatVolatilitySurface (
				dtSpot,
				strCurrency,
				dblFlatVol3
			)
		};

		ForwardCurve fc = ScenarioForwardCurveBuilder.FlatForwardForwardCurve (
			dtSpot,
			forwardLabel,
			dblFlatForwardRate
		);

		MergedDiscountForwardCurve dc = ScenarioDiscountCurveBuilder.ExponentiallyCompoundedFlatRate (
			dtSpot,
			strCurrency,
			dblZeroRate
		);

		int iSpotDate = dtSpot.julian();

		int iViewDate = dtSpot.addTenor ("1Y").julian();

		int iViewTimeIncrement = 1;

		for (int iNumFactor : aiNumFactor) {
			System.out.println ("\n\n\t|----------------------------------------------------------------------------------------------------------|");

			System.out.println ("\t|                                                                                                          |");

			System.out.println ("\t|                             LOG-NORMAL LIBOR EVOLVER                                                     |");

			System.out.println ("\t|                             ---------- ----- -------                                                     |");

			System.out.println ("\t|                                                                                                          |");

			System.out.println ("\t|       Num Factors: " + iNumFactor + "                                                                                     |");

			System.out.println ("\t|       Start Date                                                                                         |");

			System.out.println ("\t|       End Date                                                                                           |");

			System.out.println ("\t|       Adjacent Step LIBOR (%)                                                                            |");

			System.out.println ("\t|       Adjacent Step LIBOR Increment (bp)                                                                 |");

			System.out.println ("\t|       Adjacent Step Effective Annual Forward Rate (%)                                                    |");

			System.out.println ("\t|       Adjacent Step Nominal Annual Forward Rate (%)                                                      |");

			System.out.println ("\t|                                                                                                          |");

			System.out.println ("\t|----------------------------------------------------------------------------------------------------------|");

			LognormalLIBORPointEvolver lle = new LognormalLIBORPointEvolver (
				fundingLabel,
				forwardLabel,
				LLVInstance (
					dtSpot.julian(),
					forwardLabel,
					aMS,
					aadblCorrelation,
					iNumFactor
				),
				fc,
				dc
			);

			for (int iRun = 0; iRun < iNumRun; ++iRun)
				DisplayRunSnap (
					lle.evolve (
						iSpotDate,
						iViewDate,
						iViewTimeIncrement,
						null
					)
				);

			System.out.println ("\t|----------------------------------------------------------------------------------------------------------|");
		}
	}
}
