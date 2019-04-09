
package org.drip.function.gamma;

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
 * <i>InfiniteProduct</i> estimates Log Gamma using Gamma Infinite Product Series Estimator. The References
 * are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Blagouchine, I. V. (2014): Re-discovery of Malmsten's Integrals, their Evaluation by Contour
 * 				Integration Methods, and some Related Results <i>Ramanujan Journal</i> <b>35 (1)</b> 21-110
 * 		</li>
 * 		<li>
 * 			Borwein, J. M., and R. M. Corless (2017): Gamma Function and the Factorial in the Monthly
 * 				https://arxiv.org/abs/1703.05349 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Davis, P. J. (1959): Leonhard Euler's Integral: A Historical Profile of the Gamma Function
 * 				<i>American Mathematical Monthly</i> <b>66 (10)</b> 849-869
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/gamma/README.md">Estimation Techniques for Gamma Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class InfiniteProduct extends org.drip.numerical.estimation.R1ToR1Estimator
{

	/**
	 * The Euler-Mascheroni Constant
	 */

	public static final double EULER_MASCHERONI = 0.57721566490153286060;

	private org.drip.numerical.estimation.R1ToR1Series _infiniteProductSeries = null;

	/**
	 * Compute the Euler Infinite Product Series of Log Gamma Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Euler Infinite Product Series of Log Gamma Estimator
	 */

	public static final InfiniteProduct Euler (
		final int termCount)
	{
		try
		{
			return new InfiniteProduct (
				org.drip.function.gamma.InfiniteProductSeries.Euler (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z <= 0.)
					{
						throw new java.lang.Exception ("InfiniteProduct::Euler::evaluate => Invalid Inputs");
					}

					return infiniteProductSeries().evaluate (z) - java.lang.Math.log (z);
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
	 * Compute the Weierstrass Infinite Product Series of Log Gamma Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Weierstrass Infinite Product Series of Log Gamma Estimator
	 */

	public static final InfiniteProduct Weierstrass (
		final int termCount)
	{
		try
		{
			return new InfiniteProduct (
				org.drip.function.gamma.InfiniteProductSeries.Weierstrass (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z <= 0.)
					{
						throw new java.lang.Exception
							("InfiniteProduct::Weierstrass::evaluate => Invalid Inputs");
					}

					return infiniteProductSeries().evaluate (z) - java.lang.Math.log (z) -
						z * EULER_MASCHERONI;
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
	 * InfiniteProduct Constructor
	 * 
	 * @param infiniteProductSeries R<sup>1</sup> To R<sup>1</sup> Infinite Product Series
	 * @param dc Differential Control
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	protected InfiniteProduct (
		final org.drip.numerical.estimation.R1ToR1Series infiniteProductSeries,
		final org.drip.numerical.differentiation.DerivativeControl dc)
		throws java.lang.Exception
	{
		super (dc);

		_infiniteProductSeries = infiniteProductSeries;
	}

	/**
	 * Retrieve the Underlying Infinite Product Series
	 * 
	 * @return The Underlying Infinite Product Series
	 */

	public org.drip.numerical.estimation.R1ToR1Series infiniteProductSeries()
	{
		return _infiniteProductSeries;
	}

	@Override public org.drip.numerical.estimation.R1Estimate seriesEstimateNative (
		final double x)
	{
		return null == _infiniteProductSeries ? seriesEstimate (
			x,
			null,
			null
		) : seriesEstimate (
			x,
			_infiniteProductSeries.termWeightMap(),
			_infiniteProductSeries
		);
	}
}
