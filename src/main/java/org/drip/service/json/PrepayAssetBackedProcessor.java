
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
 * <i>PrepayAssetBackedProcessor</i> Sets Up and Executes a JSON Based In/Out Product Pre-payable Asset
 * 	Backed Loan Processor. It provides the following Functions:
 * 
 * <ul>
 * 		<li>JSON Based in/out Pre-Payable Asset Backed Loan Secular Metrics Thunker</li>
 * </ul>
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

public class PrepayAssetBackedProcessor
{

	/**
	 * JSON Based in/out Pre-Payable Asset Backed Loan Secular Metrics Thunker
	 * 
	 * @param jsonParameter JSON Pre-Payable Asset Backed Loan Request Parameters
	 * 
	 * @return JSON Pre-Payable Asset Backed Loan Secular Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final JSONObject SecularMetrics (
		final JSONObject jsonParameter)
	{
		String currency = Converter.StringEntry (jsonParameter, "Currency");

		JulianDate effectiveDate = Converter.DateEntry (jsonParameter, "EffectiveDate");

		if (null == effectiveDate) {
			return null;
		}

		JulianDate settleDate = Converter.DateEntry (jsonParameter, "SettleDate");

		if (null == settleDate) {
			return null;
		}

		int julianSettleDate = settleDate.julian();

		int payFrequency = -1;
		int paymentCount = -1;
		double cleanPrice = 1.;
		double cpr = Double.NaN;
		double couponRate = Double.NaN;
		double bondNotional = Double.NaN;
		double serviceFeeRate = Double.NaN;
		double fixedMonthlyAmount = Double.NaN;
		double beginPrincipalFactor = Double.NaN;

		try {
			cpr = Converter.DoubleEntry (jsonParameter, "CPR");

			couponRate = Converter.DoubleEntry (jsonParameter, "CouponRate");

			paymentCount = Converter.IntegerEntry (jsonParameter, "NumPayment");

			bondNotional = Converter.DoubleEntry (jsonParameter, "BondNotional");

			payFrequency = Converter.IntegerEntry (jsonParameter, "PayFrequency");

			serviceFeeRate = Converter.DoubleEntry (jsonParameter, "ServiceFeeRate");

			fixedMonthlyAmount = Converter.DoubleEntry (jsonParameter, "FixedMonthlyAmount");

			beginPrincipalFactor = Converter.DoubleEntry (jsonParameter, "BeginPrincipalFactor");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		Bond constantPaymentBond = ConstantPaymentBondBuilder.Prepay (
			Converter.StringEntry (jsonParameter, "Name"),
			effectiveDate,
			currency,
			paymentCount,
			Converter.StringEntry (jsonParameter, "DayCount"),
			payFrequency,
			couponRate,
			serviceFeeRate,
			cpr,
			fixedMonthlyAmount,
			bondNotional
		);

		if (null == constantPaymentBond || constantPaymentBond.maturityDate().julian() <= julianSettleDate) {
			return null;
		}

		JSONObject responseJSON = new JSONObject();

		JSONArray couponFlowJSONArray = new JSONArray();

		for (CompositePeriod compositePeriod : constantPaymentBond.couponPeriods()) {
			JSONObject couponFlowJSON = new JSONObject();

			CompositePeriodCouponMetrics compositePeriodCouponMetrics = compositePeriod.couponMetrics (
				effectiveDate.julian(),
				null
			);

			if (null == compositePeriodCouponMetrics) {
				return null;
			}

			double periodCouponRate = compositePeriodCouponMetrics.rate();

			couponFlowJSON.put ("PrincipalFactor", beginPrincipalFactor);

			couponFlowJSON.put ("CouponRate", periodCouponRate);

			couponFlowJSON.put ("FeeRate", serviceFeeRate);

			try {
				double endPrincipalFactor = constantPaymentBond.notional (compositePeriod.endDate());

				couponFlowJSON.put ("EndDate", new JulianDate (compositePeriod.endDate()).toString());

				couponFlowJSON.put ("PayDate", new JulianDate (compositePeriod.payDate()).toString());

				couponFlowJSON.put ("StartDate", new JulianDate (compositePeriod.startDate()).toString());

				double couponDCF = compositePeriod.couponDCF();

				couponFlowJSON.put ("AccrualDays", couponDCF * 365.);

				couponFlowJSON.put ("AccrualFraction", couponDCF);

				double couponAmount = beginPrincipalFactor * periodCouponRate * couponDCF * bondNotional;
				double principalAmount = (beginPrincipalFactor - endPrincipalFactor) * bondNotional;
				beginPrincipalFactor = endPrincipalFactor;
				double beginNotional = beginPrincipalFactor * bondNotional;

				couponFlowJSON.put ("SurvivalFactor", 1.);

				couponFlowJSON.put ("Notional", beginNotional);

				couponFlowJSON.put ("CouponAmount", couponAmount);

				couponFlowJSON.put ("BeginPrincipal", beginNotional);

				couponFlowJSON.put ("PrincipalAmount", principalAmount);

				couponFlowJSON.put ("TotalAmount", principalAmount + couponAmount);

				couponFlowJSON.put ("EndPrincipal", endPrincipalFactor * bondNotional);

				couponFlowJSON.put (
					"FeeAmount",
					beginPrincipalFactor * serviceFeeRate * couponDCF * bondNotional
				);

				couponFlowJSON.put (
					"DiscountFactor",
					Helper.Yield2DF (
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
					)
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			couponFlowJSONArray.add (couponFlowJSON);
		}

		responseJSON.put ("CouponFlow", couponFlowJSONArray);

		responseJSON.put ("CleanPrice", 100. * cleanPrice);

		responseJSON.put ("Face", bondNotional);

		JulianDate previousCouponDate = constantPaymentBond.previousCouponDate (settleDate);

		int julianPreviousCouponDate = previousCouponDate.julian();

		if (null != previousCouponDate) {
			responseJSON.put ("PreviousCouponDate", previousCouponDate.toString());

			responseJSON.put ("AccrualDays", julianPreviousCouponDate - julianSettleDate);
		}

		ValuationParams valuationParams = ValuationParams.Spot (julianSettleDate);

		try {
			double accruedAmount = constantPaymentBond.accrued (julianSettleDate, null) * bondNotional;

			double risk = constantPaymentBond.yield01FromPrice (valuationParams, null, null, cleanPrice);

			double currentPrincipal = constantPaymentBond.notional (julianPreviousCouponDate) * bondNotional;

			responseJSON.put ("Y01", risk);

			responseJSON.put ("Risk", risk);

			responseJSON.put ("Yield01", risk);

			responseJSON.put ("Accrued", accruedAmount);

			responseJSON.put ("Notional", bondNotional);

			responseJSON.put ("DV01", risk * bondNotional);

			responseJSON.put ("SettleDate", settleDate.toString());

			responseJSON.put ("CurrentPrincipal", currentPrincipal);

			responseJSON.put ("NPV", currentPrincipal + accruedAmount);

			responseJSON.put ("Total", currentPrincipal + accruedAmount);

			responseJSON.put (
				"Yield",
				constantPaymentBond.yieldFromPrice (valuationParams, null, null, cleanPrice)
			);

			responseJSON.put (
				"Convexity",
				constantPaymentBond.convexityFromPrice (valuationParams, null, null, cleanPrice)
			);

			responseJSON.put (
				"ModifiedDuration",
				constantPaymentBond.modifiedDurationFromPrice (valuationParams, null, null, cleanPrice)
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		MergedDiscountForwardCurve fundingDiscountCurve = LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == fundingDiscountCurve) {
			return responseJSON;
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!curveSurfaceQuoteContainer.setFundingState (fundingDiscountCurve)) {
			return responseJSON;
		}

		try {
			responseJSON.put (
				"DiscountedPrice",
				constantPaymentBond.priceFromDiscountMargin (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					0.
				)
			);
		} catch (Exception e) {
		}

		if (!curveSurfaceQuoteContainer.setCreditState (
			LatentStateProcessor.CreditCurve (jsonParameter, fundingDiscountCurve)
		))
		{
			return responseJSON;
		}

		try {
			responseJSON.put (
				"DiscountedCreditPrice",
				constantPaymentBond.priceFromCreditBasis (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					0.
				)
			);
		} catch (Exception e) {
		}

		return responseJSON;
	}
}
