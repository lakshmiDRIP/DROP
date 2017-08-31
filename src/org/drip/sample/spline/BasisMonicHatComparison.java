
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
 * BasisMonicBSpline implements the comparison of the basis hat functions used in the construction of the
 *  monic basis B Splines. It demonstrates the following:
 * 	- Construction of the Linear Cubic Rational Raw Hat Functions
 * 	- Construction of the Quadratic Cubic Rational Raw Hat Functions
 * 	- Construction of the Corresponding Processed Tension Basis Hat Functions
 * 	- Construction of the Wrapping Monic Functions
 * 	- Estimation and Comparison of the Ordered Derivatives
 *
 * @author Lakshmi Krishnamurthy
 */

public class BasisMonicHatComparison {

	/*
	 * This sample display the test of the different shape controller functions. It demonstrates the
	 * 	following:
	 * 	- Construct the Raw Cubic rational left Tension Basis using the specified shape controller and
	 * 		tension.
	 * 	- Construct the Raw Cubic rational right Tension Basis using the specified shape controller and
	 *  	tension.
	 *  - Construct the processed Cubic rational left Tension Basis using the Raw Cubic rational left Tension
	 *  	Basis.
	 *  - Construct the processed Cubic rational Right Tension Basis using the Raw Cubic rational Right
	 *  	Tension Basis.
	 *  - Construct the Segment Monic Basis Function using the left and the right processed hat functions.
	 *  - Display the response and the derivatives for the left/right cubic rational, and their corresponding
	 *  	processed tension hat basis functions.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void ShapeControllerTest (
		final String strShapeController,
		final double dblTension)
		throws Exception
	{
		/*
		 * Construct the Raw Cubic rational left Tension Basis using the specified shape controller and
		 *  tension.
		 */

		CubicRationalLeftRaw crlr = new CubicRationalLeftRaw (
			1.,
			2.,
			strShapeController,
			dblTension
		);

		/*
		 * Construct the Raw Cubic rational right Tension Basis using the specified shape controller and
		 * 	tension.
		 */

		CubicRationalRightRaw crrr = new CubicRationalRightRaw (
			2.,
			3.,
			strShapeController,
			dblTension
		);

		/*
		 * Construct the processed Cubic rational left Tension Basis using the Raw Cubic rational left
		 * 	Tension Basis.
		 */

		TensionProcessedBasisHat tpbhLeft = new TensionProcessedBasisHat (
			crlr,
			2
		);

		/*
		 * Construct the processed Cubic rational right Tension Basis using the Raw Cubic rational Right
		 * 	Tension Basis.
		 */

		TensionProcessedBasisHat tpbhRight = new TensionProcessedBasisHat (
			crrr,
			2
		);

		/*
		 * Construct the Segment Monic Basis Function using the left and the right processed hat functions.
		 */

		SegmentMonicBasisFunction smbf = new SegmentMonicBasisFunction (
			tpbhLeft,
			tpbhRight
		);

		/*
		 * Display the response and the derivatives for the left/right cubic rational, and their
		 *  corresponding processed tension hat basis functions.
		 */

		double dblX = crlr.left();

		while (dblX <= crrr.right()) {
			System.out.println ("\tDeriv[" + dblX + "] => " +
				FormatUtil.FormatDouble (smbf.derivative (dblX, 1), 1, 5, 1.));

			System.out.println ("\t\tCubic Rational Left Deriv[" + dblX + "]  => " +
				FormatUtil.FormatDouble (crlr.derivative (dblX, 3), 1, 5, 1.));

			System.out.println ("\t\tCubic Rational Right Deriv[" + dblX + "] => " +
				FormatUtil.FormatDouble (crrr.derivative (dblX, 3), 1, 5, 1.));

			System.out.println ("\t\tTPBH Left Deriv[" + dblX + "]  => " +
				FormatUtil.FormatDouble (tpbhLeft.derivative (dblX, 1), 1, 5, 1.));

			System.out.println ("\t\tTPBH Right Deriv[" + dblX + "] => " +
				FormatUtil.FormatDouble (tpbhRight.derivative (dblX, 1), 1, 5, 1.));

			dblX += 0.5;
		}
	}

	/*
	 * Sample illustrating the construction and usage of different monic basis hat shape controllers. This
	 * 	example illustrates the following:
	 * 	- Test Rational Linear Shape Control with 0.0 Tension Parameter (i.e., no shape control).
	 * 	- Test Rational Linear Shape Control with 1.0 Tension Parameter.
	 * 	- Test Rational Quadratic Shape Control with 1.0 Tension Parameter.
	 * 	- Test Exponential Shape Control with 1.0 Tension Parameter.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BasisMonicHatComparisonSample()
		throws Exception
	{
		/*
		 * Test Rational Linear Shape Control with 0.0 Tension Parameter (i.e., no shape control)
		 */

		System.out.println ("\n-------------------------------------------------------------------");

		System.out.println ("----------------- NO SHAPE CONTROL --------------------------------");

		System.out.println ("-------------------------------------------------------------------");

		ShapeControllerTest (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			0.
		);

		/*
		 * Test Rational Linear Shape Control with 1.0 Tension Parameter
		 */

		System.out.println ("\n-------------------------------------------------------------------");

		System.out.println ("----------------- LINEAR SHAPE CONTROL; Tension 1.0 ---------------");

		System.out.println ("-------------------------------------------------------------------");

		ShapeControllerTest (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			1.
		);

		/*
		 * Test Rational Quadratic Shape Control with 1.0 Tension Parameter
		 */

		System.out.println ("\n-------------------------------------------------------------------");

		System.out.println ("-------------- QUADRATIC SHAPE CONTROL; Tension 1.0 ---------------");

		System.out.println ("-------------------------------------------------------------------");

		ShapeControllerTest (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_QUADRATIC,
			1.
		);

		/*
		 * Test Exponential Shape Control with 1.0 Tension Parameter
		 */

		System.out.println ("\n-------------------------------------------------------------------");

		System.out.println ("-------------- EXPONENTIAL SHAPE CONTROL; Tension 1.0 ---------------");

		System.out.println ("-------------------------------------------------------------------");

		ShapeControllerTest (
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_EXPONENTIAL,
			1.
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		BasisMonicHatComparisonSample();
	}
}
