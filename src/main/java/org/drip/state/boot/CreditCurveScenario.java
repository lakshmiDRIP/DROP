
package org.drip.state.boot;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.definition.CalibrationParams;
import org.drip.param.market.LatentStateFixingsContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.definition.CreditDefaultSwap;
import org.drip.state.creator.ScenarioCreditCurveBuilder;
import org.drip.state.credit.CreditCurve;
import org.drip.state.credit.ExplicitBootCreditCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.nonlinear.NonlinearCurveBuilder;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>CreditCurveScenario</i> uses the hazard rate calibration instruments along with the component
 * calibrator to produce scenario hazard rate curves. CreditCurveScenario typically first constructs the
 * actual curve calibrator instance to localize the intelligence around curve construction. It then uses this
 * curve calibrator instance to build individual curves or the sequence of node bumped scenario curves. The
 * curves in the set may be an array, or tenor-keyed. It exposes the following functions:
 *
 *  <ul>
 * 		<li>Calibrate a credit curve</li>
 * 		<li>Create an array of tenor bumped credit curves</li>
 * 		<li>Create an tenor named map of tenor bumped credit curves</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/boot/README.md">Bootable Discount, Credit, Volatility States</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CreditCurveScenario
{
	static class TranslatedQuoteMeasure
	{
		String _measure = "";
		double _quote = Double.NaN;

		TranslatedQuoteMeasure (
			final String measure,
			final double quote)
		{
			_quote = quote;
			_measure = measure;
		}
	}

	private static final TranslatedQuoteMeasure TranslateQuoteMeasure (
		final CalibratableComponent calibratableComponent,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final MergedDiscountForwardCurve discountCurve,
		final CreditCurve creditCurve,
		final String measure,
		final double quote)
	{
		if (!(calibratableComponent instanceof CreditDefaultSwap) || (
			!"FlatSpread".equalsIgnoreCase (measure) && !"QuotedSpread".equalsIgnoreCase (measure)
		)) {
			return new TranslatedQuoteMeasure (measure, quote);
		}

		CaseInsensitiveTreeMap<Double> quotedSpreadMeasureMap =
			((CreditDefaultSwap) calibratableComponent).valueFromQuotedSpread (
				valuationParams,
				creditPricerParams,
				MarketParamsBuilder.Credit (discountCurve, creditCurve),
				null,
				0.01,
				quote
			);

		return null == quotedSpreadMeasureMap ? null : new TranslatedQuoteMeasure (
			"Upfront",
			quotedSpreadMeasureMap.get ("Upfront")
		);
	}

	/**
	 * Calibrate a Credit Curve
	 * 
	 * @param name Credit Curve name
	 * @param valuationParams ValuationParams
	 * @param calibratableComponentArray Array of Calibration Instruments
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param recovery Component recovery
	 * @param flat Flat Calibration (True), or real bootstrapping (false)
	 * @param discountCurve Base Discount Curve
	 * @param govvieCurve Govvie Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param calibrationParams The Calibration Parameters
	 * 
	 * @return CreditCurve Instance
	 */

	public static final org.drip.state.credit.CreditCurve Standard (
		final String name,
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final double recovery,
		final boolean flat,
		final MergedDiscountForwardCurve discountCurve,
		final GovvieCurve govvieCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final CalibrationParams calibrationParams)
	{
		if (null == valuationParams || null == calibratableComponentArray || null == calibrationQuoteArray ||
			null == calibrationMeasureArray || null == discountCurve) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		double hazardRateArray[] = new double[componentCount];
		int dateArray[] = new int[componentCount];

		if (0 == componentCount || calibrationQuoteArray.length != componentCount ||
			calibrationMeasureArray.length != componentCount) {
			return null;
		}

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			if (null == calibratableComponentArray[componentIndex]) {
				return null;
			}

			hazardRateArray[componentIndex] = Double.NaN;

			dateArray[componentIndex] = calibratableComponentArray[componentIndex].maturityDate().julian();
		}

		ExplicitBootCreditCurve explicitBootCreditCurve = ScenarioCreditCurveBuilder.Hazard (
			new JulianDate (valuationParams.valueDate()),
			name,
			discountCurve.currency(),
			dateArray,
			hazardRateArray,
			recovery
		);

		CreditPricerParams creditPricerParams = new CreditPricerParams (
			7,
			null,
			false,
			CreditPricerParams.PERIOD_DISCRETIZATION_DAY_STEP
		);

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			TranslatedQuoteMeasure translatedQuoteMeasure = TranslateQuoteMeasure (
				calibratableComponentArray[componentIndex],
				valuationParams,
				creditPricerParams,
				discountCurve,
				explicitBootCreditCurve,
				calibrationMeasureArray[componentIndex],
				calibrationQuoteArray[componentIndex]
			);

			if (null == translatedQuoteMeasure) {
				return null;
			}

			if (!NonlinearCurveBuilder.CreditCurve (
				valuationParams,
				calibratableComponentArray[componentIndex],
				translatedQuoteMeasure._quote,
				translatedQuoteMeasure._measure,
				flat,
				componentIndex,
				explicitBootCreditCurve,
				discountCurve,
				govvieCurve,
				creditPricerParams,
				latentStateFixingsContainer,
				valuationCustomizationParams,
				calibrationParams
			))
			{
				return null;
			}
		}

		explicitBootCreditCurve.setInstrCalibInputs (
			valuationParams,
			flat,
			discountCurve,
			govvieCurve,
			creditPricerParams,
			calibratableComponentArray,
			calibrationQuoteArray,
			calibrationMeasureArray,
			latentStateFixingsContainer,
			valuationCustomizationParams
		);

		return explicitBootCreditCurve;
	}

	/**
	 * Create an array of tenor bumped credit curves
	 * 
	 * @param name Credit Curve name
	 * @param valuationParams ValuationParams
	 * @param calibratableComponentArray Array of Calibration Instruments
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param recovery Component recovery
	 * @param flat Flat Calibration (True), or real bootstrapping (false)
	 * @param bump Amount of bump applied to the tenor
	 * @param discountCurve Base Discount Curve
	 * @param govvieCurve Govvie Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return Array of CreditCurves
	 */

	public static final CreditCurve[] Tenor (
		final String name,
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final double recovery,
		final boolean flat,
		final double bump,
		final MergedDiscountForwardCurve discountCurve,
		final GovvieCurve govvieCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == valuationParams || null == calibratableComponentArray || null == calibrationQuoteArray ||
			null == calibrationMeasureArray || null == discountCurve) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		CreditCurve[] creditCurveArray = new CreditCurve[componentCount];

		if (0 == componentCount || calibrationQuoteArray.length != componentCount ||
			calibrationMeasureArray.length != componentCount) {
			return null;
		}

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			double[] tenorQuoteArray = new double[componentCount];

			for (int bumpComponentIndex = 0; bumpComponentIndex < componentCount; ++bumpComponentIndex) {
				tenorQuoteArray[bumpComponentIndex] += calibrationQuoteArray[bumpComponentIndex] +
					(bumpComponentIndex == componentIndex ? bump : 0.);
			}

			if (null == (
				creditCurveArray[componentIndex] = Standard (
					name,
					valuationParams,
					calibratableComponentArray,
					tenorQuoteArray,
					calibrationMeasureArray,
					recovery,
					flat,
					discountCurve,
					govvieCurve,
					latentStateFixingsContainer,
					valuationCustomizationParams,
					null
				)
			)) {
				return null;
			}
		}

		return creditCurveArray;
	}

	/**
	 * Create an tenor named map of tenor bumped credit curves
	 * 
	 * @param name Credit Curve name
	 * @param valuationParams ValuationParams
	 * @param calibratableComponentArray Array of Calibration Instruments
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param recovery Component recovery
	 * @param flat Flat Calibration (True), or real bootstrapping (false)
	 * @param bump Amount of bump applied to the tenor
	 * @param discountCurve Base Discount Curve
	 * @param govvieCurve Govvie Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return Tenor named map of tenor bumped credit curves
	 */

	public static final CaseInsensitiveTreeMap<CreditCurve> TenorMap (
		final String name,
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final double recovery,
		final boolean flat,
		final double bump,
		final MergedDiscountForwardCurve discountCurve,
		final GovvieCurve govvieCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == valuationParams || null == calibratableComponentArray || null == calibrationQuoteArray ||
			null == calibrationMeasureArray || null == discountCurve) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;

		if (0 == componentCount || calibrationQuoteArray.length != componentCount ||
			calibrationMeasureArray.length != componentCount) {
			return null;
		}

		CaseInsensitiveTreeMap<CreditCurve> tenorCreditCurveMap = new CaseInsensitiveTreeMap<CreditCurve>();

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			CreditCurve creditCurve = null;
			double[] tenorQuoteArray = new double[componentCount];

			for (int bumpComponentIndex = 0; bumpComponentIndex < componentCount; ++bumpComponentIndex) {
				tenorQuoteArray[bumpComponentIndex] = calibrationQuoteArray[bumpComponentIndex] +
					(bumpComponentIndex == componentIndex ? bump : 0.);
			}

			if (null == (
				creditCurve = Standard (
					name,
					valuationParams,
					calibratableComponentArray,
					tenorQuoteArray,
					calibrationMeasureArray,
					recovery,
					flat,
					discountCurve,
					govvieCurve,
					latentStateFixingsContainer,
					valuationCustomizationParams,
					null
				)
			)) {
				return null;
			}

			tenorCreditCurveMap.put (
				DateUtil.YYYYMMDD (calibratableComponentArray[componentIndex].maturityDate().julian()),
				creditCurve
			);
		}

		return tenorCreditCurveMap;
	}
}
