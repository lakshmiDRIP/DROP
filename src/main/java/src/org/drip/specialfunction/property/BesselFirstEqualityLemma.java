
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
 * <i>BesselFirstEqualityLemma</i> implements the implements the Equality Lemmas for the Cylindrical Bessel
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/property/README.md">Special Function Property Lemma Verifiers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BesselFirstEqualityLemma
{

	/**
	 * Construct the Bessel First Kind Mirror Identity Verifier
	 * 
	 * @return The Bessel First Kind Mirror Identity Verifier
	 */

	public static final org.drip.function.definition.R2ToR1Property MirrorIdentity()
	{
		final org.drip.specialfunction.bessel.FirstFrobeniusSeriesEstimator frobeniusEstimator =
			org.drip.specialfunction.bessel.FirstFrobeniusSeriesEstimator.Standard (
				new org.drip.specialfunction.gamma.EulerIntegralSecondKind (null),
				50
			);

		try
		{
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

						return frobeniusEstimator.bigJ (
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

						return (0 == ((int) z) % 2 ? 1. : -1.) * frobeniusEstimator.bigJ (
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
	 * Construct the Bessel First Kind Half-Integer Identity Verifier
	 * 
	 * @return The Bessel First Kind Half-Integer Identity Verifier
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
								("BesselFirstEqualityLemma::HalfIntegerIdentity => Invalid Inputs");
						}

						return besselFirstKindEstimator.bigJ (
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
								("BesselFirstEqualityLemma::HalfIntegerIdentity => Invalid Inputs");
						}

						return (0 == ((int) (alpha + 1)) % 2 ? 1. : -1.) * besselSecondKindEstimator.bigY (
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
