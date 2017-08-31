
package org.drip.sample.dual;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.sample.forward.*;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * SEK3M6MUSD3M6M demonstrates the setup and construction of the USD 3M Forward Curve from SEK3M6MUSD3M6M
 * 	CCBS, SEK 3M, SEK 6M, and USD 6M Quotes.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SEK3M6MUSD3M6M {
	private static final double _dblFXSEKUSD = 0.1496;

	private static final int[] s_aiUSDOISDepositMaturityDays = new int[] {
		1,
		2,
		3
	};

	private static final double[] s_adblUSDOISDepositQuote = new double[] {
		0.0004,	// 1D
		0.0004,	// 2D
		0.0004	// 3D
	};

	private static final String[] s_astrUSDShortEndOISMaturityTenor = new String[] {
		"1W",
		"2W",
		"3W",
		"1M"
	};

	private static final double[] s_adblUSDShortEndOISQuote = new double[] {
		0.00070,    //   1W
		0.00069,    //   2W
		0.00078,    //   3W
		0.00074     //   1M
	};

	private static final String[] s_astrUSDOISFutureTenor = new String[] {
		"1M",
		"1M",
		"1M",
		"1M",
		"1M"
	};

	private static final String[] s_astrUSDOISFutureMaturityTenor = new String[] {
		"1M",
		"2M",
		"3M",
		"4M",
		"5M"
	};

	private static final double[] s_adblUSDOISFutureQuote = new double[] {
		 0.00046,    //   1M x 1M
		 0.00016,    //   2M x 1M
		-0.00007,    //   3M x 1M
		-0.00013,    //   4M x 1M
		-0.00014     //   5M x 1M
	};

	private static final String[] s_astrUSDLongEndOISMaturityTenor = new String[] {
		"15M",
		"18M",
		"21M",
		"2Y",
		"3Y",
		"4Y",
		"5Y",
		"6Y",
		"7Y",
		"8Y",
		"9Y",
		"10Y",
		"11Y",
		"12Y",
		"15Y",
		"20Y",
		"25Y",
		"30Y"
	};

	private static final double[] s_adblUSDLongEndOISQuote = new double[] {
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

	private static final int[] s_aiSEKOISDepositMaturityDays = new int[] {
		1,
		2,
		3
	};

	private static final double[] s_adblSEKOISDepositQuote = new double[] {
		0.0004,	// 1D
		0.0004,	// 2D
		0.0004	// 3D
	};

	private static final String[] s_astrSEKShortEndOISMaturityTenor = new String[] {
		"1W",
		"2W",
		"3W",
		"1M"
	};

	private static final double[] s_adblSEKShortEndOISQuote = new double[] {
		0.00070,    //   1W
		0.00069,    //   2W
		0.00078,    //   3W
		0.00074     //   1M
	};

	private static final String[] s_astrSEKOISFutureTenor = new String[] {
		"1M",
		"1M",
		"1M",
		"1M",
		"1M"
	};

	private static final String[] s_astrSEKOISFutureMaturityTenor = new String[] {
		"1M",
		"2M",
		"3M",
		"4M",
		"5M"
	};

	private static final double[] s_adblSEKOISFutureQuote = new double[] {
		 0.00046,    //   1M x 1M
		 0.00016,    //   2M x 1M
		-0.00007,    //   3M x 1M
		-0.00013,    //   4M x 1M
		-0.00014     //   5M x 1M
	};

	private static final String[] s_astrSEKLongEndOISMaturityTenor = new String[] {
		"15M",
		"18M",
		"21M",
		"2Y",
		"3Y",
		"4Y",
		"5Y",
		"6Y",
		"7Y",
		"8Y",
		"9Y",
		"10Y",
		"11Y",
		"12Y",
		"15Y",
		"20Y",
		"25Y",
		"30Y"
	};

	private static final double[] s_adblSEKLongEndOISQuote = new double[] {
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

	private static final String[] s_astrUSD6MDepositTenor = new String[] {
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

	private static final double[] s_adblUSD6MDepositQuote = new double[] {
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

	private static final String[] s_astrUSD6MFRATenor = new String[] {
		 "0D",
		 "1M",
		 "2M",
		 "3M",
		 "4M",
		 "5M",
		 "6M",
		 "7M",
		 "8M",
		 "9M",
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

	private static final double[] s_adblUSD6MFRAQuote = new double[] {
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

	private static final String[] s_astrUSD6MFixFloatTenor = new String[] {
		 "3Y",
		 "4Y",
		 "5Y",
		 "6Y",
		 "7Y",
		 "8Y",
		 "9Y",
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

	private static final double[] s_adblUSD6MFixFloatQuote = new double[] {
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

	private static final String[] s_astrUSD3MDepositTenor = new String[] {
		"2W",
		"3W",
		"1M",
		"2M"
	};

	private static final double[] s_adblUSD3MDepositQuote = new double[] {
		0.001865,
		0.001969,
		0.001951,
		0.001874
	};

	private static final String[] s_astrUSD3MFRATenor = new String[] {
		 "0D",
		 "1M",
		 "3M",
		 "6M",
		 "9M",
		"12M",
		"15M",
		"18M",
		"21M"
	};

	private static final double[] s_adblUSD3MFRAQuote = new double[] {
		0.001790,
		0.001775,
		0.001274,
		0.001222,
		0.001269,
		0.001565,
		0.001961,
		0.002556,
		0.003101
	};

	private static final String[] s_astrUSD3MFixFloatTenor = new String[] {
		 "3Y",
		 "4Y",
		 "5Y",
		 "6Y",
		 "7Y",
		 "8Y",
		 "9Y",
		"10Y",
		"12Y",
		"15Y",
		"20Y",
		"25Y",
		"30Y"
	};

	private static final double[] s_adblUSD3MFixFloatQuote = new double[] {
		0.002850,	//  3Y
		0.004370,	//  4Y
		0.006230,	//  5Y
		0.008170,	//  6Y
		0.010000,	//  7Y
		0.011710,	//  8Y
		0.013240,	//  9Y
		0.014590,	// 10Y
		0.016920,	// 12Y
		0.019330,	// 15Y
		0.020990,	// 20Y
		0.021560,	// 25Y
		0.021860 	// 30Y
	};

	private static final String[] s_astrUSD3MSyntheticFloatFloatTenor = new String[] {
		"35Y",
		"40Y",
		"50Y",
		"60Y"
	};

	private static final double[] s_adblUSD3MSyntheticFloatFloatQuote = new double[] {
		0.00065,
		0.00060,
		0.00054,
		0.00050
	};

	private static final String[] s_astrSEK6MDepositTenor = new String[] {
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

	private static final double[] s_adblSEK6MDepositQuote = new double[] {
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

	private static final String[] s_astrSEK6MFRATenor = new String[] {
		 "0D",
		 "1M",
		 "2M",
		 "3M",
		 "4M",
		 "5M",
		 "6M",
		 "7M",
		 "8M",
		 "9M",
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

	private static final double[] s_adblSEK6MFRAQuote = new double[] {
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

	private static final String[] s_astrSEK6MFixFloatTenor = new String[] {
		 "3Y",
		 "4Y",
		 "5Y",
		 "6Y",
		 "7Y",
		 "8Y",
		 "9Y",
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

	private static final double[] s_adblSEK6MFixFloatQuote = new double[] {
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

	private static final String[] s_astrCCBSTenor = new String[] {
		"1Y",
		"2Y",
		"3Y",
		"4Y",
		"5Y",
		"7Y",
		"10Y",
		"15Y",
		"20Y"
	};

	private static final double[] s_adblCCBSQuote = new double[] {
		-0.000525, //  1Y
		-0.000400, //  2Y
		-0.000250, //  3Y
		-0.000175, //  4Y
		-0.000150, //  5Y
		-0.000125, //  7Y
		-0.000125, // 10Y
		-0.000000, // 15Y
		-0.000000  // 20Y
	};

	private static final double[] s_adblIRSQuote = new double[] {
		0.0100, //  1Y
		0.0100, //  2Y
		0.0125, //  3Y
		0.0125, //  4Y
		0.0150, //  5Y
		0.0175, //  7Y
		0.0250, // 10Y
		0.0400, // 15Y
		0.0525  // 20Y
	};

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtValue = DateUtil.CreateFromYMD (
			2012,
			DateUtil.DECEMBER,
			11
		);

		String strReferenceCurrency = "USD";
		String strDerivedCurrency = "SEK";

		SegmentCustomBuilderControl scbcCubic = new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (4),
			SegmentInelasticDesignControl.Create (
				2,
				2
			),
			new ResponseScalingShapeControl (
				true,
				new QuadraticRationalShapeControl (0.)
			),
			null
		);

		MergedDiscountForwardCurve dcReference = OvernightIndexCurve.MakeDC (
			strReferenceCurrency,
			dtValue,
			s_aiUSDOISDepositMaturityDays,
			s_adblUSDOISDepositQuote,
			s_astrUSDShortEndOISMaturityTenor,
			s_adblUSDShortEndOISQuote,
			s_astrUSDOISFutureTenor,
			s_astrUSDOISFutureMaturityTenor,
			s_adblUSDOISFutureQuote,
			s_astrUSDLongEndOISMaturityTenor,
			s_adblUSDLongEndOISQuote,
			scbcCubic,
			null
		);

		ForwardCurve fc6MReference = IBORCurve.CustomIBORBuilderSample (
			dcReference,
			null,
			ForwardLabel.Create (
				strReferenceCurrency,
				"6M"
			),
			scbcCubic,
			s_astrUSD6MDepositTenor,
			s_adblUSD6MDepositQuote,
			"ForwardRate",
			s_astrUSD6MFRATenor,
			s_adblUSD6MFRAQuote,
			"ParForwardRate",
			s_astrUSD6MFixFloatTenor,
			s_adblUSD6MFixFloatQuote,
			"SwapRate",
			null,
			null,
			"DerivedParBasisSpread",
			null,
			null,
			"DerivedParBasisSpread",
			"---- USD LIBOR 6M VANILLA CUBIC POLYNOMIAL FORWARD CURVE ---",
			false
		);

		ForwardCurve fc3MReference = IBORCurve.CustomIBORBuilderSample (
			dcReference,
			fc6MReference,
			ForwardLabel.Create (
				strReferenceCurrency,
				"3M"
			),
			scbcCubic,
			s_astrUSD3MDepositTenor,
			s_adblUSD3MDepositQuote,
			"ForwardRate",
			s_astrUSD3MFRATenor,
			s_adblUSD3MFRAQuote,
			"ParForwardRate",
			s_astrUSD3MFixFloatTenor,
			s_adblUSD3MFixFloatQuote,
			"SwapRate",
			null,
			null,
			"DerivedParBasisSpread",
			s_astrUSD3MSyntheticFloatFloatTenor,
			s_adblUSD3MSyntheticFloatFloatQuote,
			"DerivedParBasisSpread",
			"---- VANILLA CUBIC POLYNOMIAL FORWARD CURVE ---",
			false
		);

		MergedDiscountForwardCurve dcDerived = OvernightIndexCurve.MakeDC (
			strDerivedCurrency,
			dtValue,
			s_aiSEKOISDepositMaturityDays,
			s_adblSEKOISDepositQuote,
			s_astrSEKShortEndOISMaturityTenor,
			s_adblSEKShortEndOISQuote,
			s_astrSEKOISFutureTenor,
			s_astrSEKOISFutureMaturityTenor,
			s_adblSEKOISFutureQuote,
			s_astrSEKLongEndOISMaturityTenor,
			s_adblSEKLongEndOISQuote,
			scbcCubic,
			null
		);

		ForwardCurve fc6MDerived = IBORCurve.CustomIBORBuilderSample (
			dcDerived,
			null,
			ForwardLabel.Create (
				strDerivedCurrency,
				"6M"
			),
			scbcCubic,
			s_astrSEK6MDepositTenor,
			s_adblSEK6MDepositQuote,
			"ForwardRate",
			s_astrSEK6MFRATenor,
			s_adblSEK6MFRAQuote,
			"ParForwardRate",
			s_astrSEK6MFixFloatTenor,
			s_adblSEK6MFixFloatQuote,
			"SwapRate",
			null,
			null,
			"DerivedParBasisSpread",
			null,
			null,
			"DerivedParBasisSpread",
			"---- SEK LIBOR 6M VANILLA CUBIC POLYNOMIAL FORWARD CURVE ---",
			false
		);

		CCBSForwardCurve.ForwardCurveReferenceComponentBasis (
			strReferenceCurrency,
			strDerivedCurrency,
			dtValue,
			dcReference,
			fc6MReference,
			fc3MReference,
			dcDerived,
			fc6MDerived,
			_dblFXSEKUSD,
			scbcCubic,
			s_astrCCBSTenor,
			s_adblCCBSQuote,
			true
		);

		CCBSForwardCurve.ForwardCurveReferenceComponentBasis (
			strReferenceCurrency,
			strDerivedCurrency,
			dtValue,
			dcReference,
			fc6MReference,
			fc3MReference,
			dcDerived,
			fc6MDerived,
			_dblFXSEKUSD,
			scbcCubic,
			s_astrCCBSTenor,
			s_adblCCBSQuote,
			false
		);

		CCBSDiscountCurve.MakeDiscountCurve (
			strReferenceCurrency,
			strDerivedCurrency,
			dtValue,
			dcReference,
			fc6MReference,
			fc3MReference,
			_dblFXSEKUSD,
			scbcCubic,
			s_astrCCBSTenor,
			s_adblCCBSQuote,
			s_adblIRSQuote,
			true
		);

		CCBSDiscountCurve.MakeDiscountCurve (
			strReferenceCurrency,
			strDerivedCurrency,
			dtValue,
			dcReference,
			fc6MReference,
			fc3MReference,
			_dblFXSEKUSD,
			scbcCubic,
			s_astrCCBSTenor,
			s_adblCCBSQuote,
			s_adblIRSQuote,
			false
		);
	}
}
