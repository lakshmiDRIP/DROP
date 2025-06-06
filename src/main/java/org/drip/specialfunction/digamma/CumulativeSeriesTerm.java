
package org.drip.specialfunction.digamma;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.estimation.R1ToR1SeriesTerm;

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
 * <i>CumulativeSeriesTerm</i> implements a Single Term in the Cumulative Series for Digamma Estimation. The
 * 	References are:
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
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the Abramowitz-Stegun (2017) Cumulative Sum Series Term for DiGamma</li>
 * 		<li>Construct the Mezo-Hoffman (2017) Cumulative Sum Series Term for DiGamma</li>
 * 		<li>Construct the Gauss Cumulative Sum Series Term for DiGamma</li>
 * 		<li>Construct the Asymptotic Cumulative Sum Series Term for DiGamma</li>
 * 		<li>Construct the Asymptotic Cumulative Sum Series Term for exp (-diGamma)</li>
 * 		<li>Construct the Asymptotic Cumulative Sum Series Term for exp (diGamma + 0.5)</li>
 * 		<li>Construct the Taylor-Riemann Zeta Series Term for Digamma</li>
 * 		<li>Construct the Newton-Stern Series Term for Digamma</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/digamma/README.md">Estimation Techniques for Digamma Function</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CumulativeSeriesTerm
{

	/**
	 * Construct the Abramowitz-Stegun (2007) Cumulative Sum Series Term for DiGamma
	 * 
	 * @return The Abramowitz-Stegun (2007) Cumulative Sum Series Term for DiGamma
	 */

	public static final R1ToR1SeriesTerm AbramowitzStegun2007()
	{
		try {
			return new R1ToR1SeriesTerm() {
				@Override public double value (
					final int order,
					final double z)
					throws Exception
				{
					if (0 >= order || !NumberUtil.IsValid (z) || order == -z) {
						throw new Exception (
							"CumulativeSeriesTerm::AbramowitzStegun2007::value => Invalid Inputs"
						);
					}

					return z / (order * (order + z));
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Mezo-Hoffman (2017) Cumulative Sum Series Term for DiGamma
	 * 
	 * @param saddlePointArray Array of the Saddle Points
	 * 
	 * @return The Mezo-Hoffman (2017) Cumulative Sum Series Term for DiGamma
	 */

	public static final R1ToR1SeriesTerm MezoHoffman2017 (
		final double[] saddlePointArray)
	{
		if (null == saddlePointArray) {
			return null;
		}

		final int saddlePointCount = saddlePointArray.length;

		if (0 == saddlePointCount || !NumberUtil.IsValid (saddlePointArray)) {
			return null;
		}

		try {
			return new R1ToR1SeriesTerm() {
				@Override public double value (
					final int order,
					final double z)
					throws Exception
				{
					if (0 > order || order >= saddlePointCount || !NumberUtil.IsValid (z) || 0. >= z) {
						throw new Exception (
							"CumulativeSeriesTerm::MezoHoffman2017::value => Invalid Inputs"
						);
					}

					double zOverSaddlePoint = z / saddlePointArray[order];

					return zOverSaddlePoint * Math.log (1. - zOverSaddlePoint);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Gauss Cumulative Sum Series Term for DiGamma
	 * 
	 * @param termCount Term Count
	 * 
	 * @return The Gauss Cumulative Sum Series Term for DiGamma
	 */

	public static final R1ToR1SeriesTerm Gauss (
		final int termCount)
	{
		try {
			return new R1ToR1SeriesTerm() {
				@Override public double value (
					final int order,
					final double z)
					throws Exception
				{
					if (1 > order || !NumberUtil.IsValid (z)) {
						throw new Exception ("CumulativeSeriesTerm::Gauss::value => Invalid Inputs");
					}

					return Math.cos (2. * Math.PI * order * z) *
						Math.log (Math.sin (Math.PI * order / termCount));
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Asymptotic Cumulative Sum Series Term for DiGamma
	 * 
	 * @return The Asymptotic Cumulative Sum Series Term for DiGamma
	 */

	public static final R1ToR1SeriesTerm Asymptotic()
	{
		try {
			return new R1ToR1SeriesTerm() {
				@Override public double value (
					final int order,
					final double z)
					throws Exception
				{
					if (0 >= order || !NumberUtil.IsValid (z) || 0. == z) {
						throw new Exception ("CumulativeSeriesTerm::Asymptotic::value => Invalid Inputs");
					}

					return Math.pow (z, -2 * order);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Asymptotic Cumulative Sum Series Term for exp (-diGamma)
	 * 
	 * @return The Asymptotic Cumulative Sum Series Term for exp (-diGamma)
	 */

	public static final R1ToR1SeriesTerm ExponentialAsymptote()
	{
		try {
			return new R1ToR1SeriesTerm() {
				@Override public double value (
					final int order,
					final double z)
					throws Exception
				{
					if (0 >= order || !NumberUtil.IsValid (z) || 0 == z) {
						throw new Exception (
							"CumulativeSeriesTerm::ExponentialAsymptote::value => Invalid Inputs"
						);
					}

					return Math.pow (z, -1 * order);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Asymptotic Cumulative Sum Series Term for exp (diGamma + 0.5)
	 * 
	 * @return The Asymptotic Cumulative Sum Series Term for exp (-diGamma + 0.5)
	 */

	public static final R1ToR1SeriesTerm ExponentialAsymptoteHalfShifted()
	{
		try {
			return new R1ToR1SeriesTerm() {
				@Override public double value (
					final int order,
					final double z)
					throws Exception
				{
					if (0 >= order || !NumberUtil.IsValid (z)) {
						throw new Exception (
							"CumulativeSeriesTerm::ExponentialAsymptoteHalfShifted::value => Invalid Inputs"
						);
					}

					return Math.pow (z, 1 - 2 * order);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Taylor-Riemann Zeta Series Term for Digamma
	 * 
	 * @param riemannZetaEstimator The Riemann-Zeta Estimator
	 * 
	 * @return The Taylor-Riemann Zeta Series Term for Digamma
	 */

	public static final R1ToR1SeriesTerm TaylorRiemannZeta (
		final R1ToR1 riemannZetaEstimator)
	{
		if (null == riemannZetaEstimator) {
			return null;
		}

		try {
			return new R1ToR1SeriesTerm() {
				@Override public double value (
					final int order,
					final double z)
					throws Exception
				{
					if (0 >= order || !NumberUtil.IsValid (z)) {
						throw new Exception (
							"CumulativeSeriesTerm::TaylorRiemannZeta::value => Invalid Inputs"
						);
					}

					return (1 == order % 2 ? -1. : 1.) * riemannZetaEstimator.evaluate (order + 1) *
						Math.pow (z, order);
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Newton-Stern Series Term for Digamma
	 * 
	 * @return The Newton-Stern Series Term for Digamma
	 */

	public static final R1ToR1SeriesTerm NewtonStern()
	{
		try {
			return new R1ToR1SeriesTerm() {
				@Override public double value (
					final int order,
					final double z)
					throws Exception
				{
					if (0 >= order || !NumberUtil.IsValid (z)) {
						throw new Exception (
							"CumulativeSeriesTerm::TaylorRiemannZeta::value => Invalid Inputs"
						);
					}

					return (1 == order % 2 ? -1. : 1.) * NumberUtil.NCK ((int) z, order) / order;
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
