
package org.drip.spline.basis;

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
 * <i>FunctionSetBuilder</i> implements the basis set and spline builder for the following types of splines:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Exponential basis tension splines
 *  	</li>
 *  	<li>
 * 			Hyperbolic basis tension splines
 *  	</li>
 *  	<li>
 * 			Polynomial basis splines
 *  	</li>
 *  	<li>
 *  		Bernstein Polynomial basis splines
 *  	</li>
 *  	<li>
 *  		Kaklis Pandelis basis tension splines
 *  	</li>
 *  </ul>
 * 
 * This elastic coefficients for the segment using Ck basis splines inside [0,...,1) - Globally
 *  [x_0,...,x_1) are extracted for:
 * 
 * 			y = Estimator (Ck, x) * ShapeControl (x)
 * 
 *		where x is the normalized ordinate mapped as
 * 
 * 			x becomes (x - x_i-1) / (x_i - x_i-1)
 * 
 * The inverse quadratic/rational spline is a typical shape controller spline used.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/basis/README.md">Basis Spline Construction/Customization Parameters</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FunctionSetBuilder {

	/**
	 * This function implements the elastic coefficients for the segment using tension exponential basis
	 * 	splines inside - [0,...,1) - Globally [x_0,...,x_1). The segment equation is
	 * 
	 * 		y = A + B * x + C * exp (Tension * x / (x_i - x_i-1)) + D * exp (-Tension * x / (x_i - x_i-1))
	 * 
	 *	where x is the normalized ordinate mapped as
	 * 
	 * 		x .gte. (x - x_i-1) / (x_i - x_i-1)
	 * 
	 * @param etsp Exponential Tension Basis set Builder Parameters
	 * 
	 * @return Exponential Tension Basis Functions
	 */

	public static final org.drip.spline.basis.FunctionSet ExponentialTensionBasisSet (
		final org.drip.spline.basis.ExponentialTensionSetParams etsp)
	{
		if (null == etsp) return null;

		double dblTension = etsp.tension();

		try {
			return new org.drip.spline.basis.FunctionSet (new org.drip.function.definition.R1ToR1[]
				{new org.drip.function.r1tor1.Polynomial (0), new org.drip.function.r1tor1.Polynomial (1),
					new org.drip.function.r1tor1.ExponentialTension (java.lang.Math.E, dblTension), new
						org.drip.function.r1tor1.ExponentialTension (java.lang.Math.E, -dblTension)});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * This function implements the elastic coefficients for the segment using tension hyperbolic basis
	 * 	splines inside - [0,...,1) - Globally [x_0,...,x_1). The segment equation is
	 * 
	 * 		y = A + B * x + C * sinh (Tension * x / (x_i - x_i-1)) + D * cosh (Tension * x / (x_i - x_i-1))
	 * 
	 *	where x is the normalized ordinate mapped as
	 * 
	 * 		x .ge. (x - x_i-1) / (x_i - x_i-1)
	 * 
	 * @param etsp Exponential Tension Basis set Builder Parameters
	 * 
	 * @return Hyperbolic Tension Basis Set
	 */

	public static final org.drip.spline.basis.FunctionSet HyperbolicTensionBasisSet (
		final org.drip.spline.basis.ExponentialTensionSetParams etsp)
	{
		if (null == etsp) return null;

		double dblTension = etsp.tension();

		try {
			return new org.drip.spline.basis.FunctionSet (new org.drip.function.definition.R1ToR1[]
				{new org.drip.function.r1tor1.Polynomial (0), new org.drip.function.r1tor1.Polynomial (1),
					new org.drip.function.r1tor1.HyperbolicTension
						(org.drip.function.r1tor1.HyperbolicTension.COSH, dblTension), new
							org.drip.function.r1tor1.HyperbolicTension
								(org.drip.function.r1tor1.HyperbolicTension.SINH, dblTension)});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * This function implements the elastic coefficients for the segment using polynomial basis splines
	 * 		inside [0,...,1) - Globally [x_0,...,x_1):
	 * 
	 * 			y = Sum (A_i*x^i) i = 0,...,n (0 and n inclusive)
	 * 
	 *		where x is the normalized ordinate mapped as
	 * 
	 * 			x .gte. (x - x_i-1) / (x_i - x_i-1)
	 * 
	 * @param pfsp Polynomial Basis set Builder Parameters
	 * 
	 * @return The Polynomial Basis Spline Set
	 */

	public static final org.drip.spline.basis.FunctionSet PolynomialBasisSet (
		final org.drip.spline.basis.PolynomialFunctionSetParams pfsp)
	{
		if (null == pfsp) return null;

		int iNumBasis = pfsp.numBasis();

		org.drip.function.definition.R1ToR1[] aAU = new
			org.drip.function.definition.R1ToR1[iNumBasis];

		try {
			for (int i = 0; i < iNumBasis; ++i)
				aAU[i] = new org.drip.function.r1tor1.Polynomial (i);

			return new org.drip.spline.basis.FunctionSet (aAU);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * This function implements the elastic coefficients for the segment using Bernstein polynomial basis
	 * 	splines inside - [0,...,1) - Globally [x_0,...,x_1):
	 * 
	 * 			y = Sum (A_i*B^i(x)) i = 0,...,n (0 and n inclusive)
	 * 
	 *		where x is the normalized ordinate mapped as
	 * 
	 * 			x .gte. (x - x_i-1) / (x_i - x_i-1)
	 * 
	 * 		and B^i(x) is the Bernstein basis polynomial of order i.
	 * 
	 * @param pfsp Polynomial Basis set Builder Parameters
	 * 
	 * @return The Bernstein polynomial basis
	 */

	public static final org.drip.spline.basis.FunctionSet BernsteinPolynomialBasisSet (
		final org.drip.spline.basis.PolynomialFunctionSetParams pfsp)
	{
		if (null == pfsp) return null;

		int iNumBasis = pfsp.numBasis();

		org.drip.function.definition.R1ToR1[] aAU = new
			org.drip.function.definition.R1ToR1[iNumBasis];

		try {
			for (int i = 0; i < iNumBasis; ++i)
				aAU[i] = new org.drip.function.r1tor1.BernsteinPolynomial (i, iNumBasis - 1 - i);

			return new org.drip.spline.basis.FunctionSet (aAU);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct KaklisPandelis from the polynomial tension basis function set
	 * 
	 * 		y = A * (1-x) + B * x + C * x * (1-x)^m + D * x^m * (1-x)
	 * 
	 * @param kpsp Kaklis Pandelis Basis set Builder Parameters
	 * 
	 * @return The KaklisPandelis Basis Set
	 */

	public static final org.drip.spline.basis.FunctionSet KaklisPandelisBasisSet (
		final org.drip.spline.basis.KaklisPandelisSetParams kpsp)
	{
		if (null == kpsp) return null;

		try {
			org.drip.function.definition.R1ToR1 auLinearPoly = new org.drip.function.r1tor1.Polynomial
				(1);

			org.drip.function.definition.R1ToR1 auReflectedLinearPoly = new
				org.drip.function.r1tor1.UnivariateReflection (auLinearPoly);

			org.drip.function.definition.R1ToR1 auKaklisPandelisPolynomial = new
				org.drip.function.r1tor1.Polynomial (kpsp.polynomialTensionDegree());

			return new org.drip.spline.basis.FunctionSet (new org.drip.function.definition.R1ToR1[]
				{auReflectedLinearPoly, auLinearPoly, new org.drip.function.r1tor1.UnivariateConvolution
					(auLinearPoly, new org.drip.function.r1tor1.UnivariateReflection
						(auKaklisPandelisPolynomial)), new org.drip.function.r1tor1.UnivariateConvolution
							(auKaklisPandelisPolynomial, auReflectedLinearPoly)});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Exponential Rational Basis Set
	 * 
	 * 		y = A + B / (1+x) + C * exp(-x) + D * exp(-x) / (1+x)
	 * 
	 * @param ersp Exponential Rational Basis set Parameters
	 * 
	 * @return The Exponential Rational Basis Set
	 */

	public static final org.drip.spline.basis.FunctionSet ExponentialRationalBasisSet (
		final org.drip.spline.basis.ExponentialRationalSetParams ersp)
	{
		if (null == ersp) return null;

		try {
			org.drip.function.definition.R1ToR1 auLinearPoly = new org.drip.function.r1tor1.Polynomial
				(0);

			org.drip.function.definition.R1ToR1 auLRSC = new
				org.drip.function.r1tor1.LinearRationalShapeControl (ersp.rationalTension());

			org.drip.function.definition.R1ToR1 auET = new
				org.drip.function.r1tor1.ExponentialTension (java.lang.Math.E, -ersp.exponentialTension());

			org.drip.function.definition.R1ToR1 auLRET = new
				org.drip.function.r1tor1.LinearRationalTensionExponential (-ersp.exponentialTension(),
					ersp.rationalTension());

			return new org.drip.spline.basis.FunctionSet (new org.drip.function.definition.R1ToR1[]
				{auLinearPoly, auLRSC, auET, auLRET});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Exponential Mixture Basis Set
	 * 
	 * 		y = A + B * exp(-l_1 * x) + C * exp(-l_2 * x) + D * exp(-l_3 * x)
	 * 
	 * @param emsp Exponential Mixture Basis set Parameters
	 * 
	 * @return The Exponential Mixture Basis Set
	 */

	public static final org.drip.spline.basis.FunctionSet ExponentialMixtureBasisSet (
		final org.drip.spline.basis.ExponentialMixtureSetParams emsp)
	{
		if (null == emsp) return null;

		try {
			org.drip.function.definition.R1ToR1 auLinearPoly = new
				org.drip.function.r1tor1.Polynomial (0);

			org.drip.function.definition.R1ToR1 auExp1 = new
				org.drip.function.r1tor1.ExponentialTension (java.lang.Math.E, -emsp.tension (0));

			org.drip.function.definition.R1ToR1 auExp2 = new
				org.drip.function.r1tor1.ExponentialTension (java.lang.Math.E, -emsp.tension (1));

			org.drip.function.definition.R1ToR1 auExp3 = new
				org.drip.function.r1tor1.ExponentialTension (java.lang.Math.E, -emsp.tension (2));

			return new org.drip.spline.basis.FunctionSet (new org.drip.function.definition.R1ToR1[]
				{auLinearPoly, auExp1, auExp2, auExp3});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the BSpline Basis Function Set
	 * 
	 * @param bssp BSpline Basis Set Parameters
	 * 
	 * @return The BSpline Basis Function Set
	 */

	public static final org.drip.spline.basis.FunctionSet BSplineBasisSet (
		final org.drip.spline.basis.BSplineSequenceParams bssp)
	{
		if (null == bssp) return null;

		org.drip.spline.bspline.SegmentBasisFunction[] aSBF =
			org.drip.spline.bspline.SegmentBasisFunctionGenerator.MonicSequence (bssp.hat(),
				bssp.shapeControl(), bssp.predictorOrdinates(), bssp.procBasisDerivOrder(), bssp.tension());

		if (null == aSBF || bssp.numBasis() >= aSBF.length) return null;

		int iBSplineOrder = bssp.bSplineOrder();

		try {
			return new org.drip.spline.bspline.SegmentBasisFunctionSet (bssp.numBasis(), bssp.tension(), 2 ==
				iBSplineOrder ? aSBF : org.drip.spline.bspline.SegmentBasisFunctionGenerator.MulticSequence
					(iBSplineOrder, aSBF));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Argument Array
	 * 
	 * @throws java.lang.Exception Propagate Exception Encountered
	 */

	public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		org.drip.spline.basis.BSplineSequenceParams bssp = new org.drip.spline.basis.BSplineSequenceParams
			(org.drip.spline.bspline.BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC,
				org.drip.spline.bspline.BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR, 2, 4, 1., -1);

		org.drip.numerical.common.NumberUtil.Print1DArray ("BSSP", bssp.predictorOrdinates(), false);

		org.drip.spline.basis.FunctionSet fsBSS = BSplineBasisSet (bssp);

		System.out.println ("fsBSS Size = " + fsBSS.numBasis());
	}
}
