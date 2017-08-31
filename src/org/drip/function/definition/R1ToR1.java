
package org.drip.function.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * R1ToR1 provides the evaluation of the objective function and its derivatives for a specified variate.
 *  Default implementation of the derivatives are for non-analytical black box objective functions.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class R1ToR1 {
	protected org.drip.quant.calculus.DerivativeControl _dc = null;

	protected R1ToR1 (
		final org.drip.quant.calculus.DerivativeControl dc)
	{
		if (null == (_dc = dc)) _dc = new org.drip.quant.calculus.DerivativeControl();
	}

	/**
	 * Evaluate for the given variate
	 * 
	 * @param dblVariate Variate
	 *  
	 * @return Returns the calculated value
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double evaluate (
		final double dblVariate)
		throws java.lang.Exception;

	/**
	 * Calculate the Differential
	 * 
	 * @param dblVariate Variate at which the derivative is to be calculated
	 * @param dblOFBase Base Value for the Objective Function
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Derivative
	 */

	public org.drip.quant.calculus.Differential differential (
		final double dblVariate,
		final double dblOFBase,
		final int iOrder)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate) || 0 >= iOrder) return null;

		double dblDerivative = 0.;
		double dblOrderedVariateInfinitesimal = 1.;
		double dblVariateInfinitesimal = java.lang.Double.NaN;

		try {
			dblVariateInfinitesimal = _dc.getVariateInfinitesimal (dblVariate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i <= iOrder; ++i) {
			if (0 != i) dblOrderedVariateInfinitesimal *= (2. * dblVariateInfinitesimal);

			try {
				dblDerivative += (i % 2 == 0 ? 1 : -1) * org.drip.quant.common.NumberUtil.NCK (iOrder, i) *
					evaluate (dblVariate + dblVariateInfinitesimal * (iOrder - 2. * i));
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
	 * Calculate the Differential
	 * 
	 * @param dblVariate Variate at which the derivative is to be calculated
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Derivative
	 */

	public org.drip.quant.calculus.Differential differential (
		final double dblVariate,
		final int iOrder)
	{
		try {
			return differential (dblVariate, evaluate (dblVariate), iOrder);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calculate the derivative as a double
	 * 
	 * @param dblVariate Variate at which the derivative is to be calculated
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Derivative
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public double derivative (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception
	{
		return differential (dblVariate, evaluate (dblVariate), iOrder).calcSlope (true);
	}

	/**
	 * Integrate over the given range
	 * 
	 * @param dblBegin Range Begin 
	 * @param dblEnd Range End 
	 *  
	 * @return The Integrated Value
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
	}

	/**
	 * Compute the Maximal Variate and the Corresponding Function Value
	 * 
	 * @return The Maximal Variate and the Corresponding Function Value
	 */

	public org.drip.function.definition.VariateOutputPair maxima()
	{
		R1ToR1 auDerivative = new R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return derivative (dblX, 1);
			}
		};

		try {
			org.drip.function.r1tor1solver.FixedPointFinder fpf = new
				org.drip.function.r1tor1solver.FixedPointFinderZheng (0., auDerivative, false);

			org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = fpf.findRoot();

			if (null == fpfo) return null;

			double dblExtrema = fpfo.getRoot();

			if (0. <= derivative (dblExtrema, 2)) return null;

			return new org.drip.function.definition.VariateOutputPair (new double[] {dblExtrema}, new
				double[] {evaluate (dblExtrema)});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Maximum VOP within the Variate Range
	 * 
	 * @param dblVariateLeft The Range Left End
	 * @param dblVariateRight The Range Right End
	 * 
	 * @return The Maximum VOP
	 */

	public org.drip.function.definition.VariateOutputPair maxima (
		final double dblVariateLeft,
		final double dblVariateRight)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariateLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblVariateRight) || dblVariateLeft >= dblVariateRight)
			return null;

		org.drip.function.definition.VariateOutputPair vop = maxima();

		if (null != vop) {
			double dblRoot = vop.variates()[0];

			if (dblVariateLeft <= dblRoot && dblVariateRight >= dblRoot) return vop;
		}

		try {
			double dblLeftOutput = evaluate (dblVariateLeft);

			double dblRightOutput = evaluate (dblVariateRight);

			return dblLeftOutput > dblRightOutput ? new org.drip.function.definition.VariateOutputPair
				(new double[] {dblVariateLeft}, new double[] {dblLeftOutput}) : new
					org.drip.function.definition.VariateOutputPair (new double[] {dblVariateRight}, new
						double[] {dblRightOutput});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Minimal Variate and the Corresponding Function Value
	 * 
	 * @return The Minimal Variate and the Corresponding Function Value
	 */

	public org.drip.function.definition.VariateOutputPair minima()
	{
		R1ToR1 auDerivative = new R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return derivative (dblX, 1);
			}
		};

		try {
			org.drip.function.r1tor1solver.FixedPointFinder fpf = new
				org.drip.function.r1tor1solver.FixedPointFinderZheng (0., auDerivative, false);

			org.drip.function.r1tor1solver.FixedPointFinderOutput fpfo = fpf.findRoot();

			if (null == fpfo) return null;

			double dblExtrema = fpfo.getRoot();

			if (0. >= derivative (dblExtrema, 2)) return null;

			return new org.drip.function.definition.VariateOutputPair (new double[] {dblExtrema}, new
				double[] {evaluate (dblExtrema)});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Minimum VOP within the Variate Range
	 * 
	 * @param dblVariateLeft The Range Left End
	 * @param dblVariateRight The Range Right End
	 * 
	 * @return The Minimum VOP
	 */

	public org.drip.function.definition.VariateOutputPair minima (
		final double dblVariateLeft,
		final double dblVariateRight)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariateLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblVariateRight) || dblVariateLeft >= dblVariateRight)
			return null;

		org.drip.function.definition.VariateOutputPair vop = minima();

		if (null != vop) {
			double dblRoot = vop.variates()[0];

			if (dblVariateLeft <= dblRoot && dblVariateRight >= dblRoot) return vop;
		}

		try {
			double dblLeftOutput = evaluate (dblVariateLeft);

			double dblRightOutput = evaluate (dblVariateRight);

			return dblLeftOutput < dblRightOutput ? new org.drip.function.definition.VariateOutputPair
				(new double[] {dblVariateLeft}, new double[] {dblLeftOutput}) : new
					org.drip.function.definition.VariateOutputPair (new double[] {dblVariateRight}, new
						double[] {dblRightOutput});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
