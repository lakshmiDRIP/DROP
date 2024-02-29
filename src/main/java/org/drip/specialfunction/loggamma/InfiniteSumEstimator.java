
package org.drip.specialfunction.loggamma;

import org.drip.function.definition.PoleResidue;
import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.DerivativeControl;
import org.drip.numerical.estimation.R1Estimate;
import org.drip.numerical.estimation.R1ToR1Estimator;
import org.drip.numerical.estimation.R1ToR1Series;
import org.drip.numerical.integration.NewtonCotesQuadratureGenerator;
import org.drip.specialfunction.gamma.Definitions;
import org.drip.specialfunction.gamma.NemesAnalytic;

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
 * <i>InfiniteSumEstimator</i> estimates Log Gamma using the Infinite Series Infinite Sum. The References
 * 	are:
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
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Compute the Euler Infinite Sum Series of Log Gamma Estimator</li>
 * 		<li>Compute the Weierstrass Infinite Sum Series of Log Gamma Estimator</li>
 * 		<li>Compute the Fourier Infinite Sum Series of Log Gamma Estimator</li>
 * 		<li>Compute the Blagouchine (2015) Infinite Sum Series of Log Gamma Estimator</li>
 * 		<li><i>InfiniteSumEstimator</i> Constructor</li>
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
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/loggamma/README.md">Analytic/Series/Integral Log Gamma Estimators</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class InfiniteSumEstimator extends R1ToR1Estimator
{
	private R1ToR1Series _infiniteSumSeries = null;

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
		try {
			return new InfiniteSumEstimator (InfiniteSumSeries.Euler (termCount), null) {
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					if (!NumberUtil.IsValid (z) || 0. >= z) {
						throw new Exception ("InfiniteSumEstimator::Euler::evaluate => Invalid Inputs");
					}

					return infiniteSumSeries().evaluate (z) - Math.log (z);
				}
			};
		} catch (Exception e) {
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
		try {
			return new InfiniteSumEstimator (InfiniteSumSeries.Weierstrass (termCount), null) {
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					if (!NumberUtil.IsValid (z) || 0. >= z) {
						throw new Exception (
							"InfiniteSumEstimator::Weierstrass::evaluate => Invalid Inputs"
						);
					}

					return infiniteSumSeries().evaluate (z) - Math.log (z) -
						z * Definitions.EULER_MASCHERONI;
				}
			};
		} catch (Exception e) {
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
		try {
			return new InfiniteSumEstimator (InfiniteSumSeries.Fourier (termCount), null) {
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					if (!NumberUtil.IsValid (z) || 0. >= z || 1. <= z) {
						throw new Exception ("InfiniteSumEstimator::Fourier::evaluate => Invalid Inputs");
					}

					return (0.5 - z) * (Definitions.EULER_MASCHERONI + Math.log (2.)) +
						(1. - z) * Math.log (Math.PI) - 0.5 * Math.log (Math.sin (Math.PI * z)) +
						infiniteSumSeries().evaluate (z) / Math.PI;
				}
			};
		} catch (Exception e) {
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
		try {
			return new InfiniteSumEstimator (InfiniteSumSeries.Blagouchine2015 (termCount), null) {
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					if (!NumberUtil.IsValid (z) || 0. >= z || 1. <= z || 0.5 == z) {
						throw new Exception (
							"InfiniteSumEstimator::Blagouchine2015::evaluate => Invalid Inputs"
						);
					}

					return (0.5 - z) * Math.log (2. * Math.PI) + 0.5 * (Math.log (Math.PI) -
						Math.log (Math.sin (Math.PI * z))) +
						infiniteSumSeries().evaluate (z) / java.lang.Math.PI +
						NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (0., 100).integrate (
							new R1ToR1 (null) {
								@Override public double evaluate (
									final double x)
									throws Exception
								{
									return 0. == x || Double.isInfinite (x) ? 0. :
										Math.exp (-1. * termCount * x) * Math.log (x) /
											(Math.cosh (x) - Math.cos (2. * Math.PI * z));
								}
							}
						) * Math.sin (2. * Math.PI * z) / (2. * Math.PI);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>InfiniteSumEstimator</i> Constructor
	 * 
	 * @param infiniteSumSeries R<sup>1</sup> To R<sup>1</sup> Infinite Sum Series
	 * @param derivativeControl Differential Control
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	protected InfiniteSumEstimator (
		final R1ToR1Series infiniteSumSeries,
		final DerivativeControl derivativeControl)
		throws Exception
	{
		super (derivativeControl);

		_infiniteSumSeries = infiniteSumSeries;
	}

	/**
	 * Retrieve the Underlying Infinite Sum Series
	 * 
	 * @return The Underlying Infinite Sum Series
	 */

	public R1ToR1Series infiniteSumSeries()
	{
		return _infiniteSumSeries;
	}

	@Override public R1Estimate seriesEstimateNative (
		final double x)
	{
		return null == _infiniteSumSeries ? seriesEstimate (x, null, null) :
			seriesEstimate (x, _infiniteSumSeries.termWeightMap(), _infiniteSumSeries);
	}

	@Override public PoleResidue poleResidue (
		final double x)
	{
		if (!NumberUtil.IsValid (x)) {
			return null;
		}

		int n = (int) x;

		if (0 != (x - n) || 0. <= x) {
			return PoleResidue.NotAPole (x);
		}

		n = -n;

		try {
			return new PoleResidue (x, (1 == n % 2 ? -1. : 1.) / new NemesAnalytic (null).evaluate (n + 1.));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
