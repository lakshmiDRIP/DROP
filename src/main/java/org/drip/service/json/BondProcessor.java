
package org.drip.service.json;

import java.util.List;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.Helper;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.jsonparser.Converter;
import org.drip.service.representation.JSONArray;
import org.drip.service.representation.JSONObject;
import org.drip.state.credit.CreditCurve;
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
 * <i>BondProcessor</i> Sets Up and Executes a JSON Based In/Out Bond Valuation Processor. It provides the
 * 	following Functionality:
 *
 *  <ul>
 * 		<li>JSON Based in/out Bond Secular Metrics Thunker</li>
 * 		<li>JSON Based in/out Bond Curve Metrics Thunker</li>
 * 		<li>JSON Based in/out Bond Curve Cash Flow Thunker</li>
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

public class BondProcessor
{

	/**
	 * JSON Based in/out Bond Secular Metrics Thunker
	 * 
	 * @param jsonParameter JSON Bond Request Parameters
	 * 
	 * @return JSON Bond Secular Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final JSONObject SecularMetrics (
		final JSONObject jsonParameter)
	{
		MergedDiscountForwardCurve fundingDiscountCurve = LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == fundingDiscountCurve) {
			return null;
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!curveSurfaceQuoteContainer.setFundingState (fundingDiscountCurve)) {
			return null;
		}

		double cleanPrice = Double.NaN;
		BondComponent bondComponent = null;

		ValuationParams valuationParams = ValuationParams.Spot (fundingDiscountCurve.epoch().julian());

		try {
			if (null == (
				bondComponent = BondBuilder.CreateSimpleFixed (
					Converter.StringEntry (jsonParameter, "BondName"),
					fundingDiscountCurve.currency(),
					"",
					Converter.DoubleEntry (jsonParameter, "BondCoupon"),
					Converter.IntegerEntry (jsonParameter, "BondFrequency"),
					Converter.StringEntry (jsonParameter, "BondDayCount"),
					Converter.DateEntry (jsonParameter, "BondEffectiveDate"),
					Converter.DateEntry (jsonParameter, "BondMaturityDate"),
					null,
					null
				)
			))
			{
				return null;
			}

			if (jsonParameter.containsKey ("BondCleanPrice")) {
				cleanPrice = Converter.DoubleEntry (jsonParameter, "BondCleanPrice");
			} else if (jsonParameter.containsKey("BondYield")) {
				cleanPrice = bondComponent.priceFromYield (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					Converter.DoubleEntry (jsonParameter, "BondYield")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		JSONObject jsonResponse = new JSONObject();

		jsonResponse.put ("BondName", bondComponent.name());

		jsonResponse.put ("BondEffectiveDate", bondComponent.effectiveDate().toString());

		jsonResponse.put ("BondMaturityDate", bondComponent.maturityDate().toString());

		jsonResponse.put ("BondFirstCouponDate", bondComponent.firstCouponDate().toString());

		jsonResponse.put ("BondCleanPrice", cleanPrice);

		try {
			double accrued = bondComponent.accrued (valuationParams.valueDate(), curveSurfaceQuoteContainer);

			jsonResponse.put ("BondAccrued", accrued);

			double yield01 = bondComponent.yield01FromPrice (
				valuationParams,
				curveSurfaceQuoteContainer,
				null,
				cleanPrice
			);

			jsonResponse.put (
				"BondYield",
				bondComponent.yieldFromPrice (valuationParams, curveSurfaceQuoteContainer, null, cleanPrice)
			);

			jsonResponse.put (
				"BondMacaulayDuration",
				bondComponent.macaulayDurationFromPrice (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					cleanPrice
				)
			);

			jsonResponse.put (
				"BondModifiedDuration",
				10000. * bondComponent.modifiedDurationFromPrice (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					cleanPrice
				)
			);

			jsonResponse.put (
				"BondConvexity",
				1000000. * bondComponent.convexityFromPrice (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					cleanPrice
				)
			);

			jsonResponse.put (
				"BondBasis",
				10000. * bondComponent.bondBasisFromPrice (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					cleanPrice
				)
			);

			jsonResponse.put ("BondDirtyPrice", cleanPrice + accrued);

			jsonResponse.put ("BondYield01", 10000. * yield01);

			jsonResponse.put ("BondDV01", 10000. * yield01);
		} catch (Exception e) {
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

	@SuppressWarnings ("unchecked") static final JSONObject CurveMetrics (
		final JSONObject jsonParameter)
	{
		MergedDiscountForwardCurve fundingDiscountCurve = LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == fundingDiscountCurve) {
			return null;
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!curveSurfaceQuoteContainer.setFundingState (fundingDiscountCurve) ||
			!curveSurfaceQuoteContainer.setGovvieState (LatentStateProcessor.TreasuryCurve (jsonParameter)))
		{
			return null;
		}

		double cleanPrice = Double.NaN;
		BondComponent bondComponent = null;

		int spotDate = fundingDiscountCurve.epoch().julian();

		ValuationParams valuationParams = ValuationParams.Spot (spotDate);

		JulianDate maturityDate = Converter.DateEntry (jsonParameter, "BondMaturityDate");

		try {
			if (null == (
				bondComponent = BondBuilder.CreateSimpleFixed (
					Converter.StringEntry (jsonParameter, "BondName"),
					fundingDiscountCurve.currency(),
					"",
					Converter.DoubleEntry (jsonParameter, "BondCoupon"),
					Converter.IntegerEntry (jsonParameter, "BondFrequency"),
					Converter.StringEntry (jsonParameter, "BondDayCount"),
					Converter.DateEntry (jsonParameter, "BondEffectiveDate"),
					maturityDate,
					null,
					null
				)
			))
			{
				return null;
			}

			if (jsonParameter.containsKey ("BondCleanPrice")) {
				cleanPrice = Converter.DoubleEntry (jsonParameter, "BondCleanPrice");
			} else if (jsonParameter.containsKey ("BondYield")) {
				cleanPrice = bondComponent.priceFromYield (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					Converter.DoubleEntry (jsonParameter, "BondYield")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		JSONObject jsonResponse = new JSONObject();

		jsonResponse.put ("BondName", bondComponent.name());

		jsonResponse.put ("BondEffectiveDate", bondComponent.effectiveDate().toString());

		jsonResponse.put ("BondMaturityDate", maturityDate.toString());

		jsonResponse.put ("BondFirstCouponDate", bondComponent.firstCouponDate().toString());

		jsonResponse.put ("BondCleanPrice", cleanPrice);

		try {
			jsonResponse.put (
				"BondASW",
				bondComponent.aswFromPrice (valuationParams, curveSurfaceQuoteContainer, null, cleanPrice)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse.put (
				"BondGSpread",
				bondComponent.gSpreadFromPrice (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					cleanPrice
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse.put (
				"BondISpread",
				bondComponent.iSpreadFromPrice (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					cleanPrice
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse.put (
				"BondOAS",
				bondComponent.oasFromPrice (valuationParams, curveSurfaceQuoteContainer, null, cleanPrice)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			jsonResponse.put (
				"BondTreasurySpread",
				bondComponent.tsySpreadFromPrice (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					cleanPrice
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		jsonResponse.put ("BondTreasuryBenchmark", Helper.BaseTsyBmk (spotDate, maturityDate.julian()));

		try {
			jsonResponse.put (
				"BondZSpread",
				bondComponent.zSpreadFromPrice (
					valuationParams,
					curveSurfaceQuoteContainer,
					null,
					cleanPrice
				)
			);
		} catch (Exception e) {
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

	@SuppressWarnings ("unchecked") static final JSONObject CashFlows (
		final JSONObject jsonParameter)
	{
		MergedDiscountForwardCurve fundingDiscountCurve = LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == fundingDiscountCurve) {
			return null;
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!curveSurfaceQuoteContainer.setFundingState (fundingDiscountCurve)) {
			return null;
		}

		CreditCurve creditCurve = LatentStateProcessor.CreditCurve (jsonParameter, fundingDiscountCurve);

		curveSurfaceQuoteContainer.setCreditState (creditCurve);

		double valueNotional = 1.;
		BondComponent bondComponent = null;

		JulianDate maturityDate = Converter.DateEntry (jsonParameter, "BondMaturityDate");

		try {
			if (null == (
				bondComponent = BondBuilder.CreateSimpleFixed (
					Converter.StringEntry (jsonParameter, "BondName"),
					fundingDiscountCurve.currency(),
					"",
					Converter.DoubleEntry (jsonParameter, "BondCoupon"),
					Converter.IntegerEntry (jsonParameter, "BondFrequency"),
					Converter.StringEntry (jsonParameter, "BondDayCount"),
					Converter.DateEntry (jsonParameter, "BondEffectiveDate"),
					maturityDate,
					null,
					null
				)
			))
			{
				return null;
			}

			valueNotional = Converter.DoubleEntry (jsonParameter, "BondValueNotional");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		JSONObject jsonResponse = new JSONObject();

		jsonResponse.put ("BondName", bondComponent.name());

		List<CompositePeriod> compositePeriodList = bondComponent.couponPeriods();

		if (null == compositePeriodList || 0 == compositePeriodList.size()) {
			return null;
		}

		JSONArray jsonCashFlowArray = new JSONArray();

		for (CompositePeriod compositePeriod : compositePeriodList) {
			if (null == compositePeriod) {
				return null;
			}

			JSONObject jsonCashFlow = new JSONObject();

			jsonCashFlow.put ("StartDate", new JulianDate (compositePeriod.startDate()).toString());

			jsonCashFlow.put ("EndDate", new JulianDate (compositePeriod.endDate()).toString());

			jsonCashFlow.put ("PayDate", new JulianDate (compositePeriod.payDate()).toString());

			try {
				double couponRate = compositePeriod.periods().get (0).baseRate (curveSurfaceQuoteContainer);

				jsonCashFlow.put ("FixingDate", new JulianDate (compositePeriod.fxFixingDate()).toString());

				jsonCashFlow.put ("CouponDCF", compositePeriod.couponDCF());

				jsonCashFlow.put ("CouponRate", couponRate);

				if (null != creditCurve) {
					jsonCashFlow.put ("SurvivalFactor", creditCurve.survival (compositePeriod.payDate()));
				}

				jsonCashFlow.put ("PayDiscountFactor", compositePeriod.df (curveSurfaceQuoteContainer));

				jsonCashFlow.put ("CouponAmount", couponRate * valueNotional);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCashFlow.put ("BaseNotional", compositePeriod.baseNotional() * valueNotional);

			jsonCashFlowArray.add (jsonCashFlow);
		}

		jsonResponse.put ("CashFlow", jsonCashFlowArray);

		return jsonResponse;
	}
}
