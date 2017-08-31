
package org.drip.sample.multicurve;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.definition.*;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.*;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * FloatFloatForwardCurve contains the sample demonstrating the full functionality behind creating highly
 * 	customized spline based forward curves.
 * 
 * The first sample illustrates the creation and usage of the xM-6M Tenor Basis Swap:
 * 	- Construct the 6M-xM float-float basis swap.
 * 	- Calculate the corresponding starting forward rate off of the discount curve.
 * 	- Construct the shape preserving forward curve off of Cubic Polynomial Basis Spline.
 * 	- Construct the shape preserving forward curve off of Quartic Polynomial Basis Spline.
 * 	- Construct the shape preserving forward curve off of Hyperbolic Tension Based Basis Spline.
 * 	- Set the discount curve based component market parameters.
 * 	- Set the discount curve + cubic polynomial forward curve based component market parameters.
 * 	- Set the discount curve + quartic polynomial forward curve based component market parameters.
 * 	- Set the discount curve + hyperbolic tension forward curve based component market parameters.
 * 	- Compute the following forward curve metrics for each of cubic polynomial forward, quartic
 * 		polynomial forward, and KLK Hyperbolic tension forward curves:
 * 		- Reference Basis Par Spread
 * 		- Derived Basis Par Spread
 * 	- Compare these with a) the forward rate off of the discount curve, b) The LIBOR rate, and c) The
 * 		Input Basis Swap Quote.
 * 
 * The second sample illustrates how to build and test the forward curves across various tenor basis. It
 * 	shows the following steps:
 * 	- Construct the Discount Curve using its instruments and quotes.
 * 	- Build and run the sampling for the 1M-6M Tenor Basis Swap from its instruments and quotes.
 * 	- Build and run the sampling for the 3M-6M Tenor Basis Swap from its instruments and quotes.
 * 	- Build and run the sampling for the 6M-6M Tenor Basis Swap from its instruments and quotes.
 * 	- Build and run the sampling for the 12M-6M Tenor Basis Swap from its instruments and quotes.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FloatFloatForwardCurve {

	private static final FixFloatComponent OTCFixFloat (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strMaturityTenor,
		final double dblCoupon)
	{
		FixedFloatSwapConvention ffConv = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"ALL",
			strMaturityTenor,
			"MAIN"
		);

		return ffConv.createFixFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblCoupon,
			0.,
			1.
		);
	}

	private static final FloatFloatComponent OTCFloatFloat (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strDerivedTenor,
		final String strMaturityTenor,
		final double dblBasis)
	{
		FloatFloatSwapConvention ffConv = IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		return ffConv.createFloatFloatComponent (
			dtSpot,
			strDerivedTenor,
			strMaturityTenor,
			dblBasis,
			1.
		);
	}

	/*
	 * Construct the Array of Deposit Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] DepositInstrumentsFromMaturityDays (
		final JulianDate dtEffective,
		final int[] aiDay,
		final int iNumFutures,
		final String strCurrency)
		throws Exception
	{
		CalibratableComponent[] aCalibComp = new CalibratableComponent[aiDay.length + iNumFutures];

		for (int i = 0; i < aiDay.length; ++i)
			aCalibComp[i] = SingleStreamComponentBuilder.Deposit (
				dtEffective,
				dtEffective.addBusDays (
					aiDay[i],
					strCurrency
				),
				ForwardLabel.Create (
					strCurrency,
					"3M"
				)
			);

		CalibratableComponent[] aEDF = SingleStreamComponentBuilder.ForwardRateFuturesPack (
			dtEffective,
			iNumFutures,
			strCurrency
		);

		for (int i = aiDay.length; i < aiDay.length + iNumFutures; ++i)
			aCalibComp[i] = aEDF[i - aiDay.length];

		return aCalibComp;
	}

	/*
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final double[] adblCoupon)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aIRS[i] = OTCFixFloat (
				dtSpot,
				strCurrency,
				astrMaturityTenor[i],
				adblCoupon[i]
			);

		return aIRS;
	}

	/*
	 * Construct the discount curve using the following steps:
	 * 	- Construct the array of cash instruments and their quotes.
	 * 	- Construct the array of swap instruments and their quotes.
	 * 	- Construct a shape preserving and smoothing KLK Hyperbolic Spline from the cash/swap instruments.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final MergedDiscountForwardCurve MakeDC (
		final JulianDate dtSpot,
		final String strCurrency,
		final double dblBump)
		throws Exception
	{
		/*
		 * Construct the array of Deposit instruments and their quotes.
		 */

		CalibratableComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			new int[] {},
			0,
			strCurrency
		);

		double[] adblDepositQuote = new double[] {}; // Futures

		/*
		 * Construct the array of Swap instruments and their quotes.
		 */

		double[] adblSwapQuote = new double[] {
			// 0.00092 + dblBump,     //  6M
			0.0009875 + dblBump,   //  9M
			0.00122 + dblBump,     //  1Y
			0.00223 + dblBump,     // 18M
			0.00383 + dblBump,     //  2Y
			0.00827 + dblBump,     //  3Y
			0.01245 + dblBump,     //  4Y
			0.01605 + dblBump,     //  5Y
			0.02597 + dblBump      // 10Y
		};

		String[] astrSwapManifestMeasure = new String[] {
			// "SwapRate",     //  6M
			"SwapRate",		//  9M
			"SwapRate",     //  1Y
			"SwapRate",     // 18M
			"SwapRate",     //  2Y
			"SwapRate",     //  3Y
			"SwapRate",     //  4Y
			"SwapRate",     //  5Y
			"SwapRate"      // 10Y
		};

		CalibratableComponent[] aSwapComp = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			// new java.lang.String[] {"6M", "9M", "1Y", "18M", "2Y", "3Y", "4Y", "5Y", "10Y"},
			new java.lang.String[] {
				"9M", "1Y", "18M", "2Y", "3Y", "4Y", "5Y", "10Y"
			},
			adblSwapQuote
		);

		/*
		 * Construct a shape preserving and smoothing KLK Hyperbolic Spline from the cash/swap instruments.
		 */

		return ScenarioDiscountCurveBuilder.CubicKLKHyperbolicDFRateShapePreserver (
			"KLK_HYPERBOLIC_SHAPE_TEMPLATE",
			new ValuationParams (
				dtSpot,
				dtSpot,
				"USD"
			),
			aDepositComp,
			adblDepositQuote,
			null,
			aSwapComp,
			adblSwapQuote,
			astrSwapManifestMeasure,
			false
		);
	}

	/*
	 * Construct an array of float-float swaps from the corresponding reference (6M) and the derived legs.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FloatFloatComponent[] MakexM6MBasisSwap (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final int iTenorInMonths)
		throws Exception
	{
		FloatFloatComponent[] aFFC = new FloatFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aFFC[i] = OTCFloatFloat (
				dtSpot,
				strCurrency,
				iTenorInMonths + "M",
				astrMaturityTenor[i],
				0.
			);

		return aFFC;
	}

	/*
	 * This sample illustrates the creation and usage of the xM-6M Tenor Basis Swap. It shows the following:
	 * 	- Construct the 6M-xM float-float basis swap.
	 * 	- Calculate the corresponding starting forward rate off of the discount curve.
	 * 	- Construct the shape preserving forward curve off of Cubic Polynomial Basis Spline.
	 * 	- Construct the shape preserving forward curve off of Quartic Polynomial Basis Spline.
	 * 	- Construct the shape preserving forward curve off of Hyperbolic Tension Based Basis Spline.
	 * 	- Set the discount curve based component market parameters.
	 * 	- Set the discount curve + cubic polynomial forward curve based component market parameters.
	 * 	- Set the discount curve + quartic polynomial forward curve based component market parameters.
	 * 	- Set the discount curve + hyperbolic tension forward curve based component market parameters.
	 * 	- Compute the following forward curve metrics for each of cubic polynomial forward, quartic
	 * 		polynomial forward, and KLK Hyperbolic tension forward curves:
	 * 		- Reference Basis Par Spread
	 * 		- Derived Basis Par Spread
	 * 	- Compare these with a) the forward rate off of the discount curve, b) The LIBOR rate, and c) The
	 * 		Input Basis Swap Quote.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final Map<String, ForwardCurve> xM6MBasisSample (
		final JulianDate dtSpot,
		final String strCurrency,
		final MergedDiscountForwardCurve dc,
		final int iTenorInMonths,
		final String[] astrxM6MFwdTenor,
		final String strManifestMeasure,
		final double[] adblxM6MBasisSwapQuote)
		throws Exception
	{
		System.out.println ("-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println (" SPL =>              n=3              |              n=4               |              KLK               |         |         |");

		System.out.println ("--------------------------------------------------------------------------------------------------------|  LOG DF |  LIBOR  |");

		System.out.println (" MSR =>  RECALC |  REFEREN |  DERIVED |  RECALC  |  REFEREN |  DERIVED |  RECALC  |  REFEREN |  DERIVED |         |         |");

		System.out.println ("-----------------------------------------------------------------------------------------------------------------------------");

		/*
		 * Construct the 6M-xM float-float basis swap.
		 */

		FloatFloatComponent[] aFFC = MakexM6MBasisSwap (
			dtSpot,
			strCurrency,
			astrxM6MFwdTenor,
			iTenorInMonths
		);

		String strBasisTenor = iTenorInMonths + "M";

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		/*
		 * Calculate the starting forward rate off of the discount curve.
		 */

		double dblStartingFwd = dc.forward (
			dtSpot.julian(),
			dtSpot.addTenor (strBasisTenor).julian()
		);

		/*
		 * Set the discount curve based component market parameters.
		 */

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			null
		);

		Map<String, ForwardCurve> mapForward = new HashMap<String, ForwardCurve>();

		/*
		 * Construct the shape preserving forward curve off of Cubic Polynomial Basis Spline.
		 */

		ForwardCurve fcxMCubic = ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (
			"CUBIC_FWD" + strBasisTenor,
			ForwardLabel.Create (
				strCurrency,
				strBasisTenor
			),
			valParams,
			null,
			mktParams,
			null,
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (4),
			aFFC,
			strManifestMeasure,
			adblxM6MBasisSwapQuote,
			dblStartingFwd
		);

		mapForward.put (
			"   CUBIC_FWD" + strBasisTenor,
			fcxMCubic
		);

		/*
		 * Set the discount curve + cubic polynomial forward curve based component market parameters.
		 */

		CurveSurfaceQuoteContainer mktParamsCubicFwd = MarketParamsBuilder.Create (
			dc,
			fcxMCubic,
			null,
			null,
			null,
			null,
			null,
			null
		);

		/*
		 * Construct the shape preserving forward curve off of Quartic Polynomial Basis Spline.
		 */

		ForwardCurve fcxMQuartic = ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (
			"QUARTIC_FWD" + strBasisTenor,
			ForwardLabel.Create (
				strCurrency,
				strBasisTenor
			),
			valParams,
			null,
			mktParams,
			null,
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (5),
			aFFC,
			strManifestMeasure,
			adblxM6MBasisSwapQuote,
			dblStartingFwd
		);

		mapForward.put (
			" QUARTIC_FWD" + strBasisTenor,
			fcxMQuartic
		);

		/*
		 * Set the discount curve + quartic polynomial forward curve based component market parameters.
		 */

		CurveSurfaceQuoteContainer mktParamsQuarticFwd = MarketParamsBuilder.Create (
			dc,
			fcxMQuartic,
			null,
			null,
			null,
			null,
			null,
			null
		);

		/*
		 * Construct the shape preserving forward curve off of Hyperbolic Tension Based Basis Spline.
		 */

		ForwardCurve fcxMKLKHyper = ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (
			"KLKHYPER_FWD" + strBasisTenor,
			ForwardLabel.Create (
				strCurrency,
				strBasisTenor
			),
			valParams,
			null,
			mktParams,
			null,
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
			new ExponentialTensionSetParams (1.),
			aFFC,
			strManifestMeasure,
			adblxM6MBasisSwapQuote,
			dblStartingFwd
		);

		mapForward.put (
			"KLKHYPER_FWD" + strBasisTenor,
			fcxMKLKHyper
		);

		/*
		 * Set the discount curve + hyperbolic tension forward curve based component market parameters.
		 */

		CurveSurfaceQuoteContainer mktParamsKLKHyperFwd = MarketParamsBuilder.Create (
			dc,
			fcxMKLKHyper,
			null,
			null,
			null,
			null,
			null,
			null
		);

		int i = 0;
		int iFreq = 12 / iTenorInMonths;

		/*
		 * Compute the following forward curve metrics for each of cubic polynomial forward, quartic
		 * 	polynomial forward, and KLK Hyperbolic tension forward curves:
		 * 	- Reference Basis Par Spread
		 * 	- Derived Basis Par Spread
		 * 
		 * Further compare these with a) the forward rate off of the discount curve, b) the LIBOR rate, and
		 * 	c) Input Basis Swap Quote.
		 */

		for (String strMaturityTenor : astrxM6MFwdTenor) {
			int iFwdEndDate = dtSpot.addTenor (strMaturityTenor).julian();

			int iFwdStartDate = dtSpot.addTenor (strMaturityTenor).subtractTenor (strBasisTenor).julian();

			FloatFloatComponent ffc = aFFC[i++];

			CaseInsensitiveTreeMap<Double> mapCubicValue = ffc.value (
				valParams,
				null,
				mktParamsCubicFwd,
				null
			);

			CaseInsensitiveTreeMap<Double> mapQuarticValue = ffc.value (
				valParams,
				null,
				mktParamsQuarticFwd,
				null
			);

			CaseInsensitiveTreeMap<Double> mapKLKHyperValue = ffc.value (
				valParams,
				null,
				mktParamsKLKHyperFwd,
				null
			);

			System.out.println (" " + strMaturityTenor + " =>  " +
				FormatUtil.FormatDouble (fcxMCubic.forward (iFwdEndDate), 2, 2, 100.) + "  |  " +
				FormatUtil.FormatDouble (mapCubicValue.get ("ReferenceParBasisSpread"), 2, 2, 1.) + "  |  " +
				FormatUtil.FormatDouble (mapCubicValue.get ("DerivedParBasisSpread"), 2, 2, 1.) + "  |  " +
				FormatUtil.FormatDouble (fcxMQuartic.forward (iFwdEndDate), 2, 2, 100.) + "  |  " +
				FormatUtil.FormatDouble (mapQuarticValue.get ("ReferenceParBasisSpread"), 2, 2, 1.) + "  |  " +
				FormatUtil.FormatDouble (mapQuarticValue.get ("DerivedParBasisSpread"), 2, 2, 1.) + "  |  " +
				FormatUtil.FormatDouble (fcxMKLKHyper.forward (iFwdEndDate), 2, 2, 100.) + "  |  " +
				FormatUtil.FormatDouble (mapKLKHyperValue.get ("ReferenceParBasisSpread"), 2, 2, 1.) + "  |  " +
				FormatUtil.FormatDouble (mapKLKHyperValue.get ("DerivedParBasisSpread"), 2, 2, 1.) + "  |  " +
				FormatUtil.FormatDouble (iFreq * java.lang.Math.log (dc.df (iFwdStartDate) / dc.df (iFwdEndDate)), 1, 2, 100.) + "  |  " +
				FormatUtil.FormatDouble (dc.libor (iFwdStartDate, iFwdEndDate), 1, 2, 100.) + "  |  "
			);
		}

		return mapForward;
	}

	/*
	 * This sample illustrates how to build and test the forward curves across various tenor basis. It shows
	 * 	the following steps:
	 * 	- Construct the Discount Curve using its instruments and quotes.
	 * 	- Build and run the sampling for the 1M-6M Tenor Basis Swap from its instruments and quotes.
	 * 	- Build and run the sampling for the 3M-6M Tenor Basis Swap from its instruments and quotes.
	 * 	- Build and run the sampling for the 12M-6M Tenor Basis Swap from its instruments and quotes.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void CustomForwardCurveBuilderSample (
		final String strManifestMeasure)
		throws Exception
	{
		String strCurrency = "USD";

		JulianDate dtToday = DateUtil.Today().addTenor ("0D");

		/*
		 * Construct the Discount Curve using its instruments and quotes
		 */

		MergedDiscountForwardCurve dc = MakeDC (
			dtToday,
			strCurrency,
			0.
		);

		System.out.println ("\n-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("---------------------------------------------------    1M-6M Basis Swap    --------------------------------------------------");

		/*
		 * Build and run the sampling for the 1M-6M Tenor Basis Swap from its instruments and quotes.
		 */

		xM6MBasisSample (
			dtToday,
			strCurrency,
			dc,
			1,
			new String[] {
				"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y"
			},
			strManifestMeasure,
			new double[] {
				0.00551,    //  1Y
				0.00387,    //  2Y
				0.00298,    //  3Y
				0.00247,    //  4Y
				0.00211,    //  5Y
				0.00185,    //  6Y
				0.00165,    //  7Y
				0.00150,    //  8Y
				0.00137,    //  9Y
				0.00127,    // 10Y
				0.00119,    // 11Y
				0.00112,    // 12Y
				0.00096,    // 15Y
				0.00079,    // 20Y
				0.00069,    // 25Y
				0.00062     // 30Y
				}
			);

		/*
		 * Build and run the sampling for the 3M-6M Tenor Basis Swap from its instruments and quotes.
		 */

		System.out.println ("\n-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("---------------------------------------------------    3M-6M Basis Swap    --------------------------------------------------");

		xM6MBasisSample (
			dtToday,
			strCurrency,
			dc,
			3,
			new String[] {
				"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y"
			},
			strManifestMeasure,
			new double[] {
				0.00186,    //  1Y
				0.00127,    //  2Y
				0.00097,    //  3Y
				0.00080,    //  4Y
				0.00067,    //  5Y
				0.00058,    //  6Y
				0.00051,    //  7Y
				0.00046,    //  8Y
				0.00042,    //  9Y
				0.00038,    // 10Y
				0.00035,    // 11Y
				0.00033,    // 12Y
				0.00028,    // 15Y
				0.00022,    // 20Y
				0.00020,    // 25Y
				0.00018     // 30Y
				}
			);

		/*
		 * Build and run the sampling for the 12M-6M Tenor Basis Swap from its instruments and quotes.
		 */

		System.out.println ("\n-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("---------------------------------------------------   12M-6M Basis Swap    --------------------------------------------------");

		xM6MBasisSample (
			dtToday,
			strCurrency,
			dc,
			12,
			new String[] {
				"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y",
				"35Y", "40Y" // Extrapolated
			},
			strManifestMeasure,
			new double[] {
				-0.00212,    //  1Y
				-0.00152,    //  2Y
				-0.00117,    //  3Y
				-0.00097,    //  4Y
				-0.00082,    //  5Y
				-0.00072,    //  6Y
				-0.00063,    //  7Y
				-0.00057,    //  8Y
				-0.00051,    //  9Y
				-0.00047,    // 10Y
				-0.00044,    // 11Y
				-0.00041,    // 12Y
				-0.00035,    // 15Y
				-0.00028,    // 20Y
				-0.00025,    // 25Y
				-0.00022,    // 30Y
				-0.00022,    // 35Y Extrapolated
				-0.00022,    // 40Y Extrapolated
				}
			);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		System.out.println ("\n-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("-----------------------------------------------   BASIS ON THE DERIVED LEG    -----------------------------------------------");

		System.out.println ("-----------------------------------------------------------------------------------------------------------------------------");

		CustomForwardCurveBuilderSample ("DerivedParBasisSpread");

		System.out.println ("\n-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("-----------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("----------------------------------------------   BASIS ON THE REFERENCE LEG    ----------------------------------------------");

		System.out.println ("-----------------------------------------------------------------------------------------------------------------------------");

		CustomForwardCurveBuilderSample ("ReferenceParBasisSpread");
	}
}
