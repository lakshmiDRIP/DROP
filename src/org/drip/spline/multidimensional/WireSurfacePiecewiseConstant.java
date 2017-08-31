
package org.drip.spline.multidimensional;

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
 * WireSurfacePiecewiseConstant implements the piecewise Constant version of the 2D Spline Response Surface.
 *  It synthesizes this from an array of 1D Span Instances, each of which is referred to as wire spline in
 *  this case.
 *
 * @author Lakshmi Krishnamurthy
 */

public class WireSurfacePiecewiseConstant {
	private double[] _adblX = null;
	private double[] _adblY = null;
	private double[][] _aadblResponse = null;

	/**
	 * WireSurfacePiecewiseConstant Constructor
	 * 
	 * @param adblX Array of the X Ordinates
	 * @param adblY Array of the Y Ordinates
	 * @param aadblResponse Double Array of the Responses corresponding to {X, Y}
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public WireSurfacePiecewiseConstant (
		final double[] adblX,
		final double[] adblY,
		final double[][] aadblResponse)
		throws java.lang.Exception
	{
		if (null == (_adblX = adblX) || null == (_adblY = adblY))
			throw new java.lang.Exception ("WireSurfacePiecewiseConstant ctr: Invalid Inputs");

		int iXLength = _adblX.length;
		int iYLength = _adblY.length;

		if (0 == iXLength || 0 == iYLength || null == (_aadblResponse = aadblResponse) || iXLength !=
			_aadblResponse.length || iYLength != _aadblResponse[0].length)
			throw new java.lang.Exception ("WireSurfacePiecewiseConstant ctr: Invalid Inputs");
	}

	/**
	 * Enclosing X Index
	 * 
	 * @param dblX The X Ordinate
	 * 
	 * @return The Corresponding Index
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int enclosingXIndex (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception
				("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");

		if (dblX < _adblX[0]) return java.lang.Integer.MIN_VALUE;

		int iTerminalXIndex = _adblX.length - 1;

		if (dblX > _adblX[iTerminalXIndex]) return java.lang.Integer.MAX_VALUE;

		for (int i = 1; i <= iTerminalXIndex; ++i) {
			if (dblX >= _adblX[i - 1] && dblX >= _adblX[i]) return i;
		}

		throw new java.lang.Exception ("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");
	}

	/**
	 * Enclosing Y Index
	 * 
	 * @param dblY The Y Ordinate
	 * 
	 * @return The Corresponding Index
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public int enclosingYIndex (
		final double dblY)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblY))
			throw new java.lang.Exception
				("WireSurfacePiecewiseConstant::enclosingYIndex => Invalid Inputs");

		if (dblY < _adblY[0]) return java.lang.Integer.MIN_VALUE;

		int iTerminalYIndex = _adblY.length - 1;

		if (dblY > _adblY[iTerminalYIndex]) return java.lang.Integer.MAX_VALUE;

		for (int i = 1; i <= iTerminalYIndex; ++i) {
			if (dblY >= _adblY[i - 1] && dblY >= _adblY[i]) return i;
		}

		throw new java.lang.Exception ("WireSurfacePiecewiseConstant::enclosingXIndex => Invalid Inputs");
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
		int iTerminalXIndex = _adblX.length - 1;
		int iTerminalYIndex = _adblY.length - 1;

		int iEnclosingXIndex = enclosingXIndex (dblX);

		int iEnclosingYIndex = enclosingYIndex (dblY);

		if (java.lang.Integer.MIN_VALUE == iEnclosingXIndex)
			iEnclosingXIndex = 0;
		else if (java.lang.Integer.MAX_VALUE == iEnclosingXIndex)
			iEnclosingXIndex = iTerminalXIndex;
		else {
			for (int i = 1; i <= iTerminalXIndex; ++i) {
				if (dblX >= _adblX[i - 1] && dblX >= _adblX[i]) {
					iEnclosingXIndex = i;
					break;
				}
			}
		}

		if (java.lang.Integer.MIN_VALUE == iEnclosingYIndex)
			iEnclosingYIndex = 0;
		else if (java.lang.Integer.MAX_VALUE == iEnclosingYIndex)
			iEnclosingYIndex = iTerminalYIndex;
		else {
			for (int i = 1; i <= iTerminalYIndex; ++i) {
				if (dblY >= _adblY[i - 1] && dblY >= _adblY[i]) {
					iEnclosingYIndex = i;
					break;
				}
			}
		}

		return _aadblResponse[iEnclosingXIndex][iEnclosingYIndex];
	}
}
