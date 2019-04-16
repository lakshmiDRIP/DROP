
package org.drip.gamma.log;

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
 * <i>InfiniteSum</i> estimates Log Gamma using the Infinite Series Infinite Sum. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/gamma/README.md">Gamma and Related Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/gamma/log/README.md">Analytic/Series/Integral Log Gamma Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class InfiniteSum extends org.drip.numerical.estimation.R1ToR1Estimator
{
	private org.drip.numerical.estimation.R1ToR1Series _infiniteSumSeries = null;

	/**
	 * Compute the Euler Infinite Sum Series of Log Gamma Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Euler Infinite Sum Series of Log Gamma Estimator
	 */

	public static final InfiniteSum Euler (
		final int termCount)
	{
		try
		{
			return new InfiniteSum (
				org.drip.gamma.log.InfiniteSumSeries.Euler (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z <= 0.)
					{
						throw new java.lang.Exception ("InfiniteSum::Euler::evaluate => Invalid Inputs");
					}

					return infiniteSumSeries().evaluate (z) - java.lang.Math.log (z);
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
	 * Compute the Weierstrass Infinite Sum Series of Log Gamma Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Weierstrass Infinite Sum Series of Log Gamma Estimator
	 */

	public static final InfiniteSum Weierstrass (
		final int termCount)
	{
		try
		{
			return new InfiniteSum (
				org.drip.gamma.log.InfiniteSumSeries.Weierstrass (termCount),
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
							("InfiniteSum::Weierstrass::evaluate => Invalid Inputs");
					}

					return infiniteSumSeries().evaluate (z) - java.lang.Math.log (z) -
						z * org.drip.gamma.estimator.Definitions.EULER_MASCHERONI;
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
	 * Compute the Fourier Infinite Sum Series of Log Gamma Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Fourier Infinite Sum Series of Log Gamma Estimator
	 */

	public static final InfiniteSum Fourier (
		final int termCount)
	{
		try
		{
			return new InfiniteSum (
				org.drip.gamma.log.FourierSeries.MalmstenBlagouchine (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. >= z || 1. <= z)
					{
						throw new java.lang.Exception
							("InfiniteSum::Fourier::evaluate => Invalid Inputs");
					}

					return (0.5 - z) * (org.drip.gamma.estimator.Definitions.EULER_MASCHERONI +
						java.lang.Math.log (2.)) + (1. - z) * java.lang.Math.log (java.lang.Math.PI) -
						0.5 * java.lang.Math.log (java.lang.Math.sin (java.lang.Math.PI * z)) +
						infiniteSumSeries().evaluate (z) / java.lang.Math.PI;
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
	 * InfiniteSum Constructor
	 * 
	 * @param infiniteSumSeries R<sup>1</sup> To R<sup>1</sup> Infinite Sum Series
	 * @param dc Differential Control
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	protected InfiniteSum (
		final org.drip.numerical.estimation.R1ToR1Series infiniteSumSeries,
		final org.drip.numerical.differentiation.DerivativeControl dc)
		throws java.lang.Exception
	{
		super (dc);

		_infiniteSumSeries = infiniteSumSeries;
	}

	/**
	 * Retrieve the Underlying Infinite Sum Series
	 * 
	 * @return The Underlying Infinite Sum Series
	 */

	public org.drip.numerical.estimation.R1ToR1Series infiniteSumSeries()
	{
		return _infiniteSumSeries;
	}

	@Override public org.drip.numerical.estimation.R1Estimate seriesEstimateNative (
		final double x)
	{
		return null == _infiniteSumSeries ? seriesEstimate (
			x,
			null,
			null
		) : seriesEstimate (
			x,
			_infiniteSumSeries.termWeightMap(),
			_infiniteSumSeries
		);
	}

	@Override public org.drip.function.definition.PoleResidue poleResidue (
		final double x)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (x))
		{
			return null;
		}

		int n = (int) x;

		if (0 != (x - n) || x >= 0.)
		{
			return org.drip.function.definition.PoleResidue.NotAPole (x);
		}

		n = -n;

		try
		{
			return new org.drip.function.definition.PoleResidue (
				x,
				(1 == n % 2 ? -1. : 1.) / new org.drip.gamma.estimator.NemesAnalytic (null).evaluate (n + 1.)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
