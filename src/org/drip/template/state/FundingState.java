
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
 * FundingState sets up the Calibration of the Funding Latent State and examine the Emitted Metrics.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingState {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "USD";

		JulianDate dtSpot = DateUtil.Today().addBusDays (
			0,
			strCurrency
		);

		String[] astrDepositMaturityTenor = new String[] {
			"01D",
			"04D",
			"07D",
			"14D",
			"30D",
			"60D"
		};

		double[] adblDepositQuote = new double[] {
			0.0013,		//  1D
			0.0017,		//  2D
			0.0017,		//  7D
			0.0018,		// 14D
			0.0020,		// 30D
			0.0023		// 60D
		};

		double[] adblFuturesQuote = new double[] {
			0.0027,
			0.0032,
			0.0041,
			0.0054,
			0.0077,
			0.0104,
			0.0134,
			0.0160
		};

		String[] astrFixFloatMaturityTenor = new String[] {
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
			0.0166,		//   4Y
			0.0206,		//   5Y
			0.0241,		//   6Y
			0.0269,		//   7Y
			0.0292,		//   8Y
			0.0311,		//   9Y
			0.0326,		//  10Y
			0.0340,		//  11Y
			0.0351,		//  12Y
			0.0375,		//  15Y
			0.0393,		//  20Y
			0.0402,		//  25Y
			0.0407,		//  30Y
			0.0409,		//  40Y
			0.0409		//  50Y
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

		String strLatentStateLabel = dcFunding.label().fullyQualifiedName();

		System.out.println ("\n\n\t||---------------------------------------------------------------||");

		for (int i = 0; i < adblDepositQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel +
				" |  DEPOSIT  | " + astrDepositMaturityTenor[i] + "  | " +
				FormatUtil.FormatDouble (adblDepositQuote[i], 1, 4, 1.) +
				" | Forward Rate | " +
				FormatUtil.FormatDouble (dcFunding.df (astrDepositMaturityTenor[i]), 1, 6, 1.) +
				"  ||"
			);

		System.out.println ("\t||---------------------------------------------------------------||");

		System.out.println ("\n\n\t||--------------------------------------------------------||");

		for (int i = 0; i < adblFuturesQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  FUTURES  | " +
				FormatUtil.FormatDouble (adblFuturesQuote[i], 1, 4, 1.) +
				" | Forward Rate | " +
				FormatUtil.FormatDouble (dcFunding.df ((3 + 3 * i) + "M"), 1, 6, 1.) +
				"  ||"
			);

		System.out.println ("\t||--------------------------------------------------------||");

		System.out.println ("\n\n\t||--------------------------------------------------------------||");

		for (int i = 0; i < adblFixFloatQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel +
				" |  FIX FLOAT  | " + astrFixFloatMaturityTenor[i] + "  | " +
				FormatUtil.FormatDouble (adblFixFloatQuote[i], 1, 4, 1.) +
				" | Swap Rate | " +
				FormatUtil.FormatDouble (dcFunding.df (astrFixFloatMaturityTenor[i]), 1, 6, 1.) +
				"  ||"
			);

		System.out.println ("\t||--------------------------------------------------------------||\n");
	}
}
