
package org.drip.capital.label;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalSegmentCoordinate</i> implements the Managed Capital Segment Coordinate. The References are:
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

public class CapitalSegmentCoordinate
	implements org.drip.capital.label.Coordinate
{
	private java.lang.String _id = "";

	/**
	 * CapitalSegmentCoordinate Constructor
	 * 
	 * @param id Capital Segment ID
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalSegmentCoordinate (
		final java.lang.String id)
		throws java.lang.Exception
	{
		if (null == (_id = id) || _id.isEmpty())
		{
			throw new java.lang.Exception ("CapitalSegmentCoordinate Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Capital Segment ID
	 * 
	 * @return The Capital Segment ID
	 */

	public java.lang.String id()
	{
		return _id;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _id;
	}
}
