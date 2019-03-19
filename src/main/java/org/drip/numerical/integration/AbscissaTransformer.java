
package org.drip.numerical.integration;

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
 * <i>AbscissaTransformer</i> transforms the Abscissa over into Corresponding Integrand Variable. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Briol, F. X., C. J. Oates, M. Girolami, and M. A. Osborne (2015): <i>Frank-Wolfe Bayesian
 * 				Quadrature: Probabilistic Integration with Theoretical Guarantees</i> <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Forsythe, G. E., M. A. Malcolm, and C. B. Moler (1977): <i>Computer Methods for Mathematical
 * 				Computation</i> <b>Prentice Hall</b> Englewood Cliffs NJ
 * 		</li>
 * 		<li>
 * 			Leader, J. J. (2004): <i>Numerical Analysis and Scientific Computation</i> <b>Addison Wesley</b>
 * 		</li>
 * 		<li>
 * 			Stoer, J., and R. Bulirsch (1980): <i>Introduction to Numerical Analysis</i>
 * 				<b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Numerical Integration https://en.wikipedia.org/wiki/Numerical_integration
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integration/README.md">R<sup>1</sup> R<sup>d</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AbscissaTransformer
{
	private double _quadratureScale = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _r1ValueTransform = null;
	private org.drip.function.definition.R1ToR1 _r1ToR1VariateTransform = null;

	/**
	 * Generate the Scaled and Displaced Abscissa Transformer
	 * 
	 * @param left Span Left
	 * @param right Span Right
	 * 
	 * @return The Scaled and Displaced Abscissa Transformer
	 */

	public static final AbscissaTransformer DisplaceAndScale (
		final double left,
		final double right)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (left) ||
			!org.drip.numerical.common.NumberUtil.IsValid (right))
		{
			return null;
		}

		try
		{
			return new AbscissaTransformer (
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return (right - left) * x + left;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return 1.;
					}
				},
				right - left
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Gauss-Hermite Abscissa Transformer
	 * 
	 * @return The Gauss-Hermite Abscissa Transformer
	 */

	public static final AbscissaTransformer GaussHermite()
	{
		try
		{
			return new AbscissaTransformer (
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return x / (1. - x * x);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						double xSquared = x * x;

						return (1. + xSquared) / (1. - xSquared) / (1. - xSquared);
					}
				},
				1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Gauss-Laguerre Abscissa Transformer for Integrals in [a, +Infinity]
	 * 
	 * @param left Span Left
	 * 
	 * @return The Gauss-Laguerre Abscissa Transformer for Integrals in [a, +Infinity]
	 */

	public static final AbscissaTransformer GaussLaguerreLeftDefinite (
		final double left)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (left))
		{
			return null;
		}

		try
		{
			return new AbscissaTransformer (
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return left + (x / (1. - x));
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return 1. / (1. - x) / (1. - x);
					}
				},
				1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AbscissaTransformer Constructor
	 * 
	 * @param r1ToR1VariateTransform R<sup>1</sup> to R<sup>1</sup> Variate Transform Function
	 * @param r1ValueTransform R<sup>1</sup> Value Transform Function
	 * @param quadratureScale Quadrature Scale
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AbscissaTransformer (
		final org.drip.function.definition.R1ToR1 r1ToR1VariateTransform,
		final org.drip.function.definition.R1ToR1 r1ValueTransform,
		final double quadratureScale)
		throws java.lang.Exception
	{
		if (null == (_r1ToR1VariateTransform = r1ToR1VariateTransform) ||
			null == (_r1ValueTransform = r1ValueTransform) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_quadratureScale = quadratureScale))
		{
			throw new java.lang.Exception ("AbscissaTransformer Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the R<sup>1</sup> to R<sup>1</sup> Variate Transform Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Variate Transform Function
	 */

	public org.drip.function.definition.R1ToR1 r1ToR1VariateTransform()
	{
		return _r1ToR1VariateTransform;
	}

	/**
	 * Retrieve the R<sup>1</sup> Value Transform Function
	 * 
	 * @return The R<sup>1</sup> Value Transform Function
	 */

	public org.drip.function.definition.R1ToR1 r1ValueTransform()
	{
		return _r1ValueTransform;
	}

	/**
	 * Retrieve the Quadrature Scale
	 * 
	 * @return The Quadrature Scale
	 */

	public double quadratureScale()
	{
		return _quadratureScale;
	}
}
