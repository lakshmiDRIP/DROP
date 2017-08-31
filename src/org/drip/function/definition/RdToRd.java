
package org.drip.function.definition;

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
 * RdToRd provides the evaluation of the R^d To R^d objective function and its derivatives for a specified
 * 	set of R^d variates. Default implementation of the derivatives are for non-analytical black box objective
 * 	functions.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RdToRd {
	private static final int QUADRATURE_SAMPLING = 10000;

	protected org.drip.quant.calculus.DerivativeControl _dc = null;

	protected RdToRd (
		final org.drip.quant.calculus.DerivativeControl dc)
	{
		if (null == (_dc = dc)) _dc = new org.drip.quant.calculus.DerivativeControl();
	}

	/**
	 * Evaluate for the given Input R^d Variates
	 * 
	 * @param adblVariate Array of Input R^d Variates
	 *  
	 * @return The Output R^d Variates
	 */

	public abstract double[] evaluate (
		final double[] adblVariate);

	/**
	 * Calculate the Array of Differentials
	 * 
	 * @param adblVariate Variate Array at which the derivative is to be calculated
	 * @param iVariateIndex Index of the Variate whose Derivative is to be computed
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Array of Differentials
	 */

	public org.drip.quant.calculus.Differential[] differential (
		final double[] adblVariate,
		final int iVariateIndex,
		final int iOrder)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (adblVariate) || 0 >= iOrder) return null;

		int iOutputDimension = -1;
		double[] adblDerivative = null;
		int iNumVariate = adblVariate.length;
		double dblOrderedVariateInfinitesimal = 1.;
		double dblVariateInfinitesimal = java.lang.Double.NaN;

		if (iNumVariate <= iVariateIndex) return null;

		try {
			dblVariateInfinitesimal = _dc.getVariateInfinitesimal (adblVariate[iVariateIndex]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i <= iOrder; ++i) {
			if (0 != i) dblOrderedVariateInfinitesimal *= (2. * dblVariateInfinitesimal);

			double[] adblVariateIncremental = new double[iNumVariate];

			for (int j = 0; i < iNumVariate; ++j)
				adblVariateIncremental[j] = j == iVariateIndex ? adblVariate[j] + dblVariateInfinitesimal *
					(iOrder - 2. * i) : adblVariate[j];

			double[] adblValue = evaluate (adblVariateIncremental);

			if (null == adblValue || 0 == (iOutputDimension = adblValue.length)) return null;

			if (null == adblDerivative) {
				adblDerivative = new double[iOutputDimension];

				for (int j = 0; j < iOutputDimension; ++j)
					adblDerivative[j] = 0.;
			}

			try {
				for (int j = 0; j < iOutputDimension; ++j)
					adblDerivative[j] += (i % 2 == 0 ? 1 : -1) * org.drip.quant.common.NumberUtil.NCK
						(iOrder, i) * adblValue[j];
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.quant.calculus.Differential[] aDiff = new
			org.drip.quant.calculus.Differential[iOutputDimension];

		try {
			for (int j = 0; j < iOutputDimension; ++j)
				aDiff[j] = new org.drip.quant.calculus.Differential (dblOrderedVariateInfinitesimal,
					adblDerivative[j]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return aDiff;
	}

	/**
	 * Calculate the Derivative Array as a double
	 * 
	 * @param adblVariate Variate Array at which the derivative is to be calculated
	 * @param iVariateIndex Index of the Variate whose Derivative is to be computed
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Derivative Array
	 */

	public double[] derivative (
		final double[] adblVariate,
		final int iVariateIndex,
		final int iOrder)
	{
		org.drip.quant.calculus.Differential[] aDiff = differential (adblVariate, iVariateIndex, iOrder);

		if (null == aDiff) return null;

		int iOutputDimension = aDiff.length;
		double[] adblDerivative = new double[iOutputDimension];

		if (0 == iOutputDimension) return null;

		for (int i = 0; i < iOutputDimension; ++i)
			adblDerivative[i] = aDiff[i].calcSlope (true);

		return adblDerivative;
	}

	/**
	 * Integrate over the given Input Range Using Uniform Monte-Carlo
	 * 
	 * @param adblLeftEdge Array of Input Left Edge
	 * @param adblRightEdge Array of Input Right Edge
	 *  
	 * @return The Array Containing the Result of the Integration over the specified Range
	 */

	public double[] integrate (
		final double[] adblLeftEdge,
		final double[] adblRightEdge)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (adblLeftEdge) ||
			!org.drip.quant.common.NumberUtil.IsValid (adblRightEdge))
			return null;

		int iOutputDimension = -1;
		double[] adblIntegrand = null;
		int iNumVariate = adblLeftEdge.length;
		double[] adblVariate = new double[iNumVariate];
		double[] adblVariateWidth = new double[iNumVariate];

		if (adblRightEdge.length != iNumVariate) return null;

		for (int j = 0; j < iNumVariate; ++j)
			adblVariateWidth[j] = adblRightEdge[j] - adblLeftEdge[j];

		for (int i = 0; i < QUADRATURE_SAMPLING; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				adblVariate[j] = adblLeftEdge[j] + java.lang.Math.random() * adblVariateWidth[j];

			double[] adblValue = evaluate (adblVariate);

			if (null == adblValue || 0 == (iOutputDimension = adblValue.length)) return null;

			if (null == adblIntegrand) adblIntegrand = new double[iOutputDimension];

			for (int j = 0; j < iOutputDimension; ++j)
				adblIntegrand[j] += adblValue[j];
		}

		for (int i = 0; i < iOutputDimension; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				adblIntegrand[i] = adblIntegrand[i] * adblVariateWidth[j];

			adblIntegrand[i] /= QUADRATURE_SAMPLING;
		}

		return adblIntegrand;
	}
}
