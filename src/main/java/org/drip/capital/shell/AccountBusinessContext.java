
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
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
			throw new java.lang.Exception ("AccountBusinessContext Constructor => Invalid Inputs");
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
		return null != account && !account.isEmpty() && _accountBusinessMap.containsKey (account);
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
		return containsAccount (account) ? _accountBusinessMap.get (account) : "";
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

			if (business.equalsIgnoreCase (accountBusiness))
			{
				accountSet.add (accountBusinessEntry.getKey());
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
