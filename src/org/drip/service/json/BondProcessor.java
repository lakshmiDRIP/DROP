
package org.drip.service.json;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * BondProcessor Sets Up and Executes a JSON Based In/Out Bond Valuation Processor.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondProcessor {

	/**
	 * JSON Based in/out Bond Secular Metrics Thunker
	 * 
	 * @param jsonParameter JSON Bond Request Parameters
	 * 
	 * @return JSON Bond Secular Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject SecularMetrics (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.json.LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == dcFunding) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState (dcFunding)) return null;

		double dblCleanPrice = java.lang.Double.NaN;
		org.drip.product.credit.BondComponent bond = null;

		try {
			if (null == (bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
				(org.drip.json.parser.Converter.StringEntry (jsonParameter, "BondName"),
					dcFunding.currency(), "", org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
						"BondCoupon"), org.drip.json.parser.Converter.IntegerEntry (jsonParameter,
							"BondFrequency"), org.drip.json.parser.Converter.StringEntry (jsonParameter,
								"BondDayCount"), org.drip.json.parser.Converter.DateEntry (jsonParameter,
									"BondEffectiveDate"), org.drip.json.parser.Converter.DateEntry
										(jsonParameter, "BondMaturityDate"), null, null)))
				return null;

			dblCleanPrice = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "BondCleanPrice");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(dcFunding.epoch().julian());

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("BondName", bond.name());

		jsonResponse.put ("BondEffectiveDate", bond.effectiveDate().toString());

		jsonResponse.put ("BondMaturityDate", bond.maturityDate().toString());

		jsonResponse.put ("BondFirstCouponDate", bond.firstCouponDate().toString());

		jsonResponse.put ("BondCleanPrice", dblCleanPrice);

		try {
			double dblYield01 = bond.yield01FromPrice (valParams, csqc, null, dblCleanPrice);

			jsonResponse.put ("BondYield", bond.yieldFromPrice (valParams, csqc, null, dblCleanPrice));

			jsonResponse.put ("BondMacaulayDuration", bond.macaulayDurationFromPrice (valParams, csqc, null,
				dblCleanPrice));

			jsonResponse.put ("BondModifiedDuration", 10000. * bond.modifiedDurationFromPrice (valParams,
				csqc, null, dblCleanPrice));

			jsonResponse.put ("BondYield01", 10000. * dblYield01);

			jsonResponse.put ("BondDV01", 10000. * dblYield01);

			jsonResponse.put ("BondConvexity", 1000000. * bond.convexityFromPrice (valParams, csqc, null,
				dblCleanPrice));

			jsonResponse.put ("BondBasis", 10000. * bond.bondBasisFromPrice (valParams, csqc, null,
				dblCleanPrice));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return jsonResponse;
	}

	/**
	 * JSON Based in/out Bond Curve Metrics Thunker
	 * 
	 * @param jsonParameter JSON Bond Request Parameters
	 * 
	 * @return JSON Bond Curve Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject CurveMetrics (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.json.LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == dcFunding) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState (dcFunding) || !csqc.setGovvieState
			(org.drip.service.json.LatentStateProcessor.TreasuryCurve (jsonParameter)))
			return null;

		double dblCleanPrice = java.lang.Double.NaN;
		org.drip.product.credit.BondComponent bond = null;

		org.drip.analytics.date.JulianDate dtMaturity = org.drip.json.parser.Converter.DateEntry
			(jsonParameter, "BondMaturityDate");

		try {
			if (null == (bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
				(org.drip.json.parser.Converter.StringEntry (jsonParameter, "BondName"),
					dcFunding.currency(), "", org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
						"BondCoupon"), org.drip.json.parser.Converter.IntegerEntry (jsonParameter,
							"BondFrequency"), org.drip.json.parser.Converter.StringEntry (jsonParameter,
								"BondDayCount"), org.drip.json.parser.Converter.DateEntry (jsonParameter,
									"BondEffectiveDate"), dtMaturity, null, null)))
				return null;

			dblCleanPrice = org.drip.json.parser.Converter.DoubleEntry (jsonParameter, "BondCleanPrice");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		int iSpotDate = dcFunding.epoch().julian();

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iSpotDate);

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("BondName", bond.name());

		jsonResponse.put ("BondEffectiveDate", bond.effectiveDate().toString());

		jsonResponse.put ("BondMaturityDate", dtMaturity.toString());

		jsonResponse.put ("BondFirstCouponDate", bond.firstCouponDate().toString());

		jsonResponse.put ("BondCleanPrice", dblCleanPrice);

		try {
			jsonResponse.put ("BondASW", bond.aswFromPrice (valParams, csqc, null, dblCleanPrice));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse.put ("BondGSpread", bond.gSpreadFromPrice (valParams, csqc, null, dblCleanPrice));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse.put ("BondISpread", bond.iSpreadFromPrice (valParams, csqc, null, dblCleanPrice));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse.put ("BondOAS", bond.oasFromPrice (valParams, csqc, null, dblCleanPrice));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse.put ("BondTreasurySpread", bond.tsySpreadFromPrice (valParams, csqc, null,
				dblCleanPrice));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		jsonResponse.put ("BondTreasuryBenchmark", org.drip.analytics.support.Helper.BaseTsyBmk
			(iSpotDate, dtMaturity.julian()));

		try {
			jsonResponse.put ("BondZSpread", bond.zSpreadFromPrice (valParams, csqc, null, dblCleanPrice));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return jsonResponse;
	}

	/**
	 * JSON Based in/out Bond Curve Cash Flow Thunker
	 * 
	 * @param jsonParameter JSON Bond Cash Flow Parameters
	 * 
	 * @return JSON Bond Cash Flow Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject CashFlows (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.json.LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == dcFunding) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState (dcFunding)) return null;

		org.drip.state.credit.CreditCurve cc = org.drip.service.json.LatentStateProcessor.CreditCurve
			(jsonParameter, dcFunding);

		csqc.setCreditState (cc);

		double dblValueNotional = 1.;
		org.drip.product.credit.BondComponent bond = null;

		org.drip.analytics.date.JulianDate dtMaturity = org.drip.json.parser.Converter.DateEntry
			(jsonParameter, "BondMaturityDate");

		try {
			if (null == (bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
				(org.drip.json.parser.Converter.StringEntry (jsonParameter, "BondName"),
					dcFunding.currency(), "", org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
						"BondCoupon"), org.drip.json.parser.Converter.IntegerEntry (jsonParameter,
							"BondFrequency"), org.drip.json.parser.Converter.StringEntry (jsonParameter,
								"BondDayCount"), org.drip.json.parser.Converter.DateEntry (jsonParameter,
									"BondEffectiveDate"), dtMaturity, null, null)))
				return null;

			dblValueNotional = org.drip.json.parser.Converter.DoubleEntry (jsonParameter,
				"BondValueNotional");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("BondName", bond.name());

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCP = bond.couponPeriods();

		if (null == lsCP || 0 == lsCP.size()) return null;

		org.drip.json.simple.JSONArray jsonCashFlowArray = new org.drip.json.simple.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : lsCP) {
			if (null == cp) return null;

			org.drip.json.simple.JSONObject jsonCashFlow = new org.drip.json.simple.JSONObject();

			jsonCashFlow.put ("StartDate", new org.drip.analytics.date.JulianDate
				(cp.startDate()).toString());

			jsonCashFlow.put ("EndDate", new org.drip.analytics.date.JulianDate (cp.endDate()).toString());

			jsonCashFlow.put ("PayDate", new org.drip.analytics.date.JulianDate (cp.payDate()).toString());

			try {
				double dblCouponRate = cp.periods().get (0).baseRate (csqc);

				jsonCashFlow.put ("FixingDate", new org.drip.analytics.date.JulianDate
					(cp.fxFixingDate()).toString());

				jsonCashFlow.put ("CouponDCF", cp.couponDCF());

				jsonCashFlow.put ("CouponRate", dblCouponRate);

				if (null != cc) jsonCashFlow.put ("SurvivalFactor", cc.survival (cp.payDate()));

				jsonCashFlow.put ("PayDiscountFactor", cp.df (csqc));

				jsonCashFlow.put ("CouponAmount", dblCouponRate * dblValueNotional);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCashFlow.put ("BaseNotional", cp.baseNotional() * dblValueNotional);

			jsonCashFlowArray.add (jsonCashFlow);
		}

		jsonResponse.put ("CashFlow", jsonCashFlowArray);

		return jsonResponse;
	}
}
