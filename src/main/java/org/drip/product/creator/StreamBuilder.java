
package org.drip.product.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * StreamBuilder contains Utility Functions to construct Fixed, Floating, and Mixed Streams.
 *
 * @author Lakshmi Krishnamurthy
 */

public class StreamBuilder {

	/**
	 * Generate the Fixed Stream Off of the specified Parameters
	 * 
	 * @param iStreamStartDate The Stream Start Date
	 * @param iStreamEndDate The Stream End Date
	 * @param iFirstCouponDate The First Coupon Date
	 * @param iPenultimateCouponDate The Penultimate Coupon Date
	 * @param iFreq Coupon Frequency
	 * @param dblCoupon Coupon Rate
	 * @param strCouponDC Coupon Day Count
	 * @param strAccrualDC Accrual Day Count
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapStreamEnd Stream End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual End Date Adjustment Parameters
	 * @param strCurrency Coupon/Pay Currency
	 * @param creditLabel The Stream Credit Label
	 * 
	 * @return The Fixed Stream
	 */

	public static final java.util.List<org.drip.analytics.cashflow.CompositePeriod>
		FirstPenultimateDateFixedStream (
			final int iStreamStartDate,
			final int iStreamEndDate,
			final int iFirstCouponDate,
			final int iPenultimateCouponDate,
			final int iFreq,
			final double dblCoupon,
			final java.lang.String strCouponDC,
			final java.lang.String strAccrualDC,
			final org.drip.analytics.daycount.DateAdjustParams dapPay,
			final org.drip.analytics.daycount.DateAdjustParams dapStreamEnd,
			final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
			final java.lang.String strCurrency,
			final org.drip.state.identifier.EntityCDSLabel creditLabel)
	{
		boolean bCouponEOMAdj = null == strCouponDC ? false : strCouponDC.toUpperCase().contains ("EOM");

		int iCouponDCIndex = null == strCouponDC ? -1 : strCouponDC.indexOf (" NON");

		java.lang.String strCouponDCAdj = -1 != iCouponDCIndex ? strCouponDC.substring (0, iCouponDCIndex) :
			strCouponDC;

		boolean bAccrualEOMAdj = null == strAccrualDC ? false : strAccrualDC.toUpperCase().contains ("EOM");

		int iAccrualDCIndex = null == strAccrualDC ? -1 : strAccrualDC.indexOf (" NON");

		java.lang.String strAccrualDCAdj = -1 != iAccrualDCIndex ? strAccrualDC.substring (0,
			iAccrualDCIndex) : strAccrualDC;

		org.drip.analytics.date.JulianDate dtFirstCoupon = new org.drip.analytics.date.JulianDate
			(iFirstCouponDate);

		org.drip.analytics.date.JulianDate dtPenultimateCoupon = new org.drip.analytics.date.JulianDate
			(iPenultimateCouponDate);

		java.lang.String strTenor = (12 / iFreq) + "M";

		try {
			java.util.List<java.lang.Integer> lsStreamEdgeDate =
				org.drip.analytics.support.CompositePeriodBuilder.BackwardEdgeDates (
					dtFirstCoupon,
					dtPenultimateCoupon,
					strTenor,
					dapAccrualEnd,
					org.drip.analytics.support.CompositePeriodBuilder.LONG_STUB
				);

			if (null == lsStreamEdgeDate) return null;

			lsStreamEdgeDate.add (0, iStreamStartDate);

			lsStreamEdgeDate.add (null == dapStreamEnd ? iStreamEndDate : dapStreamEnd.roll
				(iStreamEndDate));

			return org.drip.analytics.support.CompositePeriodBuilder.FixedCompositeUnit (
				lsStreamEdgeDate,
				new org.drip.param.period.CompositePeriodSetting (
					iFreq,
					strTenor,
					strCurrency,
					dapPay,
					1.,
					null,
					null,
					null,
					creditLabel
				),
				new org.drip.param.period.UnitCouponAccrualSetting (
					iFreq,
					strCouponDCAdj,
					bCouponEOMAdj,
					strAccrualDCAdj,
					bAccrualEOMAdj,
					strCurrency,
					false,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
				),
				new org.drip.param.period.ComposableFixedUnitSetting (
					strTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
					null,
					dblCoupon,
					0.,
					strCurrency
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Float Stream off of the specified Parameters
	 * 
	 * @param iStreamStartDate The Stream Start Date
	 * @param iStreamEndDate The Stream End Date
	 * @param iFirstCouponDate The First Coupon Date
	 * @param iPenultimateCouponDate The Penultimate Coupon Date
	 * @param iFreq Coupon Frequency
	 * @param dblSpread The Spread
	 * @param dapPay Pay Date Adjustment Parameter
	 * @param dapStreamEnd Stream End Date Adjustment Parameter
	 * @param dapAccrualEnd Accrual End Date Adjustment Parameter
	 * @param floaterLabel Floater Label
	 * @param creditLabel Credit Label
	 * 
	 * @return The Float Stream
	 */

	public static final java.util.List<org.drip.analytics.cashflow.CompositePeriod>
		FirstPenultimateDateFloatStream (
			final int iStreamStartDate,
			final int iStreamEndDate,
			final int iFirstCouponDate,
			final int iPenultimateCouponDate,
			final int iFreq,
			final double dblSpread,
			final org.drip.analytics.daycount.DateAdjustParams dapPay,
			final org.drip.analytics.daycount.DateAdjustParams dapStreamEnd,
			final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
			final org.drip.state.identifier.FloaterLabel floaterLabel,
			final org.drip.state.identifier.EntityCDSLabel creditLabel)
	{
		if (null == floaterLabel) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = new org.drip.analytics.date.JulianDate
			(iFirstCouponDate);

		org.drip.analytics.date.JulianDate dtPenultimateCoupon = new org.drip.analytics.date.JulianDate
			(iPenultimateCouponDate);

		java.lang.String strTenor = (12 / iFreq) + "M";

		try {
			java.util.List<java.lang.Integer> lsStreamEdgeDate = 
				org.drip.analytics.support.CompositePeriodBuilder.BackwardEdgeDates (
					dtFirstCoupon,
					dtPenultimateCoupon,
					strTenor,
					dapAccrualEnd,
					org.drip.analytics.support.CompositePeriodBuilder.LONG_STUB
				);

			if (null == lsStreamEdgeDate) return null;

			lsStreamEdgeDate.add (0, iStreamStartDate);

			lsStreamEdgeDate.add (null == dapStreamEnd ? iStreamEndDate : dapStreamEnd.roll
				(iStreamEndDate));

			return org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit (
				lsStreamEdgeDate,
				new org.drip.param.period.CompositePeriodSetting (
					iFreq,
					strTenor,
					floaterLabel.currency(),
					dapPay,
					1.,
					null,
					null,
					null,
					creditLabel
				),
				new org.drip.param.period.ComposableFloatingUnitSetting (
					strTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
					null,
					floaterLabel,
					org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
					dblSpread
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate Mixed Fixed-Float Stream off of the specified Parameters
	 * 
	 * @param iStreamStartDate The Stream Start Date
	 * @param iFixedStreamEndDate The Fixed Stream End Date
	 * @param iFixedFirstCouponDate The Fixed Stream First Coupon Date
	 * @param iFixedPenultimateCouponDate The Fixed Penultimate Coupon Date
	 * @param iFixedFreq Fixed Coupon Frequency
	 * @param dblFixedCoupon Fixed Coupon Rate
	 * @param strFixedCouponDC Fixed Coupon Day Count
	 * @param strFixedAccrualDC Fixed Accrual Day Count
	 * @param iFloatStreamEndDate The Float Stream End Date
	 * @param iFloatFirstCouponDate The Float First Coupon Date
	 * @param iFloatPenultimateCouponDate The Float Penultimate Coupon Date
	 * @param iFloatFreq Float Coupon Frequency
	 * @param dblFloatSpread The Float Spread
	 * @param dapPay Pay Date Adjustment Parameter
	 * @param dapStreamEnd Stream End Date Adjustment Parameter
	 * @param dapAccrualEnd Accrual End Date Adjustment Parameter
	 * @param forwardLabel Forward Label
	 * @param creditLabel Credit Label
	 * 
	 * @return The Mixed Stream
	 */

	public static final java.util.List<org.drip.analytics.cashflow.CompositePeriod>
		FirstPenultimateDateFixedFloat (
			final int iStreamStartDate,
			final int iFixedStreamEndDate,
			final int iFixedFirstCouponDate,
			final int iFixedPenultimateCouponDate,
			final int iFixedFreq,
			final double dblFixedCoupon,
			final java.lang.String strFixedCouponDC,
			final java.lang.String strFixedAccrualDC,
			final int iFloatStreamEndDate,
			final int iFloatFirstCouponDate,
			final int iFloatPenultimateCouponDate,
			final int iFloatFreq,
			final double dblFloatSpread,
			final org.drip.analytics.daycount.DateAdjustParams dapPay,
			final org.drip.analytics.daycount.DateAdjustParams dapStreamEnd,
			final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
			final org.drip.state.identifier.ForwardLabel forwardLabel,
			final org.drip.state.identifier.EntityCDSLabel creditLabel)
	{
		if (null == forwardLabel) return null;

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsMixedPeriod =
			FirstPenultimateDateFixedStream (
				iStreamStartDate,
				iFixedStreamEndDate,
				iFixedFirstCouponDate,
				iFixedPenultimateCouponDate,
				iFixedFreq,
				dblFixedCoupon,
				strFixedCouponDC,
				strFixedAccrualDC,
				dapPay,
				dapStreamEnd,
				dapAccrualEnd,
				forwardLabel.currency(),
				creditLabel
			);

		if (null == lsMixedPeriod) return null;

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsFloatPeriod =
			FirstPenultimateDateFloatStream (
				iFixedStreamEndDate,
				iFloatStreamEndDate,
				iFloatFirstCouponDate,
				iFloatPenultimateCouponDate,
				iFloatFreq,
				dblFloatSpread,
				dapPay,
				dapStreamEnd,
				dapAccrualEnd,
				forwardLabel,
				creditLabel
			);

		if (null == lsFloatPeriod) return null;

		lsMixedPeriod.addAll (lsFloatPeriod);

		return lsMixedPeriod;
	}
}
