
package org.drip.oms.switchable;

import java.util.Date;

import org.drip.numerical.common.NumberUtil;
import org.drip.oms.depth.MontageL1Manager;
import org.drip.oms.exchange.CrossVenueMontageDigest;
import org.drip.oms.transaction.Order;
import org.drip.oms.transaction.OrderFillWholeSettings;
import org.drip.oms.transaction.OrderIssuer;
import org.drip.oms.transaction.OrderType;
import org.drip.oms.transaction.Side;
import org.drip.oms.transaction.TimeInForce;
import org.drip.oms.unthresholded.MarketOrder;
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
 * <i>StopOrder</i> holds the Details of a Stop Order. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/switchable/README.md">Implementation of Switchable Stop Order</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StopOrder
	extends Order
{
	private double _switchPrice = Double.NaN;

	/**
	 * Construct a Standard Instance of Stop Order
	 * 
	 * @param issuer Order Issuer
	 * @param ticker Security Identifier/Ticker
	 * @param side Order Side
	 * @param size Order Size
	 * @param timeInForce Time-in-Force Settings
	 * @param fillWholeSettings Order Fill-Whole Settings
	 * @param switchPrice Switch-to-Market Price
	 * 
	 * @return Standard Instance of Stop Order
	 */

	public static final StopOrder Standard (
		final OrderIssuer issuer,
		final String ticker,
		final Side side,
		final double size,
		final TimeInForce timeInForce,
		final OrderFillWholeSettings fillWholeSettings,
		final double switchPrice)
	{
		try
		{
			return new StopOrder (
				issuer,
				ticker,
				StringUtil.GUID(),
				new Date(),
				side,
				size,
				timeInForce,
				fillWholeSettings,
				switchPrice
			);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Stop Order Constructor
	 * 
	 * @param issuer Order Issuer
	 * @param ticker Security Identifier/Ticker
	 * @param id Order ID
	 * @param creationTime Creation Time
	 * @param side Order Side
	 * @param size Order Size
	 * @param timeInForce Time-in-Force Settings
	 * @param fillWholeSettings Order Fill-Whole Settings
	 * @param switchPrice Switch-to-Market Price
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public StopOrder (
		final OrderIssuer issuer,
		final String ticker,
		final String id,
		final Date creationTime,
		final Side side,
		final double size,
		final TimeInForce timeInForce,
		final OrderFillWholeSettings fillWholeSettings,
		final double switchPrice)
		throws Exception
	{
		super (
			issuer,
			ticker,
			id,
			OrderType.STOP,
			creationTime,
			side,
			size,
			timeInForce,
			fillWholeSettings
		);

		if (!NumberUtil.IsValid (
				_switchPrice = switchPrice
			) || 0. >=_switchPrice
		)
		{
			throw new Exception (
				"Stop Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Switch-to-Market Price
	 * 
	 * @return The Switch-to-Market Price
	 */

	public double switchPrice()
	{
		return _switchPrice;
	}

	/**
	 * Switch to Market Order based on the side and the L1 Montage
	 * 
	 * @param crossVenueMontageDigest The L1 Cross Venue Montage Digest
	 * 
	 * @return The Switched to Market Order (or NULL if not switch happens)
	 */

	public MarketOrder switchToMarket (
		final CrossVenueMontageDigest crossVenueMontageDigest)
	{
		if (null == crossVenueMontageDigest) {
			return null;
		}

		Side side = side();

		boolean buy = Side.BUY == side.buySell();

		MontageL1Manager montageL1Manager = buy ? crossVenueMontageDigest.bidL1MontageManagerMap().get (
			ticker()
		) : crossVenueMontageDigest.askL1MontageManagerMap().get (
			ticker()
		);

		if (null == montageL1Manager) {
			return null;
		}

		double topOfTheBookPrice = buy ? montageL1Manager.bidNBBOBlock().price() :
			montageL1Manager.askNBBOBlock().price();

		if (buy && topOfTheBookPrice > _switchPrice) {
			return null;
		}

		if (!buy && topOfTheBookPrice < _switchPrice) {
			return null;
		}

		try {
			return new MarketOrder (
				issuer(),
				ticker(),
				StringUtil.GUID(),
				new Date(),
				side(),
				size(),
				null,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public boolean isConditional()
	{
		return true;
	}

	@Override public Order generateChildOrder (
		final double filledSize)
	{
		return null;
	}
}
