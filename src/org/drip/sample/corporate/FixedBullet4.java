
package org.drip.sample.corporate;

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
 * FixedBullet4 demonstrates Non-EOS Fixed Coupon Agency Bond Pricing and Relative Value Measure Generation
 *  Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixedBullet4 {

	private static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] astrDepositMaturityTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0111956 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.011375,	// 98.8625
			0.013350,	// 98.6650
			0.014800,	// 98.5200
			0.016450,	// 98.3550
			0.017850,	// 98.2150
			0.019300	// 98.0700
		};

		String[] astrFixFloatMaturityTenor = new String[] {
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
			0.017029, //  2Y
			0.019354, //  3Y
			0.021044, //  4Y
			0.022291, //  5Y
			0.023240, //  6Y
			0.024025, //  7Y
			0.024683, //  8Y
			0.025243, //  9Y
			0.025720, // 10Y
			0.026130, // 11Y
			0.026495, // 12Y
			0.027230, // 15Y
			0.027855, // 20Y
			0.028025, // 25Y
			0.028028, // 30Y
			0.027902, // 40Y
			0.027655  // 50Y
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
			0,
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
			double dblCleanPriceOASUp = Double.NaN;
			double dblCleanPriceOASDown = Double.NaN;

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
				FormatUtil.FormatDouble (aBond[i].accrued (dtSettle.julian(), csqc), 1, 5, 100.) + " |" +
				FormatUtil.FormatDouble (wi.yield(), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (rvm.macaulayDuration(), 2, 2, 1.) + "  | " +
				FormatUtil.FormatDouble (rvm.modifiedDuration(), 2, 2, 10000.) + "  |  " +
				FormatUtil.FormatDouble (rvm.yield01(), 2, 2, 10000.) + "  |" +
				FormatUtil.FormatDouble (rvm.yield01(), 4, 0, 1000000.) + " |" +
				FormatUtil.FormatDouble (rvm.convexity(), 1, 2, 1000000.) + " |" +
				FormatUtil.FormatDouble (aBond[i].weightedAverageLife (valParams, csqc), 2, 2, 1.) + " |   " +
				FormatUtil.FormatDouble (rvm.bondBasis(), 3, 0, 10000.) + "     ||" + "\n";

			adblOAS[i] = rvm.oas();

			try {
				dblCleanPriceOASUp = aBond[i].priceFromOAS (
					valParams,
					csqc,
					null,
					adblOAS[i] + 0.0001
				);

				dblCleanPriceOASDown = aBond[i].priceFromOAS (
					valParams,
					csqc,
					null,
					adblOAS[i] - 0.0001
				);
			} catch (Exception e) {
				// e.printStackTrace();
			}

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

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|             BOND           |  EFFECTIVE  |   MATURITY  |  FIRST COUPON |  PRICE  | ACCRUED | YIELD | MAC DUR | MOD DUR | YIELD 01 | DV01 | CONV |  WAL  | BOND BASIS ||");

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.print (strSecularMetrics);

		System.out.println ("\t|----------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

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
			DateUtil.MARCH,
			15
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
			0.0104,	//  1Y
			0.0137, //  2Y
			0.0167, //  3Y
			0.0213, //  5Y
			0.0243, //  7Y
			0.0260, // 10Y
			0.0294, // 20Y
			0.0319  // 30Y
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
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1997,  7, 14), DateUtil.CreateFromYMD (2017,  7, 15), 0.07450, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007, 12, 11), DateUtil.CreateFromYMD (2018,  1, 15), 0.06400, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  3,  9), DateUtil.CreateFromYMD (2018,  3,  9), 0.02500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  4,  7), DateUtil.CreateFromYMD (2018,  4,  7), 0.05000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008, 11, 14), DateUtil.CreateFromYMD (2018, 11, 15), 0.07750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  3, 17), DateUtil.CreateFromYMD (2019,  3, 15), 0.09375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  6, 17), DateUtil.CreateFromYMD (2019,  6, 15), 0.09875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  8, 13), DateUtil.CreateFromYMD (2019,  8, 13), 0.07625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009, 10,  9), DateUtil.CreateFromYMD (2019, 10, 15), 0.07375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009, 11,  4), DateUtil.CreateFromYMD (2019, 12,  2), 0.06750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  1, 20), DateUtil.CreateFromYMD (2020,  1, 31), 0.07875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  3,  4), DateUtil.CreateFromYMD (2020,  3,  4), 0.06700, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  9, 20), DateUtil.CreateFromYMD (2020,  9, 15), 0.05375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010, 12, 10), DateUtil.CreateFromYMD (2020, 12, 15), 0.06000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  1, 14), DateUtil.CreateFromYMD (2021,  1, 14), 0.06250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  4,  6), DateUtil.CreateFromYMD (2021,  5,  1), 0.05800, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  7, 16), DateUtil.CreateFromYMD (2022,  7, 15), 0.04750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  8, 13), DateUtil.CreateFromYMD (2022,  8, 15), 0.05375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3, 12), DateUtil.CreateFromYMD (2024,  3, 14), 0.04250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  6, 20), DateUtil.CreateFromYMD (2024,  7,  1), 0.05000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  8, 19), DateUtil.CreateFromYMD (2024,  9,  1), 0.04000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  8, 31), DateUtil.CreateFromYMD (2025,  3, 18), 0.06625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1995,  6,  9), DateUtil.CreateFromYMD (2025,  6,  1), 0.06500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  6, 10), DateUtil.CreateFromYMD (2025,  6, 10), 0.04875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  7, 29), DateUtil.CreateFromYMD (2025,  7, 29), 0.04000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  4, 18), DateUtil.CreateFromYMD (2026,  4, 18), 0.04800, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  5, 25), DateUtil.CreateFromYMD (2026,  5, 25), 0.03900, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1999,  3,  1), DateUtil.CreateFromYMD (2029,  3,  1), 0.06625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2001,  3,  2), DateUtil.CreateFromYMD (2031,  3,  3), 0.07125, 2, "30/360"),
		};

		double[] adblCleanPrice = new double[] {
			1.0181970,	// (2017,  7, 15)
			1.0334310,	// (2018,  1, 15)
			1.0078050,	// (2018,  3,  9)
			1.0236300,	// (2018,  4,  7)
			1.0909630,	// (2018, 11, 15)
			1.1390280,	// (2019,  3, 15)
			1.1616470,	// (2019,  6, 15)
			1.1128800,	// (2019,  8, 13)
			1.1222590,	// (2019, 10, 15)
			1.1067600,	// (2019, 12,  2)
			1.1288850,	// (2020,  1, 31)
			1.1091400,	// (2020,  3,  4)
			1.0791630,	// (2020,  9, 15)
			1.0953230,	// (2020, 12, 15)
			1.1053550,	// (2021,  1, 14)
			1.1035480,	// (2021,  5,  1)
			1.0599080,	// (2022,  7, 15)
			1.1072900,	// (2022,  8, 15)
			1.0019800,	// (2024,  3, 14)
			1.0700000,	// (2024,  7,  1)
			0.9916000,	// (2024,  9,  1)
			1.1475000,	// (2025,  3, 18)
			1.1803440,	// (2025,  6,  1)
			1.0190700,	// (2025,  6, 10)
			1.0207250,	// (2025,  7, 29)
			1.0191350,	// (2026,  4, 18)
			0.9910400,	// (2026,  5, 25)
			1.2634410,	// (2029,  3,  1)
			1.3530000,	// (2031,  3,  3)
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

				String strDump = " |                ";

				try {
					strDump = " |      " +
						FormatUtil.FormatDouble (
							(adblCleanPrice[i] - aCorporateBond[i].priceFromOAS (
								valParams,
								csqc,
								null,
								adblOAS[i]
							)) / adblCleanPrice[i],
						2, 2, 10000.
					) + "     ";
				} catch (Exception e) {
					// e.printStackTrace();
				}

				System.out.print (strDump);
			}

			System.out.println (" ||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
