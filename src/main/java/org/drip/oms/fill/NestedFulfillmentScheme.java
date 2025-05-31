
package org.drip.oms.fill;

import org.drip.oms.transaction.Order;
import org.drip.oms.transaction.OrderState;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>NestedFulfillmentScheme</i> implements an Order Fulfillment Scheme by generating Nested Child Orders.
 *  The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/fill/README.md">Order Fulfillment Scheme Implementations/Results</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NestedFulfillmentScheme
{
	private Order _orderNode = null;
	private NestedFulfillmentScheme _child = null;
	private NestedFulfillmentScheme _parent = null;

	private boolean fillOrKill (
		final OrderExecutionProvider orderExecutionProvider)
	{
		if (!orderExecutionProvider.isOrderMarketable (_orderNode)) {
			_orderNode.setState (OrderState.CANCELED);

			return false;
		}

		return null == _orderNode.fulfill (orderExecutionProvider.attemptFill (_orderNode));
	}

	private boolean allOrNone (
		final OrderExecutionProvider orderExecutionProvider)
	{
		int fulfillTryCount = 0;

		int fulfillTryLimit = _orderNode.fillWholeSettings().fulfillTryLimit();

		while (fulfillTryLimit - 1 > fulfillTryCount &&
			!orderExecutionProvider.isOrderMarketable (_orderNode))
		{
			_orderNode.fulfill (orderExecutionProvider.attemptFill (_orderNode));

			++fulfillTryCount;
		}

		if (!orderExecutionProvider.isOrderMarketable (_orderNode)) {
			_orderNode.setState (OrderState.CANCELED);

			return false;
		}

		return null == _orderNode.fulfill (orderExecutionProvider.attemptFill (_orderNode));
	}

	private boolean fillWithChildOrders (
		final OrderExecutionProvider orderExecutionProvider)
	{
		Order childOrder = _orderNode.fulfill (orderExecutionProvider.attemptFill (_orderNode));

		if (null == childOrder) {
			return null == _parent ? true : _parent.node().setState (_orderNode.state());
		}

		try {
			_child = new NestedFulfillmentScheme (childOrder, this);
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return _child.fillWithChildOrders (orderExecutionProvider);
	}

	/**
	 * NestedFulfillmentScheme Constructor
	 * 
	 * @param orderNode Order Node
	 * @param parent Parent
	 * 
	 * @throws Exception Thrown if Inputs are Invalid
	 */

	public NestedFulfillmentScheme (
		final Order orderNode,
		final NestedFulfillmentScheme parent)
		throws Exception
	{
		if (null == (_orderNode = orderNode)) {
			throw new Exception ("NestedFulfillmentScheme Constructor => Invalid Inputs");
		}

		_parent = parent;
	}

	/**
	 * Retrieve the Order Node
	 * 
	 * @return The Order Node
	 */

	public Order node()
	{
		return _orderNode;
	}

	/**
	 * Retrieve the Parent Order
	 * 
	 * @return The Parent Order
	 */

	public NestedFulfillmentScheme parent()
	{
		return _parent;
	}

	/**
	 * Retrieve the Child Order
	 * 
	 * @return The Child Order
	 */

	public NestedFulfillmentScheme child()
	{
		return _child;
	}

	/**
	 * Fill the Order Using Child Orders
	 * 
	 * @param orderExecutionProvider The Order Execution Provider
	 * 
	 * @return TRUE - The Order successfully Filled
	 */

	public boolean fill (
		final OrderExecutionProvider orderExecutionProvider)
	{
		if (null == orderExecutionProvider) {
			return false;
		}

		if (_orderNode.fillOrKill()) {
			return fillOrKill (orderExecutionProvider);
		}

		if (_orderNode.allOrNone()) {
			return allOrNone (orderExecutionProvider);
		}

		return fillWithChildOrders (orderExecutionProvider);
	}
}
