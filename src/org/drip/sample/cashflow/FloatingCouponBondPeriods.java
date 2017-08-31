
package org.drip.sample.cashflow;

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.analytics.output.ConvexityAdjustment;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.creator.ScenarioCreditCurveBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;

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
 * FloatingCouponBondPeriods demonstrates the Cash Flow Period Details for a Floating Coupon Bond.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FloatingCouponBondPeriods {

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

	private static final GovvieCurve GovvieCurve (
		final JulianDate dtSpot,
		final String strCode,
		final double[] adblCoupon,
		final double[] adblYield)
		throws Exception
	{
		JulianDate[] adtEffective = new JulianDate[] {
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot,
			dtSpot
		};

		JulianDate[] adtMaturity = new JulianDate[] {
			dtSpot.addTenor ("1Y"),
			dtSpot.addTenor ("2Y"),
			dtSpot.addTenor ("3Y"),
			dtSpot.addTenor ("5Y"),
			dtSpot.addTenor ("7Y"),
			dtSpot.addTenor ("10Y"),
			dtSpot.addTenor ("20Y"),
			dtSpot.addTenor ("30Y")
		};

		return LatentMarketStateBuilder.GovvieCurve (
			strCode,
			dtSpot,
			adtEffective,
			adtMaturity,
			adblCoupon,
			adblYield,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.MARCH,
			10
		);

		String strCurrency = "USD";
		String strTreasuryCode = "UST";

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

		JulianDate dtEffective = DateUtil.CreateFromYMD (2016, 10, 18);
		JulianDate dtMaturity  = DateUtil.CreateFromYMD (2019, 10, 18);
		double dblSpread = 0.0091;
		int iFreq = 4;
		String strCUSIP = "00206RCP5";
		String strDayCount = "Act/360";
		String strCreditCurve = "CC";

		BondComponent bond = BondBuilder.CreateSimpleFloater (
			strCUSIP,
			"USD",
			"USD-3M",
			strCreditCurve,
			dblSpread,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			null,
			null
		);

		System.out.println();

		MergedDiscountForwardCurve mdfc = FundingCurve (
			dtSpot,
			strCurrency,
			0.
		); 

		CreditCurve cc = ScenarioCreditCurveBuilder.FlatHazard (
			dtSpot.julian(),
			strCreditCurve,
			"USD",
			0.01,
			0.4
		);

		CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Create (
			mdfc,
			GovvieCurve (
				dtSpot,
				strTreasuryCode,
				adblTreasuryCoupon,
				adblTreasuryYield
			),
			cc,
			null,
			null,
			null,
			null
		);

		ValuationParams valParams = ValuationParams.Spot (dtSpot.julian());

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                      BOND CASH FLOW PERIOD DATES AND FACTORS                                       ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||   L -> R:                                                                                                                                                 ||");

		System.out.println ("\t||           - Period Start Date                                                                                                                             ||");

		System.out.println ("\t||           - Period End Date                                                                                                                               ||");

		System.out.println ("\t||           - Period Pay Date                                                                                                                               ||");

		System.out.println ("\t||           - Period FX Fixing Date                                                                                                                         ||");

		System.out.println ("\t||           - Period Is FX MTM?                                                                                                                             ||");

		System.out.println ("\t||           - Period Tenor                                                                                                                                  ||");

		System.out.println ("\t||           - Period Coupon Frequency                                                                                                                       ||");

		System.out.println ("\t||           - Period Pay Currency                                                                                                                           ||");

		System.out.println ("\t||           - Period Coupon Currency                                                                                                                        ||");

		System.out.println ("\t||           - Period Basis                                                                                                                                  ||");

		System.out.println ("\t||           - Period Base Notional                                                                                                                          ||");

		System.out.println ("\t||           - Period Notional                                                                                                                               ||");

		System.out.println ("\t||           - Period Coupon Factor                                                                                                                          ||");

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod p : bond.couponPeriods()) {
			int iEndDate = p.endDate();

			CompositeFloatingPeriod cfp = (CompositeFloatingPeriod) p;

			ComposableUnitFloatingPeriod cufp = (ComposableUnitFloatingPeriod) cfp.periods().get(0);

			ReferenceIndexPeriod rip = cufp.referenceIndexPeriod();

			System.out.println ("\t|| " +
				DateUtil.YYYYMMDD (p.startDate()) + " => " +
				DateUtil.YYYYMMDD (iEndDate) + " | " +
				DateUtil.YYYYMMDD (rip.startDate()) + " | " +
				DateUtil.YYYYMMDD (rip.endDate()) + " | " +
				DateUtil.YYYYMMDD (rip.fixingDate()) + " | " +
				DateUtil.YYYYMMDD (p.payDate()) + " | " +
				DateUtil.YYYYMMDD (p.fxFixingDate()) + " | " +
				p.isFXMTM() + " | " +
				p.tenor() + " | " +
				p.freq() + " | " +
				p.payCurrency() + " | " +
				p.couponCurrency() + " | " +
				FormatUtil.FormatDouble (p.basis(), 1, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (p.baseNotional(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.notional (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.couponFactor (iEndDate), 1, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t||-----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

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
				DateUtil.YYYYMMDD (iEndDate) + " | " +
				p.creditLabel().fullyQualifiedName() + " | " +
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
			DateUtil.YYYYMMDD (dtMaturity.julian()) + " | " +
			bond.creditLabel().fullyQualifiedName() + " | " +
			bond.fundingLabel().fullyQualifiedName() + " | " +
			bond.forwardLabel().get (bond.name()).fullyQualifiedName() + " | " +
			FormatUtil.FormatDouble (0., 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (0., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (0., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (bond.notional (dtMaturity.julian()), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (mdfc.df (dtMaturity), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (cc.survival (dtMaturity), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (cc.recovery (dtMaturity), 2, 0, 100.) + "% ||"
		);

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println();

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                       CASH FLOW PERIODS CONVEXITY CORRECTION                                       ||");

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                                                                         ||");

		System.out.println ("\t||            - Collateral Credit Adjustment                                                                          ||");

		System.out.println ("\t||            - Collateral Forward Adjustment                                                                         ||");

		System.out.println ("\t||            - Collateral Funding Adjustment                                                                         ||");

		System.out.println ("\t||            - Collateral FX Adjustment                                                                              ||");

		System.out.println ("\t||            - Credit Forward Adjustment                                                                             ||");

		System.out.println ("\t||            - Credit Funding Adjustment                                                                             ||");

		System.out.println ("\t||            - Credit FX Adjustment                                                                                  ||");

		System.out.println ("\t||            - Forward Funding Adjustment                                                                            ||");

		System.out.println ("\t||            - Forward FX Adjustment                                                                                 ||");

		System.out.println ("\t||            - Funding FX Adjustment                                                                                 ||");

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod p : bond.couponPeriods()) {
			ConvexityAdjustment ca = p.terminalConvexityAdjustment (
				dtSpot.julian(),
				csqc
			);

			System.out.println ("\t|| " +
				DateUtil.YYYYMMDD (p.startDate()) + " => " +
				DateUtil.YYYYMMDD (p.endDate()) + " | " +
				FormatUtil.FormatDouble (ca.collateralCredit(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.collateralForward(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.collateralFunding(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.collateralFX(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.creditForward(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.creditFunding(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.creditFX(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.forwardFunding(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.forwardFX(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (ca.fundingFX(), 1, 3, 1.) + " ||"
			);
		}

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
