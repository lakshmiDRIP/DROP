
package org.drip.service.json;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for fixed income analysts and developers -
 * 		http://www.credit-trader.org/Begin.html
 * 
 *  DRIP is a free, full featured, fixed income rates, credit, and FX analytics library with a focus towards
 *  	pricing/valuation, risk, and market making.
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
 * TreasuryBondProcessor Sets Up and Executes a JSON Based In/Out Processing Service for Treasury Bonds.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryBondProcessor {

	/**
	 * JSON Based in/out Treasury Bond Secular Metrics Thunker
	 * 
	 * @param jsonParameter JSON Treasury Bond Request Parameters
	 * 
	 * @return JSON Treasury Bond Secular Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject SecularMetrics (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		java.lang.String strTreasuryCode = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"TreasuryCode");

		org.drip.analytics.date.JulianDate dtEffective = org.drip.json.parser.Converter.DateEntry
			(jsonParameter, "EffectiveDate");

		if (null == dtEffective) return null;

		int iEffectiveDate = dtEffective.julian();

		org.drip.analytics.date.JulianDate dtMaturity = org.drip.json.parser.Converter.DateEntry
			(jsonParameter, "MaturityDate");

		if (null == dtMaturity) return null;

		org.drip.analytics.date.JulianDate dtSettle = org.drip.json.parser.Converter.DateEntry
			(jsonParameter, "SettleDate");

		if (null == dtSettle) return null;

		int iSettleDate = dtSettle.julian();

		double dblYield = java.lang.Double.NaN;
		double dblCoupon = java.lang.Double.NaN;
		double dblNotional = java.lang.Double.NaN;
		double dblCleanPrice = java.lang.Double.NaN;

		try {
			dblCoupon = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "Coupon");

			dblNotional = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "Notional");

			dblCleanPrice = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "CleanPrice");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting (strTreasuryCode);

		if (null == ts) return null;

		int iFrequency = ts.frequency();

		java.lang.String strDayCount = ts.dayCount();

		org.drip.product.definition.Bond tsy = org.drip.product.creator.BondBuilder.Treasury (ts.code(),
			dtEffective, dtMaturity, ts.currency(), dblCoupon, iFrequency, strDayCount);

		if (null == tsy || tsy.maturityDate().julian() <= iSettleDate) return null;

		java.lang.String strCurrency = tsy.payCurrency();

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iSettleDate);

		try {
			dblYield = tsy.yield01FromPrice (valParams, null, null, dblCleanPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		org.drip.json.simple.JSONArray jsonCouponFlowArray = new org.drip.json.simple.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : tsy.couponPeriods()) {
			org.drip.json.simple.JSONObject jsonCouponFlow = new org.drip.json.simple.JSONObject();

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = cp.couponMetrics (iEffectiveDate,
				null);

			if (null == cpcm) return null;

			double dblPeriodCouponRate = cpcm.rate();

			jsonCouponFlow.put ("CouponRate", dblPeriodCouponRate);

			try {
				int iPeriodStartDate = cp.startDate();

				int iPeriodEndDate = cp.endDate();

				double dblYieldDF = org.drip.analytics.support.Helper.Yield2DF (iFrequency, dblYield,
					org.drip.analytics.daycount.Convention.YearFraction (iEffectiveDate, iPeriodEndDate,
						strDayCount, false, null, strCurrency));

				jsonCouponFlow.put ("StartDate", new org.drip.analytics.date.JulianDate
					(iPeriodStartDate).toString());

				jsonCouponFlow.put ("EndDate", new org.drip.analytics.date.JulianDate
					(iPeriodEndDate).toString());

				jsonCouponFlow.put ("PayDate", new org.drip.analytics.date.JulianDate
					(cp.payDate()).toString());

				double dblCouponDCF = cp.couponDCF();

				jsonCouponFlow.put ("AccrualDays", org.drip.analytics.daycount.Convention.DaysAccrued
					(iPeriodStartDate, iPeriodEndDate, strDayCount, false, null, strCurrency));

				jsonCouponFlow.put ("AccrualFraction", dblCouponDCF);

				double dblCouponAmount = dblPeriodCouponRate * dblCouponDCF * dblNotional;

				jsonCouponFlow.put ("CouponAmount", dblCouponAmount);

				jsonCouponFlow.put ("YieldDF", dblYieldDF);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCouponFlowArray.add (jsonCouponFlow);
		}

		jsonResponse.put ("CouponFlow", jsonCouponFlowArray);

		jsonResponse.put ("CleanPrice", 100. * dblCleanPrice);

		jsonResponse.put ("Face", dblNotional);

		org.drip.analytics.date.JulianDate dtPreviousCouponDate = tsy.previousCouponDate (dtSettle);

		if (null != dtPreviousCouponDate) {
			jsonResponse.put ("AccrualDays", iSettleDate - dtPreviousCouponDate.julian());

			jsonResponse.put ("PreviousCouponDate", dtPreviousCouponDate.toString());
		}

		try {
			double dblAccruedAmount = tsy.accrued (iSettleDate, null) * dblNotional;

			double dblCurrentPrincipal = tsy.notional (dtPreviousCouponDate.julian()) * dblNotional;

			double dblRisk = tsy.yield01FromPrice (valParams, null, null, dblCleanPrice);

			jsonResponse.put ("Accrued", dblAccruedAmount);

			jsonResponse.put ("Convexity", tsy.convexityFromPrice (valParams, null, null, dblCleanPrice));

			jsonResponse.put ("CurrentPrincipal", dblCurrentPrincipal);

			jsonResponse.put ("DV01", dblRisk * dblNotional);

			jsonResponse.put ("ModifiedDuration", tsy.modifiedDurationFromPrice (valParams, null, null,
				dblCleanPrice));

			jsonResponse.put ("Notional", dblNotional);

			jsonResponse.put ("NPV", dblCurrentPrincipal + dblAccruedAmount);

			jsonResponse.put ("Risk", dblRisk);

			jsonResponse.put ("SettleDate", dtSettle.toString());

			jsonResponse.put ("Total", dblCurrentPrincipal + dblAccruedAmount);

			jsonResponse.put ("Yield", tsy.yieldFromPrice (valParams, null, null, dblCleanPrice));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return jsonResponse;
	}
}
