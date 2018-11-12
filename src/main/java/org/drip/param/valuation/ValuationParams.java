
package org.drip.param.valuation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>ValuationParams</i> is the place-holder for the valuation parameters for a given product. It contains
 * the valuation and the cash pay/settle dates, as well as the calendar. It also exposes a number of methods
 * to construct standard valuation parameters.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/valuation">Valuation</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ValuationParams {
	private java.lang.String _strCalendar = "";
	private int _iValueDate = java.lang.Integer.MIN_VALUE;
	private int _iCashPayDate = java.lang.Integer.MIN_VALUE;

	/**
	 * Create the valuation parameters object instance from the valuation date, the cash settle lag, and the
	 * 	settle calendar.
	 * 
	 * @param dtValue Valuation Date
	 * @param iCashSettleLag Cash settle lag
	 * @param strCalendar Calendar Set
	 * @param iAdjustMode The Adjustment Mode Flag
	 * 
	 * @return Valuation Parameters instance
	 */

	public static final ValuationParams Spot (
		final org.drip.analytics.date.JulianDate dtValue,
		final int iCashSettleLag,
		final java.lang.String strCalendar,
		final int iAdjustMode)
	{
		try {
			return null == dtValue ? null : new ValuationParams (dtValue, new
				org.drip.analytics.date.JulianDate (org.drip.analytics.daycount.Convention.Adjust
					(dtValue.addDays (iCashSettleLag).julian(), strCalendar, iAdjustMode)), strCalendar);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the spot valuation parameters for the given valuation date (uses the T+0 settle)
	 *  
	 * @param iDate Valuation Date
	 * 
	 * @return Valuation Parameters instance
	 */

	public static final ValuationParams Spot (
		final int iDate)
	{
		org.drip.analytics.date.JulianDate dtValue = new org.drip.analytics.date.JulianDate (iDate);

		try {
			return new ValuationParams (dtValue, dtValue, "");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the standard T+2B settle parameters for the given valuation date and calendar
	 *  
	 * @param dtValue Valuation Date
	 * @param strCalendar Settle Calendar
	 * 
	 * @return Valuation Parameters instance
	 */

	public static final ValuationParams Standard (
		final org.drip.analytics.date.JulianDate dtValue,
		final java.lang.String strCalendar)
	{
		return Spot (dtValue, 2, strCalendar, org.drip.analytics.daycount.Convention.DATE_ROLL_FOLLOWING);
	}

	/**
	 * Construct ValuationParams from the Valuation Date and the Cash Pay Date parameters
	 * 
	 * @param dtValue Valuation Date
	 * @param dtCashPay Cash Pay Date
	 * @param strCalendar Calendar Set
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public ValuationParams (
		final org.drip.analytics.date.JulianDate dtValue,
		final org.drip.analytics.date.JulianDate dtCashPay,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		if (null == dtValue || null ==  dtCashPay)
			throw new java.lang.Exception ("ValuationParams ctr: Invalid settle/Cash pay into Val Params!");

		_iValueDate = dtValue.julian();

		_iCashPayDate = dtCashPay.julian();

		_strCalendar = strCalendar;
	}

	/**
	 * Retrieve the Valuation Date
	 * 
	 * @return The Valuation Date
	 */

	public int valueDate()
	{
		return _iValueDate;
	}

	/**
	 * Retrieve the Cash Pay Date
	 * 
	 * @return The Cash Pay Date
	 */

	public int cashPayDate()
	{
		return _iCashPayDate;
	}

	/**
	 * Retrieve the Calendar
	 * 
	 * @return The Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}
}
