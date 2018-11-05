
package org.drip.execution.strategy;

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
 * <i>MinimumImpactTradingTrajectory</i> holds the Trajectory of a Trading Block that is to be executed
 * uniformly over Equal Intervals, the Idea being to minimize the Trading Impact. The References are:
 * 
 * <br>
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
 * <br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy">Strategy</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/TransactionCost">Transaction Cost Analytics</a></li>
 *  </ul>
 *  
 * @author Lakshmi Krishnamurthy
 */

public class MinimumImpactTradingTrajectory extends org.drip.execution.strategy.DiscreteTradingTrajectory {

	/**
	 * Create a MinimumImpactTradingTrajectory Instance from Equal Intervals
	 * 
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param dblFinishHoldings Trajectory Finish Holdings
	 * @param dblStartTime Trajectory Start Time
	 * @param dblFinishTime Trajectory Finish Time
	 * @param iNumInterval The Number of Fixed Intervals
	 * 
	 * @return The MinimumImpactTradingTrajectory Instance from Fixed Intervals
	 */

	public static final MinimumImpactTradingTrajectory Standard (
		final double dblStartHoldings,
		final double dblFinishHoldings,
		final double dblStartTime,
		final double dblFinishTime,
		final int iNumInterval)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStartHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFinishHoldings) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblStartTime) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblFinishTime) || dblStartTime >=
						dblFinishTime || 0 >= iNumInterval)
			return null;

		double dblTradeList = (dblFinishHoldings - dblStartHoldings) / iNumInterval;
		double dblTimeInterval = (dblFinishTime - dblStartTime) / iNumInterval;
		double[] adblExecutionTimeNode = new double[iNumInterval + 1];
		double[] adblHoldings = new double[iNumInterval + 1];
		adblExecutionTimeNode[iNumInterval] = dblFinishTime;
		double[] adblTradeList = new double[iNumInterval];
		adblHoldings[iNumInterval] = dblFinishHoldings;
		adblExecutionTimeNode[0] = dblStartTime;
		adblHoldings[0] = dblStartHoldings;
		adblTradeList[0] = dblTradeList;

		for (int i = 1; i < iNumInterval; ++i) {
			adblTradeList[i] = dblTradeList;
			adblHoldings[i] = dblStartHoldings + i * dblTradeList;
			adblExecutionTimeNode[i] = dblStartTime + i * dblTimeInterval;
		}

		try {
			return new MinimumImpactTradingTrajectory (adblExecutionTimeNode, adblHoldings, adblTradeList);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected MinimumImpactTradingTrajectory (
		final double[] adblExecutionTimeNode,
		final double[] adblHoldings,
		final double[] adblTradeList)
		throws java.lang.Exception
	{
		super (adblExecutionTimeNode, adblHoldings, adblTradeList);
	}

	/**
	 * Retrieve the Trade Size
	 * 
	 * @return The Trade Size
	 */

	public double tradeSize()
	{
		double[] adblHoldings = holdings();

		return adblHoldings[1] - adblHoldings[0];
	}

	/**
	 * Retrieve the Trade Time Interval
	 * 
	 * @return The Trade Time Interval
	 */

	public double tradeTimeInterval()
	{
		double[] adblExecutionTimeNode = executionTimeNode();

		return adblExecutionTimeNode[1] - adblExecutionTimeNode[0];
	}

	/**
	 * Retrieve the Trade Rate
	 * 
	 * @return The Trade Rate
	 */

	public double tradeRate()
	{
		double[] adblHoldings = holdings();

		double[] adblExecutionTimeNode = executionTimeNode();

		return (adblHoldings[1] - adblHoldings[0]) / (adblExecutionTimeNode[1] - adblExecutionTimeNode[0]);
	}
}
