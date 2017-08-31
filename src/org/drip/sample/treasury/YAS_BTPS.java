
package org.drip.sample.treasury;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.*;
import org.drip.product.credit.BondComponent;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.FixFloatComponent;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * YAS_BTPS contains the sample demonstrating the replication of Bloomberg's Italian EUR Govvie Bond YAS
 *  Functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class YAS_BTPS {

	private static BondComponent TSYBond (
		final JulianDate dtEffective,
		final JulianDate dtMaturity,
		final int iFreq,
		final String strDayCount,
		final String strCurrency,
		final double dblCoupon)
		throws Exception
	{
		return BondBuilder.CreateSimpleFixed (
			"BTPS " + FormatUtil.FormatDouble (dblCoupon, 1, 2, 100.) + " " + dtMaturity,
			strCurrency,
			"",
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			null,
			null
		);
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
	 * Sample demonstrating building of rates curve from cash/future/swaps
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static MergedDiscountForwardCurve BuildRatesCurveFromInstruments (
		final JulianDate dtStart,
		final String[] astrCashTenor,
		final double[] adblCashRate,
		final String[] astrIRSTenor,
		final double[] adblIRSRate,
		final double dblBump,
		final String strCurrency)
		throws Exception
	{
		int iNumDCInstruments = astrCashTenor.length + adblIRSRate.length;
		int aiDate[] = new int[iNumDCInstruments];
		double adblRate[] = new double[iNumDCInstruments];
		String astrCalibMeasure[] = new String[iNumDCInstruments];
		double adblCompCalibValue[] = new double[iNumDCInstruments];
		CalibratableComponent aCompCalib[] = new CalibratableComponent[iNumDCInstruments];

		// Cash Calibration

		JulianDate dtCashEffective = dtStart.addBusDays (
			1,
			strCurrency
		);

		for (int i = 0; i < astrCashTenor.length; ++i) {
			astrCalibMeasure[i] = "Rate";
			adblRate[i] = java.lang.Double.NaN;
			adblCompCalibValue[i] = adblCashRate[i] + dblBump;

			aCompCalib[i] = SingleStreamComponentBuilder.Deposit (
				dtCashEffective,
				new JulianDate (aiDate[i] = dtCashEffective.addTenor (astrCashTenor[i]).julian()),
				ForwardLabel.Create (
					strCurrency,
					astrCashTenor[i]
				)
			);
		}

		// IRS Calibration

		JulianDate dtIRSEffective = dtStart.addBusDays (2, strCurrency);

		for (int i = 0; i < astrIRSTenor.length; ++i) {
			astrCalibMeasure[i + astrCashTenor.length] = "Rate";
			adblRate[i + astrCashTenor.length] = java.lang.Double.NaN;
			adblCompCalibValue[i + astrCashTenor.length] = adblIRSRate[i] + dblBump;

			aiDate[i + astrCashTenor.length] = dtIRSEffective.addTenor (astrIRSTenor[i]).julian();

			aCompCalib[i + astrCashTenor.length] = OTCIRS (
				dtIRSEffective,
				strCurrency,
				astrIRSTenor[i],
				0.
			);
		}

		/*
		 * Build the IR curve from the components, their calibration measures, and their calibration quotes.
		 */

		return ScenarioDiscountCurveBuilder.NonlinearBuild (
			dtStart,
			strCurrency,
			aCompCalib,
			adblCompCalibValue,
			astrCalibMeasure,
			null
		);
	}

	private static final MergedDiscountForwardCurve FundingCurve (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		String[] astrCashTenor = new String[] {"3M"};
		double[] adblCashRate = new double[] {0.00276};
		String[] astrIRSTenor = new String[] {   "1Y",    "2Y",    "3Y",    "4Y",    "5Y",    "6Y",    "7Y",
			   "8Y",    "9Y",   "10Y",   "11Y",   "12Y",   "15Y",   "20Y",   "25Y",   "30Y",   "40Y",   "50Y"};
		double[] adblIRSRate = new double[]  {0.00367, 0.00533, 0.00843, 0.01238, 0.01609, 0.01926, 0.02191,
			0.02406, 0.02588, 0.02741, 0.02870, 0.02982, 0.03208, 0.03372, 0.03445, 0.03484, 0.03501, 0.03484};

		return BuildRatesCurveFromInstruments (
			dtSpot,
			astrCashTenor,
			adblCashRate,
			astrIRSTenor,
			adblIRSRate,
			0.,
			strCurrency
		);
	}

	private static final void TSYMetrics (
		final BondComponent tsyBond,
		final double dblNotional,
		final JulianDate dtSettle,
		final CurveSurfaceQuoteContainer mktParams,
		final double dblCleanPrice)
		throws Exception
	{
		double dblAccrued = tsyBond.accrued (
			dtSettle.julian(),
			null
		);

		double dblYield = tsyBond.yieldFromPrice (
			new ValuationParams (
				dtSettle,
				dtSettle,
				tsyBond.currency()
			),
			mktParams,
			null,
			dblCleanPrice
		);

		double dblModifiedDuration = tsyBond.modifiedDurationFromPrice (
			new ValuationParams (
				dtSettle,
				dtSettle,
				tsyBond.currency()
			),
			mktParams,
			null,
			dblCleanPrice
		);

		double dblRisk = tsyBond.yield01FromPrice (
			new ValuationParams (
				dtSettle,
				dtSettle,
				tsyBond.currency()
			),
			mktParams,
			null,
			dblCleanPrice
		);

		double dblConvexity = tsyBond.convexityFromPrice (
			new ValuationParams (
				dtSettle,
				dtSettle,
				tsyBond.currency()
			),
			mktParams,
			null,
			dblCleanPrice
		);

		JulianDate dtPreviousCouponDate = tsyBond.previousCouponDate (dtSettle);

		System.out.println();

		System.out.println ("\t\t" + tsyBond.name());

		System.out.println ("\tPrice             : " + FormatUtil.FormatDouble (dblCleanPrice, 1, 4, 100.));

		System.out.println ("\tYield             : " + FormatUtil.FormatDouble (dblYield, 1, 4, 100.) + "%");

		System.out.println ("\tSettle            :  " + dtSettle);

		System.out.println();

		System.out.println ("\tModified Duration : " + FormatUtil.FormatDouble (dblModifiedDuration, 1, 4, 10000.));

		System.out.println ("\tRisk              : " + FormatUtil.FormatDouble (dblRisk, 1, 4, 10000.));

		System.out.println ("\tConvexity         : " + FormatUtil.FormatDouble (dblConvexity * dblNotional, 1, 4, 1.));

		System.out.println ("\tDV01              : " + FormatUtil.FormatDouble (dblRisk * dblNotional, 1, 0, 1.));

		System.out.println();

		System.out.println ("\tPrevious Coupon Date :  " + dtPreviousCouponDate);

		System.out.println ("\tFace                 : " + FormatUtil.FormatDouble (dblNotional, 1, 2, 1.));

		System.out.println ("\tPrincipal            : " + FormatUtil.FormatDouble (dblCleanPrice * dblNotional, 1, 2, 1.));

		System.out.println ("\tAccrued              : " + FormatUtil.FormatDouble (dblAccrued * dblNotional, 1, 2, 1.));

		System.out.println ("\tTotal                : " + FormatUtil.FormatDouble ((dblCleanPrice + dblAccrued) * dblNotional, 1, 2, 1.));

		if (null != dtPreviousCouponDate)
			System.out.println ("\tAccrual Days         : " + (dtSettle.julian() - dtPreviousCouponDate.julian()));
	}

	public static final void main (
		final String astrArgs[])
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2015,
			DateUtil.MAY,
			5
		);

		JulianDate dtEffective = DateUtil.CreateFromYMD (
			2013,
			DateUtil.AUGUST,
			25
		);

		JulianDate dtMaturity = DateUtil.CreateFromYMD (
			2024,
			DateUtil.MARCH,
			1
		);

		int iFreq = 2;
		String strDayCount = "DCAct_Act_UST";
		String strCurrency = "EUR";
		double dblCoupon = 0.045;
		double dblNotional = 1000000.;
		double dblCleanPrice = 1.2562;

		BondComponent tsyBond = TSYBond (
			dtEffective,
			dtMaturity,
			iFreq,
			strDayCount,
			strCurrency,
			dblCoupon
		);

		System.out.println();

		System.out.println ("\tEffective : " + tsyBond.effectiveDate());

		System.out.println ("\tMaturity  : " + tsyBond.maturityDate());

		System.out.println();

		MergedDiscountForwardCurve dc = FundingCurve (
			dtSpot,
			strCurrency
		);

		TSYMetrics (
			tsyBond,
			dblNotional,
			dtSpot,
			MarketParamsBuilder.Create (
				dc,
				null,
				null,
				null,
				null,
				null,
				null
			),
			dblCleanPrice
		);

		System.out.println ("\n\tCashflow\n\t--------");

		for (CompositePeriod p : tsyBond.couponPeriods())
			System.out.println ("\t\t" +
				DateUtil.YYYYMMDD (p.startDate()) + " | " +
				DateUtil.YYYYMMDD (p.endDate()) + " | " +
				DateUtil.YYYYMMDD (p.payDate()) + " | " +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 4, 1.) + " ||"
			);
	}
}
