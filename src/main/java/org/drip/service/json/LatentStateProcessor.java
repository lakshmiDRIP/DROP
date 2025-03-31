
package org.drip.service.json;

import org.drip.analytics.date.JulianDate;
import org.drip.service.jsonparser.Converter;
import org.drip.service.representation.JSONArray;
import org.drip.service.representation.JSONObject;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>LatentStateProcessor</i> Sets Up and Executes a JSON Based In/Out Curve Processor. It exposes the
 * 	following Functions:
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

public class LatentStateProcessor
{

	/**
	 * Construct a Funding Curve from the Input JSON
	 * 
	 * @param jsonParameter Input JSON Parameter
	 * 
	 * @return The Funding Curve
	 */

	static final MergedDiscountForwardCurve FundingCurve (
		final JSONObject jsonParameter)
	{
		return LatentMarketStateBuilder.SmoothFundingCurve (
			Converter.DateEntry (jsonParameter, "SpotDate"),
			Converter.StringEntry (jsonParameter, "Currency"),
			Converter.StringArrayEntry (jsonParameter, "DepositTenor"),
			Converter.DoubleArrayEntry (jsonParameter, "DepositQuote"),
			"ForwardRate",
			Converter.DoubleArrayEntry (jsonParameter, "FuturesQuote"),
			"ForwardRate",
			Converter.StringArrayEntry (jsonParameter, "FixFloatTenor"),
			Converter.DoubleArrayEntry (jsonParameter, "FixFloatQuote"),
			"SwapRate"
		);
	}

	/**
	 * Construct a Treasury Curve from the Input JSON
	 * 
	 * @param jsonParameter Input JSON Parameter
	 * 
	 * @return The Treasury Curve
	 */

	static final GovvieCurve TreasuryCurve (
		final JSONObject jsonParameter)
	{
		JulianDate spotDate = Converter.DateEntry (jsonParameter, "SpotDate");

		String[] tenorArray = Converter.StringArrayEntry (jsonParameter, "TreasuryTenor");

		double[] yieldArray = Converter.DoubleArrayEntry (jsonParameter, "TreasuryYield");

		int tenorCount = null == yieldArray ? 0 : yieldArray.length;
		double[] couponArray = 0 == tenorCount ? null : new double[tenorCount];
		JulianDate[] maturityDateArray = 0 == tenorCount ? null : new JulianDate[tenorCount];
		JulianDate[] effectiveDateArray = 0 == tenorCount ? null : new JulianDate[tenorCount];

		for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
			couponArray[tenorIndex] = yieldArray[tenorIndex];

			maturityDateArray[tenorIndex] =
				(effectiveDateArray[tenorIndex] = spotDate).addTenor (tenorArray[tenorIndex]);
		}

