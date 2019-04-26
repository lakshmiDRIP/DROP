
package org.drip.specialfunction.digamma;

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
 * <i>CumulativeSeriesTerm</i> implements a Single Term in the Cumulative Series for Digamma Estimation. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): Handbook of Mathematics Functions <b>Dover Book on
 * 				Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Blagouchine, I. V. (2018): Three Notes on Ser's and Hasse's Representations for the
 * 				Zeta-Functions https://arxiv.org/abs/1606.02044 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Mezo, I., and M. E. Hoffman (2017): Zeros of the Digamma Function and its Barnes G-function
 * 				Analogue <i>Integral Transforms and Special Functions</i> <b>28 (28)</b> 846-858
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Digamma Function https://en.wikipedia.org/wiki/Digamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/digamma/README.md">Estimation Techniques for Digamma Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CumulativeSeriesTerm
{

	/**
	 * Construct the Abramowitz-Stegun (2007) Cumulative Sum Series Term for DiGamma
	 * 
	 * @return The Abramowitz-Stegun (2007) Cumulative Sum Series Term for DiGamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm AbramowitzStegun2007()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || order == -z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesTerm::AbramowitzStegun2007::value => Invalid Inputs");
					}

					return z / (order * (order + z));
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
	 * Construct the Mezo-Hoffman (2017) Cumulative Sum Series Term for DiGamma
	 * 
	 * @param saddlePointArray Array of the Saddle Points
	 * 
	 * @return The Mezo-Hoffman (2017) Cumulative Sum Series Term for DiGamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm MezoHoffman2017 (
		final double[] saddlePointArray)
	{
		if (null == saddlePointArray)
		{
			return null;
		}

		final int saddlePointCount = saddlePointArray.length;

		if (0 == saddlePointCount || !org.drip.numerical.common.NumberUtil.IsValid (saddlePointArray))
		{
			return null;
		}

		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 > order || order >= saddlePointCount ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. >= z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesTerm::MezoHoffman2017::value => Invalid Inputs");
					}

					double zOverSaddlePoint = z / saddlePointArray[order];

					return zOverSaddlePoint * java.lang.Math.log (1. - zOverSaddlePoint);
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
	 * Construct the Gauss Cumulative Sum Series Term for DiGamma
	 * 
	 * @param termCount Term Count
	 * 
	 * @return The Gauss Cumulative Sum Series Term for DiGamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm Gauss (
		final int termCount)
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (1 > order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("CumulativeSeriesTerm::Gauss::value => Invalid Inputs");
					}

					return java.lang.Math.cos (2. * java.lang.Math.PI * order * z) *
						java.lang.Math.log (java.lang.Math.sin (java.lang.Math.PI * order / termCount));
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
	 * Construct the Asymptotic Cumulative Sum Series Term for DiGamma
	 * 
	 * @return The Asymptotic Cumulative Sum Series Term for DiGamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm Asymptotic()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0 == z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesTerm::Asymptotic::value => Invalid Inputs");
					}

					return java.lang.Math.pow (
						z,
						-2 * order
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
	 * Construct the Asymptotic Cumulative Sum Series Term for exp (-diGamma)
	 * 
	 * @return The Asymptotic Cumulative Sum Series Term for exp (-diGamma)
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm ExponentialAsymptote()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0 == z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesTerm::ExponentialAsymptote::value => Invalid Inputs");
					}

					return java.lang.Math.pow (
						z,
						-1 * order
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
	 * Construct the Asymptotic Cumulative Sum Series Term for exp (diGamma + 0.5)
	 * 
	 * @return The Asymptotic Cumulative Sum Series Term for exp (-diGamma + 0.5)
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm ExponentialAsymptoteHalfShifted()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("CumulativeSeriesTerm::ExponentialAsymptoteHalfShifted::value => Invalid Inputs");
					}

					return java.lang.Math.pow (
						z,
						1 - 2 * order
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
	 * Construct the Taylor-Riemann Zeta Series Term for Digamma
	 * 
	 * @param riemannZetaEstimator The Riemann-Zeta Estimator
	 * 
	 * @return The Taylor-Riemann Zeta Series Term for Digamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm TaylorRiemannZeta (
		final org.drip.function.definition.R1ToR1 riemannZetaEstimator)
	{
		if (null == riemannZetaEstimator)
		{
			return null;
		}

		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("CumulativeSeriesTerm::TaylorRiemannZeta::value => Invalid Inputs");
					}

					return (1 == order % 2 ? -1. : 1.) *
						riemannZetaEstimator.evaluate (order + 1) * java.lang.Math.pow (
							z,
							order
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
	 * Construct the Newton-Stern Series Term for Digamma
	 * 
	 * @return The Newton-Stern Series Term for Digamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm NewtonStern()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("CumulativeSeriesTerm::TaylorRiemannZeta::value => Invalid Inputs");
					}

					return (1 == order % 2 ? -1. : 1.) * org.drip.numerical.common.NumberUtil.NCK (
						(int) z,
						order
					) / order;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
