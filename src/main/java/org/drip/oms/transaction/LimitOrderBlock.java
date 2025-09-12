
package org.drip.oms.transaction;

import java.time.ZonedDateTime;
import java.util.Comparator;

import org.drip.numerical.common.NumberUtil;
import org.drip.service.common.FormatUtil;

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
 * <i>LimitOrderBlock</i> maintains a Limit Entry Block inside an Order Book. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/transaction/README.md">Order Specification and Session Metrics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LimitOrderBlock
	extends OrderBlock
	implements Comparator<LimitOrderBlock>, Cloneable
{
	private double _price = Double.NaN;

	/**
	 * Construct a Fresh Instance of the <i>LimitOrderBlock</i>
	 * 
	 * @param price L2 Price
	 * @param size L2 Size
	 * 
	 * @return Fresh Instance of the <i>LimitOrderBlock</i>
	 */

	public static LimitOrderBlock Now (
		final double price,
		final double size)
	{
		try
		{
			return new LimitOrderBlock (
				ZonedDateTime.now(),
				price,
				size
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>LimitOrderBlock</i> Constructor
	 * 
	 * @param lastUpdateTime Last Update Time
	 * @param price L2 Price
	 * @param size L2 Size
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public LimitOrderBlock (
		final ZonedDateTime lastUpdateTime,
		final double price,
		final double size)
		throws Exception
	{
		super (lastUpdateTime, size);

		if (!NumberUtil.IsValid (_price = price)) {
			throw new Exception ("LimitOrderBlock Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Price
	 * 
	 * @return The Price
	 */

	public double price()
	{
		return _price;
	}

	/**
	 * Compare Two Limit Order Block Instances
	 * 
	 * @param l2Block1 First Limit Order Block
	 * @param l2Block2 Second Limit Order Block
	 * 
	 * @return Follows <code>Comparable.compare</code> Semantics
	 */

	@Override public int compare (
		final LimitOrderBlock l2Block1,
		final LimitOrderBlock l2Block2)
	{
		if (null == l2Block1 && null == l2Block2) {
			return 0;
		}

		if (null == l2Block1 && null != l2Block2) {
			return -1;
		}

		if (null != l2Block1 && null == l2Block2) {
			return 1;
		}

		return l2Block1._price < l2Block2._price ? -1 : 1;
	}

	/**
	 * Clone this Instance
	 * 
	 * @return Follows <code>Object.clone</code> Semantics
	 */

	@Override public LimitOrderBlock clone()
	{
		try {
			return new LimitOrderBlock (ZonedDateTime.now(), _price, size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
		return "\n" + pad + "Limit Order Block: [" +
			"\n" + pad + "\t" +
			"Price => " + FormatUtil.FormatDouble (_price, 0, 0, 1.) + "; " +
			"Size => " + super.toString (pad + "\t") +
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
