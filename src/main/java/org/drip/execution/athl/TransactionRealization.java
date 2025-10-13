
package org.drip.execution.athl;

import org.drip.execution.impact.TransactionFunction;
import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>TransactionRealization</i> holds the Suite of Empirical Drift/Wander Signals that have been emitted off
 * 	of a Transaction Run using the Scheme by Almgren, Thum, Hauptmann, and Li (2005), using the
 * 	Parameterization of Almgren (2003). It provides the following Functions:
 * 	<ul>
 * 		<li><i>TransactionRealization</i> Constructor</li>
 * 		<li>Retrieve the Permanent Market Impact Transaction Function</li>
 * 		<li>Retrieve the Temporary Market Impact Transaction Function</li>
 * 		<li>Retrieve the Asset Daily Volatility</li>
 * 		<li>Retrieve the Transaction Amount X</li>
 * 		<li>Retrieve the Transaction Completion Time T in Days</li>
 * 		<li>Retrieve the Transaction Completion Time in Days Adjusted for the Permanent Lag <code>TPost</code></li>
 * 		<li>Emit the <code>IJK</code> Signal</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
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
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/README.md">Almgren-Thum-Hauptmann-Li Calibration</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TransactionRealization
{
	private double _t = Double.NaN;
	private double _x = Double.NaN;
	private double _tPost = Double.NaN;
	private double _tSqrt = Double.NaN;
	private double _volatility = Double.NaN;
	private TransactionFunction _permanentTransactionFunction = null;
	private TransactionFunction _temporaryTransactionFunction = null;

	/**
	 * <i>TransactionRealization</i> Constructor
	 * 
	 * @param permanentTransactionFunction The Permanent Market Impact Transaction Function
	 * @param temporaryTransactionFunction The Temporary Market Impact Transaction Function
	 * @param volatility The Asset Daily Volatility
	 * @param x The Transaction Amount
	 * @param t The Transaction Completion Time in Days
	 * @param tPost The Transaction Completion Time in Days Adjusted for the Permanent Lag
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public TransactionRealization (
		final TransactionFunction permanentTransactionFunction,
		final TransactionFunction temporaryTransactionFunction,
		final double volatility,
		final double x,
		final double t,
		final double tPost)
		throws Exception
	{
		if (null == (_permanentTransactionFunction = permanentTransactionFunction) ||
			null == (_temporaryTransactionFunction = temporaryTransactionFunction) ||
			!NumberUtil.IsValid (_volatility = volatility) || 0. > _volatility ||
			!NumberUtil.IsValid (_x = x) ||
			!NumberUtil.IsValid (_t = t) || 0. > _t ||
			!NumberUtil.IsValid (_tPost = tPost) ||
			_t >= _tPost)
		{
			throw new Exception  ("TransactionRealization Constructor => Invalid Inputs");
		}

		_tSqrt = Math.sqrt (_t);
	}

	/**
	 * Retrieve the Permanent Market Impact Transaction Function
	 * 
	 * @return The Permanent Market Impact Transaction Function
	 */

	public TransactionFunction permanentMarketImpactFunction()
	{
		return _permanentTransactionFunction;
	}

	/**
	 * Retrieve the Temporary Market Impact Transaction Function
	 * 
	 * @return The Temporary Market Impact Transaction Function
	 */

	public TransactionFunction temporaryMarketImpactFunction()
	{
		return _temporaryTransactionFunction;
	}

	/**
	 * Retrieve the Asset Daily Volatility
	 * 
	 * @return The Asset Daily Volatility
	 */

	public double volatility()
	{
		return _volatility;
	}

	/**
	 * Retrieve the Transaction Amount X
	 * 
	 * @return The Transaction Amount X
	 */

	public double x()
	{
		return _x;
	}

	/**
	 * Retrieve the Transaction Completion Time T in Days
	 * 
	 * @return The Transaction Completion Time T in Days
	 */

	public double t()
	{
		return _t;
	}

	/**
	 * Retrieve the Transaction Completion Time in Days Adjusted for the Permanent Lag <code>TPost</code>
	 * 
	 * @return The Transaction Completion Time in Days Adjusted for the Permanent Lag <code>TPost</code>
	 */

	public double tPost()
	{
		return _tPost;
	}

	/**
	 * Emit the <code>IJK</code> Signal
	 * 
	 * @param randomI The Random "I" Instance
	 * @param randomJ The Random "J" Instance
	 * 
	 * @return The <code>IJK</code> Signal Instance
	 */

	public IJK emitSignal (
		final double randomI,
		final double randomJ)
	{
		if (!NumberUtil.IsValid (randomI) || !NumberUtil.IsValid (randomJ)) {
			return null;
		}

		try {
			return new IJK (
				new TransactionSignal (
					_permanentTransactionFunction.evaluate (_x, _t),
					_volatility * _tSqrt * randomI,
					0.
				),
				new TransactionSignal (
					_temporaryTransactionFunction.evaluate (_x, _t),
					_volatility * Math.sqrt (_t / 12. * (4. - (3. * _t / _tPost))) * randomJ,
					0.5 * (_tPost - _t) / _tSqrt * randomI
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
