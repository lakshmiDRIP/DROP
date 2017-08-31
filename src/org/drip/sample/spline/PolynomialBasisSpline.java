
package org.drip.sample.spline;

import org.drip.function.r1tor1.*;
import org.drip.quant.calculus.WengertJacobian;
import org.drip.spline.basis.*;
import org.drip.spline.params.*;
import org.drip.spline.segment.*;

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
 * PolynomialBasisSpline implements Samples for the Construction and the usage of polynomial (both regular
 * 	and Hermite) basis spline functions. It demonstrates the following:
 * 	- Control the polynomial segment using the rational shape controller, the appropriate Ck, and the basis
 * 		function.
 * 	- Demonstrate the variational shape optimization behavior.
 * 	- Estimate the node value and the node value Jacobian with the segment, as well as at the boundaries.
 * 	- Calculate the segment monotonicity and the curvature penalty.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PolynomialBasisSpline {

	/*
	 * This sample demonstrates the following:
	 * 
	 * 	- Construction of two segments, 1 and 2.
	 *  - Calibration of the segments to the left and the right node values
	 *  - Extraction of the segment Jacobians and segment monotonicity
	 *  - Estimate point value and the Jacobian, monotonicity, and curvature penalty
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void TestPolynomialSpline (
		final int iNumBasis,
		final int iCk,
		final int iRoughnessPenaltyDerivativeOrder,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		System.out.println (" ------------------------------ \n     POLYNOMIAL n = " + iNumBasis +
			"; Ck = " + iCk + "\n ------------------------------ \n");

		/*
		 * Construct the segment inelastic parameter that is C2 (iCk = 2 sets it to C2), without constraint
		 */

		SegmentInelasticDesignControl sdic = SegmentInelasticDesignControl.Create (
			iCk,
			iRoughnessPenaltyDerivativeOrder
		);

		/*
		 * Create the basis parameter set from the number of basis functions, and construct the basis
		 */

		PolynomialFunctionSetParams pfsp = new PolynomialFunctionSetParams (iNumBasis);

		FunctionSet fs = FunctionSetBuilder.PolynomialBasisSet (pfsp);

		/*
		 * Construct the left and the right segments
		 */

		LatentStateResponseModel ecs1 = LatentStateResponseModel.Create (
			1.0,
			1.5,
			fs,
			rssc,
			sdic
		);

		LatentStateResponseModel ecs2 = LatentStateResponseModel.Create (
			1.5,
			2.0,
			fs,
			rssc,
			sdic
		);

		/*
		 * Calibrate the left segment using the node values, and compute the segment Jacobian, monotonicity, and curvature penalty
		 */

		WengertJacobian wj1 = ecs1.jackDCoeffDEdgeParams (
			25.,
			0.,
			20.25,
			null
		);

		System.out.println ("\tY[" + 1.0 + "]: " + ecs1.responseValue (1.));

		System.out.println ("\tY[" + 1.5 + "]: " + ecs1.responseValue (1.5));

		System.out.println ("Segment 1 Jacobian: " + wj1.displayString());

		System.out.println ("Segment 1 Head: " + ecs1.jackDCoeffDEdgeInputs().displayString());

		System.out.println ("Segment 1 Monotone Type: " + ecs1.monotoneType());

		System.out.println ("Segment 1 DPE: " + ecs1.curvatureDPE());

		/*
		 * Calibrate the right segment using the node values, and compute the segment Jacobian, monotonicity, and curvature penalty
		 */

		WengertJacobian wj2 = ecs2.jackDCoeffDEdgeParams (
			ecs1,
			"Default",
			16.,
			null,
			Double.NaN,
			null
		);

		System.out.println ("\tY[" + 1.5 + "]: " + ecs2.responseValue (1.5));

		System.out.println ("\tY[" + 2. + "]: " + ecs2.responseValue (2.));

		System.out.println ("Segment 2 Jacobian: " + wj2.displayString());

		System.out.println ("Segment 2 Regular Jacobian: " + ecs2.jackDCoeffDEdgeInputs().displayString());

		System.out.println ("Segment 2 Monotone Type: " + ecs2.monotoneType());

		System.out.println ("Segment 2 DPE: " + ecs2.curvatureDPE());

		/*
		 * Re-calibrate Segment #2 with a new Response Value
		 */

		ecs2.calibrate (
			ecs1,
			14.,
			null
		);

		/*
		 * Estimate the segment value at the given variate, and compute the corresponding Jacobian, and curvature penalty
		 */

		double dblX = 2.0;

		System.out.println ("\t\tValue[" + dblX + "]: " + ecs2.responseValue (dblX));

		System.out.println ("\t\tValue Jacobian[" + dblX + "]: " + ecs2.jackDResponseDEdgeInput (dblX, 1).displayString());

		System.out.println ("\t\tSegment 2 DPE: " + ecs2.curvatureDPE());
	}

	/*
	 * This sample demonstrates the following specifically for the Ck Hermite Splines, which are calibrated
	 *  using left and right node values, along with their derivatives:
	 * 
	 * 	- Construction of two segments, 1 and 2.
	 *  - Calibration of the segments to the left and the right node values
	 *  - Extraction of the segment Jacobians and segment monotonicity
	 *  - Estimate point value and the Jacobian, monotonicity, and curvature penalty
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void TestC1HermiteSpline (
		final int iNumBasis,
		final int iCk,
		final int iRoughnessPenaltyDerivativeOrder,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		System.out.println (" ------------------------------ \n     HERMITE POLYNOMIAL n = " + iNumBasis +
			"; Ck = " + iCk + "\n ------------------------------ \n");

		/*
		 * Construct the segment inelastic parameter that is C2 (iCk = 2 sets it to C2), without constraint
		 */

		SegmentInelasticDesignControl sdic = SegmentInelasticDesignControl.Create (
			iCk,
			iRoughnessPenaltyDerivativeOrder
		);

		/*
		 * Create the basis parameter set from the number of basis functions, and construct the basis
		 */

		PolynomialFunctionSetParams pfsp = new PolynomialFunctionSetParams (iNumBasis);

		FunctionSet fs = FunctionSetBuilder.PolynomialBasisSet (pfsp);

		/*
		 * Construct the left and the right segments
		 */

		LatentStateResponseModel ecs1 = LatentStateResponseModel.Create (
			0.0,
			1.0,
			fs,
			rssc,
			sdic
		);

		LatentStateResponseModel ecs2 = LatentStateResponseModel.Create (
			1.0,
			2.0,
			fs,
			rssc,
			sdic
		);

		/*
		 * Calibrate the left segment using the node values, and compute the segment Jacobian, monotonicity, and curvature penalty
		 */

		ecs1.calibrateState (
			new SegmentStateCalibrationInputs (
				new double[] {0., 1.}, // Segment Calibration Nodes
				new double[] {1., 4.}, // Segment Calibration Values
				new double[] {1.}, // Segment Left Derivative
				new double[] {6.}, // Segment Left Derivative
				null,
				null // Segment Constraint AND Fitness Penalty Response
			)
		);

		System.out.println ("\tY[" + 0.0 + "]: " + ecs1.responseValue (0.0));

		System.out.println ("\tY[" + 1.0 + "]: " + ecs1.responseValue (1.0));

		System.out.println ("Segment 1 Head: " + ecs1.jackDCoeffDEdgeInputs().displayString());

		System.out.println ("Segment 1 Monotone Type: " + ecs1.monotoneType());

		System.out.println ("Segment 1 DPE: " + ecs1.curvatureDPE());

		/*
		 * Calibrate the right segment using the node values, and compute the segment Jacobian, monotonicity, and curvature penalty
		 */

		ecs2.calibrateState (
			new SegmentStateCalibrationInputs (
				new double[] {1., 2.}, // Segment Calibration Nodes
				new double[] {4., 15.}, // Segment Calibration Values
				new double[] {6.}, // Segment Left Derivative
				new double[] {17.}, // Segment Left Derivative
				null, // Segment Constraint
				null // Fitness Penalty Response
			)
		);

		System.out.println ("\tY[" + 1.0 + "]: " + ecs2.responseValue (1.0));

		System.out.println ("\tY[" + 2.0 + "]: " + ecs2.responseValue (2.0));

		System.out.println ("Segment 2 Regular Jacobian: " + ecs2.jackDCoeffDEdgeInputs().displayString());

		System.out.println ("Segment 2 Monotone Type: " + ecs2.monotoneType());

		System.out.println ("Segment 2 DPE: " + ecs2.curvatureDPE());

		/*
		 * Re-calibrate Segment #2 with a new Response Value
		 */

		ecs2.calibrate (
			ecs1,
			14.,
			null
		);

		/*
		 * Estimate the segment value at the given variate, and compute the corresponding Jacobian, monotonicity, and curvature penalty
		 */

		double dblX = 2.0;

		System.out.println ("\t\tValue[" + dblX + "]: " + ecs2.responseValue (dblX));

		System.out.println ("\t\tValue Jacobian[" + dblX + "]: " + ecs2.jackDResponseDEdgeInput (dblX, 1).displayString());

		System.out.println ("\t\tSegment 2 DPE: " + ecs2.curvatureDPE());
	}

	/*
	 * This sample illustrates the construction and usage for polynomial basis splines. It shows the
	 * 	following:
	 * 	- Construct a rational shape controller with the specified shape controller tension.
	 * 	- Set the Roughness Penalty to 2nd order Roughness Penalty Derivative Order.
	 * 	- Test the polynomial spline across different polynomial degrees and Ck's.
	 * 	- Test the C1 Hermite spline.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void PolynomialBasisSplineSample()
		throws Exception
	{
		/*
		 * Construct a rational shape controller with the shape controller tension of 1.
		 */

		double dblShapeControllerTension = 1.;

		ResponseScalingShapeControl rssc = new ResponseScalingShapeControl (
			true,
			new QuadraticRationalShapeControl (dblShapeControllerTension)
		);

		/*
		 * Set to 2nd order Roughness Penalty Derivative Order.
		 */

		int iRoughnessPenaltyDerivativeOrder = 2;

		/*
		 * Test the polynomial spline across different polynomial degrees and Ck's
		 */

		TestPolynomialSpline (2, 0, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (3, 0, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (3, 1, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (4, 0, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (4, 1, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (4, 2, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (5, 0, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (5, 1, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (5, 2, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (5, 3, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (6, 0, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (6, 1, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (6, 2, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (6, 3, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (6, 4, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (7, 0, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (7, 1, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (7, 2, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (7, 3, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (7, 4, iRoughnessPenaltyDerivativeOrder, rssc);

		TestPolynomialSpline (7, 5, iRoughnessPenaltyDerivativeOrder, rssc);

		/*
		 * Test the C1 Hermite spline
		 */

		System.out.println (" -------------------- \n Ck HERMITE \n -------------------- \n");

		TestC1HermiteSpline (4, 1, iRoughnessPenaltyDerivativeOrder, rssc);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		PolynomialBasisSplineSample();
	}
}
