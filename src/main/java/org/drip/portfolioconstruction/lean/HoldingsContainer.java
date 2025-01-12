
package org.drip.portfolioconstruction.lean;

import java.util.Map;
import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.numerical.common.NumberUtil;

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
 * <i>HoldingsContainer</i> implements the container that maintains the Asset Holdings Market Value and
 * 	Weight.
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

public class HoldingsContainer
{
	private boolean _dirty = false;
	private double _cashValue = Double.NaN;
	private double _cashWeight = Double.NaN;
	private double _marketValue = Double.NaN;

	private Map<String, Double> _assetWeightMap = new CaseInsensitiveHashMap<Double>();

	private Map<String, Double> _assetMarketValueMap = new CaseInsensitiveHashMap<Double>();

	private boolean unDirty()
	{
		if (!_dirty || _assetMarketValueMap.isEmpty()) {
			return false;
		}

		_dirty = false;
		_marketValue = _cashValue;
		double cumulativeWeight = 0.;

		_assetWeightMap.clear();

		for (Map.Entry<String, Double> assetMarketValueMapEntry : _assetMarketValueMap.entrySet()) {
			_marketValue += assetMarketValueMapEntry.getValue();
		}

		if (0. == _marketValue) {
			return true;
		}

		for (Map.Entry<String, Double> assetMarketValueMapEntry : _assetMarketValueMap.entrySet()) {
			double assetWeight = assetMarketValueMapEntry.getValue() / _marketValue;

			_assetWeightMap.put (assetMarketValueMapEntry.getKey(), assetWeight);

			cumulativeWeight += assetWeight;
		}

		_cashWeight = 1. - cumulativeWeight;
		return true;
	}

	/**
	 * Empty <i>HoldingsContainer</i> Constructor
	 */

	public HoldingsContainer()
	{
		_cashValue = 0.;
		_cashWeight = 0.;
		_marketValue = 0.;

		_assetWeightMap = new CaseInsensitiveHashMap<Double>();

		_assetMarketValueMap = new CaseInsensitiveHashMap<Double>();
	}

	/**
	 * Retrieve the Map of Asset Market Values
	 * 
	 * @return Map of Asset Market Values
	 */

	public Map<String, Double> assetMarketValueMap()
	{
		return _assetMarketValueMap;
	}

	/**
	 * Retrieve the Map of Asset Weights
	 * 
	 * @return Map of Asset Market Values
	 */

	public Map<String, Double> assetWeightMap()
	{
		unDirty();

		return _assetWeightMap;
	}

	/**
	 * Retrieve the Holdings Market Value
	 * 
	 * @return Holdings Market Value
	 */

	public double marketValue()
	{
		unDirty();

		return _marketValue;
	}

	/**
	 * Retrieve the Holdings Cash Value
	 * 
	 * @return Holdings Cash Value
	 */

	public double cashValue()
	{
		return _cashValue;
	}

	/**
	 * Set the Asset to its Market Value on the Holdings
	 * 
	 * @param assetID Asset ID
	 * @param assetMarketValue Asset Market Value
	 * 
	 * @return TRUE - Asset successfully set on the Holdings
	 */

	public boolean setAsset (
		final String assetID,
		final double assetMarketValue)
	{
		if (null == assetID || assetID.isEmpty() || !NumberUtil.IsValid (assetMarketValue)) {
			return false;
		}

		_assetMarketValueMap.put (assetID, assetMarketValue);

		_dirty = true;
		return true;
	}

	/**
	 * Update the Asset to its Market Value on the Holdings
	 * 
	 * @param assetID Asset ID
	 * @param assetMarketValue Asset Market Value
	 * 
	 * @return TRUE - Asset successfully updated on the Holdings
	 */

	public boolean updateAsset (
		final String assetID,
		final double assetMarketValue)
	{
		if (null == assetID || assetID.isEmpty() || !NumberUtil.IsValid (assetMarketValue)) {
			return false;
		}

		_cashValue += assetMarketValue - (
			_assetMarketValueMap.containsKey (assetID) ? 0. : _assetMarketValueMap.get (assetID)
		);

		_assetMarketValueMap.put (assetID, assetMarketValue);

		_dirty = true;
		return true;
	}

	/**
	 * Set the Cash Value on the Holdings
	 * 
	 * @param cashValue Cash Value
	 * 
	 * @return TRUE - Cash Value successfully set on the Holdings
	 */

	public boolean setCashValue (
		final double cashValue)
	{
		if (!NumberUtil.IsValid (cashValue)) {
			return false;
		}

		_cashValue = cashValue;
		_dirty = true;
		return true;
	}

	/**
	 * Retrieve the Set of Assets
	 * 
	 * @return Set of Assets
	 */

	public Set<String> assetSet()
	{
		return _assetMarketValueMap.keySet();
	}

	/**
	 * Retrieve the Cash Weight
	 * 
	 * @return Cash Weight
	 */

	public double cashWeight()
	{
		unDirty();

		return _cashWeight;
	}

	/**
	 * Remove the Asset corresponding to the ID
	 * 
	 * @param assetID Asset ID
	 * 
	 * @return TRUE - Asset successfully removed
	 */

	public boolean removeAsset (
		final String assetID)
	{
		if (null == assetID || !_assetMarketValueMap.containsKey (assetID)) {
			return false;
		}

		_assetMarketValueMap.remove (assetID);

		_dirty = true;
		return true;
	}

	/**
	 * Clone the Holdings Container Instance
	 * 
	 * @return Cloned Holdings Container Instance
	 */

	@Override public HoldingsContainer clone()
	{
		HoldingsContainer holdingsContainer = new HoldingsContainer();

		for (Map.Entry<String, Double> assetMarketValueMapEntry : _assetMarketValueMap.entrySet()) {
			if (!holdingsContainer.setAsset (
				assetMarketValueMapEntry.getKey(),
				assetMarketValueMapEntry.getValue()
			))
			{
				return null;
			}
		}

		return holdingsContainer.setCashValue (_cashValue) ? holdingsContainer : null;
	}
}
