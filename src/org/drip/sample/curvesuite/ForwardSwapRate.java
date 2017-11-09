
package org.drip.sample.curvesuite;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.market.otc.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.rates.FixFloatComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;

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
 * ForwardSwapRate generates the Forward Swap Rates over Monthly Increments with Maturity up to 60 Years for
 *  different Swap Tenors.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ForwardSwapRate {

	private static final FixFloatComponent OTCIRS (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strMaturityTenor)
	{
		FixedFloatSwapConvention ffsc = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"NYC",
			strMaturityTenor,
			"MAIN"
		);

		return ffsc.createFixFloatComponent (
			dtSpot,
			strMaturityTenor,
			0.,
			0.,
			1.
		);
	}

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
		final String[] astrArgs)
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
		String[] astrSwapTenor = new String[] {
			 "2Y",
			 "5Y",
			"10Y",
			"15Y",
			"20Y",
			"30Y"
		};

		MergedDiscountForwardCurve mdfc = FundingCurve (
			dtSpot,
			strCurrency
		);

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Create (
			mdfc,
			null,
			null,
			null,
			null,
			null,
			null
		);

		System.out.println ("SpotDate,ForwardDate,MaturityTenor,MaturityDate,SwapRate,SpotDV01,ForwardDV01");

		for (int i = 0; i <= iNumMonth; ++i) {
			JulianDate dtForward = dtSpot.addMonths (i);

			double dblDFForward = mdfc.df (dtForward);

			for (int j = 0; j < astrSwapTenor.length; ++j) {
				FixFloatComponent ffc = OTCIRS (
					dtForward,
					strCurrency,
					astrSwapTenor[j]
				);

				Map<String, Double> mapOutput = ffc.value (
					valParams,
					null,
					csqc,
					null
				);

				double dblForwardSwapRate = mapOutput.get ("SwapRate");

				double dblForwardSwapDV01 = mapOutput.get ("CleanFixedDV01");

				System.out.println (
					dtSpot + "," +
					dtForward + "," +
					astrSwapTenor[j] + "," +
					ffc.maturityDate() + "," +
					FormatUtil.FormatDouble (dblForwardSwapRate, 1, 8, 100.) + "%" + "," +
					FormatUtil.FormatDouble (dblForwardSwapDV01, 1, 8, 10000.) + "," +
					FormatUtil.FormatDouble (dblForwardSwapDV01 / dblDFForward, 1, 8, 10000.)
				);
			}
		}
	}
}
