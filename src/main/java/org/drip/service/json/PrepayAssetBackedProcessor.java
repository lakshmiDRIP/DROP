
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
 * <i>PrepayAssetBackedProcessor</i> Sets Up and Executes a JSON Based In/Out Product Pre-payable Asset
 * Backed Loan Processor.
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

public class PrepayAssetBackedProcessor {

	/**
	 * JSON Based in/out Pre-Payable Asset Backed Loan Secular Metrics Thunker
	 * 
	 * @param jsonParameter JSON Pre-Payable Asset Backed Loan Request Parameters
	 * 
	 * @return JSON Pre-Payable Asset Backed Loan Secular Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject SecularMetrics (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		java.lang.String strBondName = org.drip.json.parser.Converter.StringEntry (jsonParameter, "Name");

		java.lang.String strCurrency = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"Currency");

		java.lang.String strDayCount = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"DayCount");

		org.drip.analytics.date.JulianDate dtEffective = org.drip.json.parser.Converter.DateEntry
			(jsonParameter, "EffectiveDate");

		if (null == dtEffective) return null;

		int iEffectiveDate = dtEffective.julian();

		org.drip.analytics.date.JulianDate dtSettle = org.drip.json.parser.Converter.DateEntry
			(jsonParameter, "SettleDate");

		if (null == dtSettle) return null;

		int iSettleDate = dtSettle.julian();

		int iNumPayment = -1;
		int iPayFrequency = -1;
		double dblCleanPrice = 1.;
		double dblCPR = java.lang.Double.NaN;
		double dblCouponRate = java.lang.Double.NaN;
		double dblBondNotional = java.lang.Double.NaN;
		double dblServiceFeeRate = java.lang.Double.NaN;
		double dblFixedMonthlyAmount = java.lang.Double.NaN;
		double dblBeginPrincipalFactor = java.lang.Double.NaN;

		try {
			dblCPR = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "CPR");

			iNumPayment = org.drip.json.parser.Converter.IntegerEntry (jsonParameter, "NumPayment");

			dblCouponRate = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "CouponRate");

			iPayFrequency = org.drip.json.parser.Converter.IntegerEntry (jsonParameter, "PayFrequency");

			dblBondNotional = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "BondNotional");

			dblServiceFeeRate = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "ServiceFeeRate");

			dblFixedMonthlyAmount = org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
				"FixedMonthlyAmount");

			dblBeginPrincipalFactor = org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
				"BeginPrincipalFactor");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.product.definition.Bond fpmb = org.drip.product.creator.ConstantPaymentBondBuilder.Prepay
			(strBondName, dtEffective, strCurrency, iNumPayment, strDayCount, iPayFrequency, dblCouponRate,
				dblServiceFeeRate, dblCPR, dblFixedMonthlyAmount, dblBondNotional);

		if (null == fpmb || fpmb.maturityDate().julian() <= iSettleDate) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		org.drip.json.simple.JSONArray jsonCouponFlowArray = new org.drip.json.simple.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : fpmb.couponPeriods()) {
			org.drip.json.simple.JSONObject jsonCouponFlow = new org.drip.json.simple.JSONObject();

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = cp.couponMetrics (iEffectiveDate,
				null);

			if (null == cpcm) return null;

			double dblPeriodCouponRate = cpcm.rate();

			jsonCouponFlow.put ("FeeRate", dblServiceFeeRate);

			jsonCouponFlow.put ("CouponRate", dblPeriodCouponRate);

			jsonCouponFlow.put ("PrincipalFactor", dblBeginPrincipalFactor);

			try {
				double dblEndPrincipalFactor = fpmb.notional (cp.endDate());

				double dblYieldDF = org.drip.analytics.support.Helper.Yield2DF (iPayFrequency, dblCouponRate,
					org.drip.analytics.daycount.Convention.YearFraction (dtEffective.julian(), cp.endDate(),
						"30/360", false, null, strCurrency));

				jsonCouponFlow.put ("StartDate", new org.drip.analytics.date.JulianDate
					(cp.startDate()).toString());

				jsonCouponFlow.put ("EndDate", new org.drip.analytics.date.JulianDate
					(cp.endDate()).toString());

				jsonCouponFlow.put ("PayDate", new org.drip.analytics.date.JulianDate
					(cp.payDate()).toString());

				double dblCouponDCF = cp.couponDCF();

				jsonCouponFlow.put ("AccrualDays", dblCouponDCF * 365.);

				jsonCouponFlow.put ("AccrualFraction", dblCouponDCF);

				double dblCouponAmount = dblBeginPrincipalFactor * dblPeriodCouponRate * dblCouponDCF *
					dblBondNotional;
				double dblFeeAmount = dblBeginPrincipalFactor * dblServiceFeeRate * dblCouponDCF *
					dblBondNotional;
				double dblPrincipalAmount = (dblBeginPrincipalFactor - dblEndPrincipalFactor) *
					dblBondNotional;
				double dblTotalAmount = dblPrincipalAmount + dblCouponAmount;
				dblBeginPrincipalFactor = dblEndPrincipalFactor;
				double dblBeginNotional = dblBeginPrincipalFactor * dblBondNotional;

				jsonCouponFlow.put ("BeginPrincipal", dblBeginNotional);

				jsonCouponFlow.put ("Notional", dblBeginNotional);

				jsonCouponFlow.put ("EndPrincipal", dblEndPrincipalFactor * dblBondNotional);

				jsonCouponFlow.put ("PrincipalAmount", dblPrincipalAmount);

				jsonCouponFlow.put ("CouponAmount", dblCouponAmount);

				jsonCouponFlow.put ("TotalAmount", dblTotalAmount);

				jsonCouponFlow.put ("DiscountFactor", dblYieldDF);

				jsonCouponFlow.put ("FeeAmount", dblFeeAmount);

				jsonCouponFlow.put ("SurvivalFactor", 1.);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCouponFlowArray.add (jsonCouponFlow);
		}

		jsonResponse.put ("CouponFlow", jsonCouponFlowArray);

		jsonResponse.put ("CleanPrice", 100. * dblCleanPrice);

		jsonResponse.put ("Face", dblBondNotional);

		org.drip.analytics.date.JulianDate dtPreviousCouponDate = fpmb.previousCouponDate (dtSettle);

		if (null != dtPreviousCouponDate) {
			jsonResponse.put ("AccrualDays", dtPreviousCouponDate.julian() - iSettleDate);

			jsonResponse.put ("PreviousCouponDate", dtPreviousCouponDate.toString());
		}

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iSettleDate);

		try {
			double dblAccruedAmount = fpmb.accrued (iSettleDate, null) * dblBondNotional;

			double dblCurrentPrincipal = fpmb.notional (dtPreviousCouponDate.julian()) * dblBondNotional;

			double dblRisk = fpmb.yield01FromPrice (valParams, null, null, dblCleanPrice);

			jsonResponse.put ("Accrued", dblAccruedAmount);

			jsonResponse.put ("Convexity", fpmb.convexityFromPrice (valParams, null, null, dblCleanPrice));

			jsonResponse.put ("CurrentPrincipal", dblCurrentPrincipal);

			jsonResponse.put ("DV01", dblRisk * dblBondNotional);

			jsonResponse.put ("ModifiedDuration", fpmb.modifiedDurationFromPrice (valParams, null, null,
				dblCleanPrice));

			jsonResponse.put ("Notional", dblBondNotional);

			jsonResponse.put ("NPV", dblCurrentPrincipal + dblAccruedAmount);

			jsonResponse.put ("Risk", dblRisk);

			jsonResponse.put ("SettleDate", dtSettle.toString());

			jsonResponse.put ("Total", dblCurrentPrincipal + dblAccruedAmount);

			jsonResponse.put ("Yield", fpmb.yieldFromPrice (valParams, null, null, dblCleanPrice));

			jsonResponse.put ("Yield01", dblRisk);

			jsonResponse.put ("Y01", dblRisk);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.json.LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == dcFunding) return jsonResponse;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState (dcFunding)) return jsonResponse;

		try {
			jsonResponse.put ("DiscountedPrice", fpmb.priceFromDiscountMargin (valParams, csqc, null, 0.));
		} catch (java.lang.Exception e) {
		}

		if (!csqc.setCreditState (org.drip.service.json.LatentStateProcessor.CreditCurve (jsonParameter,
			dcFunding)))
			return jsonResponse;

		try {
			jsonResponse.put ("DiscountedCreditPrice", fpmb.priceFromCreditBasis (valParams, csqc, null,
				0.));
		} catch (java.lang.Exception e) {
		}

		return jsonResponse;
	}

}
