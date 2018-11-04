
package org.drip.analytics.eventday;

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
 * <i>Variable</i> class contains the rule characterizing the variable holiday’s month, day in week, week in
 *  month, and the weekend days. Specific holidays in the given year are generated using these rules.
 *  <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday">Event Day</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Variable extends Base {
	private int _iMonth = 0;
	private int _iWeekDay = 0;
	private int _iWeekInMonth = 0;
	private boolean _bFromFront = true;
	private org.drip.analytics.eventday.Weekend _wkend = null;

	/**
	 * Construct the object from the week, day, month, from front/back, week end, and description
	 * 
	 * @param iWeekInMonth Week of the Month
	 * @param iWeekDay Day of the Week
	 * @param iMonth Month
	 * @param bFromFront From Front (true), Back (false)
	 * @param wkend Weekend
	 * @param strDescription Description
	 */

	public Variable (
		final int iWeekInMonth,
		final int iWeekDay,
		final int iMonth,
		final boolean bFromFront,
		final Weekend wkend,
		final java.lang.String strDescription)
	{
		super (strDescription);

		_wkend = wkend;
		_iMonth = iMonth;
		_iWeekDay = iWeekDay;
		_bFromFront = bFromFront;
		_iWeekInMonth = iWeekInMonth;
	}

	@Override public int dateInYear (
		final int iYear,
		final boolean bAdjustForWeekend)
	{
		int iDate = java.lang.Integer.MIN_VALUE;

		try {
			if (_bFromFront)
				iDate = (org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, _iMonth,
					_iWeekDay)).julian() + (7 * (_iWeekInMonth - 1));
			else {
				iDate = (org.drip.analytics.date.DateUtil.CreateFromYMD (iYear, _iMonth,
					org.drip.analytics.date.DateUtil.DaysInMonth (_iMonth, iYear))).julian() - (7 *
						(_iWeekInMonth - 1));

				while (_iWeekDay != (iDate % 7)) --iDate;
			}

			if (bAdjustForWeekend) return Base.rollHoliday (iDate, true, _wkend);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return iDate;
	}
}
