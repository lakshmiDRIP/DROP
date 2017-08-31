
package org.drip.sample.treasuryfutures;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.output.BondRVMeasures;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.quote.*;
import org.drip.param.valuation.*;
import org.drip.product.credit.*;
import org.drip.product.definition.*;
import org.drip.product.govvie.TreasuryFutures;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.creator.ScenarioRepoCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * USTULTRA demonstrates the Details behind the Implementation and the Pricing of the ULTRA LONG WN1 UST
 *  Futures Contract.
 *
 * @author Lakshmi Krishnamurthy
 */

public class USTULTRA {

	private static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] astrDepositMaturityTenor = new String[] {
			"2D",
			"1W",
			"1M",
			"2M",
			"3M"
		};

		double[] adblDepositQuote = new double[] {
			0.00195, // 2D
			0.00176, // 1W
			0.00301, // 1M
			0.00401, // 2M
			0.00492  // 3M
		};

		double[] adblFuturesQuote = new double[] {
			0.00609,
			0.00687
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
			0.00762, //  1Y
			0.01055, //  2Y
			0.01300, //  3Y
			0.01495, //  4Y
			0.01651, //  5Y
			0.01787, //  6Y
			0.01904, //  7Y
			0.02005, //  8Y
			0.02090, //  9Y
			0.02166, // 10Y
			0.02231, // 11Y
			0.02289, // 12Y
			0.02414, // 15Y
			0.02570, // 20Y
			0.02594, // 25Y
			0.02627, // 30Y
			0.02648, // 40Y
			0.02632  // 50Y
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

		System.out.println ("\n\n\t|------------------------------------||");

		System.out.println ("\t|       DEPOSIT INPUT vs. CALC       ||");

		System.out.println ("\t|------------------------------------||");

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

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\n\t|------------------------------------||");

		System.out.println ("\t|       FUTURES INPUT vs. CALC       ||");

		System.out.println ("\t|------------------------------------||");

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

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------|| ");

		System.out.println ("\t|         FIX-FLOAT INPUTS vs CALIB             ||");

		System.out.println ("\t|-----------------------------------------------|| ");

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

		System.out.println ("\t|-----------------------------------------------|| \n");

		return dcFunding;
	}

	private static final void OnTheRunQuote (
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

	private static final void FuturesQuote (
		final CurveSurfaceQuoteContainer csqc,
		final TreasuryFutures bf,
		final double dblFuturesPrice)
		throws Exception
	{
		ProductMultiMeasure pmmq = new ProductMultiMeasure();

		pmmq.addQuote (
			"Price",
			new MultiSided (
				"mid",
				dblFuturesPrice
			),
			true
		);

		csqc.setProductQuote (
			bf.name(),
			pmmq
		);
	}

	private static final void RepoCurves (
		final JulianDate dtSpot,
		final CurveSurfaceQuoteContainer csqc,
		final Bond[] aBond,
		final double[] adblRepoRate)
		throws Exception
	{
		for (int i = 0; i < aBond.length; ++i)
			csqc.setRepoState (
				ScenarioRepoCurveBuilder.FlatRateRepoCurve (
					dtSpot,
					aBond[i],
					adblRepoRate[i]
				)
			);
	}

	private static final GovvieCurve TreasuryCurve (
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
			dtSpot
		};

		JulianDate[] adtMaturity = new JulianDate[] {
			dtSpot.addTenor ("1Y"),
			dtSpot.addTenor ("2Y"),
			dtSpot.addTenor ("3Y"),
			dtSpot.addTenor ("5Y"),
			dtSpot.addTenor ("7Y"),
			dtSpot.addTenor ("10Y"),
			dtSpot.addTenor ("30Y")
		};

		GovvieCurve gc = LatentMarketStateBuilder.ShapePreservingGovvieCurve (
			strCode,
			dtSpot,
			adtEffective,
			adtMaturity,
			adblCoupon,
			adblYield,
			"Yield"
		);

		BondComponent[] aComp = TreasuryBuilder.FromCode (
			strCode,
			adtEffective,
			adtMaturity,
			adblCoupon
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			"USD"
		);

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setGovvieState (gc);

		System.out.println ("\n\t|--------------------------------------------||");

		System.out.println ("\t|       TREASURY INPUT vs CALIB YIELD        ||");

		System.out.println ("\t|--------------------------------------------||");

		for (int i = 0; i < aComp.length; ++i)
			System.out.println ("\t| " + aComp[i].name() + " | " +
				FormatUtil.FormatDouble (adblYield[i], 2, 2, 100.) + "% | " +
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
				), 2, 2, 100.) + "% ||"
			);

		System.out.println ("\t|--------------------------------------------||");

		return gc;
	}

	private static final void AccumulateBondMarketQuote (
		final CurveSurfaceQuoteContainer csqc,
		final Bond[] aBond,
		final double[] adblCleanPrice)
		throws Exception
	{
		for (int i = 0; i < aBond.length; ++i) {
			ProductMultiMeasure pmmq = new ProductMultiMeasure();

			pmmq.addQuote (
				"Price",
				new MultiSided (
					"mid",
					adblCleanPrice[i]
				),
				true
			);

			csqc.setProductQuote (
				aBond[i].name(),
				pmmq
			);
		}
	}

	/*
	 * Print the Bond RV Measures
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final boolean PrintRVMeasures (
		final String strPrefix,
		final BondRVMeasures rv)
		throws Exception
	{
		if (null == rv) return false;

		System.out.println (strPrefix + "ASW               : " + FormatUtil.FormatDouble (rv.asw(), 2, 0, 10000.));

		System.out.println (strPrefix + "Bond Basis        : " + FormatUtil.FormatDouble (rv.bondBasis(), 2, 0, 10000.));

		System.out.println (strPrefix + "Convexity         : " + FormatUtil.FormatDouble (rv.convexity(), 1, 4, 1000000.));

		System.out.println (strPrefix + "Discount Margin   : " + FormatUtil.FormatDouble (rv.discountMargin(), 2, 0, 10000.));

		System.out.println (strPrefix + "G Spread          : " + FormatUtil.FormatDouble (rv.gSpread(), 2, 0, 10000.));

		System.out.println (strPrefix + "I Spread          : " + FormatUtil.FormatDouble (rv.iSpread(), 2, 0, 10000.));

		System.out.println (strPrefix + "Macaulay Duration : " + FormatUtil.FormatDouble (rv.macaulayDuration(), 1, 4, 1.));

		System.out.println (strPrefix + "Modified Duration : " + FormatUtil.FormatDouble (rv.modifiedDuration(), 1, 4, 10000.));

		System.out.println (strPrefix + "Price             : " + FormatUtil.FormatDouble (rv.price(), 2, 4, 100.));

		System.out.println (strPrefix + "Workout Date      : " + new JulianDate (rv.wi().date()));

		System.out.println (strPrefix + "Workout Factor    : " + FormatUtil.FormatDouble (rv.wi().factor(), 2, 4, 1.));

		System.out.println (strPrefix + "Workout Type      : " + rv.wi().type());

		System.out.println (strPrefix + "Workout Yield     : " + FormatUtil.FormatDouble (rv.wi().yield(), 1, 4, 100.) + "%");

		System.out.println (strPrefix + "Yield01           : " + FormatUtil.FormatDouble (rv.yield01(), 1, 4, 10000.));

		System.out.println (strPrefix + "Yield Basis       : " + FormatUtil.FormatDouble (rv.bondBasis(), 2, 0, 10000.));

		System.out.println (strPrefix + "Yield Spread      : " + FormatUtil.FormatDouble (rv.bondBasis(), 2, 0, 10000.));

		System.out.println (strPrefix + "Z Spread          : " + FormatUtil.FormatDouble (rv.zSpread(), 2, 0, 10000.));

		return true;
	}

	private static final void BondRVMeasuresSample (
		final BondComponent bond,
		final JulianDate dtSpot,
		final CurveSurfaceQuoteContainer csqc,
		final double dblPrice)
		throws Exception
	{

		ValuationParams valParams = ValuationParams.Spot (
			dtSpot,
			0,
			"",
			Convention.DATE_ROLL_ACTUAL
		);

		/*
		 * Compute the work-out date given the price.
		 */

		WorkoutInfo wi = bond.exerciseYieldFromPrice (
			valParams,
			csqc,
			null,
			dblPrice
		);

		/*
		 * Compute the base RV measures to the work-out date.
		 */

		org.drip.analytics.output.BondRVMeasures rvm = bond.standardMeasures (
			valParams,
			null,
			csqc,
			null,
			wi,
			dblPrice
		);

		System.out.println ("\t|---------------------------------------------||\n");

		PrintRVMeasures ("\t|\t", rvm);

		Map<String, Double> mapOutput = bond.value (
			valParams,
			null,
			csqc,
			null
		);

		System.out.println ("\t|---------------------------------------------||\n");

		System.out.println ("\n\t|--------------------------------------------------------------------------||");

		System.out.println ("\t|                  CTD Full Bond Measures                                  ||");

		System.out.println ("\t|--------------------------------------------------------------------------||");

		for (Map.Entry<String, Double> me : mapOutput.entrySet())
			System.out.println ("\t|\t" + me.getKey() + " => " + me.getValue());

		System.out.println ("\t|--------------------------------------------------------------------------||");
	}

	private static final void ComputeFuturesMeasures (
		final TreasuryFutures bf,
		final JulianDate dtSpot,
		final CurveSurfaceQuoteContainer csqc,
		final double[] adblCleanPrice)
		throws Exception
	{
		ValuationParams valParams = ValuationParams.Spot (
			dtSpot,
			0,
			"",
			Convention.DATE_ROLL_ACTUAL
		);

		AccumulateBondMarketQuote (
			csqc,
			bf.basket(),
			adblCleanPrice
		);

		Map<String, Double> mapOutput = bf.value (
			valParams,
			null,
			csqc,
			null
		);

		System.out.println ("\n\t|--------------------------------------------------------------------------||");

		System.out.println ("\t|                  Bond Futures Measures                                   ||");

		System.out.println ("\t|--------------------------------------------------------------------------||");

		for (Map.Entry<String, Double> me : mapOutput.entrySet())
			System.out.println ("\t|\t" + me.getKey() + " => " + me.getValue());

		System.out.println ("\t|--------------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2015,
			DateUtil.NOVEMBER,
			18
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
			0.0300
		};

		double[] adblTreasuryYield = new double[] {
			0.00692,
			0.00945,
			0.01257,
			0.01678,
			0.02025,
			0.02235,
			0.02972
		};

		GovvieCurve gc = TreasuryCurve (
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

		csqc.setGovvieState (gc);

		OnTheRunQuote (
			csqc,
			new String[] {
				"01YON",
				"02YON",
				"03YON",
				"05YON",
				"07YON",
				"10YON",
				"30YON"
			},
			adblTreasuryYield
		);

		Bond ust912810QN = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.FEBRUARY,
				15
			),
			DateUtil.CreateFromYMD (
				2041,
				DateUtil.FEBRUARY,
				15
			),
			0.04750
		);

		Bond ust912810QQ = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.MAY,
				15
			),
			DateUtil.CreateFromYMD (
				2041,
				DateUtil.MAY,
				15
			),
			0.04375
		);

		Bond ust912810QS = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.AUGUST,
				15
			),
			DateUtil.CreateFromYMD (
				2041,
				DateUtil.AUGUST,
				15
			),
			0.03750
		);

		Bond ust912810RC = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.AUGUST,
				15
			),
			DateUtil.CreateFromYMD (
				2043,
				DateUtil.AUGUST,
				15
			),
			0.03625
		);

		Bond ust912810RD = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.NOVEMBER,
				15
			),
			DateUtil.CreateFromYMD (
				2043,
				DateUtil.NOVEMBER,
				15
			),
			0.03750
		);

		Bond ust912810QT = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.NOVEMBER,
				15
			),
			DateUtil.CreateFromYMD (
				2041,
				DateUtil.NOVEMBER,
				15
			),
			0.03125
		);

		Bond ust912810QU = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.FEBRUARY,
				15
			),
			DateUtil.CreateFromYMD (
				2043,
				DateUtil.FEBRUARY,
				15
			),
			0.03125
		);

		Bond ust912810RE = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.FEBRUARY,
				15
			),
			DateUtil.CreateFromYMD (
				2044,
				DateUtil.FEBRUARY,
				15
			),
			0.03625
		);

		Bond ust912810QZ = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.FEBRUARY,
				15
			),
			DateUtil.CreateFromYMD (
				2043,
				DateUtil.FEBRUARY,
				15
			),
			0.03125
		);

		Bond ust912810QW = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.MAY,
				15
			),
			DateUtil.CreateFromYMD (
				2042,
				DateUtil.MAY,
				15
			),
			0.03000
		);

		Bond ust912810RG = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.MAY,
				15
			),
			DateUtil.CreateFromYMD (
				2044,
				DateUtil.MAY,
				15
			),
			0.03625
		);

		Bond ust912810QX = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.AUGUST,
				15
			),
			DateUtil.CreateFromYMD (
				2042,
				DateUtil.AUGUST,
				15
			),
			0.02375
		);

		Bond ust912810RB = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.MAY,
				15
			),
			DateUtil.CreateFromYMD (
				2043,
				DateUtil.MAY,
				15
			),
			0.02875
		);

		Bond ust912810QY = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.NOVEMBER,
				15
			),
			DateUtil.CreateFromYMD (
				2042,
				DateUtil.NOVEMBER,
				15
			),
			0.02750
		);

		Bond ust912810RH = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.AUGUST,
				15
			),
			DateUtil.CreateFromYMD (
				2044,
				DateUtil.AUGUST,
				15
			),
			0.03125
		);

		Bond ust912810RJ = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.NOVEMBER,
				15
			),
			DateUtil.CreateFromYMD (
				2044,
				DateUtil.NOVEMBER,
				15
			),
			0.03000
		);

		Bond ust912810RM = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.MAY,
				15
			),
			DateUtil.CreateFromYMD (
				2045,
				DateUtil.MAY,
				15
			),
			0.03000
		);

		Bond ust912810RP = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.NOVEMBER,
				15
			),
			DateUtil.CreateFromYMD (
				2045,
				DateUtil.NOVEMBER,
				15
			),
			0.03000
		);

		Bond ust912810RN = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.AUGUST,
				15
			),
			DateUtil.CreateFromYMD (
				2045,
				DateUtil.AUGUST,
				15
			),
			0.02875
		);

		Bond ust912810RK = TreasuryBuilder.UST (
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.FEBRUARY,
				15
			),
			DateUtil.CreateFromYMD (
				2045,
				DateUtil.FEBRUARY,
				15
			),
			0.02500
		);

		double dblContractSize = 100000.;
		double dblFuturesPrice = 1.56687500;

		Bond[] aBond = new Bond[] {
			ust912810QN,
			ust912810QQ,
			ust912810QS,
			ust912810RC,
			ust912810RD,
			ust912810QT,
			ust912810QU,
			ust912810RE,
			ust912810QZ,
			ust912810QW,
			ust912810RG,
			ust912810QX,
			ust912810RB,
			ust912810QY,
			ust912810RH,
			ust912810RJ,
			ust912810RM,
			ust912810RP,
			ust912810RN,
			ust912810RK
		};

		TreasuryFutures tu1 = new TreasuryFutures (
			aBond,
			new double[] {
				0.8392, // 912810QN
				0.7900, // 912810QQ
				0.7080, // 912810QS
				0.6821, // 912810RC
				0.6976, // 912810RD
				0.6253, // 912810QT
				0.6239, // 912810QU
				0.6798, // 912810RE
				0.6179, // 912810QZ
				0.6059, // 912810QW
				0.6448, // 912810RG
				0.5714, // 912810QX
				0.5831, // 912810RB
				0.5697, // 912810QY
				0.6097, // 912810RH
				0.5913, // 912810RJ
				0.5887, // 912810RM
				0.5861, // 912810RP
				0.5702, // 912810RN
				0.5217, // 912810RK
			},
			null
		);

		double[] adblRepoRate = new double[] {
			 0.00800,
			 0.00825,
			 0.00850,
			 0.00875,
			 0.00900,
			 0.00925,
			 0.00950,
			 0.00975,
			 0.01000,
			 0.01025,
			 0.01050,
			 0.01075,
			 0.01100,
			 0.01125,
			 0.01150,
			 0.01175,
			 0.01200,
			 0.01225,
			 0.01250,
			 0.01275
		};

		RepoCurves (
			dtSpot,
			csqc,
			aBond,
			adblRepoRate
		);

		tu1.setExpiry (
			DateUtil.CreateFromYMD (
				2016,
				DateUtil.FEBRUARY,
				15
			)
		);

		double[] adblCleanPrice = new double[] {
			1.3200000,
			1.2540625,
			1.1412500,
			1.1193750,
			1.1450000,
			1.0262500,
			1.0256250,
			1.1184375,
			1.0181250,
			0.9990625,
			1.0659375,
			0.9481250,
			0.9681250,
			0.9459375,
			1.0153125,
			0.9896875,
			0.9890625,
			0.9918750,
			0.9656250,
			0.8909375
		};

		FuturesQuote (
			csqc,
			tu1,
			dblFuturesPrice
		);

		Bond bondCTD = tu1.cheapestToDeliverYield (
			dtSpot.julian(),
			adblCleanPrice
		).bond();

		System.out.println ("\n\t|---------------------------------------------||");

		System.out.println ("\t|                                             ||");

		System.out.println ("\t|      Bond # 1: " + ust912810QN.name() + "       ||");

		System.out.println ("\t|      Bond # 2: " + ust912810QQ.name() + "       ||");

		System.out.println ("\t|      Bond # 3: " + ust912810QS.name() + "       ||");

		System.out.println ("\t|      Bond # 4: " + ust912810RC.name() + "       ||");

		System.out.println ("\t|      Bond # 5: " + ust912810RD.name() + "       ||");

		System.out.println ("\t|      Bond # 6: " + ust912810QT.name() + "       ||");

		System.out.println ("\t|      Bond # 7: " + ust912810QU.name() + "       ||");

		System.out.println ("\t|      Bond # 8: " + ust912810RE.name() + "       ||");

		System.out.println ("\t|      Bond # 9: " + ust912810QZ.name() + "       ||");

		System.out.println ("\t|      Bond #10: " + ust912810QW.name() + "       ||");

		System.out.println ("\t|      Bond #11: " + ust912810RG.name() + "       ||");

		System.out.println ("\t|      Bond #12: " + ust912810QX.name() + "       ||");

		System.out.println ("\t|      Bond #13: " + ust912810RB.name() + "       ||");

		System.out.println ("\t|      Bond #14: " + ust912810QY.name() + "       ||");

		System.out.println ("\t|      Bond #15: " + ust912810RH.name() + "       ||");

		System.out.println ("\t|      Bond #16: " + ust912810RJ.name() + "       ||");

		System.out.println ("\t|      Bond #17: " + ust912810RM.name() + "       ||");

		System.out.println ("\t|      Bond #18: " + ust912810RP.name() + "       ||");

		System.out.println ("\t|      Bond #19: " + ust912810RN.name() + "       ||");

		System.out.println ("\t|      Bond #20: " + ust912810RK.name() + "       ||");

		System.out.println ("\t|                                             ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t| Cheapest to Deliver: " + bondCTD.name() + " ||");

		BondRVMeasuresSample (
			(BondComponent) bondCTD,
			dtSpot,
			csqc,
			1.3200000
		);

		ComputeFuturesMeasures (
			tu1,
			dtSpot,
			csqc,
			adblCleanPrice
		);

		System.out.println ("\n\t|---------------------------------------------||");

		System.out.println ("\t|      Futures Price  : " + FormatUtil.FormatDouble (dblFuturesPrice, 2, 5, 100.) + "            ||");

		System.out.println ("\t|      Contract Size  : " + FormatUtil.FormatDouble (dblContractSize, 1, 2, 1.) + "            ||");

		System.out.println ("\t|      Contract Value : " + FormatUtil.FormatDouble (dblContractSize * dblFuturesPrice, 1, 2, 1.) + "            ||");

		System.out.println ("\t|---------------------------------------------||\n");
	}
}
