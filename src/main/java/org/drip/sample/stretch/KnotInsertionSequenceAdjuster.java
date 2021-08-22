
package org.drip.sample.stretch;

import org.drip.function.r1tor1.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>KnotInsertionSequenceAdjuster</i> demonstrates the Stretch Manipulation and Adjustment API. It shows
 * 	the following:
 * 	- Construct a simple Base Stretch.
 * 	- Clip a left Portion of the Stretch to construct a left-clipped Stretch.
 * 	- Clip a right Portion of the Stretch to construct a tight-clipped Stretch.
 *  - Compare the values across all the stretches to establish a) the continuity in the base smoothness is,
 *  	preserved, and b) Continuity across the predictor ordinate for the implied response value is also
 *  	preserved.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stretch/README.md">Knot Insertion Curvature Roughness Penalty</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class KnotInsertionSequenceAdjuster {

	/*
	 * Build Polynomial Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl PolynomialSegmentControlParams (
		final int iNumBasis,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (iNumBasis),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Basis Spline Stretch Test Sample. Performs the following:
	 * 	- Construct the Array of Segment Builder Parameters - one per segment.
	 *  - Construct the Stretch instance.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final MultiSegmentSequence BasisSplineStretchTest (
		final double[] adblX,
		final double[] adblY,
		final SegmentCustomBuilderControl scbc)
		throws Exception
	{
		/*
		 * Array of Segment Builder Parameters - one per segment
		 */

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		/*
		 * Construct a Stretch instance 
		 */

		return MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			"SPLINE_STRETCH",
			adblX, // predictors
			adblY, // responses
			aSCBC, // Basis Segment Builder parameters
			null,
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);
	}

	/*
	 * The Stretch Adjuster Test - this brings it altogether.
	 */

	private static final void StretchAdjusterTest()
		throws Exception
	{
		/*
		 * X predictors
		 */

		double[] adblX = new double[] { 1.00,  1.50,  2.00, 3.00, 4.00, 5.00, 6.50, 8.00, 10.00};

		/*
		 * Y responses
		 */

		double[] adblY = new double[] {25.00, 20.25, 16.00, 9.00, 4.00, 1.00, 0.25, 4.00, 16.00};

		/*
		 * Construct a rational shape controller with the shape controller tension of 1.
		 */

		double dblShapeControllerTension = 1.;

		ResponseScalingShapeControl rssc = new ResponseScalingShapeControl (
			false,
			new QuadraticRationalShapeControl (dblShapeControllerTension)
		);

		/*
		 * Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
		 * 	roughness penalty derivative, and without constraint
		 */

		int iK = 2;
		int iRoughnessPenaltyDerivativeOrder = 2;

		SegmentInelasticDesignControl sdic = SegmentInelasticDesignControl.Create (
			iK,
			iRoughnessPenaltyDerivativeOrder
		);

		/*
		 * Build the polynomial basis spline segment control parameters, and set up the stretch
		 */

		System.out.println (" \n---------- \n POLYNOMIAL \n ---------- \n");

		int iPolyNumBasis = 4;

		SegmentCustomBuilderControl scbc = PolynomialSegmentControlParams (
			iPolyNumBasis,
			sdic,
			rssc
		);

		MultiSegmentSequence mssBase = BasisSplineStretchTest (
			adblX,
			adblY,
			scbc
		);

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
		 */

		double dblX = mssBase.getLeftPredictorOrdinateEdge();

		double dblXMax = mssBase.getRightPredictorOrdinateEdge();

		while (dblX <= dblXMax) {
			System.out.println ("Y[" + dblX + "] " + FormatUtil.FormatDouble (mssBase.responseValue (dblX), 1, 2, 1.) + " | "
				+ mssBase.monotoneType (dblX));

			System.out.println ("Jacobian Y[" + dblX + "]=" + mssBase.jackDResponseDCalibrationInput (dblX, 1).displayString());

			dblX += 1.;
		}

		/*
		 * Clip part of the stretch left of the specified predictor ordinate
		 */

		System.out.println ("\tSPLINE_STRETCH_BASE DPE: " + mssBase.curvatureDPE());

		System.out.println (" \n---------- \n LEFT CLIPPED \n ---------- \n");

		MultiSegmentSequence mssLeftClipped = mssBase.clipLeft (
			"LEFT_CLIP",
			1.66
		);

		dblX = mssBase.getLeftPredictorOrdinateEdge();

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian of the left clipped stretch
		 */

		while (dblX <= dblXMax) {
			if (mssLeftClipped.in (dblX)) {
				System.out.println ("Y[" + dblX + "] " + FormatUtil.FormatDouble (mssLeftClipped.responseValue (dblX), 1, 2, 1.) + " | "
					+ mssLeftClipped.monotoneType (dblX));

				System.out.println ("Jacobian Y[" + dblX + "]=" + mssLeftClipped.jackDResponseDCalibrationInput (dblX, 1).displayString());
			}

			dblX += 1.;
		}

		/*
		 * Left clipped stretch DPE
		 */

		System.out.println ("\tSPLINE_STRETCH_LEFT DPE: " + mssLeftClipped.curvatureDPE());

		/*
		 * Clip part of the stretch right of the specified predictor ordinate
		 */

		System.out.println (" \n---------- \n RIGHT CLIPPED \n ---------- \n");

		MultiSegmentSequence mssRightClipped = mssBase.clipRight (
			"RIGHT_CLIP",
			7.48
		);

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian of the right clipped stretch
		 */

		dblX = mssBase.getLeftPredictorOrdinateEdge();

		while (dblX <= dblXMax) {
			if (mssRightClipped.in (dblX)) {
				System.out.println ("Y[" + dblX + "] " + FormatUtil.FormatDouble (mssRightClipped.responseValue (dblX), 1, 2, 1.) + " | "
					+ mssRightClipped.monotoneType (dblX));

				System.out.println ("Jacobian Y[" + dblX + "]=" + mssRightClipped.jackDResponseDCalibrationInput (dblX, 1).displayString());
			}

			dblX += 1.;
		}

		/*
		 * Right clipped stretch DPE
		 */

		System.out.println ("\tSPLINE_STRETCH_RIGHT DPE: " + mssRightClipped.curvatureDPE());

		/*
		 * Ordered Side by side Comparison of left clipped - unclipped - right clipped response values
		 */

		dblX = mssBase.getLeftPredictorOrdinateEdge();

		dblXMax = mssBase.getRightPredictorOrdinateEdge();

		System.out.println ("\n-----------------------------------------------------------------------------------------------------");

		System.out.println ("                           BASE         ||      LEFT CLIPPED           ||      RIGHT CLIPPED");

		System.out.println ("-----------------------------------------------------------------------------------------------------");

		while (dblX <= dblXMax) {
			java.lang.String strLeftClippedValue = "         ";
			java.lang.String strRightClippedValue = "         ";
			java.lang.String strLeftClippedMonotonocity = "             ";
			java.lang.String strRightClippedMonotonocity = "             ";

			/*
			 * Unclipped
			 */

			java.lang.String strDisplay = "Y[" + FormatUtil.FormatDouble (dblX, 2, 3, 1.) + "] => "
				+ FormatUtil.FormatDouble (mssBase.responseValue (dblX), 2, 6, 1.) + " | "
				+ mssBase.monotoneType (dblX);

			/*
			 * Left clipped
			 */

			if (mssLeftClipped.in (dblX)) {
				strLeftClippedValue = FormatUtil.FormatDouble (mssLeftClipped.responseValue (dblX), 2, 6, 1.);

				strLeftClippedMonotonocity = mssLeftClipped.monotoneType (dblX).toString();
			}

			/*
			 * Right clipped
			 */

			if (mssRightClipped.in (dblX)) {
				strRightClippedValue = FormatUtil.FormatDouble (mssRightClipped.responseValue (dblX), 2, 6, 1.);

				strRightClippedMonotonocity = mssRightClipped.monotoneType (dblX).toString();
			}

			System.out.println (strDisplay + "  ||  " + strLeftClippedValue + " | " + strLeftClippedMonotonocity +
				"  ||  " + strRightClippedValue + " | " + strRightClippedMonotonocity);

			dblX += 0.5;
		}
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		StretchAdjusterTest();

		EnvManager.TerminateEnv();
	}
}
