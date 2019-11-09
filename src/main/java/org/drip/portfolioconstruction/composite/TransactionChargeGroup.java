
package org.drip.portfolioconstruction.composite;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>TransactionChargeGroup</i> contains the Transaction Charge Values for the specified Set of Assets.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/composite/README.md">Portfolio Construction Component Groups Suite</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TransactionChargeGroup
{
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.cost.TransactionCharge>
		_transactionChargeMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.cost.TransactionCharge>();

	/**
	 * Add an Asset's Transaction Charge
	 * 
	 * @param assetID The Asset ID
	 * @param transactionCharge The Asset's Transaction Charge
	 * 
	 * @return TRUE - The Asset's Transaction Charge successfully added.
	 */

	public boolean add (
		final java.lang.String assetID,
		final org.drip.portfolioconstruction.cost.TransactionCharge transactionCharge)
	{
		if (null == assetID || assetID.isEmpty() ||
			null == transactionCharge)
		{
			return false;
		}

		_transactionChargeMap.put (
			assetID,
			transactionCharge
		);

		return true;
	}

	/**
	 * Indicate if the Asset's Transaction Charge is Available
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return TRUE - The Asset's Transaction Charge is Available
	 */

	public boolean contains (
		final java.lang.String assetID)
	{
		return null != assetID && !assetID.isEmpty() && _transactionChargeMap.containsKey (assetID);
	}

	/**
	 * Retrieve the Asset's Transaction Charge
	 * 
	 * @param assetID The Asset ID
	 * 
	 * @return The Asset's Transaction Charge
	 */

	public org.drip.portfolioconstruction.cost.TransactionCharge transactionCharge (
		final java.lang.String assetID)
	{
		if (null == assetID || assetID.isEmpty() || !_transactionChargeMap.containsKey (assetID))
		{
			return null;
		}

		return _transactionChargeMap.get (assetID);
	}

	/**
	 * Retrieve the Map of Transaction Charge
	 * 
	 * @return Map of the Transaction Charge
	 */

	public java.util.Map<java.lang.String, org.drip.portfolioconstruction.cost.TransactionCharge>
		transactionChargeMap()
	{
		return _transactionChargeMap;
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
		if (null == holdings)
		{
			return null;
		}

		java.util.Set<java.lang.String> assetIDSet = holdings.assetIDSet();

		java.util.List<org.drip.portfolioconstruction.cost.TransactionCharge> transactionChargeList =
			new java.util.ArrayList<org.drip.portfolioconstruction.cost.TransactionCharge>();

		for (java.lang.String assetID : assetIDSet)
		{
			transactionChargeList.add (contains (assetID) ? transactionCharge (assetID) : null);
		}

		return (org.drip.portfolioconstruction.cost.TransactionCharge[]) transactionChargeList.toArray();
	}
}
