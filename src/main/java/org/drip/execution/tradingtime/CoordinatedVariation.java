
package org.drip.execution.tradingtime;

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
 * <i>CoordinatedVariation</i> implements the Coordinated Variation of the Volatility and Liquidity as
 * described in the "Trading Time" Model. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/tradingtime/README.md">Coordinated Variation Trading Time Models</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CoordinatedVariation {
	private double _dblInvariant = java.lang.Double.NaN;
	private double _dblReferenceLiquidity = java.lang.Double.NaN;
	private double _dblReferenceVolatility = java.lang.Double.NaN;

	/**
	 * CoordinatedVariation Constructor
	 * 
	 * @param dblReferenceVolatility The Reference Volatility
	 * @param dblReferenceLiquidity The Reference Liquidity
	 * 
	 * @throws java.lang.Exception Thrwon if the Inputs are Invalid
	 */

	public CoordinatedVariation (
		final double dblReferenceVolatility,
		final double dblReferenceLiquidity)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblReferenceVolatility = dblReferenceVolatility) ||
			0. >= _dblReferenceVolatility || !org.drip.numerical.common.NumberUtil.IsValid
				(_dblReferenceLiquidity = dblReferenceLiquidity) || 0. >= _dblReferenceLiquidity)
			throw new java.lang.Exception ("CoordinatedVariation Constructor => Invalid Inputs");

		_dblInvariant = _dblReferenceVolatility * _dblReferenceVolatility * _dblReferenceLiquidity;
	}

	/**
	 * Retrieve the Reference Liquidity
	 * 
	 * @return The Reference Liquidity
	 */

	public double referenceLiquidity()
	{
		return _dblReferenceLiquidity;
	}

	/**
	 * Retrieve the Reference Volatility
	 * 
	 * @return The Reference Volatility
	 */

	public double referenceVolatility()
	{
		return _dblReferenceVolatility;
	}

	/**
	 * Retrieve the Volatility/Liquidity Invariant
	 * 
	 * @return The Volatility/Liquidity Invariant
	 */

	public double invariant()
	{
		return _dblInvariant;
	}

	/**
	 * Estimate the Liquidity given the Volatility
	 * 
	 * @param dblVolatility The Volatility
	 * 
	 * @return Liquidity Estimate using the Volatility
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double liquidity (
		final double dblVolatility)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblVolatility))
			throw new java.lang.Exception ("CoordinatedVariation::liquidity => Invalid Inputs");

		return _dblInvariant / (dblVolatility * dblVolatility);
	}

	/**
	 * Estimate the Volatility given the Liquidity
	 * 
	 * @param dblLiquidity The Liquidity
	 * 
	 * @return Volatility Estimate using the Liquidity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double volatility (
		final double dblLiquidity)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblLiquidity))
			throw new java.lang.Exception ("CoordinatedVariation::volatility => Invalid Inputs");

		return java.lang.Math.sqrt (_dblInvariant / dblLiquidity);
	}

	/**
	 * Compute the Volatility Function from the Liquidity Function
	 * 
	 * @param r1ToR1Liquidity The R^1 To R^1 Liquidity Function
	 * 
	 * @return The R^1 To R^1 Volatility Function
	 */

	public org.drip.function.definition.R1ToR1 volatilityFunction (
		final org.drip.function.definition.R1ToR1 r1ToR1Liquidity)
	{
		if (null == r1ToR1Liquidity) return null;

		return new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return java.lang.Math.sqrt (_dblInvariant / r1ToR1Liquidity.evaluate (dblTime));
			}
		};
	}
}
