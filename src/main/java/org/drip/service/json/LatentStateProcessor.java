
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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>LatentStateProcessor</i> Sets Up and Executes a JSON Based In/Out Curve Processor.
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

public class LatentStateProcessor {

	static final org.drip.state.discount.MergedDiscountForwardCurve FundingCurve (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dtSpot = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"SpotDate");

		java.lang.String strCurrency = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"Currency");

		java.lang.String[] astrDepositMaturityTenor = org.drip.json.parser.Converter.StringArrayEntry
			(jsonParameter, "DepositTenor");

		double[] adblDepositQuote = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"DepositQuote");

		double[] adblFuturesQuote = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"FuturesQuote");

		java.lang.String[] astrFixFloatMaturityTenor = org.drip.json.parser.Converter.StringArrayEntry
			(jsonParameter, "FixFloatTenor");

		double[] adblFixFloatQuote = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"FixFloatQuote");

		return org.drip.service.template.LatentMarketStateBuilder.SmoothFundingCurve (dtSpot, strCurrency,
			astrDepositMaturityTenor, adblDepositQuote, "ForwardRate", adblFuturesQuote, "ForwardRate",
				astrFixFloatMaturityTenor, adblFixFloatQuote, "SwapRate");
	}

	static final org.drip.state.govvie.GovvieCurve TreasuryCurve (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dtSpot = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"SpotDate");

		java.lang.String strCode = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"TreasuryCode");

		java.lang.String[] astrTenor = org.drip.json.parser.Converter.StringArrayEntry (jsonParameter,
			"TreasuryTenor");

		double[] adblYield = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"TreasuryYield");

		int iNumTenor = null == adblYield ? 0 : adblYield.length;
		double[] adblCoupon = 0 == iNumTenor ? null : new double[iNumTenor];
		org.drip.analytics.date.JulianDate[] adtMaturity = 0 == iNumTenor ? null : new
			org.drip.analytics.date.JulianDate[iNumTenor];
		org.drip.analytics.date.JulianDate[] adtEffective = 0 == iNumTenor ? null : new
			org.drip.analytics.date.JulianDate[iNumTenor];

		for (int i = 0; i < iNumTenor; ++i) {
			adblCoupon[i] = adblYield[i];

			adtMaturity[i] = (adtEffective[i] = dtSpot).addTenor (astrTenor[i]);
		}

		return org.drip.service.template.LatentMarketStateBuilder.GovvieCurve (strCode, dtSpot, adtEffective,
			adtMaturity, adblCoupon, adblYield, "Yield",
				org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);
	}

	static final org.drip.state.credit.CreditCurve CreditCurve (
		final org.drip.json.simple.JSONObject jsonParameter,
		final org.drip.state.discount.MergedDiscountForwardCurve dcFunding)
	{
		if (null == dcFunding) return null;

		java.lang.String strReferenceEntity = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"ReferenceEntity");

		java.lang.String[] astrCDSMaturityTenor = org.drip.json.parser.Converter.StringArrayEntry
			(jsonParameter, "CDSTenor");

		double[] adblCDSQuote = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter, "CDSQuote");

		return org.drip.service.template.LatentMarketStateBuilder.CreditCurve (dcFunding.epoch(),
			strReferenceEntity, astrCDSMaturityTenor, adblCDSQuote, adblCDSQuote, "FairPremium", dcFunding);
	}

	/**
	 * JSON Based in/out Funding Curve Sample
	 * 
	 * @param jsonParameter JSON Funding Curve Request Parameters
	 * 
	 * @return JSON Funding Curve Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject FundingCurveThunker (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.analytics.date.JulianDate dtSpot = org.drip.json.parser.Converter.DateEntry (jsonParameter,
			"SpotDate");

		java.lang.String strCurrency = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"Currency");

		java.lang.String[] astrDepositMaturityTenor = org.drip.json.parser.Converter.StringArrayEntry
			(jsonParameter, "DepositTenor");

		double[] adblDepositQuote = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"DepositQuote");

		double[] adblFuturesQuote = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"FuturesQuote");

		java.lang.String[] astrFixFloatMaturityTenor = org.drip.json.parser.Converter.StringArrayEntry
			(jsonParameter, "FixFloatTenor");

		double[] adblFixFloatQuote = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter,
			"FixFloatQuote");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.template.LatentMarketStateBuilder.SmoothFundingCurve (dtSpot, strCurrency,
				astrDepositMaturityTenor, adblDepositQuote, "ForwardRate", adblFuturesQuote, "ForwardRate",
					astrFixFloatMaturityTenor, adblFixFloatQuote, "SwapRate");

		if (null == dcFunding) return null;

		int iNumDeposit = null == adblDepositQuote ? 0 : adblDepositQuote.length;
		int iNumFutures = null == adblFuturesQuote ? 0 : adblFuturesQuote.length;
		int iNumFixFloat = null == adblFixFloatQuote ? 0 : adblFixFloatQuote.length;

		org.drip.json.simple.JSONArray jsonDepositArray = new org.drip.json.simple.JSONArray();

		for (int i = 0; i < iNumDeposit; ++i) {
			org.drip.json.simple.JSONObject jsonDeposit = new org.drip.json.simple.JSONObject();

			jsonDeposit.put ("InstrumentType", "DEPOSIT");

			jsonDeposit.put ("MaturityTenor", astrDepositMaturityTenor[i]);

			jsonDeposit.put ("InstrumentQuote", adblDepositQuote[i]);

			jsonDeposit.put ("CalibrationMeasure", "ForwardRate");

			try {
				jsonDeposit.put ("DiscountFactor", dcFunding.df (astrDepositMaturityTenor[i]));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonDepositArray.add (i, jsonDeposit);
		}

		org.drip.json.simple.JSONArray jsonFuturesArray = new org.drip.json.simple.JSONArray();

		for (int i = 0; i < iNumFutures; ++i) {
			org.drip.json.simple.JSONObject jsonFutures = new org.drip.json.simple.JSONObject();

			jsonFutures.put ("InstrumentType", "FUTURES");

			jsonFutures.put ("InstrumentQuote", adblFuturesQuote[i]);

			jsonFutures.put ("CalibrationMeasure", "ForwardRate");

			try {
				jsonFutures.put ("DiscountFactor", dcFunding.df ((3 + 3 * i) + "M"));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonFuturesArray.add (i, jsonFutures);
		}

		org.drip.json.simple.JSONArray jsonFixFloatArray = new org.drip.json.simple.JSONArray();

		for (int i = 0; i < iNumFixFloat; ++i) {
			org.drip.json.simple.JSONObject jsonFixFloat = new org.drip.json.simple.JSONObject();

			jsonFixFloat.put ("InstrumentType", "FIXFLOAT");

			jsonFixFloat.put ("MaturityTenor", astrFixFloatMaturityTenor[i]);

			jsonFixFloat.put ("InstrumentQuote", adblFixFloatQuote[i]);

			jsonFixFloat.put ("CalibrationMeasure", "SwapRate");

			try {
				jsonFixFloat.put ("DiscountFactor", dcFunding.df (astrFixFloatMaturityTenor[i]));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonFixFloatArray.add (i, jsonFixFloat);
		}

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("SpotDate", dtSpot.toString());

		jsonResponse.put ("Currency", strCurrency);

		jsonResponse.put ("FundingLabel", dcFunding.label().fullyQualifiedName());

		jsonResponse.put ("DepositMetrics", jsonDepositArray);

		jsonResponse.put ("FuturesMetrics", jsonFuturesArray);

		jsonResponse.put ("FixFloatMetrics", jsonFixFloatArray);

		return jsonResponse;
	}

	/**
	 * JSON Based in/out Credit Curve Sample
	 * 
	 * @param jsonParameter JSON Credit Curve Request Parameters
	 * 
	 * @return JSON Credit Curve Response
	 */

	@SuppressWarnings ("unchecked") static final org.drip.json.simple.JSONObject CreditCurveThunker (
		final org.drip.json.simple.JSONObject jsonParameter)
	{
		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = FundingCurve (jsonParameter);

		if (null == dcFunding) return null;

		java.lang.String strReferenceEntity = org.drip.json.parser.Converter.StringEntry (jsonParameter,
			"ReferenceEntity");

		java.lang.String[] astrCDSMaturityTenor = org.drip.json.parser.Converter.StringArrayEntry
			(jsonParameter, "CDSTenor");

		double[] adblCDSQuote = org.drip.json.parser.Converter.DoubleArrayEntry (jsonParameter, "CDSQuote");

		org.drip.analytics.date.JulianDate dtSpot = dcFunding.epoch();

		org.drip.state.credit.CreditCurve ccSurvivalRecovery =
			org.drip.service.template.LatentMarketStateBuilder.CreditCurve (dtSpot, strReferenceEntity,
				astrCDSMaturityTenor, adblCDSQuote, adblCDSQuote, "FairPremium", dcFunding);

		if (null == ccSurvivalRecovery) return null;

		int iNumCDS = null == adblCDSQuote ? 0 : adblCDSQuote.length;

		String strLatentStateLabel = ccSurvivalRecovery.label().fullyQualifiedName();

		org.drip.json.simple.JSONArray jsonCDSArray = new org.drip.json.simple.JSONArray();

		for (int i = 0; i < iNumCDS; ++i) {
			org.drip.json.simple.JSONObject jsonCDS = new org.drip.json.simple.JSONObject();

			jsonCDS.put ("ReferenceEntity", strLatentStateLabel);

			jsonCDS.put ("InstrumentType", "CDS");

			jsonCDS.put ("MaturityTenor", astrCDSMaturityTenor[i]);

			jsonCDS.put ("InstrumentQuote", adblCDSQuote[i]);

			jsonCDS.put ("CalibrationMeasure", "FairPremium");

			try {
				jsonCDS.put ("SurvivalProbability", ccSurvivalRecovery.survival (astrCDSMaturityTenor[i]));

				jsonCDS.put ("Recovery", ccSurvivalRecovery.recovery (astrCDSMaturityTenor[i]));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			jsonCDSArray.add (i, jsonCDS);
		}

		org.drip.json.simple.JSONObject jsonResponse = new org.drip.json.simple.JSONObject();

		jsonResponse.put ("SpotDate", dtSpot.toString());

		jsonResponse.put ("Currency", dcFunding.currency());

		jsonResponse.put ("ReferenceEntity", strReferenceEntity);

		jsonResponse.put ("CreditLabel", strLatentStateLabel);

		jsonResponse.put ("FundingLabel", dcFunding.label().fullyQualifiedName());

		jsonResponse.put ("CDSMetrics", jsonCDSArray);

		return jsonResponse;
	}
}
