
package org.drip.capital.entity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>ManagedSegmentLn</i> implements the VaR and the Stress Functionality inside of the Ln Managed Segment.
 * 	The References are:
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

public class ManagedSegmentLn extends org.drip.capital.entity.ManagedSegmentL1
{
	private java.util.Map<java.lang.String, java.util.List<org.drip.capital.entity.ManagedSegmentL1>>
		_managedSegmentL1ListMap = null;

	/**
	 * Construct a Standard Instance of ManagedSegmentLn
	 * 
	 * @param managedSegmentCoordinate Managed Segment Coordinate
	 * @param gocArray Array of GOC's
	 * 
	 * @return Standard Instance of ManagedSegmentLn
	 */

	public static final ManagedSegmentLn Standard (
		final org.drip.capital.label.CapitalSegmentCoordinate managedSegmentCoordinate,
		final org.drip.capital.entity.CapitalUnit[] gocArray)
	{
		if (null == gocArray)
		{
			return null;
		}

		int gocCount = gocArray.length;

		if (0 == gocCount)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.util.List<org.drip.capital.entity.ManagedSegmentL1>>
			managedSegmentL1ListMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.util.List<org.drip.capital.entity.ManagedSegmentL1>>();

		try
		{
			return new ManagedSegmentLn (
				managedSegmentL1ListMap,
				managedSegmentCoordinate,
				gocArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ManagedSegmentLn Constructor
	 * 
	 * @param managedSegmentL1ListMap L1 Managed Segment List Map
	 * @param managedSegmentCoordinate Managed Segment Coordinate
	 * @param gocArray Array of GOC's
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ManagedSegmentLn (
		final java.util.Map<java.lang.String, java.util.List<org.drip.capital.entity.ManagedSegmentL1>>
			managedSegmentL1ListMap,
		final org.drip.capital.label.CapitalSegmentCoordinate managedSegmentCoordinate,
		final org.drip.capital.entity.CapitalUnit[] gocArray)
		throws java.lang.Exception
	{
		super (
			managedSegmentCoordinate,
			gocArray
		);

		if (null == (_managedSegmentL1ListMap = managedSegmentL1ListMap))
		{
			throw new java.lang.Exception ("ManagedSegmentLn Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the L1 Managed Segment List Map
	 * 
	 * @return The L1 Managed Segment List Map
	 */

	public java.util.Map<java.lang.String, java.util.List<org.drip.capital.entity.ManagedSegmentL1>>
		managedSegmentL1ListMap()
	{
		return _managedSegmentL1ListMap;
	}
}
