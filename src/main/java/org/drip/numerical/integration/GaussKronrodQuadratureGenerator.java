
package org.drip.numerical.integration;

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
 * <i>GaussKronrodQuadratureGenerator</i> generates the Array of Gaussian Quadrature Based Abscissa and their
 * corresponding Weights, with the Kronrod Extensions applied. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Holoborodko, P. (2011): Gauss-Kronrod Quadrature Nodes and Weights
 * 				https://www.advanpix.com/2011/11/07/gauss-kronrod-quadrature-nodes-weights/
 * 		</li>
 * 		<li>
 * 			Kahaner, D., C. Moler, and S. Nash (1989): <i>Numerical Methods and Software</i> <b>Prentice
 * 				Hall</b>
 * 		</li>
 * 		<li>
 * 			Laurie, D. (1997): Calculation of Gauss-Kronrod Quadrature Rules <i>Mathematics of
 * 				Computation</i> <b>66 (219)</b> 1133-1145
 * 		</li>
 * 		<li>
 * 			Piessens, R., E. de Doncker-Kapenga, C. W. Uberhuber, and D. K. Kahaner (1983): <i>QUADPACK – A
 * 				Subroutine Package for Automatic Integration</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gauss-Kronrod Quadrature Formula
 * 				https://en.wikipedia.org/wiki/Gauss%E2%80%93Kronrod_quadrature_formula
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

public class GaussKronrodQuadratureGenerator
{

	/**
	 * Generate the Nested/Embedded G7 Gaussian Quadrature over (0, +1)
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The Nested/Embedded G7 Gaussian Quadrature over (0, +1)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator G7 (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer)
	{
		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-0.949107912342759,
						-0.741531185599394,
						-0.405845151377397,
						 0.000000000000000,
						 0.405845151377397,
						 0.741531185599394,
						 0.949107912342759,
					},
					new double[]
					{
						0.129484966168870,
						0.279705391489277,
						0.381830050505119,
						0.417959183673469,
						0.381830050505119,
						0.279705391489277,
						0.129484966168870,
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
	 * Generate the K15 Gaussian Quadrature over (0, +1)
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * 
	 * @return The K15 Gaussian Quadrature over (0, +1)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator K15 (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer)
	{
		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-0.991455371120813,
						-0.949107912342759,
						-0.864864423359769,
						-0.741531185599394,
						-0.586087235467691,
						-0.405845151377397,
						-0.207784955007898,
						 0.000000000000000,
						 0.207784955007898,
						 0.405845151377397,
						 0.586087235467691,
						 0.741531185599394,
						 0.864864423359769,
						 0.949107912342759,
						 0.991455371120813,
					},
					new double[]
					{
						0.022935322010529,
						0.063092092629979,
						0.104790010322250,
						0.140653259715525,
						0.169004726639267,
						0.190350578064785,
						0.204432940075298,
						0.209482141084728,
						0.204432940075298,
						0.190350578064785,
						0.169004726639267,
						0.140653259715525,
						0.104790010322250,
						0.063092092629979,
						0.022935322010529,
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
	 * Generate the Nested/Embedded G7 Gaussian Quadrature over (a, b) onto (-1, +1)
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The Nested/Embedded G7 Gaussian Quadrature over (a, b) onto (-1, +1)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator G7 (
		final double left,
		final double right)
	{
		return G7 (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}

	/**
	 * Generate the K15 Gaussian Quadrature over (a, b) onto (-1, +1)
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The K15 Gaussian Quadrature over (a, b) onto (-1, +1)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator K15 (
		final double left,
		final double right)
	{
		return K15 (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			)
		);
	}

	/**
	 * Generate the G7-K15 Nested Quadrature Estimator over (a, b) onto (-1, +1)
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * 
	 * @return The G7-K15 Nested Quadrature Estimator over (a, b) onto (-1, +1)
	 */

	public static final org.drip.numerical.integration.NestedQuadratureEstimator G7K15 (
		final double left,
		final double right)
	{
		try
		{
			return new org.drip.numerical.integration.NestedQuadratureEstimator (
				org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
					left,
					right
				),
				org.drip.numerical.common.Array2D.FromArray (
					new double[]
					{
						-0.991455371120813,
						-0.949107912342759,
						-0.864864423359769,
						-0.741531185599394,
						-0.586087235467691,
						-0.405845151377397,
						-0.207784955007898,
						 0.000000000000000,
						 0.207784955007898,
						 0.405845151377397,
						 0.586087235467691,
						 0.741531185599394,
						 0.864864423359769,
						 0.949107912342759,
						 0.991455371120813,
					},
					new double[]
					{
						0.022935322010529,
						0.063092092629979,
						0.104790010322250,
						0.140653259715525,
						0.169004726639267,
						0.190350578064785,
						0.204432940075298,
						0.209482141084728,
						0.204432940075298,
						0.190350578064785,
						0.169004726639267,
						0.140653259715525,
						0.104790010322250,
						0.063092092629979,
						0.022935322010529,
					}
				),
				G7 (
					left,
					right
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
