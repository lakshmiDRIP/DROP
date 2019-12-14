
package org.drip.capital.label;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>RegionRiskTypeCoordinate</i> implements the Region + Risk Type Coordinate Node Identifier. The
 * 	References are:
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
 * @author Lakshmi Krishnamurthy
 */

public class RegionRiskTypeCoordinate
	extends org.drip.capital.label.CapitalUnitCoordinate
{
	private java.lang.String _region = "";

	/**
	 * Construct a Standard Instance of RegionRiskTypeCoordinate from the FQN
	 * 
	 * @param fullyQualifiedName The FQN
	 * 
	 * @return Standard Instance of RegionRiskTypeCoordinate from the FQN
	 */

	public static RegionRiskTypeCoordinate Standard (
		final java.lang.String fullyQualifiedName)
	{
		if (null == fullyQualifiedName || fullyQualifiedName.isEmpty())
		{
			return null;
		}

		java.lang.String[] regionRiskType = org.drip.numerical.common.StringUtil.Split (
			fullyQualifiedName,
			org.drip.capital.label.Coordinate.FQN_DELIMITER
		);

		if (null == regionRiskType || 2 != regionRiskType.length)
		{
			return null;
		}

		try
		{
			return new RegionRiskTypeCoordinate (
				regionRiskType[0],
				regionRiskType[1]
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RegionRiskTypeCoordinate Constructor
	 * 
	 * @param region Region
	 * @param riskType Risk Type
	 * 
	 * @throws java.lang.Exception Thown if the Inputs are Invalid
	 */

	public RegionRiskTypeCoordinate (
		final java.lang.String region,
		final java.lang.String riskType)
		throws java.lang.Exception
	{
		super (
			region,
			riskType
		);

		if (null == (_region = region) || _region.isEmpty())
		{
			throw new java.lang.Exception ("RegionRiskTypeCoordinate Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the iVAST Region
	 * 
	 * @return The iVAST Region
	 */

	public java.lang.String region()
	{
		return _region;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _region + org.drip.capital.label.Coordinate.FQN_DELIMITER + riskType();
	}
}
