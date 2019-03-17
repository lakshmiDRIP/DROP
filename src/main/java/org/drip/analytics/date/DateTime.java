
package org.drip.analytics.date;

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
 * <i>DateTime</i> provides the representation of the instantiation-time date and time objects. It provides
 * the following functionality:
 *
 *	<br><br>
 *  <ul>
 *  	<li>
 *  		Instantiation-time and Explicit Date/Time Construction
 *  	</li>
 *  	<li>
 *  		Retrieval of Date/Time Fields
 *  	</li>
 *  	<li>
 *  		Serialization/De-serialization to and from Byte Arrays
 *  	</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/date/README.md">Date</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DateTime {
	private long _lTime = 0L;
	private double _dblDate = java.lang.Double.NaN;

	/**
	 * Default constructor initializes the time and date to the current time and current date.
	 * 
	 * @throws java.lang.Exception Thrown if the DateTime Instance cannot be created
	 */

	public DateTime()
		throws java.lang.Exception
	{
		_lTime = System.nanoTime();

		java.util.Date dtNow = new java.util.Date();

		_dblDate = org.drip.analytics.date.DateUtil.ToJulian (org.drip.analytics.date.DateUtil.Year (dtNow),
			org.drip.analytics.date.DateUtil.Month (dtNow), org.drip.analytics.date.DateUtil.Day (dtNow));
	}

	/**
	 * Constructs DateTime from separate date and time inputs 
	 * 
	 * @param dblDate Date
	 * @param lTime Time
	 * 
	 * @throws java.lang.Exception Thrown on Invalid Inputs
	 */

	public DateTime (
		final double dblDate,
		final long lTime)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblDate))
			throw new java.lang.Exception ("DateTime ctr: Invalid Inputs!");

		_lTime = lTime;
		_dblDate = dblDate;
	}

	/**
	 * Retrieve the Date
	 * 
	 * @return date
	 */

	public double date()
	{
		return _dblDate;
	}

	/**
	 * Retrieve the time
	 * 
	 * @return time
	 */

	public long time()
	{
		return _lTime;
	}
}
