
package org.drip.analytics.eventday;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

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
 * DateInMonth exports Functionality that generates the specific Event Date inside of the specified
 * 	Month/Year.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DateInMonth {

	/**
	 * Instance Date Generation Rules - Generate from Lag from Front/Back
	 */

	public static final int INSTANCE_GENERATOR_RULE_EDGE_LAG = 1;

	/**
	 * Instance Date Generation Rule - Generate from Specified Day in Week/Week in Month
	 */

	public static final int INSTANCE_GENERATOR_RULE_WEEK_DAY = 2;

	/**
	 * Instance Date Generation Rule - Generate Using the Specific Day of the Month
	 */

	public static final int INSTANCE_GENERATOR_RULE_SPECIFIC_DAY_OF_MONTH = 3;

	private int _iLag = -1;
	private int _iDayOfWeek = -1;
	private int _iWeekInMonth = -1;
	private boolean _bFromBack = false;
	private int _iSpecificDayInMonth = -1;
	private int _iInstanceGeneratorRule = -1;

	/**
	 * DateInMonth Constructor
	 * 
	 * @param iInstanceGeneratorRule Instance Generation Rule
	 * @param bFromBack TRUE - Apply Rules from Back of EOM
	 * @param iLag The Lag
	 * @param iDayOfWeek Day of Week
	 * @param iWeekInMonth Week in the Month
	 * @param iSpecificDayInMonth Specific Daye In Month
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public DateInMonth (
		final int iInstanceGeneratorRule,
		final boolean bFromBack,
		final int iLag,
		final int iDayOfWeek,
		final int iWeekInMonth,
		final int iSpecificDayInMonth)
		throws java.lang.Exception
	{
		_bFromBack = bFromBack;

		if (INSTANCE_GENERATOR_RULE_EDGE_LAG == (_iInstanceGeneratorRule = iInstanceGeneratorRule)) {
			if (0 > (_iLag = iLag)) throw new java.lang.Exception ("DateInMonth ctr: Invalid Inputs");
		} else if (INSTANCE_GENERATOR_RULE_WEEK_DAY == _iInstanceGeneratorRule) {
			_iDayOfWeek = iDayOfWeek;
			_iWeekInMonth = iWeekInMonth;
		} else
			_iSpecificDayInMonth = iSpecificDayInMonth;
	}

	/**
	 * Retrieve the Instance Generation Rule
	 * 
	 * @return The Instance Generation Rule
	 */

	public int instanceGenerator()
	{
		return _iInstanceGeneratorRule;
	}

	/**
	 * Retrieve the Flag indicating whether the Lag is from the Front/Back
	 * 
	 * @return TRUE - The Lag is from the Back.
	 */

	public boolean fromBack()
	{
		return _bFromBack;
	}

	/**
	 * Retrieve the Date Lag
	 * 
	 * @return The Date Lag
	 */

	public int lag()
	{
		return _iLag;
	}

	/**
	 * Retrieve the Week In Month
	 * 
	 * @return The Week In Month
	 */

	public int weekInMonth()
	{
		return _iWeekInMonth;
	}

	/**
	 * Retrieve the Day Of Week
	 * 
	 * @return The Day Of Week
	 */

	public int dayOfWeek()
	{
		return _iDayOfWeek;
	}

	/**
	 * Retrieve the Specific Day in Month
	 * 
	 * @return The Specific Day in Month
	 */

	public int specificDayInMonth()
	{
		return _iSpecificDayInMonth;
	}

	/**
	 * Generate the Particular Day of the Year, the Month, according to the Calendar
	 * 
	 * @param iYear Target Year
	 * @param iMonth Target Month
	 * @param strCalendar Target Calendar
	 * 
	 * @return The Particular Day
	 */

	public org.drip.analytics.date.JulianDate instanceDay (
		final int iYear,
		final int iMonth,
		final java.lang.String strCalendar)
	{
		try {
			if (INSTANCE_GENERATOR_RULE_EDGE_LAG == _iInstanceGeneratorRule) {
				if (_bFromBack) {
					org.drip.analytics.date.JulianDate dtBase =
						org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, iMonth,
							org.drip.analytics.date.DateUtil.DaysInMonth (iMonth, iYear));

					return null == dtBase ? null : dtBase.subtractBusDays (_iLag, strCalendar);
				}

				org.drip.analytics.date.JulianDate dtBase = org.drip.analytics.date.DateUtil.CreateFromYMD
					(iYear, iMonth, 1);

				return null == dtBase ? null : dtBase.addBusDays (_iLag, strCalendar);
			}

			if (INSTANCE_GENERATOR_RULE_WEEK_DAY == _iInstanceGeneratorRule) {
				if (_bFromBack) {
					org.drip.analytics.date.JulianDate dtEOM = org.drip.analytics.date.DateUtil.CreateFromYMD
						(iYear, iMonth, org.drip.analytics.date.DateUtil.DaysInMonth (iMonth, iYear));

					if (null == dtEOM) return null;

					while (_iDayOfWeek != org.drip.analytics.date.DateUtil.Day
						(org.drip.analytics.date.DateUtil.JavaDateFromJulianDate (dtEOM)))
						dtEOM = dtEOM.subtractDays (1);

					org.drip.analytics.date.JulianDate dtUnadjusted = dtEOM.subtractDays (_iWeekInMonth * 7);

					return null == dtUnadjusted ? null : dtUnadjusted.subtractBusDays (0, strCalendar);
				}

				org.drip.analytics.date.JulianDate dtSOM = org.drip.analytics.date.DateUtil.CreateFromYMD
					(iYear, iMonth, 1);

				if (null == dtSOM) return null;

				while (_iDayOfWeek != org.drip.analytics.date.DateUtil.Day
					(org.drip.analytics.date.DateUtil.JavaDateFromJulianDate (dtSOM)))
					dtSOM = dtSOM.addDays (1);

				org.drip.analytics.date.JulianDate dtUnadjusted = dtSOM.addDays (_iWeekInMonth * 7);

				return null == dtUnadjusted ? null : dtUnadjusted.addBusDays (0, strCalendar);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.date.JulianDate dtBase = org.drip.analytics.date.DateUtil.CreateFromYMD (iYear,
			iMonth, _iSpecificDayInMonth);

		if (null == dtBase) return null;

		return _bFromBack ? dtBase.subtractBusDays (0, strCalendar) : dtBase.addBusDays (0, strCalendar);
	}

	@Override public java.lang.String toString()
	{
		return "[DateInMonth => Instance Generator Rule: " + _iInstanceGeneratorRule + " | From Back Flag: "
			+ _bFromBack + " | Day Of Week: " + _iDayOfWeek + " | Week In Month: " + _iWeekInMonth +
				" | Specific Day In Month: " + _iSpecificDayInMonth + " | Lag: " + _iLag + "]";
	}
}
