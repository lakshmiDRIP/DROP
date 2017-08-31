
package org.drip.analytics.cashflow;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 *  - DRIP Asset Allocation: Library for models for MPT framework, Black Litterman Strategy Incorporator,
 *  	Holdings Constraint, and Transaction Costs.
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
 * ComposableUnitPeriod contains the cash flow periods' composable unit period details. Currently it
 *  holds the accrual start date, the accrual end date, the fixed coupon, the basis spread, coupon and
 *  accrual day counts, and the EOM adjustment flags.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ComposableUnitPeriod {

	/**
	 * Node is to the Left of the Period
	 */

	public static final int NODE_LEFT_OF_SEGMENT = 1;

	/**
	 * Node is Inside the Period
	 */

	public static final int NODE_INSIDE_SEGMENT = 2;

	/**
	 * Node is to the Right of the Period
	 */

	public static final int NODE_RIGHT_OF_SEGMENT = 4;

	private int _iFreq = -1;
	private java.lang.String _strTenor = "";
	private int _iEndDate = java.lang.Integer.MIN_VALUE;
	private int _iStartDate = java.lang.Integer.MIN_VALUE;
	private double _dblFullCouponDCF = java.lang.Double.NaN;
	private org.drip.param.period.UnitCouponAccrualSetting _ucas = null;

	protected ComposableUnitPeriod (
		final int iStartDate,
		final int iEndDate,
		final java.lang.String strTenor,
		final org.drip.param.period.UnitCouponAccrualSetting ucas)
		throws java.lang.Exception
	{
		if ((_iStartDate = iStartDate) >= (_iEndDate = iEndDate) || null == (_strTenor = strTenor) ||
			strTenor.isEmpty() || null == (_ucas = ucas))
			throw new java.lang.Exception ("ComposableUnitPeriod ctr: Invalid Inputs");

		_iFreq = org.drip.analytics.support.Helper.TenorToFreq (_strTenor);

		_dblFullCouponDCF = _ucas.couponDCFOffOfFreq() ? 1. / _iFreq :
			org.drip.analytics.daycount.Convention.YearFraction (_iStartDate, _iEndDate, _ucas.couponDC(),
				_ucas.couponEOMAdjustment(), null, _ucas.calendar());
	}

	/**
	 * Retrieve the Accrual Start Date
	 * 
	 * @return The Accrual Start Date
	 */

	public int startDate()
	{
		return _iStartDate;
	}

	/**
	 * Retrieve the Accrual End Date
	 * 
	 * @return The Accrual End Date
	 */

	public int endDate()
	{
		return _iEndDate;
	}

	/**
	 * Retrieve the Coupon Frequency
	 * 
	 * @return The Coupon Frequency
	 */

	public int freq()
	{
		return _iFreq;
	}

	/**
	 * Retrieve the Coupon Day Count
	 * 
	 * @return The Coupon Day Count
	 */

	public java.lang.String couponDC()
	{
		return _ucas.couponDC();
	}

	/**
	 * Retrieve the Coupon EOM Adjustment Flag
	 * 
	 * @return The Coupon EOM Adjustment Flag
	 */

	public boolean couponEOMAdjustment()
	{
		return _ucas.couponEOMAdjustment();
	}

	/**
	 * Retrieve the Accrual Day Count
	 * 
	 * @return The Accrual Day Count
	 */

	public java.lang.String accrualDC()
	{
		return _ucas.accrualDC();
	}

	/**
	 * Retrieve the Accrual EOM Adjustment Flag
	 * 
	 * @return The Accrual EOM Adjustment Flag
	 */

	public boolean accrualEOMAdjustment()
	{
		return _ucas.accrualEOMAdjustment();
	}

	/**
	 * Retrieve the Flag indicating whether Coupon DCF is computed off of the DCF Flag
	 * 
	 * @return true - The Flag indicating whether Coupon DCF is computed off of the DCF Flag
	 */

	public boolean couponDCFOffOfFreq()
	{
		return _ucas.couponDCFOffOfFreq();
	}

	/**
	 * Retrieve the Calendar
	 * 
	 * @return The Calendar
	 */

	public java.lang.String calendar()
	{
		return _ucas.calendar();
	}

	/**
	 * Retrieve the Accrual Compounding Rule
	 * 
	 * @return The Accrual Compounding Rule
	 */

	public int accrualCompoundingRule()
	{
		return _ucas.accrualCompoundingRule();
	}

	/**
	 * Retrieve the Period Full Coupon DCF
	 * 
	 * @return The Period Full Coupon DCF
	 */

	public double fullCouponDCF()
	{
		return _dblFullCouponDCF;
	}

	/**
	 * Convert the Coupon Frequency into a Tenor
	 * 
	 * @return The Coupon Frequency converted into a Tenor
	 */

	public java.lang.String tenor()
	{
		return _strTenor;
	}

	/**
	 * Place the Date Node Location in relation to the segment Location
	 * 
	 * @param iDateNode The Node Ordinate
	 * 
	 * @return One of NODE_LEFT_OF_SEGMENT, NODE_RIGHT_OF_SEGMENT, or NODE_INSIDE_SEGMENT
	 */

	public int dateLocation (
		final int iDateNode)
	{
		if (iDateNode < _iStartDate) return NODE_LEFT_OF_SEGMENT;

		if (iDateNode > _iEndDate) return NODE_RIGHT_OF_SEGMENT;

		return NODE_INSIDE_SEGMENT;
	}

	/**
	 * Get the Period Accrual Day Count Fraction to an Accrual End Date
	 * 
	 * @param iAccrualEnd Accrual End Date
	 * 
	 * @return The Accrual DCF
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid, or if the date does not lie within the
	 * 	period
	 */

	public double accrualDCF (
		final int iAccrualEnd)
		throws java.lang.Exception
	{
		if (NODE_INSIDE_SEGMENT != dateLocation (iAccrualEnd))
			throw new java.lang.Exception
				("ComposableUnitPeriod::accrualDCF => Invalid in-period accrual date!");

		org.drip.analytics.daycount.ActActDCParams aap = new org.drip.analytics.daycount.ActActDCParams
			(_iFreq, _iEndDate - _iStartDate);

		java.lang.String strAccrualDC = accrualDC();

		java.lang.String strCalendar = calendar();

		boolean bAccrualEOMAdjustment = accrualEOMAdjustment();

		return org.drip.analytics.daycount.Convention.YearFraction (_iStartDate, iAccrualEnd, strAccrualDC,
			bAccrualEOMAdjustment, aap, strCalendar) / org.drip.analytics.daycount.Convention.YearFraction
				(_iStartDate, _iEndDate, strAccrualDC, bAccrualEOMAdjustment, aap, strCalendar) *
					_dblFullCouponDCF;
	}

	/**
	 * Get the Period Full Coupon Rate
	 * 
	 * @param csqs The Market Curve and Surface
	 * 
	 * @return The Period Full Coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if the full Coupon Rate cannot be calculated
	 */

	public double fullCouponRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
		throws java.lang.Exception
	{
		return baseRate (csqs) + basis();
	}

	/**
	 * Get the Period Base Coupon Rate
	 * 
	 * @param csqs The Market Curve and Surface
	 * 
	 * @return The Period Base Coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if the base Coupon Rate cannot be calculated
	 */

	public abstract double baseRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
		throws java.lang.Exception;

	/**
	 * Get the Period Coupon Basis
	 * 
	 * @return The Period Coupon Basis
	 */

	public abstract double basis();

	/**
	 * Get the Period Coupon Currency
	 * 
	 * @return The Period Coupon Currency
	 */

	public abstract java.lang.String couponCurrency();
}
