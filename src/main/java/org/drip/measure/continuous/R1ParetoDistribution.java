
package org.drip.measure.continuous;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>R1ParetoDistribution</i> implements the R<sup>1</sup> Pareto Distribution. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Exponential Distribution (2019): Exponential Distribution
 * 				https://en.wikipedia.org/wiki/Exponential_distribution
 * 		</li>
 * 		<li>
 * 			Norton, M., V. Khokhlov, and S. Uryasev (2019): Calculating CVaR and bPOE for Common Probability
 * 				Distributions with Application to Portfolio Optimization and Density Estimation <i>Annals of
 * 				Operations Research</i> <b>299 (1-2)</b> 1281-1315
 * 		</li>
 * 		<li>
 * 			Ross, S. M. (2009): <i>Introduction to Probability and Statistics for Engineers and Scientists
 * 				4<sup>th</sup> Edition</i> <b>Associated Press</b> New York, NY
 * 		</li>
 * 		<li>
 * 			Schmidt, D. F., and D. Makalic (2009): Universal Models for the Exponential Distribution <i>IEEE
 * 				Transactions on Information Theory</i> <b>55 (7)</b> 3087-3090
 * 		</li>
 * 	</ul>
 * 
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>R1ParetoDistribution</i> Constructor</li>
 * 		<li>Retrieve k</li>
 * 		<li>Retrieve Lambda</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Retrieve the Mode of the Distribution</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Retrieve the Quantile Variate of the Distribution</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Median of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ParetoDistribution
	extends R1Distribution
{
	private double _k = Double.NaN;
	private double _lambda = Double.NaN;

	/**
	 * <i>R1ParetoDistribution</i> Constructor
	 * 
	 * @param lambda Rate Parameter
	 * @param k K Parameter
	 * 
	 * @throws Exception Thrown if the Inputs are invalid
	 */

	public R1ParetoDistribution (
		final double lambda,
		final double k)
		throws Exception
	{
		if (!NumberUtil.IsValid (_lambda = lambda) || 0. >= _lambda ||
			!NumberUtil.IsValid (_k = k) || 0. >= _k)
		{
			throw new Exception ("R1ParetoDistribution Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve k
	 * 
	 * @return k
	 */

	public double k()
	{
		return _k;
	}

	/**
	 * Retrieve Lambda
	 * 
	 * @return Lambda
	 */

	public double lambda()
	{
		return _lambda;
	}

	/**
	 * Lay out the Support of the PDF Range
	 * 
	 * @return Support of the PDF Range
	 */

	@Override public double[] support()
	{
		return new double[] {_k, Double.POSITIVE_INFINITY};
	}

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param t Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	@Override public double density (
		final double t)
		throws Exception
	{
		if (!supported (t)) {
			throw new Exception ("R1ParetoDistribution::density => Variate not in Range");
		}

		return _lambda * Math.pow (_k, _lambda) * Math.pow (t, -1. - _lambda);
	}

	/**
	 * Retrieve the Mode of the Distribution
	 * 
	 * @return The Mode of the Distribution
	 * 
	 * @throws Exception Thrown if the Mode cannot be estimated
	 */

	@Override public double mode()
		throws Exception
	{
		return _k;
	}

	/**
	 * Compute the cumulative under the distribution to the given value
	 * 
	 * @param t Variate to which the cumulative is to be computed
	 * 
	 * @return The cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	@Override public double cumulative (
		final double t)
		throws Exception
	{
		if (!supported (t)) {
			throw new Exception ("R1ParetoDistribution::cumulative => Variate not in Range");
		}

		return 1. - Math.pow (_k / t, _lambda);
	}

	/**
	 * Retrieve the Quantile Variate of the Distribution
	 * 
	 * @param p The Quantile Fraction
	 * 
	 * @return The Quantile Variate of the Distribution
	 * 
	 * @throws Exception Thrown if the Quantile Variate cannot be estimated
	 */

	@Override public double quantile (
		final double p)
		throws Exception
	{
		if (!NumberUtil.IsValid (p) || 0. > p || 1. < p) {
			throw new Exception ("R1ParetoDistribution::quantile => p is Invalid");
		}

		if (0. == p) {
			return support()[0];
		}

		if (1. == p) {
			return support()[1];
		}

		return _k + Math.pow (1. - p, -1. * _lambda);
	}

	/**
	 * Retrieve the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 * 
	 * @throws Exception Thrown if the Mean cannot be estimated
	 */

	@Override public double mean()
		throws Exception
	{
		if (1. >= _lambda) {
			throw new Exception ("R1ParetoDistribution::mean => Does not converge for lambda <= 1.");
		}

		return _lambda * _k / (_lambda - 1.);
	}

	/**
	 * Retrieve the Median of the Distribution
	 * 
	 * @return The Median of the Distribution
	 * 
	 * @throws Exception Thrown if the Median cannot be estimated
	 */

	@Override public double median()
		throws Exception
	{
		return _k * Math.pow (2., 1. / _lambda);
	}

	/**
	 * Retrieve the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 * 
	 * @throws Exception Thrown if the Variance cannot be estimated
	 */

	@Override public double variance()
		throws Exception
	{
		if (2. >= _lambda) {
			throw new Exception ("R1ParetoDistribution::variance => Does not converge for lambda <= 2.");
		}

		double mean = mean();

		return _lambda * _k * _k / (_lambda - 2.) - mean * mean;
	}
}
