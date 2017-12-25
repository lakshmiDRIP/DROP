
package org.drip.sample.bondmetrics;

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.product.params.EmbeddedOptionSchedule;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.scenario.*;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

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
 * Muzaffarpur demonstrates the Analytics Calculation/Reconciliation for the Bond Muzaffarpur.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Muzaffarpur {

	private static final void SetEOS (
		final BondComponent bond,
		final EmbeddedOptionSchedule eosCall,
		final EmbeddedOptionSchedule eosPut)
		throws java.lang.Exception
	{
		if (null != eosPut) bond.setEmbeddedPutSchedule (eosPut);

		if (null != eosCall) bond.setEmbeddedCallSchedule (eosCall);
	}

	public static final void main (
		final String[] astArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.SEPTEMBER,
			21
		);

		String[] astrDepositTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.012 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.01495,
			0.0159,
			0.01675,
			0.01745,
			0.0183,
			0.01875
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
			"1M",
			"3M",
			"6M",
			"1Y",
			"2Y",
			"3Y",
			"5Y",
			"7Y",
			"10Y",
			"15Y",
			"20Y",
			"30Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.017013,
			0.018047,
			0.018867,
			0.019587,
			0.02024,
			0.020881,
			0.021436,
			0.021904,
			0.022332,
			0.022728,
			0.023076,
			0.023804,
			0.024512,
			0.024828,
			0.024873,
			0.024775,
			0.024462
		};

		double[] adblGovvieYield = new double[] {
			0.0099,
			0.0104,
			0.0119,
			0.0131,
			0.0145,
			0.0159,
			0.0189,
			0.0211,
			0.0227,
			0.02397,
			0.0257,
			0.028
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

		double dblFX = 1;
		int iSettleLag = 3;
		double dblSpread = 0.01;
		String strCurrency = "USD";
		double dblCleanPrice = 0.91734;
		double dblIssuePrice = 1.0;
		double dblSpreadBump = 20.;
		String strTreasuryCode = "UST";
		double dblIssueAmount = 7.50e8;
		double dblSpreadDurationMultiplier = 5.;
		double dblResetRate = 0.0232 - dblSpread;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2007,
			4,
			30
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2047,
			6,
			15
		);

		BondComponent bond = BondBuilder.CreateSimpleFloater (
			"Muzaffarpur",
			"USD",
			"USD-3M",
			"Muzaffarpur",
			dblSpread,
			4,
			"Act/360",
			dtEffective,
			dtMaturity,
			null,
			null
		);

		SetEOS (
			bond,
			EmbeddedOptionSchedule.FromAmerican (
				dtSpot.julian(),
				new int[] {
					DateUtil.CreateFromYMD (2012, 06, 15).julian(),
					DateUtil.CreateFromYMD (2047, 06, 15).julian(),
				},
				new double[] {
					1.00000,
					1.00000,
				},
				false,
				30,
				30,
				false,
				Double.NaN,
				"",
				Double.NaN
			),
			null
		);

		CompositeFloatingPeriod cfp = (CompositeFloatingPeriod) bond.stream().containingPeriod (dtSpot.julian());

		int iResetDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod) (cfp.periods().get
			(0))).referenceIndexPeriod().fixingDate();

		MergedDiscountForwardCurve mdfc = LatentMarketStateBuilder.SmoothFundingCurve (
			dtSpot,
			strCurrency,
			astrDepositTenor,
			adblDepositQuote,
			"ForwardRate",
			adblFuturesQuote,
			"ForwardRate",
			astrFixFloatTenor,
			adblFixFloatQuote,
			"SwapRate"
		);

		BondReplicator abr = BondReplicator.Standard (
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
			dblResetRate,
			iSettleLag,
			bond
		);

		BondReplicationRun abrr = abr.generateRun();

		System.out.println (abrr.display());

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		CurveSurfaceQuoteContainer csqc = abr.creditBaseCSQC();

		ForwardLabel fl = bond.floaterSetting().fri();

		csqc.setFixing (iResetDate, fl, dblResetRate);

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		double dblYield = bond.yieldFromPrice (
			ValuationParams.Spot (dtSpot.julian()),
			csqc,
			null,
			dblCleanPrice
		);

		System.out.println ("Price In  : " + dblCleanPrice);

		System.out.println ("Yield Out : " + dblYield);

		System.out.println ("Price Out : " +
			bond.priceFromYield (
				ValuationParams.Spot (dtSpot.julian()),
				csqc,
				null,
				dblYield
			)
		);

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                            PERIOD LABELS AND CURVE FACTORS                                           ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||   L -> R:                                                                                                            ||");

		System.out.println ("\t||           - Period Start Date                                                                                        ||");

		System.out.println ("\t||           - Period End Date                                                                                          ||");

		System.out.println ("\t||           - Period Credit Label                                                                                      ||");

		System.out.println ("\t||           - Period Funding Label                                                                                     ||");

		System.out.println ("\t||           - Period Coupon Rate (%)                                                                                   ||");

		System.out.println ("\t||           - Period Coupon Year Fraction                                                                              ||");

		System.out.println ("\t||           - Period Coupon Amount                                                                                     ||");

		System.out.println ("\t||           - Period Principal Amount                                                                                  ||");

		System.out.println ("\t||           - Period Discount Factor                                                                                   ||");

		System.out.println ("\t||           - Period Survival Probability                                                                              ||");

		System.out.println ("\t||           - Period Recovery                                                                                          ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod p : bond.couponPeriods()) {
			int iEndDate = p.endDate();

			int iPayDate = p.payDate();

			int iStartDate = p.startDate();

			double dblCouponRate = bond.couponMetrics (
				iPayDate,
				valParams,
				csqc
			).rate();

			double dblCouponDCF = p.couponDCF();

			System.out.println ("\t|| " +
				DateUtil.YYYYMMDD (iStartDate) + " => " +
				DateUtil.YYYYMMDD (iEndDate) + " | ? | " +
				p.fundingLabel().fullyQualifiedName() + " | " +
				p.forwardLabel().fullyQualifiedName() + " | " +
				FormatUtil.FormatDouble (dblCouponRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblCouponDCF, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblCouponRate * dblCouponDCF * p.notional (iEndDate) * p.couponFactor (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.notional (iStartDate) - p.notional (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.df (csqc), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.survival (csqc), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.recovery (csqc), 2, 0, 100.) + "% ||"
			);
		}

		System.out.println ("\t|| " +
			DateUtil.YYYYMMDD (dtEffective.julian()) + " => " +
			DateUtil.YYYYMMDD (dtMaturity.julian()) + " | ? | " +
			bond.fundingLabel().fullyQualifiedName() + " | " +
			bond.forwardLabel().get (bond.name()).fullyQualifiedName() + " | " +
			FormatUtil.FormatDouble (0., 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (0., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (0., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (bond.notional (dtMaturity.julian()), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (mdfc.df (dtMaturity), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (1., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (1., 2, 0, 100.) + "% ||"
		);

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
