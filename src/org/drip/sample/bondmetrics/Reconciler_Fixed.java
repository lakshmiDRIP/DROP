
package org.drip.sample.bondmetrics;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.*;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.product.params.EmbeddedOptionSchedule;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.scenario.*;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.creator.ScenarioCreditCurveBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;

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
 * Reconciler_Fixed demonstrates the Analytics Calculation/Reconciliation for the the Fixed Coupon Bond
 *  MCQGQO.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Reconciler_Fixed {

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
			0.0130411 + dblBump // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01345 + dblBump,	// 98.655
			0.01470 + dblBump,	// 98.530
			0.01575 + dblBump,	// 98.425
			0.01660 + dblBump,	// 98.340
			0.01745 + dblBump,  // 98.255
			0.01845 + dblBump   // 98.155
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
			0.016410 + dblBump, //  2Y
			0.017863 + dblBump, //  3Y
			0.019030 + dblBump, //  4Y
			0.020035 + dblBump, //  5Y
			0.020902 + dblBump, //  6Y
			0.021660 + dblBump, //  7Y
			0.022307 + dblBump, //  8Y
			0.022879 + dblBump, //  9Y
			0.023363 + dblBump, // 10Y
			0.023820 + dblBump, // 11Y
			0.024172 + dblBump, // 12Y
			0.024934 + dblBump, // 15Y
			0.025581 + dblBump, // 20Y
			0.025906 + dblBump, // 25Y
			0.025973 + dblBump, // 30Y
			0.025838 + dblBump, // 40Y
			0.025560 + dblBump  // 50Y
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

	private static final Map<String, MergedDiscountForwardCurve> TenorBumpedFundingCurve (
		final JulianDate dtSpot,
		final String strCurrency,
		final double dblBump)
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
			0.01745,    // 98.255
			0.01845     // 98.155
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

		return LatentMarketStateBuilder.BumpedFundingCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			adblFuturesQuote,
			"ForwardRate",
			astrFixFloatMaturityTenor,
			adblFixFloatQuote,
			"SwapRate",
			LatentMarketStateBuilder.SMOOTH,
			dblBump,
			false
		);
	}

	private static final GovvieCurve GovvieCurve (
		final JulianDate dtSpot,
		final String strCode,
		final double[] adblGovvieYield)
		throws Exception
	{
		return LatentMarketStateBuilder.GovvieCurve (
			strCode,
			dtSpot,
			new JulianDate[] {
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot
			},
			new JulianDate[] {
				dtSpot.addTenor ("1Y"),
				dtSpot.addTenor ("2Y"),
				dtSpot.addTenor ("3Y"),
				dtSpot.addTenor ("5Y"),
				dtSpot.addTenor ("7Y"),
				dtSpot.addTenor ("10Y"),
				dtSpot.addTenor ("20Y"),
				dtSpot.addTenor ("30Y")
			},
			adblGovvieYield,
			adblGovvieYield,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);
	}

	private static final Map<String, GovvieCurve> TenorBumpedGovvieCurve (
		final JulianDate dtSpot,
		final String strCode,
		final double dblBump,
		final double[] adblGovvieYield)
		throws Exception
	{
		return LatentMarketStateBuilder.BumpedGovvieCurve (
			strCode,
			dtSpot,
			new JulianDate[] {
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot,
				dtSpot
			},
			new JulianDate[] {
				dtSpot.addTenor ("1Y"),
				dtSpot.addTenor ("2Y"),
				dtSpot.addTenor ("3Y"),
				dtSpot.addTenor ("5Y"),
				dtSpot.addTenor ("7Y"),
				dtSpot.addTenor ("10Y"),
				dtSpot.addTenor ("20Y"),
				dtSpot.addTenor ("30Y")
			},
			adblGovvieYield,
			adblGovvieYield,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING,
			dblBump,
			false
		);
	}

	private static final CreditCurve CreditCurve (
		final JulianDate dtSpot,
		final String strCreditCurve,
		final MergedDiscountForwardCurve mdfc,
		final String[] astrCreditTenor,
		final double dblBump)
		throws Exception
	{
		CreditCurve cc = ScenarioCreditCurveBuilder.Survival (
			dtSpot.julian(),
			strCreditCurve,
			mdfc.currency(),
			astrCreditTenor,
			new double[] {
				 0.995 - 0.0001 * dblBump,	//  6M
				 0.990 - 0.0001 * dblBump,	//  1Y
				 0.980 - 0.0001 * dblBump,	//  2Y
				 0.970 - 0.0001 * dblBump,	//  3Y
				 0.960 - 0.0001 * dblBump,	//  4Y
				 0.950 - 0.0001 * dblBump,	//  5Y
				 0.940 - 0.0001 * dblBump,	//  7Y
				 0.910 - 0.0001 * dblBump	// 10Y
			},
			0.4
		);

		return cc;
	}

	private static final Map<String, CreditCurve> TenorBumpedCreditCurve (
		final JulianDate dtSpot,
		final String strCreditCurve,
		final MergedDiscountForwardCurve mdfc,
		final double dblBump)
		throws Exception
	{
		return LatentMarketStateBuilder.BumpedCreditCurve (
			dtSpot,
			strCreditCurve,
			new String[] {
				"06M",
				"01Y",
				"02Y",
				"03Y",
				"04Y",
				"05Y",
				"07Y",
				"10Y"
			},
			new double[] {
				 60.,	//  6M
				 68.,	//  1Y
				 88.,	//  2Y
				102.,	//  3Y
				121.,	//  4Y
				138.,	//  5Y
				168.,	//  7Y
				188.	// 10Y
			},
			new double[] {
				 60.,	//  6M
				 68.,	//  1Y
				 88.,	//  2Y
				102.,	//  3Y
				121.,	//  4Y
				138.,	//  5Y
				168.,	//  7Y
				188.	// 10Y
			},
			"FairPremium",
			mdfc,
			dblBump,
			false
		);
	}

	private static final void SetEOS (
		final BondComponent bond,
		final EmbeddedOptionSchedule eosCall,
		final EmbeddedOptionSchedule eosPut)
		throws java.lang.Exception
	{
		if (null != eosPut) bond.setEmbeddedPutSchedule (eosPut);

		if (null != eosCall) bond.setEmbeddedCallSchedule (eosCall);
	}

	private static final int NextCallDate (
		final BondComponent bond,
		final int iSpotDate)
		throws java.lang.Exception
	{
		EmbeddedOptionSchedule eosCall = bond.callSchedule();

		return null == eosCall ? bond.maturityDate().julian() : eosCall.nextDate (iSpotDate);
	}

	private static final double NextCallFactor (
		final BondComponent bond,
		final int iSpotDate)
		throws java.lang.Exception
	{
		EmbeddedOptionSchedule eosCall = bond.callSchedule();

		return null == eosCall ? 1. : eosCall.nextFactor (iSpotDate);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.JULY,
			10
		);

		String[] astrDepositTenor = new String[] {
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
			0.01745,    // 98.255
			0.01845     // 98.155
		};

		String[] astrFixFloatTenor = new String[] {
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

		String[] astrGovvieTenor = new String[] {
			"1Y",
			"2Y",
			"3Y",
			"5Y",
			"7Y",
			"10Y",
			"20Y",
			"30Y"
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

		double[] adblGovvieYield = new double[] {
			0.01219, //  1Y
			0.01391, //  2Y
			0.01590, //  3Y
			0.01937, //  5Y
			0.02200, //  7Y
			0.02378, // 10Y
			0.02677, // 20Y
			0.02927  // 30Y
		};

		String[] astrCreditTenor = new String[] {
			"06M",
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] adblCreditQuote = new double[] {
			 60.,	//  6M
			 68.,	//  1Y
			 88.,	//  2Y
			102.,	//  3Y
			121.,	//  4Y
			138.,	//  5Y
			168.,	//  7Y
			188.	// 10Y
		};

		double dblFX = 1.;
		int iSettleLag = 3;
		int iCouponFreq = 12;
		String strName = "MCQGQO";
		double dblCleanPrice = 1.;
		double dblIssuePrice = 1.;
		String strCurrency = "USD";
		double dblSpreadBump = 20.;
		double dblCouponRate = 0.0425;
		double dblIssueAmount = 2.60e7;
		String strTreasuryCode = "UST";
		String strCouponDayCount = "30/360";
		double dblSpreadDurationMultiplier = 5.;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2015,
			9,
			18
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2025,
			10,
			1
		);

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			strName,
			strCurrency,       // Coupon Currency
			strName,
			dblCouponRate,     // Coupon Rate
			iCouponFreq,       // Coupon Frequency
			strCouponDayCount, // Coupon Day Count
			dtEffective,       // Effective Date
			dtMaturity,        // Maturity Date
			null,
			null
		);

		SetEOS (
			bond,
			null,
			null
		);

		int iSpotDate = dtSpot.julian();

		JulianDate dtSettle = dtSpot.addBusDays (
			iSettleLag,
			strCurrency
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSettle,
			strCurrency
		);

		MergedDiscountForwardCurve mdfcBase = FundingCurve (
			dtSpot,
			strCurrency,
			0.
		);

		GovvieCurve gc = GovvieCurve (
			dtSpot,
			strTreasuryCode,
			adblGovvieYield
		);

		CurveSurfaceQuoteContainer csqcBase = MarketParamsBuilder.Create (
			mdfcBase,
			gc,
			null,
			null,
			null,
			null,
			null
		);

		CurveSurfaceQuoteContainer csqcCreditBase = MarketParamsBuilder.Create (
			mdfcBase,
			gc,
			CreditCurve (
				dtSpot,
				strName,
				mdfcBase,
				astrCreditTenor,
				0.
			),
			null,
			null,
			null,
			null
		);

		CurveSurfaceQuoteContainer csqcBumped01Up = MarketParamsBuilder.Create (
			FundingCurve (
				dtSpot,
				strCurrency,
				0.0001
			),
			gc,
			null,
			null,
			null,
			null,
			null
		);

		CurveSurfaceQuoteContainer csqcCreditBumped01Up = MarketParamsBuilder.Create (
			mdfcBase,
			gc,
			CreditCurve (
				dtSpot,
				strName,
				mdfcBase,
				astrCreditTenor,
				1.
			),
			null,
			null,
			null,
			null
		);

		double dblAccrued = bond.accrued (
			dtSettle.julian(),
			csqcBase
		);

		WorkoutInfo wi = bond.exerciseYieldFromPrice (
			valParams,
			csqcBase,
			null,
			dblCleanPrice
		);

		double dblBondBasisToMaturity = bond.bondBasisFromPrice (
			valParams,
			csqcBase,
			null,
			dblCleanPrice
		);

		double dblModifiedDurationToMaturity = (
			dblCleanPrice - bond.priceFromBondBasis (
				valParams,
				csqcBumped01Up,
				null,
				dblBondBasisToMaturity
			)
		) / dblCleanPrice;

		double dblYieldToMaturity = bond.yieldFromPrice (
			valParams,
			csqcBase,
			null,
			dblCleanPrice
		);

		double dblBondEquivalentYieldToMaturity = bond.yieldFromPrice (
			valParams,
			csqcBase,
			ValuationCustomizationParams.BondEquivalent (strCurrency),
			dblCleanPrice
		);

		double dblFlatForwardRateYieldToMaturity = bond.yieldFromPrice (
			valParams,
			csqcBase,
			new ValuationCustomizationParams (
				strCouponDayCount,
				iCouponFreq,
				false,
				null,
				strCurrency,
				false,
				true
			),
			dblCleanPrice
		);

		double dblYieldToExercise = bond.yieldFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblYieldToNextCall = bond.yieldFromPrice (
			valParams,
			csqcBase,
			null,
			NextCallDate (
				bond,
				iSpotDate
			),
			NextCallFactor (
				bond,
				iSpotDate
			),
			dblCleanPrice
		);

		double dblNominalYield = bond.yieldFromPrice (
			valParams,
			csqcBase,
			null,
			dblIssuePrice
		);

		double dblOASToMaturity = bond.oasFromPrice (
			valParams,
			csqcBase,
			null,
			dblCleanPrice
		);

		double dblOASToExercise = bond.oasFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblZSpreadToMaturity = bond.zSpreadFromPrice (
			valParams,
			csqcBase,
			null,
			dblCleanPrice
		);

		double dblZSpreadToExercise = bond.zSpreadFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblParZSpreadToExercise = bond.zSpreadFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			1.
		);

		double dblParOASToExercise = bond.oasFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			1.
		);

		double dblMacaulayDurationToMaturity = bond.macaulayDurationFromPrice (
			valParams,
			csqcBase,
			null,
			dblCleanPrice
		);

		double dblBondBasisToExercise = bond.bondBasisFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblModifiedDurationToWorst = (
			dblCleanPrice - bond.priceFromBondBasis (
				valParams,
				csqcBumped01Up,
				null,
				wi.date(),
				wi.factor(),
				dblBondBasisToExercise
			)
		) / dblCleanPrice;

		double dblDV01 = 0.5 * (
			bond.priceFromYield (
				valParams,
				csqcBase,
				null,
				wi.date(),
				wi.factor(),
				dblYieldToExercise - 0.0001 * dblSpreadBump
			) -
			bond.priceFromYield (
				valParams,
				csqcBase,
				null,
				wi.date(),
				wi.factor(),
				dblYieldToExercise + 0.0001 * dblSpreadBump
			)
		) / dblSpreadBump;

		double dblEffectiveDuration = dblDV01 / dblCleanPrice;

		double dblCreditBasisToExercise = bond.creditBasisFromPrice (
			valParams,
			csqcCreditBase,
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblParCreditBasisToExercise = bond.creditBasisFromPrice (
			valParams,
			csqcCreditBase,
			null,
			wi.date(),
			wi.factor(),
			1.
		);

		double dblEffectiveDurationAdj = 0.5 * (
			bond.priceFromCreditBasis (
				valParams,
				csqcCreditBase,
				null,
				wi.date(),
				wi.factor(),
				dblCreditBasisToExercise - dblSpreadBump
			) -
			bond.priceFromCreditBasis (
				valParams,
				csqcCreditBase,
				null,
				wi.date(),
				wi.factor(),
				dblCreditBasisToExercise + dblSpreadBump
			)
		) / dblCleanPrice / dblSpreadBump;

		double dblSpreadDuration = dblSpreadDurationMultiplier * (dblCleanPrice -
			bond.priceFromZSpread (
				valParams,
				csqcBase,
				null,
				wi.date(),
				wi.factor(),
				dblZSpreadToExercise + 0.0001 * dblSpreadBump
			)
		) / dblCleanPrice;

		double dblCV01 = dblCleanPrice - bond.priceFromCreditBasis (
			valParams,
			csqcCreditBumped01Up,
			null,
			wi.date(),
			wi.factor(),
			dblCreditBasisToExercise
		);

		Map<String, Double> mapLIBORKRD = new HashMap<String, Double>();

		Map<String, Double> mapLIBORKPRD = new HashMap<String, Double>();

		Map<String, MergedDiscountForwardCurve> mapFundingCurve = TenorBumpedFundingCurve (
			dtSpot,
			strCurrency,
			0.0001
		);

		for (Map.Entry<String, MergedDiscountForwardCurve> meFunding : mapFundingCurve.entrySet()) {
			CurveSurfaceQuoteContainer csqcFunding = MarketParamsBuilder.Create (
				meFunding.getValue(),
				gc,
				null,
				null,
				null,
				null,
				null
			);

			mapLIBORKRD.put (
				meFunding.getKey(),
				(dblCleanPrice - bond.priceFromZSpread (
					valParams,
					csqcFunding,
					null,
					wi.date(),
					wi.factor(),
					dblZSpreadToExercise
				)) / dblCleanPrice
			);

			mapLIBORKPRD.put (
				meFunding.getKey(),
				1. - bond.priceFromZSpread (
					valParams,
					csqcFunding,
					null,
					wi.date(),
					wi.factor(),
					dblParZSpreadToExercise
				)
			);
		}

		Map<String, Double> mapGovvieKRD = new HashMap<String, Double>();

		Map<String, Double> mapGovvieKPRD = new HashMap<String, Double>();

		Map<String, GovvieCurve> mapGovvieCurve = TenorBumpedGovvieCurve (
			dtSpot,
			strTreasuryCode,
			0.0001,
			adblGovvieYield
		);

		for (Map.Entry<String, GovvieCurve> meGovvie : mapGovvieCurve.entrySet()) {
			CurveSurfaceQuoteContainer csqcGovvie = MarketParamsBuilder.Create (
				mdfcBase,
				meGovvie.getValue(),
				null,
				null,
				null,
				null,
				null
			);

			mapGovvieKRD.put (
				meGovvie.getKey(),
				(dblCleanPrice - bond.priceFromOAS (
					valParams,
					csqcGovvie,
					null,
					wi.date(),
					wi.factor(),
					dblOASToExercise
				)) / dblCleanPrice
			);

			mapGovvieKPRD.put (
				meGovvie.getKey(),
				1. - bond.priceFromOAS (
					valParams,
					csqcGovvie,
					null,
					wi.date(),
					wi.factor(),
					dblParOASToExercise
				)
			);
		}

		Map<String, Double> mapCreditKRD = new HashMap<String, Double>();

		Map<String, Double> mapCreditKPRD = new HashMap<String, Double>();

		Map<String, CreditCurve> mapCreditCurve = TenorBumpedCreditCurve (
			dtSpot,
			strName,
			mdfcBase,
			1.
		);

		for (Map.Entry<String, CreditCurve> meCredit : mapCreditCurve.entrySet()) {
			CurveSurfaceQuoteContainer csqcCredit = MarketParamsBuilder.Create (
				mdfcBase,
				gc,
				meCredit.getValue(),
				null,
				null,
				null,
				null
			);

			mapCreditKRD.put (
				meCredit.getKey(),
				(dblCleanPrice - bond.priceFromCreditBasis (
					valParams,
					csqcCredit,
					null,
					wi.date(),
					wi.factor(),
					dblCreditBasisToExercise
				)) / dblCleanPrice
			);

			mapCreditKPRD.put (
				meCredit.getKey(),
				1. - bond.priceFromCreditBasis (
					valParams,
					csqcCredit,
					null,
					wi.date(),
					wi.factor(),
					dblParCreditBasisToExercise
				)
			);
		}

		double dblConvexityToExercise = bond.convexityFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblDiscountMarginToExercise = dblYieldToExercise - mdfcBase.libor (
			dtSpot,
			"1M"
		);

		double dblESpreadToExercise = bond.zSpreadFromPrice (
			valParams,
			MarketParamsBuilder.Create (
				LatentMarketStateBuilder.SmoothFundingCurve (
					dtSpot,
					strCurrency,
					astrDepositTenor,
					adblDepositQuote,
					"ForwardRate",
					adblFuturesQuote,
					"ForwardRate",
					null,
					null,
					"SwapRate"
				),
				gc,
				null,
				null,
				null,
				null,
				null
			),
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblISpreadToExercise = bond.iSpreadFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblJSpreadToExercise = bond.jSpreadFromPrice (
			valParams,
			csqcBase,
			null,
			wi.date(),
			wi.factor(),
			dblCleanPrice
		);

		double dblWALToExercise = bond.weightedAverageLife (
			valParams,
			csqcBase,
			wi.date(),
			wi.factor()
		);

		double dblWALPrincipalOnlyToExercise = bond.weightedAverageLifePrincipalOnly (
			valParams,
			csqcBase,
			wi.date(),
			wi.factor()
		);

		double dblWALLossOnlyToExercise = bond.weightedAverageLifeLossOnly (
			valParams,
			csqcCreditBase,
			wi.date(),
			wi.factor()
		);

		double dblWALCouponOnlyToExercise = bond.weightedAverageLifeCouponOnly (
			valParams,
			csqcBase,
			wi.date(),
			wi.factor()
		);

		System.out.println();

		BondReplicator ar = BondReplicator.Standard (
			dblCleanPrice,
			dblIssuePrice,
			dblIssueAmount,
			dtSpot,
			astrDepositTenor,
			adblDepositQuote,
			adblFuturesQuote,
			astrFixFloatTenor,
			adblFixFloatQuote,
			dblSpreadBump,
			dblSpreadDurationMultiplier,
			strTreasuryCode,
			astrGovvieTenor,
			adblGovvieYield,
			astrCreditTenor,
			adblCreditQuote,
			dblFX,
			Double.NaN,
			iSettleLag,
			bond
		);

		BondReplicationRun arr = ar.generateRun();

		Map<String, NamedField> mapNF = arr.namedField();

		Map<String, NamedFieldMap> mapNFM = arr.namedFieldMap();

		NamedFieldMap nfmLIBORKRD = mapNFM.get ("LIBOR KRD");

		NamedFieldMap nfmLIBORKPRD = mapNFM.get ("LIBOR KPRD");

		NamedFieldMap nfmGovvieKRD = mapNFM.get ("Govvie KRD");

		NamedFieldMap nfmGovvieKPRD = mapNFM.get ("Govvie KPRD");

		NamedFieldMap nfmCreditKRD = mapNFM.get ("Credit KRD");

		NamedFieldMap nfmCreditKPRD = mapNFM.get ("Credit KPRD");

		System.out.println ("\t||------------------------------------------------||");

		System.out.println (
			"\t|| ID                      =>  " +
			strName + "-" + strCurrency
		);

		System.out.println (
			"\t|| Price                   => " +
			FormatUtil.FormatDouble (dblCleanPrice, 3, 3, 100.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Price").value(), 3, 3, 100.)
		);

		System.out.println (
			"\t|| Market Value            => " +
			FormatUtil.FormatDouble (dblCleanPrice * dblIssueAmount, 7, 2, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Market Value").value(), 7, 2, 1.)
		);

		System.out.println (
			"\t|| Accrued                 => " +
			FormatUtil.FormatDouble (dblAccrued, 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Accrued").value(), 1, 4, 1.)
		);

		System.out.println (
			"\t|| Accrued                 => " +
			FormatUtil.FormatDouble (dblAccrued * dblIssueAmount, 5, 2, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Accrued$").value(), 5, 2, 1.)
		);

		System.out.println (
			"\t|| Accrued Interest Factor => " +
			FormatUtil.FormatDouble (dblAccrued * dblFX, 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Accrued Interest Factor").value(), 1, 4, 1.)
		);

		System.out.println (
			"\t|| Yield To Maturity       => " +
			FormatUtil.FormatDouble (dblYieldToMaturity, 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (mapNF.get ("Yield To Maturity").value(), 1, 2, 100.) + "%"
		);

		System.out.println (
			"\t|| Yield To Maturity CBE   => " +
			FormatUtil.FormatDouble (dblBondEquivalentYieldToMaturity, 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (mapNF.get ("Yield To Maturity CBE").value(), 1, 2, 100.) + "%"
		);

		System.out.println (
			"\t|| YTM fwdCpn              => " +
			FormatUtil.FormatDouble (dblFlatForwardRateYieldToMaturity, 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (mapNF.get ("YTM fwdCpn").value(), 1, 2, 100.) + "%"
		);

		System.out.println (
			"\t|| Yield To Worst          => " +
			FormatUtil.FormatDouble (dblYieldToExercise, 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (mapNF.get ("Yield To Worst").value(), 1, 2, 100.) + "%"
		);

		System.out.println (
			"\t|| YIELD TO CALL           => " +
			FormatUtil.FormatDouble (dblYieldToNextCall, 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (mapNF.get ("YIELD TO CALL").value(), 1, 2, 100.) + "%"
		);

		System.out.println (
			"\t|| Nominal Yield           => " +
			FormatUtil.FormatDouble (dblNominalYield, 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (mapNF.get ("Nominal Yield").value(), 1, 2, 100.) + "%"
		);

		System.out.println (
			"\t|| Z_Spread                => " +
			FormatUtil.FormatDouble (dblOASToMaturity, 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Z_Spread").value(), 3, 1, 10000.)
		);

		System.out.println (
			"\t|| Z_Vol_OAS               => " +
			FormatUtil.FormatDouble (dblZSpreadToMaturity, 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Z_Vol_OAS").value(), 3, 1, 10000.)
		);

		System.out.println (
			"\t|| OAS                     => " +
			FormatUtil.FormatDouble (dblZSpreadToExercise, 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("OAS").value(), 3, 1, 10000.)
		);

		System.out.println (
			"\t|| TSY OAS                 => " +
			FormatUtil.FormatDouble (dblOASToExercise, 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("TSY OAS").value(), 3, 1, 10000.)
		);

		System.out.println (
			"\t|| MOD DUR                 => " +
			FormatUtil.FormatDouble (dblModifiedDurationToMaturity, 1, 3, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("MOD DUR").value(), 1, 3, 10000.)
		);

		System.out.println (
			"\t|| MACAULAY DURATION       => " +
			FormatUtil.FormatDouble (dblMacaulayDurationToMaturity, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("MACAULAY DURATION").value(), 1, 3, 1.)
		);

		System.out.println (
			"\t|| MOD DUR TO WORST        => " +
			FormatUtil.FormatDouble (dblModifiedDurationToWorst, 1, 3, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("MOD DUR TO WORST").value(), 1, 3, 10000.)
		);

		System.out.println (
			"\t|| EFFECTIVE DURATION      => " +
			FormatUtil.FormatDouble (dblEffectiveDuration, 1, 3, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("EFFECTIVE DURATION").value(), 1, 3, 10000.)
		);

		System.out.println (
			"\t|| EFFECTIVE DURATION ADJ  => " +
			FormatUtil.FormatDouble (dblEffectiveDurationAdj, 1, 3, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("EFFECTIVE DURATION ADJ").value(), 1, 3, 10000.)
		);

		System.out.println (
			"\t|| OAD MULT                => " +
			FormatUtil.FormatDouble (dblEffectiveDurationAdj / dblEffectiveDuration, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("OAD MULT").value(), 1, 3, 1.)
		);

		System.out.println (
			"\t|| Spread Dur              => " +
			FormatUtil.FormatDouble (dblSpreadDuration, 1, 3, 100.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Spread Dur").value(), 1, 3, 100.)
		);

		System.out.println (
			"\t|| Spread Dur              => " +
			FormatUtil.FormatDouble (dblSpreadDuration * dblIssueAmount, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Spread Dur $").value(), 1, 3, 1.)
		);

		System.out.println (
			"\t|| DV01                    => " +
			FormatUtil.FormatDouble (dblDV01, 1, 3, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("DV01").value(), 1, 3, 10000.)
		);

		System.out.println (
			"\t|| CV01                    => " +
			FormatUtil.FormatDouble (dblCV01, 1, 3, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("CV01").value(), 1, 3, 10000.)
		);

		System.out.println (
			"\t|| Convexity               => " +
			FormatUtil.FormatDouble (dblConvexityToExercise, 1, 3, 1000000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Convexity").value(), 1, 3, 1000000.)
		);

		System.out.println (
			"\t|| Modified Convexity      => " +
			FormatUtil.FormatDouble (dblConvexityToExercise, 1, 3, 1000000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("Modified Convexity").value(), 1, 3, 1000000.)
		);

		System.out.println (
			"\t|| DISCOUNT MARGIN         => " +
			FormatUtil.FormatDouble (dblDiscountMarginToExercise, 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("DISCOUNT MARGIN").value(), 3, 1, 10000.)
		);

		System.out.println (
			"\t|| E-Spread                => " +
			FormatUtil.FormatDouble (dblESpreadToExercise, 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("E-Spread").value(), 3, 1, 10000.)
		);

		System.out.println (
			"\t|| I-Spread                => " +
			FormatUtil.FormatDouble (dblISpreadToExercise, 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("I-Spread").value(), 3, 1, 10000.)
		);

		System.out.println (
			"\t|| J-Spread                => " +
			FormatUtil.FormatDouble (dblJSpreadToExercise, 3, 1, 10000.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("J-Spread").value(), 3, 1, 10000.)
		);

		System.out.println (
			"\t|| WAL To Worst            => " +
			FormatUtil.FormatDouble (dblWALToExercise, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("WAL To Worst").value(), 1, 3, 1.)
		);

		System.out.println (
			"\t|| WAL                     => " +
			FormatUtil.FormatDouble (dblWALPrincipalOnlyToExercise, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("WAL").value(), 1, 3, 1.)
		);

		System.out.println (
			"\t|| WAL2                    => " +
			FormatUtil.FormatDouble (dblWALLossOnlyToExercise, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("WAL2").value(), 1, 3, 1.)
		);

		System.out.println (
			"\t|| WAL3                    => " +
			FormatUtil.FormatDouble (dblWALCouponOnlyToExercise, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("WAL3").value(), 1, 3, 1.)
		);

		System.out.println (
			"\t|| WAL4                    => " +
			FormatUtil.FormatDouble (dblWALToExercise, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (mapNF.get ("WAL4").value(), 1, 3, 1.)
		);

		System.out.println ("\t||------------------------------------------------||");

		for (Map.Entry<String, Double> meLIBORKRD : mapLIBORKRD.entrySet())
			System.out.println (
				"\t|| LIBOR KRD " + meLIBORKRD.getKey() + " => " +
				FormatUtil.FormatDouble (meLIBORKRD.getValue(), 1, 3, 10000.) + " | " +
				FormatUtil.FormatDouble (nfmLIBORKRD.value().get (meLIBORKRD.getKey()), 1, 3, 10000.)
			);

		System.out.println ("\t||------------------------------------------------||");

		for (Map.Entry<String, Double> meLIBORKPRD : mapLIBORKPRD.entrySet())
			System.out.println (
				"\t|| LIBOR KPRD " + meLIBORKPRD.getKey() + " => " +
				FormatUtil.FormatDouble (meLIBORKPRD.getValue(), 1, 3, 10000.) + " | " +
				FormatUtil.FormatDouble (nfmLIBORKPRD.value().get (meLIBORKPRD.getKey()), 1, 3, 10000.)
			);

		System.out.println ("\t||------------------------------------------------||");

		for (Map.Entry<String, Double> meGovvieKRD : mapGovvieKRD.entrySet())
			System.out.println (
				"\t|| Govvie KRD " + meGovvieKRD.getKey() + " => " +
				FormatUtil.FormatDouble (meGovvieKRD.getValue(), 1, 3, 10000.) + " | " +
				FormatUtil.FormatDouble (nfmGovvieKRD.value().get (meGovvieKRD.getKey()), 1, 3, 10000.)
			);

		System.out.println ("\t||------------------------------------------------||");

		for (Map.Entry<String, Double> meGovvieKPRD : mapGovvieKPRD.entrySet())
			System.out.println (
				"\t|| Govvie KPRD " + meGovvieKPRD.getKey() + " => " +
				FormatUtil.FormatDouble (meGovvieKPRD.getValue(), 1, 3, 10000.) + " | " +
				FormatUtil.FormatDouble (nfmGovvieKPRD.value().get (meGovvieKPRD.getKey()), 1, 3, 10000.)
			);

		System.out.println ("\t||------------------------------------------------||");

		for (Map.Entry<String, Double> meCreditKRD : mapCreditKRD.entrySet())
			System.out.println (
				"\t|| Credit KRD " + meCreditKRD.getKey() + " => " +
				FormatUtil.FormatDouble (meCreditKRD.getValue(), 1, 3, 10000.) + " | " +
				FormatUtil.FormatDouble (nfmCreditKRD.value().get (meCreditKRD.getKey()), 1, 3, 10000.)
			);

		System.out.println ("\t||------------------------------------------------||");

		for (Map.Entry<String, Double> meCreditKPRD : mapCreditKPRD.entrySet())
			System.out.println (
				"\t|| Credit KPRD " + meCreditKPRD.getKey() + " => " +
				FormatUtil.FormatDouble (meCreditKPRD.getValue(), 1, 3, 10000.) + " | " +
				FormatUtil.FormatDouble (nfmCreditKPRD.value().get (meCreditKPRD.getKey()), 1, 3, 10000.)
			);

		System.out.println ("\t||------------------------------------------------||");

		System.out.println();
	}
}
