
package org.drip.product.creator;

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
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * CDSBuilder contains the suite of helper functions for creating the CreditDefaultSwap product from the
 * 	parameters/byte array streams. It also creates the standard EU, NA, ASIA contracts, CDS with amortization
 *  schedules, and custom CDS from product codes/tenors.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDSBuilder {

	/**
	 * Create the credit default swap from the effective/maturity dates, coupon, IR curve name, and
	 * 	component credit valuation parameters.
	 * 
	 * @param dtEffective JulianDate effective
	 * @param dtMaturity JulianDate maturity
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param cs Credit Setting Parameters
	 * @param strCalendar Optional Holiday Calendar for Accrual calculation
	 * @param bAdjustDates Roll using the FWD mode for the period end dates and the pay dates
	 * 
	 * @return CreditDefaultSwap product
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateCDS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final org.drip.product.params.CreditSetting cs,
		final java.lang.String strCalendar,
		final boolean bAdjustDates)
	{
		if (null == dtEffective || null == dtMaturity || null == strCurrency || strCurrency.isEmpty() || null
			== cs || !org.drip.quant.common.NumberUtil.IsValid (dblCoupon))
			return null;

		try {
			org.drip.analytics.daycount.DateAdjustParams dap = bAdjustDates ? new
				org.drip.analytics.daycount.DateAdjustParams 
					(org.drip.analytics.daycount.Convention.DATE_ROLL_FOLLOWING, 1, strCalendar) : null;

			org.drip.product.definition.CreditDefaultSwap cds = new org.drip.product.credit.CDSComponent
				(dtEffective.julian(), dtMaturity.julian(), dblCoupon, 4, "Act/360", "Act/360", "", false,
					null, null, null, dap, dap, dap, dap, null, null, 1., strCurrency, cs, strCalendar);

			cds.setPrimaryCode ("CDS." + dtMaturity.toString() + "." + cs.creditCurveName());

			return cds;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the credit default swap from the effective/maturity dates, coupon, IR curve name, and
	 * 	credit curve.
	 * 
	 * @param dtEffective JulianDate effective
	 * @param dtMaturity JulianDate maturity
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param dblRecovery Recovery Rate
	 * @param strCredit Credit curve name
	 * @param strCalendar Optional Holiday Calendar for Accrual calculation
	 * @param bAdjustDates Roll using the FWD mode for the period end dates and the pay dates
	 * 
	 * @return CreditDefaultSwap product
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateCDS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final double dblRecovery,
		final java.lang.String strCredit,
		final java.lang.String strCalendar,
		final boolean bAdjustDates)
	{
		if (null == dtEffective || null == dtMaturity || null == strCurrency || strCurrency.isEmpty() || null
			== strCredit || strCredit.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid (dblCoupon))
			return null;

		org.drip.product.params.CreditSetting cs = new org.drip.product.params.CreditSetting (30,
			dblRecovery, true, strCredit, true);

		return cs.validate() ? CreateCDS (dtEffective, dtMaturity, dblCoupon, strCurrency, cs, strCalendar,
			bAdjustDates) : null;
	}

	/**
	 * Create the credit default swap from the effective date, tenor, coupon, IR curve name, and component
	 * 	credit valuation parameters.
	 * 
	 * @param dtEffective JulianDate effective
	 * @param strTenor String tenor
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param cs Credit Setting Parameters
	 * @param strCalendar Optional Holiday Calendar for Accrual calculation
	 * 
	 * @return CreditDefaultSwap product
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateCDS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final org.drip.product.params.CreditSetting cs,
		final java.lang.String strCalendar)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty() || null == strCurrency ||
			strCurrency.isEmpty() || null == cs || !org.drip.quant.common.NumberUtil.IsValid (dblCoupon))
			return null;

		try {
			org.drip.product.definition.CreditDefaultSwap cds = new org.drip.product.credit.CDSComponent
				(dtEffective.julian(), dtEffective.addTenor (strTenor).julian(), dblCoupon, 4, "30/360",
					"30/360", "", true, null, null, null, null, null, null, null, null, null, 100.,
						strCurrency, cs, strCalendar);

			cds.setPrimaryCode ("CDS." + strTenor + "." + cs.creditCurveName());

			return cds;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the credit default swap from the effective/maturity dates, coupon, IR curve name, and credit
	 * 	curve.
	 * 
	 * @param dtEffective JulianDate effective
	 * @param strTenor String tenor
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param strCredit Credit curve name
	 * @param strCalendar Optional Holiday Calendar for accrual calculation
	 * 
	 * @return CreditDefaultSwap product
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateCDS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final java.lang.String strCredit,
		final java.lang.String strCalendar)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty() || null == strCurrency ||
			strCurrency.isEmpty() || null == strCredit || strCredit.isEmpty() ||
				!org.drip.quant.common.NumberUtil.IsValid (dblCoupon))
			return null;

		org.drip.product.params.CreditSetting cs = new org.drip.product.params.CreditSetting (30,
			java.lang.Double.NaN, true, strCredit, true);

		return cs.validate() ? CreateCDS (dtEffective, strTenor, dblCoupon, strCurrency, cs, strCalendar) :
			null;
	}

	/**
	 * Create an SNAC style CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon SNAC strike coupon
	 * @param strCurrency Currency
	 * @param strCredit Credit Curve name
	 * @param strCalendar Holiday Calendar
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSNAC (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final java.lang.String strCredit,
		final java.lang.String strCalendar)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtEffective.nextCreditIMM (3);

		if (null == dtFirstCoupon) return null;

		org.drip.product.definition.CreditDefaultSwap cds = CreateCDS (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, strCurrency, 0.40, strCredit, strCalendar, true);

		if (null == cds) return null;

		cds.setPrimaryCode ("CDS." + strTenor + "." + strCredit);

		return cds;
	}

	/**
	 * Create an SNAC style CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon SNAC strike coupon
	 * @param strCredit Credit Curve name
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSNAC (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCredit)
	{
		return CreateSNAC (dtEffective, strTenor, dblCoupon, "USD", strCredit, "USD");
	}

	/**
	 * Create an Standard EU CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon Strike coupon
	 * @param strCredit Credit Curve name
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSTEU (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCredit)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtEffective.nextCreditIMM (3);

		if (null == dtFirstCoupon) return null;

		org.drip.product.definition.CreditDefaultSwap cds = CreateCDS (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, "EUR", 0.40, strCredit, "EUR", true);

		cds.setPrimaryCode ("CDS." + strTenor + "." + strCredit);

		return cds;
	}

	/**
	 * Create an Standard Asia Pacific CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon Strike coupon
	 * @param strCredit Credit Curve name
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSAPC (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCredit)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtEffective.nextCreditIMM (3);

		if (null == dtFirstCoupon) return null;

		org.drip.product.definition.CreditDefaultSwap cds = CreateCDS (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, "HKD", 0.40, strCredit, "HKD", true);

		cds.setPrimaryCode ("CDS." + strTenor + "." + strCredit);

		return cds;
	}

	/**
	 * Create an Standard Emerging Market CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon Strike coupon
	 * @param strCredit Credit Curve name
	 * @param strLocation Location
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSTEM (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCredit,
		final java.lang.String strLocation)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtEffective.nextCreditIMM (3);

		if (null == dtFirstCoupon) return null;

		org.drip.product.definition.CreditDefaultSwap cds = CreateCDS (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, strLocation, 0.25, strCredit, strLocation, true);

		cds.setPrimaryCode ("CDS." + strTenor + "." + strCredit);

		return cds;
	}
}
