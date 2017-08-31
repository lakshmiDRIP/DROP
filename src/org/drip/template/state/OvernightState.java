
package org.drip.template.state;

import org.drip.analytics.date.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;

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
 * OvernightState sets up the Calibration and the Construction of the Overnight Latent State and examine the
 *  Emitted Metrics.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OvernightState {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "EUR";

		JulianDate dtSpot = DateUtil.Today();

		String[] astrDepositMaturityTenor = new String[] {
			"1D",
			"2D",
			"3D"
		};

		double[] adblDepositQuote = new double[] {
			0.0004,		// 1D
			0.0004,		// 2D
			0.0004		// 3D
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

		MergedDiscountForwardCurve dcOvernight = LatentMarketStateBuilder.SmoothOvernightCurve (
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

		String strLatentStateLabel = dcOvernight.label().fullyQualifiedName();

		System.out.println ("\n\n\t||------------------------------------------------------------------||");

		for (int i = 0; i < adblDepositQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel +
				" |  DEPOSIT  | " + astrDepositMaturityTenor[i] + "  | " +
				FormatUtil.FormatDouble (adblDepositQuote[i], 1, 4, 1.) +
				" | Forward Rate | " +
				FormatUtil.FormatDouble (dcOvernight.df (astrDepositMaturityTenor[i]), 1, 10, 1.) +
				"  ||"
			);

		System.out.println ("\t||------------------------------------------------------------------||");

		System.out.println ("\n\n\t||------------------------------------------------------------------||");

		for (int i = 0; i < adblShortEndOISQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  SHORT END OIS  | " +
				astrShortEndOISMaturityTenor[i] + "  | " + FormatUtil.FormatDouble (adblShortEndOISQuote[i], 1, 5, 1.) +
				" | Swap Rate | " +
				FormatUtil.FormatDouble (dcOvernight.df (astrShortEndOISMaturityTenor[i]), 1, 6, 1.) +
				"  ||"
			);

		System.out.println ("\t||------------------------------------------------------------------||");

		System.out.println ("\n\n\t||---------------------------------------------------------------------||");

		for (int i = 0; i < adblOISFuturesQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  OIS FUTURES  | " +
				FormatUtil.FormatDouble (adblOISFuturesQuote[i], 1, 6, 1.) +
				" | " + astrOISFuturesEffectiveTenor[i] + " x " + astrOISFuturesMaturityTenor[i] +
				" | Swap Rate | " +
				FormatUtil.FormatDouble (
					dcOvernight.df (dtSpot.addTenor (astrOISFuturesEffectiveTenor[i]).addTenor (astrOISFuturesMaturityTenor[i])
				), 1, 6, 1.) +
				"  ||"
			);

		System.out.println ("\t||---------------------------------------------------------------------||");

		System.out.println ("\n\n\t||---------------------------------------------------------------||");

		for (int i = 0; i < adblLongEndOISQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  LONG END OIS  | " +
				astrLongEndOISMaturityTenor[i] + " | " + FormatUtil.FormatDouble (adblLongEndOISQuote[i], 1, 5, 1.) +
				" | Swap Rate | " +
				FormatUtil.FormatDouble (dcOvernight.df (astrLongEndOISMaturityTenor[i]), 1, 4, 1.) +
				"  ||"
			);

		System.out.println ("\t||---------------------------------------------------------------||\n");
	}
}
