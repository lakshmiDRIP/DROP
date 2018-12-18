
package org.drip.sample.bondmetrics;

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.FloaterLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>Belgaum</i> demonstrates the Analytics Calculation/Reconciliation for the Bond Belgaum.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bondmetrics/README.md">Bond Analytics Metrics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Belgaum {

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

		double dblSpread = 0.0066;
		String strCurrency = "USD";
		double dblCleanPrice = 1.00717;
		double dblResetRate = 0.0190167 - dblSpread;

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2016,
			6,
			14
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2019,
			6,
			14
		);

		BondComponent bond = BondBuilder.CreateSimpleFloater (
			"Belgaum",
			"USD",
			"USD-3M",
			"",
			0.0066,
			4,
			"30/360",
			dtEffective,
			dtMaturity,
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
				p.floaterLabel().fullyQualifiedName() + " | " +
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

		EnvManager.TerminateEnv();
	}
}
