
package org.drip.sequence.metrics;

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
 * PivotedDepartureBounds holds the Lower/Upper Probability Bounds in regards to the Specified Pivot-Centered
 *  Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PivotedDepartureBounds {

	/**
	 * PIVOT ANCHOR TYPE - ZERO
	 */

	public static final int PIVOT_ANCHOR_TYPE_ZERO = 1;

	/**
	 * PIVOT ANCHOR TYPE - MEAN
	 */

	public static final int PIVOT_ANCHOR_TYPE_MEAN = 2;

	/**
	 * PIVOT ANCHOR TYPE - CUSTOM
	 */

	public static final int PIVOT_ANCHOR_TYPE_CUSTOM = 4;

	private int _iPivotAnchorType = -1;
	private double _dblLower = java.lang.Double.NaN;
	private double _dblUpper = java.lang.Double.NaN;
	private double _dblCustomPivotAnchor = java.lang.Double.NaN;

	/**
	 * PivotedDepartureBounds Constructor
	 * 
	 * @param iPivotAnchorType The Type of the Pivot Anchor
	 * @param dblCustomPivotAnchor The Custom Pivot Anchor
	 * @param dblLower Lower Bound
	 * @param dblUpper Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PivotedDepartureBounds (
		final int iPivotAnchorType,
		final double dblCustomPivotAnchor,
		final double dblLower,
		final double dblUpper)
		throws java.lang.Exception
	{
		_dblLower = dblLower;
		_dblUpper = dblUpper;
		_iPivotAnchorType = iPivotAnchorType;
		_dblCustomPivotAnchor = dblCustomPivotAnchor;

		if ((!org.drip.quant.common.NumberUtil.IsValid (_dblLower) &&
			!org.drip.quant.common.NumberUtil.IsValid (_dblUpper)) || (PIVOT_ANCHOR_TYPE_CUSTOM ==
				_iPivotAnchorType && !org.drip.quant.common.NumberUtil.IsValid (_dblCustomPivotAnchor)))
			throw new java.lang.Exception ("PivotedDepartureBounds ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Lower Probability Bound
	 * 
	 * @return The Lower Probability Bound
	 */

	public double lower()
	{
		return _dblLower;
	}

	/**
	 * Retrieve the Upper Probability Bound
	 * 
	 * @return The Upper Probability Bound
	 */

	public double upper()
	{
		return _dblUpper;
	}

	/**
	 * Retrieve the Pivot Anchor Type
	 * 
	 * @return The Pivot Anchor Type
	 */

	public int pivotAnchorType()
	{
		return _iPivotAnchorType;
	}

	/**
	 * Retrieve the Custom Pivot Anchor
	 * 
	 * @return The Custom Pivot Anchor
	 */

	public double customPivotAnchor()
	{
		return _dblCustomPivotAnchor;
	}
}
