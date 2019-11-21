
package org.drip.specialfunction.definition;

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
 * <i>BesselFirstKindEstimator</i> exposes the Estimator for the Bessel Function of the First Kind. The
 * References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/definition/README.md">Definition of Special Function Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class BesselFirstKindEstimator implements org.drip.function.definition.R2ToR1
{

	/**
	 * Construct the Alpha Positive Integer or Zero Asymptotic Version of BesselFirstKindEstimator
	 * 
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @return Alpha Positive Integer or Zero Asymptotic Version of BesselFirstKindEstimator
	 */

	public static final BesselFirstKindEstimator AlphaPositiveIntegerOrZeroAsymptote (
		final org.drip.function.definition.R1ToR1 gammaEstimator)
	{
		return null == gammaEstimator ? null : new BesselFirstKindEstimator()
		{
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsInteger (alpha) || 0. > alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("BesselFirstKindEstimator::AlphaPositiveIntegerOrZeroAsymptote => Invalid Inputs");
				}

				return java.lang.Math.pow (
					0.5 * z,
					alpha
				) / gammaEstimator.evaluate (alpha + 1.);
			}
		};
	}

	/**
	 * Construct the Alpha Negative Integer Asymptotic Version of BesselFirstKindEstimator
	 * 
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @return Alpha Negative Integer Asymptotic Version of BesselFirstKindEstimator
	 */

	public static final BesselFirstKindEstimator AlphaNegativeIntegerAsymptote (
		final org.drip.function.definition.R1ToR1 gammaEstimator)
	{
		return null == gammaEstimator ? null : new BesselFirstKindEstimator()
		{
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsNegativeInteger (alpha) ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("BesselFirstKindEstimator::AlphaNegativeIntegerAsymptote => Invalid Inputs");
				}

				double negativeAlpha = -1. * alpha;

				return (0 == negativeAlpha % 2 ? 1. : -1.) * java.lang.Math.pow (
					0.5 * z,
					negativeAlpha
				) / gammaEstimator.evaluate (negativeAlpha);
			}
		};
	}

	/**
	 * Construct the High z Asymptotic Version of BesselFirstKindEstimator
	 * 
	 * @return High z Asymptotic Version of BesselFirstKindEstimator
	 */

	public static final BesselFirstKindEstimator HighZAsymptote()
	{
		return new BesselFirstKindEstimator()
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
						("BesselFirstKindEstimator::HighZAsymptote => Invalid Inputs");
				}

				return java.lang.Math.sqrt (2. / java.lang.Math.PI / z) * java.lang.Math.cos (
					z - 0.5 * java.lang.Math.PI * alpha - 0.25 * java.lang.Math.PI
				);
			}
		};
	}

	/**
	 * Construct the Alpha=0 Negative z Asymptotic Version of BesselFirstKindEstimator
	 * 
	 * @return Alpha=0 Negative z Asymptotic Version of BesselFirstKindEstimator
	 */

	public static final BesselFirstKindEstimator AlphaZeroNegativeZAsymptote()
	{
		return new BesselFirstKindEstimator()
		{
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (0. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. < z)
				{
					throw new java.lang.Exception
						("BesselFirstKindEstimator::AlphaZeroNegativeZAsymptote => Invalid Inputs");
				}

				return java.lang.Math.sqrt (-2. / java.lang.Math.PI / z) * java.lang.Math.cos (
					z + 0.25 * java.lang.Math.PI
				);
			}
		};
	}

	public static final BesselFirstKindEstimator AlphaZeroApproximate()
	{
		return new BesselFirstKindEstimator()
		{
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws java.lang.Exception
			{
				if (0. != alpha ||
					!org.drip.numerical.common.NumberUtil.IsValid (z))
				{
					throw new java.lang.Exception
						("BesselFirstKindEstimator::AlphaZeroApproximate => Invalid Inputs");
				}

				double oneOver_OnePlus__zOver7_Power20__ = 1. / (
					1 + java.lang.Math.pow (
						z / 7.,
						20.
					)
				);

				double zAbsolute = java.lang.Math.abs (z);

				double zOver2 = z / 2.;
				double zSign = 0 == z ? 1. : zAbsolute / z;

				return oneOver_OnePlus__zOver7_Power20__ * (
					(1. + java.lang.Math.cos (z)) / 6. + (
						java.lang.Math.cos (zOver2) + java.lang.Math.cos (java.lang.Math.sqrt (3.) * zOver2)
					) / 3.
				) +
				(1. - oneOver_OnePlus__zOver7_Power20__) * java.lang.Math.sqrt (
					2. / java.lang.Math.PI / zAbsolute
				) * java.lang.Math.cos (
					z -  0.25 * java.lang.Math.PI * zSign
				);
			}
		};
	}

	/**
	 * Evaluate Bessel Function First Kind J given Alpha and z
	 * 
	 * @param alpha Alpha
	 * @param z Z
	 *  
	 * @return Bessel Function First Kind J Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double bigJ (
		final double alpha,
		final double z)
		throws java.lang.Exception;

	@Override public double evaluate (
		final double alpha,
		final double z)
		throws java.lang.Exception
	{
		return bigJ (
			alpha,
			z
		);
	}
}
