
package org.drip.quant.fourier;

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
 * <i>ComplexNumber</i> implements the functionality for dealing with Complex Numbers.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant">Quant</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/quant/fourier">Fourier Analysis</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ComplexNumber {
	private double _dblReal = java.lang.Double.NaN;
	private double _dblImaginary = java.lang.Double.NaN;

	/**
	 * Add the 2 Complex Numbers
	 * 
	 * @param cn1 The First Complex Number
	 * @param cn2 The Second Complex Number
	 * 
	 * @return The Complex Number instance that is a sum of the two
	 */

	public static final ComplexNumber Add (
		final ComplexNumber cn1,
		final ComplexNumber cn2)
	{
		if (null == cn1 || null == cn2) return null;

		try {
			return new ComplexNumber (cn1.real() + cn2.real(), cn1.imaginary() + cn2.imaginary());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Scale the Complex Number with the factor
	 * 
	 * @param cn The Complex Number
	 * @param dblScale The Scaling Factor
	 * 
	 * @return The Scaled Complex Number
	 */

	public static final ComplexNumber Scale (
		final ComplexNumber cn,
		final double dblScale)
	{
		if (null == cn || !org.drip.quant.common.NumberUtil.IsValid (dblScale)) return null;

		try {
			return new ComplexNumber (dblScale * cn.real(), dblScale * cn.imaginary());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Subtract the Second Complex Number from the First
	 * 
	 * @param cn1 The First Complex Number
	 * @param cn2 The Second Complex Number
	 * 
	 * @return The "Difference" Complex Number
	 */

	public static final ComplexNumber Subtract (
		final ComplexNumber cn1,
		final ComplexNumber cn2)
	{
		if (null == cn1 || null == cn2) return null;

		try {
			return new ComplexNumber (cn1.real() - cn2.real(), cn1.imaginary() - cn2.imaginary());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Multiply the 2 Complex Numbers
	 * 
	 * @param cn1 The First Complex Number
	 * @param cn2 The Second Complex Number
	 * 
	 * @return The Complex Number instance that is a product of the two
	 */

	public static final ComplexNumber Multiply (
		final ComplexNumber cn1,
		final ComplexNumber cn2)
	{
		if (null == cn1 || null == cn2) return null;

		double dblReal1 = cn1.real();

		double dblReal2 = cn2.real();

		double dblImaginary1 = cn1.imaginary();

		double dblImaginary2 = cn2.imaginary();

		try {
			return new ComplexNumber (dblReal1 * dblReal2 - dblImaginary1 * dblImaginary2, dblReal1 *
				dblImaginary2 + dblReal2 * dblImaginary1);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Divide the Numerator Complex Number by the Denominator
	 * 
	 * @param cnNumerator The Numerator Complex Number
	 * @param cnDenominator The Denominator Complex Number
	 * 
	 * @return The "Divided" Complex Number
	 */

	public static final ComplexNumber Divide (
		final ComplexNumber cnNumerator,
		final ComplexNumber cnDenominator)
	{
		if (null == cnNumerator || null == cnDenominator) return null;

		double dblRealNumerator = cnNumerator.real();

		double dblRealDenominator = cnDenominator.real();

		double dblImaginaryNumerator = cnNumerator.imaginary();

		double dblImaginaryDenominator = cnDenominator.imaginary();

		if (0. == dblRealDenominator && 0. == dblImaginaryDenominator) return null;

		double dblInverseDenominatorModulus = 1. / cnDenominator.modulus();

		try {
			return new ComplexNumber ((dblRealNumerator * dblRealDenominator + dblImaginaryNumerator *
				dblImaginaryDenominator) * dblInverseDenominatorModulus, (dblRealDenominator *
					dblImaginaryNumerator - dblRealNumerator * dblImaginaryDenominator) *
						dblInverseDenominatorModulus);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Square the Complex Number
	 * 
	 * @param cn The Complex Number
	 * 
	 * @return The Squared Complex Number Instance
	 */

	public static final ComplexNumber Square (
		final ComplexNumber cn)
	{
		if (null == cn) return null;

		double dblRealOther = cn.real();

		double dblImaginaryOther = cn.imaginary();

		try {
			return new ComplexNumber (dblRealOther * dblRealOther - dblImaginaryOther * dblImaginaryOther, 2.
				* dblRealOther * dblImaginaryOther);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Square Root of the Complex Number
	 * 
	 * @param cn The Complex Number
	 * 
	 * @return The Square Root Complex Number Instance
	 */

	public static final ComplexNumber SquareRoot (
		final ComplexNumber cn)
	{
		if (null == cn) return null;

		double dblModulus = cn.modulus();

		if (0. == dblModulus) {
			try {
				return new ComplexNumber (0., 0.);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		double dblReal = java.lang.Math.sqrt (0.5 * (cn.real() + java.lang.Math.sqrt (dblModulus)));

		try {
			return new ComplexNumber (dblReal, 0.5 * cn.imaginary() / dblReal);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Exponentiate the  Complex Number
	 * 
	 * @param cn The Complex Number
	 * 
	 * @return The Exponentiated Complex Number Instance
	 */

	public static final ComplexNumber Exponentiate (
		final ComplexNumber cn)
	{
		if (null == cn) return null;

		double dblImaginary = cn.imaginary();

		double dblCoefficient = java.lang.Math.exp (cn.real());

		try {
			return new ComplexNumber (dblCoefficient * java.lang.Math.cos (dblImaginary), dblCoefficient *
				java.lang.Math.sin (dblImaginary));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute Logarithm of the Complex Number
	 * 
	 * @param cn The Complex Number
	 * 
	 * @return The Complex Number Logarithm Instance
	 */

	public static final ComplexNumber Logarithm (
		final ComplexNumber cn)
	{
		if (null == cn) return null;

		double dblModulus = cn.modulus();

		if (0. == dblModulus) return null;

		try {
			return new ComplexNumber (0.5 * java.lang.Math.log (dblModulus), java.lang.Math.atan
				(cn.imaginary() / cn.real()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ComplexNumber constructor
	 * 
	 * @param dblReal Real Part
	 * @param dblImaginary Imaginary Part
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public ComplexNumber (
		final double dblReal,
		final double dblImaginary)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblReal = dblReal) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblImaginary = dblImaginary))
			throw new java.lang.Exception ("ComplexNumber ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Real Part
	 * 
	 * @return The Real Part
	 */

	public double real()
	{
		return _dblReal;
	}

	/**
	 * Retrieve the Imaginary Part
	 * 
	 * @return The Imaginary Part
	 */

	public double imaginary()
	{
		return _dblImaginary;
	}

	/**
	 * Retrieve the Modulus
	 * 
	 * @return The Modulus
	 */

	public double modulus()
	{
		return _dblReal * _dblReal + _dblImaginary * _dblImaginary;
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
		return java.lang.Math.atan (_dblImaginary / _dblReal);
	}

	/**
	 * Display the Real/Imaginary Contents
	 * 
	 * @return The Real/Imaginary Contents
	 */

	public java.lang.String display()
	{
		return "\t[" + _dblReal + ", " + _dblImaginary + "]";
	}
}
