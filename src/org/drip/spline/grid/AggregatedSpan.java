
package org.drip.spline.grid;

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
 * AggregatedSpan implements the Span interface. Here response from an array of spans whose responses are
 * 	aggregated by their weights.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AggregatedSpan implements org.drip.spline.grid.Span {
	private java.util.List<java.lang.Double> _lsWeight = null;
	private java.util.List<org.drip.spline.grid.Span> _lsSpan = null;

	/**
	 * AggregatedSpan Constructor
	 * 
	 * @param lsSpan List of Spans
	 * @param lsWeight List of Weights
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AggregatedSpan (
		final java.util.List<org.drip.spline.grid.Span> lsSpan,
		final java.util.List<java.lang.Double> lsWeight)
		throws java.lang.Exception
	{
		if (null == (_lsSpan = lsSpan) || null == (_lsWeight = lsWeight))
			throw new java.lang.Exception ("AggregatedSpan ctr: Invalid Inputs");

		int iNumSpan = _lsSpan.size();

		if (0 == iNumSpan || iNumSpan != _lsWeight.size())
			throw new java.lang.Exception ("AggregatedSpan ctr: Invalid Inputs");

		for (org.drip.spline.grid.Span span : _lsSpan) {
			if (null == span) throw new java.lang.Exception ("AggregatedSpan ctr: Invalid Inputs");
		}
	}

	@Override public boolean addStretch (
		final org.drip.spline.stretch.MultiSegmentSequence mss)
	{
		return false;
	}

	@Override public org.drip.spline.stretch.MultiSegmentSequence getContainingStretch (
		final double dblPredictorOrdinate)
	{
		for (org.drip.spline.grid.Span span : _lsSpan) {
			org.drip.spline.stretch.MultiSegmentSequence mss = span.getContainingStretch
				(dblPredictorOrdinate);

			if (null != mss) return mss;
		}

		return null;
	}

	@Override public org.drip.spline.stretch.MultiSegmentSequence getStretch (
		final java.lang.String strName)
	{
		if (null == strName || strName.isEmpty()) return null;

		for (org.drip.spline.grid.Span span : _lsSpan) {
			org.drip.spline.stretch.MultiSegmentSequence mss = span.getStretch (strName);

			if (null != mss) return mss;
		}

		return null;
	}

	@Override public double left()
		throws java.lang.Exception
	{
		return _lsSpan.get (0).left();
	}

	@Override public double right()
		throws java.lang.Exception
	{
		return _lsSpan.get (_lsSpan.size() - 1).right();
	}

	@Override public double calcResponseValue (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		int i = 0;
		double dblResponseValue = 0.;

		for (org.drip.spline.grid.Span span : _lsSpan)
			dblResponseValue += span.calcResponseValue (dblPredictorOrdinate) * _lsWeight.get (i++);

		return dblResponseValue;
	}

	@Override public double calcResponseValueDerivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		int i = 0;
		double dblResponseValueDerivative = 0.;

		for (org.drip.spline.grid.Span span : _lsSpan)
			dblResponseValueDerivative += span.calcResponseValueDerivative (dblPredictorOrdinate,iOrder) *
				_lsWeight.get (i++);

		return dblResponseValueDerivative;
	}

	@Override public boolean isMergeState (
		final double dblPredictorOrdinate,
		final org.drip.state.identifier.LatentStateLabel lsl)
	{
		for (org.drip.spline.grid.Span span : _lsSpan) {
			if (span.isMergeState (dblPredictorOrdinate, lsl)) return true;
		}

		return false;
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDResponseDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		int i = 0;
		org.drip.quant.calculus.WengertJacobian wjAggregate = null;

		for (org.drip.spline.grid.Span span : _lsSpan) {
			org.drip.quant.calculus.WengertJacobian wj = span.jackDResponseDManifestMeasure
				(strManifestMeasure, dblPredictorOrdinate, iOrder);

			if (null == wj) return null;

			if (null == wjAggregate) {
				if (!(wjAggregate = wj).scale (_lsWeight.get (i++))) return null;
			} else {
				if (!wjAggregate.cumulativeMerge (wj, _lsWeight.get (i++))) return null;
			}
		}

		return wjAggregate;
	}

	@Override public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		for (org.drip.spline.grid.Span span : _lsSpan) {
			if (span.in (dblPredictorOrdinate)) return true;
		}

		return false;
	}

	@Override public java.lang.String displayString()
	{
		int i = 0;

		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (org.drip.spline.grid.Span span : _lsSpan)
			sb.append (span.displayString() + " | " + _lsWeight.get (i++));

		return sb.toString();
	}
}
