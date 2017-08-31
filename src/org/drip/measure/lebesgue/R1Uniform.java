
package org.drip.measure.lebesgue;

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
 * R1Uniform implements the R^1 Lebesgue (i.e., Bounded Uniform) Distribution, with a Uniform Distribution
 *  between a Lower and an Upper Bound.
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Uniform extends org.drip.measure.continuous.R1 {
	protected static final int GRID_WIDTH = 100;

	private double _dblLeftPredictorOrdinateEdge = java.lang.Double.NaN;
	private double _dblRightPredictorOrdinateEdge = java.lang.Double.NaN;

	/**
	 * Construct a R^1 Bounded Uniform Distribution
	 * 
	 * @param dblLeftPredictorOrdinateEdge The Left Predictor Ordinate Edge
	 * @param dblRightPredictorOrdinateEdge The Right Predictor Ordinate Edge
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public R1Uniform (
		final double dblLeftPredictorOrdinateEdge,
		final double dblRightPredictorOrdinateEdge)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLeftPredictorOrdinateEdge =
			dblLeftPredictorOrdinateEdge) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblRightPredictorOrdinateEdge = dblRightPredictorOrdinateEdge) ||
					_dblRightPredictorOrdinateEdge <= _dblLeftPredictorOrdinateEdge)
			throw new java.lang.Exception ("R1Uniform Constructor: Invalid Inputs");
	}

	/**
	 * Retrieve the Left Predictor Ordinate Edge
	 * 
	 * @return The Left Predictor Ordinate Edge
	 */

	public double leftEdge()
	{
		return _dblLeftPredictorOrdinateEdge;
	}

	/**
	 * Retrieve the Right Predictor Ordinate Edge
	 * 
	 * @return The Right Predictor Ordinat Edge
	 */

	public double rightEdge()
	{
		return _dblRightPredictorOrdinateEdge;
	}

	@Override public double cumulative (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("R1Uniform::cumulative => Invalid Inputs");

		if (dblX <= _dblLeftPredictorOrdinateEdge) return 0.;

		if (dblX >= _dblRightPredictorOrdinateEdge) return 1.;

		return (dblX - _dblLeftPredictorOrdinateEdge) / (_dblRightPredictorOrdinateEdge -
			_dblLeftPredictorOrdinateEdge);
	}

	@Override public double incremental (
		final double dblXLeft,
		final double dblXRight)
		throws java.lang.Exception
	{
		return cumulative (dblXRight) - cumulative (dblXLeft);
	}

	@Override public double invCumulative (
		final double dblY)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY) || dblY < 0. || dblY > 1.)
			throw new java.lang.Exception ("R1Uniform::invCumulative => Invalid inputs");

	    return dblY * (_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge) +
	    	_dblLeftPredictorOrdinateEdge;
	}

	@Override public double density (
		final double dblX)
		throws java.lang.Exception
	{
		return dblX <= _dblLeftPredictorOrdinateEdge || dblX >= _dblRightPredictorOrdinateEdge ? 0. : 1. /
			(_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge);
	}

	@Override public double mean()
	{
	    return 0.5 * (_dblRightPredictorOrdinateEdge + _dblLeftPredictorOrdinateEdge);
	}

	@Override public double variance()
	{
	    return (_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge) *
	    	(_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge) / 12.;
	}

	@Override public org.drip.quant.common.Array2D histogram()
	{
		double[] adblX = new double[GRID_WIDTH];
		double[] adblY = new double[GRID_WIDTH];
		double dblWidth = (_dblRightPredictorOrdinateEdge - _dblLeftPredictorOrdinateEdge) / GRID_WIDTH;

		for (int i = 0; i < GRID_WIDTH; ++i) {
			adblY[i] = 1. / GRID_WIDTH;
			adblX[i] = _dblLeftPredictorOrdinateEdge + (i + 1) * dblWidth;
		}

		return org.drip.quant.common.Array2D.FromArray (adblX, adblY);
	}
}
