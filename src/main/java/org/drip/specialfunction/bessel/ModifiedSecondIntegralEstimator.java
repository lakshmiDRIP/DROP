
package org.drip.specialfunction.bessel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>ModifiedSecondIntegralEstimator</i> implements the Integral Estimator for the Modified Bessel Function
 * of the Second Kind. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/bessel/README.md">Ordered Bessel Function Variant Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ModifiedSecondIntegralEstimator extends
	org.drip.specialfunction.definition.ModifiedBesselSecondKindEstimator
{
	private int _quadratureCount = -1;

	/**
	 * Construct the Modified Bessel Second Kind Estimator from the Integral Form
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Modified Bessel Second Kind Estimator from the Integral Form
	 */

	public static final ModifiedSecondIntegralEstimator Standard (
		final int quadratureCount)
	{
		try
		{
			return new ModifiedSecondIntegralEstimator (quadratureCount)
			{
				@Override public double bigK (
					final double alpha,
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (alpha) ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ModifiedSecondIntegralEstimator::Standard::evaluate => Invalid Inputs");
					}

					return
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
								return java.lang.Double.isInfinite (t) ? 0. :
									java.lang.Math.exp (-z * java.lang.Math.cosh (t)) *
										java.lang.Math.cosh (alpha * t);
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

	/**
	 * Construct the Modified Bessel Second Kind Zero Order Estimator from the Integral Form
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Modified Bessel Second Kind Zero Order Estimator from the Integral Form
	 */

	public static final ModifiedSecondIntegralEstimator ZeroOrder (
		final int quadratureCount)
	{
		try
		{
			return new ModifiedSecondIntegralEstimator (quadratureCount)
			{
				@Override public double bigK (
					final double alpha,
					final double z)
					throws java.lang.Exception
				{
					if (0. != alpha ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ModifiedSecondIntegralEstimator::ZeroOrder::evaluate => Invalid Inputs");
					}

					return 0.5 * org.drip.numerical.integration.NewtonCotesQuadratureGenerator.GaussHermite (
						quadratureCount
					).integrate (
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double t)
								throws java.lang.Exception
							{
								return java.lang.Double.isInfinite (t) ? 0. :
									java.lang.Math.cos (z * t) / java.lang.Math.sqrt (t * t + 1.);
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

	/**
	 * Construct the Modified Bessel Second Kind Estimator for the 1. / 3. Order from the Integral Form
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Modified Bessel Second Kind Estimator for the 1. / 3. Order from the Integral Form
	 */

	public static final ModifiedSecondIntegralEstimator OneThirdOrder (
		final int quadratureCount)
	{
		try
		{
			return new ModifiedSecondIntegralEstimator (quadratureCount)
			{
				@Override public double bigK (
					final double alpha,
					final double z)
					throws java.lang.Exception
				{
					if (1. / 3. != alpha ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ModifiedSecondIntegralEstimator::OneThirdOrder::evaluate => Invalid Inputs");
					}

					return java.lang.Math.sqrt (3.) *
					org.drip.numerical.integration.NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
						0.,
						quadratureCount
					).integrate (
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double x)
								throws java.lang.Exception
							{
								if (java.lang.Double.isInfinite (x))
								{
									return 0.;
								}

								double xSquaredOver3 = x * x / 3.;

								return java.lang.Math.exp (
									-z * (1. + 4. * xSquaredOver3) * java.lang.Math.sqrt (1. + xSquaredOver3)
								);
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

	/**
	 * Construct the Modified Bessel Second Kind Estimator for the 2. / 3. Order from the Integral Form
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Modified Bessel Second Kind Estimator for the 2. / 3. Order from the Integral Form
	 */

	public static final ModifiedSecondIntegralEstimator TwoThirdOrder (
		final int quadratureCount)
	{
		try
		{
			return new ModifiedSecondIntegralEstimator (quadratureCount)
			{
				@Override public double bigK (
					final double alpha,
					final double z)
					throws java.lang.Exception
				{
					if (2. / 3. != alpha ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("ModifiedSecondIntegralEstimator::TwoThirdOrder::evaluate => Invalid Inputs");
					}

					return 1. / java.lang.Math.sqrt (3.) *
					org.drip.numerical.integration.NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
						0.,
						quadratureCount
					).integrate (
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double x)
								throws java.lang.Exception
							{
								if (java.lang.Double.isInfinite (x))
								{
									return 0.;
								}

								double xSquared = x * x;
								double xSquaredOver3 = xSquared / 3.;

								double sqrt_OnePlusXSquaredOver3_ = java.lang.Math.sqrt (1. + xSquaredOver3);

								return (3. + 2. * xSquared) / sqrt_OnePlusXSquaredOver3_ *
									java.lang.Math.exp (
										-z * (1. + 4. * xSquaredOver3) * sqrt_OnePlusXSquaredOver3_
									);
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

	protected ModifiedSecondIntegralEstimator (
		final int quadratureCount)
		throws java.lang.Exception
	{
		if (0 >= (_quadratureCount = quadratureCount))
		{
			throw new java.lang.Exception ("ModifiedSecondIntegralEstimator Constructor => Invalid Inputs");
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
