
package org.drip.specialfunction.property;

import org.drip.function.definition.R1PropertyVerification;
import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R1ToR1Property;
import org.drip.numerical.common.Array2D;
import org.drip.numerical.common.NumberUtil;
import org.drip.specialfunction.gamma.EulerIntegralSecondKind;
import org.drip.specialfunction.loggamma.InfiniteSumEstimator;

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
 * <i>GammaInequalityLemma</i> contains the Verifiable Inequality Lemmas of the Gamma Function. The
 * 	References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Blagouchine, I. V. (2014): Re-discovery of Malmsten's Integrals, their Evaluation by Contour
 * 				Integration Methods, and some Related Results <i>Ramanujan Journal</i> <b>35 (1)</b> 21-110
 * 		</li>
 * 		<li>
 * 			Borwein, J. M., and R. M. Corless (2017): Gamma Function and the Factorial in the Monthly
 * 				https://arxiv.org/abs/1703.05349 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Davis, P. J. (1959): Leonhard Euler's Integral: A Historical Profile of the Gamma Function
 * 				<i>American Mathematical Monthly</i> <b>66 (10)</b> 849-869
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 * 
 * It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the Asymptotic Upper Approximate</li>
 * 		<li>Generate the Exponentially Convex Inequality Verifier</li>
 * 		<li>Generate the Spaced Point Convex Inequality Verifier</li>
 * 		<li>Generate the Logarithmically Convex Inequality Verifier</li>
 * 		<li>Generate the Gautschi Left Inequality Verifier</li>
 * 		<li>Generate the Gautschi Right Inequality Verifier</li>
 * 		<li>Generate the Jensen Multi-Point Interpolant Convexity Verification</li>
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

public class GammaInequalityLemma
{

	/**
	 * Construct the Asymptotic Upper Approximate
	 * 
	 * @param alpha Alpha
	 * 
	 * @return The Asymptotic Upper Approximate
	 */

