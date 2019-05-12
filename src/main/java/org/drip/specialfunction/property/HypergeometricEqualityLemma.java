
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
 * <i>HypergeometricEqualityLemma</i> verifies the Hyper-geometric Equality Lemma Properties. The References
 * are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/definition/README.md">Definition of Special Function Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HypergeometricEqualityLemma
{

	/**
	 * Construct the First-Order Derivative Switch Verifier
	 * 
	 * @param a A
	 * @param b B
	 * 
	 * @return The First-Order Derivative Switch Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property FirstOrderDerivativeSwitch (
		final double a,
		final double b)
	{
		org.drip.function.definition.R2ToR1 logBetaEstimator =
			org.drip.specialfunction.beta.LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator)
		{
			return null;
		}

		try
		{
			final org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator hypergeometricEstimator1 =
				new org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator (
					a,
					b,
					a + 1,
					logBetaEstimator,
					10000
				);

			final org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator hypergeometricEstimator2 =
				new org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator (
					b,
					a,
					a + 1,
					logBetaEstimator,
					10000
				);

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return hypergeometricEstimator1.derivative (
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
						return hypergeometricEstimator2.derivative (
							z,
							1
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
	 * Construct the First-Order Derivative Special Case Verifier
	 * 
	 * @param a A
	 * @param b B
	 * 
	 * @return The First-Order Derivative Special Case Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property FirstOrderDerivativeSpecialCase (
		final double a,
		final double b)
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
					a,
					b,
					a + 1,
					logBetaEstimator,
					10000
				);

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
						return a / z * (
							java.lang.Math.pow (
								1. - z,
								-b
							) - hypergeometricEstimator.regularHypergeometric (z)
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
	 * Construct the Log (1 + z) Special Case Verifier
	 * 
	 * @return The Log (1 + z) Special Case Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property LogOnePlusZ()
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
					1.,
					1.,
					2.,
					logBetaEstimator,
					10000
				);

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return z * hypergeometricEstimator.evaluate (-z);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return java.lang.Math.log (1. + z);
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
	 * Construct the Inverse Power A Special Case Verifier
	 * 
	 * @param a A
	 * 
	 * @return The Inverse Power A Special Case Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property InversePowerA (
		final double a)
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
					a,
					1.,
					1.,
					logBetaEstimator,
					10000
				);

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return hypergeometricEstimator.evaluate (z);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return java.lang.Math.pow (
							1. - z,
							-a
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
	 * Construct the Inverse Sine Special Case Verifier
	 * 
	 * @return The Inverse Sine Special Case Verifier
	 */

	public static final org.drip.function.definition.R1ToR1Property InverseSine()
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
					0.5,
					0.5,
					1.5,
					logBetaEstimator,
					10000
				);

			return new org.drip.function.definition.R1ToR1Property (
				org.drip.function.definition.R1ToR1Property.EQ,
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return z * hypergeometricEstimator.evaluate (z * z);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return java.lang.Math.asin (z);
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
