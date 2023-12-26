
package org.drip.state.boot;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.LatentStateFixingsContainer;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.fra.FRAStandardCapFloor;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.LatentStateLabel;
import org.drip.state.identifier.VolatilityLabel;
import org.drip.state.nonlinear.FlatForwardVolatilityCurve;
import org.drip.state.nonlinear.NonlinearCurveBuilder;
import org.drip.state.volatility.ExplicitBootVolatilityCurve;
import org.drip.state.volatility.VolatilityCurve;

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
 * <i>VolatilityCurveScenario</i> uses the Volatility calibration instruments along with the component
 * calibrator to produce scenario Volatility curves. It exposes the following functions:
 *
 *  <ul>
 * 		<li>Calibrate a Volatility Curve</li>
 * 		<li>Create an array of tenor bumped Volatility curves</li>
 * 		<li>Create an tenor named map of tenor bumped Volatility curves</li>
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

public class VolatilityCurveScenario {

	/**
	 * Calibrate a Volatility Curve
	 * 
	 * @param name Volatility Curve name
	 * @param valuationParams ValuationParams
	 * @param underlyingLatentStateLabel Underlying Latent State Label
	 * @param fraStandardCapFloorArray Array of the FRA Cap Floor Instruments
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param flat Flat Calibration (True), or real bootstrapping (false)
	 * @param discountCurve Discount Curve
	 * @param forwardCurve Forward Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return VolatilityCurve Instance
	 */

	public static final org.drip.state.volatility.VolatilityCurve Standard (
		final String name,
		final ValuationParams valuationParams,
		final LatentStateLabel underlyingLatentStateLabel,
		final FRAStandardCapFloor[] fraStandardCapFloorArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final boolean flat,
		final MergedDiscountForwardCurve discountCurve,
		final ForwardCurve forwardCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == fraStandardCapFloorArray || null == calibrationQuoteArray ||
			null == calibrationMeasureArray || null == discountCurve) {
			return null;
		}

		int componentCount = fraStandardCapFloorArray.length;
		int[] pillarDateArray = new int[componentCount];
		double[] volatilityArray = new double[componentCount];
		ExplicitBootVolatilityCurve explicitBootVolatilityCurve = null;

		if (0 == componentCount || calibrationQuoteArray.length != componentCount ||
			calibrationMeasureArray.length != componentCount) {
			return null;
		}

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			if (null == fraStandardCapFloorArray[componentIndex]) {
				return null;
			}

			volatilityArray[componentIndex] = 0.001;

