
package org.drip.sample.stretch;

import org.drip.function.r1tor1.QuadraticRationalShapeControl;
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
 * <i>CurvatureLengthRoughnessPenalty</i> demonstrates the setting up and the usage of the curvature, the
 * 	length, and the closeness of fit penalizing spline. This sample shows the following:
 * 	- Set up the X Predictor Ordinate and the Y Response Value Set.
 * 	- Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as
 * 		weighted closeness of fit.
 * 	- Construct a rational shape controller with the desired shape controller tension parameters and Global
 * 		Scaling.
 * 	- Construct the Segment Inelastic Parameter that is C2 (iK = 2 sets it to C2), with First Order Segment
 * 		Length Penalty Derivative, Second Order Segment Curvature Penalty Derivative, their Amplitudes, and
 * 		without Constraint.
 * 	- Construct the base, the base + 1 degree segment builder control.
 * 	- Construct the base, the elevated, and the best fit basis spline stretches.
 * 	- Compute the segment-by-segment monotonicity for all the three stretches.
 * 	- Compute the Stretch Jacobian for all the three stretches.
 * 	- Compute the Base Stretch Curvature, Length, and the Best Fit DPE.
 * 	- Compute the Elevated Stretch Curvature, Length, and the Best Fit DPE.
 * 	- Compute the Best Fit Stretch Curvature, Length, and the Best Fit DPE.
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

public class CurvatureLengthRoughnessPenalty {

	/*
	 * Build Polynomial Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	public static final SegmentCustomBuilderControl PolynomialSegmentControlParams (
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
	 * Construct the Basis Spline Stretch Instance using the following inputs:
	 * 	- Array of Segment Builder Parameters - one per segment
	 *  - Construct a Calibrated Stretch instance
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	public static final MultiSegmentSequence BasisSplineStretchTest (
		final double[] adblX,
		final double[] adblY,
		final SegmentCustomBuilderControl scbc,
		final StretchBestFitResponse rbfr)
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
			rbfr, // Fitness Weighted Response
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);
	}

	/*
	 * Illustrate the Penalized Curvature+Length+BestFit Usage Sample. This sample shows the following:
	 * 	- Set up the X Predictor Ordinate and the Y Response Value Set.
	 * 	- Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as
	 * 		weighted closeness of fit.
	 * 	- Construct a rational shape controller with the desired shape controller tension parameters and Global Scaling.
	 * 	- Construct the Segment Inelastic Parameter that is C2 (iK = 2 sets it to C2), with First Order
	 * 		Segment Length Penalty Derivative, Second Order Segment Curvature Penalty Derivative, their
	 * 		Amplitudes, and without Constraint.
	 * 	- Construct the base, the base + 1 degree segment builder control.
	 * 	- Construct the base, the elevated, and the best fit basis spline stretches.
	 * 	- Compute the segment-by-segment monotonicity for all the three stretches.
	 * 	- Compute the Stretch Jacobian for all the three stretches.
	 * 	- Compute the Base Stretch Curvature, Length, and the Best Fit DPE.
	 * 	- Compute the Elevated Stretch Curvature, Length, and the Best Fit DPE.
	 * 	- Compute the Best Fit Stretch Curvature, Length, and the Best Fit DPE.
	 */

	public static final void PenalizedCurvatureLengthFitTest()
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
		 * Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as
		 *  weighted closeness of fit.
		 */

		StretchBestFitResponse rbfr = StretchBestFitResponse.Create (
			new double[] { 2.28,  2.52,  2.73, 3.00,  5.50, 8.44,  8.76,  9.08,  9.80,  9.92},
			new double[] {14.27, 12.36, 10.61, 9.25, -0.50, 7.92, 10.07, 12.23, 15.51, 16.36},
			new double[] { 1.09,  0.82,  1.34, 1.10,  0.50, 0.79,  0.65,  0.49,  0.24,  0.21}
		);

		/*
		 * Construct a rational shape controller with the shape controller tension of 1, and Global Scaling.
		 */

		double dblShapeControllerTension = 1.;

		ResponseScalingShapeControl rssc = new ResponseScalingShapeControl (
			false,
			new QuadraticRationalShapeControl (dblShapeControllerTension)
		);

		/*
		 * Construct the Segment Inelastic Parameter that is C2 (iK = 2 sets it to C2), with First Order
		 * 	Segment Length Penalty Derivative, Second Order Segment Curvature Penalty Derivative, their
		 *  Amplitudes, and without Constraint
		 */

		int iK = 2;
		double dblLengthPenaltyAmplitude = 1.;
		double dblCurvaturePenaltyAmplitude = 1.;
		int iLengthPenaltyDerivativeOrder = 1;
		int iCurvaturePenaltyDerivativeOrder = 2;

		SegmentInelasticDesignControl sdic = new SegmentInelasticDesignControl (
			iK,
			new org.drip.spline.params.SegmentFlexurePenaltyControl (
				iLengthPenaltyDerivativeOrder,
				dblLengthPenaltyAmplitude
			),
			new org.drip.spline.params.SegmentFlexurePenaltyControl (
				iCurvaturePenaltyDerivativeOrder,
				dblCurvaturePenaltyAmplitude
			)
		);

		System.out.println (" \n--------------------------------------------------------------------------------------------------");

