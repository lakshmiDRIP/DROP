
package org.drip.sample.funding;

import org.drip.function.r1tor1custom.LinearRationalShapeControl;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.pchip.*;
import org.drip.spline.stretch.*;

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
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>HaganWestForwardInterpolator</i> illustrates using the Hagan and West (2006) Estimator. It provides the
 * following functionality:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Set up the Predictor ordinates and the response values.
 *  	</li>
 *  	<li>
 * 			Construct the rational linear shape control with the specified tension.
 *  	</li>
 *  	<li>
 * 			Create the Segment Inelastic design using the Ck and Curvature Penalty Derivatives.
 *  	</li>
 *  	<li>
 * 			Build the Array of Segment Custom Builder Control Parameters of the KLK Hyperbolic Tension Basis
 * 				Type, the tension, the segment inelastic design control, and the shape controller.
 *  	</li>
 *  	<li>
 * 			Setup the monotone convex stretch using the above settings, and with no linear inference, no
 * 				spurious extrema, or no monotone filtering applied.
 *  	</li>
 *  	<li>
 * 			Setup the monotone convex stretch using the above settings, and with linear inference, no
 * 				spurious extrema, or no monotone filtering applied.
 *  	</li>
 *  	<li>
 * 			Compute and display the monotone convex output with the linear forward state.
 *  	</li>
 *  	<li>
 * 			Compute and display the monotone convex output with the harmonic forward state.
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/README.md">Shape Preserving Local Funding Curve</a></li>
 *  </ul>
 * <br><br>
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
		EnvManager.InitEnv ("");

		MonotoneHaganWestInterpolatorSample();

		EnvManager.TerminateEnv();
	}
}
