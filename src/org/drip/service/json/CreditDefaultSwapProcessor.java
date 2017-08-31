
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
 * CreditDefaultSwapProcessor Sets Up and Executes a JSON Based In/Out Credit Default Swap Valuation
 *  Processor.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditDefaultSwapProcessor {

	/**
	 * JSON Based in/out Credit Default Swap Curve Metrics Thunker
	 * 
	 * @param jsonParameter JSON Credit Default Swap Request Parameters
	 * 
	 * @return JSON Credit Default Swap Curve Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject CurveMetrics (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.json.LatentStateProcessor.FundingCurve (jsonParameter);

		org.drip.state.credit.CreditCurve ccSurvivalRecovery =
			org.drip.service.json.LatentStateProcessor.CreditCurve (jsonParameter, dcFunding);

		if (null == ccSurvivalRecovery) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState (dcFunding) || !csqc.setCreditState (ccSurvivalRecovery)) return null;

		org.drip.analytics.date.JulianDate dtSpot = dcFunding.epoch();

		org.drip.product.definition.CreditDefaultSwap cds = null;

		try {
			cds = org.drip.service.template.OTCInstrumentBuilder.CDS (dtSpot,
				org.drip.json.parser.Converter.StringEntry (jsonParameter, "CDSMaturity"),
					org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "CDSCoupon"),
						dcFunding.currency(), ((org.drip.state.identifier.CreditLabel)
							(ccSurvivalRecovery.label())).referenceEntity());
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (null == cds) return null;

		java.util.Map<java.lang.String, java.lang.Double> mapResult = cds.value
			(org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), null, csqc, null);

		if (null == mapResult) return null;

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapResult.entrySet())
			jsonResponse.put (me.getKey(), me.getValue());

		org.drip.json.simple.JSONArray jsonCouponFlowArray = new org.drip.json.simple.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : cds.couponPeriods()) {
			org.drip.json.simple.JSONObject jsonCouponFlow = new org.drip.json.simple.JSONObject();

			try {
				jsonCouponFlow.put ("StartDate", new org.drip.analytics.date.JulianDate
					(cp.startDate()).toString());

				jsonCouponFlow.put ("EndDate", new org.drip.analytics.date.JulianDate
					(cp.endDate()).toString());

				jsonCouponFlow.put ("PayDate", new org.drip.analytics.date.JulianDate
					(cp.payDate()).toString());

				jsonCouponFlow.put ("CouponDCF", cp.couponDCF());

				jsonCouponFlow.put ("PayDiscountFactor", cp.df (csqc));

				jsonCouponFlow.put ("SurvivalProbability", cp.survival (csqc));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCouponFlow.put ("BaseNotional", cp.baseNotional());

			jsonCouponFlow.put ("Tenor", cp.tenor());

			jsonCouponFlow.put ("FundingLabel", cp.fundingLabel().fullyQualifiedName());

			jsonCouponFlow.put ("CreditLabel", cp.creditLabel().fullyQualifiedName());

			jsonCouponFlow.put ("ReferenceRate", cp.couponMetrics (dtSpot.julian(), csqc).rate());

			jsonCouponFlowArray.add (jsonCouponFlow);
		}

		jsonResponse.put ("CouponFlow", jsonCouponFlowArray);

		org.drip.json.simple.JSONArray jsonLossFlowArray = new org.drip.json.simple.JSONArray();

		for (org.drip.analytics.cashflow.LossQuadratureMetrics lqm : cds.lossFlow (dtSpot, csqc)) {
			org.drip.json.simple.JSONObject jsonLossFlow = new org.drip.json.simple.JSONObject();

			try {
				jsonLossFlow.put ("StartDate", new org.drip.analytics.date.JulianDate
					(lqm.startDate()).toString());

				jsonLossFlow.put ("EndDate", new org.drip.analytics.date.JulianDate
					(lqm.endDate()).toString());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonLossFlow.put ("StartSurvival", lqm.startSurvival());

			jsonLossFlow.put ("EndSurvival", lqm.endSurvival());

			jsonLossFlow.put ("EffectiveNotional", lqm.effectiveNotional());

			jsonLossFlow.put ("EffectiveRecovery", lqm.effectiveRecovery());

			jsonLossFlow.put ("EffectiveAccrual", lqm.accrualDCF());

			jsonLossFlow.put ("EffectiveDF", lqm.effectiveDF());

			jsonLossFlowArray.add (jsonLossFlow);
		}

		jsonResponse.put ("LossFlow", jsonLossFlowArray);

		return jsonResponse;
	}
}
