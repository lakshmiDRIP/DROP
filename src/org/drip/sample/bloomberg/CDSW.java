
package org.drip.sample.bloomberg;

/*
 * Credit Products imports
 */

import org.drip.analytics.cashflow.*;
import org.drip.analytics.date.*;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.*;
import org.drip.product.definition.*;
import org.drip.product.rates.*;
import org.drip.product.creator.*;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.creator.*;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * CDSW contains the sample demonstrating the replication of Bloomberg's CDSW functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDSW {
	private static final java.lang.String FIELD_SEPARATOR = "   ";

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

	/*
	 * Sample demonstrating creation of credit curve from CDS instruments
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static CreditCurve CreateCreditCurveFromCDS (
		final JulianDate dtStart,
		final double[] adblQuote,
		final String[] astrTenor,
		final String strMeasure,
		final MergedDiscountForwardCurve dc,
		final double dblRecovery,
		final String strCCName,
		final double dblStrike,
		final double dblBump)
		throws Exception
	{
		String[] astrCalibMeasure = new String[adblQuote.length];
		CreditDefaultSwap[] aCDS = new CreditDefaultSwap[adblQuote.length];

		for (int i = 0; i < astrTenor.length; ++i) {
			aCDS[i] = CDSBuilder.CreateSNAC (
				dtStart,
				astrTenor[i],
				dblStrike,
				strCCName
			);

			astrCalibMeasure[i] = strMeasure;
			adblQuote[i] += dblBump;
		}

		/*
		 * Build the credit curve from the CDS instruments and the fair premium
		 */

		return ScenarioCreditCurveBuilder.Custom (
			strCCName,
			dtStart,
			aCDS,
			dc,
			adblQuote,
			astrCalibMeasure,
			dblRecovery,
			"QuotedSpread".equals (strMeasure)
		);
	}

	/*
	 * Sample demonstrating display of survival probability at the calibration instrument maturities
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void DisplayInstrumentMaturitySurvival (
		final CreditCurve cc)
		throws java.lang.Exception
	{
		CalibratableComponent[] aCDS = cc.calibComp();

		for (int i = 0; i < aCDS.length; ++i)
			System.out.println (
				aCDS[i].maturityDate() + " | " +
				cc.manifestMeasure (aCDS[i].primaryCode()) + " | " +
				FormatUtil.FormatDouble (1. - cc.survival (aCDS[i].maturityDate()), 1, 3, 1.));
	}

	/*
	 * Sample demonstrating the creation of a CDS
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static CreditDefaultSwap CreateCDS (
		final JulianDate dtStart,
		final String strTenor,
		final double dblCoupon,
		final String strCCName)
	{
		return CDSBuilder.CreateSNAC (dtStart, strTenor, dblCoupon, strCCName);
	}

	/*
	 * Sample demonstrating the generation of the full set of CDSW measure
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtCurve = DateUtil.Today();

		JulianDate dtValue = dtCurve.addDays (1);

		JulianDate dtSettle = dtValue.addBusDays (
			3,
			"USD"
		);

		/*
		 * Model the USD ISDA Standard Curve
		 */

		double dblRecovery = 0.4;
		double dblNotional = -10.e+06;
		String[] astrCashTenor = new String[] {   "1M",     "2M",     "3M",     "6M",    "12M"};
		double[] adblCashRate = new double[] {0.001864, 0.002289, 0.002638, 0.003965, 0.006759};
		String[] astrIRSTenor = new String[] {   "2Y",     "3Y",     "4Y",     "5Y",     "6Y",     "7Y",
				"8Y",     "9Y",    "10Y",    "12Y",    "15Y",    "20Y",    "25Y",    "30Y"};
		double[] adblIRSRate = new double[] {0.004750, 0.007700, 0.011600, 0.015425, 0.018900, 0.021760,
			0.024105, 0.026095, 0.027750, 0.030400, 0.032890, 0.034855, 0.035805, 0.036345};

		/*
		 * Build the USD ISDA Standard Curve
		 */

		MergedDiscountForwardCurve dc = BuildRatesCurveFromInstruments (
			dtCurve,
			astrCashTenor,
			adblCashRate,
			astrIRSTenor,
			adblIRSRate,
			0.,
			"USD"
		);

		/*
		 * Build the CDS Instrument Quotes
		 */

		String[] astrCDSTenor = new String[] {
			"6M", "1Y", "2Y", "3Y", "4Y", "5Y", "7Y", "10Y"
		};
		double[] adblCDSParSpread = new double[] {
			60., 68., 88., 102., 121., 138., 168., 188.
		};

		/*
		 * Build the Base Credit Curve
		 */

		CreditCurve cc = CreateCreditCurveFromCDS (
			dtValue,
			adblCDSParSpread,
			astrCDSTenor,
			"FairPremium",
			dc,
			dblRecovery,
			"MS",
			0.01,
			0.
		);

		/*
		 * Display Survival Probability to the instrument maturities
		 */

		DisplayInstrumentMaturitySurvival (cc);

		/*
		 * Create the CDS to price. Contract Maturity is 6Y. Traded Spread Input is 0.05 (500 bp).
		 */

		CreditDefaultSwap cds = CreateCDS (
			dtValue,
			"6Y",
			0.05,
			"MS"
		);

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtSettle,
			"USD"
		);

		CreditPricerParams pricerParams = CreditPricerParams.Standard();

		System.out.println ("\n---- Valuation Details ----");

		System.out.println ("Trade Date   : " + dtCurve);

		System.out.println ("Cash Settle  : " + dtSettle);

		System.out.println ("\n---- CDS Details ----");

		System.out.println ("Effective    : " + cds.effectiveDate());

		System.out.println ("Maturity     : " + cds.maturityDate());

		/*
		 * Generate the base CDS Measures
		 */

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Credit (
			dc,
			cc
		);

		CaseInsensitiveTreeMap<Double> mapBaseMeasures = cds.value (
			valParams,
			pricerParams,
			mktParams,
			null
		);

		double dblAccrued = mapBaseMeasures.get ("Accrued") * 100. * dblNotional;

		double dblBaseDirtyPV = mapBaseMeasures.get ("DirtyPV");

		double dblPrincipal = mapBaseMeasures.get ("Upfront") * 0.01 * dblNotional;

		System.out.println ("\n---- Base CDS Measures ----");

		System.out.println ("Price        : " + FormatUtil.FormatDouble (mapBaseMeasures.get ("Price"), 1, 2, 1.));

		System.out.println ("Principal    : " + FormatUtil.FormatDouble (dblPrincipal, 1, 0, 1.));

		System.out.println ("Accrued      : " + FormatUtil.FormatDouble (dblAccrued, 1, 0, 1.));

		System.out.println ("Accrual Days : " + FormatUtil.FormatDouble (mapBaseMeasures.get ("AccrualDays"), 1, 0, 1.));

		System.out.println ("Cash Amount  : " + FormatUtil.FormatDouble (dblAccrued + dblPrincipal, 1, 0, 1.));

		System.out.println ("Repl Spread  : " + FormatUtil.FormatDouble (mapBaseMeasures.get ("FairPremium"), 1, 4, 1.));

		/*
		 * Build the Bumped 01 Credit Curve
		 */

		CreditCurve cc01Bump = CreateCreditCurveFromCDS (
			dtValue,
			adblCDSParSpread,
			astrCDSTenor,
			"FairPremium",
			dc,
			dblRecovery,
			"MS",
			0.01,
			1.
		);

		/*
		 * Generate the 1 bp flat Credit Curve bumped  Measures
		 */

		CaseInsensitiveTreeMap<Double> mapCreditFlat01Measures = cds.value (
			valParams,
			pricerParams,
			MarketParamsBuilder.Credit (
				dc,
				cc01Bump
			),
			null
		);

		double dblCreditFlat01DirtyPV = mapCreditFlat01Measures.get ("DirtyPV");

		System.out.println ("CS01         : " + FormatUtil.FormatDouble (dblCreditFlat01DirtyPV - dblBaseDirtyPV, 1, 0, 0.01 * dblNotional));

		/*
		 * Build the Bumped 01 Rates Curve
		 */

		MergedDiscountForwardCurve dc01Bump = BuildRatesCurveFromInstruments (
			dtCurve,
			astrCashTenor,
			adblCashRate,
			astrIRSTenor,
			adblIRSRate,
			0.0001,
			"USD"
		);

		/*
		 * Generate the 1 bp flat Rates Curve bumped  Measures
		 */

		CaseInsensitiveTreeMap<Double> mapRatesFlat01Measures = cds.value (
			valParams,
			pricerParams,
			MarketParamsBuilder.Credit (
				dc01Bump,
				cc
			),
			null
		);

		double dblRatesFlat01DirtyPV = mapRatesFlat01Measures.get ("DirtyPV");

		System.out.println ("IR01         : " + FormatUtil.FormatDouble (dblRatesFlat01DirtyPV - dblBaseDirtyPV, 1, 0, 0.01 * dblNotional));

		/*
		 * Generates and displays the coupon period details for the bonds
		 */

		System.out.println ("\n---- CDS Coupon Flows ----");

		for (CompositePeriod p : cds.couponPeriods())
			System.out.println (
				DateUtil.YYYYMMDD (p.startDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.endDate()) + FIELD_SEPARATOR +
				DateUtil.YYYYMMDD (p.payDate()) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 3, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (p.couponDCF(), 1, 2, 0.01 * dblNotional) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (dc.df (p.payDate()), 1, 4, 1.) + FIELD_SEPARATOR +
				FormatUtil.FormatDouble (cc.survival (p.payDate()), 1, 4, 1.)
			);

		/*
		 * Generate the Quoted Spread Based CDS Measures
		 */

		CaseInsensitiveTreeMap<Double> mapQSMeasures = cds.valueFromQuotedSpread (
			valParams,
			pricerParams,
			mktParams,
			null,
			0.05,
			208.
		);

		System.out.println ("\n---- Quoted Spread CDS Measures ----");

		System.out.println ("QS Price     : " + FormatUtil.FormatDouble (mapQSMeasures.get ("Price"), 1, 2, 1.));

		System.out.println ("QS Repl Spd  : " + FormatUtil.FormatDouble (mapQSMeasures.get ("FairPremium"), 1, 4, 1.));
	}
}
