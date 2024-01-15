
package org.drip.spline.stretch;

import org.drip.numerical.common.NumberUtil;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentPredictorResponseDerivative;
import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.segment.LatentStateResponseModel;

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
 * <i>MultiSegmentSequenceModifier</i> exports Stretch modification/alteration methods to generate customized
 * basis splines, with customized segment behavior using the segment control. It exposes the following
 * stretch modification methods:
 *
 * <br>
 *  <ul>
 *  	<li>Insert the specified Predictor Ordinate Knot into the specified Stretch, using the specified Response Value</li>
 *  	<li>Append a Segment to the Right of the Specified Stretch using the Supplied Constraint</li>
 *  	<li>Insert the Predictor Ordinate Knot into the specified Stretch</li>
 *  	<li>Insert a Cardinal Knot into the specified Stretch at the specified Predictor Ordinate Location</li>
 *  	<li>Insert a Catmull-Rom Knot into the specified Stretch at the specified Predictor Ordinate Location</li>
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

public class MultiSegmentSequenceModifier
{

	/**
	 * Insert the specified Predictor Ordinate Knot into the specified Stretch, using the specified Response
	 * 	Value
	 * 
	 * @param inputMultiSegmentSequence Input Stretch
	 * @param predictorOrdinate Predictor Ordinate Knot
	 * @param responseValue Response Value
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return The Stretch with the Knot inserted
	 */

