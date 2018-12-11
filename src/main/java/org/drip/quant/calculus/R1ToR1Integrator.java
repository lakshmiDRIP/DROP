
package org.drip.quant.calculus;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>R1ToR1Integrator</i> implements the following routines for integrating the R<sup>1</sup> To
 * R<sup>1</sup> objective Function.
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Linear Quadrature
 *  	</li>
 *  	<li>
 * 			Mid-Point Scheme
 *  	</li>
 *  	<li>
 * 			Trapezoidal Scheme
 *  	</li>
 *  	<li>
 * 			Simpson/Simpson38 schemes
 *  	</li>
 *  	<li>
 * 			Boole Scheme
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant">Quant</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant/calculus">Calculus</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ToR1Integrator {
	private final static int NUM_QUAD = 10000;

	/**
	 * Compute the function's integral within the specified limits using the LinearQuadrature technique.
	 * 
	 * @param funcR1ToR1 R1ToR1 Function
	 * @param dblLeft Left Variate
	 * @param dblRight Right Variate
	 * 
	 * @return The Integral
	 * 
	 * @throws java.lang.Exception Thrown if the error cannot be computed
	 */

	public static final double LinearQuadrature (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double dblLeft,
		final double dblRight)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || !org.drip.quant.common.NumberUtil.IsValid (dblLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRight) || dblLeft > dblRight)
			throw new java.lang.Exception ("R1ToR1Integrator::LinearQuadrature => Invalid Inputs");

		if (dblLeft == dblRight) return 0.;

		double dblWidth = (dblRight - dblLeft) / NUM_QUAD;
		double dblX = dblLeft + dblWidth;
		double dblAUArea = 0.;

		while (dblX <= dblRight) {
			double dblY = funcR1ToR1.evaluate (dblX - 0.5 * dblWidth);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblLeft))
				throw new java.lang.Exception
					("R1ToR1Integrator::LinearQuadrature => Cannot calculate an intermediate Y");

			dblAUArea += dblY * dblWidth;
			dblX += dblWidth;
		}

		return dblAUArea;
	}

	/**
	 * Compute the function's integral within the specified limits using the Mid-point rule.
	 * 
	 * @param funcR1ToR1 R1ToR1 Function
	 * @param dblLeft Left Variate
	 * @param dblRight Right Variate
	 * 
	 * @return The Integral
	 * 
	 * @throws java.lang.Exception Thrown if the error cannot be computed
	 */

	public static final double MidPoint (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double dblLeft,
		final double dblRight)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || !org.drip.quant.common.NumberUtil.IsValid (dblLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRight) || dblLeft > dblRight)
			throw new java.lang.Exception ("R1ToR1Integrator::MidPoint => Invalid Inputs");

		if (dblLeft == dblRight) return 0.;

		double dblYMid = funcR1ToR1.evaluate (0.5 * (dblLeft + dblRight));

		if (!org.drip.quant.common.NumberUtil.IsValid (dblYMid))
			throw new java.lang.Exception ("R1ToR1Integrator::MidPoint => Cannot calculate Y at " + 0.5 *
				(dblLeft + dblRight));

		return (dblRight - dblLeft) * dblYMid;
	}

	/**
	 * Compute the function's integral within the specified limits using the Trapezoidal rule.
	 * 
	 * @param funcR1ToR1 R1ToR1 Function
	 * @param dblLeft Left Variate
	 * @param dblRight Right Variate
	 * 
	 * @return The Integral
	 * 
	 * @throws java.lang.Exception Thrown if the error cannot be computed
	 */

	public static final double Trapezoidal (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double dblLeft,
		final double dblRight)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || !org.drip.quant.common.NumberUtil.IsValid (dblLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRight) || dblLeft > dblRight)
			throw new java.lang.Exception ("R1ToR1Integrator::Trapezoidal => Invalid Inputs");

		if (dblLeft == dblRight) return 0.;

		double dblYLeft = funcR1ToR1.evaluate (dblLeft);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblYLeft))
			throw new java.lang.Exception ("R1ToR1Integrator::Trapezoidal => Cannot calculate Y at " +
				dblLeft);

		double dblYRight = funcR1ToR1.evaluate (dblRight);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblYLeft))
			throw new java.lang.Exception ("R1ToR1Integrator::Trapezoidal => Cannot calculate Y at " +
				dblRight);

		return 0.5 * (dblRight - dblLeft) * (dblYLeft + dblYRight);
	}

	/**
	 * Compute the function's integral within the specified limits using the Simpson rule.
	 * 
	 * @param funcR1ToR1 R1ToR1 Function
	 * @param dblLeft Left Variate
	 * @param dblRight Right Variate
	 * 
	 * @return The Integral
	 * 
	 * @throws java.lang.Exception Thrown if the error cannot be computed
	 */

	public static final double Simpson (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double dblLeft,
		final double dblRight)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || !org.drip.quant.common.NumberUtil.IsValid (dblLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRight) || dblLeft > dblRight)
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson => Invalid Inputs");

		if (dblLeft == dblRight) return 0.;

		double dblYLeft = funcR1ToR1.evaluate (dblLeft);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblYLeft))
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson => Cannot calculate Y at " + dblLeft);

		double dblXMid = 0.5 * (dblLeft + dblRight);

		double dblYMid = funcR1ToR1.evaluate (dblXMid);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblYMid))
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson => Cannot calculate Y at " + dblXMid);

		double dblYRight = funcR1ToR1.evaluate (dblRight);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblYRight))
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson => Cannot calculate Y at " + dblRight);

		return (dblRight - dblLeft) / 6. * (dblYLeft + 4. * dblYMid + dblYRight);
	}

	/**
	 * Compute the function's integral within the specified limits using the Simpson 3/8 rule.
	 * 
	 * @param funcR1ToR1 R1ToR1 Function
	 * @param dblLeft Left Variate
	 * @param dblRight Right Variate
	 * 
	 * @return The Integral
	 * 
	 * @throws java.lang.Exception Thrown if the error cannot be computed
	 */

	public static final double Simpson38 (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double dblLeft,
		final double dblRight)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || !org.drip.quant.common.NumberUtil.IsValid (dblLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRight) || dblLeft > dblRight)
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson38 => Invalid Inputs");

		if (dblLeft == dblRight) return 0.;

		double dblY0 = funcR1ToR1.evaluate (dblLeft);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY0))
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson38 => Cannot calculate Y at " +
				dblLeft);

		double dblX1 = (2. * dblLeft + dblRight) / 3.;

		double dblY1 = funcR1ToR1.evaluate (dblX1);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY1))
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson38 => Cannot calculate Y at " + dblX1);

		double dblX2 = (dblLeft + 2. * dblRight) / 3.;

		double dblY2 = funcR1ToR1.evaluate (dblX2);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY2))
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson38 => Cannot calculate Y at " + dblX2);

		double dblY3 = funcR1ToR1.evaluate (dblRight);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY3))
			throw new java.lang.Exception ("R1ToR1Integrator::Simpson38 => Cannot calculate Y at " +
				dblRight);

		return (dblRight - dblLeft) * (0.125 * dblY0 + 0.375 * dblY1 + 0.375 * dblY2 + 0.125 * dblY3);
	}

	/**
	 * Compute the function's integral within the specified limits using the Boole rule.
	 * 
	 * @param funcR1ToR1 R1ToR1 Function
	 * @param dblLeft Left Variate
	 * @param dblRight Right Variate
	 * 
	 * @return The Integral
	 * 
	 * @throws java.lang.Exception Thrown if the error cannot be computed
	 */

	public static final double Boole (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double dblLeft,
		final double dblRight)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || !org.drip.quant.common.NumberUtil.IsValid (dblLeft) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRight) || dblLeft > dblRight)
			throw new java.lang.Exception ("R1ToR1Integrator::Boole => Invalid Inputs");

		if (dblLeft == dblRight) return 0.;

		double dblY0 = funcR1ToR1.evaluate (dblLeft);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY0))
			throw new java.lang.Exception ("R1ToR1Integrator::Boole => Cannot calculate Y at " + dblLeft);

		double dblX1 = 0.25 * dblLeft + 0.75 * dblRight;

		double dblY1 = funcR1ToR1.evaluate (dblX1);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY1))
			throw new java.lang.Exception ("R1ToR1Integrator::Boole => Cannot calculate Y at " + dblX1);

		double dblX2 = 0.5 * (dblLeft + dblRight);

		double dblY2 = funcR1ToR1.evaluate (dblX2);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY2))
			throw new java.lang.Exception ("R1ToR1Integrator::Boole => Cannot calculate Y at " + dblX2);

		double dblX3 = 0.75 * dblLeft + 0.25 * dblRight;

		double dblY3 = funcR1ToR1.evaluate (dblX3);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY3))
			throw new java.lang.Exception ("R1ToR1Integrator::Boole => Cannot calculate Y at " + dblX3);

		double dblY4 = funcR1ToR1.evaluate (dblRight);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblY4))
			throw new java.lang.Exception ("R1ToR1Integrator::Boole => Cannot calculate Y at " + dblRight);

		return (dblRight - dblLeft) / 90 * (7 * dblY0 + 32 * dblY1 + 12 * dblY2 + 32 * dblY3 + 7 * dblY4);
	}

	/**
	 * Integrate Numerically over [-infinity, +infinity] using a Change of Variables
	 * 
	 * @param funcR1ToR1 The R1ToR1 Function
	 * 
	 * @return The Numerical Integrand
	 * 
	 * @throws java.lang.Exception Thrown if the Integral cannot be computed
	 */

	public static final double LeftInfiniteRightInfinite (
		final org.drip.function.definition.R1ToR1 funcR1ToR1)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1)
			throw new java.lang.Exception ("IntegratorR1ToR1::LeftInfiniteRightInfinite => Invalid Inputs");

		org.drip.function.definition.R1ToR1 auTransformed = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("IntegratorR1ToR1::LeftInfiniteRightInfinite => Invalid Inputs");

				double dblX2 = dblX * dblX;
				double dblXTransform = 1. / (1. - dblX2);

				return (1. + dblX2) / (dblXTransform * dblXTransform) * funcR1ToR1.evaluate (dblX /
					dblXTransform);
			}
		};

		return auTransformed.integrate (-1., +1.);
	}

	/**
	 * Integrate the specified Function Numerically from -infinity to the specified Right Limit
	 * 
	 * @param funcR1ToR1 The Input R1ToR1 Function
	 * @param dblRight The Right Integration Limit
	 * 
	 * @return The Results of the Integration
	 * 
	 * @throws java.lang.Exception Thrown if the Integrand cannot be evaluated
	 */

	public static final double LeftInfinite (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double dblRight)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || !org.drip.quant.common.NumberUtil.IsValid (dblRight))
			throw new java.lang.Exception ("IntegratorR1ToR1::LeftInfinite => Invalid Inputs");

		org.drip.function.definition.R1ToR1 auTransformed = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception ("IntegratorR1ToR1::LeftInfinite => Invalid Inputs");

				return (funcR1ToR1.evaluate (dblRight - ((1. - dblX) / dblX))) / (dblX * dblX);
			}
		};

		return auTransformed.integrate (0., +1.);
	}

	/**
	 * Integrate the specified Function Numerically from the specified Left Limit to +infinity
	 * 
	 * @param funcR1ToR1 The Input R1ToR1 Function
	 * @param dblLeft The Left Integration Limit
	 * 
	 * @return The Results of the Integration
	 * 
	 * @throws java.lang.Exception Thrown if the Integrand cannot be evaluated
	 */

	public static final double RightInfinite (
		final org.drip.function.definition.R1ToR1 funcR1ToR1,
		final double dblLeft)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || !org.drip.quant.common.NumberUtil.IsValid (dblLeft))
			throw new java.lang.Exception ("IntegratorR1ToR1::RightInfinite => Invalid Inputs");

		org.drip.function.definition.R1ToR1 auTransformed = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception ("IntegratorR1ToR1::RightInfinite => Invalid Inputs");

				double dblXInversion = 1. - dblX;

				return (funcR1ToR1.evaluate (dblLeft + (dblX / dblXInversion))) / (dblXInversion *
					dblXInversion);
			}
		};

		return auTransformed.integrate (0., +1.);
	}
}
