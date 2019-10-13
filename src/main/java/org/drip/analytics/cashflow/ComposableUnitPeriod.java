
package org.drip.analytics.cashflow;

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
 * <i>ComposableUnitPeriod</i> represents the Cash Flow Periods' Composable Unit Period Details. Currently it
 * holds the Accrual Start Date, the Accrual End Date, the Fixed Coupon, the Basis Spread, the Coupon and the
 * Accrual Day Counts, as well as the EOM Adjustment Flags.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/README.md">Unit and Composite Cash Flow Periods</a></li>
 *  </ul>
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
		if (
			(_iStartDate = iStartDate) >= (_iEndDate = iEndDate) ||
			null == (_strTenor = strTenor) || strTenor.isEmpty() ||
			null == (_ucas = ucas)
		)
			throw new java.lang.Exception ("ComposableUnitPeriod ctr: Invalid Inputs");

		_iFreq = org.drip.analytics.support.Helper.TenorToFreq (_strTenor);

		_dblFullCouponDCF = _ucas.couponDCFOffOfFreq() ? 1. / _iFreq :
			org.drip.analytics.daycount.Convention.YearFraction (
				_iStartDate,
				_iEndDate,
				_ucas.couponDC(),
				_ucas.couponEOMAdjustment(),
				org.drip.analytics.daycount.ActActDCParams.FromFrequency (_iFreq),
				_ucas.calendar()
			);
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
	 * Place the Date Node Location in Relation to the Segment Location
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
	 * @throws java.lang.Exception Thrown if Inputs are Invalid, or if the Date does not lie within the
	 * 	Period
	 */

	public double accrualDCF (
		final int iAccrualEnd)
		throws java.lang.Exception
	{
		if (NODE_INSIDE_SEGMENT != dateLocation (iAccrualEnd))
			throw new java.lang.Exception
				("ComposableUnitPeriod::accrualDCF => Invalid in-period accrual date!");

		org.drip.analytics.daycount.ActActDCParams aap = new org.drip.analytics.daycount.ActActDCParams (
			_iFreq,
			_iEndDate - _iStartDate
		);

		java.lang.String strAccrualDC = accrualDC();

		java.lang.String strCalendar = calendar();

		boolean bAccrualEOMAdjustment = accrualEOMAdjustment();

		return org.drip.analytics.daycount.Convention.YearFraction (
			_iStartDate,
			iAccrualEnd,
			strAccrualDC,
			bAccrualEOMAdjustment,
			aap,
			strCalendar
		) / org.drip.analytics.daycount.Convention.YearFraction (
			_iStartDate,
			_iEndDate,
			strAccrualDC,
			bAccrualEOMAdjustment,
			aap,
			strCalendar
		) * _dblFullCouponDCF;
	}

	/**
	 * Get the Period Full Coupon Rate
	 * 
	 * @param csqc The Market Curve and Surface
	 * 
	 * @return The Period Full Coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Full Coupon Rate cannot be calculated
	 */

	public double fullCouponRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		return baseRate (csqc) + basis();
	}

	/**
	 * Get the Period Base Coupon Rate
	 * 
	 * @param csqc The Market Curve and Surface
	 * 
	 * @return The Period Base Coupon Rate
	 * 
	 * @throws java.lang.Exception Thrown if the base Coupon Rate cannot be calculated
	 */

	public abstract double baseRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
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
