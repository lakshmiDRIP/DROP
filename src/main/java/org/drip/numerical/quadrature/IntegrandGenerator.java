
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
	 * Generate the Integral of the Weight Function Over the Bounds
	 * 
	 * @return The Integral of the Weight Function Over the Bounds
	 * 
	 * @throws java.lang.Exception Thrown if it cannot be computed
	 */

	public double weightFunctionIntegral()
		throws java.lang.Exception
	{
		return _weightFunction.integrate (
			_lowerBound,
			_upperBound
		);
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (x))
		{
			throw new java.lang.Exception ("IntegrandGenerator::nodeWeight => Invalid Inputs");
		}

		if (0 > degree)
		{
			return 0.;
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

	/**
	 * Compute the Loaded Inner Product between the Polynomial identified by their Degrees
	 * 
	 * @param degree1 Polynomial Degree #1
	 * @param degree2 Polynomial Degree #2
	 * 
	 * @return The Loaded Inner Product
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double loadedInnerProduct (
		final int degree1,
		final int degree2)
		throws java.lang.Exception
	{
		if (0 > degree1 || 0 > degree2)
		{
			return 0.;
		}

		final org.drip.numerical.quadrature.OrthogonalPolynomial orthogonalPolynomial1 =
			_orthogonalPolynomialSuite.orthogonalPolynomial (degree1);

		final org.drip.numerical.quadrature.OrthogonalPolynomial orthogonalPolynomial2 =
			_orthogonalPolynomialSuite.orthogonalPolynomial (degree2);

		if (null == orthogonalPolynomial1 || null == orthogonalPolynomial2)
		{
			throw new java.lang.Exception ("IntegrandGenerator::loadedInnerProduct => Invalid Inputs");
		}

		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double z)
				throws java.lang.Exception
			{
				return z * _weightFunction.evaluate (z) * orthogonalPolynomial1.evaluate (z) *
					orthogonalPolynomial2.evaluate (z);
			}
		}.integrate (
			_lowerBound,
			_upperBound
		);
	}

	/**
	 * Compute the Unloaded Inner Product between the Polynomial identified by their Degrees
	 * 
	 * @param degree1 Polynomial Degree #1
	 * @param degree2 Polynomial Degree #2
	 * 
	 * @return The Unloaded Inner Product
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double unloadedInnerProduct (
		final int degree1,
		final int degree2)
		throws java.lang.Exception
	{
		if (0 > degree1 || 0 > degree2)
		{
			return 0.;
		}

		final org.drip.numerical.quadrature.OrthogonalPolynomial orthogonalPolynomial1 =
			_orthogonalPolynomialSuite.orthogonalPolynomial (degree1);

		final org.drip.numerical.quadrature.OrthogonalPolynomial orthogonalPolynomial2 =
			_orthogonalPolynomialSuite.orthogonalPolynomial (degree2);

		if (null == orthogonalPolynomial1 || null == orthogonalPolynomial2)
		{
			throw new java.lang.Exception ("IntegrandGenerator::unloadedInnerProduct => Invalid Inputs");
		}

		return new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double z)
				throws java.lang.Exception
			{
				return _weightFunction.evaluate (z) * orthogonalPolynomial1.evaluate (z) *
					orthogonalPolynomial2.evaluate (z);
			}
		}.integrate (
			_lowerBound,
			_upperBound
		);
	}

	/**
	 * Generate the Golub-Welsch Matrix A Entry
	 * 
	 * @param degree The Orthogonal Polynomial Degree
	 * 
	 * @return The Golub-Welsch Matrix A Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double golubWelschA (
		final int degree)
		throws java.lang.Exception
	{
		return loadedInnerProduct (
			degree,
			degree
		) / unloadedInnerProduct (
			degree,
			degree
		);
	}

	/**
	 * Generate the Golub-Welsch Matrix B Entry
	 * 
	 * @param degree The Orthogonal Polynomial Degree
	 * 
	 * @return The Golub-Welsch Matrix B Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double golubWelschB (
		final int degree)
		throws java.lang.Exception
	{
		return unloadedInnerProduct (
			degree,
			degree
		) / unloadedInnerProduct (
			degree - 1,
			degree - 1
		);
	}

	/**
	 * Generate the Cross Polynomial Recurrence Matrix to be used in the Golub-Welsch Algorithm
	 * 
	 * @return The Cross Polynomial Recurrence Matrix to be used in the Golub-Welsch Algorithm
	 */

	public org.drip.numerical.quadrature.GolubWelsch generateRecurrenceMatrix()
	{
		int size = _orthogonalPolynomialSuite.size();

		double[][] golubWelschMatrix = new double[size][size];

		for (int row = 0; row < size; ++row)
		{
			for (int column = 0; column < size; ++column)
			{
				golubWelschMatrix[row][column] = column == row + 1 ? 1. : 0.;
			}
		}

		try
		{
			for (int row = 0; row < size; ++row)
			{
				golubWelschMatrix[row][row] = loadedInnerProduct (
					row,
					row
				) / unloadedInnerProduct (
					row,
					row
				);

				if (0 < row)
				{
					golubWelschMatrix[row][row - 1] = unloadedInnerProduct (
						row,
						row
					) / unloadedInnerProduct (
						row - 1,
						row - 1
					);
				}
			}

			return new org.drip.numerical.quadrature.GolubWelsch (golubWelschMatrix);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Quadrature Nodes and Scaled Weights Using the Gil, Segura, and Temme (2007) Scheme
	 * 
	 * @return The Quadrature Nodes and Scaled Weights
	 */

	public org.drip.numerical.common.Array2D gilSeguraTemme2007()
	{
		org.drip.numerical.quadrature.GolubWelsch golubWelsch = generateRecurrenceMatrix();

		if (null == golubWelsch)
		{
			return null;
		}

		org.drip.numerical.common.Array2D nodesAndUnscaledWeights = golubWelsch.nodesAndUnscaledWeights();

		if (null == nodesAndUnscaledWeights)
		{
			return null;
		}

		double[] unscaledWeightArray = nodesAndUnscaledWeights.y();

		double[] nodeArray = nodesAndUnscaledWeights.x();

		int size = nodeArray.length;
		double[] scaledWeightArray = new double[size];

		try
		{
			double weightFunctionIntegral = weightFunctionIntegral();

			for (int nodeIndex = 0; nodeIndex < size; ++nodeIndex)
			{
				scaledWeightArray[nodeIndex] = unscaledWeightArray[nodeIndex] * weightFunctionIntegral;
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return org.drip.numerical.common.Array2D.FromArray (
			nodeArray,
			scaledWeightArray
		);
	}
}
