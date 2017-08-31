
package org.drip.analytics.daycount;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * This class implements the Act/Act day count convention.
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
