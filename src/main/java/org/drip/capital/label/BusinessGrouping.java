
package org.drip.capital.label;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>BusinessGrouping</i> holds the Group, Product, and the Business Hierarchy. The References are:
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

public class BusinessGrouping
{
	private java.lang.String _group = "";
	private java.lang.String _product = "";
	private java.lang.String _business = "";

	/**
	 * BusinessGrouping Constructor
	 * 
	 * @param business Business
	 * @param product Product
	 * @param group Group
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BusinessGrouping (
		final java.lang.String business,
		final java.lang.String product,
		final java.lang.String group)
		throws java.lang.Exception
	{
		if (null == (_business = business) || _business.isEmpty() ||
			null == (_product = product) || _product.isEmpty() ||
			null == (_group = group) || _group.isEmpty())
		{
			throw new java.lang.Exception ("BusinessGrouping Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Business
	 * 
	 * @return The Business
	 */

	public java.lang.String business()
	{
		return _business;
	}

	/**
	 * Retrieve the Product
	 * 
	 * @return The Product
	 */

	public java.lang.String product()
	{
		return _product;
	}

	/**
	 * Retrieve the Group
	 * 
	 * @return The Group
	 */

	public java.lang.String group()
	{
		return _group;
	}
}
