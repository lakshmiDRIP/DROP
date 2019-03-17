
package org.drip.sample.assetbacked;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.Helper;
import org.drip.numerical.common.FormatUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.ConstantPaymentBondBuilder;
import org.drip.product.definition.Bond;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>PrepayableConstantPaymentBond</i> demonstrates the Construction and Valuation of a Custom Constant
 * Payment Mortgage Bond.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetBackedAnalyticsLibrary.md">Asset Backed Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/assetbacked/README.md">Asset Backed</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PrepayableConstantPaymentBond {

	private static final void BondMetrics (
		final Bond bond,
		final double dblInitialNotional,
		final JulianDate dtSettle,
		final CurveSurfaceQuoteContainer mktParams,
		final double dblCleanPrice)
		throws Exception
	{
		double dblAccrued = bond.accrued (
			dtSettle.julian(),
			null
		);

		ValuationParams valParams = ValuationParams.Spot (dtSettle.julian());

		double dblYield = bond.yieldFromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		double dblModifiedDuration = bond.modifiedDurationFromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		double dblRisk = bond.yield01FromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		double dblConvexity = bond.convexityFromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		JulianDate dtPreviousCouponDate = bond.previousCouponDate (dtSettle);

		double dblCurrentPrincipal = bond.notional (dtPreviousCouponDate.julian()) * dblInitialNotional;

		double dblAccruedAmount = dblAccrued * dblInitialNotional;

		System.out.println ("\t-------------------------------------");

		System.out.println ("\tAnalytics Metrics for " + bond.name());

		System.out.println ("\t-------------------------------------");

		System.out.println ("\tPrice             : " + FormatUtil.FormatDouble (dblCleanPrice, 1, 4, 100.));

		System.out.println ("\tYield             : " + FormatUtil.FormatDouble (dblYield, 1, 2, 100.) + "%");

		System.out.println ("\tSettle            :  " + dtSettle);

		System.out.println();

		System.out.println ("\tModified Duration : " + FormatUtil.FormatDouble (dblModifiedDuration, 1, 4, 10000.));

		System.out.println ("\tRisk              : " + FormatUtil.FormatDouble (dblRisk, 1, 4, 10000.));

		System.out.println ("\tConvexity         : " + FormatUtil.FormatDouble (dblConvexity * dblInitialNotional, 1, 4, 1.));

		System.out.println ("\tDV01              : " + FormatUtil.FormatDouble (dblRisk * dblInitialNotional, 1, 2, 1.));

		System.out.println();

		System.out.println ("\tPrevious Coupon Date : " + dtPreviousCouponDate);

		System.out.println ("\tFace                 : " + FormatUtil.FormatDouble (dblInitialNotional, 1, 2, 1.));

		System.out.println ("\tNotional             : " + FormatUtil.FormatDouble (dblInitialNotional, 1, 2, 1.));

		System.out.println ("\tCurrent Principal    : " + FormatUtil.FormatDouble (dblCurrentPrincipal, 1, 2, 1.));

		System.out.println ("\tAccrued              : " + FormatUtil.FormatDouble (dblAccruedAmount, 1, 6, 1.));

		System.out.println ("\tTotal                : " + FormatUtil.FormatDouble (dblCleanPrice * dblCurrentPrincipal + dblAccruedAmount, 1, 2, 1.));

		System.out.println ("\tNPV                  : " + FormatUtil.FormatDouble (dblCleanPrice * dblCurrentPrincipal + dblAccruedAmount, 1, 2, 1.));

		System.out.println ("\tAccrual Days         : " + FormatUtil.FormatDouble (dtSettle.julian() - dtPreviousCouponDate.julian(), 1, 0, 1.));
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblBeginPrincipalFactor = 1.;
		double dblCouponRate = 0.1189;
		double dblServiceFeeRate = 0.00;
		double dblCPR = 0.01;
		double dblBondNotional = 147544.28;
		String strDayCount = "Act/365";
		String strCurrency = "USD";
		int iNumPayment = 48;
		int iPayFrequency = 12;
		double dblConstantPaymentAmount = 3941.98;

		double dblFixedPaymentAmount = ConstantPaymentBondBuilder.ConstantUniformPaymentAmount (
			dblBondNotional,
			dblCouponRate,
			iNumPayment / iPayFrequency
		);

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2015,
			DateUtil.OCTOBER,
			22
		);

		Bond bond = ConstantPaymentBondBuilder.Prepay (
			"FPMA 11.89 2019",
			dtEffective,
			strCurrency,
			iNumPayment,
			strDayCount,
			iPayFrequency,
			dblCouponRate,
			0.,
			dblCPR,
			dblConstantPaymentAmount,
			dblBondNotional
		);

		System.out.println ("\n\n\t|------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                         FIXED CASH-FLOW MORTGAGE BOND ANALYTICS                                                                            ||");

		System.out.println ("\t|                                         ----- --------- -------- ---- ---------                                                                            ||");

		System.out.println ("\t|    L -> R:                                                                                                                                                 ||");

		System.out.println ("\t|            - Start Date                                                                                                                                    ||");

		System.out.println ("\t|            - End Date                                                                                                                                      ||");

		System.out.println ("\t|            - Pay Date                                                                                                                                      ||");

		System.out.println ("\t|            - Discount Factor                                                                                                                               ||");

		System.out.println ("\t|            - Survival Factor                                                                                                                               ||");

		System.out.println ("\t|            - Principal Factor                                                                                                                              ||");

		System.out.println ("\t|            - Accrual Days                                                                                                                                  ||");

		System.out.println ("\t|            - Accrual Fraction                                                                                                                              ||");

		System.out.println ("\t|            - Coupon Rate (%)                                                                                                                               ||");

		System.out.println ("\t|            - Coupon Amount                                                                                                                                 ||");

		System.out.println ("\t|            - Fee Rate (%)                                                                                                                                  ||");

		System.out.println ("\t|            - Fee Amount                                                                                                                                    ||");

		System.out.println ("\t|            - Principal Amount                                                                                                                              ||");

		System.out.println ("\t|            - Total Amount                                                                                                                                  ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod p : bond.couponPeriods()) {
			double dblPeriodCouponRate = p.couponMetrics (
				dtEffective.julian(),
				null
			).rate();

			double dblCouponDCF = p.couponDCF();

			double dblEndPrincipalFactor = bond.notional (p.endDate());

			double dblPrincipalAmount = (dblBeginPrincipalFactor - dblEndPrincipalFactor) * dblBondNotional;

			double dblCouponAmount = dblBeginPrincipalFactor * dblPeriodCouponRate * dblCouponDCF * dblBondNotional;

			double dblYieldDF = Helper.Yield2DF (
				iPayFrequency,
				dblCouponRate,
				Convention.YearFraction (
					dtEffective.julian(),
					p.endDate(),
					"30/360",
					false,
					null,
					strCurrency
				)
			);

			System.out.println ("\t| [" +
				DateUtil.YYYYMMDD (p.startDate()) + " -> " +
				DateUtil.YYYYMMDD (p.endDate()) + "] => " +
				DateUtil.YYYYMMDD (p.payDate()) + " | " +
				FormatUtil.FormatDouble (1., 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblYieldDF, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblBeginPrincipalFactor, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblCouponDCF * 365, 1, 0, 1.) + " | " +
				FormatUtil.FormatDouble (dblCouponDCF, 1, 10, 1.) + " | " +
				FormatUtil.FormatDouble (dblPeriodCouponRate, 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblCouponAmount, 2, 2, 1.) + " | " +
				FormatUtil.FormatDouble (dblServiceFeeRate, 2, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dblCouponAmount * dblServiceFeeRate / dblPeriodCouponRate, 2, 2, 1.) + " | " +
				FormatUtil.FormatDouble (dblPrincipalAmount, 3, 2, 1.) + " | " +
				FormatUtil.FormatDouble (dblPrincipalAmount + dblCouponAmount, 3, 2, 1.) + " ||"
			);

			dblBeginPrincipalFactor = dblEndPrincipalFactor;
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------------------------------||\n");

		JulianDate dtSettle = DateUtil.CreateFromYMD (
			2015,
			DateUtil.DECEMBER,
			1
		);

		double dblCleanPrice = 1.00; // PAR

		CurveSurfaceQuoteContainer mktParams = new CurveSurfaceQuoteContainer();

		BondMetrics (
			bond,
			dblBondNotional,
			dtSettle,
			mktParams,
			dblCleanPrice
		);

		Bond bondFeeAdjusted = ConstantPaymentBondBuilder.Prepay (
			"SERVICEABLE FPMA 11.89 2019",
			dtEffective,
			strCurrency,
			iNumPayment,
			strDayCount,
			iPayFrequency,
			dblCouponRate,
			dblServiceFeeRate,
			dblCPR,
			dblConstantPaymentAmount,
			dblBondNotional
		);

		ValuationParams valParams = ValuationParams.Spot (dtSettle.julian());

		double dblYieldFeeAdjusted = bondFeeAdjusted.yieldFromPrice (
			valParams,
			mktParams,
			null,
			dblCleanPrice
		);

		System.out.println ("\tFee Adjusted Yield   : " + FormatUtil.FormatDouble (dblYieldFeeAdjusted, 1, 2, 100.) + "%");

		System.out.println ("\n\tUniform Constant Mortgage Amount       => " + FormatUtil.FormatDouble (dblFixedPaymentAmount, 1, 2, 1.));

		System.out.println (
			"\tFee Unadjusted Price From Coupon Yield => " + FormatUtil.FormatDouble (
				bond.priceFromYield (
					valParams,
					mktParams,
					null,
					dblCouponRate
				),
			1, 2, 100.)
		);

		System.out.println (
			"\tFee Adjusted Price From Coupon Yield   => " + FormatUtil.FormatDouble (
				bondFeeAdjusted.priceFromYield (
					valParams,
					mktParams,
					null,
					dblCouponRate
				),
			1, 2, 100.)
		);

		EnvManager.TerminateEnv();
	}
}
