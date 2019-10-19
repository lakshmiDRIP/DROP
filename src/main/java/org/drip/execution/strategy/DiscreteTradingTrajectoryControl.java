
package org.drip.execution.strategy;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>DiscreteTradingTrajectoryControl</i> holds the Time Trajectory Control Settings of a Trading Block that
 * is to be executed over a Discrete Time Sequence. The References are:
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

public class DiscreteTradingTrajectoryControl {
	private double[] _adblExecutionTimeNode = null;
	private double _dblStartHoldings = java.lang.Double.NaN;

	/**
	 * Create a DiscreteTradingTrajectoryControl from Fixed Intervals
	 * 
	 * @param os The Order Specification
	 * @param iNumInterval The Number of Fixed Intervals
	 * 
	 * @return The DiscreteTradingTrajectoryControl from Fixed Intervals
	 */

	public static final DiscreteTradingTrajectoryControl FixedInterval (
		final org.drip.execution.strategy.OrderSpecification os,
		final int iNumInterval)
	{
		if (null == os || 0 >= iNumInterval) return null;

		double dblFinishTime = os.maxExecutionTime();

		double dblTimeInterval = dblFinishTime / iNumInterval;
		double[] adblExecutionTimeNode = new double[iNumInterval + 1];
		adblExecutionTimeNode[iNumInterval] = dblFinishTime;
		adblExecutionTimeNode[0] = 0.;

		for (int i = 1; i < iNumInterval; ++i)
			adblExecutionTimeNode[i] = i * dblTimeInterval;

		try {
			return new DiscreteTradingTrajectoryControl (os.size(), adblExecutionTimeNode);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Single Interval DiscreteTradingTrajectoryControl Instance from the Order Specification
	 * 
	 * @param os The Order Specification
	 * 
	 * @return The Single Interval DiscreteTradingTrajectoryControl Instance from the Order Specification
	 */

	public static final DiscreteTradingTrajectoryControl SingleInterval (
		final org.drip.execution.strategy.OrderSpecification os)
	{
		return FixedInterval (os, 1);
	}

	/**
	 * DiscreteTradingTrajectoryControl Constructor
	 * 
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param adblExecutionTimeNode Array of the Trajectory Time Snapshots
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DiscreteTradingTrajectoryControl (
		final double dblStartHoldings,
		final double[] adblExecutionTimeNode)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblStartHoldings = dblStartHoldings) || null ==
			(_adblExecutionTimeNode = adblExecutionTimeNode))
			throw new java.lang.Exception
				("DiscreteTradingTrajectoryControl Constructor => Invalid Inputs!");

		int iNumExecutionTimeNode = _adblExecutionTimeNode.length;

		if (1 >= iNumExecutionTimeNode || !org.drip.numerical.common.NumberUtil.IsValid
			(_adblExecutionTimeNode[0]))
			throw new java.lang.Exception
				("DiscreteTradingTrajectoryControl Constructor => Invalid Inputs!");

		for (int i = 1; i < iNumExecutionTimeNode; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (_adblExecutionTimeNode[i]) ||
				_adblExecutionTimeNode[i - 1] >= _adblExecutionTimeNode[i])
				throw new java.lang.Exception
					("DiscreteTradingTrajectoryControl Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Finish Time of the Trading Trajectory
	 * 
	 * @return The Finish Time of the Trading Trajectory
	 */

	public double finishTime()
	{
		return _adblExecutionTimeNode[_adblExecutionTimeNode.length - 1];
	}

	/**
	 * Retrieve the Array containing the Execution Time Nodes
	 * 
	 * @return The Array containing the Execution Time Nodes
	 */

	public double[] executionTimeNodes()
	{
		return _adblExecutionTimeNode;
	}

	/**
	 * Retrieve the Initial Holdings, i.e., the Starting Number of Units to the Executed
	 * 
	 * @return The Initial Holdings, i.e., the Starting Number of Units to the Executed
	 */

	public double startHoldings()
	{
		return _dblStartHoldings;
	}

	/**
	 * Generate the Order Specification corresponding to the Trajectory Control
	 * 
	 * @return The Order Specificaton Instance
	 */

	public org.drip.execution.strategy.OrderSpecification order()
	{
		try {
			return new org.drip.execution.strategy.OrderSpecification (_dblStartHoldings, finishTime());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
