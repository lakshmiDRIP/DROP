
package org.drip.execution.principal;

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
 * <i>GrossProfitEstimator</i> generates the Gross Profit Distribution and the Information Ratio for a given
 * Level of Principal Discount. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 	<li>
 * 		Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 * 			<b>3 (2)</b> 5-39
 * 	</li>
 * 	<li>
 * 		Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 			<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 * 	</li>
 * 	<li>
 * 		Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b>
 * 			57-62
 * 	</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/principal/README.md">Information Ratio Based Principal Trades</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GrossProfitEstimator {
	private org.drip.execution.optimum.EfficientTradingTrajectory _ett = null;

	/**
	 * GrossProfitEstimator Constructor
	 * 
	 * @param ett The efficient Trading Trajectory Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GrossProfitEstimator (
		final org.drip.execution.optimum.EfficientTradingTrajectory ett)
		throws java.lang.Exception
	{
		if (null == (_ett = ett))
			throw new java.lang.Exception ("GrossProfitEstimator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Optimal Efficient Trajectory
	 * 
	 * @return The Optimal "Efficient" Trajectory
	 */

	public org.drip.execution.optimum.EfficientTradingTrajectory efficientTrajectory()
	{
		return _ett;
	}

	/**
	 * Generate R^1 Univariate Normal Gross Profit Distribution from the specified Principal Discount
	 * 
	 * @param dblPrincipalDiscount The Principal Discount
	 * 
	 * @return The R^1 Univariate Normal Gross Profit Distribution from the specified Principal Discount
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal principalMeasure (
		final double dblPrincipalDiscount)
	{
		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal (dblPrincipalDiscount * _ett.tradeSize()
				- _ett.transactionCostExpectation(), java.lang.Math.sqrt (_ett.transactionCostVariance()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Break-even Principal Discount
	 * 
	 * @return The Break-even Principal Discount
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double breakevenPrincipalDiscount()
		throws java.lang.Exception
	{
		return _ett.transactionCostExpectation() / _ett.tradeSize();
	}

	/**
	 * Generate R^1 Univariate Normal Gross Profit Distribution from the specified Principal Discount
	 * 
	 * @param dblPrincipalDiscount The Principal Discount
	 * 
	 * @return The R^1 Univariate Normal Gross Profit Distribution from the specified Principal Discount
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal horizonPrincipalMeasure (
		final double dblPrincipalDiscount)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPrincipalDiscount)) return null;

		double dblInverseHorizon = 1. / _ett.executionTime();

		try {
			return new org.drip.measure.gaussian.R1UnivariateNormal (dblPrincipalDiscount * _ett.tradeSize()
				- _ett.transactionCostExpectation() * dblInverseHorizon, java.lang.Math.sqrt
					(_ett.transactionCostVariance() * dblInverseHorizon));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Information Ratio given the Principal Discount
	 * 
	 * @param dblPrincipalDiscount The Principal Discount
	 * 
	 * @return The Information Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs cannot be calculated
	 */

	public double informationRatio (
		final double dblPrincipalDiscount)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblPrincipalDiscount))
			throw new java.lang.Exception ("GrossProfitEstimator::informationRatio => Invalid Inputs");

		double dblInverseHorizon = 1. / _ett.executionTime();

		return dblInverseHorizon * (dblPrincipalDiscount * _ett.tradeSize() -
			_ett.transactionCostExpectation()) / java.lang.Math.sqrt (dblInverseHorizon *
				_ett.transactionCostVariance());
	}
}
