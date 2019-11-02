
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
 * <i>NewtonCotesQuadratureGenerator</i> generates the Array of Newton-Cotes Based Quadrature Abscissa and
 * their corresponding Weights. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integration/README.md">R<sup>1</sup> R<sup>d</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NewtonCotesQuadratureGenerator
{

	/**
	 * Generate the Newton-Cotes of Equally Spaced Quadrature over (0, +1)
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * @param intermediatePointCount Number of Intermediate Points
	 * 
	 * @return The Newton-Cotes of Equally Spaced Quadrature over (0, +1)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator Zero_PlusOne (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer,
		final int intermediatePointCount)
	{
		if (0 >= intermediatePointCount)
		{
			return null;
		}

		int nodeCount = intermediatePointCount + 2;
		double width = 1. / (intermediatePointCount + 1);
		double[] abscissaArray = new double[nodeCount];
		double[] weightArray = new double[nodeCount];
		weightArray[intermediatePointCount + 1] = 0.5 * width;
		abscissaArray[intermediatePointCount + 1] = 1.;
		weightArray[0] = 0.5 * width;
		abscissaArray[0] = 0.;

		for (int intermediatePointIndex = 0; intermediatePointIndex < intermediatePointCount;
			++intermediatePointIndex)
		{
			weightArray[intermediatePointIndex + 1] = width;
			abscissaArray[intermediatePointIndex + 1] = width * (intermediatePointIndex + 1);
		}

		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					abscissaArray,
					weightArray
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
	 * Generate the Newton-Cotes of Equally Spaced Quadrature over (-1, +1)
	 * 
	 * @param abscissaTransformer The Abscissa Transformer
	 * @param intermediatePointCount Number of Intermediate Points
	 * 
	 * @return The Newton-Cotes of Equally Spaced Quadrature over (1, +1)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator MinusOne_PlusOne (
		final org.drip.numerical.integration.AbscissaTransform abscissaTransformer,
		final int intermediatePointCount)
	{
		if (0 >= intermediatePointCount)
		{
			return null;
		}

		int nodeCount = intermediatePointCount + 2;
		double[] weightArray = new double[nodeCount];
		double[] abscissaArray = new double[nodeCount];
		double width = 2. / (intermediatePointCount + 1);
		weightArray[intermediatePointCount + 1] = 0.5 * width;
		abscissaArray[intermediatePointCount + 1] = 1.;
		weightArray[0] = 0.5 * width;
		abscissaArray[0] = -1.;

		for (int intermediatePointIndex = 0; intermediatePointIndex < intermediatePointCount;
			++intermediatePointIndex)
		{
			weightArray[intermediatePointIndex + 1] = width;
			abscissaArray[intermediatePointIndex + 1] = width * (intermediatePointIndex + 1) - 1.;
		}

		try
		{
			return new org.drip.numerical.integration.QuadratureEstimator (
				abscissaTransformer,
				org.drip.numerical.common.Array2D.FromArray (
					abscissaArray,
					weightArray
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
	 * Generate the Newton-Cotes of Equally Spaced Quadrature over (a, b) onto (0, +1)
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * @param intermediatePointCount Number of Intermediate Points
	 * 
	 * @return The Newton-Cotes of Equally Spaced Quadrature over (a, b) onto (0, +1)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator Zero_PlusOne (
		final double left,
		final double right,
		final int intermediatePointCount)
	{
		return Zero_PlusOne (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleZero_PlusOne (
				left,
				right
			),
			intermediatePointCount
		);
	}

	/**
	 * Generate the Newton-Cotes of Equally Spaced Quadrature over (a, b) onto (-1, +1)
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param right Right Integrand Quadrature Limit
	 * @param intermediatePointCount Number of Intermediate Points
	 * 
	 * @return The Newton-Cotes of Equally Spaced Quadrature over (a, b) onto (-1, +1)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator MinusOne_PlusOne (
		final double left,
		final double right,
		final int intermediatePointCount)
	{
		return MinusOne_PlusOne (
			org.drip.numerical.integration.AbscissaTransform.DisplaceAndScaleMinusOne_PlusOne (
				left,
				right
			),
			intermediatePointCount
		);
	}

	/**
	 * Generate the Newton-Cotes Quadrature for the Gauss-Laguerre Left-Definite Integral over (a, +Infinity)
	 * 
	 * @param left Left Integrand Quadrature Limit
	 * @param intermediatePointCount Number of Intermediate Points
	 * 
	 * @return The Newton-Cotes Quadrature for the Gauss-Laguerre Left-Definite Integral over (a, +Infinity)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator GaussLaguerreLeftDefinite (
		final double left,
		final int intermediatePointCount)
	{
		return Zero_PlusOne (
			org.drip.numerical.integration.AbscissaTransform.GaussLaguerreLeftDefinite (left),
			intermediatePointCount
		);
	}

	/**
	 * Generate the Newton-Cotes Quadrature for the Gauss-Laguerre Left-Definite Integral over (-Infinity, a)
	 * 
	 * @param right Right Integrand Quadrature Limit
	 * @param intermediatePointCount Number of Intermediate Points
	 * 
	 * @return The Newton-Cotes Quadrature for the Gauss-Laguerre Left-Definite Integral over (-Infinity, a)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator GaussLaguerreRightDefinite (
		final double right,
		final int intermediatePointCount)
	{
		return Zero_PlusOne (
			org.drip.numerical.integration.AbscissaTransform.GaussLaguerreRightDefinite (right),
			intermediatePointCount
		);
	}

	/**
	 * Generate the Newton-Cotes Quadrature for the Gauss-Hermite Indefinite Integral over
	 * 		(-Infinity, +Infinity)
	 * 
	 * @param intermediatePointCount Number of Intermediate Points
	 * 
	 * @return The Newton-Cotes Quadrature for the Gauss-Hermite Indefinite Integral over
	 * 		(-Infinity, +Infinity)
	 */

	public static final org.drip.numerical.integration.QuadratureEstimator GaussHermite (
		final int intermediatePointCount)
	{
		return MinusOne_PlusOne (
			org.drip.numerical.integration.AbscissaTransform.GaussHermite(),
			intermediatePointCount
		);
	}
}
