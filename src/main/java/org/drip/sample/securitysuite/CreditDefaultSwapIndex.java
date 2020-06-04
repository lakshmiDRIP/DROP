
package org.drip.sample.securitysuite;

import java.util.Map;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.CDSBuilder;
import org.drip.product.definition.CreditDefaultSwap;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>CreditDefaultSwapIndex</i> demonstrates the Analytics Calculation/Reconciliation for a CDX.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/securitysuite/README.md">Custom Security Relative Value Demonstration</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditDefaultSwapIndex {

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
			0.013161 + dblBump // 2D
		};

		double[] adblFuturesQuote = new double[] {
			0.013225 + dblBump,	// 98.6775
			0.014250 + dblBump,	// 98.5750
			0.014750 + dblBump,	// 98.5250
			0.015250 + dblBump,	// 98.4750
			0.015750 + dblBump,  // 98.4250
			0.016500 + dblBump   // 98.3500
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
			0.015540 + dblBump, //  2Y
			0.016423 + dblBump, //  3Y
			0.017209 + dblBump, //  4Y
			0.017980 + dblBump, //  5Y
			0.018743 + dblBump, //  6Y
			0.019455 + dblBump, //  7Y
			0.020080 + dblBump, //  8Y
			0.020651 + dblBump, //  9Y
			0.021195 + dblBump, // 10Y
			0.021651 + dblBump, // 11Y
			0.022065 + dblBump, // 12Y
			0.022952 + dblBump, // 15Y
			0.023825 + dblBump, // 20Y
			0.024175 + dblBump, // 25Y
			0.024347 + dblBump, // 30Y
			0.024225 + dblBump, // 40Y
			0.023968 + dblBump  // 50Y
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

	private static final CreditCurve CreditCurve (
		final JulianDate dtSpot,
		final String strCreditCurve,
		final MergedDiscountForwardCurve mdfc,
		final double dblBump)
		throws Exception
	{
		return LatentMarketStateBuilder.CreditCurve (
			dtSpot,
			strCreditCurve,
			new String[] {
				 "6M",
				 "1Y",
				 "2Y",
				 "3Y",
				 "4Y",
				 "5Y",
				 "7Y",
				"10Y",
				"20Y",
				"30Y",
			},
			new double[] {
				392.509,	//  6M
				320.707,	//  1Y
				393.624,	//  2Y
				472.869,	//  3Y
				570.360,	//  4Y
				663.920,	//  5Y
				779.463,	//  7Y
				957.555, 	// 10Y
				908.712, 	// 20Y
				900.297, 	// 30Y
			},
			new double[] {
				392.509,	//  6M
				320.707,	//  1Y
				393.624,	//  2Y
				472.869,	//  3Y
				570.360,	//  4Y
				663.920,	//  5Y
				779.463,	//  7Y
				957.555, 	// 10Y
				908.712, 	// 20Y
				900.297, 	// 30Y
			},
			"FairPremium",
			mdfc
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.SEPTEMBER,
			17
		);

		JulianDate dtIssue = DateUtil.CreateFromYMD (
			2017,
			DateUtil.JUNE,
			20
		);

		String strCDXTenor = "5Y";
		String strCurrency = "USD";
		String strCDXName = "CDXNAHY";
		double dblCDXFixedCoupon = 0.05;

		CreditDefaultSwap cdx = CDSBuilder.CreateSNAC (
			dtIssue,
			strCDXTenor,
			dblCDXFixedCoupon,
			strCDXName
		);

		MergedDiscountForwardCurve mdfc = FundingCurve (
			dtSpot,
			strCurrency,
			0.
		);

		CreditCurve cc = CreditCurve (
			dtSpot,
			strCDXName,
			mdfc,
			0.
		);

		CurveSurfaceQuoteContainer csqc = MarketParamsBuilder.Credit (
			mdfc,
			cc
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		CreditPricerParams pricerParams = CreditPricerParams.Standard();

		CaseInsensitiveTreeMap<Double> mapOutput = cdx.value (
			valParams,
			pricerParams,
			csqc,
			null
		);

		System.out.println ("");

		System.out.println ("\t |-----------------------------------------------|");

		for (Map.Entry<String, Double> me : mapOutput.entrySet())
			System.out.println ("\t | " + me.getKey() + " => " + me.getValue());

		System.out.println ("\t |-----------------------------------------------|");

		System.out.println ("");

		System.out.println ("\t |---------------------------------------------------------------------------||");

		for (CompositePeriod p : cdx.couponPeriods())
			System.out.println (
				"\t | " +
				DateUtil.YYYYMMDD (p.startDate()) + " | " +
				DateUtil.YYYYMMDD (p.endDate()) + " | " +
				DateUtil.YYYYMMDD (p.payDate()) + " | " +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 2, 0.01 * 1.) + " | " +
				FormatUtil.FormatDouble (mdfc.df (p.payDate()), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (cc.survival (p.payDate()), 1, 4, 1.) + " ||"
			);

		System.out.println ("\t |---------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
