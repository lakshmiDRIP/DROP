
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
 * RdToR1 provides the evaluation of the R^d To R^1 objective function and its derivatives for a specified
 * 	set of R^d variates. Default implementation of the derivatives are for non-analytical black box objective
 * 	functions.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RdToR1 {
	private static final int EXTREMA_SAMPLING = 10000;
	private static final int QUADRATURE_SAMPLING = 10000;

	protected static final int DIMENSION_NOT_FIXED = -1;

	protected org.drip.quant.calculus.DerivativeControl _dc = null;

	/**
	 * Validate the Input Double Array
	 * 
	 * @param adblVariate The Input Double Array
	 * 
	 * @return The Input Double Array consists of valid Values
	 */

	public static final boolean ValidateInput (
		final double[] adblVariate)
	{
		if (null == adblVariate) return false;

		int iNumVariate = adblVariate.length;

		if (0 == iNumVariate) return false;

		for (int i = 0; i < iNumVariate; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblVariate[i])) return false;
		}

		return true;
	}

	protected RdToR1 (
		final org.drip.quant.calculus.DerivativeControl dc)
	{
		if (null == (_dc = dc)) _dc = new org.drip.quant.calculus.DerivativeControl();
	}

	/**
	 * Retrieve the Dimension of the Input Variate
	 * 
	 * @return The Dimension of the Input Variate
	 */

	public abstract int dimension();

	/**
	 * Evaluate for the given Input Variates
	 * 
	 * @param adblVariate Array of Input Variates
	 *  
	 * @return The Calculated Value
	 * 
	 * @throws java.lang.Exception Thrown if the Evaluation cannot be done
	 */

	public abstract double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception;

	/**
	 * Calculate the Differential
	 * 
	 * @param adblVariate Variate Array at which the derivative is to be calculated
	 * @param iVariateIndex Index of the Variate whose Derivative is to be computed
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Derivative
	 */

	public org.drip.quant.calculus.Differential differential (
		final double[] adblVariate,
		final int iVariateIndex,
		final int iOrder)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (adblVariate) || 0 >= iOrder) return null;

		double dblDerivative = 0.;
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

			for (int j = 0; j < iNumVariate; ++j)
				adblVariateIncremental[j] = j == iVariateIndex ? adblVariate[j] + dblVariateInfinitesimal *
					(iOrder - 2. * i) : adblVariate[j];

			try {
				dblDerivative += (i % 2 == 0 ? 1 : -1) * org.drip.quant.common.NumberUtil.NCK (iOrder, i) *
					evaluate (adblVariateIncremental);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new org.drip.quant.calculus.Differential (dblOrderedVariateInfinitesimal, dblDerivative);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calculate the derivative as a double
	 * 
	 * @param adblVariate Variate Array at which the derivative is to be calculated
	 * @param iVariateIndex Index of the Variate whose Derivative is to be computed
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Derivative
	 */

	public double derivative (
		final double[] adblVariate,
		final int iVariateIndex,
		final int iOrder)
	{
		return differential (adblVariate, iVariateIndex, iOrder).calcSlope (true);
	}

	/**
	 * Evaluate the Jacobian for the given Input Variates
	 * 
	 * @param adblVariate Array of Input Variates
	 *  
	 * @return The Jacobian Array
	 */

	public double[] jacobian (
		final double[] adblVariate)
	{
		if (null == adblVariate) return null;

		int iNumVariate = adblVariate.length;
		double[] adblJacobian = new double[iNumVariate];

		if (0 == iNumVariate) return null;

		for (int i = 0; i < iNumVariate; ++i) {
			try {
				adblJacobian[i] = derivative (adblVariate, i, 1);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblJacobian;
	}

	/**
	 * Construct an Instance of the Unit Gradient Vector at the given Input Variates
	 * 
	 * @param adblVariate Array of Input Variates
	 *  
	 * @return Instance of the Unit Gradient Vector Array
	 */

	public org.drip.function.definition.UnitVector gradient (
		final double[] adblVariate)
	{
		return org.drip.function.definition.UnitVector.Standard (jacobian (adblVariate));
	}

	/**
	 * Evaluate The Hessian for the given Input Variates
	 * 
	 * @param adblVariate Array of Input Variates
	 *  
	 * @return The Hessian Matrix
	 */

	public double[][] hessian (
		final double[] adblVariate)
	{
		if (null == adblVariate) return null;

		final int iNumVariate = adblVariate.length;
		double[][] adblHessian = new double[iNumVariate][iNumVariate];

		if (0 == iNumVariate) return null;

		for (int i = 0; i < iNumVariate; ++i) {
			final int iVariateIndex = i;

			org.drip.function.definition.RdToR1 gradientRdToR1 = new org.drip.function.definition.RdToR1
				(null) {
				@Override public int dimension()
				{
					return iNumVariate;
				}

				@Override public double evaluate (
					final double[] adblVariate)
					throws java.lang.Exception
				{
					return derivative (adblVariate, iVariateIndex, 1);
				}
			};

			for (int j = 0; j < iNumVariate; ++j) {
				try {
					adblHessian[i][j] = gradientRdToR1.derivative (adblVariate, j, 1);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return adblHessian;
	}

	/**
	 * Integrate over the given Input Range Using Uniform Monte-Carlo
	 * 
	 * @param adblLeftEdge Array of Input Left Edge
	 * @param adblRightEdge Array of Input Right Edge
	 *  
	 * @return The Result of the Integration over the specified Range
	 * 
	 * @throws java.lang.Exception Thrown if the Integration cannot be done
	 */

	public double integrate (
		final double[] adblLeftEdge,
		final double[] adblRightEdge)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (adblLeftEdge) ||
			!org.drip.quant.common.NumberUtil.IsValid (adblRightEdge))
			throw new java.lang.Exception ("RdToR1::integrate => Invalid Inputs");

		double dblIntegrand = 0.;
		int iNumVariate = adblLeftEdge.length;
		double[] adblVariate = new double[iNumVariate];
		double[] adblVariateWidth = new double[iNumVariate];

		if (adblRightEdge.length != iNumVariate)
			throw new java.lang.Exception ("RdToR1::integrate => Invalid Inputs");

		for (int j = 0; j < iNumVariate; ++j)
			adblVariateWidth[j] = adblRightEdge[j] - adblLeftEdge[j];

		for (int i = 0; i < QUADRATURE_SAMPLING; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				adblVariate[j] = adblLeftEdge[j] + java.lang.Math.random() * adblVariateWidth[j];

			dblIntegrand += evaluate (adblVariate);
		}

		for (int j = 0; j < iNumVariate; ++j)
			dblIntegrand = dblIntegrand * adblVariateWidth[j];

		return dblIntegrand / QUADRATURE_SAMPLING;
	}

	/**
	 * Compute the Maximum VOP within the Variate Array Range Using Uniform Monte-Carlo
	 * 
	 * @param adblVariateLeft The Range Left End Array
	 * @param adblVariateRight The Range Right End Array
	 * 
	 * @return The Maximum VOP
	 */

	public org.drip.function.definition.VariateOutputPair maxima (
		final double[] adblVariateLeft,
		final double[] adblVariateRight)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (adblVariateLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (adblVariateRight))
			return null;

		double dblValue = java.lang.Double.NaN;
		double dblMaxima = java.lang.Double.NaN;
		int iNumVariate = adblVariateLeft.length;
		double[] adblVariate = new double[iNumVariate];
		double[] adblVariateWidth = new double[iNumVariate];
		double[] adblMaximaVariate = new double[iNumVariate];

		if (adblVariateRight.length != iNumVariate) return null;

		for (int j = 0; j < iNumVariate; ++j)
			adblVariateWidth[j] = adblVariateRight[j] - adblVariateLeft[j];

		for (int i = 0; i < EXTREMA_SAMPLING; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				adblVariate[j] = adblVariateLeft[j] + java.lang.Math.random() * adblVariateWidth[j];

			try {
				dblValue = evaluate (adblVariate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (!org.drip.quant.common.NumberUtil.IsValid (dblMaxima)) {
				dblMaxima = dblValue;

				for (int j = 0; j < iNumVariate; ++j)
					adblMaximaVariate[j] = adblVariate[j];
			} else {
				if (dblMaxima < dblValue) {
					dblMaxima = dblValue;

					for (int j = 0; j < iNumVariate; ++j)
						adblMaximaVariate[j] = adblVariate[j];
				}
			}
		}

		try {
			return new org.drip.function.definition.VariateOutputPair (adblMaximaVariate, new double[]
				{dblMaxima});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Minimum VOP within the Variate Array Range Using Uniform Monte-Carlo
	 * 
	 * @param adblVariateLeft The Range Left End Array
	 * @param adblVariateRight The Range Right End Array
	 * 
	 * @return The Minimum VOP
	 */

	public org.drip.function.definition.VariateOutputPair minima (
		final double[] adblVariateLeft,
		final double[] adblVariateRight)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (adblVariateLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (adblVariateRight))
			return null;

		double dblValue = java.lang.Double.NaN;
		double dblMinima = java.lang.Double.NaN;
		int iNumVariate = adblVariateLeft.length;
		double[] adblVariate = new double[iNumVariate];
		double[] adblVariateWidth = new double[iNumVariate];
		double[] adblMinimaVariate = new double[iNumVariate];

		if (adblVariateRight.length != iNumVariate) return null;

		for (int j = 0; j < iNumVariate; ++j)
			adblVariateWidth[j] = adblVariateRight[j] - adblVariateLeft[j];

		for (int i = 0; i < EXTREMA_SAMPLING; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				adblVariate[j] = adblVariateLeft[j] + java.lang.Math.random() * adblVariateWidth[j];

			try {
				dblValue = evaluate (adblVariate);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (!org.drip.quant.common.NumberUtil.IsValid (dblMinima)) {
				dblMinima = dblValue;

				for (int j = 0; j < iNumVariate; ++j)
					adblMinimaVariate[j] = adblVariate[j];
			} else {
				if (dblMinima > dblValue) {
					dblMinima = dblValue;

					for (int j = 0; j < iNumVariate; ++j)
						adblMinimaVariate[j] = adblVariate[j];
				}
			}
		}

		try {
			return new org.drip.function.definition.VariateOutputPair (adblMinimaVariate, new double[]
				{dblMinima});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Modulus of the Gradient at the Specified Variate location
	 * 
	 * @param adblVariate The Variate Array location
	 * 
	 * @return The Modulus of the Gradient at the Specified Variate location
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double gradientModulus (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		double[] adblJacobian = jacobian (adblVariate);

		if (null == adblJacobian)
			throw new java.lang.Exception ("RdToR1::gradientModulus => Invalid Inputs!");

		double dblGradientModulus = 0.;
		int iNumVariate = adblVariate.length;

		for (int i = 0; i < iNumVariate; ++i)
			dblGradientModulus += adblJacobian[i] * adblJacobian[i];

		return dblGradientModulus;
	}

	/**
	 * Generate the Gradient Modulus Function
	 * 
	 * @return The Gradient Modulus Function
	 */

	public org.drip.function.definition.RdToR1 gradientModulusFunction()
	{
		org.drip.function.definition.RdToR1 gradientModulusRdToR1 = new org.drip.function.definition.RdToR1
			(null) {
			@Override public int dimension()
			{
				return dimension();
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				return gradientModulus (adblVariate);
			}

			@Override public double[] jacobian (
				final double[] adblVariate)
			{
				double[] adblParentJacobian = jacobian (adblVariate);

				double[][] adblParentHessian = hessian (adblVariate);

				if (null == adblParentJacobian || null == adblParentHessian) return null;

				int iDimension = adblParentJacobian.length;
				double[] adblGradientModulusJacobian = new double[iDimension];

				for (int k = 0; k < iDimension; ++k) {
					adblGradientModulusJacobian[k] = 0.;

					for (int i = 0; i < iDimension; ++i)
						adblGradientModulusJacobian[k] += adblParentJacobian[i] * adblParentHessian[i][k];

					adblGradientModulusJacobian[k] *= 2.;
				}

				return adblGradientModulusJacobian;
			}
		};

		return gradientModulusRdToR1;
	}
}
