
package org.drip.execution.capture;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>LinearImpactTrajectoryEstimator</i> estimates the Price/Cost Distribution associated with the Trading
 * Trajectory generated using the Linear Market Impact Evolution Parameters. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/capture">Capture</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LinearImpactTrajectoryEstimator extends org.drip.execution.capture.TrajectoryShortfallEstimator
{

	/**
	 * LinearImpactTrajectoryEstimator Constructor
	 * 
	 * @param tt The Trading Trajectory Instance
	 *  
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LinearImpactTrajectoryEstimator (
		final org.drip.execution.strategy.DiscreteTradingTrajectory tt)
		throws java.lang.Exception
	{
		super (tt);
	}

	@Override public org.drip.measure.gaussian.R1UnivariateNormal totalCostDistributionSynopsis (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep)
	{
		if (null == apep) return null;

		double dblMarketCoreVolatility = java.lang.Double.NaN;
		org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep =
			(org.drip.execution.dynamics.LinearPermanentExpectationParameters) apep;

		double dblPermanentLinearImpactParameter = ((org.drip.execution.impact.TransactionFunctionLinear)
			lpep.linearPermanentExpectation().epochImpactFunction()).slope();

		org.drip.execution.impact.TransactionFunctionLinear tflTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionLinear)
				apep.temporaryExpectation().epochImpactFunction();

		double dblTemporaryLinearImpactParameter = tflTemporaryExpectation.slope();

		double dblTemporaryOffsetImpactParameter = tflTemporaryExpectation.offset();

		org.drip.execution.strategy.DiscreteTradingTrajectory tt = trajectory();

		double[] adblExecutionTimeNode = tt.executionTimeNode();

		org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds =
			lpep.arithmeticPriceDynamicsSettings();

		try {
			dblMarketCoreVolatility = apds.epochVolatility();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblMarketCoreDrift = apds.drift();

		double[] adblHoldings = tt.holdings();

		int iNumTimeNode = adblHoldings.length;
		double dblUnscaledTotalCostVariance = 0.;
		double dblTotalCostMean = 0.5 * dblPermanentLinearImpactParameter * adblHoldings[0] *
			adblHoldings[0];

		for (int i = 1; i < iNumTimeNode; ++i) {
			double dblHoldingsIncrement = adblHoldings[i] - adblHoldings[i - 1];
			double dblTimeInterval = adblExecutionTimeNode[i] - adblExecutionTimeNode[i - 1];
			dblUnscaledTotalCostVariance = dblUnscaledTotalCostVariance + dblTimeInterval * adblHoldings[i] *
				adblHoldings[i];

			dblTotalCostMean = dblTotalCostMean + dblTemporaryOffsetImpactParameter * java.lang.Math.abs
				(dblHoldingsIncrement) + (dblTemporaryLinearImpactParameter - 0.5 *
					dblPermanentLinearImpactParameter * dblTimeInterval) / dblTimeInterval *
						dblHoldingsIncrement * dblHoldingsIncrement - dblMarketCoreDrift * dblTimeInterval *
							adblHoldings[i];
		}

		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal (dblTotalCostMean,
				dblMarketCoreVolatility * java.lang.Math.sqrt (dblUnscaledTotalCostVariance));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
