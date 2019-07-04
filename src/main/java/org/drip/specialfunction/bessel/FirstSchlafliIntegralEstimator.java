
package org.drip.specialfunction.bessel;

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
 * <i>FirstSchlafliIntegralEstimator</i> implements the Integral Estimator for the Cylindrical Bessel
 * Function of the First Kind. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Arfken, G. B., and H. J. Weber (2005): <i>Mathematical Methods for Physicists 6<sup>th</sup>
 * 				Edition</i> <b>Harcourt</b> San Diego
 * 		</li>
 * 		<li>
 * 			Temme N. M. (1996): <i>Special Functions: An Introduction to the Classical Functions of
 * 				Mathematical Physics 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York
 * 		</li>
 * 		<li>
 * 			Watson, G. N. (1995): <i>A Treatise on the Theory of Bessel Functions</i> <b>Cambridge University
 * 				Press</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Bessel Function https://en.wikipedia.org/wiki/Bessel_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Project</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/ode/README.md">Special Function Ordinary Differential Equations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class FirstSchlafliIntegralEstimator extends
	org.drip.specialfunction.definition.BesselFirstKindEstimator
{
	private int _quadratureCount = -1;

	/**
	 * Construct the Bessel First Kind Estimator from the Schlafli Integer Integral Form
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Bessel First Kind Estimator from the Schlafli Integer Integral Form
	 */

	public static final FirstSchlafliIntegralEstimator IntegerForm (
		final int quadratureCount)
	{
		try
		{
			return new FirstSchlafliIntegralEstimator (quadratureCount)
			{
				@Override public double bigJ (
					final double alpha,
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsInteger (alpha) ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("FirstSchlafliIntegralEstimator::IntegerForm::evaluate => Invalid Inputs");
					}

					return org.drip.numerical.integration.NewtonCotesQuadratureGenerator.Zero_PlusOne (
						0.,
						java.lang.Math.PI,
						quadratureCount
					).integrate (
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double theta)
								throws java.lang.Exception
							{
								return java.lang.Math.cos (alpha * theta - z * java.lang.Math.sin (theta));
							}
						}
					) / java.lang.Math.PI;
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
	 * Construct the Bessel First Kind Estimator from the Schlafli Non-Integer Integral Form
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Bessel First Kind Estimator from the Schlafli Non-Integer Integral Form
	 */

	public static final FirstSchlafliIntegralEstimator NonIntegerForm (
		final int quadratureCount)
	{
		try
		{
			return new FirstSchlafliIntegralEstimator (quadratureCount)
			{
				@Override public double bigJ (
					final double alpha,
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (alpha) ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("FirstSchlafliIntegralEstimator::NonIntegerForm::evaluate => Invalid Inputs");
					}

					return (org.drip.numerical.integration.NewtonCotesQuadratureGenerator.Zero_PlusOne (
						0.,
						java.lang.Math.PI,
						quadratureCount
					).integrate (
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double theta)
								throws java.lang.Exception
							{
								return java.lang.Math.cos (alpha * theta - z * java.lang.Math.sin (theta));
							}
						}
					) / java.lang.Math.PI) - (0. == alpha ? 0. :
					org.drip.numerical.integration.NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
						0.,
						quadratureCount
					).integrate (
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double t)
								throws java.lang.Exception
							{
								return java.lang.Math.exp (-z * java.lang.Math.sinh (t) - alpha * t);
							}
						}
					) * java.lang.Math.sin (alpha * java.lang.Math.PI) / java.lang.Math.PI);
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	protected FirstSchlafliIntegralEstimator (
		final int quadratureCount)
		throws java.lang.Exception
	{
		if (0 >= (_quadratureCount = quadratureCount))
		{
			throw new java.lang.Exception ("FirstSchlafliIntegralEstimator Constructor => Invalid Inputs");
		} 
	}

	/**
	 * Retrieve the Quadrature Count
	 * 
	 * @return The Quadrature Count
	 */

	public int quadratureCount()
	{
		return _quadratureCount;
	}
}
