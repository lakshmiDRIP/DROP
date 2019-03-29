
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
 * <i>GaussLobattoQuadratureGenerator</i> generates the Array of Orthogonal Lobatto Polynomial Gaussian
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integration/README.md">R<sup>1</sup> R<sup>d</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GaussLobattoQuadratureGenerator
{

	/**
	 * Generate the Three Point Gauss Lobatto Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Three Point Gauss Lobatto Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator ThreePoint (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer)
	{
		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-1.000000000000000,
						 0.000000000000000,
						 1.000000000000000,
					},
					new double[]
					{
						1. / 3.,
						4. / 3.,
						1. / 3.,
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
	 * Generate the Four Point Gauss Lobatto Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Four Point Gauss Lobatto Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator FourPoint (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer)
	{
		double sqrt_1Over5_ = java.lang.Math.sqrt (0.2);

		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-1.000000000000000,
						-sqrt_1Over5_,
						 sqrt_1Over5_,
						 1.000000000000000,
					},
					new double[]
					{
						1. / 6.,
						5. / 6.,
						5. / 6.,
						1. / 6.,
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
	 * Generate the Five Point Gauss Lobatto Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Five Point Gauss Lobatto Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator FivePoint (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer)
	{
		double sqrt_3Over7_ = java.lang.Math.sqrt (3. / 7.);

		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-1.000000000000000,
						-sqrt_3Over7_,
						 0.000000000000000,
						 sqrt_3Over7_,
						 1.000000000000000,
					},
					new double[]
					{
						 1. / 10.,
						49. / 90.,
						32. / 45.,
						49. / 90.,
						 1. / 10.,
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
	 * Generate the Six Point Gauss Lobatto Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Six Point Gauss Lobatto Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator SixPoint (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer)
	{
		double sqrt7 = java.lang.Math.sqrt (7.);

		double twoSqrt_7_Over21 = 2. / 21. * sqrt7;
		double nearWeight = (14. + sqrt7) / 30.;
		double farWeight = (14. - sqrt7) / 30.;

		double farNode = java.lang.Math.sqrt ((1. / 3.) + twoSqrt_7_Over21);

		double nearNode = java.lang.Math.sqrt ((1. / 3.) - twoSqrt_7_Over21);

		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-1.000000000000000,
						-farNode,
						-nearNode,
						 nearNode,
						 farNode,
						 1.000000000000000,
					},
					new double[]
					{
						1. / 15.,
						farWeight,
						nearWeight,
						nearWeight,
						farWeight,
						1. / 15.,
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
	 * Generate the Seven Point Gauss Lobatto Quadrature over [-1, +1]
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Seven Point Gauss Lobatto Quadrature over [-1, +1]
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator SevenPoint (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer)
	{
		double twoOver11Sqrt_5Over3_ = 2. / 11. * java.lang.Math.sqrt (5. / 3.);

		double farNode = java.lang.Math.sqrt ((5. / 11.) + twoOver11Sqrt_5Over3_);

		double nearNode = java.lang.Math.sqrt ((5. / 11.) - twoOver11Sqrt_5Over3_);

		double sevenSqrt15 = 7. * java.lang.Math.sqrt (15.);

		double nearWeight = (124. + sevenSqrt15) / 350.;
		double farWeight = (124. - sevenSqrt15) / 350.;

		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-1.000000000000000,
						-farNode,
						-nearNode,
						 0.000000000000000,
						 nearNode,
						 farNode,
						 1.000000000000000,
					},
					new double[]
					{
						1. / 21.,
						farWeight,
						nearWeight,
						256. / 525.,
						nearWeight,
						farWeight,
						1. / 21.,
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
	 * Generate the Three Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The Three Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator ThreePoint (
		final double left,
		final double right)
	{
		return ThreePoint (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
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

	public static final org.drip.numerical.integration.QuadratureEstimator FourPoint (
		final double left,
		final double right)
	{
		return FourPoint (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
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

	public static final org.drip.numerical.integration.QuadratureEstimator FivePoint (
		final double left,
		final double right)
	{
		return FivePoint (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}

	/**
	 * Generate the Six Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The Six Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator SixPoint (
		final double left,
		final double right)
	{
		return SixPoint (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}

	/**
	 * Generate the Seven Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The Seven Point Gauss Legendre Quadrature over [a, b] onto [-1, +1]
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator SevenPoint (
		final double left,
		final double right)
	{
		return SevenPoint (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}
}
