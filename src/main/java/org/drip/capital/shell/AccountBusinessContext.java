
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>AccountBusinessContext</i> maintains the Account To Business Mappings. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/shell/README.md">Economic Risk Capital Parameter Contexts</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AccountBusinessContext
{
	private java.util.Map<java.lang.String, java.lang.String> _accountBusinessMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

	/**
	 * AccountBusinessContext Constructor
	 * 
	 * @param accountBusinessMap Account To Business Map
	 * 
	 * @throws java.lang.Exception Thrwn if the Inputs are Invalid
	 */

	public AccountBusinessContext (
		final java.util.Map<java.lang.String, java.lang.String> accountBusinessMap)
		throws java.lang.Exception
	{
		if (null == (_accountBusinessMap = accountBusinessMap) || 0 == _accountBusinessMap.size())
		{
			throw new java.lang.Exception (
				"AccountBusinessContext Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Check if the Account Exists
	 * 
	 * @param account The Account
	 * 
	 * @return TRUE - The Account Exists
	 */

	public boolean containsAccount (
		final java.lang.String account)
	{
		return null != account && !account.isEmpty() && _accountBusinessMap.containsKey (
			account
		);
	}

	/**
	 * Retrieve the Business corresponding to the Account
	 * 
	 * @param account The Account
	 * 
	 * @return The Business corresponding to the Account
	 */

	public java.lang.String business (
		final java.lang.String account)
	{
		return containsAccount (
			account
		) ? _accountBusinessMap.get (
			account
		) : "";
	}

	/**
	 * Retrieve the Set of Accounts corresponding to the given Business
	 * 
	 * @param business The Business
	 * 
	 * @return The Set of Accounts
	 */

	public java.util.Set<java.lang.String> accountSet (
		final java.lang.String business)
	{
		if (null == business || business.isEmpty())
		{
			return null;
		}

		java.util.Set<java.lang.String> accountSet = new java.util.HashSet<java.lang.String>();

		for (java.util.Map.Entry<java.lang.String, java.lang.String> accountBusinessEntry :
			_accountBusinessMap.entrySet())
		{
			java.lang.String accountBusiness = accountBusinessEntry.getValue();

			if (business.equalsIgnoreCase (
				accountBusiness
			))
			{
				accountSet.add (
					accountBusinessEntry.getKey()
				);
			}
		}

		return accountSet;
	}

	/**
	 * Retrieve the Account To Business Map
	 * 
	 * @return The Account To Business Map
	 */

	public final java.util.Map<java.lang.String, java.lang.String> accountBusinessMap()
	{
		return _accountBusinessMap;
	}
}
