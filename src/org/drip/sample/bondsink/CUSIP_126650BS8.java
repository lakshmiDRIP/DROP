
package org.drip.sample.bondsink;

import org.drip.analytics.date.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.product.definition.Component;
import org.drip.quant.common.*;
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
 * CUSIP_126650BS8 demonstrates Sink Fixed Coupon Multi-flavor Pricing and Relative Value Measure Generation
 *  for CUSIP 126650BS8.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CUSIP_126650BS8 {

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

	private static final void RVMeasures (
		final BondComponent bondBullet,
		final BondComponent bondSinker,
		final JulianDate dtValue,
		final CurveSurfaceQuoteContainer csqc,
		final double dblCleanPrice)
		throws Exception
	{
		JulianDate dtSettle = dtValue.addBusDays (
			3,
			bondBullet.currency()
		);

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtSettle,
			bondBullet.currency()
		);

		System.out.println();

		System.out.println ("\t|--------------------------------||");

		System.out.println ("\t| Trade Date       : " + dtValue + " ||");

		System.out.println ("\t| Cash Settle Date : " + dtSettle + " ||");

		System.out.println ("\t|--------------------------------||");

		System.out.println();

		double dblYTM = Double.NaN;
		double dblYTW = Double.NaN;
		double dblOASTW = Double.NaN;
		double dblWALTM = Double.NaN;
		double dblWALTW = Double.NaN;
		double dblZSpreadTW = Double.NaN;
		double dblModifiedDurationTW = Double.NaN;

		WorkoutInfo wi = bondBullet.exerciseYieldFromPrice (
			valParams,
			csqc,
			null,
			dblCleanPrice
		);

		try {
			dblYTW = wi.yield();

			dblYTM = bondBullet.yieldFromPrice (
				valParams,
				csqc,
				null,
				bondBullet.maturityDate().julian(),
				1.,
				dblCleanPrice
			);

			dblWALTW = bondSinker.weightedAverageLife (
				valParams,
				csqc,
				wi.date(),
				wi.factor()
			);

			dblWALTM = bondSinker.weightedAverageLife (
				valParams,
				csqc,
				bondSinker.maturityDate().julian(),
				1.
			);

			dblZSpreadTW = bondBullet.zSpreadFromYield (
				valParams,
				csqc,
				null,
				wi.date(),
				wi.factor(),
				wi.yield()
			);

			dblOASTW = bondBullet.oasFromYield (
				valParams,
				csqc,
				null,
				wi.date(),
				wi.factor(),
				wi.yield()
			);

			dblModifiedDurationTW = bondSinker.modifiedDurationFromPrice (
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

		System.out.println ("\t Bond Name                 => " + bondSinker.name());

		System.out.println ("\t Effective Date            => " + bondSinker.effectiveDate());

		System.out.println ("\t Maturity Date             => " + bondSinker.maturityDate());

		System.out.println ("\t Exercise Date             => " + new JulianDate (wi.date()));

		System.out.println ("\t Price                     => " + FormatUtil.FormatDouble (dblCleanPrice, 1, 5, 100.));

		System.out.println ("\t Bond Accrued              => " + FormatUtil.FormatDouble (bondBullet.accrued (dtValue.julian(), csqc), 1, 4, 100.));

		System.out.println ("\t Bond YTW                  => " + FormatUtil.FormatDouble (dblYTW, 1, 3, 100.) + "%");

		System.out.println ("\t Bond YTM                  => " + FormatUtil.FormatDouble (dblYTM, 1, 3, 100.) + "%");

		System.out.println ("\t Bond WAL TW               => " + FormatUtil.FormatDouble (dblWALTW, 1, 3, 1.));

		System.out.println ("\t Bond WAL TM               => " + FormatUtil.FormatDouble (dblWALTM, 1, 3, 1.));

		System.out.println ("\t Bond Modified Duration TW => " + FormatUtil.FormatDouble (dblModifiedDurationTW, 1, 4, 10000.));

		System.out.println ("\t Bond Z Spread TW          => " + FormatUtil.FormatDouble (dblZSpreadTW, 1, 1, 10000.));

		System.out.println ("\t Bond OAS TW               => " + FormatUtil.FormatDouble (dblOASTW, 1, 1, 10000.));
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

		JulianDate dtEffective = DateUtil.CreateFromYMD (2009, 12, 22);
		JulianDate dtMaturity  = DateUtil.CreateFromYMD (2032,  1, 10);
		double dblCoupon = 0.07507;
		double dblCleanPrice = 1.233069;
		int iFreq = 12;
		String strCUSIP = "126650BS8";
		String strDayCount = "30/360";
		String strDateFactor = "12/22/2009;1;2/10/2010;0.999034198;3/10/2010;0.997909758;4/10/2010;0.996672483;5/10/2010;0.995266021;6/10/2010;0.99379564;7/10/2010;0.992285536;8/10/2010;0.990700723;9/10/2010;0.989105996;10/10/2010;0.987501293;11/10/2010;0.985886551;12/10/2010;0.984261707;1/10/2011;0.982626699;2/10/2011;0.980981462;3/10/2011;0.979325933;4/10/2011;0.977660047;5/10/2011;0.97598374;6/10/2011;0.974296946;7/10/2011;0.9725996;8/10/2011;0.970891635;9/10/2011;0.969172986;10/10/2011;0.967443585;11/10/2011;0.965703365;12/10/2011;0.963952259;1/10/2012;0.962190198;2/10/2012;0.960417114;3/10/2012;0.958632938;4/10/2012;0.9568376;5/10/2012;0.955031031;6/10/2012;0.953213161;7/10/2012;0.951383918;8/10/2012;0.949543232;9/10/2012;0.94769103;10/10/2012;0.945827242;11/10/2012;0.943951794;12/10/2012;0.942064613;1/10/2013;0.940165627;2/10/2013;0.938254761;3/10/2013;0.936331941;4/10/2013;0.934397092;5/10/2013;0.932450139;6/10/2013;0.930491006;7/10/2013;0.928519617;8/10/2013;0.926535895;9/10/2013;0.924539764;10/10/2013;0.922531145;11/10/2013;0.920509961;12/10/2013;0.918476132;1/10/2014;0.91642958;2/10/2014;0.914370225;3/10/2014;0.912297987;4/10/2014;0.910212786;5/10/2014;0.90811454;6/10/2014;0.906003167;7/10/2014;0.903878587;8/10/2014;0.901740715;9/10/2014;0.899589469;10/10/2014;0.897424765;11/10/2014;0.895246519;12/10/2014;0.893054647;1/10/2015;0.890849062;2/10/2015;0.88862968;3/10/2015;0.886396414;4/10/2015;0.884149176;5/10/2015;0.881887881;6/10/2015;0.879612439;7/10/2015;0.877322762;8/10/2015;0.875018761;9/10/2015;0.872700347;10/10/2015;0.87036743;11/10/2015;0.868019918;12/10/2015;0.86565772;1/10/2016;0.863280745;2/10/2016;0.8608889;3/10/2016;0.858482092;4/10/2016;0.856060227;5/10/2016;0.853623212;6/10/2016;0.851170951;7/10/2016;0.848703349;8/10/2016;0.84622031;9/10/2016;0.843721738;10/10/2016;0.841207535;11/10/2016;0.838677603;12/10/2016;0.836131845;1/10/2017;0.833570161;2/10/2017;0.830992451;3/10/2017;0.828398616;4/10/2017;0.825788554;5/10/2017;0.823162164;6/10/2017;0.820519344;7/10/2017;0.81785999;8/10/2017;0.815184001;9/10/2017;0.81249127;10/10/2017;0.809781695;11/10/2017;0.807055169;12/10/2017;0.804311586;1/10/2018;0.801550839;2/10/2018;0.798772822;3/10/2018;0.795977426;4/10/2018;0.793164543;5/10/2018;0.790334062;6/10/2018;0.787485875;7/10/2018;0.78461987;8/10/2018;0.781735936;9/10/2018;0.77883396;10/10/2018;0.77591383;11/10/2018;0.772975432;12/10/2018;0.770018652;1/10/2019;0.767043374;2/10/2019;0.764049484;3/10/2019;0.761036865;4/10/2019;0.758005399;5/10/2019;0.754954969;6/10/2019;0.751885456;7/10/2019;0.74879674;8/10/2019;0.745688703;9/10/2019;0.742561221;10/10/2019;0.739414175;11/10/2019;0.736247441;12/10/2019;0.733060897;1/10/2020;0.729854418;2/10/2020;0.72662788;3/10/2020;0.723381158;4/10/2020;0.720114124;5/10/2020;0.716826652;6/10/2020;0.713518615;7/10/2020;0.710189883;8/10/2020;0.706840327;9/10/2020;0.703469816;10/10/2020;0.700078221;11/10/2020;0.696665408;12/10/2020;0.693231245;1/10/2021;0.689775599;2/10/2021;0.686298334;3/10/2021;0.682799317;4/10/2021;0.67927841;5/10/2021;0.675735477;6/10/2021;0.67217038;7/10/2021;0.66858298;8/10/2021;0.664973138;9/10/2021;0.661340714;10/10/2021;0.657685566;11/10/2021;0.654007551;12/10/2021;0.650306528;1/10/2022;0.646582352;2/10/2022;0.642834878;3/10/2022;0.63906396;4/10/2022;0.635269452;5/10/2022;0.631451206;6/10/2022;0.627609074;7/10/2022;0.623742906;8/10/2022;0.619852552;9/10/2022;0.615937861;10/10/2022;0.61199868;11/10/2022;0.608034856;12/10/2022;0.604046235;1/10/2023;0.600032662;2/10/2023;0.595993981;3/10/2023;0.591930035;4/10/2023;0.587840665;5/10/2023;0.583725712;6/10/2023;0.579585018;7/10/2023;0.575418419;8/10/2023;0.571225756;9/10/2023;0.567006863;10/10/2023;0.562761578;11/10/2023;0.558489735;12/10/2023;0.554191168;1/10/2024;0.54986571;2/10/2024;0.545513193;3/10/2024;0.541133447;4/10/2024;0.536726302;5/10/2024;0.532291587;6/10/2024;0.527829129;7/10/2024;0.523338754;8/10/2024;0.518820289;9/10/2024;0.514273556;10/10/2024;0.50969838;11/10/2024;0.505094583;12/10/2024;0.500461985;1/10/2025;0.495800406;2/10/2025;0.491109665;3/10/2025;0.48638958;4/10/2025;0.481639967;5/10/2025;0.47686064;6/10/2025;0.472051416;7/10/2025;0.467212105;8/10/2025;0.46234252;9/10/2025;0.457442473;10/10/2025;0.452511771;11/10/2025;0.447550224;12/10/2025;0.442557638;1/10/2026;0.437533819;2/10/2026;0.432478572;3/10/2026;0.4273917;4/10/2026;0.422273006;5/10/2026;0.41712229;6/10/2026;0.411939352;7/10/2026;0.40672399;8/10/2026;0.401476002;9/10/2026;0.396195184;10/10/2026;0.390881329;11/10/2026;0.385534232;12/10/2026;0.380153684;1/10/2027;0.374739477;2/10/2027;0.369291399;3/10/2027;0.363809239;4/10/2027;0.358292783;5/10/2027;0.352741818;6/10/2027;0.347156126;7/10/2027;0.341535492;8/10/2027;0.335879695;9/10/2027;0.330188517;10/10/2027;0.324461736;11/10/2027;0.318699129;12/10/2027;0.312900472;1/10/2028;0.307065539;2/10/2028;0.301194105;3/10/2028;0.295285939;4/10/2028;0.289340813;5/10/2028;0.283358496;6/10/2028;0.277338754;7/10/2028;0.271281353;8/10/2028;0.265186058;9/10/2028;0.259052633;10/10/2028;0.252880837;11/10/2028;0.246670432;12/10/2028;0.240421176;1/10/2029;0.234132825;2/10/2029;0.227805135;3/10/2029;0.221437861;4/10/2029;0.215030753;5/10/2029;0.208583564;6/10/2029;0.202096043;7/10/2029;0.195567936;8/10/2029;0.188998991;9/10/2029;0.182388952;10/10/2029;0.175737561;11/10/2029;0.169044561;12/10/2029;0.162309733;1/10/2030;0.156072928;2/10/2030;0.150025552;3/10/2030;0.143940344;4/10/2030;0.137817068;5/10/2030;0.131655486;6/10/2030;0.125455359;7/10/2030;0.119216444;8/10/2030;0.1129385;9/10/2030;0.106621281;10/10/2030;0.100264544;11/10/2030;0.09386804;12/10/2030;0.08743152;1/10/2031;0.080954734;2/10/2031;0.074437431;3/10/2031;0.067879357;4/10/2031;0.061280256;5/10/2031;0.054639873;6/10/2031;0.047957948;7/10/2031;0.041234222;8/10/2031;0.034468434;9/10/2031;0.027660321;10/10/2031;0.020809616;11/10/2031;0.013916055;12/10/2031;0.006979369";

		BondComponent bondBullet = BondBuilder.CreateSimpleFixed (
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

		BondComponent bondSinker = BondBuilder.CreateSimpleFixed (
			strCUSIP,
			strCurrency,
			"",
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			Array2D.FromDateFactorVertex (
				strDateFactor,
				dtMaturity.julian()
			),
			null
		);

		RVMeasures (
			bondBullet,
			bondSinker,
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
	}
}
