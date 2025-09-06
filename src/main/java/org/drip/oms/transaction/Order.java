
package org.drip.oms.transaction;

import java.util.Date;

import org.drip.numerical.common.NumberUtil;
import org.drip.oms.exchange.TradesWindow;
import org.drip.oms.fill.OrderFulfillment;

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
 * <i>Order</i> holds the Details of an Order. The References are:
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

public class Order
{
	private String _id = "";
	private Side _side = null;
	private String _ticker = "";
	private String _parentID = "";
	private Date _updateTime = null;
	private Date _creationTime = null;
	private OrderIssuer _issuer = null;
	private Date _completionTime = null;
	private int _type = Integer.MIN_VALUE;
	private int _state = Integer.MIN_VALUE;
	private TimeInForce _timeInForce = null;
	private OrderQuantityTracker _quantityTracker = null;
	private OrderFillWholeSettings _fillWholeSettings = null;

	/**
	 * Construct a POV Participation Rate Order
	 * 
	 * @param issuer Order Issuer
	 * @param ticker Security Identifier/Ticker
	 * @param id Order ID
	 * @param parentID Parent Order ID
	 * @param type Order Type
	 * @param creationTime Creation Time
	 * @param side Order Side
	 * @param timeInForce Time-in-Force Settings
	 * @param fillWholeSettings Order Fill-Whole Settings
	 * @param participationRate POV Participation Rate
	 * @param tradesWindow Trades Window
	 * 
	 * @return POV Participation Rate Order
	 */

