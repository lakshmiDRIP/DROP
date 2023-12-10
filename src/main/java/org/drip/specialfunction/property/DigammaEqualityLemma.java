
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
 * <i>DigammaEqualityLemma</i> contains the Verifiable Equality Lemmas of the Digamma Function. The
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/property/README.md">Special Function Property Lemma Verifiers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DigammaEqualityLemma
{

	/**
	 * Generate the Digamma (0, 1) Reflection Formula Verifier
	 * 
	 * @return The Digamma (0, 1) Reflection Formula Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property ReflectionFormula()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::ReflectionFormula::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (1. - z) - abramowitzStegun2007.evaluate (z);
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
								("DigammaEqualityLemma::ReflectionFormula::evaluate => Invalid Inputs");
						}

						return java.lang.Math.PI / java.lang.Math.tan (java.lang.Math.PI * z);
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
	 * Generate the Gaussian Finite Summation Identity Verifier #1
	 * 
	 * @return The Gaussian Finite Summation Identity Verifier #1
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity1()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity1::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r <= m; ++r)
						{
							summation = summation + abramowitzStegun2007.evaluate (((double) r) / m);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity1::evaluate => Invalid Inputs");
						}

						return -m * (org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI +
							java.lang.Math.log (m));
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
	 * Generate the Gaussian Finite Summation Identity Verifier #2
	 * 
	 * @param k k
	 * 
	 * @return The Gaussian Finite Summation Identity Verifier #2
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity2 (
		final int k)
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m || m <= k)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity2::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double z = ((double) r) / m;

							summation = summation + abramowitzStegun2007.evaluate (z) *
								java.lang.Math.cos (2. * java.lang.Math.PI * k * z);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m || m <= k)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity2::evaluate => Invalid Inputs");
						}

						return m * java.lang.Math.log (2. * java.lang.Math.sin (java.lang.Math.PI * k / m)) +
							org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
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
	 * Generate the Gaussian Finite Summation Identity Verifier #3
	 * 
	 * @param k k
	 * 
	 * @return The Gaussian Finite Summation Identity Verifier #3
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity3 (
		final int k)
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m || m <= k)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity3::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double z = ((double) r) / m;

							summation = summation + abramowitzStegun2007.evaluate (z) *
								java.lang.Math.sin (2. * java.lang.Math.PI * k * z);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m || m <= k)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity3::evaluate => Invalid Inputs");
						}

						return 0.5 * java.lang.Math.PI * (2. * k - m);
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
	 * Generate the Blagouchine Finite Summation Identity Verifier #4
	 * 
	 * @param k k
	 * 
	 * @return The Blagouchine Finite Summation Identity Verifier #4
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity4 (
		final int k)
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m || m <= k)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity4::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 0; r < m; ++r)
						{
							double z = (2. * r + 1.) / (2. * m);

							summation = summation + abramowitzStegun2007.evaluate (z) *
								java.lang.Math.cos (2. * java.lang.Math.PI * k * z);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m || m <= k)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity4::evaluate => Invalid Inputs");
						}

						return m * java.lang.Math.log (java.lang.Math.tan (0.5 * java.lang.Math.PI * k / m));
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
	 * Generate the Blagouchine Finite Summation Identity Verifier #5
	 * 
	 * @param k k
	 * 
	 * @return The Blagouchine Finite Summation Identity Verifier #5
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity5 (
		final int k)
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m || m <= k)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity5::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 0; r < m; ++r)
						{
							double z = (2. * r + 1.) / (2. * m);

							summation = summation + abramowitzStegun2007.evaluate (z) *
								java.lang.Math.sin (2. * java.lang.Math.PI * k * z);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m || m <= k)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity5::evaluate => Invalid Inputs");
						}

						return -0.5 * java.lang.Math.PI * m;
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
	 * Generate the Blagouchine Finite Summation Identity Verifier #6
	 * 
	 * @return The Blagouchine Finite Summation Identity Verifier #6
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity6()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity6::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double z = ((double) r) / m;

							summation = summation + abramowitzStegun2007.evaluate (z) /
								java.lang.Math.tan (java.lang.Math.PI * z);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity6::evaluate => Invalid Inputs");
						}

						return -java.lang.Math.PI * (m - 1.) * (m - 2.) / 6.;
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
	 * Generate the Blagouchine Finite Summation Identity Verifier #7
	 * 
	 * @return The Blagouchine Finite Summation Identity Verifier #7
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity7()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity7::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double z = ((double) r) / m;

							summation = summation + z * abramowitzStegun2007.evaluate (z);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity7::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double z = ((double) r) / m;

							summation = summation + z / java.lang.Math.tan (java.lang.Math.PI * z);
						}

						return -0.5 * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI * (m - 1.)
							- 0.5 * m * java.lang.Math.log (m)
							- 0.5 * java.lang.Math.PI * summation;
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
	 * Generate the Blagouchine Finite Summation Identity Verifier #8
	 * 
	 * @param l l
	 * 
	 * @return The Blagouchine Finite Summation Identity Verifier #8
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity8 (
		final double l)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (l))
		{
			return null;
		}

		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity8::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double z = ((double) r) / m;

							summation = summation + abramowitzStegun2007.evaluate (z) *
								java.lang.Math.cos ((2. * l + 1.) * java.lang.Math.PI * z);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity8::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double z = ((double) r) / m;

							double theta = 2. * java.lang.Math.PI * z;

							summation = summation + r * java.lang.Math.sin (theta) / (
								java.lang.Math.cos (theta) -
								java.lang.Math.cos ((2. * l + 1.) * java.lang.Math.PI / m)
							);
						}

						return -1. * java.lang.Math.PI / m * summation;
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
	 * Generate the Blagouchine Finite Summation Identity Verifier #9
	 * 
	 * @param l l
	 * 
	 * @return The Blagouchine Finite Summation Identity Verifier #9
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity9 (
		final double l)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (l))
		{
			return null;
		}

		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity9::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double z = ((double) r) / m;

							summation = summation + abramowitzStegun2007.evaluate (z) *
								java.lang.Math.sin ((2. * l + 1.) * java.lang.Math.PI * z);
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity9::evaluate => Invalid Inputs");
						}

						double summation = 0.;
						double twoLPlus1PiByM = (2. * l + 1.) * java.lang.Math.PI / m;

						double cos2LPlus1PiByM = java.lang.Math.cos (twoLPlus1PiByM);

						for (int r = 1; r < m; ++r)
						{

							double z = ((double) r) / m;

							double theta = java.lang.Math.PI * z;

							summation = summation + java.lang.Math.log (java.lang.Math.sin (theta)) /
								(java.lang.Math.cos (2. * theta) - cos2LPlus1PiByM);
						}

						return java.lang.Math.sin (twoLPlus1PiByM) * summation - 
							(
								org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI +
								java.lang.Math.log (2. * m)
							) / java.lang.Math.tan (0.5 * twoLPlus1PiByM);
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
	 * Generate the Blagouchine Finite Summation Identity Verifier #10
	 * 
	 * @return The Blagouchine Finite Summation Identity Verifier #10
	 */

	public static final org.drip.function.definition.R1ToR1Property SummationIdentity10()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity10::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int r = 1; r < m; ++r)
						{
							double digamma = abramowitzStegun2007.evaluate (r) / m;

							summation = summation + digamma * digamma;
						}

						return summation;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double m)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (m) || 0. >= m)
						{
							throw new java.lang.Exception
								("DigammaEqualityLemma::SummationIdentity10::evaluate => Invalid Inputs");
						}

						double summation = 0.;

						for (int l = 1; l < m; ++l)
						{
							double logSinPiLByM = java.lang.Math.log (
								java.lang.Math.sin (
									java.lang.Math.PI * l / m
								)
							);

							summation = summation + logSinPiLByM * logSinPiLByM;
						}

						double log2 = java.lang.Math.log (2.);

						return
							(m - 1.) * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI *
								org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI +
							m * (
								2. * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI +
								java.lang.Math.log (4. * m)
							) * java.lang.Math.log (m) -
							log2 * log2 * m * (m - 1.) +
							java.lang.Math.PI * java.lang.Math.PI / 12. * (2. - 3. * m + m * m) +
							m * summation;
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
