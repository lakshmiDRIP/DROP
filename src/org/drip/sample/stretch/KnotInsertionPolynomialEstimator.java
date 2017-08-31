
package org.drip.sample.stretch;

import org.drip.function.r1tor1.*;
import org.drip.quant.common.FormatUtil;
import org.drip.spline.basis.*;
import org.drip.spline.params.*;
import org.drip.spline.pchip.*;
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
 * KnotInsertionPolynomialEstimator demonstrates the Stretch builder and usage API. It shows the following:
 * 
 * 	- Construction of segment control parameters - polynomial (regular/Bernstein) segment control,
 * 		exponential/hyperbolic tension segment control, Kaklis-Pandelis tension segment control.
 * 	- Perform the following sequence of tests for a given segment control for a predictor/response range
 * 		- Assign the array of Segment Builder Parameters - one per segment
 * 		- Construct the Stretch Instance
 * 		- Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
 * 		- Construct a new Stretch instance by inserting a pair of of predictor/response knots
 * 		- Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
 * 	- Demonstrate the construction, the calibration, and the usage of Local Control Segment Spline.
 * 	- Demonstrate the construction, the calibration, and the usage of Lagrange Polynomial Stretch.
 * 	- Demonstrate the construction, the calibration, and the usage of C1 Stretch with the desired customization.
 *
 * @author Lakshmi Krishnamurthy
 */

public class KnotInsertionPolynomialEstimator {

	/*
	 * Build Polynomial Segment Control Parameters.
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
	 * Build Bernstein Polynomial Segment Control Parameters.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl BernsteinPolynomialSegmentControlParams (
		final int iNumBasis,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_BERNSTEIN_POLYNOMIAL,
			new PolynomialFunctionSetParams (iNumBasis),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Build Exponential Tension Segment Control Parameters.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	public static final SegmentCustomBuilderControl ExponentialTensionSegmentControlParams (
		final double dblTension,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_EXPONENTIAL_TENSION,
			new ExponentialTensionSetParams (dblTension),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Build Hyperbolic Tension Segment Control Parameters.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl HyperbolicTensionSegmentControlParams (
		final double dblTension,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_HYPERBOLIC_TENSION,
			new ExponentialTensionSetParams (dblTension),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Build Kaklis-Pandelis Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl KaklisPandelisSegmentControlParams (
		final int iKPTensionDegree,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS,
			new KaklisPandelisSetParams (iKPTensionDegree),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Perform the following sequence of tests for a given segment control for a predictor/response range
	 * 	- Estimate
	 *  - Compute the segment-by-segment monotonicity
	 *  - Stretch Jacobian
	 *  - Stretch knot insertion
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void BasisSplineStretchTest (
		final double[] adblX,
		final double[] adblY,
		final SegmentCustomBuilderControl scbc)
		throws Exception
	{
		double dblX = 1.;
		double dblXMax = 10.;

		/*
		 * Assign the array of Segment Builder Parameters - one per segment
		 */

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		/*
		 * Construct a Stretch instance 
		 */

		MultiSegmentSequence mss = MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			"SPLINE_STRETCH", // Name
			adblX, // predictors
			adblY, // responses
			aSCBC, // Basis Segment Builder parameters
			null,  // NULL segment Best Fit Response
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
		 */

		while (dblX <= dblXMax) {
			System.out.println ("Y[" + dblX + "] " + FormatUtil.FormatDouble (mss.responseValue (dblX), 1, 2, 1.) + " | " +
				mss.monotoneType (dblX));

			System.out.println ("Jacobian Y[" + dblX + "]=" + mss.jackDResponseDCalibrationInput (dblX, 1).displayString());

			dblX += 1.;
		}

		System.out.println ("SPLINE_STRETCH DPE: " + mss.curvatureDPE());

		/*
		 * Construct a new Stretch instance by inserting a pair of of predictor/response knots
		 */

