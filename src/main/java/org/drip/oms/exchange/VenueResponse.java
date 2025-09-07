
package org.drip.oms.exchange;

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
 * <i>VenueResponse</i> contains the Order Processing Response coming out of a Venue. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Chen, J. (2021): Time in Force: Definition, Types, and Examples
 * 				https://www.investopedia.com/terms/t/timeinforce.asp
 * 		</li>
 * 		<li>
 * 			Cont, R., and A. Kukanov (2017): Optimal Order Placement in Limit Order Markets <i>Quantitative
 * 				Finance</i> <b>17 (1)</b> 21-39
 * 		</li>
 * 		<li>
 * 			Vassilis, P. (2005b): Slow and Fast Markets <i>Journal of Economics and Business</i> <b>57
 * 				(6)</b> 576-593
 * 		</li>
 * 		<li>
 * 			Weiss, D. (2006): <i>After the Trade is Made: Processing Securities Transactions</i> <b>Portfolio
 * 				Publishing</b> London UK
 * 		</li>
 * 		<li>
 * 			Wikipedia (2023): Central Limit Order Book
 * 				https://en.wikipedia.org/wiki/Central_limit_order_book
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/README.md">R<sup>d</sup> Order Specification, Handling, and Management</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/exchange/README.md">Implementation of Venue Order Handling</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VenueResponse
{
	private Order _order = null;
	private String _clOrdID = "";
	private String _comment = "";
	private String _origClOrdID = "";
	private int _type = Integer.MIN_VALUE;
	private Date _processingStartTime = null;
	private Date _processingFinishTime = null;

	/**
	 * Construct an Instance of <code>NEW</code> <i>VenueResponse</i> Type
	 * 
	 * @param processingStartTime Start of the Venue Processing Time
	 * @param processingFinishTime Finish of the Venue Processing Time
	 * @param clOrdID <i>clOrdID</i>
	 * @param origClOrdID <i>origClOrdID</i>
	 * @param order Order
	 * @param comment Processing Comments
	 * 
	 * @return Instance of <code>NEW</code> <i>VenueResponse</i> Type
	 */

	public static final VenueResponse NEW (
		final Date processingStartTime,
		final Date processingFinishTime,
		final String clOrdID,
		final String origClOrdID,
		final Order order,
		final String comment)
	{
		try {
			return new VenueResponse (
				processingStartTime,
				processingFinishTime,
				clOrdID,
				origClOrdID,
				VenueResponseType.NEW,
				order,
				comment
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of <code>REJECTED</code> <i>VenueResponse</i> Type
	 * 
	 * @param processingStartTime Start of the Venue Processing Time
	 * @param processingFinishTime Finish of the Venue Processing Time
	 * @param clOrdID <i>clOrdID</i>
	 * @param origClOrdID <i>origClOrdID</i>
	 * @param order Order
	 * @param comment Processing Comments
	 * 
	 * @return Instance of <code>REJECTED</code> <i>VenueResponse</i> Type
	 */

	public static final VenueResponse REJECTED (
		final Date processingStartTime,
		final Date processingFinishTime,
		final String clOrdID,
		final String origClOrdID,
		final Order order,
		final String comment)
	{
		try {
			return new VenueResponse (
				processingStartTime,
				processingFinishTime,
				clOrdID,
				origClOrdID,
				VenueResponseType.REJECTED,
				order,
				comment
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of <code>EXECUTION</code> <i>VenueResponse</i> Type
	 * 
	 * @param processingStartTime Start of the Venue Processing Time
	 * @param processingFinishTime Finish of the Venue Processing Time
	 * @param clOrdID <i>clOrdID</i>
	 * @param origClOrdID <i>origClOrdID</i>
	 * @param order Order
	 * @param comment Processing Comments
	 * 
	 * @return Instance of <code>EXECUTION</code> <i>VenueResponse</i> Type
	 */

	public static final VenueResponse EXECUTION (
		final Date processingStartTime,
		final Date processingFinishTime,
		final String clOrdID,
		final String origClOrdID,
		final Order order,
		final String comment)
	{
		try {
			return new VenueResponse (
				processingStartTime,
				processingFinishTime,
				clOrdID,
				origClOrdID,
				VenueResponseType.EXECUTION,
				order,
				comment
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>VenueResponse</i> Constructor
	 * 
	 * @param processingStartTime Start of the Venue Processing Time
	 * @param processingFinishTime Finish of the Venue Processing Time
	 * @param clOrdID <i>clOrdID</i>
	 * @param origClOrdID <i>origClOrdID</i>
	 * @param type Response Type
	 * @param order Order
	 * @param comment Processing Comments
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public VenueResponse (
		final Date processingStartTime,
		final Date processingFinishTime,
		final String clOrdID,
		final String origClOrdID,
		final int type,
		final Order order,
		final String comment)
		throws Exception
	{
		if (null == (_processingStartTime = processingStartTime) ||
			null == (_processingFinishTime = processingFinishTime) ||
			null == (_clOrdID = clOrdID) || _clOrdID.isEmpty() ||
			null == (_order = order))
		{
			throw new Exception ("VenueResponse Constructor => Invalid Inputs");
		}

		_type = type;
		_comment = comment;
		_origClOrdID = origClOrdID;
	}

	/**
	 * Retrieve the Start of the Venue Processing Time
	 * 
	 * @return Start of the Venue Processing Time
	 */

	public Date processingStartTime()
	{
		return _processingStartTime;
	}

	/**
	 * Retrieve the Finish of the Venue Processing Time
	 * 
	 * @return Finish of the Venue Processing Time
	 */

	public Date processingFinishTime()
	{
		return _processingFinishTime;
	}

	/**
	 * Retrieve the <i>clOrdID</i>
	 * 
	 * @return <i>clOrdID</i>
	 */

	public String clOrdID()
	{
		return _clOrdID;
	}

	/**
	 * Retrieve the <i>origClOrdID</i>
	 * 
	 * @return <i>origClOrdID</i>
	 */

	public String origClOrdID()
	{
		return _origClOrdID;
	}

	/**
	 * Retrieve the Response Type
	 * 
	 * @return Response Type
	 */

	public int type()
	{
		return _type;
	}

	/**
	 * Retrieve the Response Order
	 * 
	 * @return Response Order
	 */

	public Order order()
	{
		return _order;
	}

	/**
	 * Retrieve the Processing Comments
	 * 
	 * @return Processing Comments
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
		return "\n" + pad + "Venue Response: [" +
			"\n" + pad + "\t" +
			"Procesing Start Time => " + _processingStartTime + "; " +
			"Procesing Finish Time => " + _processingFinishTime + "; " +
			"clOrdID => " + _clOrdID + "; " +
			"origClOrdID => " + _origClOrdID + "; " +
			"Type => " + VenueResponseType.ToString (_type) + "; " +
			"Comment => " + _comment + "; " +
			"Order => " + _order.toString (pad + "\t") +
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
