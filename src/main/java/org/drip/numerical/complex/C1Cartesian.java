
package org.drip.numerical.complex;

import org.drip.numerical.common.NumberUtil;

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
 * <i>C1Cartesian</i> implements the functionality for dealing with the Cartesian Form of Complex Numbers.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/complex/README.md">Implementation of Complex Number Suite</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class C1Cartesian
{
	private double _real = Double.NaN;
	private double _imaginary = Double.NaN;

	/**
	 * Construct the Complex Number from its Polar Representation
	 * 
	 * @param r r
	 * @param theta theta
	 * 
	 * @return Complex Number from its Polar Representation
	 */

	public static final C1Cartesian FromPolar (
		final double r,
		final double theta)
	{
		try {
			return !NumberUtil.IsValid (r) || !NumberUtil.IsValid (theta) ? null : new C1Cartesian (
				r * Math.cos (theta),
				r * Math.sin (theta)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a "Zero" Complex Number
	 * 
	 * @return "Zero" Complex Number
	 */

	public static final C1Cartesian Zero()
	{
		try {
			new C1Cartesian (0., 0.);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Unit Real Complex Number
	 * 
	 * @return Unit Real Complex Number
	 */

	public static final C1Cartesian UnitReal()
	{
		try {
			new C1Cartesian (1., 0.);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Unit Imaginary Complex Number
	 * 
	 * @return Unit Imaginary Complex Number
	 */

	public static final C1Cartesian UnitImaginary()
	{
		try {
			new C1Cartesian (0., 1.);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CartesianComplexNumber constructor
	 * 
	 * @param real Real Part
	 * @param imaginary Imaginary Part
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public C1Cartesian (
		final double real,
		final double imaginary)
		throws Exception
	{
		if (!NumberUtil.IsValid (_real = real) || !NumberUtil.IsValid (_imaginary = imaginary)) {
			throw new Exception ("C1Cartesian Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Real Part
	 * 
	 * @return The Real Part
	 */

	public double real()
	{
		return _real;
	}

	/**
	 * Retrieve the Imaginary Part
	 * 
	 * @return The Imaginary Part
	 */

	public double imaginary()
	{
		return _imaginary;
	}

	/**
	 * Retrieve the Modulus
	 * 
	 * @return The Modulus
	 */

	public double modulus()
	{
		return _real * _real + _imaginary * _imaginary;
	}

	/**
	 * Retrieve the L<sup>2</sup> Norm
	 * 
	 * @return The L<sup>2</sup> Norm
	 */

	public double l2Norm()
	{
		return Math.sqrt (modulus());
	}

	/**
	 * Retrieve the Absolute Value
	 * 
	 * @return The Absolute Value
	 */

	public double abs()
	{
		return Math.sqrt (modulus());
	}

	/**
	 * Retrieve the Argument
	 * 
	 * @return The Argument
	 */

	public double argument()
	{
		return 0. == _real && 0. == _imaginary ? 0. : Math.atan (_imaginary / _real);
	}

	/**
	 * Add the Input Cartesian C<sup>1</sup> to the current Instance
	 * 
	 * @param cartesianC1 Input Cartesian C<sup>1</sup>
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public C1Cartesian add (
		final C1Cartesian cartesianC1)
	{
		return null == cartesianC1 ? this : C1Util.UnsafeAdd (this, cartesianC1);
	}

	/**
	 * Scale the Complex Number with the factor
	 * 
	 * @param scale The Scaling Factor
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public C1Cartesian scale (
		final double scale)
	{
		return NumberUtil.IsValid (scale) ? null : C1Util.UnsafeScale (this, scale);
	}

	/**
	 * Scale the Complex Number with the factor
	 * 
	 * @param scale The Scaling Factor
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public C1Cartesian scale (
		final C1Cartesian scale)
	{
		return null == scale ? null : C1Util.UnsafeScale (this, scale);
	}

	/**
	 * Subtract the Input Cartesian C<sup>1</sup> from the current Instance
	 * 
	 * @param cartesianC1 Input Cartesian C<sup>1</sup>
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public C1Cartesian subtract (
		final C1Cartesian cartesianC1)
	{
		return null == cartesianC1 ? this : C1Util.UnsafeSubtract (this, cartesianC1);
	}

	/**
	 * Multiply the Input Cartesian C<sup>1</sup> with the current Instance
	 * 
	 * @param cartesianC1 Input Cartesian C<sup>1</sup>
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public C1Cartesian product (
		final C1Cartesian cartesianC1)
	{
		return null == cartesianC1 ? this : C1Util.UnsafeProduct (this, cartesianC1);
	}

	/**
	 * Divide the Current Instance by the Input Cartesian C<sup>1</sup>
	 * 
	 * @param cartesianC1 Input Cartesian C<sup>1</sup>
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public C1Cartesian divide (
		final C1Cartesian cartesianC1)
	{
		return null == cartesianC1 ? this : C1Util.UnsafeDivide (this, cartesianC1);
	}

	/**
	 * Compute the Square of the Complex Number
	 * 
	 * @return The Square Complex Number Instance
	 */

	public C1Cartesian square()
	{
		return C1Util.UnsafeSquare (this);
	}

	/**
	 * Compute the Square Root of the Complex Number
	 * 
	 * @return The Square Root Complex Number Instance
	 */

	public C1Cartesian squareRoot()
	{
		return C1Util.UnsafeSquareRoot (this);
	}

	/**
	 * Exponentiate the Complex Number
	 * 
	 * @return The Exponentiated Complex Number Instance
	 */

	public C1Cartesian exponentiate()
	{
		return C1Util.UnsafeExponentiate (this);
	}

	/**
	 * Compute Logarithm of the Complex Number
	 * 
	 * @return The Complex Number Logarithm Instance
	 */

	public C1Cartesian logarithm()
	{
		return C1Util.UnsafeLogarithm (this);
	}

	/**
	 * Dot Product of with the "Other"
	 * 
	 * @param other "Other" C<sup>1</sup>
	 * 
	 * @return The Dot Product
	 * 
	 * @throws Exception Thrown if the Dot Product Cannot be computed
	 */

	public double dotProduct (
		final C1Cartesian other)
		throws Exception
	{
		if (null == other) {
			throw new Exception ("C1Cartesian::dotProduct => Invalid Inputs");
		}

		return C1Util.UnsafeDotProduct (this, other);
	}

	/**
	 * Compute Conjugate of the Complex Number
	 * 
	 * @return The Complex Number Conjugate Instance
	 */

	public C1Cartesian conjugate()
	{
		try {
			return new C1Cartesian (_real, -1. * _imaginary);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Display the Real/Imaginary Contents
	 * 
	 * @return The Real/Imaginary Contents
	 */

	public String display()
	{
		return "\t[" + _real + ", " + _imaginary + "]";
	}
}
