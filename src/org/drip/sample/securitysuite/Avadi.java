
package org.drip.sample.securitysuite;

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
 * Avadi demonstrates the Analytics Calculation/Reconciliation for the Bond Avadi.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Avadi {

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
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.OCTOBER,
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
			 10.,	//  6M
			 12.,	//  1Y
			 15.,	//  2Y
			 19.,	//  3Y
			 24.,	//  4Y
			 28.,	//  5Y
			 38.,	//  7Y
			 51.	// 10Y
		};

		double dblFX = 1;
		int iSettleLag = 3;
		double dblSpread = 0.0;
		String strCurrency = "USD";
		double dblCleanPrice = 1.006161;
		double dblIssuePrice = 0.995;
		double dblSpreadBump = 20.;
		String strTreasuryCode = "UST";
		double dblIssueAmount = 7.50e8;
		double dblSpreadDurationMultiplier = 5.;
		double dblResetRate = 0.040794 - dblSpread;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2014,
			6,
			24
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2021,
			6,
			24
		);

		BondComponent bond = BondBuilder.CreateSimpleFloater (
			"Avadi",
			"USD",
			"USD-3M",
			"Avadi",
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
					DateUtil.CreateFromYMD (2014,  6, 24).julian(),
					DateUtil.CreateFromYMD (2014, 12, 24).julian(),
					DateUtil.CreateFromYMD (2021,  6, 24).julian(),
				},
				new double[] {
					1.01,
					1.00,
					1.00,
				},
				false,
				15,
				15,
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
	}
}
