
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
 * <i>CartesianC1</i> implements the functionality for dealing with the Cartesian Form of Complex Numbers.
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

public class CartesianC1
{
	private double _real = Double.NaN;
	private double _imaginary = Double.NaN;

	/**
	 * Exponentiate the Complex Number
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Exponentiated Complex Number Instance
	 */

	public static final CartesianC1 Exponentiate (
		final CartesianC1 complexNumber)
	{
		if (null == complexNumber)
		{
			return null;
		}

		double argument = complexNumber.imaginary();

		double modulus = java.lang.Math.exp (complexNumber.real());

		try
		{
			return new CartesianC1 (
				modulus * java.lang.Math.cos (argument),
				modulus * java.lang.Math.sin (argument)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute Logarithm of the Complex Number
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Complex Number Logarithm Instance
	 */

	public static final CartesianC1 Logarithm (
		final CartesianC1 complexNumber)
	{
		if (null == complexNumber)
		{
			return null;
		}

		double modulus = complexNumber.modulus();

		if (0. == modulus)
		{
			return null;
		}

		try
		{
			return new CartesianC1 (
				0.5 * java.lang.Math.log (modulus),
				complexNumber.argument()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Complex Number from its Polar Representation
	 * 
	 * @param r r
	 * @param theta theta
	 * 
	 * @return Complex Number from its Polar Representation
	 */

	public static final CartesianC1 FromPolar (
		final double r,
		final double theta)
	{
		try
		{
			return !org.drip.numerical.common.NumberUtil.IsValid (r) ||
				!org.drip.numerical.common.NumberUtil.IsValid (theta) ? null :
				new CartesianC1 (
					r * java.lang.Math.cos (theta),
					r * java.lang.Math.sin (theta)
				);
		}
		catch (java.lang.Exception e)
		{
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

	public CartesianC1 (
		final double real,
		final double imaginary)
		throws Exception
	{
		if (!NumberUtil.IsValid (_real = real) || !NumberUtil.IsValid (_imaginary = imaginary)) {
			throw new Exception ("CartesianC1 Constructor => Invalid Inputs");
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

	public CartesianC1 add (
		final CartesianC1 cartesianC1)
	{
		return null == cartesianC1 ? this : C1MatrixUtil.UnsafeAdd (this, cartesianC1);
	}

	/**
	 * Scale the Complex Number with the factor
	 * 
	 * @param scale The Scaling Factor
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public CartesianC1 scale (
		final double scale)
	{
		return NumberUtil.IsValid (scale) ? null : C1MatrixUtil.UnsafeScale (this, scale);
	}

	/**
	 * Subtract the Input Cartesian C<sup>1</sup> from the current Instance
	 * 
	 * @param cartesianC1 Input Cartesian C<sup>1</sup>
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public CartesianC1 subtract (
		final CartesianC1 cartesianC1)
	{
		return null == cartesianC1 ? this : C1MatrixUtil.UnsafeSubtract (this, cartesianC1);
	}

	/**
	 * Multiply the Input Cartesian C<sup>1</sup> with the current Instance
	 * 
	 * @param cartesianC1 Input Cartesian C<sup>1</sup>
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public CartesianC1 multiply (
		final CartesianC1 cartesianC1)
	{
		return null == cartesianC1 ? this : C1MatrixUtil.UnsafeMultiply (this, cartesianC1);
	}

	/**
	 * Divide the Current Instance by the Input Cartesian C<sup>1</sup>
	 * 
	 * @param cartesianC1 Input Cartesian C<sup>1</sup>
	 * 
	 * @return Output Cartesian C<sup>1</sup>
	 */

	public CartesianC1 divide (
		final CartesianC1 cartesianC1)
	{
		return null == cartesianC1 ? this : C1MatrixUtil.UnsafeDivide (this, cartesianC1);
	}

	/**
	 * Compute the Square of the Complex Number
	 * 
	 * @return The Square Complex Number Instance
	 */

	public CartesianC1 square()
	{
		return C1MatrixUtil.UnsafeSquare (this);
	}

	/**
	 * Compute the Square Root of the Complex Number
	 * 
	 * @return The Square Root Complex Number Instance
	 */

	public CartesianC1 squareRoot()
	{
		return C1MatrixUtil.UnsafeSquareRoot (this);
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
