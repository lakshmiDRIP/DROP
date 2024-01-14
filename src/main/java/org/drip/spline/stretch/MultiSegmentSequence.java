	
package org.drip.spline.stretch;

import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentPredictorResponseDerivative;
import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.segment.LatentStateResponseModel;
import org.drip.state.representation.MergeSubStretchManager;

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
 * <i>MultiSegmentSequence</i> is the interface that exposes functionality that spans multiple segments. Its
 * 	derived instances hold the ordered segment sequence, the segment control parameters, and, if available,
 * 	the spanning Jacobian. <i>MultiSegmentSequence</i> exports the following group of functionality:
 *
 * <br>
 *  <ul>
 * 		<li>CALIBRATE</li>
 * 		<li>CALIBRATE_JACOBIAN</li>
 * 		<li>Retrieve the Stretch Name</li>
 * 		<li>Retrieve the Segment Builder Parameters</li>
 * 		<li>Set up (i.e., calibrate) the individual Segment in the Stretch to the Target Segment Edge Values and Constraints. This is also called the Hermite setup - where the segment boundaries are entirely locally set</li>
 * 		<li>Set the Slope at the left Edge of the Stretch</li>
 * 		<li>Calculate the <i>SegmentPredictorResponseDerivative</i> at the specified Predictor Ordinate</li>
 * 		<li>Calculate the Derivative of the requested order at the Left Edge of the Stretch</li>
 * 		<li>Calculate the Derivative of the requested order at the Right Edge of the Stretch</li>
 * 		<li>Check if the Predictor Ordinate is in the Stretch Range</li>
 * 		<li>Return the Index for the Segment containing specified Predictor Ordinate</li>
 * 		<li>Set up (i.e., calibrate) the individual Segments in the Stretch to the Stretch Edge, the Target Constraints, and the custom segment sequence builder</li>
 * 		<li>Set up (i.e., calibrate) the individual Segments in the Stretch to the Stretch Left Edge and the Target Constraints</li>
 * 		<li>Set up (i.e., calibrate) the individual Segments in the Stretch to the Stretch Left Edge Response and the Target Constraints</li>
 * 		<li>Generate a new Stretch by clipping all the Segments to the Left of the specified Predictor Ordinate. Smoothness Constraints will be maintained</li>
 * 		<li>Generate a new Stretch by clipping all the Segments to the Right of the specified Predictor Ordinate. Smoothness Constraints will be maintained</li>
 * 		<li>Retrieve the Span Curvature DPE</li>
 * 		<li>Retrieve the Span Length DPE</li>
 * 		<li>Retrieve the Stretch Best Fit DPE</li>
 * 		<li>Retrieve the Merge Stretch Manager if it exists</li>
 * 		<li>Display the Segments</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public interface MultiSegmentSequence
	extends SingleSegmentSequence
{

	/**
	 * Calibration Detail: Calibrate the Stretch as part of the set up
	 */

	public static final int CALIBRATE = 1;

	/**
	 * Calibration Detail: Calibrate the Stretch AND compute Jacobian as part of the set up
	 */

	public static final int CALIBRATE_JACOBIAN = 2;

	/**
	 * Retrieve the Stretch Name
	 * 
	 * @return The Stretch Name
	 */

	public abstract String name();

	/**
	 * Retrieve the Segment Builder Parameters
	 * 
	 * @return The Segment Builder Parameters
	 */

	public abstract SegmentCustomBuilderControl[] segmentBuilderControl();

	/**
	 * Retrieve the Stretch Segments
	 * 
	 * @return The Stretch Segments
	 */

	public abstract LatentStateResponseModel[] segments();

	/**
	 * Set up (i.e., calibrate) the individual Segment in the Stretch to the Target Segment Edge Values and
	 * 	Constraints. This is also called the Hermite setup - where the segment boundaries are entirely
	 * 	locally set.
	 * 
	 * @param leftSegmentPredictorResponseDerivativeArray Array of Left Segment Edge Values
	 * @param rightSegmentPredictorResponseDerivativeArray Array of Right Segment Edge Values
	 * @param segmentResponseValueConstraintGrid Double Array of Constraints - Outer Index corresponds to
	 *  Segment Index, and the Inner Index to Constraint Array within each Segment
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param setupMode Set up Mode (i.e., set up ITEP only, or fully calibrate the Stretch, or calibrate
	 *  Stretch plus compute Jacobian)
	 * 
	 * @return TRUE - Set up was successful
	 */

	public abstract boolean setupHermite (
		final SegmentPredictorResponseDerivative[] leftSegmentPredictorResponseDerivativeArray,
		final SegmentPredictorResponseDerivative[] rightSegmentPredictorResponseDerivativeArray,
		final SegmentResponseValueConstraint[][] segmentResponseValueConstraintGrid,
		final StretchBestFitResponse stretchBestFitResponse,
		final int setupMode
	);

	/**
	 * Set the Slope at the left Edge of the Stretch
	 * 
	 * @param stretchLeftResponse Response Value at the Left Edge of the Stretch
	 * @param stretchLeftResponseSlope Response Slope Value at the Left Edge of the Stretch
	 * @param stretchRightResponse Response Value at the Right Edge of the Stretch
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * 
	 * @return TRUE - Left slope successfully set
	 */

	public abstract boolean setLeftNode (
		final double stretchLeftResponse,
		final double stretchLeftResponseSlope,
		final double stretchRightResponse,
		final StretchBestFitResponse stretchBestFitResponse
	);

	/**
	 * Calculate the <i>SegmentPredictorResponseDerivative</i> at the specified Predictor Ordinate
	 * 
	 * @param predictorOrdinate The Predictor Ordinate
	 * 
	 * @return The Computed <i>SegmentPredictorResponseDerivative</i>
	 */

	public abstract SegmentPredictorResponseDerivative calcSPRD (
		final double predictorOrdinate
	);

	/**
	 * Calculate the Derivative of the requested order at the Left Edge of the Stretch
	 * 
	 * @param order Order of the Derivative
	 * 
	 * @return The Derivative of the requested order at the Left Edge of the Stretch
	 * 
	 * @throws Exception Thrown if the Derivative cannot be calculated
	 */

	public abstract double calcLeftEdgeDerivative (
		final int order)
		throws Exception;

	/**
	 * Calculate the Derivative of the requested order at the right Edge of the Stretch
	 * 
	 * @param order Order of the Derivative
	 * 
	 * @return The Derivative of the requested order at the right Edge of the Stretch
	 * 
	 * @throws Exception Thrown if the Derivative cannot be calculated
	 */

	public abstract double calcRightEdgeDerivative (
		final int order)
		throws Exception;

	/**
	 * Check if the Predictor Ordinate is in the Stretch Range
	 * 
	 * @param predictorOrdinate Predictor Ordinate
	 * 
	 * @return TRUE - Predictor Ordinate is in the Range
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public abstract boolean in (
		final double predictorOrdinate)
		throws Exception;

	/**
	 * Return the Index for the Segment containing specified Predictor Ordinate
	 * 
	 * @param predictorOrdinate Predictor Ordinate
	 * @param includeLeft TRUE - Less than or equal to the Left Predictor Ordinate
	 * @param includeRight TRUE - Less than or equal to the Right Predictor Ordinate
	 * 
	 * @return Index for the Segment containing specified Predictor Ordinate
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public int containingIndex (
		final double predictorOrdinate,
		final boolean includeLeft,
		final boolean includeRight)
		throws Exception;

	/**
	 * Set up (i.e., calibrate) the individual Segments in the Stretch to the Stretch Edge, the Target
	 *  Constraints, and the custom segment sequence builder.
	 * 
	 * @param segmentSequenceBuilder The Segment Sequence Builder Instance
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return TRUE - Set up was successful
	 */

	public abstract boolean setup (
		final SegmentSequenceBuilder segmentSequenceBuilder,
		final int calibrationDetail
	);

	/**
	 * Set up (i.e., calibrate) the individual Segments in the Stretch to the Stretch Left Edge and the
	 *  Target Constraints.
	 * 
	 * @param leadingSegmentResponseValueConstraint Stretch Left-most Segment Response Value Constraint
	 * @param segmentResponseValueConstraintArray Array of Segment Response Value Constraints
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return TRUE - Set up was successful
	 */

	public abstract boolean setup (
		final SegmentResponseValueConstraint leadingSegmentResponseValueConstraint,
		final SegmentResponseValueConstraint[] segmentResponseValueConstraintArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail
	);

	/**
	 * Set up (i.e., calibrate) the individual Segments in the Stretch to the Stretch Left Edge Response and
	 * 	the Target Constraints.
	 * 
	 * @param stretchLeftResponseValue Stretch Left-most Response Value
	 * @param segmentResponseValueConstraintArray Array of Segment Response Value Constraints
	 * @param stretchBestFitResponse Stretch Best Fit Weighted Response Values
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return TRUE - Set up was successful
	 */

	public abstract boolean setup (
		final double stretchLeftResponseValue,
		final SegmentResponseValueConstraint[] segmentResponseValueConstraintArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail
	);

	/**
	 * Generate a new Stretch by clipping all the Segments to the Left of the specified Predictor Ordinate.
	 *  Smoothness Constraints will be maintained.
	 * 
	 * @param name Name of the Clipped Stretch 
	 * @param predictorOrdinate Predictor Ordinate Left of which the Clipping is to be applied
	 * 
	 * @return The Clipped Stretch
	 */

	public abstract MultiSegmentSequence clipLeft (
		final String name,
		final double predictorOrdinate
	);

	/**
	 * Generate a new Stretch by clipping all the Segments to the Right of the specified Predictor Ordinate.
	 * 	Smoothness Constraints will be maintained.
	 * 
	 * @param name Name of the Clipped Stretch 
	 * @param predictorOrdinate Predictor Ordinate Right of which the Clipping is to be applied
	 * 
	 * @return The Clipped Stretch
	 */

	public abstract MultiSegmentSequence clipRight (
		final String name,
		final double predictorOrdinate
	);

	/**
	 * Retrieve the Span Curvature DPE
	 * 
	 * @return The Span Curvature DPE
	 * 
	 * @throws Exception Thrown if the Span Curvature DPE cannot be computed
	 */

	public abstract double curvatureDPE()
		throws Exception;

	/**
	 * Retrieve the Span Length DPE
	 * 
	 * @return The Span Length DPE
	 * 
	 * @throws Exception Thrown if the Span Length DPE cannot be computed
	 */

	public abstract double lengthDPE()
		throws Exception;

	/**
	 * Retrieve the Stretch Best Fit DPE
	 * 
	 * @param stretchBestFitResponse Stretch Best Fit Weighted Response Values
	 * 
	 * @return The Stretch Best Fit DPE
	 * 
	 * @throws Exception Thrown if the Stretch Best Fit DPE cannot be computed
	 */

	public abstract double bestFitDPE (
		final StretchBestFitResponse stretchBestFitResponse)
		throws Exception;

	/**
	 * Retrieve the Merge Stretch Manager if it exists.
	 * 
	 * @return The Merge Stretch Manager
	 */

	public MergeSubStretchManager msm();

	/**
	 * Display the Segments
	 * 
	 * @return The Segments String
	 */

	public abstract String displayString();
}
