
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>FixedStreamConvention</i> contains the details of the fixed stream of an OTC fixed-float IBOR/Overnight
 * Swap Contact.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC Dual Stream Option Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedStreamConvention {
	private int _iAccrualCompoundingRule = -1;
	private java.lang.String _strCalendar = "";
	private java.lang.String _strCurrency = "";
	private java.lang.String _strDayCount = "";
	private java.lang.String _strUnitPeriodTenor = "";
	private java.lang.String _strCompositePeriodTenor = "";

	/**
	 * FixedStreamConvention Constructor
	 * 
	 * @param strCurrency Currency
	 * @param strDayCount Day Count
	 * @param strCalendar Calendar
	 * @param strUnitPeriodTenor Unit Period Tenor
	 * @param strCompositePeriodTenor Composite Period Tenor
	 * @param iAccrualCompoundingRule Accrual Compounding Rule
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public FixedStreamConvention (
		final java.lang.String strCurrency,
		final java.lang.String strDayCount,
		final java.lang.String strCalendar,
		final java.lang.String strUnitPeriodTenor,
		final java.lang.String strCompositePeriodTenor,
		final int iAccrualCompoundingRule)
		throws java.lang.Exception
	{
		if (null == (_strCurrency = strCurrency) || _strCurrency.isEmpty() || null == (_strDayCount =
			strDayCount) || _strDayCount.isEmpty() || null == (_strCalendar = strCalendar) ||
				_strCalendar.isEmpty() || null == (_strUnitPeriodTenor = strUnitPeriodTenor) ||
					_strUnitPeriodTenor.isEmpty() || null == (_strCompositePeriodTenor =
						strCompositePeriodTenor) || _strCompositePeriodTenor.isEmpty() ||
							!org.drip.analytics.support.CompositePeriodBuilder.ValidateCompoundingRule
								(_iAccrualCompoundingRule = iAccrualCompoundingRule))
			throw new java.lang.Exception ("FixedStreamConvention ctr => Invalid Inputs!");
	}

	/**
	 * Retrieve the Holiday Calendar
	 * 
	 * @return The Holiday Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _strCurrency;
	}

	/**
	 * Retrieve the Day Count Convention
	 * 
	 * @return The Day Count Convention
	 */

	public java.lang.String dayCount()
	{
		return _strDayCount;
	}

	/**
	 * Retrieve the Unit Period Tenor
	 * 
	 * @return The Unit Period Tenor
	 */

	public java.lang.String unitPeriodTenor()
	{
		return _strUnitPeriodTenor;
	}

	/**
	 * Retrieve the Composite Period Tenor
	 * 
	 * @return The Composite Period Tenor
	 */

	public java.lang.String compositePeriodTenor()
	{
		return _strCompositePeriodTenor;
	}

	/**
	 * Retrieve the Accrual Compounding Rule
	 * 
	 * @return The Accrual Compounding Rule
	 */

	public int accrualCompoundingRule()
	{
		return _iAccrualCompoundingRule;
	}

	/**
	 * Create a Fixed Stream Instance
	 * 
	 * @param dtEffective Effective Date
	 * @param strMaturityTenor Maturity Tenor
	 * @param dblCoupon Coupon
	 * @param dblNotional Notional
	 * 
	 * @return The Fixed Stream Instance
	 */

	public org.drip.product.rates.Stream createStream (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strMaturityTenor,
		final double dblCoupon,
		final double dblNotional)
	{
		try {
			org.drip.param.period.UnitCouponAccrualSetting ucas = new
				org.drip.param.period.UnitCouponAccrualSetting
					(org.drip.analytics.support.Helper.TenorToFreq (_strUnitPeriodTenor),
						_strDayCount, false, _strDayCount, false, _strCurrency, false,
							org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			org.drip.param.period.ComposableFixedUnitSetting cfus = new
				org.drip.param.period.ComposableFixedUnitSetting (_strUnitPeriodTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null,
						dblCoupon, 0., _strCurrency);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting
					(org.drip.analytics.support.Helper.TenorToFreq (_strCompositePeriodTenor),
						_strCompositePeriodTenor, _strCurrency, null, dblNotional, null, null, null, null);

			java.util.List<java.lang.Integer> lsEdgeDate =
				org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
					_strCompositePeriodTenor, strMaturityTenor, null);

			return new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FixedCompositeUnit (lsEdgeDate, cps, ucas,
					cfus));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String toString()
	{
		return "[FIXED: " + _strCurrency + " | " + _strDayCount + " | " + _strCalendar + " | " +
			_strUnitPeriodTenor + " | " + _strCompositePeriodTenor + " | " + _iAccrualCompoundingRule + "]";
	}
}
