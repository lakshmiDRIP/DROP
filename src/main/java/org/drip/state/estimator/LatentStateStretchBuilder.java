
package org.drip.state.estimator;

import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.calib.ProductQuoteSet;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.fx.ComponentPair;
import org.drip.state.identifier.FXLabel;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.identifier.FundingLabel;
import org.drip.state.inference.LatentStateSegmentSpec;
import org.drip.state.inference.LatentStateStretchSpec;
import org.drip.state.representation.LatentStateSpecification;

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
 * <i>LatentStateStretchBuilder</i> contains the Functionality to construct the Curve Latent State Stretch
 * 	for the different Latent States. It provides Functionality to:
 *
 *  <ul>
 *  	<li>Construct a Forward Latent State Stretch Spec Instance</li>
 *  	<li>Construct a Merged Forward-Funding Latent State Stretch Spec Instance</li>
 *  	<li>Construct a Funding Latent State Stretch Spec Instance</li>
 *  	<li>Construct an instance of LatentStateStretchSpec for the Construction of the Forward Curve from the specified Inputs</li>
 *  	<li>Construct an instance of LatentStateStretchSpec for the Construction of the Discount Curve from the specified Inputs</li>
 *  	<li>Construct a FX Latent State Stretch Spec Instance</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator/README.md">Multi-Pass Customized Stretch Curve</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateStretchBuilder
{

	/**
	 * Construct a Forward Latent State Stretch Spec Instance
	 * 
	 * @param stretchName Stretch Name
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param calibrationMeasureArray Array of the Calibration Measures
	 * @param calibrationQuoteArray Array of the Calibration Quotes
	 * 
	 * @return Forward Latent State Stretch Spec Instance
	 */

	public static final LatentStateStretchSpec ForwardStretchSpec (
		final String stretchName,
		final CalibratableComponent[] calibratableComponentArray,
		final String[] calibrationMeasureArray,
		final double[] calibrationQuoteArray)
	{
		if (null == calibratableComponentArray || null == calibrationMeasureArray ||
			null == calibrationQuoteArray) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		LatentStateSegmentSpec[] latentStateSegmentSpeArray = new LatentStateSegmentSpec[componentCount];

		if (0 == componentCount || componentCount != calibrationMeasureArray.length ||
			componentCount != calibrationQuoteArray.length) {
			return null;
		}

		try {
			for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
				if (null == calibrationMeasureArray[componentIndex] ||
					null == calibrationMeasureArray[componentIndex] ||
					calibrationMeasureArray[componentIndex].isEmpty() ||
					!NumberUtil.IsValid (calibrationQuoteArray[componentIndex])) {
					return null;
				}

				CaseInsensitiveTreeMap<ForwardLabel> forwardLabelMap =
					calibratableComponentArray[componentIndex].forwardLabel();

				if (null == forwardLabelMap || 0 == forwardLabelMap.size()) {
					return null;
				}

				ProductQuoteSet productQuoteSet = calibratableComponentArray[componentIndex].calibQuoteSet (
					new LatentStateSpecification[] {
						new LatentStateSpecification (
							LatentStateStatic.LATENT_STATE_FORWARD,
							LatentStateStatic.FORWARD_QM_FORWARD_RATE,
							forwardLabelMap.get ("DERIVED")
						)
					}
				);

				if (null == productQuoteSet || !productQuoteSet.set (
					calibrationMeasureArray[componentIndex],
					calibrationQuoteArray[componentIndex])) {
					return null;
				}

				latentStateSegmentSpeArray[componentIndex] = new LatentStateSegmentSpec (
					calibratableComponentArray[componentIndex],
					productQuoteSet
				);
			}

			return new LatentStateStretchSpec (stretchName, latentStateSegmentSpeArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Merged Forward-Funding Latent State Stretch Spec Instance
	 * 
	 * @param stretchName Stretch Name
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param calibrationMeasureArray Array of the Calibration Measures
	 * @param calibrationQuoteArray Array of the Calibration Quotes
	 * 
	 * @return Merged Forward-Funding Latent State Stretch Spec Instance
	 */

	public static final LatentStateStretchSpec ForwardFundingStretchSpec (
		final String stretchName,
		final CalibratableComponent[] calibratableComponentArray,
		final String[] calibrationMeasureArray,
		final double[] calibrationQuoteArray)
	{
		if (null == calibratableComponentArray || null == calibrationMeasureArray ||
			null == calibrationQuoteArray) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		LatentStateSegmentSpec[] latentStateSegmentSpecArray = new LatentStateSegmentSpec[componentCount];

		if (0 == componentCount || componentCount != calibrationMeasureArray.length ||
			componentCount != calibrationQuoteArray.length) {
			return null;
		}

		try {
			for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
				if (null == calibratableComponentArray[componentIndex] ||
					null == calibrationMeasureArray[componentIndex] ||
					calibrationMeasureArray[componentIndex].isEmpty() ||
					!NumberUtil.IsValid (calibrationQuoteArray[componentIndex])) {
					return null;
				}

				CaseInsensitiveTreeMap<ForwardLabel> forwardLabelMap =
					calibratableComponentArray[componentIndex].forwardLabel();

				if (null == forwardLabelMap || 0 == forwardLabelMap.size()) {
					return null;
				}

				ProductQuoteSet productQuoteSet = calibratableComponentArray[componentIndex].calibQuoteSet (
					new LatentStateSpecification[] {
						new LatentStateSpecification (
							LatentStateStatic.LATENT_STATE_FUNDING,
							LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
							FundingLabel.Standard (calibratableComponentArray[componentIndex].payCurrency())
						),
						new LatentStateSpecification (
							LatentStateStatic.LATENT_STATE_FORWARD,
							LatentStateStatic.FORWARD_QM_FORWARD_RATE,
							forwardLabelMap.get ("DERIVED")
						)
					}
				);

				if (null == productQuoteSet ||
					!productQuoteSet.set (
						calibrationMeasureArray[componentIndex],
						calibrationQuoteArray[componentIndex]
					)
				) {
					return null;
				}

				latentStateSegmentSpecArray[componentIndex] = new LatentStateSegmentSpec (
					calibratableComponentArray[componentIndex],
					productQuoteSet
				);
			}

			return new LatentStateStretchSpec (stretchName, latentStateSegmentSpecArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Funding Latent State Stretch Spec Instance
	 * 
	 * @param stretchName Stretch Name
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param calibrationMeasureArray Array of the Calibration Measures
	 * @param calibrationQuoteArray Array of the Calibration Quotes
	 * 
	 * @return Funding Latent State Stretch Spec Instance
	 */

	public static final org.drip.state.inference.LatentStateStretchSpec FundingStretchSpec (
		final String stretchName,
		final CalibratableComponent[] calibratableComponentArray,
		final String[] calibrationMeasureArray,
		final double[] calibrationQuoteArray)
	{
		if (null == calibratableComponentArray || null == calibrationMeasureArray ||
			null == calibrationQuoteArray) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		LatentStateSegmentSpec[] latentStateSegmentSpecArray = new LatentStateSegmentSpec[componentCount];

		if (0 == componentCount || componentCount != calibrationMeasureArray.length ||
			componentCount != calibrationQuoteArray.length) {
			return null;
		}

		try {
			for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
				if (null == calibratableComponentArray[componentIndex] ||
					null == calibrationMeasureArray[componentIndex] ||
					calibrationMeasureArray[componentIndex].isEmpty() ||
					!NumberUtil.IsValid (calibrationQuoteArray[componentIndex])) {
					return null;
				}

				CaseInsensitiveTreeMap<ForwardLabel> forwardLabelMap =
					calibratableComponentArray[componentIndex].forwardLabel();

				if (null == forwardLabelMap || 0 == forwardLabelMap.size()) {
					return null;
				}

				ProductQuoteSet productQuoteSet = calibratableComponentArray[componentIndex].calibQuoteSet (
					new LatentStateSpecification[] {
						new LatentStateSpecification (
							LatentStateStatic.LATENT_STATE_FUNDING,
							LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
							FundingLabel.Standard (calibratableComponentArray[componentIndex].payCurrency())
						)
					}
				);

				if (null == productQuoteSet ||
					!productQuoteSet.set (
						calibrationMeasureArray[componentIndex],
						calibrationQuoteArray[componentIndex]
					)
				) {
					return null;
				}

				latentStateSegmentSpecArray[componentIndex] = new LatentStateSegmentSpec (
					calibratableComponentArray[componentIndex],
					productQuoteSet
				);
			}

			return new LatentStateStretchSpec (stretchName, latentStateSegmentSpecArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Forward Latent State Stretch Spec Instance
	 * 
	 * @param stretchName Stretch Name
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param calibrationMeasure The Calibration Measure
	 * @param calibrationQuoteArray Array of the Calibration Quotes
	 * 
	 * @return Forward Latent State Stretch Spec Instance
	 */

	public static final LatentStateStretchSpec ForwardStretchSpec (
		final String stretchName,
		final CalibratableComponent[] calibratableComponentArray,
		final String calibrationMeasure,
		final double[] calibrationQuoteArray)
	{
		if (null == calibrationMeasure || calibrationMeasure.isEmpty() || null == calibrationQuoteArray) {
			return null;
		}

		int componentCount = calibrationQuoteArray.length;
		String[] calibrationMeasureArray = new String[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			calibrationMeasureArray[componentIndex] = calibrationMeasure;
		}

		return ForwardStretchSpec (
			stretchName,
			calibratableComponentArray,
			calibrationMeasureArray,
			calibrationQuoteArray
		);
	}

	/**
	 * Construct a Merged Forward-Funding Latent State Stretch Spec Instance
	 * 
	 * @param stretchName Stretch Name
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param calibrationMeasure The Calibration Measure
	 * @param calibrationQuoteArray Array of the Calibration Quotes
	 * 
	 * @return Merged Forward-Funding Latent State Stretch Spec Instance
	 */

	public static final LatentStateStretchSpec ForwardFundingStretchSpec (
		final String stretchName,
		final CalibratableComponent[] calibratableComponentArray,
		final String calibrationMeasure,
		final double[] calibrationQuoteArray)
	{
		if (null == calibrationMeasure || calibrationMeasure.isEmpty() || null == calibrationQuoteArray) {
			return null;
		}

		int componentCount = calibrationQuoteArray.length;
		String[] calibrationMeasureArray = new String[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			calibrationMeasureArray[componentIndex] = calibrationMeasure;
		}

		return ForwardFundingStretchSpec (
			stretchName,
			calibratableComponentArray,
			calibrationMeasureArray,
			calibrationQuoteArray
		);
	}

	/**
	 * Construct a Funding Latent State Stretch Spec Instance
	 * 
	 * @param stretchName Stretch Name
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param calibrationMeasure The Calibration Measure
	 * @param calibrationQuoteArray Array of the Calibration Quotes
	 * 
	 * @return Funding Latent State Stretch Spec Instance
	 */

	public static final LatentStateStretchSpec FundingStretchSpec (
		final String stretchName,
		final CalibratableComponent[] calibratableComponentArray,
		final String calibrationMeasure,
		final double[] calibrationQuoteArray)
	{
		if (null == calibrationMeasure || calibrationMeasure.isEmpty() || null == calibrationQuoteArray) {
			return null;
		}

		int componentCount = calibrationQuoteArray.length;
		String[] calibrationMeasureArray = new String[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			calibrationMeasureArray[componentIndex] = calibrationMeasure;
		}

		return FundingStretchSpec (
			stretchName,
			calibratableComponentArray,
			calibrationMeasureArray,
			calibrationQuoteArray
		);
	}

	/**
	 * Construct an instance of LatentStateStretchSpec for the Construction of the Forward Curve from the
	 * 	specified Inputs
	 * 
	 * @param stretchName Stretch Name
	 * @param componentPairArray Array of Calibration Cross Currency Swap Pair Instances
	 * @param valuationParams The Valuation Parameters
	 * @param curveSurfaceQuoteContainer The Basket Market Parameters to imply the Market Quote Measure
	 * @param basisArray Array of the Basis on either the Reference Component or the Derived Component
	 * @param basisOnDerivedComponent TRUE - Apply the Basis on the Derived Component
	 * @param basisOnDerivedStream TRUE - Apply the Basis on the Derived Stream (FALSE - Reference Stream)
	 * 
	 * @return Instance of LatentStateStretchSpec
	 */

	public static final LatentStateStretchSpec ComponentPairForwardStretch (
		final String stretchName,
		final ComponentPair[] componentPairArray,
		final ValuationParams valuationParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final double[] basisArray,
		final boolean basisOnDerivedComponent,
		final boolean basisOnDerivedStream)
	{
		if (null == componentPairArray || null == curveSurfaceQuoteContainer || null == basisArray) {
			return null;
		}

		int componentPairCount = componentPairArray.length;

		if (0 == componentPairCount || basisArray.length != componentPairCount) {
			return null;
		}

		LatentStateSegmentSpec[] latentStateSegmentSpecArray =
			new LatentStateSegmentSpec[componentPairCount];

		for (int componentPairIndex = 0; componentPairIndex < componentPairCount; ++componentPairIndex) {
			if (null == componentPairArray[componentPairIndex]) return null;

			try {
				if (null == (
					latentStateSegmentSpecArray[componentPairIndex] =
						componentPairArray[componentPairIndex].derivedForwardSpec (
							valuationParams,
							curveSurfaceQuoteContainer,
							basisArray[componentPairIndex],
							basisOnDerivedComponent,
							basisOnDerivedStream
						)
					)
				) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new LatentStateStretchSpec (stretchName, latentStateSegmentSpecArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of LatentStateStretchSpec for the Construction of the Discount Curve from the
	 * 	specified Inputs
	 * 
	 * @param stretchName Stretch Name
	 * @param componentPairArray Array of Calibration Cross Currency Swap Pair Instances
	 * @param valuationParams The Valuation Parameters
	 * @param curveSurfaceQuoteContainer The Basket Market Parameters to imply the Market Quote Measure
	 * @param referenceComponentBasisArray Array of the Reference Component Reference Leg Basis Spread
	 * @param swapRateArray Array of the IRS Calibration Swap Rates
	 * @param basisOnDerivedLeg TRUE - Apply the Basis on the Derived Leg (FALSE - Reference Leg)
	 * 
	 * @return Instance of LatentStateStretchSpec
	 */

	public static final LatentStateStretchSpec ComponentPairDiscountStretch (
		final String stretchName,
		final ComponentPair[] componentPairArray,
		final ValuationParams valuationParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final double[] referenceComponentBasisArray,
		final double[] swapRateArray,
		final boolean basisOnDerivedLeg)
	{
		if (null == componentPairArray || null == curveSurfaceQuoteContainer ||
			null == referenceComponentBasisArray || null == swapRateArray) {
			return null;
		}

		int componentPairCount = componentPairArray.length;

		if (0 == componentPairCount || referenceComponentBasisArray.length != componentPairCount ||
			swapRateArray.length != componentPairCount) {
			return null;
		}

		LatentStateSegmentSpec[] latentStateSegmentSpecArray =
			new LatentStateSegmentSpec[componentPairCount];

		for (int componentPairIndex = 0; componentPairIndex < componentPairCount; ++componentPairIndex) {
			if (null == (
				latentStateSegmentSpecArray[componentPairIndex] =
					componentPairArray[componentPairIndex].derivedFundingForwardSpec (
						valuationParams,
						curveSurfaceQuoteContainer,
						referenceComponentBasisArray[componentPairIndex],
						basisOnDerivedLeg,
						swapRateArray[componentPairIndex]
					)
				)
			) {
				return null;
			}
		}

		try {
			return new LatentStateStretchSpec (stretchName, latentStateSegmentSpecArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a FX Latent State Stretch Spec Instance
	 * 
	 * @param stretchName Stretch Name
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param calibrationMeasureArray Array of the Calibration Measures
	 * @param calibrationQuoteArray Array of the Calibration Quotes
	 * 
	 * @return FX Latent State Stretch Spec Instance
	 */

	public static final LatentStateStretchSpec FXStretchSpec (
		final String stretchName,
		final CalibratableComponent[] calibratableComponentArray,
		final String[] calibrationMeasureArray,
		final double[] calibrationQuoteArray)
	{
		if (null == calibratableComponentArray || null == calibrationMeasureArray ||
			null == calibrationQuoteArray) {
			return null;
		}

		int componentCount = calibratableComponentArray.length;
		LatentStateSegmentSpec[] latentStateSegmentSpecArray = new LatentStateSegmentSpec[componentCount];

		if (0 == componentCount || componentCount != calibrationMeasureArray.length ||
			componentCount != calibrationQuoteArray.length) {
			return null;
		}

		try {
			for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
				if (null == calibratableComponentArray[componentIndex] ||
					null == calibrationMeasureArray[componentIndex] ||
					calibrationMeasureArray[componentIndex].isEmpty() ||
					!NumberUtil.IsValid (calibrationQuoteArray[componentIndex])) {
					return null;
				}

				CaseInsensitiveTreeMap<FXLabel> fxLabelMap =
					calibratableComponentArray[componentIndex].fxLabel();

				if (null == fxLabelMap || 0 == fxLabelMap.size()) {
					return null;
				}

				ProductQuoteSet productQuoteSet = calibratableComponentArray[componentIndex].calibQuoteSet (
					new LatentStateSpecification[] {
						new LatentStateSpecification (
							LatentStateStatic.LATENT_STATE_FX,
							LatentStateStatic.FX_QM_FORWARD_OUTRIGHT,
							fxLabelMap.get ("DERIVED")
						)
					}
				);

				if (null == productQuoteSet || !productQuoteSet.set (
					calibrationMeasureArray[componentIndex],
					calibrationQuoteArray[componentIndex]
				)) {
					return null;
				}

				latentStateSegmentSpecArray[componentIndex] = new LatentStateSegmentSpec (
					calibratableComponentArray[componentIndex],
					productQuoteSet
				);
			}

			return new LatentStateStretchSpec (stretchName, latentStateSegmentSpecArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a FX Latent State Stretch Spec Instance
	 * 
	 * @param stretchName Stretch Name
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param calibrationMeasure The Calibration Measure
	 * @param calibrationQuoteArray Array of the Calibration Quotes
	 * 
	 * @return FX Latent State Stretch Spec Instance
	 */

	public static final LatentStateStretchSpec FXStretchSpec (
		final String stretchName,
		final CalibratableComponent[] calibratableComponentArray,
		final String calibrationMeasure,
		final double[] calibrationQuoteArray)
	{
		if (null == calibrationMeasure || calibrationMeasure.isEmpty() || null == calibrationQuoteArray) {
			return null;
		}

		int componentCount = calibrationQuoteArray.length;
		String[] calibrationMeasureArray = new String[componentCount];

		for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
			calibrationMeasureArray[componentIndex] = calibrationMeasure;
		}

		return FXStretchSpec (
			stretchName,
			calibratableComponentArray,
			calibrationMeasureArray,
			calibrationQuoteArray
		);
	}
}
