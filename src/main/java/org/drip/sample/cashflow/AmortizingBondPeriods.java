
package org.drip.sample.cashflow;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.analytics.output.ConvexityAdjustment;
import org.drip.numerical.common.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.common.FormatUtil;
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
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>AmortizingBondPeriods</i> demonstrates the Cash Flow Period Details for an Amortizing Fixed Coupon
 * Bond.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/cashflow/README.md">Fixed Income Product Cash Flow Display</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AmortizingBondPeriods {

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

		JulianDate dtEffective = DateUtil.CreateFromYMD (2014,  8, 11);
		JulianDate dtMaturity  = DateUtil.CreateFromYMD (2026,  9,  3);
		double dblCoupon = 0.0375;
		int iFreq = 2;
		String strCUSIP = "90932QAA4";
		String strDayCount = "30/360";
		String strCreditCurve = "CC";
		String strDateFactor = "8/11/2014;1;3/3/2016;0.970903241;9/3/2016;0.942322722;3/3/2017;0.913786792;9/3/2017;0.885297621;3/3/2018;0.857293406;9/3/2018;0.829340734;3/3/2019;0.801442245;9/3/2019;0.77360076;3/3/2020;0.7458193;9/3/2020;0.718101103;3/3/2021;0.690449645;9/3/2021;0.66286866;3/3/2022;0.635362168;9/3/2022;0.607934498;3/3/2023;0.580590323;9/3/2023;0.553334692;3/3/2024;0.526173071;9/3/2024;0.499111382;3/3/2025;0.472156058;9/3/2025;0.445314095;3/3/2026;0.418593118";

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			strCUSIP,
			strCurrency,
			strCreditCurve,
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			Array2D.FromDateFactorVertex (
				strDateFactor,
				dtMaturity.julian()
			),
			null
		);

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

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                      BOND CASH FLOW PERIOD DATES AND FACTORS                                      ||");

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||   L -> R:                                                                                                         ||");

		System.out.println ("\t||           - Period Start Date                                                                                     ||");

		System.out.println ("\t||           - Period End Date                                                                                       ||");

		System.out.println ("\t||           - Period Pay Date                                                                                       ||");

		System.out.println ("\t||           - Period FX Fixing Date                                                                                 ||");

		System.out.println ("\t||           - Period Is FX MTM?                                                                                     ||");

		System.out.println ("\t||           - Period Tenor                                                                                          ||");

		System.out.println ("\t||           - Period Coupon Frequency                                                                               ||");

		System.out.println ("\t||           - Period Pay Currency                                                                                   ||");

		System.out.println ("\t||           - Period Coupon Currency                                                                                ||");

		System.out.println ("\t||           - Period Basis                                                                                          ||");

		System.out.println ("\t||           - Period Base Notional                                                                                  ||");

		System.out.println ("\t||           - Period Notional                                                                                       ||");

		System.out.println ("\t||           - Period Coupon Factor                                                                                  ||");

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod p : bond.couponPeriods()) {
			int iEndDate = p.endDate();

			System.out.println ("\t|| " +
				DateUtil.YYYYMMDD (p.startDate()) + " => " +
				DateUtil.YYYYMMDD (iEndDate) + " | " +
				DateUtil.YYYYMMDD (p.payDate()) + " | " +
				DateUtil.YYYYMMDD (p.fxFixingDate()) + " | " +
				p.isFXMTM() + " | " +
				p.tenor() + " | " +
				p.freq() + " | " +
				p.payCurrency() + " | " +
				p.couponCurrency() + " | " +
				FormatUtil.FormatDouble (p.basis(), 1, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (p.baseNotional(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (bond.notional (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.couponFactor (iEndDate), 1, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		double dblPreviousPeriodNotional = bond.notional (dtEffective.julian());

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                    PERIOD LABELS AND CURVE FACTORS                                    ||");

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||   L -> R:                                                                                             ||");

		System.out.println ("\t||           - Period Start Date                                                                         ||");

		System.out.println ("\t||           - Period End Date                                                                           ||");

		System.out.println ("\t||           - Period Credit Label                                                                       ||");

		System.out.println ("\t||           - Period Funding Label                                                                      ||");

		System.out.println ("\t||           - Period Coupon Rate (%)                                                                    ||");

		System.out.println ("\t||           - Period Coupon Year Fraction                                                               ||");

		System.out.println ("\t||           - Period Coupon Amount                                                                      ||");

		System.out.println ("\t||           - Period Principal Amount                                                                   ||");

		System.out.println ("\t||           - Period Discount Factor                                                                    ||");

		System.out.println ("\t||           - Period Survival Probability                                                               ||");

		System.out.println ("\t||           - Period Recovery                                                                           ||");

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------||");

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

			double dblCurrentPeriodNotional = bond.notional (iEndDate);

			System.out.println ("\t|| " +
				DateUtil.YYYYMMDD (iStartDate) + " => " +
				DateUtil.YYYYMMDD (iEndDate) + " | " +
				p.creditLabel().fullyQualifiedName() + " | " +
				p.fundingLabel().fullyQualifiedName() + " | " +
				FormatUtil.FormatDouble (dblCouponRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblCouponDCF, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblCouponRate * dblCouponDCF * dblCurrentPeriodNotional * p.couponFactor (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblPreviousPeriodNotional - dblCurrentPeriodNotional, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.df (csqc), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.survival (csqc), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.recovery (csqc), 2, 0, 100.) + "% ||"
			);

			dblPreviousPeriodNotional = dblCurrentPeriodNotional;
		}

		System.out.println ("\t|| " +
			DateUtil.YYYYMMDD (dtEffective.julian()) + " => " +
			DateUtil.YYYYMMDD (dtMaturity.julian()) + " | " +
			bond.creditLabel().fullyQualifiedName() + " | " +
			bond.fundingLabel().fullyQualifiedName() + " | " +
			FormatUtil.FormatDouble (0., 1, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (0., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (0., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (bond.notional (dtMaturity.julian()), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (mdfc.df (dtMaturity), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (cc.survival (dtMaturity), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (cc.recovery (dtMaturity), 2, 0, 100.) + "% ||"
		);

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------||");

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

		EnvManager.TerminateEnv();
	}
}