	public static final R1ToR1Property AsymptoticUpperApproximate (
		final double alpha)
	{
		if (!NumberUtil.IsValid (alpha)) {
			return null;
		}

		try {
			return new R1ToR1Property (
				R1ToR1Property.GTE,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double s)
						throws Exception
					{
						if (!NumberUtil.IsValid (s)) {
							throw new Exception (
								"GammaInequalityLemma::AsymptoticUpperApproximate::evaluate => Invalid Inputs"
							);
						}

						return InfiniteSumEstimator.Weierstrass (1638400).evaluate (s + alpha);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double s)
						throws Exception
					{
						if (!NumberUtil.IsValid (s)) {
							throw new Exception (
								"GammaInequalityLemma::AsymptoticUpperApproximate::evaluate => Invalid Inputs"
							);
						}

						return alpha * Math.log (s) +
							InfiniteSumEstimator.Weierstrass (1638400).evaluate (s);
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
	 * Generate the Exponentially Convex Inequality Verifier
	 * 
	 * @param z1 z1
	 * @param z2 z2
	 * 
	 * @return The Exponentially Convex Inequality Verifier
	 */

	public static final R1ToR1Property ExponentiallyConvex (
		final double z1,
		final double z2)
	{
		if (!NumberUtil.IsValid (z1) || !NumberUtil.IsValid (z2)) {
			return null;
		}

		try {
			return new R1ToR1Property (
				R1ToR1Property.LTE,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double t)
						throws Exception
					{
						if (!NumberUtil.IsValid (t) || 0. > t || 1. < t) {
							throw new Exception (
								"GammaInequalityLemma::ExponentiallyConvex::evaluate => Invalid Inputs"
							);
						}

						return InfiniteSumEstimator.Weierstrass (1638400).evaluate (t * z1 + (1. - t) * z2);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double t)
						throws Exception
					{
						if (!NumberUtil.IsValid (t) || 0. > t || 1. < t) {
							throw new Exception (
								"GammaInequalityLemma::ExponentiallyConvex::evaluate => Invalid Inputs"
							);
						}

						InfiniteSumEstimator weierStrass = InfiniteSumEstimator.Weierstrass (1638400);

						return t * weierStrass.evaluate (z1) + (1. - t) * weierStrass.evaluate (z2);
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
	 * Generate the Spaced Point Convex Inequality Verifier
	 * 
	 * @param y y
	 * 
	 * @return The Spaced Point Convex Inequality Verifier
	 */

	public static final R1ToR1Property SpacedPointConvex (
		final double y)
	{
		if (!NumberUtil.IsValid (y)) {
			return null;
		}

		final InfiniteSumEstimator weierStrass = InfiniteSumEstimator.Weierstrass (1638400);

		try {
			final double logGammaY = weierStrass.evaluate (y);

			return new R1ToR1Property (
				R1ToR1Property.GT,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double x)
						throws Exception
					{
						if (!NumberUtil.IsValid (x) || x >= y) {
							throw new Exception (
								"GammaInequalityLemma::SpacedPointConvex::evaluate => Invalid Inputs"
							);
						}

						return (logGammaY - weierStrass.evaluate (x)) / (y - x);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double x)
						throws Exception
					{
						if (!NumberUtil.IsValid (x)) {
							throw new Exception (
								"GammaInequalityLemma::SpacedPointConvex::evaluate => Invalid Inputs"
							);
						}

						EulerIntegralSecondKind eulerIntegralSecondKind = new EulerIntegralSecondKind (null);

						return eulerIntegralSecondKind.derivative (x, 1) - weierStrass.evaluate (x);
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
	 * Generate the Logarithmically Convex Inequality Verifier
	 * 
	 * @return The Logarithmically Convex Inequality Verifier
	 */

	public static final R1ToR1Property LogarithmicConvex()
	{
		final InfiniteSumEstimator weierStrass = InfiniteSumEstimator.Weierstrass (1638400);

		try {
			return new R1ToR1Property (
				R1ToR1Property.GT,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						if (!NumberUtil.IsValid (z)) {
							throw new Exception (
								"GammaInequalityLemma::LogarithmicConvex::evaluate => Invalid Inputs"
							);
						}

						return Math.log (new EulerIntegralSecondKind (null).derivative (z, 2)) +
							weierStrass.evaluate (z);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						if (!NumberUtil.IsValid (z)) {
							throw new Exception (
								"GammaInequalityLemma::LogarithmicConvex::evaluate => Invalid Inputs"
							);
						}

						return Math.log (new EulerIntegralSecondKind (null).derivative (z, 1));
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
	 * Generate the Gautschi Left Inequality Verifier
	 * 
	 * @param s s
	 * 
	 * @return The Gautschi Left Inequality Verifier
	 */

	public static final R1ToR1Property GautschiLeft (
		final double s)
	{
		if (!NumberUtil.IsValid (s) || 0. >= s || 1. <= s) {
			return null;
		}

		final InfiniteSumEstimator weierStrass = InfiniteSumEstimator.Weierstrass (1638400);

		try {
			return new R1ToR1Property (
				R1ToR1Property.LT,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						if (!NumberUtil.IsValid (z)) {
							throw new Exception (
								"GammaInequalityLemma::GautschiLeft::evaluate => Invalid Inputs"
							);
						}

						return (1. - s) * Math.log (z);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						if (!NumberUtil.IsValid (z)) {
							throw new Exception (
								"GammaInequalityLemma::GautschiLeft::evaluate => Invalid Inputs"
							);
						}

						return weierStrass.evaluate (z + 1) - weierStrass.evaluate (z + s);
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
	 * Generate the Gautschi Right Inequality Verifier
	 * 
	 * @param s s
	 * 
	 * @return The Gautschi Right Inequality Verifier
	 */

	public static final R1ToR1Property GautschiRight (
		final double s)
	{
		if (!NumberUtil.IsValid (s) || 0. >= s || 1. <= s) {
			return null;
		}

		final InfiniteSumEstimator weierStrass = InfiniteSumEstimator.Weierstrass (1638400);

		try {
			return new R1ToR1Property (
				R1ToR1Property.LT,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						if (!NumberUtil.IsValid (z)) {
							throw new Exception (
								"GammaInequalityLemma::GautschiRight::evaluate => Invalid Inputs"
							);
						}

						return weierStrass.evaluate (z + 1) - weierStrass.evaluate (z + s);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						if (!NumberUtil.IsValid (z)) {
							throw new Exception (
								"GammaInequalityLemma::GautschiRight::evaluate => Invalid Inputs"
							);
						}

						return (1. - s) * java.lang.Math.log (z + 1.);
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
	 * Generate the Jensen Multi-Point Interpolant Convexity Verification
	 * 
	 * @param multiPoint2D Multi-Point 2D
	 * 
	 * @return Jensen Multi-Point Interpolant Convexity Verification
	 */

	public static final R1PropertyVerification JensenMultiPointInterpolant (
		final Array2D multiPoint2D)
	{
		if (null == multiPoint2D) {
			return null;
		}

		final InfiniteSumEstimator weierStrass = InfiniteSumEstimator.Weierstrass (1638400);

		double[] xArray = multiPoint2D.x();

		double[] aArray = multiPoint2D.y();

		double interpolantDenominator = 0.;
		double interpolantNumerator = 0.;
		int count = aArray.length;
		double rValue = 0.;

		for (int index = 0; index < count; ++index) {
			interpolantNumerator = interpolantNumerator + aArray[index] * xArray[index];
			interpolantDenominator = interpolantDenominator + aArray[index];
		}

		double interpolantDenominatorInverse = 1. / interpolantDenominator;

		try {
			double lValue = weierStrass.evaluate (interpolantNumerator* interpolantDenominatorInverse);

			for (int index = 0; index < count; ++index) {
				rValue = rValue + aArray[index] * weierStrass.evaluate (xArray[index]);
			}

			return new R1PropertyVerification (
				lValue,
				rValue = rValue * interpolantDenominatorInverse,
				lValue <= rValue
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
