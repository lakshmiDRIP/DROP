
package org.drip.function.e2erfc;

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
 * <i>ErrorFunctionComplementAnalytical</i> implements Analytical Versions of the Error Function Complement
 * (erfc) Estimate. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Chang, S. H., P. C. Cosman, L. B. Milstein (2011): Chernoff-Type Bounds for Gaussian Error
 * 				Function <i>IEEE Transactions on Communications</i> <b>59 (11)</b> 2939-2944
 * 		</li>
 * 		<li>
 * 			Cody, W. J. (1991): Algorithm 715: SPECFUN – A Portable FORTRAN Package of Special Function
 * 				Routines and Test Drivers <i>ACM Transactions on Mathematical Software</i> <b>19 (1)</b>
 * 				22-32
 * 		</li>
 * 		<li>
 * 			Schopf, H. M., and P. H. Supancic (2014): On Burmann’s Theorem and its Application to Problems of
 * 				Linear and Non-linear Heat Transfer and Diffusion
 * 				https://www.mathematica-journal.com/2014/11/on-burmanns-theorem-and-its-application-to-problems-of-linear-and-nonlinear-heat-transfer-and-diffusion/#more-39602/
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Error Function https://en.wikipedia.org/wiki/Error_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/e2erfc/README.md">E<sub>2</sub> erfc Estimation Function Implementation</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ErrorFunctionComplementAnalytical
{

	private static final double ContinuedFractionRecursor (
		final double z,
		final int termIndex,
		final int termCount)
	{
		if (termIndex == termCount)
		{
			return 0.;
		}

		return ((1 == termIndex % 2) ? z * z : 1.) + 0.5 * termIndex / (
			1. + ContinuedFractionRecursor (
				z,
				termIndex + 1,
				termCount
			)
		);
	}

	/**
	 * Construct Karagiannidis-Lioumpas (2007) Version of the Analytical Error Function Complement
	 * 
	 * @param A A
	 * @param B B
	 * 
	 * @return Karagiannidis-Lioumpas (2007) Version of the Analytical Error Function Complement
	 */

	public static final org.drip.function.e2erfc.ErrorFunctionComplement KaragiannidisLioumpas2007 (
		final double A,
		final double B)
	{
		try
		{
			return !org.drip.numerical.common.NumberUtil.IsValid (A) ||
				!org.drip.numerical.common.NumberUtil.IsValid (B) ? null :
					new org.drip.function.e2erfc.ErrorFunctionComplement (
						null,
						null
					)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("ErrorFunctionComplementAnalytical::KaragiannidisLioumpas2007::evaluate => Invalid Inputs");
						}

						if (0. == z)
						{
							return 1.;
						}

						if (z < 0)
						{
							return 2. - evaluate (-1. * z);
						}

						return (1. - java.lang.Math.exp (-1. * A * z)) * java.lang.Math.exp (-1. * z * z) /
							(B * z * java.lang.Math.sqrt (java.lang.Math.PI));
					}
				};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Karagiannidis-Lioumpas (2007) Version of the Analytical Error Function Complement
	 * 
	 * @return Karagiannidis-Lioumpas (2007) Version of the Analytical Error Function Complement
	 */

	public static final org.drip.function.e2erfc.ErrorFunctionComplement KaragiannidisLioumpas2007()
	{
		return KaragiannidisLioumpas2007 (
			1.980,
			1.135
		);
	}

	/**
	 * Construct the Chiani-Dardari-Simon (2012a) Version of the Analytical Error Function Complement
	 * 
	 * @return The Chiani-Dardari-Simon (2012a) Version of the Analytical Error Function Complement
	 */

	public static final org.drip.function.e2erfc.ErrorFunctionComplement ChianiDardariSimon2012a()
	{
		try
		{
			return new org.drip.function.e2erfc.ErrorFunctionComplement (
				null,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ErrorFunctionComplementAnalytical::ChianiDardariSimon2012a::evaluate => Invalid Inputs");
					}

					if (0. == z)
					{
						return 1.;
					}

					if (z < 0)
					{
						return 2. - evaluate (-1. * z);
					}

					return 0.5 * java.lang.Math.exp (-2. * z * z) + 0.5 * java.lang.Math.exp (-1. * z * z);
				}

				@Override public org.drip.numerical.estimation.R1Estimate boundedEstimate (
					final double z)
				{
					try
					{
						double baseline = evaluate (z);

						return new org.drip.numerical.estimation.R1Estimate (
							baseline,
							baseline,
							java.lang.Math.exp (-1. * z * z)
						);
					}
					catch (java.lang.Exception e)
					{
						e.printStackTrace();
					}

					return null;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Chiani-Dardari-Simon (2012b) Version of the Analytical Error Function Complement
	 * 
	 * @return The Chiani-Dardari-Simon (2012b) Version of the Analytical Error Function Complement
	 */

	public static final org.drip.function.e2erfc.ErrorFunctionComplement ChianiDardariSimon2012b()
	{
		try
		{
			return new org.drip.function.e2erfc.ErrorFunctionComplement (
				null,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ErrorFunctionComplementAnalytical::ChianiDardariSimon2012b::evaluate => Invalid Inputs");
					}

					if (0. == z)
					{
						return 1.;
					}

					if (z < 0)
					{
						return 2. - evaluate (-1. * z);
					}

					return java.lang.Math.exp (-1. * z * z) / 6. + 0.5 * java.lang.Math.exp (-4. * z * z / 3.);
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Chang-Cosman-Milstein (2011) Version of the Analytical Error Function Complement
	 * 
	 * @param beta Beta
	 * 
	 * @return The Chang-Cosman-Milstein (2011) Version of the Analytical Error Function Complement
	 */

	public static final org.drip.function.e2erfc.ErrorFunctionComplement ChangCosmanMilstein2011 (
		final double beta)
	{
		try
		{
			return !org.drip.numerical.common.NumberUtil.IsValid (beta) || 1. >= beta ? null :
				new org.drip.function.e2erfc.ErrorFunctionComplement (
					null,
					null
				)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ErrorFunctionComplementAnalytical::ChangCosmanMilstein2011::evaluate => Invalid Inputs");
					}

					if (0. == z)
					{
						return 1.;
					}

					if (z < 0)
					{
						return 2. - evaluate (-1. * z);
					}

					return java.lang.Math.sqrt (2. * java.lang.Math.E * (beta - 1.) / java.lang.Math.PI) *
						java.lang.Math.exp (-1. * beta * z * z) / beta;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Continued Fraction Expansion Version of the Analytical Error Function Complement
	 * 
	 * @param termCount Term Count
	 * 
	 * @return The Continued Fraction Expansion Version of the Analytical Error Function Complement
	 */

	public static final org.drip.function.e2erfc.ErrorFunctionComplement ContinuedFractionExpansion (
		final int termCount)
	{
		try
		{
			return 0 >= termCount ? null : new org.drip.function.e2erfc.ErrorFunctionComplement (
				null,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ErrorFunctionComplementAnalytical::ContinuedFractionExpansion::evaluate => Invalid Inputs");
					}

					if (0. == z)
					{
						return 1.;
					}

					if (z < 0)
					{
						return 2. - evaluate (-1. * z);
					}

					return z * java.lang.Math.exp (-1. * z * z) / java.lang.Math.sqrt (java.lang.Math.PI) /
						ContinuedFractionRecursor (
							z,
							1,
							termCount
						);
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Craig 1991 Version of the ErrorFunctionComplement Quadrature
	 * 
	 * @return The Craig 1991 Version of the ErrorFunctionComplement Quadrature
	 */

	public static final org.drip.function.e2erfc.ErrorFunctionComplement Craig1991()
	{
		try
		{
			return new org.drip.function.e2erfc.ErrorFunctionComplement (
				null,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ErrorFunctionComplementAnalytical::Craig1991::evaluate => Invalid Inputs");
					}

					if (0. == z)
					{
						return 1.;
					}

					if (z < 0)
					{
						return 2. - evaluate (-1. * z);
					}

					return org.drip.numerical.integration.NewtonCotesQuadratureGenerator.Zero_PlusOne (
						0.,
						z,
						100
					).integrate (
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double theta)
								throws java.lang.Exception
							{
								if (0. == theta)
								{
									return 0.;
								}

								double sinTheta = java.lang.Math.sin (theta);

								return 2. * java.lang.Math.exp (-1. * z * z / (sinTheta * sinTheta)) /
									java.lang.Math.PI;
							}
						}
					);
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
