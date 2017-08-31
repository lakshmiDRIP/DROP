
package org.drip.sample.spline;

import org.drip.quant.common.FormatUtil;
import org.drip.spline.bspline.*;

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
 * BSplineSequence implements Samples for the Construction and the usage of various monic basis B Spline
 * 	Sequences. It demonstrates the following:
 * 	- Construction and Usage of segment Monic B Spline Sequence.
 * 	- Construction and Usage of segment Multic B Spline Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BSplineSequence {

	/*
	 * This sample shows the computation of the response value, the normalized cumulative, and the ordered
	 * 	derivative of the specified Segment Basis Function.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void ComputeResponseMetric (
		final SegmentBasisFunction me,
		final String strComment)
		throws Exception
	{
		int iOrder = 1;
		double dblXIncrement = 0.25;

		double dblX = me.leading() - dblXIncrement;

		double dblXEnd = me.trailing() + dblXIncrement;

		System.out.println ("\n\t---------------------------------------------------------------");

		System.out.println ("\t-------------------------" + strComment + "---------------------------");

		System.out.println ("\t---------------------------------------------------------------\n");

		while (dblX <= dblXEnd) {
			System.out.println (
				"\t\tResponse[" + FormatUtil.FormatDouble (dblX, 1, 3, 1.) + "] : " +
				FormatUtil.FormatDouble (me.evaluate (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (me.normalizedCumulative (dblX), 1, 5, 1.) + " | " +
				FormatUtil.FormatDouble (me.derivative (dblX, iOrder), 1, 5, 1.)
			);

			dblX += dblXIncrement;
		}
	}

	/*
	 * This sample demonstrates the construction and usage of the following monic/multic basis spline arrays:
	 * 	- Hyperbolic Rational Linear Monic.
	 * 	- Multic basis functions of 3rd degree (i.e., quadratic).
	 * 	- Multic basis functions of 4th degree (i.e., cubic).
	 * 	- Multic basis functions of 5th degree (i.e., quartic).
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void BSplineSequenceSample()
		throws Exception
	{
		double[] adblPredictorOrdinate = new double[] {
			1., 2., 3., 4., 5., 6.
		};

		/*
		 * Construct the Array of Hyperbolic Rational Linear Monic Segment Basis Functions. 
		 */

		SegmentBasisFunction[] aMonic = SegmentBasisFunctionGenerator.MonicSequence (
			BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC,
			BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR,
			adblPredictorOrdinate,
			0,
			1.
		);

		/*
		 * Display the response value, the normalized cumulative, and the ordered derivative of the Monic
		 * 	Segment Basis Function.
		 */

		for (int i = 0; i < aMonic.length; ++i)
			ComputeResponseMetric (
				aMonic[i],
				"   MONIC   "
			);

		/*
		 * Construct the array of multic basis functions of 3rd degree (i.e., quadratic).
		 */

		SegmentBasisFunction[] aQuadratic = SegmentBasisFunctionGenerator.MulticSequence (
			3,
			aMonic
		);

		/*
		 * Display the response value, the normalized cumulative, and the ordered derivative of the Quadratic
		 * 	Multic Segment Basis Function.
		 */

		for (int i = 0; i < aQuadratic.length; ++i)
			ComputeResponseMetric (
				aQuadratic[i],
				" QUADRATIC "
			);

		/*
		 * Construct the array of multic basis functions of 4th degree (i.e., cubic).
		 */

		SegmentBasisFunction[] aCubic = SegmentBasisFunctionGenerator.MulticSequence (
			4,
			aQuadratic
		);

		/*
		 * Display the response value, the normalized cumulative, and the ordered derivative of the Cubic
		 * 	Multic Segment Basis Function.
		 */

		for (int i = 0; i < aCubic.length; ++i)
			ComputeResponseMetric (
				aCubic[i],
				"   CUBIC   "
			);

		/*
		 * Construct the array of multic basis functions of 5th degree (i.e., quartic).
		 */

		SegmentBasisFunction[] aQuartic = SegmentBasisFunctionGenerator.MulticSequence (
			5,
			aCubic
		);

		/*
		 * Display the response value, the normalized cumulative, and the ordered derivative of the Quartic
		 * 	Multic Segment Basis Function.
		 */

		for (int i = 0; i < aQuartic.length; ++i)
			ComputeResponseMetric (
				aQuartic[i],
				"  QUARTIC  "
			);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		BSplineSequenceSample();
	}
}
