
package org.drip.portfolioconstruction.lean;

import java.util.Map;

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
 * <i>FullSequenceAllocator</i> generates an Optimal Portfolio from the Initial Holdings and performs
 * 	Post-Processing if necessary.
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

public class FullSequenceAllocator
{
	private Optimizer _optimizer = null;
	private boolean _diagnosticsOn = false;
	private PostProcessorSettings _postProcessorSettings = null;

	/**
	 * <i>FullSequenceAllocator</i> Constructor
	 * 
	 * @param optimizer Optimizer
	 * @param postProcessorSettings Post Processing Settings
	 * @param diagnosticsOn TRUE - Diagnostics is turned on
	 */

	public FullSequenceAllocator (
		final Optimizer optimizer,
		final PostProcessorSettings postProcessorSettings,
		final boolean diagnosticsOn)
		throws Exception
	{
		if (null == (_optimizer = optimizer)) {
			throw new Exception ("FullSequenceAllocator Constructor => Invalid Inputs");
		}

		_diagnosticsOn = diagnosticsOn;
		_postProcessorSettings = postProcessorSettings;
	}

	/**
	 * Retrieve the Optimizer
	 * 
	 * @return Optimizer
	 */

	public Optimizer optimizer()
	{
		return _optimizer;
	}

	/**
	 * Retrieve the Post Processing Settings
	 * 
	 * @return Post Processing Settings
	 */

	public PostProcessorSettings postProcessorSettings()
	{
		return _postProcessorSettings;
	}

	/**
	 * Indicate if the Diagnostics is turned on
	 * 
	 * @return TRUE - Diagnostics is turned on
	 */

	public boolean diagnosticsOn()
	{
		return _diagnosticsOn;
	}

	/**
	 * Allocate an Instance of Post-processed Target Holdings from the Initial Portfolio
	 * 
	 * @param startingHoldings Starting Holdings
	 * 
	 * @return Instance of Post-processed Target Holdings
	 */

	public FullSequenceAllocation allocate (
		final HoldingsContainer startingHoldings)
	{
		FullSequenceAllocation fullSequenceAllocation = _diagnosticsOn ?
			new FullSequenceAllocationDiagnostics() :
			new FullSequenceAllocation();

		if (!fullSequenceAllocation.startingHoldings (startingHoldings)) {
			return null;
		}

		HoldingsContainer endingHoldings = _optimizer.optimize (startingHoldings);

		if (null == endingHoldings) {
			return null;
		}

		if (_diagnosticsOn) {
			((FullSequenceAllocationDiagnostics) fullSequenceAllocation).setPreFiltered (endingHoldings);
		}

		if (null != _postProcessorSettings) {
			Map<String, Double> startingAssetMarketValueMap = startingHoldings.assetMarketValueMap();

			Map<String, Double> endingAssetMarketValueMap = endingHoldings.assetMarketValueMap();

			if (_postProcessorSettings.filterSells()) {
				for (String assetID : endingAssetMarketValueMap.keySet()) {
					double startingAssetMarketValue = startingAssetMarketValueMap.containsKey (assetID) ?
						startingAssetMarketValueMap.get (assetID) : 0.;

					double tradeAmount = endingAssetMarketValueMap.get (assetID) - startingAssetMarketValue;

					if (0. > tradeAmount) {
						endingHoldings.updateAsset (assetID, startingAssetMarketValue);
					}
				}
			} else if (_postProcessorSettings.filterBuys()) {
				for (String assetID : endingAssetMarketValueMap.keySet()) {
					double startingAssetMarketValue = startingAssetMarketValueMap.containsKey (assetID) ?
						startingAssetMarketValueMap.get (assetID) : 0.;

					double tradeAmount = endingAssetMarketValueMap.get (assetID) - startingAssetMarketValue;

					if (0. < tradeAmount) {
						endingHoldings.updateAsset (assetID, startingAssetMarketValue);
					}
				}
			}
		}

		return fullSequenceAllocation.endingHoldings (endingHoldings) ? fullSequenceAllocation : null;
	}
}
