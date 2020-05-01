	
package org.drip.sample.stretch;

import org.drip.function.r1tor1.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.*;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>KnotInsertionTensionEstimator</i> demonstrates the Stretch builder and usage API. It shows the
 * 	following:
 * 	- Construction of segment control parameters - polynomial (regular/Bernstein) segment control,
 * 		exponential/hyperbolic tension segment control, Kaklis-Pandelis tension segment control.
 * 	- Tension Basis Spline Test using the specified predictor/response set and the array of segment custom
 * 		builder control parameters.
 * 	- Complete the full tension stretch estimation sample test.
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
		EnvManager.InitEnv (
			""
		);

		TensionStretchEstimationSample();

		EnvManager.TerminateEnv();
	}
}
