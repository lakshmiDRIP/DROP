
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
 * <i>DepositProcessor</i> Sets Up and Executes a JSON Based In/Out Deposit Valuation Processor.
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

public class DepositProcessor {

	/**
	 * JSON Based in/out Deposit Curve Metrics Thunker
	 * 
	 * @param jsonParameter JSON Deposit Request Parameters
	 * 
	 * @return JSON Funding Deposit Curve Metrics Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.service.representation.JSONObject CurveMetrics (
		final org.drip.service.representation.JSONObject jsonParameter)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.json.LatentStateProcessor.FundingCurve (jsonParameter);

		if (null == dcFunding) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState (dcFunding)) return null;

		org.drip.analytics.date.JulianDate dtSpot = dcFunding.epoch();

		org.drip.product.rates.SingleStreamComponent deposit =
			org.drip.service.template.OTCInstrumentBuilder.FundingDeposit (dtSpot, dcFunding.currency(),
				org.drip.service.jsonparser.Converter.StringEntry (jsonParameter, "DepositMaturity"));

		if (null == deposit) return null;

		java.util.Map<java.lang.String, java.lang.Double> mapResult = deposit.value
			(org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), null, csqc, null);

		if (null == mapResult) return null;

		org.drip.service.representation.JSONObject jsonResponse = new org.drip.service.representation.JSONObject();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapResult.entrySet())
			jsonResponse.put (me.getKey(), me.getValue());

		org.drip.service.representation.JSONArray jsonCashFlowArray = new org.drip.service.representation.JSONArray();

		for (org.drip.analytics.cashflow.CompositePeriod cp : deposit.couponPeriods()) {
			org.drip.service.representation.JSONObject jsonCashFlow = new org.drip.service.representation.JSONObject();

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

			jsonCashFlow.put ("ForwardLabel", ((org.drip.state.identifier.ForwardLabel)
				cp.floaterLabel()).fullyQualifiedName());

			jsonCashFlow.put ("ReferenceRate", cp.couponMetrics (dtSpot.julian(), csqc).rate());

			jsonCashFlowArray.add (jsonCashFlow);
		}

		jsonResponse.put ("FloatingCashFlow", jsonCashFlowArray);

		return jsonResponse;
	}
}
