
package org.drip.oms.depth;

import java.util.TreeMap;

import org.drip.oms.transaction.OrderBlock;

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
 * <i>OrderBlockL2</i> maintains a Deep Price Book for a Venue. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/depth/README.md">L1, L2, L3 Deep Books</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OrderBlockL2
{
	private boolean _descending = false;
	private TreeMap<Double, OrderBlock> _orderedBlockMap = null;

	/**
	 * Construct a Bid <i>OrderBlockL2</i> Price Book
	 * 
	 * @return Bid <i>OrderBlockL2</i> Price Book
	 */

	public static final OrderBlockL2 Bid()
	{
		try
		{
			return new OrderBlockL2 (
				false
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Ask <i>OrderBlockL2</i> Price Book
	 * 
	 * @return Ask <i>OrderBlockL2</i> Price Book
	 */

	public static final OrderBlockL2 Ask()
	{
		try
		{
			return new OrderBlockL2 (
				true
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>OrderBlockL2</i> Constructor
	 * 
	 * @param descending TRUE - Price Book is in Descending Order
	 * 
	 * @throws Exception Thrown if <i>OrderBlockL2</i> cannot be constructed
	 */

	public OrderBlockL2 (
		final boolean descending)
		throws Exception
	{
		_descending = descending;

		_orderedBlockMap = new TreeMap<Double, OrderBlock>();
	}

	/**
	 * Retrieve the Ordered Block Map
	 * 
	 * @return Ordered Block Map
	 */

	public TreeMap<Double, OrderBlock> orderedBlockMap()
	{
		return _orderedBlockMap;
	}

	/**
	 * Retrieve the Ascending/Descending Flag
	 * 
	 * @return TRUE - Price Book is in Descending Order
	 */

	public boolean descending()
	{
		return _descending;
	}

	/**
	 * Add a Posted Block to the Price Book
	 * 
	 * @param postedBlock The Posted Block to be added
	 * 
	 * @return The Posted Block successfully added to the L2 Price Book
	 */

	public boolean addBlock (
		final OrderBlock postedBlock)
	{
		if (null == postedBlock)
		{
			return false;
		}

		double postedPrice = postedBlock.price();

		if (_orderedBlockMap.containsKey (
			postedPrice
		))
		{
			if (!_orderedBlockMap.get (
					postedPrice
				).augmentSize (
					postedBlock.size()
				)
			)
			{
				return false;
			}
		}
		else
		{
			_orderedBlockMap.put (
				postedPrice,
				postedBlock
			);
		}

		return true;
	}

	/**
	 * Retrieve the Top of the Book
	 * 
	 * @return Top of the Book
	 */

	public OrderBlock topOfTheBook()
	{
		return _orderedBlockMap.isEmpty() ? null : _descending ?
			_orderedBlockMap.lastEntry().getValue() : _orderedBlockMap.firstEntry().getValue();
	}
}
