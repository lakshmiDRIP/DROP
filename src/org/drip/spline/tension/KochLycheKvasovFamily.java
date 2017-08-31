
package org.drip.spline.tension;

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
 * This class implements the basic framework and the family of C2 Tension Splines outlined in Koch and Lyche
 * 	(1989), Koch and Lyche (1993), and Kvasov (2000) Papers.
 * 
 * Functions exposed here implement the Basis Function Set from:
 * 	- Hyperbolic Hat Primitive Set
 * 	- Cubic Polynomial Numerator and Linear Rational Denominator
 * 	- Cubic Polynomial Numerator and Quadratic Rational Denominator
 * 	- Cubic Polynomial Numerator and Exponential Denominator
 * 
 * @author Lakshmi Krishnamurthy
 */

public class KochLycheKvasovFamily {

	/**
	 * Implement the Basis Function Set from the Hyperbolic Hat Primitive Set
	 * 
	 * @param etsp The Tension Function Set Parameters
	 * 
	 * @return Instance of the Basis Function Set
	 */

	public static final org.drip.spline.basis.FunctionSet FromHyperbolicPrimitive (
		final org.drip.spline.basis.ExponentialTensionSetParams etsp)
	{
		if (null == etsp) return null;

		org.drip.function.definition.R1ToR1 auPhy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromHyperbolicPrimitive.Phy::evaluate => Invalid Inputs!");

				double dblTension = etsp.tension();

				return (java.lang.Math.sinh (dblTension * dblX) - dblTension * dblX) / (dblTension *
					dblTension * java.lang.Math.sinh (dblTension));
			}

			@Override public double derivative (
				final double dblX,
				final int iOrder)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromHyperbolicPrimitive.Phy::derivative => Invalid Inputs!");

				double dblTension = etsp.tension();

				if (1 == iOrder)
					return (java.lang.Math.cosh (dblTension * dblX) - 1.) / (dblTension * java.lang.Math.sinh
						(dblTension));

				if (2 == iOrder)
					return java.lang.Math.sinh (dblTension * dblX) / java.lang.Math.sinh (dblTension);

