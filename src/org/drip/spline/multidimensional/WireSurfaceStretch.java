
package org.drip.spline.multidimensional;

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
 * WireSurfaceStretch implements a 2D spline surface stretch. It synthesizes this from an array of 1D Span
 * 	instances, each of which is referred to as wire spline in this case.
 *
 * @author Lakshmi Krishnamurthy
 */

public class WireSurfaceStretch {
	private org.drip.spline.params.SegmentCustomBuilderControl _scbc = null;
	private java.util.TreeMap<java.lang.Double, org.drip.spline.grid.Span> _mapWireSpan = null;

	/**
	 * WireSurfaceStretch Constructor
	 * 
	 * @param strName Name
	 * @param scbc Segment Custom Builder Control Parameters
	 * @param mapWireSpan X-mapped Array of Y Basis Spline Wire Spans
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public WireSurfaceStretch (
		final java.lang.String strName,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc,
		final java.util.TreeMap<java.lang.Double, org.drip.spline.grid.Span> mapWireSpan)
		throws java.lang.Exception
	{
		if (null == (_mapWireSpan = mapWireSpan) || 0 == _mapWireSpan.size() || null == (_scbc = scbc))
			throw new java.lang.Exception ("WireSurfaceStretch ctr: Invalid Inputs");
	}

	/**
	 * Compute the Bivariate Surface Response Value
	 * 
	 * @param dblX X
	 * @param dblY Y
	 * 
	 * @return The Bivariate Surface Response Value
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public double responseValue (
		final double dblX,
		final double dblY)
		throws java.lang.Exception
	{
		int iSize = _mapWireSpan.size();

		int i = 0;
		double[] adblX = new double[iSize];
		double[] adblZ = new double[iSize];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iSize - 1];

		for (java.util.Map.Entry<java.lang.Double, org.drip.spline.grid.Span> me : _mapWireSpan.entrySet()) {
			if (null == me)
				throw new java.lang.Exception ("WireSurfaceStretch::responseValue => Invalid Wire Span Map");

			if (0 != i) aSCBC[i - 1] = _scbc;

			adblX[i] = me.getKey();

			org.drip.spline.grid.Span wireSpan = me.getValue();

			if (null == wireSpan)
				throw new java.lang.Exception ("WireSurfaceStretch::responseValue => Invalid Wire Span Map");

			double dblLeftY = wireSpan.left();

			double dblRightY = wireSpan.right();

			if (dblY <= dblLeftY)
				adblZ[i++] = wireSpan.calcResponseValue (dblLeftY);
			else if (dblY >= dblRightY)
				adblZ[i++] = wireSpan.calcResponseValue (dblRightY);
			else
				adblZ[i++] = wireSpan.calcResponseValue (dblY);
		}

		org.drip.spline.stretch.MultiSegmentSequence mss =
			org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
				("org.drip.spline.multidimensional.WireSurfaceStretch@" +
					org.drip.quant.common.StringUtil.GUID(), adblX, adblZ, aSCBC, null,
						org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
							org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE);

		if (null == mss)
			throw new java.lang.Exception ("WireSurfaceStretch::responseValue => Cannot extract MSS");

		double dblLeftX = mss.getLeftPredictorOrdinateEdge();

		if (dblX <= dblLeftX) return mss.responseValue (dblLeftX);

		double dblRightX = mss.getRightPredictorOrdinateEdge();

		if (dblX >= dblRightX) return mss.responseValue (dblRightX);

		return mss.responseValue (dblX);
	}

	/**
	 * Retrieve the Surface Span Stretch that corresponds to the given Y Anchor
	 * 
	 * @param dblYAnchor Y Anchor
	 * 
	 * @return The Surface Span Stretch Instance
	 */

	public org.drip.spline.grid.Span wireSpanYAnchor (
		final double dblYAnchor)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblYAnchor)) return null;

		int iSize = _mapWireSpan.size();

		int i = 0;
		double[] adblX = new double[iSize];
		double[] adblZ = new double[iSize];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iSize - 1];

		for (java.util.Map.Entry<java.lang.Double, org.drip.spline.grid.Span> me : _mapWireSpan.entrySet()) {
			if (null == me) return null;

			if (0 != i) aSCBC[i - 1] = _scbc;

			adblX[i] = me.getKey();

			org.drip.spline.grid.Span wireSpan = me.getValue();

			if (null == wireSpan) return null;

			try {
				double dblLeftY = wireSpan.left();

				double dblRightY = wireSpan.right();

				if (dblYAnchor <= dblLeftY)
					adblZ[i++] = wireSpan.calcResponseValue (dblLeftY);
				else if (dblYAnchor >= dblRightY)
					adblZ[i++] = wireSpan.calcResponseValue (dblRightY);
				else
					adblZ[i++] = wireSpan.calcResponseValue (dblYAnchor);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new org.drip.spline.grid.OverlappingStretchSpan
				(org.drip.spline.stretch.MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator
					("org.drip.spline.multidimensional.WireSurfaceStretch@" +
						org.drip.quant.common.StringUtil.GUID(), adblX, adblZ, aSCBC, null,
							org.drip.spline.stretch.BoundarySettings.NaturalStandard(),
								org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Surface Span Stretch that corresponds to the given X Anchor
	 * 
	 * @param dblXAnchor X Anchor
	 * 
	 * @return The Surface Span Stretch Instance
	 */

	public org.drip.spline.grid.Span wireSpanXAnchor (
		final double dblXAnchor)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblXAnchor)) return null;

		org.drip.spline.grid.Span spanPrev = null;
		org.drip.spline.grid.Span spanCurrent = null;
		double dblXAnchorPrev = java.lang.Double.NaN;
		double dblXAnchorCurrent = java.lang.Double.NaN;

		for (java.util.Map.Entry<java.lang.Double, org.drip.spline.grid.Span> me : _mapWireSpan.entrySet()) {
			if (null == me) return null;

			dblXAnchorCurrent = me.getKey();

			spanCurrent = me.getValue();

			if (!org.drip.quant.common.NumberUtil.IsValid (dblXAnchorPrev)) {
				if (dblXAnchor <= (dblXAnchorPrev = dblXAnchorCurrent)) return spanCurrent;

				spanPrev = spanCurrent;
				continue;
			}

			if (dblXAnchor > dblXAnchorPrev && dblXAnchor <= dblXAnchorCurrent) {
				double dblLeftWeight = (dblXAnchorCurrent - dblXAnchor) / (dblXAnchorCurrent -
					dblXAnchorPrev);

				java.util.List<java.lang.Double> lsWeight = new java.util.ArrayList<java.lang.Double>();

				java.util.List<org.drip.spline.grid.Span> lsSpan = new
					java.util.ArrayList<org.drip.spline.grid.Span>();

				lsSpan.add (spanPrev);

				lsSpan.add (spanCurrent);

				lsWeight.add (dblLeftWeight);

				lsWeight.add (1. - dblLeftWeight);

				try {
					return new org.drip.spline.grid.AggregatedSpan (lsSpan, lsWeight);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			spanPrev = spanCurrent;
			dblXAnchorPrev = dblXAnchorCurrent;
		}

		return spanCurrent;
	}
}
