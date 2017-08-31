
package org.drip.sample.corporate;

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
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
 * NonFixedBullet demonstrates Non-EOS Non-Fixed Coupon (Floater, Variable) Corporate Bond Pricing and
 *  Relative Value Measure Generation Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NonFixedBullet {

	private static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate dtSpot,
		final String strCurrency,
		final double dblBump)
		throws Exception
	{
		String[] astrDepositMaturityTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0111956 + dblBump // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.011375 + dblBump,	// 98.8625
			0.013350 + dblBump,	// 98.6650
			0.014800 + dblBump,	// 98.5200
			0.016450 + dblBump,	// 98.3550
			0.017850 + dblBump,	// 98.2150
			0.019300 + dblBump	// 98.0700
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
			0.017029 + dblBump, //  2Y
			0.019354 + dblBump, //  3Y
			0.021044 + dblBump, //  4Y
			0.022291 + dblBump, //  5Y
			0.023240 + dblBump, //  6Y
			0.024025 + dblBump, //  7Y
			0.024683 + dblBump, //  8Y
			0.025243 + dblBump, //  9Y
			0.025720 + dblBump, // 10Y
			0.026130 + dblBump, // 11Y
			0.026495 + dblBump, // 12Y
			0.027230 + dblBump, // 15Y
			0.027855 + dblBump, // 20Y
			0.028025 + dblBump, // 25Y
			0.028028 + dblBump, // 30Y
			0.027902 + dblBump, // 40Y
			0.027655 + dblBump  // 50Y
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

	private static final GovvieCurve GovvieCurve (
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

		GovvieCurve gc = LatentMarketStateBuilder.GovvieCurve (
			strCode,
			dtSpot,
			adtEffective,
			adtMaturity,
			adblCoupon,
			adblYield,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);

		BondComponent[] aComp = TreasuryBuilder.FromCode (
			strCode,
			adtEffective,
			adtMaturity,
			adblCoupon
		);

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setGovvieState (gc);

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
						gc.yield (aComp[i].maturityDate().julian())
					)
				), 1, 3, 100.) + "% ||"
			);

		System.out.println ("\t|-------------------------------------------||");

		return gc;
	}

	private static final BondComponent Corporate (
		final String strCUSIP,
		final String strRateIndex,
		final JulianDate dtEffective,
		final JulianDate dtMaturity,
		final double dblFloatSpread)
		throws Exception
	{
		return BondBuilder.CreateSimpleFloater (
			strCUSIP,
			"USD",
			strRateIndex,
			"",
			dblFloatSpread,
			4,
			"Act/360",
			dtEffective,
			dtMaturity,
			null,
			null
		);
	}

	private static final void RVMeasures (
		final BondComponent[] aBond,
		final JulianDate dtValue,
		final MergedDiscountForwardCurve dcBase,
		final MergedDiscountForwardCurve dcBump,
		final GovvieCurve gc,
		final double[] adblCleanPrice,
		final double[] adblFullFirstCoupon)
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

		String strSecularMetrics = "";

		for (int i = 0; i < aBond.length; ++i) {
			CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Create (
				dcBase,
				gc,
				null,
				null,
				null,
				null,
				null
			);

			CurveSurfaceQuoteContainer csqcBump = MarketParamsBuilder.Create (
				dcBump,
				null,
				null,
				null,
				null,
				null,
				null
			);

			ComposableUnitPeriod cupFirst = aBond[i].stream().containingPeriod (dtValue.julian()).periods().get (0);

			csqc.setFixing (
				((ComposableUnitFloatingPeriod) cupFirst).referenceIndexPeriod().fixingDate(),
				aBond[i].floaterSetting().fri(),
				adblFullFirstCoupon[i] - aBond[i].floatSpread()
			);

			csqcBump.setFixing (
				((ComposableUnitFloatingPeriod) cupFirst).referenceIndexPeriod().fixingDate(),
				aBond[i].floaterSetting().fri(),
				adblFullFirstCoupon[i] - aBond[i].floatSpread() + 0.0001
			);

			double dblAccrued = aBond[i].accrued (
				dtSettle.julian(),
				csqc
			);

			WorkoutInfo wi = aBond[i].exerciseYieldFromPrice (
				valParams,
				csqc,
				null,
				adblCleanPrice[i]
			);

			double dblYTM = aBond[i].yieldFromPrice (
				valParams,
				csqc,
				null,
				aBond[i].maturityDate().julian(),
				1.,
				adblCleanPrice[i]
			);

			double dblYTMBondEquivalent = aBond[i].yieldFromPrice (
				valParams,
				csqc,
				ValuationCustomizationParams.BondEquivalent (aBond[i].currency()),
				aBond[i].maturityDate().julian(),
				1.,
				adblCleanPrice[i]
			);

			double dblWALTW = aBond[i].weightedAverageLife (
				valParams,
				csqc,
				wi.date(),
				wi.factor()
			);

			double dblWALTM = aBond[i].weightedAverageLife (
				valParams,
				csqc,
				aBond[i].maturityDate().julian(),
				1.
			);

			double dblDiscountMargin = aBond[i].discountMarginFromYield (
				valParams,
				csqc,
				null,
				wi.date(),
				wi.factor(),
				wi.yield()
			);

			double dblOAS = aBond[i].oasFromYield (
				valParams,
				csqc,
				null,
				wi.date(),
				wi.factor(),
				wi.yield()
			);

			double dblBasePrice = aBond[i].priceFromFundingCurve (
				valParams,
				csqc,
				wi.date(),
				wi.factor(),
				0.
			);

			double dblBumpPrice = aBond[i].priceFromFundingCurve (
				valParams,
				csqcBump,
				wi.date(),
				wi.factor(),
				0.
			);

			strSecularMetrics +=
				aBond[i].name() + "," +
				aBond[i].effectiveDate() + "," +
				aBond[i].maturityDate() + "," +
				aBond[i].floaterSetting().fri().fullyQualifiedName() + "," +
				FormatUtil.FormatDouble (aBond[i].floatSpread(), 3, 1, 10000.) + "," +
				aBond[i].firstCouponDate() + "," +
				FormatUtil.FormatDouble (adblCleanPrice[i], 3, 3, 100.) + "," +
				FormatUtil.FormatDouble (dblAccrued, 1, 4, 100.) + "," +
				FormatUtil.FormatDouble (wi.yield(), 1, 3, 100.) + "%," +
				FormatUtil.FormatDouble (dblYTM, 1, 3, 100.) + "%," +
				FormatUtil.FormatDouble (dblYTMBondEquivalent, 1, 3, 100.) + "%," +
				FormatUtil.FormatDouble (dblWALTW, 1, 3, 1.) + "," +
				FormatUtil.FormatDouble (dblWALTM, 1, 3, 1.) + "," +
				FormatUtil.FormatDouble (dblBasePrice - dblBumpPrice, 1, 4, 10000.) + "," +
				FormatUtil.FormatDouble (dblDiscountMargin, 1, 3, 10000.) + "," +
				FormatUtil.FormatDouble (dblOAS, 1, 3, 10000.) + "\n";
		}

		System.out.println
			("Bond, Issue, Maturity, Floater Index, Spread, First Coupon, Clean Price, Accrued, Yield TW, Yield TM, Bond Equivalent Yield TM, WAL TW, WAL TM, Duration TW, Discount Margin TW, OAS TW");

		System.out.print (strSecularMetrics);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.MARCH,
			10
		);

		String strCurrency = "USD";
		String strTreasuryCode = "UST";

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

		BondComponent[] aCorporateBond = new BondComponent[] {
			Corporate ("55608PAF1", "USD-3M", DateUtil.CreateFromYMD (2014,  3, 24), DateUtil.CreateFromYMD (2017,  3, 24), 0.00790),
			Corporate ("233851BX1", "USD-3M", DateUtil.CreateFromYMD (2015,  8,  3), DateUtil.CreateFromYMD (2017,  8,  3), 0.00710),
			Corporate ("00817YAR9", "USD-3M", DateUtil.CreateFromYMD (2016,  6,  9), DateUtil.CreateFromYMD (2017, 12,  8), 0.00650),
			Corporate ("38141GVK7", "USD-3M", DateUtil.CreateFromYMD (2013,  4, 30), DateUtil.CreateFromYMD (2018,  4, 30), 0.01200),
			Corporate ("865622CD4", "USD-3M", DateUtil.CreateFromYMD (2016, 10, 19), DateUtil.CreateFromYMD (2018, 10, 19), 0.00670),
			Corporate ("63307A2B0", "USD-3M", DateUtil.CreateFromYMD (2015, 12, 14), DateUtil.CreateFromYMD (2018, 12, 14), 0.00840),
			Corporate ("6325C0DE8", "USD-3M", DateUtil.CreateFromYMD (2016,  1, 14), DateUtil.CreateFromYMD (2019,  1, 14), 0.00780),
			Corporate ("55608PAU8", "USD-3M", DateUtil.CreateFromYMD (2016,  1, 15), DateUtil.CreateFromYMD (2019,  1, 15), 0.01180),
			Corporate ("61746BDY9", "USD-3M", DateUtil.CreateFromYMD (2016,  1, 27), DateUtil.CreateFromYMD (2019,  2,  1), 0.01375),
			Corporate ("80283LAL7", "USD-3M", DateUtil.CreateFromYMD (2016,  3, 14), DateUtil.CreateFromYMD (2019,  3, 14), 0.01480),
			Corporate ("961214CU5", "USD-3M", DateUtil.CreateFromYMD (2016,  5, 13), DateUtil.CreateFromYMD (2019,  5, 13), 0.00710),
			Corporate ("94988J5E3", "USD-3M", DateUtil.CreateFromYMD (2016,  6,  2), DateUtil.CreateFromYMD (2019,  5, 24), 0.00600),
			Corporate ("064159HU3", "USD-3M", DateUtil.CreateFromYMD (2016,  6, 14), DateUtil.CreateFromYMD (2019,  6, 14), 0.00660),
			Corporate ("23636AAG6", "USD-3M", DateUtil.CreateFromYMD (2016,  9,  8), DateUtil.CreateFromYMD (2019,  9,  6), 0.00580),
			Corporate ("65557CAU7", "USD-3M", DateUtil.CreateFromYMD (2016,  9, 30), DateUtil.CreateFromYMD (2019,  9, 30), 0.00620),
			Corporate ("86563VAF6", "USD-3M", DateUtil.CreateFromYMD (2016, 10, 18), DateUtil.CreateFromYMD (2019, 10, 18), 0.00910),
		};

		double[] adblCleanPrice = new double[] {
			1.0001950,	// (2017,  3, 24)
			1.0024600,	// (2017,  8,  3)
			1.0039670,	// (2017, 12,  8)
			1.0097600,	// (2018,  4, 30)
			1.0033100,	// (2018, 10, 19)
			1.0058650,	// (2018, 12, 14)
			1.0079700,	// (2019,  1, 14)
			1.0114550,	// (2019,  1, 15)
			1.0172800,	// (2019,  2,  1)
			1.0164400,	// (2019,  3, 14)
			1.0073900,	// (2019,  5, 13)
			1.0055400,	// (2019,  5, 24)
			1.0063760,	// (2019,  6, 14)
			1.0032590,	// (2019,  9,  6)
			1.0042840,	// (2019,  9, 30)
			1.0052800,	// (2019, 10, 18)
		};

		double[] adblFullFirstCoupon = new double[] {
			0.0178706,	// (2017,  3, 24)
			0.0174456,	// (2017,  8,  3)
			0.0175622,	// (2017, 12,  8)
			0.0223900,	// (2018,  4, 30)
			0.0168483,	// (2018, 10, 19)
			0.0196122,	// (2018, 12, 14)
			0.0180317,	// (2019,  1, 14)
			0.0220317,	// (2019,  1, 15)
			0.0240900,	// (2019,  2,  1)
			0.0260122,	// (2019,  3, 14)
			0.0174372,	// (2019,  5, 13)
			0.0165400,	// (2019,  5, 24)
			0.0178122,	// (2019,  6, 14)
			0.0168000,	// (2019,  9,  6)
			0.0161817,	// (2019,  9, 30)
			0.0193372,	// (2019, 10, 18)
		};

		RVMeasures (
			aCorporateBond,
			dtSpot,
			FundingCurve (
				dtSpot,
				strCurrency,
				0.
			),
			FundingCurve (
				dtSpot,
				strCurrency,
				0.0001
			),
			GovvieCurve (
				dtSpot,
				strTreasuryCode,
				adblTreasuryCoupon,
				adblTreasuryYield
			),
			adblCleanPrice,
			adblFullFirstCoupon
		);

		System.out.println();
	}
}
