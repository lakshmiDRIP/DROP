
package org.drip.state.inference;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.params.PreceedingManifestSensitivityControl;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.estimator.CurveStretch;
import org.drip.state.estimator.GlobalControlCurveParams;

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
 * <i>LinearLatentStateCalibrator</i> calibrates/constructs the Latent State Stretch/Span from the
 * calibration instrument details. The span construction may be customized using specific settings provided
 * in GlobalControlCurveParams. It implements the following Functionality.
 * 
 *  <ul>
 *		<li><i>LinearLatentStateCalibrator</i> Constructor</li>
 *		<li>Calibrate the Span from the Instruments in the Stretches and their Details.</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/inference/README.md">Latent State Stretch Sequence Inference</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LinearLatentStateCalibrator extends GlobalControlCurveParams
{

	/**
	 * <i>LinearLatentStateCalibrator</i> constructor
	 * 
	 * @param segmentCustomBuilderControl Segment Builder Control Parameters
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * @param stretchBestFitResponse Curve Fitness Weighted Response
	 * @param stretchBestFitResponseSensitivity Curve Fitness Weighted Response Sensitivity
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public LinearLatentStateCalibrator (
		final SegmentCustomBuilderControl segmentCustomBuilderControl,
		final BoundarySettings boundarySettings,
		final int calibrationDetail,
		final StretchBestFitResponse stretchBestFitResponse,
		final StretchBestFitResponse stretchBestFitResponseSensitivity)
		throws Exception
	{
		super (
			"",
			segmentCustomBuilderControl,
			boundarySettings,
			calibrationDetail,
			stretchBestFitResponse,
			stretchBestFitResponseSensitivity
		);
	}

	/**
	 * Calibrate the Span from the Instruments in the Stretches and their Details.
	 * 
	 * @param latentStateStretchSpecArray The Stretch Sequence constituting the Span
	 * @param epochResponse Segment Sequence Left-most Response Value
	 * @param valuationParams Valuation Parameter
	 * @param creditPricerParams Pricer Parameter
	 * @param valuationCustomizationParams The Valuation Customization Parameters
	 * @param curveSurfaceQuoteContainer The Market Parameters Surface and Quote
	 * 
	 * @return Instance of the Latent State Span
	 */

	public OverlappingStretchSpan calibrateSpan (
		final LatentStateStretchSpec[] latentStateStretchSpecArray,
		final double epochResponse,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final ValuationCustomizationParams valuationCustomizationParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer)
	{
		if (null == latentStateStretchSpecArray || null == valuationParams) {
			return null;
		}

		OverlappingStretchSpan overlappingStretchSpan = null;
		int stretchCount = latentStateStretchSpecArray.length;

		if (0 == stretchCount) {
			return null;
		}

		for (LatentStateStretchSpec latentStateStretchSpec : latentStateStretchSpecArray) {
			if (null == latentStateStretchSpec) {
				continue;
			}

			LatentStateSegmentSpec[] latentStateSegmentSpecArray = latentStateStretchSpec.segmentSpec();

			CurveStretch curveStretch = null;
			int calibrationComponentCount = latentStateSegmentSpecArray.length;
			double[] predictorOrdinateArray = new double[calibrationComponentCount + 1];
			SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
				new SegmentCustomBuilderControl[calibrationComponentCount];

			for (int calibrationComponentIndex = 0; calibrationComponentIndex <= calibrationComponentCount;
				++calibrationComponentIndex) {
				predictorOrdinateArray[calibrationComponentIndex] = 0 == calibrationComponentIndex ?
					valuationParams.valueDate() :
					latentStateSegmentSpecArray[calibrationComponentIndex - 1].component().maturityDate().julian();

				if (calibrationComponentIndex != calibrationComponentCount) {
					segmentCustomBuilderControlArray[calibrationComponentIndex] =
						segmentBuilderControl (latentStateStretchSpec.name());
				}
			}

			try {
				curveStretch = new CurveStretch (
					latentStateStretchSpec.name(),
					MultiSegmentSequenceBuilder.CreateSegmentSet (
						predictorOrdinateArray,
						segmentCustomBuilderControlArray
					),
					segmentCustomBuilderControlArray
				);

				if (!curveStretch.setup (
					new LatentStateSequenceBuilder (
						epochResponse,
						latentStateStretchSpec,
						valuationParams,
						creditPricerParams,
						curveSurfaceQuoteContainer,
						valuationCustomizationParams,
						overlappingStretchSpan,
						bestFitWeightedResponse(),
						new CaseInsensitiveHashMap<PreceedingManifestSensitivityControl>(),
						bestFitWeightedResponseSensitivity(),
						calibrationBoundaryCondition()
					),
					calibrationDetail()
				)) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == overlappingStretchSpan) {
				try {
					overlappingStretchSpan = new OverlappingStretchSpan (curveStretch);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			} else {
				if (!overlappingStretchSpan.addStretch (curveStretch)) {
					return null;
				}
			}
		}

		return overlappingStretchSpan;
	}
}
