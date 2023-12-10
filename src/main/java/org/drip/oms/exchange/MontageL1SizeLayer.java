
package org.drip.oms.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
 * <i>MontageL1SizeLayer</i> holds the Posted Blocks for a given Venue and a Price, ordered by Size. The
 *  References are:
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

public class MontageL1SizeLayer
{
	private TreeMap<Double, List<MontageL1Entry>> _orderedEntryListMap = null;

	/**
	 * MontageL1SizeLayer Constructor
	 */

	public MontageL1SizeLayer()
	{
		_orderedEntryListMap = new TreeMap<Double, List<MontageL1Entry>>();
	}

	/**
	 * Retrieve the Ordered L1 Entry Map
	 * 
	 * @return Ordered L1 Entry Map
	 */

	public TreeMap<Double, List<MontageL1Entry>> orderedEntryListMap()
	{
		return _orderedEntryListMap;
	}

	/**
	 * Add the L1 Montage Entry
	 * 
	 * @param montageL1Entry The L1 Montage Entry
	 * 
	 * @return TRUE - The L1 Montage Entry successfully added
	 */

	public boolean addEntry (
		final MontageL1Entry montageL1Entry)
	{
		if (null == montageL1Entry)
		{
			return false;
		}

		double size = montageL1Entry.topOfTheBook().size();

		if (_orderedEntryListMap.containsKey (
			size
		))
		{
			_orderedEntryListMap.get (
				size
			).add (
				montageL1Entry
			);
		}
		else
		{
			List<MontageL1Entry> montageL1EntryList = new ArrayList<MontageL1Entry>();

			montageL1EntryList.add (
				montageL1Entry
			);

			_orderedEntryListMap.put (
				size,
				montageL1EntryList
			);
		}

		return true;
	}

	/**
	 * Retrieve the Price of the Montage Layer
	 * 
	 * @return Price of the Montage Layer
	 * 
	 * @throws Exception Thrown if the MontageL1SizeLayer is Empty
	 */

	public double price()
		throws Exception
	{
		if (_orderedEntryListMap.isEmpty())
		{
			throw new Exception (
				"MontageL1SizeLayer::price => Empty Container"
			);
		}

		return _orderedEntryListMap.firstEntry().getValue().get (
			0
		).topOfTheBook().price();
	}

	/**
	 * Retrieve the Peak Size of the Montage Layer
	 * 
	 * @return Peak Size of the Montage Layer
	 * 
	 * @throws Exception Thrown if the MontageL1SizeLayer is Empty
	 */

	public double peak()
		throws Exception
	{
		if (_orderedEntryListMap.isEmpty())
		{
			throw new Exception (
				"MontageL1SizeLayer::peak => Empty Container"
			);
		}

		return _orderedEntryListMap.lastEntry().getValue().get (
			0
		).topOfTheBook().size();
	}

	/**
	 * Retrieve the List of the Peak Blocks of the Montage Layer
	 * 
	 * @return List of the Peak Blocks of the Montage Layer
	 */

	public List<MontageL1Entry> peakBlockList()
	{
		return _orderedEntryListMap.isEmpty() ? null : _orderedEntryListMap.lastEntry().getValue();
	}

	/**
	 * Retrieve the Aggregated Size of the Montage Layer
	 * 
	 * @return Aggregated Size of the Montage Layer
	 * 
	 * @throws Exception Thrown if the MontageL1SizeLayer is Empty
	 */

	public double aggregate()
		throws Exception
	{
		if (_orderedEntryListMap.isEmpty())
		{
			throw new Exception (
				"MontageL1SizeLayer::aggregate => Empty Container"
			);
		}

		double aggregate = 0;

		for (List<MontageL1Entry> montageL1EntryList : _orderedEntryListMap.values())
		{
			for (MontageL1Entry montageL1Entry : montageL1EntryList)
			{
				aggregate += montageL1Entry.topOfTheBook().size();
			}
		}

		return aggregate;
	}
}
