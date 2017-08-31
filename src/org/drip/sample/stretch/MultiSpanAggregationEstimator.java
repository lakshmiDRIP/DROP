
package org.drip.sample.stretch;

import java.util.*;

import org.drip.quant.common.FormatUtil;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.grid.*;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * MultiSpanAggregationEstimator demonstrates the Construction and Usage of the Multiple Span Aggregation
 *  Functionality.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultiSpanAggregationEstimator {

	/*
	 * Build Polynomial Segment Control Parameters.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final SegmentCustomBuilderControl PolynomialSegmentControlParams (
		final int iNumBasis,
		final SegmentInelasticDesignControl sdic,
		final ResponseScalingShapeControl rssc)
		throws Exception
	{
		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (iNumBasis),
			sdic,
			rssc,
			null
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		double[] adblX = new double[] { 1.00,  1.50,  2.00, 3.00, 4.00, 5.00, 6.50, 8.00, 10.00};
		double[] adblY1 = new double[] {25.00, 20.25, 16.00, 9.00, 4.00, 1.00, 0.25, 4.00, 16.00};
		double[] adblY2 = new double[] {27.00, 22.25, 18.00, 11.00, 6.00, 3.00, 2.25, 6.00, 18.00};

		SegmentCustomBuilderControl scbc = PolynomialSegmentControlParams (
			4,
			SegmentInelasticDesignControl.Create (2, 2),
			null
		);

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		MultiSegmentSequence mss1 = MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			"SPLINE_STRETCH_1", // Name
			adblX, // predictors
			adblY1, // responses
			aSCBC, // Basis Segment Builder parameters
			null,  // NULL segment Best Fit Response
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);

		Span span1 = new OverlappingStretchSpan (mss1);

		MultiSegmentSequence mss2 = MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			"SPLINE_STRETCH_2", // Name
			adblX, // predictors
			adblY2, // responses
			aSCBC, // Basis Segment Builder parameters
			null,  // NULL segment Best Fit Response
			BoundarySettings.NaturalStandard(), // Boundary Condition - Natural
			MultiSegmentSequence.CALIBRATE // Calibrate the Stretch predictors to the responses
		);

		Span span2 = new OverlappingStretchSpan (mss2);

		List<Double> lsWeight = new ArrayList<Double>();

		lsWeight.add (0.14);

		lsWeight.add (0.71);

		List<Span> lsSpan = new ArrayList<Span>();

		lsSpan.add (span1);

		lsSpan.add (span2);

		AggregatedSpan ass = new AggregatedSpan (
			lsSpan,
			lsWeight
		);

		double dblX = 1.;
		double dblXMax = 10.;

		while (dblX <= dblXMax) {
			double dblStretchResponse = 0.14 * mss1.responseValue (dblX) + 0.71 * mss2.responseValue (dblX);

			System.out.println ("Y[" + dblX + "] " +
				FormatUtil.FormatDouble (ass.calcResponseValue (dblX), 2, 2, 1.) + " | " +
				FormatUtil.FormatDouble (dblStretchResponse, 2, 2, 1.)
			);

			dblX += 1.;
		}
	}
}
