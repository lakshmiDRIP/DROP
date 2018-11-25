
package org.drip.analytics.daycount;

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
 * <i>DateEOMAdjustment</i> holds the applicable adjustments for a given date pair. It exposes the following
 * functionality:
 *
 *	<br><br>
 *  <ul>
 * 		<li>
 * 			Static Methods for creating 30/360, 30/365, and EOMA Date Adjustments
 * 		</li>
 * 		<li>
 * 			Export Anterior and Posterior EOM Adjustments
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount">Day Count</a></li>
 *  </ul>
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
