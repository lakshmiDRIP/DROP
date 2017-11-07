
package org.drip.sample.securitysuite;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.market.otc.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.rates.FixFloatComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;

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
 * CMEFixFloat demonstrates the Analytics Calculation/Reconciliation for the CME Cleared Fix-Float IRS.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CMEFixFloat {

	private static final MergedDiscountForwardCurve OvernightCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] astrDepositMaturityTenor = new String[] {
			"1D",
			// "3D"
		};

		double[] adblDepositQuote = new double[] {
			0.0004,		// 1D
			// 0.0004		// 3D
		};

		String[] astrShortEndOISMaturityTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M"
		};

		double[] adblShortEndOISQuote = new double[] {
			0.00070,    //   1W
			0.00069,    //   2W
			0.00078,    //   3W
			0.00074     //   1M
		};

		String[] astrOISFuturesEffectiveTenor = new String[] {
			"1M",
			"2M",
			"3M",
			"4M",
			"5M"
		};

		String[] astrOISFuturesMaturityTenor = new String[] {
			"1M",
			"1M",
			"1M",
			"1M",
			"1M"
		};

		double[] adblOISFuturesQuote = new double[] {
			 0.00046,    //   1M x 1M
			 0.00016,    //   2M x 1M
			-0.00007,    //   3M x 1M
			-0.00013,    //   4M x 1M
			-0.00014     //   5M x 1M
		};

		String[] astrLongEndOISMaturityTenor = new String[] {
			"15M",
			"18M",
			"21M",
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
			"30Y"
		};

		double[] adblLongEndOISQuote = new double[] {
			0.00002,    //  15M
			0.00008,    //  18M
			0.00021,    //  21M
			0.00036,    //   2Y
			0.00127,    //   3Y
			0.00274,    //   4Y
			0.00456,    //   5Y
			0.00647,    //   6Y
			0.00827,    //   7Y
			0.00996,    //   8Y
			0.01147,    //   9Y
			0.01280,    //  10Y
			0.01404,    //  11Y
			0.01516,    //  12Y
			0.01764,    //  15Y
			0.01939,    //  20Y
			0.02003,    //  25Y
			0.02038     //  30Y
		};

		return LatentMarketStateBuilder.SmoothOvernightCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"Rate",
			astrShortEndOISMaturityTenor,
			adblShortEndOISQuote,
			"SwapRate",
			astrOISFuturesEffectiveTenor,
			astrOISFuturesMaturityTenor,
			adblOISFuturesQuote,
			"SwapRate",
			astrLongEndOISMaturityTenor,
			adblLongEndOISQuote,
			"SwapRate"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		String strCurrency = "GBP";
		String strForwardTenor = "6M";

		JulianDate dtSpot = DateUtil.Today();

		MergedDiscountForwardCurve dcOvernight = OvernightCurve (
			dtSpot,
			strCurrency
		);

		ForwardLabel forwardLabel = ForwardLabel.Create (
			strCurrency,
			strForwardTenor
		);

		String[] astrDepositMaturityTenor = new String[] {
			"1D",
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"4M",
			"5M"
		};

		double[] adblDepositQuote = new double[] {
			0.003565,	// 1D
			0.003858,	// 1W
			0.003840,	// 2W
			0.003922,	// 3W
			0.003869,	// 1M
			0.003698,	// 2M
			0.003527,	// 3M
			0.003342,	// 4M
			0.003225	// 5M
		};

		String[] astrFRAExerciseTenor = new String[] {
			"00D",
			"01M",
			"02M",
			"03M",
			"04M",
			"05M",
			"06M",
			"07M",
			"08M",
			"09M",
			"10M",
			"11M",
			"12M",
			"13M",
			"14M",
			"15M",
			"16M",
			"17M",
			"18M"
		};

		double[] adblFRAQuote = new double[] {
			0.003120,	//  0D
			0.002930,	//  1M
			0.002720,	//  2M
			0.002600,	//  3M
			0.002560,	//  4M
			0.002520,	//  5M
			0.002480,	//  6M
			0.002540,	//  7M
			0.002610,	//  8M
			0.002670,	//  9M
			0.002790,	// 10M
			0.002910,	// 11M
			0.003030,	// 12M
			0.003180,	// 13M
			0.003350,	// 14M
			0.003520,	// 15M
			0.003710,	// 16M
			0.003890,	// 17M
			0.004090	// 18M
		};

		String[] astrFixFloatMaturityTenor = new String[] {
			"03Y",
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
			"10Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"35Y",
			"40Y",
			"50Y",
			"60Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.004240,	//  3Y
			0.005760,	//  4Y			
			0.007620,	//  5Y
			0.009540,	//  6Y
			0.011350,	//  7Y
			0.013030,	//  8Y
			0.014520,	//  9Y
			0.015840,	// 10Y
			0.018090,	// 12Y
			0.020370,	// 15Y
			0.021870,	// 20Y
			0.022340,	// 25Y
			0.022560,	// 30Y
			0.022950,	// 35Y
			0.023480,	// 40Y
			0.024210,	// 50Y
			0.024630	// 60Y
		};

		ForwardCurve fc = LatentMarketStateBuilder.ShapePreservingForwardCurve (
			dtSpot,
			forwardLabel,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			astrFRAExerciseTenor,
			adblFRAQuote,
			"ParForwardRate",
			astrFixFloatMaturityTenor,
			adblFixFloatQuote,
			"SwapRate",
			null,
			null,
			"DerivedParBasisSpread",
			null,
			null,
			"DerivedParBasisSpread",
			dcOvernight,
			null
		);

		String strMaturityTenor = "5Y";
		double dblFixedCoupon = 0.021893;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2017,
			DateUtil.JULY,
			8
		);

		FixedFloatSwapConvention ffsc = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"ALL",
			strMaturityTenor,
			"MAIN"
		);

		FixFloatComponent ffc = ffsc.createFixFloatComponent (
			dtEffective,
			strMaturityTenor,
			dblFixedCoupon,
			0.,
			1.
		);

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setFundingState (dcOvernight);

		csqc.setForwardState (fc);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		CaseInsensitiveTreeMap<Double> mapOutput = ffc.value (
			valParams,
			null,
			csqc,
			null
		);

		for (Map.Entry<String, Double> me : mapOutput.entrySet())
			System.out.println ("\t\t" + me.getKey() + " => " + me.getValue());

		System.out.println();

		System.out.println ("\tClean Price       =>" +
			FormatUtil.FormatDouble (mapOutput.get ("CleanPrice"), 1, 4, 1.)
		);

		System.out.println ("\tDirty Price       =>" +
			FormatUtil.FormatDouble (mapOutput.get ("DirtyPrice"), 1, 4, 1.)
		);

		System.out.println ("\tFixed Stream PV   =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("ReferencePV"), 1, 8, 1.)
		);

		System.out.println ("\tFloat Stream PV   =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("DerivedPV"), 1, 8, 1.)
		);

		System.out.println ("\tFixed Stream PV   =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("ReferencePV"), 1, 8, 1.)
		);

		System.out.println ("\tFixed Stream DV01 =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("ReferenceDV01"), 1, 8, 10000.)
		);

		System.out.println ("\tFloat Stream DV01 =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("DerivedDV01"), 1, 8, 10000.)
		);

		System.out.println ("\tFixing 01         =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("Fixing01"), 1, 8, 10000.)
		);

		System.out.println ("\tClean PV          =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("CleanPV"), 1, 8, 1.)
		);

		System.out.println ("\tDirty PV          =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("DirtyPV"), 1, 8, 1.)
		);

		System.out.println ("\tFixed Accrued     =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("FixedAccrued"), 1, 8, 1.)
		);

		System.out.println ("\tFloat Accrued     =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("FloatAccrued"), 1, 8, 1.)
		);

		System.out.println ("\tAccrued           =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("Accrued"), 1, 8, 1.)
		);

		System.out.println ("\tPar Swap Rate     =>  " +
			FormatUtil.FormatDouble (mapOutput.get ("ParSwapRate"), 1, 4, 100.) + "%"
		);
 	}
}
