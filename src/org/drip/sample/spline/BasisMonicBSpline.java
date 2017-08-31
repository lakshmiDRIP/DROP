
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
 * BasisMonicBSpline implements Samples for the Construction and the usage of various monic basis B Splines.
 *  It demonstrates the following:
 * 	- Construction of segment B Spline Hat Basis Functions.
 * 	- Estimation of the derivatives and the basis envelope cumulative integrands.
 * 	- Estimation of the normalizer and the basis envelope cumulative normalized integrands.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisMonicBSpline {

	/*
	 * This sample illustrates the construction and the usage of the monic basis B Splines. It shows the
	 * 	following:
	 * 	- Construct the segment basis monic function from the specified hat type, the shape controller, the
	 * 		derivative order, and the tension.
	 * 	- Compare the responses emitted by the basis hat functions and the monic basis functions.
	 * 	- Compute the normalized cumulative emitted by the monic basis functions.
	 * 	- Compute the ordered derivative emitted by the monic basis functions.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void TestMonicHatBasis (
		final String strHatType,
		final String strShapeController,
		final TensionBasisHat[] aTBH,
		final double[] adblPredictorOrdinate,
		final String strTest)
		throws Exception
	{
		/*
		 * Construct the segment basis monic function from the specified hat type, the shape controller, the
		 *  derivative order, and the tension.
		 */

		SegmentBasisFunction me = SegmentBasisFunctionGenerator.Monic (
			strHatType,
			strShapeController,
			adblPredictorOrdinate,
			2,
			aTBH[0].tension()
		);

		/*
		 * Compare the responses emitted by the basis hat functions and the monic basis functions.
		 */

		double dblX = 1.0;
		double dblXIncrement = 0.25;

		System.out.println ("\n\t-------------------------------------------------");

		System.out.println ("\t--------------" + strTest + "-------------");

		System.out.println ("\t-------------------------------------------------\n");

		System.out.println ("\t-------------X---|---LEFT---|---RIGHT--|--MONIC--\n");

		while (dblX <= 3.0) {
			System.out.println (
				"\tResponse[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (aTBH[0].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (aTBH[1].evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (me.evaluate (dblX), 1, 5, 1.));

			dblX += dblXIncrement;
		}

		System.out.println ("\n\t------------------------------------------------\n");

		/*
		 * Compute the normalized cumulative emitted by the monic basis functions.
		 */

		dblX = 1.0;

		while (dblX <= 3.0) {
			System.out.println (
				"\t\tNormCumulative[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (me.normalizedCumulative (dblX), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}

		System.out.println ("\n\t------------------------------------------------\n");

		/*
		 * Compute the ordered derivative emitted by the monic basis functions.
		 */

		dblX = 1.0;
		int iOrder = 1;

		while (dblX <= 3.0) {
			System.out.println (
				"\t\t\tDeriv[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (me.derivative (dblX, iOrder), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}

		System.out.println ("\n\t-----------------------------------------------\n");
	}

	/*
	 * This sample illustrates the construction and usage of raw/processed basis tension splines, and their
	 * 	comparisons with the correspondingly constructed monic hat basis functions. It shows the following:
	 * 	- Construct the Processed Hyperbolic Tension Hat Pair from the co-ordinate arrays, the Ck, and the
	 * 		tension.
	 * 	- Implement and test the basis monic spline function using the constructed Processed Hyperbolic
	 * 		Tension Hat Pair and the Rational Linear Shape Controller.
	 * 	- Construct the Raw Hyperbolic Tension Hat Pair from the co-ordinate arrays and the tension.
	 * 	- Implement and test the basis monic spline function using the constructed Raw Hyperbolic Tension Hat
	 * 		Pair and the Rational Linear Shape Controller.
	 * 	- Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Linear
	 * 		Rational Shape Controller, and no tension.
	 * 	- Implement and test the basis monic spline function using the constructed Flat Processed Cubic
	 * 		Tension Hat Pair and the Rational Linear Shape Controller.
	 * 	- Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Linear
	 * 		Rational Shape Controller, and non-zero tension.
	 * 	- Implement and test the basis monic spline function using the constructed Processed Cubic Rational
	 * 		Tension Hat Pair and the Rational Linear Shape Controller.
	 * 	- Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Quadratic
	 * 		Rational Shape Controller, and the tension.
	 * 	- Implement and test the basis monic spline function using the constructed Processed Cubic Rational
	 * 		Tension Hat Pair and the Quadratic Linear Shape Controller.
	 * 	- Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Exponential
	 * 		Rational Shape Controller, and the tension.
	 * 	- Implement and test the basis monic spline function using the constructed Processed Cubic Rational
	 * 		Tension Hat Pair and the Rational Exponential Shape Controller.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BasisMonicBSplineSample()
		throws Exception
	{
		double[] adblPredictorOrdinate = new double[] {1., 2., 3.};

		/*
		 * Construct the Processed Hyperbolic Tension Hat Pair from the co-ordinate arrays, the Ck, and the
		 *  tension.
		 */

		TensionBasisHat[] aTBHProcessed = BasisHatPairGenerator.ProcessedHyperbolicTensionHatPair (
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Processed Hyperbolic
		 * 	Tension Hat Pair and the Rational Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_TENSION_HYPERBOLIC,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			aTBHProcessed,
			adblPredictorOrdinate,
			" PROCESSED HYPERBOLIC "
		);

		/*
		 * Construct the Raw Hyperbolic Tension Hat Pair from the co-ordinate arrays and the tension.
		 */

		TensionBasisHat[] aTBHStraight = BasisHatPairGenerator.HyperbolicTensionHatPair (
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Raw Hyperbolic Tension
		 * 	Hat Pair and the Rational Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			aTBHStraight,
			adblPredictorOrdinate,
			" STRAIGHT  HYPERBOLIC "
		);

		/*
		 * Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Linear
		 * 	Rational Shape Controller, and no tension.
		 */

		TensionBasisHat[] aTBHCubicRationalPlain = BasisHatPairGenerator.ProcessedCubicRationalHatPair (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			0.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Flat Processed Cubic
		 * 	Tension Hat Pair and the Rational Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			aTBHCubicRationalPlain,
			adblPredictorOrdinate,
			"     CUBIC     FLAT   "
		);

		/*
		 * Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Linear
		 * 	Rational Shape Controller, and non-zero tension.
		 */

		TensionBasisHat[] aTBHCubicRationalLinear = BasisHatPairGenerator.ProcessedCubicRationalHatPair (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Processed Cubic Rational
		 * 	Tension Hat Pair and the Rational Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			aTBHCubicRationalLinear,
			adblPredictorOrdinate,
			" CUBIC LINEAR RATIONAL "
		);

		/*
		 * Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Quadratic
		 * 	Rational Shape Controller, and the tension.
		 */

		TensionBasisHat[] aTBHCubicRationalQuadratic = BasisHatPairGenerator.ProcessedCubicRationalHatPair (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_QUADRATIC,
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Processed Cubic Rational
		 * 	Tension Hat Pair and the Quadratic Linear Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_QUADRATIC,
			aTBHCubicRationalQuadratic,
			adblPredictorOrdinate,
			" CUBIC  QUAD  RATIONAL "
		);

		/*
		 * Construct the Processed Cubic Rational Tension Hat Pair from the co-ordinate arrays, Exponential
		 * 	Rational Shape Controller, and the tension.
		 */

		TensionBasisHat[] aTBHCubicRationalExponential = BasisHatPairGenerator.ProcessedCubicRationalHatPair (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_EXPONENTIAL,
			adblPredictorOrdinate[0],
			adblPredictorOrdinate[1],
			adblPredictorOrdinate[2],
			2,
			1.
		);

		/*
		 * Implement and test the basis monic spline function using the constructed Processed Cubic Rational
		 * 	Tension Hat Pair and the Rational Exponential Shape Controller.
		 */

		TestMonicHatBasis (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_EXPONENTIAL,
			aTBHCubicRationalExponential,
			adblPredictorOrdinate,
			" CUBIC  EXP  RATIONAL "
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		BasisMonicBSplineSample();
	}
}
