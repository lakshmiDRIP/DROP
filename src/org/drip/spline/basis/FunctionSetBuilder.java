
package org.drip.spline.basis;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * This class implements the basis set and spline builder for the following types of splines:
 * 
 * 	- Exponential basis tension splines
 * 	- Hyperbolic basis tension splines
 * 	- Polynomial basis splines
 *  - Bernstein Polynomial basis splines
 *  - Kaklis Pandelis basis tension splines
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

	public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		org.drip.spline.basis.BSplineSequenceParams bssp = new org.drip.spline.basis.BSplineSequenceParams
			(org.drip.spline.bspline.BasisHatPairGenerator.RAW_TENSION_HYPERBOLIC,
				org.drip.spline.bspline.BasisHatShapeControl.SHAPE_CONTROL_RATIONAL_LINEAR, 2, 4, 1., -1);

		org.drip.quant.common.NumberUtil.Print1DArray ("BSSP", bssp.predictorOrdinates(), false);

		org.drip.spline.basis.FunctionSet fsBSS = BSplineBasisSet (bssp);

		System.out.println ("fsBSS Size = " + fsBSS.numBasis());
	}
}
