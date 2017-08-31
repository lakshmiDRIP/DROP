
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
 * BulletCorporate4 demonstrates Non-EOS Fixed Coupon Corporate Bond Pricing and Relative Value Measure
 *  Generation Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BulletCorporate4 {

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
			// Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  3, 17), DateUtil.CreateFromYMD (2017,  3, 15), 0.02400, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  7, 23), DateUtil.CreateFromYMD (2017,  7,  1), 0.01450, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  9, 20), DateUtil.CreateFromYMD (2017,  9, 25), 0.01800, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1999,  6, 25), DateUtil.CreateFromYMD (2019,  3,  1), 0.07575, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  4,  7), DateUtil.CreateFromYMD (2019,  4, 15), 0.09000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  6, 30), DateUtil.CreateFromYMD (2019,  7, 15), 0.08500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007, 11, 13), DateUtil.CreateFromYMD (2019, 11,  1), 0.06040, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  3,  1), DateUtil.CreateFromYMD (2020,  3,  1), 0.07000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  3, 11), DateUtil.CreateFromYMD (2020,  3, 15), 0.05350, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  3, 26), DateUtil.CreateFromYMD (2020,  4,  1), 0.07375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  9, 27), DateUtil.CreateFromYMD (2020,  9, 30), 0.06250, 2, "30/360 NON/EOM"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010, 11, 16), DateUtil.CreateFromYMD (2020, 11, 16), 0.05750, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  1, 25), DateUtil.CreateFromYMD (2021,  1, 30), 0.05250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  8,  9), DateUtil.CreateFromYMD (2021,  8, 15), 0.05375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011, 11, 14), DateUtil.CreateFromYMD (2021,  9, 15), 0.07250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  6, 14), DateUtil.CreateFromYMD (2022,  6, 15), 0.03950, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  8, 13), DateUtil.CreateFromYMD (2022,  8, 15), 0.05375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  9, 12), DateUtil.CreateFromYMD (2023,  9, 12), 0.03875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  9, 18), DateUtil.CreateFromYMD (2022,  9, 15), 0.04450, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  6, 20), DateUtil.CreateFromYMD (2023,  9, 15), 0.05100, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  9, 12), DateUtil.CreateFromYMD (2023,  9, 15), 0.05000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013, 11, 26), DateUtil.CreateFromYMD (2023, 11, 26), 0.03875, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  4,  3), DateUtil.CreateFromYMD (2024,  4,  1), 0.04250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  3, 22), DateUtil.CreateFromYMD (2024,  4, 11), 0.04150, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 11,  4), DateUtil.CreateFromYMD (2024,  4, 11), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  5, 23), DateUtil.CreateFromYMD (2024,  6,  1), 0.05250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 11, 10), DateUtil.CreateFromYMD (2024, 11, 15), 0.04600, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  1, 29), DateUtil.CreateFromYMD (2024, 11, 15), 0.05875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 11, 17), DateUtil.CreateFromYMD (2024, 11, 15), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  9, 27), DateUtil.CreateFromYMD (2024, 12,  1), 0.04875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  3, 31), DateUtil.CreateFromYMD (2025,  3, 18), 0.06625, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  5, 15), DateUtil.CreateFromYMD (2025,  5, 15), 0.03663, 12, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  8,  3), DateUtil.CreateFromYMD (2025,  8, 15), 0.04700, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  9, 24), DateUtil.CreateFromYMD (2025,  9, 15), 0.04600, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 12,  9), DateUtil.CreateFromYMD (2025,  9, 15), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 10, 14), DateUtil.CreateFromYMD (2025, 10, 14), 0.04375, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 11, 23), DateUtil.CreateFromYMD (2025, 12,  1), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 11, 23), DateUtil.CreateFromYMD (2025, 12,  1), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  9, 16), DateUtil.CreateFromYMD (2027,  1, 16), 0.03107, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  1, 19), DateUtil.CreateFromYMD (2028,  1, 15), 0.04100, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  9, 27), DateUtil.CreateFromYMD (2028, 10,  7), 0.03100, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  4, 25), DateUtil.CreateFromYMD (2031,  1,  5), 0.06625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009, 12, 22), DateUtil.CreateFromYMD (2032,  1, 10), 0.07507, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  5, 14), DateUtil.CreateFromYMD (2035,  5, 15), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2006,  1, 31), DateUtil.CreateFromYMD (2036,  1, 15), 0.05560, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007,  5, 10), DateUtil.CreateFromYMD (2037,  5, 10), 0.05951, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008,  4,  4), DateUtil.CreateFromYMD (2038,  4,  1), 0.06750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008,  5,  8), DateUtil.CreateFromYMD (2038,  5, 15), 0.05900, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  2,  3), DateUtil.CreateFromYMD (2039,  2,  1), 0.06500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  8, 10), DateUtil.CreateFromYMD (2039,  8, 15), 0.09375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010, 10, 15), DateUtil.CreateFromYMD (2040, 10, 15), 0.06950, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2007, 12,  4), DateUtil.CreateFromYMD (2041, 12, 15), 0.06071, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  9, 18), DateUtil.CreateFromYMD (2044,  9, 15), 0.04900, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  7, 11), DateUtil.CreateFromYMD (2046,  3, 15), 0.08125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2004,  5, 27), DateUtil.CreateFromYMD (2049,  2,  1), 0.06193, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  5, 12), DateUtil.CreateFromYMD (2050,  6, 15), 0.05750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2006,  9, 29), DateUtil.CreateFromYMD (2051,  9, 15), 0.05611, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008, 11,  4), DateUtil.CreateFromYMD (2053,  9, 15), 0.06160, 2, "30/360"),
		};

		double[] adblCleanPrice = new double[] {
			// 1.0018170,	// (2017,  3, 15)
			1.0013230,	// (2017,  7,  1)
			1.0011760,	// (2017,  9, 25)
			1.0455000,	// (2019,  3,  1)
			1.1366680,	// (2019,  4, 15)
			1.1376600,	// (2019,  7, 15)
			1.0530710,	// (2019, 11,  1)
			1.0807080,	// (2020,  3,  1)
			1.0493140,	// (2020,  3, 15)
			1.0025000,	// (2020,  4,  1)
			1.1120710,	// (2020,  9, 30)
			1.0956540,	// (2020, 11, 16)
			1.0712130,	// (2021,  1, 30)
			1.0986090,	// (2021,  8, 15)
			1.0740510,	// (2021,  9, 15)
			0.9936411,	// (2022,  6, 15)
			1.1100570,	// (2022,  8, 15)
			1.0419520,	// (2022,  9, 15)
			0.9646333,	// (2023,  9, 12)
			1.0170750,	// (2023,  9, 15)
			1.0842850,	// (2023,  9, 15)
			1.0184740,	// (2023, 11, 15)
			1.0133860,	// (2024,  4,  1)
			1.0317130,	// (2024,  4, 11)
			1.0168940,	// (2024,  4, 11)
			0.9895703,	// (2024,  6,  1)
			0.9994200,	// (2024, 11, 15)
			1.0124990,	// (2024, 11, 15)
			0.9855716,	// (2024, 11, 15)
			1.0431030,	// (2024, 12,  1)
			1.1375300,	// (2025,  3, 18)
			1.0096890,	// (2025,  5, 15)
			1.0327190,	// (2025,  8, 15)
			1.0455430,	// (2025,  9, 15)
			0.9841400,	// (2025, 10, 14)
			0.9922074,	// (2025, 12,  1)
			1.0320300,	// (2025, 12,  9)
			0.9789541,	// (2025, 12, 15)
			0.9501763,	// (2027,  1, 16)
			1.0181940,	// (2028,  1, 15)
			0.9576301,	// (2028, 10,  7)
			1.1426420,	// (2031,  5,  1)
			1.2375490,	// (2032,  1, 10)
			0.9592908,	// (2035,  5, 15)
			1.1073040,	// (2036,  1, 15)
			1.2147760,	// (2037,  5, 10)
			1.3483540,	// (2038,  4,  1)
			1.1883150,	// (2038,  5, 15)
			1.2622890,	// (2039,  2,  1)
			1.5450800,	// (2039,  8, 15)
			1.2270620,	// (2040, 10, 15)
			0.9457504,	// (2041, 12, 15)
			1.0821480,	// (2044,  9, 15)
			1.2320100,	// (2046,  3, 15)
			1.1807840,	// (2049,  4,  1)
			0.9854683,	// (2050,  6, 15)
			1.0373120,	// (2051,  9, 15)
			0.9749159,	// (2053,  9, 15)
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
