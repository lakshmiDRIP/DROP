	
package org.drip.sample.stretch;

import org.drip.function.r1tor1.*;
import org.drip.quant.common.FormatUtil;
import org.drip.spline.basis.*;
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
 * KnotInsertionTensionEstimator demonstrates the Stretch builder and usage API. It shows the following:
 * 
 * 	- Construction of segment control parameters - polynomial (regular/Bernstein) segment control,
 * 		exponential/hyperbolic tension segment control, Kaklis-Pandelis tension segment control.
 * 	- Tension Basis Spline Test using the specified predictor/response set and the array of segment custom
 * 		builder control parameters.
 * 	- Complete the full tension stretch estimation sample test.
 *
 * @author Lakshmi Krishnamurthy
 */

public class KnotInsertionTensionEstimator {

	/*
	 * Build KLK Exponential Tension Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl KLKExponentialTensionSegmentControlParams (
		final double dblTension,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_EXPONENTIAL_TENSION,
			new ExponentialTensionSetParams (dblTension),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Build KLK Hyperbolic Tension Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl KLKHyperbolicTensionSegmentControlParams (
		final double dblTension,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
			new ExponentialTensionSetParams (dblTension),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Build KLK Rational Linear Tension Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl KLKRationalLinearTensionSegmentControlParams (
		final double dblTension,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
			new ExponentialTensionSetParams (dblTension),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Build KLK Rational Quadratic Tension Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl KLKRationalQuadraticTensionSegmentControlParams (
		final double dblTension,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
			new ExponentialTensionSetParams (dblTension),
			sdic,
			rssc,
			null
		);
	}

	/*
	 * Tension Basis Spline Test using the specified predictor/response set and the array of segment custom
	 * 	builder control parameters. It consists of the following steps:
	 * 	- Array of Segment Builder Parameters - one per segment
	 *  - Construct a Stretch instance
	 *  - Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
	 *  - Construct a new Stretch instance by inserting a pair of of predictor/response knots
	 *  - Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
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
		 * Array of Segment Builder Parameters - one per segment
		 */

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		/*
		 * Construct a Stretch instance 
		 */

		MultiSegmentSequence mss = MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			"SPLINE_STRETCH",
			adblX, // predictors
			adblY, // responses
			aSCBC, // Basis Segment Builder parameters
			null, 
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
		 */

		while (dblX <= dblXMax) {
			System.out.println ("Y[" + dblX + "] " + FormatUtil.FormatDouble (mss.responseValue (dblX), 1, 2, 1.) + " | " +
				mss.monotoneType (dblX));

			System.out.println ("\tJacobian Y[" + dblX + "]=" + mss.jackDResponseDCalibrationInput (dblX, 1).displayString());

			dblX += 1.;
		}

		System.out.println ("\t\tSPLINE_STRETCH DPE: " + mss.curvatureDPE());

		/*
		 * Construct a new Stretch instance by inserting a pair of of predictor/response knots
		 */

		MultiSegmentSequence mssInsert = MultiSegmentSequenceModifier.InsertKnot (mss,
			9.,
			10.,
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
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

		System.out.println ("\t\tSPLINE_STRETCH_INSERT DPE: " + mssInsert.curvatureDPE());
	}

	/*
	 * Complete the full tension stretch estimation sample test by doing the following:
	 * 	- Composing the array of predictor/responses
	 * 	- Construct a rational shape controller with the desired shape controller tension
	 * 	- Construct the Segment Inelastic Parameter that is C2 (iK = 2 sets it to C2), with Second Order
	 * 		Curvature Penalty Derivative, and without constraint
	 * 	- KLK Hyperbolic Tension Basis Spline Stretch Test
	 * 	- KLK Exponential Tension Basis Spline Stretch Test
	 * 	- KLK Rational Linear Tension Basis Spline Stretch Test
	 * 	- KLK Rational Quadratic Tension Basis Spline Stretch Test
	 */

	public static final void TensionStretchEstimationSample()
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
			new LinearRationalShapeControl (dblShapeControllerTension)
		);

		/*
		 * Construct the Segment Inelastic Parameter that is C2 (iK = 2 sets it to C2), with Second Order
		 * 	Curvature Penalty Derivative, and without constraint
		 */

		int iK = 2;
		int iCurvaturePenaltyDerivativeOrder= 2;

		SegmentInelasticDesignControl segParams = SegmentInelasticDesignControl.Create (
			iK,
			iCurvaturePenaltyDerivativeOrder
		);

		double dblKLKTension = 1.;

		/*
		 * KLK Hyperbolic Tension Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n KLK HYPERBOLIC TENSION \n ---------- \n");

		BasisSplineStretchTest (
			adblX,
			adblY,
			KLKHyperbolicTensionSegmentControlParams (
				dblKLKTension,
				segParams,
				rssc
			)
		);

		/*
		 * KLK Exponential Tension Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n KLK EXPONENTIAL TENSION \n ---------- \n");

		BasisSplineStretchTest (
			adblX,
			adblY,
			KLKExponentialTensionSegmentControlParams (
				dblKLKTension,
				segParams,
				rssc
			)
		);

		/*
		 * KLK Rational Linear Tension Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n KLK RATIONAL LINEAR TENSION \n ---------- \n");

		BasisSplineStretchTest (
			adblX,
			adblY,
			KLKRationalLinearTensionSegmentControlParams (
				dblKLKTension,
				segParams,
				rssc
			)
		);

		/*
		 * KLK Rational Quadratic Tension Basis Spline Stretch Test
		 */

		System.out.println (" \n---------- \n KLK RATIONAL QUADRATIC TENSION \n ---------- \n");

		BasisSplineStretchTest (
			adblX,
			adblY,
			KLKRationalQuadraticTensionSegmentControlParams (
				dblKLKTension,
				segParams,
				rssc
			)
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		TensionStretchEstimationSample();
	}
}
