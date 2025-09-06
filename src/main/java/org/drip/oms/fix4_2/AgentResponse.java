
package org.drip.oms.fix4_2;

import java.util.Date;

import org.drip.oms.transaction.Order;

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
 * <i>AgentResponse</i> implements the Response out of a FIX Agent. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/fix4_2/README.md">Implementation of FIX 4.2 Constructs</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AgentResponse
{
	private Order _order = null;
	private String _comment = "";
	private String _requestID = "";
	private Date _processingStartTime = null;
	private Date _processingUpdateTime = null;
	private int _messageType = Integer.MIN_VALUE;
	private int _executionType = Integer.MIN_VALUE;
	private int _executionTransactionType = Integer.MIN_VALUE;

	/**
	 * Construct a REJECTED <i>AgentResponse</i> Instance
	 * 
	 * @param processingStartTime Processing Start Time
	 * @param order Order Instance
	 * @param requestID Request ID
	 * 
	 * @return REJECTED <i>AgentResponse</i> Instance
	 */

	public static final AgentResponse REJECTED (
		final Date processingStartTime,
		final Order order,
		final String requestID)
	{
		try {
			return new AgentResponse (
				processingStartTime,
				new Date(),
				AgentResponseMessageType.EXECUTION,
				order,
				requestID,
				AgentResponseExecutionType.REJECTED,
				AgentResponseExecutionTransactionType.NEW,
				"Rejected by Desk, i.e., Sales/Trader"
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an ACCEPTED <i>AgentResponse</i> Instance
	 * 
	 * @param processingStartTime Processing Start Time
	 * @param order Order Instance
	 * @param requestID Request ID
	 * 
	 * @return ACCEPTED <i>AgentResponse</i> Instance
	 */

	public static final AgentResponse ACCEPTED (
		final Date processingStartTime,
		final Order order,
		final String requestID)
	{
		try {
			return new AgentResponse (
				processingStartTime,
				new Date(),
				AgentResponseMessageType.EXECUTION,
				order,
				requestID,
				AgentResponseExecutionType.NEW,
				AgentResponseExecutionTransactionType.NEW,
				""
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>AgentResponse</i> Constructor
	 * 
	 * @param processingStartTime Processing Start Time
	 * @param processingUpdateTime Processing Update Time
	 * @param messageType Agent Response Message Type
	 * @param order Order Instance
	 * @param requestID Request ID
	 * @param executionType Response Execution Type
	 * @param executionTransactionType Response Execution Transaction Type
	 * @param comment Comment
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public AgentResponse (
		final Date processingStartTime,
		final Date processingUpdateTime,
		final int messageType,
		final Order order,
		final String requestID,
		final int executionType,
		final int executionTransactionType,
		final String comment)
		throws Exception
	{
		if (null == (_processingStartTime = processingStartTime) ||
			null == (_processingUpdateTime = processingUpdateTime) ||
			null == (_order = order) ||
			null == (_requestID = requestID) || _requestID.isEmpty())
		{
			throw new Exception ("AgentResponse Constructor => Invalid Inputs");
		}

		_comment = comment;
		_messageType = messageType;
		_executionType = executionType;
		_executionTransactionType = executionTransactionType;
	}

	/**
	 * Retrieve the Agent Processing Start Time
	 * 
	 * @return Agent Processing Start Time
	 */

	public Date processingStartTime()
	{
		return _processingStartTime;
	}

	/**
	 * Retrieve the Agent Processing Update Time
	 * 
	 * @return Agent Processing Update Time
	 */

	public Date processingUpdateTime()
	{
		return _processingUpdateTime;
	}

	/**
	 * Retrieve the Response Message Type
	 * 
	 * @return Response Message Type
	 */

	public int messageType()
	{
		return _messageType;
	}

	/**
	 * Retrieve the Order Instance
	 * 
	 * @return Order Instance
	 */

	public Order order()
	{
		return _order;
	}

	/**
	 * Retrieve the Agent Request ID
	 * 
	 * @return Agent Request ID
	 */

	public String requestID()
	{
		return _requestID;
	}

	/**
	 * Retrieve the Response Execution Type
	 * 
	 * @return Response Execution Type
	 */

	public int executionType()
	{
		return _executionType;
	}

	/**
	 * Retrieve the Response Execution Transaction Type
	 * 
	 * @return Response Execution Transaction Type
	 */

	public int executionTransactionType()
	{
		return _executionTransactionType;
	}

	/**
	 * Retrieve the Agent Request Comment
	 * 
	 * @return Agent Request Comment
	 */

	public String comment()
	{
		return _comment;
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
		return pad + "Agent Response: [" +
			"\n" + pad + "\t" +
			"Processing Start Time =>" + _processingStartTime + "; " +
			"processing Update Time =>" + _processingUpdateTime + "; " +
			"Message Type =>" + _messageType + "; " +
			"Type =>" + _requestID + "; " +
			"Execution Type =>" + _executionType + "; " +
			"Execution Transaction Type =>" + _executionTransactionType + "; " +
			"Comment =>" + _comment + "; " +
			"Order =>" + _order.toString (pad + pad) +
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
