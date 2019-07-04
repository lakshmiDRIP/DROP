
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
 * <i>BesselSecondEqualityLemma</i> implements the implements the Equality Lemmas for the Cylindrical Bessel
 * Function of the Second Kind. The References are:
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

public class BesselSecondEqualityLemma
{

	/**
	 * Construct the Bessel Second Kind Mirror Identity Verifier
	 * 
	 * @return The Bessel Second Kind Mirror Identity Verifier
	 */

	public static final org.drip.function.definition.R2ToR1Property MirrorIdentity()
	{
		try
		{
			org.drip.function.definition.R1ToR1 gammaEstimator = new
				org.drip.specialfunction.gamma.EulerIntegralSecondKind (null);

			final org.drip.specialfunction.definition.BesselSecondKindEstimator besselSecondKindEstimator =
				org.drip.specialfunction.bessel.SecondNISTSeriesEstimator.Standard (
					new org.drip.specialfunction.digamma.BinetFirstIntegral (null),
					gammaEstimator,
					org.drip.specialfunction.bessel.FirstFrobeniusSeriesEstimator.Standard (
						gammaEstimator,
						40
					),
					40
				);

			return new org.drip.function.definition.R2ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double alpha,
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsInteger (alpha))
						{
							throw new java.lang.Exception
								("BesselFirstEqualityLemma::MirrorIdentity => Invalid Inputs");
						}

						return besselSecondKindEstimator.bigY (
							-1. * alpha,
							z
						);
					}
				},
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double alpha,
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsInteger (alpha))
						{
							throw new java.lang.Exception
								("BesselFirstEqualityLemma::MirrorIdentity => Invalid Inputs");
						}

						return (0 == ((int) z) % 2 ? 1. : -1.) * besselSecondKindEstimator.bigY (
							alpha,
							z
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
	 * Construct the Bessel Second Kind Half-Integer Identity Verifier
	 * 
	 * @return The Bessel Second Kind Half-Integer Identity Verifier
	 */

	public static final org.drip.function.definition.R2ToR1Property HalfIntegerIdentity()
	{
		org.drip.function.definition.R1ToR1 gammaEstimator = new
			org.drip.specialfunction.gamma.EulerIntegralSecondKind (null);

		final org.drip.specialfunction.definition.BesselFirstKindEstimator besselFirstKindEstimator =
			org.drip.specialfunction.bessel.FirstFrobeniusSeriesEstimator.Standard (
				gammaEstimator,
				50
			);

		try
		{
			final org.drip.specialfunction.definition.BesselSecondKindEstimator besselSecondKindEstimator =
				org.drip.specialfunction.bessel.SecondNISTSeriesEstimator.Standard (
					new org.drip.specialfunction.digamma.BinetFirstIntegral (null),
					gammaEstimator,
					org.drip.specialfunction.bessel.FirstFrobeniusSeriesEstimator.Standard (
						gammaEstimator,
						40
					),
					40
				);

			return new org.drip.function.definition.R2ToR1Property (
				org.drip.function.definition.RxToR1Property.EQ,
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double alpha,
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsInteger (alpha))
						{
							throw new java.lang.Exception
								("BesselSecondEqualityLemma::HalfIntegerIdentity => Invalid Inputs");
						}

						return besselSecondKindEstimator.bigY (
							-1. * (alpha + 0.5),
							z
						);
					}
				},
				new org.drip.function.definition.R2ToR1()
				{
					@Override public double evaluate (
						final double alpha,
						final double z)
						throws java.lang.Exception
					{
						if (!org.drip.numerical.common.NumberUtil.IsInteger (alpha))
						{
							throw new java.lang.Exception
								("BesselSecondEqualityLemma::HalfIntegerIdentity => Invalid Inputs");
						}

						return (0 == ((int) (alpha)) % 2 ? 1. : -1.) * besselFirstKindEstimator.bigJ (
							alpha + 0.5,
							z
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
