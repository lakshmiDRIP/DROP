
package org.drip.execution.strategy;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * MinimumImpactTradingTrajectory holds the Trajectory of a Trading Block that is to be executed uniformly
 *  over Equal Intervals, the Idea being to minimize the Trading Impact. The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 *
 * 	- Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades,
 * 		Journal of Finance, 50, 1147-1174.
 *
 * 	- Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 		Analysis of Institutional Equity Trades, Journal of Financial Economics, 46, 265-292.
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
