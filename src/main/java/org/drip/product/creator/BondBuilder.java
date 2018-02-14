
package org.drip.product.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * BondBuilder contains the suite of helper functions for creating simple fixed/floater bonds, user defined
 * 	bonds, optionally with custom cash flows and embedded option schedules (European or American). It also
 * 	constructs bonds by de-serializing the byte stream.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BondBuilder {

	/**
	 * Custom Bond Type Simple Fixed
	 */

	public static final int BOND_TYPE_SIMPLE_FIXED = 0;

	/**
	 * Custom Bond Type Simple Floater
	 */

	public static final int BOND_TYPE_SIMPLE_FLOATER = 1;

	/**
	 * Custom Bond Type Simple From Cash flows
	 */

	public static final int BOND_TYPE_SIMPLE_FROM_CF = 2;

	/**
	 * Create the full generic bond object from the complete set of parameters
	 * 
	 * @param tsyParams Bond Treasury Parameters
	 * @param idParams Bond Identifier Parameters
	 * @param cpnParams Bond Coupon Parameters
	 * @param fltParams Bond Floater Parameters
	 * @param mktConv Bond Market Quote Convention
	 * @param crValParams Bond Credit Valuation Parameters
	 * @param cfteParams Bond Cash-flow Termination Event Parameters
	 * @param periodParams Bond Period Generation Parameters
	 * @param notlParams Bond Notional Parameters
	 * 
	 * @return The Bond object
	 */

	public static final org.drip.product.credit.BondComponent CreateBondFromParams (
		final org.drip.product.params.TreasuryBenchmarks tsyParams,
		final org.drip.product.params.IdentifierSet idParams,
		final org.drip.product.params.CouponSetting cpnParams,
		final org.drip.product.params.FloaterSetting fltParams,
		final org.drip.product.params.QuoteConvention mktConv,
		final org.drip.product.params.CreditSetting crValParams,
		final org.drip.product.params.TerminationSetting cfteParams,
		final org.drip.product.params.BondStream periodParams,
		final org.drip.product.params.NotionalSetting notlParams)
	{
		if (null == idParams || !idParams.validate() || null == cpnParams || !cpnParams.validate() || (null
			!= fltParams && !fltParams.validate()) || null == mktConv || !mktConv.validate() || null ==
				crValParams || !crValParams.validate() || null == cfteParams || !cfteParams.validate() ||
					null == periodParams || null == notlParams || !notlParams.validate())
			return null;

		org.drip.product.credit.BondComponent bond = new org.drip.product.credit.BondComponent();

		bond.setTreasuryBenchmark (tsyParams);

		bond.setIdentifierSet (idParams);

		bond.setCouponSetting (cpnParams);

		bond.setFloaterSetting (fltParams);

		bond.setMarketConvention (mktConv);

		bond.setCreditSetting (crValParams);

		bond.setTerminationSetting (cfteParams);

		bond.setStream (periodParams);

		bond.setNotionalSetting (notlParams);

		return bond;
	}

	/**
	 * Create a simple fixed bond from parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblCoupon Bond Fixed Coupon
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Bond Coupon Day count convention
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param fsPrincipalOutstanding Outstanding Principal schedule
	 * @param fsCoupon Bond Coupon Schedule
	 * 
	 * @return The Bond Object
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleFixed (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strCreditCurveName,
		final double dblCoupon,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		if (null == strName || strName.isEmpty() || null == strCurrency || strCurrency.isEmpty() || null ==
			dtEffective || null == dtMaturity || !org.drip.quant.common.NumberUtil.IsValid (dblCoupon))
			return null;

		return CreateBondFromParams (
			null,
			new org.drip.product.params.IdentifierSet (
				strName,
				strName,
				strName,
				strName
			),
			new org.drip.product.params.CouponSetting (
				fsCoupon,
				"",
				dblCoupon,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			),
			null,
			new org.drip.product.params.QuoteConvention (
				null,
				"",
				dtEffective.julian(),
				100.,
				0,
				strCurrency,
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL
			),
			new org.drip.product.params.CreditSetting (
				30,
				java.lang.Double.NaN,
				true,
				strCreditCurveName,
				true
			),
			new org.drip.product.params.TerminationSetting (
				false,
				false,
				false,
				null
			),
			org.drip.product.params.BondStream.Create (
				dtMaturity.julian(),
				dtEffective.julian(),
				java.lang.Integer.MIN_VALUE,
				java.lang.Integer.MIN_VALUE,
				dtEffective.julian(),
				iFreq,
				dblCoupon,
				strDayCount,
				strDayCount,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				"",
				false,
				strCurrency,
				strCurrency,
				null,
				!org.drip.quant.common.StringUtil.IsEmpty (strCreditCurveName) ?
					org.drip.state.identifier.EntityCDSLabel.Standard (
						strCreditCurveName,
						strCurrency
					) : null
			), new org.drip.product.params.NotionalSetting (
				100.,
				strCurrency,
				fsPrincipalOutstanding,
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END,
				false
			)
		);
	}

	/**
	 * Create a Fixed Coupon Bond from the First and Penultimate Coupon Dates, and the other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblCoupon Bond Fixed Coupon
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Bond Coupon Day count convention
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param iFirstCouponDate First Coupon Date
	 * @param iPenultimateCouponDate Penultimate Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal schedule
	 * @param fsCoupon Bond Coupon Schedule
	 * 
	 * @return The Bond Object
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleFixedFP (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strCreditCurveName,
		final double dblCoupon,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iFirstCouponDate,
		final int iPenultimateCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		if (null == strName || strName.isEmpty() || null == strCurrency || strCurrency.isEmpty() || null ==
			dtEffective || null == dtMaturity || !org.drip.quant.common.NumberUtil.IsValid (dblCoupon))
			return null;

		return CreateBondFromParams (
			null,
			new org.drip.product.params.IdentifierSet (
				strName,
				strName,
				strName,
				strName
			),
			new org.drip.product.params.CouponSetting (
				fsCoupon,
				"",
				dblCoupon,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			),
			null,
			new org.drip.product.params.QuoteConvention (
				null,
				"",
				dtEffective.julian(),
				100.,
				0,
				strCurrency,
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL
			),
			new org.drip.product.params.CreditSetting (
				30,
				java.lang.Double.NaN,
				true,
				strCreditCurveName,
				true
			),
			new org.drip.product.params.TerminationSetting (
				false,
				false,
				false,
				dapMaturity
			),
			org.drip.product.params.BondStream.FromFirstPenultimateCouponDate (
				dtMaturity.julian(),
				dtEffective.julian(),
				dtMaturity.julian(),
				iFirstCouponDate,
				iPenultimateCouponDate,
				iFreq,
				dblCoupon,
				strDayCount,
				strDayCount,
				dapPay,
				dapReset,
				dapMaturity,
				dapEffective,
				dapPeriodEnd,
				dapAccrualEnd,
				dapPeriodStart,
				dapAccrualStart,
				"",
				false,
				strCurrency,
				strCurrency,
				null,
				!org.drip.quant.common.StringUtil.IsEmpty (strCreditCurveName) ?
					org.drip.state.identifier.EntityCDSLabel.Standard (
						strCreditCurveName,
						strCurrency
					) : null
			), new org.drip.product.params.NotionalSetting (
				100.,
				strCurrency,
				fsPrincipalOutstanding,
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END,
				false
			)
		);
	}

	/**
	 * Create a Fixed Coupon Bond from the First Coupon Date and the other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblCoupon Bond Fixed Coupon
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Bond Coupon Day count convention
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param iFirstCouponDate First Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal schedule
	 * @param fsCoupon Bond Coupon Schedule
	 * 
	 * @return The Bond Object
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleFixedF (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strCreditCurveName,
		final double dblCoupon,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iFirstCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		return CreateSimpleFixedFP (
			strName,
			strCurrency,
			strCreditCurveName,
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			iFirstCouponDate,
			dtMaturity.subtractTenor ((12 / iFreq) + "M").julian(),
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart,
			fsPrincipalOutstanding,
			fsCoupon
		);
	}

	/**
	 * Create a Fixed Coupon Bond from the Penultimate Coupon Date and the other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblCoupon Bond Fixed Coupon
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Bond Coupon Day count convention
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param iPenultimateCouponDate Penultimate Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal schedule
	 * @param fsCoupon Bond Coupon Schedule
	 * 
	 * @return The Bond Object
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleFixedP (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strCreditCurveName,
		final double dblCoupon,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iPenultimateCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		return CreateSimpleFixedFP (
			strName,
			strCurrency,
			strCreditCurveName,
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			dtEffective.addTenor ((12 / iFreq) + "M").julian(),
			iPenultimateCouponDate,
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart,
			fsPrincipalOutstanding,
			fsCoupon
		);
	}

	/**
	 * Create a simple floating rate bond
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strRateIndex Floating Rate Index
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblSpread Bond Floater Spread
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Coupon Day Count Convention
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity Date
	 * @param fsPrincipalOutstanding Outstanding Principal Schedule
	 * @param fsCoupon Coupon Schedule
	 * 
	 * @return The Bond object
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleFloater (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strRateIndex,
		final java.lang.String strCreditCurveName,
		final double dblSpread,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		if (null == strName || strName.isEmpty() || null == strCurrency || strCurrency.isEmpty() || null ==
			dtEffective || null == dtMaturity || !org.drip.quant.common.NumberUtil.IsValid (dblSpread))
			return null;

		return CreateBondFromParams (
			null,
			new org.drip.product.params.IdentifierSet (
				strName,
				strName,
				strName,
				strCurrency
			),
			new org.drip.product.params.CouponSetting (
				fsCoupon,
				"",
				dblSpread,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			),
			new org.drip.product.params.FloaterSetting (
				org.drip.state.identifier.ForwardLabel.Standard (strRateIndex),
				"",
				dblSpread,
				java.lang.Double.NaN
			),
			new org.drip.product.params.QuoteConvention (
				null,
				"",
				dtEffective.julian(),
				100.,
				0,
				strCurrency,
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL
			),
			new org.drip.product.params.CreditSetting (
				30,
				java.lang.Double.NaN,
				true,
				strCreditCurveName,
				true
			),
			new org.drip.product.params.TerminationSetting (
				false,
				false,
				false,
				null
			),
			org.drip.product.params.BondStream.Create (
				dtMaturity.julian(),
				dtEffective.julian(),
				java.lang.Integer.MIN_VALUE,
				java.lang.Integer.MIN_VALUE,
				dtEffective.julian(),
				iFreq,
				dblSpread,
				strDayCount,
				strDayCount,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				"",
				false,
				strCurrency,
				strCurrency,
				org.drip.state.identifier.ForwardLabel.Standard (strRateIndex),
				null == strCreditCurveName || strCreditCurveName.isEmpty() ? null :
					org.drip.state.identifier.EntityCDSLabel.Standard (
						strCreditCurveName,
						strCurrency
					)
				),
			new org.drip.product.params.NotionalSetting (
				100.,
				strCurrency,
				fsPrincipalOutstanding,
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END,
				false
			)
		);
	}

	/**
	 * Create a Floating Rate Bond from the First and Penultimate Coupon Dates, and the other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strRateIndex Floating Rate Index
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblSpread Bond Floater Spread
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Coupon Day Count Convention
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity Date
	 * @param iFirstCouponDate First Coupon Date
	 * @param iPenultimateCouponDate Penultimate Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal Schedule
	 * @param fsCoupon Coupon Schedule
	 * 
	 * @return The Floating Rate Bond Instance
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleFloaterFP (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strRateIndex,
		final java.lang.String strCreditCurveName,
		final double dblSpread,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iFirstCouponDate,
		final int iPenultimateCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		if (null == strName || strName.isEmpty() || null == strCurrency || strCurrency.isEmpty() || null ==
			dtEffective || null == dtMaturity || !org.drip.quant.common.NumberUtil.IsValid (dblSpread))
			return null;

		return CreateBondFromParams (
			null,
			new org.drip.product.params.IdentifierSet (
				strName,
				strName,
				strName,
				strCurrency
			),
			new org.drip.product.params.CouponSetting (
				fsCoupon,
				"",
				dblSpread,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			),
			new org.drip.product.params.FloaterSetting (
				org.drip.state.identifier.ForwardLabel.Standard (strRateIndex),
				"",
				dblSpread,
				java.lang.Double.NaN
			),
			new org.drip.product.params.QuoteConvention (
				null,
				"",
				dtEffective.julian(),
				100.,
				0,
				strCurrency,
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL
			),
			new org.drip.product.params.CreditSetting (
				30,
				java.lang.Double.NaN,
				true,
				strCreditCurveName,
				true
			),
			new org.drip.product.params.TerminationSetting (
				false,
				false,
				false,
				dapMaturity
			),
			org.drip.product.params.BondStream.FromFirstPenultimateCouponDate (
				dtMaturity.julian(),
				dtEffective.julian(),
				dtMaturity.julian(),
				iFirstCouponDate,
				iPenultimateCouponDate,
				iFreq,
				dblSpread,
				strDayCount,
				strDayCount,
				dapPay,
				dapReset,
				dapMaturity,
				dapEffective,
				dapPeriodEnd,
				dapAccrualEnd,
				dapPeriodStart,
				dapAccrualStart,
				"",
				false,
				strCurrency,
				strCurrency,
				org.drip.state.identifier.ForwardLabel.Standard (strRateIndex),
				null == strCreditCurveName || strCreditCurveName.isEmpty() ? null :
					org.drip.state.identifier.EntityCDSLabel.Standard (
						strCreditCurveName,
						strCurrency
					)
				),
			new org.drip.product.params.NotionalSetting (
				100.,
				strCurrency,
				fsPrincipalOutstanding,
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END,
				false
			)
		);
	}

	/**
	 * Create a Floating Rate Bond from the First and Penultimate Coupon Dates, and the other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strRateIndex Floating Rate Index
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblSpread Bond Floater Spread
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Coupon Day Count Convention
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity Date
	 * @param iFirstCouponDate First Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal Schedule
	 * @param fsCoupon Coupon Schedule
	 * 
	 * @return The Floating Rate Bond Instance
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleFloaterF (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strRateIndex,
		final java.lang.String strCreditCurveName,
		final double dblSpread,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iFirstCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		return CreateSimpleFloaterFP (
			strName,
			strCurrency,
			strRateIndex,
			strCreditCurveName,
			dblSpread,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			iFirstCouponDate,
			dtMaturity.subtractTenor ((12 / iFreq) + "M").julian(),
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart,
			fsPrincipalOutstanding,
			fsCoupon
		);
	}

	/**
	 * Create a Floating Rate Bond from the First and Penultimate Coupon Dates, and the other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strRateIndex Floating Rate Index
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblSpread Bond Floater Spread
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Coupon Day Count Convention
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity Date
	 * @param iPenultimateCouponDate Penultimate Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal Schedule
	 * @param fsCoupon Coupon Schedule
	 * 
	 * @return The Floating Rate Bond Instance
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleFloaterP (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strRateIndex,
		final java.lang.String strCreditCurveName,
		final double dblSpread,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iPenultimateCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		return CreateSimpleFloaterFP (
			strName,
			strCurrency,
			strRateIndex,
			strCreditCurveName,
			dblSpread,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			dtEffective.addTenor ((12 / iFreq) + "M").julian(),
			iPenultimateCouponDate,
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart,
			fsPrincipalOutstanding,
			fsCoupon
		);
	}

	/**
	 * Create a bond from custom/user-defined cash flows and coupon conventions
	 * 
	 * @param strName Bond Name
	 * @param dtEffective Effective Date
	 * @param strCurrency Bond Currency
	 * @param strCreditCurveName Credit Curve Name
	 * @param strDayCount Coupon Day Count Convention
	 * @param dblInitialNotional The Initial Notional
	 * @param dblCouponRate The Coupon Rate
	 * @param iCouponFrequency Coupon Frequency
	 * @param adtPeriodEnd Array of Period End Dates
	 * @param adblCouponAmount Matching Array of Coupon Amounts
	 * @param adblPrincipalAmount Matching Array of Principal Amounts
	 * @param bIsPrincipalPayDown Flag indicating whether principal is pay down or outstanding
	 * 
	 * @return The Bond object
	 */

	public static final org.drip.product.credit.BondComponent CreateBondFromCF (
		final java.lang.String strName,
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strCurrency,
		final java.lang.String strCreditCurveName,
		final java.lang.String strDayCount,
		final double dblInitialNotional,
		final double dblCouponRate,
		final int iCouponFrequency,
		final org.drip.analytics.date.JulianDate[] adtPeriodEnd,
		final double[] adblCouponAmount,
		final double[] adblPrincipalAmount,
		final boolean bIsPrincipalPayDown)
	{
		if (null == adtPeriodEnd || null == adblCouponAmount || null == adblPrincipalAmount || null ==
			dtEffective || !org.drip.quant.common.NumberUtil.IsValid (dblInitialNotional) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblCouponRate) || 0 == iCouponFrequency)
			return null;

		int iEffectiveDate = dtEffective.julian();

		int iNumPeriod = adtPeriodEnd.length;
		int iPeriodStartDate = iEffectiveDate;
		int[] aiPeriodEndDate = new int[iNumPeriod];
		int[] aiPrincipalDate = new int[iNumPeriod + 1];
		org.drip.product.params.BondStream stream = null;
		double[] adblCouponFactor = new double[iNumPeriod];
		double[] adblPeriodEndPrincipal = new double[iNumPeriod];
		double[] adblPrincipalFactor = new double[iNumPeriod + 1];
		double[] adblPeriodEndPrincipalFactor = new double[iNumPeriod];

		if (0 == iNumPeriod || iNumPeriod != adblCouponAmount.length || iNumPeriod !=
			adblPrincipalAmount.length)
			return null;

		if (bIsPrincipalPayDown) {
			for (int i = 0; i < iNumPeriod; ++i)
				adblPeriodEndPrincipal[i] = (0 == i ? dblInitialNotional : adblPeriodEndPrincipal[i - 1]) -
					adblPrincipalAmount[i];
		} else
			adblPeriodEndPrincipal = adblPrincipalAmount;

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod = new
			java.util.ArrayList<org.drip.analytics.cashflow.CompositePeriod>();

		for (int i = 0; i < iNumPeriod; ++i) {
			if (null == adtPeriodEnd[i]) return null;

			aiPeriodEndDate[i] = adtPeriodEnd[i].julian();

			adblPeriodEndPrincipalFactor[i] = adblPeriodEndPrincipal[i] / dblInitialNotional;

			try {
				adblCouponFactor[i] = adblCouponAmount[i] / (0 == i ? dblInitialNotional :
					adblPeriodEndPrincipal[i - 1]) / org.drip.analytics.daycount.Convention.YearFraction
						(iPeriodStartDate, aiPeriodEndDate[i], strDayCount, false, null, "") / dblCouponRate;

				java.util.List<org.drip.analytics.cashflow.ComposableUnitPeriod> lsCUP = new
					java.util.ArrayList<org.drip.analytics.cashflow.ComposableUnitPeriod>();

				lsCUP.add (
					new org.drip.analytics.cashflow.ComposableUnitFixedPeriod (
						iPeriodStartDate,
						aiPeriodEndDate[i],
						new org.drip.param.period.UnitCouponAccrualSetting (
							iCouponFrequency,
							strDayCount,
							false,
							strDayCount,
							false,
							strCurrency,
							false,
							org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
						),
						new org.drip.param.period.ComposableFixedUnitSetting (
							(12 / iCouponFrequency) + "M",
							org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
							null,
							dblCouponRate,
							0.,
							strCurrency
						)
					)
				);

				lsCouponPeriod.add (
					new org.drip.analytics.cashflow.CompositeFixedPeriod (
						new org.drip.param.period.CompositePeriodSetting (
							iCouponFrequency,
							(12 / iCouponFrequency) + "M",
							strCurrency,
							null,
							adblPrincipalAmount[i],
							null,
							null,
							null,
							null
						),
						lsCUP
					)
				);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			iPeriodStartDate = aiPeriodEndDate[i];
		}

		for (int i = 0; i <= iNumPeriod; ++i) {
			aiPrincipalDate[i] = 0 == i ? iEffectiveDate : aiPeriodEndDate[i - 1];
			adblPrincipalFactor[i] = 0 == i ? 1. : adblPeriodEndPrincipalFactor[i - 1];
		}

		try {
			stream = new org.drip.product.params.BondStream (
				lsCouponPeriod,
				java.lang.Integer.MIN_VALUE,
				"MATURITY_TYPE_REGULAR"
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return CreateBondFromParams (
			null,
			new org.drip.product.params.IdentifierSet (
				strName,
				strName,
				strName,
				strCurrency
			),
			new org.drip.product.params.CouponSetting (
				org.drip.quant.common.Array2D.FromArray (
					aiPeriodEndDate,
					adblCouponFactor
				),
				"",
				dblCouponRate,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			),
			null,
			new org.drip.product.params.QuoteConvention (
				null,
				"",
				dtEffective.julian(),
				1.,
				0,
				strCurrency,
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL
			),
			new org.drip.product.params.CreditSetting (
				30,
				java.lang.Double.NaN,
				true,
				strCreditCurveName,
				false
			),
			new org.drip.product.params.TerminationSetting (
				false,
				false,
				false,
				null
			),
			stream,
			new org.drip.product.params.NotionalSetting (
				1.,
				strCurrency,
				org.drip.quant.common.Array2D.FromArray (
					aiPrincipalDate,
					adblPrincipalFactor
				),
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_START,
				false
			)
		);
	}

	/**
	 * Creates a Treasury Bond from the Parameters
	 * 
	 * @param strTreasuryCode Treasury Code
	 * @param strCurrency Bond Currency
	 * @param dblCoupon Bond Fixed Coupon
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Bond Coupon Day count convention
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * 
	 * @return The Treasury Bond Instance
	 */

	public static final org.drip.product.govvie.TreasuryComponent Treasury (
		final java.lang.String strTreasuryCode,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final java.lang.String strCurrency,
		final double dblCoupon,
		final int iFreq,
		final java.lang.String strDayCount)
	{
		if (null == strTreasuryCode || strTreasuryCode.isEmpty()) return null;

		org.drip.product.govvie.TreasuryComponent tsyBond = null;

		try {
			tsyBond = new org.drip.product.govvie.TreasuryComponent (strTreasuryCode);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		java.lang.String strName = strTreasuryCode + " " + org.drip.quant.common.FormatUtil.FormatDouble
			(dblCoupon, 1, 2, 100.) + " " + dtMaturity;

		tsyBond.setIdentifierSet (
			new org.drip.product.params.IdentifierSet (
				strName,
				strName,
				strName,
				strName
			)
		);

		tsyBond.setNotionalSetting (
			new org.drip.product.params.NotionalSetting (
				100.,
				strCurrency,
				org.drip.quant.common.Array2D.BulletSchedule(),
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END,
				false
			)
		);

		tsyBond.setCouponSetting (
			new org.drip.product.params.CouponSetting (
				null,
				"",
				dblCoupon,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			)
		);

		tsyBond.setStream (
			org.drip.product.params.BondStream.Create (
				dtMaturity.julian(),
				dtEffective.julian(),
				java.lang.Integer.MIN_VALUE,
				java.lang.Integer.MIN_VALUE,
				dtEffective.julian(),
				iFreq,
				dblCoupon,
				strDayCount,
				strDayCount,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				"",
				false,
				strCurrency,
				strCurrency,
				null,
				null
			)
		);

		return tsyBond;
	}

	/**
	 * Construct a Fixed To Float Bond Component
	 * 
	 * @param strName Bond Name
	 * @param strCreditCurveName Credit Curve Name
	 * @param iEffectiveDate Effective Date
	 * @param iFixedStreamEndDate Fixed Stream End Date
	 * @param iFixedFirstCouponDate Fixed Stream First Coupon Date
	 * @param iFixedPenultimateCouponDate Fixed Stream Penultimate Coupon Date
	 * @param iFixedFreq Fixed Stream Coupon Frequency
	 * @param dblFixedCoupon Fixed Stream Coupon Rate
	 * @param strFixedCouponDC Fixed Stream Coupon Day Count
	 * @param strFixedAccrualDC Fixed Stream Accrual Day Count
	 * @param iMaturityDate Maturity Date
	 * @param iFloatFirstCouponDate Float Stream First Coupon Date
	 * @param iFloatPenultimateCouponDate Float Stream Penultimate Coupon Date
	 * @param iFloatFreq Float Stream Coupon Frequency
	 * @param dblFloatSpread Float Stream Spread
	 * @param strFloatIndex Float Stream Rate Index
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * 
	 * @return The Bond Component
	 */

	public static final org.drip.product.credit.BondComponent FixedFPToFloatFP (
		final java.lang.String strName,
		final java.lang.String strCreditCurveName,
		final int iEffectiveDate,
		final int iFixedStreamEndDate,
		final int iFixedFirstCouponDate,
		final int iFixedPenultimateCouponDate,
		final int iFixedFreq,
		final double dblFixedCoupon,
		final java.lang.String strFixedCouponDC,
		final java.lang.String strFixedAccrualDC,
		final int iMaturityDate,
		final int iFloatFirstCouponDate,
		final int iFloatPenultimateCouponDate,
		final int iFloatFreq,
		final double dblFloatSpread,
		final java.lang.String strFloatIndex,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart)
	{
		try {
			org.drip.state.identifier.ForwardLabel forwardLabel =
				org.drip.state.identifier.ForwardLabel.Standard (strFloatIndex);

			if (null == forwardLabel) return null;

			java.lang.String strCurrency = forwardLabel.currency();

			return CreateBondFromParams (
				null,
				new org.drip.product.params.IdentifierSet (
					strName,
					strName,
					strName,
					strCurrency
				),
				new org.drip.product.params.CouponSetting (
					null,
					"",
					dblFloatSpread,
					java.lang.Double.NaN,
					java.lang.Double.NaN
				),
				new org.drip.product.params.FloaterSetting (
					org.drip.state.identifier.ForwardLabel.Standard (strFloatIndex),
					"",
					dblFloatSpread,
					java.lang.Double.NaN
				),
				new org.drip.product.params.QuoteConvention (
					null,
					"",
					iEffectiveDate,
					100.,
					0,
					strCurrency,
					org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL
				),
				new org.drip.product.params.CreditSetting (
					30,
					java.lang.Double.NaN,
					true,
					strCreditCurveName,
					true
				),
				new org.drip.product.params.TerminationSetting (
					false,
					false,
					false,
					null
				),
				new org.drip.product.params.BondStream (
					org.drip.product.creator.StreamBuilder.FirstPenultimateDateFixedFloat (
						iEffectiveDate,
						iFixedStreamEndDate,
						iFixedFirstCouponDate,
						iFixedPenultimateCouponDate,
						iFixedFreq,
						dblFixedCoupon,
						strFixedCouponDC,
						strFixedAccrualDC,
						iMaturityDate,
						iFloatFirstCouponDate,
						iFloatPenultimateCouponDate,
						iFloatFreq,
						dblFloatSpread,
						dapPay,
						dapPeriodEnd,
						dapAccrualEnd,
						org.drip.state.identifier.ForwardLabel.Standard (strFloatIndex),
						!org.drip.quant.common.StringUtil.IsEmpty (strCreditCurveName) ?
							org.drip.state.identifier.EntityCDSLabel.Standard (
								strCreditCurveName,
								strCurrency
							) : null
					),
					iMaturityDate,
					""
				),
				new org.drip.product.params.NotionalSetting (
					100.,
					strCurrency,
					null,
					org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END,
					false
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Fixed To Float Bond Component
	 * 
	 * @param strName Bond Name
	 * @param strCreditCurveName Credit Curve Name
	 * @param iEffectiveDate Effective Date
	 * @param iFixedStreamEndDate Fixed Stream End Date
	 * @param iFixedFirstCouponDate Fixed Stream First Coupon Date
	 * @param iFixedFreq Fixed Stream Coupon Frequency
	 * @param dblFixedCoupon Fixed Stream Coupon Rate
	 * @param strFixedCouponDC Fixed Stream Coupon Day Count
	 * @param strFixedAccrualDC Fixed Stream Accrual Day Count
	 * @param iMaturityDate Maturity Date
	 * @param iFloatFirstCouponDate Float Stream First Coupon Date
	 * @param iFloatFreq Float Stream Coupon Frequency
	 * @param dblFloatSpread Float Stream Spread
	 * @param strFloatIndex Float Stream Rate Index
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * 
	 * @return The Bond Component
	 */

	public static final org.drip.product.credit.BondComponent FixedFToFloatF (
		final java.lang.String strName,
		final java.lang.String strCreditCurveName,
		final int iEffectiveDate,
		final int iFixedStreamEndDate,
		final int iFixedFirstCouponDate,
		final int iFixedFreq,
		final double dblFixedCoupon,
		final java.lang.String strFixedCouponDC,
		final java.lang.String strFixedAccrualDC,
		final int iMaturityDate,
		final int iFloatFirstCouponDate,
		final int iFloatFreq,
		final double dblFloatSpread,
		final java.lang.String strFloatIndex,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart)
	{
		java.lang.String strPeriodTenor = (12 / iFixedFreq) + "M";

		return FixedFPToFloatFP (
			strName,
			strCreditCurveName,
			iEffectiveDate,
			iFixedStreamEndDate,
			iFixedFirstCouponDate,
			new org.drip.analytics.date.JulianDate (iFixedStreamEndDate).subtractTenor
				(strPeriodTenor).julian(),
			iFixedFreq,
			dblFixedCoupon,
			strFixedCouponDC,
			strFixedAccrualDC,
			iMaturityDate,
			iFloatFirstCouponDate,
			new org.drip.analytics.date.JulianDate (iMaturityDate).subtractTenor (strPeriodTenor).julian(),
			iFloatFreq,
			dblFloatSpread,
			strFloatIndex,
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart
		);
	}

	/**
	 * Construct a Fixed To Float Bond Component
	 * 
	 * @param strName Bond Name
	 * @param strCreditCurveName Credit Curve Name
	 * @param iEffectiveDate Effective Date
	 * @param iFixedStreamEndDate Fixed Stream End Date
	 * @param iFixedFirstCouponDate Fixed Stream First Coupon Date
	 * @param iFixedFreq Fixed Stream Coupon Frequency
	 * @param dblFixedCoupon Fixed Stream Coupon Rate
	 * @param strFixedCouponDC Fixed Stream Coupon Day Count
	 * @param strFixedAccrualDC Fixed Stream Accrual Day Count
	 * @param iMaturityDate Maturity Date
	 * @param iFloatPenultimateCouponDate Float Stream Penultimate Coupon Date
	 * @param iFloatFreq Float Stream Coupon Frequency
	 * @param dblFloatSpread Float Stream Spread
	 * @param strFloatIndex Float Stream Rate Index
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * 
	 * @return The Bond Component
	 */

	public static final org.drip.product.credit.BondComponent FixedFToFloatP (
		final java.lang.String strName,
		final java.lang.String strCreditCurveName,
		final int iEffectiveDate,
		final int iFixedStreamEndDate,
		final int iFixedFirstCouponDate,
		final int iFixedFreq,
		final double dblFixedCoupon,
		final java.lang.String strFixedCouponDC,
		final java.lang.String strFixedAccrualDC,
		final int iMaturityDate,
		final int iFloatPenultimateCouponDate,
		final int iFloatFreq,
		final double dblFloatSpread,
		final java.lang.String strFloatIndex,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart)
	{
		java.lang.String strPeriodTenor = (12 / iFixedFreq) + "M";

		return FixedFPToFloatFP (
			strName,
			strCreditCurveName,
			iEffectiveDate,
			iFixedStreamEndDate,
			iFixedFirstCouponDate,
			new org.drip.analytics.date.JulianDate (iFixedStreamEndDate).subtractTenor
				(strPeriodTenor).julian(),
			iFixedFreq,
			dblFixedCoupon,
			strFixedCouponDC,
			strFixedAccrualDC,
			iMaturityDate,
			new org.drip.analytics.date.JulianDate (iFixedStreamEndDate).addTenor (strPeriodTenor).julian(),
			iFloatPenultimateCouponDate,
			iFloatFreq,
			dblFloatSpread,
			strFloatIndex,
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart
		);
	}

	/**
	 * Construct a Fixed To Float Bond Component
	 * 
	 * @param strName Bond Name
	 * @param strCreditCurveName Credit Curve Name
	 * @param iEffectiveDate Effective Date
	 * @param iFixedStreamEndDate Fixed Stream End Date
	 * @param iFixedPenultimateCouponDate Fixed Stream Penultimate Coupon Date
	 * @param iFixedFreq Fixed Stream Coupon Frequency
	 * @param dblFixedCoupon Fixed Stream Coupon Rate
	 * @param strFixedCouponDC Fixed Stream Coupon Day Count
	 * @param strFixedAccrualDC Fixed Stream Accrual Day Count
	 * @param iMaturityDate Maturity Date
	 * @param iFloatFirstCouponDate Float Stream First Coupon Date
	 * @param iFloatFreq Float Stream Coupon Frequency
	 * @param dblFloatSpread Float Stream Spread
	 * @param strFloatIndex Float Stream Rate Index
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * 
	 * @return The Bond Component
	 */

	public static final org.drip.product.credit.BondComponent FixedPToFloatF (
		final java.lang.String strName,
		final java.lang.String strCreditCurveName,
		final int iEffectiveDate,
		final int iFixedStreamEndDate,
		final int iFixedPenultimateCouponDate,
		final int iFixedFreq,
		final double dblFixedCoupon,
		final java.lang.String strFixedCouponDC,
		final java.lang.String strFixedAccrualDC,
		final int iMaturityDate,
		final int iFloatFirstCouponDate,
		final int iFloatFreq,
		final double dblFloatSpread,
		final java.lang.String strFloatIndex,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart)
	{
		java.lang.String strPeriodTenor = (12 / iFixedFreq) + "M";

		return FixedFPToFloatFP (
			strName,
			strCreditCurveName,
			iEffectiveDate,
			iFixedStreamEndDate,
			new org.drip.analytics.date.JulianDate (iEffectiveDate).addTenor (strPeriodTenor).julian(),
			iFixedPenultimateCouponDate,
			iFixedFreq,
			dblFixedCoupon,
			strFixedCouponDC,
			strFixedAccrualDC,
			iMaturityDate,
			iFloatFirstCouponDate,
			new org.drip.analytics.date.JulianDate (iMaturityDate).subtractTenor (strPeriodTenor).julian(),
			iFloatFreq,
			dblFloatSpread,
			strFloatIndex,
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart
		);
	}

	/**
	 * Construct a Fixed To Float Bond Component
	 * 
	 * @param strName Bond Name
	 * @param strCreditCurveName Credit Curve Name
	 * @param iEffectiveDate Effective Date
	 * @param iFixedStreamEndDate Fixed Stream End Date
	 * @param iFixedPenultimateCouponDate Fixed Stream Penultimate Coupon Date
	 * @param iFixedFreq Fixed Stream Coupon Frequency
	 * @param dblFixedCoupon Fixed Stream Coupon Rate
	 * @param strFixedCouponDC Fixed Stream Coupon Day Count
	 * @param strFixedAccrualDC Fixed Stream Accrual Day Count
	 * @param iMaturityDate Maturity Date
	 * @param iFloatPenultimateCouponDate Float Stream Penultimate Coupon Date
	 * @param iFloatFreq Float Stream Coupon Frequency
	 * @param dblFloatSpread Float Stream Spread
	 * @param strFloatIndex Float Stream Rate Index
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * 
	 * @return The Bond Component
	 */

	public static final org.drip.product.credit.BondComponent FixedPToFloatP (
		final java.lang.String strName,
		final java.lang.String strCreditCurveName,
		final int iEffectiveDate,
		final int iFixedStreamEndDate,
		final int iFixedPenultimateCouponDate,
		final int iFixedFreq,
		final double dblFixedCoupon,
		final java.lang.String strFixedCouponDC,
		final java.lang.String strFixedAccrualDC,
		final int iMaturityDate,
		final int iFloatPenultimateCouponDate,
		final int iFloatFreq,
		final double dblFloatSpread,
		final java.lang.String strFloatIndex,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart)
	{
		java.lang.String strPeriodTenor = (12 / iFixedFreq) + "M";

		return FixedFPToFloatFP (
			strName,
			strCreditCurveName,
			iEffectiveDate,
			iFixedStreamEndDate,
			new org.drip.analytics.date.JulianDate (iEffectiveDate).addTenor (strPeriodTenor).julian(),
			iFixedPenultimateCouponDate,
			iFixedFreq,
			dblFixedCoupon,
			strFixedCouponDC,
			strFixedAccrualDC,
			iMaturityDate,
			new org.drip.analytics.date.JulianDate (iFixedStreamEndDate).addTenor (strPeriodTenor).julian(),
			iFloatPenultimateCouponDate,
			iFloatFreq,
			dblFloatSpread,
			strFloatIndex,
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart
		);
	}

	/**
	 * Create a Simple OTF Fix Float Floating Rate Bond
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strRateIndex Floating Rate Index
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblSpread Bond Floater Spread
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Coupon Day Count Convention
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity Date
	 * @param fsPrincipalOutstanding Outstanding Principal Schedule
	 * @param fsCoupon Coupon Schedule
	 * 
	 * @return The Bond object
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleOTCIRSFloater (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strRateIndex,
		final java.lang.String strCreditCurveName,
		final double dblSpread,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		if (null == strName || strName.isEmpty() || null == strCurrency || strCurrency.isEmpty() || null ==
			dtEffective || null == dtMaturity || !org.drip.quant.common.NumberUtil.IsValid (dblSpread))
			return null;

		return CreateBondFromParams (
			null,
			new org.drip.product.params.IdentifierSet (
				strName,
				strName,
				strName,
				strCurrency
			),
			new org.drip.product.params.CouponSetting (
				fsCoupon,
				"",
				dblSpread,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			),
			new org.drip.product.params.FloaterSetting (
				org.drip.state.identifier.OTCFixFloatLabel.Standard (strRateIndex),
				"",
				dblSpread,
				java.lang.Double.NaN
			),
			new org.drip.product.params.QuoteConvention (
				null,
				"",
				dtEffective.julian(),
				100.,
				0,
				strCurrency,
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL
			),
			new org.drip.product.params.CreditSetting (
				30,
				java.lang.Double.NaN,
				true,
				strCreditCurveName,
				true
			),
			new org.drip.product.params.TerminationSetting (
				false,
				false,
				false,
				null
			),
			org.drip.product.params.BondStream.Create (
				dtMaturity.julian(),
				dtEffective.julian(),
				java.lang.Integer.MIN_VALUE,
				java.lang.Integer.MIN_VALUE,
				dtEffective.julian(),
				iFreq,
				dblSpread,
				strDayCount,
				strDayCount,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				"",
				false,
				strCurrency,
				strCurrency,
				org.drip.state.identifier.OTCFixFloatLabel.Standard (strRateIndex),
				null == strCreditCurveName || strCreditCurveName.isEmpty() ? null :
					org.drip.state.identifier.EntityCDSLabel.Standard (
						strCreditCurveName,
						strCurrency
					)
				),
			new org.drip.product.params.NotionalSetting (
				100.,
				strCurrency,
				fsPrincipalOutstanding,
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END,
				false
			)
		);
	}

	/**
	 * Create a OTC Fix Float Index Floating Rate Bond from the First and Penultimate Coupon Dates, and the
	 *  other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strRateIndex Floating Rate Index
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblSpread Bond Floater Spread
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Coupon Day Count Convention
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity Date
	 * @param iFirstCouponDate First Coupon Date
	 * @param iPenultimateCouponDate Penultimate Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal Schedule
	 * @param fsCoupon Coupon Schedule
	 * 
	 * @return The Floating Rate Bond Instance
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleOTCIRSFloaterFP (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strRateIndex,
		final java.lang.String strCreditCurveName,
		final double dblSpread,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iFirstCouponDate,
		final int iPenultimateCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		if (null == strName || strName.isEmpty() || null == strCurrency || strCurrency.isEmpty() || null ==
			dtEffective || null == dtMaturity || !org.drip.quant.common.NumberUtil.IsValid (dblSpread))
			return null;

		return CreateBondFromParams (
			null,
			new org.drip.product.params.IdentifierSet (
				strName,
				strName,
				strName,
				strCurrency
			),
			new org.drip.product.params.CouponSetting (
				fsCoupon,
				"",
				dblSpread,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			),
			new org.drip.product.params.FloaterSetting (
				org.drip.state.identifier.OTCFixFloatLabel.Standard (strRateIndex),
				"",
				dblSpread,
				java.lang.Double.NaN
			),
			new org.drip.product.params.QuoteConvention (
				null,
				"",
				dtEffective.julian(),
				100.,
				0,
				strCurrency,
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL
			),
			new org.drip.product.params.CreditSetting (
				30,
				java.lang.Double.NaN,
				true,
				strCreditCurveName,
				true
			),
			new org.drip.product.params.TerminationSetting (
				false,
				false,
				false,
				dapMaturity
			),
			org.drip.product.params.BondStream.FromFirstPenultimateCouponDate (
				dtMaturity.julian(),
				dtEffective.julian(),
				dtMaturity.julian(),
				iFirstCouponDate,
				iPenultimateCouponDate,
				iFreq,
				dblSpread,
				strDayCount,
				strDayCount,
				dapPay,
				dapReset,
				dapMaturity,
				dapEffective,
				dapPeriodEnd,
				dapAccrualEnd,
				dapPeriodStart,
				dapAccrualStart,
				"",
				false,
				strCurrency,
				strCurrency,
				org.drip.state.identifier.OTCFixFloatLabel.Standard (strRateIndex),
				null == strCreditCurveName || strCreditCurveName.isEmpty() ? null :
					org.drip.state.identifier.EntityCDSLabel.Standard (
						strCreditCurveName,
						strCurrency
					)
				),
			new org.drip.product.params.NotionalSetting (
				100.,
				strCurrency,
				fsPrincipalOutstanding,
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END,
				false
			)
		);
	}

	/**
	 * Create a OTC Fix Float Index Floating Rate Bond from the First and Penultimate Coupon Dates, and the
	 *  other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strRateIndex Floating Rate Index
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblSpread Bond Floater Spread
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Coupon Day Count Convention
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity Date
	 * @param iFirstCouponDate First Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal Schedule
	 * @param fsCoupon Coupon Schedule
	 * 
	 * @return The Floating Rate Bond Instance
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleOTCIRSFloaterF (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strRateIndex,
		final java.lang.String strCreditCurveName,
		final double dblSpread,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iFirstCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		return CreateSimpleFloaterFP (
			strName,
			strCurrency,
			strRateIndex,
			strCreditCurveName,
			dblSpread,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			iFirstCouponDate,
			dtMaturity.subtractTenor ((12 / iFreq) + "M").julian(),
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart,
			fsPrincipalOutstanding,
			fsCoupon
		);
	}

	/**
	 * Create a OTC Fix-Float Index Floating Rate Bond from the First and Penultimate Coupon Dates, and the
	 *  other Parameters
	 * 
	 * @param strName Bond Name
	 * @param strCurrency Bond Currency
	 * @param strRateIndex Floating Rate Index
	 * @param strCreditCurveName Credit Curve Name
	 * @param dblSpread Bond Floater Spread
	 * @param iFreq Coupon Frequency
	 * @param strDayCount Coupon Day Count Convention
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity Date
	 * @param iPenultimateCouponDate Penultimate Coupon Date
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param fsPrincipalOutstanding Outstanding Principal Schedule
	 * @param fsCoupon Coupon Schedule
	 * 
	 * @return The Floating Rate Bond Instance
	 */

	public static final org.drip.product.credit.BondComponent CreateSimpleOTCIRSFloaterP (
		final java.lang.String strName,
		final java.lang.String strCurrency,
		final java.lang.String strRateIndex,
		final java.lang.String strCreditCurveName,
		final double dblSpread,
		final int iFreq,
		final java.lang.String strDayCount,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final int iPenultimateCouponDate,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final org.drip.quant.common.Array2D fsPrincipalOutstanding,
		final org.drip.quant.common.Array2D fsCoupon)
	{
		return CreateSimpleFloaterFP (
			strName,
			strCurrency,
			strRateIndex,
			strCreditCurveName,
			dblSpread,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			dtEffective.addTenor ((12 / iFreq) + "M").julian(),
			iPenultimateCouponDate,
			dapPay,
			dapReset,
			dapMaturity,
			dapEffective,
			dapPeriodEnd,
			dapAccrualEnd,
			dapPeriodStart,
			dapAccrualStart,
			fsPrincipalOutstanding,
			fsCoupon
		);
	}
}
