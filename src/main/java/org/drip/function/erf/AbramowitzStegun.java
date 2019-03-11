
package org.drip.function.erf;

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
 * <i>AbramowitzStegun</i> implements the Error Function (erf) Estimator using Abramowitz-Stegun Scheme. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Chang, S. H., P. C. Cosman, L. B. Milstein (2011): Chernoff-Type Bounds for Gaussian Error
 * 				Function <i>IEEE Transactions on Communications</i> <b>59 (11)</b> 2939-2944
 * 		</li>
 * 		<li>
 * 			Cody, W. J. (1991): Algorithm 715: SPECFUN – A Portable FORTRAN Package of Special Function
 * 				Routines and Test Drivers <i>ACM Transactions on Mathematical Software</i> <b>19 (1)</b>
 * 				22-32
 * 		</li>
 * 		<li>
 * 			Schopf, H. M., and P. H. Supancic (2014): On Burmann’s Theorem and its Application to Problems of
 * 				Linear and Non-linear Heat Transfer and Diffusion
 * 				https://www.mathematica-journal.com/2014/11/on-burmanns-theorem-and-its-application-to-problems-of-linear-and-nonlinear-heat-transfer-and-diffusion/#more-39602/
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Error Function https://en.wikipedia.org/wiki/Error_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/erf/README.md">Implementation of Error Function Variants</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class AbramowitzStegun extends org.drip.function.erf.ErrorFunction
{
	private double _maximumError = java.lang.Double.NaN;

	/**
	 * Construct the Inverse Degree 4 Polynomial Version of Abramowitz-Stegun Error Function Estimator
	 * 
	 * @return The Inverse Degree 4 Polynomial Version of Abramowitz-Stegun Error Function Estimator
	 */

	public static final AbramowitzStegun InversePolynomial4()
	{
		final AbramowitzStegunSeriesGenerator abramowitzStegunSeriesGenerator =
			AbramowitzStegunSeriesGenerator.InversePolynomial4();

		try
		{
			return new AbramowitzStegun (
				abramowitzStegunSeriesGenerator,
				null,
				0.0005
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.quant.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("AbramowitzStegun::InversePolynomial4::evaluate => Invalid Inputs");
					}

					if (z < 0)
					{
						return -1. * evaluate (-1. * z);
					}

					double erf = 1. - java.lang.Math.pow (
						abramowitzStegunSeriesGenerator.cumulative (
							0.,
							z
						),
						-4
					);

					return erf > 1. ? 1. : erf;
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
	 * Construct the Mixed Degree 3 Polynomial Version of Abramowitz-Stegun Error Function Estimator
	 * 
	 * @return The Mixed Degree 3 Polynomial Version of Abramowitz-Stegun Error Function Estimator
	 */

	public static final AbramowitzStegun MixedPolynomial3()
	{
		final AbramowitzStegunSeriesGenerator abramowitzStegunSeriesGenerator =
			AbramowitzStegunSeriesGenerator.MixedPolynomial3();

		try
		{
			return new AbramowitzStegun (
				abramowitzStegunSeriesGenerator,
				null,
				0.000025
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.quant.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("AbramowitzStegun::MixedPolynomial3::evaluate => Invalid Inputs");
					}

					if (z < 0)
					{
						return -1. * evaluate (-1. * z);
					}

					double erf = 1. - abramowitzStegunSeriesGenerator.cumulative (
						0.,
						1. / (1. + 0.47047 * z)
					) * java.lang.Math.exp (-1. * z * z);

					return erf > 1. ? 1. : erf;
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
	 * Construct the Inverse Degree 6 Polynomial Version of Abramowitz-Stegun Error Function Estimator
	 * 
	 * @return The Inverse Degree 6 Polynomial Version of Abramowitz-Stegun Error Function Estimator
	 */

	public static final AbramowitzStegun InversePolynomial6()
	{
		final AbramowitzStegunSeriesGenerator abramowitzStegunSeriesGenerator =
			AbramowitzStegunSeriesGenerator.InversePolynomial6();

		try
		{
			return new AbramowitzStegun (
				abramowitzStegunSeriesGenerator,
				null,
				0.0000003
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.quant.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("AbramowitzStegun::InversePolynomial6::evaluate => Invalid Inputs");
					}

					if (z < 0)
					{
						return -1. * evaluate (-1. * z);
					}

					double erf = 1. - java.lang.Math.pow (
						abramowitzStegunSeriesGenerator.cumulative (
							0.,
							z
						),
						-16
					);

					return erf > 1. ? 1. : erf;
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
	 * Construct the Mixed Degree 5 Polynomial Version of Abramowitz-Stegun Error Function Estimator
	 * 
	 * @return The Mixed Degree 5 Polynomial Version of Abramowitz-Stegun Error Function Estimator
	 */

	public static final AbramowitzStegun MixedPolynomial5()
	{
		final AbramowitzStegunSeriesGenerator abramowitzStegunSeriesGenerator =
			AbramowitzStegunSeriesGenerator.MixedPolynomial5();

		try
		{
			return new AbramowitzStegun (
				abramowitzStegunSeriesGenerator,
				null,
				0.00000015
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.quant.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("AbramowitzStegun::MixedPolynomial5::evaluate => Invalid Inputs");
					}

					if (z < 0)
					{
						return -1. * evaluate (-1. * z);
					}

					double erf = 1. - abramowitzStegunSeriesGenerator.cumulative (
						0.,
						1. / (1. + 0.3275911 * z)
					) * java.lang.Math.exp (-1. * z * z);

					return erf > 1. ? 1. : erf;
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
	 * AbramowitzStegun Constructor
	 * 
	 * @param abramowitzStegunSeriesGenerator Abramowitz Stegun Series Generator
	 * @param dc The Derivative Control
	 * @param maximumError Maximum Error
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AbramowitzStegun (
		final org.drip.function.erf.AbramowitzStegunSeriesGenerator abramowitzStegunSeriesGenerator,
		final org.drip.quant.calculus.DerivativeControl dc,
		final double maximumError)
		throws java.lang.Exception
	{
		super (
			abramowitzStegunSeriesGenerator,
			dc
		);

		if (!org.drip.quant.common.NumberUtil.IsValid (_maximumError = maximumError) || 0. >= _maximumError)
		{
			throw new java.lang.Exception ("AbramowitzStegun Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Maximum Error
	 * 
	 * @return The Maximum Error
	 */

	public double maximumError()
	{
		return _maximumError;
	}
}
