
package org.drip.capital.shell;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>BusinessGroupingContext</i> maintains the Loaded Business Groupings. The References
 * 	are:
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

public class BusinessGroupingContext
{
	private java.util.Map<java.lang.String, org.drip.capital.label.BusinessGrouping>
		_businessGroupingMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.label.BusinessGrouping>();

	/**
	 * BusinessGroupingContext Constructor
	 * 
	 * @param businessGroupingMap Business Grouping Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BusinessGroupingContext (
		final java.util.Map<java.lang.String, org.drip.capital.label.BusinessGrouping>
			businessGroupingMap)
		throws java.lang.Exception
	{
		if (null == (_businessGroupingMap = businessGroupingMap) || 0 == _businessGroupingMap.size())
		{
			throw new java.lang.Exception ("BusinessGroupingContext Constructor => Invalid Inputs");
		}
	}

	/**
	 * Indicate if the Grouping for the specified Business Unit is Available
	 * 
	 * @param business Business
	 * 
	 * @return TRUE - Grouping for the specified Business Unit is Available
	 */

	public boolean containsBusiness (
		final java.lang.String business)
	{
		return null == business || business.isEmpty() ? false : _businessGroupingMap.containsKey (business);
	}

	/**
	 * Retrieve the Grouping for the specified Business Unit
	 * 
	 * @param business Business
	 * 
	 * @return The Grouping for the specified Business Unit
	 */

	public org.drip.capital.label.BusinessGrouping businessGrouping (
		final java.lang.String business)
	{
		return containsBusiness (business) ? _businessGroupingMap.get (business) : null;
	}

	/**
	 * Retrieve the Set of Businesses belonging to the Group
	 * 
	 * @param group The Group
	 * 
	 * @return Set of Businesses belonging to the Group
	 */

	public java.util.Set<java.lang.String> businessSetFromGroup (
		final java.lang.String group)
	{
		if (null == group || group.isEmpty())
		{
			return null;
		}

		java.util.Set<java.lang.String> businessSet = new java.util.HashSet<java.lang.String>();

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.label.BusinessGrouping>
			businessGroupingEntry : _businessGroupingMap.entrySet())
		{
			org.drip.capital.label.BusinessGrouping businessGrouping =
				businessGroupingEntry.getValue();

			if (group.equalsIgnoreCase (businessGrouping.group()))
			{
				businessSet.add (businessGroupingEntry.getKey());
			}
		}

		return businessSet;
	}

	/**
	 * Retrieve the Set of Businesses belonging to the Product
	 * 
	 * @param product The Product
	 * 
	 * @return Set of Businesses belonging to the Product
	 */

	public java.util.Set<java.lang.String> businessSetFromProduct (
		final java.lang.String product)
	{
		if (null == product || product.isEmpty())
		{
			return null;
		}

		java.util.Set<java.lang.String> businessSet = new java.util.HashSet<java.lang.String>();

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.label.BusinessGrouping>
			businessGroupingEntry : _businessGroupingMap.entrySet())
		{
			org.drip.capital.label.BusinessGrouping businessGrouping =
				businessGroupingEntry.getValue();

			if (product.equalsIgnoreCase (businessGrouping.product()))
			{
				businessSet.add (businessGroupingEntry.getKey());
			}
		}

		return businessSet;
	}

	/**
	 * Retrieve the Business Grouping Map
	 * 
	 * @return The Business Grouping Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.label.BusinessGrouping> businessGroupingMap()
	{
		return _businessGroupingMap;
	}
}
