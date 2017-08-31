
package org.drip.spline.params;

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
 * SegmentPredictorResponseDerivative contains the segment local parameters used for the segment calibration. It
 * 	holds the edge Y value and the derivatives.
 * 
 * It exposes the following functions:
 * 	- Retrieve the Response Value as well as the DResponseDPredictorOrdinate Array.
 * 	- Aggregate the 2 Predictor Ordinate Response Derivatives by applying the Cardinal Tension Weight.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentPredictorResponseDerivative {
	private double _dblResponseValue = java.lang.Double.NaN;
	private double[] _adblDResponseDPredictorOrdinate = null;

	/**
	 * Aggregate the 2 Predictor Ordinate Response Derivatives by applying the Cardinal Tension Weight
	 * 
	 * @param sprdA Predictor Ordinate Response Derivative A
	 * @param sprdB Predictor Ordinate Response Derivative B
	 * @param dblCardinalTension Cardinal Tension
	 * 
	 * @return The Aggregated Predictor Ordinate Response Derivatives
	 */

	public static final SegmentPredictorResponseDerivative CardinalEdgeAggregate (
		final org.drip.spline.params.SegmentPredictorResponseDerivative sprdA,
		final org.drip.spline.params.SegmentPredictorResponseDerivative sprdB,
		final double dblCardinalTension)
	{
		if (null == sprdA || null == sprdB || !org.drip.quant.common.NumberUtil.IsValid (dblCardinalTension))
			return null;

		int iNumDeriv = 0;

		double[] adblEdgeDResponseDPredictorOrdinateA = sprdA.getDResponseDPredictorOrdinate();

		double[] adblEdgeDResponseDPredictorOrdinateB = sprdB.getDResponseDPredictorOrdinate();

		if ((null != adblEdgeDResponseDPredictorOrdinateA && null == adblEdgeDResponseDPredictorOrdinateB) ||
			(null == adblEdgeDResponseDPredictorOrdinateA && null != adblEdgeDResponseDPredictorOrdinateB) ||
				(null != adblEdgeDResponseDPredictorOrdinateA && null != adblEdgeDResponseDPredictorOrdinateB
					&& (iNumDeriv = adblEdgeDResponseDPredictorOrdinateA.length) !=
						adblEdgeDResponseDPredictorOrdinateB.length))
			return null;

		double dblAggregatedEdgeResponseValue = 0.5 * (1. - dblCardinalTension) * (sprdA.responseValue() +
			sprdB.responseValue());

		if (null == adblEdgeDResponseDPredictorOrdinateA || null == adblEdgeDResponseDPredictorOrdinateB || 0
			== iNumDeriv) {
			try {
				return new SegmentPredictorResponseDerivative (dblAggregatedEdgeResponseValue, null);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		double[] adblEdgeDResponseDPredictorOrdinate = new double[iNumDeriv];

		for (int i = 0; i < iNumDeriv; ++i)
			adblEdgeDResponseDPredictorOrdinate[i] = 0.5 * (1. - dblCardinalTension) *
				(adblEdgeDResponseDPredictorOrdinateA[i] + adblEdgeDResponseDPredictorOrdinateB[i]);

		try {
			return new SegmentPredictorResponseDerivative (dblAggregatedEdgeResponseValue,
				adblEdgeDResponseDPredictorOrdinate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SegmentPredictorResponseDerivative constructor
	 * 
	 * @param dblResponseValue Edge Response Value
	 * @param adblDResponseDPredictorOrdinate Array of ordered Edge Derivatives
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public SegmentPredictorResponseDerivative (
		final double dblResponseValue,
		final double[] adblDResponseDPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblResponseValue = dblResponseValue))
			throw new java.lang.Exception ("SegmentPredictorResponseDerivative ctr: Invalid Inputs!");

		_adblDResponseDPredictorOrdinate = adblDResponseDPredictorOrdinate;
	}

	/**
	 * Retrieve the Response Value
	 * 
	 * @return The Response Value
	 */

	public double responseValue()
	{
		return _dblResponseValue;
	}

	/**
	 * Retrieve the DResponseDPredictorOrdinate Array
	 * 
	 * @return DResponseDPredictorOrdinate Array
	 */

	public double[] getDResponseDPredictorOrdinate()
	{
		return _adblDResponseDPredictorOrdinate;
	}
}
