
package org.drip.spline.stretch;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>MultiSegmentSequenceModifier</i> exports Stretch modification/alteration methods to generate customized
 * basis splines, with customized segment behavior using the segment control. It exposes the following
 * stretch modification methods:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Insert the specified Predictor Ordinate Knot into the specified Stretch, using the specified
 *  			Response Value
 *  	</li>
 *  	<li>
 * 			Append a Segment to the Right of the Specified Stretch using the Supplied Constraint
 *  	</li>
 *  	<li>
 * 			Insert the Predictor Ordinate Knot into the specified Stretch
 *  	</li>
 *  	<li>
 * 			Insert a Cardinal Knot into the specified Stretch at the specified Predictor Ordinate Location
 *  	</li>
 *  	<li>
 * 			Insert a Catmull-Rom Knot into the specified Stretch at the specified Predictor Ordinate Location
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline">Spaces</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch">Stretch</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/SplineBuilder">Spline Builder Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultiSegmentSequenceModifier {

	/**
	 * Insert the specified Predictor Ordinate Knot into the specified Stretch, using the specified Response
	 * 	Value
	 * 
	 * @param mssIn Input Stretch
	 * @param dblPredictorOrdinate Predictor Ordinate Knot
	 * @param dblResponseValue Response Value
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return The Stretch with the Knot inserted
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence InsertKnot (
		final org.drip.spline.stretch.MultiSegmentSequence mssIn,
		final double dblPredictorOrdinate,
		final double dblResponseValue,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblResponseValue) || null == mssIn || mssIn.isKnot
				(dblPredictorOrdinate))
			return null;

		org.drip.spline.segment.LatentStateResponseModel[] aCS = mssIn.segments();

		int iNewIndex = 0;
		int iNumSegmentIn = aCS.length;
		double[] adblResponseValue = new double[iNumSegmentIn + 2];
		double[] adblPredictorOrdinate = new double[iNumSegmentIn + 2];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBCOut = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumSegmentIn + 1];

		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBCIn = mssIn.segmentBuilderControl();

		if (dblPredictorOrdinate < aCS[0].left()) {
			adblPredictorOrdinate[iNewIndex] = dblPredictorOrdinate;
			adblResponseValue[iNewIndex] = dblResponseValue;
			aSCBCOut[iNewIndex++] = aSCBCIn[0];
		}

		for (int i = 0; i < iNumSegmentIn; ++i) {
			aSCBCOut[iNewIndex] = aSCBCIn[i];

			adblPredictorOrdinate[iNewIndex] = aCS[i].left();

			try {
				adblResponseValue[iNewIndex++] = mssIn.responseValue (aCS[i].left());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (dblPredictorOrdinate > aCS[i].left() && dblPredictorOrdinate < aCS[i].right()) {
				adblPredictorOrdinate[iNewIndex] = dblPredictorOrdinate;
				adblResponseValue[iNewIndex] = dblResponseValue;
				aSCBCOut[iNewIndex++] = aSCBCIn[i];
			}
		}

		adblPredictorOrdinate[iNewIndex] = aCS[iNumSegmentIn - 1].right();

		try {
			adblResponseValue[iNewIndex++] = mssIn.responseValue (aCS[iNumSegmentIn - 1].right());
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (dblPredictorOrdinate > aCS[iNumSegmentIn - 1].right()) {
			adblResponseValue[iNewIndex] = dblResponseValue;
			adblPredictorOrdinate[iNewIndex] = dblPredictorOrdinate;
			aSCBCOut[aSCBCOut.length - 1] = aSCBCIn[aSCBCIn.length - 1];
		}

		return org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
			(mssIn.name(), adblPredictorOrdinate, adblResponseValue, aSCBCOut, null, bs, iCalibrationDetail);
	}

	/**
	 * Append a Segment to the Right of the Specified Stretch using the Supplied Constraint
	 * 
	 * @param mssIn Input Stretch
	 * @param dblPredictorOrdinateAppendRight The Predictor Ordinate at the Right Edge of the Segment to be
	 * 	appended
	 * @param srvc The Segment Response Value Constraint
	 * @param scbc Segment Builder Parameters
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return The Stretch with the Segment Appended
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence AppendSegment (
		final org.drip.spline.stretch.MultiSegmentSequence mssIn,
		final double dblPredictorOrdinateAppendRight,
		final org.drip.spline.params.SegmentResponseValueConstraint srvc,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		if (null == mssIn || null == srvc || null == scbc || !org.drip.quant.common.NumberUtil.IsValid
			(dblPredictorOrdinateAppendRight))
			return null;

		double dblStretchPredictorOrdinateRight = mssIn.getRightPredictorOrdinateEdge();

		double[] adblConstraintOrdinate = srvc.predictorOrdinates();

		for (int i = 0; i < adblConstraintOrdinate.length; ++i) {
			if (adblConstraintOrdinate[i] <= dblStretchPredictorOrdinateRight) return null;
		}

		org.drip.spline.segment.LatentStateResponseModel[] aCS = mssIn.segments();

		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBCIn = mssIn.segmentBuilderControl();

		int iNumSegmentIn = aCS.length;
		double dblStretchResponseValueLeft = java.lang.Double.NaN;
		double[] adblPredictorOrdinateOut = new double[iNumSegmentIn + 2];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBCOut = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumSegmentIn + 1];
		org.drip.spline.params.SegmentResponseValueConstraint[] aSRVCOut = new
			org.drip.spline.params.SegmentResponseValueConstraint[iNumSegmentIn + 1];

		try {
			dblStretchResponseValueLeft = mssIn.responseValue (mssIn.getLeftPredictorOrdinateEdge());
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < iNumSegmentIn; ++i) {
			aSCBCOut[i] = aSCBCIn[i];

			adblPredictorOrdinateOut[i] = aCS[i].left();

			double dblPredictorOrdinateRight = aCS[i].right();

			try {
				aSRVCOut[i] = new org.drip.spline.params.SegmentResponseValueConstraint (new double[]
					{dblPredictorOrdinateRight}, new double[] {1.}, mssIn.responseValue
						(dblPredictorOrdinateRight));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		aSRVCOut[iNumSegmentIn] = srvc;
		aSCBCOut[iNumSegmentIn] = scbc;
		adblPredictorOrdinateOut[iNumSegmentIn + 1] = dblPredictorOrdinateAppendRight;

		adblPredictorOrdinateOut[iNumSegmentIn] = aCS[iNumSegmentIn - 1].right();

		return org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
			(mssIn.name(), adblPredictorOrdinateOut, dblStretchResponseValueLeft, aSRVCOut, aSCBCOut, null,
				bs, iCalibrationDetail);
	}

	/**
	 * Insert the Predictor Ordinate Knot into the specified Stretch
	 * 
	 * @param mssIn Input Stretch
	 * @param dblPredictorOrdinate Knot Predictor Ordinate
	 * @param sprdLeftSegmentRightEdge Response Values for the Right Edge of the Left Segment
	 * @param sprdRightSegmentLeftEdge Response Values for the Left Edge of the Right segment
	 * 
	 * @return The Stretch with the Predictor Ordinate Knot inserted
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence InsertKnot (
		final org.drip.spline.stretch.MultiSegmentSequence mssIn,
		final double dblPredictorOrdinate,
		final org.drip.spline.params.SegmentPredictorResponseDerivative sprdLeftSegmentRightEdge,
		final org.drip.spline.params.SegmentPredictorResponseDerivative sprdRightSegmentLeftEdge)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate) || null == mssIn ||
			mssIn.isKnot (dblPredictorOrdinate) || null == sprdLeftSegmentRightEdge || null ==
				sprdRightSegmentLeftEdge)
			return null;

		org.drip.spline.segment.LatentStateResponseModel[] aCSIn = mssIn.segments();

		int iOutSegmentIndex = 1;
		int iNumSegmentIn = aCSIn.length;
		double[] adblPredictorOrdinateOut = new double[iNumSegmentIn + 2];
		org.drip.spline.params.SegmentPredictorResponseDerivative[] aSPRDOutLeft = new
			org.drip.spline.params.SegmentPredictorResponseDerivative[iNumSegmentIn + 1];
		org.drip.spline.params.SegmentPredictorResponseDerivative[] aSPRDOutRight = new
			org.drip.spline.params.SegmentPredictorResponseDerivative[iNumSegmentIn + 1];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBCOut = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumSegmentIn + 1];

		if (dblPredictorOrdinate < aCSIn[0].left() || dblPredictorOrdinate >
			aCSIn[iNumSegmentIn - 1].right())
			return null;

		adblPredictorOrdinateOut[0] = aCSIn[0].left();

		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBCIn = mssIn.segmentBuilderControl();

		for (int i = 0; i < iNumSegmentIn; ++i) {
			aSCBCOut[iOutSegmentIndex - 1] = aSCBCIn[i];

			aSPRDOutLeft[iOutSegmentIndex - 1] = mssIn.calcSPRD (aCSIn[i].left());

			if (dblPredictorOrdinate > aCSIn[i].left() && dblPredictorOrdinate < aCSIn[i].right()) {
				aSPRDOutRight[iOutSegmentIndex - 1] = sprdLeftSegmentRightEdge;
				adblPredictorOrdinateOut[iOutSegmentIndex++] = dblPredictorOrdinate;
				aSCBCOut[iOutSegmentIndex - 1] = aSCBCIn[i];
				aSPRDOutLeft[iOutSegmentIndex - 1] = sprdRightSegmentLeftEdge;
			}

			aSPRDOutRight[iOutSegmentIndex - 1] = mssIn.calcSPRD (aCSIn[i].right());

			adblPredictorOrdinateOut[iOutSegmentIndex++] = aCSIn[i].right();
		}

		org.drip.spline.stretch.MultiSegmentSequence mssOut =
			org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateUncalibratedStretchEstimator
				(mssIn.name(), adblPredictorOrdinateOut, aSCBCOut);

		if (null == mssOut) return null;

		return mssOut.setupHermite (aSPRDOutLeft, aSPRDOutRight, null, null,
			org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE) ? mssOut : null;
	}

	/**
	 * Insert a Cardinal Knot into the specified Stretch at the specified Predictor Ordinate Location
	 * 
	 * @param mssIn Input Stretch
	 * @param dblPredictorOrdinate Knot Predictor Ordinate
	 * @param dblCardinalTension Cardinal Tension Parameter
	 * 
	 * @return The Stretch with the Knot inserted
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence InsertCardinalKnot (
		final org.drip.spline.stretch.MultiSegmentSequence mssIn,
		final double dblPredictorOrdinate,
		final double dblCardinalTension)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblCardinalTension) || null == mssIn || mssIn.isKnot
				(dblPredictorOrdinate))
			return null;

		org.drip.spline.segment.LatentStateResponseModel[] aCSIn = mssIn.segments();

		int iOutSegmentIndex = 0;
		int iNumSegmentIn = aCSIn.length;

		if (dblPredictorOrdinate < aCSIn[0].left() || dblPredictorOrdinate >
			aCSIn[iNumSegmentIn - 1].right())
			return null;

		for (; iOutSegmentIndex < iNumSegmentIn; ++iOutSegmentIndex) {
			if (dblPredictorOrdinate > aCSIn[iOutSegmentIndex].left() && dblPredictorOrdinate <
				aCSIn[iOutSegmentIndex].right())
				break;
		}

		org.drip.spline.params.SegmentPredictorResponseDerivative sprdCardinalOut =
			org.drip.spline.params.SegmentPredictorResponseDerivative.CardinalEdgeAggregate
				(mssIn.calcSPRD (aCSIn[iOutSegmentIndex].left()), mssIn.calcSPRD
					(aCSIn[iOutSegmentIndex].right()), dblCardinalTension);

		return null == sprdCardinalOut ? null : InsertKnot (mssIn, dblPredictorOrdinate, sprdCardinalOut,
			sprdCardinalOut);
	}

	/**
	 * Insert a Catmull-Rom Knot into the specified Stretch at the specified Predictor Ordinate Location
	 * 
	 * @param mssIn Input Stretch
	 * @param dblPredictorOrdinate Knot Predictor Ordinate
	 * 
	 * @return The Stretch with the Knot inserted
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence InsertCatmullRomKnot (
		final org.drip.spline.stretch.MultiSegmentSequence mssIn,
		final double dblPredictorOrdinate)
	{
		return InsertCardinalKnot (mssIn, dblPredictorOrdinate, 0.);
	}
}
