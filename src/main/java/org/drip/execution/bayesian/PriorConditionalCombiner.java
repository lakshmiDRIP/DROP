
package org.drip.execution.bayesian;

import org.drip.measure.gaussian.R1UnivariateNormal;
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
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>PriorConditionalCombiner</i> holds the Distributions associated with the Prior Drift and the
 * 	Conditional Price Distributions. It uses them to generate the resulting Joint, Posterior, and MAP Implied
 * 	Posterior Distributions. It provides the following Functions:
 * 	<ul>
 * 		<li><i>PriorConditionalCombiner</i> Constructor</li>
 * 		<li>Retrieve the Prior Drift Distribution Instance</li>
 * 		<li>Retrieve the Conditional Price Distribution Instance</li>
 * 		<li>Generate the Joint Price Distribution</li>
 * 		<li>Generate the Posterior Drift Distribution</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 * 	<ul>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading <i>Journal of Finance</i> <b>60
 * 				(4)</b> 1825-1863
 * 		</li>
 * 		<li>
 * 			Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle <i>Journal of
 * 				Trading</i> <b>1 (4)</b> 38-46
 * 		</li>
 * 		<li>
 * 			Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework <i>Journal of
 * 				Trading</i> <b>1 (1)</b> 12-21
 * 		</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/bayesian/README.md">Bayesian Price Based Optimal Execution</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PriorConditionalCombiner
{
	private PriorDriftDistribution _driftDistribution = null;
	private ConditionalPriceDistribution _priceDistribution = null;

	/**
	 * <i>PriorConditionalCombiner</i> Constructor
	 * 
	 * @param driftDistribution The Prior Drift Distribution Instance
	 * @param priceDistribution The Conditional Price Distribution Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PriorConditionalCombiner (
		final PriorDriftDistribution driftDistribution,
		final ConditionalPriceDistribution priceDistribution)
		throws Exception
	{
		if (null == (_driftDistribution = driftDistribution) ||
			null == (_priceDistribution = priceDistribution))
		{
			throw new Exception ("PriorConditionalCombiner Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Prior Drift Distribution Instance
	 * 
	 * @return The Prior Drift Distribution Instance
	 */

	public PriorDriftDistribution priorDriftDistribution()
	{
		return _driftDistribution;
	}

	/**
	 * Retrieve the Conditional Price Distribution Instance
	 * 
	 * @return The Conditional Price Distribution Instance
	 */

	public ConditionalPriceDistribution conditionalPriceDistribution()
	{
		return _priceDistribution;
	}

	/**
	 * Generate the Joint Price Distribution
	 * 
	 * @return The Joint Price Distribution
	 */

	public R1UnivariateNormal jointPriceDistribution()
	{
		double time = _priceDistribution.time();

		try {
			return new R1UnivariateNormal (
				_driftDistribution.expectation() * time,
				_driftDistribution.variance() * time * time +_priceDistribution.variance()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Posterior Drift Distribution
	 * 
	 * @param deltaS The Price Change (Final - Initial)
	 * 
	 * @return The Posterior Drift Distribution
	 */

	public R1UnivariateNormal posteriorDriftDistribution (
		final double deltaS)
	{
		if (!NumberUtil.IsValid (deltaS)) {
			return null;
		}

		double t = _priceDistribution.time();

		double nuSquared = _driftDistribution.variance();

		double sigmaSquared = _priceDistribution.variance() / t;

		double precisionSquared = 1. / (sigmaSquared + nuSquared * t);

		try {
			return new R1UnivariateNormal (
				(_driftDistribution.expectation() * sigmaSquared + nuSquared * deltaS) * precisionSquared,
				sigmaSquared * nuSquared * precisionSquared
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
