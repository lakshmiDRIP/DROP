
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
 * <i>GaussContiguousEqualityLemma</i> verifies the Hyper-geometric Gauss Contiguous Equality Lemma
 * Properties. The References are:
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

public class GaussContiguousEqualityLemma
{

	/**
	 * Construct the Hyper-geometric Gauss Contiguous Identity #2 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #2 Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property RelationIdentity2 (
		final double a,
		final double b,
		final double c)
	{
		org.drip.function.definition.R2ToR1 logBetaEstimator =
			org.drip.specialfunction.beta.LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator)
		{
			return null;
		}

		try
		{
			final org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator hypergeometricEstimator =
				new org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator (
					new org.drip.specialfunction.definition.HypergeometricParameters (
						a,
						b,
						c
					),
					logBetaEstimator,
					10000
				);

			final org.drip.specialfunction.definition.RegularHypergeometricEstimator
				aPlusRegularHypergeometricEstimator = new
					org.drip.specialfunction.hypergeometric.GaussContiguousRelations
						(hypergeometricEstimator).aPlus();

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return z * hypergeometricEstimator.derivative (
							z,
							1
						);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return a * (
							aPlusRegularHypergeometricEstimator.regularHypergeometric (z) -
							hypergeometricEstimator.regularHypergeometric (z)
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
	 * Construct the Hyper-geometric Gauss Contiguous Identity #3 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #3 Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property RelationIdentity3 (
		final double a,
		final double b,
		final double c)
	{
		org.drip.function.definition.R2ToR1 logBetaEstimator =
			org.drip.specialfunction.beta.LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator)
		{
			return null;
		}

		try
		{
			final org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator hypergeometricEstimator =
				new org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator (
					new org.drip.specialfunction.definition.HypergeometricParameters (
						a,
						b,
						c
					),
					logBetaEstimator,
					10000
				);

			final org.drip.specialfunction.definition.RegularHypergeometricEstimator
				bPlusHypergeometricEstimator = new
					org.drip.specialfunction.hypergeometric.GaussContiguousRelations
						(hypergeometricEstimator).bPlus();

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return z * hypergeometricEstimator.derivative (
							z,
							1
						);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return b * (
							bPlusHypergeometricEstimator.regularHypergeometric (z) -
							hypergeometricEstimator.regularHypergeometric (z)
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
	 * Construct the Hyper-geometric Gauss Contiguous Identity #4 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #4 Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property RelationIdentity4 (
		final double a,
		final double b,
		final double c)
	{
		org.drip.function.definition.R2ToR1 logBetaEstimator =
			org.drip.specialfunction.beta.LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator)
		{
			return null;
		}

		try
		{
			final org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator hypergeometricEstimator =
				new org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator (
					new org.drip.specialfunction.definition.HypergeometricParameters (
						a,
						b,
						c
					),
					logBetaEstimator,
					100000
				);

			final org.drip.specialfunction.definition.RegularHypergeometricEstimator
				cMinusHypergeometricEstimator = new
					org.drip.specialfunction.hypergeometric.GaussContiguousRelations
						(hypergeometricEstimator).cMinus();

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return z * hypergeometricEstimator.derivative (
							z,
							1
						);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return (c - 1.) * (
							cMinusHypergeometricEstimator.regularHypergeometric (z) -
							hypergeometricEstimator.regularHypergeometric (z)
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
	 * Construct the Hyper-geometric Gauss Contiguous Identity #5 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #5 Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property RelationIdentity5 (
		final double a,
		final double b,
		final double c)
	{
		org.drip.function.definition.R2ToR1 logBetaEstimator =
			org.drip.specialfunction.beta.LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator)
		{
			return null;
		}

		try
		{
			final org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator hypergeometricEstimator =
				new org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator (
					new org.drip.specialfunction.definition.HypergeometricParameters (
						a,
						b,
						c
					),
					logBetaEstimator,
					100000
				);

			final org.drip.specialfunction.definition.RegularHypergeometricEstimator
				aMinusHypergeometricEstimator = new
					org.drip.specialfunction.hypergeometric.GaussContiguousRelations
						(hypergeometricEstimator).aMinus();

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return z * hypergeometricEstimator.derivative (
							z,
							1
						);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return (
							(c - a) * aMinusHypergeometricEstimator.regularHypergeometric (z) +
							(a - c + b * z) * hypergeometricEstimator.regularHypergeometric (z)
						) / (1. - z);
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
	 * Construct the Hyper-geometric Gauss Contiguous Identity #6 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #6 Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property RelationIdentity6 (
		final double a,
		final double b,
		final double c)
	{
		org.drip.function.definition.R2ToR1 logBetaEstimator =
			org.drip.specialfunction.beta.LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator)
		{
			return null;
		}

		try
		{
			final org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator hypergeometricEstimator =
				new org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator (
					new org.drip.specialfunction.definition.HypergeometricParameters (
						a,
						b,
						c
					),
					logBetaEstimator,
					100000
				);

			final org.drip.specialfunction.definition.RegularHypergeometricEstimator
				bMinusHypergeometricEstimator = new
					org.drip.specialfunction.hypergeometric.GaussContiguousRelations
						(hypergeometricEstimator).bMinus();

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return z * hypergeometricEstimator.derivative (
							z,
							1
						);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return (
							(c - b) * bMinusHypergeometricEstimator.regularHypergeometric (z) +
							(b - c + a * z) * hypergeometricEstimator.regularHypergeometric (z)
						) / (1. - z);
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
	 * Construct the Hyper-geometric Gauss Contiguous Identity #7 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #7 Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property RelationIdentity7 (
		final double a,
		final double b,
		final double c)
	{
		org.drip.function.definition.R2ToR1 logBetaEstimator =
			org.drip.specialfunction.beta.LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator)
		{
			return null;
		}

		try
		{
			final org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator hypergeometricEstimator =
				new org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator (
					new org.drip.specialfunction.definition.HypergeometricParameters (
						a,
						b,
						c
					),
					logBetaEstimator,
					100000
				);

			final org.drip.specialfunction.definition.RegularHypergeometricEstimator
				cPlusHypergeometricEstimator = new
					org.drip.specialfunction.hypergeometric.GaussContiguousRelations
						(hypergeometricEstimator).cPlus();

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return hypergeometricEstimator.derivative (
							z,
							1
						);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return (
							(c - a) * (c - b) * cPlusHypergeometricEstimator.regularHypergeometric (z) +
							c * (a + b - c) * hypergeometricEstimator.regularHypergeometric (z)
						) / (c * (1. - z));
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
