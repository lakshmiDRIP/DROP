
package org.drip.sample.stretch;

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
 * KnottedRegressionSplineEstimator shows the sample construction and usage of Knot-based Regression Splines.
 *  It demonstrates construction of the segment's predictor ordinate/response value combination, and eventual
 *  calibration.
 *
 * @author Lakshmi Krishnamurthy
 */

public class KnottedRegressionSplineEstimator {

	/*
	 * Build Polynomial Segment Control Parameters
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl PolynomialSegmentControlParams (
		final int iNumBasis,
		final SegmentInelasticDesignControl sdic)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (iNumBasis),
			sdic,
			null,
			null
		);
	}

	/*
	 * Basis Spline Stretch Test Sample. Performs the following:
	 * 	- Construct the Array of Segment Builder Parameters - one per segment.
	 *  - Construct a Stretch instance using the predictor ordinate array and the Segment Best Fit Response Values.
	 *  - Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
	 *  - Compute the Segment Curvature Penalty Estimate.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void BasisSplineStretchTest (
		final double[] adblX,
		final SegmentCustomBuilderControl scbc,
		final StretchBestFitResponse sbfr)
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
		 * Construct a Stretch instance using the predictor ordinate array and the Segment Best Fit Response Values
		 */

		MultiSegmentSequence mss = MultiSegmentSequenceBuilder.CreateRegressionSplineEstimator (
			"SPLINE_STRETCH",
			adblX, // predictors
			aSCBC, // Basis Segment Builder parameters
			sbfr,
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);

		/*
		 * Estimate, compute the segment-by-segment monotonicity and the Stretch Jacobian
		 */

		while (dblX <= dblXMax) {
			System.out.println ("Y[" + dblX + "] " + FormatUtil.FormatDouble (mss.responseValue (dblX), 1, 2, 1.) + " | " +
				mss.monotoneType (dblX));

			System.out.println ("\t\tJacobian Y[" + dblX + "]=" + mss.jackDResponseDCalibrationInput (dblX, 1).displayString());

			dblX += 1.;
		}

		/*
		 * Compute the Segment Curvature Penalty Estimate
		 */

		System.out.println ("\tSPLINE_STRETCH DPE: " + mss.curvatureDPE());
	}

	/*
	 * Bring together to compose the Regression Spline Estimator Test. It is made up of the following steps:
	 * 	- Set the Predictor Ordinate Knot Points.
	 * 	- Construct a set of Predictor Ordinates, their Responses, and corresponding Weights to serve as
	 * 		weighted closeness of fit.
	 * 	- Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
	 * 		roughness penalty derivative, and without constraint.
	 * 	- Basis Spline Stretch Test Using the Segment Best Fit Response.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void RegressionSplineEstimatorTest()
		throws Exception
	{
		/*
		 * Set the Knot Points
		 */

		double[] adblX = new double[] { 1.00,  5.00, 10.00};

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
		 * Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with 2nd order
		 * 	roughness penalty derivative, and without constraint
		 */

		int iK = 2;
		int iRoughnessPenaltyDerivativeOrder = 2;

		SegmentInelasticDesignControl sdic = SegmentInelasticDesignControl.Create (
			iK,
			iRoughnessPenaltyDerivativeOrder
		);

		int iPolyNumBasis = 4;

		/*
		 * Basis Spline Stretch Test Using the Segment Best Fit Response
		 */

		BasisSplineStretchTest (
			adblX,
			PolynomialSegmentControlParams (
				iPolyNumBasis,
				sdic
			),
			sbfr
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		RegressionSplineEstimatorTest();
	}
}
