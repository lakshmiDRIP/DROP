
package org.drip.service.json;

import java.util.Map;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.JulianDate;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.rates.FixFloatComponent;
import org.drip.service.jsonparser.Converter;
import org.drip.service.representation.JSONArray;
import org.drip.service.representation.JSONObject;
import org.drip.service.template.OTCInstrumentBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.identifier.ForwardLabel;

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
 * <i>FixFloatProcessor</i> Sets Up and Executes a JSON Based In/Out Fix Float Swap Valuation Processor. It
 * 	provides the following Functionality:
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

public class FixFloatProcessor
{

	/**
	 * JSON Based in/out Funding Fix Float Curve Metrics Thunker
	 * 
	 * @param jsonParameter JSON Funding Fix Float Request Parameters
	 * 
	 * @return JSON Funding Fix Float Curve Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final JSONObject CurveMetrics (
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

		JulianDate spotDate = fundingDiscountCurve.epoch();

		FixFloatComponent irs = null;

		try {
			irs = OTCInstrumentBuilder.FixFloatStandard (
				spotDate,
				fundingDiscountCurve.currency(),
				"ALL",
				Converter.StringEntry (jsonParameter, "FixFloatMaturity"),
				"MAIN",
				Converter.DoubleEntry (jsonParameter, "FixFloatCoupon" )
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		if (null == irs) {
			return null;
		}

		Map<String, Double> irsMetricsMap = irs.value (
			ValuationParams.Spot (spotDate.julian()),
			null,
			curveSurfaceQuoteContainer,
			null
		);

		if (null == irsMetricsMap) {
			return null;
		}

		JSONObject jsonResponse = new JSONObject();

		for (Map.Entry<String, Double> irsMetricsMapEntry : irsMetricsMap.entrySet()) {
			jsonResponse.put (irsMetricsMapEntry.getKey(), irsMetricsMapEntry.getValue());
		}

		JSONArray jsonFixedCashFlowArray = new JSONArray();

		for (CompositePeriod compositePeriod : irs.referenceStream().cashFlowPeriod()) {
			JSONObject jsonCashFlow = new JSONObject();

			try {
				jsonCashFlow.put ("StartDate", new JulianDate (compositePeriod.startDate()).toString());

				jsonCashFlow.put ("EndDate", new JulianDate (compositePeriod.endDate()).toString());

				jsonCashFlow.put ("PayDate", new JulianDate (compositePeriod.payDate()).toString());

				jsonCashFlow.put ("FixingDate", new JulianDate (compositePeriod.fxFixingDate()).toString());

				jsonCashFlow.put ("CouponDCF", compositePeriod.couponDCF());

				jsonCashFlow.put ("PayDiscountFactor", compositePeriod.df (curveSurfaceQuoteContainer));
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCashFlow.put ("BaseNotional", compositePeriod.baseNotional());

			jsonCashFlow.put ("Tenor", compositePeriod.tenor());

			jsonCashFlow.put ("FundingLabel", compositePeriod.fundingLabel().fullyQualifiedName());

			jsonCashFlow.put (
				"ReferenceRate",
				compositePeriod.couponMetrics (spotDate.julian(), curveSurfaceQuoteContainer).rate()
			);

			jsonFixedCashFlowArray.add (jsonCashFlow);
		}

		jsonResponse.put ("FixedCashFlow", jsonFixedCashFlowArray);

		JSONArray jsonFloatingCashFlowArray = new JSONArray();

		for (CompositePeriod compositePeriod : irs.derivedStream().cashFlowPeriod()) {
			JSONObject jsonCashFlow = new JSONObject();

			try {
				jsonCashFlow.put ("StartDate", new JulianDate (compositePeriod.startDate()).toString());

				jsonCashFlow.put ("EndDate", new JulianDate (compositePeriod.endDate()).toString());

				jsonCashFlow.put ("PayDate", new JulianDate (compositePeriod.payDate()).toString());

				jsonCashFlow.put ("FixingDate", new JulianDate (compositePeriod.fxFixingDate()).toString());

				jsonCashFlow.put ("CouponDCF", compositePeriod.couponDCF());

				jsonCashFlow.put ("PayDiscountFactor", compositePeriod.df (curveSurfaceQuoteContainer));
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCashFlow.put ("BaseNotional", compositePeriod.baseNotional());

			jsonCashFlow.put ("Tenor", compositePeriod.tenor());

			jsonCashFlow.put ("FundingLabel", compositePeriod.fundingLabel().fullyQualifiedName());

			jsonCashFlow.put (
				"ForwardLabel",
				((ForwardLabel) compositePeriod.floaterLabel()).fullyQualifiedName()
			);

			jsonCashFlow.put (
				"ReferenceRate",
				compositePeriod.couponMetrics (spotDate.julian(), curveSurfaceQuoteContainer).rate()
			);

			jsonFloatingCashFlowArray.add (jsonCashFlow);
		}

		jsonResponse.put ("FloatingCashFlow", jsonFloatingCashFlowArray);

		return jsonResponse;
	}
}
