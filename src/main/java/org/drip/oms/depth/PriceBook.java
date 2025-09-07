
package org.drip.oms.depth;

import java.util.TreeMap;

import org.drip.oms.transaction.LimitOrderBlock;

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
 * <i>PriceBook</i> maintains the Ordered Price Book Entry for a Ticker/Venue. The References are:
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

public class PriceBook
{
	private TreeMap<Double, LimitOrderBlock> _aggregatedPostedBlockMap = null;

	/**
	 * PriceBook Constructor
	 */

	public PriceBook()
	{
		_aggregatedPostedBlockMap = new TreeMap<Double, LimitOrderBlock>();
	}

	/**
	 * Retrieve the Aggregated Posted Block Price Map
	 * 
	 * @return The Aggregated Posted Block Price Map
	 */

	public TreeMap<Double, LimitOrderBlock> aggregatedPostedBlockMap()
	{
		return _aggregatedPostedBlockMap;
	}

	/**
	 * Aggregate a Posted Block to the Price Book
	 * 
	 * @param postedBlock Posted Block
	 * 
	 * @return TRUE - The Posted Block successfully aggregated to the Price Book
	 */

	public boolean aggregatePostedBlock (
		final LimitOrderBlock postedBlock)
	{
		if (null == postedBlock)
		{
			return false;
		}

		double price = postedBlock.price();

		if (_aggregatedPostedBlockMap.containsKey (
			price
		))
		{
			_aggregatedPostedBlockMap.get (
				price
			).augmentSize (
				postedBlock.size()
			);
		}
		else
		{
			_aggregatedPostedBlockMap.put (
				price,
				postedBlock.clone()
			);
		}

		return true;
	}

	/**
	 * Disaggregate a Swept Block to the Price Book
	 * 
	 * @param sweptBlock Swept Block
	 * @param allowPartialSweep TRUE - Partial Sweep is allowed
	 * 
	 * @return TRUE - The Swept Block successfully disaggregated to the Price Book
	 */

	public boolean disaggregateSweptBlock (
		final LimitOrderBlock sweptBlock,
		final boolean allowPartialSweep)
	{
		if (null == sweptBlock)
		{
			return false;
		}

		double price = sweptBlock.price();

		if (!_aggregatedPostedBlockMap.containsKey (
			price
		))
		{
			return false;
		}

		double sweptBlockSize = sweptBlock.size();

		LimitOrderBlock postedBlock = _aggregatedPostedBlockMap.get (
			price
		);

		double sizeRemaining = postedBlock.size() - sweptBlockSize;

		if (0. == sizeRemaining || (
			0. > sizeRemaining && allowPartialSweep
		))
		{
			_aggregatedPostedBlockMap.remove (
				price
			);
		}
		else
		{
			postedBlock.augmentSize (
				-1. * sweptBlockSize
			);
		}

		return true;
	}

	/**
	 * Retrieve the Top-of-the-Book
	 * 
	 * @param bid TRUE - Top-of-the-Bid-Book
	 * 
	 * @return The Top-of-the-Book
	 */

	public LimitOrderBlock topOfTheBook (
		final boolean bid)
	{
		return _aggregatedPostedBlockMap.isEmpty() ? null : bid ?
			_aggregatedPostedBlockMap.lastEntry().getValue() :
			_aggregatedPostedBlockMap.firstEntry().getValue();
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
		return "\n" + pad + "Order Block: [" +
			"\n" + pad + "\t" +
			"Aggregated Posted Block Map => " + _aggregatedPostedBlockMap +
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
