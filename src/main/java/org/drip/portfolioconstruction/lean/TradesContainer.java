
package org.drip.portfolioconstruction.lean;

import java.util.Map;

import org.drip.analytics.support.CaseInsensitiveHashMap;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>TradesContainer</i> implements the container that maintains the Map of Trades.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/lean/README.md">"Lean" Portfolio Construction Utilities Suite</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TradesContainer
{
	private Map<String, Double> _assetQuantityMap = null;

	/**
	 * Construct a <i>TradesContainer</i> Instance from the Starting and the Ending Holdings
	 * 
	 * @param startingHoldingsContainer Starting <i>HoldingsContainer</i>
	 * @param endingHoldingsContainer Ending <i>HoldingsContainer</i>
	 * 
	 * @return <i>TradesContainer</i> Instance
	 */

	public static final TradesContainer FromStartAndEndHoldings (
		final HoldingsContainer startingHoldingsContainer,
		final HoldingsContainer endingHoldingsContainer)
	{
		if (null == startingHoldingsContainer && null == endingHoldingsContainer) {
			return null;
		}

		Map<String, Double> assetQuantityMap = new CaseInsensitiveHashMap<Double>();

		if (null == startingHoldingsContainer) {
			for (Map.Entry<String, Double> assetMarketValueMapEntry :
				endingHoldingsContainer.assetMarketValueMap().entrySet())
			{
				assetQuantityMap.put (
					assetMarketValueMapEntry.getKey(),
					assetMarketValueMapEntry.getValue()
				);
			}
		} else if (null == endingHoldingsContainer) {
			for (Map.Entry<String, Double> assetMarketValueMapEntry :
				startingHoldingsContainer.assetMarketValueMap().entrySet())
			{
				assetQuantityMap.put (
					assetMarketValueMapEntry.getKey(),
					-1. * assetMarketValueMapEntry.getValue()
				);
			}
		} else {
			Map<String, Double> startingAssetMarketValueMap =
				startingHoldingsContainer.assetMarketValueMap();

			for (Map.Entry<String, Double> assetMarketValueMapEntry :
				endingHoldingsContainer.assetMarketValueMap().entrySet())
			{
				String assetID = assetMarketValueMapEntry.getKey();

				assetQuantityMap.put (
					assetID,
					assetMarketValueMapEntry.getValue() - (
						startingAssetMarketValueMap.containsKey (assetID) ?
							startingAssetMarketValueMap.get (assetID) : 0.
					)
				);
			}

			for (Map.Entry<String, Double> assetMarketValueMapEntry :
				startingHoldingsContainer.assetMarketValueMap().entrySet())
			{
				String assetID = assetMarketValueMapEntry.getKey();

				if (!assetQuantityMap.containsKey (assetID)) {
					assetQuantityMap.put (assetID, -1. * assetMarketValueMapEntry.getValue());
				}
			}
		}

		try {
			return new TradesContainer (assetQuantityMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>TradesContainer</i> Constructor
	 * 
	 * @param assetQuantityMap Asset Quantity Map
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public TradesContainer (
		final Map<String, Double> assetQuantityMap)
		throws Exception
	{
		if (null == (_assetQuantityMap = assetQuantityMap) || 0 == _assetQuantityMap.size()) {
			throw new Exception ("TradesContainer Constructor => Invalid Asset Quantity Map");
		}
	}

	/**
	 * Retrieve the Asset Quantity Map
	 * 
	 * @return Asset Quantity Map
	 */

	public Map<String, Double> assetQuantityMap()
	{
		return _assetQuantityMap;
	}
}
