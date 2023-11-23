
package org.drip.oms.specification;

import java.util.Date;

import org.drip.service.common.StringUtil;

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
 * <i>MarketOrder</i> holds the Details of a Market Order. The References are:
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

public class MarketOrder
	extends Order
{

	/**
	 * Construct a Standard Instance of MarketOrder
	 * 
	 * @param issuer Order Issuer
	 * @param securityIdentifier Security Identifier
	 * @param side Order Side
	 * @param size Order Size
	 * @param fillWholeSettings Order Fill-Whole Settings
	 * 
	 * @return Standard Instance of MarketOrder
	 */

	public static final MarketOrder Standard (
		final OrderIssuer issuer,
		final String securityIdentifier,
		final String side,
		final double size,
		final OrderFillWholeSettings fillWholeSettings)
	{
		try
		{
			return new MarketOrder (
				issuer,
				securityIdentifier,
				StringUtil.GUID(),
				new Date(),
				side,
				size,
				fillWholeSettings
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of Buy MarketOrder
	 * 
	 * @param issuer Order Issuer
	 * @param securityIdentifier Security Identifier
	 * @param size Order Size
	 * @param fillWholeSettings Order Fill-Whole Settings
	 * 
	 * @return Standard Instance of Buy MarketOrder
	 */

	public static final MarketOrder StandardBuy (
		final OrderIssuer issuer,
		final String securityIdentifier,
		final double size,
		final OrderFillWholeSettings fillWholeSettings)
	{
		try
		{
			return new MarketOrder (
				issuer,
				securityIdentifier,
				StringUtil.GUID(),
				new Date(),
				Order.BUY,
				size,
				fillWholeSettings
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of Buy Fill-or-Kill MarketOrder
	 * 
	 * @param issuer Order Issuer
	 * @param securityIdentifier Security Identifier
	 * @param size Order Size
	 * 
	 * @return Standard Instance of Buy Fill-or-Kill MarketOrder
	 */

	public static final MarketOrder StandardBuyFillOrKill (
		final OrderIssuer issuer,
		final String securityIdentifier,
		final double size)
	{
		try
		{
			return new MarketOrder (
				issuer,
				securityIdentifier,
				StringUtil.GUID(),
				new Date(),
				Order.BUY,
				size,
				OrderFillWholeSettings.FillOrKill()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of Buy All-or-None MarketOrder
	 * 
	 * @param issuer Order Issuer
	 * @param securityIdentifier Security Identifier
	 * @param size Order Size
	 * @param fulfillTryLimit Fulfill Try Limit
	 * 
	 * @return Standard Instance of Buy All-or-None MarketOrder
	 */

	public static final MarketOrder StandardBuyAllOrNone (
		final OrderIssuer issuer,
		final String securityIdentifier,
		final double size,
		final int fulfillTryLimit)
	{
		try
		{
			return new MarketOrder (
				issuer,
				securityIdentifier,
				StringUtil.GUID(),
				new Date(),
				Order.BUY,
				size,
				OrderFillWholeSettings.AllOrNone (
					fulfillTryLimit
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of Sell MarketOrder
	 * 
	 * @param issuer Order Issuer
	 * @param securityIdentifier Security Identifier
	 * @param size Order Size
	 * @param fillWholeSettings Order Fill-Whole Settings
	 * 
	 * @return Standard Instance of Sell MarketOrder
	 */

	public static final MarketOrder StandardSell (
		final OrderIssuer issuer,
		final String securityIdentifier,
		final double size,
		final OrderFillWholeSettings fillWholeSettings)
	{
		try
		{
			return new MarketOrder (
				issuer,
				securityIdentifier,
				StringUtil.GUID(),
				new Date(),
				Order.SELL,
				size,
				fillWholeSettings
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of Sell Fill-or-Kill MarketOrder
	 * 
	 * @param issuer Order Issuer
	 * @param securityIdentifier Security Identifier
	 * @param size Order Size
	 * 
	 * @return Standard Instance of Sell Fill-or-Kill MarketOrder
	 */

	public static final MarketOrder StandardSellFillOrKill (
		final OrderIssuer issuer,
		final String securityIdentifier,
		final double size)
	{
		try
		{
			return new MarketOrder (
				issuer,
				securityIdentifier,
				StringUtil.GUID(),
				new Date(),
				Order.SELL,
				size,
				OrderFillWholeSettings.FillOrKill()
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of Sell All-or-None MarketOrder
	 * 
	 * @param issuer Order Issuer
	 * @param securityIdentifier Security Identifier
	 * @param size Order Size
	 * @param fulfillTryLimit Fulfill Try Limit
	 * 
	 * @return Standard Instance of Sell All-or-None MarketOrder
	 */

	public static final MarketOrder StandardSellAllOrNone (
		final OrderIssuer issuer,
		final String securityIdentifier,
		final double size,
		final int fulfillTryLimit)
	{
		try
		{
			return new MarketOrder (
				issuer,
				securityIdentifier,
				StringUtil.GUID(),
				new Date(),
				Order.SELL,
				size,
				OrderFillWholeSettings.AllOrNone (
					fulfillTryLimit
				)
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarketOrder Constructor
	 * 
	 * @param issuer Order Issuer
	 * @param securityIdentifier Security Identifier
	 * @param id Order ID
	 * @param creationTime Creation Time
	 * @param side Order Side
	 * @param size Order Size
	 * @param fillWholeSettings Order Fill-Whole Settings
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public MarketOrder (
		final OrderIssuer issuer,
		final String securityIdentifier,
		final String id,
		final Date creationTime,
		final String side,
		final double size,
		final OrderFillWholeSettings fillWholeSettings)
		throws Exception
	{
		super (
			issuer,
			securityIdentifier,
			id,
			OrderType.MARKET,
			creationTime,
			side,
			size,
			fillWholeSettings
		);
	}
}
