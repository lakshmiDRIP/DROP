
package org.drip.sample.spline;

import org.drip.function.r1tor1custom.QuadraticRationalShapeControl;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.*;
import org.drip.spline.params.*;
import org.drip.spline.segment.*;

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
 * <i>PolynomialBasisSpline</i> implements Samples for the Construction and the usage of polynomial (both
 * 	regular and Hermite) basis spline functions. It demonstrates the following:
 * 	- Control the polynomial segment using the rational shape controller, the appropriate Ck, and the basis
 * 		function.
 * 	- Demonstrate the variational shape optimization behavior.
 * 	- Estimate the node value and the node value Jacobian with the segment, as well as at the boundaries.
 * 	- Calculate the segment monotonicity and the curvature penalty.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/spline/README.md">Basis Monic Multic Tension Spline</a></li>
 *  </ul>
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

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		PolynomialBasisSplineSample();

		EnvManager.TerminateEnv();
	}
}