			pillarDateArray[componentIndex] =
				fraStandardCapFloorArray[componentIndex].stream().maturity().julian();
		}

		try {
			explicitBootVolatilityCurve = new FlatForwardVolatilityCurve (
				discountCurve.epoch().julian(),
				VolatilityLabel.Standard (underlyingLatentStateLabel),
				discountCurve.currency(),
				pillarDateArray,
				volatilityArray
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			try {
				NonlinearCurveBuilder.VolatilityCurveNode (
					valuationParams,
					fraStandardCapFloorArray[componentIndex],
					calibrationQuoteArray[componentIndex],
					calibrationMeasureArray[componentIndex],
					flat,
					componentIndex,
					explicitBootVolatilityCurve,
					discountCurve,
					forwardCurve,
					latentStateFixingsContainer,
					valuationCustomizationParams
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return explicitBootVolatilityCurve;
	}

	/**
	 * Create an array of tenor bumped Volatility curves
	 * 
	 * @param name Volatility Curve Name
	 * @param valuationParams ValuationParams
	 * @param underlyingLatentStateLabel Underlying Latent State Label
	 * @param fraStandardCapFloorArray Array of the FRA Cap Floor Instruments
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param flat Flat Calibration (True), or real bootstrapping (false)
	 * @param bump Amount of bump applied to the tenor
	 * @param discountCurve Base Discount Curve
	 * @param forwardCurve Forward Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return Array of Volatility Curves
	 */

	public static final org.drip.state.volatility.VolatilityCurve[] Tenor (
		final String name,
		final ValuationParams valuationParams,
		final LatentStateLabel underlyingLatentStateLabel,
		final FRAStandardCapFloor[] fraStandardCapFloorArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final boolean flat,
		final double bump,
		final MergedDiscountForwardCurve discountCurve,
		final ForwardCurve forwardCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == fraStandardCapFloorArray || !NumberUtil.IsValid (bump)) {
			return null;
		}

		int componentCount = fraStandardCapFloorArray.length;
		VolatilityCurve[] volatilityCurveArray = new VolatilityCurve[componentCount];

		if (0 == componentCount) {
			return null;
		}

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			double[] tenorQuoteArray = new double[componentCount];

			for (int bumpIndex = 0; bumpIndex < componentCount; ++bumpIndex) {
				tenorQuoteArray[bumpIndex] = calibrationQuoteArray[bumpIndex] +
					(bumpIndex == componentIndex ? bump : 0.);
			}

			if (null == (
				volatilityCurveArray[componentIndex] = Standard (
					name,
					valuationParams,
					underlyingLatentStateLabel,
					fraStandardCapFloorArray,
					tenorQuoteArray,
					calibrationMeasureArray,
					flat,
					discountCurve,
					forwardCurve,
					latentStateFixingsContainer,
					valuationCustomizationParams
				)
			)) {
				return null;
			}
		}

		return volatilityCurveArray;
	}

	/**
	 * Create an tenor named map of tenor bumped Volatility curves
	 * 
	 * @param name Volatility Curve name
	 * @param valuationParams ValuationParams
	 * @param underlyingLatentStateLabel Underlying Latent State Label
	 * @param fraStandardCapFloorArray Array of the FRA Cap Floor Instruments
	 * @param calibrationQuoteArray Array of component quotes
	 * @param calibrationMeasureArray Array of the calibration measures
	 * @param flat Flat Calibration (True), or real bootstrapping (false)
	 * @param bump Amount of bump applied to the tenor
	 * @param discountCurve Base Discount Curve
	 * @param forwardCurve Forward Curve
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * 
	 * @return Tenor named map of tenor bumped Volatility curves
	 */

	public CaseInsensitiveTreeMap<VolatilityCurve> TenorMap (
		final String name,
		final ValuationParams valuationParams,
		final LatentStateLabel underlyingLatentStateLabel,
		final FRAStandardCapFloor[] fraStandardCapFloorArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final boolean flat,
		final double bump,
		final MergedDiscountForwardCurve discountCurve,
		final ForwardCurve forwardCurve,
		final LatentStateFixingsContainer latentStateFixingsContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == fraStandardCapFloorArray || !NumberUtil.IsValid (bump)) {
			return null;
		}

		int componentCount = fraStandardCapFloorArray.length;

		if (0 == componentCount) {
			return null;
		}

		CaseInsensitiveTreeMap<VolatilityCurve> tenorVolatilityCurveMap =
			new CaseInsensitiveTreeMap<VolatilityCurve>();

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			VolatilityCurve volatilityCurve = null;
			double[] tenorQuoteArray = new double[componentCount];

			for (int bumpComponentIndex = 0; bumpComponentIndex < componentCount; ++bumpComponentIndex) {
				tenorQuoteArray[bumpComponentIndex] = calibrationQuoteArray[bumpComponentIndex] +
					(bumpComponentIndex == componentIndex ? bump : 0.);
			}

			if (null == (
				volatilityCurve = Standard (
					name,
					valuationParams,
					underlyingLatentStateLabel,
					fraStandardCapFloorArray,
					tenorQuoteArray,
					calibrationMeasureArray,
					flat,
					discountCurve,
					forwardCurve,
					latentStateFixingsContainer,
					valuationCustomizationParams
				)
			)) {
				return null;
			}

			tenorVolatilityCurveMap.put (
				DateUtil.YYYYMMDD (fraStandardCapFloorArray[componentIndex].maturityDate().julian()),
				volatilityCurve
			);
		}

		return tenorVolatilityCurveMap;
	}
}
