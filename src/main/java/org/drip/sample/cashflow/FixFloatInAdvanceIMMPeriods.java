
package org.drip.sample.cashflow;

import java.util.List;

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.analytics.output.ConvexityAdjustment;
import org.drip.analytics.support.CompositePeriodBuilder;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.CashSettleParams;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

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
 * <i>FixFloatInAdvanceIMMPeriods</i> demonstrates the Cash Flow Period Details for an In-Advance Fix-Float
 * IMM Swap.
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

public class FixFloatInAdvanceIMMPeriods {

	private static final FixFloatComponent FixFloatInArrears (
		final JulianDate dtEffective,
		final String strCurrency,
		final String strMaturityTenor)
		throws Exception
	{
		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			2,
			"Act/360",
			false,
			"Act/360",
			false,
			strCurrency,
			true,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCurrency,
				"6M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			0.,
			0.,
			strCurrency
		);

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
			2,
			"6M",
			strCurrency,
			null,
			-1.,
			null,
			null,
			null,
			null
		);

		CompositePeriodSetting cpsFixed = new CompositePeriodSetting (
			2,
			"6M",
			strCurrency,
			null,
			1.,
			null,
			null,
			null,
			null
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			strCurrency,
			0
		);

		List<Integer> lsFixedStreamEdgeDate = CompositePeriodBuilder.IMMEdgeDates (
			dtEffective,
			3,
			"6M",
			strMaturityTenor,
			null
		);

		List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.IMMEdgeDates (
			dtEffective,
			3,
			"6M",
			strMaturityTenor,
			null
		);

		Stream floatingStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsFloatingStreamEdgeDate,
				cpsFloating,
				cfusFloating
			)
		);

		Stream fixedStream = new Stream (
			CompositePeriodBuilder.FixedCompositeUnit (
				lsFixedStreamEdgeDate,
				cpsFixed,
				ucasFixed,
				cfusFixed
			)
		);

		FixFloatComponent irs = new FixFloatComponent (
			fixedStream,
			floatingStream,
			csp
		);

		irs.setPrimaryCode ("IRS." + strMaturityTenor + "." + strCurrency);

		return irs;
	}

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

		FixFloatComponent ffcInAdvanceIMM = FixFloatInArrears (
			dtSpot,
			strCurrency,
			"8Y"
		);

		System.out.println();

		MergedDiscountForwardCurve mdfc = FundingCurve (
			dtSpot,
			strCurrency,
			0.
		); 

		CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Create (
			mdfc,
			null,
			null,
			null,
			null,
			null,
			null
		);

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                         FIXED PERIOD DATES AND FACTORS                                            ||");

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

		for (CompositePeriod p : ffcInAdvanceIMM.referenceStream().cashFlowPeriod()) {
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
				FormatUtil.FormatDouble (p.notional (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.couponFactor (iEndDate), 1, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t||-------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||---------------------------------------------------------------------------------||");

		System.out.println ("\t||                     FIXED PERIOD LABELS AND CURVE FACTORS                       ||");

		System.out.println ("\t||---------------------------------------------------------------------------------||");

		System.out.println ("\t||   L -> R:                                                                       ||");

		System.out.println ("\t||           - Period Start Date                                                   ||");

		System.out.println ("\t||           - Period End Date                                                     ||");

		System.out.println ("\t||           - Period Funding Label                                                ||");

		System.out.println ("\t||           - Period Coupon Rate (%)                                              ||");

		System.out.println ("\t||           - Period Coupon Year Fraction                                         ||");

		System.out.println ("\t||           - Period Coupon Amount                                                ||");

		System.out.println ("\t||           - Period Principal Amount                                             ||");

		System.out.println ("\t||           - Period Discount Factor                                              ||");

		System.out.println ("\t||---------------------------------------------------------------------------------||");

		for (CompositePeriod p : ffcInAdvanceIMM.referenceStream().cashFlowPeriod()) {
			int iEndDate = p.endDate();

			int iStartDate = p.startDate();

			double dblCouponRate = p.couponMetrics (
				iEndDate,
				csqc
			).rate();

			double dblCouponDCF = p.couponDCF();

			System.out.println ("\t|| " +
				DateUtil.YYYYMMDD (iStartDate) + " => " +
				DateUtil.YYYYMMDD (iEndDate) + " | " +
				p.fundingLabel().fullyQualifiedName() + " | " +
				FormatUtil.FormatDouble (dblCouponRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblCouponDCF, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblCouponRate * dblCouponDCF * p.notional (iEndDate) * p.couponFactor (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.notional (iStartDate) - p.notional (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.df (csqc), 1, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t||---------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                                           FLOATING PERIOD DATES AND FACTORS                                                              ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||   L -> R:                                                                                                                                                ||");

		System.out.println ("\t||           - Period Start Date                                                                                                                            ||");

		System.out.println ("\t||           - Period End Date                                                                                                                              ||");

		System.out.println ("\t||           - Reference Index Start Date                                                                                                                   ||");

		System.out.println ("\t||           - Reference Index End Date                                                                                                                     ||");

		System.out.println ("\t||           - Reference Index Fixing Date                                                                                                                  ||");

		System.out.println ("\t||           - Period Pay Date                                                                                                                              ||");

		System.out.println ("\t||           - Period FX Fixing Date                                                                                                                        ||");

		System.out.println ("\t||           - Period Is FX MTM?                                                                                                                            ||");

		System.out.println ("\t||           - Period Tenor                                                                                                                                 ||");

		System.out.println ("\t||           - Period Coupon Frequency                                                                                                                      ||");

		System.out.println ("\t||           - Period Pay Currency                                                                                                                          ||");

		System.out.println ("\t||           - Period Coupon Currency                                                                                                                       ||");

		System.out.println ("\t||           - Period Basis                                                                                                                                 ||");

		System.out.println ("\t||           - Period Base Notional                                                                                                                         ||");

		System.out.println ("\t||           - Period Notional                                                                                                                              ||");

		System.out.println ("\t||           - Period Coupon Factor                                                                                                                         ||");

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod p : ffcInAdvanceIMM.derivedStream().cashFlowPeriod()) {
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

		System.out.println ("\t||----------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                            FLOATING PERIOD LABELS AND CURVE FACTORS                            ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||   L -> R:                                                                                      ||");

		System.out.println ("\t||           - Period Start Date                                                                  ||");

		System.out.println ("\t||           - Period End Date                                                                    ||");

		System.out.println ("\t||           - Period Funding Label                                                               ||");

		System.out.println ("\t||           - Period Forward Label                                                               ||");

		System.out.println ("\t||           - Period Coupon Rate (%)                                                             ||");

		System.out.println ("\t||           - Period Coupon Year Fraction                                                        ||");

		System.out.println ("\t||           - Period Coupon Amount                                                               ||");

		System.out.println ("\t||           - Period Principal Amount                                                            ||");

		System.out.println ("\t||           - Period Discount Factor                                                             ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------------||");

		for (CompositePeriod p : ffcInAdvanceIMM.derivedStream().cashFlowPeriod()) {
			int iEndDate = p.endDate();

			int iStartDate = p.startDate();

			double dblCouponRate = p.couponMetrics (
				iEndDate,
				csqc
			).rate();

			double dblCouponDCF = p.couponDCF();

			System.out.println ("\t|| " +
				DateUtil.YYYYMMDD (iStartDate) + " => " +
				DateUtil.YYYYMMDD (iEndDate) + " | " +
				p.fundingLabel().fullyQualifiedName() + " | " +
				p.floaterLabel().fullyQualifiedName() + " | " +
				FormatUtil.FormatDouble (dblCouponRate, 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblCouponDCF, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblCouponRate * dblCouponDCF * p.notional (iEndDate) * p.couponFactor (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.notional (iStartDate) - p.notional (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (p.df (csqc), 1, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------------||");

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

		for (CompositePeriod p : ffcInAdvanceIMM.couponPeriods()) {
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
