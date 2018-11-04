
package org.drip.analytics.output;

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
 * <i>UnitPeriodConvexityMetrics</i> holds the results of a unit composable period convexity metrics estimate
 * 	output.
 *  <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/output">Output</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnitPeriodConvexityMetrics {
	private int _iEndDate = java.lang.Integer.MIN_VALUE;
	private int _iStartDate = java.lang.Integer.MIN_VALUE;
	private org.drip.analytics.output.ConvexityAdjustment _convAdj = null;

	/**
	 * UnitPeriodConvexityMetrics constructor
	 * 
	 * @param iStartDate Metric Period Start Date
	 * @param iEndDate Metric Period End Date
	 * @param convAdj Coupon Period Convexity Adjustment
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public UnitPeriodConvexityMetrics (
		final int iStartDate,
		final int iEndDate,
		final org.drip.analytics.output.ConvexityAdjustment convAdj)
		throws java.lang.Exception
	{
		if ((_iEndDate = iEndDate) < (_iStartDate = iStartDate) || null == (_convAdj = convAdj))
			throw new java.lang.Exception ("UnitPeriodConvexityMetrics ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Start Date
	 * 
	 * @return The Start Date
	 */

	public int startDate()
	{
		return _iStartDate;
	}

	/**
	 * Retrieve the End Date
	 * 
	 * @return The End Date
	 */

	public int endDate()
	{
		return _iEndDate;
	}

	/**
	 * Retrieve the Convexity Adjustment
	 * 
	 * @return The Convexity Adjustment
	 */

	public org.drip.analytics.output.ConvexityAdjustment convAdj()
	{
		return _convAdj;
	}
}
