
package org.drip.specialfunction.property;

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
 * <i>IncompleteBetaEqualityLemma</i> implements the Equality Lemmas for the Incomplete Beta Estimation. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
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
 * 			Wikipedia (2019): Beta Function https://en.wikipedia.org/wiki/Beta_function
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/property/README.md">Special Function Property Lemma Verifiers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IncompleteBetaEqualityLemma
{

	/**
	 * Construct the Identity #1 Verifier
	 * 
	 * @return The Identity #1 Verifier
	 */

	public static final org.drip.function.definition.R2ToR1Property Identity1()
	{
		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000)
						);

			return new org.drip.function.definition.R2ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity1::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							0.,
							a,
							b
						);
					}
				},
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double y)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (y) || 0. >= y)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity1::evaluate => Invalid Inputs");
						}

						return 0.;
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
	 * Construct the Identity #2 Verifier
	 * 
	 * @return The Identity #2 Verifier
	 */

	public static final org.drip.function.definition.R2ToR1Property Identity2()
	{
		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000)
						);

			return new org.drip.function.definition.R2ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity2::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							1.,
							a,
							b
						);
					}
				},
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double y)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (y) || 0. >= y)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity2::evaluate => Invalid Inputs");
						}

						return 1.;
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
	 * Construct the Identity #3 Verifier
	 * 
	 * @return The Identity #3 Verifier
	 */

	public static final org.drip.function.definition.R2ToR1Property Identity3()
	{
		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000)
						);

			return new org.drip.function.definition.R2ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity3::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x,
							a,
							1.
						);
					}
				},
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity3::evaluate => Invalid Inputs");
						}

						return java.lang.Math.pow (
							x,
							a
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
	 * Construct the Identity #4 Verifier
	 * 
	 * @return The Identity #4 Verifier
	 */

	public static final org.drip.function.definition.R2ToR1Property Identity4()
	{
		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000)
						);

			return new org.drip.function.definition.R2ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity4::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x,
							1.,
							b
						);
					}
				},
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity4::evaluate => Invalid Inputs");
						}

						return 1. - java.lang.Math.pow (
							1. - x,
							b
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
	 * Construct the Identity #5 Verifier
	 * 
	 * @return The Identity #5 Verifier
	 */

	public static final org.drip.function.definition.R3ToR1Property Identity5()
	{
		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000)
				);

			return new org.drip.function.definition.R3ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity5::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x,
							a,
							b
						);
					}
				},
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity5::evaluate => Invalid Inputs");
						}

						return 1. - incompleteRegularizedEstimator.evaluate (
							1. - x,
							b,
							a
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
	 * Construct the Identity #6 Verifier
	 * 
	 * @return The Identity #6 Verifier
	 */

	public static final org.drip.function.definition.R3ToR1Property Identity6()
	{
		final org.drip.specialfunction.beta.IntegrandEstimator integrandEstimator =
			org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000);

		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					integrandEstimator
				);

			return new org.drip.function.definition.R3ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity6::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x,
							a + 1.,
							b
						);
					}
				},
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity6::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x,
							a,
							b
						) - java.lang.Math.pow (
							x,
							a
						) * java.lang.Math.pow (
							1. - x,
							b
						) / a / integrandEstimator.evaluate (
							a,
							b
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
	 * Construct the Identity #7 Verifier
	 * 
	 * @return The Identity #7 Verifier
	 */

	public static final org.drip.function.definition.R3ToR1Property Identity7()
	{
		final org.drip.specialfunction.beta.IntegrandEstimator integrandEstimator =
			org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000);

		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					integrandEstimator
				);

			return new org.drip.function.definition.R3ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity7::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x,
							a,
							b + 1.
						);
					}
				},
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity7::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x,
							a,
							b
						) + java.lang.Math.pow (
							x,
							a
						) * java.lang.Math.pow (
							1. - x,
							b
						) / b / integrandEstimator.evaluate (
							a,
							b
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
	 * Construct the Identity #8 Verifier
	 * 
	 * @return The Identity #8 Verifier
	 */

	public static final org.drip.function.definition.R3ToR1Property Identity8()
	{
		final org.drip.specialfunction.beta.IntegrandEstimator integrandEstimator =
			org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000);

		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					integrandEstimator
				);

			return new org.drip.function.definition.R3ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity8::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x,
							a,
							b
						);
					}
				},
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double x,
						final double a,
						final double b)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x ||
							!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. >= a ||
							!org.drip.numerical.common.NumberUtil.IsValid (b) || 0. >= b)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::Identity8::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							x / (1. - x),
							a,
							1. - a - b
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
	 * Construct the Cumulative Binomial Distribution Verifier
	 * 
	 * @return The Cumulative Binomial Distribution Verifier
	 */

	public static final org.drip.function.definition.R3ToR1Property CumulativeBinomialDistribution()
	{
		try
		{
			final org.drip.specialfunction.beta.IncompleteRegularizedEstimator incompleteRegularizedEstimator
				= new org.drip.specialfunction.beta.IncompleteRegularizedEstimator (
					org.drip.specialfunction.beta.IncompleteIntegrandEstimator.EulerFirst (1000),
					org.drip.specialfunction.beta.IntegrandEstimator.EulerFirstRightPlane (1000)
				);

			return new org.drip.function.definition.R3ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double p,
						final double n,
						final double k)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (p) || 0. >= p ||
							!org.drip.numerical.common.NumberUtil.IsValid (n) || 0. >= n ||
							!org.drip.numerical.common.NumberUtil.IsValid (k) || 0. >= k)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::CumulativeBinomialDistribution::evaluate => Invalid Inputs");
						}

						return incompleteRegularizedEstimator.evaluate (
							1. - p,
							n - k,
							k + 1.
						);
					}
				},
				new org.drip.function.definition.R3ToR1()
				{
					@Override public double evaluate (
						final double p,
						final double n,
						final double k)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (p) || 0. >= p ||
							!org.drip.numerical.common.NumberUtil.IsValid (n) || 0. >= n ||
							!org.drip.numerical.common.NumberUtil.IsValid (k) || 0. >= k)
						{
							throw new java.lang.Exception
								("IncompleteBetaEqualityLemma::CumulativeBinomialDistribution::evaluate => Invalid Inputs");
						}

						return 1. - incompleteRegularizedEstimator.evaluate (
							p,
							k + 1.,
							n - k
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
}
