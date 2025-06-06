
package org.drip.execution.cost;

import org.drip.numerical.common.NumberUtil;
import org.drip.oms.transaction.Trade;

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
 * <i>ImplementationShortfall</i> implements the Implementation Shortfall/Slippage associated with a Trade.
 *  The References are:
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
 * 			Jacob, B. (2024): <i>7 Execution Algorithms You Should Know About�.</i>
 * 				https://www.linkedin.com/pulse/7-execution-algorithms-you-should-know-benjamin-jacob-vl7pf/
 * 		</li>
 * 		<li>
 * 			Vassilis, P. (2005): Slow and Fast Markets <i>Journal of Economics and Business</i> <b>57
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/oms/benchmark/README.md">Benchmark/Tie/Peg Price Thresholds</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ImplementationShortfall
{
	private Trade _trade = null;
	private double _decisionPrice = Double.NaN;
	private double _explicitCosts = Double.NaN;

	/**
	 * <i>ImplementationShortfall</i> Constructor
	 * 
	 * @param trade <i>Trade</i> Instance
	 * @param decisionPrice Decision Price
	 * @param explicitCosts Explicit Costs
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ImplementationShortfall (
		final Trade trade,
		final double decisionPrice,
		final double explicitCosts)
		throws Exception
	{
		if (null == (_trade = trade) ||
			!NumberUtil.IsValid (_decisionPrice = decisionPrice) ||
			!NumberUtil.IsValid (_explicitCosts = explicitCosts))
		{
			throw new Exception ("ImplementationShortfall Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the <i>Trade</i> Instance
	 * 
	 * @return <i>Trade</i> Instance
	 */

	public Trade trade()
	{
		return _trade;
	}

	/**
	 * Retrieve the Decision Price
	 * 
	 * @return Decision Price
	 */

	public double decisionPrice()
	{
		return _decisionPrice;
	}

	/**
	 * Retrieve the Explicit Costs
	 * 
	 * @return Explicit Costs
	 */

	public double explicitCosts()
	{
		return _explicitCosts;
	}

	/**
	 * Calculate the IS Slippage
	 * 
	 * @return IS Slippage
	 */

	public double slippage()
	{
		return Math.abs (_trade.price() - _decisionPrice) * _trade.size()  + _explicitCosts;
	}
}