	public static final Order FromPOV (
		final OrderIssuer issuer,
		final String ticker,
		final String id,
		final String parentID,
		final int type,
		final Date creationTime,
		final Side side,
		final TimeInForce timeInForce,
		final OrderFillWholeSettings fillWholeSettings,
		final double participationRate,
		final TradesWindow tradesWindow)
	{
		try {
			return null == tradesWindow ? null : new org.drip.oms.transaction.Order (
				issuer,
				ticker,
				id,
				parentID,
				type,
				creationTime,
				side,
				participationRate * tradesWindow.sessionTradeVolume(),
				timeInForce,
				fillWholeSettings
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Order Constructor
	 * 
	 * @param issuer Order Issuer
	 * @param ticker Security Identifier/Ticker
	 * @param id Order ID
	 * @param parentID Parent Order ID
	 * @param type Order Type
	 * @param creationTime Creation Time
	 * @param side Order Side
	 * @param size Order Size
	 * @param timeInForce Time-in-Force Settings
	 * @param fillWholeSettings Order Fill-Whole Settings
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public Order (
		final OrderIssuer issuer,
		final String ticker,
		final String id,
		final String parentID,
		final int type,
		final Date creationTime,
		final Side side,
		final double size,
		final TimeInForce timeInForce,
		final OrderFillWholeSettings fillWholeSettings)
		throws Exception
	{
		if (null == (_issuer = issuer) ||
			null == (_ticker = ticker) || _ticker.isEmpty() ||
			null == (_id = id) || _id.isEmpty() ||
			null == (_creationTime = creationTime) ||
			null == (_side = side))
		{
			throw new Exception ("Order Constructor => Invalid Inputs");
		}

		_type = type;
		_parentID = parentID;
		_state = OrderState.OPEN;
		_timeInForce = timeInForce;
		_updateTime = creationTime;
		_fillWholeSettings = fillWholeSettings;

		_quantityTracker = new OrderQuantityTracker (Math.abs (size));
	}

	/**
	 * Retrieve the Order Security Identifier/Ticker
	 * 
	 * @return The Order Security Identifier/Ticker
	 */

	public String ticker()
	{
		return _ticker;
	}

	/**
	 * Retrieve the Order ID
	 * 
	 * @return The Order ID
	 */

	public String id()
	{
		return _id;
	}

	/**
	 * Retrieve the Parent Order ID
	 * 
	 * @return The Parent Order ID
	 */

	public String parentID()
	{
		return _parentID;
	}

	/**
	 * Retrieve the Order Issuer
	 * 
	 * @return The Order Issuer
	 */

	public OrderIssuer issuer()
	{
		return _issuer;
	}

	/**
	 * Retrieve the Order Type
	 * 
	 * @return The Order Type
	 */

	public int type()
	{
		return _type;
	}

	/**
	 * Retrieve the Order State
	 * 
	 * @return The Order State
	 */

	public int state()
	{
		return _state;
	}

	/**
	 * Retrieve the Order Creation Time
	 * 
	 * @return The Order Creation Time
	 */

	public Date creationTime()
	{
		return _creationTime;
	}

	/**
	 * Retrieve the Order Update Time
	 * 
	 * @return The Order Update Time
	 */

	public Date updateTime()
	{
		return _updateTime;
	}

	/**
	 * Retrieve the Order Completion Time
	 * 
	 * @return The Order Completion Time
	 */

	public Date completionTime()
	{
		return _completionTime;
	}

	/**
	 * Retrieve the Time-in-Force Settings
	 * 
	 * @return The Time-in-Force Settings
	 */

	public TimeInForce timeInForce()
	{
		return _timeInForce;
	}

	/**
	 * Retrieve the Fill-Whole Settings
	 * 
	 * @return The Fill-Whole Settings
	 */

	public OrderFillWholeSettings fillWholeSettings()
	{
		return _fillWholeSettings;
	}

	/**
	 * Retrieve the Order Side
	 * 
	 * @return The Order Side
	 */

	public Side side()
	{
		return _side;
	}

	/**
	 * Retrieve the Order Quantity Tracker
	 * 
	 * @return The Order Quantity Tracker
	 */

	public OrderQuantityTracker quantityTracker()
	{
		return _quantityTracker;
	}

	/**
	 * Indicate if the Order is Conditional. Default is Non-Conditional
	 * 
	 * @return TRUE - Order is Conditional
	 */

	public boolean isConditional()
	{
		return false;
	}

	/**
	 * Retrieve the Fill-or-Kill Flag
	 * 
	 * @return The Fill-or-Kill Flag
	 */

	public boolean fillOrKill()
	{
		return null != _fillWholeSettings &&
			OrderFillWholeSettings.FILL_OR_KILL == _fillWholeSettings.fulfillScheme();
	}

	/**
	 * Retrieve the All-or-None Flag
	 * 
	 * @return The All-or-None Flag
	 */

	public boolean allOrNone()
	{
		return null != _fillWholeSettings &&
			OrderFillWholeSettings.ALL_OR_NONE == _fillWholeSettings.fulfillScheme();
	}

	/**
	 * Set the Order State
	 * 
	 * @param orderState Order State
	 * 
	 * @return TRUE - The Order State successfully set
	 */

	public boolean setState (
		final int orderState)
	{
		_updateTime = new Date();

		_state = orderState;
		return true;
	}

	/**
	 * Amend the Order Size
	 * 
	 * @param orderSize Order Size
	 * 
	 * @return TRUE - The Order Size successfully set
	 */

	public boolean amendSize (
		final double orderSize)
	{
		if (!NumberUtil.IsValid (
				orderSize
			) || 0. >= orderSize
		)
		{
			return false;
		}

		_updateTime = new Date();

		return _quantityTracker.updateCurrent (orderSize);
	}

	/**
	 * Fill an Order Partially/Fully
	 * 
	 * @param orderFulfillment Order Fulfillment
	 * 
	 * @return TRUE - The Order Full/Partial Fill has been successfully handled
	 */

	public boolean fulfill (
		final OrderFulfillment orderFulfillment)
	{
		if (null == orderFulfillment)
		{
			_state = OrderState.CANCELED;
			return false;
		}

		double filledSize = orderFulfillment.executedSize();

		_updateTime = new Date();

		if (!_quantityTracker.updateLastShares (filledSize)) {
			return false;
		}

		if (filledSize < _quantityTracker.current()) {
			_state = OrderState.PARTIALLY_FILLED;
		}

		_completionTime = new Date();

		_state = OrderState.FILLED;
		return true;
	}

	/**
	 * Indicate if the Order is Outstanding
	 * 
	 * @return TRUE - Order is Outstanding
	 */

	public boolean isOutstanding()
	{
		return null == _completionTime;
	}

	/**
	 * Set the Order Completion Time
	 * 
	 * @param completionTime The Order Completion Time
	 * 
	 * @return TRUE - Order is Set to Complete
	 */

	public boolean setComplete (
		final Date completionTime)
	{
		if (null != _completionTime || null == completionTime)
		{
			return false;
		}

		_completionTime = new Date();

		return true;
	}

	/**
	 * Set the Order as Rejected
	 * 
	 * @param orderState Order State
	 * 
	 * @return TRUE - The Order State successfully set to Rejected
	 */

	public boolean setRejected()
	{
		_updateTime = new Date();

		_state = OrderState.REJECTED;
		return true;
	}

	/**
	 * Set the Order as Accepted
	 * 
	 * @param orderState Order State
	 * 
	 * @return TRUE - The Order State successfully set to Accepted
	 */

	public boolean setAccepted()
	{
		_updateTime = new Date();

		if (!_quantityTracker.accepted()) {
			return false;
		}

		_state = OrderState.OPEN;
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
		return "\n" + pad + "Order: [" +
			"\n" + pad + "\t" +
			"Ticker => " + _ticker + "; " +
			"ID => " + _id + "; " +
			"Parent ID => " + _parentID + "; " +
			"Type => " + OrderType.ToString (_type) + "; " +
			"State => " + OrderState.ToString (_state) + "; " +
			"Creation Time => " + _creationTime + "; " +
			"Update Time => " + _updateTime + "; " +
			"Completion Time => " + _completionTime + "; " +
			"Issuer => " + _issuer.toString (pad + pad) + "; " +
			(null == _timeInForce ? "" : "Time In Force =>" + _timeInForce.toString (pad + "\t") + "; ") +
			(
				null == _fillWholeSettings ?
					"" : "Fill Whole Settings =>" + _fillWholeSettings.toString (pad + "\t") + "; "
			) +
			"Side => " + _side.toString (pad + pad) + "; " +
			"Quantity Tracker => " + _quantityTracker.toString (pad + "\t") +
			 "\n" + pad + "]";
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
