
package org.drip.oms.depth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
 * <i>MontageL1Manager</i> manages the Top-of-the Book L1 Montage across Venues for a single Ticker. The
 * 	References are:
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

public class MontageL1Manager
{
	private TreeMap<Double, MontageL1SizeLayer> _orderedAskBook = null;
	private TreeMap<Double, MontageL1SizeLayer> _orderedBidBook = null;

	/**
	 * Empty MontageL1Manager Constructor
	 */

	public MontageL1Manager()
	{
	}

	/**
	 * Retrieve the Ordered Bid Book
	 * 
	 * @return The Ordered Bid Book
	 */

	public TreeMap<Double, MontageL1SizeLayer> orderedBidBook()
	{
		return _orderedBidBook;
	}

	/**
	 * Retrieve the Ordered Ask Book
	 * 
	 * @return The Ordered Ask Book
	 */

	public TreeMap<Double, MontageL1SizeLayer> orderedAskBook()
	{
		return _orderedAskBook;
	}

	/**
	 * Add a Bid Venue L1 Montage Size Layer
	 * 
	 * @param montageL1SizeLayer Bid Venue L1 Montage Size Layer
	 * 
	 * @return TRUE - Successfully added the Bid Venue L1 Montage Size Layer to the Book
	 */

	public boolean addBidSizeLayer (
		final MontageL1SizeLayer montageL1SizeLayer)
	{
		if (null == montageL1SizeLayer)
		{
			return false;
		}

		try
		{
			_orderedBidBook.put (
				montageL1SizeLayer.price(),
				montageL1SizeLayer
			);
		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}

	/**
	 * Add the L1 Bid Entry to the Montage Manager
	 * 
	 * @param montageL1BidEntry L1 Montage Bid Entry
	 * 
	 * @return TRUE - The L1 Bid Entry successfully added to the Montage Manager
	 */

	public boolean addBidEntry (
		final MontageL1Entry montageL1BidEntry)
	{
		if (null == montageL1BidEntry)
		{
			return false;
		}

		double price = montageL1BidEntry.topOfTheBook().price();

		if (_orderedBidBook.containsKey (
			price
		))
		{
			_orderedBidBook.get (
				price
			).addEntry (
				montageL1BidEntry
			);
		}
		else
		{
			MontageL1SizeLayer montageL1SizeLayer = new MontageL1SizeLayer();

			montageL1SizeLayer.addEntry (
				montageL1BidEntry
			);

			_orderedBidBook.put (
				price,
				montageL1SizeLayer
			);
		}

		return true;
	}

	/**
	 * Add a Ask Venue L1 Montage Size Layer
	 * 
	 * @param montageL1SizeLayer Ask Venue L1 Montage Size Layer
	 * 
	 * @return TRUE - Successfully added the Ask Venue L1 Montage Size Layer to the Book
	 */

	public boolean addAskSizeLayer (
		final MontageL1SizeLayer montageL1SizeLayer)
	{
		if (null == montageL1SizeLayer)
		{
			return false;
		}

		try
		{
			_orderedAskBook.put (
				montageL1SizeLayer.price(),
				montageL1SizeLayer
			);
		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}

	/**
	 * Add the L1 Ask Entry to the Montage Manager
	 * 
	 * @param montageL1AskEntry L1 Montage Ask Entry
	 * 
	 * @return TRUE - The L1 Ask Entry successfully added to the Montage Manager
	 */

	public boolean addAskEntry (
		final MontageL1Entry montageL1AskEntry)
	{
		if (null == montageL1AskEntry)
		{
			return false;
		}

		double price = montageL1AskEntry.topOfTheBook().price();

		if (_orderedAskBook.containsKey (
			price
		))
		{
			_orderedAskBook.get (
				price
			).addEntry (
				montageL1AskEntry
			);
		}
		else
		{
			MontageL1SizeLayer montageL1SizeLayer = new MontageL1SizeLayer();

			montageL1SizeLayer.addEntry (
				montageL1AskEntry
			);

			_orderedAskBook.put (
				price,
				montageL1SizeLayer
			);
		}

		return true;
	}

	/**
	 * Retrieve the Ordered Ask Book List
	 * 
	 * @return The Ordered Ask Book List
	 */

	public List<MontageL1Entry> orderedAskBookList()
	{
		List<MontageL1Entry> montageL1EntryList = new ArrayList<MontageL1Entry>();

		if (_orderedAskBook.isEmpty())
		{
			return montageL1EntryList;
		}

		for (Map.Entry<Double, MontageL1SizeLayer> montageL1SizeLayerMapEntry : _orderedAskBook.entrySet())
		{
			for (Map.Entry<Double, List<MontageL1Entry>> montageL1EntryMapEntry :
				montageL1SizeLayerMapEntry.getValue().orderedEntryListMap().entrySet())
			{
				montageL1EntryList.addAll (
					montageL1EntryMapEntry.getValue()
				);
			}
		}

		return montageL1EntryList;
	}

	/**
	 * Retrieve the Ordered Bid Book List
	 * 
	 * @return The Ordered Bid Book List
	 */

	public List<MontageL1Entry> orderedBidBookList()
	{
		List<MontageL1Entry> montageL1EntryList = new ArrayList<MontageL1Entry>();

		if (_orderedBidBook.isEmpty())
		{
			return montageL1EntryList;
		}

		for (Map.Entry<Double, MontageL1SizeLayer> montageL1SizeLayerMapEntry : _orderedBidBook.entrySet())
		{
			for (Map.Entry<Double, List<MontageL1Entry>> montageL1EntryMapEntry :
				montageL1SizeLayerMapEntry.getValue().orderedEntryListMap().entrySet()
			)
			{
				montageL1EntryList.addAll (
					montageL1EntryMapEntry.getValue()
				);
			}
		}

		return montageL1EntryList;
	}

	/**
	 * Retrieve the NBBO Bid Block
	 * 
	 * @return The NBBO Bid Block
	 */

	public OrderBlock bidNBBOBlock()
	{
		return _orderedBidBook.isEmpty() ? null :
			_orderedBidBook.lastEntry().getValue().leadingBlockList().get (
				0
			).topOfTheBook();
	}

	/**
	 * Retrieve the NBBO Ask Block
	 * 
	 * @return The NBBO Ask Block
	 */

	public OrderBlock askNBBOBlock()
	{
		return _orderedAskBook.isEmpty() ? null :
			_orderedAskBook.firstEntry().getValue().leadingBlockList().get (
				0
			).topOfTheBook();
	}

	public double midPrice()
		throws Exception
	{
		OrderBlock bidNBBOBlock = bidNBBOBlock();

		if (null == bidNBBOBlock)
		{
			throw new Exception (
				"MontageL1Manager::midPrice => No Bid available"
			);
		}

		OrderBlock askNBBOBlock = askNBBOBlock();

		if (null == askNBBOBlock)
		{
			throw new Exception (
				"MontageL1Manager::midPrice => No Ask available"
			);
		}

		return 0.5 * (
			bidNBBOBlock.price() + askNBBOBlock.price()
		);
	}

	/**
	 * Retrieve the Bid UBBO Block
	 * 
	 * @return The Bid UBBO Block
	 */

	public UBBOBlock bidUBBOBlock()
	{
		return _orderedBidBook.isEmpty() ? null : _orderedBidBook.lastEntry().getValue().ubboBlock();
	}

	/**
	 * Retrieve the Ask UBBO Block
	 * 
	 * @return The Ask UBBO Block
	 */

	public UBBOBlock askUBBOBlock()
	{
		return _orderedAskBook.isEmpty() ? null : _orderedAskBook.firstEntry().getValue().ubboBlock();
	}
}
