
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
 * BulletCorporate2 demonstrates Non-EOS Fixed Coupon Corporate Bond Pricing and Relative Value Measure
 *  Generation Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BulletCorporate2 {

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
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  4, 27), DateUtil.CreateFromYMD (2017,  5,  1), 0.05625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  5, 22), DateUtil.CreateFromYMD (2017,  5, 15), 0.01150, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  3, 12), DateUtil.CreateFromYMD (2018,  3, 12), 0.02350, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  8, 20), DateUtil.CreateFromYMD (2019,  8, 15), 0.06625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  8, 14), DateUtil.CreateFromYMD (2019,  8, 15), 0.06875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 10, 17), DateUtil.CreateFromYMD (2019, 10, 15), 0.04250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  5, 11), DateUtil.CreateFromYMD (2019, 12, 15), 0.07250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  1, 15), DateUtil.CreateFromYMD (2020,  1, 15), 0.06113, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  4,  1), DateUtil.CreateFromYMD (2020,  3, 15), 0.06750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  6, 29), DateUtil.CreateFromYMD (2020,  6, 30), 0.10250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  7, 19), DateUtil.CreateFromYMD (2020,  7, 15), 0.05450, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  9, 13), DateUtil.CreateFromYMD (2020,  9, 15), 0.05250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  9, 20), DateUtil.CreateFromYMD (2020,  9, 15), 0.05375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  9, 10), DateUtil.CreateFromYMD (2020,  9, 30), 0.05000, 2, "30/360 NON-EOM"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010, 10, 12), DateUtil.CreateFromYMD (2020, 10,  1), 0.06535, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  9, 30), DateUtil.CreateFromYMD (2021,  1, 21), 0.05500, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  7, 11), DateUtil.CreateFromYMD (2021,  7, 11), 0.06125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  6,  8), DateUtil.CreateFromYMD (2021,  9, 11), 0.05125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  5,  5), DateUtil.CreateFromYMD (2021, 10, 15), 0.06250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  2, 29), DateUtil.CreateFromYMD (2022,  4,  1), 0.04600, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  3, 13), DateUtil.CreateFromYMD (2022,  4,  1), 0.04950, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  3, 22), DateUtil.CreateFromYMD (2022,  4, 15), 0.03950, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  3, 15), DateUtil.CreateFromYMD (2022,  5, 15), 0.06500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  7, 16), DateUtil.CreateFromYMD (2022,  7, 15), 0.04750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  7, 25), DateUtil.CreateFromYMD (2023,  1, 15), 0.04950, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  1, 27), DateUtil.CreateFromYMD (2023,  2,  1), 0.09500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  6,  6), DateUtil.CreateFromYMD (2023,  6, 15), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  8,  8), DateUtil.CreateFromYMD (2023,  8,  8), 0.06500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  9, 24), DateUtil.CreateFromYMD (2023,  9, 15), 0.04700, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3,  3), DateUtil.CreateFromYMD (2024,  3,  3), 0.04000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  6, 30), DateUtil.CreateFromYMD (2024,  4,  1), 0.04850, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  4, 10), DateUtil.CreateFromYMD (2024,  4, 10), 0.04375, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3, 17), DateUtil.CreateFromYMD (2024,  4, 15), 0.04000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  9, 16), DateUtil.CreateFromYMD (2024,  9, 30), 0.04250, 2, "30/360 NON-EOM"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  3, 22), DateUtil.CreateFromYMD (2025,  1, 15), 0.04250, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  6, 27), DateUtil.CreateFromYMD (2025,  1, 15), 0.03900, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  4,  7), DateUtil.CreateFromYMD (2025,  4, 15), 0.03900, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1995,  6,  9), DateUtil.CreateFromYMD (2025,  6,  1), 0.06500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  7, 31), DateUtil.CreateFromYMD (2025,  8,  1), 0.04875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  6, 10), DateUtil.CreateFromYMD (2025,  6, 10), 0.04875, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 10, 20), DateUtil.CreateFromYMD (2025, 10, 15), 0.05000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  2,  3), DateUtil.CreateFromYMD (2026,  2, 15), 0.05250, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  4, 18), DateUtil.CreateFromYMD (2026,  4, 18), 0.04800, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  9, 28), DateUtil.CreateFromYMD (2027,  4,  1), 0.04300, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015, 12, 14), DateUtil.CreateFromYMD (2027, 12, 15), 0.04125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012, 12, 20), DateUtil.CreateFromYMD (2027, 12, 20), 0.05500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2001,  8, 27), DateUtil.CreateFromYMD (2031,  5,  1), 0.07500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  5, 27), DateUtil.CreateFromYMD (2035,  8, 15), 0.04400, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  7, 15), DateUtil.CreateFromYMD (2036,  7, 15), 0.03850, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1997,  9, 26), DateUtil.CreateFromYMD (2037,  7, 15), 0.07750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  8, 13), DateUtil.CreateFromYMD (2038,  1,  1), 0.07000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  7, 20), DateUtil.CreateFromYMD (2038,  3, 31), 0.04125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  8, 11), DateUtil.CreateFromYMD (2048,  2, 15), 0.05383, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  4, 26), DateUtil.CreateFromYMD (2050,  6, 15), 0.05624, 2, "ISMA-30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005,  8,  1), DateUtil.CreateFromYMD (2050,  8,  1), 0.05240, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2005, 10,  3), DateUtil.CreateFromYMD (2050, 12,  1), 0.05433, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2006,  2,  1), DateUtil.CreateFromYMD (2051,  1,  1), 0.05581, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2008, 10, 30), DateUtil.CreateFromYMD (2055, 11, 15), 0.07050, 2, "30/360"),
		};

		double[] adblCleanPrice = new double[] {
			1.0113840,	// (2017,  5,  1)
			0.9993076,	// (2017,  5, 15)
			1.0067930,	// (2018,  3, 12)
			1.1078490,	// (2019,  8, 15)
			1.1112680,	// (2019,  8, 15)
			1.0381250,	// (2019, 10, 15)
			1.1373470,	// (2019, 12, 15)
			1.0797090,	// (2020,  1, 15)
			1.1183630,	// (2020,  3, 15)
			0.9725000,	// (2020,  6, 30)
			1.0785830,	// (2020,  7, 15)
			1.0954580,	// (2020,  9, 15)
			1.0852840,	// (2020,  9, 15)
			1.0777550,	// (2020,  9, 30)
			1.0099750,	// (2020, 10,  1)
			1.0354710,	// (2021,  1, 21)
			1.1336520,	// (2021,  7, 11)
			1.0442470,	// (2021,  9, 11)
			1.0970000,	// (2021, 10, 15)
			1.0584550,	// (2022,  4,  1)
			1.0238990,	// (2022,  4,  1)
			1.0406760,	// (2022,  4, 15)
			1.0292000,	// (2022,  5, 15)
			1.0713740,	// (2022,  7, 15)
			1.0600000,	// (2023,  1, 15)
			1.1425000,	// (2023,  2,  1)
			1.0191830,	// (2023,  6, 15)
			1.0720350,	// (2023,  8,  8)
			1.0707080,	// (2023,  9, 15)
			1.0346820,	// (2024,  3,  3)
			1.0079400,	// (2024,  4,  1)
			0.9978986,	// (2024,  4, 10)
			1.0220860,	// (2024,  4, 15)
			1.0494240,	// (2024,  9, 30)
			0.9090727,	// (2025,  1, 15)
			0.9961395,	// (2025,  1, 15)
			0.9958941,	// (2025,  4, 15)
			1.1920290,	// (2025,  6,  1)
			1.0174550,	// (2025,  6, 10)
			1.0447330,	// (2025,  8, 15)
			1.0910300,	// (2025, 10, 15)
			1.0274640,	// (2026,  2, 15)
			1.0239890,	// (2026,  4, 18)
			0.9950930,	// (2027,  4,  1)
			1.0191590,	// (2027, 12, 15)
			1.0496860,	// (2027, 12, 20)
			1.2709190,	// (2031,  5,  1)
			1.0362270,	// (2035,  8, 15)
			0.9790604,	// (2036,  7, 15)
			1.1324350,	// (2037,  7, 15)
			1.2309690,	// (2038,  1,  1)
			0.9835252,	// (2038,  3, 31)
			0.9723881,	// (2048,  2, 15)
			1.0940080,	// (2050,  6, 15)
			1.0242040,	// (2050,  8,  1)
			0.9588270,	// (2050, 12,  1)
			0.8015440,	// (2051,  1,  1)
			1.1538110,	// (2055, 11, 15)
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