		return LatentMarketStateBuilder.GovvieCurve (
			Converter.StringEntry (jsonParameter, "TreasuryCode"),
			spotDate,
			effectiveDateArray,
			maturityDateArray,
			couponArray,
			yieldArray,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING
		);
	}

	/**
	 * Construct a Credit Curve from the Input JSON
	 * 
	 * @param jsonParameter Input JSON Parameter
	 * 
	 * @return The Credit Curve
	 */

	static final CreditCurve CreditCurve (
		final JSONObject jsonParameter,
		final MergedDiscountForwardCurve fundingCurve)
	{
		if (null == fundingCurve) {
			return null;
		}

		double[] cdsQuoteArray = Converter.DoubleArrayEntry (jsonParameter, "CDSQuote");

		return org.drip.service.template.LatentMarketStateBuilder.CreditCurve (
			fundingCurve.epoch(),
			Converter.StringEntry (jsonParameter, "ReferenceEntity"),
			Converter.StringArrayEntry (jsonParameter, "CDSTenor"),
			cdsQuoteArray,
			cdsQuoteArray,
			"FairPremium",
			fundingCurve
		);
	}

	/**
	 * JSON Based in/out Funding Curve Sample
	 * 
	 * @param jsonParameter JSON Funding Curve Request Parameters
	 * 
	 * @return JSON Funding Curve Response
	 */

	@SuppressWarnings ("unchecked") static final JSONObject FundingCurveThunker (
		final JSONObject jsonParameter)
	{
		String currency = Converter.StringEntry (jsonParameter, "Currency");

		JulianDate spotDate = Converter.DateEntry (jsonParameter, "SpotDate");

		double[] depositQuoteArray = Converter.DoubleArrayEntry (jsonParameter, "DepositQuote");

		double[] futuresQuoteArray = Converter.DoubleArrayEntry (jsonParameter, "FuturesQuote");

		double[] fixFloatQuoteArray = Converter.DoubleArrayEntry (jsonParameter, "FixFloatQuote");

		String[] depositMaturityTenorArray = Converter.StringArrayEntry (jsonParameter, "DepositTenor");

		String[] fixFloatMaturityTenorArray = Converter.StringArrayEntry (jsonParameter, "FixFloatTenor");

		MergedDiscountForwardCurve fundingCurve = LatentMarketStateBuilder.SmoothFundingCurve (
			spotDate,
			currency,
			depositMaturityTenorArray,
			depositQuoteArray,
			"ForwardRate",
			futuresQuoteArray,
			"ForwardRate",
			fixFloatMaturityTenorArray,
			fixFloatQuoteArray,
			"SwapRate"
		);

		if (null == fundingCurve) {
			return null;
		}

		JSONArray depositJSONArray = new JSONArray();

		int depositCount = null == depositQuoteArray ? 0 : depositQuoteArray.length;
		int futuresCount = null == futuresQuoteArray ? 0 : futuresQuoteArray.length;
		int fixFloatCount = null == fixFloatQuoteArray ? 0 : fixFloatQuoteArray.length;

		for (int depositIndex = 0; depositIndex < depositCount; ++depositIndex) {
			JSONObject depositJSON = new JSONObject();

			depositJSON.put ("InstrumentType", "DEPOSIT");

			depositJSON.put ("CalibrationMeasure", "ForwardRate");

			depositJSON.put ("InstrumentQuote", futuresQuoteArray[depositIndex]);

			depositJSON.put ("MaturityTenor", depositMaturityTenorArray[depositIndex]);

			try {
				depositJSON.put ("DiscountFactor", fundingCurve.df (depositMaturityTenorArray[depositIndex]));
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			depositJSONArray.add (depositIndex, depositJSON);
		}

		JSONArray futuresJSONArray = new JSONArray();

		for (int futuresIndex = 0; futuresIndex < futuresCount; ++futuresIndex) {
			JSONObject futuresJSON = new JSONObject();

			futuresJSON.put ("InstrumentType", "FUTURES");

			futuresJSON.put ("CalibrationMeasure", "ForwardRate");

			futuresJSON.put ("InstrumentQuote", futuresQuoteArray[futuresIndex]);

			try {
				futuresJSON.put ("DiscountFactor", fundingCurve.df ((3 + 3 * futuresIndex) + "M"));
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			futuresJSONArray.add (futuresIndex, futuresJSON);
		}

		JSONArray fixFloatJSONArray = new JSONArray();

		for (int fixFloatIndex = 0; fixFloatIndex < fixFloatCount; ++fixFloatIndex) {
			JSONObject fixFloatJSON = new JSONObject();

			fixFloatJSON.put ("InstrumentType", "FIXFLOAT");

			fixFloatJSON.put ("MaturityTenor", fixFloatMaturityTenorArray[fixFloatIndex]);

			fixFloatJSON.put ("InstrumentQuote", fixFloatQuoteArray[fixFloatIndex]);

			fixFloatJSON.put ("CalibrationMeasure", "SwapRate");

			try {
				fixFloatJSON.put ("DiscountFactor", fundingCurve.df (fixFloatMaturityTenorArray[fixFloatIndex]));
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			fixFloatJSONArray.add (fixFloatIndex, fixFloatJSON);
		}

		JSONObject jsonResponse = new JSONObject();

		jsonResponse.put ("Currency", currency);

		jsonResponse.put ("SpotDate", spotDate.toString());

		jsonResponse.put ("DepositMetrics", depositJSONArray);

		jsonResponse.put ("FuturesMetrics", futuresJSONArray);

		jsonResponse.put ("FixFloatMetrics", fixFloatJSONArray);

		jsonResponse.put ("FundingLabel", fundingCurve.label().fullyQualifiedName());

		return jsonResponse;
	}

	/**
	 * JSON Based in/out Credit Curve Sample
	 * 
	 * @param jsonParameter JSON Credit Curve Request Parameters
	 * 
	 * @return JSON Credit Curve Response
	 */

	@SuppressWarnings ("unchecked") static final JSONObject CreditCurveThunker (
		final JSONObject jsonParameter)
	{
		MergedDiscountForwardCurve fundingCurve = FundingCurve (jsonParameter);

		if (null == fundingCurve) {
			return null;
		}

		JulianDate spotDate = fundingCurve.epoch();

		double[] cdsQuoteArray = Converter.DoubleArrayEntry (jsonParameter, "CDSQuote");

		String referenceEntity = Converter.StringEntry (jsonParameter, "ReferenceEntity");

		String[] cdsMaturityTenorArray = Converter.StringArrayEntry (jsonParameter, "CDSTenor");

		CreditCurve survivalRecoveryCreditCurve = LatentMarketStateBuilder.CreditCurve (
			spotDate,
			referenceEntity,
			cdsMaturityTenorArray,
			cdsQuoteArray,
			cdsQuoteArray,
			"FairPremium",
			fundingCurve
		);

		if (null == survivalRecoveryCreditCurve) {
			return null;
		}

		int cdsCount = null == cdsQuoteArray ? 0 : cdsQuoteArray.length;

		String creditCurveLabel = survivalRecoveryCreditCurve.label().fullyQualifiedName();

		JSONArray cdsJSONArray = new JSONArray();

		for (int cdsIndex = 0; cdsIndex < cdsCount; ++cdsIndex) {
			JSONObject cdsJSON = new JSONObject();

			cdsJSON.put ("InstrumentType", "CDS");

			cdsJSON.put ("ReferenceEntity", creditCurveLabel);

			cdsJSON.put ("CalibrationMeasure", "FairPremium");

			cdsJSON.put ("InstrumentQuote", cdsQuoteArray[cdsIndex]);

			cdsJSON.put ("MaturityTenor", cdsMaturityTenorArray[cdsIndex]);

			try {
				cdsJSON.put (
					"SurvivalProbability",
					survivalRecoveryCreditCurve.survival (cdsMaturityTenorArray[cdsIndex])
				);

				cdsJSON.put (
					"Recovery",
					survivalRecoveryCreditCurve.recovery (cdsMaturityTenorArray[cdsIndex])
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			cdsJSONArray.add (cdsIndex, cdsJSON);
		}

		JSONObject jsonResponse = new JSONObject();

		jsonResponse.put ("CDSMetrics", cdsJSONArray);

		jsonResponse.put ("CreditLabel", creditCurveLabel);

		jsonResponse.put ("SpotDate", spotDate.toString());

		jsonResponse.put ("ReferenceEntity", referenceEntity);

		jsonResponse.put ("Currency", fundingCurve.currency());

		jsonResponse.put ("FundingLabel", fundingCurve.label().fullyQualifiedName());

		return jsonResponse;
	}
}
