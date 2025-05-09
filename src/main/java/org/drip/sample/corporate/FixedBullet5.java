
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
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>FixedBullet5</i> demonstrates Non-EOS Fixed Coupon Agency Bond Pricing and Relative Value Measure
 * Generation Functionality.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/corporate/README.md">Corporate Bond Relative Value Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FixedBullet5 {

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
						mapGovvieCurve.get ("BASE").yld (aComp[i].maturityDate().julian())
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

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

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
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  5, 22), DateUtil.CreateFromYMD (2017,  5, 15), 0.01150, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013, 11, 14), DateUtil.CreateFromYMD (2018,  3,  1), 0.05150, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  1, 30), DateUtil.CreateFromYMD (2019,  2,  1), 0.07125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  4,  7), DateUtil.CreateFromYMD (2019,  4, 15), 0.09000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2009,  5, 14), DateUtil.CreateFromYMD (2020,  2, 15), 0.06850, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  3, 19), DateUtil.CreateFromYMD (2020,  3, 19), 0.06750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010,  7, 21), DateUtil.CreateFromYMD (2020,  7, 21), 0.05000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2011,  7, 11), DateUtil.CreateFromYMD (2021,  7, 11), 0.06125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2012,  3, 12), DateUtil.CreateFromYMD (2022,  3, 15), 0.04700, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  8, 13), DateUtil.CreateFromYMD (2023,  3, 15), 0.04375, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2013,  8, 15), DateUtil.CreateFromYMD (2023,  8, 15), 0.06125, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3,  3), DateUtil.CreateFromYMD (2024,  3,  3), 0.04000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  3, 13), DateUtil.CreateFromYMD (2024,  3, 13), 0.04000, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  6, 10), DateUtil.CreateFromYMD (2024,  4,  1), 0.04850, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014,  4,  8), DateUtil.CreateFromYMD (2024,  4,  8), 0.03800, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1994,  5, 11), DateUtil.CreateFromYMD (2024,  5,  1), 0.08625, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 11, 10), DateUtil.CreateFromYMD (2024, 11, 15), 0.04600, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 11, 17), DateUtil.CreateFromYMD (2024, 11, 15), 0.04500, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2015,  9, 15), DateUtil.CreateFromYMD (2025,  9, 15), 0.04750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2016,  3,  4), DateUtil.CreateFromYMD (2026,  3,  4), 0.04150, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2014, 11, 25), DateUtil.CreateFromYMD (2026, 11, 25), 0.04270, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1998,  7, 10), DateUtil.CreateFromYMD (2028,  7, 15), 0.07050, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2003, 10,  1), DateUtil.CreateFromYMD (2033, 10,  1), 0.07450, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (1997,  9, 26), DateUtil.CreateFromYMD (2037,  7, 15), 0.07750, 2, "30/360"),
			Corporate ("CORPORA", DateUtil.CreateFromYMD (2010, 10, 15), DateUtil.CreateFromYMD (2040, 10, 15), 0.06950, 2, "30/360"),
		};

		double[] adblCleanPrice = new double[] {
			1.0002500,	// (2017,  5, 15)
			1.0275720,	// (2018,  3,  1)
			1.0905560,	// (2019,  2,  1)
			1.1277300,	// (2019,  4, 15)
			1.1121930,	// (2020,  2, 15)
			1.1192150,	// (2020,  3, 19)
			1.0804500,	// (2020,  7, 21)
			1.1260800,	// (2021,  7, 11)
			1.0593400,	// (2022,  3, 15)
			0.9909100,	// (2023,  3, 15)
			1.0375000,	// (2023,  8, 15)
			1.0274900,	// (2024,  3,  3)
			1.0331100,	// (2024,  3, 13)
			1.0016400,	// (2024,  4,  1)
			0.9918850,	// (2024,  4,  8)
			1.2035600,	// (2024,  5,  1)
			0.9959750,	// (2024, 11, 15)
			0.9817420,	// (2024, 11, 15)
			0.9831200,	// (2025,  9, 15)
			1.0359300,	// (2026,  3,  4)
			0.9996523,	// (2026, 11, 25)
			1.2003340,	// (2028,  7, 15)
			1.2054840,	// (2033, 10,  1)
			1.1373170,	// (2037,  7, 15)
			1.2248370,	// (2040, 10, 15)
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

		EnvManager.TerminateEnv();
	}
}
