
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
 * <i>Weekend</i> holds the left and the right weekend days. It provides functionality to retrieve them,
 *  check if the given day is a weekend, and serialize/de-serialize weekend days.
 *  <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/eventday">Event Day</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Weekend {
	private int[] _aiDay = null;

	/**
	 * Create a Weekend Instance with SATURDAY and SUNDAY
	 * 
	 * @return Weekend object
	 */

	public static final Weekend StandardWeekend()
	{
		try {
			return new Weekend (new int[] {org.drip.analytics.date.DateUtil.SUNDAY,
				org.drip.analytics.date.DateUtil.SATURDAY});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the weekend instance object from the array of the weekend days
	 * 
	 * @param aiDay Array of the weekend days
	 * 
	 * @throws java.lang.Exception Thrown if cannot properly de-serialize Weekend
	 */

	public Weekend (
		final int[] aiDay)
		throws java.lang.Exception
	{
		if (null == aiDay) throw new java.lang.Exception ("Weekend ctr: Invalid Inputs");

		int iNumWeekendDays = aiDay.length;;

		if (0 == iNumWeekendDays) throw new java.lang.Exception ("Weekend ctr: Invalid Inputs");

		_aiDay = new int[iNumWeekendDays];

		for (int i = 0; i < iNumWeekendDays; ++i)
			_aiDay[i] = aiDay[i];
	}

	/**
	 * Retrieve the weekend days
	 * 
	 * @return Array of the weekend days
	 */

	public int[] days()
	{
		return _aiDay;
	}

	/**
	 * Is the given date a left weekend day
	 * 
	 * @param iDate Date
	 * 
	 * @return True (Left weekend day)
	 */

	public boolean isLeftWeekend (
		final int iDate)
	{
		if (null == _aiDay || 0 == _aiDay.length) return false;

		if (_aiDay[0] == (iDate % 7)) return true;

		return false;
	}

	/**
	 * Is the given date a right weekend day
	 * 
	 * @param dblDate Date
	 * 
	 * @return True (Right weekend day)
	 */

	public boolean isRightWeekend (
		final double dblDate)
	{
		if (null == _aiDay || 1 >= _aiDay.length) return false;

		if (_aiDay[1] == (dblDate % 7)) return true;

		return false;
	}

	/**
	 * Is the given date a weekend day
	 * 
	 * @param iDate Date
	 * 
	 * @return True (Weekend day)
	 */

	public boolean isWeekend (
		final int iDate)
	{
		return isLeftWeekend (iDate) || isRightWeekend (iDate);
	}
}
