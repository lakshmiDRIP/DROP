
package org.drip.oms.transaction;

import org.drip.numerical.common.NumberUtil;
import org.drip.service.common.FormatUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>OrderQuantityTracker</i> tracks the Components of an Order Quantity. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Berkowitz, S. A., D. E. Logue, and E. A. J. Noser (1988): The Total Cost of Transactions on the
 * 				NYSE <i>Journal of Finance</i> <b>43 (1)</b> 97-112
 * 		</li>
 * 		<li>
 * 			Cont, R., and A. Kukanov (2017): Optimal Order Placement in Limit Order Markets <i>Quantitative
 * 				Finance</i> <b>17 (1)</b> 21-39
 * 		</li>
 * 		<li>
 * 			Vassilis, P. (2005a): A Realistic Model of Market Liquidity and Depth <i>Journal of Futures
 * 				Markets</i> <b>25 (5)</b> 443-464
 * 		</li>
 * 		<li>
 * 			Vassilis, P. (2005b): Slow and Fast Markets <i>Journal of Economics and Business</i> <b>57
 * 				(6)</b> 576-593
 * 		</li>
 * 		<li>
 * 			Weiss, D. (2006): <i>After the Trade is Made: Processing Securities Transactions</i> <b>Portfolio
 * 				Publishing</b> London UK
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/README.md">R<sup>d</sup> Order Specification, Handling, and Management</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/transaction/README.md">Order Specification and Session Metrics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OrderQuantityTracker
{
	private double _leaves = Double.NaN;
	private double _current = Double.NaN;
	private double _original = Double.NaN;
	private double _cumulative = Double.NaN;
	private double _lastShares = Double.NaN;

	/**
	 * <i>OrderQuantityTracker</i> Constructor
	 * 
	 * @param original Original Order Quantity
	 * 
	 * @throws Exception Thrown if the Order Quantity is Invalid
	 */

	public OrderQuantityTracker (
		final double original)
		throws Exception
	{
		if (!NumberUtil.IsValid (_original = original)) {
			throw new Exception ("OrderQuantityTracker Constructor => Invalid Inputs");
		}

		_current = _original;
		_lastShares = 0.;
		_cumulative = 0.;
		_leaves = 0.;
	}

	/**
	 * Retrieve the Original Order Quantity
	 * 
	 * @return Original Order Quantity
	 */

	public double original()
	{
		return _original;
	}

	/**
	 * Retrieve the Current Order Quantity
	 * 
	 * @return Current Order Quantity
	 */

	public double current()
	{
		return _current;
	}

	/**
	 * Retrieve the Cumulatively Executed Order Quantity
	 * 
	 * @return Cumulatively Executed Order Quantity
	 */

	public double cumulative()
	{
		return _cumulative;
	}

	/**
	 * Retrieve the Order Leaves Quantity
	 * 
	 * @return Order Leaves Quantity
	 */

	public double leaves()
	{
		return _leaves;
	}

	/**
	 * Retrieve the Last Executed Shares Count
	 * 
	 * @return Last Executed Shares Count
	 */

	public double lastShares()
	{
		return _lastShares;
	}

	/**
	 * Update the Current Order Quantity
	 * 
	 * @param current Current Order Quantity
	 * 
	 * @return TRUE - Current Order Quantity successfully Updated
	 */

	public boolean updateCurrent (
		final double current)
	{
		if (!NumberUtil.IsValid (current)) {
			return false;
		}

		_leaves -= _original - (_current = current);
		return true;
	}

	/**
	 * Update the Last Executed Shares Count
	 * 
	 * @param lastShares Last Executed Shares Count
	 * 
	 * @return TRUE - Last Executed Shares Count successfully Updated
	 */

	public boolean updateLastShares (
		final double lastShares)
	{
		if (!NumberUtil.IsValid (lastShares)) {
			return false;
		}

		_cumulative += (_lastShares = lastShares);
		_leaves -= lastShares;
		return true;
	}

	/**
	 * Indicate if the Order has been Filled
	 * 
	 * @return TRUE - Order has been Filled
	 */

	public boolean filled()
	{
		return 0. >= _leaves;
	}

	/**
	 * Process Order Acceptance
	 * 
	 * @return TRUE - Order Acceptance successfully processed
	 */

	public boolean accepted()
	{
		_leaves = _current;
		return true;
	}

	/**
	 * Generate String version of the state with Padding applied
	 * 
	 * @param pad Padding
	 * 
	 * @return String version of the state with Padding applied
	 */

	public String toString (
		final String pad)
	{
		return pad + "OrderQuantityTracker[" +
			"Original =>" + FormatUtil.FormatDouble (_original, 0, 0, 1.) +
			"Current =>" + FormatUtil.FormatDouble (_current, 0, 0, 1.) +
			"Cumulative =>" + FormatUtil.FormatDouble (_cumulative, 0, 0, 1.) +
			"Leaves =>" + FormatUtil.FormatDouble (_leaves, 0, 0, 1.) +
			"Last Shares =>" + FormatUtil.FormatDouble (_lastShares, 0, 0, 1.) +
		"]";
	}

	/**
	 * Generate String version of the state without Padding
	 * 
	 * @return String version of the state without Padding
	 */

	@Override public String toString()
	{
		return toString ("");
	}
}
