
package org.drip.spline.segment;

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
 * This class contains the spline segment in-elastic fields - in this case the start/end ranges. It exports
 * 	the following functions:
 * 	- Retrieve the Segment Left/Right Predictor Ordinate
 * 	- Find out if the Predictor Ordinate is inside the segment - inclusive of left/right.
 * 	- Get the Width of the Predictor Ordinate in this Segment
 * 	- Transform the Predictor Ordinate to the Local Segment Predictor Ordinate
 * 	- Transform the Local Predictor Ordinate to the Segment Ordinate
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateInelastic implements java.lang.Comparable<LatentStateInelastic> {
	private double _dblPredictorOrdinateLeft = java.lang.Double.NaN;
	private double _dblPredictorOrdinateRight = java.lang.Double.NaN;

	/**
	 * LatentStateInelastic constructor
	 * 
	 * @param dblPredictorOrdinateLeft Segment Predictor Ordinate Left
	 * @param dblPredictorOrdinateRight Segment Predictor Ordinate Right
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public LatentStateInelastic (
		final double dblPredictorOrdinateLeft,
		final double dblPredictorOrdinateRight)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblPredictorOrdinateLeft = dblPredictorOrdinateLeft)
			|| !org.drip.quant.common.NumberUtil.IsValid (_dblPredictorOrdinateRight =
				dblPredictorOrdinateRight) || _dblPredictorOrdinateLeft >= _dblPredictorOrdinateRight)
			throw new java.lang.Exception ("LatentStateInelastic ctr: Invalid inputs!");
	}

	/**
	 * Retrieve the Segment Left Predictor Ordinate
	 * 
	 * @return Segment Left Predictor Ordinate
	 */

	public double left()
	{
		return _dblPredictorOrdinateLeft;
	}

	/**
	 * Retrieve the Segment Right Predictor Ordinate
	 * 
	 * @return Segment Right Predictor Ordinate
	 */

	public double right()
	{
		return _dblPredictorOrdinateRight;
	}

	/**
	 * Find out if the Predictor Ordinate is inside the segment - inclusive of left/right.
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * 
	 * @return TRUE - Predictor Ordinate is inside the segment
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("LatentStateInelastic::in => Invalid Inputs");

		return _dblPredictorOrdinateLeft <= dblPredictorOrdinate && _dblPredictorOrdinateRight >=
			dblPredictorOrdinate;
	}

	/**
	 * Get the Width of the Predictor Ordinate in this Segment
	 * 
	 * @return Segment Width
	 */

	public double width()
	{
		return _dblPredictorOrdinateRight - _dblPredictorOrdinateLeft;
	}

	/**
	 * Transform the Predictor Ordinate to the Local Segment Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The Global Predictor Ordinate
	 * 
	 * @return Local Segment Predictor Ordinate
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public double localize (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!in (dblPredictorOrdinate))
			throw new java.lang.Exception ("LatentStateInelastic::localize: Invalid inputs!");

		return (dblPredictorOrdinate - _dblPredictorOrdinateLeft) / (_dblPredictorOrdinateRight -
			_dblPredictorOrdinateLeft);
	}

	/**
	 * Transform the Local Predictor Ordinate to the Segment Ordinate
	 * 
	 * @param dblLocalPredictorOrdinate The Local Segment Predictor Ordinate
	 * 
	 * @return The Segment Ordinate
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public double delocalize (
		final double dblLocalPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLocalPredictorOrdinate))
			throw new java.lang.Exception ("LatentStateInelastic::delocalize => Invalid Inputs");

		return _dblPredictorOrdinateLeft + dblLocalPredictorOrdinate * (_dblPredictorOrdinateRight -
			_dblPredictorOrdinateLeft);
	}

	@Override public int hashCode()
	{
		long lBits = java.lang.Double.doubleToLongBits ((int) _dblPredictorOrdinateLeft);

		return (int) (lBits ^ (lBits >>> 32));
	}

	@Override public int compareTo (
		final org.drip.spline.segment.LatentStateInelastic ieOther)
	{
		if (_dblPredictorOrdinateLeft > ieOther._dblPredictorOrdinateLeft) return 1;

		if (_dblPredictorOrdinateLeft < ieOther._dblPredictorOrdinateLeft) return -1;

		return 0;
	}
}
