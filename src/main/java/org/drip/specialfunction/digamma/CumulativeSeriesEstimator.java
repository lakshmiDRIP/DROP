
package org.drip.specialfunction.digamma;

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
 * <i>CumulativeSeriesEstimator</i> implements the Cumulative Series Based Digamma Estimation. The References
 * are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): Handbook of Mathematics Functions <b>Dover Book on
 * 				Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Blagouchine, I. V. (2018): Three Notes on Ser's and Hasse's Representations for the
 * 				Zeta-Functions https://arxiv.org/abs/1606.02044 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Mezo, I., and M. E. Hoffman (2017): Zeros of the Digamma Function and its Barnes G-function
 * 				Analogue <i>Integral Transforms and Special Functions</i> <b>28 (28)</b> 846-858
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Digamma Function https://en.wikipedia.org/wiki/Digamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/digamma/README.md">Estimation Techniques for Digamma Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class CumulativeSeriesEstimator extends org.drip.numerical.estimation.R1ToR1Estimator
{
	private org.drip.numerical.estimation.R1ToR1Series _cumulativeSeries = null;

	/**
	 * Compute the Abramowitz-Stegun (2007) Cumulative Series of Digamma Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return The Abramowitz-Stegun (2007) Cumulative Series of Digamma Estimator
	 */

	public static final CumulativeSeriesEstimator AbramowitzStegun2007 (
		final int termCount)
	{
		try
		{
			return new CumulativeSeriesEstimator (
				org.drip.specialfunction.digamma.CumulativeSeries.AbramowitzStegun2007 (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::AbramowitzStegun2007::evaluate => Invalid Inputs");
					}

					return cumulativeSeries().evaluate (z - 1.) -
						org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
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
	 * Compute the Harmonic Cumulative Series of Digamma Estimator
	 * 
	 * @return The Harmonic Cumulative Series of Digamma Estimator
	 */

	public static final CumulativeSeriesEstimator Harmonic()
	{
		try
		{
			return new CumulativeSeriesEstimator (
				null,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. >= z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::Harmonic::evaluate => Invalid Inputs");
					}

					double harmonicEstimate = 0.;

					for (int i = 1; i < (int) z; ++i)
					{
						harmonicEstimate = harmonicEstimate + (1. / i);
					}

					return harmonicEstimate - org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
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
	 * Compute the Half-Integer Cumulative Series of Digamma Estimator
	 * 
	 * @return The Half-Integer Cumulative Series of Digamma Estimator
	 */

	public static final CumulativeSeriesEstimator HalfInteger()
	{
		try
		{
			return new CumulativeSeriesEstimator (
				null,
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z || 0.5 != z - (int) z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::HalfInteger::evaluate => Invalid Inputs");
					}

					double halfIntegerEstimate = 0.;

					for (int i = 1; i <= (int) z; ++i)
					{
						halfIntegerEstimate = halfIntegerEstimate + (1. / (-0.5 + i));
					}

					return halfIntegerEstimate - org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI
						- java.lang.Math.log (4.);
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
	 * Compute the Saddle-Point Cumulative Series of Digamma Estimator
	 * 
	 * @param logGammaEstimator The Log Gamma Estimator
	 * @param saddlePointFunction The Saddle Point Generation Function
	 * @param saddlePointCount The Saddle Point Count
	 * 
	 * @return The Saddle-Point Cumulative Series of Digamma Estimator
	 */

	public static final CumulativeSeriesEstimator MezoHoffman2017 (
		final org.drip.function.definition.R1ToR1 logGammaEstimator,
		final org.drip.function.definition.R1ToR1 saddlePointFunction,
		final int saddlePointCount)
	{
		if (null == logGammaEstimator)
		{
			return null;
		}

		try
		{
			return new CumulativeSeriesEstimator (
				org.drip.specialfunction.digamma.CumulativeSeries.MezoHoffman2017 (
					saddlePointFunction,
					saddlePointCount
				),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::MezoHoffman2017::evaluate => Invalid Inputs");
					}

					return -1. * java.lang.Math.exp (
						logGammaEstimator.evaluate (z) + 
						cumulativeSeries().evaluate (z) +
						2. * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI * z
					);
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
	 * Compute the Gauss Cumulative Series of Digamma Estimator
	 * 
	 * @param termCount Term Count
	 * 
	 * @return The Gauss Cumulative Series of Digamma Estimator
	 */

	public static final CumulativeSeriesEstimator Gauss (
		final int termCount)
	{
		try
		{
			return new CumulativeSeriesEstimator (
				org.drip.specialfunction.digamma.CumulativeSeries.Gauss (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z || 1. < z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::Gauss::evaluate => Invalid Inputs");
					}

					return 2. * cumulativeSeries().evaluate (z) -
						org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI -
						java.lang.Math.log (2. * termCount) -
						0.5 * java.lang.Math.PI / java.lang.Math.tan (java.lang.Math.PI * z);
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
	 * Compute the Asymptotic Cumulative Series of Digamma Estimator
	 * 
	 * @return The Asymptotic Cumulative Series of Digamma Estimator
	 */

	public static final CumulativeSeriesEstimator Asymptotic()
	{
		try
		{
			return new CumulativeSeriesEstimator (
				org.drip.specialfunction.digamma.CumulativeSeries.Asymptotic(),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::Asymptotic::evaluate => Invalid Inputs");
					}

					return java.lang.Math.log (z) - 0.5 / z + cumulativeSeries().evaluate (z);
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
	 * Compute the Exponential Asymptotic Cumulative Series of Digamma Estimator
	 * 
	 * @return The Exponential Asymptotic Cumulative Series of Digamma Estimator
	 */

	public static final CumulativeSeriesEstimator ExponentialAsymptote()
	{
		try
		{
			return new CumulativeSeriesEstimator (
				org.drip.specialfunction.digamma.CumulativeSeries.ExponentialAsymptote(),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::ExponentialAsymptote::evaluate => Invalid Inputs");
					}

					return cumulativeSeries().evaluate (z);
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
	 * Compute the Exponential Asymptotic Cumulative Series of Digamma + 0.5 Estimator
	 * 
	 * @return The Exponential Asymptotic Cumulative Series of Digamma + 0.5 Estimator
	 */

	public static final CumulativeSeriesEstimator ExponentialAsymptoteHalfShifted()
	{
		try
		{
			return new CumulativeSeriesEstimator (
				org.drip.specialfunction.digamma.CumulativeSeries.ExponentialAsymptoteHalfShifted(),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z))
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::ExponentialAsymptoteHalfShifted::evaluate => Invalid Inputs");
					}

					return z - 0.5 + cumulativeSeries().evaluate (z - 0.5);
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
	 * Compute the Taylor-Riemann Zeta Cumulative Series of Digamma Estimator
	 * 
	 * @param riemannZetaEstimator The Riemann-Zeta Estimator
	 * @param termCount Term Count
	 * 
	 * @return The Taylor-Riemann Zeta Cumulative Series of Digamma Estimator
	 */

	public static final CumulativeSeriesEstimator TaylorRiemannZeta (
		final org.drip.function.definition.R1ToR1 riemannZetaEstimator,
		final int termCount)
	{
		try
		{
			return new CumulativeSeriesEstimator (
				org.drip.specialfunction.digamma.CumulativeSeries.TaylorRiemannZeta (
					riemannZetaEstimator,
					termCount
				),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z)
					{
						throw new java.lang.Exception
							("CumulativeSeriesEstimator::TaylorRiemannZeta::evaluate => Invalid Inputs");
					}

					return -1. * cumulativeSeries().evaluate (z - 1.) -
						org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	protected CumulativeSeriesEstimator (
		final org.drip.numerical.estimation.R1ToR1Series cumulativeSeries,
		final org.drip.numerical.differentiation.DerivativeControl dc)
		throws java.lang.Exception
	{
		super (dc);

		_cumulativeSeries = cumulativeSeries;
	}

	/**
	 * Retrieve the Underlying Cumulative Series
	 * 
	 * @return The Underlying Cumulative Series
	 */

	public org.drip.numerical.estimation.R1ToR1Series cumulativeSeries()
	{
		return _cumulativeSeries;
	}
}
