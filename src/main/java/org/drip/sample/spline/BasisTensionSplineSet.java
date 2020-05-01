
package org.drip.sample.spline;

import org.drip.function.r1tor1.*;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.*;
import org.drip.spline.params.*;
import org.drip.spline.segment.*;
import org.drip.spline.tension.KochLycheKvasovFamily;

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
 * <i>BasisTensionSplineSet</i> implements Samples for the Construction and the usage of various basis spline
 *  functions. It demonstrates the following:
 * 	- Construction of Kocke-Lyche-Kvasov tension spline segment control parameters - using hyperbolic,
 * 		exponential, rational linear, and rational quadratic primitives.
 * 	- Control the segment using the rational shape controller, and the appropriate Ck.
 * 	- Estimate the node value and the node value Jacobian with the segment, as well as at the boundaries.
 * 	- Calculate the segment monotonicity.
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

public class BasisTensionSplineSet {

	/*
	 * Sample demonstrating the creation of the KLK Hyperbolic tension basis spline set
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FunctionSet KLKHyperbolicTensionSpline()
		throws Exception
	{
		double dblTension = .01;

		/*
		 * Create the basis parameter set from the segment tension parameter, and construct the basis
		 */

		ExponentialTensionSetParams etbsbp = new ExponentialTensionSetParams (dblTension);

