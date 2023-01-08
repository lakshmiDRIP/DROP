
package org.drip.sample.bondmetrics;

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.daycount.DateAdjustParams;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.FloaterLabel;

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
 * <i>Agartala</i> demonstrates the Analytics Calculation/Reconciliation for the Bond Agartala.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bondmetrics/README.md">Bond Relative Value Replication Demonstration</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Agartala {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

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
			1
		);

		String[] astrDepositTenor = new String[] {
			"2D"
		};

		double[] adblDepositQuote = new double[] {
			0.013161 // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.013225,	// 98.6775
			0.01425,	// 98.575
			0.01475,	// 98.525
			0.01525,	// 98.475
			0.01575,    // 98.425
			0.01650     // 98.350
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
			0.015540, //  2Y
			0.016423, //  3Y
			0.017209, //  4Y
			0.017980, //  5Y
			0.018743, //  6Y
			0.019455, //  7Y
			0.020080, //  8Y
			0.020651, //  9Y
			0.021195, // 10Y
			0.021651, // 11Y
			0.022065, // 12Y
			0.022952, // 15Y
			0.023825, // 20Y
			0.024175, // 25Y
			0.024347, // 30Y
			0.024225, // 40Y
			0.023968  // 50Y
		};

		double dblSpread = 0.0060;
		String strCurrency = "USD";
		double dblCleanPrice = 1.00717;
		double dblResetRate = 0.0191722 - dblSpread;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2016,
			6,
			12
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2019,
			5,
			24
		);

		BondComponent bond = BondBuilder.CreateSimpleFloaterFP (
			"Agartala",
			strCurrency,
			strCurrency + "-3M",
			"",
			dblSpread,
			4,
			"Act/360",
			dtEffective,
			dtMaturity,
			DateUtil.CreateFromYMD (
				2016,
				8,
				24
			).julian(),
			DateUtil.CreateFromYMD (
				2019,
				2,
				24
			).julian(),
			new DateAdjustParams (
				Convention.DATE_ROLL_FOLLOWING,
				0,
				strCurrency
			),
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
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

		CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Create (
			mdfc,
			null,
			null,
			null,
			null,
			null,
			null
		);

		FloaterLabel fl = bond.floaterSetting().fri();

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

		System.out.println();

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                                           PERIOD LABELS AND CURVE FACTORS                                          ||");

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||   L -> R:                                                                                                          ||");

		System.out.println ("\t||           - Period Start Date                                                                                      ||");

		System.out.println ("\t||           - Period End Date                                                                                        ||");

		System.out.println ("\t||           - Period Pay Date                                                                                        ||");

		System.out.println ("\t||           - Period Credit Label                                                                                    ||");

		System.out.println ("\t||           - Period Funding Label                                                                                   ||");

		System.out.println ("\t||           - Period Forward Label                                                                                   ||");

		System.out.println ("\t||           - Period Coupon Rate (%)                                                                                 ||");

		System.out.println ("\t||           - Period Coupon Year Fraction                                                                            ||");

		System.out.println ("\t||           - Period Coupon Amount                                                                                   ||");

		System.out.println ("\t||           - Period Principal Amount                                                                                ||");

		System.out.println ("\t||           - Period Discount Factor                                                                                 ||");

		System.out.println ("\t||           - Period Survival Probability                                                                            ||");

		System.out.println ("\t||           - Period Recovery                                                                                        ||");

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod p : bond.couponPeriods()) {
			int iEndDate = p.endDate();

			int iPayDate = p.payDate();

			int iStartDate = p.startDate();

			double dblCouponRate = bond.couponMetrics (
				iEndDate,
				valParams,
				csqc
			).rate();

			double dblCouponDCF = p.couponDCF();

			System.out.println ("\t|| " +
				DateUtil.YYYYMMDD (iStartDate) + " => " +
				DateUtil.YYYYMMDD (iEndDate) + " | " +
				DateUtil.YYYYMMDD (iPayDate) + " | ? | " +
				p.fundingLabel().fullyQualifiedName() + " | ? | " +
				FormatUtil.FormatDouble (dblCouponRate, 1, 3, 100.) + "% | " +
				FormatUtil.FormatDouble (dblCouponDCF, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblCouponRate * dblCouponDCF * p.notional (iEndDate) * p.couponFactor (iEndDate), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (p.notional (iStartDate) - p.notional (iEndDate), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (1., 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (1., 2, 2, 100.) + "% ||"
			);
		}

		System.out.println ("\t|| " +
			DateUtil.YYYYMMDD (dtEffective.julian()) + " => " +
			DateUtil.YYYYMMDD (bond.maturityDate().julian()) + " | " +
			DateUtil.YYYYMMDD (bond.maturityDate().julian()) + " | ? | " +
			bond.fundingLabel().fullyQualifiedName() + " | ? | " +
			FormatUtil.FormatDouble (0., 1, 3, 100.) + "% | " +
			FormatUtil.FormatDouble (0., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (0., 1, 6, 1.) + " | " +
			FormatUtil.FormatDouble (bond.notional (bond.maturityDate().julian()), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (1., 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (1., 2, 2, 100.) + "% ||"
		);

		System.out.println ("\t||--------------------------------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
