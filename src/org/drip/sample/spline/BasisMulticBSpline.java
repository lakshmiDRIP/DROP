
package org.drip.sample.spline;

import org.drip.spline.bspline.*;
import org.drip.quant.common.FormatUtil;

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
 * BasisMulticBSpline implements Samples for the Construction and the usage of various multic basis B Splines.
 *  It demonstrates the following:
 * 	- Construction of segment higher order B Spline from the corresponding Hat Basis Functions.
 * 	- Estimation of the derivatives and the basis envelope cumulative integrands.
 * 	- Estimation of the normalizer and the basis envelope cumulative normalized integrands.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisMulticBSpline {

	/*
	 * This sample illustrates the construction and the usage of multic basis functions, and their eventual
	 * 	response/derivative computation and comparison with the corresponding raw/processed and monic basis.
	 * 	It shows the following:
	 * 	- Construct the hyperbolic tension basis hat pair using the left predictor ordinates and the tension.
	 * 	- Construct the hyperbolic tension basis hat pair using the right predictor ordinates and the
	 * 		tension.
	 * 	- Generate the left monic basis function from the hat type, left predictor ordinates, shape control,
	 * 		and the tension parameters.
	 * 	- Generate the right monic basis function from the hat type, right predictor ordinates, shape
	 * 		control, and the tension parameters.
	 * 	- Run a response value calculation comparison across the predictor ordinates for each of the left
	 * 		basis hat and the monic basis functions.
	 * 	- Run a response value calculation comparison across the predictor ordinates for each of the right
	 * 		basis hat and the monic basis functions.
	 * 	- Construct a multic basis function using the left/right monic basis functions, and the multic order.
	 * 	- Display the multic Basis Function response as well as normalized Cumulative across the specified
	 * 		variate range.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void RunMulticBSplineTest (
		final String strHatType,
		final String strShapeControlType,
		final double dblTension,
		final int iMulticBSplineOrder)
		throws Exception
	{
		double[] adblPredictorOrdinateLeft = new double[] {
			1., 2., 3.
		};
		double[] adblPredictorOrdinateRight = new double[] {
			2., 3., 4.
		};

		/*
		 * Construct the hyperbolic tension basis hat pair using the left predictor ordinates and the
		 * 	tension.
		 */

		TensionBasisHat[] aTBHLeft = BasisHatPairGenerator.HyperbolicTensionHatPair (
			adblPredictorOrdinateLeft[0],
			adblPredictorOrdinateLeft[1],
			adblPredictorOrdinateLeft[2],
			dblTension
		);

		/*
		 * Construct the hyperbolic tension basis hat pair using the right predictor ordinates and the
		 *  tension.
		 */

		TensionBasisHat[] aTBHRight = BasisHatPairGenerator.HyperbolicTensionHatPair (
			adblPredictorOrdinateRight[0],
			adblPredictorOrdinateRight[1],
			adblPredictorOrdinateRight[2],
			dblTension
		);

		/*
		 * Generate the left monic basis function from the hat type, left predictor ordinates, shape control,
		 * 	and the tension parameters.
		 */

		SegmentBasisFunction sbfMonicLeft = SegmentBasisFunctionGenerator.Monic (
			strHatType,
			strShapeControlType,
			adblPredictorOrdinateLeft,
			2,
			dblTension
		);

		/*
		 * Generate the right monic basis function from the hat type, right predictor ordinates, shape
		 * 	control, and the tension parameters.
		 */

		SegmentBasisFunction sbfMonicRight = SegmentBasisFunctionGenerator.Monic (
			strHatType,
			strShapeControlType,
			adblPredictorOrdinateRight,
			2,
			dblTension
		);

		/*
		 * Run a response value calculation comparison across the predictor ordinates for each of the left
		 * 	basis hat and the monic basis functions.
		 */

		System.out.println ("\n\t-------------------------------------------------");

		System.out.println ("\t            X    |   LEFT   |   RIGHT  |   MONIC  ");

		System.out.println ("\t-------------------------------------------------");

		double dblX = 0.50;
		double dblXIncrement = 0.25;

		while (dblX <= 4.50) {
			System.out.println (
				"\tResponse[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (aTBHLeft[0].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (aTBHLeft[1].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (sbfMonicLeft.evaluate (dblX), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}

		/*
		 * Run a response value calculation comparison across the predictor ordinates for each of the right
		 * 	basis hat and the monic basis functions.
		 */

		System.out.println ("\n\t-------------------------------------------------");

		System.out.println ("\t            X    |   LEFT   |   RIGHT  |   MONIC  ");

		System.out.println ("\t-------------------------------------------------");

		dblX = 0.50;

		while (dblX <= 4.50) {
			System.out.println (
				"\tResponse[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (aTBHRight[0].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (aTBHRight[1].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (sbfMonicRight.evaluate (dblX), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}

		/*
		 * Construct a multic basis function using the left/right monic basis functions, and the multic
		 * 	order.
		 */

		SegmentBasisFunction[] sbfMultic = SegmentBasisFunctionGenerator.MulticSequence (
			iMulticBSplineOrder,
			new SegmentBasisFunction[] {
				sbfMonicLeft,
				sbfMonicRight
			}
		);

		/*
		 * Display the multic Basis Function response as well as normalized Cumulative across the specified
		 * 	variate range.
		 */

		System.out.println ("\n\t-------------------------------------------------");

		System.out.println ("\t          PREDICTOR    | RESPONSE | CUMULATIVE  ");

		System.out.println ("\t-------------------------------------------------");

		dblX = 0.50;
		dblXIncrement = 0.125;

		while (dblX <= 4.50) {
			System.out.println (
				"\t\tMultic[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (sbfMultic[0].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (sbfMultic[0].normalizedCumulative (dblX), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}

		System.out.println ("\n\t-------------------------------------------------\n");
	}

	/*
	 * This sample illustrates a sequence of tests using basis multic B Splines. In particular it shows the
	 * 	following:
	 * 	- Creation and usage of Multic B Spline built off of raw hyperbolic tension basis function, rational
	 * 		linear shape controller, tension = 1.0, and 3rd order multic.
	 * 	- Creation and usage of Multic B Spline built off of processed hyperbolic tension basis function,
	 * 		rational linear shape controller, tension = 1.0, and 3rd order multic.
	 * 	- Creation and usage of Multic B Spline built off of raw cubic tension basis function, rational
	 * 		linear shape controller, tension = 0.0, and 3rd order multic.
	 * 	- Creation and usage of Multic B Spline built off of raw cubic tension basis function, rational
	 * 		linear shape controller, tension = 1.0, and 3rd order multic.
	 * 	- Creation and usage of Multic B Spline built off of raw cubic tension basis function, rational
	 * 		quadratic shape controller, tension = 1.0, and 3rd order multic.
	 * 	- Creation and usage of Multic B Spline built off of raw cubic tension basis function, rational
	 * 		exponential shape controller, tension = 1.0, and 3rd order multic.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BasisMulticBSplineSample()
		throws Exception
	{
		/*
		 * Creation and usage of Multic B Spline built off of raw hyperbolic tension basis function,
		 *  rational linear shape controller, tension = 1.0, and 3rd order multic.
		 */

		System.out.println ("\n    RAW TENSION HYPERBOLIC | LINEAR SHAPE CONTROL | TENSION = 1.0 | CUBIC B SPLINE");

		RunMulticBSplineTest (
			BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			1.,
			3
		);

		/*
		 * Creation and usage of Multic B Spline built off of processed hyperbolic tension basis function,
		 *  rational linear shape controller, tension = 1.0, and 3rd order multic.
		 */

		System.out.println ("\n   PROC TENSION HYPERBOLIC | LINEAR SHAPE CONTROL | TENSION = 1.0 | CUBIC B SPLINE");

		RunMulticBSplineTest (
			BasisHatPairGenerator.PROCESSED_TENSION_HYPERBOLIC,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			1.,
			3
		);

		/*
		 * Creation and usage of Multic B Spline built off of raw cubic tension basis function, rational
		 *  linear shape controller, tension = 0.0, and 3rd order multic.
		 */

		System.out.println ("\n   RAW CUBIC RATIONAL | LINEAR SHAPE CONTROL | TENSION = 0.0 | CUBIC B SPLINE");

		RunMulticBSplineTest (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			0.,
			3
		);

		/*
		 * Creation and usage of Multic B Spline built off of raw cubic tension basis function, rational
		 *  linear shape controller, tension = 1.0, and 3rd order multic.
		 */

		System.out.println ("\n   RAW CUBIC RATIONAL | LINEAR SHAPE CONTROL | TENSION = 1.0 | CUBIC B SPLINE");

		RunMulticBSplineTest (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			1.,
			3
		);

		/*
		 * Creation and usage of Multic B Spline built off of raw cubic tension basis function, rational
		 *  quadratic shape controller, tension = 1.0, and 3rd order multic.
		 */

		System.out.println ("\n   RAW CUBIC RATIONAL | QUADRATIC SHAPE CONTROL | TENSION = 1.0 | CUBIC B SPLINE");

		RunMulticBSplineTest (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_QUADRATIC,
			1.,
			3
		);

		/*
		 * Creation and usage of Multic B Spline built off of raw cubic tension basis function, rational
		 *  exponential shape controller, tension = 1.0, and 3rd order multic.
		 */

		System.out.println ("\n   RAW CUBIC RATIONAL | EXPONENTIAL SHAPE CONTROL | TENSION = 1.0 | CUBIC B SPLINE");

		RunMulticBSplineTest (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_EXPONENTIAL,
			1.,
			3
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		BasisMulticBSplineSample();
	}
}
