
package org.drip.capital.entity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>ManagedSegmentL1</i> implements the VaR and the Stress Functionality inside of the L1 Managed Segment.
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

public class ManagedSegmentL1 extends org.drip.capital.entity.CapitalSegment
{
	private org.drip.capital.entity.CapitalUnit[] _capitalUnitArray = null;

	/**
	 * ManagedSegmentL1 Constructor
	 * 
	 * @param managedSegmentCoordinate Managed Segment Coordinate
	 * @param capitalUnitArray Array of Capital Units
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ManagedSegmentL1 (
		final org.drip.capital.label.CapitalSegmentCoordinate managedSegmentCoordinate,
		final org.drip.capital.entity.CapitalUnit[] capitalUnitArray)
		throws java.lang.Exception
	{
		super (managedSegmentCoordinate);

		if (null == (_capitalUnitArray = capitalUnitArray))
		{
			throw new java.lang.Exception ("ManagedSegmentL1 Constructor => Invalid Inputs");
		}

		int capitalUnitCount = _capitalUnitArray.length;

		if (0 == capitalUnitCount)
		{
			throw new java.lang.Exception ("ManagedSegment Constructor => Invalid Inputs");
		}

		for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
		{
			if (null == _capitalUnitArray[capitalUnitIndex])
			{
				throw new java.lang.Exception ("ManagedSegmentL1 Constructor => Invalid Inputs");
			}
		}
	}

	@Override public org.drip.capital.entity.CapitalUnit[] capitalUnitArray()
	{
		return _capitalUnitArray;
	}
}
