
package org.drip.oms.fix4_2;

import java.util.Date;
import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.oms.exchange.VenueHandler;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>Agent</i> implements the FIX Agent Handler. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/fix4_2/README.md">Implementation of FIX 4.2 Constructs</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Agent
{
	private DeskHandler _deskHandler = null;
	private VenueHandler _venueHandler = null;
	private Map<String, AgentOrder> _fixOrderMap = null;

	protected AgentResponse clientNEW (
		final Date processingStartTime,
		final AgentRequest agentRequest)
	{
		AgentOrder fixOrder = AgentOrder.FromAgentRequest (agentRequest);

		if (null == fixOrder) {
			return null;
		}

		_fixOrderMap.put (fixOrder.clOrdID(), fixOrder);

		if (null != _deskHandler && !_deskHandler.process (agentRequest)) {
			return fixOrder.reject() ?
				AgentResponse.REJECTED (processingStartTime, fixOrder.order(), agentRequest.id()) : null;
		}

		return fixOrder.accept() ?
			AgentResponse.ACCEPTED (processingStartTime, fixOrder.order(), agentRequest.id()) : null;
	}

	/**
	 * Construct a FIX <i>Agent</i> Instance
	 * 
	 * @param venueHandler Venue Handler
	 * @param deskHandler Desk Handler
	 */

	public Agent (
		final VenueHandler venueHandler,
		final DeskHandler deskHandler)
		throws Exception
	{
		if (null == (_venueHandler = venueHandler)) {
			throw new Exception ("Agent Constructor => Invalid Inputs");
		}

		_deskHandler = deskHandler;

		_fixOrderMap = new CaseInsensitiveHashMap<AgentOrder>();
	}

	/**
	 * Retrieve the Venue Handler Instance
	 * 
	 * @return Venue Handler Instance
	 */

	public VenueHandler venueHandler()
	{
		return _venueHandler;
	}

	/**
	 * Retrieve the <i>DeskHandler</i> Instance
	 * 
	 * @return <i>DeskHandler</i> Instance
	 */

	public DeskHandler deskHandler()
	{
		return _deskHandler;
	}

	/**
	 * Retrieve the FIX Order Map
	 * 
	 * @return FIX Order Map
	 */

	public Map<String, AgentOrder> fixOrderMap()
	{
		return _fixOrderMap;
	}

	/**
	 * Handle the FIX Client Request
	 * 
	 * @param agentRequest Agent Request
	 * 
	 * @return Response to the Request
	 */

	public AgentResponse handleClientRequest (
		final AgentRequest agentRequest)
	{
		Date processingStartTime = new Date();

		if (null == agentRequest) {
			return null;
		}

		if (AgentRequestType.NEW == agentRequest.type()) {
			return clientNEW (processingStartTime, agentRequest);
		}

		return null;
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
			"Venue Handler => " + _venueHandler.toString (pad + "\t") + "; " +
			"Desk Handler => " + _deskHandler.toString (pad + "\t") + "; " +
			"Fix Order Map => " + _fixOrderMap +
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
