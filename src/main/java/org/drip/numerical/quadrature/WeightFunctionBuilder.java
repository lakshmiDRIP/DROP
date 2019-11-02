
package org.drip.numerical.quadrature;

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
 * <i>WeightFunctionBuilder</i> builds the Weight Function associated with Different Kinds of Orthogonal
 * Basis Polynomials. The References are:
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
 * 				The Art of Scientific Computing 3rd Edition</i> <b>Cambridge University Press</b> New York
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/quadrature/README.md">R<sup>1</sup> Gaussian Integration Quadrature Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class WeightFunctionBuilder
{

	/**
	 * Generate the Legendre Polynomial Weight Function
	 * 
	 * @return The Legendre Polynomial Weight Function
	 */

	public static final org.drip.function.definition.R1ToR1 Legendre()
	{
		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				return 1.;
			}
		};
	}

	/**
	 * Generate the Jacobi Polynomial Weight Function
	 * 
	 * @param alpha Jacobi Alpha
	 * @param beta Jacobi Beta
	 * 
	 * @return The Jacobi Polynomial Weight Function
	 */

	public static final org.drip.function.definition.R1ToR1 Jacobi (
		final double alpha,
		final double beta)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (alpha) || -1. >= alpha || -1. >= beta)
		{
			return null;
		}

		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception
						("WeightFunctionBuilder::Jacobi::evaluate => Invalid Inputs");
				}

				return java.lang.Math.pow (
					1. - x,
					alpha
				) * java.lang.Math.pow (
					1. + x,
					beta
				) * java.lang.Math.exp (-1. * x);
			}
		};
	}

	/**
	 * Generate the Chebyshev Polynomial (First-Kind) Weight Function
	 * 
	 * @return The Chebyshev Polynomial (First-Kind) Weight Function
	 */

	public static final org.drip.function.definition.R1ToR1 ChebyshevFirstKind()
	{
		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception
						("WeightFunctionBuilder::ChebyshevFirstKind::evaluate => Invalid Inputs");
				}

				return 1. / java.lang.Math.sqrt (1. - x * x);
			}
		};
	}

	/**
	 * Generate the Chebyshev Polynomial (Second-Kind) Weight Function
	 * 
	 * @return The Chebyshev Polynomial (Second-Kind) Weight Function
	 */

	public static final org.drip.function.definition.R1ToR1 ChebyshevSecondKind()
	{
		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception
						("WeightFunctionBuilder::ChebyshevSecondKind::evaluate => Invalid Inputs");
				}

				return java.lang.Math.sqrt (1. - x * x);
			}
		};
	}

	/**
	 * Generate the Laguerre Polynomial Weight Function
	 * 
	 * @return The Laguerre Polynomial Weight Function
	 */

	public static final org.drip.function.definition.R1ToR1 Laguerre()
	{
		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception
						("WeightFunctionBuilder::Laguerre::evaluate => Invalid Inputs");
				}

				return java.lang.Math.exp (-1. * x);
			}
		};
	}

	/**
	 * Generate the Generalized Laguerre Polynomial Weight Function
	 * 
	 * @param alpha Generalized Laguerre Alpha
	 * 
	 * @return The Generalized Laguerre Polynomial Weight Function
	 */

	public static final org.drip.function.definition.R1ToR1 GeneralizedLaguerre (
		final double alpha)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (alpha) || -1. >= alpha)
		{
			return null;
		}

		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception
						("WeightFunctionBuilder::GeneralizedLaguerre::evaluate => Invalid Inputs");
				}

				return java.lang.Math.pow (
					x,
					alpha
				) * java.lang.Math.exp (-1. * x);
			}
		};
	}

	/**
	 * Generate the Hermite Polynomial Weight Function
	 * 
	 * @return The Hermite Polynomial Weight Function
	 */

	public static final org.drip.function.definition.R1ToR1 Hermite()
	{
		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double x)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception
						("WeightFunctionBuilder::Hermite::evaluate => Invalid Inputs");
				}

				return java.lang.Math.exp (-1. * x * x);
			}
		};
	}
}
