
package org.drip.oms.venue;

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
 * <i>MontageL1</i> holds the Top-of-the Book L1 across Venues. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/venue/README.md">Implementation of Venue Order Handling</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MontageL1
{
	private TreeMap<Double, MontageL1SizeLayer> _orderedAskBook = null;
	private TreeMap<Double, MontageL1SizeLayer> _orderedBidBook = null;

	/**
	 * Empty MontageL1 Constructor
	 */

	public MontageL1()
	{
	}

	/**
	 * Retrieve the Ordered Bid Book
	 * 
	 * @return The Ordered Bid Book
	 */

	public TreeMap<Double, MontageL1SizeLayer> _orderedBidBook()
	{
		return _orderedBidBook;
	}

	/**
	 * Retrieve the Ordered Ask Book
	 * 
	 * @return The Ordered Ask Book
	 */

	public TreeMap<Double, MontageL1SizeLayer> _orderedAskBook()
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
}
