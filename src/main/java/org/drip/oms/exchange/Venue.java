
package org.drip.oms.exchange;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.drip.oms.depth.MontageL1Entry;
import org.drip.oms.depth.PriceBook;
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
 * <i>Venue</i> implements Functionality corresponding to a Venue. The References are:
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

public class Venue
{
	private VenueSettings _settings = null;
	private Map<String, PriceBook> _askTickerPriceBookMap = null;
	private Map<String, PriceBook> _bidTickerPriceBookMap = null;

	/**
	 * Venue Constructor
	 * 
	 * @param settings Venue Settings
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public Venue (
		final VenueSettings settings)
		throws Exception
	{
		if (null == (_settings = settings))
		{
			throw new Exception (
				"Venue Constructor => Invalid Inputs"
			);
		}

		_askTickerPriceBookMap = new HashMap<String, PriceBook>();

		_bidTickerPriceBookMap = new HashMap<String, PriceBook>();
	}

	/**
	 * Retrieve the Venue Settings
	 * 
	 * @return The Venue Settings
	 */

	public VenueSettings settings()
	{
		return _settings;
	}

	/**
	 * Retrieve the Bid Price Book per Ticker
	 * 
	 * @return Bid Price Book per Ticker
	 */

	public Map<String, PriceBook> bidTickerPriceBookMap()
	{
		return _bidTickerPriceBookMap;
	}

	/**
	 * Retrieve the Ask Price Book per Ticker
	 * 
	 * @return Ask Price Book per Ticker
	 */

	public Map<String, PriceBook> askTickerPriceBookMap()
	{
		return _askTickerPriceBookMap;
	}

	/**
	 * Estimate Liquidity Posting Fee for the specified Ticker at the Venue at the Price/Size.
	 * 
	 * @param ticker Ticker
	 * @param price Price
	 * @param size Size
	 * 
	 * @return Fee for Liquidity Posting
	 * 
	 * @throws Exception Thrown if the Liquidity Posting Fee cannot be calculated
	 */

	public double postFee (
		final String ticker,
		final double price,
		final double size)
		throws Exception
	{
		return _settings.pricingRebateFunction().makerFee (
			ticker,
			price,
			size
		);
	}

	/**
	 * Estimate Liquidity Sweeping Fee for the specified Ticker at the Venue at the Price/Size.
	 * 
	 * @param ticker Ticker
	 * @param price Price
	 * @param size Size
	 * 
	 * @return Fee for Liquidity Sweeping
	 * 
	 * @throws Exception Thrown if the Liquidity Sweeping Fee cannot be calculated
	 */

	public double sweepFee (
		final String ticker,
		final double price,
		final double size)
		throws Exception
	{
		return _settings.pricingRebateFunction().makerFee (
			ticker,
			price,
			size
		);
	}

	/**
	 * Post a Block to the Venue Bid Book for the Ticker
	 * 
	 * @param ticker The Ticker
	 * @param postedBlock The Posted Block
	 * 
	 * @return TRUE - The Block posted to the Venue Bid Book for the Ticker
	 */

	public boolean postBidBlock (
		final String ticker,
		final LimitOrderBlock postedBlock)
	{
		if (null == ticker || ticker.isEmpty() ||
			null == postedBlock
		)
		{
			return false;
		}

		if (!_bidTickerPriceBookMap.containsKey (
			ticker
		))
		{
			_bidTickerPriceBookMap.put (
				ticker,
				new PriceBook()
			);
		}

		return _bidTickerPriceBookMap.get (
			ticker
		).aggregatePostedBlock (
			postedBlock
		);
	}

	/**
	 * Post a Block to the Venue Ask Book for the Ticker
	 * 
	 * @param ticker The Ticker
	 * @param postedBlock The Posted Block
	 * 
	 * @return TRUE - The Block posted to the Venue Ask Book for the Ticker
	 */

	public boolean postAskBlock (
		final String ticker,
		final LimitOrderBlock postedBlock)
	{
		if (null == ticker || ticker.isEmpty() ||
			null == postedBlock
		)
		{
			return false;
		}

		if (!_askTickerPriceBookMap.containsKey (
			ticker
		))
		{
			_askTickerPriceBookMap.put (
				ticker,
				new PriceBook()
			);
		}

		return _askTickerPriceBookMap.get (
			ticker
		).aggregatePostedBlock (
			postedBlock
		);
	}

