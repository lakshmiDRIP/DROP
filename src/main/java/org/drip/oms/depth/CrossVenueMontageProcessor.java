
package org.drip.oms.depth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drip.oms.exchange.Venue;

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
 * <i>CrossVenueMontageProcessor</i> compiles and processes cross-Venue Montage Functionality. The References
 *  are:
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

public class CrossVenueMontageProcessor
{
	private Set<String> _askTickerSet = null;
	private Set<String> _bidTickerSet = null;
	private Map<String, Venue> _l1Container = null;

	/**
	 * Empty CrossVenueMontageProcessor Constructor
	 */

	public CrossVenueMontageProcessor (
		final List<Venue> venueList)
		throws Exception
	{
		if (null == venueList || 0 == venueList.size())
		{
			throw new Exception (
				"CrossVenueMontageProcessor Contructor => Invalid Inputs"
			);
		}

		_l1Container = new HashMap<String, Venue>();

		_bidTickerSet = new HashSet<String>();

		_askTickerSet = new HashSet<String>();

		for (Venue venue : venueList)
		{
			_l1Container.put (
				venue.settings().code(),
				venue
			);

			_bidTickerSet.addAll (
				venue.bidTickerSet()
			);

			_askTickerSet.addAll (
				venue.askTickerSet()
			);
		}
	}

	/**
	 * Retrieve the Venue L1 Container
	 * 
	 * @return The Venue L1 Container
	 */

	public Map<String, Venue> l1Container()
	{
		return _l1Container;
	}

	/**
	 * Retrieve the Bid Ticker Set
	 * 
	 * @return The Bid Ticker Set
	 */

	public Set<String> bidTickerSet()
	{
		return _bidTickerSet;
	}

	/**
	 * Retrieve the Ask Ticker Set
	 * 
	 * @return The Ask Ticker Set
	 */

	public Set<String> askTickerSet()
	{
		return _askTickerSet;
	}

	private boolean updateL1MontageManagerMap (
		final Venue venue,
		final Set<String> tickerSet,
		final Set<String> venueMontageTickerSet,
		final Map<String, MontageL1Manager> l1MontageManagerMap,
		final boolean bid)
	{
		for (String venueMontageTicker : venueMontageTickerSet) {
			MontageL1Entry montageL1Entry = bid ? venue.bidMontageL1Entry (
				venueMontageTicker
			) : venue.askMontageL1Entry (
				venueMontageTicker
			);

			if (null == montageL1Entry) {
				continue;
			}

			if (!tickerSet.contains (
				venueMontageTicker
			))
			{
				MontageL1Manager montageL1Manager = new MontageL1Manager();

				if (bid) {
					montageL1Manager.addBidEntry (
						montageL1Entry
					);
				} else {
					montageL1Manager.addAskEntry (
						montageL1Entry
					);
				}

				l1MontageManagerMap.put (
					venueMontageTicker,
					montageL1Manager
				);
			} else {
				l1MontageManagerMap.get (
					venueMontageTicker
				).addBidEntry (
					montageL1Entry
				);
			}
		}

		return true;
	}

	public CrossVenueMontageDigest munge()
	{
		Map<String, MontageL1Manager> tickerMontageL1ManagerMap = new HashMap<String, MontageL1Manager>();

		for (Map.Entry<String, Venue> l1ContainerMapEntry : _l1Container.entrySet()) {
			Venue venue = l1ContainerMapEntry.getValue();

			updateL1MontageManagerMap (
				venue,
				_bidTickerSet,
				venue.bidTickerSet(),
				tickerMontageL1ManagerMap,
				true
			);

			updateL1MontageManagerMap (
				venue,
				_askTickerSet,
				venue.askTickerSet(),
				tickerMontageL1ManagerMap,
				false
			);
		};

		try {
			return new CrossVenueMontageDigest (
				tickerMontageL1ManagerMap
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
