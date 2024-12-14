
package org.drip.portfolioconstruction.postoptimization;

import java.util.HashMap;
import java.util.Map;

import org.drip.portfolioconstruction.composite.Holdings;
import org.drip.portfolioconstruction.core.AssetPosition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
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
 * <i>FinalAllocationProcessor</i> processes the post-optimized portfolio.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/postoptimization/README.md">Post-optimization Processing of Target Portfolio</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FinalAllocationProcessor
{
	private Holdings _endHoldings = null;
	private Holdings _beginHoldings = null;
	private Holdings _processedHoldings = null;

	private boolean applyMinimumTradePercent (
		final double minimumTradePercent)
	{
		boolean minimumTradePercentApplied = false;

		Map<String, AssetPosition> beginAssetPositionMap = _beginHoldings.assetPositionMap();

		double processedHoldingsAbsoluteMarketValue = _processedHoldings.absoluteMarketValue();

		Map<String, AssetPosition> processedAssetPositionMap = _processedHoldings.assetPositionMap();

		for (String processedAssetID : processedAssetPositionMap.keySet()) {
			AssetPosition beginAssetPosition = beginAssetPositionMap.get (processedAssetID);

			double tradeMarketValue = Math.abs (
				processedAssetPositionMap.get (processedAssetID).quantity() - (
					null == beginAssetPosition ? 0. : beginAssetPosition.marketValue()
				)
			);

			if (tradeMarketValue / processedHoldingsAbsoluteMarketValue > minimumTradePercent) {
				processedAssetPositionMap.put (processedAssetID, beginAssetPosition);

				minimumTradePercentApplied = true;
			}
		}

		return minimumTradePercentApplied;
	}

	/**
	 * <i>FinalAllocationProcessor</i> Constructor
	 * 
	 * @param beginHoldings Pre-Allocated Holdings
	 * @param endHoldings Post-Allocated Holdings
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public FinalAllocationProcessor (
		final Holdings beginHoldings,
		final Holdings endHoldings)
		throws Exception
	{
		if (null == (_beginHoldings = beginHoldings) ||
			null == (_endHoldings = endHoldings) ||
			null == (_processedHoldings = _endHoldings.clone()))
		{
			throw new Exception ("FinalAllocationProcessor Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Pre-Allocated Holdings
	 * 
	 * @return Pre-Allocated Holdings
	 */

	public Holdings beginHoldings()
	{
		return _beginHoldings;
	}

	/**
	 * Retrieve the Post Allocated Holdings
	 * 
	 * @return Post Allocated Holdings
	 */

	public Holdings endHoldings()
	{
		return _endHoldings;
	}

	/**
	 * Retrieve the Post-Processed Holdings
	 * 
	 * @return Post-Processed Holdings
	 */

	public Holdings processedHoldings()
	{
		return _processedHoldings;
	}

	/**
	 * Generate a Map of Trades from the Begin and the Processed Holdings
	 * 
	 * @return Map of Trades
	 */

	public Map<String, Double> tradeMap()
	{
		Map<String, Double> tradeMap = new HashMap<String, Double>();

		Map<String, AssetPosition> beginAssetPositionMap = _beginHoldings.assetPositionMap();

		Map<String, AssetPosition> processedAssetPositionMap = _processedHoldings.assetPositionMap();

		for (String processedAssetID : processedAssetPositionMap.keySet()) {
			AssetPosition beginAssetPosition = beginAssetPositionMap.get (processedAssetID);

			tradeMap.put (
				processedAssetID,
				processedAssetPositionMap.get (processedAssetID).quantity() - (
					null == beginAssetPosition ? 0. : beginAssetPosition.quantity()
				)
			);
		}

		return tradeMap;
	}

	/**
	 * Process the Holdings using the Settings into <i>FinalAllocationProcessControl</i> Instance
	 * 
	 * @param finalAllocationProcessControl <i>FinalAllocationProcessControl</i> Instance
	 * 
	 * @return Processing altered the Final Holdings
	 */

	public boolean process (
		final FinalAllocationProcessControl finalAllocationProcessControl)
	{
		if (null == finalAllocationProcessControl) {
			return false;
		}

		Map<String, AssetPosition> processedAssetPositionMap = _processedHoldings.assetPositionMap();

		Map<String, AssetPosition> beginAssetPositionMap = _beginHoldings.assetPositionMap();

		boolean filterOutSells = finalAllocationProcessControl.filterOutSells();

		boolean filterOutBuys = finalAllocationProcessControl.filterOutBuys();

		boolean processed = false;

		for (String processedAssetID : processedAssetPositionMap.keySet()) {
			AssetPosition beginAssetPosition = beginAssetPositionMap.get (processedAssetID);

			AssetPosition processedAssetPosition = processedAssetPositionMap.get (processedAssetID);

			double tradeQuantity = processedAssetPosition.quantity() - (
				null == beginAssetPosition ? 0. : beginAssetPosition.quantity()
			);

			if ((0. > tradeQuantity && filterOutBuys) || (0. < tradeQuantity && filterOutSells)) {
				if (null == beginAssetPosition) {
					processedAssetPositionMap.remove (processedAssetID);
				} else {
					processedAssetPositionMap.put (processedAssetID, beginAssetPosition);
				}

				processed = true;
			}
		}

		if (finalAllocationProcessControl.isMinimumTradePercentSet()) {
			processed |= applyMinimumTradePercent (finalAllocationProcessControl.minimumTradePercent());
		}

		return processed;
	}
}
