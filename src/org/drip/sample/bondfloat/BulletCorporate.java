
package org.drip.sample.bondfloat;

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
 * BulletCorporate demonstrates Non-EOS Floating Coupon Corporate Bond Pricing and Relative Value Measure
 *  Generation Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BulletCorporate {

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
		final String strRateIndex,
		final JulianDate dtEffective,
		final JulianDate dtMaturity,
		final double dblSpread)
		throws Exception
	{
		return BondBuilder.CreateSimpleFloater (
			strRateIndex + " +" + FormatUtil.FormatDouble (dblSpread, 3, 0, 10000.) + " bp " + dtMaturity,
			"USD",
			strRateIndex,
			"",
			dblSpread,
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
			// System.out.println ("Doing " + aBond[i].name());

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
				FormatUtil.FormatDouble (wi.yield(), 1, 2, 100.) + "% |" +
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

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|             BOND            |  EFFECTIVE  |   MATURITY  |  FIRST COUPON |  PRICE  | YIELD | MAC DUR | MOD DUR | YIELD 01 | DV01 | CONV | BOND BASIS ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.print (strSecularMetrics);

		System.out.println ("\t|-----------------------------------------------------------------------------------------------------------------------------------------------------||\n");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|             BOND            |  PRICE  | YIELD | OAS | OAS DUR |  OAS CONV | ASW | G SPREAD | I SPREAD | TSY SPREAD | TSY BMK ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		System.out.print (strCurveMetrics);

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

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

		double dblUSD3MLIBOR = dcFunding.libor (
			dtSpot,
			"3M"
		);

		Bond[] aAgencyBond = new Bond[] {
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2014,  3, 24), DateUtil.CreateFromYMD (2017,  3, 24), 0.0178706 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2015,  8,  3), DateUtil.CreateFromYMD (2017,  8,  3), 0.0150904 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  6,  9), DateUtil.CreateFromYMD (2017, 12,  8), 0.0160083 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2013,  4, 30), DateUtil.CreateFromYMD (2018,  4, 30), 0.0208733 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016, 10, 19), DateUtil.CreateFromYMD (2018, 10, 19), 0.0169483 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2015, 12, 14), DateUtil.CreateFromYMD (2018, 12, 14), 0.0179872 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  1, 14), DateUtil.CreateFromYMD (2019,  1, 14), 0.0180317 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  1, 15), DateUtil.CreateFromYMD (2019,  1, 15), 0.0220317 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  1, 22), DateUtil.CreateFromYMD (2019,  1, 22), 0.0204122 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  1, 27), DateUtil.CreateFromYMD (2019,  2,  1), 0.0226094 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  2, 23), DateUtil.CreateFromYMD (2019,  2, 22), 0.0173983 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  3,  4), DateUtil.CreateFromYMD (2019,  3, 14), 0.0243872 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  3, 15), DateUtil.CreateFromYMD (2019,  3, 15), 0.0165344 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  5, 13), DateUtil.CreateFromYMD (2019,  5, 13), 0.0161206 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  6,  2), DateUtil.CreateFromYMD (2019,  5, 24), 0.0153011 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  6, 14), DateUtil.CreateFromYMD (2019,  6, 14), 0.0161872 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  9,  8), DateUtil.CreateFromYMD (2019,  9,  6), 0.0152639 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016,  9, 30), DateUtil.CreateFromYMD (2019,  9, 30), 0.0161817 - dblUSD3MLIBOR),
			Corporate ("USD-3M", DateUtil.CreateFromYMD (2016, 10, 18), DateUtil.CreateFromYMD (2019, 10, 18), 0.0193372 - dblUSD3MLIBOR),
		};

		double[] adblCleanPrice = new double[] {
			1.0006750,	// (2017,  3, 24)
			1.0027220,	// (2017,  8,  3)
			1.0050000,	// (2017, 12,  8)
			1.0091000,	// (2018,  4, 30)
			1.0016000,	// (2018, 10, 19)
			1.0053430,	// (2018, 12, 14)
			1.0051600,	// (2019,  1, 14)
			1.0075900,	// (2019,  1, 15)
			1.0085700,	// (2019,  1, 22)
			1.0174100,	// (2019,  2,  1)
			1.0144650,	// (2019,  2, 22)
			1.0152950,	// (2019,  3, 14)
			1.0106700,	// (2019,  3, 15)
			1.0045400,	// (2019,  5, 13)
			1.0025140,	// (2019,  5, 24)
			1.0027120,	// (2019,  6, 14)
			1.0012000,	// (2019,  9,  6)
			1.0022590,	// (2019,  9, 30)
			1.0003480,	// (2019, 10, 18)
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

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.print ("\t|             BOND           ");

		for (Map.Entry<String, GovvieCurve> meGovvieCurve : mapGovvieCurve.entrySet()) {
			if ("BASE".equalsIgnoreCase (meGovvieCurve.getKey()) || "BUMP".equalsIgnoreCase (meGovvieCurve.getKey()))
				continue;

			System.out.print (" | " + meGovvieCurve.getKey());
		}

		System.out.println (" ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

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

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
