
package org.drip.numerical.complex;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>C1Util</i> implements a C<sup>1</sup> Complex Number Manipulation Utilities. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Fuhr, H., and Z. Rzeszotnik (2018): A Note on Factoring Unitary Matrices <i>Linear Algebra and
 * 				its Applications</i> <b>547</b> 32-44
 * 		</li>
 * 		<li>
 * 			Horn, R. A., and C. R. Johnson (2013): <i>Matrix Analysis</i> <b>Cambridge University Press</b>
 * 				Cambridge UK
 * 		</li>
 * 		<li>
 * 			Li, C. K., and E. Poon (2002): Additive Decomposition of Real Matrices <i>Linear and Multilinear
 * 				Algebra</i> <b>50 (4)</b> 321-326
 * 		</li>
 * 		<li>
 * 			Marvian, I. (2022): Restrictions on realizable Unitary Operations imposed by Symmetry and
 * 				Locality <i>Nature Science</i> <b>18 (3)</b> 283-289
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Unitary Matrix https://en.wikipedia.org/wiki/Unitary_matrix
 * 		</li>
 * 	</ul>
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

public class C1Util
{

	/**
	 * Add the 2 Complex Numbers. Unsafe Methods do not validate the Input Arguments, so <b>use caution</b>
	 *  in applying these Methods
	 * 
	 * @param firstCartesianC1 The First Complex Number
	 * @param secondCartesianC1 The Second Complex Number
	 * 
	 * @return The Complex Number instance that is a sum of the two
	 */

