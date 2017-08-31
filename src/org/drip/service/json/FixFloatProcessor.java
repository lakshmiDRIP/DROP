
package org.drip.service.json;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * FixFloatProcessor Sets Up and Executes a JSON Based In/Out Fix Float Swap Valuation Processor.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatProcessor {

	/**
	 * JSON Based in/out Funding Fix Float Curve Metrics Thunker
	 * 
	 * @param jsonParameter JSON Funding Fix Float Request Parameters
	 * 
	 * @return JSON Funding Fix Float Curve Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject CurveMetrics (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.json.LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == dcFunding) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState (dcFunding)) return null;

		org.drip.analytics.date.JulianDate dtSpot = dcFunding.epoch();

		org.drip.product.rates.FixFloatComponent irs = null;

		try {
			irs = org.drip.service.template.OTCInstrumentBuilder.FixFloatStandard (dtSpot,
				dcFunding.currency(), "ALL", org.drip.json.parser.Converter.StringEntry (jsonParameter,
					"FixFloatMaturity"), "MAIN", org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
						"FixFloatCoupon"));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (null == irs) return null;

		java.util.Map<java.lang.String, java.lang.Double> mapResult = irs.value
			(org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), null, csqc, null);

		if (null == mapResult) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapResult.entrySet())
			jsonResponse.put (me.getKey(), me.getValue());

		org.drip.json.simple.JSONArray jsonFixedCashFlowArray = new org.drip.json.simple.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : irs.referenceStream().cashFlowPeriod()) {
			org.drip.json.simple.JSONObject jsonCashFlow = new org.drip.json.simple.JSONObject();

			try {
				jsonCashFlow.put ("StartDate", new org.drip.analytics.date.JulianDate
					(cp.startDate()).toString());

				jsonCashFlow.put ("EndDate", new org.drip.analytics.date.JulianDate
					(cp.endDate()).toString());

				jsonCashFlow.put ("PayDate", new org.drip.analytics.date.JulianDate
					(cp.payDate()).toString());

				jsonCashFlow.put ("FixingDate", new org.drip.analytics.date.JulianDate
					(cp.fxFixingDate()).toString());

				jsonCashFlow.put ("CouponDCF", cp.couponDCF());

				jsonCashFlow.put ("PayDiscountFactor", cp.df (csqc));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCashFlow.put ("BaseNotional", cp.baseNotional());

			jsonCashFlow.put ("Tenor", cp.tenor());

			jsonCashFlow.put ("FundingLabel", cp.fundingLabel().fullyQualifiedName());

			jsonCashFlow.put ("ReferenceRate", cp.couponMetrics (dtSpot.julian(), csqc).rate());

			jsonFixedCashFlowArray.add (jsonCashFlow);
		}

		jsonResponse.put ("FixedCashFlow", jsonFixedCashFlowArray);

		org.drip.json.simple.JSONArray jsonFloatingCashFlowArray = new org.drip.json.simple.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : irs.derivedStream().cashFlowPeriod()) {
			org.drip.json.simple.JSONObject jsonCashFlow = new org.drip.json.simple.JSONObject();

			try {
				jsonCashFlow.put ("StartDate", new org.drip.analytics.date.JulianDate
					(cp.startDate()).toString());

				jsonCashFlow.put ("EndDate", new org.drip.analytics.date.JulianDate
					(cp.endDate()).toString());

				jsonCashFlow.put ("PayDate", new org.drip.analytics.date.JulianDate
					(cp.payDate()).toString());

				jsonCashFlow.put ("FixingDate", new org.drip.analytics.date.JulianDate
					(cp.fxFixingDate()).toString());

				jsonCashFlow.put ("CouponDCF", cp.couponDCF());

				jsonCashFlow.put ("PayDiscountFactor", cp.df (csqc));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCashFlow.put ("BaseNotional", cp.baseNotional());

			jsonCashFlow.put ("Tenor", cp.tenor());

			jsonCashFlow.put ("FundingLabel", cp.fundingLabel().fullyQualifiedName());

			jsonCashFlow.put ("ForwardLabel", cp.forwardLabel().fullyQualifiedName());

			jsonCashFlow.put ("ReferenceRate", cp.couponMetrics (dtSpot.julian(), csqc).rate());

			jsonFloatingCashFlowArray.add (jsonCashFlow);
		}

		jsonResponse.put ("FloatingCashFlow", jsonFloatingCashFlowArray);

		return jsonResponse;
	}
}
