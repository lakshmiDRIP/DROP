
package org.drip.sample.intexfeed;

import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.Helper;
import org.drip.market.otc.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
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
 * BrokenDateLIBORSpot generates the LIBOR's at the Broken Date Tenors in the Currency specified.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BrokenDateLIBORSpot {

	private static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] astrDepositMaturityTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.0130411 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01345,	// 98.655
			0.01470,	// 98.530
			0.01575,	// 98.425
			0.01660,	// 98.340
			0.01745,  	// 98.255
			0.01845   	// 98.155
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
			0.016410, //  2Y
			0.017863, //  3Y
			0.019030, //  4Y
			0.020035, //  5Y
			0.020902, //  6Y
			0.021660, //  7Y
			0.022307, //  8Y
			0.022879, //  9Y
			0.023363, // 10Y
			0.023820, // 11Y
			0.024172, // 12Y
			0.024934, // 15Y
			0.025581, // 20Y
			0.025906, // 25Y
			0.025973, // 30Y
			0.025838, // 40Y
			0.025560  // 50Y
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
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.OCTOBER,
			5
		);

		int iNumMonth = 720;
		String strCurrency = "USD";

		FixedFloatSwapConvention ffsc = IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency);

		ForwardLabel forwardLabel = ffsc.floatStreamConvention().floaterIndex();

		String strLIBORDayCount = forwardLabel.floaterIndex().dayCount();

		int iLIBORFreq = Helper.TenorToFreq (forwardLabel.tenor());

		MergedDiscountForwardCurve mdfc = FundingCurve (
			dtSpot,
			strCurrency
		);

		System.out.println ("SpotDate,ForwardTenor,ForwardDate,ZeroPrice,LIBOR,ZeroRate");

		for (int i = 1; i <= iNumMonth; ++i) {
			String strTenor = i + "M";

			JulianDate dtForward = dtSpot.addMonths (i);

			double dblDFForward = mdfc.df (strTenor);

			double dblLIBOR = Helper.DF2Yield (
				iLIBORFreq,
				dblDFForward,
				Convention.YearFraction (
					dtSpot.julian(),
					dtForward.julian(),
					strLIBORDayCount,
					false,
					null,
					strCurrency
				)
			);

			double dblZero = Helper.DF2Yield (
				4,
				dblDFForward,
				Convention.YearFraction (
					dtSpot.julian(),
					dtForward.julian(),
					"30/360",
					false,
					null,
					strCurrency
				)
			);

			System.out.println (
				dtSpot + "," +
				strTenor + "," +
				dtForward + "," +
				FormatUtil.FormatDouble (dblDFForward, 1, 6, 1.) + "," +
				FormatUtil.FormatDouble (dblLIBOR, 1, 6, 100.) + "%," +
				FormatUtil.FormatDouble (dblZero, 1, 6, 100.) + "%"
			);
		}
	}
}
