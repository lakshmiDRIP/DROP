
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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>ContinuousTradingTrajectory</i> holds the Continuous Trajectory of a Trading Block that is to be
 * executed over the Specified Horizon. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/strategy/README.md">Strategy</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ContinuousTradingTrajectory implements org.drip.execution.strategy.TradingTrajectory {
	private double _dblExecutionTime = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _r1ToR1Holdings = null;
	private org.drip.function.definition.R1ToR1 _r1ToR1TradeRate = null;
	private org.drip.function.definition.R1ToR1 _r1ToR1TransactionCostVariance = null;
	private org.drip.function.definition.R1ToR1 _r1ToR1TransactionCostExpectation = null;

	/**
	 * Construct a Standard Instance of ContinuousTradingTrajectory
	 * 
	 * @param dblExecutionTime The Execution Time
	 * @param r1ToR1Holdings The Holdings Function
	 * @param r1ToR1TransactionCostExpectation The Transaction Cost Expectation Function
	 * @param r1ToR1TransactionCostVariance The Transaction Cost Variance Function
	 * 
	 * @return Standard Instance of ContinuousTradingTrajectory
	 */

	public static final ContinuousTradingTrajectory Standard (
		final double dblExecutionTime,
		final org.drip.function.definition.R1ToR1 r1ToR1Holdings,
		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectation,
		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVariance)
	{
		if (null == r1ToR1Holdings) return null;

		org.drip.function.definition.R1ToR1 r1ToR1TradeRate = new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblVariate)
				throws java.lang.Exception
			{
				return r1ToR1Holdings.derivative (dblVariate, 1);
			}
		};

		try {
			return new ContinuousTradingTrajectory (dblExecutionTime, r1ToR1Holdings, r1ToR1TradeRate,
				r1ToR1TransactionCostExpectation, r1ToR1TransactionCostVariance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ContinuousTradingTrajectory Constructor
	 * 
	 * @param dblExecutionTime The Execution Time
	 * @param r1ToR1Holdings The Holdings Function
	 * @param r1ToR1TradeRate The Trade Rate Function
	 * @param r1ToR1TransactionCostExpectation The Transaction Cost Expectation Function
	 * @param r1ToR1TransactionCostVariance The Transaction Cost Variance Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ContinuousTradingTrajectory (
		final double dblExecutionTime,
		final org.drip.function.definition.R1ToR1 r1ToR1Holdings,
		final org.drip.function.definition.R1ToR1 r1ToR1TradeRate,
		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectation,
		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVariance)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblExecutionTime = dblExecutionTime) || 0. >=
			_dblExecutionTime || null == (_r1ToR1Holdings = r1ToR1Holdings) || null == (_r1ToR1TradeRate =
				r1ToR1TradeRate) || null == (_r1ToR1TransactionCostExpectation =
					r1ToR1TransactionCostExpectation) || null == (_r1ToR1TransactionCostVariance =
						r1ToR1TransactionCostVariance))
			throw new java.lang.Exception ("ContinuousTradingTrajectory Constructor => Invalid Inputs");
	}

	@Override public double tradeSize()
	{
		try {
			return _r1ToR1Holdings.evaluate (0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return 0.;
	}

	@Override public double executedBlockSize()
	{
		try {
			return _r1ToR1Holdings.evaluate (0.) - _r1ToR1Holdings.evaluate (_dblExecutionTime);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return 0.;
	}

	@Override public double executionTime()
	{
		return _dblExecutionTime;
	}

	@Override public double instantTradeRate()
	{
		return executedBlockSize() / executionTime();
	}

	/**
	 * Retrieve the Holdings Function
	 * 
	 * @return The Holdings Function
	 */

	public org.drip.function.definition.R1ToR1 holdings()
	{
		return _r1ToR1Holdings;
	}

	/**
	 * Retrieve the Trade Rate Function
	 * 
	 * @return The Trade Rate Function
	 */

	public org.drip.function.definition.R1ToR1 tradeRate()
	{
		return _r1ToR1TradeRate;
	}

	/**
	 * Retrieve the Transaction Cost Expectation Function
	 * 
	 * @return The Transaction Cost Expectation Function
	 */

	public org.drip.function.definition.R1ToR1 transactionCostExpectationFunction()
	{
		return _r1ToR1TransactionCostExpectation;
	}

	/**
	 * Retrieve the Transaction Cost Variance Function
	 * 
	 * @return The Transaction Cost Variance Function
	 */

	public org.drip.function.definition.R1ToR1 transactionCostVarianceFunction()
	{
		return _r1ToR1TransactionCostVariance;
	}
}
