
package org.drip.specialfunction.beta;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.integration.NewtonCotesQuadratureGenerator;
import org.drip.specialfunction.definition.BetaEstimator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>IntegrandEstimator</i> implements the Beta Function using Integrand Estimation Schemes. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
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
 * 			Wikipedia (2019): Beta Function https://en.wikipedia.org/wiki/Beta_function
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 * 
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the Beta Estimator from the Trigonometric Integral</li>
 * 		<li>Construct the Beta Estimator from the Euler Integral of the First Kind</li>
 * 		<li>Construct the Beta Estimator from the Euler Integral of the First Kind Exponent N</li>
 * 		<li>Construct the Beta Estimator from the Euler Integral of the First Kind over the Right Half Plane</li>
 * 		<li>Retrieve the Quadrature Count</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/beta/README.md">Estimation Techniques for Beta Function</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class IntegrandEstimator extends BetaEstimator
{
	private int _quadratureCount = -1;

	/**
	 * Construct the Beta Estimator from the Trigonometric Integral
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Beta Estimator from the Trigonometric Integral
	 */

	public static final IntegrandEstimator Trigonometric (
		final int quadratureCount)
	{
		try {
			return new IntegrandEstimator (quadratureCount) {
				@Override public double evaluate (
					final double x,
					final double y)
					throws Exception
				{
					if (!NumberUtil.IsValid (x) || !NumberUtil.IsValid (y)) {
						throw new Exception (
							"IntegrandEstimator::Trigonometric::evaluate => Invalid Inputs"
						);
					}

					return 2. * NewtonCotesQuadratureGenerator.Zero_PlusOne (
						0.,
						0.5 * Math.PI,
						quadratureCount
					).integrate (
						new R1ToR1 (null) {
							@Override public double evaluate (
								final double theta)
								throws Exception
							{
								return 0. == theta || 0.5 * Math.PI == theta ? 0. :
									Math.pow (Math.sin (theta), 2. * x - 1.) *
									Math.pow (Math.cos (theta), 2. * y - 1.);
							}
						}
					);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Beta Estimator from the Euler Integral of the First Kind
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Beta Estimator from the Euler Integral of the First Kind
	 */

	public static final IntegrandEstimator EulerFirst (
		final int quadratureCount)
	{
		try {
			return new IntegrandEstimator (quadratureCount) {
				@Override public double evaluate (
					final double x,
					final double y)
					throws Exception
				{
					if (!NumberUtil.IsValid (x) || !NumberUtil.IsValid (y)) {
						throw new Exception ("IntegrandEstimator::EulerFirst::evaluate => Invalid Inputs");
					}

					return NewtonCotesQuadratureGenerator.Zero_PlusOne (0., 1., 1000000).integrate (
						new R1ToR1 (null) {
							@Override public double evaluate (
								final double t)
								throws Exception
							{
								return 0. == t || 1. == t ? 0. :
									Math.pow (t, x - 1.) * Math.pow (1. - t, y - 1.);
							}
						}
					);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Beta Estimator from the Euler Integral of the First Kind Exponent N
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * @param exponent Exponent
	 * 
	 * @return Beta Estimator from the Euler Integral of the First Kind
	 */

	public static final IntegrandEstimator EulerFirstN (
		final int quadratureCount,
		final double exponent)
	{
		if (!NumberUtil.IsValid (exponent) || 0. >= exponent) {
			return null;
		}

		try {
			return new IntegrandEstimator (quadratureCount) {
				@Override public double evaluate (
					final double x,
					final double y)
					throws Exception
				{
					if (!NumberUtil.IsValid (x) || !NumberUtil.IsValid (y)) {
						throw new Exception ("IntegrandEstimator::EulerFirstN::evaluate => Invalid Inputs");
					}

					return exponent * NewtonCotesQuadratureGenerator.Zero_PlusOne (
						0.,
						1.,
						quadratureCount
					).integrate (
						new R1ToR1 (null) {
							@Override public double evaluate (
								final double t)
								throws Exception
							{
								return 0. == t || 1. == t ? 0. :
									Math.pow (t, exponent * x - 1.) *
									Math.pow (Math.pow (1. - t, exponent), y - 1.);
							}
						}
					);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Beta Estimator from the Euler Integral of the First Kind over the Right Half Plane
	 * 
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @return Beta Estimator from the Euler Integral of the First Kind over the Right Half Plane
	 */

	public static final IntegrandEstimator EulerFirstRightPlane (
		final int quadratureCount)
	{
		try {
			return new IntegrandEstimator (quadratureCount)
			{
				@Override public double evaluate (
					final double x,
					final double y)
					throws Exception
				{
					if (!NumberUtil.IsValid (x) || !NumberUtil.IsValid (y)) {
						throw new Exception (
							"IntegrandEstimator::EulerFirstRightPlane::evaluate => Invalid Inputs"
						);
					}

					return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (
						0.,
						quadratureCount
					).integrate (
						new R1ToR1 (null) {
							@Override public double evaluate (
								final double t)
								throws Exception
							{
								return 0. == t || Double.isInfinite (t) ? 0. :
									Math.pow (t, x - 1.) / Math.pow (1. + t, x + y);
							}
						}
					);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected IntegrandEstimator (
		final int quadratureCount)
		throws Exception
	{
		if (0 >= (_quadratureCount = quadratureCount)) {
			throw new Exception ("IntegrandEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Quadrature Count
	 * 
	 * @return The Quadrature Count
	 */

	public int quadratureCount()
	{
		return _quadratureCount;
	}
}
