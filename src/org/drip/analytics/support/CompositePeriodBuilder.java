
package org.drip.analytics.support;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * CompositePeriodBuilder exposes the composite period construction functionality.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CompositePeriodBuilder {

	/**
	 * Edge Date Generation Sequence - Forward
	 */

	public static final int EDGE_DATE_SEQUENCE_FORWARD = 0;

	/**
	 * Edge Date Generation Sequence - Reverse
	 */

	public static final int EDGE_DATE_SEQUENCE_REVERSE = 1;

	/**
	 * Edge Date Generation Sequence - Regular
	 */

	public static final int EDGE_DATE_SEQUENCE_REGULAR = 2;

	/**
	 * Edge Date Generation Sequence - Overnight
	 */

	public static final int EDGE_DATE_SEQUENCE_OVERNIGHT = 4;

	/**
	 * Edge Date Generation Sequence - Single Edge Date Pair Between Dates
	 */

	public static final int EDGE_DATE_SEQUENCE_SINGLE = 8;

	/**
	 * Period Set Generation Customization - Short Stub (i.e., No adjustment on either end)
	 */

	public static final int SHORT_STUB = 0;

	/**
	 * Period Set Generation Customization - Merge the front periods to produce a long front
	 */

	public static final int FULL_FRONT_PERIOD = 1;

	/**
	 * Period Set Generation Customization - Long Stub (if present) belongs to the front/back end depending
	 * 	upon backwards/forwards generation scheme
	 */

	public static final int LONG_STUB = 2;

	/**
	 * Reference Period Fixing is IN-ARREARS (i.e., displaced one period to the right) of the Coupon Period
	 */

	public static final int REFERENCE_PERIOD_IN_ARREARS = 0;

	/**
	 * Reference Period Fixing is IN-ADVANCE (i.e., the same as that) of the Coupon Period
	 */

	public static final int REFERENCE_PERIOD_IN_ADVANCE = 1;

	/**
	 * Accrual Compounding Rule - Arithmetic
	 */

	public static final int ACCRUAL_COMPOUNDING_RULE_ARITHMETIC = 1;

	/**
	 * Accrual Compounding Rule - Geometric
	 */

	public static final int ACCRUAL_COMPOUNDING_RULE_GEOMETRIC = 2;

	private static final int DAPAdjust (
		final int iDate,
		final org.drip.analytics.daycount.DateAdjustParams dap)
	{
		if (null == dap) return iDate;

		try {
			return dap.roll (iDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return iDate;
	}

	/**
	 * Verify if the Specified Accrual Compounding Rule is a Valid One
	 * 
	 * @param iAccrualCompoundingRule The Accrual Compounding Rule
	 * 
	 * @return TRUE - The Accrual Compounding Rule is valid
	 */

	public static final boolean ValidateCompoundingRule (
		final int iAccrualCompoundingRule)
	{
		return ACCRUAL_COMPOUNDING_RULE_ARITHMETIC == iAccrualCompoundingRule ||
			ACCRUAL_COMPOUNDING_RULE_GEOMETRIC == iAccrualCompoundingRule;
	}

	/**
	 * Generate a list of period edge dates forward from the start.
	 * 
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity date
	 * @param strTenor Period Tenor
	 * @param dap Inner Date Adjustment Parameters
	 * @param iPSEC Period Set Edge Customizer Setting
	 * 
	 * @return List of Period Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> ForwardEdgeDates (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final java.lang.String strTenor,
		final org.drip.analytics.daycount.DateAdjustParams dap,
		final int iPSEC)
	{
		if (null == dtEffective || null == dtMaturity || null == strTenor || strTenor.isEmpty()) return null;

		java.lang.String strPeriodRollTenor = "";
		org.drip.analytics.date.JulianDate dtEdge = dtEffective;

		int iMaturityDate = dtMaturity.julian();

		int iEdgeDate = dtEdge.julian();

		if (iEdgeDate >= iMaturityDate) return null;

		java.util.List<java.lang.Integer> lsEdgeDate = new java.util.ArrayList<java.lang.Integer>();

		while (iEdgeDate < iMaturityDate) {
			lsEdgeDate.add (iEdgeDate);

			strPeriodRollTenor = org.drip.analytics.support.Helper.AggregateTenor
				(strPeriodRollTenor, strTenor);

			if (null == (dtEdge = dtMaturity.addTenor (strPeriodRollTenor))) return null;

			iEdgeDate = dtEdge.julian();
		}

		if (iEdgeDate > iMaturityDate) {
			if (SHORT_STUB == iPSEC)
				lsEdgeDate.add (iMaturityDate);
			else if (LONG_STUB == iPSEC) {
				lsEdgeDate.remove (lsEdgeDate.size() - 1);

				lsEdgeDate.add (iMaturityDate);
			}
		} else if (iEdgeDate == iMaturityDate)
			lsEdgeDate.add (iMaturityDate);

		if (null == dap) return lsEdgeDate;

		java.util.List<java.lang.Integer> lsAdjustedEdgeDate = new java.util.ArrayList<java.lang.Integer>();

		lsAdjustedEdgeDate.add (lsEdgeDate.get (0));

		int iNumDate = lsEdgeDate.size();

		for (int i = 1; i < iNumDate - 1; ++i)
			lsAdjustedEdgeDate.add (DAPAdjust (lsEdgeDate.get (i), dap));

		lsAdjustedEdgeDate.add (lsEdgeDate.get (iNumDate - 1));

		return lsAdjustedEdgeDate;
	}

	/**
	 * Generate a list of period edge dates backward from the end.
	 * 
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity date
	 * @param strTenor Period Tenor
	 * @param dap Inner Date Adjustment Parameters
	 * @param iPSEC Period Set Edge Customizer Setting
	 * 
	 * @return List of Period Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> BackwardEdgeDates (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final java.lang.String strTenor,
		final org.drip.analytics.daycount.DateAdjustParams dap,
		final int iPSEC)
	{
		if (null == dtEffective || null == dtMaturity || null == strTenor || strTenor.isEmpty()) return null;

		int iEffectiveDate = dtEffective.julian();

		java.lang.String strPeriodRollTenor = "";
		org.drip.analytics.date.JulianDate dtEdge = dtMaturity;

		int iEdgeDate = dtEdge.julian();

		if (iEffectiveDate >= iEdgeDate) return null;

		java.util.List<java.lang.Integer> lsEdgeDate = new java.util.ArrayList<java.lang.Integer>();

		while (iEdgeDate > iEffectiveDate) {
			lsEdgeDate.add (0, iEdgeDate);

			strPeriodRollTenor = org.drip.analytics.support.Helper.AggregateTenor (strPeriodRollTenor,
				strTenor);

			if (null == (dtEdge = dtMaturity.subtractTenor (strPeriodRollTenor))) return null;

			iEdgeDate = dtEdge.julian();
		}

		if (iEdgeDate < iEffectiveDate) {
			if (SHORT_STUB == iPSEC)
				lsEdgeDate.add (0, iEffectiveDate);
			else if (FULL_FRONT_PERIOD == iPSEC)
				lsEdgeDate.add (0, iEdgeDate);
			else if (LONG_STUB == iPSEC) {
				lsEdgeDate.remove (0);

				lsEdgeDate.add (0, iEffectiveDate);
			}
		} else if (dtEdge.julian() == iEffectiveDate)
			lsEdgeDate.add (0, iEffectiveDate);

		if (null == dap) return lsEdgeDate;

		java.util.List<java.lang.Integer> lsAdjustedEdgeDate = new java.util.ArrayList<java.lang.Integer>();

		lsAdjustedEdgeDate.add (lsEdgeDate.get (0));

		int iNumDate = lsEdgeDate.size();

		for (int i = 1; i < iNumDate - 1; ++i)
			lsAdjustedEdgeDate.add (DAPAdjust (lsEdgeDate.get (i), dap));

		lsAdjustedEdgeDate.add (lsEdgeDate.get (iNumDate - 1));

		return lsAdjustedEdgeDate;
	}

	/**
	 * Generate a list of regular period edge dates forward from the start.
	 * 
	 * @param dtEffective Effective date
	 * @param strPeriodTenor Period Tenor
	 * @param strMaturityTenor Period Tenor
	 * @param dap Inner Date Adjustment Parameters
	 * 
	 * @return List of Period Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> RegularEdgeDates (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strPeriodTenor,
		final java.lang.String strMaturityTenor,
		final org.drip.analytics.daycount.DateAdjustParams dap)
	{
		if (null == dtEffective || null == strPeriodTenor || strPeriodTenor.isEmpty() || null ==
			strMaturityTenor || strMaturityTenor.isEmpty())
			return null;

		int iPeriodTenorMonth = -1;
		int iMaturityTenorMonth = -1;
		int iPeriodMaturityTenorComparison = -1;

		int iMaturityDate = dtEffective.addTenor (strMaturityTenor).julian();

		java.util.List<java.lang.Integer> lsEdgeDate = new java.util.ArrayList<java.lang.Integer>();

		try {
			iPeriodMaturityTenorComparison = org.drip.analytics.support.Helper.TenorCompare (strPeriodTenor,
				strMaturityTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (org.drip.analytics.support.Helper.LEFT_TENOR_EQUALS == iPeriodMaturityTenorComparison ||
			org.drip.analytics.support.Helper.LEFT_TENOR_GREATER == iPeriodMaturityTenorComparison) {
			lsEdgeDate.add (dtEffective.julian());

			lsEdgeDate.add (iMaturityDate);

			return lsEdgeDate;
		}

		try {
			iPeriodTenorMonth = org.drip.analytics.support.Helper.TenorToMonths (strPeriodTenor);

			iMaturityTenorMonth = org.drip.analytics.support.Helper.TenorToMonths (strMaturityTenor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.date.JulianDate dtEdge = dtEffective;
		int iNumPeriod = iMaturityTenorMonth / iPeriodTenorMonth;

		lsEdgeDate.add (dtEdge.julian());

		for (int i = 0; i < iNumPeriod; ++i) {
			dtEdge = dtEdge.addTenor (strPeriodTenor);

			int iEdgeDate = dtEdge.julian();

			if (iEdgeDate < iMaturityDate) lsEdgeDate.add (DAPAdjust (iEdgeDate, dap));
		}

		lsEdgeDate.add (iMaturityDate);

		return lsEdgeDate;
	}

	/**
	 * Generate a list of regular period edge dates forward from the start.
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param strPeriodTenor Period Tenor
	 * @param dap Inner Date Adjustment Parameters
	 * 
	 * @return List of Period Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> RegularEdgeDates (
		final int iStartDate,
		final int iEndDate,
		final java.lang.String strPeriodTenor,
		final org.drip.analytics.daycount.DateAdjustParams dap)
	{
		if (iStartDate >= iEndDate || null == strPeriodTenor || strPeriodTenor.isEmpty()) return null;

		java.util.List<java.lang.Integer> lsEdgeDate = new java.util.ArrayList<java.lang.Integer>();

		int iEdgeDate = iStartDate;

		org.drip.analytics.date.JulianDate dtEdge = new org.drip.analytics.date.JulianDate (iStartDate);

		while (iEdgeDate < iEndDate) {
			lsEdgeDate.add (DAPAdjust (iEdgeDate, dap));

			if (null == (dtEdge = dtEdge.addTenor (strPeriodTenor))) return null;

			iEdgeDate = dtEdge.julian();
		}

		lsEdgeDate.add (iEndDate);

		return lsEdgeDate;
	}

	/**
	 * Generate a list of the IMM period edge dates forward from the spot date.
	 * 
	 * @param dtSpot Spot Date
	 * @param iRollMonths Number of Months to Roll to the Next IMM Date
	 * @param strPeriodTenor Period Tenor
	 * @param strMaturityTenor Period Tenor
	 * @param dap Inner Date Adjustment Parameters
	 * 
	 * @return List of IMM Period Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> IMMEdgeDates (
		final org.drip.analytics.date.JulianDate dtSpot,
		final int iRollMonths,
		final java.lang.String strPeriodTenor,
		final java.lang.String strMaturityTenor,
		final org.drip.analytics.daycount.DateAdjustParams dap)
	{
		if (null == dtSpot) return null;

		try {
			return RegularEdgeDates (dtSpot.nextRatesFuturesIMM (iRollMonths), strPeriodTenor,
				strMaturityTenor, dap);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the List of Overnight Edge Dates between the specified dates, using the specified Calendar
	 * 
	 * @param dtStart Start Date
	 * @param dtEnd End Date
	 * @param strCalendar Calendar
	 * 
	 * @return List of Overnight Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> OvernightEdgeDates (
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.analytics.date.JulianDate dtEnd,
		final java.lang.String strCalendar)
	{
		if (null == dtStart || null == dtEnd) return null;

		org.drip.analytics.date.JulianDate dtEdge = dtStart;

		int iEndDate = dtEnd.julian();

		int iEdgeDate = dtEdge.julian();

		if (iEndDate <= iEdgeDate) return null;

		java.util.List<java.lang.Integer> lsOvernightEdgeDate = new java.util.ArrayList<java.lang.Integer>();

		while (iEdgeDate < iEndDate) {
			lsOvernightEdgeDate.add (iEdgeDate);

			if (null == (dtEdge = dtEdge.addBusDays (1, strCalendar))) return null;

			iEdgeDate = dtEdge.julian();
		}

		lsOvernightEdgeDate.add (iEndDate);

		return lsOvernightEdgeDate;
	}

	/**
	 * Generate a single Spanning Edge Pair between the specified dates, using the specified Calendar
	 * 
	 * @param dtStart Start Date
	 * @param dtEnd End Date
	 * 
	 * @return List Containing the Pair
	 */

	public static final java.util.List<java.lang.Integer> EdgePair (
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.analytics.date.JulianDate dtEnd)
	{
		if (null == dtStart || null == dtEnd) return null;

		int iEndDate = dtEnd.julian();

		int iStartDate = dtStart.julian();

		if (iEndDate <= iStartDate) return null;

		java.util.List<java.lang.Integer> lsOvernightEdgeDate = new java.util.ArrayList<java.lang.Integer>();

		lsOvernightEdgeDate.add (iStartDate);

		lsOvernightEdgeDate.add (iEndDate);

		return lsOvernightEdgeDate;
	}

	/**
	 * Construct a Reference Period using the Start/End Dates, Fixing DAP, Forward Label, and the Reference
	 * 	Period Arrears Type
	 * 
	 * @param dtStart Start Date
	 * @param dtEnd End Date
	 * @param forwardLabel Forward Label
	 * @param iReferencePeriodArrearsType Reference Period Arrears Type
	 * 
	 * @return The Reference Period
	 */

	public static final org.drip.analytics.cashflow.ReferenceIndexPeriod MakeReferencePeriod (
		final org.drip.analytics.date.JulianDate dtStart,
		final org.drip.analytics.date.JulianDate dtEnd,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final int iReferencePeriodArrearsType)
	{
		if (null == dtStart || null == dtEnd || null == forwardLabel) return null;

		java.lang.String strForwardTenor = forwardLabel.tenor();

		int iReferencePeriodStartDate = REFERENCE_PERIOD_IN_ARREARS == iReferencePeriodArrearsType ?
			dtStart.addTenor (strForwardTenor).julian() : dtStart.julian();

		int iReferencePeriodEndDate = REFERENCE_PERIOD_IN_ARREARS == iReferencePeriodArrearsType ?
			dtEnd.addTenor (strForwardTenor).julian() : dtEnd.julian();

		try {
			return new org.drip.analytics.cashflow.ReferenceIndexPeriod (iReferencePeriodStartDate,
				iReferencePeriodEndDate, forwardLabel);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a list of period edge dates forward from the start.
	 * 
	 * @param iEffective Effective Date
	 * @param iMaturity Maturity Date
	 * @param strTenor Period Tenor
	 * @param dap Inner Date Adjustment Parameters
	 * @param iPSEC Period Set Edge Customizer Setting
	 * 
	 * @return List of Period Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> ForwardEdgeDates (
		final int iEffective,
		final int iMaturity,
		final java.lang.String strTenor,
		final org.drip.analytics.daycount.DateAdjustParams dap,
		final int iPSEC)
	{
		return ForwardEdgeDates (new org.drip.analytics.date.JulianDate (iEffective), new
			org.drip.analytics.date.JulianDate (iMaturity), strTenor, dap, iPSEC);
	}

	/**
	 * Generate a list of period edge dates backward from the end.
	 * 
	 * @param iEffective Effective Date
	 * @param iMaturity Maturity Date
	 * @param strTenor Period Tenor
	 * @param dap Inner Date Adjustment Parameters
	 * @param iPSEC Period Set Edge Customizer Setting
	 * 
	 * @return List of Period Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> BackwardEdgeDates (
		final int iEffective,
		final int iMaturity,
		final java.lang.String strTenor,
		final org.drip.analytics.daycount.DateAdjustParams dap,
		final int iPSEC)
	{
		return BackwardEdgeDates (new org.drip.analytics.date.JulianDate (iEffective), new
			org.drip.analytics.date.JulianDate (iMaturity), strTenor, dap, iPSEC);
	}

	/**
	 * Generate a list of regular period edge dates forward from the start.
	 * 
	 * @param iEffective Effective Date
	 * @param strPeriodTenor Period Tenor
	 * @param strMaturityTenor Period Tenor
	 * @param dap Inner Date Adjustment Parameters
	 * 
	 * @return List of Period Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> RegularEdgeDates (
		final int iEffective,
		final java.lang.String strPeriodTenor,
		final java.lang.String strMaturityTenor,
		final org.drip.analytics.daycount.DateAdjustParams dap)
	{
		return RegularEdgeDates (new org.drip.analytics.date.JulianDate (iEffective), strPeriodTenor,
			strMaturityTenor, dap);
	}

	/**
	 * Generate the List of Overnight Edge Dates between the specified dates, using the specified Calendar
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param strCalendar Calendar
	 * 
	 * @return List of Overnight Edge Dates
	 */

	public static final java.util.List<java.lang.Integer> OvernightEdgeDates (
		final int iStartDate,
		final int iEndDate,
		final java.lang.String strCalendar)
	{
		return OvernightEdgeDates (new org.drip.analytics.date.JulianDate (iStartDate), new
			org.drip.analytics.date.JulianDate (iEndDate), strCalendar);
	}

	/**
	 * Construct a Reference Period using the Start/End Dates, Fixing DAP, Forward Label, and the Reference
	 * 	Period Arrears Type
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param forwardLabel Forward Label
	 * @param iReferencePeriodArrearsType Reference Period Arrears Type
	 * 
	 * @return The Reference Period
	 */

	public static final org.drip.analytics.cashflow.ReferenceIndexPeriod MakeReferencePeriod (
		final int iStartDate,
		final int iEndDate,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final int iReferencePeriodArrearsType)
	{
		return MakeReferencePeriod (new org.drip.analytics.date.JulianDate (iStartDate), new
			org.drip.analytics.date.JulianDate (iEndDate), forwardLabel, iReferencePeriodArrearsType);
	}

	/**
	 * Retrieve the List of Edge Dates across all Units
	 * 
	 * @param iUnitPeriodStartDate Unit Period Start Date
	 * @param iUnitPeriodEndDate Unit Period End Date
	 * @param strCalendar Unit Date Generation Calendar
	 * @param cubs Composable Unit Builder Setting
	 * 
	 * @return List of Edge Dates across all Units
	 */

	public static final java.util.List<java.lang.Integer> UnitDateEdges (
		final int iUnitPeriodStartDate,
		final int iUnitPeriodEndDate,
		final java.lang.String strCalendar,
		final org.drip.param.period.ComposableUnitBuilderSetting cubs)
	{
		if (null == cubs) return null;

		int iEdgeDateSequenceScheme = cubs.edgeDateSequenceScheme();

		if (EDGE_DATE_SEQUENCE_SINGLE == iEdgeDateSequenceScheme) {
			if (iUnitPeriodStartDate >= iUnitPeriodEndDate) return null;

			java.util.List<java.lang.Integer> lsEdgeDates = new java.util.ArrayList<java.lang.Integer>();

			lsEdgeDates.add (iUnitPeriodStartDate);

			lsEdgeDates.add (iUnitPeriodEndDate);

			return lsEdgeDates;
		}

		if (EDGE_DATE_SEQUENCE_REGULAR == iEdgeDateSequenceScheme)
			return RegularEdgeDates (iUnitPeriodStartDate, iUnitPeriodEndDate, cubs.tenor(), cubs.dapEdge());

		if (EDGE_DATE_SEQUENCE_OVERNIGHT == iEdgeDateSequenceScheme)
			return OvernightEdgeDates (iUnitPeriodStartDate, iUnitPeriodEndDate, strCalendar);

		return null;
	}

	/**
	 * Construct the List of Composable Fixed Units from the inputs
	 * 
	 * @param iUnitPeriodStartDate Unit Period Start Date
	 * @param iUnitPeriodEndDate Unit Period End Date
	 * @param ucas Unit Coupon/Accrual Setting
	 * @param cfus Composable Fixed Unit Setting
	 * 
	 * @return The List of Composable Floating Units
	 */

	public static final java.util.List<org.drip.analytics.cashflow.ComposableUnitPeriod> FixedUnits (
		final int iUnitPeriodStartDate,
		final int iUnitPeriodEndDate,
		final org.drip.param.period.UnitCouponAccrualSetting ucas,
		final org.drip.param.period.ComposableFixedUnitSetting cfus)
	{
		if (null == cfus) return null;

		java.util.List<java.lang.Integer> lsUnitEdgeDate = UnitDateEdges (iUnitPeriodStartDate,
			iUnitPeriodEndDate, ucas.calendar(), cfus);

		if (null == lsUnitEdgeDate) return null;

		int iNumDate = lsUnitEdgeDate.size();

		if (2 > iNumDate) return null;

		java.util.List<org.drip.analytics.cashflow.ComposableUnitPeriod> lsCUP = new
			java.util.ArrayList<org.drip.analytics.cashflow.ComposableUnitPeriod>();

		for (int i = 1; i < iNumDate; ++i) {
			try {
				lsCUP.add (new org.drip.analytics.cashflow.ComposableUnitFixedPeriod (lsUnitEdgeDate.get
					(i - 1), lsUnitEdgeDate.get (i), ucas, cfus));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return lsCUP;
	}

	/**
	 * Construct the List of Composable Floating Units from the inputs
	 * 
	 * @param iUnitPeriodStartDate Unit Period Start Date
	 * @param iUnitPeriodEndDate Unit Period End Date
	 * @param cfus Composable Floating Unit Setting
	 * 
	 * @return The List of Composable Floating Units
	 */

	public static final java.util.List<org.drip.analytics.cashflow.ComposableUnitPeriod> FloatingUnits (
		final int iUnitPeriodStartDate,
		final int iUnitPeriodEndDate,
		final org.drip.param.period.ComposableFloatingUnitSetting cfus)
	{
		if (null == cfus) return null;

		org.drip.state.identifier.ForwardLabel forwardLabel = cfus.forwardLabel();

		java.lang.String strCalendar = forwardLabel.floaterIndex().calendar();

		java.util.List<java.lang.Integer> lsUnitEdgeDate = UnitDateEdges (iUnitPeriodStartDate,
			iUnitPeriodEndDate, strCalendar, cfus);

		if (null == lsUnitEdgeDate) return null;

		int iNumDate = lsUnitEdgeDate.size();

		if (2 > iNumDate) return null;

		java.util.List<org.drip.analytics.cashflow.ComposableUnitPeriod> lsCUP = new
			java.util.ArrayList<org.drip.analytics.cashflow.ComposableUnitPeriod>();

		double dblSpread = cfus.spread();

		java.lang.String strUnitTenor = cfus.tenor();

		java.lang.String strForwardTenor = forwardLabel.tenor();

		int iReferencePeriodArrearsType = cfus.referencePeriodArrearsType();

		boolean bComposableForwardPeriodsMatch = cfus.tenor().equalsIgnoreCase (strForwardTenor);

		for (int i = 1; i < iNumDate; ++i) {
			int iUnitStartDate = lsUnitEdgeDate.get (i - 1);

			int iUnitEndDate = lsUnitEdgeDate.get (i);

			int iReferencePeriodEndDate = iUnitEndDate;

			int iReferencePeriodStartDate = bComposableForwardPeriodsMatch ? iUnitStartDate : new
				org.drip.analytics.date.JulianDate (iUnitEndDate).subtractTenorAndAdjust (strForwardTenor,
					strCalendar).julian();

			try {
				lsCUP.add (new org.drip.analytics.cashflow.ComposableUnitFloatingPeriod (iUnitStartDate,
					iUnitEndDate, strUnitTenor, MakeReferencePeriod (iReferencePeriodStartDate,
						iReferencePeriodEndDate, forwardLabel, iReferencePeriodArrearsType), dblSpread));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return lsCUP;
	}

	/**
	 * Construct the List of Composite Fixed Periods from the corresponding composable Fixed Period Units
	 * 
	 * @param lsCompositeEdgeDate The Composite Period Edge Dates
	 * @param cps Composite Period Setting Instance
	 * @param ucas Unit Coupon/Accrual Setting
	 * @param cfus Composable Fixed Unit Setting
	 * 
	 * @return List of Composite Fixed Periods
	 */

	public static final java.util.List<org.drip.analytics.cashflow.CompositePeriod> FixedCompositeUnit (
		final java.util.List<java.lang.Integer> lsCompositeEdgeDate,
		final org.drip.param.period.CompositePeriodSetting cps,
		final org.drip.param.period.UnitCouponAccrualSetting ucas,
		final org.drip.param.period.ComposableFixedUnitSetting cfus)
	{
		if (null == lsCompositeEdgeDate) return null;

		int iNumEdge = lsCompositeEdgeDate.size();
		
		if (2 > iNumEdge) return null;

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCFP = new
			java.util.ArrayList<org.drip.analytics.cashflow.CompositePeriod>();

		for (int i = 1; i < iNumEdge; ++i) {
			try {
				lsCFP.add (new org.drip.analytics.cashflow.CompositeFixedPeriod (cps, FixedUnits
					(lsCompositeEdgeDate.get (i - 1), lsCompositeEdgeDate.get (i), ucas, cfus)));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return lsCFP;
	}

	/**
	 * Construct the List of Composite Floating Period from the corresponding composable Floating Period Units
	 * 
	 * @param lsCompositeEdgeDate The Composite Period Edge Dates
	 * @param cps Composite Period Setting Instance
	 * @param cfus Composable Floating Unit Setting
	 * 
	 * @return List of Composite Floating Periods
	 */

	public static final java.util.List<org.drip.analytics.cashflow.CompositePeriod> FloatingCompositeUnit (
		final java.util.List<java.lang.Integer> lsCompositeEdgeDate,
		final org.drip.param.period.CompositePeriodSetting cps,
		final org.drip.param.period.ComposableFloatingUnitSetting cfus)
	{
		if (null == lsCompositeEdgeDate) return null;

		int iNumEdge = lsCompositeEdgeDate.size();

		if (2 > iNumEdge) return null;

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCFP = new
			java.util.ArrayList<org.drip.analytics.cashflow.CompositePeriod>();

		for (int i = 1; i < iNumEdge; ++i) {
			try {
				lsCFP.add (new org.drip.analytics.cashflow.CompositeFloatingPeriod (cps, FloatingUnits
					(lsCompositeEdgeDate.get (i - 1), lsCompositeEdgeDate.get (i), cfus)));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return lsCFP;
	}
}
