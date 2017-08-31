
package org.drip.spaces.tensor;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * R1CombinatorialVector exposes the normed/non-normed Discrete Spaces with R^1 Combinatorial Vector
 *  Elements.
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1CombinatorialVector implements org.drip.spaces.tensor.R1GeneralizedVector {
	private java.util.List<java.lang.Double> _lsElementSpace = new java.util.ArrayList<java.lang.Double>();

	/**
	 * R1CombinatorialVector Constructor
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1CombinatorialVector (
		final java.util.List<java.lang.Double> lsElementSpace)
		throws java.lang.Exception
	{
		if (null == (_lsElementSpace = lsElementSpace) || 0 == _lsElementSpace.size())
			throw new java.lang.Exception ("R1CombinatorialVector ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Full Candidate List of Elements
	 * 
	 * @return The Full Candidate List of Elements
	 */

	public java.util.List<java.lang.Double> elementSpace()
	{
		return _lsElementSpace;
	}

	@Override public double leftEdge()
	{
		double dblLeftEdge = java.lang.Double.NaN;

		for (double dblElement : _lsElementSpace) {
			if (java.lang.Double.NEGATIVE_INFINITY == dblElement) return dblElement;

			if (!org.drip.quant.common.NumberUtil.IsValid (dblLeftEdge))
				dblLeftEdge = dblElement;
			else {
				if (dblLeftEdge > dblElement) dblLeftEdge = dblElement;
			}
		}

		return dblLeftEdge;
	}

	@Override public double rightEdge()
	{
		double dblRightEdge = java.lang.Double.NaN;

		for (double dblElement : _lsElementSpace) {
			if (java.lang.Double.POSITIVE_INFINITY == dblElement) return dblElement;

			if (!org.drip.quant.common.NumberUtil.IsValid (dblRightEdge))
				dblRightEdge = dblElement;
			else {
				if (dblRightEdge < dblElement) dblRightEdge = dblElement;
			}
		}

		return dblRightEdge;
	}

	@Override public boolean validateInstance (
		final double dblX)
	{
		return _lsElementSpace.contains (dblX);
	}

	@Override public org.drip.spaces.tensor.Cardinality cardinality()
	{
		return org.drip.spaces.tensor.Cardinality.CountablyFinite (_lsElementSpace.size());
	}

	@Override public boolean match (
		final org.drip.spaces.tensor.GeneralizedVector gvOther)
	{
		if (null == gvOther || !(gvOther instanceof R1CombinatorialVector)) return false;

		R1CombinatorialVector r1cvOther = (R1CombinatorialVector) gvOther;

		if (!cardinality().match (r1cvOther.cardinality())) return false;

		java.util.List<java.lang.Double> lsElementSpaceOther = r1cvOther.elementSpace();

		for (double dblElement : _lsElementSpace) {
			if (!lsElementSpaceOther.contains (dblElement)) return false;
		}

		return true;
	}

	@Override public boolean subset (
		final org.drip.spaces.tensor.GeneralizedVector gvOther)
	{
		if (null == gvOther || !(gvOther instanceof R1CombinatorialVector)) return false;

		R1CombinatorialVector r1cvOther = (R1CombinatorialVector) gvOther;

		if (cardinality().number() < r1cvOther.cardinality().number()) return false;

		java.util.List<java.lang.Double> lsElementSpaceOther = r1cvOther.elementSpace();

		for (double dblElement : _lsElementSpace) {
			if (!lsElementSpaceOther.contains (dblElement)) return false;
		}

		return true;
	}

	@Override public boolean isPredictorBounded()
	{
		return leftEdge() != java.lang.Double.NEGATIVE_INFINITY && rightEdge() !=
			java.lang.Double.POSITIVE_INFINITY;
	}

	@Override public double hyperVolume()
		throws java.lang.Exception
	{
		if (!isPredictorBounded())
			throw new java.lang.Exception ("R1CombinatorialVector::hyperVolume => Space not Bounded");

		return rightEdge() - leftEdge();
	}
}
