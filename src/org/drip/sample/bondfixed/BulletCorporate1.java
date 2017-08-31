
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
 * BulletCorporate1 demonstrates Non-EOS Fixed Coupon Corporate Bond Pricing and Relative Value Measure
 *  Generation Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BulletCorporate1 {

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
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3, 10), DateUtil.CreateFromYMD (2017,  3, 10), 0.01350, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  5, 11), DateUtil.CreateFromYMD (2017,  5, 11), 0.03750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  6, 11), DateUtil.CreateFromYMD (2017,  6,  9), 0.01350, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012, 10,  1), DateUtil.CreateFromYMD (2017, 10,  2), 0.02125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 11, 13), DateUtil.CreateFromYMD (2018,  7, 16), 0.03875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008, 12, 11), DateUtil.CreateFromYMD (2019,  1, 15), 0.08250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  2,  3), DateUtil.CreateFromYMD (2019,  2, 15), 0.08125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007,  5,  4), DateUtil.CreateFromYMD (2019,  3, 15), 0.05490, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  3, 17), DateUtil.CreateFromYMD (2019,  3, 15), 0.09375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  2,  6), DateUtil.CreateFromYMD (2019,  8, 16), 0.09250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009, 10,  9), DateUtil.CreateFromYMD (2019, 10, 15), 0.07375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  1, 15), DateUtil.CreateFromYMD (2020,  1, 31), 0.07875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009, 12, 11), DateUtil.CreateFromYMD (2020,  2, 15), 0.06250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  4, 26), DateUtil.CreateFromYMD (2020,  4, 30), 0.06375, 2, "30/360 NON-EOM"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  5, 10), DateUtil.CreateFromYMD (2020,  5, 15), 0.08500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  9, 17), DateUtil.CreateFromYMD (2020,  9, 15), 0.05875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  9, 29), DateUtil.CreateFromYMD (2020,  9, 29), 0.06375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010, 11, 15), DateUtil.CreateFromYMD (2020, 11, 15), 0.05500, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  3, 17), DateUtil.CreateFromYMD (2021,  3,  1), 0.05000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  5, 23), DateUtil.CreateFromYMD (2021, 11,  1), 0.05375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011, 11, 15), DateUtil.CreateFromYMD (2021, 11, 15), 0.04450, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  4, 15), DateUtil.CreateFromYMD (2022,  4, 15), 0.05125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007,  4, 10), DateUtil.CreateFromYMD (2022,  4, 19), 0.05983, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008,  4, 14), DateUtil.CreateFromYMD (2022, 10,  8), 0.06821, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007, 12, 13), DateUtil.CreateFromYMD (2023,  1, 15), 0.06251, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  8, 23), DateUtil.CreateFromYMD (2023,  3, 15), 0.04375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  5, 22), DateUtil.CreateFromYMD (2023,  6,  1), 0.03750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  8, 15), DateUtil.CreateFromYMD (2023,  8, 15), 0.06125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013, 10,  2), DateUtil.CreateFromYMD (2023, 10,  2), 0.05000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 11,  9), DateUtil.CreateFromYMD (2023, 11, 15), 0.06125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013, 12, 11), DateUtil.CreateFromYMD (2024,  1, 15), 0.04150, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  2, 11), DateUtil.CreateFromYMD (2024,  2, 15), 0.04350, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3,  3), DateUtil.CreateFromYMD (2024,  3,  3), 0.04000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3, 14), DateUtil.CreateFromYMD (2024,  4,  1), 0.04600, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3,  9), DateUtil.CreateFromYMD (2024,  4,  1), 0.04400, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  4,  8), DateUtil.CreateFromYMD (2024,  4,  8), 0.03800, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  6, 20), DateUtil.CreateFromYMD (2024,  7,  1), 0.05000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  1, 26), DateUtil.CreateFromYMD (2025,  2,  1), 0.04000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  2, 24), DateUtil.CreateFromYMD (2025,  2, 15), 0.04350, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 12,  4), DateUtil.CreateFromYMD (2025,  2, 15), 0.04450, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 11, 24), DateUtil.CreateFromYMD (2025, 11, 24), 0.04750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012, 12, 16), DateUtil.CreateFromYMD (2025, 12, 16), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012, 11,  9), DateUtil.CreateFromYMD (2027, 11,  9), 0.04375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2001,  3,  2), DateUtil.CreateFromYMD (2031,  3,  3), 0.07125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 10,  1), DateUtil.CreateFromYMD (2035,  7, 29), 0.04950, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  5,  9), DateUtil.CreateFromYMD (2035, 11,  1), 0.06250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2006, 11,  9), DateUtil.CreateFromYMD (2036, 10,  1), 0.05558, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  8,  1), DateUtil.CreateFromYMD (2043,  8, 15), 0.06503, 12, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2003, 10,  1), DateUtil.CreateFromYMD (2043, 10,  1), 0.05937, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  2, 24), DateUtil.CreateFromYMD (2047, 12, 15), 0.05270, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  3,  1), DateUtil.CreateFromYMD (2049,  5, 15), 0.05690, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  4, 26), DateUtil.CreateFromYMD (2050,  6, 15), 0.05524, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  6,  2), DateUtil.CreateFromYMD (2050,  6, 15), 0.05912, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2006,  7,  1), DateUtil.CreateFromYMD (2051,  1,  1), 0.05521, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2006, 11,  1), DateUtil.CreateFromYMD (2051, 10,  1), 0.05658, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007, 12,  4), DateUtil.CreateFromYMD (2053,  6, 15), 0.06171, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007, 12,  4), DateUtil.CreateFromYMD (2053,  6, 15), 0.08421, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  8,  1), DateUtil.CreateFromYMD (2053,  8, 15), 0.06803, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008, 11,  4), DateUtil.CreateFromYMD (2053,  9, 15), 0.07500, 2, "30/360"),
		};

		double[] adblCleanPrice = new double[] {
			1.0004420,	// (2017,  3, 10)
			1.0070520,	// (2017,  5, 11)
			1.0030200,	// (2017,  6,  9)
			1.0027010,	// (2017, 10,  2)
			1.0210990,	// (2018,  7, 16)
			1.1229040,	// (2018, 12, 11)
			1.1101630,	// (2019,  2, 15)
			1.0622680,	// (2019,  3, 15)
			1.1503290,	// (2019,  3, 15)
			1.1771910,	// (2019,  8, 16)
			1.1299690,	// (2019, 10, 15)
			1.1391860,	// (2020,  1, 31)
			1.1063470,	// (2020,  2, 15)
			1.1061200,	// (2020,  4, 30)
			1.1154030,	// (2020,  5, 15)
			1.0756280,	// (2020,  9, 15)
			1.1310700,	// (2020,  9, 29)
			1.0886500,	// (2020, 11, 15)
			1.0783280,	// (2021,  3,  1)
			1.0248690,	// (2021, 11,  1)
			1.0366210,	// (2021, 11, 15)
			1.1100210,	// (2022,  4, 15)
			1.1030640,	// (2022,  4, 19)
			1.1515940,	// (2022, 10,  8)
			1.1458440,	// (2023,  1, 15)
			1.0075000,	// (2023,  3, 15)
			1.0077190,	// (2023,  6,  1)
			1.0452330,	// (2023,  8, 15)
			1.0545460,	// (2023, 10,  2)
			1.0748650,	// (2023, 11, 15)
			1.0500690,	// (2024,  1, 15)
			0.9578745,	// (2024,  2, 15)
			1.0283760,	// (2024,  3,  3)
			1.0263960,	// (2024,  4,  1)
			0.9989979,	// (2024,  4,  1)
			0.9891589,	// (2024,  4,  8)
			1.0542700,	// (2024,  7,  1)
			0.9759940,	// (2025,  2,  1)
			0.9980053,	// (2025,  2, 15)
			0.9772800,	// (2025,  2, 15)
			1.0065140,	// (2025, 11, 24)
			1.0318190,	// (2025, 12, 16)
			1.0575060,	// (2027, 11,  9)
			1.3593050,	// (2031,  3,  3)
			1.0600360,	// (2035,  7, 29)
			1.0899400,	// (2035, 11,  1)
			1.1337930,	// (2036, 10,  1)
			1.1776690,	// (2043,  8,  1)
			1.1227270,	// (2043, 10,  1)
			1.0392280,	// (2047, 12, 15)
			1.1496480,	// (2049,  5, 15)
			1.0961770,	// (2050,  6, 15)
			1.2025960,	// (2050,  6, 15)
			0.9276792,	// (2051,  1,  1)
			1.0181790,	// (2051, 10,  1)
			0.9694037,	// (2053,  6, 15)
			1.0446570,	// (2053,  6, 15)
			1.3776140,	// (2053,  8, 15)
			1.3822020,	// (2053,  9, 15)
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
