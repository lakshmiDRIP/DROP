
package org.drip.execution.capture;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>LinearImpactUniformTrajectoryEstimator</i> estimates the Price/Cost Distribution associated with the
 * Uniform Trading Trajectory generated using the Linear Market Impact Evolution Parameters. The References
 * are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 * 		</li>
 * 		<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture/README.md">Execution Trajectory Transaction Cost Capture</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LinearImpactUniformTrajectoryEstimator extends
	org.drip.execution.capture.TrajectoryShortfallEstimator {

	/**
	 * LinearImpactUniformTrajectoryEstimator Constructor
	 * 
	 * @param mittUniform The Uniform (i.e., Minimum Impact) Trading Trajectory
	 *  
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LinearImpactUniformTrajectoryEstimator (
		final org.drip.execution.strategy.MinimumImpactTradingTrajectory mittUniform)
		throws java.lang.Exception
	{
		super (mittUniform);
	}

	@Override public org.drip.measure.gaussian.R1UnivariateNormal totalCostDistributionSynopsis (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == apep) return null;

		org.drip.execution.strategy.MinimumImpactTradingTrajectory mitt =
			(org.drip.execution.strategy.MinimumImpactTradingTrajectory) trajectory();

		double dblBlockSize = mitt.tradeSize();

		double dblExecutionTime = mitt.executionTime();

		double dblNumberOfTradesReciprocal = 1. / mitt.numberOfTrades();

		double dblMarketCoreVolatility = java.lang.Double.NaN;
		double dblBlockSizeSquared = dblBlockSize * dblBlockSize;
		org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep =
			(org.drip.execution.dynamics.LinearPermanentExpectationParameters) apep;

		double dblPermanentLinearImpactParameter = ((org.drip.execution.impact.TransactionFunctionLinear)
			lpep.linearPermanentExpectation().epochImpactFunction()).slope();

		org.drip.execution.impact.TransactionFunctionLinear tflTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionLinear)
				apep.temporaryExpectation().epochImpactFunction();

		try {
			dblMarketCoreVolatility = apep.arithmeticPriceDynamicsSettings().epochVolatility();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal (0.5 * dblPermanentLinearImpactParameter
				* dblBlockSizeSquared + tflTemporaryExpectation.offset() * java.lang.Math.abs (dblBlockSize)
					+ (tflTemporaryExpectation.slope() - 0.5 * dblPermanentLinearImpactParameter *
						mitt.tradeTimeInterval()) * dblBlockSizeSquared / dblExecutionTime,
							dblMarketCoreVolatility * dblMarketCoreVolatility * dblBlockSizeSquared *
								dblExecutionTime * (1. - dblNumberOfTradesReciprocal) * (1. - 0.5 *
									dblNumberOfTradesReciprocal) / 3.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
