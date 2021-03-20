
package org.drip.specialfunction.loggamma;

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
 * <i>InfiniteSumEstimator</i> estimates Log Gamma using the Infinite Series Infinite Sum. The References
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/loggamma/README.md">Analytic/Series/Integral Log Gamma Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class InfiniteSumEstimator extends org.drip.numerical.estimation.R1ToR1Estimator
{
	private org.drip.numerical.estimation.R1ToR1Series _infiniteSumSeries = null;

	/**
	 * Compute the Euler Infinite Sum Series of Log Gamma Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Euler Infinite Sum Series of Log Gamma Estimator
	 */

	public static final InfiniteSumEstimator Euler (
		final int termCount)
	{
		try
		{
			return new InfiniteSumEstimator (
				org.drip.specialfunction.loggamma.InfiniteSumSeries.Euler (termCount),
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
							("InfiniteSumEstimator::Euler::evaluate => Invalid Inputs");
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

	public static final InfiniteSumEstimator Weierstrass (
		final int termCount)
	{
		try
		{
			return new InfiniteSumEstimator (
				org.drip.specialfunction.loggamma.InfiniteSumSeries.Weierstrass (termCount),
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
							("InfiniteSumEstimator::Weierstrass::evaluate => Invalid Inputs");
					}

					return infiniteSumSeries().evaluate (z) - java.lang.Math.log (z) -
						z * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
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

	public static final InfiniteSumEstimator Fourier (
		final int termCount)
	{
		try
		{
			return new InfiniteSumEstimator (
				org.drip.specialfunction.loggamma.InfiniteSumSeries.Fourier (termCount),
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
							("InfiniteSumEstimator::Fourier::evaluate => Invalid Inputs");
					}

					return (0.5 - z) * (org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI +
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
	 * Compute the Blagouchine (2015) Infinite Sum Series of Log Gamma Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Blagouchine (2015) Infinite Sum Series of Log Gamma Estimator
	 */

	public static final InfiniteSumEstimator Blagouchine2015 (
		final int termCount)
	{
		try
		{
			return new InfiniteSumEstimator (
				org.drip.specialfunction.loggamma.InfiniteSumSeries.Blagouchine2015 (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. >= z || 1. <= z || 0.5 == z)
					{
						throw new java.lang.Exception
							("InfiniteSumEstimator::Blagouchine2015::evaluate => Invalid Inputs");
					}

					return (0.5 - z) * java.lang.Math.log (2. * java.lang.Math.PI) +
						0.5 * (
							java.lang.Math.log (java.lang.Math.PI) -
							java.lang.Math.log (java.lang.Math.sin (java.lang.Math.PI * z))
						) +
						infiniteSumSeries().evaluate (z) / java.lang.Math.PI +
						org.drip.numerical.integration.NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
							0.,
							100
						).integrate (
							new org.drip.function.definition.R1ToR1 (null)
							{
								@Override public double evaluate (
									final double x)
									throws java.lang.Exception
								{
									return 0. == x || java.lang.Double.isInfinite (x) ? 0. :
										java.lang.Math.exp (-1. * termCount * x) *
										java.lang.Math.log (x) / (
											java.lang.Math.cosh (x) -
											java.lang.Math.cos (2. * java.lang.Math.PI * z)
										);
								}
							}
						) * java.lang.Math.sin (2. * java.lang.Math.PI * z) / (2. * java.lang.Math.PI);
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

	protected InfiniteSumEstimator (
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
				(1 == n % 2 ? -1. : 1.) /
					new org.drip.specialfunction.gamma.NemesAnalytic (null).evaluate (n + 1.)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
