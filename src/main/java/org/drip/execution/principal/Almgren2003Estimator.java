
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
 * <i>Almgren2003Estimator</i> generates the Gross Profit Distribution and the Information Ratio for a given
 * Level of Principal Discount for an Optimal Trajectory that is generated using the Almgren (2003) Scheme.
 * The References are:
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

public class Almgren2003Estimator extends org.drip.execution.principal.GrossProfitEstimator {
	private org.drip.execution.dynamics.LinearPermanentExpectationParameters _lpep =  null;

	/**
	 * Almgren2003Estimator Constructor
	 * 
	 * @param pic The Power Continuous Market Impact Trajectory
	 * @param lpep The Linear Permanent Expectation Paremeter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Almgren2003Estimator (
		final org.drip.execution.optimum.PowerImpactContinuous pic,
		final org.drip.execution.dynamics.LinearPermanentExpectationParameters lpep)
		throws java.lang.Exception
	{
		super (pic);

		if (null == (_lpep = lpep))
			throw new java.lang.Exception ("Almgren2003Estimator Constructor => Invalid Inputs");
	}

	/**
	 * Generate the Horizon that results in the Optimal Information Ratio
	 * 
	 * @param dblD The Principal Discount "D"
	 * 
	 * @return The Horizon that results in the Optimal Information Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double optimalInformationRatioHorizon (
		final double dblD)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblD))
			throw new java.lang.Exception
				("Almgren2003Estimator::optimalInformationRatioHorizon => Invalid Inputs");

		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionPower)
				_lpep.temporaryExpectation().epochImpactFunction();

		double dblGamma = ((org.drip.execution.impact.TransactionFunctionLinear)
			_lpep.linearPermanentExpectation().epochImpactFunction()).slope();

		double dblEta = tfpTemporaryExpectation.constant();

		double dblK = tfpTemporaryExpectation.exponent();

		double dblX = efficientTrajectory().tradeSize();

		return dblX * java.lang.Math.pow (dblEta * (dblK + 1.) * (dblK + 1.) / (3. * dblK + 1.) / (dblD - 0.5
			* dblGamma * dblX), 1. / dblK);
	}

	/**
	 * Compute the Optimal Information Ratio
	 * 
	 * @param dblD The Principal Discount "D"
	 * 
	 * @return The Optimal Information Ratio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double optimalInformationRatio (
		final double dblD)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblD))
			throw new java.lang.Exception
				("Almgren2003Estimator::optimalInformationRatio => Invalid Inputs");

		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionPower)
				_lpep.temporaryExpectation().epochImpactFunction();

		double dblSigma = _lpep.arithmeticPriceDynamicsSettings().epochVolatility();

		double dblGamma = ((org.drip.execution.impact.TransactionFunctionLinear)
			_lpep.linearPermanentExpectation().epochImpactFunction()).slope();

		double dblEta = tfpTemporaryExpectation.constant();

		double dblK = tfpTemporaryExpectation.exponent();

		double dblX = efficientTrajectory().tradeSize();

		return java.lang.Math.pow (3. * dblK + 1.              , (1. * dblK + 2.) / (2. * dblK)) /
			   java.lang.Math.pow (1. * dblK + 1.              , (3. * dblK + 4.) / (2. * dblK)) *
			   java.lang.Math.pow (dblD - 0.5 * dblGamma * dblX, (1. * dblK + 1.) / (1. * dblK)) /
			   java.lang.Math.pow (dblEta                      , (0. * dblK + 1.) / (1. * dblK)) /
			   (dblX * dblSigma);
	}

	/**
	 * Compute the Principal Discount Hurdle given the Information Ratio
	 * 
	 * @param dblI The Optimal Information Ratio "I"
	 * 
	 * @return The Principal Discount Hurdle
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double principalDiscountHurdle (
		final double dblI)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblI))
			throw new java.lang.Exception
				("Almgren2003Estimator::principalDiscountHurdle => Invalid Inputs");

		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionPower)
				_lpep.temporaryExpectation().epochImpactFunction();

		double dblEta = tfpTemporaryExpectation.constant();

		double dblGamma = ((org.drip.execution.impact.TransactionFunctionLinear)
			_lpep.linearPermanentExpectation().epochImpactFunction()).slope();

		double dblSigma = _lpep.arithmeticPriceDynamicsSettings().epochVolatility();

		double dblK = tfpTemporaryExpectation.exponent();

		double dblX = efficientTrajectory().tradeSize();

		return java.lang.Math.pow (
			   0.5 * dblGamma * dblX +
			   java.lang.Math.pow (1. * dblK + 1.              , (3. * dblK + 4.) / (2. * dblK)) /
			   java.lang.Math.pow (3. * dblK + 1.              , (1. * dblK + 2.) / (2. * dblK)) *
			   java.lang.Math.pow (dblEta                      , (0. * dblK + 1.) / (1. * dblK)) *
			   (dblX * dblSigma * dblI),
			   dblK / (dblK + 1.)
		);
	}

	/**
	 * Generate the Constant/Exponent Dependencies on the Market Parameters for the Optimal Execution Horizon
	 * 	/ Information Ratio
	 *  
	 * @return The Optimal Execution Horizon/Information Ratio Dependency
	 */

	public org.drip.execution.principal.HorizonInformationRatioDependence optimalMeasures()
	{
		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionPower)
				_lpep.temporaryExpectation().epochImpactFunction();

		double dblK = tfpTemporaryExpectation.exponent();

		try {
			return new org.drip.execution.principal.HorizonInformationRatioDependence (
				new org.drip.execution.principal.OptimalMeasureDependence (
					java.lang.Math.pow ((dblK + 1.) * (dblK + 1.) / (3. * dblK + 1.), 1. / dblK),
					1. / dblK,
					1.,
					0.,
					-1. / dblK
				),
				new org.drip.execution.principal.OptimalMeasureDependence (
					java.lang.Math.pow (3. * dblK + 1., (1. * dblK + 2.) / (2. * dblK)) /
						java.lang.Math.pow (1. * dblK + 1., (3. * dblK + 4.) / (2. * dblK)),
					-1. / dblK,
					-1.,
					-1.,
					(dblK + 1.) / dblK
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
