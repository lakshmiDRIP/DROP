
package org.drip.specialfunction.beta;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.numerical.common.NumberUtil;

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
 * <i>CombinatorialEstimate</i> implements the Combinatorial Function Estimate using Beta-based Schemes. The
 * 	References are:
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
 * 		<li>Estimate the Binomial Coefficient Using the Beta Function</li>
 * 		<li>Estimate the Binomial Coefficient Using a Continuous Interpolation Function</li>
 * 		<li>Compute the Cumulative Binomial Distribution Function for the specified n, k, and p</li>
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

public class CombinatorialEstimate
{

	/**
	 * Estimate the Binomial Coefficient Using the Beta Function
	 * 
	 * @param n n
	 * @param k k
	 * @param betaEstimator The Beta Function Estimator
	 * 
	 * @return Binomial Coefficient Using the Beta Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double BetaBinomial (
		final double n,
		final double k,
		final R2ToR1 betaEstimator)
		throws Exception
	{
		if (!NumberUtil.IsValid (n) || 0. >= n || !NumberUtil.IsValid (k) || 0. >= k || n < k ||
			null == betaEstimator) {
			throw new Exception ("CombinatorialEstimate::BetaBinomial => Invalid Inputs");
		}

		return 1. / ((n + 1.) * betaEstimator.evaluate (n - k + 1., k + 1.));
	}

	/**
	 * Estimate the Binomial Coefficient Using a Continuous Interpolation Function
	 * 
	 * @param n n
	 * @param k k
	 * @param gammaEstimator The Gamma Function Estimator
	 * 
	 * @return Binomial Coefficient Using a Continuous Interpolation Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double GammaBinomial (
		final double n,
		final double k,
		final R1ToR1 gammaEstimator)
		throws Exception
	{
		if (!NumberUtil.IsValid (n) || 0. >= n || !NumberUtil.IsValid (k) || 0. >= k || n < k ||
			null == gammaEstimator) {
			throw new Exception ("CombinatorialEstimate::GammaBinomial => Invalid Inputs");
		}

		double gammaBinomial = (1 == n % 2 ? -1. : 1.) * gammaEstimator.evaluate (n + 1.) *
			Math.sin (Math.PI * k) / Math.PI;

		for (int i = 0; i <= n; ++i) {
			gammaBinomial = gammaBinomial / (k - i);
		}

		return gammaBinomial;
	}

	/**
	 * Compute the Cumulative Binomial Distribution Function for the specified n, k, and p
	 * 
	 * @param n n
	 * @param k k
	 * @param p p
	 * @param incompleteRegularizedEstimator The Incomplete Regularized Beta Estimator
	 * 
	 * @return The Cumulative Binomial Distribution Function Value
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double CumulativeBinomialDistribution (
		final double n,
		final double k,
		final double p,
		final IncompleteRegularizedEstimator incompleteRegularizedEstimator)
		throws Exception
	{
		if (!NumberUtil.IsValid (n) || 0. >= n || !NumberUtil.IsValid (k) || 0. >= k || n < k ||
			!NumberUtil.IsValid (p) || 0. > p || 1. < p || null == incompleteRegularizedEstimator) {
			throw new Exception ("CombinatorialEstimate::CumulativeBinomialDistribution => Invalid Inputs");
		}

		return incompleteRegularizedEstimator.evaluate (1. - p, n - k, k + 1.);
	}
}