		MultiSegmentSequence mssInsert = MultiSegmentSequenceModifier.InsertKnot (
			mss, 								// The Original MSS
			9.,  								// Predictor Ordinate at which the Insertion is to be made
			10., 								// Response Value to be inserted
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE 	// Calibrate the Stretch predictors to the responses
		);

		dblX = 1.;

		/*
		 * Estimate, compute the sgement-by-segment monotonicty and the Stretch Jacobian
		 */

		while (dblX <= dblXMax) {
			System.out.println ("Inserted Y[" + dblX + "] " + FormatUtil.FormatDouble (mssInsert.responseValue (dblX), 1, 2, 1.)
				+ " | " + mssInsert.monotoneType (dblX));

			dblX += 1.;
		}

		System.out.println ("SPLINE_STRETCH_INSERT DPE: " + mssInsert.curvatureDPE());
	}

	/*
	 * This function demonstrates the construction, the calibration, and the usage of Local Control Segment Spline.
	 * 	It does the following:
	 * 	- Set up the predictor/response values, the shape controller, and the basis spline (in this case polynomial)
	 *  - Create the left and the right segment edge derivative parameters for each segment
	 * 	- Construct the C1 Hermite Polynomial Spline based Stretch Estimator by using the following steps:
	 * 		- Set up the Stretch Builder Parameter
	 * 		- Set the array of Segment Builder Parameters - one per segment
	 * 		- Construct the Stretch
	 * 		- Set up the left and the local control Parameters - in this case the derivatives
	 * 		- Calibrate the Stretch and compute the Jacobian
	 * 		- Display the Estimated Y and the Stretch Jacobian across the variates
	 * 	- Insert the Local Control spline point(s) for the following variants:
	 * 		- Local Control Explicit Hermite Point
	 * 		- Local Control Explicit Cardinal Point
	 * 		- Local Control Explicit Catmull-Rom Point
	 * 	- In each of the above instances perform the following tests:
	 * 		- Set up the left and the right segment edge parameters
	 * 		- Insert the pair of edge parameters at the chosen variate node.
	 * 		- Compute the Estimated segment value and the motonicity across a suitable variate range.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void TestHermiteCatmullRomCardinal()
		throws java.lang.Exception
	{
		/*
		 * X predictors
		 */

		double[] adblX = new double[] {0.00, 1.00,  2.00,  3.00,  4.00};

		/*
		 * Y responses
		 */

		double[] adblY = new double[] {1.00, 4.00, 15.00, 40.00, 85.00};

		/*
		 * DY/DX explicit local shape control for the responses
		 */

		double[] adblDYDX = new double[] {1.00, 6.00, 17.00, 34.00, 57.00};

		/*
		 * Construct a rational shape controller with the shape controller tension of 1.
		 */

		double dblShapeControllerTension = 1.;

		ResponseScalingShapeControl rssc = new ResponseScalingShapeControl (
			true,
			new QuadraticRationalShapeControl (dblShapeControllerTension)
		);

		/*
		 * Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
		 * 	roughness penalty derivative, and without constraint
		 */

		int iK = 1;
		int iRoughnessPenaltyDerivativeOrder = 2;

		SegmentInelasticDesignControl sdic = SegmentInelasticDesignControl.Create (
			iK,
			iRoughnessPenaltyDerivativeOrder
		);

		/* 
		 * Construct the C1 Hermite Polynomial Spline based Stretch Estimator by using the following steps:
		 * 
		 * - 1) Set up the Stretch Builder Parameter
		 */

		int iNumBasis = 4;

		SegmentCustomBuilderControl scbc = new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (iNumBasis),
			sdic,
			rssc,
			null
		);

		/*
		 *	- 2a) Set the array of Segment Builder Parameters - one per segment
		 */

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		/* 
		 * - 2b) Construct the Stretch
		 */

		MultiSegmentSequence mss = MultiSegmentSequenceBuilder.CreateUncalibratedStretchEstimator (
			"SPLINE_STRETCH",
			adblX,
			aSCBC
		);

		SegmentPredictorResponseDerivative[] aSPRDLeft = new SegmentPredictorResponseDerivative[adblY.length - 1];
		SegmentPredictorResponseDerivative[] aSPRDRight = new SegmentPredictorResponseDerivative[adblY.length - 1];

		 /* 
		  * - 3) Set up the left and the local control Parameters - in this case the derivatives
		  */

		for (int i = 0; i < adblY.length - 1; ++i) {
			aSPRDLeft[i] = new SegmentPredictorResponseDerivative (adblY[i], new double[] {adblDYDX[i]});

			aSPRDRight[i] = new SegmentPredictorResponseDerivative (adblY[i + 1], new double[] {adblDYDX[i + 1]});
		}

		/* 
		 * - 4) Calibrate the Stretch and compute the Jacobian
		 */

		System.out.println ("Stretch Setup Succeeded: " +
			mss.setupHermite (
				aSPRDLeft,
				aSPRDRight,
				null,
				null,
				MultiSegmentSequence.CALIBRATE
			)
		);

		double dblX = 0.;
		double dblXMax = 4.;

		/* 
		 * - 5) Display the Estimated Y and the Stretch Jacobian across the variates
		 */

		while (dblX <= dblXMax) {
			System.out.println ("Y[" + dblX + "] " + FormatUtil.FormatDouble (mss.responseValue (dblX), 1, 2, 1.) + " | " +
				mss.monotoneType (dblX));

			System.out.println ("Jacobian Y[" + dblX + "]=" + mss.jackDResponseDCalibrationInput (dblX, 1).displayString());

			dblX += 0.5;
		}

		System.out.println ("SPLINE_STRETCH DPE: " + mss.curvatureDPE());

		/* 
		 * We now insert a Hermite local control knot. The following are the steps:
		 * 
		 * - 1) Set up the left and the right segment edge parameters
		 * - 2) Insert the pair of edge parameters at the chosen variate node.
		 * - 3) Compute the Estimated segment value and the motonicity across a suitable variate range.
		 */

		SegmentPredictorResponseDerivative sprdLeftSegmentRightNode = new SegmentPredictorResponseDerivative (
			27.5,
			new double[] {25.5}
		);

		SegmentPredictorResponseDerivative sprdRightSegmentLeftNode = new SegmentPredictorResponseDerivative (
			27.5,
			new double[] {25.5}
		);

		MultiSegmentSequence mssInsert = MultiSegmentSequenceModifier.InsertKnot (
			mss,
			2.5,
			sprdLeftSegmentRightNode,
			sprdRightSegmentLeftNode
		);

		dblX = 1.;

		while (dblX <= dblXMax) {
			System.out.println ("Inserted Y[" + dblX + "] " + FormatUtil.FormatDouble (mssInsert.responseValue (dblX), 1, 2, 1.)
				+ " | " + mssInsert.monotoneType (dblX));

			dblX += 0.5;
		}

		System.out.println ("SPLINE_STRETCH_INSERT DPE: " + mssInsert.curvatureDPE());

		/* 
		 * We now insert a Cardinal local control knot. The following are the steps:
		 * 
		 * - 1) Set up the left and the right segment edge parameters
		 * - 2) Insert the pair of edge parameters at the chosen variate node.
		 * - 3) Compute the Estimated segment value and the motonicity across a suitable variate range.
		 */

		MultiSegmentSequence mssCardinalInsert = MultiSegmentSequenceModifier.InsertCardinalKnot (
			mss,
			2.5,
			0.
		);

		dblX = 1.;

		while (dblX <= dblXMax) {
			System.out.println ("Cardinal Inserted Y[" + dblX + "] " + FormatUtil.FormatDouble
				(mssCardinalInsert.responseValue (dblX), 1, 2, 1.) + " | " + mssInsert.monotoneType (dblX));

			dblX += 0.5;
		}

		System.out.println ("SPLINE_STRETCH_CARDINAL_INSERT DPE: " + mssCardinalInsert.curvatureDPE());

		/* 
		 * We now insert a Catmull-Rom local control knot. The following are the steps:
		 * 
		 * - 1) Set up the left and the right segment edge parameters
		 * - 2) Insert the pair of edge parameters at the chosen variate node.
		 * - 3) Compute the Estimated segment value and the motonicity across a suitable variate range.
		 */

		MultiSegmentSequence mssCatmullRomInsert = MultiSegmentSequenceModifier.InsertCatmullRomKnot (
			mss,
			2.5
		);

		dblX = 1.;

		while (dblX <= dblXMax) {
			System.out.println ("Catmull-Rom Inserted Y[" + dblX + "] " + FormatUtil.FormatDouble
				(mssCatmullRomInsert.responseValue (dblX), 1, 2, 1.) + " | " + mssInsert.monotoneType (dblX));

			dblX += 0.5;
		}

		System.out.println ("SPLINE_STRETCH_CATMULL_ROM_INSERT DPE: " + mssCatmullRomInsert.curvatureDPE());
	}

	/*
	 * This function demonstrates the construction, the calibration, and the usage of Lagrange Polynomial Stretch.
	 * 	It does the following:
	 * 	- Set up the predictors and the Lagrange Polynomial Stretch.
	 *  - Calibrate to a target Y array.
	 *  - Calibrate the value to a target X.
	 *  - Calibrate the value Jacobian to a target X.
	 *  - Verify the local monotonicity and convexity (both the co- and the local versions).
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void TestLagrangePolynomialStretch()
		throws java.lang.Exception
	{
		SingleSegmentSequence sslp = new SingleSegmentLagrangePolynomial (new double[] {-2., -1., 2., 5.});

		System.out.println ("Setup: " + sslp.setup (
			0.25, 										// Left Edge Response Value
			new double[] {0.25, 0.25, 12.25, 42.25},	// Array of Segment Response Values
			null, 										// Fitness Weighted Response
			BoundarySettings.NaturalStandard(), 		// Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE) 			// Calibrate the Stretch predictors to the responses
		);

		System.out.println ("Value = " + sslp.responseValue (2.16));

		System.out.println ("Value Jacobian = " + sslp.jackDResponseDCalibrationInput (2.16, 1).displayString());

		System.out.println ("Value Monotone Type: " + sslp.monotoneType (2.16));

		System.out.println ("Is Locally Monotone: " + sslp.isLocallyMonotone());
	}

	/*
	 * Construct the C1 Stretch with the desired customization - this demonstrates the following steps:
	 * 	- Construct the Local Monotone C1 Generator with the desired Customization.
	 * 	- Array of Segment Builder Parameters - one per segment.
	 * 	- Construct the Local Control Stretch instance.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final MultiSegmentSequence ConstructSpecifiedC1Stretch (
		final double[] adblX,
		final double[] adblY,
		final java.lang.String strGeneratorType,
		final SegmentCustomBuilderControl scbc,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
	{
		/*
		 * Construct the Local Monotone C1 Generator with the desired Customization
		 */

		LocalMonotoneCkGenerator lmcg = LocalMonotoneCkGenerator.Create (
			adblX,						// The Array of Predictor Ordinates
			adblY,						// The Array of Response Value
			strGeneratorType,			// The C1 Generator Type
			bEliminateSpuriousExtrema,	// TRUE => Eliminate Spurious Extremum
			bApplyMonotoneFilter		// TRUE => Apply Monotone Filter
		);

		/*
		 * Array of Segment Builder Parameters - one per segment
		 */

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		/*
		 * Construct the Local Control Stretch instance 
		 */

		return LocalControlStretchBuilder.CustomSlopeHermiteSpline (
			strGeneratorType + "_LOCAL_STRETCH",
			adblX,
			adblY,
			lmcg.C1(),
			aSCBC,
			null,
			MultiSegmentSequence.CALIBRATE
		);
	}

	/*
	 * Perform the following sequence of tests for a given segment control for a predictor/response range:
	 * 	- Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian.
	 *  - Construct a new Stretch instance by inserting a pair of of predictor/response knots.
	 *  - Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian.
	 *  - Stretch knot insertion
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void C1GeneratedStretchTest (
		final double[] adblX,
		final double[] adblY,
		final MultiSegmentSequence mss)
		throws Exception
	{
		double dblX = 1.;
		double dblXMax = 10.;

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
		 */

		while (dblX <= dblXMax) {
			System.out.println (
				"Y[" + dblX + "] => " + FormatUtil.FormatDouble (mss.responseValue (dblX), 1, 2, 1.) + " | " +
				mss.monotoneType (dblX));

			System.out.println ("Jacobian Y[" + dblX + "]=" + mss.jackDResponseDCalibrationInput (dblX, 1).displayString());

			dblX += 1.;
		}

		System.out.println ("\tSPLINE_STRETCH DPE: " + mss.curvatureDPE());

		/*
		 * Construct a new Stretch instance by inserting a pair of of predictor/response knots
		 */

		MultiSegmentSequence mssInsert = MultiSegmentSequenceModifier.InsertKnot (
			mss, 								// The Original MSS
			9.,  								// Predictor Ordinate at which the Insertion is to be made
			10., 								// Response Value to be inserted
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE 	// Calibrate the Stretch predictors to the responses
		);

		dblX = 1.;

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
		 */

		while (dblX <= dblXMax) {
			System.out.println ("Inserted Y[" + dblX + "] " + FormatUtil.FormatDouble (mssInsert.responseValue (dblX), 1, 2, 1.)
				+ " | " + mssInsert.monotoneType (dblX));

			dblX += 1.;
		}

		System.out.println ("\tSPLINE_STRETCH_INSERT DPE: " + mssInsert.curvatureDPE());
	}

	/*
	 * This function brings it all together. It demonstrates the following sequence:
	 * 	- Setup and X predictor ordinate and Y response value arrays.
	 * 	- Construct a rational shape controller with the specified shape controller tension parameter.
	 * 	- Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
	 * 		roughness penalty derivative, and without constraint
	 * 	- Regular Polynomial Basis Spline Stretch Test.
	 * 	- Bernstein Polynomial Basis Spline Stretch Test.
	 * 	- Exponential Tension Basis Spline Stretch Test.
	 * 	- Hyperbolic Tension Basis Spline Stretch Test.
	 * 	- Kaklis-Pandelis Basis Spline Stretch Test.
	 * 	- Catmull-Rom Cardinal Hermite Basis Spline Stretch Test.
	 * 	- Lagrange Polynomial Basis Spline Stretch Test.
	 * 	- Akima C1 Basis Spline Stretch Test.
	 * 	- Bessel/Hermite C1 Basis Spline Stretch Test.
	 * 	- Harmonic Monotone C1 Basis Spline Stretch Test with Filter.
	 * 	- Harmonic Monotone C1 Basis Spline Stretch Test without Filter.
	 * 	- Huynh-Le Floch Limiter Monotone C1 Basis Spline Stretch Test without Filter.
	 * 	- Hyman 1983 Monotone C1 Basis Spline Stretch Test with Filter.
	 * 	- Hyman 1989 Monotone C1 Basis Spline Stretch Test with Filter.
	 * 	- Kruger C1 Basis Spline Stretch Test with Filter.
	 * 	- Van Leer Limiter Monotone C1 Basis Spline Stretch Test without Filter.
	 */

	public static final void StretchEstimationTestSequence()
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
			true,
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
		 * Bernstein Polynomial Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n BERNSTEIN POLYNOMIAL \n ---------- \n");

		int iBernPolyNumBasis = 4;

		BasisSplineStretchTest (
			adblX,
			adblY,
			BernsteinPolynomialSegmentControlParams (
				iBernPolyNumBasis,
				sdic,
				rssc
			)
		);

		/*
		 * Regular Polynomial Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n POLYNOMIAL \n ---------- \n");

		int iPolyNumBasis = 4;

		BasisSplineStretchTest (
			adblX,
			adblY,
			PolynomialSegmentControlParams (
				iPolyNumBasis,
				sdic,
				rssc
			)
		);

		/*
		 * Exponential Tension Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n EXPONENTIAL TENSION \n ---------- \n");

		double dblTension = 1.;

		BasisSplineStretchTest (
			adblX,
			adblY,
			ExponentialTensionSegmentControlParams (
				dblTension,
				sdic,
				rssc
			)
		);

		/*
		 * Hyperbolic Tension Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n HYPERBOLIC TENSION \n ---------- \n");

		BasisSplineStretchTest (
			adblX,
			adblY,
			HyperbolicTensionSegmentControlParams (
				dblTension,
				sdic,
				rssc
			)
		);

		/*
		 * Kaklis-Pandelis Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n KAKLIS PANDELIS \n ---------- \n");

		int iKPTensionDegree = 2;

		BasisSplineStretchTest (
			adblX,
			adblY,
			KaklisPandelisSegmentControlParams (
				iKPTensionDegree,
				sdic,
				rssc
			)
		);

		/*
		 * Catmull-Rom Cardinal Hermite Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n HERMITE - CATMULL ROM - CARDINAL \n ---------- \n");

		TestHermiteCatmullRomCardinal();

		/*
		 * Lagrange Polynomial Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n LAGRANGE POLYNOMIAL STRETCH\n ---------- \n");

		TestLagrangePolynomialStretch();

		/*
		 * Akima C1 Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n C1 AKIMA STRETCH\n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_AKIMA,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				true
			)
		);

		/*
		 * Bessel/Hermite C1 Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n C1 BESSEL/HERMITE \n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_BESSEL,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				true
			)
		);

		/*
		 * Harmonic Monotone C1 Basis Spline Stretch Test with Filter
		 */

		System.out.println (" \n---------- \n C1 HARMONIC MONOTONE WITH FILTER \n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_HARMONIC,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				true
			)
		);

		/*
		 * Harmonic Monotone C1 Basis Spline Stretch Test without Filter
		 */

		System.out.println (" \n---------- \n C1 HARMONIC MONOTONE WITHOUT FILTER \n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_HARMONIC,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				false
			)
		);

		/*
		 * Huynh-Le Floch Limiter Monotone C1 Basis Spline Stretch Test without Filter
		 */

		System.out.println (" \n---------- \n C1 HUYNH LE-FLOCH LIMITER STRETCH WITHOUT FILTER \n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_HUYNH_LE_FLOCH,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				true
			)
		);

		/*
		 * 
		 * Hyman 1983 Monotone C1 Basis Spline Stretch Test with Filter
		 */

		System.out.println (" \n---------- \n C1 HYMAN 1983 MONOTONE \n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_HYMAN83,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				true
			)
		);

		/*
		 * Hyman 1989 Monotone C1 Basis Spline Stretch Test with Filter
		 */

		System.out.println (" \n---------- \n C1 HYMAN 1989 MONOTONE \n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_HYMAN89,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				true
			)
		);

		/*
		 * Kruger C1 Basis Spline Stretch Test with Filter
		 */

		System.out.println (" \n---------- \n C1 KRUGER STRETCH\n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_KRUGER,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				true
			)
		);

		/*
		 * Van Leer Limiter Monotone C1 Basis Spline Stretch Test without Filter
		 */

		System.out.println (" \n---------- \n C1 VAN LEER LIMITER STRETCH WITHOUT FILTER \n ---------- \n");

		C1GeneratedStretchTest (
			adblX,
			adblY,
			ConstructSpecifiedC1Stretch (
				adblX,
				adblY,
				LocalMonotoneCkGenerator.C1_VAN_LEER,
				PolynomialSegmentControlParams (
					iPolyNumBasis,
					sdic,
					rssc
				),
				true,
				false
			)
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		StretchEstimationTestSequence();
	}
}
