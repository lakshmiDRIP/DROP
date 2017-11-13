
package org.drip.sample.securitysuite;

import java.util.Map;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.CDSBuilder;
import org.drip.product.definition.CreditDefaultSwap;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * CreditDefaultSwapIndex demonstrates the Analytics Calculation/Reconciliation for a CDX.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditDefaultSwapIndex {

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
			0.013161 + dblBump // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.013225 + dblBump,	// 98.6775
			0.014250 + dblBump,	// 98.5750
			0.014750 + dblBump,	// 98.5250
			0.015250 + dblBump,	// 98.4750
			0.015750 + dblBump,  // 98.4250
			0.016500 + dblBump   // 98.3500
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
			0.015540 + dblBump, //  2Y
			0.016423 + dblBump, //  3Y
			0.017209 + dblBump, //  4Y
			0.017980 + dblBump, //  5Y
			0.018743 + dblBump, //  6Y
			0.019455 + dblBump, //  7Y
			0.020080 + dblBump, //  8Y
			0.020651 + dblBump, //  9Y
			0.021195 + dblBump, // 10Y
			0.021651 + dblBump, // 11Y
			0.022065 + dblBump, // 12Y
			0.022952 + dblBump, // 15Y
			0.023825 + dblBump, // 20Y
			0.024175 + dblBump, // 25Y
			0.024347 + dblBump, // 30Y
			0.024225 + dblBump, // 40Y
			0.023968 + dblBump  // 50Y
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

	private static final CreditCurve CreditCurve (
		final JulianDate dtSpot,
		final String strCreditCurve,
		final MergedDiscountForwardCurve mdfc,
		final double dblBump)
		throws Exception
	{
		return LatentMarketStateBuilder.CreditCurve (
			dtSpot,
			strCreditCurve,
			new String[] {
				 "6M",
				 "1Y",
				 "2Y",
				 "3Y",
				 "4Y",
				 "5Y",
				 "7Y",
				"10Y",
				"20Y",
				"30Y",
			},
			new double[] {
				392.509,	//  6M
				320.707,	//  1Y
				393.624,	//  2Y
				472.869,	//  3Y
				570.360,	//  4Y
				663.920,	//  5Y
				779.463,	//  7Y
				957.555, 	// 10Y
				908.712, 	// 20Y
				900.297, 	// 30Y
			},
			new double[] {
				392.509,	//  6M
				320.707,	//  1Y
				393.624,	//  2Y
				472.869,	//  3Y
				570.360,	//  4Y
				663.920,	//  5Y
				779.463,	//  7Y
				957.555, 	// 10Y
				908.712, 	// 20Y
				900.297, 	// 30Y
			},
			"FairPremium",
			mdfc
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.SEPTEMBER,
			17
		);

		JulianDate dtIssue = DateUtil.CreateFromYMD (
			2017,
			DateUtil.JUNE,
			20
		);

		String strCDXTenor = "5Y";
		String strCurrency = "USD";
		String strCDXName = "CDXNAHY";
		double dblCDXFixedCoupon = 0.05;

		CreditDefaultSwap cdx = CDSBuilder.CreateSNAC (
			dtIssue,
			strCDXTenor,
			dblCDXFixedCoupon,
			strCDXName
		);

		MergedDiscountForwardCurve mdfc = FundingCurve (
			dtSpot,
			strCurrency,
			0.
		);

		CreditCurve cc = CreditCurve (
			dtSpot,
			strCDXName,
			mdfc,
			0.
		);

		CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Credit (
			mdfc,
			cc
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		CreditPricerParams pricerParams = CreditPricerParams.Standard();

		CaseInsensitiveTreeMap<Double> mapOutput = cdx.value (
			valParams,
			pricerParams,
			csqc,
			null
		);

		System.out.println ("");

		System.out.println ("\t |-----------------------------------------------|");

		for (Map.Entry<String, Double> me : mapOutput.entrySet())
			System.out.println ("\t | " + me.getKey() + " => " + me.getValue());

		System.out.println ("\t |-----------------------------------------------|");

		System.out.println ("");

		System.out.println ("\t |---------------------------------------------------------------------------||");

		for (CompositePeriod p : cdx.couponPeriods())
			System.out.println (
				"\t | " +
				DateUtil.YYYYMMDD (p.startDate()) + " | " +
				DateUtil.YYYYMMDD (p.endDate()) + " | " +
				DateUtil.YYYYMMDD (p.payDate()) + " | " +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 2, 0.01 * 1.) + " | " +
				FormatUtil.FormatDouble (mdfc.df (p.payDate()), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (cc.survival (p.payDate()), 1, 4, 1.) + " ||"
			);

		System.out.println ("\t |---------------------------------------------------------------------------||");
	}
}
