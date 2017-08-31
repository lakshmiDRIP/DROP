
package org.drip.sample.funding;

import org.drip.function.r1tor1.LinearRationalShapeControl;
import org.drip.quant.common.FormatUtil;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.pchip.*;
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
 * This sample illustrates using the Hagan and West (2006) Estimator. It provides the following
 *  functionality:
 * 	- Set up the Predictor ordinates and the response values..
 * 	- Construct the rational linear shape control with the specified tension.
 * 	- Create the Segment Inelastic design using the Ck and Curvature Penalty Derivatives.
 * 	- Build the Array of Segment Custom Builder Control Parameters of the KLK Hyperbolic Tension Basis
 * 		Type, the tension, the segment inelastic design control, and the shape controller.
 * 	- Setup the monotone convex stretch using the above settings, and with no linear inference, no
 * 		spurious extrema, or no monotone filtering applied.
 * 	- Setup the monotone convex stretch using the above settings, and with linear inference, no spurious
 * 		extrema, or no monotone filtering applied.
 * 	- Compute and display the monotone convex output with the linear forward state.
 * 	- Compute and display the monotone convex output with the harmonic forward state.
 *
 * @author Lakshmi Krishnamurthy
 */

public class HaganWestForwardInterpolator {

	/*
	 * Display the monotone convex response value pre- and post- positivity enforcement.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static void DisplayOP (
		final MonotoneConvexHaganWest mchw,
		final MultiSegmentSequence mss,
		final double[] adblTime)
		throws Exception
	{
		/*
		 * Compare the stretch response values with that of the Monotone Convex across the range of the
		 * 	predictor ordinates pre-positivity enforcement.
		 */

		double dblTimeBegin = 0.;
		double dblTimeFinish = 30.;
		double dblTimeDelta = 3.00;
		double dblTime = dblTimeBegin;

		while (dblTime <= dblTimeFinish) {
			System.out.println ("\t\tResponse[" +
				FormatUtil.FormatDouble (dblTime, 2, 2, 1.) + "]: " +
				FormatUtil.FormatDouble (mchw.evaluate (dblTime), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (mss.responseValue (dblTime), 1, 6, 1.)
			);

			dblTime += dblTimeDelta;
		}

		/*
		 * Verify if the monotone convex positivity enforcement succeeded
		 */

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t\tPositivity enforced? " + mchw.enforcePositivity());

		System.out.println ("\t----------------------------------------------------------------");

		dblTime = dblTimeBegin;

		/*
		 * Compare the stretch response values with that of the Monotone Convex across the range of the
		 * 	predictor ordinates post-positivity enforcement.
		 */

		while (dblTime <= dblTimeFinish) {
			System.out.println ("\t\tPositivity Enforced Response[" +
				FormatUtil.FormatDouble (dblTime, 2, 2, 1.) + "]: " +
				FormatUtil.FormatDouble (mchw.evaluate (dblTime), 1, 6, 1.) + " | " +
				FormatUtil.FormatDouble (mss.responseValue (dblTime), 1, 6, 1.)
			);

			dblTime += dblTimeDelta;
		}
	}

	/*
	 * This sample demonstrates the construction and usage of the Monotone Hagan West Functionality. It shows
	 * 	the following:
	 * 	- Set up the Predictor ordinates and the response values..
	 * 	- Construct the rational linear shape control with the specified tension.
	 * 	- Create the Segment Inelastic design using the Ck and Curvature Penalty Derivatives.
	 * 	- Build the Array of Segment Custom Builder Control Parameters of the KLK Hyperbolic Tension Basis
	 * 		Type, the tension, the segment inelastic design control, and the shape controller.
	 * 	- Setup the monotone convex stretch using the above settings, and with no linear inference, no
	 * 		spurious extrema, or no monotone filtering applied.
	 * 	- Setup the monotone convex stretch using the above settings, and with linear inference, no spurious
	 * 		extrema, or no monotone filtering applied.
	 * 	- Compute and display the monotone convex output with the linear forward state.
	 * 	- Compute and display the monotone convex output with the harmonic forward state.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void MonotoneHaganWestInterpolatorSample()
		throws Exception
	{
		/*
		 * Set up the Predictor ordinates and the response values.
		 */

		double[] adblTime = new double[] {
			0., 0.10, 1.0, 4.0, 9.0, 20.0, 30.0
		};
		double[] adblForwardRate = new double[] {
			1.008, 1.073, 1.221, 1.878, 2.226, 2.460
		};

		/*
		 * Construct the rational linear shape control with the specified tension.
		 */

		double dblShapeControllerTension = 1.;

		ResponseScalingShapeControl rssc = new ResponseScalingShapeControl (
			false,
			new LinearRationalShapeControl (dblShapeControllerTension)
		);

		int iK = 2;
		int iCurvaturePenaltyDerivativeOrder = 2;

		/*
		 * Create the Segment Inelastic design using the Ck and Curvature Penalty Derivatives.
		 */

		SegmentInelasticDesignControl sdic = SegmentInelasticDesignControl.Create (
			iK,
			iCurvaturePenaltyDerivativeOrder
		);

		/*
		 * Build the Array of Segment Custom Builder Control Parameters of the KLK Hyperbolic Tension Basis
		 * 	Type, the tension, the segment inelastic design control, and the shape controller.
		 */

		double dblKLKTension = 1.;

		SegmentCustomBuilderControl scbc = new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
			new ExponentialTensionSetParams (dblKLKTension),
			sdic,
			rssc,
			null
		);

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblForwardRate.length];

		for (int i = 0; i < adblForwardRate.length; ++i)
			aSCBC[i] = scbc;

		/*
		 * Setup the monotone convex stretch using the above settings, and with no linear inference, no
		 * 	spurious extrema, or no monotone filtering applied.
		 */

		MultiSegmentSequence mssLinear = LocalControlStretchBuilder.CreateMonotoneConvexStretch (
			"MSS_LINEAR",
			adblTime,
			adblForwardRate,
			aSCBC,
			null,
			MultiSegmentSequence.CALIBRATE,
			false,
			false,
			false
		);

		/*
		 * Setup the monotone convex stretch using the above settings, and with linear inference, no
		 * 	spurious extrema, or no monotone filtering applied.
		 */

		MultiSegmentSequence mssHarmonic = LocalControlStretchBuilder.CreateMonotoneConvexStretch (
			"MSS_HARMONIC",
			adblTime,
			adblForwardRate,
			aSCBC,
			null,
			MultiSegmentSequence.CALIBRATE,
			true,
			false,
			false
		);

		/*
		 * Compute and display the monotone convex output with the linear forward state.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t     MONOTONE CONVEX HAGAN WEST WITH LINEAR FORWARD STATE");

		System.out.println ("\t----------------------------------------------------------------");

		/*
		 * Compute and display the monotone convex output with the harmonic forward state.
		 */

		DisplayOP (
			MonotoneConvexHaganWest.Create (
				adblTime,
				adblForwardRate,
				false
			),
			mssLinear,
			adblTime
		);

		System.out.println ("\n\n\t----------------------------------------------------------------");

		System.out.println ("\t     MONOTONE CONVEX HAGAN WEST WITH HARMONIC FORWARD STATE");

		System.out.println ("\t----------------------------------------------------------------");

		DisplayOP (
			MonotoneConvexHaganWest.Create (
				adblTime,
				adblForwardRate,
				true
			),
			mssHarmonic,
			adblTime
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		MonotoneHaganWestInterpolatorSample();
	}
}
