
package org.drip.numerical.integration;

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
 * <i>GaussLegendreQuadratureGenerator</i> generates the Array of Orthogonal Legendre Polynomial Gaussian
 * Quadrature Based Abscissa and their corresponding Weights. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integration/README.md">R<sup>1</sup> R<sup>d</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GaussLegendreQuadratureGenerator
{

	/**
	 * Generate the One Point Gauss Legendre Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The One Point Gauss Legendre Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator OnePoint (
		final org.drip.numerical.integration.R1AbscissaTransform abscissaTransformer)
	{
		try
		{
			return new org.drip.numerical.integration.R1QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						0.000000000000000,
					},
					new double[]
					{
						2.000000000000000,
					}
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Two Point Gauss Legendre Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Two Point Gauss Legendre Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator TwoPoint (
		final org.drip.numerical.integration.R1AbscissaTransform abscissaTransformer)
	{
		double sqrt_1Over3_ = java.lang.Math.sqrt (1. / 3.);

		try
		{
			return new org.drip.numerical.integration.R1QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-sqrt_1Over3_,
						 sqrt_1Over3_,
					},
					new double[]
					{
						1.000000000000000,
						1.000000000000000,
					}
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Three Point Gauss Legendre Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Three Point Gauss Legendre Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator ThreePoint (
		final org.drip.numerical.integration.R1AbscissaTransform abscissaTransformer)
	{
		double sqrt_3Over5_ = java.lang.Math.sqrt (3. / 5.);

		try
		{
			return new org.drip.numerical.integration.R1QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-sqrt_3Over5_,
						 0.000000000000000,
						 sqrt_3Over5_,
					},
					new double[]
					{
						5. / 9.,
						8. / 9.,
						5. / 9.,
					}
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Four Point Gauss Legendre Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Four Point Gauss Legendre Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator FourPoint (
		final org.drip.numerical.integration.R1AbscissaTransform abscissaTransformer)
	{
		double sqrt_30_Over36 = java.lang.Math.sqrt (30.) / 36.;

		double nearWeight = 0.5 + sqrt_30_Over36;
		double farWeight = 0.5 - sqrt_30_Over36;
		double threeOver7 = 3. / 7.;

		double twoOver7Sqrt_6Over5_ = 2. / 7. * java.lang.Math.sqrt (6. / 5.);

		double farNode = java.lang.Math.sqrt (threeOver7 + twoOver7Sqrt_6Over5_);

		double nearNode = java.lang.Math.sqrt (threeOver7 - twoOver7Sqrt_6Over5_);

		try
		{
			return new org.drip.numerical.integration.R1QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-farNode,
						-nearNode,
						 nearNode,
						 farNode,
					},
					new double[]
					{
						farWeight,
						nearWeight,
						nearWeight,
						farWeight,
					}
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Five Point Gauss Legendre Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Five Point Gauss Legendre Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator FivePoint (
		final org.drip.numerical.integration.R1AbscissaTransform abscissaTransformer)
	{
		double thirteenSqrt_70_ = 13. * java.lang.Math.sqrt (70.);

		double nearWeight = (322. + thirteenSqrt_70_) / 900.;
		double farWeight = (322. - thirteenSqrt_70_) / 900.;

		double twoSqrt_10Over7_ = 2. * java.lang.Math.sqrt (10. / 7.);

		double farNode = java.lang.Math.sqrt (5. + twoSqrt_10Over7_) / 3.;

		double nearNode = java.lang.Math.sqrt (5. - twoSqrt_10Over7_) / 3.;

		try
		{
			return new org.drip.numerical.integration.R1QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-farNode,
						-nearNode,
						 0.000000000000000,
						 nearNode,
						 farNode,
					},
					new double[]
					{
						farWeight,
						nearWeight,
						128. / 225.,
						nearWeight,
						farWeight,
					}
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the One Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The One Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator OnePoint (
		final double left,
		final double right)
	{
		return OnePoint (
			org.drip.numerical.integration.R1AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}

	/**
	 * Generate the Two Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The Two Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator TwoPoint (
		final double left,
		final double right)
	{
		return TwoPoint (
			org.drip.numerical.integration.R1AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}

	/**
	 * Generate the Three Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The Three Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator ThreePoint (
		final double left,
		final double right)
	{
		return ThreePoint (
			org.drip.numerical.integration.R1AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}

	/**
	 * Generate the Four Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The Four Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator FourPoint (
		final double left,
		final double right)
	{
		return FourPoint (
			org.drip.numerical.integration.R1AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}

	/**
	 * Generate the Five Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The Five Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 */

	public static final org.drip.numerical.integration.R1QuadratureEstimator FivePoint (
		final double left,
		final double right)
	{
		return FivePoint (
			org.drip.numerical.integration.R1AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}
}
