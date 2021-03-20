
package org.drip.spline.tension;

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
 * <i>KochLycheKvasovFamily</i> implements the basic framework and the family of C2 Tension Splines outlined
 * in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers. Functions exposed here
 * implement the Basis Function Set from:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 * 			Hyperbolic Hat Primitive Set
 *  	</li>
 *  	<li>
 * 			Cubic Polynomial Numerator and Linear Rational Denominator
 *  	</li>
 *  	<li>
 * 			Cubic Polynomial Numerator and Quadratic Rational Denominator
 *  	</li>
 *  	<li>
 * 			Cubic Polynomial Numerator and Exponential Denominator
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/tension/README.md">Koch Lyche Kvasov Tension Splines</a></li>
 *  </ul>
 * <br><br>
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblBegin) ||
					!org.drip.numerical.common.NumberUtil.IsValid (dblEnd))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblBegin) ||
					!org.drip.numerical.common.NumberUtil.IsValid (dblEnd))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		org.drip.function.definition.R1ToR1 auPsy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		org.drip.function.definition.R1ToR1 auPsy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		org.drip.function.definition.R1ToR1 auPsy = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (dblX))
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
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
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