	public static final C1Cartesian UnsafeAdd (
		final C1Cartesian firstCartesianC1,
		final C1Cartesian secondCartesianC1)
	{
		try {
			return new C1Cartesian (
				firstCartesianC1.real() + secondCartesianC1.real(),
				firstCartesianC1.imaginary() + secondCartesianC1.imaginary()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Scale the Complex Number with the factor. Unsafe Methods do not validate the Input Arguments, so
	 *  <b>use caution</b> in applying these Methods
	 * 
	 * @param cartesianC1 The Complex Number
	 * @param scale The Scaling Factor
	 * 
	 * @return The Scaled Complex Number
	 */

	public static final C1Cartesian UnsafeScale (
		final C1Cartesian cartesianC1,
		final double scale)
	{
		try {
			return new C1Cartesian (scale * cartesianC1.real(), scale * cartesianC1.imaginary());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Scale the Complex Number with the factor. Unsafe Methods do not validate the Input Arguments, so
	 *  <b>use caution</b> in applying these Methods
	 * 
	 * @param cartesianC1 The Complex Number
	 * @param cartesianC1Scale The Complex Scaling Factor
	 * 
	 * @return The Scaled Complex Number
	 */

	public static final C1Cartesian UnsafeScale (
		final C1Cartesian cartesianC1,
		final C1Cartesian cartesianC1Scale)
	{
		try {
			return UnsafeProduct (cartesianC1, cartesianC1Scale);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Subtract the Second Complex Number from the First. Unsafe Methods do not validate the Input Arguments,
	 * 	so <b>use caution</b> in applying these Methods
	 * 
	 * @param firstCartesianC1 The First Complex Number
	 * @param secondCartesianC1 The Second Complex Number
	 * 
	 * @return The "Difference" Complex Number
	 */

	public static final C1Cartesian UnsafeSubtract (
		final C1Cartesian firstCartesianC1,
		final C1Cartesian secondCartesianC1)
	{
		try {
			return new C1Cartesian (
				firstCartesianC1.real() - secondCartesianC1.real(),
				firstCartesianC1.imaginary() - secondCartesianC1.imaginary()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Multiply the 2 Complex Numbers. Unsafe Methods do not validate the Input Arguments, so <b>use
	 *  caution</b> in applying these Methods
	 * 
	 * @param firstCartesianC1 The First Complex Number
	 * @param secondCartesianC1 The Second Complex Number
	 * 
	 * @return The Complex Number instance that is a product of the two
	 */

	public static final C1Cartesian UnsafeProduct (
		final C1Cartesian firstCartesianC1,
		final C1Cartesian secondCartesianC1)
	{
		double real1 = firstCartesianC1.real();

		double real2 = secondCartesianC1.real();

		double imaginary1 = firstCartesianC1.imaginary();

		double imaginary2 = secondCartesianC1.imaginary();

		try {
			return new C1Cartesian (
				real1 * real2 - imaginary1 * imaginary2,
				real1 * imaginary2 + real2 * imaginary1
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Divide the Numerator Complex Number by the Denominator Complex Number. Unsafe Methods do not validate
	 *  the Input Arguments, so <b>use caution</b> in applying these Methods
	 * 
	 * @param numeratorC1 The Numerator Complex Number
	 * @param denominatorC1 The Denominator Complex Number
	 * 
	 * @return The "Divided" Complex Number
	 */

	public static final C1Cartesian UnsafeDivide (
		final C1Cartesian numeratorC1,
		final C1Cartesian denominatorC1)
	{
		double numeratorReal = numeratorC1.real();

		double denominatorReal = denominatorC1.real();

		double numeratorImaginary = numeratorC1.imaginary();

		double denominatorImaginary = denominatorC1.imaginary();

		if (0. == denominatorReal && 0. == denominatorImaginary) {
			return null;
		}

		double inverseDenominatorModulus = 1. / denominatorC1.modulus();

		try {
			return new C1Cartesian (
				(numeratorReal * denominatorReal + numeratorImaginary * denominatorImaginary) *
					inverseDenominatorModulus,
				(denominatorReal * numeratorImaginary - numeratorReal * denominatorImaginary) *
					inverseDenominatorModulus
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Square the Complex Number. Unsafe Methods do not validate the Input Arguments, so <b>use caution</b>
	 *  in applying these Methods
	 * 
	 * @param c1 The Complex Number
	 * 
	 * @return The Squared Complex Number Instance
	 */

	public static final C1Cartesian UnsafeSquare (
		final C1Cartesian c1)
	{
		double modulus = c1.modulus();

		if (0. == modulus) {
			try {
				return new C1Cartesian (0., 0.);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		double argument = 2. * c1.argument();

		try {
			return new C1Cartesian (modulus * Math.cos (argument), modulus * Math.sin (argument));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Square Root of the Complex Number. Unsafe Methods do not validate the Input Arguments, so
	 * 	<b>use caution</b> in applying these Methods
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Square Root Complex Number Instance
	 */

	public static final C1Cartesian UnsafeSquareRoot (
		final C1Cartesian complexNumber)
	{
		double modulus = java.lang.Math.sqrt (complexNumber.modulus());

		if (0. == modulus) {
			try {
				return new C1Cartesian (0., 0.);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		double argument = 0.5 * complexNumber.argument();

		try {
			return new C1Cartesian (modulus * Math.cos (argument), modulus * Math.sin (argument));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Exponentiate the Complex Number. Unsafe Methods do not validate the Input Arguments, so <b>use
	 * 	caution</b> in applying these Methods
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Exponentiated Complex Number Instance
	 */

	public static final C1Cartesian UnsafeExponentiate (
		final C1Cartesian complexNumber)
	{
		double argument = complexNumber.imaginary();

		double modulus = Math.exp (complexNumber.real());

		try {
			return new C1Cartesian (modulus * Math.cos (argument), modulus * Math.sin (argument));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute Logarithm of the Complex Number. Unsafe Methods do not validate the Input Arguments, so <b>use
	 * 	caution</b> in applying these Methods
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Complex Number Logarithm Instance
	 */

	public static final C1Cartesian UnsafeLogarithm (
		final C1Cartesian complexNumber)
	{
		double modulus = complexNumber.modulus();

		try {
			return 0. == modulus ? null : new C1Cartesian (
				0.5 * Math.log (modulus),
				complexNumber.argument()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Dot Product of Complex Numbers A and E. Unsafe Methods do not validate the Input Arguments, so <b>use
	 * 	caution</b> in applying these Methods
	 * 
	 * @param a First C<sup>1</sup>
	 * @param e Second C<sup>1</sup>
	 * 
	 * @return The Dot Product
	 */

	public static final double UnsafeDotProduct (
		final C1Cartesian a,
		final C1Cartesian e)
	{
		return a.real() * e.real() + a.imaginary() + a.imaginary();
	}

	/**
	 * Add the 2 Complex Numbers
	 * 
	 * @param firstCartesianC1 The First Complex Number
	 * @param secondCartesianC1 The Second Complex Number
	 * 
	 * @return The Complex Number instance that is a sum of the two
	 */

	public static final C1Cartesian Add (
		final C1Cartesian firstCartesianC1,
		final C1Cartesian secondCartesianC1)
	{
		return null == firstCartesianC1 || null == secondCartesianC1 ? null :
			UnsafeAdd (firstCartesianC1, secondCartesianC1);
	}

	/**
	 * Scale the Complex Number with the factor
	 * 
	 * @param cartesianC1 The Complex Number
	 * @param scale The Scaling Factor
	 * 
	 * @return The Scaled Complex Number
	 */

	public static final C1Cartesian Scale (
		final C1Cartesian cartesianC1,
		final double scale)
	{
		return null == cartesianC1 || !NumberUtil.IsValid (scale) ? null :
			UnsafeScale (cartesianC1, scale);
	}

	/**
	 * Scale the Complex Number with the factor
	 * 
	 * @param cartesianC1 The Complex Number
	 * @param cartesianC1Scale The ComplexScaling Factor
	 * 
	 * @return The Scaled Complex Number
	 */

	public static final C1Cartesian Scale (
		final C1Cartesian cartesianC1,
		final C1Cartesian cartesianC1Scale)
	{
		return null == cartesianC1 || null == cartesianC1? null :
			UnsafeScale (cartesianC1, cartesianC1Scale);
	}

	/**
	 * Subtract the Second Complex Number from the First
	 * 
	 * @param firstCartesianC1 The First Complex Number
	 * @param secondCartesianC1 The Second Complex Number
	 * 
	 * @return The "Difference" Complex Number
	 */

	public static final C1Cartesian Subtract (
		final C1Cartesian firstCartesianC1,
		final C1Cartesian secondCartesianC1)
	{
		return null == firstCartesianC1 || null == secondCartesianC1 ? null :
			UnsafeSubtract (firstCartesianC1, secondCartesianC1);
	}

	/**
	 * Multiply the 2 Complex Numbers
	 * 
	 * @param firstCartesianC1 The First Complex Number
	 * @param secondCartesianC1 The Second Complex Number
	 * 
	 * @return The Complex Number instance that is a product of the two
	 */

	public static final C1Cartesian Multiply (
		final C1Cartesian firstCartesianC1,
		final C1Cartesian secondCartesianC1)
	{
		return null == firstCartesianC1 || null == secondCartesianC1 ? null :
			UnsafeProduct (firstCartesianC1, secondCartesianC1);
	}

	/**
	 * Divide the Numerator Complex Number by the Denominator Complex Number
	 * 
	 * @param numeratorC1 The Numerator Complex Number
	 * @param denominatorC1 The Denominator Complex Number
	 * 
	 * @return The "Divided" Complex Number
	 */

	public static final C1Cartesian Divide (
		final C1Cartesian numeratorC1,
		final C1Cartesian denominatorC1)
	{
		return null == numeratorC1 || null == denominatorC1 ? null :
			UnsafeDivide (numeratorC1, denominatorC1);
	}

	/**
	 * Square the Complex Number
	 * 
	 * @param c1 The Complex Number
	 * 
	 * @return The Squared Complex Number Instance
	 */

	public static final C1Cartesian Square (
		final C1Cartesian c1)
	{
		return null == c1 ? null : UnsafeSquare (c1);
	}

	/**
	 * Compute the Square Root of the Complex Number
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Square Root Complex Number Instance
	 */

	public static final C1Cartesian SquareRoot (
		final C1Cartesian complexNumber)
	{
		return null == complexNumber ? null : UnsafeSquareRoot (complexNumber);
	}

	/**
	 * Exponentiate the Complex Number
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Exponentiated Complex Number Instance
	 */

	public static final C1Cartesian Exponentiate (
		final C1Cartesian complexNumber)
	{
		return null == complexNumber ? null : UnsafeExponentiate (complexNumber);
	}

	/**
	 * Compute Logarithm of the Complex Number
	 * 
	 * @param complexNumber The Complex Number
	 * 
	 * @return The Complex Number Logarithm Instance
	 */

	public static final C1Cartesian Logarithm (
		final C1Cartesian complexNumber)
	{
		return null == complexNumber ? null : UnsafeLogarithm (complexNumber);
	}

	/**
	 * Dot Product of Complex Numbers A and E
	 * 
	 * @param a First C<sup>1</sup>
	 * @param e Second C<sup>1</sup>
	 * 
	 * @return The Dot Product
	 * 
	 * @throws Exception Thrown if the Dot Product Cannot be computed
	 */

	public static final double DotProduct (
		final C1Cartesian a,
		final C1Cartesian e)
		throws Exception
	{
		if (null == a || null == e) {
			throw new Exception ("C1Util::DotProduct => Invalid Inputs");
		}

		return UnsafeDotProduct (a, e);
	}
}
