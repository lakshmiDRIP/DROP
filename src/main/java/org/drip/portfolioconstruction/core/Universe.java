
package org.drip.portfolioconstruction.core;

import java.util.Map;
import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.portfolioconstruction.composite.Holdings;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>Universe</i> contains all the Assets in the Universe.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/core/README.md">Core Portfolio Construction Component Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Universe
{
	private Map<String, Holdings> _holdingsMap = null;
	private Map<String, AssetPosition> _assetPositionMap = null;

	/**
	 * Empty Universe Constructor
	 */

	public Universe()
	{
		_holdingsMap = new CaseInsensitiveTreeMap<Holdings>();

		_assetPositionMap = new CaseInsensitiveHashMap<AssetPosition>();
	}

	/**
	 * Retrieve the Holdings Map
	 * 
	 * @return Holdings Map
	 */

	public Map<String, Holdings> holdingsMap()
	{
		return _holdingsMap;
	}

	/**
	 * Retrieve the Asset Position Map
	 * 
	 * @return Asset Position Map
	 */

	public Map<String, AssetPosition> assetPositionMap()
	{
		return _assetPositionMap;
	}

	/**
	 * Add an Asset Position to the Universe
	 * 
	 * @param assetPosition Asset Position to be added
	 * 
	 * @return TRUE - The Asset Position has been added successfully
	 */

	public boolean addAssetPosition (
		final AssetPosition assetPosition)
	{
		if (null == assetPosition) {
			return false;
		}

		_assetPositionMap.put (assetPosition.id(), assetPosition);

		return true;
	}

	/**
	 * Add a Holdings Entity
	 * 
	 * @param holdings Holdings Entity
	 * 
	 * @return TRUE - The Holdings Entity successfully added
	 */

	public boolean addHoldings (
		final Holdings holdings)
	{
		if (null == holdings) {
			return false;
		}

		_holdingsMap.put (holdings.id(), holdings);

		return true;
	}

	/**
	 * Indicate if the Asset is contained in the Universe
	 * 
	 * @param asset The Asset Position Instance
	 * 
	 * @return TRUE - The Asset is contained in the Universe
	 */

	public boolean containsAssetInPosition (
		final Asset asset)
	{
		return null != asset && _assetPositionMap.containsKey (asset.id());
	}

	/**
	 * Indicate if the Asset Position is contained in the Universe
	 * 
	 * @param assetPosition The Asset Position Instance
	 * 
	 * @return TRUE - The Asset Position is contained in the Universe
	 */

	public boolean containsAssetPosition (
		final AssetPosition assetPosition)
	{
		return null != assetPosition && _assetPositionMap.containsKey (assetPosition.id());
	}

	/**
	 * Indicate if the Holdings is contained in the Universe
	 * 
	 * @param holdings Holdings
	 * 
	 * @return TRUE - The Holdings is contained in the Universe
	 */

	public boolean containsHoldings (
		final Holdings holdings)
	{
		return null != holdings && _holdingsMap.containsKey (holdings.id());
	}

	/**
	 * Indicate if the Asset is contained in the Universe Holdings
	 * 
	 * @param asset The Asset Position Instance
	 * 
	 * @return TRUE - The Asset is contained in the Universe Holdings
	 */

	public boolean containsAssetInHoldings (
		final Asset asset)
	{
		if (null == asset || _assetPositionMap.isEmpty()) {
			return false;
		}

		String assetID = asset.id();

		for (Holdings holdings : _holdingsMap.values()) {
			if (holdings.contains (assetID)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Indicate if the Asset Position is contained in the Universe
	 * 
	 * @param id The Asset Position ID
	 * 
	 * @return TRUE - The Asset Position is contained in the Universe
	 */

	public boolean containsAssetPosition (
		final String id)
	{
		return null != id && !id.isEmpty() && _assetPositionMap.containsKey (id);
	}

	/**
	 * Indicate if the Holdings is contained in the Universe
	 * 
	 * @param id Holdings ID
	 * 
	 * @return TRUE - The Holdings is contained in the Universe
	 */

	public boolean containsHoldings (
		final String id)
	{
		return null != id && !id.isEmpty() && _holdingsMap.containsKey (id);
	}

	/**
	 * Retrieve the List of the Asset Position Identifiers
	 * 
	 * @return The List of the Asset Position Identifiers
	 */

	public Set<String> assetPositionIDSet()
	{
		return _assetPositionMap.keySet();
	}

	/**
	 * Retrieve the List of the Holdings Identifiers
	 * 
	 * @return The List of the Holdings Identifiers
	 */

	public Set<String> holdingsIDSet()
	{
		return _holdingsMap.keySet();
	}

	/**
	 * Retrieve the Asset Position corresponding to the ID
	 * 
	 * @param id ID
	 * 
	 * @return Asset Position corresponding to the ID
	 */

	public Asset retrieveAssetPosition (
		final String id)
	{
		return null == id || _assetPositionMap.containsKey (id) ? null : _assetPositionMap.get (id);
	}

	/**
	 * Retrieve the Holdings corresponding to the ID
	 * 
	 * @param id ID
	 * 
	 * @return Holdings corresponding to the ID
	 */

	public Holdings retrieveHoldings (
		final String id)
	{
		return null == id || _holdingsMap.containsKey (id) ? null : _holdingsMap.get (id);
	}
}
