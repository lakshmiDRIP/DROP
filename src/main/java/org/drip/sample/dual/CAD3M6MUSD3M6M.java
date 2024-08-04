
package org.drip.sample.dual;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1custom.QuadraticRationalShapeControl;
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
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>CAD3M6MUSD3M6M</i> demonstrates the setup and construction of the USD 3M Forward Curve from
 * CAD3M6MUSD3M6M CCBS, CAD 3M, CAD 6M, and USD 6M Quotes.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cross/README.md">G7 Standard Cross Currency Swap</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CAD3M6MUSD3M6M {
	private static final double _dblFXCADUSD = 0.9168;

	private static final int[] s_aiUSDOISDepositMaturityDays = new int[] {
		1,
		2,
		3
	};

	private static final int[] s_aiCADOISDepositMaturityDays = new int[] {
		1,
		2,
		3
	};

	private static final double[] s_adblUSDOISDepositQuote = new double[] {
		0.0004,	// 1D
		0.0004,	// 2D
		0.0004	// 3D
	};

	private static final double[] s_adblCADOISDepositQuote = new double[] {
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

	private static final String[] s_astrCADShortEndOISMaturityTenor = new String[] {
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

	private static final double[] s_adblCADShortEndOISQuote = new double[] {
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

	private static final String[] s_astrCADOISFutureTenor = new String[] {
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

	private static final String[] s_astrCADOISFutureMaturityTenor = new String[] {
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

	private static final double[] s_adblCADOISFutureQuote = new double[] {
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

	private static final String[] s_astrCADLongEndOISMaturityTenor = new String[] {
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

	private static final double[] s_adblCADLongEndOISQuote = new double[] {
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

	private static final String[] s_astrCAD6MDepositTenor = new String[] {
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

	private static final double[] s_adblCAD6MDepositQuote = new double[] {
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

	private static final String[] s_astrCAD6MFRATenor = new String[] {
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

	private static final double[] s_adblCAD6MFRAQuote = new double[] {
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

	private static final String[] s_astrCAD6MFixFloatTenor = new String[] {
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

	private static final double[] s_adblCAD6MFixFloatQuote = new double[] {
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
		0.001050, //  1Y
		0.001150, //  2Y
		0.001225, //  3Y
		0.001325, //  4Y
		0.001425, //  5Y
		0.001500, //  7Y
		0.001500, // 10Y
		0.001525, // 15Y
		0.001525  // 20Y
	};

	private static final double[] s_adblIRSQuote = new double[] {
		0.01050, //  1Y
		0.01150, //  2Y
		0.01225, //  3Y
		0.01325, //  4Y
		0.01425, //  5Y
		0.01500, //  7Y
		0.01500, // 10Y
		0.01525, // 15Y
		0.01525  // 20Y
	};

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
		String strDerivedCurrency = "CAD";

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

		MergedDiscountForwardCurve dcDerived = OvernightIndexCurve.MakeDC (
			strDerivedCurrency,
			dtValue,
			s_aiCADOISDepositMaturityDays,
			s_adblCADOISDepositQuote,
			s_astrCADShortEndOISMaturityTenor,
			s_adblCADShortEndOISQuote,
			s_astrCADOISFutureTenor,
			s_astrCADOISFutureMaturityTenor,
			s_adblCADOISFutureQuote,
			s_astrCADLongEndOISMaturityTenor,
			s_adblCADLongEndOISQuote,
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

		ForwardCurve fc6MDerived = IBORCurve.CustomIBORBuilderSample (
			dcDerived,
			null,
			ForwardLabel.Create (
				strDerivedCurrency,
				"6M"
			),
			scbcCubic,
			s_astrCAD6MDepositTenor,
			s_adblCAD6MDepositQuote,
			"ForwardRate",
			s_astrCAD6MFRATenor,
			s_adblCAD6MFRAQuote,
			"ParForwardRate",
			s_astrCAD6MFixFloatTenor,
			s_adblCAD6MFixFloatQuote,
			"SwapRate",
			null,
			null,
			"DerivedParBasisSpread",
			null,
			null,
			"DerivedParBasisSpread",
			"---- CAD LIBOR 6M VANILLA CUBIC POLYNOMIAL FORWARD CURVE ---",
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

		CCBSForwardCurve.ForwardCurveReferenceComponentBasis (
			strReferenceCurrency,
			strDerivedCurrency,
			dtValue,
			dcReference,
			fc6MReference,
			fc3MReference,
			dcDerived,
			fc6MDerived,
			_dblFXCADUSD,
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
			_dblFXCADUSD,
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
			_dblFXCADUSD,
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
			_dblFXCADUSD,
			scbcCubic,
			s_astrCCBSTenor,
			s_adblCCBSQuote,
			s_adblIRSQuote,
			false
		);

		EnvManager.TerminateEnv();
	}
}
