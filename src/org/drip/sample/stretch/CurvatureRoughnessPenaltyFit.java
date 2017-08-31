
package org.drip.sample.stretch;

import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.quant.common.FormatUtil;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * CurvatureRoughnessPenaltyFit demonstrates the setting up and the usage of the curvature and closeness of
 * 	fit penalizing spline. It illustrates in detail the following steps:
 * 	- Set up the X Predictor Ordinate and the Y Response Value Set.
 * 	- Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as
 * 		weighted closeness of fit.
 * 	- Construct a rational shape controller with the desired shape controller tension parameters and Global
 * 		Scaling.
 * 	- Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order roughness
 * 		penalty derivative, and without constraint.
 * 	- Construct the base, the base + 1 degree segment builder control.
 * 	- Construct the base, the elevated, and the best fit basis spline stretches.
 * 	- Compute the segment-by-segment monotonicity for all the three stretches.
 * 	- Compute the Stretch Jacobian for all the three stretches.
 * 	- Compute the Base Stretch Curvature Penalty Estimate.
 * 	- Compute the Elevated Stretch Curvature Penalty Estimate.
 * 	- Compute the Best Fit Stretch Curvature Penalty Estimate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CurvatureRoughnessPenaltyFit {

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
		final StretchBestFitResponse sbfr)
		throws Exception
	{
		/*
		 * Array of Segment Builder Parameters - one per segment
		 */

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		/*
		 * Construct a Calibrated Stretch instance 
		 */

		return MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			"SPLINE_STRETCH",
			adblX, // predictors
			adblY, // responses
			aSCBC, // Basis Segment Builder parameters
			sbfr, // Stretch Fitness Weighted Response
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);
	}

	/*
	 * Bring it all together in the Penalized Curvature Fit Test using the following steps:
	 * 	- Set up the X Predictor Ordinate and the Y Response Value Set.
	 * 	- Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as
	 * 		weighted closeness of fit.
	 * 	- Construct a rational shape controller with the desired shape controller tension parameters and Global Scaling.
	 * 	- Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
	 * 		roughness penalty derivative, and without constraint.
	 * 	- Construct the base, the base + 1 degree segment builder control.
	 * 	- Construct the base, the elevated, and the best fit basis spline stretches.
	 * 	- Compute the segment-by-segment monotonicity for all the three stretches.
	 * 	- Compute the Stretch Jacobian for all the three stretches.
	 * 	- Compute the Base Stretch Curvature Penalty Estimate.
	 * 	- Compute the Elevated Stretch Curvature Penalty Estimate.
	 * 	- Compute the Best Fit Stretch Curvature Penalty Estimate.
	 */

	public static final void PenalizedCurvatureFitTest()
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

		StretchBestFitResponse sbfr = StretchBestFitResponse.Create (
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
		 * Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
		 * 	roughness penalty derivative, and without constraint
		 */

		int iK = 2;
		int iRoughnessPenaltyDerivativeOrder = 2;

		SegmentInelasticDesignControl sdic = SegmentInelasticDesignControl.Create (
			iK,
			iRoughnessPenaltyDerivativeOrder
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
			sbfr
		);

		/*
		 * Compute the segment-by-segment monotonicity for all the three stretches
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
		 * Compute the Base Stretch Curvature Penalty Estimate
		 */

		System.out.println ("\tBASE #1  DPE: " + FormatUtil.FormatDouble (mssBase1.curvatureDPE(), 10, 0, 1.));

		/*
		 * Compute the Elevated Stretch Curvature Penalty Estimate
		 */

		System.out.println ("\tBASE #2  DPE: " + FormatUtil.FormatDouble (mssBase2.curvatureDPE(), 10, 0, 1.));

		/*
		 * Compute the Best Fit Stretch Curvature Penalty Estimate
		 */

		System.out.println ("\tBEST FIT DPE: " + FormatUtil.FormatDouble (mssBestFit.curvatureDPE(), 10, 0, 1.));
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		PenalizedCurvatureFitTest();
	}
}
