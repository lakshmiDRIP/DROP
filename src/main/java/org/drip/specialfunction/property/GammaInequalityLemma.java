
package org.drip.specialfunction.property;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>GammaInequalityLemma</i> contains the Verifiable Inequality Lemmas of the Gamma Function. The
 * References are:
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/property/README.md">Special Function Property Lemma Verifiers</a></li>
 *  </ul>
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

	public static final org.drip.function.definition.R1ToR1Property AsymptoticUpperApproximate (
		final double alpha)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (alpha))
		{
			return null;
		}

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.GTE,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::AsymptoticUpperApproximate::evaluate => Invalid Inputs");
						}

						return org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400).evaluate 
							(s + alpha);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double s)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (s))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::AsymptoticUpperApproximate::evaluate => Invalid Inputs");
						}

						return alpha * java.lang.Math.log (s)  +
							org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400).evaluate (s);
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
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

	public static final org.drip.function.definition.R1ToR1Property ExponentiallyConvex (
		final double z1,
		final double z2)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z1) ||
			!org.drip.numerical.common.NumberUtil.IsValid (z2))
		{
			return null;
		}

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.LTE,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double t)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (t) || 0. > t || 1. < t)
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::ExponentiallyConvex::evaluate => Invalid Inputs");
						}

						return org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400).evaluate
							(t * z1 + (1. - t) * z2);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double t)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (t) || 0. > t || 1. < t)
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::ExponentiallyConvex::evaluate => Invalid Inputs");
						}

						org.drip.specialfunction.loggamma.InfiniteSumEstimator weierStrass =
							org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

						return t * weierStrass.evaluate (z1) + (1. - t) * weierStrass.evaluate (z2);
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
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

	public static final org.drip.function.definition.R1ToR1Property SpacedPointConvex (
		final double y)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (y))
		{
			return null;
		}

		final org.drip.specialfunction.loggamma.InfiniteSumEstimator weierStrass =
			org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

		try
		{
			final double logGammaY = weierStrass.evaluate (y);

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.GT,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || x >= y)
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::SpacedPointConvex::evaluate => Invalid Inputs");
						}

						return (logGammaY - weierStrass.evaluate (x)) / (y - x);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::SpacedPointConvex::evaluate => Invalid Inputs");
						}

						org.drip.specialfunction.gamma.EulerIntegralSecondKind eulerIntegralSecondKind =
							new org.drip.specialfunction.gamma.EulerIntegralSecondKind (null);

						return eulerIntegralSecondKind.derivative (
							x,
							1
						) - weierStrass.evaluate (x);
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Logarithmically Convex Inequality Verifier
	 * 
	 * @return The Logarithmically Convex Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property LogarithmicConvex()
	{
		final org.drip.specialfunction.loggamma.InfiniteSumEstimator weierStrass =
			org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.GT,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::LogarithmicConvex::evaluate => Invalid Inputs");
						}

						return java.lang.Math.log (
							new org.drip.specialfunction.gamma.EulerIntegralSecondKind (null).derivative (
								z,
								2
							)
						) + weierStrass.evaluate (z);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::LogarithmicConvex::evaluate => Invalid Inputs");
						}

						return java.lang.Math.log (
							new org.drip.specialfunction.gamma.EulerIntegralSecondKind (null).derivative (
								z,
								1
							)
						);
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
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

	public static final org.drip.function.definition.R1ToR1Property GautschiLeft (
		final double s)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (s) || 0. >= s || 1. <= s)
		{
			return null;
		}

		final org.drip.specialfunction.loggamma.InfiniteSumEstimator weierStrass =
			org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.LT,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::GautschiLeft::evaluate => Invalid Inputs");
						}

						return (1. - s) * java.lang.Math.log (z);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::GautschiLeft::evaluate => Invalid Inputs");
						}

						return weierStrass.evaluate (z + 1) - weierStrass.evaluate (z + s);
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
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

	public static final org.drip.function.definition.R1ToR1Property GautschiRight (
		final double s)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (s) || 0. >= s || 1. <= s)
		{
			return null;
		}

		final org.drip.specialfunction.loggamma.InfiniteSumEstimator weierStrass =
			org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.LT,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::GautschiRight::evaluate => Invalid Inputs");
						}

						return weierStrass.evaluate (z + 1) - weierStrass.evaluate (z + s);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("GammaInequalityLemma::GautschiRight::evaluate => Invalid Inputs");
						}

						return (1. - s) * java.lang.Math.log (z + 1.);
					}
				},
				org.drip.function.definition.R1ToR1Property.MISMATCH_TOLERANCE
			);
		}
		catch (java.lang.Exception e)
		{
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

	public static final org.drip.function.definition.R1ToR1PropertyVerification JensenMultiPointInterpolant (
		final org.drip.numerical.common.Array2D multiPoint2D)
	{
		if (null == multiPoint2D)
		{
			return null;
		}

		final org.drip.specialfunction.loggamma.InfiniteSumEstimator weierStrass =
			org.drip.specialfunction.loggamma.InfiniteSumEstimator.Weierstrass (1638400);

		double[] xArray = multiPoint2D.x();

		double[] aArray = multiPoint2D.y();

		double interpolantDenominator = 0.;
		double interpolantNumerator = 0.;
		int count = aArray.length;
		double rValue = 0.;

		for (int index = 0; index < count; ++index)
		{
			interpolantNumerator = interpolantNumerator + aArray[index] * xArray[index];
			interpolantDenominator = interpolantDenominator + aArray[index];
		}

		double interpolantDenominatorInverse = 1. / interpolantDenominator;

		try
		{
			double lValue = weierStrass.evaluate (interpolantNumerator* interpolantDenominatorInverse);

			for (int index = 0; index < count; ++index)
			{
				rValue = rValue + aArray[index] * weierStrass.evaluate (xArray[index]);
			}

			return new org.drip.function.definition.R1ToR1PropertyVerification (
				lValue,
				rValue = rValue * interpolantDenominatorInverse,
				lValue <= rValue
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
