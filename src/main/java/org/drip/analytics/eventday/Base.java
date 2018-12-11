
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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * <i>Base</i> is an abstraction around holiday and description. Abstract function generates an optional
 * adjustment for weekends in a given year.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday/README.md">Event Day</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class Base {
	private java.lang.String _strDescription = "";

	/**
	 * Constructs the Base instance from the description
	 * 
	 * @param strDescription Holiday Description
	 */

	public Base (
		final java.lang.String strDescription)
	{
		_strDescription = strDescription;
	}

	/**
	 * Roll the date to a non-holiday according to the rule specified
	 * 
	 * @param iDate Date to be rolled
	 * @param bBalkOnYearShift Throw an exception if the year change happens
	 * @param wkend Object representing the weekend days
	 * 
	 * @return The Adjusted Date
	 * 
	 * @throws java.lang.Exception Thrown if the holiday cannot be rolled
	 */

	public static final int rollHoliday (
		final int iDate,
		final boolean bBalkOnYearShift,
		final Weekend wkend)
		throws java.lang.Exception
	{
		int iRolledDate = iDate;

		if (null != wkend && wkend.isLeftWeekend (iDate)) iRolledDate = iDate - 1;

		if (null != wkend && wkend.isRightWeekend (iDate)) iRolledDate = iDate + 1;

		if (bBalkOnYearShift & org.drip.analytics.date.DateUtil.Year (iDate) !=
			org.drip.analytics.date.DateUtil.Year (iRolledDate))
			throw new java.lang.Exception ("Base::rollHoliday => Invalid Inputs");

		return iRolledDate;
	}

	/**
	 * Return the description
	 * 
	 * @return Description
	 */

	public java.lang.String description()
	{
		return _strDescription;
	}

	/**
	 * Generate the full date specific to the input year
	 * 
	 * @param iYear Input Year
	 * @param bAdjusted Whether adjustment is desired
	 * 
	 * @return The full date
	 */

	public abstract int dateInYear (
		final int iYear,
		final boolean bAdjusted);
}
