
package org.drip.product.creator;

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
 * <i>StreamBuilder</i> contains Utility Functions to construct Fixed, Floating, and Mixed Streams.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/README.md">Streams and Products Construction Utilities</a></li>
 *  </ul>
 * <br><br>
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
