
package org.drip.sample.option;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.NodeStructure;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.market.otc.*;
import org.drip.param.valuation.*;
import org.drip.pricer.option.BlackScholesAlgorithm;
import org.drip.product.creator.*;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.option.EuropeanCallPut;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

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
 * ATMTermStructureSpline contains an illustration of the Calibration and Extraction of the Deterministic ATM
 * 	Price and Volatility Term Structures using Custom Splines. This does not deal with Local Volatility
 *  Surfaces.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ATMTermStructureSpline {

	private static final FixFloatComponent OTCIRS (
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

	private static final FixFloatComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final double[] adblCoupon)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			FixFloatComponent irs = OTCIRS (
				dtSpot,
				strCurrency,
				astrMaturityTenor[i],
				adblCoupon[i]
			);

			irs.setPrimaryCode ("IRS." + astrMaturityTenor[i] + "." + strCurrency);

			aIRS[i] = irs;
		}

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

		CalibratableComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			new int[] {
				1, 2, 3, 7, 14, 21, 30, 60
			},
			0,
			strCurrency
		);

		double[] adblDepositQuote = new double[] {
			0.01200, 0.01200, 0.01200, 0.01450, 0.01550, 0.01600, 0.01660, 0.01850
		};

		String[] astrDepositManifestMeasure = new String[] {
			"ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate", "ForwardRate"
		};

		/*
		 * Construct the array of Swap instruments and their quotes.
		 */

		double[] adblSwapQuote = new double[] {
			0.02604,    //  4Y
			0.02808,    //  5Y
			0.02983,    //  6Y
			0.03136,    //  7Y
			0.03268,    //  8Y
			0.03383,    //  9Y
			0.03488,    // 10Y
			0.03583,    // 11Y
			0.03668,    // 12Y
			0.03833,    // 15Y
			0.03854,    // 20Y
			0.03672,    // 25Y
			0.03510,    // 30Y
			0.03266,    // 40Y
			0.03145     // 50Y
		};

		String[] astrSwapManifestMeasure = new String[] {
			"SwapRate",    //  4Y
			"SwapRate",    //  5Y
			"SwapRate",    //  6Y
			"SwapRate",    //  7Y
			"SwapRate",    //  8Y
			"SwapRate",    //  9Y
			"SwapRate",    // 10Y
			"SwapRate",    // 11Y
			"SwapRate",    // 12Y
			"SwapRate",    // 15Y
			"SwapRate",    // 20Y
			"SwapRate",    // 25Y
			"SwapRate",    // 30Y
			"SwapRate",    // 40Y
			"SwapRate"     // 50Y
		};

		CalibratableComponent[] aSwapComp = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"
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
			astrDepositManifestMeasure,
			aSwapComp,
			adblSwapQuote,
			astrSwapManifestMeasure,
			true
		);
	}

	private static final double ATMCall (
		final JulianDate dtMaturity,
		final ValuationParams valParams,
		final MergedDiscountForwardCurve dc,
		final double dblVolatility,
		final String strMeasure)
		throws Exception
	{
		Map<String, Double> mapOptionCalc = new EuropeanCallPut (dtMaturity, 1.).value (
			valParams,
			1.,
			false,
			dc,
			new FlatUnivariate (dblVolatility),
			new BlackScholesAlgorithm()
		);

		return mapOptionCalc.get (strMeasure);
	}

	private static final void InputNodeReplicator (
		final NodeStructure ts,
		final String[] astrMaturityTenor,
		final double[] dblNodeInput)
		throws Exception
	{
		System.out.println ("\n\t" + ts.label());

		System.out.println ("\n\t|--------------------------|");

		System.out.println ("\t| TNR =>   CALC  |  INPUT  |");

		System.out.println ("\t|--------------------------|");

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			System.out.println ("\t| " + astrMaturityTenor[i] + " => " +
				FormatUtil.FormatDouble (ts.node (astrMaturityTenor[i]), 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblNodeInput[i], 2, 2, 100.) + "% |");

		System.out.println ("\t|--------------------------|");
	}

	private static final void OffGrid (
		final String strHeader,
		final String[] astrLabel,
		final NodeStructure[] aTS,
		final String[] astrMaturityTenor)
		throws Exception
	{
		System.out.println ("\n\n\t\t" + strHeader + "\n");

		System.out.print ("\t| TNR =>");

		for (int i = 0; i < aTS.length; ++i)
			System.out.print (" " + astrLabel[i] + " | ");

		System.out.println ("\n");

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			System.out.print ("\t| " + astrMaturityTenor[i] + " =>");

			for (int j = 0; j < aTS.length; ++j)
				System.out.print ("  " + FormatUtil.FormatDouble (aTS[j].node (astrMaturityTenor[i]), 2, 2, 100.) + "%   | ");

			System.out.print ("\n");
		}

		System.out.println ("\n");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = DateUtil.Today();

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			"USD"
		);

		/*
		 * Construct the Discount Curve using its instruments and quotes
		 */

		MergedDiscountForwardCurve dc = MakeDC (
			dtToday,
			"USD"
		);

		String[] astrMaturityTenor = new String[] {
			"06M", "01Y", "02Y", "03Y", "04Y", "05Y", "07Y", "10Y", "15Y", "20Y"
		};
		double[] adblVolatility = new double[] {
			0.20, 0.23, 0.27, 0.30, 0.33, 0.35, 0.34, 0.29, 0.26, 0.19
		};
		double[] adblCallPrice = new double[adblVolatility.length];
		double[] adblImpliedCallVolatility = new double[adblVolatility.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			adblCallPrice[i] = ATMCall (
				dtToday.addTenor (astrMaturityTenor[i]),
				valParams,
				dc,
				adblVolatility[i],
				"CallPrice");

			adblImpliedCallVolatility[i] = ATMCall (
				dtToday.addTenor (astrMaturityTenor[i]),
				valParams,
				dc,
				adblVolatility[i],
				"ImpliedCallVolatility");
		}

		NodeStructure tsCallPriceCubicPoly = ScenarioTermStructureBuilder.CubicPolynomialTermStructure (
			"CUBIC_POLY_CALLPRICE_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblCallPrice
		);

		NodeStructure tsCallPriceQuarticPoly = ScenarioTermStructureBuilder.QuarticPolynomialTermStructure (
			"QUARTIC_POLY_CALLPRICE_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblCallPrice
		);

		NodeStructure tsCallPriceKaklisPandelis = ScenarioTermStructureBuilder.KaklisPandelisTermStructure (
			"KAKLIS_PANDELIS_CALLPRICE_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblCallPrice
		);

		NodeStructure tsCallPriceKLKHyperbolic = ScenarioTermStructureBuilder.KLKHyperbolicTermStructure (
			"KLK_HYPERBOLIC_CALLPRICE_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblCallPrice,
			1.
		);

		NodeStructure tsCallPriceKLKRationalLinear = ScenarioTermStructureBuilder.KLKRationalLinearTermStructure (
			"KLK_RATIONAL_LINEAR_CALLPRICE_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblCallPrice,
			1.
		);

		NodeStructure tsCallPriceKLKRationalQuadratic = ScenarioTermStructureBuilder.KLKRationalQuadraticTermStructure (
			"KLK_RATIONAL_QUADRATIC_CALLPRICE_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblCallPrice,
			0.0001
		);

		InputNodeReplicator (
			tsCallPriceCubicPoly,
			astrMaturityTenor,
			adblCallPrice
		);

		NodeStructure tsCallVolatilityCubicPoly = ScenarioTermStructureBuilder.CubicPolynomialTermStructure (
			"CUBIC_POLY_CALLVOL_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblImpliedCallVolatility
		);

		NodeStructure tsCallVolatilityQuarticPoly = ScenarioTermStructureBuilder.QuarticPolynomialTermStructure (
			"QUARTIC_POLY_CALLVOL_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblImpliedCallVolatility
		);

		NodeStructure tsCallVolatilityKaklisPandelis = ScenarioTermStructureBuilder.KaklisPandelisTermStructure (
			"KAKLIS_PANDELIS_CALLVOL_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblImpliedCallVolatility
		);

		NodeStructure tsCallVolatilityKLKHyperbolic = ScenarioTermStructureBuilder.KLKHyperbolicTermStructure (
			"KLK_HYPERBOLIC_CALLVOL_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblImpliedCallVolatility,
			1.
		);

		NodeStructure tsCallVolatilityKLKRationalLinear = ScenarioTermStructureBuilder.KLKRationalLinearTermStructure (
			"KLK_RATIONAL_LINEAR_CALLVOL_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblImpliedCallVolatility,
			1.
		);

		NodeStructure tsCallVolatilityKLKRationalQuadratic = ScenarioTermStructureBuilder.KLKRationalQuadraticTermStructure (
			"KLK_RATIONAL_QUADRATIC_CALLVOL_TERMSTRUCTURE",
			dtToday,
			"USD",
			astrMaturityTenor,
			adblImpliedCallVolatility,
			0.0001
		);

		InputNodeReplicator (
			tsCallVolatilityCubicPoly,
			astrMaturityTenor,
			adblImpliedCallVolatility
		);

		String[] astrOffGridTenor = new String[] {
			"03M", "09M", "18M", "30Y", "42M", "54M", "06Y", "09Y", "12Y", "18Y", "25Y"
		};

		OffGrid (
			"ATM_CALLPRICE_TERM_STRUCTURE",
			new String[] {
				"Cubic Poly", "Quart Poly", "KaklisPand", "KLKHyperbl", "KLKRatlLin", "KLKRatlQua"
			},
			new NodeStructure[] {
				tsCallPriceCubicPoly,
				tsCallPriceQuarticPoly,
				tsCallPriceKaklisPandelis,
				tsCallPriceKLKHyperbolic,
				tsCallPriceKLKRationalLinear,
				tsCallPriceKLKRationalQuadratic
			},
			astrOffGridTenor
		);

		OffGrid (
			"ATM_CALLVOL_TERM_STRUCTURE",
			new String[] {
				"Cubic Poly", "Quart Poly", "KaklisPand", "KLKHyperbl", "KLKRatlLin", "KLKRatlQua"
			},
			new NodeStructure[] {
				tsCallVolatilityCubicPoly,
				tsCallVolatilityQuarticPoly,
				tsCallVolatilityKaklisPandelis,
				tsCallVolatilityKLKHyperbolic,
				tsCallVolatilityKLKRationalLinear,
				tsCallVolatilityKLKRationalQuadratic
			},
			astrOffGridTenor
		);
	}
}
