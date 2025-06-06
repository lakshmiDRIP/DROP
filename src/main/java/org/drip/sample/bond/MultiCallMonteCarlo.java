
package org.drip.sample.bond;

import org.drip.analytics.date.*;
import org.drip.analytics.output.BondEOSMetrics;
import org.drip.measure.dynamics.DiffusionEvaluatorLogarithmic;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.product.params.EmbeddedOptionSchedule;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.sequence.GovvieBuilderSettings;

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
 * <i>MultiCallMonteCarlo</i> demonstrates the Simulations of the Path/Vertex EOS Bond Metrics.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bond/README.md">Bullet, EOS Bond Metrics + Curve</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MultiCallMonteCarlo {

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
			24
		);

		int iNumPath = 50;
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

		boolean[][] aabExerciseIndicator = bem.exerciseIndicator();

		double[][] aadblForwardPrice = bem.forwardPrice();

		int iNumVertex = aabExerciseIndicator[0].length;

		System.out.println();

		System.out.println ("\t||-------------------------------------------------------------------------------||");

		System.out.println ("\t||                          FORWARD EXERCISE INDICATOR                           ||");

		System.out.println ("\t||-------------------------------------------------------------------------------||");

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			String strDump = "\t||";

			for (int iVertex = 0; iVertex < iNumVertex; ++iVertex)
				strDump = strDump + (aabExerciseIndicator[iPath][iVertex] ? " Y" : " N") + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||-------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                                                                FORWARD BOND PRICE                                                                                 ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			String strDump = "\t||";

			for (int iVertex = 0; iVertex < iNumVertex; ++iVertex)
				strDump = strDump + FormatUtil.FormatDouble (aadblForwardPrice[iPath][iVertex], 3, 2, 100) + " |";

			System.out.println (strDump + "|");
		}

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