				return derivative (dblX, iOrder);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblEnd))
					throw new java.lang.Exception
						("KLKF::FromHyperbolicPrimitive.Phy::integrate => Invalid Inputs");

				double dblTension = etsp.tension();

				return (java.lang.Math.cosh (dblTension * dblEnd) - java.lang.Math.cosh (dblTension *
					dblBegin) - 0.5 * dblTension * (dblEnd * dblEnd - dblBegin * dblBegin)) / (dblTension *
						dblTension * dblTension * java.lang.Math.sinh (dblTension));
			}
		};

		org.drip.function.definition.R1ToR1 auPsy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF.Psy::FromHyperbolicPrimitive::evaluate => Invalid Inputs!");

				double dblTension = etsp.tension();

				return (java.lang.Math.sinh (dblTension * (1. - dblX)) - dblTension * (1. - dblX)) /
					(dblTension * dblTension * java.lang.Math.sinh (dblTension));
			}

			@Override public double derivative (
				final double dblX,
				final int iOrder)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromHyperbolicPrimitive.Psy::derivative => Invalid Inputs!");

				double dblTension = etsp.tension();

				if (1 == iOrder)
					return (1. - java.lang.Math.cosh (dblTension * (1. - dblX))) / (dblTension *
						java.lang.Math.cosh (dblTension));

				if (2 == iOrder)
					return java.lang.Math.sinh (dblTension * (1. - dblX)) / java.lang.Math.sinh (dblTension);

				return derivative (dblX, iOrder);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblEnd))
					throw new java.lang.Exception
						("KLKF::FromHyperbolicPrimitive.Psy::integrate => Invalid Inputs");

				double dblTension = etsp.tension();

				return -1. * (java.lang.Math.sinh (dblTension * (1. - dblEnd)) - java.lang.Math.sinh 
					(dblTension * (1. - dblBegin)) - 0.5 * dblTension * ((1. - dblEnd) * (1. - dblEnd) - (1.
						- dblBegin) * (1. - dblBegin))) / (dblTension * dblTension * dblTension *
							java.lang.Math.sinh (dblTension));
			}
		};

		try {
			return new org.drip.spline.bspline.SegmentBasisFunctionSet (2, etsp.tension(), new
				org.drip.function.definition.R1ToR1[] {auPhy, auPsy});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Implement the Basis Function Set from the Cubic Polynomial Numerator and Linear Rational Denominator
	 * 
	 * @param etsp The Tension Function Set Parameters
	 * 
	 * @return Instance of the Basis Function Set
	 */

	public static final org.drip.spline.basis.FunctionSet FromRationalLinearPrimitive (
		final org.drip.spline.basis.ExponentialTensionSetParams etsp)
	{
		if (null == etsp) return null;

		org.drip.function.definition.R1ToR1 auPhy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromRationalLinearPrimitive.Phy::evaluate => Invalid Inputs!");

				double dblTension = etsp.tension();

				return dblX * dblX * dblX / (1. + dblTension * (1. - dblX)) / (6. + 8. * dblTension);
			}

			@Override public double derivative (
				final double dblX,
				final int iOrder)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromRationalLinearPrimitive.Phy::derivative => Invalid Inputs!");

				double dblTension = etsp.tension();

				if (1 == iOrder) {
					double dblDLDX = -1. * dblTension;
					double dblL = 1. + dblTension * (1. - dblX);

					return 1. / (dblL * dblL * (6. + 8. * dblTension)) * (3. * dblL * dblX * dblX - dblDLDX *
						dblX * dblX * dblX);
				}

				if (2 == iOrder) {
					double dblD2LDX2 = 0.;
					double dblDLDX = -1. * dblTension;
					double dblL = 1. + dblTension * (1. - dblX);

					return 1. / (dblL * dblL * (6. + 8. * dblTension)) * (6. * dblL * dblX - dblD2LDX2 * dblX
						* dblX * dblX) - 2. / (dblL * dblL * dblL * (6. + 8. * dblTension)) *
							(3. * dblL * dblX * dblX - dblDLDX * dblX * dblX * dblX);
				}

				return derivative (dblX, iOrder);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		org.drip.function.definition.R1ToR1 auPsy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromRationalLinearPrimitive.Psy::evaluate => Invalid Inputs!");

				double dblTension = etsp.tension();

				return (1. - dblX) * (1. - dblX) * (1. - dblX) / (1. + dblTension * dblX) / (6. + 8. *
					dblTension);
			}

			@Override public double derivative (
				final double dblX,
				final int iOrder)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromRationalLinearPrimitive.Psy::derivative => Invalid Inputs!");

				double dblTension = etsp.tension();

				if (1 == iOrder) {
					double dblDLDX = dblTension;
					double dblL = 1. + dblTension * dblX;

					return -1. / (dblL * dblL * (6. + 8. * dblTension)) * (3. * dblL * (1. - dblX) *
						(1. - dblX) + dblDLDX * (1. - dblX) * (1. - dblX) * (1. - dblX));
				}

				if (2 == iOrder) {
					double dblD2LDX2 = 0.;
					double dblDLDX = dblTension;
					double dblL = 1. + dblTension * dblX;

					return 1. / (dblL * dblL * (6. + 8. * dblTension)) * (6. * dblL * (1. - dblX) - dblD2LDX2
						* (1. - dblX) * (1. - dblX) * (1. - dblX)) - 2. / (dblL * dblL * dblL *
							(6. + 8. * dblTension)) * (3. * dblL * (1. - dblX) * (1. - dblX) + dblDLDX *
								(1. - dblX) * (1. - dblX) * (1. - dblX));
				}

				return derivative (dblX, iOrder);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		try {
			return new org.drip.spline.bspline.SegmentBasisFunctionSet (2, etsp.tension(), new
				org.drip.function.definition.R1ToR1[] {auPhy, auPsy});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Implement the Basis Function Set from the Cubic Polynomial Numerator and Quadratic Rational
	 *  Denominator
	 * 
	 * @param etsp The Tension Function Set Parameters
	 * 
	 * @return Instance of the Basis Function Set
	 */

	public static final org.drip.spline.basis.FunctionSet FromRationalQuadraticPrimitive (
		final org.drip.spline.basis.ExponentialTensionSetParams etsp)
	{
		if (null == etsp) return null;

		org.drip.function.definition.R1ToR1 auPhy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromRationalQuadraticPrimitive.Phy::evaluate => Invalid Inputs!");

				double dblTension = etsp.tension();

				return dblX * dblX * dblX / (1. + dblTension * dblX * (1. - dblX)) / (6. + 8. * dblTension);
			}

			@Override public double derivative (
				final double dblX,
				final int iOrder)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromRationalQuadraticPrimitive.Phy::derivative => Invalid Inputs!");

				double dblTension = etsp.tension();

				if (1 == iOrder) {
					double dblDLDX = dblTension * (1. - 2. * dblX);
					double dblL = 1. + dblTension * dblX * (1. - dblX);

					return 1. / (dblL * dblL * (6. + 8. * dblTension)) * (3. * dblL * dblX * dblX - dblDLDX *
						dblX * dblX * dblX);
				}

				if (2 == iOrder) {
					double dblD2LDX2 = -2. * dblTension;
					double dblDLDX = dblTension * (1. - 2. * dblX);
					double dblL = 1. + dblTension * dblX * (1. - dblX);

					return 1. / (dblL * dblL * (6. + 8. * dblTension)) * (6. * dblL * dblX - dblD2LDX2 * dblX
						* dblX * dblX) - 2. / (dblL * dblL * dblL * (6. + 8. * dblTension)) *
							(3. * dblL * dblX * dblX - dblDLDX * dblX * dblX * dblX);
				}

				return derivative (dblX, iOrder);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		org.drip.function.definition.R1ToR1 auPsy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromRationalQuadraticPrimitive.Psy::evaluate => Invalid Inputs!");

				double dblTension = etsp.tension();

				return (1. - dblX) * (1. - dblX) * (1. - dblX) / (1. + dblTension * dblX * (1. - dblX)) / (6.
					+ 8. * dblTension);
			}

			@Override public double derivative (
				final double dblX,
				final int iOrder)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromRationalQuadraticPrimitive.Psy::derivative => Invalid Inputs!");

				double dblTension = etsp.tension();

				if (1 == iOrder) {
					double dblDLDX = dblTension * (1. - 2. * dblX);
					double dblL = 1. + dblTension * dblX * (1. - dblX);

					return -1. / (dblL * dblL * (6. + 8. * dblTension)) * (3. * dblL * (1. - dblX) *
						(1. - dblX) + dblDLDX * (1. - dblX) * (1. - dblX) * (1. - dblX));
				}

				if (2 == iOrder) {
					double dblD2LDX2 = -2. * dblTension * dblX;
					double dblDLDX = dblTension * (1. - 2. * dblX);
					double dblL = 1. + dblTension * dblX * (1. - dblX);

					return 1. / (dblL * dblL * (6. + 8. * dblTension)) * (6. * dblL * (1. - dblX) - dblD2LDX2
						* (1. - dblX) * (1. - dblX) * (1. - dblX)) - 2. / (dblL * dblL * dblL *
							(6. + 8. * dblTension)) * (3. * dblL * (1. - dblX) * (1. - dblX) + dblDLDX *
								(1. - dblX) * (1. - dblX) * (1. - dblX));
				}

				return derivative (dblX, iOrder);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		try {
			return new org.drip.spline.bspline.SegmentBasisFunctionSet (2, etsp.tension(), new
				org.drip.function.definition.R1ToR1[] {auPhy, auPsy});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Implement the Basis Function Set from the Cubic Polynomial Numerator and Exponential Denominator
	 * 
	 * @param etsp The Tension Function Set Parameters
	 * 
	 * @return Instance of the Basis Function Set
	 */

	public static final org.drip.spline.basis.FunctionSet FromExponentialPrimitive (
		final org.drip.spline.basis.ExponentialTensionSetParams etsp)
	{
		if (null == etsp) return null;

		org.drip.function.definition.R1ToR1 auPhy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromExponentialPrimitive.Phy::evaluate => Invalid Inputs!");

				double dblTension = etsp.tension();

				return dblX * dblX * dblX * java.lang.Math.exp (-1. * dblTension * (1. - dblX)) / (6. + 7. *
					dblTension);
			}

			@Override public double derivative (
				final double dblX,
				final int iOrder)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromExponentialPrimitive.Phy::derivative => Invalid Inputs!");

				double dblTension = etsp.tension();

				if (1 == iOrder)
					return (3. + dblTension * dblX) / (6. + 7. * dblTension) * dblX * dblX *
						java.lang.Math.exp (-1. * dblTension * (1. - dblX));

				if (2 == iOrder)
					return (dblTension * dblTension * dblX * dblX + 6. * dblTension * dblX + 6.) / (6. + 7. *
						dblTension) * dblX * java.lang.Math.exp (-1. * dblTension * (1. - dblX));

				return derivative (dblX, iOrder);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		org.drip.function.definition.R1ToR1 auPsy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromExponentialPrimitive.Psy::evaluate => Invalid Inputs!");

				double dblTension = etsp.tension();

				return (1. - dblX) * (1. - dblX) * (1. - dblX) * java.lang.Math.exp (-1. * dblTension * dblX)
					/ (6. + 7. * dblTension);
			}

			@Override public double derivative (
				final double dblX,
				final int iOrder)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
					throw new java.lang.Exception
						("KLKF::FromExponentialPrimitive.Psy::derivative => Invalid Inputs!");

				double dblTension = etsp.tension();

				if (1 == iOrder)
					return -1. * (3. + dblTension * (1. - dblX)) / (6. + 7. * dblTension) * (1. - dblX) *
						(1. - dblX) * java.lang.Math.exp (-1. * dblTension * dblX);

				if (2 == iOrder)
					return (dblTension * dblTension * (1. - dblX) * (1. - dblX) + 6. * dblTension *
						(1. - dblX) + 6.) / (6. + 7. * dblTension) * (1. - dblX) * java.lang.Math.exp (-1. *
							dblTension * dblX);

				return derivative (dblX, iOrder);
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		try {
			return new org.drip.spline.bspline.SegmentBasisFunctionSet (2, etsp.tension(), new
				org.drip.function.definition.R1ToR1[] {auPhy, auPsy});
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
