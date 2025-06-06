
package org.drip.oms.transaction;

import java.util.Date;

import org.drip.numerical.common.NumberUtil;
import org.drip.oms.exchange.Venue;
import org.drip.service.common.StringUtil;

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
 * <i>Trade</i> maintains Details of a single Trade. The References are:
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
 * 			Jacob, B. (2024): <i>7 Execution Algorithms You Should Know About�.</i>
 * 				https://www.linkedin.com/pulse/7-execution-algorithms-you-should-know-benjamin-jacob-vl7pf/
 * 		</li>
 * 		<li>
 * 			Vassilis, P. (2005): Slow and Fast Markets <i>Journal of Economics and Business</i> <b>57
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

public class Trade
{
	private String _id = "";
	private Date _time = null;
	private Side _side = null;
	private Venue _venue = null;
	private String _ticker = "";
	private double _size = Double.NaN;
	private double _price = Double.NaN;

	/**
	 * Construct a Standard <i>Trade</i> Instance
	 * 
	 * @param ticker Ticker
	 * @param price Price
	 * @param size Size
	 * @param side Side
	 * 
	 * @return Standard <i>Trade</i> Instance
	 */

	public static final Trade Standard (
		final String ticker,
		final double price,
		final double size,
		final Side side)
	{
		try {
			return new Trade (ticker, StringUtil.GUID(), price, size, side, new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>Trade</i> Constructor
	 * 
	 * @param ticker Ticker
	 * @param id Trade ID
	 * @param price Price
	 * @param size Size
	 * @param side Side
	 * @param time Time
	 * 
	 * @throws Exception Thrown if <i>Trade</i> cannot be constructed
	 */

	public Trade (
		final String ticker,
		final String id,
		final double price,
		final double size,
		final Side side,
		final Date time)
		throws Exception
	{
		if (null == (_ticker = ticker) || _ticker.isEmpty() ||
			null == (_id = id) || _id.isEmpty() ||
			!NumberUtil.IsValid (_price = price) ||
			!NumberUtil.IsValid (_size = size) ||
			null == (_side = side) ||
			null == (_time = time))
		{
			throw new Exception ("Trade Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Trade Ticker
	 * 
	 * @return Trade Ticker
	 */

	public String ticker()
	{
		return _ticker;
	}

	/**
	 * Retrieve the Trade ID
	 * 
	 * @return The Trade ID
	 */

	public String id()
	{
		return _id;
	}

	/**
	 * Retrieve the Trade Price
	 * 
	 * @return Trade Price
	 */

	public double price()
	{
		return _price;
	}

	/**
	 * Retrieve the Trade Size
	 * 
	 * @return Trade Size
	 */

	public double size()
	{
		return _size;
	}

	/**
	 * Retrieve the Trade Side
	 * 
	 * @return Trade Side
	 */

	public Side side()
	{
		return _side;
	}

	/**
	 * Retrieve the Trade Time
	 * 
	 * @return Trade Time
	 */

	public Date time()
	{
		return _time;
	}

	/**
	 * Retrieve the Trade Venue
	 * 
	 * @return Trade Venue
	 */

	public Venue venue()
	{
		return _venue;
	}

	/**
	 * Retrieve the Trade MV
	 * 
	 * @return Trade MV
	 */

	public double marketValue()
	{
		return _size * _price;
	}
}
