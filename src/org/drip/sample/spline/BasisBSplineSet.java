
package org.drip.sample.spline;

import org.drip.spline.basis.*;
import org.drip.spline.bspline.*;
import org.drip.spline.params.*;
import org.drip.spline.segment.LatentStateResponseModel;

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
 * BasisSplineSet implements Samples for the Construction and the usage of various basis spline functions. It
 *  demonstrates the following:
 * 	- Construction of segment control parameters - polynomial (regular/Bernstein) segment control,
 * 		exponential/hyperbolic tension segment control, Kaklis-Pandelis tension segment control, and C1
 * 		Hermite.
 * 	- Control the segment using the rational shape controller, and the appropriate Ck.
 * 	- Estimate the node value and the node value Jacobian with the segment, as well as at the boundaries.
 * 	- Calculate the segment monotonicity.

 * @author Lakshmi Krishnamurthy
 */

public class BasisBSplineSet {

	/*
	 * This sample demonstrates construction and usage of B Spline hat functions over solitary segments. It
	 * 	shows the constructions of left/right segments, their calibration, and Jacobian evaluation.
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

		/*
		 * Calibrate the left segment using the node values, and compute the segment Jacobian
		 */

		System.out.println (
			seg1.calibrate (
				25.,
				0.,
				20.25,
				null
			)
		);

		System.out.println ("\tY[" + 1.0 + "]: " + seg1.responseValue (1.));

		System.out.println ("\tY[" + 1.5 + "]: " + seg1.responseValue (1.5));
	}

	/*
	 * This sample demonstrates the construction and usage of the Basis B Spline Set Functionality. It shows
	 * 	the following:
	 * 	- Set up the B Spline Sequence Parameters for the Cubic Rational Hat Type, Linear Shape Controller,
	 * 		using the specified tension and derivative order parameters.
	 * 	- Setup the B Spline Basis Set.
	 * 	- Construct the segment inelastic parameter that is C2 (iK = 2 sets it to C2), with second order
	 * 		curvature penalty, and without constraint.
	 * 	- Construct and Evaluate the B Spline.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BasisBSplineSetSample()
		throws Exception
	{
		/*
		 * Set up the B Spline Sequence Parameters for the Cubic Rational Hat Type, Linear Shape Controller,
		 * 	using the specified tension and derivative order parameters.
		 */

		BSplineSequenceParams bssp = new BSplineSequenceParams (
			BasisHatPairGenerator.PROCESSED_CUBIC_RATIONAL,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			2,
			4,
			1.,
			2);

		/*
		 * Setup the B Spline Basis Set
		 */

		FunctionSet fsBSS = FunctionSetBuilder.BSplineBasisSet (bssp);

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
		 * Construct and Evaluate the B Spline
		 */

		TestSpline (
			fsBSS,
			null,
			segParams
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		BasisBSplineSetSample();
	}
}
