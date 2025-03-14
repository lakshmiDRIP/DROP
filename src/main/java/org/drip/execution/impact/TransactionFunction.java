
package org.drip.execution.impact;

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
 * <i>TransactionFunction</i> exports the Temporary/Permanent Market Impact Displacement/Volatility
 * Functional Dependence on the Trade Rate. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/impact/README.md">Market Impact Transaction Function Implementation</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class TransactionFunction extends org.drip.function.definition.R1ToR1 {

	protected TransactionFunction()
	{
		super (null);
	}

	/**
	 * Regularize the Input Function using the specified Trade Inputs
	 * 
	 * @param dblTradeInterval The Trade Interval
	 * 
	 * @return The Regularize Input
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double regularize (
		final double dblTradeInterval)
		throws java.lang.Exception;

	/**
	 * Modulate/Scale the Impact Output
	 * 
	 * @param dblTradeInterval The Trade Interval
	 * 
	 * @return The Modulated Output
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double modulate (
		final double dblTradeInterval)
		throws java.lang.Exception;

	/**
	 * Evaluate the Impact Function at the specified Trade Parameters
	 * 
	 * @param dblTradeAmount The Trade Amount
	 * @param dblTradeInterval The Trade Interval
	 * 
	 * @return The Value of the Impact Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double evaluate (
		final double dblTradeAmount,
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		return modulate (dblTradeInterval) * evaluate (dblTradeAmount * regularize (dblTradeInterval));
	}

	/**
	 * Compute the Sensitivity to the Left Holdings
	 * 
	 * @param dblTradeAmount The Trade Amount
	 * @param dblTradeInterval The Trade Interval
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Sensitivity to the Left Holdings of the Impact Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double leftHoldingsDerivative (
		final double dblTradeAmount,
		final double dblTradeInterval,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblTradeAmount) || 0 >= iOrder)
			throw new java.lang.Exception ("TransactionFunction::leftHoldingsDerivative => Invalid Inputs");

		double dblRegularizedInput = regularize (dblTradeInterval);

		return modulate (dblTradeInterval) * java.lang.Math.pow (-1. * dblRegularizedInput, iOrder) *
			derivative (dblTradeAmount * dblRegularizedInput, iOrder);
	}

	/**
	 * Compute the Sensitivity to the Right Holdings
	 * 
	 * @param dblTradeAmount The Trade Amount
	 * @param dblTradeInterval The Trade Interval
	 * @param iOrder Order of the Derivative
	 * 
	 * @return The Sensitivity to the Right Holdings of the Impact Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double rightHoldingsDerivative (
		final double dblTradeAmount,
		final double dblTradeInterval,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblTradeAmount) || 0 >= iOrder)
			throw new java.lang.Exception ("TransactionFunction::rightHoldingsDerivative => Invalid Inputs");

		double dblRegularizedInput = regularize (dblTradeInterval);

		return modulate (dblTradeInterval) * java.lang.Math.pow (dblRegularizedInput, iOrder) * derivative
			(dblTradeAmount * dblRegularizedInput, iOrder);
	}

	/**
	 * Compute the Second Order Sensitivity to the Left/Right Holdings
	 * 
	 * @param dblTradeAmount The Trade Amount
	 * @param dblTradeInterval The Trade Interval
	 * 
	 * @return The Second Order Sensitivity to the Left/Right Holdings of the Impact Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double crossHoldingsDerivative (
		final double dblTradeAmount,
		final double dblTradeInterval)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblTradeAmount))
			throw new java.lang.Exception ("TransactionFunction::crossHoldingsDerivative => Invalid Inputs");

		double dblRegularizedInput = regularize (dblTradeInterval);

		return -1. * modulate (dblTradeInterval) * dblRegularizedInput * dblRegularizedInput * derivative
			(dblTradeAmount * dblRegularizedInput, 2);
	}
}
