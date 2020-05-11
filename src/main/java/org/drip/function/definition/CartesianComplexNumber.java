
package org.drip.function.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>CartesianComplexNumber</i> implements the functionality for dealing with the Cartesian Form of Complex
 * 	Numbers.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/definition/README.md">Function Implementation Ancillary Support Objects</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CartesianComplexNumber
{
	private double _real = java.lang.Double.NaN;
	private double _imaginary = java.lang.Double.NaN;

	/**
	 * Add the 2 Complex Numbers
	 * 
	 * @param complexNumber1 The First Complex Number
	 * @param complexNumber2 The Second Complex Number
	 * 
	 * @return The Complex Number instance that is a sum of the two
	 */

	public static final CartesianComplexNumber Add (
		final CartesianComplexNumber complexNumber1,
		final CartesianComplexNumber complexNumber2)
	{
		if (null == complexNumber1 || null == complexNumber2)
		{
			return null;
		}

		try
		{
			return new CartesianComplexNumber (
				complexNumber1.real() + complexNumber2.real(),
				complexNumber1.imaginary() + complexNumber2.imaginary()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Scale the Complex Number with the factor
	 * 
	 * @param complexNumber The Complex Number
	 * @param scale The Scaling Factor
	 * 
	 * @return The Scaled Complex Number
	 */

	public static final CartesianComplexNumber Scale (
		final CartesianComplexNumber complexNumber,
		final double scale)
	{
		if (null == complexNumber || !org.drip.numerical.common.NumberUtil.IsValid (scale))
		{
			return null;
		}

		try
		{
			return new CartesianComplexNumber (
				scale * complexNumber.real(),
				scale * complexNumber.imaginary()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Subtract the Second Complex Number from the First
	 * 
	 * @param complexNumber1 The First Complex Number
	 * @param complexNumber2 The Second Complex Number
	 * 
	 * @return The "Difference" Complex Number
	 */

	public static final CartesianComplexNumber Subtract (
		final CartesianComplexNumber complexNumber1,
		final CartesianComplexNumber complexNumber2)
	{
		if (null == complexNumber1 || null == complexNumber2)
		{
			return null;
		}

		try {
			return new CartesianComplexNumber (
				complexNumber1.real() - complexNumber2.real(),
				complexNumber1.imaginary() - complexNumber2.imaginary()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Multiply the 2 Complex Numbers
	 * 
	 * @param complexNumber1 The First Complex Number
	 * @param complexNumber2 The Second Complex Number
	 * 
	 * @return The Complex Number instance that is a product of the two
	 */

	public static final CartesianComplexNumber Multiply (
		final CartesianComplexNumber complexNumber1,
		final CartesianComplexNumber complexNumber2)
	{
		if (null == complexNumber1 || null == complexNumber2)
		{
			return null;
		}

		double real1 = complexNumber1.real();

		double real2 = complexNumber2.real();

		double imaginary1 = complexNumber1.imaginary();

		double imaginary2 = complexNumber2.imaginary();

		try
		{
			return new CartesianComplexNumber (
				real1 * real2 - imaginary1 * imaginary2,
				real1 * imaginary2 + real2 * imaginary1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Divide the Numerator Complex Number by the Denominator Complex Number
	 * 
	 * @param numerator The Numerator Complex Number
	 * @param denominator The Denominator Complex Number
	 * 
	 * @return The "Divided" Complex Number
	 */

	public static final CartesianComplexNumber Divide (
		final CartesianComplexNumber numerator,
		final CartesianComplexNumber denominator)
	{
		if (null == numerator || null == denominator)
		{
			return null;
		}

		double numeratorReal = numerator.real();

		double denominatorReal = denominator.real();

		double numeratorImaginary = numerator.imaginary();

		double denominatorImaginary = denominator.imaginary();

		if (0. == denominatorReal && 0. == denominatorImaginary)
		{
			return null;
		}

		double inverseDenominatorModulus = 1. / denominator.modulus();

		try
		{
			return new CartesianComplexNumber (
				(numeratorReal * denominatorReal + numeratorImaginary * denominatorImaginary) *
					inverseDenominatorModulus,
				(denominatorReal * numeratorImaginary - numeratorReal * denominatorImaginary) *
					inverseDenominatorModulus
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Square the Complex Number
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Squared Complex Number Instance
	 */

	public static final CartesianComplexNumber Square (
		final CartesianComplexNumber complexNumber)
	{
		if (null == complexNumber)
		{
			return null;
		}

		double modulus = complexNumber.modulus();

		if (0. == modulus)
		{
			try
			{
				return new CartesianComplexNumber (
					0.,
					0.
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}

			return null;
		}

		double argument = 2. * complexNumber.argument();

		try
		{
			return new CartesianComplexNumber (
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
	 * Compute the Square Root of the Complex Number
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Square Root Complex Number Instance
	 */

	public static final CartesianComplexNumber SquareRoot (
		final CartesianComplexNumber complexNumber)
	{
		if (null == complexNumber)
		{
			return null;
		}

		double modulus = java.lang.Math.sqrt (complexNumber.modulus());

		if (0. == modulus)
		{
			try
			{
				return new CartesianComplexNumber (
					0.,
					0.
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}

			return null;
		}

		double argument = 0.5 * complexNumber.argument();

		try
		{
			return new CartesianComplexNumber (
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
	 * Exponentiate the Complex Number
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Exponentiated Complex Number Instance
	 */

	public static final CartesianComplexNumber Exponentiate (
		final CartesianComplexNumber complexNumber)
	{
		if (null == complexNumber)
		{
			return null;
		}

		double argument = complexNumber.imaginary();

		double modulus = java.lang.Math.exp (complexNumber.real());

		try
		{
			return new CartesianComplexNumber (
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

	public static final CartesianComplexNumber Logarithm (
		final CartesianComplexNumber complexNumber)
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
			return new CartesianComplexNumber (
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

	public static final CartesianComplexNumber FromPolar (
		final double r,
		final double theta)
	{
		try
		{
			return !org.drip.numerical.common.NumberUtil.IsValid (r) ||
				!org.drip.numerical.common.NumberUtil.IsValid (theta) ? null :
				new CartesianComplexNumber (
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
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public CartesianComplexNumber (
		final double real,
		final double imaginary)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_real = real) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_imaginary = imaginary))
		{
			throw new java.lang.Exception ("CartesianComplexNumber Constructor => Invalid Inputs");
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
		return java.lang.Math.sqrt (modulus());
	}

	/**
	 * Retrieve the Argument
	 * 
	 * @return The Argument
	 */

	public double argument()
	{
		return 0. == _real && 0. == _imaginary ? 0. : java.lang.Math.atan (_imaginary / _real);
	}

	/**
	 * Display the Real/Imaginary Contents
	 * 
	 * @return The Real/Imaginary Contents
	 */

	public java.lang.String display()
	{
		return "\t[" + _real + ", " + _imaginary + "]";
	}
}
