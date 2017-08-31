
package org.drip.sample.floatfloat;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.definition.*;
import org.drip.product.fx.ComponentPair;
import org.drip.product.rates.*;
import org.drip.quant.common.*;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.stretch.*;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.inference.*;

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
 * JurisdictionOTCIndexSwaps demonstrates the Construction and Usage of the Jurisdiction Standard OTC
 *  Float-Float Swaps.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class JurisdictionOTCIndexSwaps {

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

	private static final ComponentPair OTCComponentPair (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strDerivedTenor,
		final String strMaturityTenor,
		final double dblReferenceFixedCoupon,
		final double dblDerivedFixedCoupon,
		final double dblDerivedStreamBasis)
	{
		FloatFloatSwapConvention ffConv = IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		return ffConv.createFixFloatComponentPair (
			dtSpot,
			strDerivedTenor,
			strMaturityTenor,
			dblReferenceFixedCoupon,
			dblDerivedFixedCoupon,
			dblDerivedStreamBasis,
			1.
		);
	}

	/*
	 * Construct the Array of Deposit from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] DepositFromMaturityDays (
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
					aiDay[i] + "D"
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

	private static final FixFloatComponent[] SwapFromMaturityTenor (
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
		final String strCurrency)
		throws Exception
	{
		/*
		 * Construct the array of Deposit instruments and their quotes.
		 */

		CalibratableComponent[] aDepositComp = DepositFromMaturityDays (
			dtSpot,
			new int[] {
			},
			0,
			strCurrency
		);

		double[] adblDepositQuote = new double[] {
		};

		/*
		 * Construct the array of Swap instruments and their quotes.
		 */

		double[] adblSwapQuote = new double[] {
			0.0009875,   //  9M
			0.00122,     //  1Y
			0.00223,     // 18M
			0.00383,     //  2Y
			0.00827,     //  3Y
			0.01245,     //  4Y
			0.01605,     //  5Y
			0.02597      // 10Y
		};

		String[] astrSwapManifestMeasure = new String[] {
			"SwapRate",		//  9M
			"SwapRate",     //  1Y
			"SwapRate",     // 18M
			"SwapRate",     //  2Y
			"SwapRate",     //  3Y
			"SwapRate",     //  4Y
			"SwapRate",     //  5Y
			"SwapRate"      // 10Y
		};

		CalibratableComponent[] aSwapComp = SwapFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"9M",
				"1Y",
				"18M",
				"2Y",
				"3Y",
				"4Y",
				"5Y",
				"10Y"
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
				strCurrency
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

	private static final FloatFloatComponent[] OTCFloatFloat (
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
	 * Construct an array of fix-float component pairs from the corresponding reference (6M) and the derived legs.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final ComponentPair[] OTCComponentPair (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final int iTenorInMonths,
		final CurveSurfaceQuoteContainer csqs)
		throws Exception
	{
		if (null == astrMaturityTenor || 0 == astrMaturityTenor.length) return null;

		ComponentPair[] aFFCP = new ComponentPair[astrMaturityTenor.length];

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			ComponentPair cp = OTCComponentPair (
				dtSpot,
				strCurrency,
				iTenorInMonths + "M",
				astrMaturityTenor[i],
				0.,
				0.,
				0.
			);

			double dblReferenceFixedCoupon = cp.referenceComponent().measureValue (
				valParams,
				null,
				csqs,
				null,
				"FairPremium"
			);

			double dblDerivedFixedCoupon = cp.derivedComponent().measureValue (
				valParams,
				null,
				csqs,
				null,
				"FairPremium"
			);

			aFFCP[i] = OTCComponentPair (
				dtSpot,
				strCurrency,
				iTenorInMonths + "M",
				astrMaturityTenor[i],
				dblReferenceFixedCoupon,
				dblDerivedFixedCoupon,
				0.
			);
		}

		return aFFCP;
	}

	private static final ForwardCurve MakeFloatFloatFC (
		final JulianDate dtSpot,
		final String strCurrency,
		final MergedDiscountForwardCurve dc,
		final int iTenorInMonths,
		final String[] astrxM6MFwdTenor,
		final double[] adblxM6MBasisSwapQuote,
		final boolean bDisplay)
		throws Exception
	{
		if (bDisplay) {
			System.out.println ("------------------------------------------------------------");

			System.out.println (" SPL =>              n=4               |         |         |");

			System.out.println ("---------------------------------------|  LOG DF |  LIBOR  |");

			System.out.println (" MSR =>  RECALC  |  REFEREN |  DERIVED |         |         |");

			System.out.println ("------------------------------------------------------------");
		}

		/*
		 * Construct the 6M-xM float-float basis swap.
		 */

		FloatFloatComponent[] aFFC = OTCFloatFloat (
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
			new PolynomialFunctionSetParams (4),
			aFFC,
			"DerivedParBasisSpread",
			adblxM6MBasisSwapQuote,
			dblStartingFwd
		);

		if (bDisplay) {

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

			for (int i = 0; i < astrxM6MFwdTenor.length; ++i) {
				FloatFloatComponent ffc = aFFC[i];
				String strMaturityTenor = astrxM6MFwdTenor[i];

				int iFwdEndDate = dtSpot.addTenor (strMaturityTenor).julian();

				int iFwdStartDate = dtSpot.addTenor (strMaturityTenor).subtractTenor (strBasisTenor).julian();

				CaseInsensitiveTreeMap<Double> mapQuarticValue = ffc.value (
					valParams,
					null,
					mktParamsQuarticFwd,
					null
				);

				System.out.println (" " + strMaturityTenor + " =>  " +
					FormatUtil.FormatDouble (fcxMQuartic.forward (strMaturityTenor), 2, 2, 100.) + "  |  " +
					FormatUtil.FormatDouble (mapQuarticValue.get ("ReferenceParBasisSpread"), 2, 2, 1.) + "  |  " +
					FormatUtil.FormatDouble (mapQuarticValue.get ("DerivedParBasisSpread"), 2, 2, 1.) + "  |  " +
					FormatUtil.FormatDouble (iFreq * java.lang.Math.log (dc.df (iFwdStartDate) / dc.df (iFwdEndDate)), 1, 2, 100.) + "  |  " +
					FormatUtil.FormatDouble (dc.libor (iFwdStartDate, iFwdEndDate), 1, 2, 100.) + "  |  "
				);
			}
		}

		return fcxMQuartic;
	}

	private static final ForwardCurve MakeComponentPairFC (
		final JulianDate dtSpot,
		final String strCurrency,
		final MergedDiscountForwardCurve dc,
		final int iTenorInMonths,
		final String[] astrComponentPairTenor,
		final double[] adblComponentPairQuote,
		final boolean bDisplay)
		throws Exception
	{
		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
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
			null,
			null
		);

		org.drip.product.fx.ComponentPair[] aComponentPair = OTCComponentPair (
			dtSpot,
			strCurrency,
			astrComponentPairTenor,
			iTenorInMonths,
			mktParams
		);

		/*
		 * Construct the Float-Float Component Set Stretch Builder
		 */

		FloatFloatSwapConvention ffConv = IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		LatentStateStretchSpec fixFloatCPStretch = LatentStateStretchBuilder.ComponentPairForwardStretch (
			"FIXFLOATCP",
			aComponentPair,
			valParams,
			mktParams,
			adblComponentPairQuote,
			ffConv.basisOnDerivedComponent(),
			ffConv.basisOnDerivedStream()
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {
			fixFloatCPStretch
		};

		/*
		 * Set up the Linear Curve Calibrator using the following parameters:
		 * 	- Cubic Exponential Mixture Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LinearLatentStateCalibrator lcc = new LinearLatentStateCalibrator (
			new org.drip.spline.params.SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (5),
				org.drip.spline.params.SegmentInelasticDesignControl.Create (
					2,
					2
				),
				new org.drip.spline.params.ResponseScalingShapeControl (
					true,
					new org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)
				),
				null
			),
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE,
			null,
			null
		);

		/*
		 * Construct the Shape Preserving Forward Curve by applying the linear curve calibrator to the array
		 *  of Deposit and Swap Stretches.
		 */

		ForwardCurve fcDerived = ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (
			lcc,
			aStretchSpec,
			aComponentPair[0].derivedComponent().forwardLabel().get ("DERIVED"),
			valParams,
			null,
			mktParams,
			null,
			dc.libor (
				dtSpot,
				iTenorInMonths + "M"
			)
		);

		/*
		 * Set the discount curve + cubic polynomial forward curve based component market parameters.
		 */

		mktParams.setForwardState (fcDerived);

		if (bDisplay) {
			System.out.println ("\n\t----------------------------------------------------------------");

			System.out.println ("\t----------------------------------------------------------------");

			/*
			 * Cross-Comparison of the Fix-Float Component Pair "DerivedParBasisSpread" metric.
			 */

			if (null != aComponentPair && null != adblComponentPairQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     FIX-FLOAT COMPONENT PAIR QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aComponentPair.length; ++i)
					System.out.println ("\t[" + aComponentPair[i].effective() + " - " + aComponentPair[i].maturity() + "] = " +
						FormatUtil.FormatDouble (aComponentPair[i].derivedComponent().measureValue (valParams, null, mktParams, null, "DerivedParBasisSpread"), 1, 2, 1.) + " | " +
							FormatUtil.FormatDouble (aComponentPair[i].derivedComponent().measureValue (valParams, null, mktParams, null, "ReferenceParBasisSpread"), 1, 2, 1.) + " | " +
								FormatUtil.FormatDouble (adblComponentPairQuote[i], 1, 2, 10000.) + " | " +
									FormatUtil.FormatDouble (fcDerived.forward (aComponentPair[i].maturity()), 1, 4, 100.) + "% | " +
										FormatUtil.FormatDouble (dc.libor (aComponentPair[i].maturity().subtractTenor ("3M"), iTenorInMonths + "M"), 1, 4, 100.) + "%");
			}

			System.out.println ("\t---------------------------------------------------------");

			System.out.println ("\n\t---------------------------------------------------------");

			System.out.println ("\t\tFIX-FLOAT COMPONENT PAIR RUNS");

			System.out.println ("\t---------------------------------------------------------");

			System.out.println ("\tL -> R:");

			System.out.println ("\t\tCurrency");

			System.out.println ("\t\tFloat-Float Effective");

			System.out.println ("\t\tFloat-Float Maturity");

			System.out.println ("\t\tDerived Component Derived Stream Par Basis Spread");

			System.out.println ("\t\tDerived Component Reference Stream Par Basis Spread");

			System.out.println ("\t---------------------------------------------------------");
		}

		return fcDerived;
	}

	private static final ForwardCurve MakeFloatFloatFC (
		final String strCurrency,
		final MergedDiscountForwardCurve dc,
		final boolean bDisplay)
		throws Exception
	{
		/*
		 * Build and run the sampling for the 3M-6M Tenor Basis Swap from its instruments and quotes.
		 */

		if (bDisplay) {
			System.out.println ("\n------------------------------------------------------------");

			System.out.println ("-------------------    3M-6M Basis Swap    -----------------");
		}

		ForwardCurve fc3M = MakeFloatFloatFC (
			dc.epoch(),
			strCurrency,
			dc,
			3,
			new String[] {
				"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y"
			},
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
			},
			bDisplay
		);

		if (bDisplay) System.out.println ("------------------------------------------------------------\n\n");

		return fc3M;
	}

	private static final ForwardCurve MakeComponentPairFC (
		final String strCurrency,
		final MergedDiscountForwardCurve dc,
		final boolean bDisplay)
		throws Exception
	{
		/*
		 * Build and run the sampling for the 3M-6M Tenor Basis Swap from its instruments and quotes.
		 */

		ForwardCurve fc3M = MakeComponentPairFC (
			dc.epoch(),
			strCurrency,
			dc,
			3,
			new String[] {
				"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y"
			},
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
			},
			bDisplay
		);

		return fc3M;
	}

	private static final void OTCFloatFloatRun (
		final String strCurrency,
		final JulianDate dtSpot,
		final boolean bDisplay)
		throws Exception
	{
		/*
		 * Construct the Discount Curve using its instruments and quotes
		 */

		MergedDiscountForwardCurve dc = MakeDC (
			dtSpot,
			strCurrency
		);

		ForwardCurve fc3M = MakeFloatFloatFC (
			strCurrency,
			dc,
			bDisplay
		);

		CurveSurfaceQuoteContainer csqs = MarketParamsBuilder.Create (
			dc,
			fc3M,
			null,
			null,
			null,
			null,
			null,
			null
		);

		FloatFloatComponent ffc = OTCFloatFloat (
			dtSpot,
			strCurrency,
			"3M",
			"10Y",
			0.
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		Map<String, Double> mapFFCMeasures = ffc.value (
			valParams,
			null,
			csqs,
			null
		);

		System.out.println (
			"\t| " + strCurrency + "  [" + ffc.effectiveDate() + " -> " + ffc.maturityDate() + "]  =>  " +
			FormatUtil.FormatDouble (mapFFCMeasures.get ("ReferenceParBasisSpread"), 1, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (mapFFCMeasures.get ("DerivedParBasisSpread"), 1, 2, 1.) + "  |"
		);
	}

	private static final void OTCComponentPairRun (
		final String strCurrency,
		final JulianDate dtSpot,
		final String strMaturityTenor,
		final boolean bDisplay)
		throws Exception
	{
		/*
		 * Construct the Discount Curve using its instruments and quotes
		 */

		MergedDiscountForwardCurve dc = MakeDC (
			dtSpot,
			strCurrency
		);

		ForwardCurve fc3M = MakeComponentPairFC (
			strCurrency,
			dc,
			bDisplay
		);

		CurveSurfaceQuoteContainer csqs = MarketParamsBuilder.Create (
			dc,
			fc3M,
			null,
			null,
			null,
			null,
			null,
			null
		);

		ComponentPair cp = OTCComponentPair (
			dtSpot,
			strCurrency,
			"3M",
			strMaturityTenor,
			0.,
			0.,
			0.
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		Map<String, Double> mapComponentPairMeasures = cp.value (
			valParams,
			null,
			csqs,
			null
		);

		System.out.println (
			"\t| " + strCurrency + "  [" + cp.effective() + " -> " + cp.maturity() + "]  =>  " +
			FormatUtil.FormatDouble (mapComponentPairMeasures.get ("DerivedCompDerivedBasis"), 1, 2, 1.) + "  |  " +
			FormatUtil.FormatDouble (mapComponentPairMeasures.get ("DerivedCompReferenceBasis"), 1, 2, 1.) + "  |"
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

		JulianDate dtSpot = DateUtil.Today();

		OTCFloatFloatRun ("AUD", dtSpot, true);

		System.out.println ("\t---------------------------------------------------------");

		System.out.println ("\tL -> R:");

		System.out.println ("\t\tCurrency");

		System.out.println ("\t\tFloat-Float Effective");

		System.out.println ("\t\tFloat-Float Maturity");

		System.out.println ("\t\tReference Stream Par Basis Spread");

		System.out.println ("\t\tDerived Stream Par Basis Spread");

		System.out.println ("\t---------------------------------------------------------");

		System.out.println ("\t\tFLOAT-FLOAT SINGLE COMPONENT RUNS");

		System.out.println ("\t---------------------------------------------------------");

		OTCFloatFloatRun ("AUD", dtSpot, false);

		OTCFloatFloatRun ("CAD", dtSpot, false);

		OTCFloatFloatRun ("CHF", dtSpot, false);

		OTCFloatFloatRun ("CNY", dtSpot, false);

		OTCFloatFloatRun ("DKK", dtSpot, false);

		OTCFloatFloatRun ("GBP", dtSpot, false);

		OTCFloatFloatRun ("HKD", dtSpot, false);

		OTCFloatFloatRun ("INR", dtSpot, false);

		OTCFloatFloatRun ("JPY", dtSpot, false);

		OTCFloatFloatRun ("NOK", dtSpot, false);

		OTCFloatFloatRun ("NZD", dtSpot, false);

		OTCFloatFloatRun ("PLN", dtSpot, false);

		OTCFloatFloatRun ("SEK", dtSpot, false);

		OTCFloatFloatRun ("SGD", dtSpot, false); 

		OTCFloatFloatRun ("USD", dtSpot, false);

		OTCFloatFloatRun ("ZAR", dtSpot, false);

		OTCComponentPairRun ("EUR", dtSpot, "1Y", true);

		OTCComponentPairRun ("EUR", dtSpot, "2Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "3Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "4Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "5Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "6Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "7Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "8Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "9Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "11Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "12Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "15Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "20Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "25Y", false);

		OTCComponentPairRun ("EUR", dtSpot, "30Y", false);

		System.out.println ("\t---------------------------------------------------------");
	}
}
