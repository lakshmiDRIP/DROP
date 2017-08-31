
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
 * BulletAgency demonstrates Non-EOS Fixed Coupon Agency Bond Pricing and Relative Value Measure Generation
 *  Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BulletAgency {

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

	private static final Bond Agency (
		final JulianDate dtEffective,
		final JulianDate dtMaturity,
		final double dblCoupon)
		throws Exception
	{
		return BondBuilder.CreateSimpleFixed (
			"AGENCY " + FormatUtil.FormatDouble (dblCoupon, 1, 4, 100.) + " " + dtMaturity,
			"USD",
			"",
			dblCoupon,
			2,
			"30/360",
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

		Bond[] aAgencyBond = new Bond[] {
			Agency (DateUtil.CreateFromYMD (2016,  8, 30), DateUtil.CreateFromYMD (2028,  8, 28), 0.01000),
			Agency (DateUtil.CreateFromYMD (2012, 10, 30), DateUtil.CreateFromYMD (2020,  4, 30), 0.01500),
			Agency (DateUtil.CreateFromYMD (2016,  1, 12), DateUtil.CreateFromYMD (2026,  1, 12), 0.03020),
			Agency (DateUtil.CreateFromYMD (2016,  7, 12), DateUtil.CreateFromYMD (2027,  7, 12), 0.02500),
			Agency (DateUtil.CreateFromYMD (2013,  4, 10), DateUtil.CreateFromYMD (2028,  4, 10), 0.04000),
			Agency (DateUtil.CreateFromYMD (2013, 10,  4), DateUtil.CreateFromYMD (2028,  9,  1), 0.04000),
			Agency (DateUtil.CreateFromYMD (2013, 12,  8), DateUtil.CreateFromYMD (2028, 12,  8), 0.04000),
			Agency (DateUtil.CreateFromYMD (2014,  4, 11), DateUtil.CreateFromYMD (2029,  4, 11), 0.04000),
			Agency (DateUtil.CreateFromYMD (2014,  6,  4), DateUtil.CreateFromYMD (2029,  6,  8), 0.03375),
			Agency (DateUtil.CreateFromYMD (2015,  5, 28), DateUtil.CreateFromYMD (2029,  8,  1), 0.03350),
			Agency (DateUtil.CreateFromYMD (2013,  4, 24), DateUtil.CreateFromYMD (2029, 10, 24), 0.04000),
			Agency (DateUtil.CreateFromYMD (2016,  2,  8), DateUtil.CreateFromYMD (2030,  2,  8), 0.03280),
			Agency (DateUtil.CreateFromYMD (2016,  9, 29), DateUtil.CreateFromYMD (2030,  3, 29), 0.02990),
			Agency (DateUtil.CreateFromYMD (2015,  8,  1), DateUtil.CreateFromYMD (2030,  8,  1), 0.03500),
			Agency (DateUtil.CreateFromYMD (2012, 10, 24), DateUtil.CreateFromYMD (2030, 10, 24), 0.03050),
			Agency (DateUtil.CreateFromYMD (2015, 11, 27), DateUtil.CreateFromYMD (2030, 11, 27), 0.03300),
			Agency (DateUtil.CreateFromYMD (2015, 12, 23), DateUtil.CreateFromYMD (2030, 12, 23), 0.03530),
			Agency (DateUtil.CreateFromYMD (2016,  1, 14), DateUtil.CreateFromYMD (2031,  1, 14), 0.03340),
			Agency (DateUtil.CreateFromYMD (2016,  1, 27), DateUtil.CreateFromYMD (2031,  1, 27), 0.03390),
			Agency (DateUtil.CreateFromYMD (2016,  2, 11), DateUtil.CreateFromYMD (2031,  2, 11), 0.03040),
			Agency (DateUtil.CreateFromYMD (2016,  3, 10), DateUtil.CreateFromYMD (2031,  3, 10), 0.03100),
			Agency (DateUtil.CreateFromYMD (2016,  7, 11), DateUtil.CreateFromYMD (2031,  7, 11), 0.02750),
			Agency (DateUtil.CreateFromYMD (2015,  5,  8), DateUtil.CreateFromYMD (2031,  8,  1), 0.03550),
			Agency (DateUtil.CreateFromYMD (2016,  8,  8), DateUtil.CreateFromYMD (2031,  8,  8), 0.02700),
			Agency (DateUtil.CreateFromYMD (2015,  6, 28), DateUtil.CreateFromYMD (2032,  6, 28), 0.02900),
			Agency (DateUtil.CreateFromYMD (2015,  7, 20), DateUtil.CreateFromYMD (2032,  7, 20), 0.03500),
			Agency (DateUtil.CreateFromYMD (2016,  2,  1), DateUtil.CreateFromYMD (2033,  2,  1), 0.03330),
			Agency (DateUtil.CreateFromYMD (2016,  4, 21), DateUtil.CreateFromYMD (2033,  4, 21), 0.03000),
			Agency (DateUtil.CreateFromYMD (2013, 10, 11), DateUtil.CreateFromYMD (2033, 10, 11), 0.04125),
			Agency (DateUtil.CreateFromYMD (2014,  7, 24), DateUtil.CreateFromYMD (2034,  7, 24), 0.03990),
			Agency (DateUtil.CreateFromYMD (2014,  9,  5), DateUtil.CreateFromYMD (2034,  9,  5), 0.03940),
			Agency (DateUtil.CreateFromYMD (2016,  2,  2), DateUtil.CreateFromYMD (2035,  2,  2), 0.03300),
			Agency (DateUtil.CreateFromYMD (2015,  6,  1), DateUtil.CreateFromYMD (2035,  6,  1), 0.03400),
			Agency (DateUtil.CreateFromYMD (2015, 10, 22), DateUtil.CreateFromYMD (2035, 10, 22), 0.03625),
			Agency (DateUtil.CreateFromYMD (2015, 11, 12), DateUtil.CreateFromYMD (2035, 11, 13), 0.03315),
			Agency (DateUtil.CreateFromYMD (2015, 12, 28), DateUtil.CreateFromYMD (2035, 12, 28), 0.03670),
			Agency (DateUtil.CreateFromYMD (2016,  2,  1), DateUtil.CreateFromYMD (2036,  2,  1), 0.03560),
			Agency (DateUtil.CreateFromYMD (2016,  2,  6), DateUtil.CreateFromYMD (2036,  2,  6), 0.03130),
			Agency (DateUtil.CreateFromYMD (2016,  2,  8), DateUtil.CreateFromYMD (2036,  2,  8), 0.03500),
			Agency (DateUtil.CreateFromYMD (2016,  2, 22), DateUtil.CreateFromYMD (2036,  2, 22), 0.03440),
			Agency (DateUtil.CreateFromYMD (2016,  4, 18), DateUtil.CreateFromYMD (2036,  4, 18), 0.03140),
			Agency (DateUtil.CreateFromYMD (2016,  4, 29), DateUtil.CreateFromYMD (2036,  4, 29), 0.03000),
			Agency (DateUtil.CreateFromYMD (2016,  5, 16), DateUtil.CreateFromYMD (2036,  5, 16), 0.03000),
			Agency (DateUtil.CreateFromYMD (2016,  6,  9), DateUtil.CreateFromYMD (2036,  5, 20), 0.03040),
			Agency (DateUtil.CreateFromYMD (2016,  5, 23), DateUtil.CreateFromYMD (2036,  5, 23), 0.03030),
			Agency (DateUtil.CreateFromYMD (2006,  6, 16), DateUtil.CreateFromYMD (2036,  6, 16), 0.03000),
			Agency (DateUtil.CreateFromYMD (2006,  7, 17), DateUtil.CreateFromYMD (2036,  7, 15), 0.05500),
			Agency (DateUtil.CreateFromYMD (2016,  8, 22), DateUtil.CreateFromYMD (2036,  8, 22), 0.02700),
			Agency (DateUtil.CreateFromYMD (2016,  9,  8), DateUtil.CreateFromYMD (2036,  9,  8), 0.02750),
			Agency (DateUtil.CreateFromYMD (2016,  9,  8), DateUtil.CreateFromYMD (2036,  9,  8), 0.02700),
			Agency (DateUtil.CreateFromYMD (2007,  7, 18), DateUtil.CreateFromYMD (2037,  7, 15), 0.05625),
			Agency (DateUtil.CreateFromYMD (2015,  7,  6), DateUtil.CreateFromYMD (2037,  8,  6), 0.03950),
			Agency (DateUtil.CreateFromYMD (2016, 11,  2), DateUtil.CreateFromYMD (2037, 11,  2), 0.02780),
			Agency (DateUtil.CreateFromYMD (1998,  8,  6), DateUtil.CreateFromYMD (2038,  8,  6), 0.06210),
			Agency (DateUtil.CreateFromYMD (2016,  7, 26), DateUtil.CreateFromYMD (2038,  8, 26), 0.02710),
			Agency (DateUtil.CreateFromYMD (2016,  2,  2), DateUtil.CreateFromYMD (2039,  2,  2), 0.03500),
			Agency (DateUtil.CreateFromYMD (2009,  9, 22), DateUtil.CreateFromYMD (2039,  9, 15), 0.05250),
			Agency (DateUtil.CreateFromYMD (2016,  2,  2), DateUtil.CreateFromYMD (2041,  2,  2), 0.03650),
			Agency (DateUtil.CreateFromYMD (2015,  4,  6), DateUtil.CreateFromYMD (2045,  4,  6), 0.03430),
			Agency (DateUtil.CreateFromYMD (2006,  4,  3), DateUtil.CreateFromYMD (2056,  4,  1), 0.05375),
			Agency (DateUtil.CreateFromYMD (2015,  9, 24), DateUtil.CreateFromYMD (2065,  9, 24), 0.04250),
		};

		double[] adblCleanPrice = new double[] {
			0.9931592,	// (2018,  8, 28)
			0.9934814,	// (2020,  4, 30)
			0.9904951,	// (2026,  1, 12)
			0.9479743,	// (2027,  7, 12)
			1.0966930,	// (2028,  4, 10)
			1.0935950,	// (2028,  9,  1)
			1.0967200,	// (2028, 12,  8)
			1.0065050,	// (2029,  4, 11)
			1.0304410,	// (2029,  6,  8)
			1.0110210,	// (2029,  8,  1)
			1.0958790,	// (2029, 10, 24)
			0.9880349,	// (2030,  2,  8)
			0.9667400,	// (2030,  3, 29)
			1.0189310,	// (2030,  8,  1)
			0.9860338,	// (2030, 10, 24)
			0.9759851,	// (2030, 11, 27)
			0.9871626,	// (2030, 12, 23)
			0.9770129,	// (2031,  1, 14)
			0.9942566,	// (2031,  1, 27)
			0.9536498,	// (2031,  2, 11)
			0.9730267,	// (2031,  3, 10)
			0.9399774,	// (2031,  7, 11)
			1.0172150,	// (2031,  8,  1)
			0.9329716,	// (2031,  8,  8)
			0.9317174,	// (2032,  6, 28)
			1.0240540,	// (2032,  7, 20)
			0.9658405,	// (2033,  2,  1)
			0.9424355,	// (2033,  4, 21)
			1.0197810,	// (2033, 10, 11)
			0.9988397,	// (2034,  7, 24)
			0.9970544,	// (2034,  9,  5)
			0.9770791,	// (2035,  2,  2)
			1.0207810,	// (2035,  6,  1)
			0.9765588,	// (2035, 10, 22)
			0.9995269,	// (2035, 11, 13)
			0.9889580,	// (2035, 12, 28)
			0.9820512,	// (2036,  2,  1)
			0.9267384,	// (2036,  2,  6)
			0.9785613,	// (2036,  2,  8)
			0.9731897,	// (2036,  2, 22)
			0.9285945,	// (2036,  4, 18)
			0.9132757,	// (2036,  4, 29)
			0.9125336,	// (2036,  5, 16)
			0.9385671,	// (2036,  5, 20)
			0.9347221,	// (2036,  5, 23)
			0.9343378,	// (2036,  6, 16)
			1.3270050,	// (2036,  7, 15)
			0.8896572,	// (2036,  8, 22)
			0.8947115,	// (2036,  9,  8)
			0.8985469,	// (2036,  9,  8)
			1.3428920,	// (2037,  7, 15)
			1.0576060,	// (2037,  8,  6)
			0.8892567,	// (2037, 11,  2)
			1.4447600,	// (2038,  8,  6)
			0.8611548,	// (2038,  8, 26)
			0.9764945,	// (2039,  2,  2)
			1.2595240,	// (2039,  9, 15)
			0.9811483,	// (2041,  2,  2)
			0.9423921,	// (2045,  4,  6)
			1.2715880,	// (2056,  4,  1)
			1.0513800,	// (2065,  9, 15)
		};

		double[] adblOAS = RVMeasures (
			aAgencyBond,
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
			System.out.print ("\t| " + aAgencyBond[i].name());

			for (Map.Entry<String, GovvieCurve> meGovvieCurve : mapGovvieCurve.entrySet()) {
				if ("BASE".equalsIgnoreCase (meGovvieCurve.getKey()) || "BUMP".equalsIgnoreCase (meGovvieCurve.getKey()))
					continue;

				csqc.setGovvieState (meGovvieCurve.getValue());

				System.out.print (" |      " +
					FormatUtil.FormatDouble (
						(adblCleanPrice[i] - aAgencyBond[i].priceFromOAS (
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
