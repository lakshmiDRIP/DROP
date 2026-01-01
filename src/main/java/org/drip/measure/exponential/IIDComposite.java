
package org.drip.measure.exponential;

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
 * <i>IIDComposite</i> generates Metrics for a Composite Set of i.i.d. R<sup>1</sup> Exponential
 *  Distributions. The References are:
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
 * 		<li>Get the Maximum of the specified Order Statistic</li>
 * 		<li>Get the Minimum of the specified Order Statistic</li>
 * 		<li>Compute the Joint Moment of the Order Statistics for a Set of i.i.d. Distributions</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/exponential/README.md">R<sup>1</sup> Exponential Distribution Implementation/Properties</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IIDComposite
{

	/**
	 * Get the Maximum of the specified Order Statistic
	 * 
	 * @param orderStatistic1 First Order Statistic
	 * @param orderStatistic2 Second Order Statistic
	 * 
	 * @return Maximum if the specified Order Statistics Pair
	 */

	public static final int MaxOrderStatistic (
		final int orderStatistic1,
		final int orderStatistic2)
	{
		return orderStatistic1 > orderStatistic2 ? orderStatistic1 : orderStatistic2;
	}

	/**
	 * Get the Minimum of the specified Order Statistic
	 * 
	 * @param orderStatistic1 First Order Statistic
	 * @param orderStatistic2 Second Order Statistic
	 * 
	 * @return Minimum if the specified Order Statistics Pair
	 */

	public static final int MinOrderStatistic (
		final int orderStatistic1,
		final int orderStatistic2)
	{
		return orderStatistic1 <= orderStatistic2 ? orderStatistic1 : orderStatistic2;
	}

	/**
	 * Compute the Joint Moment of the Order Statistics for a Set of i.i.d. Distributions
	 * 
	 * @param r1RateDistribution R<sup>1</sup> Exponential Distribution
	 * @param variateCount Variate Count
	 * @param orderStatistic1 First Order Statistic
	 * @param orderStatistic2 Second Order Statistic
	 * 
	 * @return Joint Moment of the Order Statistics for a Set of i.i.d. Distributions
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double OrderStatisticsJointMoment (
		final R1RateDistribution r1RateDistribution,
		final int variateCount,
		final int orderStatistic1,
		final int orderStatistic2)
		throws Exception
	{
		if (null == r1RateDistribution ||
			1 > orderStatistic1 || orderStatistic1 > variateCount ||
			1 > orderStatistic2 || orderStatistic2 > variateCount || orderStatistic1 == orderStatistic2)
		{
			throw new Exception ("IIDComposite::OrderStatisticsJointMoment => Invalid Inputs");
		}

		double expectationMaxIndex = 0.;
		double expectationMinIndex = 0.;
		double expectationMinIndexSquared = 0.;
		int maxIndex = orderStatistic1 > orderStatistic2 ? orderStatistic1 : orderStatistic2;
		int minIndex = orderStatistic1 < orderStatistic2 ? orderStatistic1 : orderStatistic2;

		double inverseRate = 1. / r1RateDistribution.rate();

		for (int k = 0; k < minIndex; ++k) {
			double expectation = inverseRate / (variateCount - k);
			expectationMinIndexSquared += expectation * expectation;
			expectationMinIndex += expectation;
		}

		for (int k = 0; k < maxIndex; ++k) {
			expectationMaxIndex += inverseRate / (variateCount - k);
		}

		return expectationMinIndex * expectationMaxIndex + expectationMinIndexSquared +
			expectationMinIndex * expectationMinIndex;
	}
}
