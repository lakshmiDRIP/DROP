
package org.drip.specialfunction.hypergeometric;

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
 * <i>EulerQuadratureEstimator</i> estimates the Hyper-geometric Function using the Euler Integral
 * Representation. The References are:
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

public class EulerQuadratureEstimator extends
	org.drip.specialfunction.definition.RegularHypergeometricEstimator
{
	private int _quadratureCount = -1;
	private org.drip.function.definition.R2ToR1 _logBetaEstimator = null;

	/**
	 * EulerQuadratureEstimator Constructor
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * @param logBetaEstimator Log Beta Estimator
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EulerQuadratureEstimator (
		final double a,
		final double b,
		final double c,
		final org.drip.function.definition.R2ToR1 logBetaEstimator,
		final int quadratureCount)
		throws java.lang.Exception
	{
		super (
			a,
			b,
			c
		);

		if (null == (_logBetaEstimator = logBetaEstimator) ||
			0 >= (_quadratureCount = quadratureCount))
		{
			throw new java.lang.Exception ("EulerQuadratureEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Quadrature Count
	 * 
	 * @return The Quadrature Count
	 */

	public int quadratureCount()
	{
		return _quadratureCount;
	}

	/**
	 * Retrieve the Log Beta Estimator
	 * 
	 * @return The Log Beta Estimator
	 */

	public org.drip.function.definition.R2ToR1 logBetaEstimator()
	{
		return _logBetaEstimator;
	}

	@Override public double regularHypergeometric (
		final double z)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z < -1. || z > 1.)
		{
			throw new java.lang.Exception ("EulerQuadratureEstimator::regularHypergeometric => Invalid Inputs");
		}

		final double a = a();

		final double b = b();

		final double c = c();

		return org.drip.numerical.integration.NewtonCotesQuadratureGenerator.Zero_PlusOne (
			0.,
			1.,
			_quadratureCount
		).integrate (
			new org.drip.function.definition.R1ToR1 (null)
			{
				@Override public double evaluate (
					final double t)
					throws java.lang.Exception
				{
					return 0. == t || 1. == t ? 0. :
						java.lang.Math.pow (
							t,
							b - 1.
						) * java.lang.Math.pow (
							1. - t,
							c - b - 1.
						) * java.lang.Math.pow (
							1. - z * t,
							-a
						);
				}
			}
		) * java.lang.Math.exp (
		-1. * _logBetaEstimator.evaluate (
				b,
				c - b
			)
		);
	}

	@Override public double derivative (
		final double z,
		final int order)
		throws java.lang.Exception
	{
		return new EulerQuadratureEstimator (
			a() + order,
			b() + order,
			c() + order,
			_logBetaEstimator,
			_quadratureCount
		).regularHypergeometric (z);
	}

	@Override public org.drip.specialfunction.definition.RegularHypergeometricEstimator albinate (
		final double a,
		final double b,
		final double c,
		final org.drip.function.definition.R1ToR1 valueScaler,
		final org.drip.function.definition.R1ToR1 zTransformer)
	{
		try
		{
			return new EulerQuadratureEstimator (
				a,
				b,
				c,
				_logBetaEstimator,
				_quadratureCount
			)
			{
				@Override public double regularHypergeometric (
					final double z)
					throws java.lang.Exception
				{
					return (null == valueScaler ? 1. : valueScaler.evaluate (z)) *
						super.regularHypergeometric (null == zTransformer ? z : zTransformer.evaluate (z));
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
