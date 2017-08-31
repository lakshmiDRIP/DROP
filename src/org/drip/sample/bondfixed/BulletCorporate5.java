
package org.drip.sample.bondfixed;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.analytics.output.BondRVMeasures;
import org.drip.analytics.support.Helper;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.quote.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.product.definition.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * BulletCorporate5 demonstrates Non-EOS Fixed Coupon Corporate Bond Pricing and Relative Value Measure
 *  Generation Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BulletCorporate5 {

	private static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] astrDepositMaturityTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0103456 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01070,
			0.01235,
			0.01360
		};

		String[] astrFixFloatMaturityTenor = new String[] {
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"40Y",
			"50Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.012484, //  1Y
			0.014987, //  2Y
			0.017036, //  3Y
			0.018624, //  4Y
			0.019868, //  5Y
			0.020921, //  6Y
			0.021788, //  7Y
			0.022530, //  8Y
			0.023145, //  9Y
			0.023685, // 10Y
			0.024153, // 11Y
			0.024562, // 12Y
			0.025389, // 15Y
			0.026118, // 20Y
			0.026368, // 25Y
			0.026432, // 30Y
			0.026339, // 40Y
			0.026122  // 50Y
		};

		MergedDiscountForwardCurve dcFunding = LatentMarketStateBuilder.SmoothFundingCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			adblFuturesQuote,
			"ForwardRate",
			astrFixFloatMaturityTenor,
			adblFixFloatQuote,
			"SwapRate"
		);

		Component[] aDepositComp = OTCInstrumentBuilder.FundingDeposit (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor
		);

		Component[] aFuturesComp = ExchangeInstrumentBuilder.ForwardRateFuturesPack (
			dtSpot,
			adblFuturesQuote.length,
			strCurrency
		);

		Component[] aFixFloatComp = OTCInstrumentBuilder.FixFloatStandard (
			dtSpot,
			strCurrency,
			"ALL",
			astrFixFloatMaturityTenor,
			"MAIN",
			0.
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Create (
			dcFunding,
			null,
			null,
			null,
			null,
			null,
			null
		);

		System.out.println();

		System.out.println ("\t|-------------------------------------||");

		System.out.println ("\t|        DEPOSIT INPUT vs. CALC       ||");

		System.out.println ("\t|-------------------------------------||");

		for (int i = 0; i < aDepositComp.length; ++i)
			System.out.println ("\t| [" + aDepositComp[i].maturityDate() + "] =" +
				FormatUtil.FormatDouble (aDepositComp[i].measureValue (
					valParams,
					null,
					csqc,
					null,
					"ForwardRate"
				), 1, 6, 1.) + " |" +
				FormatUtil.FormatDouble (adblDepositQuote[i], 1, 6, 1.) + " ||"
			);

		System.out.println ("\t|-------------------------------------||");

		System.out.println();

		System.out.println ("\t|-------------------------------------||");

		System.out.println ("\t|        FUTURES INPUT vs. CALC       ||");

		System.out.println ("\t|-------------------------------------||");

		for (int i = 0; i < aFuturesComp.length; ++i)
			System.out.println ("\t| [" + aFuturesComp[i].maturityDate() + "] =" +
				FormatUtil.FormatDouble (aFuturesComp[i].measureValue (
					valParams,
					null,
					csqc,
					null,
					"ForwardRate"
				), 1, 6, 1.) + " |" +
				FormatUtil.FormatDouble (adblFuturesQuote[i], 1, 6, 1.) + " ||"
			);

		System.out.println ("\t|-------------------------------------||");

		System.out.println();

		System.out.println ("\t|------------------------------------------------|| ");

		System.out.println ("\t|          FIX-FLOAT INPUTS vs CALIB             ||");

		System.out.println ("\t|------------------------------------------------|| ");

		for (int i = 0; i < aFixFloatComp.length; ++i)
			System.out.println ("\t| [" + aFixFloatComp[i].maturityDate() + "] =" +
				FormatUtil.FormatDouble (aFixFloatComp[i].measureValue (
					valParams,
					null,
					csqc,
					null,
					"CalibSwapRate"
				), 1, 6, 1.) + " |" +
				FormatUtil.FormatDouble (adblFixFloatQuote[i], 1, 6, 1.) + " |" +
				FormatUtil.FormatDouble (aFixFloatComp[i].measureValue (
					valParams,
					null,
					csqc,
					null,
					"FairPremium"
				), 1, 6, 1.) + " ||"
			);

		System.out.println ("\t|------------------------------------------------||");

		System.out.println();

		return dcFunding;
	}

	private static final Map<String, GovvieCurve> GovvieCurve (
		final JulianDate dtSpot,
		final String strCode,
		final double[] adblCoupon,
		final double[] adblYield)
		throws Exception
	{
		JulianDate[] adtEffective = new JulianDate[] {
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot
		};

		JulianDate[] adtMaturity = new JulianDate[] {
			dtSpot.addTenor ("1Y"),
			dtSpot.addTenor ("2Y"),
			dtSpot.addTenor ("3Y"),
			dtSpot.addTenor ("5Y"),
			dtSpot.addTenor ("7Y"),
			dtSpot.addTenor ("10Y"),
			dtSpot.addTenor ("20Y"),
			dtSpot.addTenor ("30Y")
		};

		Map<String, GovvieCurve> mapGovvieCurve = LatentMarketStateBuilder.BumpedGovvieCurve (
			strCode,
			dtSpot,
			adtEffective,
			adtMaturity,
			adblCoupon,
			adblYield,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING,
			0.0001,
			false
		);

		BondComponent[] aComp = TreasuryBuilder.FromCode (
			strCode,
			adtEffective,
			adtMaturity,
			adblCoupon
		);

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setGovvieState (mapGovvieCurve.get ("BASE"));

		System.out.println();

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|       TREASURY INPUT vs CALIB YIELD       ||");

		System.out.println ("\t|-------------------------------------------||");

		for (int i = 0; i < aComp.length; ++i)
			System.out.println ("\t| " + aComp[i].name() + " | " +
				FormatUtil.FormatDouble (adblYield[i], 1, 3, 100.) + "% | " +
				FormatUtil.FormatDouble (aComp[i].yieldFromPrice (
					valParams,
					null,
					null,
					aComp[i].maturityDate().julian(),
					1.,
					aComp[i].priceFromYield (
						valParams,
						null,
						null,
						mapGovvieCurve.get ("BASE").yield (aComp[i].maturityDate().julian())
					)
				), 1, 3, 100.) + "% ||"
			);

		System.out.println ("\t|-------------------------------------------||");

		return mapGovvieCurve;
	}

	private static final void AccumulateBondMarketQuote (
		final CurveSurfaceQuoteContainer csqc,
		final String[] astrOnTheRunCode,
		final double[] adblYield)
		throws Exception
	{
		for (int i = 0; i < astrOnTheRunCode.length; ++i) {
			ProductMultiMeasure pmmq = new ProductMultiMeasure();

			pmmq.addQuote (
				"Yield",
				new MultiSided (
					"mid",
					adblYield[i]
				),
				true
			);

			csqc.setProductQuote (
				astrOnTheRunCode[i],
				pmmq
			);
		}
	}

	private static final Bond Corporate (
		final String strName,
		final JulianDate dtEffective,
		final JulianDate dtMaturity,
		final double dblCoupon,
		final int iFreq,
		final String strDayCount)
		throws Exception
	{
		return BondBuilder.CreateSimpleFixed (
			strName + FormatUtil.FormatDouble (dblCoupon, 1, 4, 100.) + " " + dtMaturity,
			"USD",
			"",
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			null,
			null
		);
	}

	private static final double[] RVMeasures (
		final Bond[] aBond,
		final JulianDate dtValue,
		final CurveSurfaceQuoteContainer csqc,
		final double[] adblCleanPrice)
		throws Exception
	{
		JulianDate dtSettle = dtValue.addBusDays (
			3,
			aBond[0].currency()
		);

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtSettle,
			aBond[0].currency()
		);

		System.out.println();

		System.out.println ("\t|--------------------------------||");

		System.out.println ("\t| Trade Date       : " + dtValue + " ||");

		System.out.println ("\t| Cash Settle Date : " + dtSettle + " ||");

		System.out.println ("\t|--------------------------------||");

		System.out.println();

		String strCurveMetrics = "";
		String strSecularMetrics = "";
		double[] adblOAS = new double[aBond.length];

		for (int i = 0; i < aBond.length; ++i) {
			System.out.println ("Doing " + aBond[i].name());

			WorkoutInfo wi = aBond[i].exerciseYieldFromPrice (
				valParams,
				csqc,
				null,
				adblCleanPrice[i]
			);

			BondRVMeasures rvm = aBond[i].standardMeasures (
				valParams,
				null,
				csqc,
				null,
				wi,
				adblCleanPrice[i]
			);

			strSecularMetrics += "\t| " +
				aBond[i].name() + " | " +
				aBond[i].effectiveDate() + " | " +
				aBond[i].maturityDate() + " |  " +
				aBond[i].firstCouponDate() + "  |" +
				FormatUtil.FormatDouble (adblCleanPrice[i], 3, 3, 100.) + " |" +
				FormatUtil.FormatDouble (wi.yield(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (rvm.macaulayDuration(), 2, 2, 1.) + "  | " +
				FormatUtil.FormatDouble (rvm.modifiedDuration(), 2, 2, 10000.) + "  |  " +
				FormatUtil.FormatDouble (rvm.yield01(), 2, 2, 10000.) + "  |" +
				FormatUtil.FormatDouble (rvm.yield01(), 4, 0, 1000000.) + " |" +
				FormatUtil.FormatDouble (rvm.convexity(), 1, 2, 1000000.) + " |   " +
				FormatUtil.FormatDouble (rvm.bondBasis(), 3, 0, 10000.) + "     ||" + "\n";

			adblOAS[i] = rvm.oas();

			double dblCleanPriceOASUp = aBond[i].priceFromOAS (
				valParams,
				csqc,
				null,
				adblOAS[i] + 0.0001
			);

			double dblCleanPriceOASDown = aBond[i].priceFromOAS (
				valParams,
				csqc,
				null,
				adblOAS[i] - 0.0001
			);

			strCurveMetrics += "\t| " +
				aBond[i].name() + " |" +
				FormatUtil.FormatDouble (adblCleanPrice[i], 3, 3, 100.) + " |" +
				FormatUtil.FormatDouble (wi.yield(), 1, 2, 100.) + "% |   " +
				FormatUtil.FormatDouble (rvm.zSpread(), 3, 0, 10000.) + "   |" +
				FormatUtil.FormatDouble (adblOAS[i], 3, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (0.5 * (dblCleanPriceOASDown - dblCleanPriceOASUp) / adblCleanPrice[i], 2, 2, 10000.) + "  |  " +
				FormatUtil.FormatDouble ((dblCleanPriceOASDown + dblCleanPriceOASUp - 2. * adblCleanPrice[i]) / adblCleanPrice[i], 2, 2, 1000000.) + "   |" +
				FormatUtil.FormatDouble (rvm.asw(), 3, 0, 10000.) + " |  " +
				FormatUtil.FormatDouble (rvm.gSpread(), 3, 0, 10000.) + "    |   " +
				FormatUtil.FormatDouble (rvm.iSpread(), 3, 0, 10000.) + "   |    " +
				FormatUtil.FormatDouble (rvm.tsySpread(), 3, 0, 10000.) + "    |  " +
				Helper.BaseTsyBmk (
					dtValue.julian(),
					aBond[i].maturityDate().julian()
				) + "  ||" + "\n";
		}

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|             BOND           |  EFFECTIVE  |   MATURITY  |  FIRST COUPON |  PRICE  | YIELD | MAC DUR | MOD DUR | YIELD 01 | DV01 | CONV | BOND BASIS ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.print (strSecularMetrics);

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------||\n");

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|             BOND           |  PRICE  | YIELD | Z SPREAD | OAS | OAS DUR |  OAS CONV | ASW | G SPREAD | I SPREAD | TSY SPREAD | TSY BMK ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.print (strCurveMetrics);

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------||");

		return adblOAS;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.FEBRUARY,
			2
		);

		String strCurrency = "USD";
		String strTreasuryCode = "UST";

		MergedDiscountForwardCurve dcFunding = FundingCurve (
			dtSpot,
			strCurrency
		);

		double[] adblTreasuryCoupon = new double[] {
			0.0100,
			0.0100,
			0.0125,
			0.0150,
			0.0200,
			0.0225,
			0.0250,
			0.0300
		};

		double[] adblTreasuryYield = new double[] {
			0.0083,	//  1Y
			0.0122, //  2Y
			0.0149, //  3Y
			0.0193, //  5Y
			0.0227, //  7Y
			0.0248, // 10Y
			0.0280, // 20Y
			0.0308  // 30Y
		};

		Map<String, GovvieCurve> mapGovvieCurve = GovvieCurve (
			dtSpot,
			strTreasuryCode,
			adblTreasuryCoupon,
			adblTreasuryYield
		);

		CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Create (
			dcFunding,
			null,
			null,
			null,
			null,
			null,
			null
		);

		csqc.setGovvieState (mapGovvieCurve.get ("BASE"));

		AccumulateBondMarketQuote (
			csqc,
			new String[] {
				"01YON",
				"02YON",
				"03YON",
				"05YON",
				"07YON",
				"10YON",
				"20YON",
				"30YON"
			},
			adblTreasuryYield
		);

		Bond[] aCorporateBond = new Bond[] {
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  2, 25), DateUtil.CreateFromYMD (2017,  2, 25), 0.00900, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  1, 14), DateUtil.CreateFromYMD (2017,  6,  5), 0.02250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1997,  7, 14), DateUtil.CreateFromYMD (2017,  7, 15), 0.07450, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  3,  5), DateUtil.CreateFromYMD (2017,  9,  5), 0.05125, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  7, 15), DateUtil.CreateFromYMD (2018,  4, 15), 0.03625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011, 11, 21), DateUtil.CreateFromYMD (2018, 11,  1), 0.06250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008, 11, 14), DateUtil.CreateFromYMD (2018, 11, 15), 0.07750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011, 11,  9), DateUtil.CreateFromYMD (2018, 11, 15), 0.09000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  1, 30), DateUtil.CreateFromYMD (2019,  2,  1), 0.07125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  5, 15), DateUtil.CreateFromYMD (2019,  6,  1), 0.08125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  6, 17), DateUtil.CreateFromYMD (2019,  6, 15), 0.09875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  6, 30), DateUtil.CreateFromYMD (2019,  6, 15), 0.09500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  8, 13), DateUtil.CreateFromYMD (2019,  8, 13), 0.07625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  9, 14), DateUtil.CreateFromYMD (2019,  9, 15), 0.07375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  9, 15), DateUtil.CreateFromYMD (2019, 10, 30), 0.06250, 4, "30/360 NON-EOM"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  5, 14), DateUtil.CreateFromYMD (2020,  2, 15), 0.06850, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  3,  4), DateUtil.CreateFromYMD (2020,  3,  4), 0.06700, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  3, 19), DateUtil.CreateFromYMD (2020,  3, 19), 0.06775, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  3, 23), DateUtil.CreateFromYMD (2020,  6,  1), 0.05875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  9, 15), DateUtil.CreateFromYMD (2020,  9, 15), 0.05625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  9, 16), DateUtil.CreateFromYMD (2020,  9, 15), 0.05375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  6, 28), DateUtil.CreateFromYMD (2021,  4, 15), 0.06875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  4,  6), DateUtil.CreateFromYMD (2021,  5,  1), 0.05800, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  4, 16), DateUtil.CreateFromYMD (2021, 10, 17), 0.04890, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011, 11,  7), DateUtil.CreateFromYMD (2021, 11, 15), 0.04875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  3, 12), DateUtil.CreateFromYMD (2022,  3, 15), 0.04700, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012, 10,  5), DateUtil.CreateFromYMD (2022, 10, 15), 0.04700, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2002,  4, 30), DateUtil.CreateFromYMD (2023,  1,  2), 0.06718, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013, 11, 19), DateUtil.CreateFromYMD (2023, 11, 20), 0.03700, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013, 11, 29), DateUtil.CreateFromYMD (2023, 12,  1), 0.04625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3, 12), DateUtil.CreateFromYMD (2024,  3, 14), 0.04250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1994,  5, 11), DateUtil.CreateFromYMD (2024,  5,  1), 0.08625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  8, 29), DateUtil.CreateFromYMD (2024,  9,  3), 0.05750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  9, 15), DateUtil.CreateFromYMD (2025,  9, 15), 0.04750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  1, 27), DateUtil.CreateFromYMD (2026,  1, 27), 0.04650, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  8, 11), DateUtil.CreateFromYMD (2026,  9,  3), 0.03750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  9, 16), DateUtil.CreateFromYMD (2026, 10,  1), 0.03700, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 11, 25), DateUtil.CreateFromYMD (2026, 11, 25), 0.04270, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 12, 14), DateUtil.CreateFromYMD (2027, 12, 15), 0.03750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1998,  7, 10), DateUtil.CreateFromYMD (2028,  7, 15), 0.07050, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016, 10,  3), DateUtil.CreateFromYMD (2028, 10, 15), 0.03250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2000,  3,  6), DateUtil.CreateFromYMD (2029,  3,  1), 0.06927, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1999,  3,  1), DateUtil.CreateFromYMD (2029,  3,  1), 0.06625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  3,  1), DateUtil.CreateFromYMD (2030, 12, 15), 0.05380, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2000, 12, 15), DateUtil.CreateFromYMD (2030, 12, 15), 0.08600, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2001,  8, 15), DateUtil.CreateFromYMD (2031,  8, 15), 0.07300, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2003,  3,  4), DateUtil.CreateFromYMD (2032, 10, 15), 0.05900, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2003,  9, 25), DateUtil.CreateFromYMD (2033,  3, 15), 0.06550, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2003, 10,  1), DateUtil.CreateFromYMD (2033, 10,  1), 0.07450, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008, 12, 11), DateUtil.CreateFromYMD (2038, 12, 15), 0.06375, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  6,  1), DateUtil.CreateFromYMD (2039,  6,  1), 0.08875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009, 12, 16), DateUtil.CreateFromYMD (2039, 12, 16), 0.06850, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  7,  1), DateUtil.CreateFromYMD (2040,  6, 15), 0.07625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008, 11,  4), DateUtil.CreateFromYMD (2046,  3, 15), 0.05780, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  8,  1), DateUtil.CreateFromYMD (2050,  8,  1), 0.05300, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2006, 11, 13), DateUtil.CreateFromYMD (2051, 12,  1), 0.05595, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007,  2,  1), DateUtil.CreateFromYMD (2052,  2, 15), 0.05815, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  3, 18), DateUtil.CreateFromYMD (2057,  3, 15), 0.06750, 2, "30/360"),
		};

		double[] adblCleanPrice = new double[] {
			0.9997787,	// (2017,  2, 25)
			1.0033310,	// (2017,  6,  5)
			1.0278170,	// (2017,  7, 15)
			1.0172200,	// (2017,  9,  5)
			1.0135930,	// (2018,  4, 15)
			1.0562500,	// (2018, 11,  1)
			1.0975070,	// (2018, 11, 15)
			1.0973010,	// (2018, 11, 15)
			1.0976260,	// (2019,  2,  1)
			1.1243420,	// (2019,  6,  1)
			1.1716120,	// (2019,  6, 15)
			1.1584940,	// (2019,  6, 15)
			1.1199700,	// (2019,  8, 13)
			1.1160530,	// (2019,  9, 15)
			0.9841736,	// (2019, 10, 30)
			1.1216150,	// (2020,  2, 15)
			1.1174650,	// (2020,  3,  4)
			1.1262290,	// (2020,  3, 19)
			1.1012410,	// (2020,  6,  1)
			1.0949670,	// (2020,  9, 15)
			1.0831400,	// (2020,  9, 15)
			1.1349120,	// (2021,  4, 15)
			1.1107220,	// (2021,  5,  1)
			1.0216740,	// (2021, 10, 17)
			1.0693220,	// (2021, 11, 15)
			1.0685770,	// (2022,  3, 15)
			1.0456960,	// (2022, 10, 15)
			1.1312500,	// (2023,  1,  2)
			1.0453750,	// (2023, 11, 20)
			1.0542780,	// (2023, 12,  1)
			1.0100730,	// (2024,  3, 14)
			1.2260200,	// (2024,  5,  1)
			0.9529315,	// (2024,  9,  3)
			0.9868410,	// (2025,  9, 15)
			1.0594940,	// (2026,  1, 27)
			1.0098560,	// (2026,  9,  3)
			1.0025000,	// (2026, 10,  1)
			1.0202100,	// (2026, 11, 25)
			1.0093360,	// (2027, 12, 25)
			1.1575970,	// (2028,  7, 15)
			0.9596740,	// (2028, 10, 15)
			1.3272900,	// (2029,  3,  1)
			1.2673600,	// (2029,  3,  1)
			1.0724490,	// (2030, 12, 15)
			1.3656760,	// (2030, 12, 15)
			1.1686390,	// (2031,  8, 15)
			1.1678330,	// (2032, 10, 15)
			1.2210150,	// (2033,  3, 15)
			1.2038020,	// (2033, 10,  1)
			1.2909820,	// (2038, 12, 15)
			1.5222220,	// (2039,  6,  1)
			1.3022560,	// (2039, 12, 16)
			1.2955540,	// (2040,  6, 15)
			1.1743430,	// (2046,  3, 15)
			0.9253630,	// (2050,  8,  1)
			1.0570930,	// (2051, 12,  1)
			0.9831220,	// (2052,  2, 15)
			1.3153640,	// (2057,  3, 15)
		};

		double[] adblOAS = RVMeasures (
			aCorporateBond,
			dtSpot,
			csqc,
			adblCleanPrice
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot.addBusDays (
				3,
				dcFunding.currency()
			),
			dcFunding.currency()
		);

		System.out.println();

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.print ("\t|             BOND          ");

		for (Map.Entry<String, GovvieCurve> meGovvieCurve : mapGovvieCurve.entrySet()) {
			if ("BASE".equalsIgnoreCase (meGovvieCurve.getKey()) || "BUMP".equalsIgnoreCase (meGovvieCurve.getKey()))
				continue;

			System.out.print (" | " + meGovvieCurve.getKey());
		}

		System.out.println (" ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblOAS.length; ++i) {
			System.out.print ("\t| " + aCorporateBond[i].name());

			for (Map.Entry<String, GovvieCurve> meGovvieCurve : mapGovvieCurve.entrySet()) {
				if ("BASE".equalsIgnoreCase (meGovvieCurve.getKey()) || "BUMP".equalsIgnoreCase (meGovvieCurve.getKey()))
					continue;

				csqc.setGovvieState (meGovvieCurve.getValue());

				System.out.print (" |      " +
					FormatUtil.FormatDouble (
						(adblCleanPrice[i] - aCorporateBond[i].priceFromOAS (
							valParams,
							csqc,
							null,
							adblOAS[i]
						)) / adblCleanPrice[i],
					2, 2, 10000.) + "     "
				);
			}

			System.out.println (" ||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
