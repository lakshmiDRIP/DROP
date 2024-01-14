
package org.drip.spline.stretch;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.segment.Monotonocity;

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
 * <i>SingleSegmentSequence</i> is the interface that exposes functionality that spans multiple segments. Its
 * 	derived instances hold the ordered segment sequence, the segment control parameters, and, if available,
 * 	the spanning Jacobian. SingleSegmentSequence exports the following group of functionality:
 *
 * <br>
 *  <ul>
 * 		<li>Set up (i.e., calibrate) the individual Segments in the Stretch to the Response Values corresponding to each Segment Predictor right Ordinate</li>
 * 		<li>Calculate the Response Value at the given Predictor Ordinate</li>
 * 		<li>Calculate the Response Value Derivative at the given Predictor Ordinate for the specified order</li>
 * 		<li>Calculate the Response Derivative to the Calibration Inputs at the specified Ordinate</li>
 * 		<li>Calculate the Response Derivative to the Manifest Measure at the specified Ordinate</li>
 * 		<li>Identify the Monotone Type for the Segment underlying the given Predictor Ordinate</li>
 * 		<li>Indicate if all the comprising Segments are Monotone</li>
 * 		<li>Verify whether the Stretch mini-max Behavior matches the Measurement</li>
 * 		<li>Is the given Predictor Ordinate a Knot Location</li>
 * 		<li>Reset the Predictor Ordinate Node Index with the given Response</li>
 * 		<li>Reset the Predictor Ordinate Node Index with the given Segment Constraint</li>
 * 		<li>Return the Left Predictor Ordinate Edge</li>
 * 		<li>Return the Right Predictor Ordinate Edge</li>
 * 		<li>Convert the Segment Sequence into an Abstract Univariate Instance</li>
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

public interface SingleSegmentSequence
{

	/**
	 * Set up (i.e., calibrate) the individual Segments in the Stretch to the Response Values corresponding
	 * 	to each Segment Predictor right Ordinate.
	 * 
	 * @param stretchLeadingResponse Stretch Left-most Response
	 * @param segmentRightEdgeResponseArray Array of Segment Right Edge Responses
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return TRUE - Set up was successful
	 */

	public abstract boolean setup (
		final double stretchLeadingResponse,
		final double[] segmentRightEdgeResponseArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail
	);

	/**
	 * Calculate the Response Value at the given Predictor Ordinate
	 * 
	 * @param predictorOrdinate Predictor Ordinate
	 * 
	 * @return The Response Value
	 * 
	 * @throws Exception Thrown if the Response Value cannot be calculated
	 */

	public abstract double responseValue (
		final double predictorOrdinate)
		throws Exception;

	/**
	 * Calculate the Response Value Derivative at the given Predictor Ordinate for the specified order
	 * 
	 * @param predictorOrdinate Predictor Ordinate
	 * @param order Order the Derivative
	 * 
	 * @return The Response Value
	 * 
	 * @throws Exception Thrown if the Response Value Derivative cannot be calculated
	 */

	public abstract double responseValueDerivative (
		final double predictorOrdinate,
		final int order)
		throws Exception;

	/**
	 * Calculate the Response Derivative to the Calibration Inputs at the specified Ordinate
	 * 
	 * @param predictorOrdinate Predictor Ordinate
	 * @param order Order of Derivative desired
	 * 
	 * @return Jacobian of the Response Derivative to the Calibration Inputs at the Ordinate
	 */

	public abstract WengertJacobian jackDResponseDCalibrationInput (
		final double predictorOrdinate,
		final int order
	);

	/**
	 * Calculate the Response Derivative to the Manifest Measure at the specified Ordinate
	 * 
	 * @param manifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param predictorOrdinate Predictor Ordinate
	 * @param order Order of Derivative desired
	 * 
	 * @return Jacobian of the Response Derivative to the Quote at the Ordinate
	 */

	public abstract WengertJacobian jackDResponseDManifestMeasure (
		final String manifestMeasure,
		final double predictorOrdinate,
		final int order
	);

	/**
	 * Identify the Monotone Type for the Segment underlying the given Predictor Ordinate
	 * 
	 * @param predictorOrdinate Predictor Ordinate
	 * 
	 * @return Segment Monotone Type
	 */

	public abstract Monotonocity monotoneType (
		final double predictorOrdinate
	);

	/**
	 * Indicate if all the comprising Segments are Monotone
	 * 
	 * @return TRUE - Fully locally monotonic
	 * 
	 * @throws Exception Thrown if the Segment Monotone Type could not be estimated
	 */

	public abstract boolean isLocallyMonotone()
		throws Exception;

	/**
	 * Verify whether the Stretch mini-max Behavior matches the Measurement
	 * 
	 * @param measuredResponseArray The Array of Measured Responses
	 * 
	 * @return TRUE - Stretch is co-monotonic with the measured Responses
	 * 
	 * @throws Exception Thrown if the Segment Monotone Type could not be estimated
	 */

	public abstract boolean isCoMonotone (
		final double[] measuredResponseArray)
		throws Exception;

	/**
	 * Is the given Predictor Ordinate a Knot Location
	 * 
	 * @param predictorOrdinate Predictor Ordinate
	 * 
	 * @return TRUE - Given Predictor Ordinate corresponds to a Knot
	 */

	public abstract boolean isKnot (
		final double predictorOrdinate
	);

	/**
	 * Reset the Predictor Ordinate Node Index with the given Response
	 * 
	 * @param predictorOrdinateNodeIndex The Predictor Ordinate Node Index whose Response is to be reset
	 * @param resetResponse The Response to reset
	 * 
	 * @return TRUE - Reset succeeded
	 */

	public abstract boolean resetNode (
		final int predictorOrdinateNodeIndex,
		final double resetResponse
	);

	/**
	 * Reset the Predictor Ordinate Node Index with the given Segment Constraint
	 * 
	 * @param nodeIndex The Predictor Ordinate Node Index whose Response is to be reset
	 * @param resetSegmentResponseValueConstraint The Segment Constraint
	 * 
	 * @return TRUE - Reset succeeded
	 */

	public abstract boolean resetNode (
		final int nodeIndex,
		final SegmentResponseValueConstraint resetSegmentResponseValueConstraint
	);

	/**
	 * Return the Left Predictor Ordinate Edge
	 * 
	 * @return The Left Predictor Ordinate Edge
	 */

	public abstract double getLeftPredictorOrdinateEdge();

	/**
	 * Return the Right Predictor Ordinate Edge
	 * 
	 * @return The Right Predictor Ordinate Edge
	 */

	public abstract double getRightPredictorOrdinateEdge();

	/**
	 * Convert the Segment Sequence into an AbstractUnivariate Instance
	 * 
	 * @return The AbstractUnivariate Instance
	 */

	public abstract R1ToR1 toAU();
}
