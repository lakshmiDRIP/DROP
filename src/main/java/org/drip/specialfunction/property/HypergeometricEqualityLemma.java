
package org.drip.specialfunction.property;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R1ToR1Property;
import org.drip.function.definition.R2ToR1;
import org.drip.function.definition.R2ToR1Property;
import org.drip.function.definition.R3ToR1;
import org.drip.function.definition.R3ToR1Property;
import org.drip.numerical.common.NumberUtil;
import org.drip.specialfunction.beta.IncompleteIntegrandEstimator;
import org.drip.specialfunction.beta.LogGammaEstimator;
import org.drip.specialfunction.definition.HypergeometricParameters;
import org.drip.specialfunction.gamma.WindschitlTothAnalytic;
import org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator;

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
 * <i>HypergeometricEqualityLemma</i> verifies the Hyper-geometric Equality Lemma Properties. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Gessel, I., and D. Stanton (1982): Strange Evaluations of Hyper-geometric Series <i>SIAM Journal
 * 				on Mathematical Analysis</i> <b>13 (2)</b> 295-308
 * 		</li>
 * 		<li>
 * 			Koepf, W (1995): Algorithms for m-fold Hyper-geometric Summation <i>Journal of Symbolic
 * 				Computation</i> <b>20 (4)</b> 399-417
 * 		</li>
 * 		<li>
 * 			Lavoie, J. L., F. Grondin, and A. K. Rathie (1996): Generalization of Whipple’s Theorem on the
 * 				Sum of a (_2^3)F(a,b;c;z) <i>Journal of Computational and Applied Mathematics</i> <b>72</b>
 * 				293-300
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Hyper-geometric Function
 * 				https://dlmf.nist.gov/15
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Hyper-geometric Function https://en.wikipedia.org/wiki/Hypergeometric_function
 * 		</li>
 * 	</ul>
 * 
 * It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the First-Order Derivative Switch Verifier</li>
 * 		<li>Construct the First-Order Derivative Special Case Verifier</li>
 * 		<li>Construct the Log (1 + z) Special Case Verifier</li>
 * 		<li>Construct the Inverse Power A Special Case Verifier</li>
 * 		<li>Construct the Inverse Sine Special Case Verifier</li>
 * 		<li>Construct the Goursat Quadratic Transformation Verifier</li>
 * 		<li>Construct the Goursat Cubic Transformation Verifier</li>
 * 		<li>Construct the Vidunas Higher Order Transformation Verifier</li>
 * 		<li>Construct the Gauss Van der Monde z = +1 Verifier</li>
 * 		<li>Construct the Gauss-Dougall z = +1 Verifier</li>
 * 		<li>Construct the Gauss Kummer z = -1 Verifier</li>
 * 		<li>Construct the Gauss Second Summation z = 0.5 Verifier</li>
 * 		<li>Construct the Gauss Bailey z = +0.5 Verifier</li>
 * 		<li>Construct the First Gessel Stanton Koepf Rational Z Verifier</li>
 * 		<li>Construct the Second Gessel Stanton Koepf Rational Z Verifier</li>
 * 		<li>Construct the Incomplete Beta Verifier</li>
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
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/property/README.md">Special Function Property Lemma Verifiers</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HypergeometricEqualityLemma
{

	private static final double RecursiveGaussContinedFraction (
		final double a,
		final double b,
		final double c,
		final double z,
		final int termCurrent,
		final int termCount)
	{
		if (termCurrent == termCount) {
			return 1.;
		}

		double gaussContinedFraction = 1. + ((b - c - termCurrent - 1.) * (a + termCurrent) /
			((c + termCurrent + 1.) * (c + termCurrent + 2.))) * z / RecursiveGaussContinedFraction (
				a,
				b,
				c,
				z,
				termCurrent + 1,
				termCount
			);

		return 1. + (a - c - termCurrent) * (b + termCurrent) /
			((c + termCurrent) * (c + termCurrent + 1)) * z / gaussContinedFraction;
	}

	/**
	 * Construct the First-Order Derivative Switch Verifier
	 * 
	 * @param a A
	 * @param b B
	 * 
	 * @return The First-Order Derivative Switch Verifier
	 */

	public static final R1ToR1Property FirstOrderDerivativeSwitch (
		final double a,
		final double b)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator1 = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, a + 1),
				logBetaEstimator,
				10000
			);

			final EulerQuadratureEstimator hypergeometricEstimator2 = new EulerQuadratureEstimator (
				new HypergeometricParameters (b, a, a + 1),
				logBetaEstimator,
				10000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator1.derivative (z, 1);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator2.derivative (z, 1);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the First-Order Derivative Special Case Verifier
	 * 
	 * @param a A
	 * @param b B
	 * 
	 * @return The First-Order Derivative Special Case Verifier
	 */

	public static final R1ToR1Property FirstOrderDerivativeSpecialCase (
		final double a,
		final double b)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (b, a, a + 1),
				logBetaEstimator,
				10000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator.derivative (z, 1);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return a / z * (
							Math.pow (1. - z, -b) - hypergeometricEstimator.regularHypergeometric (z)
						);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Log (1 + z) Special Case Verifier
	 * 
	 * @return The Log (1 + z) Special Case Verifier
	 */

	public static final R1ToR1Property LogOnePlusZ()
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (1., 1., 2),
				logBetaEstimator,
				10000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return z * hypergeometricEstimator.evaluate (-z);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return Math.log (1. + z);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Inverse Power A Special Case Verifier
	 * 
	 * @param a A
	 * 
	 * @return The Inverse Power A Special Case Verifier
	 */

	public static final R1ToR1Property InversePowerA (
		final double a)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, 1., 1),
				logBetaEstimator,
				10000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator.evaluate (z);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return Math.pow (1. - z, -a);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Inverse Sine Special Case Verifier
	 * 
	 * @return The Inverse Sine Special Case Verifier
	 */

	public static final R1ToR1Property InverseSine()
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (0.5, 0.5, 1.5),
				logBetaEstimator,
				10000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return z * hypergeometricEstimator.evaluate (z * z);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return Math.asin (z);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Goursat Quadratic Transformation Verifier
	 * 
	 * @param a A
	 * @param b B
	 * 
	 * @return The Goursat Quadratic Transformation Verifier
	 */

	public static final R1ToR1Property GoursatQuadraticTransformation (
		final double a,
		final double b)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator1 = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, 2. * b),
				logBetaEstimator,
				100000
			);

			final EulerQuadratureEstimator hypergeometricEstimator2 = new EulerQuadratureEstimator (
				new HypergeometricParameters (0.5 * a, b - 0.5 * a, b + 0.5),
				logBetaEstimator,
				100000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator1.regularHypergeometric (z);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return Math.pow (1. - z, -0.5 * a) *
							hypergeometricEstimator2.regularHypergeometric (z * z / (4. * z - 4.));
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Goursat Cubic Transformation Verifier
	 * 
	 * @param a A
	 * 
	 * @return The Goursat Cubic Transformation Verifier
	 */

	public static final R1ToR1Property GoursatCubicTransformation (
		final double a)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator1 = new EulerQuadratureEstimator (
				new HypergeometricParameters (1.5 * a, 1.5 * a - 0.5, a + 0.5),
				logBetaEstimator,
				100000
			);

			final EulerQuadratureEstimator hypergeometricEstimator2 = new EulerQuadratureEstimator (
				new HypergeometricParameters (a - (1. / 3.), a, 2. * a),
				logBetaEstimator,
				100000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator1.regularHypergeometric (-1. * z * z / 3.);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						double onePlusZ = 1. + z;

						return Math.pow (onePlusZ, 1. - 3. * a) *
							hypergeometricEstimator2.regularHypergeometric (
								2. * z * (3. + z * z) / (onePlusZ * onePlusZ * onePlusZ)
							);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Vidunas Higher Order Transformation Verifier
	 * 
	 * @return The Vidunas Higher Order Transformation Verifier
	 */

	public static final R1ToR1Property VidunasHigherOrderTransformation()
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator1 = new EulerQuadratureEstimator (
				new HypergeometricParameters (0.25, 0.375, 0.875),
				logBetaEstimator,
				100000
			);

			final EulerQuadratureEstimator hypergeometricEstimator2 = new EulerQuadratureEstimator (
				new HypergeometricParameters (1./ 48., 17. / 48., 0.875),
				logBetaEstimator,
				100000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return Math.pow (
							z * z * z * z - 60. * z * z * z + 134. * z * z - 60. * z + 1.,
							1. / 16.
						) * hypergeometricEstimator1.regularHypergeometric (z);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator2.regularHypergeometric (
							-432. * z * (z - 1.) * (z - 1.) * Math.pow (z + 1., 8.) * Math.pow (
								z * z * z * z - 60. * z * z * z + 134. * z * z - 60. * z + 1.,
								-3.
							)
						);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Gauss Van der Monde z = +1 Verifier
	 * 
	 * @return The Gauss Van der Monde z = +1 Verifier
	 */

	public static final R3ToR1Property GaussVanderMondeZPlusOne()
	{
		final R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		final WindschitlTothAnalytic gammaEstimator = new WindschitlTothAnalytic (null);

		try {
			return new R3ToR1Property (
				R1ToR1Property.EQ,
				new R3ToR1() {
					@Override public double evaluate (
						final double a,
						final double b,
						final double c)
						throws Exception
					{
						return new EulerQuadratureEstimator (
							new HypergeometricParameters (a, b, c),
							logBetaEstimator,
							100000
						).regularHypergeometric (1.);
					}
				},
				new R3ToR1() {
					@Override public double evaluate (
						final double a,
						final double b,
						final double c)
						throws Exception
					{
						return gammaEstimator.evaluate (c) * gammaEstimator.evaluate (c - a - b) / (
							gammaEstimator.evaluate (c - a) * gammaEstimator.evaluate (c - b)
						);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Gauss-Dougall z = +1 Verifier
	 * 
	 * @return The Gauss-Dougall z = +1 Verifier
	 */

	public static final R3ToR1Property GaussDougallZPlusOne()
	{
		final R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			return new R3ToR1Property (
				R1ToR1Property.EQ,
				new R3ToR1() {
					@Override public double evaluate (
						final double m,
						final double b,
						final double c)
						throws Exception
					{
						return new EulerQuadratureEstimator (
							new HypergeometricParameters (-m, b, c),
							logBetaEstimator,
							100000
						).regularHypergeometric (1.);
					}
				},
				new R3ToR1() {
					@Override public double evaluate (
						final double m,
						final double b,
						final double c)
						throws Exception
					{
						return NumberUtil.PochhammerSymbol (c - b, (int) m) /
							NumberUtil.PochhammerSymbol (c, (int) m);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Gauss Kummer z = -1 Verifier
	 * 
	 * @return The Gauss Kummer z = -1 Verifier
	 */

	public static final R2ToR1Property GaussKummerZMinusOne()
	{
		final R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		final WindschitlTothAnalytic gammaEstimator = new WindschitlTothAnalytic (null);

		try {
			return new R2ToR1Property (
				R1ToR1Property.EQ,
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double b)
						throws Exception
					{
						return new EulerQuadratureEstimator (
							new HypergeometricParameters (a, b, 1. + a - b),
							logBetaEstimator,
							100000
						).regularHypergeometric (-1.);
					}
				},
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double b)
						throws Exception
					{
						return gammaEstimator.evaluate (1. + a - b) * gammaEstimator.evaluate (1. + 0.5 * a)
						/ (gammaEstimator.evaluate (1. + a) * gammaEstimator.evaluate (1. + 0.5 * a - b));
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Gauss Second Summation z = 0.5 Verifier
	 * 
	 * @return The Gauss Second Summation z = 0.5 Verifier
	 */

	public static final R2ToR1Property GaussSecondSummationZPlusHalf()
	{
		final R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		final WindschitlTothAnalytic gammaEstimator = new WindschitlTothAnalytic (null);

		try {
			return new R2ToR1Property (
				R1ToR1Property.EQ,
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double b)
						throws Exception
					{
						return new EulerQuadratureEstimator (
							new HypergeometricParameters (a, b, 0.5 * (1. + a + b)),
							logBetaEstimator,
							100000
						).regularHypergeometric (0.5);
					}
				},
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double b)
						throws Exception
					{
						return gammaEstimator.evaluate (0.5) * gammaEstimator.evaluate (0.5 * (1. + a + b)) /
						(
							gammaEstimator.evaluate (0.5 * (1. + a)) *
							gammaEstimator.evaluate (0.5 * (1. + b))
						);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Gauss Bailey z = +0.5 Verifier
	 * 
	 * @return The Gauss Bailey z = +0.5 Verifier
	 */

	public static final R2ToR1Property GaussBaileyZPlusHalf()
	{
		final R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		final WindschitlTothAnalytic gammaEstimator = new WindschitlTothAnalytic (null);

		try {
			return new R2ToR1Property (
				R1ToR1Property.EQ,
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double c)
						throws Exception
					{
						return new EulerQuadratureEstimator (
							new HypergeometricParameters (a, 1. - a, c),
							logBetaEstimator,
							100000
						).regularHypergeometric (0.5);
					}
				},
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double c)
						throws Exception
					{
						return gammaEstimator.evaluate (0.5 * c) * gammaEstimator.evaluate (0.5 * (1. + c)) /
						(
							gammaEstimator.evaluate (0.5 * (c + a)) *
							gammaEstimator.evaluate (0.5 * (1. + c - a))
						);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the First Gessel Stanton Koepf Rational Z Verifier
	 * 
	 * @return The First Gessel Stanton Koepf Rational Z Verifier
	 */

	public static final R2ToR1Property FirstGesselStantonKoepf()
	{
		final R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (100000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			return new R2ToR1Property (
				R1ToR1Property.EQ,
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double z)
						throws Exception
					{
						return new EulerQuadratureEstimator (
							new HypergeometricParameters (a, -1. * a, 0.5),
							logBetaEstimator,
							1000000
						).regularHypergeometric (0.25 * z * z / (z - 1.));
					}
				},
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double z)
						throws Exception
					{
						return 0.5 * (Math.pow (1. - z, a) + Math.pow (1. - z, -a));
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Second Gessel Stanton Koepf Rational Z Verifier
	 * 
	 * @return The Second Gessel Stanton Koepf Rational Z Verifier
	 */

	public static final R2ToR1Property SecondGesselStantonKoepf()
	{
		final R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (100000);

		if (null == logBetaEstimator) {
			return null;
		}

		try
		{
			return new R2ToR1Property (
				R1ToR1Property.EQ,
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double z)
						throws Exception
					{
						return new EulerQuadratureEstimator (
							new HypergeometricParameters (a, -1. * a, 0.5),
							logBetaEstimator,
							1000000
						).regularHypergeometric (0.5 * (1. - Math.cos (z)));
					}
				},
				new R2ToR1() {
					@Override public double evaluate (
						final double a,
						final double z)
						throws Exception
					{
						return Math.cos (a * z);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Gauss Continued Fraction Recursive Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Gauss Continued Fraction Recursive Verifier
	 */

	public static final R1ToR1Property GaussContinuedFractionRecursive (
		final double a,
		final double b,
		final double c)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator1 = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, c),
				logBetaEstimator,
				10000
			);

			final EulerQuadratureEstimator hypergeometricEstimator2 = new EulerQuadratureEstimator (
				new HypergeometricParameters (a + 1, b, c + 1),
				logBetaEstimator,
				10000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator2.evaluate (z) / hypergeometricEstimator1.evaluate (z);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return 1. / RecursiveGaussContinedFraction (a, b, c, z, 0, 10);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Incomplete Beta Verifier
	 * 
	 * @param p P
	 * @param q Q
	 * 
	 * @return The Incomplete Beta Verifier
	 */

	public static final R1ToR1Property IncompleteBeta (
		final double p,
		final double q)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		final IncompleteIntegrandEstimator incompleteBetaEstimator =
			IncompleteIntegrandEstimator.EulerFirst (10000);

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (p, 1. - q, p + 1.),
				logBetaEstimator,
				10000
			);

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return Math.pow (z, p) * hypergeometricEstimator.evaluate (z) / p;
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return incompleteBetaEstimator.evaluate (z, p, q);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
