
package org.drip.analytics.date;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <br><br>
 *
 * The References are:
 * 	<ul>
 * 		<li>
 * 			Fliegel, H. F., and T. C. van Flandern (1968): A Machine Algorithm for Processing Calendar Dates
 * 				<i>Communications of the ACM</i> <b>11</b> 657
 * 		</li>
 * 		<li>
 * 			Fenton, D. (2001): Julian to Calendar Date Conversion
 * 				http://mathforum.org/library/drmath/view/51907.html
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/date/README.md">Date and Time Creation, Manipulation, and Usage</a></li>
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
