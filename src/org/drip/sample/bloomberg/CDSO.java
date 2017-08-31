
package org.drip.sample.bloomberg;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.pricer.option.BlackScholesAlgorithm;
import org.drip.product.creator.CDSBuilder;
import org.drip.product.definition.CreditDefaultSwap;
import org.drip.product.option.CDSEuropeanOption;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.creator.ScenarioDeterministicVolatilityBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * CDSO contains the sample demonstrating the replication of Bloomberg's CDSO functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDSO {

	private static final MergedDiscountForwardCurve MarketFundingCurve (
		final JulianDate dtSpot,
		final String strCurrency,
		final double dblBump)
	{
		return LatentMarketStateBuilder.SmoothFundingCurve (
			dtSpot,
			strCurrency,
			new String[] {
				"02D",
				"07D",
				"14D",
				"30D",
				"60D"
			},
			new double[] {
				0.0017 + dblBump,	//  2D
				0.0017 + dblBump,	//  7D
				0.0018 + dblBump,	// 14D
				0.0020 + dblBump,	// 30D
				0.0023 + dblBump	// 60D
			},
			"ForwardRate",
			new double[] {
				0.0027 + dblBump,
				0.0032 + dblBump,
				0.0041 + dblBump,
				0.0054 + dblBump,
				0.0077 + dblBump,
				0.0104 + dblBump,
				0.0134 + dblBump,
				0.0160 + dblBump
			},
			"ForwardRate",
			new String[] {
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
			},
			new double[] {
				0.0166 + dblBump,	//  4Y
				0.0206 + dblBump,	//  5Y
				0.0241 + dblBump,	//  6Y
				0.0269 + dblBump,	//  7Y
				0.0292 + dblBump,	//  8Y
				0.0311 + dblBump,	//  9Y
				0.0326 + dblBump,	// 10Y
				0.0340 + dblBump,	// 11Y
				0.0351 + dblBump,	// 12Y
				0.0375 + dblBump,	// 15Y
				0.0393 + dblBump,	// 20Y
				0.0402 + dblBump,	// 25Y
				0.0407 + dblBump,	// 30Y
				0.0409 + dblBump,	// 40Y
				0.0409 + dblBump	// 50Y
			},
			"SwapRate"
		);
	}

	private static final CreditCurve MarketCreditCurve (
		final JulianDate dtSpot,
		final String strCreditCurve,
		final String strManifestMeasure,
		final MergedDiscountForwardCurve dcFunding,
		final double dblBump,
		final boolean bDisplay)
		throws Exception
	{
		String[] astrCDSMaturityTenor = new String[] {
			"06M",
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] adblCDSParSpread = new double[] {
			100. + dblBump,	//  6M
			100. + dblBump,	//  1Y
			100. + dblBump,	//  2Y
			100. + dblBump,	//  3Y
			100. + dblBump,	//  4Y
			100. + dblBump,	//  5Y
			100. + dblBump,	//  7Y
			100. + dblBump	// 10Y
		};

		CreditCurve cc = LatentMarketStateBuilder.CreditCurve (
			dtSpot,
			strCreditCurve,
			astrCDSMaturityTenor,
			adblCDSParSpread,
			adblCDSParSpread,
			strManifestMeasure,
			dcFunding
		);

		if (!bDisplay) return cc;

		CreditPricerParams pricerParams = CreditPricerParams.Standard();

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setFundingState (dcFunding);

		csqc.setCreditState (cc);

		System.out.println ("\n\t|---------------||");

		System.out.println ("\t| CREDIT SPREAD ||");

		System.out.println ("\t|---------------||");

		for (int i = 0; i < adblCDSParSpread.length; ++i)
			System.out.println (
				"\t| " + astrCDSMaturityTenor[i] + " |" +
				FormatUtil.FormatDouble (
					CDSBuilder.CreateSNAC (
						dtSpot,
						astrCDSMaturityTenor[i],
						0.1,
						strCreditCurve
					).measureValue (
						ValuationParams.Spot (dtSpot.julian()),
						pricerParams,
						csqc,
						null,
						strManifestMeasure
					),
				3, 3, 1.) + " ||"
			);

		System.out.println ("\t|---------------||");

		return cc;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2016,
			DateUtil.FEBRUARY,
			2
		);

		JulianDate dtCashPay = DateUtil.CreateFromYMD (
			2016,
			DateUtil.FEBRUARY,
			5
		);

		String strCurrency = "USD";
		String strCreditCurve = "DB";
		String strCDSForwardTenor = "5Y";
		double dblCDSForwardCoupon = 0.1;
		double dblFairPremiumVolatility = 0.3;
		String strManifestMeasure = "FairPremium";
		double dblNotional = 10000000.;
		double dblCreditBump = 10.;
		double dblFundingBump = .0001;

		MergedDiscountForwardCurve dcFunding = MarketFundingCurve (
			dtSpot,
			strCurrency,
			0.
		);

		CreditCurve cc = MarketCreditCurve (
			dtSpot,
			strCreditCurve,
			strManifestMeasure,
			dcFunding,
			0.,
			true
		);

		CreditPricerParams pricerParams = CreditPricerParams.Standard();

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setFundingState (dcFunding);

		csqc.setCreditState (cc);

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2016,
			DateUtil.MARCH,
			20
		);

		CreditDefaultSwap cdsForward = CDSBuilder.CreateSNAC (
			dtEffective,
			strCDSForwardTenor,
			dblCDSForwardCoupon,
			strCreditCurve
		);

		System.out.println ("\n\t|-------------------------------------|");

		System.out.println ("\t|        UNDERLYING CDS FORWARD       |");

		System.out.println ("\t|-------------------------------------|");

		System.out.println ("\t|  Effective Date    : " + cdsForward.effectiveDate());

		System.out.println ("\t|  Maturity Date     : " + cdsForward.maturityDate());

		System.out.println ("\t|  Notional          : " + FormatUtil.FormatDouble (dblNotional, 1, 0, 1.));

		System.out.println ("\t|  Payment Frequency : " + cdsForward.freq());

		System.out.println ("\t|  Currency          : " + cdsForward.payCurrency());

		System.out.println ("\t|-------------------------------------|\n");

		csqc.setCustomVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtSpot.julian(),
				VolatilityLabel.Standard (CustomLabel.Standard (cdsForward.name() + "_" + strManifestMeasure)),
				strCurrency,
				dblFairPremiumVolatility
			)
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtCashPay,
			strCurrency
		);

		Map<String, Double> mapCDSForwardOutput = cdsForward.value (
			valParams,
			pricerParams,
			csqc,
			null
		);

		double dblStrike = mapCDSForwardOutput.get (strManifestMeasure);

		CDSEuropeanOption cdsOptionPayer = new CDSEuropeanOption (
			cdsForward.name() + "::PAYER_OPT",
			cdsForward,
			strManifestMeasure,
			false,
			dblStrike,
			null,
			new BlackScholesAlgorithm(),
			null
		);

		System.out.println ("\n\t|-------------------------------------|");

		System.out.println ("\t|          OPTION PARAMETERS          |");

		System.out.println ("\t|-------------------------------------|");

		System.out.println ("\t| Payer Swaption?  " + true);

		System.out.println ("\t| Exercise Type  : " + "European");

		System.out.println ("\t| Knock Out?       " + true);

		System.out.println ("\t| Strike         : " + FormatUtil.FormatDouble (dblStrike, 1, 0, 1.));

		System.out.println ("\t| Start Date     : " + dtSpot);

		System.out.println ("\t| Cash Pay Date  : " + dtCashPay);

		System.out.println ("\t| Exercise Date  : " + cdsOptionPayer.exerciseDate());

		System.out.println ("\t|-------------------------------------|\n");

		Map<String, Double> mapPayerOptionOutput = cdsOptionPayer.value (
			valParams,
			null,
			csqc,
			null
		);

		double dblOptionPriceBase = mapPayerOptionOutput.get ("Price") / cdsForward.initialNotional();

		CreditCurve ccBumpUp = MarketCreditCurve (
			dtSpot,
			strCreditCurve,
			strManifestMeasure,
			dcFunding,
			dblCreditBump,
			false
		);

		CreditCurve ccBumpDown = MarketCreditCurve (
			dtSpot,
			strCreditCurve,
			strManifestMeasure,
			dcFunding,
			-dblCreditBump,
			false
		);

		csqc.setCreditState (ccBumpUp);

		Map<String, Double> mapPayerOptionOutputCreditBumpUp = cdsOptionPayer.value (
			valParams,
			null,
			csqc,
			null
		);

		double dblOptionPriceCreditBumpUp = mapPayerOptionOutputCreditBumpUp.get ("Price") / cdsForward.initialNotional();

		csqc.setCreditState (ccBumpDown);

		Map<String, Double> mapPayerOptionOutputCreditBumpDown = cdsOptionPayer.value (
			valParams,
			null,
			csqc,
			null
		);

		double dblOptionPriceCreditBumpDown = mapPayerOptionOutputCreditBumpDown.get ("Price") / cdsForward.initialNotional();

		MergedDiscountForwardCurve dcFundingBumpUp = MarketFundingCurve (
			dtSpot,
			strCurrency,
			dblFundingBump
		);

		csqc.setFundingState (dcFundingBumpUp);

		csqc.setCreditState (cc);

		Map<String, Double> mapPayerOptionOutputFundingBumpUp = cdsOptionPayer.value (
			valParams,
			null,
			csqc,
			null
		);

		double dblOptionPriceFundingBumpUp = mapPayerOptionOutputFundingBumpUp.get ("Price") / cdsForward.initialNotional();

		MergedDiscountForwardCurve dcFundingBumpDown = MarketFundingCurve (
			dtSpot,
			strCurrency,
			-dblFundingBump
		);

		csqc.setFundingState (dcFundingBumpDown);

		Map<String, Double> mapPayerOptionOutputFundingBumpDown = cdsOptionPayer.value (
			valParams,
			null,
			csqc,
			null
		);

		double dblOptionPriceFundingBumpDown = mapPayerOptionOutputFundingBumpDown.get ("Price") / cdsForward.initialNotional();

		System.out.println ("\n\t|-----------------------------------------------|");

		System.out.println ("\t|           OPTION INPUTS AND PRICING           |");

		System.out.println ("\t|-----------------------------------------------|");

		System.out.println ("\t| Valuation Date          : " + dtSpot);

		System.out.println ("\t| Fair Premium Volatility : " + FormatUtil.FormatDouble (dblFairPremiumVolatility, 1, 3, 100.) + "%");

		System.out.println ("\t| Option Premium          : " + FormatUtil.FormatDouble (dblOptionPriceBase, 1, 5, 100.) + "%");

		System.out.println ("\t| Option MTM              : " + FormatUtil.FormatDouble (dblOptionPriceBase * dblNotional, 1, 2, 1.));

		System.out.println ("\t| Credit Spread Delta 01  : " + FormatUtil.FormatDouble (0.5 * (dblOptionPriceCreditBumpUp - dblOptionPriceCreditBumpDown) * dblNotional / dblCreditBump, 1, 2, 1.));

		System.out.println ("\t| Credit Spread Gamma 01  : " + FormatUtil.FormatDouble (0.5 * (dblOptionPriceCreditBumpUp + dblOptionPriceCreditBumpDown - 2. * dblOptionPriceBase) * dblNotional / (dblCreditBump * dblCreditBump), 1, 2, 1.));

		System.out.println ("\t| Funding Spread Delta 01 : " + FormatUtil.FormatDouble (0.5 * (dblOptionPriceFundingBumpUp - dblOptionPriceFundingBumpDown) * dblNotional / (10000. * dblFundingBump), 1, 2, 1.));

		System.out.println ("\t| Funding Spread Gamma 01 : " + FormatUtil.FormatDouble (0.5 * (dblOptionPriceFundingBumpUp + dblOptionPriceFundingBumpDown - 2. * dblOptionPriceBase) * dblNotional / (10000. * 10000. * dblFundingBump * dblFundingBump), 1, 2, 1.));

		System.out.println ("\t| ATM Forward             : " + FormatUtil.FormatDouble (mapPayerOptionOutput.get ("ATMManifestMeasure"), 1, 0, 1.));

		System.out.println ("\t| FPG Delta               : " + FormatUtil.FormatDouble (mapPayerOptionOutput.get ("FPGDelta"), 1, 4, 1.));

		System.out.println ("\t| FPG Gamma               : " + FormatUtil.FormatDouble (mapPayerOptionOutput.get ("FPGGamma"), 1, 4, 1.));

		System.out.println ("\t| FPG Vega                : " + FormatUtil.FormatDouble (mapPayerOptionOutput.get ("FPGVega"), 1, 2, 1.));

		System.out.println ("\t| FPG Theta               : " + FormatUtil.FormatDouble (mapPayerOptionOutput.get ("FPGTheta"), 1, 2, 1.));

		System.out.println ("\t| Vega (1%)               : " + FormatUtil.FormatDouble (mapPayerOptionOutput.get ("Vega") / cdsForward.initialNotional() * dblNotional * 0.01, 1, 2, 1.));

		System.out.println ("\t| Theta                   : " + FormatUtil.FormatDouble (mapPayerOptionOutput.get ("Theta") / cdsForward.initialNotional() * dblNotional / 365.25, 1, 2, 1.));

		System.out.println ("\t|-----------------------------------------------|\n");
	}
}
