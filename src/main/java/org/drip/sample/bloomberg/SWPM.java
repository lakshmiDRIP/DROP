
package org.drip.sample.bloomberg;

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.param.creator.*;
import org.drip.param.market.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.definition.*;
import org.drip.product.rates.*;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>SWPM</i> contains the sample demonstrating the replication of Bloomberg's SWPM functionality.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/bloomberg/README.md">Bloomberg CDSO CDSW SWPM YAS</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SWPM {
	private static final String FIELD_SEPARATOR = "    ";

	/*
	 * Construct the Array of Deposit Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] DepositInstrumentsFromMaturityDays (
		final JulianDate dtEffective,
		final int[] aiDay,
		final int iNumFuture,
		final String strCurrency)
		throws Exception
	{
		CalibratableComponent[] aCalibComp = new CalibratableComponent[aiDay.length + iNumFuture];

		for (int i = 0; i < aiDay.length; ++i)
			aCalibComp[i] = SingleStreamComponentBuilder.Deposit (
				dtEffective,
				dtEffective.addBusDays (
					aiDay[i],
					strCurrency
				),
				ForwardLabel.Create (
					strCurrency,
					aiDay[i] + "D"
				)
			);

		CalibratableComponent[] aEDF = SingleStreamComponentBuilder.ForwardRateFuturesPack (
			dtEffective,
			iNumFuture,
			strCurrency
		);

		for (int i = aiDay.length; i < aiDay.length + iNumFuture; ++i)
			aCalibComp[i] = aEDF[i - aiDay.length];

		return aCalibComp;
	}

	private static final FixFloatComponent OTCIRS (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strMaturityTenor,
		final double dblCoupon)
	{
		FixedFloatSwapConvention ffConv = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"ALL",
			strMaturityTenor,
			"MAIN"
		);

		return ffConv.createFixFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblCoupon,
			0.,
			1.
		);
	}

	/*
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final CalibratableComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtEffective,
		final String strCurrency,
		final String[] astrTenor,
		final double[] adblCoupon)
		throws Exception
	{
		CalibratableComponent[] aCalibComp = new CalibratableComponent[astrTenor.length];

		for (int i = 0; i < astrTenor.length; ++i)
			aCalibComp[i] = OTCIRS (
				dtEffective,
				strCurrency,
				astrTenor[i],
				adblCoupon[i]
			);

		return aCalibComp;
	}

	/*
	 * Construct the discount curve using the following steps:
	 * 	- Construct the array of cash instruments and their quotes.
	 * 	- Construct the array of swap instruments and their quotes.
	 * 	- Construct a shape preserving and smoothing KLK Hyperbolic Spline from the cash/swap instruments.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final MergedDiscountForwardCurve MakeDC (
		final JulianDate dtSpot,
		final String strCurrency,
		final double dblBump)
		throws Exception
	{
		/*
		 * Construct the array of Deposit instruments and their quotes.
		 */

		CalibratableComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			new int[] {},
			0,
			strCurrency
		);

		double[] adblDepositQuote = new double[] {}; // Futures

		/*
		 * Construct the array of Swap instruments and their quotes.
		 */

		double[] adblSwapQuote = new double[] {
			0.0009875 + dblBump,   //  9M
			0.00122 + dblBump,     //  1Y
			0.00223 + dblBump,     // 18M
			0.00383 + dblBump,     //  2Y
			0.00827 + dblBump,     //  3Y
			0.01245 + dblBump,     //  4Y
			0.01605 + dblBump,     //  5Y
			0.02597 + dblBump      // 10Y
		};

		String[] astrSwapManifestMeasure = new String[] {
			"SwapRate",		//  9M
			"SwapRate",     //  1Y
			"SwapRate",     // 18M
			"SwapRate",     //  2Y
			"SwapRate",     //  3Y
			"SwapRate",     //  4Y
			"SwapRate",     //  5Y
			"SwapRate"      // 10Y
		};

		CalibratableComponent[] aSwapComp = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"9M", "1Y", "18M", "2Y", "3Y", "4Y", "5Y", "10Y"
			},
			new double[] {
				0.0009875, 0.00122, 0.00223, 0.00383, 0.00827, 0.01245, 0.01605, 0.02597
			}
		);

		/*
		 * Construct a shape preserving and smoothing KLK Hyperbolic Spline from the cash/swap instruments.
		 */

		return ScenarioDiscountCurveBuilder.CubicKLKHyperbolicDFRateShapePreserver (
			"KLK_HYPERBOLIC_SHAPE_TEMPLATE",
			new ValuationParams (
				dtSpot,
				dtSpot,
				"USD"
			),
			aDepositComp,
			adblDepositQuote,
			null,
			aSwapComp,
			adblSwapQuote,
			astrSwapManifestMeasure,
			true
		);
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		JulianDate dtValue = DateUtil.Today();

		JulianDate dtSettle = dtValue.addBusDays (
			2,
			"USD"
		);

		System.out.println ("\n---- Valuation Details ----\n");

		System.out.println ("Trade Date  : " + dtValue);

		System.out.println ("Settle Date : " + dtSettle);

		double dblCoupon = 0.0187;
		double dblFixing = 0.00087;
		double dblNotional = 10.e+06;

		/*
		 * Model the discount curve instrument quotes. Best pulled from Curves #42 in the BBG SWPM "Curves" tab
		 */

		/*
		 * Build the Discount Curve
		 */

		MergedDiscountForwardCurve dc = MakeDC (
			dtValue,
			"USD",
			0.
		);

		JulianDate dtEffective = dtValue.addBusDays (
			2,
			"USD"
		);

		JulianDate dtMaturity = dtEffective.addTenor ("5Y");

		/*
		 * Build the Fixed Receive Stream
		 */

		FixFloatComponent swap = OTCIRS (
			dtEffective,
			"USD",
			"5Y",
			0.
		);

		System.out.println ("\n---- Swap Details ----\n");

		System.out.println ("Effective: " + dtEffective);

		System.out.println ("Maturity:  " + dtMaturity);

		/*
		 * Set up the base market parameters, including base discount curves and the base fixings
		 */

		LatentStateFixingsContainer lsfc = new LatentStateFixingsContainer();

		ComposableUnitFloatingPeriod cufs = ((ComposableUnitFloatingPeriod) (swap.derivedStream().periods().get (0).periods().get (0)));

		lsfc.add (
			cufs.referenceIndexPeriod().fixingDate(),
			swap.derivedStream().forwardLabel(),
			dblFixing
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			lsfc
		);

		/*
		 * Set up the valuation parameters
		 */

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtSettle,
			"USD"
		);

		/*
		 * Generate the base scenario measures for the swap
		 */

		CaseInsensitiveTreeMap<Double> mapSwapCalc = swap.value (
			valParams,
			null,
			mktParams,
			null
		);

		double dblBasePV = mapSwapCalc.get ("PV");

		double dblBaseFixedDV01 = mapSwapCalc.get ("FixedDV01");

		System.out.println ("\n---- Swap Output Measures ----\n");

		System.out.println ("Mkt Val      : " + FormatUtil.FormatDouble (dblBasePV, 0, 0, dblNotional));

		System.out.println ("Par Cpn      : " + FormatUtil.FormatDouble (mapSwapCalc.get ("FairPremium"), 1, 5, 100.));

		System.out.println ("Fixed DV01   : " + FormatUtil.FormatDouble (dblBaseFixedDV01, 0, 0, dblNotional));

		/*
		 * Set up the fixings bumped market parameters - these use base discount curve and the bumped fixing
		 */

		lsfc.add (
			cufs.referenceIndexPeriod().fixingDate(),
			swap.derivedStream().forwardLabel(),
			dblFixing + 0.0001
		);

		CurveSurfaceQuoteContainer mktParamsFixingsBumped = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			lsfc
		);

		/*
		 * Generate the fixing bumped scenario measures for the swap
		 */

		CaseInsensitiveTreeMap<Double> mapSwapFixingsBumpedCalc = swap.value (
			valParams,
			null,
			mktParamsFixingsBumped,
			null
		);

		double dblFixingsDV01 = mapSwapFixingsBumpedCalc.get ("PV") - dblBasePV;

		System.out.println ("Fixings DV01 : " + FormatUtil.FormatDouble (dblFixingsDV01, 0, 0, dblNotional));

		System.out.println ("Total DV01   : " + FormatUtil.FormatDouble (dblBaseFixedDV01 + dblFixingsDV01, 0, 0, dblNotional));

		/*
		 * Set up the rate flat bumped market parameters - these use the bumped base discount curve and the base fixing
		 */

		MergedDiscountForwardCurve dcBumped = MakeDC (
			dtValue,
			"USD",
			-0.0001
		);

		lsfc.add (
			dtEffective,
			swap.derivedStream().forwardLabel(),
			dblFixing - 0.0001
		);

		CurveSurfaceQuoteContainer mktParamsRateBumped = MarketParamsBuilder.Create (
			dcBumped,
			null,
			null,
			null,
			null,
			null,
			lsfc
		);

		/*
		 * Generate the rate flat bumped scenario measures for the swap
		 */

		CaseInsensitiveTreeMap<Double> mapSwapRateBumpedCalc = swap.value (
			valParams,
			null,
			mktParamsRateBumped,
			null
		);

		System.out.println ("PV01         : " + FormatUtil.FormatDouble (mapSwapRateBumpedCalc.get ("PV") - dblBasePV, 0, 0, dblNotional));

		/*
		 * Generate the Swap's fixed cash flows
		 */

		System.out.println ("\n---- Fixed Cashflow ----\n");

		for (CompositePeriod p : swap.referenceStream().cashFlowPeriod())
			System.out.println (
				DateUtil.YYYYMMDD (p.payDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.startDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.endDate()) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF() * 360, 0, 0, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF(), 0, 2, dblCoupon * dblNotional) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dc.df (p.payDate()), 1, 4, 1.)
			);

		/*
		 * Generate the Swap's floating cash flows
		 */

		System.out.println ("\n---- Floating Cashflow ----\n");

		for (CompositePeriod p : swap.derivedStream().cashFlowPeriod())
			System.out.println (
				DateUtil.YYYYMMDD (p.payDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.startDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.endDate()) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF() * 360, 0, 0, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dc.df (p.payDate()), 1, 4, 1.)
			);

		EnvManager.TerminateEnv();
	}
}
