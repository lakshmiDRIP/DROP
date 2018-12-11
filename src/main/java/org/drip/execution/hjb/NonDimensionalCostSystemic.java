
package org.drip.execution.hjb;

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
 * <i>NonDimensionalCostSystemic</i> contains the Level, the Gradient, and the Jacobian of the HJB Non
 * Dimensional Cost Value Function to the Systemic Market State. The References are:
 * 
 * <br><br>
 *  <ul>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 			of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 		<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 * 		</li>
 * 		<li>
 * 			Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 * 				Financial Studies</i> <b>7 (4)</b> 631-651
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/hjb">Hamilton Jacobin Bellman Based Optimal Evolution</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NonDimensionalCostSystemic extends org.drip.execution.hjb.NonDimensionalCost {
	private double _dblGradient = java.lang.Double.NaN;
	private double _dblJacobian = java.lang.Double.NaN;

	/**
	 * Generate a Zero Sensitivity Systemic Non Dimensional Cost Instance
	 * 
	 * @return The Zero Sensitivity Systemic Non Dimensional Cost Instance
	 */

	public static final NonDimensionalCostSystemic Zero()
	{
		try {
			return new NonDimensionalCostSystemic (0., 0., 0., 0.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Linear Trading Systemic Non Dimensional Cost Instance
	 * 
	 * @param dblMarketStateExponentiation The Exponentiated Market State
	 * @param dblNonDimensionalTime The Non Dimensional Time
	 * 
	 * @return The Linear Trading Systemic Non Dimensional Cost Instance
	 */

	public static final NonDimensionalCostSystemic LinearThreshold (
		final double dblMarketStateExponentiation,
		final double dblNonDimensionalTime)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblMarketStateExponentiation) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalTime) || 0. >= dblNonDimensionalTime)
			return null;

		double dblNonDimensionalUrgency = 1. / dblNonDimensionalTime;
		double dblNonDimensionalCostThreshold = dblMarketStateExponentiation * dblNonDimensionalUrgency;

		try {
			return new NonDimensionalCostSystemic (dblNonDimensionalCostThreshold,
				dblNonDimensionalCostThreshold, dblNonDimensionalCostThreshold, dblNonDimensionalUrgency);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Euler Enhanced Linear Trading Systemic Non Dimensional Cost Instance
	 * 
	 * @param dblMarketState The Market State
	 * @param dblNonDimensionalCost The Non Dimensional Cost
	 * @param dblNonDimensionalCostCross The Non Dimensional Cost Cross Term
	 * 
	 * @return The Euler Enhanced Linear Trading Systemic Non Dimensional Cost Instance
	 */

	public static final NonDimensionalCostSystemic EulerEnhancedLinearThreshold (
		final double dblMarketState,
		final double dblNonDimensionalCost,
		final double dblNonDimensionalCostCross)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblMarketState) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalCost) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblNonDimensionalCostCross))
			return null;

		try {
			return new NonDimensionalCostSystemic (dblNonDimensionalCost, dblNonDimensionalCost +
				dblNonDimensionalCostCross, dblNonDimensionalCost + 2. * dblNonDimensionalCostCross,
					java.lang.Math.exp (-dblMarketState) * dblNonDimensionalCost);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * NonDimensionalCostSystemic Constructor
	 * 
	 * @param dblRealization The Non Dimensional Cost Value Function Realization
	 * @param dblGradient The Non Dimensional Cost Value Function Gradient to the Systemic Market State
	 * @param dblJacobian The Non Dimensional Cost Value Function Jacobian to the Systemic Market State
	 * @param dblNonDimensionalTradeRate The Non-dimensional Trade Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NonDimensionalCostSystemic (
		final double dblRealization,
		final double dblGradient,
		final double dblJacobian,
		final double dblNonDimensionalTradeRate)
		throws java.lang.Exception
	{
		super (dblRealization, dblNonDimensionalTradeRate);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblGradient = dblGradient) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblJacobian = dblJacobian))
			throw new java.lang.Exception ("NonDimensionalCostSystemic Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Realized Non Dimensional Cost Value Function Gradient to the Systemic Market State
	 * 
	 * @return The Realized Non Dimensional Cost Value Function Gradient to the Systemic Market State
	 */

	public double gradient()
	{
		return _dblGradient;
	}

	/**
	 * Retrieve the Realized Non Dimensional Cost Value Function Jacobian to the Systemic Market State
	 * 
	 * @return The Realized Non Dimensional Cost Value Function Jacobian to the Systemic Market State
	 */

	public double jacobian()
	{
		return _dblJacobian;
	}
}
