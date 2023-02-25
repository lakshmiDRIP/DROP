
package org.drip.service.json;

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
 * <i>BondProcessor</i> Sets Up and Executes a JSON Based In/Out Bond Valuation Processor.
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

public class BondProcessor {

	/**
	 * JSON Based in/out Bond Secular Metrics Thunker
	 * 
	 * @param jsonParameter JSON Bond Request Parameters
	 * 
	 * @return JSON Bond Secular Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.service.representation.JSONObject SecularMetrics (
		final org.drip.service.representation.JSONObject jsonParameter)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.json.LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == dcFunding) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState (dcFunding)) return null;

		double dblCleanPrice = java.lang.Double.NaN;
		org.drip.product.credit.BondComponent bond = null;

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(dcFunding.epoch().julian());

		try {
			if (null == (bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
				(org.drip.service.jsonparser.Converter.StringEntry (jsonParameter, "BondName"),
					dcFunding.currency(), "", org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter,
						"BondCoupon"), org.drip.service.jsonparser.Converter.IntegerEntry (jsonParameter,
							"BondFrequency"), org.drip.service.jsonparser.Converter.StringEntry (jsonParameter,
								"BondDayCount"), org.drip.service.jsonparser.Converter.DateEntry (jsonParameter,
									"BondEffectiveDate"), org.drip.service.jsonparser.Converter.DateEntry
										(jsonParameter, "BondMaturityDate"), null, null)))
				return null;

			if (jsonParameter.containsKey ("BondCleanPrice"))
				dblCleanPrice = org.drip.service.jsonparser.Converter.DoubleEntry (
					jsonParameter,
					"BondCleanPrice"
				);
			else if (jsonParameter.containsKey("BondYield"))
				dblCleanPrice = bond.priceFromYield (
					valParams,
					csqc,
					null,
					org.drip.service.jsonparser.Converter.DoubleEntry (
						jsonParameter,
						"BondYield"
					)
				);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.service.representation.JSONObject jsonResponse = new org.drip.service.representation.JSONObject();

		jsonResponse.put ("BondName", bond.name());

		jsonResponse.put ("BondEffectiveDate", bond.effectiveDate().toString());

		jsonResponse.put ("BondMaturityDate", bond.maturityDate().toString());

		jsonResponse.put ("BondFirstCouponDate", bond.firstCouponDate().toString());

		jsonResponse.put ("BondCleanPrice", dblCleanPrice);

		try {
			double accrued = bond.accrued (valParams.valueDate(), csqc);

			jsonResponse.put ("BondAccrued", accrued);

			double dblYield01 = bond.yield01FromPrice (valParams, csqc, null, dblCleanPrice);

			jsonResponse.put ("BondYield", bond.yieldFromPrice (valParams, csqc, null, dblCleanPrice));

			jsonResponse.put ("BondMacaulayDuration", bond.macaulayDurationFromPrice (valParams, csqc, null,
				dblCleanPrice));

			jsonResponse.put ("BondModifiedDuration", 10000. * bond.modifiedDurationFromPrice (valParams,
				csqc, null, dblCleanPrice));

			jsonResponse.put ("BondConvexity", 1000000. * bond.convexityFromPrice (valParams, csqc, null,
				dblCleanPrice));

			jsonResponse.put ("BondBasis", 10000. * bond.bondBasisFromPrice (valParams, csqc, null,
				dblCleanPrice));

			jsonResponse.put ("BondDirtyPrice", dblCleanPrice + accrued);

			jsonResponse.put ("BondYield01", 10000. * dblYield01);

			jsonResponse.put ("BondDV01", 10000. * dblYield01);
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

	@SuppressWarnings ("unchecked") static final org.drip.service.representation.JSONObject CurveMetrics (
		final org.drip.service.representation.JSONObject jsonParameter)
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

		int iSpotDate = dcFunding.epoch().julian();

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iSpotDate);

		org.drip.analytics.date.JulianDate dtMaturity = org.drip.service.jsonparser.Converter.DateEntry
			(jsonParameter, "BondMaturityDate");

		try {
			if (null == (bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
				(org.drip.service.jsonparser.Converter.StringEntry (jsonParameter, "BondName"),
					dcFunding.currency(), "", org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter,
						"BondCoupon"), org.drip.service.jsonparser.Converter.IntegerEntry (jsonParameter,
							"BondFrequency"), org.drip.service.jsonparser.Converter.StringEntry (jsonParameter,
								"BondDayCount"), org.drip.service.jsonparser.Converter.DateEntry (jsonParameter,
									"BondEffectiveDate"), dtMaturity, null, null)))
				return null;


			if (jsonParameter.containsKey ("BondCleanPrice"))
				dblCleanPrice = org.drip.service.jsonparser.Converter.DoubleEntry (
					jsonParameter,
					"BondCleanPrice"
				);
			else if (jsonParameter.containsKey("BondYield"))
				dblCleanPrice = bond.priceFromYield (
					valParams,
					csqc,
					null,
					org.drip.service.jsonparser.Converter.DoubleEntry (
						jsonParameter,
						"BondYield"
					)
				);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.service.representation.JSONObject jsonResponse = new org.drip.service.representation.JSONObject();

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

	@SuppressWarnings ("unchecked") static final org.drip.service.representation.JSONObject CashFlows (
		final org.drip.service.representation.JSONObject jsonParameter)
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

		org.drip.analytics.date.JulianDate dtMaturity = org.drip.service.jsonparser.Converter.DateEntry
			(jsonParameter, "BondMaturityDate");

		try {
			if (null == (bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
				(org.drip.service.jsonparser.Converter.StringEntry (jsonParameter, "BondName"),
					dcFunding.currency(), "", org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter,
						"BondCoupon"), org.drip.service.jsonparser.Converter.IntegerEntry (jsonParameter,
							"BondFrequency"), org.drip.service.jsonparser.Converter.StringEntry (jsonParameter,
								"BondDayCount"), org.drip.service.jsonparser.Converter.DateEntry (jsonParameter,
									"BondEffectiveDate"), dtMaturity, null, null)))
				return null;

			dblValueNotional = org.drip.service.jsonparser.Converter.DoubleEntry (jsonParameter,
				"BondValueNotional");
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.service.representation.JSONObject jsonResponse = new org.drip.service.representation.JSONObject();

		jsonResponse.put ("BondName", bond.name());

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCP = bond.couponPeriods();

		if (null == lsCP || 0 == lsCP.size()) return null;

		org.drip.service.representation.JSONArray jsonCashFlowArray = new org.drip.service.representation.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : lsCP) {
			if (null == cp) return null;

			org.drip.service.representation.JSONObject jsonCashFlow = new org.drip.service.representation.JSONObject();

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
