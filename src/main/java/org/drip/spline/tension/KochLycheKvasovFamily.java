
package org.drip.spline.tension;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.r1integration.Integrator;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.basis.FunctionSet;
import org.drip.spline.bspline.SegmentBasisFunctionSet;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 *  <br>
 *  <ul>
 *  	<li>Hyperbolic Hat Primitive Set</li>
 *  	<li>Cubic Polynomial Numerator and Linear Rational Denominator</li>
 *  	<li>Cubic Polynomial Numerator and Quadratic Rational Denominator</li>
 *  	<li>Cubic Polynomial Numerator and Exponential Denominator</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/tension/README.md">Koch Lyche Kvasov Tension Splines</a></td></tr>
 *  </table>
 *  <br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class KochLycheKvasovFamily
{

	/**
	 * Implement the Basis Function Set from the Hyperbolic Hat Primitive Set
	 * 
	 * @param exponentialTensionSetParams The Tension Function Set Parameters
	 * 
	 * @return Instance of the Basis Function Set
	 */

	public static final FunctionSet FromHyperbolicPrimitive (
		final ExponentialTensionSetParams exponentialTensionSetParams)
	{
		if (null == exponentialTensionSetParams) {
			return null;
		}

		R1ToR1 tensionBasisPhy = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception ("KLKF::FromHyperbolicPrimitive.Phy::evaluate => Invalid Inputs!");
				}

				double tension = exponentialTensionSetParams.tension();

				return (Math.sinh (tension * x) - tension * x) / (tension * tension * Math.sinh (tension));
			}

			@Override public double derivative (
				final double x,
				final int order)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception ("KLKF::FromHyperbolicPrimitive.Phy::derivative => Invalid Inputs!");
				}

				double tension = exponentialTensionSetParams.tension();

				if (1 == order) {
					return (Math.cosh (tension * x) - 1.) / (tension * Math.sinh (tension));
				}

				if (2 == order) {
					return Math.sinh (tension * x) / Math.sinh (tension);
				}

				return derivative (x, order);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws Exception
			{
				if (!NumberUtil.IsValid (begin) || !NumberUtil.IsValid (end)) {
					throw new Exception ("KLKF::FromHyperbolicPrimitive.Phy::integrate => Invalid Inputs");
				}

				double tension = exponentialTensionSetParams.tension();

				return (
					Math.cosh (tension * end) - Math.cosh (tension * begin) - 0.5 * tension * (
						end * end - begin * begin
					)
				) / (tension * tension * tension * java.lang.Math.sinh (tension));
			}
		};

		R1ToR1 tensionBasisPsy = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception ("KLKF.Psy::FromHyperbolicPrimitive::evaluate => Invalid Inputs!");
				}

				double tension = exponentialTensionSetParams.tension();

				return (Math.sinh (tension * (1. - x)) - tension * (1. - x)) /
					(tension * tension * Math.sinh (tension));
			}

			@Override public double derivative (
				final double x,
				final int order)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception ("KLKF::FromHyperbolicPrimitive.Psy::derivative => Invalid Inputs!");
				}

				double tension = exponentialTensionSetParams.tension();

				if (1 == order) {
					return (1. - Math.cosh (tension * (1. - x))) / (tension * Math.cosh (tension));
				}

				if (2 == order) {
					return Math.sinh (tension * (1. - x)) / Math.sinh (tension);
				}

				return derivative (x, order);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws java.lang.Exception
			{
				if (!NumberUtil.IsValid (begin) || !NumberUtil.IsValid (end)) {
					throw new Exception ("KLKF::FromHyperbolicPrimitive.Psy::integrate => Invalid Inputs");
				}

				double tension = exponentialTensionSetParams.tension();

				return -1. * (
					Math.sinh (tension * (1. - end)) - Math.sinh  (tension * (1. - begin)) -
						0.5 * tension * ((1. - end) * (1. - end) - (1. - begin) * (1. - begin))
					) / (tension * tension * tension * Math.sinh (tension));
			}
		};

		try {
			return new SegmentBasisFunctionSet (
				2,
				exponentialTensionSetParams.tension(),
				new R1ToR1[] {tensionBasisPhy, tensionBasisPsy}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Implement the Basis Function Set from the Cubic Polynomial Numerator and Linear Rational Denominator
	 * 
	 * @param exponentialTensionSetParams The Tension Function Set Parameters
	 * 
	 * @return Instance of the Basis Function Set
	 */

	public static final FunctionSet FromRationalLinearPrimitive (
		final ExponentialTensionSetParams exponentialTensionSetParams)
	{
		if (null == exponentialTensionSetParams) {
			return null;
		}

		R1ToR1 tensionPhyFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception ("KLKF::FromRationalLinearPrimitive.Phy::evaluate => Invalid Inputs!");
				}

				double tension = exponentialTensionSetParams.tension();

				return x * x * x / (1. + tension * (1. - x)) / (6. + 8. * tension);
			}

			@Override public double derivative (
				final double x,
				final int order)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception (
						"KLKF::FromRationalLinearPrimitive.Phy::derivative => Invalid Inputs!"
					);
				}

				double tension = exponentialTensionSetParams.tension();

				if (1 == order) {
					double dblDLDX = -1. * tension;
					double dblL = 1. + tension * (1. - x);

					return 1. /
						(dblL * dblL * (6. + 8. * tension)) * (3. * dblL * x * x - dblDLDX * x * x * x);
				}

				if (2 == order) {
					double d2ldx2 = 0.;
					double l = 1. + tension * (1. - x);

					return 1. / (l * l * (6. + 8. * tension)) * (6. * l * x - d2ldx2 * x * x * x) - 2. /
						(l * l * l * (6. + 8. * tension)) * (3. * l * x * x + tension * x * x * x);
				}

				return derivative (x, order);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws Exception
			{
				return Integrator.Boole (this, begin, end);
			}
		};

		R1ToR1 tensionPsyFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception (
						"KLKF::FromRationalLinearPrimitive.Psy::evaluate => Invalid Inputs!"
					);
				}

				double tension = exponentialTensionSetParams.tension();

				return (1. - x) * (1. - x) * (1. - x) / (1. + tension * x) / (6. + 8. * tension);
			}

			@Override public double derivative (
				final double x,
				final int order)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception (
						"KLKF::FromRationalLinearPrimitive.Psy::derivative => Invalid Inputs!"
					);
				}

				double tension = exponentialTensionSetParams.tension();

				if (1 == order) {
					double l = 1. + tension * x;

					return -1. / (l * l * (6. + 8. * tension)) * (
						3. * l * (1. - x) * (1. - x) + tension * (1. - x) * (1. - x) * (1. - x)
					);
				}

				if (2 == order) {
					double d2ldx2 = 0.;
					double dldx = tension;
					double l = 1. + tension * x;

					return 1. / (l * l * (6. + 8. * tension)) * (
						6. * l * (1. - x) - d2ldx2 * (1. - x) * (1. - x) * (1. - x)
					) - 2. / (l * l * l * (6. + 8. * tension)) * (
						3. * l * (1. - x) * (1. - x) + dldx * (1. - x) * (1. - x) * (1. - x)
					);
				}

				return derivative (x, order);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws Exception
			{
				return Integrator.Boole (this, begin, end);
			}
		};

		try {
			return new SegmentBasisFunctionSet (
				2,
				exponentialTensionSetParams.tension(),
				new R1ToR1[] {tensionPhyFunction, tensionPsyFunction}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Implement the Basis Function Set from the Cubic Polynomial Numerator and Quadratic Rational
	 *  Denominator
	 * 
	 * @param exponentialTensionSetParams The Tension Function Set Parameters
	 * 
	 * @return Instance of the Basis Function Set
	 */

	public static final FunctionSet FromRationalQuadraticPrimitive (
		final ExponentialTensionSetParams exponentialTensionSetParams)
	{
		if (null == exponentialTensionSetParams) {
			return null;
		}

		R1ToR1 phyBasisFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception (
						"KLKF::FromRationalQuadraticPrimitive.Phy::evaluate => Invalid Inputs!"
					);
				}

				double tension = exponentialTensionSetParams.tension();

				return x * x * x / (1. + tension * x * (1. - x)) / (6. + 8. * tension);
			}

			@Override public double derivative (
				final double x,
				final int order)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception (
						"KLKF::FromRationalQuadraticPrimitive.Phy::derivative => Invalid Inputs!"
					);
				}

				double tension = exponentialTensionSetParams.tension();

				if (1 == order) {
					double dldx = tension * (1. - 2. * x);
					double l = 1. + tension * x * (1. - x);
					return 1. / (l * l * (6. + 8. * tension)) * (3. * l * x * x - dldx * x * x * x);
				}

				if (2 == order) {
					double d2ldx2 = -2. * tension;
					double dldx = tension * (1. - 2. * x);
					double l = 1. + tension * x * (1. - x);

					return 1. / (l * l * (6. + 8. * tension)) * (6. * l * x - d2ldx2 * x * x * x) -
						2. / (l * l * l * (6. + 8. * tension)) * (3. * l * x * x - dldx * x * x * x);
				}

				return derivative (x, order);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws Exception
			{
				return Integrator.Boole (this, begin, end);
			}
		};

		R1ToR1 psyBasisFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception (
						"KLKF::FromRationalQuadraticPrimitive.Psy::evaluate => Invalid Inputs!"
					);
				}

				double tension = exponentialTensionSetParams.tension();

				return (1. - x) * (1. - x) * (1. - x) / (1. + tension * x * (1. - x)) / (6. + 8. * tension);
			}

			@Override public double derivative (
				final double x,
				final int order)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception (
						"KLKF::FromRationalQuadraticPrimitive.Psy::derivative => Invalid Inputs!"
					);
				}

				double tension = exponentialTensionSetParams.tension();

				if (1 == order) {
					double dldx = tension * (1. - 2. * x);
					double l = 1. + tension * x * (1. - x);
					return -1. / (l * l * (6. + 8. * tension)) * (
						3. * l * (1. - x) * (1. - x) + dldx * (1. - x) * (1. - x) * (1. - x)
					);
				}

				if (2 == order) {
					double d2ldx2 = -2. * tension * x;
					double dldx = tension * (1. - 2. * x);
					double l = 1. + tension * x * (1. - x);

					return 1. / (l * l * (6. + 8. * tension)) * (
						6. * l * (1. - x) - d2ldx2 * (1. - x) * (1. - x) * (1. - x)
					) - 2. / (l * l * l * (6. + 8. * tension)) * (
						3. * l * (1. - x) * (1. - x) + dldx * (1. - x) * (1. - x) * (1. - x)
					);
				}

				return derivative (x, order);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws Exception
			{
				return Integrator.Boole (this, begin, end);
			}
		};

		try {
			return new SegmentBasisFunctionSet (
				2,
				exponentialTensionSetParams.tension(),
				new R1ToR1[] {phyBasisFunction, psyBasisFunction}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Implement the Basis Function Set from the Cubic Polynomial Numerator and Exponential Denominator
	 * 
	 * @param exponentialTensionSetParams The Tension Function Set Parameters
	 * 
	 * @return Instance of the Basis Function Set
	 */

	public static final FunctionSet FromExponentialPrimitive (
		final ExponentialTensionSetParams exponentialTensionSetParams)
	{
		if (null == exponentialTensionSetParams) {
			return null;
		}

		R1ToR1 phyBasisFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception ("KLKF::FromExponentialPrimitive.Phy::evaluate => Invalid Inputs!");
				}

				double tension = exponentialTensionSetParams.tension();

				return x * x * x * Math.exp (-1. * tension * (1. - x)) / (6. + 7. * tension);
			}

			@Override public double derivative (
				final double x,
				final int order)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception ("KLKF::FromExponentialPrimitive.Phy::derivative => Invalid Inputs!");
				}

				double tension = exponentialTensionSetParams.tension();

				if (1 == order) {
					return (3. + tension * x) /
						(6. + 7. * tension) * x * x * Math.exp (-1. * tension * (1. - x));
				}

				if (2 == order) {
					return (tension * tension * x * x + 6. * tension * x + 6.) / (6. + 7. * tension) * x *
						Math.exp (-1. * tension * (1. - x));
				}

				return derivative (x, order);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws Exception
			{
				return Integrator.Boole (this, begin, end);
			}
		};

		R1ToR1 psyBasisFunction = new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception ("KLKF::FromExponentialPrimitive.Psy::evaluate => Invalid Inputs!");
				}

				double tension = exponentialTensionSetParams.tension();

				return (1. - x) * (1. - x) * (1. - x) * Math.exp (-1. * tension * x) / (6. + 7. * tension);
			}

			@Override public double derivative (
				final double x,
				final int order)
				throws Exception
			{
				if (!NumberUtil.IsValid (x)) {
					throw new Exception (
						"KLKF::FromExponentialPrimitive.Psy::derivative => Invalid Inputs!"
					);
				}

				double tension = exponentialTensionSetParams.tension();

				if (1 == order) {
					return -1. * (3. + tension * (1. - x)) / (6. + 7. * tension) * (1. - x) *
						(1. - x) * Math.exp (-1. * tension * x);
				}

				if (2 == order) {
					return (tension * tension * (1. - x) * (1. - x) + 6. * tension * (1. - x) + 6.) /
						(6. + 7. * tension) * (1. - x) * Math.exp (-1. * tension * x);
				}

				return derivative (x, order);
			}

			@Override public double integrate (
				final double begin,
				final double end)
				throws Exception
			{
				return Integrator.Boole (this, begin, end);
			}
		};

		try {
			return new SegmentBasisFunctionSet (
				2,
				exponentialTensionSetParams.tension(),
				new R1ToR1[] {phyBasisFunction, psyBasisFunction}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
