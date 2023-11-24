
package org.drip.oms.specification;

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
 * <i>OrderType</i> holds the different Types of Orders. The References are:
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
 * 			Vassilis, P. (2005a): A Realistic Model of Market Liquidity and Depth <i>Journal of Futures
 * 				Markets</i> <b>25 (5)</b> 443-464
 * 		</li>
 * 		<li>
 * 			Vassilis, P. (2005b): Slow and Fast Markets <i>Journal of Economics and Business</i> <b>57
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/specification/README.md">Order Specification and Session Metrics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OrderType
{

	/**
	 * Market Order
	 */

	public static final int MARKET = 1;

	/**
	 * Limit Order
	 */

	public static final int LIMIT = 2;

	/**
	 * Day Order
	 */

	public static final int DAY = 3;

	/**
	 * Good for Day Order
	 */

	public static final int GOOD_FOR_DAY = 4;

	/**
	 * Good till Canceled Order
	 */

	public static final int GOOD_TILL_CANCELED = 5;

	/**
	 * Immediate or Canceled Order
	 */

	public static final int IMMEDIATE_OR_CANCELED = 6;

	/**
	 * On the Close Order
	 */

	public static final int ON_THE_CLOSE = 7;

	/**
	 * On the Open Order
	 */

	public static final int ON_THE_OPEN = 8;

	/**
	 * Market on Close Order
	 */

	public static final int MARKET_ON_CLOSE = 9;

	/**
	 * Market On Open Order
	 */

	public static final int MARKET_ON_OPEN = 10;

	/**
	 * Limit-on-close Order
	 */

	public static final int LIMIT_ON_CLOSE = 11;

	/**
	 * Limit-on-open Order
	 */

	public static final int LIMIT_ON_OPEN = 12;

	/**
	 * Stop Order
	 */

	public static final int STOP = 13;

	/**
	 * Stop-Loss Order
	 */

	public static final int STOP_LOSS = 14;

	/**
	 * Sell-stop Order
	 */

	public static final int SELL_STOP = 15;

	/**
	 * Buy-stop Order
	 */

	public static final int BUY_STOP = 16;

	/**
	 * Stop-limit Order
	 */

	public static final int STOP_LIMIT = 17;

	/**
	 * Trailing Stop Order
	 */

	public static final int TRAILING_STOP = 18;

	/**
	 * Trailing Stop Limit Order
	 */

	public static final int TRAILING_STOP_LIMIT = 19;

	/**
	 * Peg Order
	 */

	public static final int PEG = 20;

	/**
	 * Peg Best Order
	 */

	public static final int PEG_BEST = 21;

	/**
	 * Mid-price Peg Order
	 */

	public static final int MID_PRICE_PEG = 22;

	/**
	 * Buy Market-if-touched Order
	 */

	public static final int BUY_MARKET_IF_TOUCHED = 23;

	/**
	 * Sell Market-if-touched Order
	 */

	public static final int SELL_MARKET_IF_TOUCHED = 24;

	/**
	 * One Cancels Other Order
	 */

	public static final int ONE_CANCELS_OTHER = 25;

	/**
	 * One Sends Other Order
	 */

	public static final int ONE_SENDS_OTHER_ORDER = 26;

	/**
	 * Tick Sensitive Order
	 */

	public static final int TICK_SENSITIVE = 27;

	/**
	 * At the Opening Order
	 */

	public static final int AT_THE_OPENING = 28;

	/**
	 * Discretionary Order
	 */

	public static final int DISCRETIONARY = 29;

	/**
	 * Bracket Order
	 */

	public static final int BRACKET = 30;

	/**
	 * Hidden Order
	 */

	public static final int HIDDEN = 31;

	/**
	 * Iceberg Order
	 */

	public static final int ICEBERG = 32;
}
