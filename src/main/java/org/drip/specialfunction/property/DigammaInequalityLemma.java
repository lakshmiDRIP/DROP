
package org.drip.specialfunction.property;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>DigammaInequalityLemma</i> contains the Verifiable Inequality Lemmas for the Digamma Function. The
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

public class DigammaInequalityLemma
{

	/**
	 * Generate the Digamma Asymptotic Left Inequality Verifier
	 * 
	 * @return The Digamma Asymptotic Left Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property LeftAsymptote()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::LeftAsymptote::evaluate => Invalid Inputs");
						}

						return java.lang.Math.log (z - 0.5);
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
								("DigammaInequalityLemma::LeftAsymptote::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z);
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
	 * Generate the Digamma Asymptotic Right Inequality Verifier
	 * 
	 * @return The Digamma Asymptotic Right Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property RightAsymptote()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::RightAsymptote::evaluate => Invalid Inputs");
						}

						return java.lang.Math.log (z);
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
								("DigammaInequalityLemma::RightAsymptote::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z);
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
	 * Generate the Digamma (0, 1) Left Bound Inequality Verifier
	 * 
	 * @return The Digamma (0, 1) Left Bound Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property ZeroOneLeftBound()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::ZeroOneLeftBound::evaluate => Invalid Inputs");
						}

						return -1. / z - org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
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
								("DigammaInequalityLemma::ZeroOneLeftBound::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z);
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
	 * Generate the Digamma (0, 1) Right Bound Verifier
	 * 
	 * @return The Digamma (0, 1) Right Bound Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property ZeroOneRightBound()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::ZeroOneRightBound::evaluate => Invalid Inputs");
						}

						return 1. - 1. / z - org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
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
								("DigammaInequalityLemma::ZeroOneRightBound::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z);
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
	 * Generate the Bernstein-Binet Left Bound Inequality Verifier
	 * 
	 * @return The Bernstein-Binet Left Bound Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property BernsteinBinetLeftBound()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::BernsteinBinetLeftBound::evaluate => Invalid Inputs");
						}

						return java.lang.Math.log (z) - 1. / z;
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
								("DigammaInequalityLemma::BernsteinBinetLeftBound::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z);
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
	 * Generate the Bernstein-Binet Right Bound Inequality Verifier
	 * 
	 * @return The Bernstein-Binet Right Bound Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property BernsteinBinetRightBound()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::BernsteinBinetRightBound::evaluate => Invalid Inputs");
						}

						return java.lang.Math.log (z) - 0.5 / z;
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
								("DigammaInequalityLemma::BernsteinBinetRightBound::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z);
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
	 * Generate the Elezovic-Giordano-Pecaric Left Bound Inequality Verifier
	 * 
	 * @return The Elezovic-Giordano-Pecaric Left Bound Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property ElezovicGiordanoPecaricLeftBound()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::ElezovicGiordanoPecaricLeftBound::evaluate => Invalid Inputs");
						}

						return java.lang.Math.log (z + 0.5) - 1. / z;
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
								("DigammaInequalityLemma::ElezovicGiordanoPecaricLeftBound::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z);
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
	 * Generate the Elezovic-Giordano-Pecaric Right Bound Inequality Verifier
	 * 
	 * @return The Elezovic-Giordano-Pecaric Right Bound Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property ElezovicGiordanoPecaricRightBound()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::ElezovicGiordanoPecaricRightBound::evaluate => Invalid Inputs");
						}

						return java.lang.Math.log (
							z + java.lang.Math.exp (
								-org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI
							)
						) - 1. / z;
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
								("DigammaInequalityLemma::ElezovicGiordanoPecaricRightBound::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z);
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
	 * Generate the Alzer (1997) Digamma Difference Lemma Verifier
	 * 
	 * @param s s
	 * 
	 * @return The Alzer (1997) Digamma Difference Lemma Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property AlzerDifference1997 (
		final double s)
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

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
								("DigammaInequalityLemma::AlzerDifference1997::evaluate => Invalid Inputs");
						}

						return (1. - s) / (z + s);
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
								("DigammaInequalityLemma::AlzerDifference1997::evaluate => Invalid Inputs");
						}

						return abramowitzStegun2007.evaluate (z + 1) - abramowitzStegun2007.evaluate (z + s);
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
	 * Generate the Alzer-Jameson (2017) Inequality Verifier
	 * 
	 * @return The Alzer-Jameson (2017) Inequality Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property AlzerJameson2017()
	{
		final org.drip.specialfunction.digamma.CumulativeSeriesEstimator abramowitzStegun2007 =
			org.drip.specialfunction.digamma.CumulativeSeriesEstimator.AbramowitzStegun2007 (1638400);

		try
		{
			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.LTE,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsValid (z))
						{
							throw new java.lang.Exception
								("DigammaInequalityLemma::AlzerJameson2017::evaluate => Invalid Inputs");
						}

						return -1. * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
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
								("DigammaInequalityLemma::AlzerJameson2017::evaluate => Invalid Inputs");
						}

						double digammaZ = abramowitzStegun2007.evaluate (z);

						double digammaZInverse = abramowitzStegun2007.evaluate (1. / z);

						return 2. * digammaZ * digammaZInverse / (digammaZ + digammaZInverse);
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
