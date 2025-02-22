
package org.drip.analytics.daycount;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>DCAct_Act</i> implements the Act/Act day count convention.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount/README.md">Day Count Year Fraction Utilities</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DCAct_Act implements org.drip.analytics.daycount.DCFCalculator {

	/**
	 * Empty DCAct_Act constructor
	 */

	public DCAct_Act()
	{
	}

	@Override public java.lang.String baseCalculationType()
	{
		return "DCAct_Act";
	}

	@Override public java.lang.String[] alternateNames()
	{
		return new java.lang.String[] {"Actual/Actual", "Actual/Actual ICMA", "Act/Act", "Act/Act ICMA",
			"ISMA-99", "Act/Act ISMA", "DCAct_Act"};
	}

	@Override public double yearFraction (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj,
		final ActActDCParams actactParams,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		double dblDCF = 0.;
		int iNumLeapDays = 0;
		int iNumNonLeapDays = 0;

		int iYearStart = org.drip.analytics.date.DateUtil.Year (iStartDate);

		int iYearEnd = org.drip.analytics.date.DateUtil.Year (iEndDate);

		if (iYearStart == iYearEnd)
			return org.drip.analytics.date.DateUtil.IsLeapYear (iStartDate) ? (iEndDate - iStartDate) / 366.
				: (iEndDate - iStartDate) / 365.;

		if (org.drip.analytics.date.DateUtil.IsLeapYear (iStartDate))
			iNumLeapDays += org.drip.analytics.date.DateUtil.DaysRemaining (iStartDate);
		else
			iNumNonLeapDays += org.drip.analytics.date.DateUtil.DaysRemaining (iStartDate);

		if (org.drip.analytics.date.DateUtil.IsLeapYear (iEndDate))
			iNumLeapDays += org.drip.analytics.date.DateUtil.DaysElapsed (iEndDate);
		else
			iNumNonLeapDays += org.drip.analytics.date.DateUtil.DaysElapsed (iEndDate);

		for (int iYear = iYearStart + 1; iYear < iYearEnd; ++iYear) {
			int iYearJan1 = org.drip.analytics.date.DateUtil.ToJulian (iYear,
				org.drip.analytics.date.DateUtil.JANUARY, 1);

			if (org.drip.analytics.date.DateUtil.IsLeapYear (iYearJan1))
				iNumLeapDays += 366;
			else
				iNumNonLeapDays += 365;
		}

		return dblDCF + (((double) (iNumLeapDays)) / 366.) + (((double) (iNumNonLeapDays)) / 365.);
	}

	@Override public int daysAccrued (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj,
		final ActActDCParams actactParams,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		int iNumLeapDays = 0;
		int iNumNonLeapDays = 0;

		int iYearStart = org.drip.analytics.date.DateUtil.Year (iStartDate);

		int iYearEnd = org.drip.analytics.date.DateUtil.Year (iEndDate);

		if (iYearStart == iYearEnd) return iEndDate - iStartDate;

		if (org.drip.analytics.date.DateUtil.IsLeapYear (iStartDate))
			iNumLeapDays += org.drip.analytics.date.DateUtil.DaysRemaining (iStartDate);
		else
			iNumNonLeapDays += org.drip.analytics.date.DateUtil.DaysRemaining (iStartDate);

		if (org.drip.analytics.date.DateUtil.IsLeapYear (iEndDate))
			iNumLeapDays += org.drip.analytics.date.DateUtil.DaysElapsed (iEndDate);
		else
			iNumNonLeapDays += org.drip.analytics.date.DateUtil.DaysElapsed (iEndDate);

		for (int iYear = iYearStart + 1; iYear < iYearEnd; ++iYear) {
			int iYearJan1 = org.drip.analytics.date.DateUtil.ToJulian (iYear,
				org.drip.analytics.date.DateUtil.JANUARY, 1);

			if (org.drip.analytics.date.DateUtil.IsLeapYear (iYearJan1))
				iNumLeapDays += 366;
			else
				iNumNonLeapDays += 365;
		}

		return iNumLeapDays + iNumNonLeapDays;
	}
}
