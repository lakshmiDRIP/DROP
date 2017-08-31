
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
 * This class holds the applicable adjustments for a given date pair. It exposes the following functionality:
 * 	- Static Methods for creating 30/360, 30/365, and EOMA Date Adjustments
 * 	- Export Anterior and Posterior EOM Adjustments
 *
 * @author Lakshmi Krishnamurthy
 */

public class DateEOMAdjustment {
	private int _iD1Adj = 0;
	private int _iD2Adj = 0;

	/**
	 * Construct a DateEOMAdjustment Instance for the 30/365 Day Count
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param bApplyEOMAdj TRUE - Apply EOM Adjustment
	 * 
	 * @return DateEOMAdjustment Instance
	 */

	public static final DateEOMAdjustment MakeDEOMA30_365 (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj)
	{
		DateEOMAdjustment dm = new DateEOMAdjustment();

		if (!bApplyEOMAdj) return dm;

		if (iEndDate > iStartDate) return null;

		try {
			if (org.drip.analytics.date.DateUtil.FEBRUARY == org.drip.analytics.date.DateUtil.Month
				(iStartDate) && org.drip.analytics.date.DateUtil.IsEOM (iStartDate) &&
					org.drip.analytics.date.DateUtil.FEBRUARY == org.drip.analytics.date.DateUtil.Month
						(iEndDate) && org.drip.analytics.date.DateUtil.IsEOM (iEndDate))
				dm._iD2Adj = (28 == org.drip.analytics.date.DateUtil.DaysInMonth
					(org.drip.analytics.date.DateUtil.Month (iEndDate),
						org.drip.analytics.date.DateUtil.Year (iEndDate)) ? 2 : 1);

			if (org.drip.analytics.date.DateUtil.FEBRUARY == org.drip.analytics.date.DateUtil.Month
				(iStartDate) && org.drip.analytics.date.DateUtil.IsEOM (iStartDate))
				dm._iD1Adj = (28 == org.drip.analytics.date.DateUtil.DaysInMonth
					(org.drip.analytics.date.DateUtil.Month (iStartDate),
						org.drip.analytics.date.DateUtil.Year (iStartDate)) ? 2 : 1);

			if (31 == org.drip.analytics.date.DateUtil.Date (iEndDate) + dm._iD2Adj && (30 ==
				org.drip.analytics.date.DateUtil.Date (iStartDate) + dm._iD1Adj || 31 ==
					org.drip.analytics.date.DateUtil.Date (iStartDate) + dm._iD1Adj))
				dm._iD2Adj -= 1;

			if (31 == org.drip.analytics.date.DateUtil.Date (iStartDate) + dm._iD1Adj) dm._iD1Adj -= 1;

			return dm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a DateEOMAdjustment Instance for the 30/360 Day Count
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param bApplyEOMAdj TRUE - Apply EOM Adjustment
	 * 
	 * @return DateEOMAdjustment Instance
	 */

	public static final DateEOMAdjustment MakeDEOMA30_360 (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj)
	{
		DateEOMAdjustment dm = new DateEOMAdjustment();

		if (!bApplyEOMAdj) return dm;

		try {
			if (31 == org.drip.analytics.date.DateUtil.Date (iStartDate)) dm._iD1Adj -= 1;

			if (!org.drip.analytics.date.DateUtil.IsLeapYear (iStartDate)) {
				if (org.drip.analytics.date.DateUtil.FEBRUARY == org.drip.analytics.date.DateUtil.Month
					(iStartDate) && 28 == org.drip.analytics.date.DateUtil.Date (iStartDate))
					dm._iD1Adj += 2;
			} else {
				if (org.drip.analytics.date.DateUtil.FEBRUARY == org.drip.analytics.date.DateUtil.Month
					(iStartDate) && 29 == org.drip.analytics.date.DateUtil.Date (iStartDate))
					dm._iD1Adj += 1;
			}

			if (31 == org.drip.analytics.date.DateUtil.Date (iEndDate) && (30 ==
				org.drip.analytics.date.DateUtil.Date (iStartDate) || 31 ==
					org.drip.analytics.date.DateUtil.Date (iStartDate)))
				dm._iD2Adj -= 1;

			return dm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a DateEOMAdjustment Instance for all other Day Counts
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param bApplyEOMAdj TRUE - Apply EOM Adjustment
	 * 
	 * @return DateEOMAdjustment Instance
	 */

	public static final DateEOMAdjustment MakeDEOMA (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj)
	{
		DateEOMAdjustment dm = new DateEOMAdjustment();

		if (!bApplyEOMAdj) return dm;

		try {
			if (bApplyEOMAdj) {
				if (org.drip.analytics.date.DateUtil.IsEOM (iStartDate))
					dm._iD1Adj = 30 - org.drip.analytics.date.DateUtil.Date (iStartDate);

				if (org.drip.analytics.date.DateUtil.IsEOM (iEndDate) &&
					(org.drip.analytics.date.DateUtil.FEBRUARY != org.drip.analytics.date.DateUtil.Month
						(iEndDate)))
					dm._iD2Adj = 30 - org.drip.analytics.date.DateUtil.Date (iEndDate);
			}

			return dm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a DateEOMAdjustment Instance for the 30E/360 Day Count
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param bApplyEOMAdj TRUE - Apply EOM Adjustment
	 * 
	 * @return DateEOMAdjustment Instance
	 */

	public static final DateEOMAdjustment MakeDEOMA30E_360 (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj)
	{
		DateEOMAdjustment dm = new DateEOMAdjustment();

		if (!bApplyEOMAdj) return dm;

		try {
			if (bApplyEOMAdj) {
				if (31 == org.drip.analytics.date.DateUtil.Date (iStartDate)) dm._iD1Adj = -1;

				if (31 == org.drip.analytics.date.DateUtil.Date (iEndDate)) dm._iD2Adj = -1;
			}

			return dm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a DateEOMAdjustment Instance for the 30E/360 ISDA Day Count
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param bApplyEOMAdj TRUE - Apply EOM Adjustment
	 * 
	 * @return DateEOMAdjustment instance
	 */

	public static final DateEOMAdjustment MakeDEOMA30E_360_ISDA (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj)
	{
		DateEOMAdjustment dm = new DateEOMAdjustment();

		if (!bApplyEOMAdj) return dm;

		try {
			if (bApplyEOMAdj) {
				if (org.drip.analytics.date.DateUtil.IsEOM (iStartDate))
					dm._iD1Adj = 30 - org.drip.analytics.date.DateUtil.Date (iStartDate);

				if (org.drip.analytics.date.DateUtil.IsEOM (iEndDate))
					dm._iD2Adj = 30 - org.drip.analytics.date.DateUtil.Date (iEndDate);
			}

			return dm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a DateEOMAdjustment Instance for the 30E+/360 ISDA Day Count
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param bApplyEOMAdj TRUE - Apply EOM Adjustment
	 * 
	 * @return DateEOMAdjustment instance
	 */

	public static final DateEOMAdjustment MakeDEOMA30EPLUS_360_ISDA (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj)
	{
		DateEOMAdjustment dm = new DateEOMAdjustment();

		if (!bApplyEOMAdj) return dm;

		try {
			if (bApplyEOMAdj) {
				if (31 == org.drip.analytics.date.DateUtil.Date (iStartDate)) dm._iD1Adj = -1;

				if (31 == org.drip.analytics.date.DateUtil.Date (iStartDate)) dm._iD2Adj = +1;
			}

			return dm;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Anterior Date Adjustment
	 * 
	 * @return The Anterior Date Adjustment
	 */

	public int anterior()
	{
		return _iD1Adj;
	}

	/**
	 * Retrieve the Posterior Date Adjustment
	 * 
	 * @return The Posterior Date Adjustment
	 */

	public int posterior()
	{
		return _iD2Adj;
	}
}