		System.out.println (" \n         == ORIGINAL #1 ==      $$   == ORIGINAL #2 ==    $$   == BEST FIT ==    ");

		System.out.println (" \n--------------------------------------------------------------------------------------------------");

		int iPolyNumBasis = 4;

		/* 
		 * Construct the base, the base + 1 degree segment builder control
		 */

		SegmentCustomBuilderControl scbc1 = PolynomialSegmentControlParams (
			iPolyNumBasis,
			sdic,
			rssc
		);

		SegmentCustomBuilderControl scbc2 = PolynomialSegmentControlParams (
			iPolyNumBasis + 1,
			sdic,
			rssc
		);

		/* 
		 * Construct the base, the elevated, and the best fit basis spline stretches
		 */

		MultiSegmentSequence mssBase1 = BasisSplineStretchTest (
			adblX,
			adblY,
			scbc1,
			null
		);

		MultiSegmentSequence mssBase2 = BasisSplineStretchTest (
			adblX,
			adblY,
			scbc2,
			null
		);

		MultiSegmentSequence mssBestFit = BasisSplineStretchTest (
			adblX,
			adblY,
			scbc2,
			rbfr
		);

		/*
		 * Compute the segment-by-segment response and monotonicity for all the three stretches
		 */

		double dblX = mssBase1.getLeftPredictorOrdinateEdge();

		double dblXMax = mssBase1.getRightPredictorOrdinateEdge();

		while (dblX <= dblXMax) {
			System.out.println (
				"Y[" + FormatUtil.FormatDouble (dblX, 1, 2, 1.) + "] " +
				FormatUtil.FormatDouble (mssBase1.responseValue (dblX), 2, 2, 1.) + " | "
					+ mssBase1.monotoneType (dblX) + " $$ "
				+ FormatUtil.FormatDouble (mssBase2.responseValue (dblX), 2, 2, 1.) + " | "
					+ mssBase2.monotoneType (dblX) + " $$ "
				+ FormatUtil.FormatDouble (mssBestFit.responseValue (dblX), 2, 2, 1.) + " | "
					+ mssBestFit.monotoneType (dblX));

			dblX += 0.25;
		}

		/*
		 * Compute the Stretch Jacobian for all the three stretches
		 */

		dblX = mssBase1.getLeftPredictorOrdinateEdge();

		while (dblX <= dblXMax) {
			System.out.println (
				"\t\tJacobian Y[" + FormatUtil.FormatDouble (dblX, 2, 2, 1.) + "] => " +
					mssBase1.jackDResponseDCalibrationInput (dblX, 1).displayString());

			System.out.println (
				"\t\tJacobian Y[" + FormatUtil.FormatDouble (dblX, 2, 2, 1.) + "] => " +
					mssBase2.jackDResponseDCalibrationInput (dblX, 1).displayString());

			System.out.println (
				"\t\tJacobian Y[" + FormatUtil.FormatDouble (dblX, 2, 2, 1.) + "] => " +
					mssBestFit.jackDResponseDCalibrationInput (dblX, 1).displayString());

			System.out.println ("\t\t----\n\t\t----");

			dblX += 0.25;
		}

		/*
		 * Compute the Base Stretch Curvature, Length, and the Best Fit DPE
		 */

		System.out.println ("\n\t\t----STRETCH #1----\n\t\t-----------------");

		System.out.println ("\tCURVATURE DPE         => " +
			FormatUtil.FormatDouble (mssBase1.curvatureDPE(), 10, 0, 1.));

		System.out.println ("\tLENGTH DPE            => " +
			FormatUtil.FormatDouble (mssBase1.lengthDPE(), 10, 0, 1.));

		System.out.println ("\tBEST FIT DPE          => " +
			FormatUtil.FormatDouble (mssBase1.bestFitDPE (rbfr), 10, 0, 1.));

		/*
		 * Compute the Elevated Stretch Curvature, Length, and the Best Fit DPE
		 */

		System.out.println ("\n\t\t----STRETCH #2----\n\t\t-----------------");

		System.out.println ("\tCURVATURE DPE         => " +
			FormatUtil.FormatDouble (mssBase2.curvatureDPE(), 10, 0, 1.));

		System.out.println ("\tLENGTH DPE            => " +
			FormatUtil.FormatDouble (mssBase2.lengthDPE(), 10, 0, 1.));

		System.out.println ("\tBEST FIT DPE          => " +
			FormatUtil.FormatDouble (mssBase2.bestFitDPE (rbfr), 10, 0, 1.));

		/*
		 * Compute the Best Fit Stretch Curvature, Length, and the Best Fit DPE
		 */

		System.out.println ("\n\t\t----STRETCH BEST FIT----\n\t\t-----------------------");

		System.out.println ("\tCURVATURE DPE         => " +
			FormatUtil.FormatDouble (mssBestFit.curvatureDPE(), 10, 0, 1.));

		System.out.println ("\tLENGTH DPE            => " +
			FormatUtil.FormatDouble (mssBestFit.lengthDPE(), 10, 0, 1.));

		System.out.println ("\tBEST FIT DPE          => " +
			FormatUtil.FormatDouble (mssBestFit.bestFitDPE (rbfr), 10, 0, 1.));
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		PenalizedCurvatureLengthFitTest();

		EnvManager.TerminateEnv();
	}
}
