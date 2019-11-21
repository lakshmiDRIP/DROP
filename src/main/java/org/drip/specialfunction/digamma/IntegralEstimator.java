
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
 * <i>IntegralEstimator</i> demonstrates the Estimation of the Digamma Function using the Integral
 * Representations. The References are:
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

public class IntegralEstimator
{

	/**
	 * Generate the Gaussian Integral Digamma Estimator
	 * 
	 * @return The Gaussian Integral Digamma Estimator
	 */

	public static final org.drip.numerical.estimation.R1ToR1IntegrandEstimator Gauss()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1IntegrandEstimator (
				null,
				new org.drip.numerical.estimation.R1ToR1IntegrandGenerator()
				{
					@Override public org.drip.numerical.estimation.R1ToR1Estimator integrand (
						final double z)
					{
						try
						{
							return new org.drip.numerical.estimation.R1ToR1Estimator (null)
							{
								@Override public double evaluate (
									final double t)
									throws java.lang.Exception
								{
									double ePowerMinusT = java.lang.Math.exp (-t);

									return 0. == t || java.lang.Double.isInfinite (t) ? 0. :
										(ePowerMinusT / t) - (java.lang.Math.exp (-z * t) /
											(1. - ePowerMinusT));
								}
							};
						}
						catch (java.lang.Exception e)
						{
							e.printStackTrace();
						}

						return null;
					}
				},
				org.drip.numerical.estimation.R1ToR1IntegrandEstimator.INTEGRAND_LIMITS_SETTING_ZERO_INFINITY,
				1.,
				new org.drip.numerical.estimation.R1ToR1Estimator (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return 0.;
					}
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Gauss-Euler-Mascheroni Integral Digamma Estimator
	 * 
	 * @return The Gauss-Euler-Mascheroni Integral Digamma Estimator
	 */

	public static final org.drip.numerical.estimation.R1ToR1IntegrandEstimator GaussEulerMascheroni()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1IntegrandEstimator (
				null,
				new org.drip.numerical.estimation.R1ToR1IntegrandGenerator()
				{
					@Override public org.drip.numerical.estimation.R1ToR1Estimator integrand (
						final double z)
					{
						try
						{
							return new org.drip.numerical.estimation.R1ToR1Estimator (null)
							{
								@Override public double evaluate (
									final double t)
									throws java.lang.Exception
								{
									return 0. == t ? 1. -
										org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI : 1. == t
										? -org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI : (
											1. - java.lang.Math.pow (
												t,
												z - 1.
											)
										) / (1. - t);
								}
							};
						}
						catch (java.lang.Exception e)
						{
							e.printStackTrace();
						}

						return null;
					}
				},
				org.drip.numerical.estimation.R1ToR1IntegrandEstimator.INTEGRAND_LIMITS_SETTING_ZERO_ONE,
				1.,
				new org.drip.numerical.estimation.R1ToR1Estimator (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return -org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;
					}
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Dirichlet Integral Digamma Estimator
	 * 
	 * @return The Dirichlet Integral Digamma Estimator
	 */

	public static final org.drip.numerical.estimation.R1ToR1IntegrandEstimator Dirichlet()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1IntegrandEstimator (
				null,
				new org.drip.numerical.estimation.R1ToR1IntegrandGenerator()
				{
					@Override public org.drip.numerical.estimation.R1ToR1Estimator integrand (
						final double z)
					{
						try
						{
							return new org.drip.numerical.estimation.R1ToR1Estimator (null)
							{
								@Override public double evaluate (
									final double t)
									throws java.lang.Exception
								{
									return 0. == t || java.lang.Double.isInfinite (t) ? 0. : (
										java.lang.Math.exp (-t) - java.lang.Math.pow (
											1. + t,
											-z
										)
									) / t;
								}
							};
						}
						catch (java.lang.Exception e)
						{
							e.printStackTrace();
						}

						return null;
					}
				},
				org.drip.numerical.estimation.R1ToR1IntegrandEstimator.INTEGRAND_LIMITS_SETTING_ZERO_INFINITY,
				1.,
				new org.drip.numerical.estimation.R1ToR1Estimator (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return 0.;
					}
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Binet Second Integral Digamma Estimator
	 * 
	 * @return The Binet Second Integral Digamma Estimator
	 */

	public static final org.drip.numerical.estimation.R1ToR1IntegrandEstimator BinetSecond()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1IntegrandEstimator (
				null,
				new org.drip.numerical.estimation.R1ToR1IntegrandGenerator()
				{
					@Override public org.drip.numerical.estimation.R1ToR1Estimator integrand (
						final double z)
					{
						try
						{
							return new org.drip.numerical.estimation.R1ToR1Estimator (null)
							{
								@Override public double evaluate (
									final double t)
									throws java.lang.Exception
								{
									return 0. == t || java.lang.Double.isInfinite (t) ? 0. : t / (
										(t * t + z * z) *
											(java.lang.Math.exp (2. * java.lang.Math.PI * t) - 1.)
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
				},
				org.drip.numerical.estimation.R1ToR1IntegrandEstimator.INTEGRAND_LIMITS_SETTING_ZERO_INFINITY,
				-2.,
				new org.drip.numerical.estimation.R1ToR1Estimator (null)
				{
					@Override public double evaluate (
						final double z)
						throws java.lang.Exception
					{
						return 0. == z ? 0. : java.lang.Math.log (z) - 0.5 / z;
					}
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
