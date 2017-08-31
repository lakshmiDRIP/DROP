
package org.drip.sample.bond;

import org.drip.analytics.date.*;
import org.drip.analytics.output.BondEOSMetrics;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.product.params.EmbeddedOptionSchedule;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.sequence.GovvieBuilderSettings;

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
 * MultiCallExerciseMetrics demonstrates the Simulations of the Per-Path Callable Bond OAS Based Exercise
 *  Metrics.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MultiCallExerciseMetrics {

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

		return LatentMarketStateBuilder.SmoothFundingCurve (
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
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.MARCH,
			24
		);

		int iNumPath = 500;
		double dblCleanPrice = 1.08641;
		int[] aiExerciseDate = new int[] {
			DateUtil.CreateFromYMD (2019, 12,  1).julian(),
			DateUtil.CreateFromYMD (2020, 12,  1).julian(),
			DateUtil.CreateFromYMD (2021, 12,  1).julian(),
			DateUtil.CreateFromYMD (2022, 12,  1).julian(),
			DateUtil.CreateFromYMD (2023, 12,  1).julian(),
			DateUtil.CreateFromYMD (2024, 12,  1).julian(),
			DateUtil.CreateFromYMD (2025, 12,  1).julian(),
			DateUtil.CreateFromYMD (2026, 12,  1).julian(),
			DateUtil.CreateFromYMD (2027, 12,  1).julian(),
			DateUtil.CreateFromYMD (2028, 12,  1).julian(),
			DateUtil.CreateFromYMD (2029, 12,  1).julian(),
			DateUtil.CreateFromYMD (2030, 12,  1).julian(),
			DateUtil.CreateFromYMD (2031, 12,  1).julian(),
			DateUtil.CreateFromYMD (2032, 12,  1).julian(),
			DateUtil.CreateFromYMD (2033, 12,  1).julian(),
			DateUtil.CreateFromYMD (2034, 12,  1).julian(),
			DateUtil.CreateFromYMD (2035, 12,  1).julian(),
			DateUtil.CreateFromYMD (2036, 12,  1).julian(),
			DateUtil.CreateFromYMD (2037, 12,  1).julian(),
			DateUtil.CreateFromYMD (2038, 12,  1).julian(),
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
			1.,
			1.,
			1.,
			1.,
			1.,
			1.,
		};
		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2009,
			12,
			3
		);

		JulianDate dtMaturity  = DateUtil.CreateFromYMD (
			2039,
			12,
			1
		);

		double dblCoupon = 0.06558;
		int iFreq = 2;
		String strCUSIP = "033177XV3";
		String strDayCount = "30/360";
		double dblVolatility = 0.10;
		String strTreasuryCode = "UST";

		String[] astrTenor = new String[] {
			"01Y",
			"02Y",
			"03Y",
			"05Y",
			"07Y",
			"10Y",
			"20Y",
			"30Y"
		};

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

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			strCUSIP,
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

		bond.setEmbeddedCallSchedule (
			new EmbeddedOptionSchedule (
				aiExerciseDate,
				adblExercisePrice,
				false,
				30,
				false,
				Double.NaN,
				"",
				Double.NaN
			)
		);

		GovvieBuilderSettings gbs = new GovvieBuilderSettings (
			dtSpot,
			strTreasuryCode,
			astrTenor,
			adblTreasuryCoupon,
			adblTreasuryYield
		);

		BondEOSMetrics bem = bond.callMetrics (
			ValuationParams.Spot (dtSpot.julian()),
			MarketParamsBuilder.Create (
				FundingCurve (
					dtSpot,
					"USD",
					0.
				),
				gbs.groundState(),
				null,
				null,
				null,
				null,
				null
			),
			null,
			dblCleanPrice,
			gbs,
			new DiffusionEvolver (
				DiffusionEvaluatorLogarithmic.Standard (
					0.,
					dblVolatility
				)
			),
			iNumPath
		);

		UnivariateDiscreteThin udtOptimalExercisePrice = bem.optimalExercisePrice();

		UnivariateDiscreteThin udtOptimalExercisePV = bem.optimalExerciseValue();

		UnivariateDiscreteThin udtOptimalExerciseOAS = bem.optimalExerciseOAS();

		UnivariateDiscreteThin udtOptimalExerciseOASGap = bem.optimalExerciseOASGap();

		UnivariateDiscreteThin udtOptimalExerciseDuration = bem.optimalExerciseDuration();

		UnivariateDiscreteThin udtOptimalExerciseConvexity = bem.optimalExerciseConvexity();

		System.out.println();

		System.out.println ("\t||-------------------------------------------------------------||");

		System.out.println (
			"\t||  OAS             => " +
				FormatUtil.FormatDouble (
					bem.oas(), 3, 1, 10000.
				)
			+ "                                  ||"
		);

		System.out.println (
			"\t||  OAS Duration    =>   " +
				FormatUtil.FormatDouble (
					bem.oasDuration(), 1, 1, 10000.
				)
			+ "                                  ||"
		);

		System.out.println (
			"\t||  OAS Convexity   =>   " +
				FormatUtil.FormatDouble (
					bem.oasConvexity(), 1, 1, 1000000.
				)
			+ "                                  ||"
		);

		System.out.println (
			"\t||  OAS To Maturity => " +
				FormatUtil.FormatDouble (
					bem.oasTM(), 3, 1, 10000.
				)
			+ "                                  ||"
		);

		System.out.println ("\t||-------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||-------------------------------------------------------------||");

		System.out.println ("\t||        Optimal Exercise Price                               ||");

		System.out.println ("\t||        Optimal Exercise Value                               ||");

		System.out.println ("\t||        Optimal Exercise OAS                                 ||");

		System.out.println ("\t||        Optimal Exercise OAS Gap                             ||");

		System.out.println ("\t||        Optimal Exercise Duration                            ||");

		System.out.println ("\t||        Optimal Exercise Convexity                           ||");

		System.out.println ("\t||-------------------------------------------------------------||");

		System.out.println ("\t|| AVERAGE => " +
			FormatUtil.FormatDouble (udtOptimalExercisePrice.average(), 3, 2, 100.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExercisePV.average(), 2, 1, 100.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseOAS.average(), 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseOASGap.average(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseDuration.average(), 2, 2, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseConvexity.average(), 1, 2, 1000000.) + " ||"
		);

		System.out.println ("\t||  ERROR  => " +
			FormatUtil.FormatDouble (udtOptimalExercisePrice.error(), 3, 2, 100.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExercisePV.error(), 2, 1, 100.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseOAS.error(), 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseOASGap.error(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseDuration.error(), 2, 2, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseConvexity.error(), 1, 2, 1000000.) + " ||"
		);

		System.out.println ("\t|| MAXIMUM => " +
			FormatUtil.FormatDouble (udtOptimalExercisePrice.maximum(), 3, 2, 100.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExercisePV.maximum(), 2, 1, 100.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseOAS.maximum(), 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseOASGap.maximum(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseDuration.maximum(), 2, 2, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseConvexity.maximum(), 1, 2, 1000000.) + " ||"
		);

		System.out.println ("\t|| MINIMUM => " +
			FormatUtil.FormatDouble (udtOptimalExercisePrice.minimum(), 3, 2, 100.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExercisePV.minimum(), 2, 1, 100.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseOAS.minimum(), 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseOASGap.minimum(), 3, 0, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseDuration.minimum(), 2, 2, 10000.) + " | " +
			FormatUtil.FormatDouble (udtOptimalExerciseConvexity.minimum(), 1, 2, 1000000.) + " ||"
		);

		System.out.println ("\t||-------------------------------------------------------------||");

		System.out.println();
	}
}
