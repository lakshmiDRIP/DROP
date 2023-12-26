
package org.drip.state.boot;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.input.BootCurveConstructionInput;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.LatentStateFixingsContainer;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.ExplicitBootDiscountCurve;
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
 * <i>DiscountCurveScenario</i> uses the interest rate calibration instruments along with the component
 * 	calibrator to produce scenario interest rate curves. DiscountCurveScenario typically first constructs the
 * 	actual curve calibrator instance to localize the intelligence around curve construction. It then uses this
 * 	curve calibrator instance to build individual curves or the sequence of node bumped scenario curves. The
 * 	curves in the set may be an array, or tenor-keyed. It exposes the following functions:
 *
 *  <ul>
 * 		<li>Calibrate a discount curve</li>
 * 		<li>Calibrate an array of tenor bumped discount curves</li>
 * 		<li>Calibrate a tenor map of tenor bumped discount curves</li>
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

public class DiscountCurveScenario
{

	/**
	 * Calibrate a discount curve
	 * 
	 * @param valuationParams ValuationParams
	 * @param calibratableComponentArray Array of Calibratable Components
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param bump Quote bump
	 * @param govvieCurve Govvie Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return DiscountCurve Instance
	 */

	public static final MergedDiscountForwardCurve Standard (
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final double bump,
		final GovvieCurve govvieCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == valuationParams || null == calibratableComponentArray || null == calibrationQuoteArray ||
			null == calibrationMeasureArray) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		double rateArray[] = new double[componentCount];
		int dateArray[] = new int[componentCount];

		if (0 == componentCount || calibrationQuoteArray.length != componentCount ||
			calibrationMeasureArray.length != componentCount) {
			return null;
		}

		String currency = calibratableComponentArray[0].payCurrency();

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			if (null == calibratableComponentArray[componentIndex] ||
				!currency.equalsIgnoreCase (calibratableComponentArray[componentIndex].payCurrency())) {
				return null;
			}

			rateArray[componentIndex] = 0.02;

			dateArray[componentIndex] = calibratableComponentArray[componentIndex].maturityDate().julian();
		}

		ExplicitBootDiscountCurve explicitBootDiscountCurve = ScenarioDiscountCurveBuilder.PiecewiseForward (
			new JulianDate (valuationParams.valueDate()),
			currency,
			dateArray,
			rateArray
		);

		if (!NonlinearCurveBuilder.DiscountCurve (
			valuationParams,
			calibratableComponentArray,
			calibrationQuoteArray,
			calibrationMeasureArray,
			bump,
			false,
			explicitBootDiscountCurve,
			govvieCurve,
			latentStateFixingsContainer,
			valuationCustomizationParams
		)) {
			return null;
		}

		explicitBootDiscountCurve.setCCIS (
			BootCurveConstructionInput.Create (
				valuationParams,
				valuationCustomizationParams,
				calibratableComponentArray,
				calibrationQuoteArray,
				calibrationMeasureArray,
				latentStateFixingsContainer
			)
		);

		return explicitBootDiscountCurve;
	}

	/**
	 * Calibrate an array of tenor bumped discount curves
	 * 
	 * @param valuationParams Valuation Parameters
	 * @param calibratableComponentArray Array of Calibratable Components
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param bump Quote bump
	 * @param govvieCurve Govvie Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return Array of tenor bumped discount curves
	 */

	public static final MergedDiscountForwardCurve[] Tenor (
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final double bump,
		final GovvieCurve govvieCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == calibratableComponentArray || !NumberUtil.IsValid (bump)) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		MergedDiscountForwardCurve[] discountCurveArray = new MergedDiscountForwardCurve[componentCount];

		if (0 == componentCount || calibrationQuoteArray.length != componentCount ||
			calibrationMeasureArray.length != componentCount) {
			return null;
		}

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			double[] tenorQuoteArray = new double [componentCount];

			for (int tenorComponentIndex = 0; tenorComponentIndex < componentCount; ++tenorComponentIndex) {
				tenorQuoteArray[tenorComponentIndex] = calibrationQuoteArray[tenorComponentIndex] +
					(tenorComponentIndex == componentIndex ? bump : 0.);
			}

			if (null == (
				discountCurveArray[componentIndex] = Standard (
					valuationParams,
					calibratableComponentArray,
					tenorQuoteArray,
					calibrationMeasureArray,
					0.,
					govvieCurve,
					latentStateFixingsContainer,
					valuationCustomizationParams
				)
			)) {
				return null;
			}
		}

		return discountCurveArray;
	}

	/**
	 * Calibrate a tenor map of tenor bumped discount curves
	 * 
	 * @param valuationParams ValuationParams
	 * @param calibratableComponentArray Array of Calibratable Components
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param bump Quote bump
	 * @param govvieCurve Govvie Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return Tenor map of tenor bumped discount curves
	 */

	public static final CaseInsensitiveTreeMap<MergedDiscountForwardCurve> TenorMap (
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final double bump,
		final GovvieCurve govvieCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == calibratableComponentArray || null == calibrationQuoteArray ||
			!NumberUtil.IsValid (bump)) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;

		if (0 == componentCount || calibrationQuoteArray.length != componentCount) {
			return null;
		}

		CaseInsensitiveTreeMap<MergedDiscountForwardCurve> tenorDiscountCurveMap =
			new CaseInsensitiveTreeMap<MergedDiscountForwardCurve>();

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			double[] tenorQuoteArray = new double [componentCount];

			for (int tenorComponentIndex = 0; tenorComponentIndex < componentCount; ++tenorComponentIndex) {
				tenorQuoteArray[tenorComponentIndex] = calibrationQuoteArray[tenorComponentIndex] +
					(tenorComponentIndex == componentIndex ? bump : 0.);
			}

			tenorDiscountCurveMap.put (
				DateUtil.YYYYMMDD (calibratableComponentArray[componentIndex].maturityDate().julian()),
				Standard (
					valuationParams,
					calibratableComponentArray,
					tenorQuoteArray,
					calibrationMeasureArray,
					0.,
					govvieCurve,
					latentStateFixingsContainer,
					valuationCustomizationParams
				)
			);
		}

		return tenorDiscountCurveMap;
	}
}
