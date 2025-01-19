
package org.drip.service.json;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.output.CompositePeriodCouponMetrics;
import org.drip.analytics.support.Helper;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.ConstantPaymentBondBuilder;
import org.drip.product.definition.Bond;
import org.drip.service.jsonparser.Converter;
import org.drip.service.representation.JSONArray;
import org.drip.service.representation.JSONObject;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>FixedAssetBackedProcessor</i> Sets Up and Executes a JSON Based In/Out Product Constant Payment Asset
 * 	Backed Loan Processor. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>JSON Based in/out Constant Payment Asset Backed Loan Secular Metrics Thunker</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/json/README.md">JSON Based Valuation Request Service</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedAssetBackedProcessor
{

	/**
	 * JSON Based in/out Constant Payment Asset Backed Loan Secular Metrics Thunker
	 * 
	 * @param jsonParameter JSON Constant Payment Asset Backed Loan Request Parameters
	 * 
	 * @return JSON Constant Payment Asset Backed Loan Secular Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final JSONObject SecularMetrics (
		final JSONObject jsonParameter)
	{
		String bondName = Converter.StringEntry (jsonParameter, "Name");

		String currency = Converter.StringEntry (jsonParameter, "Currency");

		String dayCount = Converter.StringEntry (jsonParameter, "DayCount");

		JulianDate effectiveDate = Converter.DateEntry (jsonParameter, "EffectiveDate");

		if (null == effectiveDate) {
			return null;
		}

		int effectiveDateInteger = effectiveDate.julian();

		JulianDate settleDate = Converter.DateEntry (jsonParameter, "SettleDate");

		if (null == settleDate) {
			return null;
		}

		int settleDateJulian = settleDate.julian();

		int payFrequency = -1;
		int paymentCount = -1;
		double cleanPrice = 1.;
		double couponRate = Double.NaN;
		double bondNotional = Double.NaN;
		double serviceFeeRate = Double.NaN;
		double fixedMonthlyAmount = Double.NaN;
		double beginPrincipalFactor = Double.NaN;

		try {
			paymentCount = Converter.IntegerEntry (jsonParameter, "NumPayment");

			couponRate = Converter.DoubleEntry (jsonParameter, "CouponRate");

			payFrequency = Converter.IntegerEntry (jsonParameter, "PayFrequency");

			bondNotional = Converter.DoubleEntry (jsonParameter, "BondNotional");

			serviceFeeRate = Converter.DoubleEntry (jsonParameter, "ServiceFeeRate");

			fixedMonthlyAmount = Converter.DoubleEntry (jsonParameter, "FixedMonthlyAmount");

			beginPrincipalFactor = Converter.DoubleEntry (jsonParameter, "BeginPrincipalFactor");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		Bond bond = ConstantPaymentBondBuilder.Standard (
			bondName,
			effectiveDate,
			currency,
			paymentCount,
			dayCount,
			payFrequency,
			couponRate,
			serviceFeeRate,
			fixedMonthlyAmount,
			bondNotional
		);

		if (null == bond || bond.maturityDate().julian() <= settleDateJulian) {
			return null;
		}

		JSONObject jsonResponse = new JSONObject();

		JSONArray jsonCouponFlowArray = new JSONArray();

		for (CompositePeriod compositePeriod : bond.couponPeriods()) {
			JSONObject jsonCouponFlow = new JSONObject();

			CompositePeriodCouponMetrics compositePeriodCouponMetrics = compositePeriod.couponMetrics (
				effectiveDateInteger,
				null
			);

			if (null == compositePeriodCouponMetrics) {
				return null;
			}

			double periodCouponRate = compositePeriodCouponMetrics.rate();

			jsonCouponFlow.put ("FeeRate", serviceFeeRate);

			jsonCouponFlow.put ("CouponRate", periodCouponRate);

			jsonCouponFlow.put ("PrincipalFactor", beginPrincipalFactor);

			try {
				double endPrincipalFactor = bond.notional (compositePeriod.endDate());

				double yieldDF = Helper.Yield2DF (
					payFrequency,
					couponRate,
					Convention.YearFraction (
						effectiveDate.julian(),
						compositePeriod.endDate(),
						"30/360",
						false,
						null,
						currency
					)
				);

				jsonCouponFlow.put ("StartDate", new JulianDate (compositePeriod.startDate()).toString());

				jsonCouponFlow.put ("EndDate", new JulianDate (compositePeriod.endDate()).toString());

				jsonCouponFlow.put ("PayDate", new JulianDate (compositePeriod.payDate()).toString());

				double couponDCF = compositePeriod.couponDCF();

				jsonCouponFlow.put ("AccrualDays", couponDCF * 365.);

				jsonCouponFlow.put ("AccrualFraction", couponDCF);

				double couponAmount = beginPrincipalFactor * periodCouponRate * couponDCF * bondNotional;
				double feeAmount = beginPrincipalFactor * serviceFeeRate * couponDCF * bondNotional;
				double principalAmount = (beginPrincipalFactor - endPrincipalFactor) * bondNotional;
				double totalAmount = principalAmount + couponAmount;
				beginPrincipalFactor = endPrincipalFactor;
				double beginNotional = beginPrincipalFactor * bondNotional;

				jsonCouponFlow.put ("BeginPrincipal", beginNotional);

				jsonCouponFlow.put ("Notional", beginNotional);

				jsonCouponFlow.put ("EndPrincipal", endPrincipalFactor * bondNotional);

				jsonCouponFlow.put ("PrincipalAmount", principalAmount);

				jsonCouponFlow.put ("CouponAmount", couponAmount);

				jsonCouponFlow.put ("TotalAmount", totalAmount);

				jsonCouponFlow.put ("DiscountFactor", yieldDF);

				jsonCouponFlow.put ("FeeAmount", feeAmount);

				jsonCouponFlow.put ("SurvivalFactor", 1.);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCouponFlowArray.add (jsonCouponFlow);
		}

		jsonResponse.put ("CouponFlow", jsonCouponFlowArray);

		jsonResponse.put ("CleanPrice", 100. * cleanPrice);

		jsonResponse.put ("Face", bondNotional);

		JulianDate previousCouponDate = bond.previousCouponDate (settleDate);

		if (null != previousCouponDate) {
			jsonResponse.put ("AccrualDays", previousCouponDate.julian() - settleDateJulian);

			jsonResponse.put ("PreviousCouponDate", previousCouponDate.toString());
		}

		ValuationParams valuationParams = ValuationParams.Spot (settleDateJulian);

		try {
			double accruedAmount = bond.accrued (settleDateJulian, null) * bondNotional;

			double currentPrincipal = bond.notional (previousCouponDate.julian()) * bondNotional;

			double risk = bond.yield01FromPrice (valuationParams, null, null, cleanPrice);

			jsonResponse.put ("Accrued", accruedAmount);

			jsonResponse.put (
				"Convexity",
				bond.convexityFromPrice (valuationParams, null, null, cleanPrice)
			);

			jsonResponse.put ("CurrentPrincipal", currentPrincipal);

			jsonResponse.put ("DV01", risk * bondNotional);

			jsonResponse.put (
				"ModifiedDuration",
				bond.modifiedDurationFromPrice (valuationParams, null, null, cleanPrice)
			);

			jsonResponse.put ("Notional", bondNotional);

			jsonResponse.put ("NPV", currentPrincipal + accruedAmount);

			jsonResponse.put ("Risk", risk);

			jsonResponse.put ("SettleDate", settleDate.toString());

			jsonResponse.put ("Total", currentPrincipal + accruedAmount);

			jsonResponse.put ("Yield", bond.yieldFromPrice (valuationParams, null, null, cleanPrice));

			jsonResponse.put ("Yield01", risk);

			jsonResponse.put ("Y01", risk);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		MergedDiscountForwardCurve fundingDiscountCurve = LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == fundingDiscountCurve) {
			return jsonResponse;
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!curveSurfaceQuoteContainer.setFundingState (fundingDiscountCurve)) {
			return jsonResponse;
		}

		try {
			jsonResponse.put (
				"DiscountedPrice",
				bond.priceFromDiscountMargin (valuationParams, curveSurfaceQuoteContainer, null, 0.)
			);
		} catch (Exception e) {
		}

		if (!curveSurfaceQuoteContainer.setCreditState (
			LatentStateProcessor.CreditCurve (jsonParameter, fundingDiscountCurve)
		))
		{
			return jsonResponse;
		}

		try {
			jsonResponse.put (
				"DiscountedCreditPrice",
				bond.priceFromCreditBasis (valuationParams, curveSurfaceQuoteContainer, null, 0.)
			);
		} catch (Exception e) {
		}

		return jsonResponse;
	}
}
