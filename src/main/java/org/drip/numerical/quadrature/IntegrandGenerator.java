
package org.drip.numerical.quadrature;

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
 * <i>IntegrandGenerator</i> contains the Settings that enable the Generation of Integrand Quadrature and
 * Weights for the Specified Orthogonal Polynomial Scheme. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Gil, A., J. Segura, and N. M. Temme (2007): <i>Numerical Methods for Special Functions</i>
 * 				<b>Society for Industrial and Applied Mathematics</b> Philadelphia
 * 		</li>
 * 		<li>
 * 			Press, W. H., S. A. Teukolsky, W. T. Vetterling, and B. P. Flannery (2007): <i>Numerical Recipes:
 * 				The Art of Scientific Computing 3rd Edition<i> <b>Cambridge University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Stoer, J., and R. Bulirsch (2002): <i>Introduction to Numerical Analysis 3rd Edition</i>
 * 				<b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gaussian Quadrature https://en.wikipedia.org/wiki/Gaussian_quadrature
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/quadrature/README.md">R<sup>1</sup> Gaussian Integration Quadrature Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IntegrandGenerator
{
	private double _lowerBound = java.lang.Double.NaN;
	private double _upperBound = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _weightFunction = null;
	private org.drip.numerical.quadrature.OrthogonalPolynomialSuite _orthogonalPolynomialSuite = null;

	/**
	 * Construct the Gauss-Legendre Integrand Quadrature Generator
	 * 
	 * @param orthogonalPolynomialSuite Orthogonal Polynomial Suite
	 * 
	 * @return The Gauss-Legendre Integrand Quadrature Generator
	 */

	public static final IntegrandGenerator GaussLegendre (
		final org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite)
	{
		try
		{
			return new IntegrandGenerator (
				orthogonalPolynomialSuite,
				org.drip.numerical.quadrature.WeightFunctionBuilder.Legendre(),
				-1.,
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
	 * Construct the Gauss-Jacobi Integrand Quadrature Generator
	 * 
	 * @param orthogonalPolynomialSuite Orthogonal Polynomial Suite
	 * @param alpha Jacobi Alpha
	 * @param beta Jacobi Beta
	 * 
	 * @return The Gauss-Jacobi Integrand Quadrature Generator
	 */

	public static final IntegrandGenerator GaussJacobi (
		final org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite,
		final double alpha,
		final double beta)
	{
		try
		{
			return new IntegrandGenerator (
				orthogonalPolynomialSuite,
				org.drip.numerical.quadrature.WeightFunctionBuilder.Jacobi (
					alpha,
					beta
				),
				-1.,
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
	 * Construct the Gauss-Chebyshev (Second-Kind) Integrand Quadrature Generator
	 * 
	 * @param orthogonalPolynomialSuite Orthogonal Polynomial Suite
	 * 
	 * @return The Gauss-Chebyshev (Second-Kind) Integrand Quadrature Generator
	 */

	public static final IntegrandGenerator GaussChebyshevSecondKind (
		final org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite)
	{
		try
		{
			return new IntegrandGenerator (
				orthogonalPolynomialSuite,
				org.drip.numerical.quadrature.WeightFunctionBuilder.ChebyshevSecondKind(),
				-1.,
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
	 * Construct the Gauss-Chebyshev (First-Kind) Integrand Quadrature Generator
	 * 
	 * @param orthogonalPolynomialSuite Orthogonal Polynomial Suite
	 * 
	 * @return The Gauss-Chebyshev (First-Kind) Integrand Quadrature Generator
	 */

	public static final IntegrandGenerator GaussChebyshevFirstKind (
		final org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite)
	{
		try
		{
			return new IntegrandGenerator (
				orthogonalPolynomialSuite,
				org.drip.numerical.quadrature.WeightFunctionBuilder.ChebyshevFirstKind(),
				-1.,
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
	 * Construct the Gauss-Laguerre Integrand Quadrature Generator
	 * 
	 * @param orthogonalPolynomialSuite Orthogonal Polynomial Suite
	 * 
	 * @return The Gauss-Laguerre Integrand Quadrature Generator
	 */

	public static final IntegrandGenerator GaussLaguerre (
		final org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite)
	{
		try
		{
			return new IntegrandGenerator (
				orthogonalPolynomialSuite,
				org.drip.numerical.quadrature.WeightFunctionBuilder.Laguerre(),
				-1.,
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
	 * Construct the Generalized Gauss-Laguerre Integrand Quadrature Generator
	 * 
	 * @param orthogonalPolynomialSuite Orthogonal Polynomial Suite
	 * @param alpha Generalized Laguerre Alpha
	 * 
	 * @return The Generalized Gauss-Laguerre Integrand Quadrature Generator
	 */

	public static final IntegrandGenerator GeneralizedGaussLaguerre (
		final org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite,
		final double alpha)
	{
		try
		{
			return new IntegrandGenerator (
				orthogonalPolynomialSuite,
				org.drip.numerical.quadrature.WeightFunctionBuilder.GeneralizedLaguerre (alpha),
				-1.,
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
	 * Construct the Gauss-Hermite Integrand Quadrature Generator
	 * 
	 * @param orthogonalPolynomialSuite Orthogonal Polynomial Suite
	 * 
	 * @return The Gauss-Hermite Integrand Quadrature Generator
	 */

	public static final IntegrandGenerator GaussHermite (
		final org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite)
	{
		try
		{
			return new IntegrandGenerator (
				orthogonalPolynomialSuite,
				org.drip.numerical.quadrature.WeightFunctionBuilder.Hermite(),
				-1.,
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
	 * IntegrandGenerator Constructor
	 * 
	 * @param orthogonalPolynomialSuite Orthogonal Polynomial Suite
	 * @param weightFunction Weight Function
	 * @param lowerBound Lower Bound
	 * @param upperBound Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IntegrandGenerator (
		final org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite,
		final org.drip.function.definition.R1ToR1 weightFunction,
		final double lowerBound,
		final double upperBound)
		throws java.lang.Exception
	{
		if (null == (_orthogonalPolynomialSuite = orthogonalPolynomialSuite) ||
			null == (_weightFunction = weightFunction) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_lowerBound = lowerBound) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_upperBound = upperBound) ||
				_lowerBound >= _upperBound)
		{
			throw new java.lang.Exception ("IntegrandGenerator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Orthogonal Polynomial Suite
	 * 
	 * @return The Orthogonal Polynomial Suite
	 */

	public org.drip.numerical.quadrature.OrthogonalPolynomialSuite orthogonalPolynomialSuite()
	{
		return _orthogonalPolynomialSuite;
	}

	/**
	 * Retrieve the Weight Function
	 * 
	 * @return The Weight Function
	 */

	public org.drip.function.definition.R1ToR1 weightFunction()
	{
		return _weightFunction;
	}

	/**
	 * Retrieve the Lower Integration Bound
	 * 
	 * @return The Lower Integration Bound
	 */

	public double lowerBound()
	{
		return _lowerBound;
	}

	/**
	 * Retrieve the Upper Integration Bound
	 * 
	 * @return The Upper Integration Bound
	 */

	public double upperBound()
	{
		return _upperBound;
	}

	/**
	 * Generate the Weight at the specified Node for the specified Orthogonal Polynomial
	 * 
	 * @param x X Node
	 * @param degree Orthogonal Polynomial Degree
	 * 
	 * @return The Weight at the specified Node for the specified Orthogonal Polynomial
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double nodeWeight (
		final double x,
		final int degree)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (x) ||
			1 >= degree)
		{
			throw new java.lang.Exception ("IntegrandGenerator::nodeWeight => Invalid Inputs");
		}

		final org.drip.numerical.quadrature.OrthogonalPolynomial orthogonalPolynomialN =
			_orthogonalPolynomialSuite.orthogonalPolynomial (degree);

		final org.drip.numerical.quadrature.OrthogonalPolynomial orthogonalPolynomialNMinusOne =
			_orthogonalPolynomialSuite.orthogonalPolynomial (degree - 1);

		if (null == orthogonalPolynomialN || null == orthogonalPolynomialNMinusOne)
		{
			throw new java.lang.Exception ("IntegrandGenerator::nodeWeight => Invalid Inputs");
		}

		double weightIntegrand = new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double z)
				throws java.lang.Exception
			{
				double pNMinusOne = orthogonalPolynomialNMinusOne.evaluate (z);

				return _weightFunction.evaluate (z) * pNMinusOne * pNMinusOne;
			}
		}.integrate (
			_lowerBound,
			_upperBound
		);

		return orthogonalPolynomialN.degreeCoefficient() * weightIntegrand / (
			orthogonalPolynomialNMinusOne.degreeCoefficient() *
			orthogonalPolynomialNMinusOne.evaluate (x) *
			orthogonalPolynomialN.derivative (
				x,
				1
			)
		);
	}
}
