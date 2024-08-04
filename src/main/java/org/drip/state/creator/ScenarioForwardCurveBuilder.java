
package org.drip.state.creator;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.function.r1tor1custom.QuadraticRationalShapeControl;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.calib.ProductQuoteSet;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.DualStreamComponent;
import org.drip.spline.basis.FunctionSetBuilderParams;
import org.drip.spline.params.ResponseScalingShapeControl;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.inference.LatentStateSegmentSpec;
import org.drip.state.inference.LatentStateStretchSpec;
import org.drip.state.inference.LinearLatentStateCalibrator;
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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>ScenarioForwardCurveBuilder</i> implements the the construction of the scenario Forward curve using the
 * input discount curve instruments, and a wide variety of custom builds. It implements the following
 * functionality:
 *
 *  <ul>
 * 		<li>Build the Shape Preserving Forward Curve using the Custom Parameters</li>
 * 		<li>Construct an instance of the Shape Preserver of the desired basis type, using the specified basis set builder parameters</li>
 * 		<li>Construct an Instance of the Flat Forward Rate Forward Curve</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/README.md">Scenario State Curve/Surface Builders</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioForwardCurveBuilder {

	/**
	 * Build the Shape Preserving Forward Curve using the Custom Parameters
	 * 
	 * @param linearLatentStateCalibrator The Linear Latent State Calibrator Instance
	 * @param latentStateStretchSpecArray Array of the Latent State Stretches
	 * @param forwardLabel The Floating Rate Index Forward Label
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * @param epochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserving Discount Curve
	 */

	public static final ForwardCurve ShapePreservingForwardCurve (
		final LinearLatentStateCalibrator linearLatentStateCalibrator,
		final LatentStateStretchSpec[] latentStateStretchSpecArray,
		final ForwardLabel forwardLabel,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final double epochResponse)
	{
		if (null == linearLatentStateCalibrator) {
			return null;
		}

		try {
			ForwardCurve forwardCurve = new org.drip.state.curve.BasisSplineForwardRate (
				forwardLabel,
				linearLatentStateCalibrator.calibrateSpan (
					latentStateStretchSpecArray,
					epochResponse,
					valuationParams,
					creditPricerParams,
					valuationCustomizationParams,
					curveSurfaceQuoteContainer
				)
			);

			return forwardCurve.setCCIS (
				new org.drip.analytics.input.LatentStateShapePreservingCCIS (
					linearLatentStateCalibrator,
					latentStateStretchSpecArray,
					valuationParams,
					creditPricerParams,
					valuationCustomizationParams,
					curveSurfaceQuoteContainer
				)
			) ? forwardCurve : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of the Shape Preserver of the desired basis type, using the specified basis set
	 * 	builder parameters.
	 * 
	 * @param name Curve Name
	 * @param friForwardLabel The Floating Rate Index Forward Label
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * @param basisType The Basis Type
	 * @param functionSetBuilderParams The Function Set Basis Parameters
	 * @param calibrationComponentArray Array of Calibration Components
	 * @param manifestMeasure The Calibration Manifest Measure
	 * @param quoteArray Array of Calibration Quotes
	 * @param epochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final ForwardCurve ShapePreservingForwardCurve (
		final String name,
		final ForwardLabel friForwardLabel,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final String basisType,
		final FunctionSetBuilderParams functionSetBuilderParams,
		final CalibratableComponent[] calibrationComponentArray,
		final String manifestMeasure,
		final double[] quoteArray,
		final double epochResponse)
	{
		if (null == functionSetBuilderParams || null == manifestMeasure || manifestMeasure.isEmpty()) {
			return null;
		}

		int quoteCount = null == quoteArray ? 0 : quoteArray.length;
		int componentCount = null == calibrationComponentArray ? 0 : calibrationComponentArray.length;

		if (0 == componentCount || componentCount != quoteCount) {
			return null;
		}

		try {
			ForwardLabel forwardLabel = null;

			if (calibrationComponentArray[0] instanceof DualStreamComponent) {
				forwardLabel =
					((DualStreamComponent) calibrationComponentArray[0]).derivedStream().forwardLabel();
			} else {
				CaseInsensitiveTreeMap<ForwardLabel> forwardLabelMap =
					calibrationComponentArray[0].forwardLabel();

				if (null != forwardLabelMap && 0 != forwardLabelMap.size()) {
					forwardLabel = forwardLabelMap.get ("BASE");
				}
			}

			LatentStateSpecification[] latentStateSpecificationArray = new LatentStateSpecification[] {
				new LatentStateSpecification (
					LatentStateStatic.LATENT_STATE_FORWARD,
					LatentStateStatic.FORWARD_QM_FORWARD_RATE,
					forwardLabel
				)
			};

			LatentStateSegmentSpec[] latentStateSegmentSpecArray =
				new LatentStateSegmentSpec[componentCount];

			for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
				ProductQuoteSet productQuoteSet = calibrationComponentArray[componentIndex].calibQuoteSet
					(latentStateSpecificationArray);

				if (null == productQuoteSet || !productQuoteSet.set (
					manifestMeasure,
					quoteArray[componentIndex]
				)) {
					return null;
				}

				latentStateSegmentSpecArray[componentIndex] = new LatentStateSegmentSpec (
					calibrationComponentArray[componentIndex],
					productQuoteSet
				);
			}

			return ShapePreservingForwardCurve (
				new LinearLatentStateCalibrator (
					new SegmentCustomBuilderControl (
						basisType,
						functionSetBuilderParams,
						org.drip.spline.params.SegmentInelasticDesignControl.Create (2, 2),
						new ResponseScalingShapeControl (true, new QuadraticRationalShapeControl (0.)),
						null
					),
					BoundarySettings.FinancialStandard(),
					MultiSegmentSequence.CALIBRATE,
					null,
					null
				),
				new LatentStateStretchSpec[] {
					new LatentStateStretchSpec (name, latentStateSegmentSpecArray)
				},
				friForwardLabel,
				valuationParams,
				creditPricerParams,
				curveSurfaceQuoteContainer,
				valuationCustomizationParams,
				epochResponse
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Flat Forward Rate Forward Curve
	 * 
	 * @param startDate The Forward Curve Start Date
	 * @param forwardLabel The Floating Rate Index
	 * @param flatForwardRate The Flat Forward Rate
	 * 
	 * @return Instance of the Flat Forward Rate Forward Curve
	 */

	public static final ForwardCurve FlatForwardForwardCurve (
		final JulianDate startDate,
		final ForwardLabel forwardLabel,
		final double flatForwardRate)
	{
		try {
			return new org.drip.state.nonlinear.FlatForwardForwardCurve (
				startDate,
				forwardLabel,
				flatForwardRate
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
