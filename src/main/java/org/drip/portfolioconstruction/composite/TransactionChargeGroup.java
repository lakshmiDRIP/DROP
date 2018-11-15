
package org.drip.portfolioconstruction.composite;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>TransactionChargeGroup\</i> contains the Transaction Charge Values for the specified Set of Assets.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/composite">Composite</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TransactionChargeGroup {
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.cost.TransactionCharge>
		_mapTransactionCharge = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.cost.TransactionCharge>();

	/**
	 * Add an Asset's Transaction Charge
	 * 
	 * @param strAssetID The Asset ID
	 * @param tc The Asset's Transaction Charge
	 * 
	 * @return TRUE - The Asset's Transaction Charge successfully added.
	 */

	public boolean add (
		final java.lang.String strAssetID,
		final org.drip.portfolioconstruction.cost.TransactionCharge tc)
	{
		if (null == strAssetID || strAssetID.isEmpty() || null == tc) return false;

		_mapTransactionCharge.put (strAssetID, tc);

		return true;
	}

	/**
	 * Indicate if the Asset's Transaction Charge is Available
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return TRUE - The Asset's Transaction Charge is Available
	 */

	public boolean contains (
		final java.lang.String strAssetID)
	{
		return null != strAssetID && !strAssetID.isEmpty() && _mapTransactionCharge.containsKey (strAssetID);
	}

	/**
	 * Retrieve the Asset's Transaction Charge
	 * 
	 * @param strAssetID The Asset ID
	 * 
	 * @return The Asset's Transaction Charge
	 */

	public org.drip.portfolioconstruction.cost.TransactionCharge get (
		final java.lang.String strAssetID)
	{
		if (null == strAssetID || strAssetID.isEmpty() || !_mapTransactionCharge.containsKey (strAssetID))
			return null;

		return _mapTransactionCharge.get (strAssetID);
	}

	/**
	 * Retrieve the Map of Transaction Charge
	 * 
	 * @return Map of the Transaction Charge
	 */

	public java.util.Map<java.lang.String, org.drip.portfolioconstruction.cost.TransactionCharge> map()
	{
		return _mapTransactionCharge;
	}

	/**
	 * Constrict the Transaction Charge Array to those of the Holdings
	 * 
	 * @param holdings The Holdings Instance
	 * 
	 * @return The Array of Transaction Charge Objects
	 */

	public org.drip.portfolioconstruction.cost.TransactionCharge[] constrict (
		final org.drip.portfolioconstruction.composite.Holdings holdings)
	{
		if (null == holdings) return null;

		java.util.Set<java.lang.String> setAsset = holdings.assets();

		java.util.List<org.drip.portfolioconstruction.cost.TransactionCharge> lsTransactionCharge = new
			java.util.ArrayList<org.drip.portfolioconstruction.cost.TransactionCharge>();

		for (java.lang.String strAssetID : setAsset)
			lsTransactionCharge.add (contains (strAssetID) ? get (strAssetID) : null);

		return (org.drip.portfolioconstruction.cost.TransactionCharge[]) lsTransactionCharge.toArray();
	}
}
