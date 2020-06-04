
package org.drip.service.json;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>TreasuryBondProcessor</i> Sets Up and Executes a JSON Based In/Out Processing Service for Treasury
 * Bonds.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/json/README.md">JSON Based Valuation Request Service</a></li>
 *  </ul>
 * <br><br>
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

	@SuppressWarnings ("unchecked") static final org.drip.service.representation.JSONObject SecularMetrics (
		final org.drip.service.representation.JSONObject jsonParameter)
	{
		java.lang.String strTreasuryCode = org.drip.service.jsonparser.Converter.StringEntry (jsonParameter,
			"TreasuryCode");

		org.drip.analytics.date.JulianDate dtEffective = org.drip.service.jsonparser.Converter.DateEntry
			(jsonParameter, "EffectiveDate");

		if (null == dtEffective) return null;

		int iEffectiveDate = dtEffective.julian();

		org.drip.analytics.date.JulianDate dtMaturity = org.drip.service.jsonparser.Converter.DateEntry
			(jsonParameter, "MaturityDate");

		if (null == dtMaturity) return null;

		org.drip.analytics.date.JulianDate dtSettle = org.drip.service.jsonparser.Converter.DateEntry
			(jsonParameter, "SettleDate");

		if (null == dtSettle) return null;

		int iSettleDate = dtSettle.julian();

		double dblYield = java.lang.Double.NaN;
		double dblCoupon = java.lang.Double.NaN;
		double dblNotional = java.lang.Double.NaN;
		double dblCleanPrice = java.lang.Double.NaN;

		try {
			dblCoupon = org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter, "Coupon");

			dblNotional = org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter, "Notional");

			dblCleanPrice = org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter, "CleanPrice");
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

		org.drip.service.representation.JSONObject jsonResponse = new org.drip.service.representation.JSONObject();

		org.drip.service.representation.JSONArray jsonCouponFlowArray = new org.drip.service.representation.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : tsy.couponPeriods()) {
			org.drip.service.representation.JSONObject jsonCouponFlow = new org.drip.service.representation.JSONObject();

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