		return KochLycheKvasovFamily.FromHyperbolicPrimitive (etbsbp);
	}

	/*
	 * Sample demonstrating the creation of the KLK Rational Linear tension basis spline set
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FunctionSet KLKRationalLinearTensionSpline()
		throws Exception
	{
		double dblTension = 1.;

		/*
		 * Create the basis parameter set from the segment tension parameter, and construct the basis
		 */

		ExponentialTensionSetParams etbsbp = new ExponentialTensionSetParams (dblTension);

		return KochLycheKvasovFamily.FromRationalLinearPrimitive (etbsbp);
	}

	/*
	 * Sample demonstrating the creation of the KLK Rational Quadratic tension basis spline set
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FunctionSet KLKRationalQuadraticTensionSpline()
		throws Exception
	{
		double dblTension = 1.;

		/*
		 * Create the basis parameter set from the segment tension parameter, and construct the basis
		 */

		ExponentialTensionSetParams etbsbp = new ExponentialTensionSetParams (dblTension);

		return KochLycheKvasovFamily.FromRationalQuadraticPrimitive (etbsbp);
	}

	/*
	 * Sample demonstrating the creation of the KLK Exponential tension basis spline set
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FunctionSet KLKExponentialTensionSpline()
		throws Exception
	{
		double dblTension = 1.;

		/*
		 * Create the basis parameter set from the segment tension parameter, and construct the basis
		 */

		ExponentialTensionSetParams etbsbp = new ExponentialTensionSetParams (dblTension);

		return KochLycheKvasovFamily.FromExponentialPrimitive (etbsbp);
	}

	/*
	 * This sample demonstrates the following:
	 * 
	 * 	- Construction of two segments, 1 and 2.
	 *  - Calibration of the segments to the left and the right node values
	 *  - Extraction of the segment Jacobians and segment monotonicity
	 *  - Estimate point value and the Jacobian
	 *  - Estimate the curvature penalty
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void TestSpline (
		final FunctionSet fs,
		final ResponseScalingShapeControl rssc,
		final SegmentInelasticDesignControl segParams)
		throws Exception
	{
		/*
		 * Construct the left and the right segments
		 */

		LatentStateResponseModel seg1 = LatentStateResponseModel.Create (
			1.0,
			1.5,
			fs,
			rssc,
			segParams
		);

		LatentStateResponseModel seg2 = LatentStateResponseModel.Create (
			1.5,
			2.0,
			fs,
			rssc,
			segParams
		);

		/*
		 * Calibrate the left segment using the node values, and compute the segment Jacobian, the monotonicity, and the curvature penalty
		 */

		WengertJacobian wj1 = seg1.jackDCoeffDEdgeParams (
			25.,
			0.,
			20.25,
			null
		);

		System.out.println ("\tY[" + 1.0 + "]: " + seg1.responseValue (1.));

		System.out.println ("\tY[" + 1.5 + "]: " + seg1.responseValue (1.5));

		System.out.println ("Segment 1 Jacobian: " + wj1.displayString());

		System.out.println ("Segment 1 Head: " + seg1.jackDCoeffDEdgeInputs().displayString());

		System.out.println ("Segment 1 Monotone Type: " + seg1.monotoneType());

		System.out.println ("\tSegment 1 DPE: " + seg1.curvatureDPE());

		/*
		 * Calibrate the right segment using the node values, and compute the segment Jacobian, the monotonicity, and the curvature penalty
		 */

		WengertJacobian wj2 = seg2.jackDCoeffDEdgeParams (
			seg1,
			"Default",
			16.,
			null,
			Double.NaN,
			null
		);

		System.out.println ("\tY[" + 1.5 + "]: " + seg2.responseValue (1.5));

		System.out.println ("\tY[" + 2. + "]: " + seg2.responseValue (2.));

		System.out.println ("Segment 2 Jacobian: " + wj2.displayString());

		System.out.println ("Segment 2 Regular Jacobian: " + seg2.jackDCoeffDEdgeInputs().displayString());

		System.out.println ("Segment 2 Monotone Type: " + seg2.monotoneType());

		System.out.println ("\tSegment 2 DPE: " + seg2.curvatureDPE());

		/*
		 * Re-calibrate Segment #2 with a different response value
		 */

		seg2.calibrate (
			seg1,
			14.,
			null
		);

		/*
		 * Estimate the segment value at the given variate, and compute the corresponding Jacobian and the curvature penalty
		 */

		double dblX = 2.0;

		System.out.println ("\t\tValue[" + dblX + "]: " + seg2.responseValue (dblX));

		System.out.println ("\t\tValue Jacobian[" + dblX + "]: " + seg2.jackDResponseDEdgeInput (dblX, 1).displayString());

		System.out.println ("\t\tSegment 2 DPE: " + seg2.curvatureDPE());
	}

	/*
	 * This sample illustrates the construction and the usage of basis splines (all types, really). It shows
	 *  the following:
	 * 	- Construct a rational shape controller with the specified shape controller tension.
	 * 	- Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with second order
	 * 		curvature penalty, and without constraint.
	 * 	- Test the KLK Hyperbolic Tension basis tension spline.
	 * 	- Test the KLK Rational Linear basis tension spline.
	 * 	- Test the KLK Rational Quadratic basis tension spline.
	 * 	- Test the KLK Exponential Tension basis tension spline.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BasisTensionSplineSetSample()
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
		 * Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with second order
		 *  curvature penalty, and without constraint
		 */

		int iK = 2;
		int iCurvaturePenaltyDerivativeOrder = 2;

		SegmentInelasticDesignControl segParams = SegmentInelasticDesignControl.Create (
			iK,
			iCurvaturePenaltyDerivativeOrder
		);

		/*
		 * Test the KLK Hyperbolic tension spline
		 */

		System.out.println ( " ----------- \n KLK HYPERBOLIC \n ----------- \n");

		TestSpline (
			KLKHyperbolicTensionSpline(),
			rssc,
			segParams
		);

		/*
		 * Test the KLK Rational Linear tension spline
		 */

		System.out.println ( " ----------- \n KLK RATIONAL LINEAR \n ----------- \n");

		TestSpline (
			KLKRationalLinearTensionSpline(),
			rssc,
			segParams
		);

		/*
		 * Test the KLK Rational Quadratic tension spline
		 */

		System.out.println ( " ----------- \n KLK RATIONAL QUADRATIC \n ----------- \n");

		TestSpline (
			KLKRationalQuadraticTensionSpline(),
			rssc,
			segParams
		);

		/*
		 * Test the KLK Exponential tension spline
		 */

		System.out.println ( " ----------- \n KLK EXPONENTIAL \n ----------- \n");

		TestSpline (
			KLKExponentialTensionSpline(),
			rssc,
			segParams
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		BasisTensionSplineSetSample();

		EnvManager.TerminateEnv();
	}
}
