
package org.drip.function.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>R1ToR1</i> provides the evaluation of the objective function and its derivatives for a specified
 * variate. Default implementation of the derivatives are for non-analytical black box objective functions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/README.md">Function Implementation Ancillary Support Objects</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class R1ToR1 {
	protected org.drip.numerical.differentiation.DerivativeControl _dc = null;

	protected R1ToR1 (
		final org.drip.numerical.differentiation.DerivativeControl dc)
	{
		if (null == (_dc = dc)) _dc = new org.drip.numerical.differentiation.DerivativeControl();
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

	public org.drip.numerical.differentiation.Differential differential (
		final double dblVariate,
		final double dblOFBase,
		final int iOrder)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblVariate) || 0 >= iOrder) return null;

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
				dblDerivative += (i % 2 == 0 ? 1 : -1) * org.drip.numerical.common.NumberUtil.NCK (iOrder, i) *
					evaluate (dblVariate + dblVariateInfinitesimal * (iOrder - 2. * i));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		try {
			return new org.drip.numerical.differentiation.Differential (dblOrderedVariateInfinitesimal, dblDerivative);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calculate the Ordered Derivative
	 * 
	 * @param dblVariate Variate at which the derivative is to be calculated
	 * @param iOrder Order of the derivative to be computed
	 * 
	 * @return The Derivative
	 */

	public org.drip.numerical.differentiation.Differential differential (
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
		return org.drip.numerical.r1integration.Integrator.Boole (this, dblBegin, dblEnd);
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblVariateLeft) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblVariateRight) || dblVariateLeft >= dblVariateRight)
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblVariateLeft) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblVariateRight) || dblVariateLeft >= dblVariateRight)
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

	/**
	 * Compute the Anti-Derivative Function
	 * 
	 * @return The Anti-Derivative Function
	 */

	public R1ToR1 antiDerivative()
	{
		return null;
	}

	/**
	 * Compute the Residue if the Variate is a Pole
	 * 
	 * @param x Variate
	 * 
	 * @return The Residue if the Variate is a Pole
	 */

	public org.drip.function.definition.PoleResidue poleResidue (
		final double x)
	{
		return null;
	}

	/**
	 * Compute the Condition Number at the specified Variate
	 * 
	 * @param x Variate
	 * 
	 * @return The Condition Number
	 * 
	 * @throws Exception Thrown if the Condition Number cannor be computed
	 */

	public double conditionNumber (
		final double x)
		throws Exception
	{
		return x * derivative (x, 1) / evaluate (x);
	}
}