	/**
	 * Sweep a Block to the Venue Bid Book for the Ticker
	 * 
	 * @param ticker The Ticker
	 * @param sweptBlock The Swept Block
	 * @param allowPartialSweep TRUE - Partial Sweep is allowed
	 * 
	 * @return TRUE - The Block Swept to the Venue Bid Book for the Ticker
	 */

	public boolean sweepBidBlock (
		final String ticker,
		final LimitOrderBlock sweptBlock,
		final boolean allowPartialSweep)
	{
		return null != ticker && !ticker.isEmpty() &&
			null != sweptBlock &&
			_bidTickerPriceBookMap.containsKey (
				ticker
			) && _bidTickerPriceBookMap.get (
				ticker
			).disaggregateSweptBlock (
				sweptBlock,
				allowPartialSweep
			);
	}

	/**
	 * Sweep a Block to the Venue Ask Book for the Ticker
	 * 
	 * @param ticker The Ticker
	 * @param sweptBlock The Swept Block
	 * @param allowPartialSweep TRUE - Partial Sweep is allowed
	 * 
	 * @return TRUE - The Block Swept to the Venue Ask Book for the Ticker
	 */

	public boolean sweepAskBlock (
		final String ticker,
		final LimitOrderBlock sweptBlock,
		final boolean allowPartialSweep)
	{
		return null != ticker && !ticker.isEmpty() &&
			null != sweptBlock &&
			_askTickerPriceBookMap.containsKey (
				ticker
			) && _askTickerPriceBookMap.get (
				ticker
			).disaggregateSweptBlock (
				sweptBlock,
				allowPartialSweep
			);
	}

	/**
	 * Retrieve the Top-of-the-Bid-Book for the specified Ticker
	 * 
	 * @param ticker Ticker
	 * 
	 * @return The Top-of-the-Bid-Book
	 */

	public LimitOrderBlock topOfTheBidBook (
		final String ticker)
	{
		return null != ticker && !ticker.isEmpty() && _bidTickerPriceBookMap.containsKey (
			ticker
		) ? _bidTickerPriceBookMap.get (
			ticker
		).topOfTheBook (
			true
		) : null;
	}

	/**
	 * Retrieve the Top-of-the-Ask-Book for the specified Ticker
	 * 
	 * @param ticker Ticker
	 * 
	 * @return The Top-of-the-Ask-Book
	 */

	public LimitOrderBlock topOfTheAskBook (
		final String ticker)
	{
		return null != ticker && !ticker.isEmpty() && _askTickerPriceBookMap.containsKey (
			ticker
		) ? _askTickerPriceBookMap.get (
			ticker
		).topOfTheBook (
			false
		) : null;
	}

	/**
	 * Retrieve the Bid L1 Montage Entry for the specified Ticker
	 * 
	 * @param ticker Ticker
	 * 
	 * @return The Bid L1 Montage Entry
	 */

	public MontageL1Entry bidMontageL1Entry (
		final String ticker)
	{
		LimitOrderBlock topOfTheBidBook = topOfTheBidBook (
			ticker
		);

		try {
			return null == topOfTheBidBook ? null : new MontageL1Entry (
				settings().code(),
				topOfTheBidBook
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Ask L1 Montage Entry for the specified Ticker
	 * 
	 * @param ticker Ticker
	 * 
	 * @return The Ask L1 Montage Entry
	 */

	public MontageL1Entry askMontageL1Entry (
		final String ticker)
	{
		LimitOrderBlock topOfTheAskBook = topOfTheAskBook (
			ticker
		);

		try {
			return null == topOfTheAskBook ? null : new MontageL1Entry (
				settings().code(),
				topOfTheAskBook
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Bid Ticker Set
	 * 
	 * @return The Bid Ticker Set
	 */

	public Set<String> bidTickerSet()
	{
		return _bidTickerPriceBookMap.keySet();
	}

	/**
	 * Retrieve the Ask Ticker Set
	 * 
	 * @return The Ask Ticker Set
	 */

	public Set<String> askTickerSet()
	{
		return _askTickerPriceBookMap.keySet();
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
		return "\n" + pad + "Agent: [" +
			"\n" + pad + "\t" +
			"Settings => " + _settings.toString (pad + "\t") + "; " +
			"Bid Ticker Price Book Map => " + _bidTickerPriceBookMap + "; " +
			"Ask Ticker Price Book Map => " + _askTickerPriceBookMap +
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
