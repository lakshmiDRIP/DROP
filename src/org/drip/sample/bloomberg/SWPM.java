
package org.drip.sample.bloomberg;

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.definition.*;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
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
 * SWPM contains the sample demonstrating the replication of Bloomberg's SWPM functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SWPM {
	private static final String FIELD_SEPARATOR = "    ";

	/*
	 * Construct the Array of Deposit Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] DepositInstrumentsFromMaturityDays (
		final JulianDate dtEffective,
		final int[] aiDay,
		final int iNumFuture,
		final String strCurrency)
		throws Exception
	{
		CalibratableComponent[] aCalibComp = new CalibratableComponent[aiDay.length + iNumFuture];

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
			iNumFuture,
			strCurrency
		);

		for (int i = aiDay.length; i < aiDay.length + iNumFuture; ++i)
			aCalibComp[i] = aEDF[i - aiDay.length];

		return aCalibComp;
	}

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
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtEffective,
		final String strCurrency,
		final String[] astrTenor,
		final double[] adblCoupon)
		throws Exception
	{
		CalibratableComponent[] aCalibComp = new CalibratableComponent[astrTenor.length];

		for (int i = 0; i < astrTenor.length; ++i)
			aCalibComp[i] = OTCIRS (
				dtEffective,
				strCurrency,
				astrTenor[i],
				adblCoupon[i]
			);

		return aCalibComp;
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
			new java.lang.String[] {
				"9M", "1Y", "18M", "2Y", "3Y", "4Y", "5Y", "10Y"
			},
			new double[] {
				0.0009875, 0.00122, 0.00223, 0.00383, 0.00827, 0.01245, 0.01605, 0.02597
			}
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
			true
		);
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtValue = DateUtil.Today();

		JulianDate dtSettle = dtValue.addBusDays (
			2,
			"USD"
		);

		System.out.println ("\n---- Valuation Details ----\n");

		System.out.println ("Trade Date  : " + dtValue);

		System.out.println ("Settle Date : " + dtSettle);

		double dblCoupon = 0.0187;
		double dblFixing = 0.00087;
		double dblNotional = 10.e+06;

		/*
		 * Model the discount curve instrument quotes. Best pulled from Curves #42 in the BBG SWPM "Curves" tab
		 */

		/*
		 * Build the Discount Curve
		 */

		MergedDiscountForwardCurve dc = MakeDC (
			dtValue,
			"USD",
			0.
		);

		JulianDate dtEffective = dtValue.addBusDays (
			2,
			"USD"
		);

		JulianDate dtMaturity = dtEffective.addTenor ("5Y");

		/*
		 * Build the Fixed Receive Stream
		 */

		FixFloatComponent swap = OTCIRS (
			dtEffective,
			"USD",
			"5Y",
			0.
		);

		System.out.println ("\n---- Swap Details ----\n");

		System.out.println ("Effective: " + dtEffective);

		System.out.println ("Maturity:  " + dtMaturity);

		/*
		 * Set up the base market parameters, including base discount curves and the base fixings
		 */

		LatentStateFixingsContainer lsfc = new LatentStateFixingsContainer();

		ComposableUnitFloatingPeriod cufs = ((ComposableUnitFloatingPeriod) (swap.derivedStream().periods().get (0).periods().get (0)));

		lsfc.add (
			cufs.referenceIndexPeriod().fixingDate(),
			swap.derivedStream().forwardLabel(),
			dblFixing
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			lsfc
		);

		/*
		 * Set up the valuation parameters
		 */

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtSettle,
			"USD"
		);

		/*
		 * Generate the base scenario measures for the swap
		 */

		CaseInsensitiveTreeMap<Double> mapSwapCalc = swap.value (
			valParams,
			null,
			mktParams,
			null
		);

		double dblBasePV = mapSwapCalc.get ("PV");

		double dblBaseFixedDV01 = mapSwapCalc.get ("FixedDV01");

		System.out.println ("\n---- Swap Output Measures ----\n");

		System.out.println ("Mkt Val      : " + FormatUtil.FormatDouble (dblBasePV, 0, 0, dblNotional));

		System.out.println ("Par Cpn      : " + FormatUtil.FormatDouble (mapSwapCalc.get ("FairPremium"), 1, 5, 100.));

		System.out.println ("Fixed DV01   : " + FormatUtil.FormatDouble (dblBaseFixedDV01, 0, 0, dblNotional));

		/*
		 * Set up the fixings bumped market parameters - these use base discount curve and the bumped fixing
		 */

		lsfc.add (
			cufs.referenceIndexPeriod().fixingDate(),
			swap.derivedStream().forwardLabel(),
			dblFixing + 0.0001
		);

		CurveSurfaceQuoteContainer mktParamsFixingsBumped = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			lsfc
		);

		/*
		 * Generate the fixing bumped scenario measures for the swap
		 */

		CaseInsensitiveTreeMap<Double> mapSwapFixingsBumpedCalc = swap.value (
			valParams,
			null,
			mktParamsFixingsBumped,
			null
		);

		double dblFixingsDV01 = mapSwapFixingsBumpedCalc.get ("PV") - dblBasePV;

		System.out.println ("Fixings DV01 : " + FormatUtil.FormatDouble (dblFixingsDV01, 0, 0, dblNotional));

		System.out.println ("Total DV01   : " + FormatUtil.FormatDouble (dblBaseFixedDV01 + dblFixingsDV01, 0, 0, dblNotional));

		/*
		 * Set up the rate flat bumped market parameters - these use the bumped base discount curve and the base fixing
		 */

		MergedDiscountForwardCurve dcBumped = MakeDC (
			dtValue,
			"USD",
			-0.0001
		);

		lsfc.add (
			dtEffective,
			swap.derivedStream().forwardLabel(),
			dblFixing - 0.0001
		);

		CurveSurfaceQuoteContainer mktParamsRateBumped = MarketParamsBuilder.Create (
			dcBumped,
			null,
			null,
			null,
			null,
			null,
			lsfc
		);

		/*
		 * Generate the rate flat bumped scenario measures for the swap
		 */

		CaseInsensitiveTreeMap<Double> mapSwapRateBumpedCalc = swap.value (
			valParams,
			null,
			mktParamsRateBumped,
			null
		);

		System.out.println ("PV01         : " + FormatUtil.FormatDouble (mapSwapRateBumpedCalc.get ("PV") - dblBasePV, 0, 0, dblNotional));

		/*
		 * Generate the Swap's fixed cash flows
		 */

		System.out.println ("\n---- Fixed Cashflow ----\n");

		for (CompositePeriod p : swap.referenceStream().cashFlowPeriod())
			System.out.println (
				DateUtil.YYYYMMDD (p.payDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.startDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.endDate()) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF() * 360, 0, 0, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF(), 0, 2, dblCoupon * dblNotional) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dc.df (p.payDate()), 1, 4, 1.)
			);

		/*
		 * Generate the Swap's floating cash flows
		 */

		System.out.println ("\n---- Floating Cashflow ----\n");

		for (CompositePeriod p : swap.derivedStream().cashFlowPeriod())
			System.out.println (
				DateUtil.YYYYMMDD (p.payDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.startDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.endDate()) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF() * 360, 0, 0, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dc.df (p.payDate()), 1, 4, 1.)
			);
	}
}
