
package org.drip.sample.capfloor;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CompositePeriodBuilder;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.ValuationParams;
import org.drip.pricer.option.*;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.fra.FRAStandardCapFloor;
import org.drip.product.params.LastTradingDateSetting;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
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
 * FRAStdCapModels runs a side-by-side comparison of the FRA Cap sequence using different models.
 * 
 * 	- Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics, Mathematical
 * 		Finance 7 (2), 127-155.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FRAStdCapModels {

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
					"3M"
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

	private static final FixFloatComponent[] SwapInstrumentsFromMaturityTenor (
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
				30,
				60,
				91,
				182,
				273
			},
			0,
			strCurrency
		);

		double[] adblDepositQuote = new double[] {
			0.0668750,	//  30D
			0.0675000,	//  60D
			0.0678125,	//  91D
			0.0712500,	// 182D
			0.0750000	// 273D
		};

		String[] astrDepositManifestMeasure = new String[] {
			"ForwardRate", //  30D
			"ForwardRate", //  60D
			"ForwardRate", //  91D
			"ForwardRate", // 182D
			"ForwardRate"  // 273D
		};

		/*
		 * Construct the array of Swap instruments and their quotes.
		 */

		double[] adblSwapQuote = new double[] {
			0.08265,    //  2Y
			0.08550,    //  3Y
			0.08655,    //  4Y
			0.08770,    //  5Y
			0.08910,    //  7Y
			0.08920     // 10Y
		};

		String[] astrSwapManifestMeasure = new String[] {
			"SwapRate",    //  2Y
			"SwapRate",    //  3Y
			"SwapRate",    //  4Y
			"SwapRate",    //  5Y
			"SwapRate",    //  7Y
			"SwapRate"     // 10Y
		};

		CalibratableComponent[] aSwapComp = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"2Y",
				"3Y",
				"4Y",
				"5Y",
				"7Y",
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
			astrDepositManifestMeasure,
			aSwapComp,
			adblSwapQuote,
			astrSwapManifestMeasure,
			false
		);
	}

	private static final FRAStandardCapFloor MakeCap (
		final JulianDate dtEffective,
		final ForwardLabel fri,
		final String strMaturityTenor,
		final String strManifestMeasure,
		final double dblStrike,
		final FokkerPlanckGenerator fpg)
		throws Exception
	{
		ComposableFloatingUnitSetting cfus = new ComposableFloatingUnitSetting (
			fri.tenor(),
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			fri,
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cps = new CompositePeriodSetting (
			4,
			fri.tenor(),
			fri.currency(),
			null,
			1.,
			null,
			null,
			null,
			null
		);

		Stream floatStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				CompositePeriodBuilder.RegularEdgeDates (
					dtEffective.julian(),
					fri.tenor(),
					strMaturityTenor,
					null
				),
				cps,
				cfus
			)
		);

		return new FRAStandardCapFloor (
			"FRA_CAP",
			floatStream,
			strManifestMeasure,
			true,
			dblStrike,
			new LastTradingDateSetting (
				LastTradingDateSetting.MID_CURVE_OPTION_QUARTERLY,
				"",
				Integer.MIN_VALUE
			),
			null,
			fpg
		);
	}

	private static final Map<JulianDate, Double> ValueCap (
		final ForwardLabel fri,
		final String strManifestMeasure,
		final ValuationParams valParams,
		final CurveSurfaceQuoteContainer mktParams,
		final String[] astrMaturityTenor,
		final double[] adblATMStrike,
		final double[] adblATMVol,
		final FokkerPlanckGenerator fpg)
		throws Exception
	{
		Map<JulianDate, Double> mapDateVol = new TreeMap<JulianDate, Double>();

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			FRAStandardCapFloor cap = MakeCap (
				new JulianDate (valParams.valueDate()),
				fri,
				astrMaturityTenor[i],
				strManifestMeasure,
				adblATMStrike[i],
				fpg
			);

			Map<String, Double> mapCapStreamOutput = cap.stream().value (
				valParams,
				null,
				mktParams,
				null
			);

			double dblCapStreamFairPremium = mapCapStreamOutput.get ("FairPremium");

			FixFloatComponent swap = OTCFixFloat (
				new JulianDate (valParams.valueDate()),
				fri.currency(),
				astrMaturityTenor[i],
				0.
			);

			Map<String, Double> mapSwapOutput = swap.value (
				valParams,
				null,
				mktParams,
				null
			);

			double dblSwapRate = mapSwapOutput.get ("FairPremium");

			double dblCapPrice = cap.priceFromFlatVolatility (
				valParams,
				null,
				mktParams,
				null,
				adblATMVol[i]
			);

			cap.stripPiecewiseForwardVolatility (
				valParams,
				null,
				mktParams,
				null,
				adblATMVol[i],
				mapDateVol
			);

			System.out.println (
				"\tCap  " + cap.maturityDate() + " | " +
				FormatUtil.FormatDouble (dblCapStreamFairPremium, 1, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (dblSwapRate, 1, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (cap.strike(), 1, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (adblATMVol[i], 2, 2, 100.) + "% |" +
				FormatUtil.FormatDouble (dblCapPrice, 1, 0, 10000.) + " ||"
			);
		}

		return mapDateVol;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			1995,
			DateUtil.FEBRUARY,
			3
		);

		String strFRATenor = "3M";
		String strCurrency = "GBP";
		String strManifestMeasure = "ParForward";

		ForwardLabel fri = ForwardLabel.Create (
			strCurrency,
			strFRATenor
		);

		MergedDiscountForwardCurve dc = MakeDC (
			dtSpot,
			strCurrency
		);

		ForwardCurve fcNative = dc.nativeForwardCurve (strFRATenor);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			fcNative,
			null,
			null,
			null,
			null,
			null,
			null
		);

		String[] astrMaturityTenor = new String[] {
			 "1Y",
			 "2Y",
			 "3Y",
			 "4Y",
			 "5Y",
			 "7Y",
			"10Y"
		};

		double[] adblATMStrike = new double[] {
			0.0788, //  "1Y",
			0.0839, // 	"2Y",
			0.0864, //  "3Y",
			0.0869, //  "4Y",
			0.0879, //  "5Y",
			0.0890, //  "7Y",
			0.0889  // "10Y"
		};

		double[] adblATMVol = new double[] {
			0.1550, //  "1Y",
			0.1775, // 	"2Y",
			0.1800, //  "3Y",
			0.1775, //  "4Y",
			0.1775, //  "5Y",
			0.1650, //  "7Y",
			0.1550  // "10Y"
		};

		System.out.println ("\t---------------------------------------------------");

		System.out.println ("\t---------------------------------------------------");

		Map<JulianDate, Double> mapLognormalDateVol = ValueCap (
			fri,
			strManifestMeasure,
			valParams,
			mktParams,
			astrMaturityTenor,
			adblATMStrike,
			adblATMVol,
			new BlackScholesAlgorithm()
		);

		System.out.println ("\t---------------------------------------------------");

		System.out.println ("\t---------------------------------------------------");

		Map<JulianDate, Double> mapNormalDateVol = ValueCap (
			fri,
			strManifestMeasure,
			valParams,
			mktParams,
			astrMaturityTenor,
			adblATMStrike,
			adblATMVol,
			new BlackNormalAlgorithm()
		);

		System.out.println ("\n\n\t---------------------------------------------------");

		System.out.println ("\t-----  CALIBRATED FORWARD VOLATILITY NODES --------");

		System.out.println ("\t---------------------------------------------------\n");

		for (Map.Entry<JulianDate, Double> me : mapLognormalDateVol.entrySet())
			System.out.println (
				"\t" +
				me.getKey() + " => " +
				FormatUtil.FormatDouble (me.getValue(), 2, 2, 100.) + "%  |" +
				FormatUtil.FormatDouble (mapNormalDateVol.get (me.getKey()), 2, 2, 100.) + "%  ||"
			);

		System.out.println ("\t---------------------------------------------------");

		System.out.println ("\t---------------------------------------------------");
	}
}