	public static final MultiSegmentSequence InsertKnot (
		final MultiSegmentSequence inputMultiSegmentSequence,
		final double predictorOrdinate,
		final double responseValue,
		final BoundarySettings boundarySettings,
		final int calibrationDetail)
	{
		if (!NumberUtil.IsValid (predictorOrdinate) ||
			!NumberUtil.IsValid (responseValue) ||
			null == inputMultiSegmentSequence || inputMultiSegmentSequence.isKnot (predictorOrdinate)) {
			return null;
		}

		LatentStateResponseModel[] latentStateResponseModelArray = inputMultiSegmentSequence.segments();

		int newIndex = 0;
		int inputSegmentCount = latentStateResponseModelArray.length;
		double[] responseValueArray = new double[inputSegmentCount + 2];
		double[] predictorOrdinateArray = new double[inputSegmentCount + 2];
		SegmentCustomBuilderControl[] outputSegmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[inputSegmentCount + 1];

		SegmentCustomBuilderControl[] inputSegmentCustomBuilderControlArray =
			inputMultiSegmentSequence.segmentBuilderControl();

		if (predictorOrdinate < latentStateResponseModelArray[0].left()) {
			responseValueArray[newIndex] = responseValue;
			predictorOrdinateArray[newIndex] = predictorOrdinate;
			outputSegmentCustomBuilderControlArray[newIndex++] = inputSegmentCustomBuilderControlArray[0];
		}

		for (int inputSegmentIndex = 0; inputSegmentIndex < inputSegmentCount; ++inputSegmentIndex) {
			outputSegmentCustomBuilderControlArray[newIndex] =
				inputSegmentCustomBuilderControlArray[inputSegmentIndex];

			predictorOrdinateArray[newIndex] = latentStateResponseModelArray[inputSegmentIndex].left();

			try {
				responseValueArray[newIndex++] = inputMultiSegmentSequence.responseValue (
					latentStateResponseModelArray[inputSegmentIndex].left()
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			if (predictorOrdinate > latentStateResponseModelArray[inputSegmentIndex].left() &&
				predictorOrdinate < latentStateResponseModelArray[inputSegmentIndex].right()) {
				responseValueArray[newIndex] = responseValue;
				predictorOrdinateArray[newIndex] = predictorOrdinate;
				outputSegmentCustomBuilderControlArray[newIndex++] =
					inputSegmentCustomBuilderControlArray[inputSegmentIndex];
			}
		}

		predictorOrdinateArray[newIndex] = latentStateResponseModelArray[inputSegmentCount - 1].right();

		try {
			responseValueArray[newIndex++] = inputMultiSegmentSequence.responseValue (
				latentStateResponseModelArray[inputSegmentCount - 1].right()
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		if (predictorOrdinate > latentStateResponseModelArray[inputSegmentCount - 1].right()) {
			responseValueArray[newIndex] = responseValue;
			predictorOrdinateArray[newIndex] = predictorOrdinate;
			outputSegmentCustomBuilderControlArray[outputSegmentCustomBuilderControlArray.length - 1] =
				inputSegmentCustomBuilderControlArray[inputSegmentCustomBuilderControlArray.length - 1];
		}

		return MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			inputMultiSegmentSequence.name(),
			predictorOrdinateArray,
			responseValueArray,
			outputSegmentCustomBuilderControlArray,
			null,
			boundarySettings,
			calibrationDetail
		);
	}

	/**
	 * Append a Segment to the Right of the Specified Stretch using the Supplied Constraint
	 * 
	 * @param inputMultiSegmentSequence Input Stretch
	 * @param predictorOrdinateAppendRight The Predictor Ordinate at the Right Edge of the Segment to be
	 * 	appended
	 * @param segmentResponseValueConstraint The Segment Response Value Constraint
	 * @param segmentCustomBuilderControl Segment Builder Parameters
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return The Stretch with the Segment Appended
	 */

	public static final MultiSegmentSequence AppendSegment (
		final MultiSegmentSequence inputMultiSegmentSequence,
		final double predictorOrdinateAppendRight,
		final SegmentResponseValueConstraint segmentResponseValueConstraint,
		final SegmentCustomBuilderControl segmentCustomBuilderControl,
		final BoundarySettings boundarySettings,
		final int calibrationDetail)
	{
		if (null == inputMultiSegmentSequence || null == segmentResponseValueConstraint ||
			null == segmentCustomBuilderControl || !NumberUtil.IsValid (predictorOrdinateAppendRight)) {
			return null;
		}

		double stretchPredictorOrdinateRight = inputMultiSegmentSequence.getRightPredictorOrdinateEdge();

		double[] constraintOrdinateArray = segmentResponseValueConstraint.predictorOrdinates();

		for (int constraintOrdinateIndex = 0; constraintOrdinateIndex < constraintOrdinateArray.length;
			++constraintOrdinateIndex) {
			if (constraintOrdinateArray[constraintOrdinateIndex] <= stretchPredictorOrdinateRight) {
				return null;
			}
		}

		LatentStateResponseModel[] latentStateResponseModelArray = inputMultiSegmentSequence.segments();

		SegmentCustomBuilderControl[] inputSegmentCustomBuilderControlArray =
			inputMultiSegmentSequence.segmentBuilderControl();

		double stretchResponseValueLeft = Double.NaN;
		int inputSegmentCount = latentStateResponseModelArray.length;
		double[] outputPredictorOrdinateArray = new double[inputSegmentCount + 2];
		SegmentCustomBuilderControl[] outputSegmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[inputSegmentCount + 1];
		SegmentResponseValueConstraint[] outputSegmentResponseValueConstraintArray =
			new SegmentResponseValueConstraint[inputSegmentCount + 1];

		try {
			stretchResponseValueLeft = inputMultiSegmentSequence.responseValue (
				inputMultiSegmentSequence.getLeftPredictorOrdinateEdge()
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int inputSegmentIndex = 0; inputSegmentIndex < inputSegmentCount; ++inputSegmentIndex) {
			outputSegmentCustomBuilderControlArray[inputSegmentIndex] =
				inputSegmentCustomBuilderControlArray[inputSegmentIndex];

			outputPredictorOrdinateArray[inputSegmentIndex] =
				latentStateResponseModelArray[inputSegmentIndex].left();

			double dblPredictorOrdinateRight = latentStateResponseModelArray[inputSegmentIndex].right();

			try {
				outputSegmentResponseValueConstraintArray[inputSegmentIndex] =
					new SegmentResponseValueConstraint (
						new double[] {dblPredictorOrdinateRight},
						new double[] {1.},
						inputMultiSegmentSequence.responseValue (dblPredictorOrdinateRight)
					);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		outputSegmentResponseValueConstraintArray[inputSegmentCount] = segmentResponseValueConstraint;
		outputSegmentCustomBuilderControlArray[inputSegmentCount] = segmentCustomBuilderControl;
		outputPredictorOrdinateArray[inputSegmentCount + 1] = predictorOrdinateAppendRight;

		outputPredictorOrdinateArray[inputSegmentCount] =
			latentStateResponseModelArray[inputSegmentCount - 1].right();

		return MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			inputMultiSegmentSequence.name(),
			outputPredictorOrdinateArray,
			stretchResponseValueLeft,
			outputSegmentResponseValueConstraintArray,
			outputSegmentCustomBuilderControlArray,
			null,
			boundarySettings,
			calibrationDetail
		);
	}

	/**
	 * Insert the Predictor Ordinate Knot into the specified Stretch
	 * 
	 * @param inputMultiSegmentSequence Input Stretch
	 * @param predictorOrdinate Knot Predictor Ordinate
	 * @param leftSegmentRightEdgePredictorResponseDerivative Response Values for the Right Edge of the Left
	 *  Segment
	 * @param rightSegmentLeftEdgePredictorResponseDerivative Response Values for the Left Edge of the Right
	 *  Segment
	 * 
	 * @return The Stretch with the Predictor Ordinate Knot inserted
	 */

	public static final MultiSegmentSequence InsertKnot (
		final MultiSegmentSequence inputMultiSegmentSequence,
		final double predictorOrdinate,
		final SegmentPredictorResponseDerivative leftSegmentRightEdgePredictorResponseDerivative,
		final SegmentPredictorResponseDerivative rightSegmentLeftEdgePredictorResponseDerivative)
	{
		if (!NumberUtil.IsValid (predictorOrdinate) ||
			null == inputMultiSegmentSequence || inputMultiSegmentSequence.isKnot (predictorOrdinate) ||
			null == leftSegmentRightEdgePredictorResponseDerivative ||
			null == rightSegmentLeftEdgePredictorResponseDerivative) {
			return null;
		}

		LatentStateResponseModel[] inputLatentStateResponseModelArray = inputMultiSegmentSequence.segments();

		int outputSegmentIndex = 1;
		int inputSegmentCount = inputLatentStateResponseModelArray.length;
		double[] outputPredictorOrdinateArray = new double[inputSegmentCount + 2];
		SegmentPredictorResponseDerivative[] outputLeftSegmentPredictorResponseDerivativeArray =
			new SegmentPredictorResponseDerivative[inputSegmentCount + 1];
		SegmentPredictorResponseDerivative[] outputRightSegmentPredictorResponseDerivativeArray =
			new SegmentPredictorResponseDerivative[inputSegmentCount + 1];
		SegmentCustomBuilderControl[] outputSegmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[inputSegmentCount + 1];

		if (predictorOrdinate < inputLatentStateResponseModelArray[0].left() ||
			predictorOrdinate > inputLatentStateResponseModelArray[inputSegmentCount - 1].right()) {
			return null;
		}

		outputPredictorOrdinateArray[0] = inputLatentStateResponseModelArray[0].left();

		SegmentCustomBuilderControl[] inputSegmentCustomBuilderControlArray =
			inputMultiSegmentSequence.segmentBuilderControl();

		for (int inputSegmentIndex = 0; inputSegmentIndex < inputSegmentCount; ++inputSegmentIndex) {
			outputSegmentCustomBuilderControlArray[outputSegmentIndex - 1] = inputSegmentCustomBuilderControlArray[inputSegmentIndex];

			outputLeftSegmentPredictorResponseDerivativeArray[outputSegmentIndex - 1] =
				inputMultiSegmentSequence.calcSPRD (
					inputLatentStateResponseModelArray[inputSegmentIndex].left()
				);

			if (predictorOrdinate > inputLatentStateResponseModelArray[inputSegmentIndex].left() &&
				predictorOrdinate < inputLatentStateResponseModelArray[inputSegmentIndex].right()) {
				outputRightSegmentPredictorResponseDerivativeArray[outputSegmentIndex - 1] =
					leftSegmentRightEdgePredictorResponseDerivative;
				outputPredictorOrdinateArray[outputSegmentIndex++] = predictorOrdinate;
				outputSegmentCustomBuilderControlArray[outputSegmentIndex - 1] =
					inputSegmentCustomBuilderControlArray[inputSegmentIndex];
				outputLeftSegmentPredictorResponseDerivativeArray[outputSegmentIndex - 1] =
					rightSegmentLeftEdgePredictorResponseDerivative;
			}

			outputRightSegmentPredictorResponseDerivativeArray[outputSegmentIndex - 1] =
				inputMultiSegmentSequence.calcSPRD (
					inputLatentStateResponseModelArray[inputSegmentIndex].right()
				);

			outputPredictorOrdinateArray[outputSegmentIndex++] =
				inputLatentStateResponseModelArray[inputSegmentIndex].right();
		}

		MultiSegmentSequence outputMultiSegmentSequence =
			MultiSegmentSequenceBuilder.CreateUncalibratedStretchEstimator (
				inputMultiSegmentSequence.name(),
				outputPredictorOrdinateArray,
				outputSegmentCustomBuilderControlArray
			);

		return null == outputMultiSegmentSequence ? null : outputMultiSegmentSequence.setupHermite (
			outputLeftSegmentPredictorResponseDerivativeArray,
			outputRightSegmentPredictorResponseDerivativeArray,
			null,
			null,
			MultiSegmentSequence.CALIBRATE
		) ? outputMultiSegmentSequence : null;
	}

	/**
	 * Insert a Cardinal Knot into the specified Stretch at the specified Predictor Ordinate Location
	 * 
	 * @param inputMultiSegmentSequence Input Stretch
	 * @param predictorOrdinate Knot Predictor Ordinate
	 * @param cardinalTension Cardinal Tension Parameter
	 * 
	 * @return The Stretch with the Knot inserted
	 */

	public static final MultiSegmentSequence InsertCardinalKnot (
		final MultiSegmentSequence inputMultiSegmentSequence,
		final double predictorOrdinate,
		final double cardinalTension)
	{
		if (!NumberUtil.IsValid (predictorOrdinate) ||
			!NumberUtil.IsValid (cardinalTension) ||
			null == inputMultiSegmentSequence || inputMultiSegmentSequence.isKnot (predictorOrdinate)) {
			return null;
		}

		LatentStateResponseModel[] inputLatentStateResponseModelArray = inputMultiSegmentSequence.segments();

		int outputSegmentIndex = 0;
		int inputSegmentCount = inputLatentStateResponseModelArray.length;

		if (predictorOrdinate < inputLatentStateResponseModelArray[0].left() ||
			predictorOrdinate > inputLatentStateResponseModelArray[inputSegmentCount - 1].right()) {
			return null;
		}

		for (; outputSegmentIndex < inputSegmentCount; ++outputSegmentIndex) {
			if (predictorOrdinate > inputLatentStateResponseModelArray[outputSegmentIndex].left() &&
				predictorOrdinate < inputLatentStateResponseModelArray[outputSegmentIndex].right()) {
				break;
			}
		}

		SegmentPredictorResponseDerivative outputSegmentPredictorResponseDerivativeCardinal =
			SegmentPredictorResponseDerivative.CardinalEdgeAggregate (
				inputMultiSegmentSequence.calcSPRD (
					inputLatentStateResponseModelArray[outputSegmentIndex].left()
				),
				inputMultiSegmentSequence.calcSPRD (
					inputLatentStateResponseModelArray[outputSegmentIndex].right()
				),
				cardinalTension
			);

		return null == outputSegmentPredictorResponseDerivativeCardinal ? null : InsertKnot (
			inputMultiSegmentSequence,
			predictorOrdinate,
			outputSegmentPredictorResponseDerivativeCardinal,
			outputSegmentPredictorResponseDerivativeCardinal
		);
	}

	/**
	 * Insert a Catmull-Rom Knot into the specified Stretch at the specified Predictor Ordinate Location
	 * 
	 * @param inputMultiSegmentSequence Input Stretch
	 * @param knotOrdinate Knot Predictor Ordinate
	 * 
	 * @return The Stretch with the Knot inserted
	 */

	public static final MultiSegmentSequence InsertCatmullRomKnot (
		final MultiSegmentSequence inputMultiSegmentSequence,
		final double knotOrdinate)
	{
		return InsertCardinalKnot (inputMultiSegmentSequence, knotOrdinate, 0.);
	}
}
