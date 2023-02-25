
package org.drip.execution.strategy;

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
 * <i>DiscreteTradingTrajectory</i> holds the Trajectory of a Trading Block that is to be executed over a
 * Discrete Time Set. The References are:
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
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/README.md">Discrete/Continuous Trading Trajectory Schedule</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DiscreteTradingTrajectory implements org.drip.execution.strategy.TradingTrajectory {
	private double[] _adblHoldings = null;
	private double[] _adblTradeList = null;
	private double[] _adblExecutionTimeNode = null;

	/**
	 * Construct a Standard DiscreteTradingTrajectory Instance
	 * 
	 * @param adblExecutionTimeNode Array containing the Trajectory Time Nodes
	 * @param adblHoldings Array containing the Holdings
	 * 
	 * @return The DiscreteTradingTrajectory Instance
	 */

	public static DiscreteTradingTrajectory Standard (
		final double[] adblExecutionTimeNode,
		final double[] adblHoldings)
	{
		if (null == adblHoldings) return null;

		int iNumExecutionTime = adblHoldings.length;
		double[] adblTradeList = 1 >= iNumExecutionTime ? null : new double[iNumExecutionTime - 1];

		if (1 >= iNumExecutionTime) return null;

		for (int i = 0; i < iNumExecutionTime - 1; ++i)
			adblTradeList[i] = adblHoldings[i + 1] - adblHoldings[i];

		try {
			return new DiscreteTradingTrajectory (adblExecutionTimeNode, adblHoldings, adblTradeList);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Linear DiscreteTradingTrajectory Instance
	 * 
	 * @param adblExecutionTimeNode Array of the Execution Time Nodes
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param dblFinishHoldings Trajectory Finish Holdings
	 * 
	 * @return The Linear TradingTrajectory Instance
	 */

	public static final DiscreteTradingTrajectory Linear (
		final double[] adblExecutionTimeNode,
		final double dblStartHoldings,
		final double dblFinishHoldings)
	{
		if (null == adblExecutionTimeNode || !org.drip.numerical.common.NumberUtil.IsValid
			(adblExecutionTimeNode) || !org.drip.numerical.common.NumberUtil.IsValid (dblStartHoldings) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblFinishHoldings))
			return null;

		int iNumNode = adblExecutionTimeNode.length;
		double[] adblHoldings = 1 >= iNumNode ? null : new double[iNumNode];
		double dblHoldingsChangeRate = (dblFinishHoldings - dblStartHoldings) /
			(adblExecutionTimeNode[iNumNode - 1] - adblExecutionTimeNode[0]);

		if (1 >= iNumNode) return null;

		for (int i = 0; i < iNumNode; ++i)
			adblHoldings[i] = dblStartHoldings + dblHoldingsChangeRate * i;

		return DiscreteTradingTrajectory.Standard (adblExecutionTimeNode, adblHoldings);
	}

	/**
	 * DiscreteTradingTrajectory Constructor
	 * 
	 * @param adblExecutionTimeNode Array containing the Trajectory Time Nodes
	 * @param adblHoldings Array containing the Holdings
	 * @param adblTradeList Array containing the Trade List
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiscreteTradingTrajectory (
		final double[] adblExecutionTimeNode,
		final double[] adblHoldings,
		final double[] adblTradeList)
		throws java.lang.Exception
	{
		if (null == (_adblHoldings = adblHoldings) || null == (_adblTradeList = adblTradeList) || null ==
			(_adblExecutionTimeNode = adblExecutionTimeNode))
			throw new java.lang.Exception ("DiscreteTradingTrajectory Constructor => Invalid Inputs!");

		int iNumExecutionTime = _adblExecutionTimeNode.length;

		if (1 >= iNumExecutionTime || iNumExecutionTime != _adblHoldings.length || iNumExecutionTime - 1 !=
			_adblTradeList.length || !org.drip.numerical.common.NumberUtil.IsValid (_adblHoldings[0]) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_adblExecutionTimeNode[0]))
			throw new java.lang.Exception ("DiscreteTradingTrajectory Constructor => Invalid Inputs!");

		for (int i = 1; i < iNumExecutionTime; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_adblHoldings[i]) ||
				!org.drip.numerical.common.NumberUtil.IsValid (_adblTradeList[i - 1]) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_adblExecutionTimeNode[i]) ||
						_adblExecutionTimeNode[i - 1] >= _adblExecutionTimeNode[i])
				throw new java.lang.Exception ("DiscreteTradingTrajectory Constructor => Invalid Inputs!");
		}
	}

	@Override public double tradeSize()
	{
		return _adblHoldings[0];
	}

	@Override public double executedBlockSize()
	{
		return _adblHoldings[_adblHoldings.length - 1] - _adblHoldings[0];
	}

	@Override public double executionTime()
	{
		return _adblExecutionTimeNode[_adblExecutionTimeNode.length - 1] - _adblExecutionTimeNode[0];
	}

	@Override public double instantTradeRate()
	{
		return executedBlockSize() / executionTime();
	}

	/**
	 * Retrieve the Array containing the Execution Time Nodes Sequence
	 * 
	 * @return The Array containing the Execution Time Nodes Sequence
	 */

	public double[] executionTimeNode()
	{
		return _adblExecutionTimeNode;
	}

	/**
	 * Retrieve the Array of the Number of Units Outstanding
	 * 
	 * @return The Array of the Number of Units Outstanding
	 */

	public double[] holdings()
	{
		return _adblHoldings;
	}

	/**
	 * Retrieve the Trade List, i.e., the Array of the Number of Units executed
	 * 
	 * @return The Trade List, i.e., the Array of the Number of Units executed
	 */

	public double[] tradeList()
	{
		return _adblTradeList;
	}

	/**
	 * Retrieve the Number of Trades
	 * 
	 * @return The Number of Trades
	 */

	public int numberOfTrades()
	{
		return _adblHoldings.length - 1;
	}

	/**
	 * Retrieve the Array of the Inner Holdings
	 * 
	 * @return The Array of the Inner Holdings
	 */

	public double[] innerHoldings()
	{
		int iNumInnerHoldings = _adblHoldings.length - 2;
		double[] adblInnerHoldings = new double[iNumInnerHoldings];

		for (int i = 0; i < iNumInnerHoldings; ++i)
			adblInnerHoldings[i] = _adblHoldings[i + 1];

		return adblInnerHoldings;
	}
}
