
package org.drip.sample.bondeos;

import org.drip.analytics.date.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.product.definition.*;
import org.drip.product.params.EmbeddedOptionSchedule;
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
 * <i>Qidong</i> demonstrates EOS Fixed Coupon Multi-flavor Pricing and Relative Value Measure Generation for
 * Qidong.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bondeos/README.md">EOS Bond Bullet/Exercise Measures</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Qidong {

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
						gc.yld (aComp[i].maturityDate().julian())
					)
				), 1, 3, 100.) + "% ||"
			);

		System.out.println ("\t|-------------------------------------------||");

		return gc;
	}

	private static final void RVMeasures (
		final BondComponent bond,
		final JulianDate dtValue,
		final CurveSurfaceQuoteContainer csqc,
		final double dblCleanPrice)
		throws Exception
	{
		JulianDate dtSettle = dtValue.addBusDays (
			3,
			bond.currency()
		);

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtSettle,
			bond.currency()
		);

		System.out.println();

		System.out.println ("\t|--------------------------------||");

		System.out.println ("\t| Trade Date       : " + dtValue + " ||");

		System.out.println ("\t| Cash Settle Date : " + dtSettle + " ||");

		System.out.println ("\t|--------------------------------||");

		System.out.println();

		double dblYTM = Double.NaN;
		double dblYTW = Double.NaN;
		double dblOASTM = Double.NaN;
		double dblOASTW = Double.NaN;
		double dblWALTM = Double.NaN;
		double dblWALTW = Double.NaN;
		double dblZSpreadTM = Double.NaN;
		double dblZSpreadTW = Double.NaN;
		double dblModifiedDurationTW = Double.NaN;

		WorkoutInfo wi = bond.exerciseYieldFromPrice (
			valParams,
			csqc,
			null,
			dblCleanPrice
		);

		try {
			dblYTW = wi.yield();

			dblYTM = bond.yieldFromPrice (
				valParams,
				csqc,
				null,
				bond.maturityDate().julian(),
				1.,
				dblCleanPrice
			);

			dblWALTW = bond.weightedAverageLife (
				valParams,
				csqc,
				wi.date(),
				wi.factor()
			);

			dblWALTM = bond.weightedAverageLife (
				valParams,
				csqc,
				bond.maturityDate().julian(),
				1.
			);

			dblZSpreadTW = bond.zSpreadFromYield (
				valParams,
				csqc,
				null,
				wi.date(),
				wi.factor(),
				wi.yield()
			);

			dblZSpreadTM = bond.zSpreadFromYield (
				valParams,
				csqc,
				null,
				bond.maturityDate().julian(),
				1.,
				wi.yield()
			);

			dblOASTM = bond.oasFromYield (
				valParams,
				csqc,
				null,
				bond.maturityDate().julian(),
				1.,
				wi.yield()
			);

			dblOASTW = bond.oasFromYield (
				valParams,
				csqc,
				null,
				wi.date(),
				wi.factor(),
				wi.yield()
			);

			dblModifiedDurationTW = bond.modifiedDurationFromPrice (
				valParams,
				csqc,
				null,
				wi.date(),
				wi.factor(),
				dblCleanPrice
			);
		} catch (Exception e) {
			// e.printStackTrace();
		}

		System.out.println ("\t Bond Name                 => " + bond.name());

		System.out.println ("\t Effective Date            => " + bond.effectiveDate());

		System.out.println ("\t Maturity Date             => " + bond.maturityDate());

		System.out.println ("\t Exercise Date             => " + new JulianDate (wi.date()));

		System.out.println ("\t Price                     => " + FormatUtil.FormatDouble (dblCleanPrice, 1, 5, 100.));

		System.out.println ("\t Bond Accrued              => " + FormatUtil.FormatDouble (bond.accrued (dtSettle.julian(), csqc), 1, 4, 100.));

		System.out.println ("\t Bond YTW                  => " + FormatUtil.FormatDouble (dblYTW, 1, 3, 100.) + "%");

		System.out.println ("\t Bond YTM                  => " + FormatUtil.FormatDouble (dblYTM, 1, 3, 100.) + "%");

		System.out.println ("\t Bond WAL TM               => " + FormatUtil.FormatDouble (dblWALTM, 1, 3, 1.));

		System.out.println ("\t Bond WAL TW               => " + FormatUtil.FormatDouble (dblWALTW, 1, 3, 1.));

		System.out.println ("\t Bond Modified Duration TW => " + FormatUtil.FormatDouble (dblModifiedDurationTW, 1, 4, 10000.));

		System.out.println ("\t Bond Z Spread TM          => " + FormatUtil.FormatDouble (dblZSpreadTM, 1, 1, 10000.));

		System.out.println ("\t Bond Z Spread TW          => " + FormatUtil.FormatDouble (dblZSpreadTW, 1, 1, 10000.));

		System.out.println ("\t Bond OAS TM               => " + FormatUtil.FormatDouble (dblOASTM, 1, 1, 10000.));

		System.out.println ("\t Bond OAS TW               => " + FormatUtil.FormatDouble (dblOASTW, 1, 1, 10000.));
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
		EnvManager.InitEnv (
			"",
			true
		);

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

		JulianDate dtEffective = DateUtil.CreateFromYMD (2010, 12, 15);
		JulianDate dtMaturity  = DateUtil.CreateFromYMD (2034,  7,  1);
		double dblCoupon = 0.07747;
		double dblCleanPrice = 1.14843;
		int iFreq = 2;
		String strCUSIP = "Qidong";
		String strDayCount = "30/360";
		int[] aiExerciseDate = new int[] {
			DateUtil.CreateFromYMD (2020,  7,  1).julian(),
			DateUtil.CreateFromYMD (2021,  7,  1).julian(),
			DateUtil.CreateFromYMD (2022,  7,  1).julian(),
			DateUtil.CreateFromYMD (2023,  7,  1).julian(),
			DateUtil.CreateFromYMD (2024,  7,  1).julian(),
			DateUtil.CreateFromYMD (2025,  7,  1).julian(),
			DateUtil.CreateFromYMD (2026,  7,  1).julian(),
			DateUtil.CreateFromYMD (2027,  7,  1).julian(),
			DateUtil.CreateFromYMD (2028,  7,  1).julian(),
			DateUtil.CreateFromYMD (2029,  7,  1).julian(),
			DateUtil.CreateFromYMD (2030,  7,  1).julian(),
			DateUtil.CreateFromYMD (2031,  7,  1).julian(),
			DateUtil.CreateFromYMD (2032,  7,  1).julian(),
			DateUtil.CreateFromYMD (2033,  7,  1).julian(),
		};
		double[] adblExercisePrice = new double[] {
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
		};

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			strCUSIP,
			strCurrency,
			"",
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			null,
			null
		);

		EmbeddedOptionSchedule eos = new EmbeddedOptionSchedule (
			aiExerciseDate,
			adblExercisePrice,
			false,
			30,
			false,
			Double.NaN,
			"",
			Double.NaN
		);

		bond.setEmbeddedCallSchedule (eos);

		RVMeasures (
			bond,
			dtSpot,
			MarketParamsBuilder.Create (
				FundingCurve (
					dtSpot,
					strCurrency,
					0.
				),
				GovvieCurve (
					dtSpot,
					strTreasuryCode,
					adblTreasuryCoupon,
					adblTreasuryYield
				),
				null,
				null,
				null,
				null,
				null
			),
			dblCleanPrice
		);

		EnvManager.TerminateEnv();
	}
}
